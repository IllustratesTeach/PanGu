package nirvana.hall.spark.services

import java.util

import nirvana.hall.spark.config.SparkConfigProperty

import scala.collection.convert.decorateAsScala._
/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-30
  */
object SysProperties{
  private var sysProperties:Map[String,String] = _
  def setProperties(properties: util.List[SparkConfigProperty]): Unit ={
    val data = new util.HashMap[String,String](properties.size())
    val it = properties.iterator()
    if(it.hasNext) {
      val property = it.next()
      data.put(property.name,property.value)
    }
    sysProperties = data.asScala.toMap
  }
  def getPropertyOption(name:String): Option[String] ={
    sysProperties.get(name)
  }
  def getBoolean(name:String,defaultValue:Boolean):Boolean={
    getPropertyOption(name) match{
      case Some(value)=>
        value.toBoolean
      case None=>
        defaultValue
    }
  }
}
