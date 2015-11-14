package nirvana.hall.v62.internal.c.gloclib

import nirvana.hall.protocol.v62.FPTProto
import nirvana.hall.protocol.v62.FPTProto.LPCard
import nirvana.hall.v62.internal.CaseStruct
import nirvana.hall.v62.internal.c.gloclib.galoclp.GLPCARDINFOSTRUCT
import nirvana.hall.v62.internal.c.gloclib.glocdef.{GAFISMICSTRUCT, GATEXTITEMSTRUCT}

import scala.collection.mutable

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-14
 */
object galoclpConverter {
  /**
   * convert protobuf object to latent card object
   * @param card protobuf object
   * @return gafis latent card object
   */
  def convertProtoBuf2GLPCARDINFOSTRUCT(card: LPCard): GLPCARDINFOSTRUCT= {
    val data = new GLPCARDINFOSTRUCT
    data.szCardID = card.getStrCardID

    if(card.hasText) {
      import CaseStruct.appendTextStruct
      val text = card.getText

      val buffer = mutable.Buffer[GATEXTITEMSTRUCT]()

      //text information
      appendTextStruct(buffer, "SeqNo",text.getStrSeq)
      appendTextStruct(buffer, "RemainPlace",text.getStrRemainPlace)
      appendTextStruct(buffer, "RidgeColor",text.getStrRidgeColor)
      if(text.hasBDeadBody)
        appendTextStruct(buffer, "IsUnknownBody",if(text.getBDeadBody) "1" else "0")
      appendTextStruct(buffer, "UnknownBodyCode",text.getStrDeadPersonNo)
      if(text.hasNXieChaState)
        appendTextStruct(buffer, "XieChaFlag",text.getNXieChaState.toString)
      if(text.hasNBiDuiState)
        appendTextStruct(buffer, "BiDuiState",text.getNBiDuiState.toString)
      appendTextStruct(buffer, "LatStart",text.getStrStart)
      appendTextStruct(buffer, "LatEnd",text.getStrEnd)


      data.pstText_Data = buffer.toArray
      data.nTextItemCount = buffer.size.asInstanceOf[Short]
    }

    if(card.hasBlob){
      val blob = card.getBlob
      val mic = new GAFISMICSTRUCT
      var flag = 0
      if(blob.hasStMnt){
        mic.pstMnt_Data = blob.getStMntBytes.toByteArray
        mic.nMntLen = mic.pstMnt_Data.length

        flag |= glocdef.GAMIC_ITEMFLAG_MNT
      }
      if(blob.hasStImage){
        val imgType = blob.getStImageBytes.byteAt(9) //see tagGAFISIMAGEHEADSTRUCT.bIsCompressed
        if(imgType == 1){ //image compressed
          mic.pstCpr_Data = blob.getStImageBytes.toByteArray
          mic.nCprLen = mic.pstCpr_Data.length
          flag |= glocdef.GAMIC_ITEMFLAG_CPR
        }else{
          mic.pstImg_Data = blob.getStImageBytes.toByteArray
          mic.nImgLen = mic.pstImg_Data.length
          flag |= glocdef.GAMIC_ITEMFLAG_IMG
        }
      }

      mic.nItemFlag = flag.asInstanceOf[Byte] //传送的特征类型 ,特征+图像 , 1 2 4 8

      blob.getType match{
        case FPTProto.ImageType.IMAGETYPE_FINGER =>
          mic.nItemType = glocdef.GAMIC_ITEMTYPE_FINGER.asInstanceOf[Byte]
        case FPTProto.ImageType.IMAGETYPE_FACE =>
          mic.nItemType = glocdef.GAMIC_ITEMTYPE_FACE.asInstanceOf[Byte]
        case FPTProto.ImageType.IMAGETYPE_CARDIMG =>
          mic.nItemType = glocdef.GAMIC_ITEMTYPE_DATA.asInstanceOf[Byte]
        case FPTProto.ImageType.IMAGETYPE_PALM =>
          mic.nItemType = glocdef.GAMIC_ITEMTYPE_PALM.asInstanceOf[Byte]
        case FPTProto.ImageType.IMAGETYPE_VOICE =>
          mic.nItemType = glocdef.GAMIC_ITEMTYPE_VOICE.asInstanceOf[Byte]
        case other =>
          throw new UnsupportedOperationException
      }
      mic.bIsLatent = 1 //是否位现场数据

      data.pstMIC_Data = Array(mic)
      data.nMicItemCount = 1
    }
    data
  }
}
