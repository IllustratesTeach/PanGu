package nirvana.hall.orm.services

import org.apache.tapestry5.ioc.services.PropertyAccess

import scala.language.dynamics
import scala.language.experimental.macros
import scala.reflect.{ClassTag, classTag}

/**
 * method missing trait
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-01
 */
trait MethodMissing[A] extends Dynamic{
  this:PropertyAccessorSupport =>
  def selectDynamic( field : String ) = Symbol( field )
  def updateDynamic(field:String)(value:Any)={

  }
  def applyDynamicNamed(name:String)(params:(String,Any)*):List[A]=macro HallOrmMacroDefine.findDynamicImplNamed[A]
  def applyDynamic(name:String)(params:Any*):List[A]=macro HallOrmMacroDefine.findDynamicImpl[A]
  def selfFind(name:String)(params:Any*): List[A]={
    println("name:",name)
    params.foreach(println)
    Nil
  }
  def selfFindNamed(name:String)(params:(String,Any)*): List[A]={
    params.foreach(println)
    Nil
  }
  def _create(params:(String,Any)*)(implicit tag:ClassTag[A]):List[A]={
    val instance = classTag[A].runtimeClass.newInstance().asInstanceOf[A]
    params.foreach{
      case (name,value)=>
        propertyAccessor.set(instance,name,value)
    }
    List(instance)
  }
}
trait PropertyAccessorSupport{
  def propertyAccessor:PropertyAccess
}
