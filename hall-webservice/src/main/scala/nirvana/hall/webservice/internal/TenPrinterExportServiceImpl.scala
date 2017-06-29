package nirvana.hall.webservice.internal

import java.util.UUID
import javax.activation.DataHandler
import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.c.services.gfpt4lib.FPT4File.FPT4File
import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.webservice.{HallWebserviceConstants, Upload}
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.TenPrinterExportService
import nirvana.hall.webservice.util.xingzhuan.FPTUtil
import org.apache.axiom.attachments.ByteArrayDataSource

import scala.collection.mutable.ArrayBuffer

/**
  * Created by yuchen on 2017/5/10.
  */
class TenPrinterExportServiceImpl( implicit dataSource: DataSource
                                  ,facade:V62Facade
                                  ,v62config:HallV62Config
                                  ,hallWebserviceConfig: HallWebserviceConfig
                                  ,fPTService:FPTService) extends TenPrinterExportService with LoggerSupport{


  /**
    *
    */
  override  def exportTenPrinterFPT(): Unit ={
    val cardIdList = searchWillExportTenPrinterFPT(
      10
      ,FPTUtil.getTableName(dataSource,v62config.templateTable.dbId.toShort, V62Facade.TID_LATFINGER)
      ,hallWebserviceConfig.union4pfmip.TenPrintPrex
      ,hallWebserviceConfig.union4pfmip.dateLimit)
    val fPT4File = new FPT4File
    cardIdList.foreach{
      cardId =>
        try{
          fPT4File.logic02Recs = Array(fPTService.getLogic02Rec(cardId))
          val path = FPTUtil.saveFPTPath(HallWebserviceConstants.LocalTenFinger
            ,new DataHandler(new ByteArrayDataSource(fPT4File.build.toByteArray())).getInputStream
            ,cardId
            ,1L
            ,hallWebserviceConfig
            ,dataSource)
          saveResult(UUID.randomUUID().toString.replace("-",""),cardId,FPTUtil.TenprintRecord,Upload.UPLOAD_STATUS_CREATED,"",path)
        }catch{
          case e:Exception =>
            val errorInfo = "tenprint export error:" + ExceptionUtil.getStackTraceInfo(e)
            error(errorInfo)
            saveResult(UUID.randomUUID().toString.replace("-",""),cardId,FPTUtil.TenprintRecord,-1,errorInfo,"")
        }
    }
  }


  /**
    * 保存上报结果
    * @param uuid
    * @param cardID
    * @param typ
    * @param msg
    * @param errorMsg
    */
  def saveResult(uuid: String,cardID: String, typ: String,msg: Int, errorMsg: String,path:String): Unit = {
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




  private def searchWillExportTenPrinterFPT(size: Int, tableName: String,tenPrintPrex:String,dateLimit:String): ArrayBuffer[(String)] ={
    val SYNC_SQL=s"SELECT t.cardid " +
      s"FROM ${tableName} t " +
      s"WHERE NOT EXISTS(SELECT p.serviceid " +
      s"FROM HALL_XC_REPORT p " +
      s"WHERE p.typ='" + FPTUtil.TenprintRecord + "' AND p.serviceid = t.cardid) AND t.cardid like '" + tenPrintPrex +"%'" +
      s" AND t.createtime>= to_date('${dateLimit}-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss') and rownum<= ?"
    val cardIdBuffer: ArrayBuffer[(String)] = new ArrayBuffer[(String)]
    JdbcDatabase.queryWithPsSetter2(SYNC_SQL){ps=>
      ps.setInt(1, size)
    }{rs=>
      while (rs.next()){
        cardIdBuffer += rs.getString("cardid")
      }
    }
    cardIdBuffer
  }

}
