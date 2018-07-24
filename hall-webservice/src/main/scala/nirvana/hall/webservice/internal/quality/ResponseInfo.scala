package nirvana.hall.webservice.internal.quality

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlRootElement}

/**
  * Created by mx on 2018/7/17.
  */
@XmlRootElement(name = "RESPONSE")
@XmlAccessorType(XmlAccessType.FIELD)
class ResponseInfo {

  var STATUS :String = _
  var RETURNCODE :String = _
  var MSG:String = _
}