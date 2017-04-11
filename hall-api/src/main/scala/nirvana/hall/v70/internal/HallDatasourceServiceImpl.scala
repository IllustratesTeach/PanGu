package nirvana.hall.v70.internal

import nirvana.hall.api.HallDatasource
import nirvana.hall.api.services.HallDatasourceService
import stark.activerecord.services.ActiveRecord

class HallDatasourceServiceImpl extends HallDatasourceService {

  override def save(hallDatasource: HallDatasource, table: String): Unit = {

    val fun = new Function1[javax.persistence.EntityManager, scala.Int] {
      def apply(em:javax.persistence.EntityManager) = {
        var sql = "select count(1) quantity from " + table + " where serviceid = ?"
        val quantity = em.createNativeQuery(sql).setParameter(1, hallDatasource.serviceid).getSingleResult().toString.toInt
        if(quantity > 0) {
          sql = "update "+table+" set ip_source = ?,update_service_type = ?,update_operation_type = ?,updatetime = sysdate,sign = ? " +
                "where serviceid = ?"
          em.createNativeQuery(sql)
            .setParameter(1, hallDatasource.ipSource)
            .setParameter(2, hallDatasource.updateServiceType)
            .setParameter(3, hallDatasource.updateOperationType)
            .setParameter(4, HallDatasource.SIGN)
            .setParameter(5, hallDatasource.serviceid)
            .executeUpdate
        } else {
          sql = "insert into " + table + "(id,serviceid,dist_serviceid,service_pkid,ip_source,data_source,create_service_type,create_operation_type,update_service_type,update_operation_type,status,createtime,updatetime,sign,remark) " +
              "values(?,?,?,?,?,?,?,?,?,?,?,sysdate,sysdate,?,?)"
          em.createNativeQuery(sql)
            .setParameter(1, CommonUtils.getUUID)
            .setParameter(2, hallDatasource.serviceid)
            .setParameter(3, hallDatasource.distServiceid)
            .setParameter(4, hallDatasource.servicePkid)
            .setParameter(5, hallDatasource.ipSource)
            .setParameter(6, 1)
            .setParameter(7, hallDatasource.createServiceType)
            .setParameter(8, hallDatasource.createOperationType)
            .setParameter(9, hallDatasource.updateServiceType)
            .setParameter(10, hallDatasource.updateOperationType)
            .setParameter(11, 2)
            .setParameter(12, HallDatasource.SIGN)
            .setParameter(13, hallDatasource.remark)
            .executeUpdate
        }
      }
    }
    ActiveRecord.executeInTransaction(fun)
  }
}
