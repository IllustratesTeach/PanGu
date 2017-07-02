package nirvana.hall.webservice

import javax.activation.{DataHandler, FileDataSource}

import nirvana.hall.webservice.services.TaskHandlerService
import nirvana.hall.webservice.util.AFISConstant
import org.junit.Test

/**
  * Created by yuchen on 2017/4/26.
  */
class TaskHandlerServiceImplTest extends BaseTestCase{

  @Test
  def test_queryTaskHandler(): Unit ={
    try{
      val service = getService[TaskHandlerService]
      service.queryTaskHandler(new DataHandler(new FileDataSource("/Users/yuchen/fpt/44200322322242362722432.fpt"))
        ,AFISConstant.XINGZHUAN)
    }catch{
      case ex:Exception => println(ex.getMessage)
    }
  }

}
