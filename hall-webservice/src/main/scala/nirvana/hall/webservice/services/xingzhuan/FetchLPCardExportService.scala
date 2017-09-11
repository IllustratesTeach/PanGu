package nirvana.hall.webservice.services.xingzhuan

import scala.collection.mutable.ArrayBuffer

/**
  * Created by sjr on 2017/4/25.
  */
trait FetchLPCardExportService {


  /**
    * 获取待上报的该批次的现场指纹案件集合
    *
    * @param size
    * @param dbId
    */
  def fetchCardIdListBySize_AssistCheck(size: Int, dbId: Option[String] = None): ArrayBuffer[(String)]

  /**
    * 保存上报结果
    *
    * @param uuid
    * @param cardID
    * @param typ
    * @param msg
    * @param errorMsg
    */
  def saveResult(uuid: String,cardID: String, typ: String,msg: Int,errorMsg: String,path:String): Unit

}
