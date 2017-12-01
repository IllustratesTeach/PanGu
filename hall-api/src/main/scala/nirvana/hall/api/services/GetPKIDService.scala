package nirvana.hall.api.services

import scala.collection.mutable.ListBuffer

/**
  * Created by Administrator on 2017/4/21.
  */
trait GetPKIDService {

    def getDataInfo(queryid:String,ora_sid:String): ListBuffer[scala.collection.mutable.HashMap[String,Any]]

    def getDatabyPKIDInfo(pkid:String): ListBuffer[scala.collection.mutable.HashMap[String,Any]]

}
