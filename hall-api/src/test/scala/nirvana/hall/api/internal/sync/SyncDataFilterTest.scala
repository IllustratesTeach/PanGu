package nirvana.hall.api.internal.sync

import com.google.protobuf.ExtensionRegistry
import nirvana.hall.protocol.api.SyncDataProto._
import nirvana.hall.protocol.api.{SyncDataProto, TPCardProto}
import nirvana.hall.support.internal.RpcHttpClientImpl
import org.junit.{Assert, Test}

/**
 * Created by songpeng on 16/8/18.
 */
class SyncDataFilterTest {
  val registry = ExtensionRegistry.newInstance()
  TPCardProto.registerAllExtensions(registry)
  SyncDataProto.registerAllExtensions(registry)
  val httpClient = new RpcHttpClientImpl(registry)
  val url = "http://127.0.0.1:8080"

  @Test
  def test_SyncTPCard: Unit = {
    val request = SyncTPCardRequest.newBuilder()
    request.setDbid("1")
    request.setSeq(0)
    request.setSize(1)
    val baseResponse = httpClient.call(url, SyncTPCardRequest.cmd, request.build())
    val response = baseResponse.getExtension(SyncTPCardResponse.cmd)
    println(response.getSyncTPCardCount)
    Assert.assertEquals(response.getSyncTPCardCount, 1)
  }
  @Test
  def test_SyncLPCard: Unit = {
    val request = SyncLPCardRequest.newBuilder()
    request.setDbid("2")
    request.setSeq(0)
    request.setSize(1)
    val baseResponse = httpClient.call(url, SyncLPCardRequest.cmd, request.build())
    val response = baseResponse.getExtension(SyncLPCardResponse.cmd)
    println(response.getSyncLPCardCount)
    Assert.assertEquals(response.getSyncLPCardCount, 1)
  }
  @Test
  def test_SyncLPPalm: Unit = {
    val request = SyncLPPalmRequest.newBuilder()
    request.setDbid("2")
    request.setSeq(0)
    request.setSize(1)
    val baseResponse = httpClient.call(url, SyncLPPalmRequest.cmd, request.build())
    val response = baseResponse.getExtension(SyncLPPalmResponse.cmd)
    println(response.getSyncLPCardCount)
    Assert.assertEquals(response.getSyncLPCardCount, 1)
  }
  @Test
  def test_SyncCaseInfo: Unit = {
    val request = SyncCaseRequest.newBuilder()
    request.setDbid("2")
    request.setSeq(0)
    request.setSize(1)
    val baseResponse = httpClient.call(url, SyncCaseRequest.cmd, request.build())
    val response = baseResponse.getExtension(SyncCaseResponse.cmd)
    println(response.getSyncCaseCount)
    Assert.assertEquals(response.getSyncCaseCount, 1)
  }
}
