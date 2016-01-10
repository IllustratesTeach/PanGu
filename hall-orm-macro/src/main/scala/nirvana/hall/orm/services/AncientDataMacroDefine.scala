package nirvana.hall.orm.services


import scala.collection.mutable
import scala.language.dynamics
import scala.language.experimental.macros
import scala.reflect.ClassTag
import scala.reflect.macros.whitebox

/**
 * ancient data macro definition
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-08
 */
object AncientDataMacroDefine {
  /**
   * find method
   */
  def writeStream[M:c.WeakTypeTag,W:c.WeakTypeTag,SR:c.WeakTypeTag,IT:c.WeakTypeTag,L:c.WeakTypeTag](c: whitebox.Context)(dataSink:c.Expr[W]) : c.Expr[Unit] = {
    import c.universe._
    import c.universe.definitions._
    val selfType = c.weakTypeOf[M]


    println("stream impl",selfType)

    val STRING_CLASS = typeOf[String]
    implicit val sr = c.weakTypeOf[SR]
    def findDataSize(t:c.Type)={
      getDataSizeInternal[c.type,SR,IT,L](c)(t)
    }
    val selfTree = Select(c.prefix.tree, TermName("model"))

    val buffer = mutable.Buffer[c.Tree]()
    internalWriteStream(selfTree,selfType)
    def internalWriteStream(selfTree:c.Tree,selfType:c.Type):Unit={
      internalProcessField[c.type, Unit, IT, L](c)(selfType) { (symbol, length) =>
        val tpe = symbol.info

        def returnLengthOrThrowException: Int = {
          if (length == 0)
            c.error(c.enclosingPosition, "@Length not defined at " + tpe)
          length
        }
        def writeString(str: c.Tree, length: Int):c.Tree = {
          val result = writeBytes(q"$str.getBytes",length)
          q"""
             if($str == null) $dataSink.writeZero($length) else $result
           """
        }
        def writeBytes(bytes: c.Tree, length: Int) :c.Tree={
            q"""
        val bytesLength = if($bytes == null) 0 else $bytes.length
        val zeroLength = $length - bytesLength
        if($bytes != null) {
          $dataSink.writeBytes($bytes)
        }
        $dataSink.writeZero(zeroLength)
          """
        }


        val termName = symbol.getter
        val returnType = symbol.typeSignature
        val valueTree = q"$selfTree.$termName"

        tpe match {
          case ByteTpe => buffer += q"$dataSink.writeByte($valueTree.asInstanceOf[Byte])"
          case ShortTpe | CharTpe => buffer += q"$dataSink.writeShort($valueTree.asInstanceOf[Short])"
          case IntTpe => buffer += q"$dataSink.writeInt($valueTree.asInstanceOf[Int])"
          case LongTpe => buffer += q"$dataSink.writeLong($valueTree.asInstanceOf[Long])"
          case STRING_CLASS =>
            buffer += writeString(q"$valueTree.asInstanceOf[String]", returnLengthOrThrowException)
          case t if t <:< sr => //inherit ScalaReflect
            val valueSize = findDataSize(t)
            buffer +=
              q"""
            if($valueTree == null) {
              $dataSink.writeZero($valueSize)
            }else{
              $valueTree.writeToStreamWriter($dataSink)
            }
            """
          case t if t =:= typeOf[Array[Byte]] =>
            buffer += writeBytes(q"$valueTree.asInstanceOf[Array[Byte]]", returnLengthOrThrowException)
        case TypeRef(pre,sym,args) if args.head <:< sr => //Array of ScalaReflect
        //case TypeRef(pre,sym,args) if sym == typeOf[Array[Model]].typeSymbol => //Array of ScalaReflect
          val len = returnLengthOrThrowException
          val type_len = findDataSize(args.head) //createAncientDataByType(args.head).getDataSize
          val totalLen = len * type_len
          buffer += q"""
          var zeroLen:Int = $totalLen
          if($valueTree != null){
            val ancientDataArray = $valueTree.filterNot( _ == null)
            ancientDataArray.foreach{x=>
              x.writeToStreamWriter($dataSink)
              zeroLen -= $type_len
            }
          }
          $dataSink.writeZero(zeroLen)
          """
          case other =>
            c.error(c.enclosingPosition, "type is not supported for " + other)
        }
      }
    }


    val result = buffer.toSeq
    //result.foreach(show(_))
    c.warning(c.enclosingPosition,s"${result}")
    c.Expr[Unit](q" ..$result;Unit")
  }

  def getDataSizeImpl[M:c.WeakTypeTag,SR:c.WeakTypeTag,IT:c.WeakTypeTag,L:c.WeakTypeTag](c: whitebox.Context) : c.Expr[Int] = {
    import c.universe._
    val selfType = c.weakTypeOf[M]
    implicit val sr = c.weakTypeOf[SR]
    println("size",selfType)
    val len = getDataSizeInternal[c.type,SR,IT,L](c)(selfType)
    val lenTree = Literal(Constant(len))
    c.Expr[Int](lenTree)
  }
  private def getDataSizeInternal[C <: whitebox.Context,SR:c.WeakTypeTag,IT:c.WeakTypeTag,L:c.WeakTypeTag](c:C)
                                                                                                          (tpe:c.Type)(implicit sr:c.Type):Int={
    internalProcessField[C,Int,IT,L](c)(tpe)((symbol,length)=>findPrimitiveTypeLength(c)(symbol,symbol.info,length)).sum
  }
  /**
   * find primitive data type length
   */
  private def findPrimitiveTypeLength[C <: whitebox.Context](c:C)(term:c.Symbol,tpe:c.Type,length:Int)(implicit sr:c.Type):Int={
    import c.universe._
    import c.universe.definitions._
    val STRING_CLASS = typeOf[String]
    def returnLengthOrThrowException:Int={
      if(length == 0)
        c.error(c.enclosingPosition,"@Length not defined at "+term)
      length
    }
    def throwExceptionIfLengthGTZeroOrGet[T](value:T): T={
      if(length !=0)
        c.error(c.enclosingPosition,"@Length wrong defined at "+term)
      value
    }
    tpe match {
      case ByteTpe => throwExceptionIfLengthGTZeroOrGet(1)
      case ShortTpe | CharTpe => throwExceptionIfLengthGTZeroOrGet(2)
      case IntTpe => throwExceptionIfLengthGTZeroOrGet(4)
      case LongTpe => throwExceptionIfLengthGTZeroOrGet(8)
      case STRING_CLASS =>
        returnLengthOrThrowException
      case t if t <:< sr=>
        //throwExceptionIfLengthGTZeroOrGet(createAncientDataByType(t).getDataSize)
        throwExceptionIfLengthGTZeroOrGet(getDataSizeInternal(c)(t))
      case TypeRef(pre,sym,args) if sym == typeOf[Array[_]].typeSymbol =>
        if(args.length != 1)
          c.error(c.enclosingPosition,"only support one type parameter in Array.")
        //using recursive call to find length
        returnLengthOrThrowException * findPrimitiveTypeLength(c)(term,args.head,0)
      case other =>
        c.error(c.enclosingPosition,"type is not supported "+other)
        0
    }
  }
  private def internalProcessField[C <: whitebox.Context,T:ClassTag,IT:c.WeakTypeTag,L:c.WeakTypeTag](c:C)(model:c.Type)(processor:(c.universe.TermSymbol,Int)=>T):Array[T]={
    import c.universe._
    val itType = c.weakTypeOf[IT]
    val lengthType = c.weakTypeOf[L]
    model
       .members
       // force loading method's signature
      .map{x=>x.typeSignature;x} //@see https://issues.scala-lang.org/browse/SI-7424
      .toSeq.reverse // <----- must be reversed
      .collect {
      case m:TermSymbol if m.isVar && !m.annotations.exists(itType =:= _.tree.tpe)=>

        c.warning(c.enclosingPosition,s"${m}")

      val lengthAnnotation = m.annotations.find (lengthType =:= _.tree.tpe)
      val length=
        lengthAnnotation.map(_.tree).map {
          case Apply(fun, AssignOrNamedArg(name, Literal(Constant(value))) :: Nil) =>
            value.asInstanceOf[Int]
        }.sum
      processor(m, length)
    }.toArray
  }
}
