package nirvana.hall.v70.gz.fpt5


import java.io.{ByteArrayInputStream, File, FileOutputStream}
import javax.imageio.ImageIO
import javax.imageio.spi.IIORegistry

import com.google.protobuf.ByteString
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.api.services.remote.HallImageRemoteService
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.c.services.gfpt5lib._
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.support.services.{GAFISImageReaderSpi, XmlLoader}
import nirvana.hall.v70.internal.BaseV70TestCase
import org.apache.commons.io.{FileUtils, IOUtils}
import org.apache.commons.lang.StringUtils
import org.junit.Test

import scala.collection.mutable.ArrayBuffer


/**
  * Created by Administrator on 2017/9/26.
  */
class Fpt5Test extends BaseV70TestCase {

  @Test
  def test_fpt5_tpget(): Unit = {
    val service = getService[FPT5Service]

    val fileList = FileUtils.listFiles(new File("/Users/yuchen/fpt4/liaoning/tp1"), Array[String]("completed"), true)
    val files = fileList.iterator
    while (files.hasNext) {
      val fPT5File = new FPT5File
      val fingerprintPackage = new scala.collection.mutable.ArrayBuffer[FingerprintPackage]

      val file = files.next
      var personId = ""
      val taskFpt = FPTFile.parseFromInputStream(
        new ByteArrayInputStream(FileUtils.readFileToByteArray(file)))

      taskFpt match {
        case Left(fpt3) => throw new Exception("Not Support FPT-V3.0")
        case Right(fpt4) =>
          if(fpt4.logic02Recs.length>0){
            fpt4.logic02Recs.foreach{
              t =>
                personId = t.personId
                //t.nativeplace = t.nativeplace.substring(0,1)
                //service.addLogic02Res(t)
            }
          }else if(fpt4.logic03Recs.length>0){
            //fpt4.logic03Recs.foreach(service.addLogic03Res(_))
          }
      }


      fingerprintPackage += service.getFingerprintPackage(personId)

      fPT5File.fingerprintPackage = fingerprintPackage.toArray

      val a = fPT5File.build(fPT5File)
      a.packageHead.originSystem = fPT5File.AFIS_SYSTEM
      a.packageHead.sendUnitCode = "000000000000"
      a.packageHead.sendUnitName = "aaa"
      a.packageHead.sendPersonName = "yuchen"
      a.packageHead.sendPersonIdCard = "120101198601031538"
      a.packageHead.sendPersonTel = "3756473"
      a.packageHead.sendUnitSystemType = "5555"

      a.fingerprintPackage.foreach{
        t =>
          //if(t.descriptiveMsg.houseAdministrativeDivisionCode.length < 6)
          t.descriptiveMsg.houseAdministrativeDivisionCode = "110000"
          t.descriptiveMsg.houkouAdministrativeDivisionCode = "110000"
          if(StringUtils.isEmpty(t.descriptiveMsg.birthday) || StringUtils.isBlank(t.descriptiveMsg.birthday)){
            t.descriptiveMsg.birthday = "19000101"
          }
          if(t.descriptiveMsg.houkouAddress.length <=0){
            t.descriptiveMsg.houkouAddress = "111"
          }
          if(t.descriptiveMsg.houseAddress.length <= 0){
            t.descriptiveMsg.houseAddress = "111"

          }
          if(t.collectInfoMsg.chopUnitCode.length < 12){
            t.collectInfoMsg.chopUnitCode = "111111111112"
          }
      }

      val xmlStr = XmlLoader.toXml(a,"GBK")
      XmlLoader.parseXML[FPT5File](xmlStr, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/fpt5/fingerprint.xsd"))
        , basePath = "/nirvana/hall/fpt5/")
      FileUtils.writeByteArrayToFile(new File("/Users/yuchen/fpt4/liaoning/fptx1/tp/"
        + personId + ".fptx"), xmlStr.getBytes())

      file.renameTo(new File("/Users/yuchen/fpt4/liaoning/tp1/" +file.getName() + ".completedex"))
      //
      //      val obj = XmlLoader.parseXML[FPT5File](XmlLoader.toXml(a),xsd = Some(getClass.getResourceAsStream("/fpt5.xsd")))
      //      println(obj)
    }

  }

  @Test
  def test_fpt5_export(): Unit = {
    val service = getService[FPT5Service]
    val fPT5File = new FPT5File
    val fingerprintPackage = new scala.collection.mutable.ArrayBuffer[FingerprintPackage]
    //files.next.getName.substring(0,23)
    fingerprintPackage += service.getFingerprintPackage("R5205274100002017120017")

    fPT5File.fingerprintPackage = fingerprintPackage.toArray

    val a = fPT5File.build(fPT5File)
    a.packageHead.originSystem = fPT5File.AFIS_SYSTEM
    a.packageHead.sendUnitCode = "000000000000"
    a.packageHead.sendUnitName = "aaa"
    a.packageHead.sendPersonName = "yuchen"
    a.packageHead.sendPersonIdCard = "120101198601031538"
    a.packageHead.sendPersonTel = "3756473"
    a.packageHead.sendUnitSystemType = "5555"


    val xmlStr = XmlLoader.toXml(a)
    XmlLoader.parseXML[FPT5File](xmlStr, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/fpt5/fingerprint.xsd"))
      , basePath = "/nirvana/hall/fpt5/")
    FileUtils.writeByteArrayToFile(new File("/Users/yuchen/fpt4/guizhou/fptx/tp/"
      + "R5205274100002017120017" + ".fptx"), xmlStr.getBytes())
  }

  @Test
  def test_fpt5_tpadd(): Unit = {
    val service = getService[FPT5Service]
    val hallImageRemoteService = getService[HallImageRemoteService]

    val originalList = new String(IOUtils.toByteArray(getClass.getResourceAsStream("/R4101000206002017075001.fptx")))


    val fpt  = XmlLoader.parseXML[FPT5File](originalList, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/fpt5/fingerprint.xsd"))
      , basePath = "/nirvana/hall/fpt5/")

    fpt.fingerprintPackage.foreach {
      fp => fp.fingers.fingerMsg.foreach{
        p =>
          val imgOrigin = fpt5PrintImgToGafisImg(p.fingerImageHorizontalDirectionLength.toShort
            ,p.fingerImageVerticalDirectionLength.toShort
          ,p.fingerImageRatio.toShort
          ,p.fingerImageData)



          val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(p.fingerImageData)
          val imgOrigin_ = hallImageRemoteService.decodeGafisImage(imgOrigin)

          ByteString.copyFrom(imgOrigin_.toByteArray(AncientConstants.GBK_ENCODING))
          val iioRegistry = IIORegistry.getDefaultInstance
          iioRegistry.registerServiceProvider(new GAFISImageReaderSpi)
          val img = ImageIO.read(new ByteArrayInputStream(imgOrigin_.toByteArray()))
          val out = new FileOutputStream("/Users/yuchen/" + 1 + "1.bmp")
          ImageIO.write(img, "bmp", out)
          out.close


      }
    }

  }

  @Test
  def test_fpt5_lp(): Unit = {


    val service = getService[FPT5Service]

    val fileList = FileUtils.listFiles(new File("/Users/yuchen/fpt4/liaoning/lp1"), Array[String]("fpt","FPT"), true)
    val files = fileList.iterator
    while (files.hasNext) {
      val fPT5File = new FPT5File
      var latentPackage = new ArrayBuffer[LatentPackage]


      val file = files.next
      var caseId = ""
      val taskFpt = FPTFile.parseFromInputStream(
        new ByteArrayInputStream(FileUtils.readFileToByteArray(file)))

      taskFpt match {
        case Left(fpt3) => throw new Exception("Not Support FPT-V3.0")
        case Right(fpt4) =>
          if(fpt4.logic02Recs.length>0){
            fpt4.logic02Recs.foreach{
              t =>

            }
          }else if(fpt4.logic03Recs.length>0){
            fpt4.logic03Recs.foreach{
              t => caseId = t.caseId
            }
          }
      }

      latentPackage += service.getLatentPackage(caseId)

      fPT5File.latentPackage = latentPackage.toArray

      fPT5File.latentPackage.foreach {
        t =>
          t.caseMsg.latentSurveyId = "K1111111111111111111111"
          t.caseMsg.caseId = "A1111111111111111111123"
          t.latentCollectInfoMsg.extractPersonIdCard = "120101198711031234"
          t.latentCollectInfoMsg.extractDateTime = "20171101123412"
          t.caseMsg.caseOccurAdministrativeDivisionCode = "110000"
          t.caseMsg.briefCase = "123掌声看到飞机失联的飞机上的冷风机"
          t.caseMsg.money = "120"
          var ajlb = new ArrayBuffer[String]()
          ajlb += "01000000"
          if(t.caseMsg.caseClassSet!=null){
            t.caseMsg.caseClassSet.caseTypeCode = ajlb.toArray
          }

          t.latentFingers.foreach {
            m =>
              m.latentFingerImageMsg.latentFingerLeftPosition = "01"
              m.latentFingerImageMsg.latentFingerComparisonStatusCode = "3"
          }
          t.latentPalms.foreach {
            m =>
              m.latentPalmImageMsg.latentPalmLeftPostion = "01"
              m.latentPalmImageMsg.latentPalmComparisonStatusCode = "3"
          }
      }

      val a = fPT5File.build(fPT5File)
      a.packageHead.originSystem = fPT5File.AFIS_SYSTEM
      a.packageHead.sendUnitCode = "000000000000"
      a.packageHead.sendUnitName = "aaa"
      a.packageHead.sendPersonName = "yuchen"
      a.packageHead.sendPersonIdCard = "120101198601031538"
      a.packageHead.sendPersonTel = "3756473"
      a.packageHead.sendUnitSystemType = "5555"
      a.latentPackage.foreach{
        t => if(t.latentCollectInfoMsg.extractUnitCode.length <12){
          t.latentCollectInfoMsg.extractUnitCode = "121111111112"
        }
      }
      val xmlStr = XmlLoader.toXml(a,"GBK")
      XmlLoader.parseXML[FPT5File](xmlStr, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/fpt5/latent.xsd"))
        , basePath = "/nirvana/hall/fpt5/")
      FileUtils.writeByteArrayToFile(new File("/Users/yuchen/fpt4/liaoning/fptx1/lp/"
        + caseId + ".fptx"), xmlStr.getBytes())
      file.renameTo(new File("/Users/yuchen/fpt4/liaoning/lp1/" +file.getName() + ".completedex"))
    }
  }

  @Test
  def test_fpt5_lp_Export(): Unit = {


    val service = getService[FPT5Service]
    val fPT5File = new FPT5File
    var latentPackage = new ArrayBuffer[LatentPackage]
    //
    latentPackage += service.getLatentPackage("A3607820000002017110006")

    fPT5File.latentPackage = latentPackage.toArray

    fPT5File.latentPackage.foreach {
      t =>
        t.caseMsg.latentSurveyId = "K1111111111111111111111"
        t.caseMsg.caseId = "A1111111111111111111123"
        t.latentCollectInfoMsg.extractPersonIdCard = "120101198711031234"
        t.latentCollectInfoMsg.extractDateTime = "20171101123412"
        t.caseMsg.caseOccurAdministrativeDivisionCode = "110000"
        t.caseMsg.briefCase = "123掌声看到飞机失联的飞机上的冷风机"
        t.latentFingers.foreach {
          m =>
            m.latentFingerImageMsg.latentFingerLeftPosition = "01"
            m.latentFingerImageMsg.latentFingerComparisonStatusCode = "3"
        }
        t.latentPalms.foreach {
          m =>
            m.latentPalmImageMsg.latentPalmLeftPostion = "01"
            m.latentPalmImageMsg.latentPalmComparisonStatusCode = "3"
        }
    }

    val a = fPT5File.build(fPT5File)
    a.packageHead.originSystem = fPT5File.AFIS_SYSTEM
    a.packageHead.sendUnitCode = "000000000000"
    a.packageHead.sendUnitName = "aaa"
    a.packageHead.sendPersonName = "yuchen"
    a.packageHead.sendPersonIdCard = "120101198601031538"
    a.packageHead.sendPersonTel = "3756473"
    a.packageHead.sendUnitSystemType = "5555"

    val xmlStr = XmlLoader.toXml(a)
    XmlLoader.parseXML[FPT5File](xmlStr, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/fpt5/latent.xsd"))
      , basePath = "/nirvana/hall/fpt5/")
    FileUtils.writeByteArrayToFile(new File("/Users/yuchen/fpt4/jiangxi/fptx/lp/"
      + "A3607820000002017110006" + ".fptx"), xmlStr.getBytes())

  }

  @Test
  def test_fpt5_lpadd(): Unit = {
    val service = getService[FPT5Service]

     val hallImageRemoteService = getService[HallImageRemoteService]
    val fPT5File = new FPT5File

    val originalList = new String(IOUtils.toByteArray(getClass.getResourceAsStream("/A44011117055520171201001101001.fptx")))


    val fpt  = XmlLoader.parseXML[FPT5File](originalList, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/fpt5/latent.xsd"))
      , basePath = "/nirvana/hall/fpt5/")

    fpt.latentPackage.foreach{
      t => t.latentFingers.foreach{
        lf =>
//          val imgOrigin = fpt5LatentImgToGafisImg(lf.latentFingerImageMsg.latentFingerImageHorizontalDirectionLength.toShort
//          ,lf.latentFingerImageMsg.latentFingerImageVerticalDirectionLength.toShort
//          ,lf.latentFingerImageMsg.latentFingerImageRatio.toShort
//          ,lf.latentFingerImageMsg.latentFingerImageData)

          val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(lf.latentFingerImageMsg.latentFingerImageData)


          val imgOrigin_ = hallImageRemoteService.decodeGafisImage(gafisImage)


          ByteString.copyFrom(imgOrigin_.toByteArray(AncientConstants.GBK_ENCODING))
          val iioRegistry = IIORegistry.getDefaultInstance
          iioRegistry.registerServiceProvider(new GAFISImageReaderSpi)
          val img = ImageIO.read(new ByteArrayInputStream(imgOrigin_.toByteArray()))
          val out = new FileOutputStream("/Users/yuchen/" + 1 + "1.bmp")
          ImageIO.write(img, "bmp", out)
          out.close

      }
    }
    println(XmlLoader.toXml(originalList))

  }


  private def fpt5LatentImgToGafisImg(width:Short,height:Short,dpi:Short,imgData:Array[Byte]): GAFISIMAGESTRUCT ={
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.bIsCompressed = 0
    gafisImg.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte
    gafisImg.stHead.nWidth = width
    gafisImg.stHead.nHeight = height
    gafisImg.stHead.nBits = 8
    gafisImg.stHead.nResolution = dpi
    gafisImg.bnData = imgData
    gafisImg.stHead.nImgSize = gafisImg.bnData.length
    gafisImg
  }


  private def fpt5PrintImgToGafisImg(width:Short,height:Short,dpi:Short,imgData:Array[Byte]): GAFISIMAGESTRUCT ={
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.bIsCompressed = 1
    gafisImg.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte
    gafisImg.stHead.nWidth = width
    gafisImg.stHead.nHeight = height
    gafisImg.stHead.nBits = 8
    gafisImg.stHead.nResolution = dpi
    gafisImg.bnData = imgData
    gafisImg.stHead.nImgSize = gafisImg.bnData.length
    gafisImg
  }

  @Test
  def test_LatentTaskPackageImport(): Unit ={
    val service = getService[FPT5Service]
    val fptFile = XmlLoader.parseXML[FPT5File](FileUtils.readFileToString(new File("/Users/yuchen/fptx/guizhou/latentTaskPackage.fptx")))
    fptFile.latentTaskPackage.foreach(service.addLatentTaskPackage(_))
  }

  @Test
  def test_LatentTaskPackageExport(): Unit ={
    val service = getService[FPT5Service]
    val fPT5File = new FPT5File
    val latenttaskPackageList = new ArrayBuffer[LatenttaskPackage]
    latenttaskPackageList += service.getLatentTaskPackage("00111111111111111111001")//taskNo
    fPT5File.latentTaskPackage = latenttaskPackageList.toArray
    fPT5File.packageHead.originSystem = fPT5File.AFIS_SYSTEM
    fPT5File.packageHead.sendUnitCode = "000000000000"
    fPT5File.packageHead.sendUnitName = "aaa"
    fPT5File.packageHead.sendPersonName = "yuchen"
    fPT5File.packageHead.sendPersonIdCard = "120101198601031538"
    fPT5File.packageHead.sendPersonTel = "3756473"
    fPT5File.packageHead.sendUnitSystemType = "5555"

    val xmlStr = XmlLoader.toXml(fPT5File,"GBK")
    XmlLoader.parseXML[FPT5File](xmlStr, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/fpt5/latentTask.xsd"))
      , basePath = "/nirvana/hall/fpt5/")

    FileUtils.writeByteArrayToFile(new File("/Users/yuchen/fpt4/guizhou/fptx/latentTask/XCCX001.fptx")
      ,XmlLoader.toXml(fPT5File).getBytes())
  }

  @Test
  def test_FingerPrintTaskPackageImport(): Unit ={
    val service = getService[FPT5Service]
    val fptFile = XmlLoader.parseXML[FPT5File](FileUtils.readFileToString(new File("/Users/yuchen/fptx/guizhou/printTaskPackage.fptx")))
    fptFile.printTaskPackage.foreach(service.addPrintTaskPackage(_))
  }

  @Test
  def test_FingerPrintTaskPackageExport(): Unit ={
    val service = getService[FPT5Service]
    val printtaskPackageList = new ArrayBuffer[PrinttaskPackage]
    printtaskPackageList += service.getPrintTaskPackage("R5205274100002017120015")
    val fPT5File = new FPT5File
    fPT5File.printTaskPackage = printtaskPackageList.toArray
    fPT5File.packageHead.originSystem = fPT5File.AFIS_SYSTEM
    fPT5File.packageHead.sendUnitCode = "P52000000000"
    fPT5File.packageHead.sendUnitName = "aaa"
    fPT5File.packageHead.sendPersonName = "yuchen"
    fPT5File.packageHead.sendPersonIdCard = "120101198601031538"
    fPT5File.packageHead.sendPersonTel = "3756473"
    fPT5File.packageHead.sendUnitSystemType = "5555"

    val xmlStr = XmlLoader.toXml(fPT5File,"GBK")
    XmlLoader.parseXML[FPT5File](xmlStr, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/fpt5/printTask.xsd"))
      , basePath = "/nirvana/hall/fpt5/")

    FileUtils.writeByteArrayToFile(new File("/Users/yuchen/fpt4/guizhou/fptx/tpTask/NYCX001.fptx")
      ,XmlLoader.toXml(fPT5File).getBytes())
  }
}
