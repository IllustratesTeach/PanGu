package nirvana.hall.matcher.internal

import com.google.protobuf.ByteString
import monad.support.services.LoggerSupport
import net.sf.json.JSONObject
import nirvana.protocol.TextQueryProto.TextData.{ColData, ColType}
import nirvana.protocol.TextQueryProto.TextQueryData._

import nirvana.hall.matcher.internal.TextQueryConstants._

import scala.collection.mutable.ArrayBuffer

/**
  * Created by songpeng on 2017/3/5.
  * 文本查询工具类
  */
object TextQueryUtil extends LoggerSupport{

  def getColDataByPersonid(personid: String): Seq[ColData] ={
    getColDataById(personid, COL_NAME_PID_PRE, COL_NAME_PID_DEPT, COL_NAME_PID_DATE)
  }
  def getColDataByCaseid(caseid: String): Seq[ColData] ={
    getColDataById(caseid, COL_NAME_CID_PRE, COL_NAME_CID_DEPT, COL_NAME_CID_DATE)
  }

  def getPersonidGroupQuery(personidBeg: String, personidEnd: String): GroupQuery={
    getGroupQuery(personidBeg, personidEnd, false)
  }
  def getCaseidGroupQuery(caseidBeg: String, caseidEnd: String): GroupQuery={
    getGroupQuery(caseidBeg, caseidEnd, true)
  }
  def getPersonidGroupQueryByJSONObject(json: JSONObject): GroupQuery={
    getCardidGroupQueryByJSONObject(json, false)
  }
  def getCaseidGroupQueryByJSONObject(json: JSONObject): GroupQuery={
    getCardidGroupQueryByJSONObject(json, true)
  }

  /**
    * 根据人员编号或者案件编号的key值从json取值，两个区间，使用GroupQuery包裹
    * @param json
    * @return
    */
  def getCardidGroupQueryByJSONObject(json: JSONObject, isLatent: Boolean): GroupQuery={
    var begKey1 = PERSONID_BEG1
    var endKey1 = TextQueryConstants.PERSONID_END1
    var begKey2 = TextQueryConstants.PERSONID_BEG2
    var endKey2 = TextQueryConstants.PERSONID_END2
    var occurKey1 = TextQueryConstants.PERSONID_OCCUR1
    var occurKey2 = TextQueryConstants.PERSONID_OCCUR2
    if(isLatent){
      begKey1 = TextQueryConstants.CASEID_BEG1
      endKey1 = TextQueryConstants.CASEID_END1
      begKey2 = TextQueryConstants.CASEID_BEG2
      endKey2 = TextQueryConstants.CASEID_END2
      occurKey1 = TextQueryConstants.CASEID_OCCUR1
      occurKey2 = TextQueryConstants.CASEID_OCCUR2
    }
    val groupQuery1 = getGroupQueryByJSONObject(json, begKey1, endKey1, isLatent)
    val groupQuery2 = getGroupQueryByJSONObject(json, begKey2, endKey2, isLatent)
    //两个区间，需要放到一个组查询里, 默认occur=should
    if(groupQuery1 != null || groupQuery2 != null){
      val groupQuery = GroupQuery.newBuilder()
      if(groupQuery1 != null){
        var occur = Occur.SHOULD
        if(json.has(occurKey1)){
          val pidOccur1 = json.getString(occurKey1)
          if(TextQueryConstants.OCCUR_MUST_NOT.equals(pidOccur1)){
            occur = Occur.MUST_NOT
            //由于MUST_NOT不能单独使用，这里添加一个全集
            groupQuery.addClauseQueryBuilder().setName(COL_NAME_PID_DEPT).setExtension(LongRangeQuery.query, LongRangeQuery.newBuilder().setMin(0).build())
          }
        }
        groupQuery.addClauseQueryBuilder().setName("id").setExtension(GroupQuery.query, groupQuery1).setOccur(occur)
      }
      if(groupQuery2 != null){
        var occur = Occur.SHOULD
        if(json.has(occurKey2)){
          val pidOccur2 = json.getString(occurKey2)
          if(TextQueryConstants.OCCUR_MUST_NOT.equals(pidOccur2)){
            occur = Occur.MUST_NOT
            //由于MUST_NOT不能单独使用，这里添加一个全集
            groupQuery.addClauseQueryBuilder().setName(COL_NAME_PID_DEPT).setExtension(LongRangeQuery.query, LongRangeQuery.newBuilder().setMin(0).build())
          }
        }
        groupQuery.addClauseQueryBuilder().setName("id").setExtension(GroupQuery.query, groupQuery2).setOccur(occur)
      }

      groupQuery.build()
    }else{
      null
    }
  }

  /**
    * 根据人员编号或者案件编号的key值从json取值，一个区间，使用GroupQuery包裹
    * @param json
    * @param begKey
    * @param endKey
    * @param isLatent true=现场
    * @return
    */
  def getGroupQueryByJSONObject(json: JSONObject, begKey: String, endKey: String, isLatent: Boolean): GroupQuery={
    if(json.has(begKey) || json.has(endKey)){
      var beg = ""
      var end = ""
      if(json.has(begKey)){
        beg = json.getString(begKey)
      }
      if(json.has(endKey)){
        end = json.getString(endKey)
      }
      if(beg.nonEmpty || end.nonEmpty){//如果有人员编号区间
        if(isLatent){
          return getCaseidGroupQuery(beg, end)
        }else{
          return getPersonidGroupQuery(beg, end)
        }
      }
    }
    null
  }

  /**
    * 人员编号或案件编号拆分为3部分
    * 1，前缀  KeyWord
    * 2，12位单位代码 36进制的Long
    * 3，10位日期 36进制的Long
    * @param cardid  案件编号
    * @param preColName 前缀COL_NAME
    * @param deptColName 单位COL_NAME
    * @param dateColName 日期COL_NAME
    * @return
    */
  def getColDataById(cardid: String, preColName: String, deptColName: String, dateColName: String): Seq[ColData] ={
    val colDataArr = new ArrayBuffer[ColData]()
    var id = cardid
    try{
      if (id.matches("^[a-zA-Z]\\w*")) {
        val id_ = splitCardidByPre(id)
        val colData = ColData.newBuilder()
        colData.setColName(preColName).setColType(ColType.KEYWORD).setColValue(ByteString.copyFrom(id_._1.getBytes()))
        colDataArr += colData.build()
        id = id_._2
      }
      val len = id.length
      if(len >= 12){
        val id_dept = java.lang.Long.parseLong(id.substring(0, 12), 36)
        colDataArr += ColData.newBuilder().setColName(deptColName).setColType(ColType.LONG).setColValue(ByteString.copyFrom(DataConverter.long2Bytes(id_dept))).build()
        //长度不足22位之后补0
        for (i <- len until 22){
          id += "0"
        }
        val id_date = java.lang.Long.parseLong(id.substring(12), 36)
        colDataArr += ColData.newBuilder().setColName(dateColName).setColType(ColType.LONG).setColValue(ByteString.copyFrom(DataConverter.long2Bytes(id_date))).build()
      }else{
        for (i <- len until 12){
          id += "0"
        }
        val id_dept = java.lang.Long.parseLong(id.substring(0, 12), 36)
        colDataArr += ColData.newBuilder().setColName(deptColName).setColType(ColType.LONG).setColValue(ByteString.copyFrom(DataConverter.long2Bytes(id_dept))).build()
      }
    } catch {
      case e: Exception =>
        error("illegal cardid:{}", id)
    }

    colDataArr
  }

  /**
    * 人员编号和案件编号区间查询
    * 由于编号分两部分dept,data, 每一部分如果长度不够都需要补0，然后转为Long
    * @param cardidBeg
    * @param cardidEnd
    * @param isLatent true:现场
    * @return
    */
  def getGroupQuery(cardidBeg: String, cardidEnd: String, isLatent: Boolean): GroupQuery={
    var preColName = COL_NAME_PID_PRE
    var deptColName = COL_NAME_PID_DEPT
    var dateColName = COL_NAME_PID_DATE
    if(isLatent){
      preColName = COL_NAME_CID_PRE
      deptColName = COL_NAME_CID_DEPT
      dateColName = COL_NAME_CID_DATE
    }

    val groupQuery = GroupQuery.newBuilder()
    //前缀
    var idBeg = cardidBeg
    var idEnd = cardidEnd
    if (idBeg.matches("^[a-zA-Z]\\w*")) {
      val id_ = splitCardidByPre(idBeg)
      val keywordQuery = KeywordQuery.newBuilder().setValue(id_._1)
      groupQuery.addClauseQueryBuilder().setName(preColName).setExtension(KeywordQuery.query, keywordQuery.build())
      idBeg = id_._2
    }
    if (idEnd.matches("^[a-zA-Z]\\w*")) {
      val id_ = splitCardidByPre(idEnd)
      val keywordQuery = KeywordQuery.newBuilder().setValue(id_._1)
      groupQuery.addClauseQueryBuilder().setName(preColName).setExtension(KeywordQuery.query, keywordQuery.build())
      idEnd = id_._2
    }
    /*
    人员编号一分为二，dept, date A1=deptBeg A2=dateBeg B1=deptEnd B2=dateEnd
    or 代表should, and 代表must
    程序逻辑可能跟一下描述不太一样，但是结果一样
    A1=B1          dept:[A1] and date:[A2,B2]
    A1             dept:[A1,_)
    A1 A2          dept:(A1,_) or (dept:A1 and date:[A2,_))
    A1 A2 B1       dept:(A1, B1] or (dept:A1 and date:[A2,_))
    A1 A2 B1 B2    dept:(A1, B1) or (dept:A1 and date:[A2,_) or (dept:B1 and date:(_,B2])
    A1 B1          dept:[A1, B1]
    A1 B1 B2       dept:[A1, B1) or (dept: B1 and date:(_,B2])
    B1             dept:(_, B1]
    B1 B2          dept:(_, B1) or (dept:B1 and date:(_,B2])
     */
    val beg = getLong36ValueById(idBeg)
    val end = getLong36ValueById(idEnd)

    val deptBeg = beg._1
    val dateBeg = beg._2
    val deptEnd = end._1
    val dateEnd = end._2

    if(deptBeg == deptEnd){//如果部门编号相同，对部门编号使用LongQuery，对日期使用LongRangeQuery
      groupQuery.addClauseQueryBuilder().setName(deptColName).setExtension(LongQuery.query,
        LongQuery.newBuilder().setValue(deptBeg).build())

      groupQuery.addClauseQueryBuilder().setName(dateColName).setExtension(LongRangeQuery.query,
        LongRangeQuery.newBuilder().setMin(dateBeg).setMinInclusive(true).setMax(dateEnd).setMaxInclusive(true).build())
    }else{
      //由于deptBeg默认为0,所有这里只判断deptEnd
      if(deptEnd > 0){
        groupQuery.addClauseQueryBuilder().setName(deptColName).setExtension(LongRangeQuery.query,
          LongRangeQuery.newBuilder().setMin(deptBeg).setMinInclusive(dateBeg == 0).setMax(deptEnd).setMaxInclusive(false).build()).setOccur(Occur.SHOULD)
      }else{
        groupQuery.addClauseQueryBuilder().setName(deptColName).setExtension(LongRangeQuery.query,
          LongRangeQuery.newBuilder().setMin(deptBeg).setMinInclusive(dateBeg == 0).build()).setOccur(Occur.SHOULD)
      }
      //日期判断
      if(dateBeg > 0){
        val groupQuery2 = GroupQuery.newBuilder()
        groupQuery2.addClauseQueryBuilder().setName(deptColName).setExtension(LongQuery.query,
          LongQuery.newBuilder().setValue(deptBeg).build())
        groupQuery2.addClauseQueryBuilder().setName(dateColName).setExtension(LongRangeQuery.query,
          LongRangeQuery.newBuilder().setMin(dateBeg).setMinInclusive(true).build())

        groupQuery.addClauseQueryBuilder.setName("id").setExtension(GroupQuery.query, groupQuery2.build()).setOccur(Occur.SHOULD)
      }
      if(dateEnd > 0){
        val groupQuery2 = GroupQuery.newBuilder()
        groupQuery2.addClauseQueryBuilder().setName(deptColName).setExtension(LongQuery.query,
          LongQuery.newBuilder().setValue(deptEnd).build())
        groupQuery2.addClauseQueryBuilder().setName(dateColName).setExtension(LongRangeQuery.query,
          LongRangeQuery.newBuilder().setMax(dateEnd).setMaxInclusive(true).build())

        groupQuery.addClauseQueryBuilder.setName("id").setExtension(GroupQuery.query, groupQuery2.build()).setOccur(Occur.SHOULD)
      }
    }

    groupQuery.build()
  }

  /**
    * 将人员编号或案件编号拆分为两部分（dept: 12位，date: 10位  位数不够不够补0）,并转为Long36
    * @param id
    * @return
    */
  private def getLong36ValueById(id: String):(Long, Long) = {
    var dept: Long = 0
    var date: Long = 0
    if(id.length >= 12){
      dept = java.lang.Long.parseLong(id.substring(0, 12), 36)

      var dateStr = id.substring(12)
      for(i <- dateStr.length until 10){
        dateStr += "0"
      }
      date = java.lang.Long.parseLong(dateStr, 36)

      (dept, date)
    }else{
      var deptStr = id
      for(i <- id.length until 12){
        deptStr += "0"
      }
      dept = java.lang.Long.parseLong(deptStr, 36)

      (dept, 0)
    }
  }

  /**
    * 将人员编号的前缀拆分开
    * 人员编号前缀,一般都是一个字母开头，贵州多为R,P开头
    * 青岛社会人员库： 捺印卡号以BA，JLRY，XCRY开头
    * @param id
    * @return (前缀，无前缀编号)
    */
  private def splitCardidByPre(id: String): (String, String) = {
    var idPre = ""
    if(id.toUpperCase.matches("^(BA)\\w*")){
      idPre = id.substring(0, 2)
      (idPre, id.substring(2))
    }else if(id.toUpperCase().matches("^(JLRY)|(XCRY)\\w*")){
      idPre = id.substring(0, 4)
      (idPre, id.substring(4))
    }else
    if (id.matches("^[a-zA-Z]\\w*")) {
      idPre = id.substring(0, 1)
      (idPre, id.substring(1))
    }else{
      (idPre, id)
    }
  }

}
