package nirvana.hall.api.internal.sync

import javax.sql.DataSource

import nirvana.hall.api.services.{CaseInfoService, LPCardService, TPCardService}
import nirvana.hall.api.services.remote.{LPCardRemoteService, CaseInfoRemoteService, TPCardRemoteService}
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
                          caseInfoService: CaseInfoService,
                          lPCardService: LPCardService,
                          tpCardRemoteService: TPCardRemoteService,
                          caseInfoRemoteService: CaseInfoRemoteService,
                          lPCardRemoteService: LPCardRemoteService) extends Sync6to7Service{

  private final val TPCARD_MOD_SQL = "select t.ora_sid, tp.cardid from NORMALTP_TPCARDINFO_MOD_7 t LEFT JOIN NORMALTP_TPCARDINFO tp ON t.ORA_SID= tp.ORA_SID order by t.MODTIME"
  private final val CASEINFO_MOD_SQL = "select t.ORA_SID, c.CASEID from NORMALLP_CASE_MOD_7 t left join NORMALLP_CASE c on t.ORA_SID=c.ORA_SID order by t.MODTIME"
  private final val LPCARD_MOD_SQL = "select t.ORA_SID, l.FINGERID from NORMALLP_LATFINGER_MOD_7 t left join NORMALLP_LATFINGER l on t.ORA_SID=l.ORA_SID order by t.MODTIME"
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
    syncTPCard(dataSource)
    syncCaseInfo(dataSource)
    syncLPCard(dataSource)
  }

  /**
   * 同步捺印卡信息
   * @param dataSource
   */
  private def syncTPCard(implicit dataSource: DataSource): Unit ={
    JdbcDatabase.queryWithPsSetter(TPCARD_MOD_SQL){ps=>
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

  /**
   * 同步案件信息
   * @param dataSource
   */
  private def syncCaseInfo(implicit dataSource: DataSource): Unit ={
    JdbcDatabase.queryWithPsSetter(CASEINFO_MOD_SQL){ps=>
    }{rs=>
      val oraSid = rs.getInt("ora_sid")
      val caseId = rs.getString("caseid")
      val caseInfo = caseInfoService.getCaseInfo(caseId)
      if(caseInfoRemoteService.addCaseInfo(caseInfo, v62Config.v70Url)){
        //删除mod
        JdbcDatabase.update("delete from NORMALLP_CASE_MOD_7 t where t.ora_sid=?"){ps=>
          ps.setInt(1, oraSid)
        }
      }
    }
  }

  private def syncLPCard(implicit dataSource: DataSource): Unit ={
    JdbcDatabase.queryWithPsSetter(LPCARD_MOD_SQL){ps=>
    }{rs=>
      val oraSid = rs.getInt("ora_sid")
      val fingerId = rs.getString("fingerid")
      val lpCard = lPCardService.getLPCard(fingerId)
      if(lPCardRemoteService.addLPCard(lpCard, v62Config.v70Url)){
        //删除mod
        JdbcDatabase.update("delete from NORMALLP_LATFINGER_MOD_7 t where t.ora_sid=?"){ps=>
          ps.setInt(1, oraSid)
        }
      }
    }

  }

}
