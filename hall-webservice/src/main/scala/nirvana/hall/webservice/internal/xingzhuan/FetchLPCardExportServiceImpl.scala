package nirvana.hall.webservice.internal.xingzhuan

import javax.sql.DataSource

import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.xingzhuan.FetchLPCardExportService
import nirvana.hall.webservice.util.xingzhuan.FPTUtil

import scala.collection.mutable.ArrayBuffer

/**
  * Created by sjr on 2017/4/21.
  */
class FetchLPCardExportServiceImpl(implicit dataSource: DataSource,
                                   hallWebserviceConfig: HallWebserviceConfig
                                   , facade:V62Facade
                                   , v62config:HallV62Config
) extends FetchUtil with FetchLPCardExportService{

  /**
    * 获取待上报的该批次的现场指纹案件集合
    *
    * @param size
    * @param dbId
    * @return
    */
  override def fetchCardIdListBySize_AssistCheck(size: Int, dbId: Option[String]): Seq[(String,String)] = {
    val cardIdList = new ArrayBuffer[(String,String)]()
    doFetcher(cardIdList, size, FPTUtil.getTableName(dataSource,v62config.latentTable.dbId.toShort, V62Facade.TID_LATFINGER),hallWebserviceConfig.union4pfmip.dateLimit)
    cardIdList
  }

  /**
    * 保存上报结果
    *
    * @param uuid
    * @param cardID
    * @param typ
    * @param msg
    * @param errorMsg
    */
    override def saveResult(uuid: String,cardID: String, typ: String,msg: Int, errorMsg: String,path:String): Unit = {
      val sql = "INSERT INTO HALL_XC_Report(id,serviceid,typ,status,errormsg,fpt_path,is_delete,create_time,update_time) VALUES (?,?,?,?,?,?,?,sysdate,?)"
      JdbcDatabase.update(sql) { ps =>
        ps.setString(1,uuid)
        ps.setString(2,cardID)
        ps.setString(3,typ)
        ps.setInt(4,msg)
        ps.setString(5,errorMsg)
        ps.setString(6,path)
        ps.setInt(7,0)
        ps.setString(8,"")
      }
  }
}
