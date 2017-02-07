package nirvana.hall.v62.services

import nirvana.hall.api.internal.BaseJniLoader
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.v62.BaseV62TestCase
import org.junit.{Assert, Test}

/**
  * Created by songpeng on 2017/1/24.
  */
class FPTServiceImplTest extends BaseV62TestCase with BaseJniLoader{

  @Test
  def test_getLogic02Rec: Unit ={
    val service = getService[FPTService]
    val logic02Rec = service.getLogic02Rec("1234567890")
    Assert.assertNotNull(logic02Rec)
  }

  @Test
  def test_addLogic02Rec: Unit ={
    val service = getService[FPTService]
    val fptFile = FPTFile.parseFromInputStream(getClass.getResourceAsStream("/3101045600002017020001.fpt"))
    val logic02Rec = fptFile.right.get.logic02Recs(0)
    service.addLogic02Res(logic02Rec)
  }

  @Test
  def test_addLogic03Rec: Unit ={
    val service = getService[FPTService]
    val fptFile = FPTFile.parseFromInputStream(getClass.getResourceAsStream("/A3202050008882016050249.fpt"))
    val logic03Rec = fptFile.right.get.logic03Recs(0)
    service.addLogic03Res(logic03Rec)
  }
}
