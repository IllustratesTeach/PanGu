package nirvana.hall.matcher.internal.adapter.gz

import javax.sql.DataSource

import net.sf.json.JSONObject
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.matcher.HallMatcherConstants
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.adapter.common.GetMatchTaskServiceImpl
import nirvana.hall.matcher.internal.{DateConverter, TextQueryUtil}
import nirvana.protocol.TextQueryProto
import nirvana.protocol.TextQueryProto.TextQueryData
import nirvana.protocol.TextQueryProto.TextQueryData._
import nirvana.hall.matcher.internal.TextQueryConstants._

/**
 * Created by songpeng on 16/7/10.
 */
class GetMatchTaskServiceGzImpl(hallMatcherConfig: HallMatcherConfig, featureExtractor: FeatureExtractor,override implicit val dataSource: DataSource) extends GetMatchTaskServiceImpl(hallMatcherConfig, featureExtractor, dataSource){
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
                                                           "WHERE t.status=" + HallMatcherConstants.QUERY_STATUS_WAIT + " AND t.deletag=1 AND t.ora_sid IS NOT NULL ORDER BY t.prioritynew DESC, t.ora_sid ) tt " +
                                          "WHERE ROWNUM <=?"

  private val personCols: Array[String] = Array[String](COL_NAME_GATHERCATEGORY, COL_NAME_GATHERTYPE, COL_NAME_DOOR, COL_NAME_ADDRESS, COL_NAME_SEXCODE, COL_NAME_DATASOURCES, COL_NAME_CASECLASS)
  private val caseCols: Array[String] = Array[String](COL_NAME_CASECLASSCODE, COL_NAME_CASENATURE, COL_NAME_CASEOCCURPLACECODE, COL_NAME_SUSPICIOUSAREACODE, COL_NAME_ISMURDER, COL_NAME_ISASSIST, COL_NAME_ASSISTLEVEL, COL_NAME_CASESTATE)

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
        }
        //处理其他特殊的查询条件
        if(json.has(PERSON_NAME)){
          val keywordQuery = KeywordQuery.newBuilder()
          keywordQuery.setValue(json.getString(PERSON_NAME) + "*")
          textQuery.addQueryBuilder().setName(COL_NAME_NAME).setExtension(KeywordQuery.query, keywordQuery.build())
        }
        if(json.has(BIRTHDAY_BEG) && json.has(BIRTHDAY_END)){
          val longQuery = LongRangeQuery.newBuilder()
          longQuery.setMin(DateConverter.convertStr2Date(json.getString(BIRTHDAY_BEG), "yyyy-MM-dd").getTime).setMinInclusive(true)
          longQuery.setMax(DateConverter.convertStr2Date(json.getString(BIRTHDAY_END), "yyyy-MM-dd").getTime).setMaxInclusive(true)
          textQuery.addQueryBuilder().setName(COL_NAME_BIRTHDAY).setExtension(LongRangeQuery.query, longQuery.build())
        }
        if(json.has(GATHERDATE_BEG) && json.has(GATHERDATE_END)){
          val longQuery = LongRangeQuery.newBuilder()
          longQuery.setMin(DateConverter.convertStr2Date(json.getString(GATHERDATE_BEG), "yyyy-MM-dd").getTime).setMinInclusive(true)
          longQuery.setMax(DateConverter.convertStr2Date(json.getString(GATHERDATE_END), "yyyy-MM-dd").getTime).setMaxInclusive(true)
          textQuery.addQueryBuilder().setName(COL_NAME_GATHERDATE).setExtension(LongRangeQuery.query, longQuery.build())
        }
        //导入编号
        if(json.has(IMPKEYS)){
          val personIds = json.getString(IMPKEYS).split("\\|")
          val groupQuery = GroupQuery.newBuilder()
          personIds.foreach{personId =>
            groupQuery.addClauseQueryBuilder().setName(COL_NAME_PERSONID).setExtension(KeywordQuery.query,
              KeywordQuery.newBuilder().setValue(personId).build()).setOccur(Occur.SHOULD)
          }
          textQuery.addQueryBuilder().setName("personId").setExtension(GroupQuery.query, groupQuery.build())
        }
        //人员编号区间
        val personidGroupQuery = TextQueryUtil.getPersonidGroupQueryByJSONObject(json)
        if(personidGroupQuery != null){
          textQuery.addQueryBuilder().setName("personid").setExtension(GroupQuery.query, personidGroupQuery)
        }
      }
      catch {
        case e: Exception =>
          error(e.getMessage, e)
      }

    }
    println(textQuery)
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
        if (json.has(CASEOCCURDATE_BEG) && json.has(CASEOCCURDATE_END)) {
          val longQuery = LongRangeQuery.newBuilder
          longQuery.setMin(DateConverter.convertStr2Date(json.getString(CASEOCCURDATE_BEG), "yyyy-MM-dd").getTime).setMinInclusive(true)
          longQuery.setMax(DateConverter.convertStr2Date(json.getString(CASEOCCURDATE_END), "yyyy-MM-dd").getTime).setMaxInclusive(true)
          textQuery.addQueryBuilder.setExtension(LongRangeQuery.query, longQuery.build).setName(COL_NAME_CASEOCCURDATE)
        }
        //案件编号区间
        val caseidGroupQuery = TextQueryUtil.getCaseidGroupQueryByJSONObject(json)
        if (caseidGroupQuery != null) {
          println(caseidGroupQuery)
          textQuery.addQueryBuilder().setName("caseid").setExtension(GroupQuery.query, caseidGroupQuery)
        }
      }
    }

    textQuery.build()
  }

}
