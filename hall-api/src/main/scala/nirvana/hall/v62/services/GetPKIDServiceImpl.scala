package nirvana.hall.v62.services

import javax.sql.DataSource
import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.v62.services.service.GetPKIDService

import scala.collection.mutable
import scala.collection.mutable.ListBuffer


/**
  * Created by Administrator on 2017/4/21.
  */
class GetPKIDServiceImpl(implicit val dataSource: DataSource) extends GetPKIDService{
  override def getDataInfo(queryid:String,ora_sid:String): ListBuffer[mutable.HashMap[String,Any]] = {

    val sql = "select rawtohex(t.ora_uuid) ora_uuid,t.keyid,t.candlist,t.checkerunitcode,t.checkusername,t.checktime,t.querytype " +
      "from NORMALQUERY_QUERYQUE t where ((t.status =7 and t.querytype =0) or (t.status=11 and t.querytype=2) " +
      "or (t.status=11 and t.querytype=3) or (t.status=11 and t.querytype=1)) " +
      "and t.queryid=? and ora_sid=?"
    val resultList = new mutable.ListBuffer[mutable.HashMap[String,Any]]
    JdbcDatabase.queryWithPsSetter(sql) { ps =>
      ps.setString(1,queryid)
      ps.setString(2,ora_sid)
    } { rs =>
      var map = new scala.collection.mutable.HashMap[String,Any]
      map += ("ora_uuid" -> rs.getString("ora_uuid"))
      map += ("keyid" -> rs.getString("keyid"))
      map += ("candlist" -> rs.getBytes("candlist"))
      map += ("querytype" -> rs.getString("querytype"))
      resultList.append(map)
    }
    resultList
  }


  override def getDatabyPKIDInfo(pkid: String): ListBuffer[mutable.HashMap[String,Any]] = {
    val sql = "select rawtohex(t.ora_uuid) ora_uuid,t.keyid,t.candlist,t.checkerunitcode,t.checkusername,to_char(t.checktime,'yyyyMMdd') checktime,t.querytype " +
      "from NORMALQUERY_QUERYQUE t where (t.status =7 or t.status=11) and ora_uuid = ?"
    val resultList = new mutable.ListBuffer[mutable.HashMap[String,Any]]
    JdbcDatabase.queryWithPsSetter(sql) { ps =>
      ps.setString(1,pkid)
    } { rs =>
      var map = new scala.collection.mutable.HashMap[String,Any]
      map += ("ora_uuid" -> rs.getString("ora_uuid"))
      map += ("keyid" -> rs.getString("keyid"))
      map += ("candlist" -> rs.getBytes("candlist"))
      map += ("checkerunitcode" -> rs.getString("checkerunitcode"))
      map += ("checkusername" -> rs.getString("checkusername"))
      map += ("checktime" -> rs.getString("checktime"))
      map += ("querytype" -> rs.getString("querytype"))
      resultList.append(map)
    }
    resultList
  }
}
