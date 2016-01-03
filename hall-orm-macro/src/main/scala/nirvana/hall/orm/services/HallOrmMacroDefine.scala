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
  private val find_by_pattern = "(?i)find_by_([_a-zA-Z0-9]*)".r
  def findDynamicImpl[E: c.WeakTypeTag](c: whitebox.Context)(name: c.Expr[String])(params:c.Expr[Any]*): c.Expr[List[E]] = {
    import c.universe._
    val clazzName = c.weakTypeOf[E]
    var ql = "from "+clazzName+" where 1=1"
    //find class field
    val expectedNames =  c.weakTypeOf[E].members
      .filter(_.isTerm)
      .filter(_.asTerm.isVar).map(_.name.toString.trim).toSeq
    val Literal(Constant(findMethod)) = name.tree
    findMethod.toString.trim match {
      case find_by_pattern(attributes) =>
        val attrs =  attributes.split("_and_")
        if(attrs.length != params.length){
          c.error(c.enclosingPosition, s"name's length ${attrs.length} !=  parameter's length ${params.length}.")
        }
        attrs.foreach{attr=>
          if(expectedNames.contains(attr)){
            ql += " and %s=?".format(attr)
          }else{
            c.error(c.enclosingPosition, s"${c.weakTypeOf[E]}#${attr} not found. Expected fields are ${expectedNames.mkString("#", ", #", "")}.")
          }
        }
      case other=>
    }
    val qlTree = Literal(Constant(ql))

    c.Expr[List[E]](Apply(Apply(Select(c.prefix.tree, TermName("selfFind")), List(qlTree)),params.map(_.tree).toList))
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

    val hql = Literal(Constant("from xxy"))
    methodName match{
      case "find" =>
        c.Expr[List[E]](Apply(Apply(Select(c.prefix.tree, TermName("selfFindNamed")), List(hql)),trees))
      case other=>
        c.Expr[List[E]](Apply(Select(c.prefix.tree, TermName("_"+other)),trees))
    }
  }
}
