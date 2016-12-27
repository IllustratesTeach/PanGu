package nirvana.hall.v62.internal.c.gloclib

import java.nio.ByteBuffer

import com.google.protobuf.ByteString
import nirvana.hall.api.config.QueryDBConfig
import nirvana.hall.c.services.gbaselib.gitempkg.{GBASE_ITEMPKG_ITEMHEADSTRUCT, GBASE_ITEMPKG_PKGHEADSTRUCT}
import nirvana.hall.c.services.gloclib.gadbprop.GADBIDSTRUCT
import nirvana.hall.c.services.gloclib.gaqryque.{GAQUERYCANDSTRUCT, GAQUERYSTRUCT}
import nirvana.hall.c.services.gloclib.glocdef.GAFISMICSTRUCT
import nirvana.hall.c.services.gloclib.gqrycond.GAFIS_QRYPARAM
import nirvana.hall.c.services.gloclib.{gaqryque, glocdef}
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchStatus
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult.{MatchResultObject, MatcherStatus}
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import org.jboss.netty.buffer.ChannelBuffers

import scala.collection.JavaConversions._
import scala.collection.mutable


/**
 * Created by songpeng on 15/12/9.
 */
object gaqryqueConverter {
  final val GAFIS_KEYLIST_GetName = "KeyList"
  final val GAFIS_QRYPARAM_GetName = "QryParam"
  final val GAFIS_QRYFILTER_GetName = "QryFilter"
  final val GAFIS_CANDKEYFILTER_GetName = "CandKeyFilter"
  final val GAFIS_TEXTSQL_GetName = "TextSql"

  /**
   * 将protobuf结构查询任务转为gafis结构
   * @param matchTask
   * @param queryDBConfig
   * @return
   */
  def convertProtoBuf2GAQUERYSTRUCT(matchTask: MatchTask, queryDBConfig: QueryDBConfig)(implicit v62Config: HallV62Config): GAQUERYSTRUCT = {
    val queryStruct = new GAQUERYSTRUCT
    queryStruct.stSimpQry.nQueryType = matchTask.getMatchType.ordinal().asInstanceOf[Byte]
    queryStruct.stSimpQry.nPriority = matchTask.getPriority.toByte
    queryStruct.stSimpQry.nFlag = (gaqryque.GAQRY_FLAG_USEFINGER).asInstanceOf[Byte]
    queryStruct.stSimpQry.szKeyID = matchTask.getMatchId
    queryStruct.stSimpQry.nMaxCandidateNum = matchTask.getTopN  //最大候选个数
    queryStruct.stSimpQry.nDestDBCount = 1  //被查数据库，目前只指定一个
    queryStruct.stSimpQry.nRmtFlag = gaqryque.GAQRY_RMTFLAG_FROMREMOTE.toByte //远程查询
    queryStruct.stSimpQry.szUserName = matchTask.getCommitUser //提交用户
    queryStruct.stSimpQry.nQueryID = matchTask.getQueryid.getBytes //远程查询ID
    //TODO 支持指定物理库查询，但不支持多个物理库查询
    val srcDB = new GADBIDSTRUCT  //特征来源数据库
    val destDB = new GADBIDSTRUCT //被查数据库
    //如果没有指定特征库来源，使用默认库
    if(queryDBConfig.srcDB == None){
      srcDB.nDBID = matchTask.getMatchType match {
        case MatchType.FINGER_TT =>
          v62Config.templateTable.dbId.toShort
        case MatchType.FINGER_TL =>
          v62Config.templateTable.dbId.toShort
        case MatchType.FINGER_LT =>
          v62Config.latentTable.dbId.toShort
        case MatchType.FINGER_LL =>
          v62Config.latentTable.dbId.toShort
      }
    }else{
      srcDB.nDBID = queryDBConfig.srcDB.get
    }
    //如果没有指定被查库，使用默认库
    if(queryDBConfig.destDB == None){
      destDB.nDBID = matchTask.getMatchType match {
        case MatchType.FINGER_TT =>
          v62Config.templateTable.dbId.toShort
        case MatchType.FINGER_TL =>
          v62Config.latentTable.dbId.toShort
        case MatchType.FINGER_LT =>
          v62Config.templateTable.dbId.toShort
        case MatchType.FINGER_LL =>
          v62Config.latentTable.dbId.toShort
      }
    }else{
      destDB.nDBID = queryDBConfig.destDB.get
    }
    //根据比对类型，设置tableid
    matchTask.getMatchType match {
      case MatchType.FINGER_TT =>
        srcDB.nTableID = V62Facade.TID_TPCARDINFO
        destDB.nTableID = V62Facade.TID_TPCARDINFO
      case MatchType.FINGER_TL =>
        srcDB.nTableID = V62Facade.TID_TPCARDINFO
        destDB.nTableID = V62Facade.TID_LATFINGER
      case MatchType.FINGER_LT =>
        srcDB.nTableID = V62Facade.TID_LATFINGER
        destDB.nTableID = V62Facade.TID_TPCARDINFO
      case MatchType.FINGER_LL =>
        srcDB.nTableID = V62Facade.TID_LATFINGER
        destDB.nTableID = V62Facade.TID_LATFINGER
      case other =>
    }
    queryStruct.stSimpQry.stSrcDB = srcDB
    queryStruct.stSimpQry.stDestDB = Array(destDB)

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

  /**
   * 将6的查询候选结果转为protobuf
   * @param gaQueryStruct
   * @return
   */
  def convertGAQUERYSTRUCT2MatchResult(gaQueryStruct: GAQUERYSTRUCT): MatchResult={
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
        cand.setDbid(candData.nDBID.toString)
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
   * 将6的查询任务转为protobuf
   * @param gaQueryStruct
   * @return
   */
  def convertGAQUERYSTRUCT2MatchTask(gaQueryStruct: GAQUERYSTRUCT): MatchTask ={
    val matchTask = MatchTask.newBuilder()
    //ora_sid
    matchTask.setMatchId(convertSixByteArrayToLong(gaQueryStruct.stSimpQry.nQueryID).toString)
    //优先级
    matchTask.setPriority(gaQueryStruct.stSimpQry.nPriority)
    //查询类型 TODO 掌纹查询类型?
    matchTask.setMatchType(MatchType.valueOf(gaQueryStruct.stSimpQry.nQueryType + 1))
    //任务本身的sid
    matchTask.setObjectId(1)
    matchTask.setScoreThreshold(gaQueryStruct.stSimpQry.nMinScore)
    matchTask.setTopN(gaQueryStruct.stSimpQry.nMaxCandidateNum)
    gaQueryStruct.pstMIC_Data.foreach{micStruct =>
      if(micStruct.bIsLatent == 1){
        val ldata = matchTask.getLDataBuilder
        ldata.setMinutia(ByteString.copyFrom(micStruct.pstMnt_Data))
        if(micStruct.pstBin_Data.length > 0)
          ldata.setRidge(ByteString.copyFrom(micStruct.pstBin_Data))
      }else{
        //这里指位不做转换
        val pos = micStruct.nItemData
        matchTask.getTDataBuilder.addMinutiaDataBuilder().setMinutia(ByteString.copyFrom(micStruct.pstMnt_Data)).setPos(pos)
      }
    }
    //TODO 文本查询,高级查询参数

    matchTask.build()
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

  /**
   * 比对状态status转换为protobuf
   * @param status
   * @return
   */
  def convertStatusAsMatchStatus(status: Short): MatchStatus={
    status match {
      case 0 =>
        MatchStatus.WAITING_MATCH
      case 1 =>
        MatchStatus.MATCHING
      case 2 =>
        MatchStatus.WAITING_CHECK
      case 5 =>
        MatchStatus.FAILED
      case 7 =>
        MatchStatus.CHECKED
      case 8 =>
        MatchStatus.CHECKING
      case 9 =>
        MatchStatus.WAITING_RECHECK
      case 10 =>
        MatchStatus.RECHECKING
      case 11 =>
        MatchStatus.RECHECKED
      case other =>
        MatchStatus.UN_KNOWN
    }
  }

  /**
   * 转换候选列表
   * @param candList
   * @return
   */
  def convertCandList2GAQUERYCANDSTRUCT(candList: Array[Byte]): Seq[MatchResultObject]={
    if(candList != null && candList.size > 0){
      val buffer = ChannelBuffers.wrappedBuffer(candList)
      val result = mutable.Buffer[MatchResultObject]()
      while(buffer.readableBytes() >= 96) {
        val gaCand = new GAQUERYCANDSTRUCT
        gaCand.fromStreamReader(buffer)
        val matchResultObject = MatchResultObject.newBuilder()
        matchResultObject.setObjectId(gaCand.szKey)
        matchResultObject.setPos(gaCand.nIndex)
        matchResultObject.setScore(gaCand.nScore)
        matchResultObject.setDbid(gaCand.nDBID.toString)     //后来加的，2016.12.5
        result += matchResultObject.build()
      }
      result
    }else{
      Seq()
    }
  }

}
