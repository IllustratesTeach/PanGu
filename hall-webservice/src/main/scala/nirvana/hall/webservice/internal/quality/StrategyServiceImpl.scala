package nirvana.hall.webservice.internal.quality

import javax.sql.DataSource

import nirvana.hall.webservice.internal.haixin.exception.CustomException._
import nirvana.hall.webservice.services.quality.StrategyService
import org.apache.commons.lang.StringUtils

import scala.collection.mutable

/**
  * Created by mengxin on 2018/7/17.
  */
class StrategyServiceImpl(implicit dataSource: DataSource) extends StrategyService{

  /**
    * 检验传入的必填的入参是否全部传入
    */
  override def inputParamIsNullOrEmpty(paramMap: mutable.HashMap[String, Any]): Unit = {

    paramMap.foreach(
      t =>
        if(t._2.isInstanceOf[String]){
          if(StringUtils.isEmpty(t._2.asInstanceOf[String]) ||StringUtils.isBlank(t._2.asInstanceOf[String])){
            throw new InputParamIsNullOrEmptyException("入参" + t._1 + "为空或空串;当前传入为:" + t._2)
          }
        }else{
          if(null == t._2){
            throw new InputParamIsNullOrEmptyException("入参" + t._1 + "为空或空串;当前传入为:" + t._2)
          }
        }
    )
  }


}
