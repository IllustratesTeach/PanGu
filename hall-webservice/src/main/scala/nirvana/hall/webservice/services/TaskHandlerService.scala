package nirvana.hall.webservice.services

import javax.activation.DataHandler

/**
  * Created by yuchen on 2017/4/25.
  */
trait TaskHandlerService {
  def queryTaskHandler(dataHandler: DataHandler,source:String): Unit
}
