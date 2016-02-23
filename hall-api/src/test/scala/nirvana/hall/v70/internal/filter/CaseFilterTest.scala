package nirvana.hall.v70.internal.filter

import nirvana.hall.api.services.CaseInfoService
import nirvana.hall.protocol.api.CaseProto._
import nirvana.hall.v70.internal.BaseV70TestCase
import org.junit.{Assert, Test}

/**
 * Created by songpeng on 16/2/17.
 */
class CaseFilterTest extends BaseV70TestCase{

  @Test
  def test_add(): Unit ={
    val requestBuilder = CaseAddRequest.newBuilder()
    val caseInfo = requestBuilder.getCaseBuilder
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
    val response = caseInfoService.addCaseInfo(requestBuilder.build())
    Assert.assertNotNull(response)
  }
  @Test
  def test_del(): Unit ={
    val requestBuilder = CaseDelRequest.newBuilder()
    requestBuilder.setCaseId("123456")
    val caseInfoService = getService[CaseInfoService]
    val response = caseInfoService.delCaseInfo(requestBuilder.build())
    Assert.assertNotNull(response)
  }
  @Test
  def test_update(): Unit ={
    val requestBuilder = CaseUpdateRequest.newBuilder()
    val caseInfo = requestBuilder.getCaseBuilder
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
    val response = caseInfoService.updateCaseInfo(requestBuilder.build())
    Assert.assertNotNull(response)
  }
  @Test
  def test_get(): Unit ={
    val requestBuilder = CaseGetRequest.newBuilder()
    requestBuilder.setCaseId("123456")

    val caseInfoService = getService[CaseInfoService]
    val response = caseInfoService.getCaseInfo(requestBuilder.build())

    Assert.assertNotNull(response.getCase)
  }

}
