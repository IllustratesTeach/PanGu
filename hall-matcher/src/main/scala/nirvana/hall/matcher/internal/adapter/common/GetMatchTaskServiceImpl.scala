package nirvana.hall.matcher.internal.adapter.common

import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import monad.support.services.LoggerSupport
import net.sf.json.JSONObject
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.matcher.HallMatcherConstants
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.{DataConverter, GafisConverter, TextQueryConstants}
import nirvana.hall.matcher.service.GetMatchTaskService
import nirvana.hall.support.services.JdbcDatabase
import nirvana.protocol.MatchTaskQueryProto.{MatchTask, MatchTaskQueryRequest, MatchTaskQueryResponse}
import nirvana.protocol.NirvanaTypeDefinition.MatchType
import nirvana.protocol.TextQueryProto
import nirvana.protocol.TextQueryProto.TextQueryData
import org.jboss.netty.buffer.ChannelBuffers

/**
 * 获取比对任务通用service
  */
abstract class GetMatchTaskServiceImpl(hallMatcherConfig: HallMatcherConfig, featureExtractor: FeatureExtractor, implicit val dataSource: DataSource) extends GetMatchTaskService with LoggerSupport{
  /** 获取比对任务  */
  val MATCH_TASK_QUERY: String
  /** 获取sid根据卡号（人员编号） */
  val GET_SID_BY_PERSONID: String = "select t.sid as ora_sid from gafis_person t where t.personid=?"
//  /** 获取sid根据卡号（现场指纹） */
  val GET_SID_BY_CASE_FINGERID: String = "select t.sid as ora_sid from gafis_case_finger t where t.finger_id=?"
  val GET_SID_BY_CASE_PALMID: String = "select t.sid as ora_sid from gafis_case_palm t where t.palm_id=?"

  /**
   * 获取比对任务
   * @param matchTaskQueryRequest
   * @return
   */
  override def getMatchTask(matchTaskQueryRequest: MatchTaskQueryRequest): MatchTaskQueryResponse = {
    val matchTaskQueryResponse = MatchTaskQueryResponse.newBuilder()
    val size = matchTaskQueryRequest.getSize
    JdbcDatabase.queryWithPsSetter(MATCH_TASK_QUERY) { ps =>
      ps.setInt(1, size)
    } { rs =>
      val oraSid = rs.getString("ora_sid")
      try {
        matchTaskQueryResponse.addMatchTask(readMatchTask(rs))
      }
      catch {
        case e: Exception =>
          error("getMatchTask error msg:" + e.getMessage)
          updateMatchStatusFail(oraSid, e.getMessage)
      }
    }
    matchTaskQueryResponse.build()
  }

  /**
   * 读取比对任务
   * @param rs
   * @return
   */
  def readMatchTask(rs: ResultSet): MatchTask = {
    val matchTaskBuilder = MatchTask.newBuilder()
    val oraSid = rs.getString("ora_sid")
    matchTaskBuilder.setMatchId(oraSid)
    val keyId = rs.getString("keyid")
    val queryType = rs.getInt("querytype")
    val flag = rs.getInt("flag")
    val isPalm = flag == 2 || flag == 22
    val textSql = rs.getString("textsql")
    var topN = rs.getInt("maxcandnum")
    if (topN <= 0) topN = 50 //最大候选队列默认50
    //如果有候选过滤配置, 同时没有屏蔽候选参数,候选+1000
    if(hallMatcherConfig.candKeyFilters != null && hallMatcherConfig.candKeyFilters.exists(_.queryType == queryType)){
      if(textSql != null){
        val jsonObj = JSONObject.fromObject(textSql)
        if(hallMatcherConfig.candKeyFilters != null && hallMatcherConfig.candKeyFilters.exists(_.queryType == queryType)){
          if(!(jsonObj.has(TextQueryConstants.SHIELD_CANDKEYFILTER) && "1".equals(jsonObj.getString(TextQueryConstants.SHIELD_CANDKEYFILTER)))){
            topN += 1000
          }
        }
      }else{
        topN += 1000
      }
    }
    matchTaskBuilder.setObjectId(getObjectIdByCardId(keyId, queryType, isPalm))
    matchTaskBuilder.setTopN(topN)
    matchTaskBuilder.setScoreThreshold(rs.getInt("minscore"))
    matchTaskBuilder.setPriority(rs.getInt("priority"))
    if (isPalm) {
      queryType match {
        case HallMatcherConstants.QUERY_TYPE_TT =>
          matchTaskBuilder.setMatchType(MatchType.PALM_TT)
        case HallMatcherConstants.QUERY_TYPE_TL =>
          matchTaskBuilder.setMatchType(MatchType.PALM_TL)
        case HallMatcherConstants.QUERY_TYPE_LT =>
          matchTaskBuilder.setMatchType(MatchType.PALM_LT)
        case HallMatcherConstants.QUERY_TYPE_LL =>
          matchTaskBuilder.setMatchType(MatchType.PALM_LL)
      }
    } else {
      queryType match {
        case HallMatcherConstants.QUERY_TYPE_TT =>
          matchTaskBuilder.setMatchType(MatchType.FINGER_TT)
        case HallMatcherConstants.QUERY_TYPE_TL =>
          matchTaskBuilder.setMatchType(MatchType.FINGER_TL)
        case HallMatcherConstants.QUERY_TYPE_LT =>
          matchTaskBuilder.setMatchType(MatchType.FINGER_LT)
        case HallMatcherConstants.QUERY_TYPE_LL =>
          matchTaskBuilder.setMatchType(MatchType.FINGER_LL)
      }
    }

    val mic = rs.getBytes("mic")
    val mics = GafisConverter.GAFIS_MIC_GetDataFromStream(ChannelBuffers.wrappedBuffer(mic))
    mics.foreach { micStruct =>
      if (micStruct.bIsLatent == 1) {
        val ldata = matchTaskBuilder.getLDataBuilder
        ldata.setMinutia(ByteString.copyFrom(micStruct.pstMnt_Data))
        if (hallMatcherConfig.mnt.hasRidge && micStruct.pstBin_Data.length > 0){
          val bin = new GAFISIMAGESTRUCT().fromByteArray(micStruct.pstBin_Data)
          ldata.setRidge(ByteString.copyFrom(bin.toByteArray(AncientConstants.GBK_ENCODING)))
        }
      } else {
        val tdata = matchTaskBuilder.getTDataBuilder.addMinutiaDataBuilder()
        val pos = DataConverter.fingerPos6to8(micStruct.nItemData)//掌纹1，2 使用指纹指位转换没有问题
        var mnt = micStruct.pstMnt_Data
        //TT，TL查询老特征转新特征
        if (hallMatcherConfig.mnt.isNewFeature && !isPalm && (queryType == HallMatcherConstants.QUERY_TYPE_TT || queryType == HallMatcherConstants.QUERY_TYPE_TL)) {
          mnt = featureExtractor.ConvertMntOldToNew(ByteString.copyFrom(mnt).newInput()).get
        }
        tdata.setMinutia(ByteString.copyFrom(mnt)).setPos(pos)
        //纹线数据
        if (hallMatcherConfig.mnt.hasRidge && micStruct.pstBin_Data.length > 0){
          val bin = new GAFISIMAGESTRUCT().fromByteArray(micStruct.pstBin_Data)
          tdata.setRidge(ByteString.copyFrom(bin.toByteArray(AncientConstants.GBK_ENCODING)))
        }
      }
    }
    //文本查询
    if (textSql != null) {
      queryType match {
        case HallMatcherConstants.QUERY_TYPE_TT =>
          matchTaskBuilder.getTDataBuilder.setTextQuery(getTextQueryDataOfTemplate(textSql))
        case HallMatcherConstants.QUERY_TYPE_TL =>
          matchTaskBuilder.getTDataBuilder.setTextQuery(getTextQueryDataOfLatent(textSql))
        case HallMatcherConstants.QUERY_TYPE_LT =>
          matchTaskBuilder.getLDataBuilder.setTextQuery(getTextQueryDataOfTemplate(textSql))
        case HallMatcherConstants.QUERY_TYPE_LL =>
          matchTaskBuilder.getLDataBuilder.setTextQuery(getTextQueryDataOfLatent(textSql))
      }
      //高级查询
      matchTaskBuilder.setConfig(DataConverter.getMatchConfig(textSql))
    }
    //更新status
    updateStatusMatching(oraSid)

    matchTaskBuilder.build()
  }

  private def getObjectIdByCardId(cardId: String, queryType: Int, isPalm: Boolean): Long = {
    var sql: String = ""
    if (queryType == HallMatcherConstants.QUERY_TYPE_TT || queryType == HallMatcherConstants.QUERY_TYPE_TL) {
      sql = GET_SID_BY_PERSONID
    } else {
      if (isPalm) {
        sql = GET_SID_BY_CASE_PALMID
      } else {
        sql = GET_SID_BY_CASE_FINGERID
      }
    }
    val oraSidOption = JdbcDatabase.queryFirst[Long](sql) { ps =>
      ps.setString(1, cardId)
    } { rs =>
      rs.getInt("ora_sid")
    }
    if (!oraSidOption.isEmpty) {
      oraSidOption.get
    } else {
      0
    }
  }


  def updateStatusMatching(oraSid: String)(implicit dataSource: DataSource): Unit ={
    JdbcDatabase.update("update GAFIS_NORMALQUERY_QUERYQUE t set t.status="+HallMatcherConstants.QUERY_STATUS_MATCHING+", t.begintime=sysdate where t.ora_sid=?"){ps=>
      ps.setString(1, oraSid)
    }
  }
  def updateMatchStatusFail(match_id: String, message: String) {
    val sql: String = "UPDATE GAFIS_NORMALQUERY_QUERYQUE t SET t.status="+HallMatcherConstants.QUERY_STATUS_FAIL+", t.ORACOMMENT=? WHERE t.ora_sid=?"
    JdbcDatabase.update(sql) { ps =>
      ps.setString(1, message)
      ps.setString(2, match_id)
    }
  }

  /**
   * 获取捺印文本查询条件
   * @param textSql
   * @return
   */
  def getTextQueryDataOfTemplate(textSql: String): TextQueryProto.TextQueryData

  /**
   * 现场文本检索
   * @param textSql
   * @return
   */
  def getTextQueryDataOfLatent(textSql: String): TextQueryData

}
