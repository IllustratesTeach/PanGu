package nirvana.hall.v62.services

import nirvana.hall.protocol.api.FPTProto.TPCard.TPCardText
import nirvana.hall.protocol.api.FPTProto.{Case, LPCard, TPCard}

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
    checkNumber(textBuilder.setStrBirthAddrCode, textBuilder.getStrBirthAddrCode, 6)
    checkNumber(textBuilder.setStrNation, textBuilder.getStrNation, 6)
    checkNumber(textBuilder.setStrRace, textBuilder.getStrRace, 2)
    checkNumber(textBuilder.setStrCaseType1, textBuilder.getStrCaseType1, 6)
    checkNumber(textBuilder.setStrCaseType2, textBuilder.getStrCaseType2, 6)
    checkNumber(textBuilder.setStrCaseType3, textBuilder.getStrCaseType3, 6)
    checkNumber(textBuilder.setStrAddrCode, textBuilder.getStrAddrCode, 6)

    //证件类型
    val code = DictCode6Map7.certificatetype.get(textBuilder.getStrCertifType)
    if(code != null && code.nonEmpty){
      textBuilder.setStrCertifType(code.get)
    }else{
      textBuilder.setStrCertifType("")
    }
    if(textBuilder.getStrCertifType.length > 2){
      println(textBuilder.getStrCertifType)
      textBuilder.setStrCertifType("")
    }
  }

  /**
   * 校验案件文字信息
   * @param caseInfo
   */
  def convertCaseInfo6to7(caseInfo: Case): Unit ={
    val textBuilder = caseInfo.getText.toBuilder
    checkNumber(textBuilder.setStrCaseType1, textBuilder.getStrCaseType1, 6)
    checkNumber(textBuilder.setStrCaseType2, textBuilder.getStrCaseType2, 6)
    checkNumber(textBuilder.setStrCaseType3, textBuilder.getStrCaseType3, 6)
    checkNumber(textBuilder.setStrSuspArea1Code, textBuilder.getStrSuspArea1Code, 6)
    checkNumber(textBuilder.setStrSuspArea2Code, textBuilder.getStrSuspArea2Code, 6)
    checkNumber(textBuilder.setStrSuspArea3Code, textBuilder.getStrSuspArea3Code, 6)
    checkNumber(textBuilder.setStrCaseOccurPlaceCode, textBuilder.getStrCaseOccurPlaceCode, 6)
    checkNumber(textBuilder.setStrExtractUnitCode, textBuilder.getStrExtractUnitCode, 12)

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
   * @param setter
   * @param value
   * @param len
   */
  private def checkNumber(setter: String => Any, value: String, len: Int = 0): Unit ={
    if(value != null){
      if(!value.matches("[0-9]+")
        || value.length > len){
        setter("")
      }
    }
  }
}
