package nirvana.hall.v62.services

import nirvana.hall.protocol.api.FPTProto.TPCard.TPCardText
import nirvana.hall.protocol.api.FPTProto.{Case, LPCard, TPCard}

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
    checkNormalCode(textBuilder.setStrBirthAddrCode, textBuilder.getStrBirthAddrCode, 6)
    checkNormalCode(textBuilder.setStrNation, textBuilder.getStrNation, 6)
    checkNormalCode(textBuilder.setStrRace, textBuilder.getStrRace, 2)
    checkNormalCode(textBuilder.setStrCaseType1, textBuilder.getStrCaseType1, 6)
    checkNormalCode(textBuilder.setStrCaseType2, textBuilder.getStrCaseType2, 6)
    checkNormalCode(textBuilder.setStrCaseType3, textBuilder.getStrCaseType3, 6)
    checkNormalCode(textBuilder.setStrAddrCode, textBuilder.getStrAddrCode, 6)

    //证件类型
    convertCode(textBuilder.getStrCertifType, DictCode6Map7.certificatetype, textBuilder.setStrCertifType,
      "证件类型", textBuilder.getStrComment, textBuilder.setStrComment)
    //人员类型
    convertCode(textBuilder.getStrPersonType, DictCode6Map7.personType, textBuilder.setStrPersonType,
      "人员类型", textBuilder.getStrComment, textBuilder.setStrComment)

  }

  /**
   * 校验案件文字信息
   * @param caseInfo
   */
  def convertCaseInfo6to7(caseInfo: Case): Unit ={
    val textBuilder = caseInfo.getText.toBuilder
    checkNormalCode(textBuilder.setStrCaseType1, textBuilder.getStrCaseType1, 6)
    checkNormalCode(textBuilder.setStrCaseType2, textBuilder.getStrCaseType2, 6)
    checkNormalCode(textBuilder.setStrCaseType3, textBuilder.getStrCaseType3, 6)
    checkNormalCode(textBuilder.setStrSuspArea1Code, textBuilder.getStrSuspArea1Code, 6)
    checkNormalCode(textBuilder.setStrSuspArea2Code, textBuilder.getStrSuspArea2Code, 6)
    checkNormalCode(textBuilder.setStrSuspArea3Code, textBuilder.getStrSuspArea3Code, 6)
    checkNormalCode(textBuilder.setStrCaseOccurPlaceCode, textBuilder.getStrCaseOccurPlaceCode, 6)
    checkNormalCode(textBuilder.setStrExtractUnitCode, textBuilder.getStrExtractUnitCode, 12)

  }
  def convertLPCard6to7(lPCard: LPCard): Unit ={

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
   * 验证value是否是数字并且长度不大于len，否则setter赋值为空字符
   * 用于一般数字格式的字典，只需要验证格式和长度即可，不做字典转换
   * @param setter
   * @param code
   * @param len
   */
  private def checkNormalCode(setter: String => Any, code: String, len: Int = 0): Unit ={
    if(code != null){
      if(!code.matches("[0-9]+")
        || code.length > len){
        setter("")
      }
    }
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
    if(code != null && code.nonEmpty){
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
