package nirvana.hall.v70.internal.filter

import com.google.protobuf.{ByteString, ExtensionRegistry}
import nirvana.hall.api.services.TPCardService
import nirvana.hall.protocol.api.{FPTProto, TPCardProto}
import nirvana.hall.protocol.api.FPTProto.{FingerFgp, TPCard}
import nirvana.hall.protocol.api.TPCardProto._
import nirvana.hall.support.internal.RpcHttpClientImpl
import nirvana.hall.v70.internal.BaseV70TestCase
import org.junit.{Assert, Test}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-04
 */
class TPCardFilterTest extends BaseV70TestCase{
  val url = "http://192.168.1.12:10002"
  val registry = ExtensionRegistry.newInstance()
  TPCardProto.registerAllExtensions(registry)
  val httpClient = new RpcHttpClientImpl(registry)

  @Test
  def test_add: Unit ={
    //新建
    val tpCard = TPCard.newBuilder()
    tpCard.setStrCardID("1243908874223689")
    val blobBuilder = tpCard.addBlobBuilder()
    blobBuilder.setStMntBytes(ByteString.readFrom(getClass.getResourceAsStream("/t.mnt")))
    blobBuilder.setStImageBytes(ByteString.readFrom(getClass.getResourceAsStream("/t.cpr")))
    blobBuilder.setType(FPTProto.ImageType.IMAGETYPE_FINGER)
    blobBuilder.setFgp(FingerFgp.FINGER_R_THUMB)

    val textBuilder = tpCard.getTextBuilder
    textBuilder.setStrName ("NellyLiu")
    textBuilder.setStrAliasName ("韩明名")
    textBuilder.setNSex (1)
    textBuilder.setStrBirthDate ("19800911")
    textBuilder.setStrIdentityNum ("123123")
    textBuilder.setStrBirthAddrCode ("123123")
    textBuilder.setStrBirthAddr ("中国")
    textBuilder.setStrAddrCode ("12")
    textBuilder.setStrAddr ("中国CHINA")
    textBuilder.setStrPersonType ("1")
    textBuilder.setStrCaseType1 ("1")
    textBuilder.setStrCaseType2 ("1")
    textBuilder.setStrCaseType3 ("1")
    textBuilder.setStrPrintUnitCode ("1")
    textBuilder.setStrPrintUnitName ("1")
    textBuilder.setStrPrinter ("1")
    textBuilder.setStrPrintDate ("20150121")
    textBuilder.setStrComment ("中国CHINA")
    textBuilder.setStrNation ("139")
    textBuilder.setStrRace ("01")
    textBuilder.setStrCertifType ("1")
    textBuilder.setStrCertifID ("1")
    textBuilder.setBHasCriminalRecord (true)
    textBuilder.setStrCriminalRecordDesc ("asdf")
    textBuilder.setStrPremium ("asdf")
    textBuilder.setNXieChaFlag (1)
    textBuilder.setStrXieChaRequestUnitName ("asdfasdf")
    textBuilder.setStrXieChaRequestUnitCode ("123")
    textBuilder.setNXieChaLevel (1)
    textBuilder.setStrXieChaForWhat ("11111")
    textBuilder.setStrRelPersonNo ("12")
    textBuilder.setStrRelCaseNo ("12")
    textBuilder.setStrXieChaTimeLimit ("12")
    textBuilder.setStrXieChaDate ("")
    textBuilder.setStrXieChaRequestComment ("")
    textBuilder.setStrXieChaContacter ("")
    textBuilder.setStrXieChaTelNo ("")
    textBuilder.setStrShenPiBy ("")

    val tpCardService = getService[TPCardService]
    tpCardService.addTPCard(tpCard.build())
  }
  @Test
  def test_del: Unit ={
    val requestBuilder = TPCardDelRequest.newBuilder()
    requestBuilder.setCardId("1243908874223689")
  }
  @Test
  def test_update: Unit ={
    val tpCard = TPCard.newBuilder()
    tpCard.setStrCardID("1243908874223689")
    val blobBuilder = tpCard.addBlobBuilder()
    blobBuilder.setStMntBytes(ByteString.readFrom(getClass.getResourceAsStream("/t.mnt")))
    blobBuilder.setStImageBytes(ByteString.readFrom(getClass.getResourceAsStream("/t.cpr")))
    blobBuilder.setType(FPTProto.ImageType.IMAGETYPE_FINGER)
    blobBuilder.setFgp(FingerFgp.FINGER_R_THUMB)

    val textBuilder = tpCard.getTextBuilder
    textBuilder.setStrName ("蔡Sir2")
    textBuilder.setStrAliasName ("小刀蔡")
    textBuilder.setNSex (2)
    textBuilder.setStrBirthDate ("19900911")
    textBuilder.setStrIdentityNum ("123456")
    textBuilder.setStrBirthAddrCode ("100000")
    textBuilder.setStrBirthAddr ("中国")
    textBuilder.setStrAddrCode ("520000")
    textBuilder.setStrAddr ("中国CHINA")
    textBuilder.setStrPersonType ("1")
    textBuilder.setStrCaseType1 ("1")
    textBuilder.setStrCaseType2 ("1")
    textBuilder.setStrCaseType3 ("1")
    textBuilder.setStrPrintUnitCode ("1")
    textBuilder.setStrPrintUnitName ("1")
    textBuilder.setStrPrinter ("1")
    textBuilder.setStrPrintDate ("20150121")
    textBuilder.setStrComment ("中国CHINA")
    textBuilder.setStrNation ("100")
    textBuilder.setStrRace ("01")
    textBuilder.setStrCertifType ("1")
    textBuilder.setStrCertifID ("1")
    textBuilder.setBHasCriminalRecord (true)
    textBuilder.setStrCriminalRecordDesc ("asdf")
    textBuilder.setStrPremium ("100")
    textBuilder.setNXieChaFlag (1)
    textBuilder.setStrXieChaRequestUnitName ("asdfasdf")
    textBuilder.setStrXieChaRequestUnitCode ("123")
    textBuilder.setNXieChaLevel (1)
    textBuilder.setStrXieChaForWhat ("11111")
    textBuilder.setStrRelPersonNo ("12")
    textBuilder.setStrRelCaseNo ("12")
    textBuilder.setStrXieChaTimeLimit ("12")
    textBuilder.setStrXieChaDate ("")
    textBuilder.setStrXieChaRequestComment ("")
    textBuilder.setStrXieChaContacter ("")
    textBuilder.setStrXieChaTelNo ("")
    textBuilder.setStrShenPiBy ("")

    val tpCardService = getService[TPCardService]
    val response = tpCardService.updateTPCard(tpCard.build())
    Assert.assertNotNull(response)
  }
  @Test
  def test_get: Unit ={
    val requestBuilder = TPCardGetRequest.newBuilder()
    requestBuilder.setCardId("R3702020100002016000200")
    requestBuilder.setDbid("1")

    val response = httpClient.call(url, TPCardGetRequest.cmd, requestBuilder.build())
    val tPCard = response.getExtension(TPCardGetResponse.cmd)
    Assert.assertNotNull(tPCard)
  }
  @Test
  def test_isExist: Unit ={
    val tPCardService = getService[TPCardService]
    val isExist = tPCardService.isExist("1234567890")
    Assert.assertTrue(isExist)
  }

}
