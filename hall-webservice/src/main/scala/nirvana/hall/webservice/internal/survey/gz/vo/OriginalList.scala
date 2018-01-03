package nirvana.hall.webservice.internal.survey.gz.vo

import java.util
import javax.xml.bind.annotation._

/**
  * Created by ssj on 2017/11/16.
  */
@XmlRootElement(name = "list")
@XmlAccessorType(XmlAccessType.FIELD)
class OriginalList{
  var k = new util.ArrayList[ListNode]()
}
@XmlAccessorType(XmlAccessType.FIELD)
class ListNode{
  var AJMC :String = _      //案件名称
  var XCKYBH :String = _    //现场勘验编号
  var XCWZBH :String = _    //现场物证编号
}




