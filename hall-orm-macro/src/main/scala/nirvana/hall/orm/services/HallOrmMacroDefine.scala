package nirvana.hall.orm.services


import scala.language.dynamics
import scala.language.experimental.macros
import scala.reflect.macros.whitebox

/**
 * hall orm macro definition
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-01
 */
object HallOrmMacroDefine {
  def findDynamicImpl[E: c.WeakTypeTag](c: whitebox.Context)(name: c.Expr[String])(params:c.Expr[Any]*): c.Expr[List[E]] = {
    import c.universe._
    val nameOpt: Option[String] = name.tree match {
      case Literal(Constant(value: String)) => Some(value)
      case _ => None
    }
    c.Expr[List[E]](Apply(Apply(Select(c.prefix.tree, TermName("selfFind")), List(name.tree)),params.map(_.tree).toList))
  }
  def findDynamicImplNamed[E: c.WeakTypeTag](c: whitebox.Context)(name: c.Expr[String])(params:c.Expr[(String,Any)]*): c.Expr[List[E]] = {
    import c.universe._
    val Literal(Constant(methodName:String)) = name.tree
    //find class field
    val expectedNames =  c.weakTypeOf[E].members
      .filter(_.isTerm)
      .filter(_.asTerm.isVar).map(_.name.toString.trim).toSeq

    //validate params
    val trees = params.map(_.tree).toList
    trees.foreach{
      case Apply(_,Literal(Constant(_name: String))::_) =>
        if(_name.isEmpty)
          c.error(c.enclosingPosition, s"name parameter is empty.")
        else if(!expectedNames.contains(_name))
          c.error(c.enclosingPosition, s"${c.weakTypeOf[E]}#${_name} not found. Expected fields are ${expectedNames.mkString("#", ", #", "")}.")
      case other =>
          c.error(c.enclosingPosition, s"${other} unsupported.")
    }

    methodName match{
      case "find" =>
        c.Expr[List[E]](Apply(Apply(Select(c.prefix.tree, TermName("selfFindNamed")), List(name.tree)),trees))
      case other=>
        c.Expr[List[E]](Apply(Select(c.prefix.tree, TermName("_"+other)),trees))
    }
  }
}
