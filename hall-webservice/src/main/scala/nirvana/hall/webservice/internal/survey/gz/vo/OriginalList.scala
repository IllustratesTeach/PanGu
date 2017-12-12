package nirvana.hall.webservice.internal.survey.gz.vo

import java.util
import javax.xml.bind.annotation._

/**
  * Created by ssj on 2017/11/16.
  */
@XmlRootElement(name = "LIST")
@XmlAccessorType(XmlAccessType.FIELD)
class OriginalList{
  var K = new util.ArrayList[ListNode]()
}
@XmlAccessorType(XmlAccessType.FIELD)
class ListNode{
  var K_NO :String = _
  var S_NO :String = _
  var CARD_TYPE :String = _
  var CASE_NAME :String = _
  var IP : String = _
}




