package nirvana.hall.webservice.services.fpt

import javax.activation.DataHandler
import javax.jws.{WebMethod, WebService}

/**
  * Created by songpeng on 2017/11/3.
  * 指掌纹信息应用服务技术规范(5.0) 接口服务
  */
//TODO 设定 serviceName,targetNamespace
@WebService(serviceName = "", targetNamespace = "")
trait FingerPalmAppServer {

  /**
    * 发送捺印指掌纹信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param NYZZWdh 捺印指掌纹信息交换文件，交换文件内容参见《指掌纹交换文件格式技术规范》，须符合捺印指掌纹信息值域要求
    * @return <?xml version="1.0" encoding="UTF-8"?>
    *         <RESPONSE>
    *           <STATUS>SUCCESS</STATUS>
    *           <RETURNCODE>10101</RETURNCODE>
    *           <MSG/>
    *         </RESPONSE>
    *         STATUS	SUCCESS/FAIL  标识请求是否是成功的
    *         RETURNCODE	参见“附录A：服务返回状态码”
    *         MSG	服务方可以在此处返回有助于调试的信息。例如：各类质量检查错误
    */
  @WebMethod def sendFingerPrint(userID: String, password: String, NYZZWdh: DataHandler): DataHandler

  /**
    * 发送现场指掌纹信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param XCZZWdh 现场指掌纹交换文件
    * @return 返回结果参见“发送捺印指掌纹信息服务”的服务结果部分
    */
  @WebMethod def sendLatent(userID: String, password: String, XCZZWdh: DataHandler): DataHandler

  /**
    * 发送现场指掌纹查询请求信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param LatentTaskdh 现场指掌纹查询请求交换文件
    * @return 返回结果参见“发送捺印指掌纹信息服务”的服务结果部分
    */
  @WebMethod def sendLatentTask(userID: String, password: String, LatentTaskdh: DataHandler): DataHandler

  /**
    * 发送捺印指掌纹查询请求信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param PrintTaskdh 捺印指掌纹查询请求交换文件
    * @return
    */
  @WebMethod def sendPrintTask(userID: String, password: String, PrintTaskdh: DataHandler): DataHandler

  /**
    * 发送指掌纹正查比对结果信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param LTResultdh 指掌纹正查比对结果交换文件
    * @return
    */
  @WebMethod def sendLTResult(userID: String, password: String, LTResultdh: DataHandler): DataHandler

  /**
    * 发送指掌纹倒查比对结果信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param TLResultdh 指掌纹倒查比对结果交换文件
    * @return 返回结果参见“发送捺印指掌纹信息服务”的服务结果部分
    */
  @WebMethod def sendTLResult(userID: String, password: String, TLResultdh: DataHandler): DataHandler

  /**
    * 发送指掌纹查重比对结果信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param TTResultdh 指掌纹查重比对结果交换文件
    * @return 返回结果参见“发送捺印指掌纹信息服务”的服务结果部分
    */
  @WebMethod def sendTTResult(userID: String, password: String, TTResultdh: DataHandler): DataHandler

  /**
    * 发送指掌纹串查比对结果信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param LLResultdh 指掌纹串查比对结果交换文件
    * @return 返回结果参见“发送捺印指掌纹信息服务”的服务结果部分
    */
  @WebMethod def sendLLResult(userID: String, password: String, LLResultdh: DataHandler): DataHandler

  /**
    * 发送正查和倒查比中信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param LTHitResultdh 指掌纹正查比中信息交换文件
    * @return 返回结果参见“发送捺印指掌纹信息服务”的服务结果部分
    */
  @WebMethod def sendLTHitResult(userID: String, password: String, LTHitResultdh: DataHandler): DataHandler

  /**
    * 发送查重比中结果信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param TTHitResultdh 指掌纹查重比中信息交换文件
    * @return 返回结果参见“发送捺印指掌纹信息服务”的服务结果部分
    */
  @WebMethod def sendTTHitResult(userID: String, password: String, TTHitResultdh: DataHandler): DataHandler

  /**
    * 发送串查比中结果信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param LLHitResultdh 指掌纹串查比中信息交换文件
    * @return 返回结果参见“发送捺印指掌纹信息服务”的服务结果部分
    */
  @WebMethod def sendLLHitResult(userID: String, password: String, LLHitResultdh: DataHandler): DataHandler

  /**
    * 发送现场指掌纹撤销信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param cancelLatentDh 发送现场指掌纹撤销信息交换文件
    * @return 返回结果参见“发送捺印指掌纹信息服务”的服务结果部分
    */
  @WebMethod def sendNationalCancelLatent(userID: String, password: String, cancelLatentDh: DataHandler): DataHandler

  /**
    * 发送信息反馈状态信息服务
    * @param userID        请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password      请求方密码,请求方在请求服务系统上注册的识别密码
    * @param statusChanges 任务类型信息xml
    *                     <statusChanges>
    *                       <type>任务类型（根据请求参数确定）</type>
    *                       <task>
    *                         <content>任务类型对应信息ID</content>
    *                         <status>状态（根据业务场景确定状态内容）</status>
    *                       </task>
    *                       <task>
    *                         <content>任务类型对应信息ID</content>
    *                         <status>状态（根据业务场景确定状态内容）</status>
    *                       </task>
    *                       ……
    *                     </statusChanges>
    *
    * @return 返回结果参见“发送捺印指掌纹信息服务”的服务结果部分
    */
  @WebMethod def sendStatusChanges(userID: String, password: String, statusChanges: DataHandler): DataHandler

  /**
    * 获取捺印指掌纹信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param IDType 编号类型 1 原始系统_案事件编号; 2 案事件编号;3 现场勘验编号；4 现场物证编号。
    * @param bh 编号 需要获取捺印指掌纹信息人员的编号，多值用“,”分隔，最多256个。
    * @return 一个捺印指掌纹交换文件,如无可获取的捺印指掌纹信息则返回一个空捺印指掌纹交换文件
    */
  @WebMethod def getFingerPrint(userID: String, password: String, IDType: Int, bh: DataHandler): DataHandler

  /**
    * 获取现场指掌纹信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param IDType 编号类型 1 原始系统_案事件编号; 2 案事件编号;3 现场勘验编号；4 现场物证编号。
    * @param bh 编号,需要获取现场指掌纹信息编号，多值用“,”分隔，最多256个
    * @return 一个现场指掌纹交换文件,如无可获取的现场指掌纹信息则返回一个空现场指掌纹交换文件
    */
  @WebMethod def getLatent(userID: String, password: String, IDType: Int, bh: DataHandler): DataHandler

  /**
    * 获取新增捺印指掌纹信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个捺印指掌纹交换文件,如无可获取的捺印指掌纹信息则返回一个空捺印指掌纹交换文件
    */
  @WebMethod def getNewPrint(userID: String, password: String): DataHandler

  /**
    * 获取新增现场指掌纹信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个现场指掌纹交换文件,如无可获取的现场指掌纹信息则返回一个空现场指掌纹交换文件
    */
  @WebMethod def getNewLatent(userID: String, password: String): DataHandler

  /**
    * 获取全国现场指掌纹信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个现场指掌纹交换文件,如无可获取的现场指掌纹信息则返回一个空现场指掌纹交换文件
    */
  @WebMethod def getNationlLatent(userID: String, password: String): DataHandler

  /**
    * 获取现场指掌纹撤销信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个撤销现场指掌纹交换文件,如无可获取的信息则返回一个空撤销现场指掌纹信息包
    */
  @WebMethod def getNationalCancelLatent(userID: String, password: String): DataHandler

  /**
    * 获取正查任务信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个指掌纹正查任务信息交换文件,如无可获取的信息则返回一个空正查任务信息交换文件
    */
  @WebMethod def getLTTask(userID: String, password: String): DataHandler

  /**
    * 获取倒查任务信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个指掌纹倒查任务信息交换文件,如无可获取的信息则返回一个空倒查任务信息交换文件
    */
  @WebMethod def getTLTask(userID: String, password: String): DataHandler

  /**
    * 获取查重任务信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个指掌纹查重任务信息交换文件,如无可获取的信息则返回一个空查重任务信息交换文件
    */
  @WebMethod def getTTTask(userID: String, password: String): DataHandler

  /**
    * 获取串查任务信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个指掌纹串查任务信息交换文件,如无可获取的信息则返回一个空串查任务信息交换文件
    */
  @WebMethod def getLLTask(userID: String, password: String): DataHandler

  /**
    * 获取正查比对结果信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个指掌纹正查比对结果交换文件,如无可获取的信息则返回一个空正查比对结果交换文件
    */
  @WebMethod def getLTResult(userID: String, password: String): DataHandler

  /**
    * 获取倒查比对结果信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个指掌纹倒查比对结果交换文件,如无可获取的信息则返回一个空倒查比对结果交换文件
    */
  @WebMethod def getTLResult(userID: String, password: String): DataHandler

  /**
    * 获取查重比对结果信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个指掌纹查重比对结果交换文件,如无可获取的信息则返回一个空查重比对结果交换文件
    */
  @WebMethod def getTTResult(userID: String, password: String): DataHandler

  /**
    * 获取串查比对结果信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个指掌纹串查比对结果交换文件,如无可获取的信息则返回一个空串查比对结果交换文件
    */
  @WebMethod def getLLResult(userID: String, password: String): DataHandler

  /**
    *	获取正查和倒查比中信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个指掌纹正查和倒查比中信息交换文件,如无可获取的信息则返回一个空正查和倒查比中信息交换文件
    */
  @WebMethod def getLTHitResult(userID: String, password: String): DataHandler

  /**
    *	获取查重比中信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个指掌纹查重比中信息交换文件,如无可获取的信息则返回一个空查重比中信息交换文件
    */
  @WebMethod def getTTHitResult(userID: String, password: String): DataHandler

  /**
    *	获取串查比中信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return 一个指掌纹串查比中信息交换文件,如无可获取的信息则返回一个空串查比中信息交换文件
    */
  @WebMethod def getLLHitResult(userID: String, password: String): DataHandler

  /**
    * 获取反馈交换信息状态信息服务
    * @param userID     请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password   请求方密码,请求方在请求服务系统上注册的识别密码
    * @param statusType 反馈信息xml
    *                   <statusType>
    *                     <type>任务类型（根据业务场景确定任务类型）</type>
    *                   </statusType>
    * @return <statusChanges>
    *           <type>任务类型（根据请求参数确定）</type>
    *           <task>
    *             <content>任务类型对应信息ID</content>
    *             <status>状态（根据业务场景确定状态内容）</status>
    *           </task>
    *           <task>
    *             <content>任务类型对应信息ID</content>
    *             <status>状态（根据业务场景确定状态内容）</status>
    *           </task>
    *         ……
    *         <statusChanges>
    *
    */
  @WebMethod def getStatusChanges(userID: String, password: String, statusType: DataHandler): DataHandler

}
