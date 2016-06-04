package nirvana.hall.matcher.internal.adapter.gz

import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import net.sf.json.JSONObject
import nirvana.hall.matcher.HallMatcherConstants
import nirvana.hall.matcher.internal.{DataConverter, GafisConverter}
import nirvana.hall.matcher.service.GetMatchTaskService
import nirvana.hall.support.services.JdbcDatabase
import nirvana.protocol.MatchTaskQueryProto.MatchTask.MatchConfig
import nirvana.protocol.MatchTaskQueryProto.{MatchTask, MatchTaskQueryRequest, MatchTaskQueryResponse}
import nirvana.protocol.NirvanaTypeDefinition.MatchType
import nirvana.protocol.TextQueryProto.TextQueryData.{GroupQuery, KeywordQuery, Occur}
import org.jboss.netty.buffer.ChannelBuffers

/**
  * Created by songpeng on 16/4/8.
  */
class GetMatchTaskServiceImpl(implicit dataSource: DataSource) extends GetMatchTaskService{
   /** 获取比对任务  */
   private val MATCH_TASK_QUERY: String = "select * from (select t.ora_sid ora_sid, t.keyid, t.querytype, t.maxcandnum, t.minscore, t.priority, t.mic, t.qrycondition, t.textsql, t.flag  from GAFIS_NORMALQUERY_QUERYQUE t where t.status="+HallMatcherConstants.QUERY_STATUS_WAIT+" and t.deletag=1 order by t.prioritynew desc, t.ora_sid ) tt where rownum <=?"
   /** 获取sid根据卡号（人员编号） */
   private val GET_SID_BY_PERSONID: String = "select t.sid as ora_sid from gafis_person t where t.personid=?"
   /** 获取sid根据卡号（现场指纹） */
   private val GET_SID_BY_CASE_FINGERID: String = "select t.sid as ora_sid from gafis_case_finger t where t.finger_id=?"
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
           updateMatchStatusFail(oraSid, e.getMessage)
       }
     }
     matchTaskQueryResponse.build()
   }
   def readMatchTask(rs: ResultSet): MatchTask = {
     val matchTaskBuilder = MatchTask.newBuilder()
     val oraSid = rs.getString("ora_sid")
     matchTaskBuilder.setMatchId(oraSid)
     val keyId = rs.getString("keyid")
     val queryType = rs.getInt("querytype")
     val flag = rs.getInt("flag")
     val textSql = rs.getString("textsql")
     val topN = rs.getInt("maxcandnum")
     //TODO 没有掌纹
     if(flag == 2 || flag == 22){

     }
     matchTaskBuilder.setObjectId(getObjectIdByCardId(keyId, queryType, flag))
     matchTaskBuilder.setTopN(if(topN <=0)  50 else topN);//最大候选队列默认50
     matchTaskBuilder.setScoreThreshold(rs.getInt("minscore"))
     matchTaskBuilder.setPriority(rs.getInt("priority"))
     val mic = rs.getBytes("mic")
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

     val mics = GafisConverter.GAFIS_MIC_GetDataFromStream(ChannelBuffers.wrappedBuffer(mic))
     mics.foreach{ micStruct =>
       if(micStruct.bIsLatent == 1){
         val ldata = matchTaskBuilder.getLDataBuilder
         ldata.setMinutia(ByteString.copyFrom(micStruct.pstMnt_Data))
         if(micStruct.pstBin_Data.length > 0)
           ldata.setRidge(ByteString.copyFrom(micStruct.pstBin_Data))
       }else{
         val pos = DataConverter.fingerPos6to8(micStruct.nItemData)
         matchTaskBuilder.getTDataBuilder.addMinutiaDataBuilder().setMinutia(ByteString.copyFrom(micStruct.pstMnt_Data)).setPos(pos)
       }
     }
     if(textSql != null){
       //文本查询
       val json = JSONObject.fromObject(textSql)
       if(queryType == HallMatcherConstants.QUERY_TYPE_TT || queryType == HallMatcherConstants.QUERY_TYPE_LT){
         val queryBuilder = matchTaskBuilder.getTDataBuilder.getTextQueryBuilder
         //布控和追逃不比1.2亿
         if (json.has("controlPursuitStatus")) {
           val value: String = json.getString("controlPursuitStatus")
           val colQuery = KeywordQuery.newBuilder
           colQuery.setValue(value)
           queryBuilder.addQueryBuilder.setName("controlPursuitStatus").setExtension(KeywordQuery.query, colQuery.build)
           val dataInQuery = GroupQuery.newBuilder
           dataInQuery.addClauseQueryBuilder.setName("dataIn").setExtension(KeywordQuery.query, KeywordQuery.newBuilder.setValue("2").build).setOccur(Occur.MUST_NOT)
         }
       }else if(queryType == HallMatcherConstants.QUERY_TYPE_TL || queryType == HallMatcherConstants.QUERY_TYPE_LL){

       }
       //高级查询
       matchTaskBuilder.setConfig(getMatchConfig(textSql))
     }
     //更新status
     updateStatusMatching(oraSid)

     matchTaskBuilder.build()
   }

   private def getObjectIdByCardId(cardId: String, queryType: Int, flag: Int): Long={
     var sql: String = ""
     if (queryType == HallMatcherConstants.QUERY_TYPE_TT || queryType == HallMatcherConstants.QUERY_TYPE_TL) {
       sql = GET_SID_BY_PERSONID
     } else {
       sql = GET_SID_BY_CASE_FINGERID
     }
     val oraSidOption = JdbcDatabase.queryFirst[Long](sql){ps =>
       ps.setString(1, cardId)
     }{ rs =>
       rs.getInt("ora_sid")
     }
     if(! oraSidOption.isEmpty){
       oraSidOption.get
     }else{
       0
     }
   }

   private def getMatchConfig(textSql:String): MatchConfig ={
     val builder = MatchConfig.newBuilder
     if (textSql != null && textSql.length > 0) {
       try {
         val json: JSONObject = JSONObject.fromObject(textSql)
         if (json.has("minutia")) builder.setMinutia(json.getInt("minutia"))
         if (json.has("distore")) builder.setDistore(json.getInt("distore"))
         if (json.has("loc_structure")) builder.setLocStructure(json.getInt("loc_structure"))
         if (json.has("full_match_on")) builder.setFullMatchOn(json.getInt("full_match_on"))
         if (json.has("mask_enh_feat")) builder.setMaskEnhFeat(json.getInt("mask_enh_feat"))
         if (json.has("morph_accu_use")) builder.setMorphAccuUse(json.getInt("morph_accu_use"))
         if (json.has("scale0")) builder.setScale0(json.getDouble("scale0") / 100.0)
         if (json.has("scale1")) builder.setScale1(json.getDouble("scale1") / 100.0)
       }
       catch {
         case e: Exception => {
           e.printStackTrace()
         }
       }
     }
     return builder.build
   }
  private def updateStatusMatching(oraSid: String)(implicit dataSource: DataSource): Unit ={
    JdbcDatabase.update("update GAFIS_NORMALQUERY_QUERYQUE t set t.status="+HallMatcherConstants.QUERY_STATUS_MATCHING+" where t.ora_sid=?"){ps=>
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
 }
