package nirvana.hall.v62.internal.filter

import com.google.protobuf.ByteString
import nirvana.hall.api.services.LPCardService
import nirvana.hall.protocol.api.FPTProto.{FingerFgp, ImageType, PatternType}
import nirvana.hall.protocol.api.LPCardProto._
import nirvana.hall.v62.BaseV62TestCase
import org.junit.{Assert, Test}

/**
 * Created by songpeng on 15/11/15.
 */
class LPCardFilterTest extends BaseV62TestCase{
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

    val lpCardService = getService[LPCardService]
    val response = lpCardService.addLPCard(requestBuilder.build())

    Assert.assertNotNull(response)
  }
  @Test
  def test_del(): Unit ={
    val requestBuilder = LPCardDelRequest.newBuilder()
    requestBuilder.setCardId("12345601")

    val lpCardService = getService[LPCardService]
    val response = lpCardService.delLPCard(requestBuilder.build())

    Assert.assertNotNull(response)
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

    val lpCardService = getService[LPCardService]
    val response = lpCardService.updateLPCard(requestBuilder.build())

    Assert.assertNotNull(response)
  }
  @Test
  def test_get(): Unit ={
    val requestBuilder = LPCardGetRequest.newBuilder()
    requestBuilder.setCardId("12345601")

    val lpCardService = getService[LPCardService]
    val response = lpCardService.getLPCard(requestBuilder.build())

    Assert.assertNotNull(response.getCard)
  }

}
