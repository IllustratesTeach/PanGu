package nirvana.hall.v62.internal

import nirvana.hall.v62.BaseV62TestCase
import org.junit.Test

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-14
  */
class QueryServiceImplTest extends BaseV62TestCase{
  @Test
  def testQuery: Unit ={
    val service  = new QueryServiceImpl(createFacade,null)
    service.queryMatchResultByCardId(20,2,Some("((KeyID REGLIKE '123'))"),10);

    service.queryMatchInfo(None,10)
  }

}
