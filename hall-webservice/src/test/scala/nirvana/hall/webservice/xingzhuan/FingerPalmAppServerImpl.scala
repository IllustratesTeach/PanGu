package nirvana.hall.webservice.xingzhuan

import javax.activation.DataHandler
import javax.jws.WebMethod

import nirvana.hall.webservice.services.xingzhuan.FingerPalmAppServer
import org.apache.axiom.attachments.ByteArrayDataSource

import scala.io.Source

/**
  * Created by songpeng on 2017/12/6.
  */
class FingerPalmAppServerImpl extends FingerPalmAppServer{
  /**
    * 发送捺印指掌纹信息服务
    *
    * @param userID   请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param NYZZWdh  捺印指掌纹信息交换文件，交换文件内容参见《指掌纹交换文件格式技术规范》，须符合捺印指掌纹信息值域要求
    * @return <?xml version="1.0" encoding="UTF-8"?>
    *         <RESPONSE>
    *         <STATUS>SUCCESS</STATUS>
    *         <RETURNCODE>10101</RETURNCODE>
    *         <MSG/>
    *         </RESPONSE>
    *         STATUS	SUCCESS/FAIL  标识请求是否是成功的
    *         RETURNCODE	参见“附录A：服务返回状态码”
    *         MSG	服务方可以在此处返回有助于调试的信息。例如：各类质量检查错误
    */
  @WebMethod
  override def sendFingerPrint(userID: String, password: String, NYZZWdh: DataHandler): DataHandler = {
    val xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
      "       <RESPONSE>" +
      "         <STATUS>SUCCESS</STATUS>" +
      "         <RETURNCODE>10101</RETURNCODE>" +
      "         <MSG/>" +
      "       </RESPONSE>"
//    println(Source.fromInputStream(NYZZWdh.getInputStream).mkString)
    new DataHandler(new ByteArrayDataSource(xml.getBytes))
  }

  /**
    * 发送现场指掌纹信息服务
    *
    * @param userID   请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param XCZZWdh  现场指掌纹交换文件
    * @return 返回结果参见“发送捺印指掌纹信息服务”的服务结果部分
    */
  @WebMethod
  override def sendLatent(userID: String, password: String, XCZZWdh: DataHandler): DataHandler = ???

  /**
    * 发送现场指掌纹查询请求信息服务
    *
    * @param userID       请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password     请求方密码,请求方在请求服务系统上注册的识别密码
    * @param LatentTaskdh 现场指掌纹查询请求交换文件
    * @return 返回结果参见“发送捺印指掌纹信息服务”的服务结果部分
    */
  override def sendLatentTask(userID: String, password: String, LatentTaskdh: DataHandler): DataHandler = ???

  /**
    * 发送捺印指掌纹查询请求信息服务
    *
    * @param userID      请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password    请求方密码,请求方在请求服务系统上注册的识别密码
    * @param PrintTaskdh 捺印指掌纹查询请求交换文件
    * @return
    */
  @WebMethod
  override def sendPrintTask(userID: String, password: String, PrintTaskdh: DataHandler): DataHandler = ???

  /**
    * 发送指掌纹正查比对结果信息服务
    *
    * @param userID     请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password   请求方密码,请求方在请求服务系统上注册的识别密码
    * @param LTResultdh 指掌纹正查比对结果交换文件
    * @return
    */
  @WebMethod
  override def sendLTResult(userID: String, password: String, LTResultdh: DataHandler): DataHandler = ???

  /**
    * 发送指掌纹倒查比对结果信息服务
    *
    * @param userID     请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password   请求方密码,请求方在请求服务系统上注册的识别密码
    * @param TLResultdh 指掌纹倒查比对结果交换文件
    * @return 返回结果参见“发送捺印指掌纹信息服务”的服务结果部分
    */
  @WebMethod
  override def sendTLResult(userID: String, password: String, TLResultdh: DataHandler): DataHandler = ???

  /**
    * 发送指掌纹查重比对结果信息服务
    *
    * @param userID     请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password   请求方密码,请求方在请求服务系统上注册的识别密码
    * @param TTResultdh 指掌纹查重比对结果交换文件
    * @return 返回结果参见“发送捺印指掌纹信息服务”的服务结果部分
    */
  @WebMethod
  override def sendTTResult(userID: String, password: String, TTResultdh: DataHandler): DataHandler = ???

  /**
    * 发送指掌纹串查比对结果信息服务
    *
    * @param userID     请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password   请求方密码,请求方在请求服务系统上注册的识别密码
    * @param LLResultdh 指掌纹串查比对结果交换文件
    * @return 返回结果参见“发送捺印指掌纹信息服务”的服务结果部分
    */
  @WebMethod
  override def sendLLResult(userID: String, password: String, LLResultdh: DataHandler): DataHandler = ???

  /**
    * 发送正查和倒查比中信息服务
    *
    * @param userID        请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password      请求方密码,请求方在请求服务系统上注册的识别密码
    * @param LTHitResultdh 指掌纹正查比中信息交换文件
    * @return 返回结果参见“发送捺印指掌纹信息服务”的服务结果部分
    */
  @WebMethod
  override def sendLTHitResult(userID: String, password: String, LTHitResultdh: DataHandler): DataHandler = ???

  /**
    * 发送查重比中结果信息服务
    *
    * @param userID        请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password      请求方密码,请求方在请求服务系统上注册的识别密码
    * @param TTHitResultdh 指掌纹查重比中信息交换文件
    * @return 返回结果参见“发送捺印指掌纹信息服务”的服务结果部分
    */
  @WebMethod
  override def sendTTHitResult(userID: String, password: String, TTHitResultdh: DataHandler): DataHandler = ???

  /**
    * 发送串查比中结果信息服务
    *
    * @param userID        请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password      请求方密码,请求方在请求服务系统上注册的识别密码
    * @param LLHitResultdh 指掌纹串查比中信息交换文件
    * @return 返回结果参见“发送捺印指掌纹信息服务”的服务结果部分
    */
  @WebMethod
  override def sendLLHitResult(userID: String, password: String, LLHitResultdh: DataHandler): DataHandler = ???

  /**
    * 发送现场指掌纹撤销信息服务
    *
    * @param userID         请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password       请求方密码,请求方在请求服务系统上注册的识别密码
    * @param cancelLatentDh 发送现场指掌纹撤销信息交换文件
    * @return 返回结果参见“发送捺印指掌纹信息服务”的服务结果部分
    */
  @WebMethod
  override def sendNationalCancelLatent(userID: String, password: String, cancelLatentDh: DataHandler): DataHandler = ???

  /**
    * 发送信息反馈状态信息服务
    *
    * @param userID        请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password      请求方密码,请求方在请求服务系统上注册的识别密码
    * @param statusChanges 任务类型信息xml
    *                      <statusChanges>
    *                      <type>任务类型（根据请求参数确定）</type>
    *                      <task>
    *                      <content>任务类型对应信息ID</content>
    *                      <status>状态（根据业务场景确定状态内容）</status>
    *                      </task>
    *                      <task>
    *                      <content>任务类型对应信息ID</content>
    *                      <status>状态（根据业务场景确定状态内容）</status>
    *                      </task>
    *                      ……
    *                      </statusChanges>
    * @return 返回结果参见“发送捺印指掌纹信息服务”的服务结果部分
    */
  @WebMethod
  override def sendStatusChanges(userID: String, password: String, statusChanges: DataHandler): DataHandler = ???

  /**
    * 获取捺印指掌纹信息服务
    *
    * @param userID   请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param IDType   编号类型 1 原始系统_案事件编号; 2 案事件编号;3 现场勘验编号；4 现场物证编号。
    * @param bh       编号 需要获取捺印指掌纹信息人员的编号，多值用“,”分隔，最多256个。
    * @return 一个捺印指掌纹交换文件,如无可获取的捺印指掌纹信息则返回一个空捺印指掌纹交换文件
    */
  @WebMethod
  override def getFingerPrint(userID: String, password: String, IDType: Int, bh: DataHandler): DataHandler = {
    new DataHandler(new ByteArrayDataSource(Source.fromFile("/NYZZW-5224010107772013070452.ftpx").mkString.getBytes))
  }

  /**
    * 获取现场指掌纹信息服务
    *
    * @param userID   请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param IDType   编号类型 1 原始系统_案事件编号; 2 案事件编号;3 现场勘验编号；4 现场物证编号。
    * @param bh       编号,需要获取现场指掌纹信息编号，多值用“,”分隔，最多256个
    * @return 一个现场指掌纹交换文件,如无可获取的现场指掌纹信息则返回一个空现场指掌纹交换文件
    */
  @WebMethod
  override def getLatent(userID: String, password: String, IDType: Int, bh: DataHandler): DataHandler = {
    new DataHandler(new ByteArrayDataSource(Source.fromInputStream(getClass.getResourceAsStream("/a.xml")).mkString.getBytes))
  }

  /**
    * 获取新增捺印指掌纹信息服务
    *
    * @param userID   请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个捺印指掌纹交换文件,如无可获取的捺印指掌纹信息则返回一个空捺印指掌纹交换文件
    */
  @WebMethod
  override def getNewPrint(userID: String, password: String): DataHandler = ???

  /**
    * 获取新增现场指掌纹信息服务
    *
    * @param userID   请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个现场指掌纹交换文件,如无可获取的现场指掌纹信息则返回一个空现场指掌纹交换文件
    */
  @WebMethod
  override def getNewLatent(userID: String, password: String): DataHandler = ???

  /**
    * 获取全国现场指掌纹信息服务
    *
    * @param userID   请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个现场指掌纹交换文件,如无可获取的现场指掌纹信息则返回一个空现场指掌纹交换文件
    */
  @WebMethod
  override def getNationalLatent(userID: String, password: String): DataHandler = ???

  /**
    * 获取现场指掌纹撤销信息服务
    *
    * @param userID   请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个撤销现场指掌纹交换文件,如无可获取的信息则返回一个空撤销现场指掌纹信息包
    */
  @WebMethod
  override def getNationalCancelLatent(userID: String, password: String): DataHandler = ???

  /**
    * 获取正查任务信息服务
    *
    * @param userID   请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个指掌纹正查任务信息交换文件,如无可获取的信息则返回一个空正查任务信息交换文件
    */
  @WebMethod
  override def getLTTask(userID: String, password: String): DataHandler = ???

  /**
    * 获取倒查任务信息服务
    *
    * @param userID   请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个指掌纹倒查任务信息交换文件,如无可获取的信息则返回一个空倒查任务信息交换文件
    */
  @WebMethod
  override def getTLTask(userID: String, password: String): DataHandler = ???

  /**
    * 获取查重任务信息服务
    *
    * @param userID   请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个指掌纹查重任务信息交换文件,如无可获取的信息则返回一个空查重任务信息交换文件
    */
  @WebMethod
  override def getTTTask(userID: String, password: String): DataHandler = ???

  /**
    * 获取串查任务信息服务
    *
    * @param userID   请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个指掌纹串查任务信息交换文件,如无可获取的信息则返回一个空串查任务信息交换文件
    */
  @WebMethod
  override def getLLTask(userID: String, password: String): DataHandler = ???

  /**
    * 获取正查比对结果信息服务
    *
    * @param userID   请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个指掌纹正查比对结果交换文件,如无可获取的信息则返回一个空正查比对结果交换文件
    */
  @WebMethod
  override def getLTResult(userID: String, password: String): DataHandler = ???

  /**
    * 获取倒查比对结果信息服务
    *
    * @param userID   请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个指掌纹倒查比对结果交换文件,如无可获取的信息则返回一个空倒查比对结果交换文件
    */
  @WebMethod
  override def getTLResult(userID: String, password: String): DataHandler = ???

  /**
    * 获取查重比对结果信息服务
    *
    * @param userID   请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个指掌纹查重比对结果交换文件,如无可获取的信息则返回一个空查重比对结果交换文件
    */
  @WebMethod
  override def getTTResult(userID: String, password: String): DataHandler = ???

  /**
    * 获取串查比对结果信息服务
    *
    * @param userID   请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个指掌纹串查比对结果交换文件,如无可获取的信息则返回一个空串查比对结果交换文件
    */
  @WebMethod
  override def getLLResult(userID: String, password: String): DataHandler = ???

  /**
    * 获取正查和倒查比中信息服务
    *
    * @param userID   请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个指掌纹正查和倒查比中信息交换文件,如无可获取的信息则返回一个空正查和倒查比中信息交换文件
    */
  @WebMethod
  override def getLTHitResult(userID: String, password: String): DataHandler = ???

  /**
    * 获取查重比中信息服务
    *
    * @param userID   请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个指掌纹查重比中信息交换文件,如无可获取的信息则返回一个空查重比中信息交换文件
    */
  @WebMethod
  override def getTTHitResult(userID: String, password: String): DataHandler = ???

  /**
    * 获取串查比中信息服务
    *
    * @param userID   请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个指掌纹串查比中信息交换文件,如无可获取的信息则返回一个空串查比中信息交换文件
    */
  @WebMethod
  override def getLLHitResult(userID: String, password: String): DataHandler = ???

  /**
    * 获取反馈交换信息状态信息服务
    *
    * @param userID     请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password   请求方密码,请求方在请求服务系统上注册的识别密码
    * @param statusType 反馈信息xml
    *                   <statusType>
    *                   <type>任务类型（根据业务场景确定任务类型）</type>
    *                   </statusType>
    * @return <statusChanges>
    *         <type>任务类型（根据请求参数确定）</type>
    *         <task>
    *         <content>任务类型对应信息ID</content>
    *         <status>状态（根据业务场景确定状态内容）</status>
    *         </task>
    *         <task>
    *         <content>任务类型对应信息ID</content>
    *         <status>状态（根据业务场景确定状态内容）</status>
    *         </task>
    *         ……
    *         <statusChanges>
    *
    */
  @WebMethod
  override def getStatusChanges(userID: String, password: String, statusType: DataHandler): DataHandler = ???
}
