package nirvana.hall.api.internal

import nirvana.hall.protocol.sys.SyncDictProto.SyncDictResponse.CodeData
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
    try {
      access.get(protoObject, name)
    }catch{
      case e:IllegalArgumentException => //property not found
        Nil
    }
  }

  /**
   * convert Protobuf to Scala case object
   * @param obj CodeData
   */
  def convertProtobufToScala[T](obj:CodeData)(implicit typeTag: TypeTag[T],clazzTag:ClassTag[T]):T={
    val clazzSymbol = typeOf[T].typeSymbol
    val clazzType = clazzSymbol.asType.toType
    def findValue(symbol:Symbol):Any= {
      val value = getValueFromProtoObject(obj, symbol.name.toString)
      symbol.info.resultType.typeArgs match {
        case Nil =>
          value
        case other =>
          if(symbol.info.resultType <:< typeOf[Option[_]]){ //is option parameter
            Option(value)
          }else if(other.head =:= typeOf[Byte]){ //is array of byte
            value
          }else{
            throw new UnsupportedOperationException("")
          }
      }
    }

    val args = clazzType.members
      .filter(_.isTerm)
      .filter(_.asTerm.isAccessor)
      .map(findValue).toSeq.reverse

    val clazzMirror = clazzSymbol.asClass
    val c = clazzMirror.primaryConstructor.asMethod
    mirror.reflectClass(clazzMirror).reflectConstructor(c)(args:_*).asInstanceOf[T]
  }
}
