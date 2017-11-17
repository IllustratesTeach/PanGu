package nirvana.hall.v70.gz.services

import nirvana.hall.api.services.CaseInfoService
import nirvana.hall.v70.gz.sys.BaseV70TestCase
import org.junit.{Assert, Test}

/**
  * Created by songpeng on 2017/6/29.
  */
class CaseInfoServiceImplTest extends BaseV70TestCase{

  @Test
  def test_get: Unit ={
    val service = getService[CaseInfoService]
    val caseInfo = service.getCaseInfo("A0000000000000000000000")
    Assert.assertNotNull(caseInfo)
    println(caseInfo.getStrCaseID)

  }
}
