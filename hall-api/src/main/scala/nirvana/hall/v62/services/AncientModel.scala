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
case class DatabaseTable(dbId:Int,tableId:Int)

case class SelfMatchTask(cardId:String,options:MatchOptions)
