package nirvana.hall.matcher.internal

import java.nio.ByteBuffer
import java.util.{Calendar, Date}

import com.google.protobuf.ByteString
import net.sf.json.JSONObject
import nirvana.hall.matcher.HallMatcherConstants
import nirvana.protocol.MatchTaskQueryProto.MatchTask.MatchConfig
import nirvana.protocol.TextQueryProto.TextQueryData.{LongRangeQuery, LongQuery, KeywordQuery, GroupQuery}

/**
 * Created by songpeng on 16/3/21.
 */
object DataConverter {

  def int2Bytes(num: Int): Array[Byte] = {
    return ByteBuffer.allocate(4).putInt(num).array()
  }

  def long2Bytes(num: Long): Array[Byte] = {
    ByteBuffer.allocate(8).putLong(num).array();
  }
  def short2Bytes(num: Short): Array[Byte] = {
    ByteBuffer.allocate(2).putShort(num).array();
  }
  /**
   * 将int转为byte数组
   * @param num
   * @param len [1-4]
   * @return
   */
  def int2Bytes(num: Int, len: Int): Array[Byte] = {
    val result: Array[Byte] = new Array[Byte](len)
    len match {
      case 1 =>
        result(0) = (num & 0xFF).toByte
      case 2 =>
        result(0) = (num >> 8 & 0xFF).toByte
        result(1) = (num & 0xFF).toByte
      case 3 =>
        result(0) = (num >> 16 & 0xFF).toByte
        result(1) = (num >> 8 & 0xFF).toByte
        result(2) = (num & 0xFF).toByte
      case 4 =>
        result(0) = (num >> 24 & 0xFF).toByte
        result(1) = (num >> 16 & 0xFF).toByte
        result(2) = (num >> 8 & 0xFF).toByte
        result(3) = (num & 0xFF).toByte
      case _ =>
        if (len > 4) {
          result(len - 4) = (num >> 24 & 0xFF).toByte
          result(len - 3) = (num >> 16 & 0xFF).toByte
          result(len - 2) = (num >> 8 & 0xFF).toByte
          result(len - 1) = (num & 0xFF).toByte
        }
    }
    return result
  }

  /**
   * 获取指位6.2转8.0,如:右手拇指是 1 << 0 ,左手小指是 1 << 9
   * 平指11-20
   * @param fgp
   */
  def fingerPos6to8(fgp: Int): Int = {
    return 1 << (fgp - 1)
  }

  /**
   * 获取指位8.0转6.2
   * @param fgp
   */
  def fingerPos8to6(fgp: Int): Int = {
    return (Math.log(fgp) / Math.log(2) + 1).toInt
  }

  /**
   * 掌纹指纹转换11->1,12->2
   * @param fgp
   * @return
   */
  def palmPos6to8(fgp: Int): Int = {
    if (fgp == 11) return 1
    if (fgp == 12) return 2
    return 1 << (fgp - 1)
  }

  def palmPos8to6(fgp: Int): Int = {
    if (fgp == 1) {
      return 11
    }
    else if (fgp == 2) {
      return 12
    }
    return (Math.log(fgp) / Math.log(2) + 1).toInt
  }

  def readGAFISIMAGESTRUCTDataLength(data: ByteString): Int = {
    if (data.size > HallMatcherConstants.HEADER_LENGTH) {
      val bytes: Array[Byte] = new Array[Byte](4)
      data.copyTo(bytes, 12, 0, 4)
      return ByteBuffer.wrap(bytes).getInt
    }
    return 0
  }

  /**
   * afis日期转换
   * @param date
   * @return
   */
  def getAFISDateTime (date: Date): Array[Byte] = {
    val c: Calendar = Calendar.getInstance
    c.setTime(date)
    val result: Array[Byte] = new Array[Byte](8)
    val ss: Array[Byte] = short2Bytes((c.get(Calendar.SECOND) * 1000).toShort)
    result(0) = ss(1)
    result(1) = ss(0)
    result(2) = c.get(Calendar.MINUTE).toByte
    result(3) = c.get(Calendar.HOUR_OF_DAY).toByte
    result(4) = c.get(Calendar.DAY_OF_MONTH).toByte
    result(5) = c.get(Calendar.MONTH).toByte
    val yy: Array[Byte] = short2Bytes(c.get(Calendar.YEAR).toShort)
    result(6) = yy(1)
    result(7) = yy(0)
    return result
  }

  /**
   * 高级查询条件
   * @param textSql
   * @return
   */
  def getMatchConfig(textSql:String): MatchConfig ={
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
          println("getMatchConfig error msg:" + e.getMessage)
        }
      }
    }
    return builder.build
  }
  /**
   * 人员编号区间查询,用于多个区间的模糊查询
   * @param personId_
   * @return
   */
  def getPersonIdGroupQuery(personId_ : String): GroupQuery={
    val groupQuery = GroupQuery.newBuilder()
    if(personId_ != null && personId_.nonEmpty){
      //正则表达式校验
      var personId = personId_.replace("*", "")//替换*
      if (!personId.matches("^[a-zA-Z0-9]*$")) {
        return groupQuery.build
      }
      //如果人员编号是完整的精确查询
      if (personId.length >= 23) {
        groupQuery.addClauseQueryBuilder.setName("personId").setExtension(KeywordQuery.query, KeywordQuery.newBuilder.setValue(personId).build)
      }
      if (personId.matches("^[a-zA-Z]\\w*")) {
        val pId_pre: String = personId.substring(0, 1)
        groupQuery.addClauseQueryBuilder.setName("pId_pre").setExtension(KeywordQuery.query, KeywordQuery.newBuilder.setValue(pId_pre).build)
        personId = personId.substring(1)
      }
      val len = personId.length

      if (len >= 12) {
        val pId_deptCode: String = personId.substring(0, 12)
        groupQuery.addClauseQueryBuilder.setName("pId_deptCode").setExtension(LongQuery.query,
          LongQuery.newBuilder.setValue(java.lang.Long.parseLong(pId_deptCode, 36)).build)
        if (len >= 18) {
          val pId_date: String = personId.substring(12, 18)
          groupQuery.addClauseQueryBuilder.setName("pId_date").setExtension(LongQuery.query,
            LongQuery.newBuilder.setValue(java.lang.Long.parseLong(pId_date, 36)).build)
          if (len > 18) {
            var pid_serialnum_beg: String = personId.substring(18)
            var pid_serialnum_end: String = pid_serialnum_beg
            for(i <- len until 22){
              pid_serialnum_beg += "0"
              pid_serialnum_end += "z"
            }
            groupQuery.addClauseQueryBuilder.setName("pId_serialNum").setExtension(LongRangeQuery.query,
              LongRangeQuery.newBuilder.setMin(java.lang.Long.parseLong(pid_serialnum_beg, 36)).setMinInclusive(true)
                .setMax(java.lang.Long.parseLong(pid_serialnum_end, 36)).setMaxInclusive(true).build)
          }
        }
        else {
          var pid_date_beg: String = personId.substring(12)
          var pid_date_end: String = pid_date_beg
          for(i <- len until 18){
            pid_date_beg += "0"
            pid_date_end += "z"
          }
          groupQuery.addClauseQueryBuilder.setName("pId_date").setExtension(LongRangeQuery.query,
            LongRangeQuery.newBuilder.setMin(java.lang.Long.parseLong(pid_date_beg, 36)).setMinInclusive(true)
              .setMax(java.lang.Long.parseLong(pid_date_end, 36)).setMaxInclusive(true).build)
        }
      }
      else {
        var pid_deptcode_beg: String = personId
        var pid_deptcode_end: String = personId
        for(i <- len until 12){
          pid_deptcode_beg += "0"
          pid_deptcode_end += "z"
        }
        groupQuery.addClauseQueryBuilder.setName("pId_deptCode").setExtension(LongRangeQuery.query,
          LongRangeQuery.newBuilder.setMin(java.lang.Long.parseLong(pid_deptcode_beg, 36)).setMinInclusive(true)
            .setMax(java.lang.Long.parseLong(pid_deptcode_end, 36)).setMaxInclusive(true).build)
      }
    }

    groupQuery.build()
  }
  /**
   * 案件编号区间模糊查询
   * @param caseId_
   * @return
   */
  def getCaseIdGroupQuery(caseId_ : String): GroupQuery = {
    val groupQuery = GroupQuery.newBuilder()
    if (caseId_ != null && caseId_.nonEmpty) {
      var caseId = caseId_.replace("*", "")
      if (!caseId.matches("^[a-zA-Z0-9]*$")) {
        return groupQuery.build
      }
      if (caseId.length >= 25) {
        groupQuery.addClauseQueryBuilder.setName("caseId").setExtension(KeywordQuery.query, KeywordQuery.newBuilder.setValue(caseId).build)
      }
      if (caseId.matches("^[a-zA-Z]\\w*")) {
        caseId = caseId.substring(1)
      }
      val len: Int = caseId.length
      if (len >= 12) {
        val cId_deptCode: String = caseId.substring(0, 12)
        groupQuery.addClauseQueryBuilder.setName("cId_deptCode").setExtension(LongQuery.query, LongQuery.newBuilder.setValue(java.lang.Long.parseLong(cId_deptCode, 36)).build)
        if (len >= 18) {
          val cId_date: String = caseId.substring(12, 18)
          groupQuery.addClauseQueryBuilder.setName("cId_date").setExtension(LongQuery.query, LongQuery.newBuilder.setValue(java.lang.Long.parseLong(cId_date, 36)).build)
          if (len > 18) {
            var cid_serialnum_beg: String = caseId.substring(18)
            var cid_serialnum_end: String = cid_serialnum_beg
            for (i <- len until 18) {
              cid_serialnum_beg += "0"
              cid_serialnum_end += "z"
            }
            groupQuery.addClauseQueryBuilder.setName("cId_serialNum").setExtension(LongRangeQuery.query,
              LongRangeQuery.newBuilder.setMin(java.lang.Long.parseLong(cid_serialnum_beg, 36)).setMinInclusive(true)
                .setMax(java.lang.Long.parseLong(cid_serialnum_end, 36)).setMaxInclusive(true).build)
          }
        }
        else {
          var cid_date_beg: String = caseId.substring(12)
          var cid_date_end: String = cid_date_beg
          for (i <- len until 18) {
            cid_date_beg += "0"
            cid_date_end += "z"
          }
          groupQuery.addClauseQueryBuilder.setName("cId_date").setExtension(LongRangeQuery.query,
            LongRangeQuery.newBuilder.setMin(java.lang.Long.parseLong(cid_date_beg, 36)).setMinInclusive(true)
              .setMax(java.lang.Long.parseLong(cid_date_end, 36)).setMaxInclusive(true).build)
        }
      }
      else {
        var cid_deptcode_beg: String = caseId
        var cid_deptcode_end: String = caseId
        for (i <- len until 18) {
          cid_deptcode_beg += "0"
          cid_deptcode_end += "z"
        }
        groupQuery.addClauseQueryBuilder.setName("cId_deptCode").setExtension(LongRangeQuery.query,
          LongRangeQuery.newBuilder.setMin(java.lang.Long.parseLong(cid_deptcode_beg, 36)).setMinInclusive(true)
            .setMax(java.lang.Long.parseLong(cid_deptcode_end, 36)).setMaxInclusive(true).build)
      }
    }

    groupQuery.build()
  }

  /**
   * 人员编号区间查询
   * @param personIdST
   * @param personIdED
   * @param isContain
   * @return
   */
  def getPersonIdRangeGroupQuery(personIdST: String, personIdED: String, isContain: Boolean = true): GroupQuery ={
    val groupQuery = GroupQuery.newBuilder()
    val pid_st = checkPersonId(personIdST)
    val pid_ed = checkPersonId(personIdED)
    if(pid_st.length > 0 || pid_ed.length > 0){
      val pId_deptCodeST = getDepartCodeByPersonId(pid_st)
      val pId_deptCodeED = getDepartCodeByPersonId(pid_ed, 'z')
      groupQuery.addClauseQueryBuilder.setName("pId_deptCode").setExtension(LongRangeQuery.query,
        LongRangeQuery.newBuilder.setMin(java.lang.Long.parseLong(pId_deptCodeST, 36)).setMinInclusive(true)
          .setMax(java.lang.Long.parseLong(pId_deptCodeED, 36)).setMaxInclusive(true).build)
      if(pId_deptCodeST.equals(pId_deptCodeED) && pid_st.length > 12){
        val pid_date_st = getDateByPersonId(pid_st)
        val pid_date_ed = getDateByPersonId(pid_ed)
        groupQuery.addClauseQueryBuilder.setName("pId_date").setExtension(LongRangeQuery.query,
          LongRangeQuery.newBuilder.setMin(java.lang.Long.parseLong(pid_date_st, 36)).setMinInclusive(true)
            .setMax(java.lang.Long.parseLong(pid_date_ed, 36)).setMaxInclusive(true).build)
      }
    }

    groupQuery.build()
  }

  /**
   * 截取人员编号部门部分
   * @param personId
   * @param endFill
   * @return
   */
  private def getDepartCodeByPersonId(personId: String, endFill: Char = '0'): String ={
    if(personId.length >= 12){
      personId.substring(0, 12)
    }else{
      var departCode = personId
      for( i <- personId.length until 12){
        departCode += endFill
      }
      departCode
    }
  }

  private def getDateByPersonId(personId: String, endFill: Char = '0'): String ={
    if(personId.length >= 18){
      personId.substring(12, 18)
    }else {
      var date = personId.substring(12, personId.length)
      for(i <- personId.length until 18){
        date += endFill
      }
      date
    }
  }

  /**
   * 查询人员编号，去除空格，*，前缀字母
   * @param personId
   * @return
   */
  private def checkPersonId(personId: String): String ={
    if(personId == null){
      ""
    }else{
      var personid = personId.trim.replace("*","")//去除空格和*
      if(personid.matches("^[a-za-z]\\w*")){//去除前缀
        personid = personid.substring(1)
      }
      personid
    }
  }
}
