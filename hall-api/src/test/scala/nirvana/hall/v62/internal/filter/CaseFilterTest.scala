package nirvana.hall.v62.internal.filter

import nirvana.hall.api.services.CaseInfoService
import nirvana.hall.protocol.api.FPTProto.Case
import nirvana.hall.v62.BaseV62TestCase
import org.junit.{Assert, Test}

/**
  * Created by songpeng on 15/11/15.
  */
class CaseFilterTest extends BaseV62TestCase{
   @Test
   def test_add(): Unit ={
     val caseInfo = Case.newBuilder()
     caseInfo.setStrCaseID("123456")
     caseInfo.setNCaseFingerCount(0)
     caseInfo.setNSendFingerCount(0)
     val textBuilder = caseInfo.getTextBuilder
     textBuilder.setStrCaseOccurDate("20151111")
     textBuilder.setBPersonKilled(false)
     textBuilder.setNCancelFlag(1)
     textBuilder.setNCaseState(1)
     textBuilder.setNSuperviseLevel(1)
     textBuilder.setNXieChaState(1)
     textBuilder.setStrCaseOccurPlace("贵阳市")
     textBuilder.setStrCaseOccurPlaceCode("520000")
     textBuilder.setStrCaseType1("010000")
     textBuilder.setStrComment("comment")
     textBuilder.setStrExtractDate("20151111")
     textBuilder.setStrExtractUnitCode("520000")
     textBuilder.setStrExtractUnitName("gui zhou")
     textBuilder.setStrMoneyLost("100")
     textBuilder.setStrPremium("1000")
     textBuilder.setStrSuspArea1Code("520100")

     val caseInfoService = getService[CaseInfoService]
     caseInfoService.addCaseInfo(caseInfo.build())
   }
  @Test
  def test_del(): Unit ={
    val caseInfoService = getService[CaseInfoService]
    caseInfoService.delCaseInfo("123456")
  }
  @Test
  def test_update(): Unit ={
    val caseInfo = Case.newBuilder()
    caseInfo.setStrCaseID("123456")
    caseInfo.setNCaseFingerCount(0)
    caseInfo.setNSendFingerCount(0)
    val textBuilder = caseInfo.getTextBuilder
    textBuilder.setStrCaseOccurDate("20151111")
    textBuilder.setBPersonKilled(false)
    textBuilder.setNCancelFlag(1)
    textBuilder.setNCaseState(1)
    textBuilder.setNSuperviseLevel(1)
    textBuilder.setNXieChaState(1)
    textBuilder.setStrCaseOccurPlace("贵阳市")
    textBuilder.setStrCaseOccurPlaceCode("520000")
    textBuilder.setStrCaseType1("020000")
    textBuilder.setStrComment("案件备注信息")
    textBuilder.setStrExtractDate("20151111")
    textBuilder.setStrExtractUnitCode("520000")
    textBuilder.setStrExtractUnitName("情报中心")
    textBuilder.setStrMoneyLost("1000")
    textBuilder.setStrPremium("10000")
    textBuilder.setStrSuspArea1Code("520300")

    val caseInfoService = getService[CaseInfoService]
    caseInfoService.updateCaseInfo(caseInfo.build())
  }
  @Test
  def test_get(): Unit ={
    val caseInfoService = getService[CaseInfoService]
    val caseInfo = caseInfoService.getCaseInfo("3100000000002016070601")

    Assert.assertNotNull(caseInfo)
  }

 }
