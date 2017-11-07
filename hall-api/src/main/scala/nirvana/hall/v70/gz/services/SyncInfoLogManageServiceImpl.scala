package nirvana.hall.v70.gz.services

import nirvana.hall.api.services.SyncInfoLogManageService

/**
  * Created by yuchen on 2017/2/6.
  */
class SyncInfoLogManageServiceImpl extends SyncInfoLogManageService{
  /**
    * 数据发送成功或数据成功接收，则调用该方法
    *
    * @param uUID          全局唯一id
    * @param card_Id       卡号
    * @param business_type 业务类型
    * @param ip_source     发送端或接收端ip地址
    * @param is_send       标识是发送端还是接收端 0-发送端 1-接收端
    * @param status        发送或接收是否成功 0-失败 1-成功(成功可以不传-有默认值)
    */
  override def recordSyncDataIdentifyLog(uUID: String, card_Id: String, business_type: String, ip_source: String, is_send: String, status: String): Unit = ???

  /**
    * 数据同步过程中若出现异常，则调用该方法
    *
    * @param uUID     全局唯一id
    * @param card_Id  卡号
    * @param seq      序列
    * @param content  异常内容
    * @param log_type 日志类型 1-消息 2-错误
    * @param msg      日志信息
    */
  override def recordSyncDataLog(uUID: String, card_Id: String, seq: String, content: String, log_type: Int, msg: String): Unit = ???
}
