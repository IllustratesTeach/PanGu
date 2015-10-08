package nirvana.hall.api.internal

import nirvana.hall.api.services.AuthService
import org.junit.{ Assert, Test }

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-01
 */
class AuthServiceImplTest extends BaseServiceTestSupport {
  @Test
  def test_auth: Unit = {
    val authService = registry.getService(classOf[AuthService])
    login()

    val user = authService.refreshToken(token).get
    Assert.assertEquals("jcai", user.login)
  }
}
