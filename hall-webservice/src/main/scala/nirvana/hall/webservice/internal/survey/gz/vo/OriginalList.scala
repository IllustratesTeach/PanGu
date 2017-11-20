package nirvana.hall.webservice.internal.survey.gz.vo

import java.util
import javax.xml.bind.annotation._

/**
  * Created by ssj on 2017/11/16.
  */
@XmlRootElement(name = "List")
@XmlAccessorType(XmlAccessType.FIELD)
class OriginalList{
  var K = new util.ArrayList[ListNode]()
}
@XmlAccessorType(XmlAccessType.FIELD)
class ListNode{
  var K_No :String = _
  var S_No :String = _
  var card_type :String = _
  var CASE_NAME :String = _
}




