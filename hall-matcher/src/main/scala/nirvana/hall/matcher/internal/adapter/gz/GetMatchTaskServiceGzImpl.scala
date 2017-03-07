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

/**
 * Created by songpeng on 16/7/10.
 */
class GetMatchTaskServiceGzImpl(hallMatcherConfig: HallMatcherConfig, featureExtractor: FeatureExtractor,override implicit val dataSource: DataSource) extends GetMatchTaskServiceImpl(hallMatcherConfig, featureExtractor, dataSource){
  override val MATCH_TASK_QUERY: String = "select * from (select t.ora_sid ora_sid, t.keyid, t.querytype, t.maxcandnum, t.minscore, t.priority, t.mic, t.qrycondition, t.textsql, t.flag  from GAFIS_NORMALQUERY_QUERYQUE t where t.status=" + HallMatcherConstants.QUERY_STATUS_WAIT + " and t.deletag=1 order by t.prioritynew desc, t.ora_sid ) tt where rownum <=?"

  private val personCols: Array[String] = Array[String]("gatherCategory", "gatherType", "door", "address", "sexCode", "dataSources", "caseClass")
  private val caseCols: Array[String] = Array[String]("caseClassCode", "caseNature", "caseOccurPlaceCode", "suspiciousAreaCode", "isMurder", "isAssist", "assistLevel", "caseState")

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
        if(json.has("name")){
          val keywordQuery = KeywordQuery.newBuilder()
          keywordQuery.setValue(json.getString("name") + "*")
          textQuery.addQueryBuilder().setName("name").setExtension(KeywordQuery.query, keywordQuery.build())
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
        if (json.has("caseOccurDateBeg") && json.has("caseOccurDateBeg")) {
          val longQuery = LongRangeQuery.newBuilder
          longQuery.setMin(DateConverter.convertStr2Date(json.getString("caseOccurDateBeg"), "yyyy-MM-dd").getTime).setMinInclusive(true)
          longQuery.setMax(DateConverter.convertStr2Date(json.getString("caseOccurDateEnd"), "yyyy-MM-dd").getTime).setMaxInclusive(true)
          textQuery.addQueryBuilder.setExtension(LongRangeQuery.query, longQuery.build).setName("caseOccurDate")
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
