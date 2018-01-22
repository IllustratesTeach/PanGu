package nirvana.hall.v70.services

import java.io.{File, FileOutputStream}

import junit.framework.Assert
import nirvana.hall.api.services.{ExportRelationService, MatchRelationService}
import nirvana.hall.c.services.gfpt5lib.FPT5File
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchRelationGetRequest
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.support.services.XmlLoader
import nirvana.hall.v70.internal.BaseV70TestCase
import org.apache.commons.io.FileUtils
import org.junit.Test

/**
  * Created by songpeng on 16/11/26.
  */
class MatchRelationServiceImplTest extends BaseV70TestCase{

  @Test
  def test_getMatchRelationTT: Unit ={
    val service = getService[MatchRelationService]
    val requestBuilder = MatchRelationGetRequest.newBuilder()
    requestBuilder.setCardId("P3702000000002015109995")
    requestBuilder.setMatchType(MatchType.FINGER_TT)

    val matchRelation = service.getMatchRelation(requestBuilder.build())
    Assert.assertNotNull(matchRelation)
    println(matchRelation)
  }
  @Test
  def test_getMatchRelationTL: Unit ={
    val service = getService[MatchRelationService]
    val requestBuilder = MatchRelationGetRequest.newBuilder()
    requestBuilder.setCardId("R3702000000002015000004")
    requestBuilder.setMatchType(MatchType.FINGER_TL)

    val matchRelation = service.getMatchRelation(requestBuilder.build())
    Assert.assertNotNull(matchRelation)
    println(matchRelation)
  }
  @Test
  def test_getMatchRelationLT: Unit ={
    val service = getService[MatchRelationService]
    val requestBuilder = MatchRelationGetRequest.newBuilder()
    requestBuilder.setCardId("A333333332222222222222202")
    requestBuilder.setMatchType(MatchType.FINGER_LT)

    val matchRelation = service.getMatchRelation(requestBuilder.build())
    Assert.assertNotNull(matchRelation)
    println(matchRelation)
  }


  @Test
  def test_exportMatchRelation(): Unit ={

    val service = getService[ExportRelationService]
    val relation = service.exportMatchRelation("123","9536") //9536 tt //11017 tl //10816 lt //6322 ll

    val fileOutPutStream = new FileOutputStream("D://123.fpt")
//    fileOutPutStream.write(relation)
//    relation.wr(fileOutPutStream)
    fileOutPutStream.flush()
    fileOutPutStream.close()

  }

  @Test
  def test_getMatchRelation(): Unit ={
    val service = getService[MatchRelationService]
    val matchRelationInfo = service.getMatchRelation("402881e45cafe1fe015cafe38d000001")
    Assert.assertEquals("430111501999201206001703",matchRelationInfo.getSrckey)
  }

  @Test
  def test_getLTTLMatchRelation():Unit ={
    val oraSid = "224915"
    val service = getService[MatchRelationService]
    val matchRelationInfo = service.getLtHitResultPackageByOraSid(oraSid)
    val fPT5File = new FPT5File
    fPT5File.build(fPT5File)
    fPT5File.packageHead.originSystem = fPT5File.AFIS_SYSTEM
    fPT5File.packageHead.sendUnitCode = "000000000000"
    fPT5File.packageHead.sendUnitName = "aaa"
    fPT5File.packageHead.sendPersonName = "yuchen"
    fPT5File.packageHead.sendPersonIdCard = "120101198601031538"
    fPT5File.packageHead.sendPersonTel = "3756473"
    fPT5File.packageHead.sendUnitSystemType = "5555"
    fPT5File.ltHitResultPackage = matchRelationInfo.toArray

    val xmlStr = XmlLoader.toXml(fPT5File,"GBK")
    XmlLoader.parseXML[FPT5File](xmlStr, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/fpt5/LTHitResult.xsd"))
      , basePath = "/nirvana/hall/fpt5/")
    FileUtils.writeByteArrayToFile(new File("/Users/yuchen/fptx/"
      + oraSid + ".fptx"), xmlStr.getBytes())

  }


  @Test
  def test_getLLMatchRelation():Unit ={
    val oraSid = "224915"
    val service = getService[MatchRelationService]
    val matchRelationInfo = service.getLlHitResultPackageByOraSid(oraSid)
    val fPT5File = new FPT5File
    fPT5File.build(fPT5File)
    fPT5File.packageHead.originSystem = fPT5File.AFIS_SYSTEM
    fPT5File.packageHead.sendUnitCode = "000000000000"
    fPT5File.packageHead.sendUnitName = "aaa"
    fPT5File.packageHead.sendPersonName = "yuchen"
    fPT5File.packageHead.sendPersonIdCard = "120101198601031538"
    fPT5File.packageHead.sendPersonTel = "3756473"
    fPT5File.packageHead.sendUnitSystemType = "5555"
    fPT5File.llHitResultPackage = matchRelationInfo.toArray

    val xmlStr = XmlLoader.toXml(fPT5File,"GBK")
    XmlLoader.parseXML[FPT5File](xmlStr, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/fpt5/LLHitResult.xsd"))
      , basePath = "/nirvana/hall/fpt5/")
    FileUtils.writeByteArrayToFile(new File("/Users/yuchen/fptx/"
      + oraSid + ".fptx"), xmlStr.getBytes())
  }

  @Test
  def test_getTTMatchRelation():Unit ={
    val oraSid = "224915"
    val service = getService[MatchRelationService]
    val matchRelationInfo = service.getTtHitResultPackageByOraSid(oraSid)
    val fPT5File = new FPT5File
    fPT5File.build(fPT5File)
    fPT5File.packageHead.originSystem = fPT5File.AFIS_SYSTEM
    fPT5File.packageHead.sendUnitCode = "000000000000"
    fPT5File.packageHead.sendUnitName = "aaa"
    fPT5File.packageHead.sendPersonName = "yuchen"
    fPT5File.packageHead.sendPersonIdCard = "120101198601031538"
    fPT5File.packageHead.sendPersonTel = "3756473"
    fPT5File.packageHead.sendUnitSystemType = "5555"
    fPT5File.ttHitResultPackage = matchRelationInfo.toArray

    val xmlStr = XmlLoader.toXml(fPT5File,"GBK")
    XmlLoader.parseXML[FPT5File](xmlStr, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/fpt5/TTHitResult.xsd"))
      , basePath = "/nirvana/hall/fpt5/")
    FileUtils.writeByteArrayToFile(new File("/Users/yuchen/fptx/"
      + oraSid + ".fptx"), xmlStr.getBytes())
  }
}
