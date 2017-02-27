package nirvana.hall.v70.services


import nirvana.hall.api.services.SyncInfoLogManageService
import nirvana.hall.v70.internal.BaseV70TestCase
import org.junit.Test

/**
  * Created by yuchen on 2017/2/6.
  */
class SyncInfoLogManageServiceImplTest extends BaseV70TestCase{

  @Test
  def test_recordSyncDataIdentifyLog(): Unit ={
    val syncInfoLogManageService = getService[SyncInfoLogManageService]
    syncInfoLogManageService.recordSyncDataIdentifyLog("123456","TT","127.0.0.1","0","1")
    syncInfoLogManageService.recordSyncDataIdentifyLog("123456","TT","127.0.0.1","1")
    assert(true)
  }

  @Test
  def test_recordSyncDataExceptionLog(): Unit ={
    val syncInfoLogManageService = getService[SyncInfoLogManageService]
    syncInfoLogManageService.recordSyncDataExceptionLog("123456","error")
    assert(true)
  }
}
