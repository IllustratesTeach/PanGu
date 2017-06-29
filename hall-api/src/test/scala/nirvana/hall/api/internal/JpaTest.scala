package nirvana.hall.api.internal

import nirvana.hall.v70.jpa.CodeAjlb
import org.junit.Test

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-06
 */
class JpaTest extends JpaBaseServiceTestSupport{
  @Test
  def test_save: Unit ={
    val codeAjlb = new CodeAjlb("122345677")
    codeAjlb.save()
    CodeAjlb.find_by_code("asdf").size
  }
}
