package nirvana.hall.v70.internal.filter

import com.google.protobuf.ByteString
import nirvana.hall.api.services.ProtobufRequestHandler
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse, ResponseStatus}
import nirvana.hall.protocol.v62.FPTProto
import nirvana.hall.protocol.v62.FPTProto.{FingerFgp, TPCard}
import nirvana.hall.protocol.v62.tp.TPCardProto._
import org.apache.tapestry5.ioc.{Registry, RegistryBuilder}
import org.junit.{Assert, Test}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-04
 */
class TPCardFilterTest {
  val modules = Seq[String](
    "nirvana.hall.api.LocalProtobufModule",
    "nirvana.hall.api.LocalApiServiceModule",
    "nirvana.hall.orm.HallOrmModule",
    "nirvana.hall.v70.internal.filter.TestModule",
    "nirvana.hall.v70.LocalV70ServiceModule",
    "nirvana.hall.v70.LocalDataSourceModule"
  ).map(Class.forName)
  protected var registry:Registry = RegistryBuilder.buildAndStartupRegistry(modules: _*)
  @Test
  def test_add: Unit ={
    //新建
    val tpCard = TPCard.newBuilder()
    tpCard.setStrCardID("1234567890")
    val blobBuilder = tpCard.addBlobBuilder()
    blobBuilder.setStMntBytes(ByteString.readFrom(getClass.getResourceAsStream("/t.mnt")))
    blobBuilder.setStImageBytes(ByteString.readFrom(getClass.getResourceAsStream("/t.cpr")))
    blobBuilder.setType(FPTProto.ImageType.IMAGETYPE_FINGER)
    blobBuilder.setFgp(FingerFgp.FINGER_R_THUMB)

    val textBuilder = tpCard.getTextBuilder
    textBuilder.setStrName ("蔡Sir")
    textBuilder.setStrAliasName ("大刀蔡")
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

    val TPCardAdd = TPCardAddRequest.newBuilder()
    TPCardAdd.setCard(tpCard.build())

    val handler = registry.getService(classOf[ProtobufRequestHandler])
    val protobufRequest = BaseRequest.newBuilder().setToken("asdf").setVersion(102)
    protobufRequest.setExtension(TPCardAddRequest.cmd, TPCardAdd.build())
    val protobufResponse = BaseResponse.newBuilder()
    protobufResponse.setStatus(ResponseStatus.OK)
    handler.handle(protobufRequest.build(), protobufResponse)

    Assert.assertTrue(protobufResponse.hasExtension(TPCardAddResponse.cmd))
    Assert.assertEquals(ResponseStatus.OK,protobufResponse.getStatus)
  }
  @Test
  def test_del: Unit ={
    val requestBuilder = TPCardDelRequest.newBuilder()
    requestBuilder.setCardId("1234567890")
    val handler = registry.getService(classOf[ProtobufRequestHandler])
    val protobufRequest = BaseRequest.newBuilder().setToken("asdf").setVersion(102)
    protobufRequest.setExtension(TPCardDelRequest.cmd, requestBuilder.build())
    val protobufResponse = BaseResponse.newBuilder()
    handler.handle(protobufRequest.build(), protobufResponse)

    Assert.assertEquals(ResponseStatus.OK,protobufResponse.getStatus)
  }
  @Test
  def test_update: Unit ={
    val requestBuilder = TPCardUpdateRequest.newBuilder()
    val tpCard = TPCard.newBuilder()
    tpCard.setStrCardID("1234567890")
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
    requestBuilder.setCard(tpCard)

    val handler = registry.getService(classOf[ProtobufRequestHandler])
    val protobufRequest = BaseRequest.newBuilder().setToken("asdf").setVersion(102)
    protobufRequest.setExtension(TPCardUpdateRequest.cmd, requestBuilder.build())
    val protobufResponse = BaseResponse.newBuilder()
    handler.handle(protobufRequest.build(), protobufResponse)

    Assert.assertTrue(protobufResponse.hasExtension(TPCardUpdateResponse.cmd))
    Assert.assertEquals(ResponseStatus.OK,protobufResponse.getStatus)
  }
  @Test
  def test_get: Unit ={
    val requestBuilder = TPCardGetRequest.newBuilder()
    requestBuilder.setCardId("1234567890")

    val handler = registry.getService(classOf[ProtobufRequestHandler])
    val protobufRequest = BaseRequest.newBuilder().setToken("asdf").setVersion(102)
    protobufRequest.setExtension(TPCardGetRequest.cmd, requestBuilder.build())
    val protobufResponse = BaseResponse.newBuilder()
    handler.handle(protobufRequest.build(), protobufResponse)

    Assert.assertTrue(protobufResponse.hasExtension(TPCardGetResponse.cmd))
    Assert.assertEquals(ResponseStatus.OK,protobufResponse.getStatus)
    Assert.assertNotNull(protobufResponse.getExtension(TPCardGetResponse.cmd).getCard)
  }

}
