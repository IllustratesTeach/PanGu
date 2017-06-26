package nirvana.hall.v62.fingerInfoInteractive.shanghai.services

/**
  * Created by ssj on 2017/03/09.
  */
trait DataService {


    def getDataInfo(rowno:Int): scala.collection.mutable.HashMap[String,Any]

    def insertCard(card:String,state:Int):Int

}
