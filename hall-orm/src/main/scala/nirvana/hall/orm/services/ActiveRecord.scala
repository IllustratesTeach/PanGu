package nirvana.hall.orm.services

import javax.persistence.{EntityManager, Id, Transient}

import org.apache.tapestry5.ioc.ObjectLocator
import org.slf4j.LoggerFactory

import scala.collection.generic.CanBuildFrom
import scala.collection.immutable.Stream
import scala.collection.{mutable, GenTraversableOnce, JavaConversions}
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
  @volatile
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
  def updateRelation[T](relation: Relation[T]):Int={
    getService[EntityService].updateRelation(relation)
  }
  def deleteRelation[T](relation: Relation[T]):Int={
    getService[EntityService].deleteRelation(relation)
  }

  /**
   * find some records by Relation
   * @param dsl relation object
   * @tparam T type parameter
   * @return record stream
   */
  private[services] def find[T](dsl:Relation[T]):Stream[T]={
    val entityManager = ActiveRecord.getService[EntityManager]

    var fullQl = "from %s".format(dsl.entityClazz.getSimpleName)
    dsl.queryClause.foreach{fullQl += " where %s".format(_)}
    dsl.orderBy.foreach{fullQl += " order by %s".format(_)}

    logger.debug("ql:{}",fullQl)
    val query = entityManager.createQuery(fullQl)

    dsl.queryParams.zipWithIndex.foreach{
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
  /**
   * find_by and where function
   * @param ql query
   * @param params parameter values
   * @return Relation object
   */
  def internalWhere[A](clazz:Class[A],primaryKey:String,ql:String)(params:Any*): Relation[A]={
    new Relation[A](clazz,primaryKey,ql,params)
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
 * @param entityClazz entity class
 * @param primaryKey primary key
 * @tparam A type parameter
 */
class QueryRelation[A](val entityClazz:Class[A],val primaryKey:String) extends Dynamic{
  def this(entityClazz:Class[A],primaryKey:String,query:String,queryParams:Seq[Any]){
    this(entityClazz,primaryKey)
    if(query != null)
      this.queryClause = Some(query)
    this.queryParams = queryParams
  }
  private[orm] var limit:Int = -1
  private[orm] var offset:Int = -1
  private[orm] var orderBy:Option[String]=None

  private[orm] var queryClause:Option[String] = None
  private[orm] var queryParams:Seq[Any] = Nil

  private var underlying_result:Stream[A] = _
  private def executeQuery: Stream[A] = {
    //if(underlying_result == null)
      //underlying_result = ActiveRecord.find(this)
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
  def takeOption:Option[A]= take(1).headOption
  def take(n:Int):QueryRelation[A]={
    limit(n)
  }
  def first:A= first(1).head
  def firstOption:Option[A]= first(1).headOption
  def first(n:Int):QueryRelation[A]= {
    take(n).internalOrder(primaryKey-> "ASC")
  }
  def last:A= last(1).head
  def last(n:Int):QueryRelation[A]= {
    take(n).internalOrder(primaryKey->"DESC")
  }
  def offset(n:Int): this.type={
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
class DeleteRelation[A](val entityClazz:Class[A],val primaryKey:String) extends Dynamic{
  def this(entityClazz:Class[A],primaryKey:String,query:String,queryParams:Seq[Any]){
    this(entityClazz,primaryKey)
    if(query != null)
      this.queryClause = Some(query)

    this.queryParams = queryParams
  }

  private[orm] var queryClause:Option[String] = None
  private[orm] var queryParams:Seq[Any] = Nil

  def delete():Int = {
    //ActiveRecord.deleteRelation(this)
    0
  }
}
class UpdateRelation[A](val entityClazz:Class[A],val primaryKey:String) extends Dynamic{
  def this(entityClazz:Class[A],primaryKey:String,query:String,queryParams:Seq[Any]){
    this(entityClazz,primaryKey)
    if(query != null)
      this.queryClause = Some(query)

    this.queryParams = queryParams
  }

  private[orm] var queryClause:Option[String] = None
  private[orm] var queryParams:Seq[Any] = Nil

  private[orm] var updateQl:Option[String] = None
  private[orm] var updateParams:Seq[Any] = Nil

  def update():Int = {
    //ActiveRecord.updateRelation(this)
    0
  }
}
/**
 * query case class
 * @param entityClazz entity class
 * @param primaryKey primary key
 * @tparam A type parameter
 */
class Relation[A](val entityClazz:Class[A],val primaryKey:String) extends Dynamic{
  def this(entityClazz:Class[A],primaryKey:String,query:String,queryParams:Seq[Any]){
    this(entityClazz,primaryKey)
    if(query != null)
      this.queryClause = Some(query)

    this.queryParams = queryParams
  }
  private[orm] var limit:Int = -1
  private[orm] var offset:Int = -1
  private[orm] var orderBy:Option[String]=None

  private[orm] var queryClause:Option[String] = None
  private[orm] var queryParams:Seq[Any] = Nil

  private[orm] var updateQl:Option[String] = None
  private[orm] var updateParams:Seq[Any] = Nil

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
  def internalUpdate(params:(String,Any)*): this.type ={
    var ql = ""
    var index = queryParams.size + 1
    val updateParams = mutable.Buffer[Any]()
    params.foreach{
      case (key,value)=>
        ql += "%s=?%s,".format(key,index)
        index += 1
        updateParams += value
    }

    this.updateParams = updateParams.toSeq
    if(ql.length>0){
      this.updateQl = Some(ql.dropRight(1))
    }

    this
  }
  def update():Int={
    ActiveRecord.updateRelation(this)
  }
  def delete():Int = {
    ActiveRecord.deleteRelation(this)
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
  def takeOption:Option[A]= take(1).headOption
  def take(n:Int):Relation[A]={
    limit(n)
  }
  def first:A= first(1).head
  def firstOption:Option[A]= first(1).headOption
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

  val clazz = clazzTag.runtimeClass.asInstanceOf[Class[A]]
  protected val field = clazz.getDeclaredFields.find(_.isAnnotationPresent(classOf[Id]))
  val primaryKey = field.getOrElse(throw new IllegalStateException("primary key is null")).getName

  /**
   * find_by and where method
   * @param name method name
   * @param params method parameter
   * @return relation query object
   */
  def applyDynamicNamed(name:String)(params:(String,Any)*):Relation[A]=macro HallOrmMacroDefine.findDynamicImplNamed[A,Relation[A]]

  /**
   * find_by_xx_and_yy method
   * @param name method name
   * @param params parameter list
   * @return Relation query instance
   */
  def applyDynamic(name:String)(params:Any*):Relation[A]= macro HallOrmMacroDefine.findDynamicImpl[A,Relation[A]]
  /*
  /**
   * find_by and where function
   * @param ql query
   * @param params parameter values
   * @return Relation object
   */
  def internalWhere(ql:String)(params:Any*): Relation[A]={
    new Relation[A](clazz,primaryKey,ql,params)
  }
  */

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
        new Relation[A](clazz,primaryKey,"%s=?1".format(primaryKey),Seq(key))
      case keys:Array[_] =>
        new Relation[A](clazz,primaryKey,"%s IN (?1)".format(primaryKey),Seq(key))
      case None =>
        new Relation[A](clazz,primaryKey,null,Seq())
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
