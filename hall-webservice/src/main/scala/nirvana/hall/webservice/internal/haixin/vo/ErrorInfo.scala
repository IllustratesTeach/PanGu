package nirvana.hall.webservice.internal.haixin.vo

import java.util
import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlRootElement}

/**
  * 综采对接错误信息
  * Created by zqLuo
  */
@XmlRootElement(name = "List")
@XmlAccessorType(XmlAccessType.FIELD)
class ErrorInfo {
  var Item = new util.ArrayList[ErrorInfoItem]()
}

@XmlAccessorType(XmlAccessType.FIELD)
class ErrorInfoItem{
  var time:String = _
  var exception:String = _
}
