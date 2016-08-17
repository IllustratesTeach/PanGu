package nirvana.hall.v62.services

import nirvana.hall.api.services.sync.{SyncCaseInfoService, SyncLPCardService, SyncTPCardService}
import nirvana.hall.protocol.api.SyncDataProto.{SyncCaseResponse, SyncLPCardResponse, SyncTPCardResponse}
import nirvana.hall.v62.BaseV62TestCase
import org.junit.{Assert, Test}

/**
 * Created by songpeng on 16/6/18.
 */
class SyncDataServiceTest extends BaseV62TestCase{

  @Test
  def test_syncTPCard(): Unit ={
    val syncTPCardService = getService[SyncTPCardService]
    val response = SyncTPCardResponse.newBuilder()
    syncTPCardService.syncTPCard(response, 0, 10)
    Assert.assertTrue(response.getSyncTPCardCount >= 10)
  }

  @Test
  def test_syncLPCard(): Unit ={
    val syncLPCardService = getService[SyncLPCardService]
    val response = SyncLPCardResponse.newBuilder()
    syncLPCardService.syncLPCard(response, 0, 10)
    Assert.assertTrue(response.getSyncLPCardCount >= 10)
  }

  @Test
  def test_syncCaseInfo(): Unit ={
    val syncCaseInfoService = getService[SyncCaseInfoService]
    val response = SyncCaseResponse.newBuilder()
    syncCaseInfoService.syncCaseInfo(response, 0, 10)
    Assert.assertTrue(response.getSyncCaseCount >= 10)
  }
}
