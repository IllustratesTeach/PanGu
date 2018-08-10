package nirvana.hall.matcher.internal.adapter.gafis6

import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import monad.support.services.LoggerSupport
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.matcher.HallMatcherConstants
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.{DataConverter, GafisConverter, TextQueryUtil}
import nirvana.hall.matcher.service.GetMatchTaskService
import nirvana.hall.support.services.JdbcDatabase
import nirvana.protocol.MatchTaskQueryProto.{MatchTask, MatchTaskQueryRequest, MatchTaskQueryResponse}
import nirvana.protocol.NirvanaTypeDefinition.MatchType
import nirvana.protocol.TextQueryProto.TextQueryData
import nirvana.protocol.TextQueryProto.TextQueryData.GroupQuery
import org.jboss.netty.buffer.ChannelBuffers

/**
  * Created by songpeng on 16/4/8.
  */
class GetMatchTaskServiceImpl(hallMatcherConfig: HallMatcherConfig, featureExtractor: FeatureExtractor, implicit val dataSource: DataSource) extends GetMatchTaskService with LoggerSupport{
   /** 获取比对任务  */
  private val MATCH_TASK_QUERY: String = "select t.ora_sid ora_sid, t.keyid, t.querytype, t.maxcandnum, t.minscore, t.priority, t.mic, t.qrycondition, t.flag, t.startkey1, t.endkey1, t.startkey2, t.endkey2 " +
  " from NORMALQUERY_QUERYQUE t where rowid in " +
  " (select rid from (select rowid rid from NORMALQUERY_QUERYQUE t1 where t1.status = 0 and t1.rmtflag in (0,2) order by t1.priority desc, t1.ora_sid) tt where rownum <= ?)"

  /** 获取sid根据卡号（人员编号） */
   private val GET_SID_BY_PERSONID: String = "select t.ora_sid from normaltp_tpcardinfo t where t.cardid=?"
   /** 获取sid根据卡号（现场指纹） */
   private val GET_SID_BY_CASE_FINGERID: String = "select t.ora_sid from normallp_latfinger t where t.fingerid=?"
   private val GET_SID_BY_CASE_PALMID: String = "select t.ora_sid from normallp_latpalm t where t.palmid=?"
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
         if(MatchScheduler.matchingJobs.contains(oraSid)){
           warn("ora_sid {} is matching", oraSid)
         }else{
           val matchTask = readMatchTask(rs)
           //更新status
           updateStatusMatching(oraSid)
           MatchScheduler.matchingJobs + oraSid
           matchTaskQueryResponse.addMatchTask(matchTask)
         }
       }
       catch {
         case e: Exception =>
           updateMatchStatusFail(oraSid, e.getMessage)
           e.printStackTrace()
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
     val isPalm = flag == 2 || flag == 22
     val topN = rs.getInt("maxcandnum")
     matchTaskBuilder.setObjectId(getObjectIdByCardId(keyId, queryType, isPalm))
     matchTaskBuilder.setTopN(if(topN <=0)  50 else topN);//最大候选队列默认50
     matchTaskBuilder.setScoreThreshold(rs.getInt("minscore"))
     matchTaskBuilder.setPriority(rs.getInt("priority"))
     if(isPalm){
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
     }else{
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

     val ldataBuilderMap = scala.collection.mutable.Map[Int, MatchTask.LatentMatchData.Builder]()
     val tdataBuilderMap = scala.collection.mutable.Map[Int, MatchTask.TemplateMatchData.Builder]()
     val mic = rs.getBytes("mic")
     val mics = GafisConverter.GAFIS_MIC_GetDataFromStream(ChannelBuffers.wrappedBuffer(mic))
     mics.foreach{ micStruct =>
       val index = micStruct.nIndex.toInt
       if(micStruct.bIsLatent == 1){
         if(ldataBuilderMap.get(index).isEmpty){
           ldataBuilderMap.put(index, matchTaskBuilder.addLDataBuilder())
         }
         val ldata = ldataBuilderMap(index)
         ldata.setMinutia(ByteString.copyFrom(micStruct.pstMnt_Data))
         if(hallMatcherConfig.mnt.hasRidge && micStruct.pstBin_Data.length > 0)
           ldata.setRidge(ByteString.copyFrom(micStruct.pstBin_Data))
       }else{
         if(tdataBuilderMap.get(index).isEmpty){
           tdataBuilderMap.put(index, matchTaskBuilder.addTDataBuilder())
         }
         val tdata = tdataBuilderMap(index).addMinutiaDataBuilder()
         var nItemData = micStruct.nItemData.toInt
         if(micStruct.nItemType == glocdef.GAMIC_ITEMTYPE_TPLAIN){
           //如果是平面指纹,+10
           nItemData += 10
         }
         val pos = DataConverter.fingerPos6to8(nItemData)//掌纹1，2 使用指纹指位转换没有问题
         var mnt = micStruct.pstMnt_Data
         //TT，TL查询老特征转新特征
         if (hallMatcherConfig.mnt.isNewFeature && !isPalm && (queryType == HallMatcherConstants.QUERY_TYPE_TT || queryType == HallMatcherConstants.QUERY_TYPE_TL)) {
           mnt = featureExtractor.ConvertMntOldToNew(ByteString.copyFrom(mnt).newInput()).get
         }
         tdata.setMinutia(ByteString.copyFrom(mnt)).setPos(pos)
         //纹线数据
         if (hallMatcherConfig.mnt.hasRidge && micStruct.pstBin_Data.length > 0){
           val binData = ByteString.copyFrom(micStruct.pstBin_Data)
           val dataSizeExpected = DataConverter.readGAFISIMAGESTRUCTDataLength(binData) + hallMatcherConfig.mnt.headerSize
           if(binData.size > dataSizeExpected && binData.size - dataSizeExpected < 4){
             tdata.setRidge(binData.substring(0, dataSizeExpected))
           }else{
             tdata.setRidge(binData)
           }
         }
       }
     }
     //条码区间查询
     val startKey1 = rs.getString("startkey1")
     val endKey1 = rs.getString("endkey1")
     val startKey2 = rs.getString("startkey2")
     val endKey2 = rs.getString("endkey2")
     val textQueryDataBuilder = TextQueryData.newBuilder()
     if(startKey1 != null || endKey1 != null || startKey2 != null || endKey2 != null){
       val destDBIsLatent = queryType match {
         case HallMatcherConstants.QUERY_TYPE_TT |
              HallMatcherConstants.QUERY_TYPE_LT =>
           false
         case HallMatcherConstants.QUERY_TYPE_TL |
              HallMatcherConstants.QUERY_TYPE_LL =>
           true
       }
       val groupQuery = getGroupQuery(startKey1, endKey1, startKey2, endKey2, destDBIsLatent)
       textQueryDataBuilder.addQueryBuilder().setName("id").setExtension(GroupQuery.query, groupQuery)
     }

     //文本查询
     val qrycondition = rs.getBytes("qrycondition")
     if(qrycondition != null){
       TextQueryUtil.convertQrycondition2TextQueryData(qrycondition, textQueryDataBuilder)
     }
     queryType match {
       case HallMatcherConstants.QUERY_TYPE_TT =>
         tdataBuilderMap.foreach { f =>
           f._2.setTextQuery(textQueryDataBuilder)
         }
       case HallMatcherConstants.QUERY_TYPE_TL =>
         tdataBuilderMap.foreach { f =>
           f._2.setTextQuery(textQueryDataBuilder)
         }
       case HallMatcherConstants.QUERY_TYPE_LT =>
         ldataBuilderMap.foreach{ f =>
           f._2.setTextQuery(textQueryDataBuilder)
         }
       case HallMatcherConstants.QUERY_TYPE_LL =>
         ldataBuilderMap.foreach{ f=>
           f._2.setTextQuery(textQueryDataBuilder)
         }
     }

     matchTaskBuilder.build()
   }

   private def getObjectIdByCardId(cardId: String, queryType: Int, isPalm: Boolean): Long={
     var sql: String = ""
     if (queryType == HallMatcherConstants.QUERY_TYPE_TT || queryType == HallMatcherConstants.QUERY_TYPE_TL) {
       sql = GET_SID_BY_PERSONID
     } else {
       if(isPalm){
         sql = GET_SID_BY_CASE_PALMID
       }else{
         sql = GET_SID_BY_CASE_FINGERID
       }
     }
     val oraSidOption = JdbcDatabase.queryFirst[Long](sql){ps =>
       ps.setString(1, cardId)
     }{ rs =>
       rs.getInt("ora_sid")
     }
     if(oraSidOption.nonEmpty){
       oraSidOption.get
     }else{
       0
     }
   }

  /**
   * 更新比对状态正在比对
   * @param oraSid
   * @param dataSource
   * @return
   */
  private def updateStatusMatching(oraSid: String)(implicit dataSource: DataSource): Unit ={
    JdbcDatabase.update("update NORMALQUERY_QUERYQUE t set t.status="+HallMatcherConstants.QUERY_STATUS_MATCHING+" where t.ora_sid=?"){ps=>
      ps.setString(1, oraSid)
    }
  }
  /**
   * 更新比对状态比对失败
   * @param match_id
   * @param message 异常信息
   */
  private def updateMatchStatusFail(match_id: String, message: String) {
    val sql: String = "UPDATE NORMALQUERY_QUERYQUE t SET t.status="+HallMatcherConstants.QUERY_STATUS_FAIL+", t.ORACOMMENT=? WHERE t.ora_sid=?"
    JdbcDatabase.update(sql) { ps =>
      ps.setString(1, message)
      ps.setString(2, match_id)
    }
  }

  private def getGroupQuery(startKey1: String, endKey1: String, startKey2: String, endKey2: String, isLatent: Boolean): GroupQuery={
    val groupQuery1 = TextQueryUtil.getGroupQuery(startKey1, endKey1, isLatent)
    val groupQuery2 = TextQueryUtil.getGroupQuery(startKey2, endKey2, isLatent)
    if(groupQuery1 != null || groupQuery2 != null){
      val groupQuery = GroupQuery.newBuilder()
      if(groupQuery1 != null){
        groupQuery.addClauseQueryBuilder().setName("id").setExtension(GroupQuery.query, groupQuery1)
      }
      if(groupQuery2 != null){
        groupQuery.addClauseQueryBuilder().setName("id").setExtension(GroupQuery.query, groupQuery2)
      }

      groupQuery.build()
    }else{
      null
    }
  }

 }
