package nirvana.hall.v62.internal

import com.google.protobuf.ByteString
import monad.support.services.LoggerSupport
import nirvana.hall.protocol.api.FPTProto
import nirvana.hall.protocol.api.FPTProto.{Case, FingerFgp, LPCard, TPCard}
import nirvana.hall.v62.internal.c.gnetlib.{gnetcsr, reqansop}
import nirvana.hall.v62.services.{DatabaseTable, V62ServerAddress}
import org.junit.Test

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-03
 */
class DataSyncSupportTest {
  private val address = V62ServerAddress("192.168.1.51",6798,10,30,"afisadmin",Some("helloafis"))
  @Test
  def test_send_template(): Unit ={
    val sync = createSender()

    val tpCard = TPCard.newBuilder()
    tpCard.setStrCardID(System.currentTimeMillis().toString)
    val blobBuilder = tpCard.addBlobBuilder()
    blobBuilder.setStMntBytes(ByteString.readFrom(getClass.getResourceAsStream("/t.mnt")))
    blobBuilder.setStImageBytes(ByteString.readFrom(getClass.getResourceAsStream("/t.cpr")))
    blobBuilder.setType(FPTProto.ImageType.IMAGETYPE_FINGER)
    blobBuilder.setFgp(FingerFgp.FINGER_R_LITTLE)

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
    textBuilder.setStrNation ("CHINA")
    textBuilder.setStrRace ("asdf")
    textBuilder.setStrCertifType ("1")
    textBuilder.setStrCertifID ("1")
    textBuilder.setBHasCriminalRecord (true)
    textBuilder.setStrCriminalRecordDesc ("asdf")
    textBuilder.setStrPremium ("asdf")
    textBuilder.setNXieChaFlag (1)
    textBuilder.setStrXieChaRequestUnitName ("asdfasdf")
    textBuilder.setStrXieChaRequestUnitCode ("123")
    textBuilder.setNXieChaLevel (1)
    textBuilder.setStrXieChaForWhat ("哈哈哈哈哈")
    textBuilder.setStrRelPersonNo ("12")
    textBuilder.setStrRelCaseNo ("12")
    textBuilder.setStrXieChaTimeLimit ("12")
    textBuilder.setStrXieChaDate ("")
    textBuilder.setStrXieChaRequestComment ("")
    textBuilder.setStrXieChaContacter ("")
    textBuilder.setStrXieChaTelNo ("")
    textBuilder.setStrShenPiBy ("")






    sync.addTemplateData(address,DatabaseTable(1,2),tpCard.build())
    sync.updateTemplateData(address,DatabaseTable(1,2),tpCard.build())
    println(tpCard.getStrCardID)
    sync.deleteTemplateData(address,DatabaseTable(1,2),tpCard.getStrCardID)

  }
  @Test
  def test_send_case(): Unit ={
    val sync = createSender()
    //sync.sendData(DatabaseTable(2,2))
    val protoCase = Case.newBuilder()
    protoCase.setStrCaseID(System.currentTimeMillis().toString)

    val textBuilder = protoCase.getTextBuilder
    textBuilder.setStrCaseType1 ("010000")
    textBuilder.setStrCaseType2 ("010100")
    textBuilder.setStrCaseType3 ("01")
    textBuilder.setStrSuspArea1Code ("41")
    textBuilder.setStrSuspArea2Code ("30")
    textBuilder.setStrSuspArea3Code ("28")
    textBuilder.setStrCaseOccurDate ("20150212")
    textBuilder.setStrCaseOccurPlaceCode ("1232")
    textBuilder.setStrCaseOccurPlace ("北京市朝阳区beijingchaoyang")
    textBuilder.setNSuperviseLevel (1)
    textBuilder.setStrExtractUnitCode ("123123")
    textBuilder.setStrExtractUnitName ("unitName 单位名称")
    textBuilder.setStrExtractor ("who")
    textBuilder.setStrExtractDate ("20150212")
    textBuilder.setStrMoneyLost ("asdf")
    textBuilder.setStrPremium ("1")
    textBuilder.setBPersonKilled (true)
    textBuilder.setStrComment ("test 正在测试")
    textBuilder.setNCaseState (1)
    textBuilder.setNXieChaState (1)
    textBuilder.setNCancelFlag (0)
    textBuilder.setStrXieChaDate ("20150101")
    textBuilder.setStrXieChaRequestUnitName ("北京市beijing")
    textBuilder.setStrXieChaRequestUnitCode ("510000")


    sync.addCaseData(address,DatabaseTable(2,4),protoCase.build())
    sync.updateCaseData(address,DatabaseTable(2,4),protoCase.build())
    sync.deleteCaseData(address,DatabaseTable(2,4),protoCase.getStrCaseID)
  }
  @Test
  def test_latent(): Unit ={
    val sync = createSender()
    val lpCard = LPCard.newBuilder()
    lpCard.setStrCardID(System.currentTimeMillis().toString)
    val blobBuilder = lpCard.getBlobBuilder()
    blobBuilder.setStMntBytes(ByteString.readFrom(getClass.getResourceAsStream("/lf.mnt")))
    blobBuilder.setStImageBytes(ByteString.readFrom(getClass.getResourceAsStream("/lf.img")))
    blobBuilder.setType(FPTProto.ImageType.IMAGETYPE_FINGER)


    val textBuilder = lpCard.getTextBuilder
    textBuilder.setStrSeq ("01")
    textBuilder.setStrRemainPlace ("中国")
    textBuilder.setStrRidgeColor ("1")
    textBuilder.setBDeadBody (true)
    textBuilder.setStrDeadPersonNo ("123123123")
    textBuilder.setNXieChaState (1)
    textBuilder.setNBiDuiState (1)
    textBuilder.setStrStart ("")
    textBuilder.setStrEnd ("")


    sync.addLatentData(address,DatabaseTable(2,2),lpCard.build())
    sync.updateLatentData(address,DatabaseTable(2,2),lpCard.build())
    sync.deleteLatentData(address,DatabaseTable(2,2),lpCard.getStrCardID)
  }
  private def createSender():DataSyncSupport={
    new DataSyncSupport with AncientClientSupport with LoggerSupport with reqansop with gnetcsr{

      override def serverAddress: V62ServerAddress = address
    }
  }
}
