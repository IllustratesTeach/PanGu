package nirvana.hall.matcher.internal

import java.io.ByteArrayOutputStream

import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.ghpcbase.ghpcdef.AFISDateTime
import nirvana.hall.c.services.gloclib.galoctp.GADB_MICSTREAMNAMESTRUCT
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYCANDSTRUCT
import nirvana.hall.c.services.gloclib.glocdef.GAFISMICSTRUCT
import nirvana.hall.matcher.HallMatcherConstants
import nirvana.protocol.MatchResult.MatchResultRequest.MatchResultObject
import org.jboss.netty.buffer.ChannelBuffer

import scala.collection.mutable

/**
 * Created by songpeng on 16/1/21.
 */
object GafisConverter {
  val streamParameter = new GADB_MICSTREAMNAMESTRUCT

  /**
   * gafis查询mic字段解析
   * @param buffer
   * @return
   */
  def GAFIS_MIC_GetDataFromStream(buffer:ChannelBuffer): Seq[GAFISMICSTRUCT]={
    val result= mutable.Buffer[GAFISMICSTRUCT]()
    while(buffer.readableBytes() >= 360) {
      GADB_COL_CmpName(buffer.readBytes(36),streamParameter.pszMICName_Data)
      val pmic = new GAFISMICSTRUCT
      try {
        buffer.markReaderIndex()
        pmic.nIndex = GAFIS_MIC_GetItemDataFromStreamSingleByte(buffer, streamParameter.pszItemIndex_Data)
      } catch {
        case e: Throwable => //
          buffer.resetReaderIndex()
      }
      pmic.nItemFlag = GAFIS_MIC_GetItemDataFromStreamSingleByte(buffer, streamParameter.pszItemFlag_Data)
      pmic.nItemType = GAFIS_MIC_GetItemDataFromStreamSingleByte(buffer, streamParameter.pszItemType_Data)
      pmic.nItemData = GAFIS_MIC_GetItemDataFromStreamSingleByte(buffer, streamParameter.pszItemData_Data)
      pmic.bIsLatent = GAFIS_MIC_GetItemDataFromStreamSingleByte(buffer, streamParameter.pszIsLatent_Data)

      pmic.pstMnt_Data = GAFIS_MIC_GetItemDataFromStreamByteArray(buffer, streamParameter.pszMntName_Data)
      pmic.nMntLen = pmic.pstMnt_Data.length

      pmic.pstImg_Data = GAFIS_MIC_GetItemDataFromStreamByteArray(buffer, streamParameter.pszImgName_Data)
      pmic.nImgLen = pmic.pstImg_Data.length

      pmic.pstCpr_Data = GAFIS_MIC_GetItemDataFromStreamByteArray(buffer, streamParameter.pszCprName_Data)
      pmic.nCprLen = pmic.pstCpr_Data.length

      pmic.pstBin_Data = GAFIS_MIC_GetItemDataFromStreamByteArray(buffer, streamParameter.pszBinName_Data)
      pmic.nBinLen = pmic.pstBin_Data.length

      result += pmic
    }

    result.toSeq
  }
  private def GAFIS_MIC_GetItemDataFromStream(buffer:ChannelBuffer,pszExpectedName:String): Int={
    GADB_COL_CmpName(buffer.readBytes(32),pszExpectedName)
    val nLen = buffer.readInt()
    if(nLen > buffer.readableBytes()){
      throw new IllegalAccessException("nLen=%d > readable =%d".format(nLen,buffer.readableBytes()))
    }
    nLen
  }
  private def GAFIS_MIC_GetItemDataFromStreamSingleByte(buffer:ChannelBuffer,pszExpectedName:String): Byte={
    val dataLength = GAFIS_MIC_GetItemDataFromStream(buffer,pszExpectedName)
    val byte = buffer.readByte()
    buffer.skipBytes(UTIL_TO4ALIGN(dataLength) - dataLength)
    byte
  }
  private def GAFIS_MIC_GetItemDataFromStreamByteArray(buffer:ChannelBuffer,pszExpectedName:String): Array[Byte]={
    val dataLength = GAFIS_MIC_GetItemDataFromStream(buffer,pszExpectedName)
    val bytes = buffer.readBytes(dataLength).array()
    buffer.skipBytes(UTIL_TO4ALIGN(dataLength) - dataLength)
    bytes
  }
  private def UTIL_TO4ALIGN(x:Int)=	((x + 3) / 4) * 4

  private def GADB_COL_CmpName(bytes:ChannelBuffer,expectedName:String) {
    val currentName= new String(bytes.array()).trim
    if(!currentName.startsWith(expectedName))
      throw new IllegalAccessException("expectName=%s actual =%s".format(expectedName,currentName))
  }

  /**
    * 比对结果MatchResultObject列表转换为gafis候选列表
    * @param matchResultObjectList
    * @param queryType
    * @param sidKeyidMap
    * @param isPalm
    * @param isGafis6
    * @return
    */
  def convertMatchResultObjectList2CandList(matchResultObjectList: Seq[MatchResultObject], queryType: Int, sidKeyidMap: Map[Long, String], isPalm: Boolean = false, isGafis6: Boolean = false): Array[Byte] ={
    val result = new ByteArrayOutputStream()
    var index = 0 //比对排名
    matchResultObjectList.foreach{cand=>
      index += 1
      val keyId = sidKeyidMap.get(cand.getObjectId)
      if (keyId.nonEmpty) {
        var fgp = cand.getPos
        //指位转换，TT查询指位0
        if(fgp > 0 || !isPalm){
          fgp = DataConverter.fingerPos8to6(cand.getPos)
          if(isGafis6){
            if(fgp > 10){//gafis6.2中平指指位[21,30]
              fgp += 10
            }
          }
        }
        val gCand = new GAQUERYCANDSTRUCT
        gCand.nScore = cand.getScore
        gCand.szKey = keyId.get
        gCand.nDBID = if (queryType == HallMatcherConstants.QUERY_TYPE_TT || queryType == HallMatcherConstants.QUERY_TYPE_LT) 1 else 2
        gCand.nTableID = 2
        gCand.nIndex = fgp.toByte
        gCand.tFinishTime = new AFISDateTime
        gCand.nStepOneRank = index
        //        gCand.nSrcKeyIndex = cand.getSrcIndex.toByte
        result.write(gCand.toByteArray(AncientConstants.GBK_ENCODING))//这里使用GBK编码，防止keyId是中文的时候报错
      }
    }

    result.toByteArray
  }
}
