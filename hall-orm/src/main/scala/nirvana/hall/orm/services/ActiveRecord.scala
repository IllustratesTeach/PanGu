package nirvana.hall.orm.services

import javax.persistence.{EntityManager, Transient}

import org.apache.tapestry5.ioc.ObjectLocator

import scala.collection.JavaConversions
import scala.language.dynamics
import scala.language.experimental.macros
import scala.reflect._

/**
 * active record
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-03
 */
object ActiveRecord {
  var objectLocator:ObjectLocator= _
  def save[T](record:T):T={
    getService[EntityService].save(record)
  }
  def delete[T](record:T): Unit ={
    getService[EntityService].delete(record)
  }
  private[orm] def getService[T:ClassTag]:T={
    if(objectLocator == null)
      throw new IllegalStateException("object locator is null")
    objectLocator.getService(classTag[T].runtimeClass.asInstanceOf[Class[T]])
  }
}
trait ActiveRecord {
  @Transient
  def save():this.type = {
    ActiveRecord.save(this)
  }
  def delete(): Unit ={
    ActiveRecord.delete(this)
  }
}
trait ActiveRecordInstance[A] extends Dynamic{
  def applyDynamicNamed(name:String)(params:(String,Any)*):List[A]=macro HallOrmMacroDefine.findDynamicImplNamed[A]
  def applyDynamic(name:String)(params:Any*):List[A]=macro HallOrmMacroDefine.findDynamicImpl[A]
  def selfFind(ql:String)(params:Any*): List[A]={
    val entityManager = ActiveRecord.getService[EntityManager]
    val query = entityManager.createQuery(ql)
    params.zipWithIndex.foreach{
      case (value,index)=>
        query.setParameter(index+1,value)
    }
    JavaConversions.asScalaBuffer[A](query.getResultList.asInstanceOf[java.util.List[A]]).toList
  }
  def selfFindNamed(name:String)(params:(String,Any)*): List[A]={
    params.foreach(println)
    Nil
  }
}
