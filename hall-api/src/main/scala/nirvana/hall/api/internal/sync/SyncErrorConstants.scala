package nirvana.hall.api.internal.sync

/**
 * constant for api error   Hall API同步错误原因常量
 * @author shijiarui
 * @since 2017-02-08
 */
object SyncErrorConstants {

  // 6.2 端异常信息
  final val SYNC_6_TO_7_TPCARD_RETURN =    "Hall6.2 向Hall7.0 返回数据时出错 类型: TPCard"
  final val SYNC_6_TO_7_LPCARD_RETURN =    "Hall6.2 向Hall7.0 返回数据时出错 类型: LPCard"
  final val SYNC_6_TO_7_LPPALM_RETURN =    "Hall6.2 向Hall7.0 返回数据时出错 类型: LPPALM"
  final val SYNC_6_TO_7_CASEINFO_RETURN =  "Hall6.2 向Hall7.0 返回数据时出错 类型: CASEINFO"
  final val SYNC_6_TO_7_MATCHTASK_RETURN = "Hall6.2 向Hall7.0 返回数据时出错 类型: MatchTask"

  //7.0端异常信息
  final val SYNC_7_from_6_TPCARD_save =   "Hall7.0 获取6.2返回的同步数据后7.0入库时出错 类型: TPCard"
  final val SYNC_7_from_6_LPCARD_save =   "Hall7.0 获取6.2返回的同步数据后7.0入库时出错 类型: LPCard"
  final val SYNC_7_from_6_LPPALM_save =   "Hall7.0 获取6.2返回的同步数据后7.0入库时出错 类型: LPPALM"
  final val SYNC_7_from_6_CASEINFO_save = "Hall7.0 获取6.2返回的同步数据后7.0入库时出错 类型: CASEINFO"
  final val SYNC_7_from_6_MATCHTASK_save = "Hall7.0 获取6.2返回的同步数据后7.0入库时出错 类型: MatchTask"

  final val SYNC_7_from_6_TPCARD_delete = "Hall7.0 获取6.2返回的同步数据后7.0逻辑删除时出错 没有对应cardId的Person 类型: TPCard"
  final val SYNC_7_from_6_LPCARD_delete = "Hall7.0 获取6.2返回的同步数据后7.0逻辑删除时出错 类型: LPCard"

  final val SYNC_REQUEST_UNKNOWN = "同步数据请求方未知异常"
  final val SYNC_RESPONSE_UNKNOWN = "同步数据响应方未知异常"
  final val SYNC_FETCH = "抓取数据通讯异常"
  final val SYNC_RETURN_FAIL = "抓取数据返回失败"

  final val SYNC_UPDATE_CASEInfo_SEQ_FAIL = "更新CaseInfo的SEQ失败"
  final val SYNC_UPDATE_TPCard_SEQ_FAIL = "更新TPCard的SEQ失败"
  final val SYNC_UPDATE_LPCard_SEQ_FAIL = "更新LPCard的SEQ失败"
  final val SYNC_UPDATE_LPalm_SEQ_FAIL = "更新LPalm的SEQ失败"
  final val SYNC_UPDATE_MATCHRELATION_SEQ_FAIL = "更新MatchRelation的SEQ失败"

  final val SEND_REMOTE_TASK_FAIL = "Hall7.0 向Hall6.2发送远程比对任务失败"
  final val GET_REMOTE_RESULT_FAIL = "Hall7.0 向Hall6.2请求远程比对结果失败"

  final val SEND_REMOTE_REQUEST_UNKNOWN = "远程任务请求方未知异常"
  final val SEND_REMOTE_RESPONSE_UNKNOWN = "远程任务响应方未知异常"

}
