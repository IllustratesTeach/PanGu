package nirvana.hall.webservice.services.xingzhuan

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlElement, XmlRootElement}

object FingerPalmAppServerConstants {

  /* 服务返回状态码 */
  /**
    * 请求成功
    */
  val RESPONSE_SUCCESS:String = "10101"
  val RESPONSE_VALID_ERR_XML:String = "10201"
  val RESPONSE_VALID_ERR_BASE:String = "10301"
  //TODO 补全字典
}

@XmlRootElement(name = "RESPONSE")
class FingerPalmAppServerResponse{
  @XmlElement(name = "STATUS")
  var status: String = _
  @XmlElement(name = "RETURNCODE")
  var returnCode: String = _
  @XmlElement(name = "MSG")
  var msg: String = _
}

@XmlRootElement(name = "statusChanges")
class SendStatusChangesRequest{
  @XmlElement(name = "type")
  var typ: String = _
  @XmlElement(name = "task")
  var task: Array[SendStatusChangesTask] = _
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlElement(name = "task")
class SendStatusChangesTask {
  @XmlElement(name = "content")
  var content: String = _
  /** 现场勘验编号 */
  @XmlElement(name = "status")
  var status: String = _
}
