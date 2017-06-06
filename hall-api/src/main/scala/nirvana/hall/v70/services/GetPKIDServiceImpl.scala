package nirvana.hall.v70.services

import javax.sql.DataSource

import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.v70.services.service.GetPKIDService

import scala.collection.mutable
import scala.collection.mutable.ListBuffer


/**
  * Created by Administrator on 2017/4/21.
  */
class GetPKIDServiceImpl(implicit val dataSource: DataSource) extends GetPKIDService{
  override def getDataInfo(queryid:String,ora_sid:String): ListBuffer[mutable.HashMap[String,Any]] = {

    val sql = "select Distinct k.pk_id from Gafis_Checkin_Info k  " +
      "where k.query_uuid in (select pk_id  from GAFIS_NORMALQUERY_QUERYQUE t " +
      "where (t.querytype = '1' or t.querytype = '2' or t.querytype = '0' or t.querytype = '3') " +
      "and deletag='1' and t.status = '2' and ora_sid =?)"
    val resultList = new mutable.ListBuffer[mutable.HashMap[String,Any]]
    JdbcDatabase.queryWithPsSetter(sql) { ps =>
      ps.setString(1,ora_sid)
    } { rs =>
      var map = new scala.collection.mutable.HashMap[String,Any]
      map += ("pk_id" -> rs.getString("pk_id"))
      resultList.append(map)
    }
    resultList
  }
}
