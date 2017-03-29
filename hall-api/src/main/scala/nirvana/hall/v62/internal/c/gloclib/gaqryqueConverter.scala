package nirvana.hall.v62.internal.c.gloclib

import java.nio.ByteBuffer

import com.google.protobuf.ByteString
import monad.support.services.LoggerSupport
import nirvana.hall.api.config.QueryDBConfig
import nirvana.hall.api.internal.DateConverter
import nirvana.hall.c.services.gbaselib.gitempkg.{GBASE_ITEMPKG_ITEMHEADSTRUCT, GBASE_ITEMPKG_PKGHEADSTRUCT}
import nirvana.hall.c.services.gloclib.gadbprop.GADBIDSTRUCT
import nirvana.hall.c.services.gloclib.gaqryque.{GAFIS_QUERYINFO, GAQUERYCANDSTRUCT, GAQUERYSTRUCT}
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
import nirvana.hall.v70.internal.sync.ProtobufConverter
import org.jboss.netty.buffer.ChannelBuffers

import scala.collection.JavaConversions._
import scala.collection.mutable


/**
 * Created by songpeng on 15/12/9.
 */
object gaqryqueConverter extends LoggerSupport{
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

    queryStruct.pstInfo_Data = new GAFIS_QUERYINFO
    val ips=matchTask.getComputerIp.split('.') //机器IP
    val new_ip=new Array[Byte](4)
    val hex_size=16 //16进制位数
    for( i <- 0 until ips.length){
      new_ip(i)=Integer.parseInt(Integer.toHexString(Integer.parseInt(ips(i))),hex_size).toByte // 由于存储需要16进制数，将String转成10进制数后再转成16进制字符串最后转成16进制数
    }

    queryStruct.pstInfo_Data.bnIP = new_ip
    queryStruct.pstInfo_Data.szUserUnitCode= matchTask.getUserUnitCode  //提交用户单位代码
    queryStruct.nQryInfoLen = queryStruct.pstInfo_Data.getDataSize

    queryStruct.stSimpQry.nRmtFlag = gaqryque.GAQRY_RMTFLAG_FROMREMOTE.toByte //远程查询
    queryStruct.stSimpQry.szUserName = matchTask.getCommitUser //提交用户
    if(matchTask.getQueryid.nonEmpty){
      queryStruct.stSimpQry.nQueryID = gaqryqueConverter.convertLongAsSixByteArray(matchTask.getQueryid.toLong) //远程查询ID
    }
    //TODO 支持指定物理库查询，但不支持多个物理库查询
    val srcDB = new GADBIDSTRUCT  //特征来源数据库
    val destDB = new GADBIDSTRUCT //被查数据库
    //如果没有指定特征库来源，使用默认库
    if(queryDBConfig.srcDB == None){
      srcDB.nDBID = matchTask.getMatchType match {
        case MatchType.FINGER_TT | MatchType.FINGER_TL | MatchType.PALM_TT | MatchType.PALM_TL =>
          v62Config.templateTable.dbId.toShort
        case MatchType.FINGER_LT | MatchType.FINGER_LL | MatchType.PALM_LT | MatchType.PALM_LL =>
          v62Config.latentTable.dbId.toShort
      }
    }else{
      srcDB.nDBID = queryDBConfig.srcDB.get
    }
    //如果没有指定被查库，使用默认库
    if(queryDBConfig.destDB == None){
      destDB.nDBID = matchTask.getMatchType match {
        case MatchType.FINGER_TT | MatchType.FINGER_LT | MatchType.PALM_TT | MatchType.PALM_LT =>
          v62Config.templateTable.dbId.toShort
        case MatchType.FINGER_TL | MatchType.FINGER_LL | MatchType.PALM_TL | MatchType.PALM_LL =>
          v62Config.latentTable.dbId.toShort
      }
    }else{
      destDB.nDBID = queryDBConfig.destDB.get
    }
    //根据比对类型，设置tableid
    matchTask.getMatchType match {
      case MatchType.FINGER_TT | MatchType.PALM_TT =>
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
      case MatchType.PALM_TL =>
        srcDB.nTableID = V62Facade.TID_TPCARDINFO
        destDB.nTableID = V62Facade.TID_LATPALM
      case MatchType.PALM_LT =>
        srcDB.nTableID = V62Facade.TID_LATPALM
        destDB.nTableID = V62Facade.TID_TPCARDINFO
      case MatchType.PALM_LL =>
        srcDB.nTableID = V62Facade.TID_LATPALM
        destDB.nTableID = V62Facade.TID_LATPALM
    }
    queryStruct.stSimpQry.stSrcDB = srcDB
    queryStruct.stSimpQry.stDestDB = Array(destDB)

    //设置比对参数
    val item = new GAFIS_QRYPARAM
    item.stXgw.bFullMatchOn = matchTask.getConfig.getFullMatchOn.toByte

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

    queryStruct.nItemFlagA = gaqryque.GAIFA_FLAG.toByte

    //特征数据
    matchTask.getMatchType match {
      case MatchType.FINGER_LL | MatchType.FINGER_LT =>
        val mic = new GAFISMICSTRUCT
        mic.pstMnt_Data = matchTask.getLData.getMinutia.toByteArray
        mic.nMntLen = mic.pstMnt_Data.length
        mic.nItemFlag = glocdef.GAMIC_ITEMFLAG_MNT.toByte
        mic.nItemData = 0
        mic.nItemType = glocdef.GAMIC_ITEMTYPE_FINGER.toByte
        queryStruct.pstMIC_Data = Array(mic)
      case MatchType.FINGER_TT | MatchType.FINGER_TL =>
        queryStruct.pstMIC_Data =
          matchTask.getTData.getMinutiaDataList.map{md=>
            val mic = new GAFISMICSTRUCT
            mic.pstMnt_Data = md.getMinutia.toByteArray
            mic.nMntLen = mic.pstMnt_Data.length
            mic.nItemFlag = glocdef.GAMIC_ITEMFLAG_MNT.toByte
            if(md.getPos > 10){//平面指纹
              mic.nItemType = glocdef.GAMIC_ITEMTYPE_TPLAIN.toByte
              mic.nItemData = (md.getPos - 10).toByte
            }else{
              mic.nItemType = glocdef.GAMIC_ITEMTYPE_FINGER.toByte
              mic.nItemData =  md.getPos.toByte
            }

            mic
          }.toArray
      case MatchType.PALM_LL | MatchType.PALM_LT =>
        val mic = new GAFISMICSTRUCT
        mic.pstMnt_Data = matchTask.getLData.getMinutia.toByteArray
        mic.nMntLen = mic.pstMnt_Data.length
        mic.nItemFlag = glocdef.GAMIC_ITEMFLAG_MNT.toByte
        mic.nItemData = 0
        mic.nItemType = glocdef.GAMIC_ITEMTYPE_PALM.toByte
        queryStruct.pstMIC_Data = Array(mic)

      case MatchType.PALM_TT | MatchType.PALM_TL =>
        queryStruct.pstMIC_Data =
          matchTask.getTData.getMinutiaDataList.map{md=>
            val mic = new GAFISMICSTRUCT
            mic.pstMnt_Data = md.getMinutia.toByteArray
            mic.nMntLen = mic.pstMnt_Data.length
            mic.nItemFlag = glocdef.GAMIC_ITEMFLAG_MNT.toByte
            mic.nItemType = glocdef.GAMIC_ITEMTYPE_PALM.toByte
            mic.nItemData =  md.getPos.toByte

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

    //比中概率
    matchResult.setHITPOSSIBILITY(gaQueryStruct.stSimpQry.nHitPossibility)

    matchResult.build()
  }

  /**
   * 将6的查询任务转为protobuf
   * @param gaQueryStruct
   * @return
   */
  def convertGAQUERYSTRUCT2MatchTask(gaQueryStruct: GAQUERYSTRUCT): MatchTask ={
    val stSimpQry = gaQueryStruct.stSimpQry
    val matchTask = MatchTask.newBuilder()
    matchTask.setMatchId(stSimpQry.szKeyID)
    //优先级
    matchTask.setPriority(stSimpQry.nPriority)
    //查询类型
    val queryType = stSimpQry.nQueryType.toShort
    val isPalm = (gaqryque.GAQRY_FLAG_USEPALM & stSimpQry.nFlag) > 0
    val matchType = ProtobufConverter.convertQueryType2MatchType(queryType, isPalm)
    matchTask.setMatchType(matchType)
    //ora_sid
    matchTask.setObjectId(convertSixByteArrayToLong(gaQueryStruct.stSimpQry.nQueryID))
    matchTask.setScoreThreshold(gaQueryStruct.stSimpQry.nMinScore)
    matchTask.setTopN(gaQueryStruct.stSimpQry.nMaxCandidateNum)
    //添加6.2任务的创建时间
    matchTask.setOraCreatetime(DateConverter.convertAFISDateTime2String(gaQueryStruct.stSimpQry.tSubmitTime))
    gaQueryStruct.pstMIC_Data.foreach{mic =>
      if(mic.bIsLatent == 1){
        val ldata = matchTask.getLDataBuilder
        ldata.setMinutia(ByteString.copyFrom(mic.pstMnt_Data))
        if(mic.pstBin_Data.length > 0)
          ldata.setRidge(ByteString.copyFrom(mic.pstBin_Data))
      }else{
        val tdata = matchTask.getTDataBuilder.addMinutiaDataBuilder()
        mic.nItemType match {
          case glocdef.GAMIC_ITEMTYPE_FINGER =>
            tdata.setMinutia(ByteString.copyFrom(mic.pstMnt_Data)).setPos(mic.nItemData)
          case glocdef.GAMIC_ITEMTYPE_TPLAIN =>
            //平面
            tdata.setMinutia(ByteString.copyFrom(mic.pstMnt_Data)).setPos(mic.nItemData+10)
          case glocdef.GAMIC_ITEMTYPE_PALM =>
            //掌纹
            tdata.setMinutia(ByteString.copyFrom(mic.pstMnt_Data)).setPos(mic.nItemFlag)
          case other =>
            warn("unsupport itemType:", other)
        }
        if(mic.pstBin_Data != null){
          tdata.setRidge(ByteString.copyFrom(mic.pstBin_Data))
        }
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
  def convertCandList2MatchResultObject(candList: Array[Byte]): Seq[MatchResultObject]={
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

  /**
    * protobuf候选转换为GAQUERYCANDSTRUCT
    * @param matchResultObject
    * @return
    */
  def convertMatchResultObject2GAQUERYCANDSTRUCT(matchResultObject: MatchResultObject): GAQUERYCANDSTRUCT={
    val gCand = new GAQUERYCANDSTRUCT
    gCand.szKey = matchResultObject.getObjectId
    gCand.nIndex = matchResultObject.getPos.toByte
    gCand.nScore = matchResultObject.getScore
    gCand.nDBID = matchResultObject.getDbid.toShort

    gCand
  }

}
