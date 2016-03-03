package nirvana.hall.image.app

import java.io.{File, IOException, InputStream, OutputStream}
import java.lang.ProcessBuilder.Redirect
import java.util.concurrent.{CountDownLatch, Executors, TimeUnit}

import monad.support.services.LoggerSupport
import org.apache.commons.io.FileUtils

import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer
import scala.util.control.NonFatal

/**
 * supervise hall image app
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-02-15
 */
object HallImageSupervisorApp extends LoggerSupport {
  private val maxWaitingRestartCount = 4
  @volatile
  private var waitingRestartCount = 0
  @volatile
  private  var running = true
  @volatile
  private  var processes:Array[Process] = _
  private val executors = Executors.newCachedThreadPool()

  def main(args: Array[String]): Unit = {
    if(args != null)
      info("image jvm args:"+args.toSeq)
    val portStart = System.getProperty("server.port.start").toInt
    val numProcess = System.getProperty("server.process.num").toInt

    processes = new Array[Process](numProcess)
    0.until(numProcess).foreach{ i=>
      executors.execute{new Runnable {
        override def run(): Unit = {
          while (running) {
            info("start process ["+i+"]...")
            val process = startDecompressProcess(args,portStart+i)
            processes(i) = process
            info("process ["+i+"] started")
            process.waitFor()
          }
        }
      }}
    }
    executors.execute(new RestartThread())
    sys.addShutdownHook{
      running = false
    }
    Thread.currentThread().join()
  }
  private class RestartThread extends Runnable{
    override def run(): Unit = {
      val countDown = new CountDownLatch(1)
      while (true) {
        if(running && waitingRestartCount > maxWaitingRestartCount || countDown.await(15,TimeUnit.MINUTES)){
          //first stop process
          processes.foreach { p =>
            try {
              info("destroy process...")
              p.destroy()
            } catch{
              case NonFatal(e)=>
                error(e.toString,e)
            }
          }
          waitingRestartCount = 0
        }else{
          waitingRestartCount += 1
        }
      }
    }
  }
  private def startDecompressProcess(jvmOptions:Array[String],port:Int): Process = {
    val javaBinary = System.getProperty("java.home") + "/bin/java"
    val classPath = System.getProperty("java.class.path")
    // Create and start the worker
    val properties = System.getProperties.map { case (k, v) => "-D%s=%s".format(k, v) }
    val commandParameters = new ArrayBuffer[String]()
    commandParameters += javaBinary
    commandParameters.appendAll(properties)
    commandParameters += "-classpath"
    commandParameters += classPath
    if(jvmOptions != null)
      commandParameters ++= jvmOptions

    commandParameters += "-Dserver.port="+port
    val serverHome = new File(System.getProperty("server.home")).getAbsolutePath
    commandParameters += "-Dserver.home="+serverHome

    commandParameters += "nirvana.hall.image.app.HallImageApp"
    //"nirvana.hall.image.internal.FirmMain"

    val workDirectory = new File("worker"+port)
    FileUtils.forceMkdir(workDirectory)

    val pb = new ProcessBuilder(commandParameters: _*)
    pb.directory(workDirectory)
    val workerEnv = pb.environment()
    pb.redirectErrorStream(true)
    pb.redirectOutput(Redirect.PIPE)
    val worker = pb.start()
    // Redirect worker stdout and stderr
    redirectStreamsToStderr(worker.getInputStream, worker.getErrorStream)
    sys.addShutdownHook {
      worker.destroy()
    }
    worker
  }

  /**
   * Redirect the given streams to our stderr in separate threads.
   */
  private def redirectStreamsToStderr(stdout: InputStream, stderr: InputStream) {
    try {
      new RedirectThread(stdout, System.out, "stdout reader for decompress").start()
      new RedirectThread(stderr, System.err, "stderr reader for decompress").start()
    } catch {
      case e: Exception =>
        logger.error("Exception in redirecting streams", e)
    }
  }

  private class RedirectThread( in: InputStream,
                                out: OutputStream,
                                name: String,
                                propagateEof: Boolean = false)
    extends Thread(name) {

    setDaemon(true)

    override def run() {
      scala.util.control.Exception.ignoring(classOf[IOException]) {
        // FIXME: We copy the stream on the level of bytes to avoid encoding problems.
        tryWithSafeFinally {
          val buf = new Array[Byte](1024)
          var len = in.read(buf)
          while (len != -1) {
            if(out != null) {
              out.write(buf, 0, len)
              out.flush()
            }
            len = in.read(buf)
          }
        } {
          if (propagateEof) {
            if(out != null)
              out.close()
          }
        }
      }
    }
  }

  def tryWithSafeFinally[T](block: => T)(finallyBlock: => Unit): T = {
    // It would be nice to find a method on Try that did this
    var originalThrowable: Throwable = null
    try {
      block
    } catch {
      case t: Throwable =>
        // Purposefully not using NonFatal, because even fatal exceptions
        // we don't want to have our finallyBlock suppress
        originalThrowable = t
        throw originalThrowable
    } finally {
      try {
        finallyBlock
      } catch {
        case t: Throwable =>
          if (originalThrowable != null) {
            originalThrowable.addSuppressed(t)
            logger.warn(s"Suppressing exception in finally: " + t.getMessage, t)
            throw originalThrowable
          } else {
            throw t
          }
      }
    }
  }
}
