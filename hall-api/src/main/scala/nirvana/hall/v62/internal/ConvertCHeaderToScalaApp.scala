package nirvana.hall.v62.internal

import java.io.File

import nirvana.hall.v62.AncientConstants

import scala.io.Source

/**
 * convert C/C++ header file to scala application
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-06
 */
object ConvertCHeaderToScalaApp {
  private val definePattern = "#define[\\s]+([^\\s]+)[\\s]+([^$]+)$".r
  private val structBeginPattern = "typedef[\\s]+struct[\\s]+(tag)?([A-Za-z0-9_]+)([^$]*)$".r
  private val structElement = "(?i)^[\\s\\t]*([a-zA-Z_0-9]+)([\\s\\t\\*]+)([A-Z0-9_a-z]+)(\\[([0-9\\*\\-\\+a-z_]+)\\])?([\\s\\S]+)$".r
  private val charType="(?i)UCHAR|CHAR|VOID".r
  private val structEndPattern ="[\\s\\t]*}([^$]*)$".r
  private val isOutputErrorLine = true
  private var structBegin = false
  def main(args:Array[String]): Unit ={
    val file = new File("/Users/jcai/workspace/finger/gafis-6/include/gfptlib/gfptdef.h")
    val content = Source.fromFile(file,AncientConstants.GBK_ENCODING.name().intern()).getLines()
    content
      .filterNot(_.startsWith("#ifndef"))
      .filterNot(_.startsWith("#ifdef"))
      .filterNot(_.startsWith("#if"))
      .filterNot(_.startsWith("#endif"))
      .filterNot(_.startsWith("extern"))
      .foreach {
      case definePattern(name,valueAndComment) =>
        println("final val %s = %s".format(name,valueAndComment))
      case structBeginPattern(_,name,remain)=>
        println("class %s extends AncientData %s".format(name,remain))
        structBegin = true
      case structElement(dataType,pointer,name,_,length,remain) if structBegin =>
        val isPointer = pointer.indexOf("*") >= 0

        def convertCharAsScalaType:String = {
          length match {
            case "2" =>
              "Short"
            case "4" =>
              "Int"
            case "8" =>
              "Long"
            case other =>
              if (length != null) {
                println("  @Length(%s)".format(length))
                if (name.startsWith("bn")) "Array[Byte]" else "String"
              } else {
                "Byte"
              }
          }
        }

        //for pointer
        if(isPointer) {
            println("  var %s_Ptr:%s = _ //using 4 byte as pointer".format(name, "Int"))
            println("  @IgnoreTransfer")
          val arrayType = charType.findFirstIn(dataType) match{
            case Some(x) =>
              "Byte"
            case other=>
              dataType
          }
          print("  var %s_Data:Array[%s] = _ // for %1$s pointer ,struct:%s".format(name,arrayType,dataType))
        }else {
          dataType match {
            case charType() =>
              if (name.startsWith("bn")) {
                if (length != null) {
                  println("  @Length(%s)".format(length))
                  print("  var %s:Array[Byte] = _ ".format(name))
                } else {
                  print("  var %s:Byte = _ ".format(name))
                }
              } else {
                print("  var %s:%s = _ ".format(name, convertCharAsScalaType))
              }
            case other =>
              if (length != null) {
                println("  @Length(%s)".format(length))
                print("  var %s:Array[%s] = _".format(name, other))
              } else {
                if (other == "int" || other == "uint4") {
                  print("  var %s:Int = _".format(name))
                } else if (other == "uint2") {
                  print("  var %s:Short = _".format(name))
                } else {
                  print("  var %s = new %s".format(name, other))
                }
              }
          }
        }
        println(remain)
      case "{" =>
        println("{")
      case structEndPattern(comment) if structBegin =>
        structBegin = false
        println("} //%s".format(comment))
      case other =>
        println(other)
    }
  }
}
