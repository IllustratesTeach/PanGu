package nirvana.hall.v62.internal.c.gloclib

import java.nio.ByteBuffer

import nirvana.hall.c.services.gbaselib.gitempkg.{GBASE_ITEMPKG_ITEMHEADSTRUCT, GBASE_ITEMPKG_PKGHEADSTRUCT}
import nirvana.hall.c.services.gloclib.gadbprop.GADBIDSTRUCT
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYSTRUCT
import nirvana.hall.c.services.gloclib.glocdef.GAFISMICSTRUCT
import nirvana.hall.c.services.gloclib.gqrycond.GAFIS_QRYPARAM
import nirvana.hall.c.services.gloclib.{gaqryque, glocdef}
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult.MatcherStatus
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.v62.config.HallV62Config
import org.jboss.netty.buffer.ChannelBuffers

import scala.collection.JavaConversions._


/**
 * Created by songpeng on 15/12/9.
 */
object gaqryqueConverter {
  final val GAFIS_KEYLIST_GetName = "KeyList"
  final val GAFIS_QRYPARAM_GetName = "QryParam"
  final val GAFIS_QRYFILTER_GetName = "QryFilter"
  final val GAFIS_CANDKEYFILTER_GetName = "CandKeyFilter"
  final val GAFIS_TEXTSQL_GetName = "TextSql"

  def convertProtoBuf2GAQUERYSTRUCT(matchTask: MatchTask)(implicit v62Config: HallV62Config): GAQUERYSTRUCT = {
    val queryStruct = new GAQUERYSTRUCT
    queryStruct.stSimpQry.nQueryType = matchTask.getMatchType.ordinal().asInstanceOf[Byte]
    queryStruct.stSimpQry.nPriority = matchTask.getPriority.toByte
    queryStruct.stSimpQry.nFlag = (gaqryque.GAQRY_FLAG_USEFINGER).asInstanceOf[Byte]
    queryStruct.stSimpQry.szKeyID = matchTask.getMatchId

    matchTask.getMatchType match {
      case MatchType.FINGER_TT =>
        queryStruct.stSimpQry.stSrcDB.nDBID = v62Config.templateTable.dbId.asInstanceOf[Short]
        queryStruct.stSimpQry.stSrcDB.nTableID= v62Config.templateTable.tableId.asInstanceOf[Short]
        queryStruct.stSimpQry.stDestDB = Array(new GADBIDSTRUCT)
        queryStruct.stSimpQry.nDestDBCount = 1
        queryStruct.stSimpQry.stDestDB.apply(0).nDBID = v62Config.templateTable.dbId.asInstanceOf[Short]
        queryStruct.stSimpQry.stDestDB.apply(0).nTableID= v62Config.templateTable.tableId.asInstanceOf[Short]
      case MatchType.FINGER_TL =>
        queryStruct.stSimpQry.stSrcDB.nDBID = v62Config.templateTable.dbId.asInstanceOf[Short]
        queryStruct.stSimpQry.stSrcDB.nTableID= v62Config.templateTable.tableId.asInstanceOf[Short]
        queryStruct.stSimpQry.stDestDB = Array(new GADBIDSTRUCT)
        queryStruct.stSimpQry.nDestDBCount = 1
        queryStruct.stSimpQry.stDestDB.apply(0).nDBID = v62Config.latentTable.dbId.asInstanceOf[Short]
        queryStruct.stSimpQry.stDestDB.apply(0).nTableID= v62Config.latentTable.tableId.asInstanceOf[Short]
      case MatchType.FINGER_LT =>
        queryStruct.stSimpQry.stSrcDB.nDBID = v62Config.latentTable.dbId.asInstanceOf[Short]
        queryStruct.stSimpQry.stSrcDB.nTableID= v62Config.latentTable.tableId.asInstanceOf[Short]
        queryStruct.stSimpQry.stDestDB = Array(new GADBIDSTRUCT)
        queryStruct.stSimpQry.nDestDBCount = 1
        queryStruct.stSimpQry.stDestDB.apply(0).nDBID = v62Config.templateTable.dbId.asInstanceOf[Short]
        queryStruct.stSimpQry.stDestDB.apply(0).nTableID= v62Config.templateTable.tableId.asInstanceOf[Short]
      case MatchType.FINGER_LL =>
        queryStruct.stSimpQry.stSrcDB.nDBID = v62Config.latentTable.dbId.asInstanceOf[Short]
        queryStruct.stSimpQry.stSrcDB.nTableID= v62Config.latentTable.tableId.asInstanceOf[Short]
        queryStruct.stSimpQry.stDestDB = Array(new GADBIDSTRUCT)
        queryStruct.stSimpQry.nDestDBCount = 1
        queryStruct.stSimpQry.stDestDB.apply(0).nDBID = v62Config.latentTable.dbId.asInstanceOf[Short]
        queryStruct.stSimpQry.stDestDB.apply(0).nTableID= v62Config.latentTable.tableId.asInstanceOf[Short]
      case other =>
    }

    //设置比对参数
    val item = new GAFIS_QRYPARAM
    item.stXgw.bFullMatchOn = matchTask.getConfig.getFullMatchOn.asInstanceOf[Byte]

    val itemDataLength = item.getDataSize
    val itemHead = new GBASE_ITEMPKG_ITEMHEADSTRUCT
    itemHead.szItemName = GAFIS_TEXTSQL_GetName
    itemHead.nItemLen = itemDataLength

    val itemPackage = new GBASE_ITEMPKG_PKGHEADSTRUCT
    itemPackage.nDataLen = itemPackage.getDataSize + itemHead.getDataSize + itemHead.nItemLen
    itemPackage.nBufSize = itemPackage.nDataLen

    val buffer = ChannelBuffers.buffer(itemPackage.nDataLen)
    itemPackage.writeToStreamWriter(buffer)

    itemHead.writeToStreamWriter(buffer)
    item.writeToStreamWriter(buffer)

    val bytes = buffer.array()
    queryStruct.pstQryCond_Data = bytes
    queryStruct.nQryCondLen = queryStruct.pstQryCond_Data.length

    queryStruct.nItemFlagA = gaqryque.GAIFA_FLAG.asInstanceOf[Byte]

    //特征数据
    val matchType = matchTask.getMatchType.getNumber
    if (matchType == MatchType.FINGER_LL.getNumber || matchType == MatchType.FINGER_LT.getNumber){
      val mic = new GAFISMICSTRUCT
      mic.pstMnt_Data = matchTask.getLData.getMinutia.toByteArray
      mic.nMntLen = mic.pstMnt_Data.length
      mic.nItemFlag = glocdef.GAMIC_ITEMFLAG_MNT.asInstanceOf[Byte]
      mic.nItemData = 1
      mic.nItemType = glocdef.GAMIC_ITEMTYPE_FINGER.asInstanceOf[Byte]
      queryStruct.pstMIC_Data = Array(mic)
    }else if(matchType == MatchType.FINGER_TT.getNumber || matchType == MatchType.FINGER_TL.getNumber){
      queryStruct.pstMIC_Data =
        matchTask.getTData.getMinutiaDataList.map{md=>
          val mic = new GAFISMICSTRUCT
          mic.pstMnt_Data = md.getMinutia.toByteArray
          mic.nMntLen = mic.pstMnt_Data.length
          mic.nItemFlag = glocdef.GAMIC_ITEMFLAG_MNT.asInstanceOf[Byte]
          if(md.getPos > 10){//平面指纹
            mic.nItemType = glocdef.GAMIC_ITEMTYPE_TPLAIN.asInstanceOf[Byte]
            mic.nItemData = (md.getPos - 10).toByte
          }else{
            mic.nItemType = glocdef.GAMIC_ITEMTYPE_FINGER.asInstanceOf[Byte]
            mic.nItemData =  md.getPos.toByte
          }

          mic

        }.toArray
    }
    queryStruct.nMICCount = queryStruct.pstMIC_Data.length
    queryStruct
  }

  def convertQueryId2GAQUERYSTRUCT(queryId: Long):GAQUERYSTRUCT ={
    val pstQry = new GAQUERYSTRUCT
    pstQry.stSimpQry.nQueryID = convertLongAsSixByteArray(queryId)
    pstQry
  }

  def convertGAQUERYSTRUCT2ProtoBuf(gaQueryStruct: GAQUERYSTRUCT): MatchResult={
    val matchResult = MatchResult.newBuilder()

    val queryId = gaQueryStruct.stSimpQry.nQueryID
    matchResult.setMatchId(convertSixByteArrayToLong(queryId)+"")

    var maxScore = 0
    val pstCandData = gaQueryStruct.pstCand_Data
    if(gaQueryStruct.pstCand_Data != null){
      matchResult.setCandidateNum(gaQueryStruct.pstCand_Data.length)
      pstCandData.foreach{ candData =>
        val cand = matchResult.addCandidateResultBuilder()
        cand.setObjectId(candData.szKey)
        cand.setPos(candData.nIndex)
        cand.setScore(candData.nScore)
        if(maxScore < candData.nScore)
          maxScore = candData.nScore
      }
    }else{
      matchResult.setCandidateNum(0)
    }

    matchResult.setMaxScore(maxScore)
    matchResult.setStatus(MatcherStatus.newBuilder().setMsg("success").setCode(0))

    matchResult.build()
  }

  /**
   * 转换6个字节成long
   * @param bytes 待转换的六个字节
   * @return 转换后的long数字
   */
  def convertSixByteArrayToLong(bytes: Array[Byte]): Long = {
    /*
    val byteBuffer = ByteBuffer.allocate(8).put(Array[Byte](0,0)).put(bytes)
    byteBuffer.rewind()
    byteBuffer.getLong
    */
    var l = 0L
    l |= (0xff & bytes(0)) << 16
    l |= (0xff & bytes(1)) << 8
    l |= (0xff & bytes(2))
    l <<= 24

    l |= (0xff & bytes(3)) << 16
    l |= (0xff & bytes(4)) << 8
    l |= (0xff & bytes(5))

    l
  }
  def convertLongAsSixByteArray(sid: Long): Array[Byte]=
    ByteBuffer.allocate(8).putLong(sid).array().slice(2,8)
}
