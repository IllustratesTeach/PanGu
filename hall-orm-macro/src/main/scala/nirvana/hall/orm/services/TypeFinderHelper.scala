package nirvana.hall.orm.services

import scala.language.experimental.macros
import scala.reflect.macros.whitebox

/**
 * helper macro
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-11
 */
class TypeFinderHelper[C<:whitebox.Context,TYPE](c:C) {
  import c.universe._
  def findType(typeName:c.Tree):TYPE={
    val expr =
      q"""
          import scala.reflect.runtime.universe._;
          $typeName
       """
    c.eval(c.Expr(expr))
  }
  def findLengthType:TYPE={
    findType(q"typeOf[nirvana.hall.c.annotations.Length]")
  }
  def findIgnoreTransferType:TYPE={
    val expr =
      q"""
          import scala.reflect.runtime.universe._;
          typeOf[nirvana.hall.c.annotations.IgnoreTransfer]
          //typeOf[java.lang.String]
       """
    c.eval[TYPE](c.Expr[TYPE](expr))
    //findType("nirvana.hall.c.annotations.IgnoreTransfer")
  }
  def findModelType:TYPE={
    import c.universe._
    findType(q"typeOf[nirvana.hall.c.services.Model]")
  }
  def findModelArrayType:TYPE={
    findType(q"typeOf[Array[nirvana.hall.c.services.Model]]")
  }
}
