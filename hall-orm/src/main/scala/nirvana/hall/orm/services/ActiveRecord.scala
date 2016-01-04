package nirvana.hall.orm.services

import javax.persistence.{Id, EntityManager, Transient}

import org.apache.tapestry5.ioc.ObjectLocator

import scala.collection.{GenTraversableOnce, JavaConversions}
import scala.collection.generic.CanBuildFrom
import scala.collection.immutable.Stream
import scala.language.dynamics
import scala.language.experimental.macros
import scala.reflect.{ClassTag, classTag}
import ActiveRecord._

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
  def find[T](dsl:Relation[T]):Stream[T]={
    val entityManager = ActiveRecord.getService[EntityManager]
    val fullQl = dsl.orderBy match{
      case Some(order) =>
        dsl.ql + " order by "+order
      case None =>
        dsl.ql
    }
    val query = entityManager.createQuery(fullQl)

    dsl.params.zipWithIndex.foreach{
      case (value,index)=>
        query.setParameter(index+1,value)
    }
    if(dsl.offset > -1)
      query.setFirstResult(dsl.offset)
    if(dsl.limit > -1)
      query.setMaxResults(dsl.limit)

    JavaConversions.asScalaBuffer[T](query.getResultList.asInstanceOf[java.util.List[T]]).toStream
  }
  private[orm] def getService[T:ClassTag]:T={
    if(objectLocator == null)
      throw new IllegalStateException("object locator is null")
    objectLocator.getService(classTag[T].runtimeClass.asInstanceOf[Class[T]])
  }
  private[orm] def getPrimaryKey[A](implicit clazzTag:ClassTag[A]): String={
    val clazz = clazzTag.runtimeClass.asInstanceOf[Class[A]]
    val field = clazz.getDeclaredFields.find(_.isAnnotationPresent(classOf[Id]))
    field.getOrElse(throw new IllegalStateException("primary key is null")).getName
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
case class Relation[A](ql:String, params:Seq[Any]) extends Dynamic with Product{
  private[services] var limit:Int = -1
  private[services] var offset:Int = -1
  private[services] var orderBy:Option[String]=None
  private var underlying_result:Stream[A] = _
  private def executeQuery: Stream[A] = {
    if(underlying_result == null)
      underlying_result = ActiveRecord.find(this)
    underlying_result
  }
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
  def limit(n:Int):this.type={
    limit=n
    this
  }
  def take(n:Int=1)(implicit classTag:ClassTag[A]):Relation[A]={
    limit(n)
  }
  def first(n:Int=1)(implicit classTag:ClassTag[A]):Relation[A]= {
    take(n).internalOrder(getPrimaryKey -> "ASC")
  }
  def last(n:Int=1)(implicit classTag:ClassTag[A]):Relation[A]= {
    take(n).internalOrder(getPrimaryKey->"DESC")
  }
  def offset(n:Int): Relation[A]={
    offset = n
    this
  }
  @inline final def size = executeQuery.size
  @inline final def foreach[U](f: A => U) = executeQuery.foreach(f)
  @inline final def head = executeQuery.head
  @inline final def headOption = executeQuery.headOption
  @inline final def tail = executeQuery.tail
  @inline final def map[B, That](f: A => B)(implicit bf: CanBuildFrom[Stream[A], B, That]): That =  executeQuery.map(f)
  @inline final def flatMap[B, That](f: A => GenTraversableOnce[B])(implicit bf: CanBuildFrom[Stream[A], B, That]): That = executeQuery.flatMap(f)

}
trait ActiveRecordInstance[A] extends Dynamic{

  def applyDynamicNamed(name:String)(params:(String,Any)*):Relation[A]=macro HallOrmMacroDefine.findDynamicImplNamed[A,Relation[A]]
  def applyDynamic(name:String)(params:Any*):Relation[A]= macro HallOrmMacroDefine.findDynamicImpl[A,Relation[A]]

  def internalWhere(ql:String)(params:Any*): Relation[A]={
    Relation[A](ql,params)
  }
  def find(key:Any)(implicit classTag:ClassTag[A]):Relation[A]={
    val clazz = classTag.runtimeClass.asInstanceOf[Class[A]]
    val field = clazz.getDeclaredFields.find(_.isAnnotationPresent(classOf[Id]))
    val primaryKey = field.getOrElse(throw new IllegalStateException("primary key is null"))
    key match{
      case _:Int|_:String|_:Long=>
        Relation[A]("from %s where %s=?".format(clazz.getSimpleName,primaryKey.getName),Seq(key))
      case keys:Array[_] =>
        Relation[A]("from %s where %s IN (?)".format(clazz.getSimpleName,primaryKey.getName),Seq(key))
      case None =>
        Relation[A]("from %s".format(clazz.getSimpleName),Seq())
    }
  }
  def take(n:Int=1)(implicit classTag:ClassTag[A]):Relation[A]={
    find(None).limit(n)
  }
  def first(n:Int=1)(implicit classTag:ClassTag[A]):Relation[A]= {
    take(n).internalOrder(getPrimaryKey -> "ASC")
  }
  def last(n:Int=1)(implicit classTag:ClassTag[A]):Relation[A]= {
    take(n).internalOrder(getPrimaryKey->"DESC")
  }
  def all(n:Int=1)(implicit clazzTag:ClassTag[A]):Relation[A]= {
    find(None)
  }
  def find_each(implicit clazzTag:ClassTag[A]):Relation[A]= {
    find(None)
  }
}
