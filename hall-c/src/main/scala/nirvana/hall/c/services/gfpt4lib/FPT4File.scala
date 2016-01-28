package nirvana.hall.c.services.gfpt4lib

import nirvana.hall.c.annotations.{IgnoreTransfer, LengthRef, Length}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-27
 */
class FPT4File {
}

class Logic01Rec{
  @Length(12)
  var fileLength: String = _
  @Length(1)
  var dataType:String = "1"
  @Length(6)
  var tpCount: String =  _
  @Length(6)
  var lpCount: String = _

  @Length(6)
  var tl_ltCount: String = _
  @Length(6)
  var ttCount: String =  _
  @Length(6)
  var llCount: String =  _
  @Length(6)
  var lpRequestCount: String =  _
  @Length(6)
  var tpRequestCount: String =  _
  @Length(6)
  var ltCandidateCount: String =  _
  @Length(6)
  var tlCandidateCount: String =  _
  @Length(6)
  var ttCandidateCount: String =  _
  @Length(6)
  var llCandidateCount: String =  _
  @Length(6)
  var customCandidateCount: String =  _



  @Length(14)
  var sendTime: String = _
  @Length(12)
  var receiveUnitCode: String = _
  @Length(12)
  var sendUnitCode: String = _
  @Length(70)
  var sendUnitName: String = _
  @Length(30)
  var sender: String = _
  /**
   * 发送单位系统类型
   * 1900 东方金指
   * 1300 北大高科
   * 1700 北京海鑫
   * 1800 小日本NEC
   * 1200 北京邮电大学
   * 1100 北京刑科所
   */
  @Length(4)
  var sendUnitSystemType: String = _
  @Length(10)
  var sid: String = _
  @Length(512)
  var remark: String = _
  var fs:Byte = FPTFile.FS //FS
}

class Logic02RecHead {
  @Length(6)
  var index: String = _
  @Length(4)
  var systemType: String = _
  @Length(23)
  var personId: String = _
  @Length(23)
  var cardId: String = _
  @Length(30)
  var personName: String = _
  @Length(30)
  var alias: String = _
  @Length(1)
  var gender: String = _
  @Length(8)
  var birthday: String = _
  @Length(3)
  var nativeplace: String = _
  @Length(2)
  var nation: String = _
  @Length(18)
  var idCardNo: String = _
  @Length(3)
  var certificateType: String = _
  @Length(20)
  var certificateNo: String = _
  @Length(6)
  var door: String = _
  @Length(70)
  var doorDetail: String = _
  @Length(6)
  var address: String = _
  @Length(70)
  var addressDetail: String = _
  @Length(2)
  var category: String = _
  @Length(6)
  var caseClass1Code: String = _
  @Length(6)
  var caseClass2Code: String = _
  @Length(6)
  var caseClass3Code: String = _
  @Length(1)
  var isCriminal: String = _
  @Length(1024)
  var criminalInfo: String = _
  @Length(12)
  var gatherUnitCode: String = _
  @Length(70)
  var gatherUnitName: String = _
  @Length(30)
  var gatherName: String = _
  @Length(8)
  var gatherDate: String = _
  @Length(1)
  var assistLevel: String = _
  @Length(6)
  var bonus: String = _
  @Length(5)
  var assistPurpose: String = _
  @Length(23)
  var relatedPersonId: String = _
  @Length(23)
  var relatedCaseId: String = _
  @Length(1)
  var assistTimeLimit: String = _
  @Length(512)
  var assistAskingInfo: String = _
  @Length(12)
  var assistUnitCode: String = _
  @Length(70)
  var assistUnitName: String = _
  @Length(8)
  var assistDate: String = _
  @Length(30)
  var contact: String = _
  @Length(40)
  var contactPhone: String = _
  @Length(30)
  var approver: String = _
  @Length(512)
  var remark: String = _
  @Length(1)
  var isAssist: String = _
  @Length(6)
  var portraitDataLength: String = _
  @LengthRef("portraitDataLength")
  var portraitData: Array[Byte] = _
  @Length(2)
  var sendFingerCount: String = _
  @LengthRef("sendFingerCount")
  var fingers:Array[FingerTData] = _
  @IgnoreTransfer
  @IgnoreTransfer
  var logicEnd: Byte = FPTFile.FS // FS
}
class FingerTData{
  @Length(7)
  var dataLength :String = _
  @Length(2)
  var sendNo :String = _
  @Length(2)
  var fgp :String = _
  @Length(1)
  var extractMethod :String = _
  @Length(1)
  var pattern1 :String = _
  @Length(1)
  var pattern2 :String = _
  @Length(5)
  var fingerDirection :String = _
  @Length(14)
  var centerPoint :String = _
  @Length(14)
  var subCenterPoint :String = _
  @Length(14)
  var leftTriangle :String = _
  @Length(14)
  var rightTriangle :String = _
  @Length(3)
  var featureCount :String = _
  @Length(1800)
  var feature :String = _
  @Length(6)
  var customInfoLength :String = _
  @LengthRef("customInfoLength")
  var customInfo:Array[Byte]= _
  @Length(3)
  var imgHorizontalLength :String = _
  @Length(3)
  var imgVerticalLength :String = _
  @Length(3)
  var dpi :String = _
  @Length(4)
  var imgCompressMethod :String = _
  @Length(6)
  var imgDataLength :String = _
  @LengthRef("imgDataLength")
  var imgData:String = _
  @Length(1)
  var end :Byte= FPTFile.GS
}

