package nirvana.hall.c.services.gfpt4lib

import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.AncientData.{StreamReader, StreamWriter}
import nirvana.hall.c.services.gfpt4lib.FPTFile.{LogicHeadV3, FPTHead}

import scala.collection.mutable
import scala.language.reflectiveCalls

/**
 * fpt file
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-27
 */
object FPTFile {
  val FS:Byte= 28
  val GS:Byte= 29

  val V3_LOGIC_DATA_TYPE_2="2"
  val V3_LOGIC_DATA_TYPE_3="3"
  class FPTHead extends AncientData{
    @Length(3)
    var flag:String = _
    @Length(2)
    var majorVersion:String = _
    @Length(2)
    var minorVersion:String = _
  }
  class LogicHeadV3 extends AncientData{
    @Length(8)
    var fileLength: String = _
    @Length(1)
    var dataType:String = _
  }
}

