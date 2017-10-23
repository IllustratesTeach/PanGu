package nirvana.hall.matcher.internal.adapter.nj

import javax.sql.DataSource

import net.sf.json.JSONObject
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.matcher.HallMatcherConstants
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.adapter.common.GetMatchTaskServiceImpl
import nirvana.hall.matcher.internal.{DateConverter, PinyinConverter, TextQueryUtil}
import nirvana.protocol.TextQueryProto
import nirvana.protocol.TextQueryProto.TextQueryData
import nirvana.protocol.TextQueryProto.TextQueryData.{GroupQuery, KeywordQuery, LongRangeQuery, Occur}
import nirvana.hall.matcher.internal.TextQueryConstants._

/**
  * Created by songpeng on 16/4/8.
  */
class GetMatchTaskServiceNjImpl(hallMatcherConfig: HallMatcherConfig, featureExtractor: FeatureExtractor, override implicit val dataSource: DataSource) extends GetMatchTaskServiceImpl(hallMatcherConfig, featureExtractor, dataSource){
  /** 获取比对任务  */
  override val MATCH_TASK_QUERY: String = "SELECT * FROM (SELECT t.ora_sid ora_sid" +
                                                              ", t.keyid" +
                                                              ", t.querytype" +
                                                              ", t.maxcandnum" +
                                                              ", t.minscore" +
                                                              ", t.priority" +
                                                              ", t.mic" +
                                                              ", t.qrycondition" +
                                                              ", t.textsql" +
                                                              ", t.flag  " +
                                                          "FROM GAFIS_NORMALQUERY_QUERYQUE t " +
                                                          "WHERE t.status=" + HallMatcherConstants.QUERY_STATUS_WAIT + " AND t.deletag=1 AND t.sync_target_sid IS NULL AND t.ora_sid IS NOT NULL ORDER BY t.prioritynew DESC, t.ora_sid ) tt " +
                                          "WHERE ROWNUM <=?"

  private val personCols: Array[String] = Array[String](
    COL_NAME_DOOR,COL_NAME_ADDRESS,COL_NAME_ASSISTLEVEL,COL_NAME_NAME,COL_NAME_SPELLNAME,COL_NAME_ALIASNAME,COL_NAME_ALIASSPELL,COL_NAME_IDCARDNO,
    COL_NAME_SEXCODE,COL_NAME_FINGERREPEATNO,COL_NAME_NATIONCODE,COL_NAME_GATHERUSERNAME,COL_NAME_NATIVEPLACECODE)
  private val caseCols: Array[String] = Array[String](
    COL_NAME_CASEOCCURPLACECODE, COL_NAME_CASEOCCURPLACEDETAIL,COL_NAME_SUSPICIOUSAREACODE,COL_NAME_ASSISTLEVEL, COL_NAME_ISMURDER, COL_NAME_CASESTATE,
    COL_NAME_EXTRACTUNITNAME,COL_NAME_EXTRACTUNITCODE)

  /**
   * 获取捺印文本查询条件
   * @param textSql
   * @return
   */
  override def getTextQueryDataOfTemplate(textSql: String): TextQueryProto.TextQueryData ={
    val textQuery = TextQueryData.newBuilder()
    if(textSql != null && textSql.nonEmpty){
      try {
        val json = JSONObject.fromObject(textSql)
        //一般字典条件
        personCols.foreach{col=>
          if(json.has(col)){
            val value = json.getString(col)
            if(value.indexOf("|") > 0){
              val values = value.split("\\|")
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
        //处理其他特殊的查询条件
        //处理classcode，需要同时在classcode1，classcode2，classcode3中查询
        if(json.has("caseClasses")){
          val value = json.getString("caseClasses")
          if(value.indexOf(",")>0){
            val values = value.split("\\,")
            val groupQuery = GroupQuery.newBuilder()
            values.foreach{value =>
              val keywordQuery = KeywordQuery.newBuilder()
              keywordQuery.setValue(value)
              groupQuery.addClauseQueryBuilder().setName("caseClass").setExtension(KeywordQuery.query, keywordQuery.build()).setOccur(Occur.SHOULD)
              groupQuery.addClauseQueryBuilder().setName("caseClass2").setExtension(KeywordQuery.query, keywordQuery.build()).setOccur(Occur.SHOULD)
              groupQuery.addClauseQueryBuilder().setName("caseClass3").setExtension(KeywordQuery.query, keywordQuery.build()).setOccur(Occur.SHOULD)
            }
            textQuery.addQueryBuilder().setName("caseClass").setExtension(GroupQuery.query, groupQuery.build());
          } else {
            val groupQuery = GroupQuery.newBuilder()
            val keywordQuery = KeywordQuery.newBuilder()
            keywordQuery.setValue(value)
            groupQuery.addClauseQueryBuilder().setName("caseClass").setExtension(KeywordQuery.query, keywordQuery.build()).setOccur(Occur.SHOULD)
            groupQuery.addClauseQueryBuilder().setName("caseClass2").setExtension(KeywordQuery.query, keywordQuery.build()).setOccur(Occur.SHOULD)
            groupQuery.addClauseQueryBuilder().setName("caseClass3").setExtension(KeywordQuery.query, keywordQuery.build()).setOccur(Occur.SHOULD)
            textQuery.addQueryBuilder().setName("caseClass").setExtension(GroupQuery.query, groupQuery.build())
          }
        }
        //通配符查询,转为小写
        val wildcardColumn = Array(PERSONID, IDCARDNO)
        wildcardColumn.foreach{col =>
          if (json.has(col)) {
            val keywordQuery = KeywordQuery.newBuilder().setValue(json.getString(col).toLowerCase())
            textQuery.addQueryBuilder().setName(col).setExtension(KeywordQuery.query, keywordQuery.build())
          }
        }
        //姓名,如果是同音字查询根据spellname查询
        if(json.has(PERSON_NAME)){
          if(json.has(IS_HOMONYM) && "1".equals(json.getString(IS_HOMONYM))){
            val keywordQuery = KeywordQuery.newBuilder()
            val spellName = PinyinConverter.convert2Pinyin(json.getString(PERSON_NAME))
            keywordQuery.setValue(spellName)
            textQuery.addQueryBuilder().setName(COL_NAME_SPELLNAME).setExtension(KeywordQuery.query, keywordQuery.build())
          }else{
            val keywordQuery = KeywordQuery.newBuilder()
            keywordQuery.setValue(json.getString(PERSON_NAME) + "*")
            textQuery.addQueryBuilder().setName(COL_NAME_NAME).setExtension(KeywordQuery.query, keywordQuery.build())
          }
        }
        //时间段
        val dateCols = Array("gatherDate")
        dateCols.foreach{col =>
          if (json.has(col + "ST") && json.has(col + "ED")) {
            val longQuery = LongRangeQuery.newBuilder()
            longQuery.setMin(DateConverter.convertStr2Date(json.getString(col + "ST"), "yyyy-MM-dd").getTime).setMinInclusive(true)
            longQuery.setMax(DateConverter.convertStr2Date(json.getString(col + "ED"), "yyyy-MM-dd").getTime).setMaxInclusive(true)
            textQuery.addQueryBuilder().setName(col).setExtension(LongRangeQuery.query, longQuery.build())
          }
        }
        //导入编号
        if(json.has("impKeys")){
          val personIds = json.getString("impKeys").toLowerCase().split("\\|")
          val groupQuery = GroupQuery.newBuilder()
          personIds.foreach{personId =>
            groupQuery.addClauseQueryBuilder().setName(PERSONID).setExtension(KeywordQuery.query,
              KeywordQuery.newBuilder().setValue(personId).build()).setOccur(Occur.SHOULD)
          }
          textQuery.addQueryBuilder().setName(PERSONID).setExtension(GroupQuery.query, groupQuery.build())
        }
        //人员编号区间
        val personidGroupQuery = TextQueryUtil.getPersonidGroupQueryByJSONObject(json)
        if(personidGroupQuery != null){
          textQuery.addQueryBuilder().setName("personid").setExtension(GroupQuery.query, personidGroupQuery)
        }
        //逻辑库
        if(json.has("logicDBValues")){
          val logicDB = json.getString("logicDBValues")
          val values = logicDB.split("\\|")
          val groupQuery = GroupQuery.newBuilder()
          values.foreach{value =>
            val keywordQuery = KeywordQuery.newBuilder()
            keywordQuery.setValue(value)
            groupQuery.addClauseQueryBuilder().setName("logicDB").setExtension(KeywordQuery.query, keywordQuery.build()).setOccur(Occur.SHOULD)
          }
          textQuery.addQueryBuilder().setName("logicDB").setExtension(GroupQuery.query, groupQuery.build())
        }
      }
      catch {
        case e: Exception =>
          error(e.getMessage, e)
      }
    }
    return textQuery.build()
  }

  /**
   * 现场文本检索
   * @param textSql
   * @return
   */
  override def getTextQueryDataOfLatent(textSql: String): TextQueryData ={
    val textQuery = TextQueryData.newBuilder()
    if(textSql != null && textSql.nonEmpty){
      try{
        val json = JSONObject.fromObject(textSql)
        caseCols.foreach{col =>
          if(json.has(col)){
            val value = json.getString(col)
            if(value.indexOf("|") > 0){
              val values = value.split("\\|")
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
        //处理其他特殊的查询条件
        //处理classcode，需要同时在classcode1，classcode2，classcode3中查询
        if(json.has("caseClassCode")){
          val value = json.getString("caseClassCode")
          if(value.indexOf(",")>0){
            val values = value.split("\\,")
            val groupQuery = GroupQuery.newBuilder()
            values.foreach{value =>
              val keywordQuery = KeywordQuery.newBuilder()
              keywordQuery.setValue(value)
              groupQuery.addClauseQueryBuilder().setName("caseClassCode").setExtension(KeywordQuery.query, keywordQuery.build()).setOccur(Occur.SHOULD)
              groupQuery.addClauseQueryBuilder().setName("caseClassCode2").setExtension(KeywordQuery.query, keywordQuery.build()).setOccur(Occur.SHOULD)
              groupQuery.addClauseQueryBuilder().setName("caseClassCode3").setExtension(KeywordQuery.query, keywordQuery.build()).setOccur(Occur.SHOULD)
            }
            textQuery.addQueryBuilder().setName("caseClassCode").setExtension(GroupQuery.query, groupQuery.build());
          } else {
            val groupQuery = GroupQuery.newBuilder()
            val keywordQuery = KeywordQuery.newBuilder()
            keywordQuery.setValue(value)
            groupQuery.addClauseQueryBuilder().setName("caseClassCode").setExtension(KeywordQuery.query, keywordQuery.build()).setOccur(Occur.SHOULD)
            groupQuery.addClauseQueryBuilder().setName("caseClassCode2").setExtension(KeywordQuery.query, keywordQuery.build()).setOccur(Occur.SHOULD)
            groupQuery.addClauseQueryBuilder().setName("caseClassCode3").setExtension(KeywordQuery.query, keywordQuery.build()).setOccur(Occur.SHOULD)
            textQuery.addQueryBuilder().setName("caseClassCode").setExtension(GroupQuery.query, groupQuery.build())
          }
        }
        //时间段
        val dateCols = Array("caseOccurDate", "extractDateST")
        dateCols.foreach{col =>
          if (json.has(col + "ST") && json.has(col + "ED")) {
            val longQuery = LongRangeQuery.newBuilder()
            longQuery.setMin(DateConverter.convertStr2Date(json.getString(col + "ST"), "yyyy-MM-dd").getTime).setMinInclusive(true)
            longQuery.setMax(DateConverter.convertStr2Date(json.getString(col + "ED"), "yyyy-MM-dd").getTime).setMaxInclusive(true)
            textQuery.addQueryBuilder().setName(col).setExtension(LongRangeQuery.query, longQuery.build())
          }
        }
        //案件编号区间
        val caseidGroupQuery = TextQueryUtil.getCaseidGroupQueryByJSONObject(json)
        if (caseidGroupQuery != null) {
          textQuery.addQueryBuilder().setName("caseid").setExtension(GroupQuery.query, caseidGroupQuery)
        }
        //导入编号
        if(json.has("impKeys")){
          val cardIds = json.getString("impKeys").toLowerCase().split("\\|")
          val groupQuery = GroupQuery.newBuilder()
          cardIds.foreach{caseId =>
            groupQuery.addClauseQueryBuilder().setName(COL_NAME_CARDID).setExtension(KeywordQuery.query,
              KeywordQuery.newBuilder().setValue(caseId).build()).setOccur(Occur.SHOULD)
          }
          textQuery.addQueryBuilder().setName(COL_NAME_CARDID).setExtension(GroupQuery.query, groupQuery.build())
        }
        //逻辑库
        if(json.has("logicDBValues")){
          val logicDB = json.getString("logicDBValues")
          val values = logicDB.split("\\|")
          val groupQuery = GroupQuery.newBuilder()
          values.foreach{value =>
            val keywordQuery = KeywordQuery.newBuilder()
            keywordQuery.setValue(value)
            groupQuery.addClauseQueryBuilder().setName("logicDB").setExtension(KeywordQuery.query, keywordQuery.build()).setOccur(Occur.SHOULD)
          }
          textQuery.addQueryBuilder().setName("logicDB").setExtension(GroupQuery.query, groupQuery.build())
        }
      }catch {
        case e: Exception =>
          error(e.getMessage, e)
      }
    }

    textQuery.build()
  }
}
