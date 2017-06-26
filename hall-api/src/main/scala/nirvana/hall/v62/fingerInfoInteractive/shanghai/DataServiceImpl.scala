package nirvana.hall.v62.fingerInfoInteractive.shanghai

import javax.sql.DataSource

import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.v62.fingerInfoInteractive.shanghai.services.DataService


/**
  * Created by ssj on 2017/03/09.
  */
class DataServiceImpl(implicit val dataSource: DataSource) extends DataService{

  override def getDataInfo(rowno:Int): scala.collection.mutable.HashMap[String,Any] = {
    val sql = "SELECT * FROM T_ZWXX T WHERE  NOT EXISTS (SELECT 1 FROM T_ZWXX_SAVE S WHERE S.JMSFZSLH = T.JMSFZSLH) " +
      "AND GMSFHM IS NOT NULL " +
      "AND XM IS NOT NULL " +
      "AND ZWY_ZWDM IS NOT NULL " +
      "AND ZWE_ZWDM IS NOT NULL " +
      "AND CJDW_GAJGJGDM IS NOT NULL " +
      "AND CJDW_GAJGMC IS NOT NULL " +
      "AND CJR_XM IS NOT NULL " +
      "AND ROWNUM <= ?"
    val map = new scala.collection.mutable.HashMap[String,Any]
    JdbcDatabase.queryWithPsSetter(sql) { ps =>
        ps.setInt(1,rowno)
    } { rs =>
      map += ("JMSFZSLH" -> rs.getString("JMSFZSLH"))  //居民身份证受理号
      map += ("GMSFHM" -> rs.getString("GMSFHM"))  //公民身份号码
      map += ("XM" -> rs.getString("XM"))  //姓名
      map += ("ZWY_ZWDM" -> rs.getString("ZWY_ZWDM"))  //指位
      map += ("ZWY_ZWTXSJ" -> rs.getBytes("ZWY_ZWTXSJ"))  //图像数据
      map += ("ZWE_ZWDM" -> rs.getString("ZWE_ZWDM"))  //指位
      map += ("ZWE_ZWTXSJ" -> rs.getBytes("ZWE_ZWTXSJ"))  //特征数据
      map += ("CJDW_GAJGJGDM" -> rs.getString("CJDW_GAJGJGDM"))  //采集单位公安机关机构代码
      map += ("CJDW_GAJGMC" -> rs.getString("CJDW_GAJGMC"))  //采集单位公安机关名称
      map += ("CJR_XM" -> rs.getString("CJR_XM"))  //采集人_姓名
    }
    map
  }

  override def insertCard(card:String,state:Int):Int ={
    val sql = "insert into T_ZWXX_SAVE (JMSFZSLH,INSERTDATE,STATE) values (?,SYSDATE,?)"
    val result = JdbcDatabase.update(sql) { ps =>
      ps.setString(1,card)
      ps.setInt(2,state)
    }
    result
  }

}
