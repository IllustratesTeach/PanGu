package nirvana.hall.c.services.gfpt5lib

import java.util
import java.util.Date

import monad.support.services.XmlLoader
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
    descriptMsg.nationality = "1111"
    descriptMsg.nation = "11111"
    descriptMsg.credentialsCode = "1111112"
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
    //fingerMsg1.fingerCustomInfo = ""
    fingerMsg1.fingerImageHorizontalDirectionLength = 640
    fingerMsg1.fingerImageVerticalDirectionLength = 640
    fingerMsg1.fingerImageRatio = 500
    fingerMsg1.fingerImageCompressMethodDescript = "1111"
    //fingerMsg1.fingerImageData = ""



    val fingerMsg2 = new FingerMsg
    fingerMsg2.fingerPositionCode = "99"
    fingerMsg2.fingerFeatureExtractionMethodCode= "9"
    fingerMsg2.adactylismCauseCode = "3"
    fingerMsg2.fingerPatternMasterCode = "1"
    fingerMsg2.fingerPatternSlaveCode = "1"
    fingerMsg2.fingerDirectionDescript = "11111"
    fingerMsg2.fingerCenterPoint = "11111111111111"
    fingerMsg2.fingerSlaveCenter = "11111111111111"
    fingerMsg2.fingerLeftTriangle = "11111111111111"
    fingerMsg2.fingerRightTriangle = "11111111111111"
    fingerMsg2.fingerExtractionNum = 15
    fingerMsg2.fingerExtractionInfo = "111"
    //fingerMsg2.fingerCustomInfo = "111"
    fingerMsg2.fingerImageHorizontalDirectionLength = 640
    fingerMsg2.fingerImageVerticalDirectionLength = 640
    fingerMsg2.fingerImageRatio = 500
    fingerMsg2.fingerImageCompressMethodDescript = "1111"
    //fingerMsg2.fingerImageData = "111"

    listFingerMsg.add(fingerMsg1)
    listFingerMsg.add(fingerMsg2)

    fingers.rolling.fingerMsg = listFingerMsg

    val fingerPrintPackage = new FingerprintPackage

    fingerPrintPackage.descriptMsg = descriptMsg
    fingerPrintPackage.fingerprintMsg = fingerprintMsg
    fingerPrintPackage.fingers = fingers


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
    listLatentPackage.add(latentPackage)

    val latentFingerMsg = new LatentFingerMsg
    val listLatentFingerMsg =new util.ArrayList[LatentFingerMsg]

    listLatentFingerMsg.add(latentFingerMsg)



    val latentPlamMsg = new LatentplamMsg
    val listLatentPlamMsg = new util.ArrayList[LatentplamMsg]
    listLatentPlamMsg.add(latentPlamMsg)



    val listTzz = new util.ArrayList[Xczwtzz]
    val xczwtzz = new Xczwtzz
    xczwtzz.xczw_tzzhbsf = "111111111111111"
    xczwtzz.xczw_tzzhms = "asdasdasdad"
    xczwtzz.xczw_fxzw_jyqk = "1111111111"
    xczwtzz.xczw_zwwxdm = "123"
    xczwtzz.xczw_zwfxms = "12345"
    xczwtzz.xczw_zwzxd = "12313"
    xczwtzz.xczw_zwfzx = "123"
    xczwtzz.xczw_zwzsj = "123"
    xczwtzz.xczw_zwysj = "123213"
    xczwtzz.xczw_zwtzd_sl = 10
    xczwtzz.xczw_zwtzdxx = "123213"
    xczwtzz.xczw_zdyxx = "123"
    xczwtzz.xczw_txspfxcd = 12
    xczwtzz.xczw_txczfxcd = 123
    xczwtzz.xczw_txfbl = 1024
    xczwtzz.xczw_txysffms = "1234"
    xczwtzz.xczw_txsj = "adsdaf234324sfsafasf"
    listTzz.add(xczwtzz)

    latentFingerMsg.xcwzbh = "A12312312313101"
    latentFingerMsg.xczw_xczzwylbw = "123123"
    latentFingerMsg.xczw_zzwtztqfsdm = "123"
    latentFingerMsg.xczw_stzzw_pdbz = "345"
    latentFingerMsg.xczw_rtxysdm = "123123"
    latentFingerMsg.xczw_lzks_xcwzbh = "123"
    latentFingerMsg.xczw_lzjs_xcwzbh = "123"
    latentFingerMsg.xczw_zwbdztdm = "345345"
    latentFingerMsg.xczw_tzzhsl = 10
    latentFingerMsg.xczwtzz = listTzz


    //--------------------掌纹------------------------------//
    val listTzz_zhw = new util.ArrayList[Xczhwtzz]
    val xczwtzz_zhw = new Xczhwtzz
    xczwtzz_zhw.xczhw_tzzhbsf = "111111111111111"
    xczwtzz_zhw.xczhw_tzzhms = "asdasdasdad"
    xczwtzz_zhw.xczhw_fxzhw_jyqk = "12345"
    xczwtzz_zhw.xczhw_zwzfd_sl = 3
    xczwtzz_zhw.xczhw_zwzfdxx = "123"
    xczwtzz_zhw.xczhw_zwsjd_sl = 4
    xczwtzz_zhw.xczhw_zwsjdxx = "123213"
    xczwtzz_zhw.xczhw_zhwtzd_sl = 10
    xczwtzz_zhw.xczhw_zhwtzdxx = "123213"
    xczwtzz_zhw.xczhw_zdyxx = "123"
    xczwtzz_zhw.xczhw_txspfxcd = 12
    xczwtzz_zhw.xczhw_txczfxcd = 123
    xczwtzz_zhw.xczhw_txfbl = 1024
    xczwtzz_zhw.xczhw_txysffms = "1234"
    xczwtzz_zhw.xczhw_txsj = "adsdaf234324sfsafasf"
    listTzz_zhw.add(xczwtzz_zhw)


    latentPlamMsg.xczhw_tzzhsl = 2
    latentPlamMsg.xczhw_xcwzbh = "A12312312313101"
    latentPlamMsg.xczhw_xczzwbh = "123"
    latentPlamMsg.xczhw_xczzwylbw = "123123"
    latentPlamMsg.xczhw_zzwtztqfsdm = "123"
    latentPlamMsg.xczhw_stzzw_pdbz = "345"
    latentPlamMsg.xczhw_rtxysdm = "123123"
    latentPlamMsg.xczhw_zhwbdztdm = "123"
    latentPlamMsg.xczhwtzz = listTzz_zhw




    val caseMsg = new CaseMsg
    //caseMsg.zwbdxtlxms = "12312"
    caseMsg.ysxt_asjbh = "A12301010111"
    caseMsg.asjbh = "123213123"
    caseMsg.xckybh = "K001"
    caseMsg.xczzwkbh = "A123123123"
    caseMsg.ajlbdm = "AT6765"
    caseMsg.asjsscw_jermby = 120
    caseMsg.asjfsdd_xzqhdm = "123"
    caseMsg.asjfsdd_dzmc = "和平区港湾中心"
    caseMsg.jyaq = "asdasdsadsadsadsadasd"
    caseMsg.sfma_pdbz = "1"
    caseMsg.tqsj_gajgjgdm = "123"
    caseMsg.tqsj_gajgmc = "124324"
    caseMsg.tqry_xm = "张三"
    caseMsg.tqry_gmsfhm = "120101198798340292"
    caseMsg.tqry_lxdh = "122341"
    caseMsg.tqsj = String.valueOf(new Date)
    caseMsg.xczw_sl = 10
    caseMsg.xczhw_sl = 2
    val latentFingers = new LatentFingers
    latentFingers.latentfingerMsg = listLatentFingerMsg

    val latentPalms = new LatentPalms
    latentPalms.latentplamMsg = listLatentPlamMsg

    latentPackage.caseMsg = caseMsg
    latentPackage.latentFingers = latentFingers
    latentPackage.latentPalms = latentPalms

    fPT5File.latentPackage = listLatentPackage

    fPT5File.build(fPT5File).packageHead.originSystem = fPT5File.JINGZONG_SYSTEM
    println(XmlLoader.toXml(fPT5File))
  }
}
