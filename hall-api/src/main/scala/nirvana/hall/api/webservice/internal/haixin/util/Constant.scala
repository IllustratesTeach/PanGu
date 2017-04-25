package nirvana.hall.api.webservice.internal.haixin.util

/**
  * Created by yuchen on 2017/4/6.
  */
object Constant {

  val empty = ""

  val FILE_EXTENSION = ".FPT"

  val HANDER = 1 //已处理
  val NOT_HANDER = 0 //未处理


  val PUT_QUEUE_SUCCESS = 1
  val PUT_QUEUE_FAIL = 0

  val QUERY_FAIL = 0
  val CREATE_TASK_SUCCESS = 1
  val HIT = 2
  val HANDER_ING = 3
  val CREATE_TASK_FAIL = 4

  /**
    * 6.2与7.0查询状态对照表
    * //等待比对 6.2:0    7.0:0
      //正在比对 6.2:1    7.0:1
      //等待核查 6.2:2
      //正在核查 6.2:8
      //核查完毕 6.2:7
      //等待复核 6.2:9
      //正在复核 6.2:10
      //复核完毕 6.2:11
      //比对完成 6.2:     7.0:2
      //比对失败 6.2:5    7.0:3
      //未知状态 6.2:-1
    *
    * @param statusId
    * @return 4: 建库失败 3: 处理中 2: 已比中 1: 成功建库 0: 查询失败
    */
  def queryStatusConvert(statusId:Int):Int = {
      statusId match {
        case 0 =>
          CREATE_TASK_SUCCESS
        case 11 | 2 =>
          HIT
        case 1 =>
          HANDER_ING
        case _ =>
          CREATE_TASK_FAIL
      }
  }
}
