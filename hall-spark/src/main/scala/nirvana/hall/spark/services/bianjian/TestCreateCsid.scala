package nirvana.hall.spark.services.bianjian

import java.util.Properties
import javax.sql.DataSource

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import kafka.producer.{KeyedMessage, Producer, ProducerConfig}
import nirvana.hall.support.services.JdbcDatabase

/**
 * Created by songpeng on 16/3/12.
 */
object TestCreateCsid {

  def main(args:Array[String]): Unit = {
    val props = new Properties()
    props.put("metadata.broker.list", "127.0.0.1:9092")
    props.put("serializer.class", "kafka.serializer.StringEncoder")
    val config = new ProducerConfig(props)
    val producer = new Producer[String, String](config)

    val from = 3530000
    val until = 3540000
    //jdbc
    implicit val ds = createDataSource()
    val fetchSeqmentSql = "select t.csid from T_PC_A_CS t where t.csid >=? and t.csid <? "
    JdbcDatabase.queryWithPsSetter(fetchSeqmentSql){ps=>
      ps.setInt(1, from)
      ps.setInt(2, until)
    }{rs=>
      val csid = rs.getString("csid")
      val message: KeyedMessage[String, String] = new KeyedMessage("wsq", csid, csid)
      println(message)
      producer.send(message)
    }

    producer.close
  }
  def createDataSource(): DataSource={

    val hikariConfig = new HikariConfig();
    //针对oracle特别处理
    hikariConfig.setConnectionTestQuery("select 1 from dual")
    hikariConfig.setDriverClassName("oracle.jdbc.driver.OracleDriver")

//    hikariConfig.setJdbcUrl(System.getProperty(BianjianTestSymobls.IMG_JDBC_URL))
//    hikariConfig.setUsername(System.getProperty(BianjianTestSymobls.IMG_JDBC_USER))
//    hikariConfig.setPassword(System.getProperty(BianjianTestSymobls.IMG_JDBC_PASS))
    hikariConfig.setJdbcUrl("jdbc:oracle:thin:@127.0.0.1:1521:xe")
    hikariConfig.setUsername("pcsys_cs")
    hikariConfig.setPassword("11")
    //设置自动提交事务
    hikariConfig.setAutoCommit(false)

    hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
    hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
    hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    hikariConfig.setMaximumPoolSize(5)
    //hikariConfig.addDataSourceProperty("maximumPoolSize", "5")

    new HikariDataSource(hikariConfig)
  }
}
