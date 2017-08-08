package nirvana.hall.api.services

import javax.activation.DataHandler

import stark.activerecord.services.ActiveRecord

import scala.collection.mutable.ArrayBuffer

/**
 * Created by shishijie on 17/5/3.
 */
trait ExceptRelationService {

  /**
    * 类型定义
    */
  object MatchRelationService{
    val querytypeTT = "0"
    val querytypeTL = "1"
    val querytypeLT = "2"
    val querytypeLL = "3"
  }

  /**
    * 导出比对关系
    *
    * @param queryid
    * @param ora_sid
    * @return
    */
  def exportMatchRelation(queryid:String,ora_sid:String): DataHandler

  /**
    * 获取查询的比对关系
    *
    * @param pkid
    * @return
    */
  def getSearchMatchRelation(pkid: String,num: Int): GafisMatchInfo

  class GafisMatchInfo extends ActiveRecord {
    var code:java.lang.String = _
    var tcode:java.lang.String =_
    var registerOrg:java.lang.String  =_
    var registerUser:java.lang.String  = _
    var registerTime:java.lang.String = _
    var querytype:java.lang.String =_
    var fgp:java.lang.String =_
    var matchName:java.lang.String =_
  }
}
