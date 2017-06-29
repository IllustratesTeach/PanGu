package nirvana.hall.api.internal

import monad.support.services.MonadException
import nirvana.hall.api.annotations.RequiresUser
import nirvana.hall.api.services.HallExceptionCode.INVALID_USER
import nirvana.hall.api.services.{RequiresUserAdvisor, ProtobufRequestGlobal}
import org.apache.tapestry5.ioc.MethodAdviceReceiver
import org.apache.tapestry5.ioc.annotations.PreventServiceDecoration
import org.apache.tapestry5.plastic.{MethodAdvice, MethodInvocation}
import org.apache.tapestry5.services.Core

/**
 * required user
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-02
 */
@PreventServiceDecoration
class RequiresUserAdvisorImpl(@Core protobufRequestGlobal: ProtobufRequestGlobal) extends RequiresUserAdvisor {
  private val advice = new AuthAdvice
  override def addAdvice(receiver: MethodAdviceReceiver): Unit = {
    for (m <- receiver.getInterface.getMethods) {
      if (receiver.getMethodAnnotation(m, classOf[RequiresUser]) != null) {
        receiver.adviseMethod(m, advice)
      }
    }
  }
  private class AuthAdvice extends MethodAdvice {
    def advise(invocation: MethodInvocation): Unit = {
      if (protobufRequestGlobal.isLogin) invocation.proceed
      else throw new MonadException(INVALID_USER)
    }
  }
}
