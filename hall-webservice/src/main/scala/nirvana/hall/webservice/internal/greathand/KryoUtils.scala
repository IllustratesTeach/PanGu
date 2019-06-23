package nirvana.hall.webservice.internal.greathand

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.util

import cherish.component.jpa.ImageData
import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.{Input, Output}
import com.esotericsoftware.kryo.serializers.{CollectionSerializer, JavaSerializer}
import monad.support.services.LoggerSupport

import scala.collection.mutable.ArrayBuffer


object KryoUtils extends LoggerSupport{

  /**
    * 把对象列表序列化
    * @param obj
    * @return
    */
  def serializationList(obj:util.ArrayList[ImageData]):Array[Byte] = {
    val kryo = new Kryo()
    kryo.setReferences(false)
    kryo.setRegistrationRequired(true)

    val serializer = new CollectionSerializer()
    serializer.setElementClass(new ImageData().getClass, new JavaSerializer())
    serializer.setElementsCanBeNull(false)

    kryo.register(new ImageData().getClass, new JavaSerializer())
    //kryo.register(, serializer[ImageData])


    val baos = new ByteArrayOutputStream()
    val output = new Output(baos)
    kryo.writeObject(output, obj)
    output.flush()
    output.close()
    val bytes = baos.toByteArray()
    try {
      baos.flush()
      baos.close()
    } catch {
      case ex:Exception =>
        error("{}",ex.getMessage)
    }
    bytes
  }

  /**
    * 反序列化为对象列表
    * @param
    * @return
    */
//  def deserializationList(obj:Array[Byte]):List[ImageData] ={
//    val kryo = new Kryo()
//    kryo.setReferences(false)
//    kryo.setRegistrationRequired(true)
//    val serializer = new CollectionSerializer()
//    serializer.setElementClass(new ImageData().getClass, new JavaSerializer())
//    serializer.setElementsCanBeNull(false)
//    kryo.register(new ImageData().getClass, kryo.getSerializer(new ImageData().getClass))
//    //kryo.register(,serializer)
//    val bais = new ByteArrayInputStream(obj)
//    val input = new Input(bais)
//    kryo.readObject(input, new util.ArrayList[ImageData]().getClass, serializer[ImageData]).asInstanceOf[List[ImageData]]
//  }


  def main(args: Array[String]): Unit = {
    val datas = new ArrayBuffer[ImageData]()
    for(a <- 0 until(9)) {
      val data = new ImageData()
      data.fgp = a
      datas += data
    }
    datas.toList
  }

}
