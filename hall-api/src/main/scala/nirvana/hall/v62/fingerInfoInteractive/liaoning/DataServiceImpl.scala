package nirvana.hall.v62.fingerInfoInteractive.liaoning


import javax.sql.DataSource
import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.v62.fingerInfoInteractive.liaoning.services.DataService

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Created by yuchen on 2016/12/16.
  */
class DataServiceImpl(implicit val dataSource: DataSource) extends DataService{

  override def getDataInfo(rowno:Int): ListBuffer[mutable.HashMap[String,Any]] = {
    val sql = "SELECT * FROM V_SFZZWTEN_2100_TEST WHERE ROWNUM <= ?"
    val resultList = new mutable.ListBuffer[mutable.HashMap[String,Any]]
    JdbcDatabase.queryWithPsSetter(sql) { ps =>
        ps.setInt(1,rowno)
    } { rs =>
      var map = new scala.collection.mutable.HashMap[String,Any]
      map += ("XLH" -> rs.getString("XLH"))
      map += ("JMSFZSLH" -> rs.getString("JMSFZSLH"))
      resultList.append(map)
    }
    resultList
  }

  override def backAndDeleteDataInfo(xlh:String): Unit ={
    //TODO
  }
}
