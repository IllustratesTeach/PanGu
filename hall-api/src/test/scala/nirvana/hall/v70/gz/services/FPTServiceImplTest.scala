package nirvana.hall.v70.gz.services

import junit.framework.Assert
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.v70.internal.BaseV70TestCase
import org.junit.Test

/**
  * Created by songpeng on 2017/5/26.
  */
class FPTServiceImplTest extends BaseV70TestCase{

  @Test
  def test_getLogic02Rec: Unit ={
    val service = getService[FPTService]
    val logic02Rec = service.getLogic02Rec("1234567890")
    Assert.assertNotNull(logic02Rec)
  }

  @Test
  def test_getLogic03Rec: Unit ={
    val service = getService[FPTService]
    val logic03Rec = service.getLogic03Rec("A0000000000000000000000")
    Assert.assertNotNull(logic03Rec)
  }
}
