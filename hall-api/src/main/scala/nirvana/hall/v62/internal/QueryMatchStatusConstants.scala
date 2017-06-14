package nirvana.hall.v62.internal

import nirvana.hall.protocol.api.HallMatchRelationProto.MatchStatus

/**
 * Created by songpeng on 16/5/10.
 */
object QueryMatchStatusConstants {

  /*WAITING_MATCH = 1;  //等待比对 6.2:0    7.0:0
  MATCHING = 2;       //正在比对 6.2:1    7.0:1
  WAITING_CHECK = 3;  //等待核查 6.2:2
  CHECKING = 4;       //正在核查 6.2:8
  CHECKED = 5;        //核查完毕 6.2:7
  WAITING_RECHECK = 6;//等待复核 6.2:9
  RECHECKING = 7;     //正在复核 6.2:10
  RECHECKED = 8;      //复核完毕 6.2:11
  FINISHED = 9;       //比对完成 6.2:     7.0:2
  FAILED = 10;        //比对失败 6.2:5    7.0:3
  UN_KNOWN = 11;      //未知状态 6.2:-1*/
  val STATUS_WAIT = 0.toShort//任务状态，等待比对
  val STATUS_MATCHING = 1.toShort//任务状态，正在比对
  val STATUS_WAITING_CHECK = 2.toShort
  val STATUS_CHECKING = 8.toShort
  val STATUS_CHECKED = 7.toShort
  val STATUS_WAITING_RECHECK = 9.toShort
  val STATUS_RECHECKING = 10.toShort
  val STATUS_RECHECKED = 11.toShort
  val STATUS_FAIL = 5//任务状态，失败
  val STATUS_UNKNOWN = -1


  def matchStatusConvertProtoBuf(status:Int): MatchStatus ={
    status match {
      case   QueryMatchStatusConstants.STATUS_WAIT =>
        MatchStatus.WAITING_MATCH
      case   QueryMatchStatusConstants.STATUS_MATCHING =>
        MatchStatus.MATCHING
      case   QueryMatchStatusConstants.STATUS_WAITING_CHECK =>
        MatchStatus.WAITING_CHECK
      case       QueryMatchStatusConstants.STATUS_CHECKING =>
        MatchStatus.CHECKING
      case   QueryMatchStatusConstants.STATUS_CHECKED =>
        MatchStatus.CHECKED
      case   QueryMatchStatusConstants.STATUS_WAITING_RECHECK =>
        MatchStatus.WAITING_RECHECK
      case   QueryMatchStatusConstants.STATUS_RECHECKING =>
        MatchStatus.RECHECKING
      case   QueryMatchStatusConstants.STATUS_RECHECKED =>
        MatchStatus.RECHECKED
      case QueryMatchStatusConstants.STATUS_FAIL=>
        MatchStatus.FAILED
      case other => MatchStatus.UN_KNOWN
    }
  }
}
