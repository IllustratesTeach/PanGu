package nirvana.hall.v62.internal.filter.lp

import com.google.protobuf.ByteString
import nirvana.hall.api.services.ProtobufRequestHandler
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse, ResponseStatus}
import nirvana.hall.protocol.v62.FPTProto.{FingerFgp, ImageType, PatternType}
import nirvana.hall.protocol.v62.lp.LPCardUpdateProto.LPCardUpdateRequest
import org.apache.tapestry5.ioc.{Registry, RegistryBuilder}
import org.junit.{Assert, Test}

/**
 * Created by songpeng on 15/11/15.
 */
class LPCardUpdateFilterTest {
  protected var registry:Registry = _

  @Test
  def test_request(): Unit ={
    val modules = Seq[String](
      "nirvana.hall.api.LocalProtobufModule",
      "nirvana.hall.v62.LocalV62Module",
      "nirvana.hall.v62.internal.filter.TestModule").map(Class.forName)
    registry = RegistryBuilder.buildAndStartupRegistry(modules: _*)

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

}
