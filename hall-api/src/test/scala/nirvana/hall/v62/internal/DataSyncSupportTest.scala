package nirvana.hall.v62.internal

import monad.support.services.LoggerSupport
import nirvana.hall.protocol.v62.FPTProto.Case
import nirvana.hall.v62.services.{DatabaseTable, AncientClient}
import org.junit.Test

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-03
 */
class DataSyncSupportTest {
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


    sync.sendCaseData(DatabaseTable(2,4),protoCase.build())
  }
  @Test
  def test_send(): Unit ={
    val sync = createSender()
    //sync.sendData(DatabaseTable(2,2))
    sync.sendData(DatabaseTable(1,2))
  }
  private def createSender():DataSyncSupport={
    new DataSyncSupport with AncientClientSupport with LoggerSupport{
      /**
       * obtain AncientClient instance
       * @return AncientClient instance
       */
      override def createAncientClient: AncientClient = {
        AncientAppClient.connect("10.1.6.119",6898)
      }
    }
  }
}
