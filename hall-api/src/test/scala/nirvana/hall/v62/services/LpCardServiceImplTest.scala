package nirvana.hall.v62.services

import com.google.protobuf.ByteString
import nirvana.hall.api.services.LPCardService
import nirvana.hall.protocol.api.FPTProto.{FingerFgp, ImageType, LPCard, PatternType}
import nirvana.hall.v62.BaseV62TestCase
import org.junit.{Assert, Test}

/**
 * Created by songpeng on 15/11/15.
 */
class LpCardServiceImplTest extends BaseV62TestCase{
  @Test
  def test_add(): Unit ={
    val lpCard = LPCard.newBuilder()
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
    lpCardService.addLPCard(lpCard.build())
  }
  @Test
  def test_del(): Unit ={
    val lpCardService = getService[LPCardService]
    lpCardService.delLPCard("12345601")
  }
  @Test
  def test_update(): Unit ={
    val lpCard = LPCard.newBuilder()
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
    lpCardService.updateLPCard(lpCard.build())
  }
  @Test
  def test_get(): Unit ={
    val lpCardService = getService[LPCardService]
    val card = lpCardService.getLPCard("640000001999201009010001")

    Assert.assertNotNull(card)
  }

}
