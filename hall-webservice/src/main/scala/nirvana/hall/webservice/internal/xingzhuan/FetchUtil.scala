package nirvana.hall.webservice.internal.xingzhuan

import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.util.xingzhuan.FPTUtil

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Administrator on 2017/4/21.
  */
class FetchUtil(implicit dataSource: DataSource,
                hallWebserviceConfig: HallWebserviceConfig) extends LoggerSupport {

  /**
    * 获取待上报的卡号集合
    *
    * @param cardIdBuffer
    * @param size
    * @param tableName
    * @param dateLimit 上报数据年限
    */
  def doFetcher(cardIdBuffer: ArrayBuffer[(String,String)], size: Int, tableName: String, dateLimit:String): Unit ={
    val SYNC_SQL=s"select t.fingerid,t.caseid from ${tableName} t where  t.createtime>= to_date('${dateLimit}-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss') " +
      s"and not exists(select t1.serviceid " +
                        s"from HALL_XC_REPORT t1 " +
                        s"where t1.typ='" + FPTUtil.Latent +"' and t1.serviceid=t.fingerid ) " +
      s"  and rownum<= ?"
    JdbcDatabase.queryWithPsSetter2(SYNC_SQL){ps=>
      ps.setInt(1, size)
    }{rs=>
      while (rs.next()){
        val fingerid = rs.getString("fingerid")
        val caseid = rs.getString("caseid")
        if(fingerid != null && fingerid.trim.length > 0 && caseid != null && caseid.trim.length > 0){
          cardIdBuffer += (fingerid -> caseid)
        }
      }
    }
  }


  /**
    * 获取表名
    *
    * @param dbId
    * @param tableId
    * @return
    */
  def getTableName(dbId: Short, tableId: Short): String={
    val sql = "select t.TABLENAME from TABLECATLOG t where t.DBID =? and t.TABLEID =?"
    JdbcDatabase.queryFirst(sql){ps=>
      ps.setInt(1, dbId)
      ps.setInt(2, tableId)
    }{_.getString(1)}.get
  }

}
