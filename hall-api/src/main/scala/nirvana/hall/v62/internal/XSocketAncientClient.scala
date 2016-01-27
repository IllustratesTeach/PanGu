package nirvana.hall.v62.internal

import java.net.InetAddress

import monad.support.services.LoggerSupport
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.AncientData._
import nirvana.hall.v62.services.{AncientClient, ChannelOperator}
import org.jboss.netty.buffer.{ChannelBuffer, ChannelBuffers}
import org.xsocket.connection.{BlockingConnection, IBlockingConnection}

import scala.reflect._

/**
 * ancient client based on xsocket
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-03
 */
class XSocketAncientClient(host:String,port:Int,connectionTimeoutSecs:Int,readTimeoutSecs:Int) extends AncientClient with LoggerSupport{
  def executeInChannel[T](action:ChannelOperator=>T): T={
    val connection = new BlockingConnection(InetAddress.getByName(host),port,connectionTimeoutSecs*1000) with MarkStreamReaderSupport{
      override def markReaderIndex(): Unit = markReadPosition()

      override def resetReaderIndex(): Unit = removeReadMark()
    }
    connection.setReadTimeoutMillis(readTimeoutSecs * 1000)
    val channelHolder = new XSocketChannelOperator(connection)
    try{
      action(channelHolder)
    }finally{
      connection.close()
    }
  }
  class XSocketChannelOperator(connection:IBlockingConnection with MarkStreamReaderSupport) extends ChannelOperator{
    /**
     * write message to channel
     * @param data data written
     * @param manifest class reflection
     * @tparam R return type
     * @return server return message object
     */
    override def writeMessage[R <: AncientData](data: Any*)(implicit manifest: Manifest[R]): R = {
      data.foreach{
        case el:AncientData =>
          /*
          val buffer = ChannelBuffers.buffer(el.getDataSize)
          val bytes = el.writeToChannelBuffer(buffer).array()
          val count = connection.write(bytes,0,bytes.length)
          if(count != bytes.length){
            throw new IllegalAccessException("fail to write byte array")
          }
          */
          el.writeToStreamWriter(connection)
        case el:Array[Byte] =>
          connection.write(el,0,el.length)
        case other=>
          throw new IllegalArgumentException("data unsupported "+other)
      }
      receive[R]()
    }

    override def receiveByteArray(len: Int): ChannelBuffer = {
//      val destByteBuffer = ByteBuffer.allocate(len)
//      connection.read(destByteBuffer)
//      ChannelBuffers.wrappedBuffer(destByteBuffer)
//      ChannelBuffers.wrappedBuffer(connection.readByteBufferByLength(len):_*)
      ChannelBuffers.wrappedBuffer(connection.readBytesByLength(len))
    }

    /**
     * write byte array to channel
     * @param data data be sent
     * @param offset byte array offset
     * @param length data length
     * @param manifest class reflection
     * @tparam R return type
     * @return data from server
     */
    override def writeByteArray[R <: AncientData](data: Array[Byte], offset: Int, length: Int)(implicit manifest: Manifest[R]): R = {
      connection.write(data,offset,length)
      receive[R]()
    }

    override def writeByteArray[R <: AncientData](data: Array[Byte])(implicit manifest: Manifest[R]): R = {
      writeByteArray(data,0,data.length)
    }

    override def receive[R <: AncientData](target:R): R = {
      target.fromStreamReader(connection)
    }
    override def receive[R <: AncientData :ClassTag](): R = {
      classTag[R] match{
        case t if t == classTag[Nothing] =>
          null.asInstanceOf[R]
        case t if t == classTag[NoneResponse] =>
          new NoneResponse().asInstanceOf[R]
        case _ =>
          val dataInstance = classTag[R].runtimeClass.newInstance().asInstanceOf[R]
          dataInstance.fromStreamReader(connection)
          /*
          val buffer = receiveByteArray(dataInstance.getDataSize)
          dataInstance.fromChannelBuffer(buffer).asInstanceOf[R]
          */
      }
    }
  }
}
