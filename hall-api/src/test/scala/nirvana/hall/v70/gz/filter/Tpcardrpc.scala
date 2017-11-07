package nirvana.hall.v70.gz.filter


import com.google.protobuf.ExtensionRegistry
import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.protocol.api.{FPTProto, TPCardProto}
import nirvana.hall.protocol.api.FPTProto.{AdminfoData, FingerFgp, TPCard}
import nirvana.hall.protocol.api.QueryProto.{QuerySendCaptureRequest, QuerySendCaptureResponse}
import nirvana.hall.protocol.api.TPCardProto.{TPCardAddRequest, TPCardAddResponse, TPCardGetRequest, TPCardGetResponse}
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.support.internal.RpcHttpClientImpl
import org.junit.{Assert, Test}
/**
  * Created by Administrator on 2017/9/26.
  */
class Tpcardrpc {

  @Test
  def test_rpc(): Unit = {

    //新建
    val tpCard = TPCard.newBuilder()
    tpCard.setStrCardID("1243908874223689")
    tpCard.setStrPersonID("12439088742236890")
    val blobBuilder = tpCard.addBlobBuilder()
    //blobBuilder.setStMntBytes(ByteString.readFrom(getClass.getResourceAsStream("/t.mnt")))
    //blobBuilder.setStImageBytes(ByteString.readFrom(getClass.getResourceAsStream("/t.cpr")))
    blobBuilder.setType(FPTProto.ImageType.IMAGETYPE_FINGER)
    blobBuilder.setFgp(FingerFgp.FINGER_R_THUMB)

    val ss = AdminfoData.newBuilder()
    ss.setCreator("dajiade ren ")
    ss.setUpdator("dajiade ren s")
    ss.setUpdateUnitCode("5200001")
    ss.setUpdateDatetime("20170808080808")
    ss.setCreateUnitCode("5200001")
    ss.setCreateDatetime("20170808080808")
    val blobBuilders = tpCard.setAdmData(ss)

    val textBuilder = tpCard.getTextBuilder
    textBuilder.setStrName("NellyLiu")
    textBuilder.setStrAliasName("韩明名")
    textBuilder.setNSex(1)
    textBuilder.setStrBirthDate("19800911")
    textBuilder.setStrIdentityNum("123123")
    textBuilder.setStrBirthAddrCode("123123")
    textBuilder.setStrBirthAddr("中国")
    textBuilder.setStrHuKouPlaceCode("13")
    textBuilder.setStrHuKouPlaceTail("中国贵州")
    textBuilder.setStrPersonType("8a20fb2544baa8450144babe5f2e0019")
    textBuilder.setStrAddrCode("12")
    textBuilder.setStrAddr("中国CHINA")

    textBuilder.setStrCaseType1("1")

    textBuilder.setStrPrintUnitCode("520000000000")
    textBuilder.setStrPrintUnitName("贵州省公安厅")
    textBuilder.setStrPrinter("管理员")
    textBuilder.setStrPrintDate("20171019142114")
    textBuilder.setStrComment("中国CHINA666666")
    textBuilder.setStrNation("156")
    textBuilder.setStrRace("01")

    textBuilder.setBHasCriminalRecord(true)
    textBuilder.setStrCriminalRecordDesc("杀人案")
    textBuilder.setStrPremium("50000")
    textBuilder.setNXieChaFlag(1)
    textBuilder.setStrXieChaRequestUnitName("松松松松")
    textBuilder.setStrXieChaRequestUnitCode("123")
    textBuilder.setNXieChaLevel(1)
    textBuilder.setStrXieChaForWhat("大家")
    textBuilder.setStrRelPersonNo("12")
    textBuilder.setStrRelCaseNo("12")
    textBuilder.setStrXieChaTimeLimit("12")
    textBuilder.setStrXieChaDate("20171019")
    textBuilder.setStrXieChaRequestComment("收拾收拾")
    textBuilder.setStrXieChaContacter("333")
    textBuilder.setStrXieChaTelNo("123123")
    textBuilder.setStrShenPiBy("333")

     val registry = ExtensionRegistry.newInstance()
     TPCardProto.registerAllExtensions(registry)
     val httpClients = new RpcHttpClientImpl(registry)
     val tpcardaddServer = "http://127.0.0.1:8081/tpcard"
     val request = TPCardAddRequest.newBuilder()
      request.setCard(tpCard)
      val baseResponse = httpClients.call(tpcardaddServer, TPCardAddRequest.cmd, request.build())
      baseResponse.getStatus match {
        case CommandStatus.OK =>
          val tPCards = baseResponse.getExtension(TPCardAddResponse.cmd)
          val tPCard = baseResponse.hasExtension(TPCardAddResponse.cmd)
          Assert.assertNotNull(tPCard)

        case CommandStatus.FAIL =>
          throw new IllegalAccessException("fail to TPCardAdd,server message:%s".format(baseResponse.getMsg))
      }
    }
///tpcard
  @Test
  def getTpcard() : Unit = {
    val registry = ExtensionRegistry.newInstance()
    TPCardProto.registerAllExtensions(registry)
    val httpClients = new RpcHttpClientImpl(registry)
    val tpcardaddServer = "http://127.0.0.1:8081"
    val request = TPCardGetRequest.newBuilder()
    request.setCardId("1243908874223689")
    val baseResponse = httpClients.call(tpcardaddServer, TPCardGetRequest.cmd, request.build())
    baseResponse.getStatus match {
      case CommandStatus.OK =>
        val tPCards = baseResponse.getExtension(TPCardGetResponse.cmd)
        val tPCard = baseResponse.hasExtension(TPCardGetResponse.cmd)
        Assert.assertNotNull(tPCard)

      case CommandStatus.FAIL =>
        throw new IllegalAccessException("fail to TPCardGet,server message:%s".format(baseResponse.getMsg))
    }
  }

  @Test
  def getQuery() : Unit = {
    val registry = ExtensionRegistry.newInstance()
    TPCardProto.registerAllExtensions(registry)
    val httpClients = new RpcHttpClientImpl(registry)
    val tpcardaddServer = "http://127.0.0.1:8081"
    val request = QuerySendCaptureRequest.newBuilder()
    val matchtask = MatchTask.newBuilder()
    matchtask.setMatchId("12439088742236890")
    matchtask.setMatchType(MatchType.FINGER_TT)
    matchtask.setScoreThreshold(100)
    matchtask.setPriority(5)
    matchtask.setObjectId(200)
    matchtask.setCommitUser("1")
    matchtask.setComputerIp("192.168.1.110")
    matchtask.setUserUnitCode("520100000000")
    request.setMatchTask(matchtask)
    val baseResponse = httpClients.call(tpcardaddServer, QuerySendCaptureRequest.cmd, request.build())
    baseResponse.getStatus match {
      case CommandStatus.OK =>
        val tPCards = baseResponse.getExtension(QuerySendCaptureResponse.cmd)
        val tPCard = baseResponse.hasExtension(QuerySendCaptureResponse.cmd)
        Assert.assertNotNull(tPCard)

      case CommandStatus.FAIL =>
        throw new IllegalAccessException("fail to TPCardGet,server message:%s".format(baseResponse.getMsg))
    }
  }

}
