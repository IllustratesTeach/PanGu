package nirvana.hall.webservice.internal.quality

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlRootElement}

/**
  * Created by mx on 2017/11/22.
  */
@XmlRootElement(name = "RESPONSE")
@XmlAccessorType(XmlAccessType.FIELD)
class ResponseInfo {

  var STATUS :String = _
  var RETURNCODE :String = _
  var MSG:String = _
}