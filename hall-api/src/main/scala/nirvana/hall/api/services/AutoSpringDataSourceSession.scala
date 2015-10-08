package nirvana.hall.api.services

import java.sql.Connection
import javax.sql.DataSource

import org.apache.tapestry5.ioc.services.{PerthreadManager, ThreadCleanupListener}
import org.springframework.jdbc.datasource.{ConnectionHolder, DataSourceUtils}
import org.springframework.transaction.support.TransactionSynchronizationManager
import scalikejdbc.{DBConnectionAttributes, DBSession}

/**
 * integrate spring and Scalikejdbc
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-02
 */
object AutoSpringDataSourceSession {
  var perthreadManager:PerthreadManager = _
  var dataSource: DataSource = _
  var driverName: Option[String] = None
  def apply(): DBSession = {
    new AutoSpringDataSourceSession(dataSource, driverName)
  }
  def getConnection:Connection = {
    if(TransactionSynchronizationManager.hasResource(dataSource)){
      DataSourceUtils.getConnection(dataSource)
      //TransactionSynchronizationManager.getResource(dataSource).asInstanceOf[ConnectionHolder].
    }else{
      val connection = dataSource.getConnection
      TransactionSynchronizationManager.bindResource(dataSource,new ConnectionHolder(connection))
      //由当前线程清空
      perthreadManager.addThreadCleanupListener(new ThreadCleanupListener {
        override def threadDidCleanup(): Unit = {
          TransactionSynchronizationManager.unbindResource(dataSource)
          DataSourceUtils.doCloseConnection(connection,dataSource)
        }
      })

      connection
    }
  }
}
private class AutoSpringDataSourceSession(dataSource: DataSource, driverName: Option[String])
    extends DBSession {
  override lazy val connectionAttributes: DBConnectionAttributes = DBConnectionAttributes(driverName)
  override val isReadOnly: Boolean = false
  override val conn: Connection = {
    AutoSpringDataSourceSession.getConnection
  }
  override def close(): Unit = {
  }
}
