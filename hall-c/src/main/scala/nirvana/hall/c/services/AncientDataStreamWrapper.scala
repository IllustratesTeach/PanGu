package nirvana.hall.c.services

import java.io.{BufferedInputStream, InputStream, OutputStream}
import java.nio.ByteBuffer
import javax.imageio.stream.ImageInputStream

import nirvana.hall.c.services.AncientData._
import org.jboss.netty.buffer.ChannelBuffer
import org.xsocket.{IDataSink, IDataSource}

/**
 * wrap netty's ChannelBuffer and xSocket's IDataSink
 */
trait AncientDataStreamWrapper {
  implicit def asStreamReader(is:IDataSource):StreamReader = new DataSourceReader(is)
  implicit def asStreamReader(is:ChannelBuffer):StreamReader = new ChannelBufferReader(is)
  implicit def asStreamReader(is:InputStream):StreamReader = {
    if(is.markSupported()) new InputStreamReader(is) else new InputStreamReader(new BufferedInputStream(is))
  }
  implicit def asStreamReader(is:ImageInputStream):StreamReader = new ImageInputStreamReader(is)

  implicit def asStreamWriter(output: OutputStream): StreamWriter = new {
    private val arr = new Array[Byte](8)
    private val tmp = ByteBuffer.wrap(arr)
    def writeShort(i: Int): Unit = {
      tmp.position(0)
      tmp.putShort(i.toShort)
      output.write(arr,0,2)
    }
    def writeInt(i: Int): Unit = {
      tmp.position(0)
      tmp.putInt(i)
      output.write(arr,0,4)
    }
    def writeBytes(src: Array[Byte]): Unit = {
      output.write(src)
    }
    def writeLong(i: Long): Unit = {
      tmp.position(0)
      tmp.putLong(i)
      output.write(arr,0,8)
    }
    def writeByte(byte: Int): Unit = output.write(byte)
    def writeZero(length: Int): Unit = {
      if (length == 0) {
        return
      }
      if (length < 0) {
        throw new IllegalArgumentException("length must be 0 or greater than 0.")
      }
      val nLong = length >>> 3
      val nBytes = length & 7
      0 until nLong foreach (x => writeLong(0))
      if (nBytes == 4) {
        writeInt(0)
      } else if (nBytes < 4) {
        0 until nBytes foreach (x => writeByte(0))
      } else {
        writeInt(0)
        0 until (nBytes - 4) foreach (x => writeByte(0))
      }
    }
  }
  implicit def asStreamWriter(dataSink: IDataSink): StreamWriter = new {
    def writeShort(i: Int): Unit = dataSink.write(i.toShort)
    def writeInt(i: Int): Unit = dataSink.write(i)
    def writeBytes(src: Array[Byte]): Unit = dataSink.write(src, 0, src.length)
    def writeLong(i: Long): Unit = dataSink.write(i)
    def writeByte(byte: Int): Unit = dataSink.write(byte.toByte)
    def writeZero(length: Int): Unit = {
      if (length == 0) {
        return
      }
      if (length < 0) {
        throw new IllegalArgumentException("length must be 0 or greater than 0.")
      }
      val nLong = length >>> 3
      val nBytes = length & 7
      0 until nLong foreach (x => writeLong(0))
      if (nBytes == 4) {
        writeInt(0)
      } else if (nBytes < 4) {
        0 until nBytes foreach (x => writeByte(0))
      } else {
        writeInt(0)
        0 until (nBytes - 4) foreach (x => writeByte(0))
      }
    }
  }
}
