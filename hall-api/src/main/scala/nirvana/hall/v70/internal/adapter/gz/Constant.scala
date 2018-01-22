package nirvana.hall.v70.internal.adapter.gz

/**
  * 标采与指纹系统对接变量设置
  * Created by ssj on 2017/10/13.
  */
object Constant {

  final val EMPTY = ""

  /**
    * 调用后返回的结果，成功，失败
    */
  final val SUCCESS = "1"
  final val FAIL = "0"

  /**
    * 接口编码常量对应
    */
  final val TPCardFilter = "1"
  final val TaskQueryFilter = "2"
  final val CaptureFilter = "3"
  final val GetTPCardFilter = "4"
  final val GetLPCardFilter = "5"
  final val GetCASEFilter = "6"
  final val EDZFilter = "7"

  /**
    * 标采捺印入库的操作类型，成功，失败
    */
  final val ADD = "0"
  final val UPDATE = "1"

  /**
    * 标采请求获取卡片 人员，现场
    */
  final val GETTP = "1"
  final val GETLP = "2"
  final val GETCASE = "3"

  /**
    * 标采是否发送查询
    */
  final val ISSEND = "1"
}
