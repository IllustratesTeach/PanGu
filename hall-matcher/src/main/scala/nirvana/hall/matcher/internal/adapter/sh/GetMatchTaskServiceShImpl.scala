package nirvana.hall.matcher.internal.adapter.sh

import javax.sql.DataSource

import net.sf.json.JSONObject
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.matcher.HallMatcherConstants
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.TextQueryConstants._
import nirvana.hall.matcher.internal.adapter.common.GetMatchTaskServiceImpl
import nirvana.hall.matcher.internal.{DateConverter, TextQueryUtil}
import nirvana.protocol.TextQueryProto
import nirvana.protocol.TextQueryProto.TextQueryData
import nirvana.protocol.TextQueryProto.TextQueryData.{GroupQuery, KeywordQuery, LongRangeQuery, Occur}

/**
  * Created by songpeng on 16/4/8.
  */
class GetMatchTaskServiceShImpl(hallMatcherConfig: HallMatcherConfig, featureExtractor: FeatureExtractor,override implicit val dataSource: DataSource) extends GetMatchTaskServiceImpl(hallMatcherConfig, featureExtractor, dataSource){
  /** 获取比对任务  */
  override val MATCH_TASK_QUERY: String = s" SELECT * FROM (SELECT " +
                                                              s"t.ora_sid ora_sid" +
                                                              s", t.keyid" +
                                                              s", t.querytype" +
                                                              s", t.maxcandnum" +
                                                              s", t.minscore" +
                                                              s", t.priority" +
                                                              s", t.mic" +
                                                              s", t.qrycondition" +
                                                              s", t.textsql" +
                                                              s", t.flag" +
                                                        s"  FROM GAFIS_NORMALQUERY_QUERYQUE t" +
                                                        s"  WHERE t.submittsystem <> 3" +
                                                              s" AND t.status=0" +
                                                              s" AND t.deletag=1" +
                                                              s" AND t.sync_target_sid IS NULL AND t.ora_sid IS NOT NULL ORDER BY t.prioritynew DESC, t.ora_sid ) tt" +
                                        s"  WHERE ROWNUM <=?"

  private val personCols: Array[String] = Array[String](COL_NAME_GATHERCATEGORY,COL_NAME_GATHERTYPE,COL_NAME_GATHERTYPE,COL_NAME_DOOR, COL_NAME_ADDRESS, COL_NAME_SEXCODE,COL_NAME_DATASOURCES,COL_NAME_CASECLASS,
    COL_NAME_PERSONTYPE, COL_NAME_NATIONCODE, COL_NAME_RECORDMARK, COL_NAME_GATHERORGCODE, COL_NAME_NATIVEPLACECODE,COL_NAME_FOREIGNNAME, COL_NAME_ASSISTLEVEL, COL_NAME_ASSISTREFPERSON,COL_NAME_ASSISTREFCASE,
    COL_NAME_GATHERDEPARTNAME, COL_NAME_GATHERUSERNAME, COL_NAME_CONTRCAPTURECODE,COL_NAME_CERTIFICATETYPE,COL_NAME_CERTIFICATEID,COL_NAME_PROCESSNO,COL_NAME_PSISNO,COL_NAME_SPELLNAME,COL_NAME_USEDNAME,
    COL_NAME_USEDSPELL, COL_NAME_ALIASNAME,COL_NAME_ALIASSPELL,COL_NAME_BIRTHCODE,COL_NAME_BIRTHSTREET,COL_NAME_BIRTHDETAIL, COL_NAME_DOORSTREET, COL_NAME_DOORDETAIL, COL_NAME_ADDRESSSTREET, COL_NAME_ADDRESSDETAIL,
    COL_NAME_CULTURECODE, COL_NAME_FAITHCODE,COL_NAME_HAVEEMPLOYMENT,COL_NAME_JOBCODE,COL_NAME_OTHERSPECIALTY, COL_NAME_SPECIALIDENTITYCODE, COL_NAME_SPECIALGROUPCODE, COL_NAME_GATHERERID, COL_NAME_FINGERREPEATNO,
    COL_NAME_INPUTPSN, COL_NAME_MODIFIEDPSN, COL_NAME_PERSONCATEGORY, COL_NAME_GATHERFINGERMODE, COL_NAME_CASENAME, COL_NAME_REASON, COL_NAME_GATHERDEPARTCODE,COL_NAME_GATHERUSERID,COL_NAME_CARDID,COL_NAME_ISXJSSMZ)
  private val caseCols: Array[String] = Array[String](COL_NAME_CASECLASSCODE, COL_NAME_CASENATURE, COL_NAME_CASEOCCURPLACECODE, COL_NAME_SUSPICIOUSAREACODE,COL_NAME_ISMURDER
    , COL_NAME_ISASSIST
    , COL_NAME_ASSISTLEVEL,COL_NAME_CASESTATE, COL_NAME_ISCHECKED,COL_NAME_LTSTATUS,COL_NAME_CASESOURCE
    , COL_NAME_CASEOCCURPLACEDETAIL, COL_NAME_EXTRACTOR, COL_NAME_EXTRACTUNITCODE,COL_NAME_EXTRACTUNITNAME
    , COL_NAME_BROKENSTATUS,COL_NAME_CREATORUNITCODE,COL_NAME_UPDATORUNITCODE,COL_NAME_INPUTPSN, COL_NAME_MODIFIEDPSN)

  /**
   * 获取捺印文本查询条件
    *
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
        //模糊字段
        val fuzzyColumn = Array(PERSON_NAME,IDCARDNO)
        fuzzyColumn.foreach{col =>
          if (json.has(col)) {
            val keywordQuery = KeywordQuery.newBuilder()
            keywordQuery.setValue(json.getString(col) + "*")
            textQuery.addQueryBuilder().setName(col).setExtension(KeywordQuery.query, keywordQuery.build())
          }
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

        if(json.has(INPUTTIME_BEG) && json.has(INPUTTIME_END)){
          val longQuery = LongRangeQuery.newBuilder()
          longQuery.setMin(DateConverter.convertStr2Date(json.getString(INPUTTIME_BEG), "yyyy-MM-dd").getTime).setMinInclusive(true)
          longQuery.setMax(DateConverter.convertStr2Date(json.getString(INPUTTIME_END), "yyyy-MM-dd").getTime).setMaxInclusive(true)
          textQuery.addQueryBuilder().setName(COL_NAME_INPUTTIME).setExtension(LongRangeQuery.query, longQuery.build())
        }

        if(json.has(MODIFIEDTIME_BEG) && json.has(MODIFIEDTIME_END)){
          val longQuery = LongRangeQuery.newBuilder()
          longQuery.setMin(DateConverter.convertStr2Date(json.getString(MODIFIEDTIME_BEG), "yyyy-MM-dd").getTime).setMinInclusive(true)
          longQuery.setMax(DateConverter.convertStr2Date(json.getString(MODIFIEDTIME_END), "yyyy-MM-dd").getTime).setMaxInclusive(true)
          textQuery.addQueryBuilder().setName(COL_NAME_MODIFIEDTIME).setExtension(LongRangeQuery.query, longQuery.build())
        }

        //导入编号
        if(json.has(IMPKEYS)){
          val personIds = json.getString(IMPKEYS).split("\\|")
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
        //逻辑库
        if(json.has("logicDBValues")){
          val logicDB = json.getString("logicDBValues")
          val values = logicDB.split("\\|")
          val groupQuery = GroupQuery.newBuilder()
          values.foreach{value =>
            val keywordQuery = KeywordQuery.newBuilder()
            keywordQuery.setValue(value)
            groupQuery.addClauseQueryBuilder().setName(COL_NAME_LOGICDB).setExtension(KeywordQuery.query, keywordQuery.build()).setOccur(Occur.SHOULD)
          }
          textQuery.addQueryBuilder().setName(COL_NAME_LOGICDB).setExtension(GroupQuery.query, groupQuery.build())
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
    *
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
        if (json.has(EXTRACTDATE_BEG) && json.has(EXTRACTDATE_END)) {
          val longQuery = LongRangeQuery.newBuilder()
          longQuery.setMin(DateConverter.convertStr2Date(json.getString(EXTRACTDATE_BEG), "yyyy-MM-dd").getTime).setMinInclusive(true)
          longQuery.setMax(DateConverter.convertStr2Date(json.getString(EXTRACTDATE_END), "yyyy-MM-dd").getTime).setMaxInclusive(true)
          textQuery.addQueryBuilder().setName(COL_NAME_EXTRACTDATE).setExtension(LongRangeQuery.query, longQuery.build())
        }

        if(json.has(INPUTTIME_BEG) && json.has(INPUTTIME_END)){
          val longQuery = LongRangeQuery.newBuilder()
          longQuery.setMin(DateConverter.convertStr2Date(json.getString(INPUTTIME_BEG), "yyyy-MM-dd").getTime).setMinInclusive(true)
          longQuery.setMax(DateConverter.convertStr2Date(json.getString(INPUTTIME_END), "yyyy-MM-dd").getTime).setMaxInclusive(true)
          textQuery.addQueryBuilder().setName(COL_NAME_INPUTTIME).setExtension(LongRangeQuery.query, longQuery.build())
        }

        if(json.has(MODIFIEDTIME_BEG) && json.has(MODIFIEDTIME_END)){
          val longQuery = LongRangeQuery.newBuilder()
          longQuery.setMin(DateConverter.convertStr2Date(json.getString(MODIFIEDTIME_BEG), "yyyy-MM-dd").getTime).setMinInclusive(true)
          longQuery.setMax(DateConverter.convertStr2Date(json.getString(MODIFIEDTIME_END), "yyyy-MM-dd").getTime).setMaxInclusive(true)
          textQuery.addQueryBuilder().setName(COL_NAME_MODIFIEDTIME).setExtension(LongRangeQuery.query, longQuery.build())
        }
        //发案时间
        if (json.has(CASEOCCURDATE_BEG) && json.has(CASEOCCURDATE_END)) {
          val longQuery = LongRangeQuery.newBuilder
          longQuery.setMin(DateConverter.convertStr2Date(json.getString(CASEOCCURDATE_BEG), "yyyy-MM-dd").getTime).setMinInclusive(true)
          longQuery.setMax(DateConverter.convertStr2Date(json.getString(CASEOCCURDATE_END), "yyyy-MM-dd").getTime).setMaxInclusive(true)
          textQuery.addQueryBuilder.setName(COL_NAME_CASEOCCURDATE).setExtension(LongRangeQuery.query, longQuery.build)
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
            groupQuery.addClauseQueryBuilder().setName(COL_NAME_LOGICDB).setExtension(KeywordQuery.query, keywordQuery.build()).setOccur(Occur.SHOULD)
          }
          textQuery.addQueryBuilder().setName(COL_NAME_LOGICDB).setExtension(GroupQuery.query, groupQuery.build())
        }
      }catch {
        case e: Exception =>
          error(e.getMessage, e)
      }
    }

    textQuery.build()
  }
}
