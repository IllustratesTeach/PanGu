package nirvana.hall.v62.fingerInfoInteractive.shanghai

import nirvana.hall.v62.BaseV62TestCase
import nirvana.hall.v62.fingerInfoInteractive.shanghai.internal.ShangHaiInteractiveCron
import nirvana.hall.v62.fingerInfoInteractive.shanghai.services.DataService
import org.junit.Test

/**
  * Created by ssj on 2017/03/09.
  */
class FingerInfoServiceTest extends BaseV62TestCase{

  @Test
  def getDataInfo(): Unit ={
    val dataInfoService = getService[DataService]
    val resultList = dataInfoService.getDataInfo(1)
    assert(resultList.size>0,"测试失败"+resultList)
  }

  @Test
  def getShangHaiCron(): Unit ={
    val dataInfoService = getService[ShangHaiInteractiveCron]
    val resultList = dataInfoService.doWork
  }
}
