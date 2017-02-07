package nirvana.hall.v70.internal

import java.util.{Date, UUID}

import nirvana.hall.api.services.SyncInfoLogManageService
import nirvana.hall.v70.jpa.{AbnormalAfis, RecordAfis}

/**
  * Created by yuchen on 2017/2/6.
  */
class SyncInfoLogManageServiceImpl extends SyncInfoLogManageService{

  /**
    * 数据发送成功或数据成功接收，则调用该方法
    *
    * @param card_Id 卡号
    * @param business_type 业务类型
    * @param ip_source 发送端或接收端ip地址
    * @param status 发送或接收是否成功 0-失败 1-成功(成功可以不传-有默认值)
    * @param is_send 标识是发送端还是接收端 0-发送端 1-接收端
    */
  override def recordSyncDataIdentifyLog(card_Id:String,business_type:String,ip_source:String
                                         ,is_send:String,status :String = "1"): Unit = {
    val recordAfis = new RecordAfis(UUID.randomUUID.toString,card_Id
                                    ,business_type,ip_source
                                    ,new Date,status,is_send)
    recordAfis.save()
  }

  /**
    * 数据同步过程中若出现异常，则调用该方法
    *
    * @param card_Id          卡号
    * @param exceptionContent 异常内容
    */
  override def recordSyncDataExceptionLog(card_Id: String, exceptionContent: String): Unit = {
    val abnormalAfis = new AbnormalAfis(UUID.randomUUID.toString,card_Id,new Date,exceptionContent)
    abnormalAfis.save()
  }
}
