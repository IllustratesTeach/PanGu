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
    val queryResult = service.queryMatchResultByCardId(20,2,Some("((KeyID LIKE '123'))"),10);
    println(queryResult.size)

    var list = service.queryMatchInfo(None,10)
    list = service.queryMatchInfo(Some("((SrcKey LIKE '010%')) "),10)
  }

}
