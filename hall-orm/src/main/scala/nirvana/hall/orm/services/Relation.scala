package nirvana.hall.orm.services

import javax.persistence.criteria.{Path, Predicate, CriteriaBuilder}

import nirvana.hall.orm.services.QueryExpression._

import scala.collection.{mutable, GenTraversableOnce}
import scala.collection.generic.CanBuildFrom
import scala.language.experimental.macros
import scala.language.{dynamics, postfixOps, reflectiveCalls}

/**
 * query relation interface
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-15
 */
trait Relation[A] {
  protected val primaryKey:String
  private[orm] var limit:Int = -1
  private[orm] var offset:Int = -1

  private var underlying_result:Stream[A] = _
  protected def executeQuery: Stream[A] = {
    if(underlying_result == null)
      underlying_result = ActiveRecord.find(this)
    underlying_result
  }

  def order(params:(String,Any)*):this.type
  def asc(fields:String*):this.type={
    order(fields.map((_,"asc")):_*)
  }
  def desc(fields:String*):this.type={
    order(fields.map((_,"desc")):_*)
  }
  def exists():Boolean= limit(1).headOption.isDefined
  def limit(n:Int):this.type={
    limit=n
    this
  }
  def take:A= take(1).head
  def takeOption:Option[A]= take(1).headOption
  def take(n:Int):this.type={
    limit(n)
  }
  def first:A= first(1).head
  def firstOption:Option[A]= first(1).headOption
  def first(n:Int):this.type= {
    take(n).order(primaryKey-> "ASC")
  }
  def last:A= last(1).head
  def last(n:Int):this.type= {
    take(n).order(primaryKey->"DESC")
  }
  def offset(n:Int): this.type={
    offset = n
    this
  }
  @inline final def size = executeQuery.size
  @inline final def foreach[U](f: A => U) = executeQuery.foreach(f)
  @inline final def filter[U](f: A => Boolean) = executeQuery.filter(f)
  @inline final def head = executeQuery.head
  @inline final def headOption = executeQuery.headOption
  @inline final def tail = executeQuery.tail
  @inline final def map[B, That](f: A => B)(implicit bf: CanBuildFrom[Stream[A], B, That]): That =  executeQuery.map(f)
  @inline final def flatMap[B, That](f: A => GenTraversableOnce[B])(implicit bf: CanBuildFrom[Stream[A], B, That]): That = executeQuery.flatMap(f)
}

abstract class CriteriaRelation[A](val entityClass:Class[A],val primaryKey:String)
  extends Relation[A]
  with DynamicUpdateSupport[A]{
  case class WrappedExpress(field:String,expr:BaseExpression)
  private[services] val builder:CriteriaBuilder

  private[orm] lazy val query = builder.createQuery(entityClass)
  private[orm] lazy val queryRoot = query.from(entityClass)

  private[orm] lazy val updatedQuery = builder.createCriteriaUpdate(entityClass)
  private[orm] lazy val updatedRoot = updatedQuery.from(entityClass)

  private[orm] lazy val deletedQuery = builder.createCriteriaDelete(entityClass)
  private[orm] lazy val deletedRoot = deletedQuery.from(entityClass)

  private[orm] lazy val expressions = mutable.Buffer[WrappedExpress]()

  private sealed trait WhereSupportObject {
    def where (restrictions: Predicate *):Unit
    def criteriaBuilder:CriteriaBuilder
    def path[X](field:String):Path[X]
  }
  private val querySupport = new WhereSupportObject {
    override def criteriaBuilder: CriteriaBuilder = builder
    override def where(restrictions: Predicate*):Unit = query.where(restrictions:_*)
    override def path[X](field: String): Path[X] =  queryRoot.get(field)
  }
  private val updatedSupport = new WhereSupportObject {
    override def criteriaBuilder: CriteriaBuilder = builder
    override def where(restrictions: Predicate*):Unit = updatedQuery.where(restrictions:_*)
    override def path[X](field: String): Path[X] =  updatedRoot.get(field)
  }
  private val deletedSupport = new WhereSupportObject {
    override def criteriaBuilder: CriteriaBuilder = builder
    override def where(restrictions: Predicate*):Unit = deletedQuery.where(restrictions:_*)
    override def path[X](field: String): Path[X] =  deletedRoot.get(field)
  }
  def eq(field:String,value:Any): this.type ={
    value match{
      case expr:BaseExpression =>
        expressions += WrappedExpress(field,expr)
      case other=>
        expressions += WrappedExpress(field,Equal(value))
    }

    this
  }
  override def order(params: (String, Any)*): CriteriaRelation.this.type = {
    params.foreach{
      case (field,value)=>
        String.valueOf(value).toLowerCase match{
          case "asc"=>
            query.orderBy(builder.asc(queryRoot.get(field)))
          case "desc"=>
            query.orderBy(builder.desc(queryRoot.get(field)))
        }
    }
    this
  }
  def notNull(field:String):this.type= eq(field,NotNull)
  def isNull(field:String):this.type= eq(field,IsNull)
  def gt(field:String,value:Number):this.type= eq(field,Gt(value))
  def ge(field:String,value:Number):this.type= eq(field,Ge(value))
  def lt(field:String,value:Number):this.type= eq(field,Lt(value))
  def le(field:String,value:Number):this.type= eq(field,Le(value))
  def between[T](field:String,x:T,y:T):this.type= eq(field,Between(x,y))
  def like(field:String,value:String):this.type= eq(field,Like(value))
  def notLike(field:String,value:String):this.type= eq(field,NotLike(value))


  override protected def executeQuery: scala.Stream[A] = {
    expandExpressions(querySupport)
    super.executeQuery
  }
  private def expandExpressions(ws:WhereSupportObject): Unit ={
    val criteriaExpressions = expressions.map{
      case WrappedExpress(field,Equal(value:Any)) =>
        ws.criteriaBuilder.equal(ws.path(field),value)
      case WrappedExpress(field,NotNull) =>
        ws.criteriaBuilder.isNotNull(ws.path(field))
      case WrappedExpress(field,IsNull) =>
        ws.criteriaBuilder.isNull(ws.path(field))
      case WrappedExpress(field,Gt(value:Number)) =>
        ws.criteriaBuilder.gt(ws.path[Number](field),value)
      case WrappedExpress(field,Ge(value:Number)) =>
        ws.criteriaBuilder.ge(ws.path[Number](field),value)
      case WrappedExpress(field,Lt(value)) =>
        ws.criteriaBuilder.lt(ws.path[Number](field),value)
      case WrappedExpress(field,Le(value)) =>
        ws.criteriaBuilder.le(ws.path[Number](field),value)
      case WrappedExpress(field,Between(x:java.lang.Long,y:java.lang.Long)) =>
        ws.criteriaBuilder.between(ws.path[java.lang.Long](field),x,y)
      case WrappedExpress(field,Between(x:Integer,y:Integer)) =>
        ws.criteriaBuilder.between(ws.path[Integer](field),x,y)
      //addBetween(ws,field,x,y)
      case WrappedExpress(field,Like(value)) =>
        ws.criteriaBuilder.like(ws.path[String](field),value)
      case WrappedExpress(field,NotLike(value)) =>
        ws.criteriaBuilder.notLike(ws.path[String](field),value)
      case other=>
        throw new UnsupportedOperationException
    }

    ws.where(criteriaExpressions:_*)
  }

  override def internalUpdate(params: (String, Any)*): Int = {
    expandExpressions(updatedSupport)

    params.foreach{
      case (field,value) =>
        updatedQuery.set(field,value)
    }
    ActiveRecord.updateRelation(this)
  }
  def delete:Int = {
    expandExpressions(deletedSupport)
    ActiveRecord.deleteRelation(this)
  }
}
trait DynamicUpdateSupport[A] extends Dynamic{
  /**
   * update method
   */
  def applyDynamicNamed(name:String)(params:(String,Any)*):Int=macro HallOrmMacroDefine.dslDynamicImplNamed[A,Int]
  def internalUpdate(params:(String,Any)*): Int
}




/**
 * query case class
 * @param entityClazz entity class
 * @param primaryKey primary key
 * @tparam A type parameter
 */
class QlRelation[A](val entityClazz:Class[A],val primaryKey:String) extends Relation[A] with DynamicUpdateSupport[A]{
  def this(entityClazz:Class[A],primaryKey:String,query:String,queryParams:Seq[Any]){
    this(entityClazz,primaryKey)
    if(query != null)
      this.queryClause = Some(query)

    this.queryParams = queryParams
  }
  private[orm] var orderBy:Option[String]=None

  private[orm] var queryClause:Option[String] = None
  private[orm] var queryParams:Seq[Any] = Nil

  private[orm] var updateQl:Option[String] = None
  private[orm] var updateParams:Seq[Any] = Nil



  def order(params:(String,Any)*):this.type= {
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
  def internalUpdate(params:(String,Any)*): Int={
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

    ActiveRecord.updateRelation(this)
  }
  def delete:Int = {
    ActiveRecord.deleteRelation(this)
  }
}
