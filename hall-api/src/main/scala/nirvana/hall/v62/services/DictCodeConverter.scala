package nirvana.hall.v62.services

import nirvana.hall.protocol.api.FPTProto.Case.CaseText
import nirvana.hall.protocol.api.FPTProto.LPCard.LPCardText
import nirvana.hall.protocol.api.FPTProto.TPCard
import nirvana.hall.protocol.api.FPTProto.TPCard.TPCardText

import scala.collection.Map

/**
 * Created by songpeng on 16/6/23.
 */
object DictCodeConverter {

  /**
   * 校验字典数据，并将gafis6.2的字典转为7.0的字典,
   * 如果字典不能匹配写到备注里
   * @param textBuilder
   */
  def convertTPCardText6to7(textBuilder: TPCardText.Builder): Unit ={
    //校验字典是不是数字，长度不大于标准长度
    checkNormalCodeOfTPCardTextBuilder(textBuilder.setStrBirthAddrCode, textBuilder.getStrBirthAddrCode, 6,
      "出生地code", textBuilder)
    checkNormalCodeOfTPCardTextBuilder(textBuilder.setStrNation, textBuilder.getStrNation, 6,
      "国籍code", textBuilder)
    checkNormalCodeOfTPCardTextBuilder(textBuilder.setStrRace, textBuilder.getStrRace, 2,
      "民族code", textBuilder)
    checkNormalCodeOfTPCardTextBuilder(textBuilder.setStrCaseType1, textBuilder.getStrCaseType1, 6,
      "案件类别1code", textBuilder)
    checkNormalCodeOfTPCardTextBuilder(textBuilder.setStrCaseType2, textBuilder.getStrCaseType2, 6,
      "案件类别2code", textBuilder)
    checkNormalCodeOfTPCardTextBuilder(textBuilder.setStrCaseType3, textBuilder.getStrCaseType3, 6,
      "案件类别3code", textBuilder)
    checkNormalCodeOfTPCardTextBuilder(textBuilder.setStrAddrCode, textBuilder.getStrAddrCode, 6,
      "住址code", textBuilder)
    checkNormalCodeOfTPCardTextBuilder(textBuilder.setStrHuKouPlaceCode, textBuilder.getStrHuKouPlaceCode, 6,
      "出生地code", textBuilder)

    //证件类型
    checkNormalCodeOfTPCardTextBuilder(textBuilder.setStrCertifType, textBuilder.getStrCertifType, 3,
      "证件类型", textBuilder)
    //人员类型
    convertCode(textBuilder.getStrPersonType, DictCode6Map7.personType, textBuilder.setStrPersonType,
      "人员类型", textBuilder.getStrComment, textBuilder.setStrComment)

    //出生日期
    checkNormalDateOfTPCardTextBuilder(textBuilder.setStrBirthDate, textBuilder.getStrBirthDate, 8,
      "出生日期", textBuilder)
    //采集日期
    checkNormalDateOfTPCardTextBuilder(textBuilder.setStrPrintDate, textBuilder.getStrPrintDate, 8,
      "采集日期", textBuilder)
  }

  /**
   * 校验案件文字信息
   * @param textBuilder
   */
  def convertCaseInfoText6to7(textBuilder: CaseText.Builder): Unit ={
    checkNormalCodeOfCaseTextBuilder(textBuilder.setStrCaseType1, textBuilder.getStrCaseType1, 6,
      "案件类别1", textBuilder)
    checkNormalCodeOfCaseTextBuilder(textBuilder.setStrCaseType2, textBuilder.getStrCaseType2, 6,
      "案件类别2", textBuilder)
    checkNormalCodeOfCaseTextBuilder(textBuilder.setStrCaseType3, textBuilder.getStrCaseType3, 6,
      "案件类别3", textBuilder)
    checkNormalCodeOfCaseTextBuilder(textBuilder.setStrSuspArea1Code, textBuilder.getStrSuspArea1Code, 6,
      "可疑地区1", textBuilder)
    checkNormalCodeOfCaseTextBuilder(textBuilder.setStrSuspArea2Code, textBuilder.getStrSuspArea2Code, 6,
      "可疑地区2", textBuilder)
    checkNormalCodeOfCaseTextBuilder(textBuilder.setStrSuspArea3Code, textBuilder.getStrSuspArea3Code, 6,
      "可疑地区3", textBuilder)
    checkNormalCodeOfCaseTextBuilder(textBuilder.setStrCaseOccurPlaceCode, textBuilder.getStrCaseOccurPlaceCode, 6,
      "案发地", textBuilder)
    checkNormalCodeOfCaseTextBuilder(textBuilder.setStrExtractUnitCode, textBuilder.getStrExtractUnitCode, 12,
      "提取单位", textBuilder)
    checkNormalCodeOfCaseTextBuilder(textBuilder.setStrPremium, textBuilder.getStrPremium, 7,
      "奖金", textBuilder)
    checkNormalCodeOfCaseTextBuilder(textBuilder.setStrXieChaRequestUnitCode, textBuilder.getStrXieChaRequestUnitCode, 12,
      "协查单位", textBuilder)

  }

  /**
   * 校验现场文字信息
   * @param textBuilder
   */
  def convertLPCardText6to7(textBuilder: LPCardText.Builder): Unit ={
    checkNormalCodeOfLPCardTextBuilder(textBuilder.setStrRidgeColor, textBuilder.getStrRidgeColor, 1,
      "乳突线颜色", textBuilder)
    checkNormalCodeOfLPCardTextBuilder(textBuilder.setStrCaptureMethod, textBuilder.getStrCaptureMethod, 2,
      "提取方式", textBuilder)
    //seqNo
    checkNormalCode(textBuilder.setStrSeq, textBuilder.getStrSeq, 2)

  }

  /**
   * 校验字典数据，并将gafis6.2的字典转为7.0的字典,
   * @param tPCard
   */
  def convertTPCard7to6(tPCard: TPCard): Unit ={
    val textBuilder = tPCard.getText.toBuilder
    //证件类型
    val code = DictCode7Map6.certificatetype.get(textBuilder.getStrCertifType)
    if(code != null && code.nonEmpty){
      textBuilder.setStrCertifType(code.get)
    }else{
      textBuilder.setStrCertifType("")
    }
  }

  /**
   * 验证value是否是数字并且长度不大于len，否则setter赋值为空字符并记录到备注信息(tag:code)
   * 用于一般数字格式的字典，只需要验证格式和长度即可，不做字典转换
   * @param setter
   * @param code
   * @param len
   */
  private def checkNormalCode(setter: String => Any, code: String, len: Int, tag: String, comment:String, commentSetter: String => Any): Unit ={
    if(code != null && code.length > 0){
      if(!code.matches("[0-9]+")
        || code.length > len){
        setter("")
        commentSetter(comment +s"(${tag}:${code})")
      }
    }
  }
  private def checkNormalCode(setter: String => Any, code: String, len: Int): Unit ={
    if(code != null && code.length > 0){
      if(!code.matches("[0-9]+")
        || code.length > len){
        setter("")
      }
    }
  }

  /**
    * 校验8位字符串格式的时间是否满足长度和年月日的时间格式
    * @param setter 赋值方法
    * @param date 字符串时间
    * @param len date字符串的长度
    */
  private def checkNormalDate(setter: String => Any, date: String, len: Int, tag: String, comment:String, commentSetter: String => Any): Unit ={
    if(date != null && date.length > 0){
      if(!date.matches("^(?:(?:(?:(?:(?:1[6-9]|[2-9]\\d)(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:0229))|(?:(?:(?:1[6-9]|[2-9]\\d)\\d{2})(?:(?:(?:0[13578]|1[02])31)|(?:(?:0[13-9]|1[0-2])(?:29|30))|(?:(?:0[1-9])|(?:1[0-2]))(?:0[1-9]|1\\d|2[0-8]))))$")
        || date.length != len){
        setter("")
        commentSetter(comment +s"(${tag}:${date})")
      }
    }
  }

  private def checkNormalCodeOfTPCardTextBuilder(setter: String => Any, code: String, len: Int, tag: String, textBuilder: TPCardText.Builder): Unit ={
    checkNormalCode(setter, code, len, tag, textBuilder.getStrComment, textBuilder.setStrComment)
  }
  private def checkNormalCodeOfLPCardTextBuilder(setter: String => Any, code: String, len: Int, tag: String, textBuilder: LPCardText.Builder): Unit ={
    checkNormalCode(setter, code, len, tag, textBuilder.getStrComment, textBuilder.setStrComment)
  }
  private def checkNormalCodeOfCaseTextBuilder(setter: String => Any, code: String, len: Int, tag: String, textBuilder: CaseText.Builder): Unit ={
    checkNormalCode(setter, code, len, tag, textBuilder.getStrComment, textBuilder.setStrComment)
  }
  private def checkNormalDateOfTPCardTextBuilder(setter: String => Any, date: String, len: Int, tag: String, textBuilder: TPCardText.Builder): Unit ={
    checkNormalDate(setter, date, len, tag, textBuilder.getStrComment, textBuilder.setStrComment)
  }

  /**
   * 字典转换
   * 如果不能匹配则将原来的值设置为空, 将字典写入备注
   * @param setter
   * @param code
   * @param mapper
   * @param tag 标签, 格式(tag:code)
   * @param comment 备注
   * @param commentSetter 备注setter
   */
  private def convertCode(code: String, mapper: Map[String,String], setter: String => Any,tag: String, comment:String, commentSetter: String => Any): Unit ={
    if(code != null && code.nonEmpty && code.trim.length > 0){
      val codeNew = mapper.get(code)
      if(codeNew.isEmpty){
        setter("")
        commentSetter(comment +s"(${tag}:${code})")
      }else{
        setter(codeNew.get)
      }
    }
  }
}
