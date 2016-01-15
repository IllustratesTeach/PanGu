package nirvana.hall.orm.services

/**
 * query expression
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-15
 */
object QueryExpression {
  sealed trait BaseExpression
  case class Equal(value:Any) extends BaseExpression
  case object NotNull extends BaseExpression
  case object IsNull extends BaseExpression
  case class Gt(value:Number) extends BaseExpression
  case class Ge(value:Number) extends BaseExpression
  case class Lt(value:Number) extends BaseExpression
  case class Le(value:Number) extends BaseExpression
  case class Between[T](x: T, y: T) extends BaseExpression
  case class Like(value:String) extends BaseExpression
  case class NotLike(value:String) extends BaseExpression
}
