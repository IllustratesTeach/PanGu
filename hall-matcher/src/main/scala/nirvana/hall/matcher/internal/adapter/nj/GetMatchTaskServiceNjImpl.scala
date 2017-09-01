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

  private val personCols: Array[String] = Array[String](PERSONID, IDCARDNO, PERSON_NAME, "gatherCategory", "gatherTypeId", "door", "address", "sexCode", "dataSources", "caseClasses",
    "personType", "nationCode", "recordmark", "gatherOrgCode", "nativeplaceCode", "foreignName", "assistLevel", "assistRefPerson", "assistRefCase",
    "gatherdepartname", "gatherusername", "contrcaptureCode", "certificatetype", "certificateid", "processNo", "psisNo", "usedname",
    "usedspell", "aliasname", "aliasspell", "birthCode", "birthStreet", "birthdetail", "doorStreet", "doordetail", "addressStreet", "addressdetail",
    "cultureCode", "faithCode", "haveemployment", "jobCode", "otherspecialty", "specialidentityCode", "specialgroupCode", "gathererId", "fingerrepeatno",
    "inputpsn", "modifiedpsn", "personCategory", "gatherFingerMode", "caseName", "reason", "gatherdepartcode", "gatheruserid", "cardid", "isXjssmz")
  private val caseCols: Array[String] = Array[String](CASEID, "caseClassCode", "caseNature", "caseOccurPlaceCode", "suspiciousAreaCode", "isMurder", "isAssist", "assistLevel", "caseState", "isChecked", "ltStatus", "caseSource", "caseOccurPlaceDetail", "extractor", "extractUnitCode", "extractUnitName", "brokenStatus", "creatorUnitCode", "updatorUnitCode", "inputpsn", "modifiedpsn")

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
        val dateCols = Array("birthday", "gatherDate", "inputtime", "modifiedtime")
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
        //时间段
        val dateCols = Array("extractDate", "inputtime", "modifiedtime")
        dateCols.foreach{col =>
          if (json.has(col + "ST") && json.has(col + "ED")) {
            val longQuery = LongRangeQuery.newBuilder()
            longQuery.setMin(DateConverter.convertStr2Date(json.getString(col + "ST"), "yyyy-MM-dd").getTime).setMinInclusive(true)
            longQuery.setMax(DateConverter.convertStr2Date(json.getString(col + "ED"), "yyyy-MM-dd").getTime).setMaxInclusive(true)
            textQuery.addQueryBuilder().setName(col).setExtension(LongRangeQuery.query, longQuery.build())
          }
        }
        //发案时间
        if (json.has("caseOccurDateBeg") && json.has("caseOccurDateEnd")) {
          val longQuery = LongRangeQuery.newBuilder
          longQuery.setMin(DateConverter.convertStr2Date(json.getString("caseOccurDateBeg"), "yyyy-MM-dd").getTime).setMinInclusive(true)
          longQuery.setMax(DateConverter.convertStr2Date(json.getString("caseOccurDateEnd"), "yyyy-MM-dd").getTime).setMaxInclusive(true)
          textQuery.addQueryBuilder.setName("caseOccurDate").setExtension(LongRangeQuery.query, longQuery.build)
        }
        //案件编号
        if (json.has(CASEID)) {
          val keywordQuery = KeywordQuery.newBuilder().setValue(json.getString(CASEID).toLowerCase())
          textQuery.addQueryBuilder().setName(CASEID).setExtension(KeywordQuery.query, keywordQuery.build())
        }
        //案件编号区间
        val caseidGroupQuery = TextQueryUtil.getCaseidGroupQueryByJSONObject(json)
        if (caseidGroupQuery != null) {
          textQuery.addQueryBuilder().setName("caseid").setExtension(GroupQuery.query, caseidGroupQuery)
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
