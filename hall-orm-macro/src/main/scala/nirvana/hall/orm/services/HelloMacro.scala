package nirvana.hall.orm.services



import scala.annotation.StaticAnnotation
import scala.language.experimental.macros
import scala.reflect.macros.whitebox

object helloMacro {
  def impl(c: whitebox.Context)(annottees: c.Expr[Any]*): c.Expr[String] = {
    import c.universe._
    val result = {
      TypeName("A").encodedName

      val str=TypeName("A")
      annottees.map(_.tree).toList match {
        case q"object $name extends ..$parents { ..$body }" :: Nil =>
          q"""
            object $name extends ..$parents {
              def hello: ${typeOf[String]} = "hello 11"
              ..$body
              @javax.persistence.Entity
              @Table("table_A")
              class ${str}{
                var name:${typeOf[String]} = _
              }
            }
          """
      }
    }
    c.Expr[String](result)
  }
}

class hello extends StaticAnnotation {
  def macroTransform(annottees: Any*):String = macro helloMacro.impl
}

