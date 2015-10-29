package nirvana.hall.api.internal.ancientry

import org.jboss.netty.buffer.ChannelBuffer

/**
 * support to serialize/unserialize data
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-29
 */
class AncientDataSupport {
  /**
   * serialize to channel buffer
   * @param channelBuffer netty channel buffer
   */
  def writeToChannelBuffer(channelBuffer:ChannelBuffer): Unit = {
    getClass.getDeclaredFields.foreach{f=>
      val annotation = f.getAnnotation(classOf[Length])
      val fieldValue = getClass.getMethod(f.getName).invoke(this);//.asInstanceOf[AnyRef]
    var dataLength = 0
      if(annotation != null){
        dataLength = annotation.value()
      }
      val BYTE_CLASS         = classOf[Byte]
      val CHAR_CLASS         = classOf[Char]
      val SHORT_CLASS        = classOf[Short]
      val INT_CLASS          = classOf[Int]
      val LONG_CLASS         = classOf[Long]
      val STRING_CLASS       = classOf[String]
      val BYTE_ARRAY_CLASS   = classOf[Array[Byte]]
      val ANCIENT_CLASS      = classOf[AncientDataSupport]
      f.getType match{
        case `BYTE_CLASS` =>
          channelBuffer.writeByte(fieldValue.asInstanceOf[Byte])
        case `CHAR_CLASS` =>
          channelBuffer.writeChar(fieldValue.asInstanceOf[Char])
        case `SHORT_CLASS` =>
          channelBuffer.writeShort(fieldValue.asInstanceOf[Short])
        case `INT_CLASS` =>
          channelBuffer.writeInt(fieldValue.asInstanceOf[Int])
        case `LONG_CLASS` =>
          channelBuffer.writeLong(fieldValue.asInstanceOf[Long])
        case `STRING_CLASS` =>
          val x = fieldValue.asInstanceOf[String]
          var length = 0
          if(x!= null) {
            val bytes = x.getBytes
            length = bytes.length
            if (length > dataLength) {
              throw new IllegalAccessException("value length[" ++ "] > length defined [" + dataLength + "]")
            }
            channelBuffer.writeBytes(bytes)
          }
          channelBuffer.writerIndex(channelBuffer.writerIndex() + dataLength - length)
        case `BYTE_ARRAY_CLASS` =>
          var length = 0
          val x = fieldValue.asInstanceOf[Array[Byte]]
          if(x!= null) {
            length = x.length
            if (length > dataLength) {
              throw new IllegalAccessException("value length[" ++ "] > length defined [" + dataLength + "]")
            }
            channelBuffer.writeBytes(x)
          }
          channelBuffer.writerIndex(channelBuffer.writerIndex() + dataLength - length)

        case `ANCIENT_CLASS` =>
          fieldValue.asInstanceOf[AncientDataSupport].writeToChannelBuffer(channelBuffer)
        case other=>
          throw new IllegalAccessException("unknown type:"+other)
      }
    }
  }

  /**
   * convert channel buffer data as object
   * @param channelBuffer netty channel buffer
   */
  def fromChannelBuffer(channelBuffer: ChannelBuffer): Unit ={
    getClass.getDeclaredFields.foreach{f=>
      val annotation = f.getAnnotation(classOf[Length])
      var dataLength = 0
      if(annotation != null){
        dataLength = annotation.value()
      }
      val BYTE_CLASS         = classOf[Byte]
      val CHAR_CLASS         = classOf[Char]
      val SHORT_CLASS        = classOf[Short]
      val INT_CLASS          = classOf[Int]
      val LONG_CLASS         = classOf[Long]
      val STRING_CLASS       = classOf[String]
      val BYTE_ARRAY_CLASS   = classOf[Array[Byte]]
      val ANCIENT_CLASS      = classOf[AncientDataSupport]
      f.getType match{
        case `BYTE_CLASS` =>
          getClass.getMethod(f.getName+"_$eq",BYTE_CLASS).invoke(this,channelBuffer.readByte().asInstanceOf[AnyRef])
        case `CHAR_CLASS` =>
          getClass.getMethod(f.getName+"_$eq",CHAR_CLASS).invoke(this,channelBuffer.readChar().asInstanceOf[AnyRef])
        case `SHORT_CLASS` =>
          getClass.getMethod(f.getName+"_$eq",SHORT_CLASS).invoke(this,channelBuffer.readShort().asInstanceOf[AnyRef])
        case `INT_CLASS` =>
          getClass.getMethod(f.getName+"_$eq",INT_CLASS).invoke(this,channelBuffer.readInt().asInstanceOf[AnyRef])
        case `LONG_CLASS` =>
          getClass.getMethod(f.getName+"_$eq",LONG_CLASS).invoke(this,channelBuffer.readLong().asInstanceOf[AnyRef])
        case `STRING_CLASS` =>
          val bytes = new Array[Byte](dataLength)
          channelBuffer.readBytes(bytes)
          getClass.getMethod(f.getName+"_$eq",STRING_CLASS).invoke(this,new String(bytes).trim)
        case `BYTE_ARRAY_CLASS` =>
          val bytes = new Array[Byte](dataLength)
          channelBuffer.readBytes(bytes)
          getClass.getMethod(f.getName+"_$eq",BYTE_ARRAY_CLASS).invoke(this,bytes)
        case `ANCIENT_CLASS` =>
          val ancientData = f.getType.newInstance().asInstanceOf[AncientDataSupport]
          ancientData.fromChannelBuffer(channelBuffer)
          getClass.getMethod(f.getName+"_$eq",f.getType).invoke(this,ancientData)
        case other=>
          throw new IllegalAccessException("unknown type:"+other)
      }
    }
  }
}
