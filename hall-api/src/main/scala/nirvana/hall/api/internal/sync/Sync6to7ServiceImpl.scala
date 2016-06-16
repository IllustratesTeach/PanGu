package nirvana.hall.api.internal.sync

import javax.sql.DataSource

import nirvana.hall.api.services.TPCardService
import nirvana.hall.api.services.remote.TPCardRemoteService
import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.services.Sync6to7Service
import org.apache.tapestry5.ioc.annotations.{InjectService, PostInjection}
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}

/**
 * Created by songpeng on 16/6/12.
 */
class Sync6to7ServiceImpl(v62Config: HallV62Config,@InjectService("V62DataSource") dataSource: DataSource,
                          tpCardService: TPCardService,
                          tpCardRemoteService: TPCardRemoteService) extends Sync6to7Service{
  /**
   * 6.2向7.0同步数据
   * @param periodicExecutor
   * @param sync6to7Service
   */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, sync6to7Service: Sync6to7Service): Unit = {
    if(v62Config.cron != null){
      periodicExecutor.addJob(new CronSchedule(v62Config.cron.sync6to7Cron), "sync-6to7", new Runnable {
        override def run(): Unit = {
          sync6to7Service.doWork
        }
      })
    }
  }

  /**
   * 定时任务调用方法
   */
  override def doWork(): Unit = {
    implicit val ds = dataSource
    JdbcDatabase.queryWithPsSetter("select t.ora_sid, tp.cardid from NORMALTP_TPCARDINFO_MOD_7 t LEFT JOIN NORMALTP_TPCARDINFO tp ON t.ORA_SID= tp.ORA_SID order by t.MODTIME"){ps=>
    }{rs=>
      val oraSid = rs.getInt("ora_sid")
      val cardId = rs.getString("cardid")
      val tpCard = tpCardService.getTPCard(cardId)
      if(tpCardRemoteService.addTPCard(tpCard, v62Config.v70Url)){
        //删除mod
        JdbcDatabase.update("delete from NORMALTP_TPCARDINFO_MOD_7 t where t.ora_sid=?"){ps=>
          ps.setInt(1, oraSid)
        }
      }
    }
  }

}
