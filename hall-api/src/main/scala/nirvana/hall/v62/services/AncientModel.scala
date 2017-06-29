package nirvana.hall.v62.services

import nirvana.hall.v62.services.AncientEnum.MatchType

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-02
 */
class AncientModel {
}

/**
 * match options
 */
class MatchOptions{
  var matchType:MatchType = _
  var positions:Array[Int] = _
  var srcDb:DatabaseTable = _
  var destDb:DatabaseTable = _
}

/**
 * server address
 * @param host 6.2 server host
 * @param port bind port
 */
case class V62ServerAddress(host:String,port:Int,connectionTimeoutSecs:Int,readTimeoutSecs:Int,user:String,password:Option[String]=None)

/**
 * database table definition
 * @param dbId database id
 * @param tableId
 */
case class DatabaseTable(dbId:Int,tableId:Int)

case class SelfMatchTask(cardId:String,options:MatchOptions)
