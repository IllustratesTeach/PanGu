package nirvana.hall.api.internal

import java.util.UUID

import nirvana.hall.api.entities.OnlineUser
import nirvana.hall.api.services.{AutoSpringDataSourceSession, AuthService}
import org.apache.tapestry5.ioc.annotations.{EagerLoad, PostInjection}
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.springframework.transaction.annotation.Transactional
import scalikejdbc._

/**
 * implements auth service
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-01
 */
@EagerLoad
class AuthServiceImpl extends AuthService {
  //过期时间，TODO 做成可配置
  private val EXPIRED_PERIOD = 30 * 60
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    periodicExecutor.addJob(new CronSchedule("0 0/10 * * * ? *"), "delete-expired-user", new Runnable {
      override def run(): Unit = {
        implicit val session = AutoSpringDataSourceSession
        val expiredTime = ScalaUtils.currentTimeInSeconds - EXPIRED_PERIOD
        withSQL {
          delete.from(OnlineUser).where.lt(OnlineUser.column.latestTime, expiredTime)
        }
      }
    })
  }

  /**
   * find token by login name
   */
  override def refreshToken(token: String)(implicit session: DBSession = AutoSpringDataSourceSession()): Option[OnlineUser] = {
    val currentTime = ScalaUtils.currentTimeInSeconds
    val result = withSQL {
      update(OnlineUser).set(
        OnlineUser.column.latestTime -> currentTime).where
        .eq(OnlineUser.column.token, token)
    }.update().apply()

    if (result == 1) OnlineUser.findBy(sqls.eq(OnlineUser.column.token, token)) else None
  }

  /**
   * login system.
   * @param name login name
   * @return token
   */
  override def login(name: String)(implicit session: DBSession = AutoSpringDataSourceSession()): String = {
    val c = OnlineUser.column
    val currentTime = ScalaUtils.currentTimeInSeconds
    val token = UUID.randomUUID().toString.replaceAll("-", "")

    val num = withSQL{
      update(OnlineUser).set(
        c.loginTime -> currentTime,
        c.latestTime -> currentTime,
        c.token -> token
      ).where.eq(c.login,name)
    }.update().apply()

    if( num == 0) {
      withSQL {
        insert.into(OnlineUser).namedValues(
          c.login -> name,
          c.loginTime -> currentTime,
          c.latestTime -> currentTime,
          c.token -> token)
      }.update().apply()
    }

    token
  }

  @Transactional
  override def logout(token: String)(implicit session: DBSession): Unit = {
    val c = OnlineUser.column
    OnlineUser.findBy(sqls.eq(c.token, token)).foreach(_.destroy())
  }
}
