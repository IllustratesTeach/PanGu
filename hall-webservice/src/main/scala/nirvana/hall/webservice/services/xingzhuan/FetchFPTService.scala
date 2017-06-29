package nirvana.hall.webservice.services.xingzhuan

import scala.collection.mutable

/**
  * Created by Administrator on 2017/5/9.
  */
trait FetchFPTService {

  def fetchAssistTask(): mutable.ListBuffer[mutable.HashMap[String,Any]]

  def fetchFPT(): Unit
}