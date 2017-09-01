package nirvana.hall.webservice.internal.xingzhuan

import java.util.UUID
import javax.sql.DataSource

import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.webservice.HallDatasource
import nirvana.hall.webservice.services.xingzhuan.HallDatasourceService

class HallDatasourceServiceImpl(implicit val dataSource: DataSource) extends HallDatasourceService {

  override def save(hallDatasource: HallDatasource, table: String): Unit = {
    var sql = "select count(1) quantity from " + table + " where serviceid = ?"
    var quantity = -1
    JdbcDatabase.queryWithPsSetter(sql){ps =>
      ps.setString(1, hallDatasource.serviceid)
    }{rs=>
      quantity = rs.getInt("quantity")
    }
    if(quantity > 0){
      sql = "update "+table+" set ip_source = ?,update_service_type = ?,update_operation_type = ?,updatetime = sysdate,sign = ? " +
            "where serviceid = ?"
      JdbcDatabase.update(sql) { ps =>
        ps.setString(1,hallDatasource.ipSource)
        ps.setInt(2,hallDatasource.updateServiceType)
        ps.setInt(3,hallDatasource.updateOperationType)
        ps.setString(4,HallDatasource.SIGN)
        ps.setString(5,hallDatasource.serviceid)
      }
    } else {
      val uuid = UUID.randomUUID().toString.replace("-","")
      sql = "insert into "+table+"(id,serviceid,dist_serviceid,service_pkid,ip_source,data_source,create_service_type,create_operation_type,update_service_type,update_operation_type,status,createtime,updatetime,sign,remark) " +
            "values(?,?,?,?,?,?,?,?,?,?,?,sysdate,sysdate,?,?)"
      JdbcDatabase.update(sql) { ps =>
        ps.setString(1,uuid)
        ps.setString(2,hallDatasource.serviceid)
        ps.setString(3,hallDatasource.distServiceid)
        ps.setString(4,hallDatasource.servicePkid)
        ps.setString(5,hallDatasource.ipSource)
        ps.setInt(6,2)
        ps.setInt(7,hallDatasource.createServiceType)
        ps.setInt(8,hallDatasource.createOperationType)
        ps.setInt(9,hallDatasource.updateServiceType)
        ps.setInt(10,hallDatasource.updateOperationType)
        ps.setInt(11,2)
        ps.setString(12,HallDatasource.SIGN)
        ps.setString(13,hallDatasource.remark)
      }
    }
  }

  override def del(hallDatasource: HallDatasource, table: String): Unit = {
    val sql = "delete from  "+table+" where  serviceid = ?"
    JdbcDatabase.update(sql) { ps =>
      ps.setString(1,hallDatasource.serviceid)
    }
  }
}
