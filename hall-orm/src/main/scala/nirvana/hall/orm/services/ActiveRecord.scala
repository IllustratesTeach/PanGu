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
  def find[T](dsl:QueryDSL[T]):Stream[T]={
    val entityManager = ActiveRecord.getService[EntityManager]
    val query = entityManager.createQuery(dsl.ql)
    dsl.params.zipWithIndex.foreach{
      case (value,index)=>
        query.setParameter(index+1,value)
    }
    JavaConversions.asScalaBuffer[T](query.getResultList.asInstanceOf[java.util.List[T]]).toStream
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
class QueryDSL[A](val ql:String,val params:Seq[Any]) extends Dynamic{
  var limit:Int = -1
  var offset:Int = -1
  var orderBy:Option[String]=None
  def applyDynamicNamed(name:String)(params:(String,Any)*):this.type=macro HallOrmMacroDefine.dslDynamicImplNamed[A,this.type]
  def internalOrder(params:(String,Any)*):this.type= {
    params.foreach{case (key,value)=>
      orderBy match{
        case Some(o) =>
          orderBy = Some(o+",%s %s".format(key,value))
        case None=>
          orderBy = Some("%s %s".format(key,value))
      }
    }
    this
  }
}
trait ActiveRecordInstance[A] extends Dynamic{

  def applyDynamicNamed(name:String)(params:(String,Any)*):List[A]=macro HallOrmMacroDefine.findDynamicImplNamed[A]
  def applyDynamic(name:String)(params:Any*):QueryDSL[A]= macro HallOrmMacroDefine.findDynamicImpl[A,QueryDSL[A]]

  def selfFind(ql:String)(params:Any*): QueryDSL[A]={
    new QueryDSL[A](ql,params)
  }
  def selfFindNamed(name:String)(params:(String,Any)*): List[A]={
    params.foreach(println)
    Nil
  }
}
