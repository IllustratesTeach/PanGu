package nirvana.hall.c.services.gfpt5lib

import java.util

import monad.support.services.XmlLoader
import org.apache.commons.io.IOUtils
import org.junit.Test


/**
  * Created by yuchen on 2017/10/17.
  */
class FPT5FileTest {
  @Test
  def test_stream_tp(): Unit = {

    val fPT5File = new FPT5File

    val fingerprintPackage = new util.ArrayList[FingerprintPackage]

    val descriptMsg = new DescriptMsg
    descriptMsg.originalSystemCasePersonId = "A1111111111111111111111"
    descriptMsg.jingZongPersonId = ""
    descriptMsg.casePersonid = "11111111111111111111111"
    descriptMsg.fingerPalmCardId = "11111111111111111111111"
    descriptMsg.chopedCauseCode = "222"
    descriptMsg.name = "俞晨"
    descriptMsg.alias = "111"
    descriptMsg.sex = "1"
    descriptMsg.birthday = "2017-10-17"
    descriptMsg.nationality = "111"
    descriptMsg.nation = "11"
    descriptMsg.credentialsCode = "112"
    descriptMsg.credentialsNo = "123"
    descriptMsg.houkouAdministrativeDivisionCode = "343241"
    descriptMsg.houkouAddress = "asd"
    descriptMsg.houseAdministrativeDivisionCode = "323211"
    descriptMsg.houseAddress = "123"


    val fingerprintMsg = new FingerprintMsg
    fingerprintMsg.fingerprintComparisonSysTypeDescript = "1111"
    fingerprintMsg.chopUnitCode = "111111111111"
    fingerprintMsg.chopUnitName = "234324"
    fingerprintMsg.chopPersonName = "55555"
    fingerprintMsg.chopPersonIdCard = "120101198601031538"
    fingerprintMsg.chopPersonTel = "123213123"
    fingerprintMsg.chopDateTime = "2017-10-11T10:00:01"
    fingerprintMsg.memo = "123213213213123123123123123213"
    fingerprintMsg.fingerNum = 15
    fingerprintMsg.palmNum = 0
    fingerprintMsg.fourFingerNum = 0
    fingerprintMsg.knuckleFingerNum = 0
    fingerprintMsg.fullPalmNum = 0
    fingerprintMsg.personPictureNum = 0

    val fingers = new Fingers

    val listFingerMsg = new util.ArrayList[FingerMsg]

    val fingerMsg1 = new FingerMsg
    fingerMsg1.fingerPositionCode = "99"
    fingerMsg1.fingerFeatureExtractionMethodCode= "9"
    fingerMsg1.adactylismCauseCode = "3"
    fingerMsg1.fingerPatternMasterCode = "1"
    fingerMsg1.fingerPatternSlaveCode = "1"
    fingerMsg1.fingerDirectionDescript = "11111"
    fingerMsg1.fingerCenterPoint = "11111111111111"
    fingerMsg1.fingerSlaveCenter = "11111111111111"
    fingerMsg1.fingerLeftTriangle = "11111111111111"
    fingerMsg1.fingerRightTriangle = "11111111111111"
    fingerMsg1.fingerExtractionNum = 15
    fingerMsg1.fingerExtractionInfo = "111111111111111"
    fingerMsg1.fingerCustomInfo = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
    fingerMsg1.fingerImageHorizontalDirectionLength = 640
    fingerMsg1.fingerImageVerticalDirectionLength = 640
    fingerMsg1.fingerImageRatio = 500
    fingerMsg1.fingerImageCompressMethodDescript = "1111"
    fingerMsg1.fingerImageData = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))



    val fingerMsg2 = new FingerMsg
    fingerMsg2.fingerPositionCode = "99"
    fingerMsg2.fingerFeatureExtractionMethodCode= "9"
    fingerMsg2.adactylismCauseCode = "3"
    fingerMsg2.fingerPatternMasterCode = "1"
    fingerMsg2.fingerPatternSlaveCode = "1"
    fingerMsg2.fingerDirectionDescript = "11441"
    fingerMsg2.fingerCenterPoint = "11111111111111"
    fingerMsg2.fingerSlaveCenter = "11111111111111"
    fingerMsg2.fingerLeftTriangle = "11111111111111"
    fingerMsg2.fingerRightTriangle = "11111111111111"
    fingerMsg2.fingerExtractionNum = 15
    fingerMsg2.fingerExtractionInfo = "111"
    fingerMsg2.fingerCustomInfo = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
    fingerMsg2.fingerImageHorizontalDirectionLength = 640
    fingerMsg2.fingerImageVerticalDirectionLength = 640
    fingerMsg2.fingerImageRatio = 500
    fingerMsg2.fingerImageCompressMethodDescript = "1111"
    fingerMsg2.fingerImageData = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))

    listFingerMsg.add(fingerMsg1)
    listFingerMsg.add(fingerMsg2)

    fingers.rolling.fingerMsg = listFingerMsg

    val portrait = new Portraits

    val fingerPrintPackage = new FingerprintPackage

    fingerPrintPackage.portraits = portrait
    fingerPrintPackage.descriptMsg = descriptMsg
    fingerPrintPackage.fingerprintMsg = fingerprintMsg
    fingerPrintPackage.fingers = fingers
//    fingerPrintPackage.personPictureTypeCode = "0"
//    fingerPrintPackage.personPictureImageData = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))


    fingerprintPackage.add(fingerPrintPackage)

    fPT5File.fingerprintPackage = fingerprintPackage


    val a = fPT5File.build(fPT5File)
    a.packageHead.originSystem = fPT5File.JINGZONG_SYSTEM
    a.taskInfo.sendUnitCode = "000000000000"
    a.taskInfo.sendUnitName = "aaa"
    a.taskInfo.sendPersonName = "yuchen"
    a.taskInfo.sendPersonIdCard = "120101198601031538"
    a.taskInfo.sendPersonTel = "3756473"
    a.taskInfo.sendUnitSystemType = "5555"

    println(XmlLoader.toXml(a))


    //val content = MonadConfigFileUtils.readConfigFileContent(serverHome, "hall-api.xml")
    import scala.io.Source

    //val content = Source.fromFile("D:\\a.xml", MonadSupportConstants.UTF8_ENCODING).mkString
    val obj = XmlLoader.parseXML[FPT5File](XmlLoader.toXml(a),xsd = Some(getClass.getResourceAsStream("/fpt5.xsd")))
    println(obj)
  }


  @Test
  def test_stream_lp(): Unit = {

    val fPT5File = new FPT5File
    val latentPackage = new LatentPackage
    val listLatentPackage = new util.ArrayList[LatentPackage]
    //listLatentPackage.add(latentPackage)

    val latentFingerMsg = new LatentFingerMsg
    val listLatentFingerMsg =new util.ArrayList[LatentFingerMsg]

    listLatentFingerMsg.add(latentFingerMsg)


    val latentPalmMsg = new LatentpalmMsg
    val listLatentPalmMsg = new util.ArrayList[LatentpalmMsg]
    listLatentPalmMsg.add(latentPalmMsg)


    val listTzz = new util.ArrayList[Xczwtzz]
    val xczwtzz = new Xczwtzz
    xczwtzz.latentFingerFeatureGroupIdentify = "111111111111111"
    xczwtzz.latentFingerFeatureGroupDescript = "asdasdasdad"
    xczwtzz.latentFingerAnalysisPostionBrief = "1111111111"
    xczwtzz.latentFingerPatternCode = "1231212"
    xczwtzz.latentFingerDirectionDescript = "12345"
    xczwtzz.latentFingerCenterPoint = ""
    xczwtzz.latentFingerSlaveCenter = ""
    xczwtzz.latentFingerLeftTriangle = ""
    xczwtzz.latentFingerRightTriangle = ""
    xczwtzz.latentFingerFeatureNum = 10
    xczwtzz.latentFingerFeatureInfo = "123213"
    xczwtzz.latentFingerCustomInfo = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
    xczwtzz.latentFingerImageHorizontalDirectionLength = 512
    xczwtzz.latentFingerImageVerticalDirectionLength = 512
    xczwtzz.latentFingerImageRatio = 500
    xczwtzz.latentFingerImageCompressMethodDescript = "1234"
    xczwtzz.latentFingerImageData = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
    listTzz.add(xczwtzz)

    latentFingerMsg.originalSystemLatentFingerPalmId = "A12312312313101"
    latentFingerMsg.latentPhysicalId = "A12312312313101A12312312313101"
    latentFingerMsg.latentFingerLeftPosition = "123123"
    latentFingerMsg.latentFingerFeatureExtractMethodCode = "1"
    latentFingerMsg.latentFingerCorpseJudgeIdentify = "3"
    latentFingerMsg.latentFingerMastoidProcessLineColorCode = "1"
    latentFingerMsg.latentFingerConnectFingerBeginPhysicalId = ""
    latentFingerMsg.latentFingerConnectFingerEndPhysicalId = ""
    latentFingerMsg.latentFingerComparisonStatusCode = "3"
    latentFingerMsg.latentFingerFeatureGroupNum = 10
    latentFingerMsg.latentFingerFeatureGroup = listTzz


    //--------------------掌纹------------------------------//
    val listTzz_zhw = new util.ArrayList[Xczhwtzz]
    val xczwtzz_zhw = new Xczhwtzz
    xczwtzz_zhw.latentPalmFeatureGroupIdentify = "111111111111111"
    xczwtzz_zhw.latentPalmFeatureGroupDescript = "asdasdasdad"
    xczwtzz_zhw.latentPalmAnalysisPostionBrief = "1245"
    xczwtzz_zhw.latentPalmRetracingPointNum = 3
    xczwtzz_zhw.latentPalmRetracingPointInfo = "123"
    xczwtzz_zhw.latentPalmTrianglePointNum = 4
    xczwtzz_zhw.latentPalmTrianglePointInfo = "123213"
    xczwtzz_zhw.latentPalmFeaturePointNum = 10
    xczwtzz_zhw.latentPalmFeaturePointInfo = "123213"
    xczwtzz_zhw.latentPalmCustomInfo = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
    xczwtzz_zhw.latentPalmImageHorizontalDirectionLength = 12
    xczwtzz_zhw.latentPalmImageVerticalDirectionLength = 123
    xczwtzz_zhw.latentPalmImageRatio = 1024
    xczwtzz_zhw.latentPalmImageCompressMethodDescript = "1234"
    xczwtzz_zhw.latentPalmImageData = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
    listTzz_zhw.add(xczwtzz_zhw)



    latentPalmMsg.latentPalmId = "123"
    latentPalmMsg.latentPalmPhysicalId = "A12312312313101A12312312313101"
    latentPalmMsg.latentPalmLeftPostion = "1"
    latentPalmMsg.latentPalmFeatureExtractMethodCode = "2"
    latentPalmMsg.latentPalmCorpseJudgeIdentify = "3"
    latentPalmMsg.latentPalmMastoidProcessLineColorCode = "1"
    latentPalmMsg.latentPalmComparisonStatusCode = "2"
    latentPalmMsg.latentPalmFeatureGroupNum = 2
    latentPalmMsg.latentPalmFeatureGroup = listTzz_zhw


    val caseMsg = new CaseMsg
    caseMsg.latentFingerprintComparisonSysTypeDescript = "1231"
    caseMsg.originalSystemCaseId = "A1230101011212121213211"
    caseMsg.caseId = "A1230101011212121213211"
    caseMsg.latentSurveyId = "A1230101011212121213211"
    caseMsg.latentCardId = "A123123123"
    caseMsg.caseTypeCode = "AT6765"
    caseMsg.money = "999999999.99"
    caseMsg.caseOccurAdministrativeDivisionCode = "123543"
    caseMsg.caseOccurAddress = "和平区港湾中心"
    caseMsg.briefCase = "asdasdsadsadsadsadasd"
    caseMsg.whetherKill = "1"
    caseMsg.extractUnitCode = "123444343354"
    caseMsg.extractUnitName = "124324"
    caseMsg.extractPerson = "张三"
    caseMsg.extractPersonIdCard = "120101198798340292"
    caseMsg.extractPersonTel = "122341"
    caseMsg.extractDateTime = "2017-10-17T09:00:00"
    caseMsg.latentFingerNum = 10
    caseMsg.latentPalmNum = 2

    val latentFingers = new LatentFingers
    latentFingers.latentfingerMsg = listLatentFingerMsg

    val latentPalms = new LatentPalms
    latentPalms.latentpalmMsg = listLatentPalmMsg

    latentPackage.caseMsg = caseMsg
    latentPackage.latentFingers = latentFingers
    latentPackage.latentPalms = latentPalms


    listLatentPackage.add(latentPackage)

    fPT5File.latentPackage = listLatentPackage

//    fPT5File.build(fPT5File).packageHead.originSystem = fPT5File.JINGZONG_SYSTEM
//    println(XmlLoader.toXml(fPT5File))

    val a = fPT5File.build(fPT5File)
    a.packageHead.originSystem = fPT5File.JINGZONG_SYSTEM
    a.taskInfo.sendUnitCode = "000000000000"
    a.taskInfo.sendUnitName = "aaa"
    a.taskInfo.sendPersonName = "yuchen"
    a.taskInfo.sendPersonIdCard = "120101198601031538"
    a.taskInfo.sendPersonTel = "3756473"
    a.taskInfo.sendUnitSystemType = "5555"
    println(XmlLoader.toXml(a))


    val obj = XmlLoader.parseXML[FPT5File](XmlLoader.toXml(a),xsd = Some(getClass.getResourceAsStream("/fpt5.xsd")))
    println(obj)
  }

  @Test
  def test_stream_ltp(): Unit = {
    val fPT5File = new FPT5File

    val listlatentTaskPackage = new util.ArrayList[LatenttaskPackage]
    val latentTaskPackage = new LatenttaskPackage

    latentTaskPackage.taskId = "12121212121212121212121"
    latentTaskPackage.taskTypeCode = "1"
    latentTaskPackage.comparisonSystemDescript = "1234"
    latentTaskPackage.originalSystemCaseId = "12121212121212121212121"
    latentTaskPackage.caseId = "12121212121212121212121"
    latentTaskPackage.latentSurveyId = "12121212121212121212121"
    latentTaskPackage.originalSystemLatentFingerId = ""
    latentTaskPackage.latentPhysicalId = "121212121212121212121211212121"
    latentTaskPackage.latentCardId = ""
    latentTaskPackage.submitDateTime = "2017-08-02T09:00:00"
    latentTaskPackage.latentPackage = new util.ArrayList[LatentPackage]


    listlatentTaskPackage.add(latentTaskPackage)
    fPT5File.latentTaskPackage = listlatentTaskPackage

    val a = fPT5File.build(fPT5File)
    a.packageHead.originSystem = fPT5File.JINGZONG_SYSTEM
    a.taskInfo.sendUnitCode = "000000000000"
    a.taskInfo.sendUnitName = "aaa"
    a.taskInfo.sendPersonName = "yuchen"
    a.taskInfo.sendPersonIdCard = "120101198601031538"
    a.taskInfo.sendPersonTel = "3756473"
    a.taskInfo.sendUnitSystemType = "5555"
    println(XmlLoader.toXml(a))

    val obj = XmlLoader.parseXML[FPT5File](XmlLoader.toXml(a),xsd = Some(getClass.getResourceAsStream("/fpt5.xsd")))
    println(obj)
  }


  @Test
  def test_stream_lhrp(): Unit = {
    val fPT5File = new FPT5File

    val listltHitResultPackage = new util.ArrayList[LtHitResultPackage]
    val ltHitResultPackage = new LtHitResultPackage

    ltHitResultPackage.comparisonSystemTypeDescript = "1234"
    ltHitResultPackage.latentFingerCaseId = "12312121212121212121214"
    ltHitResultPackage.latentFingerOriginalSystemCaseId = "12312121212121212121214"
    ltHitResultPackage.latentFingerLatentSurveyId = "12312121212121212121214"
    ltHitResultPackage.latentFingerOriginalSystemFingerId = ""
    ltHitResultPackage.latentFingerLatentPhysicalId = "123121212121212121212142323232"
    ltHitResultPackage.latentFingerCardId = ""
    ltHitResultPackage.fingerPrintOriginalSystemPersonId = "12312121212121212121214"
    ltHitResultPackage.fingerPrintJingZongPersonId = ""
    ltHitResultPackage.fingerPrintPersonId = "12312121212121212121214"
    ltHitResultPackage.fingerPrintCardId = ""
    ltHitResultPackage.fingerPrintPostionCode = "12"
    ltHitResultPackage.fingerPrintComparisonMethodCode = "1"
    ltHitResultPackage.hitUnitCode = "123412341234"
    ltHitResultPackage.hitUnitName = "1234"
    ltHitResultPackage.hitPersonName = "1234"
    ltHitResultPackage.hitPersonIdCard = "111111111111111"
    ltHitResultPackage.hitPersonTel = "1234"
    ltHitResultPackage.hitDateTime = "2017-09-09T10:10:10"
    ltHitResultPackage.checkUnitCode = "123412341234"
    ltHitResultPackage.checkUnitName = "1234"
    ltHitResultPackage.checkPersonName = "1234"
    ltHitResultPackage.checkPersonIdCard = "111111111111111"
    ltHitResultPackage.checkPersonTel = "1234"
    ltHitResultPackage.checkDateTime = "2017-09-09T10:10:11"
    ltHitResultPackage.memo = "1234"

    ltHitResultPackage.latentPackage = new util.ArrayList[LatentPackage]
    ltHitResultPackage.fingerprintPackage = new util.ArrayList[FingerprintPackage]


    listltHitResultPackage.add(ltHitResultPackage)
    fPT5File.ltHitResultPackage = listltHitResultPackage

    val a = fPT5File.build(fPT5File)
    a.packageHead.originSystem = fPT5File.JINGZONG_SYSTEM
    a.taskInfo.sendUnitCode = "000000000000"
    a.taskInfo.sendUnitName = "aaa"
    a.taskInfo.sendPersonName = "yuchen"
    a.taskInfo.sendPersonIdCard = "120101198601031538"
    a.taskInfo.sendPersonTel = "3756473"
    a.taskInfo.sendUnitSystemType = "5555"
    println(XmlLoader.toXml(a))

    val obj = XmlLoader.parseXML[FPT5File](XmlLoader.toXml(a),xsd = Some(getClass.getResourceAsStream("/fpt5.xsd")))
    println(obj)
  }


  @Test
  def test_stream_tthrp(): Unit = {
    val fPT5File = new FPT5File

    val listltHitResultPackage = new util.ArrayList[TtHitResultPackage]
    val ltHitResultPackage = new TtHitResultPackage

    ltHitResultPackage.comparisonSystemTypeDescript = "1234"
    ltHitResultPackage.ttHitTypeCode = "12"
    ltHitResultPackage.originalPersonId = "12312121212121212121214"
    ltHitResultPackage.jingZongPersonId = "12312121212121212121214"
    ltHitResultPackage.personId = "12312121212121212121214"
    ltHitResultPackage.cardId = ""
    ltHitResultPackage.whetherFingerJudgmentMark = "1"
    ltHitResultPackage.resultOriginalSystemPersonId = "12312121212121212121214"
    ltHitResultPackage.resultjingZongPersonId = "12312121212121212121214"
    ltHitResultPackage.resultPersonId = "12312121212121212121214"
    ltHitResultPackage.resultCardId = "12312121212121212121214"
    ltHitResultPackage.hitUnitCode = "123412341234"
    ltHitResultPackage.hitUnitName = "1234"
    ltHitResultPackage.hitPersonName = "1234"
    ltHitResultPackage.hitPersonIdCard = "111111111111111"
    ltHitResultPackage.hitPersonTel = "1234"
    ltHitResultPackage.hitDateTime = "2017-09-09T10:10:10"
    ltHitResultPackage.checkUnitCode = "123412341234"
    ltHitResultPackage.checkUnitName = "1234"
    ltHitResultPackage.checkPersonName = "1234"
    ltHitResultPackage.checkPersonIdCard = "111111111111111"
    ltHitResultPackage.checkPersonTel = "1234"
    ltHitResultPackage.checkDateTime = "2017-09-09T10:10:11"
    ltHitResultPackage.memo = "1234"

    ltHitResultPackage.fingerprintPackageSource = new util.ArrayList[FingerprintPackage]
    ltHitResultPackage.fingerprintPackageDesc = new util.ArrayList[FingerprintPackage]


    listltHitResultPackage.add(ltHitResultPackage)
    fPT5File.ttHitResultPackage = listltHitResultPackage

    val a = fPT5File.build(fPT5File)
    a.packageHead.originSystem = fPT5File.JINGZONG_SYSTEM
    a.taskInfo.sendUnitCode = "000000000000"
    a.taskInfo.sendUnitName = "aaa"
    a.taskInfo.sendPersonName = "yuchen"
    a.taskInfo.sendPersonIdCard = "120101198601031538"
    a.taskInfo.sendPersonTel = "3756473"
    a.taskInfo.sendUnitSystemType = "5555"
    println(XmlLoader.toXml(a))

    val obj = XmlLoader.parseXML[FPT5File](XmlLoader.toXml(a),xsd = Some(getClass.getResourceAsStream("/fpt5.xsd")))
    println(obj)
  }

  @Test
  def test_stream_llhrp(): Unit = {
    val fPT5File = new FPT5File

    val listltHitResultPackage = new util.ArrayList[LlHitResultPackage]
    val ltHitResultPackage = new LlHitResultPackage

    ltHitResultPackage.xxzjbh = ""
    ltHitResultPackage.comparisonSystemTypeDescript = "1234"
    ltHitResultPackage.originalSystemCaseId = "12312121212121212121214"
    ltHitResultPackage.caseId = "12312121212121212121214"
    ltHitResultPackage.latentSurveyId = "12312121212121212121214"
    ltHitResultPackage.originalSystemLatentFingerId = "12312121212121212121214"
    ltHitResultPackage.latentPhysicalId = "123121212121212121212142222222"
    ltHitResultPackage.cardId = ""
    ltHitResultPackage.resultOriginalSystemCaseId = "12312121212121212121214"
    ltHitResultPackage.resultCaseId = "12312121212121212121214"
    ltHitResultPackage.resultLatentSurveyId = "12312121212121212121214"
    ltHitResultPackage.resultOriginalSystemLatentPersonId = ""
    ltHitResultPackage.resultLatentPhysicalId = "123121212121277777771212121214"
    ltHitResultPackage.resultCardId = "12312121212121212121214"
    ltHitResultPackage.hitUnitCode = "123412341234"
    ltHitResultPackage.hitUnitName = "1234"
    ltHitResultPackage.hitPersonName = "1234"
    ltHitResultPackage.hitPersonIdCard = "111111111111111"
    ltHitResultPackage.hitPersonTel = "1234"
    ltHitResultPackage.hitDateTime = "2017-09-09T10:10:10"
    ltHitResultPackage.checkUnitCode = "123412341234"
    ltHitResultPackage.checkUnitName = "1234"
    ltHitResultPackage.checkPersonName = "1234"
    ltHitResultPackage.checkPersonIdCard = "111111111111111"
    ltHitResultPackage.checkPersonTel = "1234"
    ltHitResultPackage.checkDateTime = "2017-09-09T10:10:11"
    ltHitResultPackage.memo = "1234"


    val latentPackage = new LatentPackage
    val listLatentPackage = new util.ArrayList[LatentPackage]
    //listLatentPackage.add(latentPackage)
    val latentFingerMsg = new LatentFingerMsg
    val listLatentFingerMsg =new util.ArrayList[LatentFingerMsg]
    listLatentFingerMsg.add(latentFingerMsg)
    val latentPalmMsg = new LatentpalmMsg
    val listLatentPalmMsg = new util.ArrayList[LatentpalmMsg]
    listLatentPalmMsg.add(latentPalmMsg)
    val listTzz = new util.ArrayList[Xczwtzz]
    val xczwtzz = new Xczwtzz
    xczwtzz.latentFingerFeatureGroupIdentify = "111111111111111"
    xczwtzz.latentFingerFeatureGroupDescript = "asdasdasdad"
    xczwtzz.latentFingerAnalysisPostionBrief = "1111111111"
    xczwtzz.latentFingerPatternCode = "1231212"
    xczwtzz.latentFingerDirectionDescript = "12345"
    xczwtzz.latentFingerCenterPoint = ""
    xczwtzz.latentFingerSlaveCenter = ""
    xczwtzz.latentFingerLeftTriangle = ""
    xczwtzz.latentFingerRightTriangle = ""
    xczwtzz.latentFingerFeatureNum = 10
    xczwtzz.latentFingerFeatureInfo = "123213"
    xczwtzz.latentFingerCustomInfo = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
    xczwtzz.latentFingerImageHorizontalDirectionLength = 512
    xczwtzz.latentFingerImageVerticalDirectionLength = 512
    xczwtzz.latentFingerImageRatio = 500
    xczwtzz.latentFingerImageCompressMethodDescript = "1234"
    xczwtzz.latentFingerImageData = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
    listTzz.add(xczwtzz)
    latentFingerMsg.originalSystemLatentFingerPalmId = "A12312312313101"
    latentFingerMsg.latentPhysicalId = "A12312312313101A12312312313101"
    latentFingerMsg.latentFingerLeftPosition = "123123"
    latentFingerMsg.latentFingerFeatureExtractMethodCode = "1"
    latentFingerMsg.latentFingerCorpseJudgeIdentify = "3"
    latentFingerMsg.latentFingerMastoidProcessLineColorCode = "1"
    latentFingerMsg.latentFingerConnectFingerBeginPhysicalId = ""
    latentFingerMsg.latentFingerConnectFingerEndPhysicalId = ""
    latentFingerMsg.latentFingerComparisonStatusCode = "3"
    latentFingerMsg.latentFingerFeatureGroupNum = 10
    latentFingerMsg.latentFingerFeatureGroup = listTzz
    //--------------------掌纹------------------------------//
    val listTzz_zhw = new util.ArrayList[Xczhwtzz]
    val xczwtzz_zhw = new Xczhwtzz
    xczwtzz_zhw.latentPalmFeatureGroupIdentify = "111111111111111"
    xczwtzz_zhw.latentPalmFeatureGroupDescript = "asdasdasdad"
    xczwtzz_zhw.latentPalmAnalysisPostionBrief = "1245"
    xczwtzz_zhw.latentPalmRetracingPointNum = 3
    xczwtzz_zhw.latentPalmRetracingPointInfo = "123"
    xczwtzz_zhw.latentPalmTrianglePointNum = 4
    xczwtzz_zhw.latentPalmTrianglePointInfo = "123213"
    xczwtzz_zhw.latentPalmFeaturePointNum = 10
    xczwtzz_zhw.latentPalmFeaturePointInfo = "123213"
    xczwtzz_zhw.latentPalmCustomInfo = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
    xczwtzz_zhw.latentPalmImageHorizontalDirectionLength = 12
    xczwtzz_zhw.latentPalmImageVerticalDirectionLength = 123
    xczwtzz_zhw.latentPalmImageRatio = 1024
    xczwtzz_zhw.latentPalmImageCompressMethodDescript = "1234"
    xczwtzz_zhw.latentPalmImageData = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
    listTzz_zhw.add(xczwtzz_zhw)
    latentPalmMsg.latentPalmId = "123"
    latentPalmMsg.latentPalmPhysicalId = "A12312312313101A12312312313101"
    latentPalmMsg.latentPalmLeftPostion = "1"
    latentPalmMsg.latentPalmFeatureExtractMethodCode = "2"
    latentPalmMsg.latentPalmCorpseJudgeIdentify = "3"
    latentPalmMsg.latentPalmMastoidProcessLineColorCode = "1"
    latentPalmMsg.latentPalmComparisonStatusCode = "2"
    latentPalmMsg.latentPalmFeatureGroupNum = 2
    latentPalmMsg.latentPalmFeatureGroup = listTzz_zhw
    val caseMsg = new CaseMsg
    caseMsg.latentFingerprintComparisonSysTypeDescript = "1231"
    caseMsg.originalSystemCaseId = "A1230101011212121213211"
    caseMsg.caseId = "A1230101011212121213211"
    caseMsg.latentSurveyId = "A1230101011212121213211"
    caseMsg.latentCardId = "A123123123"
    caseMsg.caseTypeCode = "AT6765"
    caseMsg.money = "999999999.99"
    caseMsg.caseOccurAdministrativeDivisionCode = "123543"
    caseMsg.caseOccurAddress = "和平区港湾中心"
    caseMsg.briefCase = "asdasdsadsadsadsadasd"
    caseMsg.whetherKill = "1"
    caseMsg.extractUnitCode = "123444343354"
    caseMsg.extractUnitName = "124324"
    caseMsg.extractPerson = "张三"
    caseMsg.extractPersonIdCard = "120101198798340292"
    caseMsg.extractPersonTel = "122341"
    caseMsg.extractDateTime = "2017-10-17T09:00:00"
    caseMsg.latentFingerNum = 10
    caseMsg.latentPalmNum = 2
    val latentFingers = new LatentFingers
    latentFingers.latentfingerMsg = listLatentFingerMsg
    val latentPalms = new LatentPalms
    latentPalms.latentpalmMsg = listLatentPalmMsg
    latentPackage.caseMsg = caseMsg
    latentPackage.latentFingers = latentFingers
    latentPackage.latentPalms = latentPalms
    listLatentPackage.add(latentPackage)


    ltHitResultPackage.latentPackageSource = listLatentPackage
    ltHitResultPackage.latentPackageDesc = listLatentPackage


    listltHitResultPackage.add(ltHitResultPackage)
    fPT5File.llHitResultPackage = listltHitResultPackage

    val a = fPT5File.build(fPT5File)
    a.packageHead.originSystem = fPT5File.JINGZONG_SYSTEM
    a.taskInfo.sendUnitCode = "000000000000"
    a.taskInfo.sendUnitName = "aaa"
    a.taskInfo.sendPersonName = "yuchen"
    a.taskInfo.sendPersonIdCard = "120101198601031538"
    a.taskInfo.sendPersonTel = "3756473"
    a.taskInfo.sendUnitSystemType = "5555"
    println(XmlLoader.toXml(a))

    val obj = XmlLoader.parseXML[FPT5File](XmlLoader.toXml(a),xsd = Some(getClass.getResourceAsStream("/fpt5.xsd")))
    println(obj)
  }

}
