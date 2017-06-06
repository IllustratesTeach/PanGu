package nirvana.hall.v70.services

import nirvana.hall.api.services.AssistCheckRecordService
import nirvana.hall.v70.internal.BaseV70TestCase
import org.junit.Test

/**
  * Created by yuchen on 2017/4/21.
  */
class AssistCheckRecordServiceImplTest extends BaseV70TestCase{

  @Test
  def test_recordAssistCheck(): Unit ={
    val service = getService[AssistCheckRecordService]
    val hallAssistCheck = service.recordAssistCheck("3","1","A123","R37001","2")

  }

}
