package nirvana.hall.v70.services.service

import scala.collection.mutable


/**
  * Created by Administrator on 2017/4/21.
  */
trait GetPKIDService {

    def getDataInfo(queryid:String,ora_sid:String): mutable.ListBuffer[mutable.HashMap[String,Any]]

}
