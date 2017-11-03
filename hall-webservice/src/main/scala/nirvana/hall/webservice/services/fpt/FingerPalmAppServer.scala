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
    * @return
    */
  @WebMethod def sendFingerPrint(userID: String, password: String, NYZZWdh: DataHandler): DataHandler

  /**
    * 发送现场指掌纹信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param XCZZWdh
    * @return
    */
  @WebMethod def sendLatent(userID: String, password: String, XCZZWdh: DataHandler): DataHandler

  /**
    * 发送现场指掌纹查询请求信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param LatentTaskdh
    * @return
    */
  @WebMethod def sendLatentTask(userID: String, password: String, LatentTaskdh: DataHandler): DataHandler

  /**
    * 发送捺印指掌纹查询请求信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param PrintTaskdh
    * @return
    */
  @WebMethod def sendPrintTask(userID: String, password: String, PrintTaskdh: DataHandler): DataHandler

  /**
    * 发送指掌纹正查比对结果信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param LTResultdh
    * @return
    */
  @WebMethod def sendLTResult(userID: String, password: String, LTResultdh: DataHandler): DataHandler

  /**
    * 发送指掌纹倒查比对结果信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param TLResultdh
    * @return
    */
  @WebMethod def sendTLResult(userID: String, password: String, TLResultdh: DataHandler): DataHandler

  /**
    * 发送指掌纹查重比对结果信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param TTResultdh
    * @return
    */
  @WebMethod def sendTTResult(userID: String, password: String, TTResultdh: DataHandler): DataHandler

  /**
    * 发送指掌纹串查比对结果信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param LLResultdh
    * @return
    */
  @WebMethod def sendLLResult(userID: String, password: String, LLResultdh: DataHandler): DataHandler

  /**
    * 发送正查和倒查比中信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param LTHitResultdh
    * @return
    */
  @WebMethod def sendLTHitResult(userID: String, password: String, LTHitResultdh: DataHandler): DataHandler

  /**
    * 发送查重比中结果信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param TTHitResultdh
    * @return
    */
  @WebMethod def sendTTHitResult(userID: String, password: String, TTHitResultdh: DataHandler): DataHandler

  /**
    * 发送串查比中结果信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param LLHitResultdh
    * @return
    */
  @WebMethod def sendLLHitResult(userID: String, password: String, LLHitResultdh: DataHandler): DataHandler

  /**
    * 获取捺印指掌纹信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param asjxgrybh
    * @return
    */
  @WebMethod def getPrint(userID: String, password: String, asjxgrybh: DataHandler): DataHandler

  /**
    * 获取现场指掌纹信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return
    */
  @WebMethod def getNationalLatent(userID: String, password: String): DataHandler

  /**
    * 获取现场指掌纹撤销信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return
    */
  @WebMethod def getNationalCancelLatent(userID: String, password: String): DataHandler

  /**
    * 获取正查任务信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return
    */
  @WebMethod def getLTTask(userID: String, password: String): DataHandler

  /**
    * 获取倒查任务信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return
    */
  @WebMethod def getTLTask(userID: String, password: String): DataHandler

  /**
    * 获取查重任务信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return
    */
  @WebMethod def getTTTask(userID: String, password: String): DataHandler

  /**
    * 获取串查任务信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return
    */
  @WebMethod def getLLTask(userID: String, password: String): DataHandler

  /**
    * 获取正查比对结果信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return
    */
  @WebMethod def getLTResult(userID: String, password: String): DataHandler

  /**
    * 获取倒查比对结果信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return
    */
  @WebMethod def getTLResult(userID: String, password: String): DataHandler

  /**
    * 获取查重比对结果信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return
    */
  @WebMethod def getTTResult(userID: String, password: String): DataHandler

  /**
    * 获取串查比对结果信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return
    */
  @WebMethod def getLLResult(userID: String, password: String): DataHandler

  /**
    *	获取正查和倒查比中信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return
    */
  @WebMethod def getLTHitResult(userID: String, password: String): DataHandler

  /**
    *	获取查重比中信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return
    */
  @WebMethod def getTTHitResult(userID: String, password: String): DataHandler

  /**
    *	获取串查比中信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @return
    */
  @WebMethod def getLLHitResult(userID: String, password: String): DataHandler

  /**
    * 获取反馈交换信息状态信息服务
    * @param userID 请求方ID,请求方在请求服务系统上注册的唯一内部标识
    * @param password 请求方密码,请求方在请求服务系统上注册的识别密码
    * @param ztfkHandler
    * @return
    */
  @WebMethod def GetStatusChanges(userID: String, password: String, ztfkHandler: DataHandler): DataHandler

}
