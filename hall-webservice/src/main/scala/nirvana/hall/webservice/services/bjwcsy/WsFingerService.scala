package nirvana.hall.webservice.services.bjwcsy

import javax.activation.DataHandler
import javax.jws.{WebMethod, WebParam, WebService}

/**
  * 为互查系统提供的接口
  */
@WebService(serviceName = "WsFingerService", targetNamespace = "http://www.egfit.com/")
trait WsFingerService {

  /**
    * 查询十指指纹文字信息供用户选择进行任务的协查
    * @param userid 用户名
    * @param password 密码
    * @param ryno 人员编号
    * @param xm 姓名
    * @param xb 性别
    * @param idno 身份证号码
    * @param zjlb 证件类别
    * @param zjhm 证件号码
    * @param hjddm 户籍地代码
    * @param xzzdm 现住址代码
    * @param rylb 人员类别
    * @param ajlb 案件类别
    * @param qkbs 前科标识
    * @param xcjb 协查级别
    * @param nydwdm 捺印单位代码
    * @param startnydate 开始时间（检索捺印时间，时间格式YYYYMMDDHHMM）
    * @param endnydate 结束时间（检索捺印时间，时间格式YYYYMMDDHHMM）
    * @return 返回的FPT文件信息需用soap的附件形式存储，只返回相应文字信息，不包含图像信息（FPT文件中145项“人像图像数据长度”应为0,147项“发送指纹个数”应为0）
    *         每次返回的FPT信息数量不多于256条
    *         若没有查询出数据，则返回一个空FPT文件，即只有第一类记录
    */
  @WebMethod def getTenprintRecord(@WebParam(name="userid") userid: String,
                                   @WebParam(name="password") password: String,
                                   @WebParam(name="ryno") ryno: String,
                                   @WebParam(name="xm") xm: String,
                                   @WebParam(name="xb") xb: String,
                                   @WebParam(name="idno") idno: String,
                                   @WebParam(name="zjlb") zjlb: String,
                                   @WebParam(name="zjhm") zjhm: String,
                                   @WebParam(name="hjddm") hjddm: String,
                                   @WebParam(name="xzzdm") xzzdm: String,
                                   @WebParam(name="rylb") rylb: String,
                                   @WebParam(name="ajlb") ajlb: String,
                                   @WebParam(name="qkbs") qkbs: String,
                                   @WebParam(name="xcjb") xcjb: String,
                                   @WebParam(name="nydwdm") nydwdm: String,
                                   @WebParam(name="startnydate") startnydate: String,
                                   @WebParam(name="endnydate") endnydate: String): DataHandler

  /**
    * 通过查询参数返回指定返回相应人员的全部信息（包含文字信息和图像信息）
    * @param userid 用户名
    * @param password 密码
    * @param ryno 人员编号
    * @return 返回的FPT文件信息需用soap的附件形式存储
    *         若没有查询出数据，则返回一个空FPT文件，即只有第一类记录
    */
  @WebMethod def getTenprintFinger(@WebParam(name="userid") userid: String,
                                   @WebParam(name="password") password: String,
                                   @WebParam(name="ryno") ryno: String): DataHandler

  /**
    * 查询现场指纹文字信息供用户选择进行任务的协查
    * @param userid 用户名
    * @param password 密码
    * @param ajno 案件编号
    * @param ajlb 案件类别
    * @param fadddm 发案地代码
    * @param mabs 命案标识
    * @param xcjb 协查级别
    * @param xcdwdm 查询单位代码
    * @param startfadate 开始时间（检索发案时间，时间格式YYYYMMDD）
    * @param endfadate 结束时间（检索发案时间，时间格式YYYYMMDD）
    * @return 返回的FPT文件信息需用soap的附件形式存储，只返回相应文字信息，不包含图像信息（FPT文件中132项“发送现场指纹个数”应为0）
    *         若没有查询出数据，则返回一个空FPT文件，即只有第一类记录
    */
  @WebMethod def getLatent(@WebParam(name="userid") userid: String,
                           @WebParam(name="password") password: String,
                           @WebParam(name="ajno") ajno: String,
                           @WebParam(name="ajlb") ajlb: String,
                           @WebParam(name="fadddm") fadddm: String,
                           @WebParam(name="mabs") mabs: String,
                           @WebParam(name="xcjb") xcjb: String,
                           @WebParam(name="xcdwdm") xcdwdm: String,
                           @WebParam(name="startfadate") startfadate: String,
                           @WebParam(name="endfadate") endfadate: String): DataHandler

  /**
    * 通过查询参数返回指定返回相应案件的全部信息（包含文字信息和图像信息）
    * @param userid 用户名
    * @param password 密码
    * @param ajno 案件编号
    * @return 返回的FPT文件信息需用soap的附件形式存储，只返回相应文字信息，不包含图像信息（FPT文件中132项“发送现场指纹个数”应为0）
    *         若没有查询出数据，则返回一个空FPT文件，即只有第一类记录
    */
  @WebMethod def getLatentFinger(@WebParam(name="userid") userid: String,
                                 @WebParam(name="password") password: String,
                                 @WebParam(name="ajno") ajno: String): DataHandler
}
