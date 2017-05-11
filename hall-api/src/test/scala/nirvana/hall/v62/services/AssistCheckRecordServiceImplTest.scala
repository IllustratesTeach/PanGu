package nirvana.hall.v62.services

import nirvana.hall.api.internal.AssistCheckConstant
import nirvana.hall.api.services.AssistCheckRecordService
import nirvana.hall.v62.BaseV62TestCase
import org.junit.Test

/**
  * Created by yuchen on 2017/4/21.
  */
class AssistCheckRecordServiceImplTest extends BaseV62TestCase{

  @Test
  def test_recordAssistCheck(): Unit ={
    val service = getService[AssistCheckRecordService]
    val hallAssistCheck = service.recordAssistCheck("3","1","A123","R37001",AssistCheckConstant.XINGZHUAN)

  }

}
