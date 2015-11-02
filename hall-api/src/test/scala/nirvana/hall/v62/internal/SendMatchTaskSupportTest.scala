package nirvana.hall.v62.internal

import nirvana.hall.v62.services.AncientEnum.MatchType
import nirvana.hall.v62.services.{MatchOptions, DatabaseTable, SelfMatchTask}
import org.junit.Test

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-02
 */
class SendMatchTaskSupportTest {

  @Test
  def test: Unit ={
    val srcDb = DatabaseTable(1,2)
    val destDb = DatabaseTable(1,2)
    val options = new MatchOptions
    options.matchType = MatchType.TT
    options.positions = Array[Int](1,2,3,4,5,6,7,8,9,10)
    options.srcDb = srcDb
    options.destDb = destDb

    val task = SelfMatchTask("3702022014000002",options)
  }
}
