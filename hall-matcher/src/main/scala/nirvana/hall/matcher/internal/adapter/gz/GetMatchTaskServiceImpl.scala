package nirvana.hall.matcher.internal.adapter.gz

import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import monad.support.services.LoggerSupport
import net.sf.json.JSONObject
import nirvana.hall.matcher.HallMatcherConstants
import nirvana.hall.matcher.internal.{DataConverter, DateConverter, GafisConverter}
import nirvana.hall.matcher.service.GetMatchTaskService
import nirvana.hall.support.services.JdbcDatabase
import nirvana.protocol.MatchTaskQueryProto.{MatchTask, MatchTaskQueryRequest, MatchTaskQueryResponse}
import nirvana.protocol.NirvanaTypeDefinition.MatchType
import nirvana.protocol.TextQueryProto
import nirvana.protocol.TextQueryProto.TextQueryData
import nirvana.protocol.TextQueryProto.TextQueryData._
import org.jboss.netty.buffer.ChannelBuffers

/**
  * Created by songpeng on 16/4/8.
  */
class GetMatchTaskServiceImpl(implicit dataSource: DataSource) extends GetMatchTaskService with LoggerSupport{
  /** 获取比对任务  */
  private val MATCH_TASK_QUERY: String = "select * from (select t.ora_sid ora_sid, t.keyid, t.querytype, t.maxcandnum, t.minscore, t.priority, t.mic, t.qrycondition, t.textsql, t.flag  from GAFIS_NORMALQUERY_QUERYQUE t where t.status=" + HallMatcherConstants.QUERY_STATUS_WAIT + " and t.deletag=1 order by t.prioritynew desc, t.ora_sid ) tt where rownum <=?"
  /** 获取sid根据卡号（人员编号） */
  private val GET_SID_BY_PERSONID: String = "select t.sid as ora_sid from gafis_person t where t.personid=?"
  /** 获取sid根据卡号（现场指纹） */
  private val GET_SID_BY_CASE_FINGERID: String = "select t.sid as ora_sid from gafis_case_finger t where t.finger_id=?"
  private val GET_SID_BY_CASE_PALMID: String = "select t.sid as ora_sid from gafis_case_palm t where t.palm_id=?"

  private val personCols: Array[String] = Array[String]("gatherCategory", "gatherType", "door", "address", "sexCode", "dataSources", "caseClass")
  private val caseCols: Array[String] = Array[String]("caseClassCode", "caseNature", "caseOccurPlaceCode", "suspiciousAreaCode", "isMurder", "isAssist", "assistLevel", "caseState")

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
    val topN = rs.getInt("maxcandnum")
    matchTaskBuilder.setObjectId(getObjectIdByCardId(keyId, queryType, isPalm))
    matchTaskBuilder.setTopN(if (topN <= 0) 50 else topN); //最大候选队列默认50
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
        if (micStruct.pstBin_Data.length > 0)
          ldata.setRidge(ByteString.copyFrom(micStruct.pstBin_Data))
      } else {
        val pos = DataConverter.fingerPos6to8(micStruct.nItemData)
        matchTaskBuilder.getTDataBuilder.addMinutiaDataBuilder().setMinutia(ByteString.copyFrom(micStruct.pstMnt_Data)).setPos(pos)
      }
    }
    //文本查询
    if (textSql != null) {
      if (queryType == HallMatcherConstants.QUERY_TYPE_TT || queryType == HallMatcherConstants.QUERY_TYPE_LT) {
        getTextQueryDataOfTemplate(textSql)
      } else if (queryType == HallMatcherConstants.QUERY_TYPE_TL || queryType == HallMatcherConstants.QUERY_TYPE_LL) {
        getTextQueryDataOfLatent(textSql)
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


  private def updateStatusMatching(oraSid: String)(implicit dataSource: DataSource): Unit ={
    JdbcDatabase.update("update GAFIS_NORMALQUERY_QUERYQUE t set t.status="+HallMatcherConstants.QUERY_STATUS_MATCHING+", t.begintime=sysdate where t.ora_sid=?"){ps=>
      ps.setString(1, oraSid)
    }
  }
  private def updateMatchStatusFail(match_id: String, message: String) {
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
  private def getTextQueryDataOfTemplate(textSql: String): TextQueryProto.TextQueryData ={
    val textQuery = TextQueryData.newBuilder()
    if(textSql != null && textSql.nonEmpty){
      try {
        val json = JSONObject.fromObject(textSql)
        personCols.foreach{col=>
          if(json.has(col)){
            val value = json.getString(col)
            if(value.indexOf(",") > 0){
              val values = value.split(",")
              val groupQuery = GroupQuery.newBuilder()
              values.foreach{value =>
                val keywordQuery = KeywordQuery.newBuilder()
                keywordQuery.setValue(value)
                groupQuery.addClauseQueryBuilder().setName(col).setExtension(KeywordQuery.query, keywordQuery.build()).setOccur(Occur.SHOULD)
              }
              textQuery.addQueryBuilder().setName(col).setExtension(GroupQuery.query, groupQuery.build());
            }else{
              val keywordQuery = KeywordQuery.newBuilder()
              keywordQuery.setValue(value)
              textQuery.addQueryBuilder().setName(col).setExtension(KeywordQuery.query, keywordQuery.build())
            }
          }
          //处理其他特殊的查询条件
          if(json.has("name")){
            val keywordQuery = KeywordQuery.newBuilder()
            keywordQuery.setValue(json.getString("name") + "*")
            textQuery.addQueryBuilder().setName(col).setExtension(KeywordQuery.query, keywordQuery.build())

          }
          if(json.has("birthdayST") && json.has("birthdayED")){
            val longQuery = LongRangeQuery.newBuilder()
            longQuery.setMin(DateConverter.convertStr2Date(json.getString("birthdayST"), "yyyy-MM-dd").getTime).setMinInclusive(true)
            longQuery.setMax(DateConverter.convertStr2Date(json.getString("birthdayED"), "yyyy-MM-dd").getTime).setMaxInclusive(true)
            textQuery.addQueryBuilder().setName("birthday").setExtension(LongRangeQuery.query, longQuery.build())
          }
          if(json.has("gatherDateST") && json.has("gatherDateED")){
            val longQuery = LongRangeQuery.newBuilder()
            longQuery.setMin(DateConverter.convertStr2Date(json.getString("gatherDateST"), "yyyy-MM-dd").getTime).setMinInclusive(true)
            longQuery.setMax(DateConverter.convertStr2Date(json.getString("gatherDateED"), "yyyy-MM-dd").getTime).setMaxInclusive(true)
            textQuery.addQueryBuilder().setName("gatherDate").setExtension(LongRangeQuery.query, longQuery.build())
          }
          //导入编号
          if(json.has("impKeys")){
            val personIds = json.getString("impKeys").split("\\|")
            val groupQuery = GroupQuery.newBuilder()
            personIds.foreach{personId =>
              groupQuery.addClauseQueryBuilder().setName("personId").setExtension(KeywordQuery.query,
                KeywordQuery.newBuilder().setValue(personId).build()).setOccur(Occur.SHOULD)
            }
            textQuery.addQueryBuilder().setName("personId").setExtension(GroupQuery.query, groupQuery.build())
          }
          //人员编号区间
          if(json.has("personIdST1") || json.has("personIdED1")){
            val groupQuery = GroupQuery.newBuilder()
            if (json.has("personIdST1")) {
              groupQuery.addClauseQueryBuilder.setName("personId").setExtension(GroupQuery.query, DataConverter.getPersonIdGroupQuery(json.getString("personIdST1"))).setOccur(Occur.SHOULD)
            }
            if (json.has("personIdED1")) {
              groupQuery.addClauseQueryBuilder.setName("personId").setExtension(GroupQuery.query, DataConverter.getPersonIdGroupQuery(json.getString("personIdED1"))).setOccur(Occur.SHOULD)
            }
            textQuery.addQueryBuilder.setName("personId").setExtension(GroupQuery.query, groupQuery.build)
          }
        }
      }
      catch {
        case e: Exception =>
          e.printStackTrace()
      }

    }
    return textQuery.build()
  }

  /**
   * 现场文本检索
   * @param textSql
   * @return
   */
  private def getTextQueryDataOfLatent(textSql: String): TextQueryData ={
    val textQuery = TextQueryData.newBuilder()
    if(textSql != null && textSql.nonEmpty){
      try{
        val json = JSONObject.fromObject(textSql)
        caseCols.foreach{col =>
          if(json.has(col)){
            val value = json.getString(col)
            if(value.indexOf(",") > 0){
              val values = value.split(",")
              val groupQuery = GroupQuery.newBuilder()
              values.foreach{value =>
                val keywordQuery = KeywordQuery.newBuilder()
                keywordQuery.setValue(value)
                groupQuery.addClauseQueryBuilder().setName(col).setExtension(KeywordQuery.query, keywordQuery.build()).setOccur(Occur.SHOULD)
              }
              textQuery.addQueryBuilder().setName(col).setExtension(GroupQuery.query, groupQuery.build());
            }else{
              val keywordQuery = KeywordQuery.newBuilder()
              keywordQuery.setValue(value)
              textQuery.addQueryBuilder().setName(col).setExtension(KeywordQuery.query, keywordQuery.build())
            }
          }
        }
        if (json.has("caseOccurDateBeg") && json.has("caseOccurDateBeg")) {
          val longQuery = LongRangeQuery.newBuilder
          longQuery.setMin(DateConverter.convertStr2Date(json.getString("caseOccurDateBeg"), "yyyy-MM-dd").getTime).setMinInclusive(true)
          longQuery.setMax(DateConverter.convertStr2Date(json.getString("caseOccurDateEnd"), "yyyy-MM-dd").getTime).setMaxInclusive(true)
          textQuery.addQueryBuilder.setExtension(LongRangeQuery.query, longQuery.build).setName("caseOccurDate")
        }
        //案件编号区间
        if (json.has("caseIdST") || json.has("caseIdED")) {
          val groupQuery = GroupQuery.newBuilder
          if (json.has("caseIdST")) {
            groupQuery.addClauseQueryBuilder.setName("caseId").setExtension(GroupQuery.query, DataConverter.getCaseIdGroupQuery(json.getString("caseIdST"))).setOccur(Occur.SHOULD)
          }
          if (json.has("caseIdED")) {
            groupQuery.addClauseQueryBuilder.setName("caseId").setExtension(GroupQuery.query, DataConverter.getCaseIdGroupQuery(json.getString("caseIdED"))).setOccur(Occur.SHOULD)
          }
          textQuery.addQueryBuilder.setName("caseId").setExtension(GroupQuery.query, groupQuery.build)
        }
      }catch {
        case e: Exception =>
          e.printStackTrace()
      }
    }

    textQuery.build()
  }

}
