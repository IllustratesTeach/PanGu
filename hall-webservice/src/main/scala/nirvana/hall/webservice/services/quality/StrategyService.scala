package nirvana.hall.webservice.services.quality

/**
  * Created by mengxin on 2018/7/17.
  */
trait StrategyService {

  /**
    * 检验传入的必填的入参是否全部传入
    */
  def inputParamIsNullOrEmpty(paramMap:scala.collection.mutable.HashMap[String,Any]): Unit

}
