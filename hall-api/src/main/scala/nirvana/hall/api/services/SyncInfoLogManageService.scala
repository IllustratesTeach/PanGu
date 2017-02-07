package nirvana.hall.api.services



/**
  * Created by yuchen on 2017/2/6.
  *
  * 同步数据的管理接口，包含两个方法：
  * 1、发送端记录已成功发送的数据日志
  * 2、接收端成功接收数据后记录日志
  * 3、异常时，记录异常的卡号和异常信息及时间等信息
  */
trait SyncInfoLogManageService {

  /**
    * 数据发送成功或数据成功接收，则调用该方法
    * @param card_Id 卡号
    * @param business_type 业务类型
    * @param ip_source 发送端或接收端ip地址
    * @param is_send 标识是发送端还是接收端 0-发送端 1-接收端
    * @param status 发送或接收是否成功 0-失败 1-成功(成功可以不传-有默认值)
    */
    def recordSyncDataIdentifyLog(card_Id:String,business_type:String,ip_source:String
                                  ,is_send:String,status :String = "1"): Unit

  /**
    * 数据同步过程中若出现异常，则调用该方法
    * @param card_Id 卡号
    * @param exceptionContent 异常内容
    */
    def recordSyncDataExceptionLog(card_Id:String,exceptionContent:String): Unit
}
