package nirvana.hall.v62.internal

import java.util.UUID
import javax.sql.DataSource
import nirvana.hall.api.services.SyncInfoLogManageService
import nirvana.hall.support.services.JdbcDatabase

/**
  * Created by yuchen on 2017/2/6.
  */
class SyncInfoLogManageServiceImpl(implicit val dataSource: DataSource) extends SyncInfoLogManageService{

  /**
    * 数据发送成功或数据成功接收，则调用该方法
    *
    * @param card_Id 卡号
    * @param business_type 业务类型
    * @param ip_source 发送端或接收端ip地址
    * @param is_send 标识是发送端还是接收端 0-发送端 1-接收端
    * @param status 发送或接收是否成功 0-失败 1-成功(成功可以不传-有默认值)
    */
  override def recordSyncDataIdentifyLog(card_Id:String,business_type:String,ip_source:String
                                         ,is_send:String,status :String = "1"): Unit = {

    val sql = "INSERT INTO RECORD_AFIS(uuid,card_id,type,ip_source,insert_date,status,is_send) " +
              "VALUES(?,?,?,?,sysdate,?,?)"

    JdbcDatabase.update(sql) { ps =>
      ps.setString(1,UUID.randomUUID.toString)
      ps.setString(2,card_Id)
      ps.setString(3,business_type)
      ps.setString(4,ip_source)
      ps.setString(5,is_send)
      ps.setString(6,status)

    }
  }

  /**
    * 数据同步过程中若出现异常，则调用该方法
    *
    * @param card_Id          卡号
    * @param exceptionContent 异常内容
    */
  override def recordSyncDataExceptionLog(card_Id: String, exceptionContent: String): Unit = {

    val sql = "INSERT INTO ABNORMAL_AFIS(uuid,card_id,Insert_Date,ABN_CONTENT) VALUES (?,?,sysdate,?)"
    JdbcDatabase.update(sql) { ps =>
      ps.setString(1,UUID.randomUUID.toString)
      ps.setString(2,card_Id)
      ps.setString(3,exceptionContent)
    }
  }
}
