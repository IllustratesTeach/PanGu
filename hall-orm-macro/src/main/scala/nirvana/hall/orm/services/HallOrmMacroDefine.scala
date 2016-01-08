package nirvana.hall.orm.services


import scala.collection.mutable
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
  case class SimpleQuery[A](ql:String, params:Seq[Any])
  case class SimpleOrder[A](order:String)
  case class NamedParameter(key:String,value:Any)

  /**
   * find method
   */
  def findDynamicImpl[E: c.WeakTypeTag,R](c: whitebox.Context)
                                       (name: c.Expr[String])
                                       (params:c.Expr[Any]*) : c.Expr[R] = {
    import c.universe._
    val Literal(Constant(findMethod)) = name.tree
    val paramsTree = params.map(_.tree).toList
    findMethod.toString.trim match{
      case find_by_pattern(attributes) =>
        var ql = ""
        //find class field
        val expectedNames =  c.weakTypeOf[E].members
          .filter(_.isTerm)
          .filter(_.asTerm.isVar).map(_.name.toString.trim).toSeq
        val attrs =  attributes.split("_and_")
        if(attrs.length != params.length){
          c.error(c.enclosingPosition, s"name's length ${attrs.length} !=  parameter's length ${params.length}.")
        }
        attrs.zipWithIndex.foreach{case (attr,index)=>
          if(expectedNames.contains(attr)){
            ql += " and %s=?%s".format(attr,index+1)
          }else{
            c.error(c.enclosingPosition, s"${c.weakTypeOf[E]}#${attr} not found. Expected fields are ${expectedNames.mkString("#", ", #", "")}.")
          }
        }

        if(ql.length>0)
          ql = "1=1"+ql
        val qlTree = Literal(Constant(ql))

        //c.Expr[R](Apply(Apply(Select(c.prefix.tree, TermName("internalWhere")), List(qlTree)),paramsTree))
        //c.Expr[R](Apply(Apply(Select(objectTree, TermName("internalWhere")), List(clazzTree,primaryKeyTree,qlTree)),paramsTree))
        executeInternalWhere[c.type,R](c)(qlTree,paramsTree)
      case "where" =>
        val qlTree = paramsTree.head
        val remainTree = paramsTree.drop(1)
        executeInternalWhere[c.type,R](c)(qlTree,remainTree)
        //c.Expr[R](Apply(Apply(Select(c.prefix.tree, TermName("internalWhere")), List(qlTree)),remainTree))
      case other=>
        c.error(c.enclosingPosition, s"unsupport operation")
        c.Expr[R](Literal(Constant(Nil)))
    }

  }
  private def executeInternalWhere[C <: whitebox.Context,R](c:C)(qlTree:c.universe.Tree,paramsTree:List[c.universe.Tree]): c.Expr[R]={
    import c.universe._

    val objectTree= q"nirvana.hall.orm.services.ActiveRecord"
    val clazzTree = Select(c.prefix.tree,TermName("clazz"))
    val primaryKeyTree = Select(c.prefix.tree,TermName("primaryKey"))

    c.Expr[R](Apply(Apply(Select(objectTree, TermName("internalWhere")), List(clazzTree,primaryKeyTree,qlTree)),paramsTree))
  }
  def dslDynamicImplNamed[E: c.WeakTypeTag,R](c: whitebox.Context)
                                           (name: c.Expr[String])
                                           (params:c.Expr[(String,Any)]*):c.Expr[R] = {
    import c.universe._
    val Literal(Constant(methodName:String)) = name.tree
    //find class field
    val expectedNames =  c.weakTypeOf[E].members
      .filter(_.isTerm)
      .filter(_.asTerm.isVar).map(_.name.toString.trim).toSeq
    //validate params
    var order_str=""

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
    //c.error(c.enclosingPosition,"asdf")

    methodName match{
      case "order" | "order_by" =>
        c.Expr[R](Apply(Select(c.prefix.tree, TermName("internalOrder")), trees))
      case "update_set"=>
        c.Expr[R](Apply(Select(c.prefix.tree, TermName("internalUpdate")), trees))
      case other=>
        c.error(c.enclosingPosition, s"${other} unsupported.")
        c.Expr[R](Literal(Constant(Nil)))
    }
  }
  def findDynamicImplNamed[E: c.WeakTypeTag,R](c: whitebox.Context)
                                            (name: c.Expr[String])
                                            (params:c.Expr[(String,Any)]*): c.Expr[R] = {
    import c.universe._

    val clazzName = c.weakTypeOf[E]
    var ql = ""

    val Literal(Constant(methodName:String)) = name.tree
    //find class field
    val expectedNames =  c.weakTypeOf[E].members
      .filter(_.isTerm)
      .filter(_.asTerm.isVar).map(_.name.toString.trim).toSeq

    //validate params
    val trees = params.map(_.tree).toList
    val parameterValues = mutable.Buffer[Tree]()
    var i = 1
    trees.foreach {
      case Apply(_,Literal(Constant(_name: String))::value::Nil) =>
        if(_name.isEmpty)
          c.error(c.enclosingPosition, s"name parameter is empty.")
        else if(!expectedNames.contains(_name))
          c.error(c.enclosingPosition, s"${c.weakTypeOf[E]}#${_name} not found. Expected fields are ${expectedNames.mkString("#", ", #", "")}.")
        else {
          ql += " and %s=?%s".format(_name,i)
          i += 1
          parameterValues += value
        }
      case other =>
          c.error(c.enclosingPosition, s"${other} unsupported.")
    }

    if(ql.length>0)
      ql ="1=1 " + ql

    methodName match{
      case "find_by" | "where" =>
        val qlTree = Literal(Constant(ql))
        executeInternalWhere[c.type,R](c)(qlTree,parameterValues.toList)
        //c.Expr[R](Apply(Apply(Select(c.prefix.tree, TermName("internalWhere")), List(Literal(Constant(ql)))),parameterValues.toList))
      case other=>
        c.error(c.enclosingPosition, s"${other} unsupported.")
        c.Expr[R](Literal(Constant(Nil)))
    }
  }
}
