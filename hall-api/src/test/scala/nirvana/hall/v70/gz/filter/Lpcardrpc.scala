package nirvana.hall.v70.gz.filter

import com.google.protobuf.ExtensionRegistry
import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.protocol.api.CaseProto.{CaseGetRequest, CaseGetResponse}
import nirvana.hall.protocol.api.LPCardProto.{LPCardGetRequest, LPCardGetResponse}
import nirvana.hall.protocol.api.TPCardProto
import nirvana.hall.support.internal.RpcHttpClientImpl
import org.junit.{Assert, Test}

/**
  * Created by Administrator on 2017/9/26.
  */
class Lpcardrpc {

  @Test
  def getTpcard() : Unit = {
    val registry = ExtensionRegistry.newInstance()
    TPCardProto.registerAllExtensions(registry)
    val httpClients = new RpcHttpClientImpl(registry)
    val tpcardaddServer = "http://127.0.0.1:8081"
    val request = LPCardGetRequest.newBuilder()
    request.setCardId("A520000201710171111111101")
    val baseResponse = httpClients.call(tpcardaddServer, LPCardGetRequest.cmd, request.build())
    baseResponse.getStatus match {
      case CommandStatus.OK =>
        val tPCards = baseResponse.getExtension(LPCardGetResponse.cmd)
        val tPCard = baseResponse.hasExtension(LPCardGetResponse.cmd)
        Assert.assertNotNull(tPCard)

      case CommandStatus.FAIL =>
        throw new IllegalAccessException("fail to LPCardGet,server message:%s".format(baseResponse.getMsg))
    }
  }

  @Test
  def getCase() : Unit = {
    val registry = ExtensionRegistry.newInstance()
    TPCardProto.registerAllExtensions(registry)
    val httpClients = new RpcHttpClientImpl(registry)
    val tpcardaddServer = "http://127.0.0.1:8081"
    val request = CaseGetRequest.newBuilder()
    request.setCaseId("A5200002017101711111111")
    val baseResponse = httpClients.call(tpcardaddServer, CaseGetRequest.cmd, request.build())
    baseResponse.getStatus match {
      case CommandStatus.OK =>
        val tPCards = baseResponse.getExtension(CaseGetResponse.cmd)
        val tPCard = baseResponse.hasExtension(CaseGetResponse.cmd)
        Assert.assertNotNull(tPCard)

      case CommandStatus.FAIL =>
        throw new IllegalAccessException("fail to CaseGet,server message:%s".format(baseResponse.getMsg))
    }
  }
}
