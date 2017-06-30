package nirvana.hall.api

/**
 * constant for api moudle
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-02
 */
object HallApiConstants {
  final val PROTOBUF_CONTEXT = "application/protobuf"
  //http://bigtext.org/?font=smslant&text=HALL

  //数据库ID
  final val HTTP_HEADER_DBID = "X-HALL-DBID"
  final val HTTP_HEADER_TABLEID = "X-HALL-TABLEID"

  final val HTTP_HEADER_QUERY_SRC_DBID = "X-HALL-QUERY-SRC-DBID"
  final val HTTP_HEADER_QUERY_DEST_DBID = "X-HALL-QUERY-DEST-DBID"

  final val HALL_TEXT_LOGO = """ #
   __ _____   __   __
  / // / _ | / /  / /
 / _  / __ |/ /__/ /__ module : |@@|red %s|@#
/_//_/_/ |_/____/____/ version: |@@|yellow %s|@
                               """.replaceAll("#", "@|green ")



  final val SYNC_TYPE_TPCARD = "TPCard"
  final val SYNC_TYPE_LPCARD = "LPCard"
  final val SYNC_TYPE_LPPALM= "LPPalm"
  final val SYNC_TYPE_CASEINFO = "CaseInfo"
  final val SYNC_TYPE_MATCH_TASK = "MatchTask"
  final val SYNC_TYPE_MATCH_RESULT = "MatchResult"
  final val SYNC_TYPE_MATCH_RELATION = "MatchRelation"

  final val REMOTE_TYPE_MATCH_TASK = "RemoteMatchTask"
  final val REMOTE_TYPE_MATCH_RESULT = "RemoteMatchResult"
  final val REMOTE_TYPE_MATCH_RESULT_LPCARD = "RemoteMatchResult_LPCard"
  final val REMOTE_TYPE_MATCH_RESULT_LPPALM = "RemoteMatchResult_LPPalm"
  final val REMOTE_TYPE_MATCH_RESULT_TPCARD = "RemoteMatchResult_TPCard"

  //operatorType
  final val PUT = "-PUT"
  final val UPDATE = "-UPDATE"
  final val DELETE = "-DELETE"

  //use for Sync Log
  final val MESSAGE_SEND = "0"
  final val MESSAGE_RECEIVE = "1"
  final val MESSAGE_RECEIVE_OR_SEND_FAIL = "0"
  final val MESSAGE_RECEIVE_OR_SEND_SUCCESS = "1"
  final val LOG_MESSAGE_TYPE = 1
  final val LOG_ERROR_TYPE = 2

  //Afis62 breakcase status
  final val GAQRYCAND_CHKSTATE_MATCH = 3
  final val GAQRYCAND_STATUS_FINISHED = 1

}
