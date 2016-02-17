package nirvana.hall.v62.internal.filter

import nirvana.hall.api.services.ProtobufRequestHandler
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse, ResponseStatus}
import nirvana.hall.protocol.v62.lp.CaseProto._
import org.apache.tapestry5.ioc.{Registry, RegistryBuilder}
import org.junit.{Assert, Test}

/**
  * Created by songpeng on 15/11/15.
  */
class CaseFilterTest {
   private val modules = Seq[String](
    "nirvana.hall.api.LocalProtobufModule",
    "nirvana.hall.api.LocalApiServiceModule",
    "nirvana.hall.v62.LocalV62ServiceModule",
    "nirvana.hall.v62.internal.filter.TestModule").map(Class.forName)
   protected var registry:Registry = RegistryBuilder.buildAndStartupRegistry(modules: _*)

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

     val handler = registry.getService(classOf[ProtobufRequestHandler])
     val protobufRequest = BaseRequest.newBuilder().setToken("asdf").setVersion(102)
     protobufRequest.setExtension(CaseAddRequest.cmd, requestBuilder.build())
     val protobufResponse = BaseResponse.newBuilder()

     handler.handle(protobufRequest.build(), protobufResponse)

     Assert.assertEquals(ResponseStatus.OK,protobufResponse.getStatus)
   }
  @Test
  def test_del(): Unit ={
    val requestBuilder = CaseDelRequest.newBuilder()
    requestBuilder.setCaseId("123456")
    val handler = registry.getService(classOf[ProtobufRequestHandler])
    val protobufRequest = BaseRequest.newBuilder().setToken("asdf").setVersion(102)
    protobufRequest.setExtension(CaseDelRequest.cmd, requestBuilder.build())
    val protobufResponse = BaseResponse.newBuilder()

    handler.handle(protobufRequest.build(), protobufResponse)

    Assert.assertEquals(ResponseStatus.OK,protobufResponse.getStatus)
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

    val handler = registry.getService(classOf[ProtobufRequestHandler])
    val protobufRequest = BaseRequest.newBuilder().setToken("asdf").setVersion(102)
    protobufRequest.setExtension(CaseUpdateRequest.cmd, requestBuilder.build())
    val protobufResponse = BaseResponse.newBuilder()

    handler.handle(protobufRequest.build(), protobufResponse)
    Assert.assertEquals(ResponseStatus.OK,protobufResponse.getStatus)
  }
  @Test
  def test_get(): Unit ={
    val requestBuilder = CaseGetRequest.newBuilder()
    requestBuilder.setCaseId("123456")
    val handler = registry.getService(classOf[ProtobufRequestHandler])
    val protobufRequest = BaseRequest.newBuilder().setToken("asdf").setVersion(102)
    protobufRequest.setExtension(CaseGetRequest.cmd, requestBuilder.build())
    val protobufResponse = BaseResponse.newBuilder()

    handler.handle(protobufRequest.build(), protobufResponse)

    Assert.assertEquals(ResponseStatus.OK,protobufResponse.getStatus)
    Assert.assertNotNull(protobufResponse.getExtension(CaseGetResponse.cmd).getCase.getText)
  }

 }
