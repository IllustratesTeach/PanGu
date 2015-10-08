package nirvana.hall.api.services

import org.apache.tapestry5.ioc.MethodAdviceReceiver

/**
 * required user advisor
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-02
 */
trait RequiresUserAdvisor {
  /**
   * Adds auth advice to all methods of the object.
   */
  def addAdvice(methodAdviceReceiver: MethodAdviceReceiver)
}
