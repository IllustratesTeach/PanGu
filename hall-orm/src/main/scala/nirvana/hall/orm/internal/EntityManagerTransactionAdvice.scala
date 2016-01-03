package nirvana.hall.orm.internal

import java.lang.reflect.{AccessibleObject, Method}

import org.apache.tapestry5.plastic.{MethodAdvice, MethodInvocation}
import org.springframework.transaction.interceptor.TransactionInterceptor

/**
 * entity manager interceptor
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-03
 */
class EntityManagerTransactionAdvice(interceptor: TransactionInterceptor,method:Method) extends  MethodAdvice {
  def advise (methodInvocation: MethodInvocation) {
    interceptor.invoke (new org.aopalliance.intercept.MethodInvocation{
      def getMethod: Method = method
      def getArguments: Array[AnyRef] = {
        throw new UnsupportedOperationException
      }
      @throws (classOf[Throwable] )
      def proceed: AnyRef = {
        methodInvocation.proceed
        methodInvocation.getReturnValue
      }
      def getThis: AnyRef = {
        methodInvocation.getInstance()
      }
      def getStaticPart: AccessibleObject = {
        throw new UnsupportedOperationException
      }
    })
  }
}
