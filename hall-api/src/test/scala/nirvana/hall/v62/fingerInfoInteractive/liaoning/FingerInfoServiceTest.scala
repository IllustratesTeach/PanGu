package nirvana.hall.v62.fingerInfoInteractive.liaoning


import nirvana.hall.v62.BaseV62TestCase
import nirvana.hall.v62.fingerInfoInteractive.liaoning.services.DataService
import org.junit.Test

/**
  * Created by yuchen on 2016/12/16.
  */
class FingerInfoServiceTest extends BaseV62TestCase{

  @Test
  def getDataInfo(): Unit ={
    val dataInfoService = getService[DataService]
    val resultList = dataInfoService.getDataInfo(10)
    assert(resultList.length>=0,"测试失败"+resultList)
  }
}
