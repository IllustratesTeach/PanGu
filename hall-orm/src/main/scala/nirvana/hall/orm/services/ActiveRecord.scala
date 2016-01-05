package nirvana.hall.orm.services

import javax.persistence.{EntityManager, Id, Transient}

import org.apache.tapestry5.ioc.ObjectLocator
import org.slf4j.LoggerFactory

import scala.collection.generic.CanBuildFrom
import scala.collection.immutable.Stream
import scala.collection.{GenTraversableOnce, JavaConversions}
import scala.language.dynamics
import scala.language.experimental.macros
import scala.reflect.{ClassTag, classTag}

/**
 * active record
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-03
 */
object ActiveRecord {
  //logger
  private val logger = LoggerFactory getLogger getClass
  var objectLocator:ObjectLocator= _

  /**
   * Saves the model.
   * If the model is new a record gets created in the database,otherwise
   * the existing record gets updated.
   */
  def save[T](record:T):T={
    getService[EntityService].save(record)
  }

  /**
   * Deletes the record in the database
   * The row is simply removed with an SQL +DELETE+ statement on the
   * record's primary key.
   */
  def delete[T](record:T): Unit ={
    getService[EntityService].delete(record)
  }

  /**
   * find some records by Relation
   * @param dsl relation object
   * @tparam T type parameter
   * @return record stream
   */
  private[services] def find[T](dsl:Relation[T]):Stream[T]={
    val entityManager = ActiveRecord.getService[EntityManager]
    val fullQl = dsl.orderBy match{
      case Some(order) =>
        dsl.ql + " order by "+order
      case None =>
        dsl.ql
    }
    logger.debug("ql:{}",fullQl)
    val query = entityManager.createQuery(fullQl)

    dsl.params.zipWithIndex.foreach{
      case (value,index)=>
        query.setParameter(index+1,value)
    }
    if(dsl.offset > -1)
      query.setFirstResult(dsl.offset)
    if(dsl.limit > -1)
      query.setMaxResults(dsl.limit)

    //convert as scala stream
    JavaConversions.asScalaBuffer[T](query.getResultList.asInstanceOf[java.util.List[T]]).toStream
  }

  /**
   * find some service using ObjectLocator
   */
  private[orm] def getService[T:ClassTag]:T={
    if(objectLocator == null)
      throw new IllegalStateException("object locator is null")
    objectLocator.getService(classTag[T].runtimeClass.asInstanceOf[Class[T]])
  }
}

/**
 * ActiveRecord trait
 */
trait ActiveRecord {
  @Transient
  def save():this.type = {
    ActiveRecord.save(this)
  }
  @Transient
  def delete(): Unit ={
    ActiveRecord.delete(this)
  }
}

/**
 * query case class
 * @param ql query language
 * @param params query parameters
 * @param primaryKey primary key
 * @tparam A type parameter
 */
case class Relation[A](ql:String, params:Seq[Any],primaryKey:String) extends Dynamic with Product{
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
  //def applyDynamic(name:String)(params:(Any*):this.type=macro HallOrmMacroDefine.dslDynamicImplNamed[A,this.type]
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
  def exists():Boolean= limit(1).headOption.isDefined
  def asc(fields:String*):this.type={
    internalOrder(fields.map((_,"asc")):_*)
  }
  def desc(fields:String*):this.type={
    internalOrder(fields.map((_,"desc")):_*)
  }
  def limit(n:Int):this.type={
    limit=n
    this
  }
  def take:A= take(1).head
  def take(n:Int):Relation[A]={
    limit(n)
  }
  def first:A= first(1).head
  def first(n:Int):Relation[A]= {
    take(n).internalOrder(primaryKey-> "ASC")
  }
  def last:A= last(1).head
  def last(n:Int):Relation[A]= {
    take(n).internalOrder(primaryKey->"DESC")
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
abstract class ActiveRecordInstance[A](implicit val clazzTag:ClassTag[A]) extends Dynamic{
  /**
   * get model class and primary key
   */

  private val clazz = clazzTag.runtimeClass.asInstanceOf[Class[A]]
  private val field = clazz.getDeclaredFields.find(_.isAnnotationPresent(classOf[Id]))
  private val primaryKey = field.getOrElse(throw new IllegalStateException("primary key is null")).getName

  /**
   * find and where method
   * @param name method name
   * @param params method parameter
   * @return relation query object
   */
  def applyDynamicNamed(name:String)(params:(String,Any)*):Relation[A]=macro HallOrmMacroDefine.findDynamicImplNamed[A,Relation[A]]
  def applyDynamic(name:String)(params:Any*):Relation[A]= macro HallOrmMacroDefine.findDynamicImpl[A,Relation[A]]

  /**
   * find_by and where function
   * @param ql query
   * @param params parameter values
   * @return Relation object
   */
  def internalWhere(ql:String)(params:Any*): Relation[A]={
    Relation[A](ql,params,primaryKey)
  }

  /**
   * retrieving single object
   * @param key primary key
   * @return entity object
   */
  def find(key:Any):A={
    internalFind(key).head
  }

  /**
   * find some records by primary key
   * @param keys key array
   * @return query object
   */
  def find(keys:Array[Any]):Relation[A]={
    internalFind(keys)
  }
  private def internalFind(key:Any):Relation[A]={
    key match{
      case _:Int|_:String|_:Long=>
        Relation[A]("from %s where %s=?1".format(clazz.getSimpleName,primaryKey),Seq(key),primaryKey)
      case keys:Array[_] =>
        Relation[A]("from %s where %s IN (?1)".format(clazz.getSimpleName,primaryKey),Seq(key),primaryKey)
      case None =>
        Relation[A]("from %s".format(clazz.getSimpleName),Seq(),primaryKey)
    }
  }
  //retrieving single object
  def take:A= take(1).head
  def take(n:Int):Relation[A]={
    internalFind(None).limit(n)
  }
  def first:A= first(1).head
  def first(n:Int=1):Relation[A]= {
    take(n).internalOrder(primaryKey-> "ASC")
  }
  def last:A= last(1).head
  def last(n:Int):Relation[A]= {
    take(n).internalOrder(primaryKey->"DESC")
  }
  def all:Relation[A]= {
    internalFind(None)
  }
  def asc(fields:String*):Relation[A]={
    internalFind(None).asc(fields:_*)
  }
  def desc(fields:String*):Relation[A]={
    internalFind(None).desc(fields:_*)
  }
  /*
  def find_each(start:Int=0,batchSize:Int = 100)(f:A=>Unit): Unit ={

  }
  */
}
