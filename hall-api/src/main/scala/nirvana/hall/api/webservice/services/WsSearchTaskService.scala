package nirvana.hall.api.webservice.services

import javax.activation.DataHandler
import javax.jws.{WebMethod, WebService}

/**
  * 互查系统提供的获取比对任务接口
  */
@WebService(serviceName = "WsSearchTaskService", targetNamespace = "http://www.egfit.com/")
trait WsSearchTaskService {

  /**
    * 获取十指指纹查重\现场正查协查任务
    * @param useid 用户名
    * @param password 密码
    * @return FPT文件。返回的FPT文件信息需用soap协议的附件形式存储。
    *         若当前没有比对任务，则返回一个空FPT文件，即只有第一类记录的文件。
    */
  @WebMethod def getSearchTask(useid: String, password: String): DataHandler

  /**
    * 指纹自动识别系统比对完成后返回比对结果。无比对结果的，返回一个比对结果项为空的FPT文件
    * @param userid 用户名
    * @param password 密码
    * @param taskControlID 任务控制号
    * @param isSuccess 指纹系统处理是否成功，true：成功，false：失败
    * @return
    */
  @WebMethod def setSearchTaskStatus(userid: String, password: String, taskControlID: String, isSuccess: Boolean): Int

  /**
    * 指纹自动识别系统通过调用互查系统提供的接口将比对结果返回至互查系统。
    * 若无比对结果，返回的FPT文件中的比对结果项为空。返回的FPT文件信息需用soap的附件形式存储。
    * 无论有无比对结果均需要在FPT文件中填写获取协查任务中的第一类记录的任务控制号
    * @param userid
    * @param password
    * @param hd
    * @return
    */
  @WebMethod def setSearchResult(userid: String, password: String, hd: DataHandler): Int
}
