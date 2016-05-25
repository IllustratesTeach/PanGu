package nirvana.hall.v62.internal.proxy

import org.junit.Test

import scala.concurrent.{ExecutionContext, Await, Promise}
import scala.concurrent.duration._

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-25
  */
class PromiseTest {
  @Test
  def test_promise: Unit ={
    implicit val executionContext = ExecutionContext.global
    val promise = Promise[Int]()
    val promise2 = promise.future.map{i=>
      println(Thread.currentThread().getName)
      "a"
    }
    promise.success(123)
    Await.result(promise2,10 seconds)
  }
}
