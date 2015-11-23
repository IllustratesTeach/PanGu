package nirvana.hall.v62.internal.filter.lp

import com.google.protobuf.ByteString
import nirvana.hall.api.services.ProtobufRequestHandler
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse, ResponseStatus}
import nirvana.hall.protocol.v62.FPTProto.{FingerFgp, ImageType, PatternType}
import nirvana.hall.protocol.v62.lp.LPCardProto._
import org.apache.tapestry5.ioc.{Registry, RegistryBuilder}
import org.junit.{Assert, Test}

/**
 * Created by songpeng on 15/11/15.
 */
class LPCardFilterTest {
  val modules = Seq[String](
    "nirvana.hall.api.LocalProtobufModule",
    "nirvana.hall.v62.LocalV62Module",
    "nirvana.hall.v62.internal.filter.TestModule").map(Class.forName)
  protected var registry:Registry = RegistryBuilder.buildAndStartupRegistry(modules: _*)

  @Test
  def test_add(): Unit ={
    val requestBuilder = LPCardAddRequest.newBuilder()
    val lpCard = requestBuilder.getCardBuilder
    lpCard.setStrCardID("12345601")
    val textBuilder = lpCard.getTextBuilder
    textBuilder.setStrSeq("01")
    textBuilder.setStrStart("1")
    textBuilder.setStrEnd("2")
    textBuilder.setStrRemainPlace("杯子")
    textBuilder.setStrRidgeColor("1")
    textBuilder.setBDeadBody(false)
    textBuilder.setNBiDuiState(1)
    textBuilder.setNXieChaState(1)

    val blobBuilder = lpCard.getBlobBuilder
    blobBuilder.addFgp(FingerFgp.FINGER_L_INDEX)
    blobBuilder.addRp(PatternType.PATTERN_LEFTLOOP)
    blobBuilder.setStImageBytes(ByteString.readFrom(getClass.getResourceAsStream("/t.cpr")))
    blobBuilder.setType(ImageType.IMAGETYPE_FINGER)


    val handler = registry.getService(classOf[ProtobufRequestHandler])
    val protobufRequest = BaseRequest.newBuilder().setToken("asdf").setVersion(102)
    protobufRequest.setExtension(LPCardAddRequest.cmd, requestBuilder.build())
    val protobufResponse = BaseResponse.newBuilder()

    handler.handle(protobufRequest.build(), protobufResponse)
    Assert.assertEquals(ResponseStatus.OK,protobufResponse.getStatus)
  }
  @Test
  def test_del(): Unit ={
    val requestBuilder = LPCardDelRequest.newBuilder()
    requestBuilder.setCardId("12345601")

    val handler = registry.getService(classOf[ProtobufRequestHandler])
    val protobufRequest = BaseRequest.newBuilder().setToken("asdf").setVersion(102)
    protobufRequest.setExtension(LPCardDelRequest.cmd, requestBuilder.build())
    val protobufResponse = BaseResponse.newBuilder()

    handler.handle(protobufRequest.build(), protobufResponse)
    Assert.assertEquals(ResponseStatus.OK,protobufResponse.getStatus)
  }
  @Test
  def test_update(): Unit ={
    val requestBuilder = LPCardUpdateRequest.newBuilder()
    val lpCard = requestBuilder.getCardBuilder
    lpCard.setStrCardID("12345601")
    val textBuilder = lpCard.getTextBuilder
    textBuilder.setStrSeq("01")
    textBuilder.setStrStart("1")
    textBuilder.setStrEnd("10")
    textBuilder.setStrRemainPlace("桌子")
    textBuilder.setStrRidgeColor("2")
    textBuilder.setBDeadBody(false)
    textBuilder.setNBiDuiState(1)
    textBuilder.setNXieChaState(1)

    val blobBuilder = lpCard.getBlobBuilder
    blobBuilder.addFgp(FingerFgp.FINGER_L_INDEX)
    blobBuilder.addRp(PatternType.PATTERN_LEFTLOOP)
    blobBuilder.setStImageBytes(ByteString.readFrom(getClass.getResourceAsStream("/t.cpr")))
    blobBuilder.setType(ImageType.IMAGETYPE_FINGER)


    val handler = registry.getService(classOf[ProtobufRequestHandler])
    val protobufRequest = BaseRequest.newBuilder().setToken("asdf").setVersion(102)
    protobufRequest.setExtension(LPCardUpdateRequest.cmd, requestBuilder.build())
    val protobufResponse = BaseResponse.newBuilder()

    handler.handle(protobufRequest.build(), protobufResponse)
    Assert.assertEquals(ResponseStatus.OK,protobufResponse.getStatus)
  }
  @Test
  def test_get(): Unit ={
    val requestBuilder = LPCardGetRequest.newBuilder()
    requestBuilder.setCardId("12345601")

    val handler = registry.getService(classOf[ProtobufRequestHandler])
    val protobufRequest = BaseRequest.newBuilder().setToken("asdf").setVersion(102)
    protobufRequest.setExtension(LPCardGetRequest.cmd, requestBuilder.build())
    val protobufResponse = BaseResponse.newBuilder()

    handler.handle(protobufRequest.build(), protobufResponse)
    Assert.assertEquals(ResponseStatus.OK,protobufResponse.getStatus)
    Assert.assertNotNull(protobufResponse.getExtension(LPCardGetResponse.cmd).getCard)
  }

}
