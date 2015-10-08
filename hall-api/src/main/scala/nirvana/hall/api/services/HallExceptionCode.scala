package nirvana.hall.api.services

import monad.support.services.ErrorCode

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-02
 */
object HallExceptionCode {
  object INVALID_USER extends ErrorCode(9001)
  object NOT_LOGIN extends ErrorCode(9002)
}
