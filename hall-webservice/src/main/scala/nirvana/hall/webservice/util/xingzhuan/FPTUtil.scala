package nirvana.hall.webservice.util.xingzhuan

import java.io.{FileOutputStream, InputStream}
import java.text.SimpleDateFormat
import java.util.Date
import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.webservice.HallWebserviceConstants
import nirvana.hall.webservice.config.HallWebserviceConfig

/**
  * Created by T430 on 5/5/2017.
  */
object FPTUtil extends LoggerSupport{
  @throws(classOf[Exception])
  def saveFPTAndUpdateStatus(operationtype: String, in: InputStream, id: String, status: Long,config: HallWebserviceConfig,dataSource: DataSource): String = {
    var path = ""
    var finalPath = ""
    try{
      operationtype match{
        case HallWebserviceConstants.LocalTenFinger => path = config.localTenprintPath
        case HallWebserviceConstants.LocalLatent => path = config.localLatentPath
        case HallWebserviceConstants.LocalHitResult => path = config.localHitResultPath
        case HallWebserviceConstants.XCHitResult => path = config.xcHitResultPath
        case other => warn("Unkown operation type",other)
      }
      finalPath = saveFpt(in,id,path)
//      updateAssistcheckStatus(dataSource,status,id,finalPath)
    }
    finalPath
  }
  @throws(classOf[Exception])
 private def saveFpt(in: InputStream,taskId: String,dirPath:String): String = {
    //val dirPath = "C:/fpt_sy"
    val now = new Date()
    val sdf:SimpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS")
    val nowStr = sdf.format(now)
    var dir = new java.io.File(dirPath)
    val finalPath = dir+"/"+taskId+"_"+nowStr+".fpt"
    var out:FileOutputStream = null
    if(!dir.exists()){
      dir.mkdirs()
    }
    try{
      out = new FileOutputStream(finalPath)
      var byteArray = new Array[Byte](1024)
      var count:Int = -1
      count = in.read(byteArray)
      while(count != -1){
        out.write(byteArray,0,count)
        count = in.read(byteArray)
      }
    } finally {
      out.flush()
      out.close()
    }
    finalPath
  }
  private def updateAssistcheckStatus(implicit dataSource: DataSource,status:Long, id:String,finalPath:String): Unit = {
    val sql = "update hall_assistcheck set status = ?,path = ? where id = ? "
    JdbcDatabase.update(sql) { ps =>
      ps.setLong(1, status)
      ps.setString(2,finalPath)
      ps.setString(3,id)
    }
  }

  /**
    * 获取表名
    * @param dbId
    * @param tableId
    * @return
    */
  def getTableName(implicit dataSource: DataSource,dbId: Short, tableId: Short): String={
    val sql = "select t.TABLENAME from TABLECATLOG t where t.DBID =? and t.TABLEID =?"
    JdbcDatabase.queryFirst(sql){ps=>
      ps.setInt(1, dbId)
      ps.setInt(2, tableId)
    }{_.getString(1)}.get
  }
}
