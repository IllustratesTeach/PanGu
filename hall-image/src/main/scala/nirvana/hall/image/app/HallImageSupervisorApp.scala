package nirvana.hall.image.app

import java.io.{IOException, InputStream, OutputStream}
import java.lang.ProcessBuilder.Redirect
import java.util.concurrent.{CountDownLatch, TimeUnit}

import monad.support.services.LoggerSupport

import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer
import scala.util.control.NonFatal

/**
 * supervise hall image app
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-02-15
 */
object HallImageSupervisorApp extends LoggerSupport {
  private val max = 4

  def main(args: Array[String]): Unit = {
    if(args != null)
      info("image jvm args:"+args.toSeq)
    var process:Option[Process] = None
    val countDown = new CountDownLatch(1)

    var n = Integer.MAX_VALUE
    while (true) {
      if(n> max || countDown.await(15,TimeUnit.MINUTES)){
        //first stop process
        process.foreach { p =>
          try {
            info("destroy process...")
            p.destroy()
          } catch{
            case NonFatal(e)=>
              println(e.printStackTrace())
          }
        }
        info("start process...")
        process = Some(startDecompressProcess(args))
        info("process stared!")
        n = 0
      }else{
        n += 1
      }
    }
  }

  def startDecompressProcess(jvmOptions:Array[String]): Process = {
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

    commandParameters += "nirvana.hall.image.app.HallImageApp"
    //"nirvana.hall.image.internal.FirmMain"

    val pb = new ProcessBuilder(commandParameters: _*)
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
      new RedirectThread(stdout, System.err, "stdout reader for decompress").start()
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
            out.write(buf, 0, len)
            out.flush()
            len = in.read(buf)
          }
        } {
          if (propagateEof) {
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
