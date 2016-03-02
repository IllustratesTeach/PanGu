package nirvana.hall.api.internal

import java.util.UUID
import nirvana.hall.api.services.AuthService
import nirvana.hall.orm.services.ActiveRecord
import nirvana.hall.v70.jpa.OnlineUser
import org.apache.tapestry5.ioc.ObjectLocator
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
  def startUp(periodicExecutor: PeriodicExecutor, objectLocator: ObjectLocator): Unit = {
    ActiveRecord.objectLocator = objectLocator
    periodicExecutor.addJob(new CronSchedule("0 0/10 * * * ? *"), "delete-expired-user", new Runnable {
      override def run(): Unit = {
//        val expiredTime = ScalaUtils.currentTimeInSeconds - EXPIRED_PERIOD
//        OnlineUser.where("latestTime<?1",expiredTime).delete
      }
    })
  }

  /**
   * find token by login name
   */
  override def refreshToken(token: String): Option[OnlineUser] = {
    val result = OnlineUser.find_by_token(token).update(latestTime=ScalaUtils.currentTimeInSeconds)

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

    val num = OnlineUser.find_by(login=name).update(loginTime=currentTime,latestTime=currentTime,token=uuidToken)

    if( num == 0) {
      val onlineUser = new OnlineUser()
      onlineUser.login = name
      onlineUser.loginTime = currentTime
      onlineUser.latestTime  = currentTime
      onlineUser.token = uuidToken
      onlineUser.save()
    }
    uuidToken
  }

  @Transactional
  override def logout(token: String): Unit = {
    OnlineUser.find_by_token(token).takeOption.foreach(_.delete())
  }
}
