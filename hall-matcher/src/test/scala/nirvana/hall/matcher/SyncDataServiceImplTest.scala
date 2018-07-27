package nirvana.hall.matcher

import nirvana.hall.matcher.service.SyncDataService
import nirvana.hall.protocol.matcher.TextQueryProto.TextData
import nirvana.protocol.NirvanaTypeDefinition.SyncDataType
import nirvana.protocol.SyncDataProto.SyncDataRequest
import org.junit.{Assert, Test}


/**
  * Created by songpeng on 2017/12/1.
  */
class SyncDataServiceImplTest extends BaseHallMatcherTestCase{

  @Test
  def test_syncPerson: Unit ={
    val service = getService[SyncDataService]
    val request = SyncDataRequest.newBuilder()
    request.setSize(10)
    request.setTimestamp(0)
    request.setSyncDataType(SyncDataType.PERSON)
    val response = service.syncData(request.build())
    Assert.assertEquals(response.getSyncDataCount, 10)
  }
  @Test
  def test_syncCase: Unit ={
    val service = getService[SyncDataService]
    val request = SyncDataRequest.newBuilder()
    request.setSize(5)
    request.setTimestamp(1)
    request.setSyncDataType(SyncDataType.CASE)
    val response = service.syncData(request.build())
    println(TextData.parseFrom(response.getSyncData(0).getData))
    Assert.assertTrue(response.getSyncDataCount >=5)
  }
  @Test
  def test_syncTemplateFinger: Unit ={
    val service = getService[SyncDataService]
    val request = SyncDataRequest.newBuilder()
    request.setSize(10)
    request.setTimestamp(0)
    request.setSyncDataType(SyncDataType.TEMPLATE_FINGER)
    val response = service.syncData(request.build())
    Assert.assertTrue(response.getSyncDataCount >= 10)
    request.setSyncDataType(SyncDataType.TEMPLATE_PALM)
  }
  @Test
  def test_syncLatentFinger: Unit ={
    val service = getService[SyncDataService]
    val request = SyncDataRequest.newBuilder()
    request.setSize(5)
    request.setTimestamp(0)
    request.setSyncDataType(SyncDataType.LATENT_FINGER)
    val response = service.syncData(request.build())
    Assert.assertTrue(response.getSyncDataCount > 1)
  }
}
