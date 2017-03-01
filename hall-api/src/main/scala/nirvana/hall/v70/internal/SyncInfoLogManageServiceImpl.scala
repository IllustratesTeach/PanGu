package nirvana.hall.v70.internal

import java.text.SimpleDateFormat
import java.util.Date

import nirvana.hall.api.services.SyncInfoLogManageService
import nirvana.hall.v70.jpa.{HallSysLog, RecordAfis}

/**
  * Created by yuchen on 2017/2/6.
  */
class SyncInfoLogManageServiceImpl extends SyncInfoLogManageService{

  /**
    * 数据发送成功或数据成功接收，则调用该方法
    *
    * @param uUID 全局唯一id
    * @param card_Id 卡号
    * @param business_type 业务类型
    * @param ip_source 发送端或接收端ip地址
    * @param status 发送或接收是否成功 0-失败 1-成功
    * @param is_send 标识是发送端还是接收端 0-发送端 1-接收端
    */
  override def recordSyncDataIdentifyLog(uUID: String,card_Id:String,business_type:String,ip_source:String
                                         ,is_send:String,status :String): Unit = {
 //   val formatter = new SimpleDateFormat("yyyy-MM-dd H:mm:ss")
  //  val date = formatter.format(new Date)
    val recordAfis = new RecordAfis(uUID,card_Id
                                    ,business_type,ip_source
                                    ,new Date,status,is_send)
    recordAfis.save()
  }

  /**
    * 数据同步过程中若出现异常，则调用该方法
    *
    * @param uUID 全局唯一id
    * @param card_Id 卡号
    * @param content  异常内容
    * @param log_type 日志类型 1-消息 2-错误
    * @param msg 日志信息
    * @param seq 序列
    */
  override def recordSyncDataLog(uUID: String,card_Id: String,seq: String,content: String, log_type:Int, msg: String): Unit = {
    val hallSysLog = new HallSysLog(uUID,card_Id,new Date,content,log_type,msg,seq)
    hallSysLog.save()
  }
}
