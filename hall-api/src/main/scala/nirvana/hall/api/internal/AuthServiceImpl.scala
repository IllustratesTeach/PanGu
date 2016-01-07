package nirvana.hall.api.internal

import java.util.UUID

import nirvana.hall.api.jpa.OnlineUser
import nirvana.hall.api.services.AuthService
import org.apache.tapestry5.ioc.annotations.{EagerLoad, PostInjection}
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.springframework.transaction.annotation.Transactional

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
        val expiredTime = ScalaUtils.currentTimeInSeconds - EXPIRED_PERIOD
        OnlineUser.where("latestTime<?1",expiredTime).delete()
      }
    })
  }

  /**
   * find token by login name
   */
  override def refreshToken(token: String): Option[OnlineUser] = {
    val result = OnlineUser.find_by_token(token).update_set(latestTime=ScalaUtils.currentTimeInSeconds).update()

    if (result == 1) OnlineUser.find_by_token(token).takeOption else None
  }

  /**
   * login system.
   * @param name login name
   * @return token
   */
  override def login(name: String): String = {
    val currentTime = ScalaUtils.currentTimeInSeconds
    val uuidToken = UUID.randomUUID().toString.replaceAll("-", "")

    OnlineUser.where(login=name).update_set(loginTime=currentTime,latestTime=currentTime,token=uuidToken).update()

    uuidToken
  }

  @Transactional
  override def logout(token: String): Unit = {
    OnlineUser.find_by_token(token).takeOption.foreach(_.delete())
  }
}
