package nirvana.hall.webservice.services.xcky

import javax.activation.DataHandler
import javax.jws.{WebMethod, WebService}

/**
  * Created by songpeng on 2017/12/22.
  * 现场勘验信息系统接口
  * 2018年1月1日后，省级指掌纹自动识别系统中现场指掌纹信息的现场勘查编号、物证编号等必须与省级现场勘验系统保持一致，系统中的本地案件现场指掌纹信息（包括图片）只能从本地现场勘验系统获取，不得单独录入。省级指掌纹自动识别系统还要通过接口向省级现勘系统推送现场指掌纹正查及倒查比中关系信息、串查比中关系信息，确保指掌纹自动识别系统比中的案件线索及时推送。
  * 省级刑专系统从省级现场勘验系统获取现场指掌纹信息的接口参考本规范执行
  * 省级现场勘验系统服务器IP地址
  *   省份	服务器IP地址
  * 	北京	http://10.8.40.220:9080/xcky
  * 	天津	http://10.88.41.228:9080/xcky
  * 	河北	http://10.24.154.97:9080/xcky
  * 	山西	http://10.94.65.112:9080/xcky
  * 	内蒙古	http://10.100.11.224:9080/xcky
  * 	辽宁	http://10.78.33.192:9080/xcky
  * 	吉林	http://10.106.18.94:9080/xcky
  * 	黑龙江	http://10.112.1.240/xcky
  * 	上海	http://10.15.69.131/xcky
  * 	江苏	http://10.32.15.228/xcky
  * 	浙江	http://10.118.2.73:9080/xcky
  * 	安徽	http://10.124.1.23:9080/xcky
  * 	福建	http://10.130.101.52:9080/xcky
  * 	江西	http://10.136.31.73:9080/xcky
  * 	山东	http://10.48.147.37:9080/xcky
  * 	河南	http://10.56.5.37:9080/xcky
  * 	湖北	http://10.72.200.72:9080/xcky
  * 	湖南	http://10.142.72.24:9080/xcky
  * 	广东	http://10.40.51.209:9080/xcky
  * 	广西	http://10.148.2.33:9080/xcky
  * 	海南	http://10.184.14.150/xcky
  * 	重庆	http://10.154.132.90:9080/xcky
  * 	四川	http://10.64.1.205:9080/xcky
  * 	贵州	http://10.160.121.179:9080/xcky
  * 	云南	http://10.166.190.103:9080/xcky
  * 	西藏	http://10.188.32.221:9080/xcky
  * 	陕西	http://10.172.20.44/xcky
  * 	甘肃	http://10.178.8.210:9080/xcky
  * 	青海	http://10.192.19.201:9080/xcky
  * 	宁夏	http://10.196.11.190:9080/xcky
  * 	新疆	http://10.20.2.156:9080/xcky
  * 	新疆兵团	http://10.84.2.99:9080/xcky
  *
  * 	信息应用服务名称	信息应用服务说明
  * 1	获取待发送现场指掌纹数量服务	从现场勘验系统获取待发送现场指掌纹数量服务
  * 2	获取待发送指掌纹列表查询服务	从现场勘验系统获取待发送指掌纹列表查询服务
  * 3	获取现场指掌纹信息服务	从现场勘验系统获取现场指掌纹信息服务
  * 4	发送现场指掌纹状态服务	向现场勘验系统发送现场指掌纹状态服务
  * 5	获取现场服务器时间服务	从现场勘验系统获取现场服务器时间服务
  * 6	获取现场案件号服务	从现场勘验系统获取现场案件号服务
  * 7	获取现场接警号服务	从现场勘验系统获取现场接警号服务
  * 8	发送修改后现场指掌纹信息服务	向现场勘验系统发送修改后现场指掌纹信息服务
  * 9	发送现场指掌纹正查及倒查比中信息服务	向现场勘验系统发送现场指掌纹正查比中结果和倒查比中结果信息服务
  * 10发送现场指掌纹串查比中信息服务	向现场勘验系统发送现场指掌纹串查比中结果信息服务
  *
  */
@WebService(serviceName = "FPT50HandprintServiceService", targetNamespace = "http://xckyservice.hisign.com/")
trait FPT50HandprintService {

  /**
    * 获取待发送现场指掌纹数量服务
    * @param userID 请求方 ID , 请求方在请求服务系统上注册的唯一内部标识，必填。
    * @param password 请求方密码，请求方在请求服务系统上注册的识别密码，必填。
    * @param asjfsdd_xzqhdm 案事件发生地点_行政区划代码，必填。
    * @param zzhwlx 现场勘验编号，选填。
    * @param xckybh 查询指掌纹类型，’P’代表掌纹，’F’代表指纹，’A’代表全部。必填。
    * @param kssj 开始时间yyyy-mm-dd HH:MM:SS，选填，缺省为当月第一天开始时间。
    * @param jssj 结束时间yyyy-mm-dd HH:MM:SS，选填，缺省为当前时间
    * @return
    */
  @WebMethod def getFingerPrintCount(userID: String, password: String, asjfsdd_xzqhdm: String, zzhwlx: String, xckybh: String, kssj: String, jssj: String): String

  /**
    * 获取待发送现场指掌/纹列表查询服务接口
    * @param userID 请求方 ID , 请求方在请求服务系统上注册的唯一内部标识，必填。
    * @param password 请求方密码，请求方在请求服务系统上注册的识别密码，必填。
    * @param asjfsdd_xzqhdm 案事件发生地点_行政区划代码，必填。
    * @param zzhwlx 现场勘验编号，选填。
    * @param xckybh 查询指掌纹类型，’P’代表掌纹，’F’代表指纹，’A’代表全部。必填。
    * @param kssj 开始时间yyyy-mm-dd HH:MM:SS，选填，缺省为当月第一天开始时间。
    * @param jssj 结束时间yyyy-mm-dd HH:MM:SS，选填，缺省为当前时间
    * @param ks 记录开始位置，选填，缺省值为1。
    * @param js 记录结束位置，选填，缺省值为10。
    * @return <?xml version=”1.0” encoding=”utf-8”?>
    * <list>
    * <k>
    * <ajmc>案件名称</ajmc>
    * <xckybh>现场勘验编号</xckybh>
    * <xcwzbh>现场物证编号</xcwzbh>
    * </k>
    * </list>
    */
  @WebMethod def getFingerPrintList(userID: String, password: String, asjfsdd_xzqhdm: String, zzhwlx: String, xckybh: String, kssj: String, jssj: String, ks: Int, js: Int): DataHandler

  /**
    * 获取现场指掌纹信息服务的接口
    * @param userID 请求方 ID , 请求方在请求服务系统上注册的唯一内部标识，必填。
    * @param password 请求方密码，请求方在请求服务系统上注册的识别密码，必填。
    * @param xcwzbh 现场物证编号，必填
    * @return 现场指掌纹信息FPT5.0数据包。
    */
  @WebMethod def getFingerPrint(userID: String, password: String, xcwzbh: String): DataHandler

  /**
    * 发送现场指掌纹状态服务的接口
    * @param userID 请求方 ID , 请求方在请求服务系统上注册的唯一内部标识，必填。
    * @param password 请求方密码，请求方在请求服务系统上注册的识别密码，必填。
    * @param xcwzbh 现场物证编号，必填
    * @param resultType 反馈信息，必填
    *                   1：指掌纹系统建库
    *                   3：指纹比中
    *                   4：指纹未比中
    *                   8：指掌纹系统反馈无建库价值
    *                   9：指掌纹系统反馈数据包有问题
    * @return
    * -1：现场物证编号不存在
    * 0：反馈失败
    * 1：反馈成功
    *
    */
  @WebMethod def sendFBUseCondition(userID: String, password: String, xcwzbh: String, resultType: String): String

  /**
    * 获取现场服务器时间服务的接口
    * @return 返回时间格式：yyyy-mm-dd HH:MM:SS
    */
  @WebMethod def getSystemDateTime(): String

  /**
    * 获取现场案事件编号服务的接口
    * @param userID 请求方 ID , 请求方在请求服务系统上注册的唯一内部标识，必填。
    * @param password 请求方密码，请求方在请求服务系统上注册的识别密码，必填。
    * @param xckybh 现场勘验编号，必填。
    * @return 符合现场勘验编号的案事件编号
    */
  @WebMethod def getCaseNo(userID: String, password: String, xckybh: String): String

  /**
    * 获取现场接警号服务的接口
    * @param userID 请求方 ID , 请求方在请求服务系统上注册的唯一内部标识，必填。
    * @param password 请求方密码，请求方在请求服务系统上注册的识别密码，必填。
    * @param xckybh 现场勘验编号，必填
    * @return 符合现场勘验编号的接警编号
    */
  @WebMethod def getReceptionNo(userID: String, password: String, xckybh: String): String

  /**
    * 发送修改后现场指掌纹信息服务的接口
    * @param userID 请求方 ID , 请求方在请求服务系统上注册的唯一内部标识，必填。
    * @param password 请求方密码，请求方在请求服务系统上注册的识别密码，必填。
    * @param xckybh 现场勘验编号，必填
    * @param modiFingerPrint 与现场勘验编号对应的修改后的现场指掌纹信息包，包里只包含修改后的现场指掌纹数据，不包含案件信息
    * @return -1：现场勘验编号不存在
    *         0：发送失败
    *         1：发送成功
    */
  @WebMethod def sendModiFingerPrint (userID: String, password: String, xckybh: String, modiFingerPrint: DataHandler): String

  /**
    * 发送现场指掌纹正查及倒查比中信息服务的接口
    * @param userID 请求方 ID , 请求方在请求服务系统上注册的唯一内部标识，必填。
    * @param password 请求方密码，请求方在请求服务系统上注册的识别密码，必填。
    * @param xckybh 现场勘验编号，必填
    * @param LTHitResultdh 指掌纹正查及倒查比中信息交换文件
    * @return -1：现场勘验编号不存在
    *         0：发送失败
    *         1：发送成功
    */
  @WebMethod def sendLTHitResult(userID: String, password: String, xckybh: String, LTHitResultdh: DataHandler): String

  /**
    * 发送现场指掌纹串查比中信息服务的接口
    * @param userID 请求方 ID , 请求方在请求服务系统上注册的唯一内部标识，必填。
    * @param password 请求方密码，请求方在请求服务系统上注册的识别密码，必填。
    * @param xckybh 现场勘验编号，必填
    * @param LLHitResultdh 指掌纹串查比中信息交换文件
    * @return -1：现场勘验编号不存在
    *         0：发送失败
    *         1：发送成功
    */
  @WebMethod def sendLLHitResult(userID: String, password: String, xckybh: String, LLHitResultdh: DataHandler): String
}
