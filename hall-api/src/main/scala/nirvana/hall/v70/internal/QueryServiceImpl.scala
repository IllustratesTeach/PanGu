package nirvana.hall.v70.internal

import java.util.Date
import javax.persistence.EntityManager

import com.google.protobuf.ByteString
import nirvana.hall.api.config.QueryDBConfig
import nirvana.hall.api.internal.DateConverter
import nirvana.hall.api.services.QueryService
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gloclib.gaqryque.{GAQUERYCANDHEADSTRUCT, GAQUERYCANDSTRUCT, GAQUERYSTRUCT}
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchStatus
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult.MatcherStatus
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask.LatentMatchData
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.v62.internal.c.gloclib.{galoctp, gaqryqueConverter}
import nirvana.hall.v70.internal.query.QueryConstants
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa._
import nirvana.hall.v70.services.sys.UserService
import org.jboss.netty.buffer.ChannelBuffers

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/1/26.
 */
class QueryServiceImpl(entityManager: EntityManager,userService:UserService) extends QueryService{
  /**
   * 发送查询任务
    *
    * @param matchTask
   * @return
   */
  override def addMatchTask(matchTask: MatchTask, queryDBConfig: QueryDBConfig): Long = {
    val gafisQuery = ProtobufConverter.convertMatchTask2GafisNormalqueryQueryque(matchTask)
    val query = entityManager.createNativeQuery("select SEQ_ORASID.nextval from dual")
    gafisQuery.oraSid = query.getResultList.get(0).toString.toLong
    gafisQuery.pkId = CommonUtils.getUUID()
    gafisQuery.submittsystem = QueryConstants.SUBMIT_SYS_AFIS
    
    //用户名获取用户ID
    var sysUser = userService.findSysUserByLoginName(gafisQuery.username)
    if (sysUser.isEmpty){
      sysUser = Option(SysUser.find(Gafis70Constants.INPUTPSN))
    }
    gafisQuery.userid = sysUser.get.pkId
    gafisQuery.username = sysUser.get.trueName
    gafisQuery.userunitcode = sysUser.get.departCode

    if(matchTask.getOraCreatetime.nonEmpty){
      gafisQuery.createtime = DateConverter.convertString2Date(matchTask.getOraCreatetime, "yyyy-MM-dd HH:mm:ss")
    }else{
      gafisQuery.createtime = new Date()
    }

    gafisQuery.deletag = Gafis70Constants.DELETAG_USE
    gafisQuery.save()

    gafisQuery.oraSid
  }

  /**
   * 获取查询信息
    *
    * @param oraSid
   * @return
   */
  override def getMatchResult(oraSid: Long, dbId: Option[String]): Option[MatchResult]= {
    val queryQue = GafisNormalqueryQueryque.find_by_oraSid(oraSid).head
    if(queryQue.status == QueryConstants.STATUS_SUCCESS){
      val matchResult = MatchResult.newBuilder()
      val matchResultObj = gaqryqueConverter.convertCandList2MatchResultObject(queryQue.candlist)
      matchResultObj.foreach{cand=>
        matchResult.addCandidateResult(cand)
      }
      matchResult.setMatchId(oraSid.toString)
      matchResult.setCandidateNum(queryQue.curcandnum)
      matchResult.setTimeElapsed(queryQue.timeElapsed)
      matchResult.setRecordNumMatched(queryQue.recordNumMatched)
      matchResult.setMaxScore(queryQue.hitpossibility.toInt)
      matchResult.setStatus(MatcherStatus.newBuilder().setCode(0))
      matchResult.setMatchFinishTime(DateConverter.convertDate2String(queryQue.finishtime))
      Option(matchResult.build())
    }else{
      None
    }
  }

  /**
    * 通过卡号查找第一个的比中结果
    *
    * @param cardId 卡号
    * @return 比对结果
    */
  override def findFirstQueryResultByCardId(cardId: String, dbId: Option[String]): Option[MatchResult] = {
    throw new UnsupportedOperationException
  }

  /**
   * 根据卡号查找第一个比对任务的状态, 如果没有获取到返回UN_KNOWN
    *
    * @param cardId
   * @return
   */
  override def findFirstQueryStatusByCardIdAndMatchType(cardId: String, matchType: MatchType, dbId: Option[String]): MatchStatus = {
    throw new UnsupportedOperationException
  }

  /**
    * 根据卡号信息发送查询, 不需要特征信息
    * 根据卡号和查询类型从数据库查询特征数据，并放入matchTask，然后调用addMatchTask
    *
    * @param matchTask 只有查询信息不需要特征信息
    * @param queryDBConfig
    * @return 任务号
    */
  override def sendQuery(matchTask: MatchTask, queryDBConfig: QueryDBConfig): Long = {
    val matchTaskBuilder = matchTask.toBuilder
    val cardId = matchTask.getMatchId
    matchTask.getMatchType match {
      case MatchType.FINGER_TT | MatchType.FINGER_TL =>
        val fingerList = GafisGatherFinger.find_by_personId_and_lobtype(cardId, Gafis70Constants.LOBTYPE_MNT)
        val mntMap = mutable.Map[String, GafisGatherFinger]()
        val binMap = mutable.Map[String, GafisGatherFinger]()
        fingerList.filter(_.groupId == Gafis70Constants.GROUP_ID_MNT).map{finger=>
          mntMap += (finger.fgpCase +"-"+finger.fgp -> finger)
        }
        fingerList.filter(_.groupId == Gafis70Constants.GROUP_ID_BIN).foreach{finger=>
          binMap += (finger.fgpCase +"-"+finger.fgp -> finger)
        }
        mntMap.foreach{fingerMnt=>
          val tdata = matchTaskBuilder.getTDataBuilder.addMinutiaDataBuilder()
          var pos:Int = fingerMnt._2.fgp
          //平面指位+10
          if(fingerMnt._2.fgpCase == Gafis70Constants.FGP_CASE_PLAIN){
            pos = fingerMnt._2.fgp + 10
          }
          tdata.setMinutia(ByteString.copyFrom(fingerMnt._2.gatherData)).setPos(pos)
          //纹线数据
          val fingerBin = binMap.get(fingerMnt._1)
          if(fingerBin.nonEmpty){
            tdata.setRidge(ByteString.copyFrom(fingerBin.get.gatherData)).setPos(pos)
          }
        }
        if(fingerList.isEmpty){
          throw new IllegalArgumentException("特征数据不存在cardId:"+ cardId)
        }
      case MatchType.FINGER_LT | MatchType.FINGER_LL =>
        val fingerList = GafisCaseFingerMnt.find_by_fingerId(cardId)
        fingerList.foreach{finger=>
          val ldata = LatentMatchData.newBuilder()
          ldata.setMinutia(ByteString.copyFrom(finger.fingerMnt))
          if(finger.fingerRidge != null){
            ldata.setRidge(ByteString.copyFrom(finger.fingerRidge))
          }
          matchTaskBuilder.setLData(ldata.build())
        }
        if(fingerList.isEmpty){
          throw new IllegalArgumentException("特征数据不存在cardId:"+ cardId)
        }
    }
    addMatchTask(matchTaskBuilder.build())
  }

  /**
    * 根据编号和查询类型发送查询
    * 最大候选50，优先级2，最小分数60
    *
    * @param cardId
    * @param matchType
    * @param queryDBConfig
    * @return
    */
  override def sendQueryByCardIdAndMatchType(cardId: String, matchType: MatchType, queryDBConfig: QueryDBConfig = new QueryDBConfig(None, None, None)): Long = {
    val matchTask = MatchTask.newBuilder
    matchType match {
      case MatchType.FINGER_TT |
           MatchType.FINGER_TL |
           MatchType.FINGER_LL |
           MatchType.FINGER_LT =>
        matchTask.setMatchType(matchType)
      case other =>
        throw new IllegalArgumentException("unsupport MatchType:" + matchType)
    }
    matchTask.setObjectId(0)//这里设置为0也不会比中自己
    matchTask.setMatchId(cardId)
    matchTask.setTopN(50)
    matchTask.setObjectId(0)
    matchTask.setPriority(2)
    matchTask.setScoreThreshold(60)

    sendQuery(matchTask.build(), queryDBConfig)
  }

  /**
    * 根据任务号sid获取比对状态
    *
    * @param oraSid
    * @param dbId
    * @return
    */
  override def getStatusBySid(oraSid: Long, dbId: Option[String]): Int = {
    val status = GafisNormalqueryQueryque.select(GafisNormalqueryQueryque.status[java.lang.Short]).where(GafisNormalqueryQueryque.oraSid === oraSid)
    if(status.nonEmpty){
      status.toList(0).asInstanceOf[Short].toInt
    }else{
      0
    }
  }

  /**
    * 获取查询信息GAQUERYSTRUCT
    *
    * @param oraSid
    * @param dbId
    * @return
    */
  override def getGAQUERYSTRUCT(oraSid: Long, dbId: Option[String]): GAQUERYSTRUCT = {
    val gafisNormalqueryQueryque = GafisNormalqueryQueryque.find_by_oraSid(oraSid).head

    convertGafisNormalqueryQueryque2GAQUERYSTRUCT(gafisNormalqueryQueryque)
  }

  def convertGafisNormalqueryQueryque2GAQUERYSTRUCT(gafisNormalqueryQueryque: GafisNormalqueryQueryque): GAQUERYSTRUCT ={
    val gaQuery = new GAQUERYSTRUCT
    val stSimpQry = gaQuery.stSimpQry
    stSimpQry.szKeyID = gafisNormalqueryQueryque.keyid
    stSimpQry.nQueryID = gaqryqueConverter.convertLongAsSixByteArray(gafisNormalqueryQueryque.oraSid)
    stSimpQry.nPriority = gafisNormalqueryQueryque.priority.toByte
    stSimpQry.nStatus = gafisNormalqueryQueryque.status.toByte
    stSimpQry.nCurCandidateNum = gafisNormalqueryQueryque.curcandnum
    stSimpQry.nMaxCandidateNum = gafisNormalqueryQueryque.maxcandnum
    stSimpQry.nFlag = gafisNormalqueryQueryque.flag.toByte
    stSimpQry.nQueryType = gafisNormalqueryQueryque.querytype.toByte
//    stSimpQry.nDestDBCount = 1  //被查数据库，目前只指定一个
//    stSimpQry.stSrcDB = new GADBIDSTRUCT
//    sttSimpQry.stDestDB = Array(destDB)
    stSimpQry.nVerifyResult = gafisNormalqueryQueryque.verifyresult.toByte
    stSimpQry.szUserName = gafisNormalqueryQueryque.username
    if(gafisNormalqueryQueryque.createtime != null){
      stSimpQry.tSubmitTime = DateConverter.convertDate2AFISDateTime(gafisNormalqueryQueryque.createtime)
    }
    if(gafisNormalqueryQueryque.finishtime != null){
      stSimpQry.tFinishTime = DateConverter.convertDate2AFISDateTime(gafisNormalqueryQueryque.finishtime)
    }
    val candListData = gafisNormalqueryQueryque.candlist
    val candList = new ArrayBuffer[GAQUERYCANDSTRUCT]()
    if(candListData != null && candListData.size > 0){
      val buffer = ChannelBuffers.wrappedBuffer(candListData)
      while(buffer.readableBytes() >= 96) {
        val gaCand = new GAQUERYCANDSTRUCT
        gaCand.fromStreamReader(buffer)
        candList += gaCand
      }
      gaQuery.pstCand_Data = candList.toArray

      val candHead = new GAQUERYCANDHEADSTRUCT
      if(gafisNormalqueryQueryque.candhead != null){
        candHead.fromByteArray(gafisNormalqueryQueryque.candhead, AncientConstants.GBK_ENCODING)
      }else{
        candHead.szKey = gafisNormalqueryQueryque.keyid
        candHead.bIsPalm = if (gafisNormalqueryQueryque.flag == 22) 1 else 0
        val queryType = gafisNormalqueryQueryque.querytype
        candHead.nQueryType = queryType.toByte
        candHead.nSrcDBID = if (queryType == QueryConstants.QUERY_TYPE_TT || queryType == QueryConstants.QUERY_TYPE_TL) 1 else 2
        candHead.nTableID = 2
        candHead.nQueryID = gaqryqueConverter.convertLongAsSixByteArray(gafisNormalqueryQueryque.oraSid)
        candHead.nCandidateNum = gafisNormalqueryQueryque.curcandnum
        candHead.tFinishTime = DateConverter.convertDate2AFISDateTime(gafisNormalqueryQueryque.finishtime)
      }
      gaQuery.pstCandHead_Data = candHead
      gaQuery.nCandHeadLen = gaQuery.pstCandHead_Data.getDataSize
      gaQuery.nCandLen = gaQuery.pstCand_Data.length * new GAQUERYCANDSTRUCT().getDataSize
    } else {
      gaQuery.nCandHeadLen = 0
      gaQuery.nCandLen = 0
    }
    //mic
    val mic = gafisNormalqueryQueryque.mic
    val mics = new galoctp{}.GAFIS_MIC_GetDataFromStream(ChannelBuffers.wrappedBuffer(mic))
    gaQuery.pstMIC_Data = mics.toArray

    gaQuery.nMICCount = gaQuery.pstMIC_Data.length

    gaQuery
  }

  /**
    * 更新任务表中对应这条认定的候选信息的候选状态
 *
    * @param oraSid
    * @param taskType
    * @param keyId
    * @param fgp
    * @return
    */
  override def updateCandListStatus(oraSid:String,taskType:Int,keyId:String,tCode:String,fgp:Int): Long = ???

  /**
    * 根据任务号sid获取比对状态 SQL查询方式
    *
    * @param oraSid
    */
  override def getStatusBySidSQL(oraSid: Long): Int = ???
}
