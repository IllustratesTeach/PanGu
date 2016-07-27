package nirvana.hall.v70.service

import com.google.protobuf.ByteString
import nirvana.hall.api.services.LPPalmService
import nirvana.hall.protocol.api.FPTProto._
import nirvana.hall.v70.internal.BaseV70TestCase
import org.junit.{Assert, Test}

/**
 * Created by songpeng on 16/7/27.
 */
class LPPalmServiceImplTest extends BaseV70TestCase {

  @Test
  def test_addLPCard(): Unit = {
    val service = getService[LPPalmService]
    val lpCard = LPCard.newBuilder()
    lpCard.setStrCardID("12345604")
    val textBuilder = lpCard.getTextBuilder
    textBuilder.setStrCaseId("123456")
    textBuilder.setStrSeq("04")
    textBuilder.setStrRemainPlace("杯子")
    textBuilder.setStrRidgeColor("1")
    textBuilder.setBDeadBody(false)
    textBuilder.setNBiDuiState(1)
    textBuilder.setNXieChaState(1)

    val blobBuilder = lpCard.getBlobBuilder
    blobBuilder.setPalmFgp(PalmFgp.PALM_LEFT)
    blobBuilder.addRp(PatternType.PATTERN_LEFTLOOP)
    blobBuilder.setStImageBytes(ByteString.readFrom(getClass.getResourceAsStream("/lf.img")))
    blobBuilder.setType(ImageType.IMAGETYPE_PALM)

    service.addLPCard(lpCard.build(), Option("2"))
  }

  @Test
  def test_del(): Unit = {
    val service = getService[LPPalmService]
    service.delLPCard("12345604")
  }

  @Test
  def test_update(): Unit = {
    val lpCard = LPCard.newBuilder()
    lpCard.setStrCardID("12345604")
    val textBuilder = lpCard.getTextBuilder
    textBuilder.setStrCaseId("123456")
    textBuilder.setStrSeq("04")
    textBuilder.setStrRemainPlace("桌子")
    textBuilder.setStrRidgeColor("2")
    textBuilder.setBDeadBody(false)
    textBuilder.setNBiDuiState(1)
    textBuilder.setNXieChaState(1)

    val blobBuilder = lpCard.getBlobBuilder
    blobBuilder.setPalmFgp(PalmFgp.PALM_RIGHT)
    blobBuilder.setStImageBytes(ByteString.readFrom(getClass.getResourceAsStream("/lf.img")))
    blobBuilder.setType(ImageType.IMAGETYPE_PALM)

    val service = getService[LPPalmService]
    service.updateLPCard(lpCard.build())
  }

  @Test
  def test_get(): Unit = {
    val service = getService[LPPalmService]
    val card = service.getLPCard("12345604")

    Assert.assertNotNull(card)

  }
}
