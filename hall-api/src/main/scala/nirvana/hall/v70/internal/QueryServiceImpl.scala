package nirvana.hall.v70.internal

import java.util.Date
import javax.persistence.EntityManager

import nirvana.hall.api.config.QueryDBConfig
import nirvana.hall.api.internal.DateConverter
import nirvana.hall.api.services.QueryService
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gloclib.gaqryque.{GAQUERYCANDHEADSTRUCT, GAQUERYCANDSTRUCT, GAQUERYSTRUCT}
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchStatus
import nirvana.hall.protocol.fpt.TypeDefinitionProto.MatchType
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult.MatcherStatus
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.v62.internal.c.gloclib.{galoctp, gaqryqueConverter}
import nirvana.hall.v70.internal.query.QueryConstants
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa.{GafisNormalqueryQueryque, SysUser}
import org.jboss.netty.buffer.ChannelBuffers

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/1/26.
 */
class QueryServiceImpl(entityManager: EntityManager) extends QueryService{
  /**
   * 发送查询任务
   * @param matchTask
   * @return
   */
  override def addMatchTask(matchTask: MatchTask, queryDBConfig: QueryDBConfig): Long = {
    val gafisQuery = ProtobufConverter.convertMatchTask2GafisNormalqueryQueryque(matchTask)
    val query = entityManager.createNativeQuery("select SEQ_ORASID.nextval from dual")
    gafisQuery.oraSid = query.getResultList.get(0).toString.toLong
    gafisQuery.pkId = CommonUtils.getUUID()
    gafisQuery.submittsystem = QueryConstants.SUBMIT_SYS_FINGER
    //用户信息，单位信息
    val sysUser = SysUser.find(Gafis70Constants.INPUTPSN)
    gafisQuery.userid = Gafis70Constants.INPUTPSN
    gafisQuery.username = sysUser.trueName
    gafisQuery.userunitcode = sysUser.departCode

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
      Option(matchResult.build())
    }else{
      None
    }
  }

  /**
    * 通过卡号查找第一个的比中结果
    * @param cardId 卡号
    * @return 比对结果
    */
  override def findFirstQueryResultByCardId(cardId: String, dbId: Option[String]): Option[MatchResult] = {
    throw new UnsupportedOperationException
  }

  /**
   * 根据卡号查找第一个比对任务的状态, 如果没有获取到返回UN_KNOWN
   * @param cardId
   * @return
   */
  override def findFirstQueryStatusByCardIdAndMatchType(cardId: String, matchType: MatchType, dbId: Option[String]): MatchStatus = {
    throw new UnsupportedOperationException
  }

  /**
    * 根据卡号信息发送查询, 不需要特征信息
    * @param matchTask 只有查询信息不需要特征信息
    * @param queryDBConfig
    * @return 任务号
    */
  override def sendQuery(matchTask: MatchTask, queryDBConfig: QueryDBConfig): Long = {
    throw new UnsupportedOperationException
  }

  /**
    * 根据任务号sid获取比对状态
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
      val gaCand = new GAQUERYCANDSTRUCT
      while(buffer.readableBytes() >= gaCand.getDataSize) {
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
    }
    //mic
    val mic = gafisNormalqueryQueryque.mic
    val mics = new galoctp{}.GAFIS_MIC_GetDataFromStream(ChannelBuffers.wrappedBuffer(mic))
    gaQuery.pstMIC_Data = mics.toArray

    gaQuery.nCandHeadLen = gaQuery.pstCandHead_Data.getDataSize
    gaQuery.nCandLen = gaQuery.pstCand_Data.length * new GAQUERYCANDSTRUCT().getDataSize
    gaQuery.nMICCount = gaQuery.pstMIC_Data.length

    gaQuery
  }
}
