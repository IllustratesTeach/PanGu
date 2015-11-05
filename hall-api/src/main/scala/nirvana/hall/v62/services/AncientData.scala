package nirvana.hall.v62.services

import nirvana.hall.v62.annotations.{IgnoreTransfer, Length}
import org.apache.tapestry5.ioc.internal.services.PropertyAccessImpl
import org.jboss.netty.buffer.ChannelBuffer

/**
 * support to serialize/unserialize data
 * TODO using byte transformation to convert data
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-29
 */
object AncientData{
  private val propertyAccessor = new PropertyAccessImpl
}
class AncientData {
  private var dataSize:Int = 0
  /**
   * get data size
   */
  def getDataSize:Int={
    if(dataSize == 0) {
      val BYTE_CLASS = classOf[Byte]
      val CHAR_CLASS = classOf[Char]
      val SHORT_CLASS = classOf[Short]
      val INT_CLASS = classOf[Int]
      val LONG_CLASS = classOf[Long]
      val STRING_CLASS = classOf[String]
      val BYTE_ARRAY_CLASS = classOf[Array[Byte]]
      val ANCIENT_CLASS = classOf[AncientData]
      getClass.getDeclaredFields.filterNot(_.isAnnotationPresent(classOf[IgnoreTransfer])).foreach { f =>
        val annotation = f.getAnnotation(classOf[Length])
        var dataLength = 0
        if (annotation != null) {
          dataLength = annotation.value()
        }
        f.getType match {
          case `BYTE_CLASS` =>
            dataSize += 1
          case `CHAR_CLASS` =>
            dataSize += 1
          case `SHORT_CLASS` =>
            dataSize += 2
          case `INT_CLASS` =>
            dataSize += 4
          case `LONG_CLASS` =>
            dataSize += 8
          case `STRING_CLASS` =>
            dataSize += dataLength
          case `BYTE_ARRAY_CLASS` =>
            dataSize += dataLength
          case other =>
            if(ANCIENT_CLASS.isAssignableFrom(other)) {
              dataSize += other.newInstance().asInstanceOf[AncientData].getDataSize
            }else if(other.isArray){
              val componentType = other.getComponentType
              if(ANCIENT_CLASS.isAssignableFrom(componentType)){
                dataSize += componentType.newInstance().asInstanceOf[AncientData].getDataSize * dataLength
              }else{
                throw new IllegalAccessException("unknown type:" + componentType)
              }
            }else {
              throw new IllegalAccessException("unknown type:" + other)
            }
        }
      }
    }
    dataSize
  }
  /**
   * serialize to channel buffer
   * @param channelBuffer netty channel buffer
   */
  def writeToChannelBuffer(channelBuffer:ChannelBuffer): ChannelBuffer = {
    getClass.getDeclaredFields.filterNot(_.isAnnotationPresent(classOf[IgnoreTransfer])).foreach { f =>
      val annotation = f.getAnnotation(classOf[Length])
      val fieldValue = AncientData.propertyAccessor.get(this,f.getName) //.invoke(this)
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
      val ANCIENT_CLASS      = classOf[AncientData]
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
          fieldValue.asInstanceOf[AncientData].writeToChannelBuffer(channelBuffer)
        case other=>
          if(ANCIENT_CLASS.isAssignableFrom(other)) {
            if(fieldValue == null){
              val size = other.newInstance().asInstanceOf[AncientData].getDataSize
              channelBuffer.writerIndex(channelBuffer.writerIndex() + size)
            }else{
              fieldValue.asInstanceOf[AncientData].writeToChannelBuffer(channelBuffer)
            }
          }else if(other.isArray){
            val componentType = other.getComponentType
            if(ANCIENT_CLASS.isAssignableFrom(componentType)){
              val componentSize = componentType.newInstance().asInstanceOf[AncientData].getDataSize
              if(fieldValue == null){//fill empty data
                channelBuffer.writerIndex(channelBuffer.writerIndex() + componentSize * dataLength)
              }else{
                val arr = fieldValue.asInstanceOf[Array[_]]
                arr.foreach { x =>
                  if(x == null)//increase writer index
                    channelBuffer.writerIndex(channelBuffer.writerIndex() + componentSize)
                  else
                    x.asInstanceOf[AncientData].writeToChannelBuffer(channelBuffer)
                }

                val remain = dataLength - arr.length
                if(remain > 0)
                  channelBuffer.writerIndex(channelBuffer.writerIndex() + componentSize * remain)
              }
            }else{
              throw new IllegalAccessException("unknown type:" + componentType)
            }
          }else {
            throw new IllegalAccessException("unknown type:" + other)
          }
      }
    }

    channelBuffer
  }

  /**
   * convert channel buffer data as object
   * @param channelBuffer netty channel buffer
   */
  def fromChannelBuffer(channelBuffer: ChannelBuffer): this.type ={
    getClass.getDeclaredFields.filterNot(_.isAnnotationPresent(classOf[IgnoreTransfer])).foreach { f =>
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
      val ANCIENT_CLASS      = classOf[AncientData]
      f.getType match{
        case `BYTE_CLASS` =>
          AncientData.propertyAccessor.set(this,f.getName,channelBuffer.readByte())
        case `CHAR_CLASS` =>
          AncientData.propertyAccessor.set(this,f.getName,channelBuffer.readChar())
        case `SHORT_CLASS` =>
          AncientData.propertyAccessor.set(this,f.getName,channelBuffer.readShort())
        case `INT_CLASS` =>
          AncientData.propertyAccessor.set(this,f.getName,channelBuffer.readInt())
        case `LONG_CLASS` =>
          AncientData.propertyAccessor.set(this,f.getName,channelBuffer.readLong())
        case `STRING_CLASS` =>
          val bytes = new Array[Byte](dataLength)
          channelBuffer.readBytes(bytes)
          AncientData.propertyAccessor.set(this,f.getName,new String(bytes).trim)
        case `BYTE_ARRAY_CLASS` =>
          val bytes = new Array[Byte](dataLength)
          channelBuffer.readBytes(bytes)
          AncientData.propertyAccessor.set(this,f.getName,bytes)
        case `ANCIENT_CLASS` =>
          val ancientData = f.getType.newInstance().asInstanceOf[AncientData]
          ancientData.fromChannelBuffer(channelBuffer)
          AncientData.propertyAccessor.set(this,f.getName,ancientData)
        case other=>
          if(ANCIENT_CLASS.isAssignableFrom(other)) {
            val ancientData = other.newInstance().asInstanceOf[AncientData]
            ancientData.fromChannelBuffer(channelBuffer)
            AncientData.propertyAccessor.set(this,f.getName,ancientData)
          }else if(other.isArray){
            val componentType = other.getComponentType
            if(ANCIENT_CLASS.isAssignableFrom(componentType)){
              val dataArray = java.lang.reflect.Array.newInstance(componentType,dataLength)

              0 until dataLength foreach { x=>
                val value = componentType.newInstance().asInstanceOf[AncientData]
                value.fromChannelBuffer(channelBuffer)
                java.lang.reflect.Array.set(dataArray,x,value)
              }
              AncientData.propertyAccessor.set(this,f.getName,dataArray)
            }else{
              throw new IllegalAccessException("unknown type:" + componentType)
            }
          }else {
            throw new IllegalAccessException("unknown type:" + other)
          }
      }
    }

    this
  }
}

class DynamicByteArray(val data:Array[Byte]) extends AncientData{
  /**
   * convert channel buffer data as object
   * @param channelBuffer netty channel buffer
   */
  override def fromChannelBuffer(channelBuffer: ChannelBuffer): this.type = {
    if(channelBuffer.readableBytes() >= data.length){
      channelBuffer.readBytes(data)
    }
    this
  }

  /**
   * get data size
   */
  override def getDataSize: Int = data.length

  /**
   * serialize to channel buffer
   * @param channelBuffer netty channel buffer
   */
  override def writeToChannelBuffer(channelBuffer: ChannelBuffer): ChannelBuffer = {
    channelBuffer.writeBytes(data)
    channelBuffer
  }
}
