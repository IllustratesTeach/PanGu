package nirvana.hall.webservice.internal.haixin

/**
  * Created by yuchen on 2017/7/24.
  * 用于指纹系统与海鑫综采系统对接，该类包含事先定义好的错误类型的常量
  * IA(综合采集系统的英文简写)
  */
object IAConstant {

  /**
    * 指纹系统内部使用的常量
    */
  final val SUCCESS = 9 //成功

  final val USERID_NOT_VAILD = 2 //用户id不合法
  final val COLLECTSRC_NOT_VAILD = 1 //来源不合法
  final val INPUT_PARAM_IS_EMPTY = 0 //某些输入参数为空 值为 0
  final val FINGER_EXISTS =  3 //指纹捺印数据已存在
  final val FINGER_NOT_EXISTS = 8 //指纹捺印卡不存在
  final val FPT_NOT_FOUR_VERSION = 5 //传入的FPT不符合4.0标准
  final val INDOOR_NOT_EQ_OUTDOOR = 6 //传入的personid与PFT内部的personid不一致

  final val PALM_EXISTS = 4 //掌纹捺印卡已存在
  final val PALM_NOT_EXISTS = 8 //捺印的掌纹数据不存在
  final val UNKNOWN_ERROR = -1 //未知异常

  /**
    * 指纹系统返回给综采系统的常量
    */
  final val ADD_QUEUE_SUCCESS = 1 //加入队列成功
  final val ADD_QUEUE_FAIL = 0 //加入队列失败
  final val QUERY_FAIL = 0 //查询失败
  final val CREATE_STORE_SUCCESS = 1 //建库成功
  final val HITED = 2 //已比中
  final val HANDLE_ING = 3 //处理中
  final val CREATE_STORE_FAIL = 4 //建库失败

  final val SEARCH_SUCCESS = 1 //针对客户端调用查询接口的时候,标记成功
  final val SEARCH_FAIL = 0 //针对客户端调用查询接口的时候,标记失败


  /**
    * 接口编码常量对应
    */
  final val SET_FINGER = 1
  final val GET_FINGER_STATUS = 2
  final val SET_FINGER_AGAIN = 3
  final val GET_FINGER_MATCH_LIST = 4
  final val GET_FINGER_MATCH_COUNT = 5
  final val GET_FINGER_MATCH_DATA = 6

  /**
    * 操作类型常量
    */
  final val OPERATOR_TYPE_ADD = 1
  final val OPERATOR_TYPE_UPDATE = 2
  final val OPERATOR_TYPE_SEARCH = 3

  /**
    * 6.2 query status
    */
    final val WAITING_MATCH = 0
    final val MATCHING = 1
    final val WAITING_CHECK = 2
    final val CHECKING = 8
    final val CHECKED = 7
    final val WAITING_RECHECK = 9
    final val RECHECKING = 10
    final val RECHECKED = 11

    final val EMPTY = ""
    final val EMPTY_ORASID = 0


  /**
    * 标志发查询是否成功
    */
  final val SEND_QUERY_SUCCESS = 1
  final val SEND_QUERY_FAIL = -9
}
