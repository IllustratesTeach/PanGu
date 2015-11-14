package nirvana.hall.v62.internal

import nirvana.hall.protocol.v62.FPTProto.Case
import nirvana.hall.v62.internal.c.gloclib.galoclp.GCASEINFOSTRUCT
import nirvana.hall.v62.internal.c.gloclib.galoclpConverter

/**
 * case struct
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-03
 */
object CaseStruct {
  /*
  private[internal] def appendTextStruct(buffer:mutable.Buffer[GATEXTITEMSTRUCT],name:String,value:String):Unit = {
    if(value != null && value.length > 0) {
      val textStruct = new GATEXTITEMSTRUCT()
      textStruct.bIsPointer = 1
      textStruct.bIsNull = 0
      textStruct.szItemName = name
      //convert as GBK encoding,because 6.2 need gbk encoding
      textStruct.stData.textContent = value.getBytes(AncientConstants.GBK_ENCODING)
      textStruct.nItemLen = textStruct.stData.textContent.length

      buffer += textStruct
    }

  }
  */
  @deprecated
  def convertProtobuf2Case(protoCase:Case):GCASEINFOSTRUCT = {
    galoclpConverter.convertProtobuf2GCASEINFOSTRUCT(protoCase)
  }
}
