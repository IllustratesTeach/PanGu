package nirvana.hall.v62.fingerInfoInteractive.liaoning.services


import org.springframework.transaction.annotation.Transactional

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Created by yuchen on 2016/12/16.
  */
trait DataService {


    def getDataInfo(rowno:Int): ListBuffer[mutable.HashMap[String,Any]]

    @Transactional
    def backAndDeleteDataInfo(xlh:String): Unit
}
