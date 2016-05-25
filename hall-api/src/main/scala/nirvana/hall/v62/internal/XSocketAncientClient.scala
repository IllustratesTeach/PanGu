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
    val connection = new BlockingConnection(InetAddress.getByName(host),port,connectionTimeoutSecs*1000)
//    connection.setReadTimeoutMillis(readTimeoutSecs * 1000)
    val channelHolder = new XSocketChannelOperator(connection)
    try{
      action(channelHolder)
    }finally{
      connection.close()
    }
  }
  class XSocketChannelOperator(connection:IBlockingConnection) extends ChannelOperator{

    /**
      * 得到服务器信息
      *
      * @return
      */
    override def getServerInfo: String = connection.getRemoteAddress.toString

    override def receiveByteArray(len: Int): ChannelBuffer = {
//      val destByteBuffer = ByteBuffer.allocate(len)
//      connection.read(destByteBuffer)
//      ChannelBuffers.wrappedBuffer(destByteBuffer)
//      ChannelBuffers.wrappedBuffer(connection.readByteBufferByLength(len):_*)
      ChannelBuffers.wrappedBuffer(connection.readBytesByLength(len))
    }

    override def writeByteArray[R <: AncientData:ClassTag](data: Array[Byte], offset: Int, length: Int): R = {
      connection.write(data,offset,length)
      receive[R]()
    }


    override def writeAncientData[T <: AncientData, R <: AncientData : ClassManifest](target: T): R = {
      target.writeToStreamWriter(connection)
      receive[R]()
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
