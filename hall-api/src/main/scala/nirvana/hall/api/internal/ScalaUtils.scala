package nirvana.hall.api.internal

import com.google.protobuf.MessageOrBuilder
import org.apache.tapestry5.ioc.internal.services.PropertyAccessImpl

import scala.reflect.runtime._
import scala.reflect.runtime.universe._
import scala.reflect.{ClassTag, classTag}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-05-26
 */
object ScalaUtils {
  private val mirror = universe.runtimeMirror(getClass.getClassLoader)
  def currentTimeInSeconds = (System.currentTimeMillis() / 1000).toInt

  /**
   * Returns the [[java.lang.Class]] corresponding to the given class tag.
   *
   * @param tag the class tag to convert
   * @tparam T the tag's bound type
   * @return the runtime class of the tag
   */
  def tagToClass[T](tag: ClassTag[T]): Class[T] = {
    tag.runtimeClass.asInstanceOf[Class[T]]
  }

  /**
   * Returns the [[java.lang.Class]] corresponding to the given type.
   *
   * @tparam T the bound type to convert
   * @return the runtime class of the given type
   */
  def typeToClass[T: ClassTag]: Class[T] = {
    tagToClass(classTag[T])
  }
  //class access
  private val access = new PropertyAccessImpl

  //get value from Protobuf Object
  private def getValueFromProtoObject(protoObject:AnyRef,name:String):Any={
  }

  /**
   * convert Protobuf to Scala case object
   * @param obj CodeData
   */
  def convertProtobufToScala[T](obj:AnyRef)(implicit typeTag: TypeTag[T]):T={
    val clazzSymbol = typeOf[T].typeSymbol
    def findValue(symbol:Symbol):Any= {
      //retrieve the value by property's name
      val value = try { access.get(obj, symbol.name.toString) } catch{ case e:IllegalArgumentException => Nil }
      if(symbol.info.resultType <:< typeOf[Option[_]]){ //is option parameter
        Option(value)
      }else{
        value
      }
    }

    val clazzMirror = clazzSymbol.asClass
    val c = clazzMirror.primaryConstructor.asMethod
    val args = c.paramLists.map(x=>x.map(findValue)).head //take first group as constructor parameter
    val constructor =  mirror.reflectClass(clazzMirror).reflectConstructor(c)
    constructor.apply(args:_*).asInstanceOf[T]
  }

  /**
   * retrieve values from scala case class,set  to Protobuf builder instance
   */
  def convertScalaToProtobuf[T,P <: MessageOrBuilder](obj:T,builder:P)(implicit typeTag: ClassTag[T]):Unit={
    val instance = mirror.reflect(obj)
    val clazzSymbol = instance.symbol
    val clazzType = clazzSymbol.asType.toType
    val clazzMirror = clazzSymbol.asClass
    def setValue(symbol:Symbol):Any= {
      val termSymbol = clazzType.decl(symbol.name.toTermName).asTerm
      var value:Any= instance.reflectField(termSymbol).get
      val javaProperty = symbol.name.toString
      val firstChar = javaProperty.charAt(0)
      val propertyMethod = "set"+javaProperty.toString.updated(0,firstChar.toUpper)
      if(symbol.info.resultType <:< typeOf[Option[_]]) { //is option parameter
        value.asInstanceOf[Option[_]] match{
          case Some(v)=> value = v
          case None => value = null
        }
      }
      try {
        //FIXME: the method use reflection to set value,not cache property.TODO: use asm or cache property
        if(value != null)
          builder.getClass.getMethods.find(_.getName == propertyMethod).foreach(_.invoke(builder, value.asInstanceOf[AnyRef]))
      }catch{
        case e: Throwable =>
          throw new IllegalAccessException("fail to set value for "+javaProperty+",reason:"+e.getMessage)
      }
    }

    val c = clazzMirror.primaryConstructor.asMethod
    c.paramLists.foreach(_.foreach(setValue))
  }
}
