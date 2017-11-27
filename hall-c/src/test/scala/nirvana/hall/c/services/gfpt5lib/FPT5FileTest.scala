package nirvana.hall.c.services.gfpt5lib

import java.util
import java.util.Date

import monad.support.services.XmlLoader
import org.apache.commons.io.IOUtils
import org.junit.Test

import scala.collection.mutable.ListBuffer


/**
  * Created by yuchen on 2017/10/17.
  */
class FPT5FileTest {
  @Test
  def test_stream_tp(): Unit = {

    val fPT5File = new FPT5File

    val fingerPrintPackageList = new util.ArrayList[FingerprintPackage]
    val fingerPrintPackage = new FingerprintPackage
    fingerPrintPackageList.add(fingerPrintPackage)

    val descriptMsg = new DescriptMsg
    descriptMsg.originalSystemCasePersonId = "11111111111111111111111"
    //descriptMsg.jingZongPersonId = ""
    descriptMsg.casePersonid = "11111111111111111111111"
    descriptMsg.fingerPalmCardId = "11111111111111111111111"
    descriptMsg.name = "俞晨"
    descriptMsg.alias = "111"
    descriptMsg.sex = "1"
    descriptMsg.birthday = "20171017"
    descriptMsg.nationality = "111"
    descriptMsg.nation = "11"
    descriptMsg.credentialsCode = "112"
    descriptMsg.credentialsNo = "123"
    descriptMsg.houkouAdministrativeDivisionCode = "343241"
    descriptMsg.houkouAddress = "asd"
    descriptMsg.houseAdministrativeDivisionCode = "323211"
    descriptMsg.houseAddress = "123"


    val ab = new ListBuffer[String]
    ab.append("01")
    ab.append("02")
    ab.append("01")
    ab.append("01")
    ab.append("01")

    val b = new CollectingReasonSet
    b.captureInfoReasonCode = ab.toArray
    descriptMsg.collectingReasonSet = b

    val collectionInfo = new CollectInfoMsg
    collectionInfo.fingerprintComparisonSysTypeDescript = "1900"
    collectionInfo.chopUnitCode = "370100000000"
    collectionInfo.chopUnitName = "贵州省公安厅"
    collectionInfo.chopPersonIdCard = "123288888888888888"
    collectionInfo.chopPersonName = "俞晨"
    collectionInfo.chopPersonTel="13752344218"
    collectionInfo.chopDateTime = (new Date).toString


    fingerPrintPackage.descriptiveMsg = descriptMsg
    fingerPrintPackage.collectInfoMsg = collectionInfo

    val fingers = new Fingers

    val listFingerMsg = new util.ArrayList[FingerMsg]

    val fingerMsg1 = new FingerMsg
    val minutiaList = new util.ArrayList[Minutia]
    val minutia = new Minutia
    minutiaList.add(minutia)
    val minutiaSet = new FingerMinutiaSet
    minutiaSet.minutia = minutiaList
    minutia.fingerFeaturePointXCoordinate = 1
    minutia.fingerFeaturePointYCoordinate = 2
    minutia.fingerFeatureDirection = 21
    minutia.fingerFeatureQuality =  123

    fingerMsg1.fingerPositionCode = "99"
    fingerMsg1.fingerFeatureExtractionMethodCode= "9"
    fingerMsg1.adactylismCauseCode = "3"
    fingerMsg1.fingerPatternMasterCode = "1"
    fingerMsg1.fingerPatternSlaveCode = "1"
    fingerMsg1.fingerFeatureDirection = 12
    fingerMsg1.fingerFeatureDirectionRange = 1
    fingerMsg1.fingerCenterPointFeatureXCoordinate = 12
    fingerMsg1.fingerCenterPointFeatureYCoordinate = 23
    fingerMsg1.fingerCenterPointFeatureCoordinateRange = 123
    fingerMsg1.fingerCenterPointFeatureReliabilityLevel = 2
    fingerMsg1.fingerSlaveCenterFeatureXCoordinate = 123
    fingerMsg1.fingerSlaveCenterFeatureYCoordinate = 234
    fingerMsg1.fingerSlaveCenterFeatureCoordinateRange = 123
    fingerMsg1.fingerSlaveCenterFeatureDirection = 23
    fingerMsg1.fingerSlaveCenterFeatureDirectionRange  = 3
    fingerMsg1.fingerSlaveCenterFeatureReliabilityLevel = 1
    fingerMsg1.fingerLeftTriangleFeatureXCoordinate = 123
    fingerMsg1.fingerLeftTriangleFeatureYCoordinate = 213
    fingerMsg1.fingerLeftTriangleFeatureCoordinateRange = 123
    fingerMsg1.fingerLeftTriangleFeatureDirection = 21
    fingerMsg1.fingerLeftTriangleFeatureDirectionRange = 22
    fingerMsg1.fingerLeftTriangleFeatureReliabilityLevel = 5
    fingerMsg1.fingerRightTriangleFeatureXCoordinate = 123
    fingerMsg1.fingerRightTriangleFeatureYCoordinate = 213
    fingerMsg1.fingerRightTriangleFeatureCoordinateRange = 123
    fingerMsg1.fingerRightTriangleFeatureDirection = 21
    fingerMsg1.fingerRightTriangleFeatureDirectionRange = 22
    fingerMsg1.fingerRightTriangleFeatureReliabilityLevel = 3
    fingerMsg1.fingerMinutiaSet = minutiaSet
    fingerMsg1.fingerCustomInfo = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
    fingerMsg1.fingerImageHorizontalDirectionLength = 640
    fingerMsg1.fingerImageVerticalDirectionLength = 640
    fingerMsg1.fingerImageRatio = 500
    fingerMsg1.fingerImageCompressMethodDescript = "1111"
    fingerMsg1.fingerImageQuality = 123
    fingerMsg1.fingerImageData = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))





    listFingerMsg.add(fingerMsg1)
    listFingerMsg.add(fingerMsg1)

    fingers.fingerMsg = listFingerMsg

    val faceImages = new FaceImages
    val faceImage = new FaceImage
    faceImage.personPictureFileLayout = "JPG"
    faceImage.personPictureTypeCode = "123"
    faceImage.personPictureImageData = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))

    val faceImageList = new util.ArrayList[FaceImage]
    faceImageList.add(faceImage)

    faceImages.faceImage = faceImageList

    fingerPrintPackage.fingers = fingers
    fingerPrintPackage.faceImages = faceImages
    //fPT5File.fingerprintPackage = fingerPrintPackageList

    val a = fPT5File.build(fPT5File)
    a.packageHead.originSystem = fPT5File.JINGZONG_SYSTEM
    a.packageHead.sendUnitCode = "000000000000"
    a.packageHead.sendUnitName = "aaa"
    a.packageHead.sendPersonName = "yuchen"
    a.packageHead.sendPersonIdCard = "120101198601031538"
    a.packageHead.sendPersonTel = "3756473"
    a.packageHead.sendUnitSystemType = "5555"

    println(XmlLoader.toXml(a))


    //val content = MonadConfigFileUtils.readConfigFileContent(serverHome, "hall-api.xml")
    import scala.io.Source

    //val content = Source.fromFile("D:\\a.xml", MonadSupportConstants.UTF8_ENCODING).mkString
    val obj = XmlLoader.parseXML[FPT5File](XmlLoader.toXml(a),xsd = Some(getClass.getResourceAsStream("/fpt5.xsd")))
    println(obj)
  }


  @Test
  def test_stream_lp(): Unit = {

//    val fPT5File = new FPT5File
//    val latentPackage = new LatentPackage
//    val listLatentPackage = new util.ArrayList[LatentPackage]
//    //listLatentPackage.add(latentPackage)
//
//    val latentFingerMsg = new LatentFingerMsg
//    val listLatentFingerMsg =new util.ArrayList[LatentFingerMsg]
//
//    listLatentFingerMsg.add(latentFingerMsg)
//
//
//    val latentPalmMsg = new LatentpalmMsg
//    val listLatentPalmMsg = new util.ArrayList[LatentpalmMsg]
//    listLatentPalmMsg.add(latentPalmMsg)
//
//
//    val listTzz = new util.ArrayList[Xczwtzz]
//    val xczwtzz = new Xczwtzz
//    xczwtzz.latentFingerFeatureGroupIdentify = "111111111111111"
//    xczwtzz.latentFingerFeatureGroupDescript = "asdasdasdad"
//    xczwtzz.latentFingerAnalysisPostionBrief = "1111111111"
//    xczwtzz.latentFingerPatternCode = "1231212"
//    xczwtzz.latentFingerDirectionDescript = "12345"
//    xczwtzz.latentFingerCenterPoint = ""
//    xczwtzz.latentFingerSlaveCenter = ""
//    xczwtzz.latentFingerLeftTriangle = ""
//    xczwtzz.latentFingerRightTriangle = ""
//    xczwtzz.latentFingerFeatureNum = 10
//    xczwtzz.latentFingerFeatureInfo = "123213"
//    xczwtzz.latentFingerCustomInfo = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
//    xczwtzz.latentFingerImageHorizontalDirectionLength = 512
//    xczwtzz.latentFingerImageVerticalDirectionLength = 512
//    xczwtzz.latentFingerImageRatio = 500
//    xczwtzz.latentFingerImageCompressMethodDescript = "1234"
//    xczwtzz.latentFingerImageData = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
//    listTzz.add(xczwtzz)
//
//    latentFingerMsg.originalSystemLatentFingerPalmId = "A12312312313101"
//    latentFingerMsg.latentPhysicalId = "A12312312313101A12312312313101"
//    latentFingerMsg.latentFingerLeftPosition = "123123"
//    latentFingerMsg.latentFingerFeatureExtractMethodCode = "1"
//    latentFingerMsg.latentFingerCorpseJudgeIdentify = "3"
//    latentFingerMsg.latentFingerMastoidProcessLineColorCode = "1"
//    latentFingerMsg.latentFingerConnectFingerBeginPhysicalId = ""
//    latentFingerMsg.latentFingerConnectFingerEndPhysicalId = ""
//    latentFingerMsg.latentFingerComparisonStatusCode = "3"
//    latentFingerMsg.latentFingerFeatureGroupNum = 10
//    latentFingerMsg.latentFingerFeatureGroup = listTzz
//
//
//    //--------------------掌纹------------------------------//
//    val listTzz_zhw = new util.ArrayList[Xczhwtzz]
//    val xczwtzz_zhw = new Xczhwtzz
//    xczwtzz_zhw.latentPalmFeatureGroupIdentify = "111111111111111"
//    xczwtzz_zhw.latentPalmFeatureGroupDescript = "asdasdasdad"
//    xczwtzz_zhw.latentPalmAnalysisPostionBrief = "1245"
//    xczwtzz_zhw.latentPalmRetracingPointNum = 3
//    xczwtzz_zhw.latentPalmRetracingPointInfo = "123"
//    xczwtzz_zhw.latentPalmTrianglePointNum = 4
//    xczwtzz_zhw.latentPalmTrianglePointInfo = "123213"
//    xczwtzz_zhw.latentPalmFeaturePointNum = 10
//    xczwtzz_zhw.latentPalmFeaturePointInfo = "123213"
//    xczwtzz_zhw.latentPalmCustomInfo = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
//    xczwtzz_zhw.latentPalmImageHorizontalDirectionLength = 12
//    xczwtzz_zhw.latentPalmImageVerticalDirectionLength = 123
//    xczwtzz_zhw.latentPalmImageRatio = 1024
//    xczwtzz_zhw.latentPalmImageCompressMethodDescript = "1234"
//    xczwtzz_zhw.latentPalmImageData = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
//    listTzz_zhw.add(xczwtzz_zhw)
//
//
//
//    latentPalmMsg.latentPalmId = "123"
//    latentPalmMsg.latentPalmPhysicalId = "A12312312313101A12312312313101"
//    latentPalmMsg.latentPalmLeftPostion = "1"
//    latentPalmMsg.latentPalmFeatureExtractMethodCode = "2"
//    latentPalmMsg.latentPalmCorpseJudgeIdentify = "3"
//    latentPalmMsg.latentPalmMastoidProcessLineColorCode = "1"
//    latentPalmMsg.latentPalmComparisonStatusCode = "2"
//    latentPalmMsg.latentPalmFeatureGroupNum = 2
//    latentPalmMsg.latentPalmFeatureGroup = listTzz_zhw
//
//
//    val caseMsg = new CaseMsg
//    caseMsg.latentFingerprintComparisonSysTypeDescript = "1231"
//    caseMsg.originalSystemCaseId = "A1230101011212121213211"
//    caseMsg.caseId = "A1230101011212121213211"
//    caseMsg.latentSurveyId = "A1230101011212121213211"
//    caseMsg.latentCardId = "A123123123"
//    caseMsg.caseTypeCode = "AT6765"
//    caseMsg.money = "999999999.99"
//    caseMsg.caseOccurAdministrativeDivisionCode = "123543"
//    caseMsg.caseOccurAddress = "和平区港湾中心"
//    caseMsg.briefCase = "asdasdsadsadsadsadasd"
//    caseMsg.whetherKill = "1"
//    caseMsg.extractUnitCode = "123444343354"
//    caseMsg.extractUnitName = "124324"
//    caseMsg.extractPerson = "张三"
//    caseMsg.extractPersonIdCard = "120101198798340292"
//    caseMsg.extractPersonTel = "122341"
//    caseMsg.extractDateTime = "2017-10-17T09:00:00"
//    caseMsg.latentFingerNum = 10
//    caseMsg.latentPalmNum = 2
//
//    val latentFingers = new LatentFingers
//    latentFingers.latentfingerMsg = listLatentFingerMsg
//
//    val latentPalms = new LatentPalms
//    latentPalms.latentpalmMsg = listLatentPalmMsg
//
//    latentPackage.caseMsg = caseMsg
//    latentPackage.latentFingers = latentFingers
//    latentPackage.latentPalms = latentPalms
//
//
//    listLatentPackage.add(latentPackage)
//
//    fPT5File.latentPackage = listLatentPackage
//
////    fPT5File.build(fPT5File).packageHead.originSystem = fPT5File.JINGZONG_SYSTEM
////    println(XmlLoader.toXml(fPT5File))
//
//    val a = fPT5File.build(fPT5File)
//    a.packageHead.originSystem = fPT5File.JINGZONG_SYSTEM
//    a.packageHead.sendUnitCode = "000000000000"
//    a.packageHead.sendUnitName = "aaa"
//    a.packageHead.sendPersonName = "yuchen"
//    a.packageHead.sendPersonIdCard = "120101198601031538"
//    a.packageHead.sendPersonTel = "3756473"
//    a.packageHead.sendUnitSystemType = "5555"
//    println(XmlLoader.toXml(a))
//
//
//    val obj = XmlLoader.parseXML[FPT5File](XmlLoader.toXml(a),xsd = Some(getClass.getResourceAsStream("/fpt5.xsd")))
//    println(obj)
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
    a.packageHead.sendUnitCode = "000000000000"
    a.packageHead.sendUnitName = "aaa"
    a.packageHead.sendPersonName = "yuchen"
    a.packageHead.sendPersonIdCard = "120101198601031538"
    a.packageHead.sendPersonTel = "3756473"
    a.packageHead.sendUnitSystemType = "5555"
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
    a.packageHead.sendUnitCode = "000000000000"
    a.packageHead.sendUnitName = "aaa"
    a.packageHead.sendPersonName = "yuchen"
    a.packageHead.sendPersonIdCard = "120101198601031538"
    a.packageHead.sendPersonTel = "3756473"
    a.packageHead.sendUnitSystemType = "5555"
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
    a.packageHead.sendUnitCode = "000000000000"
    a.packageHead.sendUnitName = "aaa"
    a.packageHead.sendPersonName = "yuchen"
    a.packageHead.sendPersonIdCard = "120101198601031538"
    a.packageHead.sendPersonTel = "3756473"
    a.packageHead.sendUnitSystemType = "5555"
    println(XmlLoader.toXml(a))

    val obj = XmlLoader.parseXML[FPT5File](XmlLoader.toXml(a),xsd = Some(getClass.getResourceAsStream("/fpt5.xsd")))
    println(obj)
  }

  @Test
  def test_stream_llhrp(): Unit = {
//    val fPT5File = new FPT5File
//
//    val listltHitResultPackage = new util.ArrayList[LlHitResultPackage]
//    val ltHitResultPackage = new LlHitResultPackage
//
//    ltHitResultPackage.xxzjbh = ""
//    ltHitResultPackage.comparisonSystemTypeDescript = "1234"
//    ltHitResultPackage.originalSystemCaseId = "12312121212121212121214"
//    ltHitResultPackage.caseId = "12312121212121212121214"
//    ltHitResultPackage.latentSurveyId = "12312121212121212121214"
//    ltHitResultPackage.originalSystemLatentFingerId = "12312121212121212121214"
//    ltHitResultPackage.latentPhysicalId = "123121212121212121212142222222"
//    ltHitResultPackage.cardId = ""
//    ltHitResultPackage.resultOriginalSystemCaseId = "12312121212121212121214"
//    ltHitResultPackage.resultCaseId = "12312121212121212121214"
//    ltHitResultPackage.resultLatentSurveyId = "12312121212121212121214"
//    ltHitResultPackage.resultOriginalSystemLatentPersonId = ""
//    ltHitResultPackage.resultLatentPhysicalId = "123121212121277777771212121214"
//    ltHitResultPackage.resultCardId = "12312121212121212121214"
//    ltHitResultPackage.hitUnitCode = "123412341234"
//    ltHitResultPackage.hitUnitName = "1234"
//    ltHitResultPackage.hitPersonName = "1234"
//    ltHitResultPackage.hitPersonIdCard = "111111111111111"
//    ltHitResultPackage.hitPersonTel = "1234"
//    ltHitResultPackage.hitDateTime = "2017-09-09T10:10:10"
//    ltHitResultPackage.checkUnitCode = "123412341234"
//    ltHitResultPackage.checkUnitName = "1234"
//    ltHitResultPackage.checkPersonName = "1234"
//    ltHitResultPackage.checkPersonIdCard = "111111111111111"
//    ltHitResultPackage.checkPersonTel = "1234"
//    ltHitResultPackage.checkDateTime = "2017-09-09T10:10:11"
//    ltHitResultPackage.memo = "1234"
//
//
//    val latentPackage = new LatentPackage
//    val listLatentPackage = new util.ArrayList[LatentPackage]
//    //listLatentPackage.add(latentPackage)
//    val latentFingerMsg = new LatentFingerMsg
//    val listLatentFingerMsg =new util.ArrayList[LatentFingerMsg]
//    listLatentFingerMsg.add(latentFingerMsg)
//    val latentPalmMsg = new LatentpalmMsg
//    val listLatentPalmMsg = new util.ArrayList[LatentpalmMsg]
//    listLatentPalmMsg.add(latentPalmMsg)
//    val listTzz = new util.ArrayList[Xczwtzz]
//    val xczwtzz = new Xczwtzz
//    xczwtzz.latentFingerFeatureGroupIdentify = "111111111111111"
//    xczwtzz.latentFingerFeatureGroupDescript = "asdasdasdad"
//    xczwtzz.latentFingerAnalysisPostionBrief = "1111111111"
//    xczwtzz.latentFingerPatternCode = "1231212"
//    xczwtzz.latentFingerDirectionDescript = "12345"
//    xczwtzz.latentFingerCenterPoint = ""
//    xczwtzz.latentFingerSlaveCenter = ""
//    xczwtzz.latentFingerLeftTriangle = ""
//    xczwtzz.latentFingerRightTriangle = ""
//    xczwtzz.latentFingerFeatureNum = 10
//    xczwtzz.latentFingerFeatureInfo = "123213"
//    xczwtzz.latentFingerCustomInfo = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
//    xczwtzz.latentFingerImageHorizontalDirectionLength = 512
//    xczwtzz.latentFingerImageVerticalDirectionLength = 512
//    xczwtzz.latentFingerImageRatio = 500
//    xczwtzz.latentFingerImageCompressMethodDescript = "1234"
//    xczwtzz.latentFingerImageData = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
//    listTzz.add(xczwtzz)
//    latentFingerMsg.originalSystemLatentFingerPalmId = "A12312312313101"
//    latentFingerMsg.latentPhysicalId = "A12312312313101A12312312313101"
//    latentFingerMsg.latentFingerLeftPosition = "123123"
//    latentFingerMsg.latentFingerFeatureExtractMethodCode = "1"
//    latentFingerMsg.latentFingerCorpseJudgeIdentify = "3"
//    latentFingerMsg.latentFingerMastoidProcessLineColorCode = "1"
//    latentFingerMsg.latentFingerConnectFingerBeginPhysicalId = ""
//    latentFingerMsg.latentFingerConnectFingerEndPhysicalId = ""
//    latentFingerMsg.latentFingerComparisonStatusCode = "3"
//    latentFingerMsg.latentFingerFeatureGroupNum = 10
//    latentFingerMsg.latentFingerFeatureGroup = listTzz
//    //--------------------掌纹------------------------------//
//    val listTzz_zhw = new util.ArrayList[Xczhwtzz]
//    val xczwtzz_zhw = new Xczhwtzz
//    xczwtzz_zhw.latentPalmFeatureGroupIdentify = "111111111111111"
//    xczwtzz_zhw.latentPalmFeatureGroupDescript = "asdasdasdad"
//    xczwtzz_zhw.latentPalmAnalysisPostionBrief = "1245"
//    xczwtzz_zhw.latentPalmRetracingPointNum = 3
//    xczwtzz_zhw.latentPalmRetracingPointInfo = "123"
//    xczwtzz_zhw.latentPalmTrianglePointNum = 4
//    xczwtzz_zhw.latentPalmTrianglePointInfo = "123213"
//    xczwtzz_zhw.latentPalmFeaturePointNum = 10
//    xczwtzz_zhw.latentPalmFeaturePointInfo = "123213"
//    xczwtzz_zhw.latentPalmCustomInfo = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
//    xczwtzz_zhw.latentPalmImageHorizontalDirectionLength = 12
//    xczwtzz_zhw.latentPalmImageVerticalDirectionLength = 123
//    xczwtzz_zhw.latentPalmImageRatio = 1024
//    xczwtzz_zhw.latentPalmImageCompressMethodDescript = "1234"
//    xczwtzz_zhw.latentPalmImageData = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
//    listTzz_zhw.add(xczwtzz_zhw)
//    latentPalmMsg.latentPalmId = "123"
//    latentPalmMsg.latentPalmPhysicalId = "A12312312313101A12312312313101"
//    latentPalmMsg.latentPalmLeftPostion = "1"
//    latentPalmMsg.latentPalmFeatureExtractMethodCode = "2"
//    latentPalmMsg.latentPalmCorpseJudgeIdentify = "3"
//    latentPalmMsg.latentPalmMastoidProcessLineColorCode = "1"
//    latentPalmMsg.latentPalmComparisonStatusCode = "2"
//    latentPalmMsg.latentPalmFeatureGroupNum = 2
//    latentPalmMsg.latentPalmFeatureGroup = listTzz_zhw
//    val caseMsg = new CaseMsg
//    caseMsg.latentFingerprintComparisonSysTypeDescript = "1231"
//    caseMsg.originalSystemCaseId = "A1230101011212121213211"
//    caseMsg.caseId = "A1230101011212121213211"
//    caseMsg.latentSurveyId = "A1230101011212121213211"
//    caseMsg.latentCardId = "A123123123"
//    caseMsg.caseTypeCode = "AT6765"
//    caseMsg.money = "999999999.99"
//    caseMsg.caseOccurAdministrativeDivisionCode = "123543"
//    caseMsg.caseOccurAddress = "和平区港湾中心"
//    caseMsg.briefCase = "asdasdsadsadsadsadasd"
//    caseMsg.whetherKill = "1"
//    caseMsg.extractUnitCode = "123444343354"
//    caseMsg.extractUnitName = "124324"
//    caseMsg.extractPerson = "张三"
//    caseMsg.extractPersonIdCard = "120101198798340292"
//    caseMsg.extractPersonTel = "122341"
//    caseMsg.extractDateTime = "2017-10-17T09:00:00"
//    caseMsg.latentFingerNum = 10
//    caseMsg.latentPalmNum = 2
//    val latentFingers = new LatentFingers
//    latentFingers.latentfingerMsg = listLatentFingerMsg
//    val latentPalms = new LatentPalms
//    latentPalms.latentpalmMsg = listLatentPalmMsg
//    latentPackage.caseMsg = caseMsg
//    latentPackage.latentFingers = latentFingers
//    latentPackage.latentPalms = latentPalms
//    listLatentPackage.add(latentPackage)
//
//
//    ltHitResultPackage.latentPackageSource = listLatentPackage
//    ltHitResultPackage.latentPackageDesc = listLatentPackage
//
//
//    listltHitResultPackage.add(ltHitResultPackage)
//    fPT5File.llHitResultPackage = listltHitResultPackage
//
//    val a = fPT5File.build(fPT5File)
//    a.packageHead.originSystem = fPT5File.JINGZONG_SYSTEM
//    a.packageHead.sendUnitCode = "000000000000"
//    a.packageHead.sendUnitName = "aaa"
//    a.packageHead.sendPersonName = "yuchen"
//    a.packageHead.sendPersonIdCard = "120101198601031538"
//    a.packageHead.sendPersonTel = "3756473"
//    a.packageHead.sendUnitSystemType = "5555"
//    println(XmlLoader.toXml(a))
//
//    val obj = XmlLoader.parseXML[FPT5File](XmlLoader.toXml(a),xsd = Some(getClass.getResourceAsStream("/fpt5.xsd")))
//    println(obj)
  }

}
