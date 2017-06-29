package nirvana.hall.api.internal

import java.lang.reflect.{ AccessibleObject, Method }

import monad.support.services.MonadException
import org.apache.tapestry5.plastic.{ MethodInvocation, MethodAdvice }
import org.springframework.transaction.interceptor.TransactionInterceptor
/**
 * advice Transactional
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-02
 */
class TransactionAdvice(interceptor: TransactionInterceptor, method: Method) extends MethodAdvice {
  def advise(methodInvocation: MethodInvocation) {
    try {
      this.interceptor.invoke(new org.aopalliance.intercept.MethodInvocation() {
        def getMethod: Method = method

        def getArguments: Array[AnyRef] = {
          throw new UnsupportedOperationException
        }

        @throws(classOf[Throwable])
        def proceed: AnyRef = {
          methodInvocation.proceed
          methodInvocation.getReturnValue
        }

        def getThis: AnyRef = null

        def getStaticPart: AccessibleObject = {
          throw new UnsupportedOperationException
        }
      })
    }
    catch {
      case re: RuntimeException => {
        throw re
      }
      case e: Throwable => {
        throw MonadException.wrap(e)
      }
    }
  }
}

