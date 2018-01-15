package nirvana.hall.webservice.internal.survey.gafis62

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlElement, XmlRootElement}

/**
  * Created by songpeng on 2017/12/24.
  */
object FPT50HandprintServiceConstants {
  val ZZHWLX_PALM = "P" //掌纹
  val ZZHWLX_FINGER = "F" //指纹
  val ZZHWLX_ALL = "A" //全部

  val RESULT_TYPE_ADD = "1" //指掌纹系统建库
  val RESULT_TYPE_MATCHED = "3" //指纹比中
  val RESULT_TYPE_UNMATCHED = "4" //指纹未比中
  val RESULT_TYPE_UNUSE = "8" //指掌纹系统反馈无建库价值
  val RESULT_TYPE_ERROR = "9" //指掌纹系统反馈数据包有问题

  val SEND_FBUSE_CONDITION_RESPONSE_UNEXIST = "-1"  //现场物证编号不存在
  val SEND_FBUSE_CONDITION_RESPONSE_FAIL = "0"  //反馈失败
  val SEND_FBUSE_CONDITION_RESPONSE_SUCCESS = "1" //反馈成功

}

/**
  * 获取待发送指掌纹列表查询服务接口(getFingerPrintList)返回结果getFingerPrintListResponse
  */
@XmlRootElement(name = "list")
class FingerPrintListResponse {
  @XmlElement(name = "k")
  var list: Array[FingerPrintListVo] = _
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlElement(name = "k")
class FingerPrintListVo {
  /** 案件名称 */
  @XmlElement(name = "ajmc")
  var ajmc: String = _
  /** 现场勘验编号 */
  @XmlElement(name = "xckybh")
  var xckybh: String = _
  /** 现场物证编号 */
  @XmlElement(name = "xcwzbh")
  var xcwzbh: String = _
}
