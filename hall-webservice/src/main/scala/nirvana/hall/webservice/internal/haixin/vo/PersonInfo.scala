package nirvana.hall.webservice.internal.haixin.vo

import java.util
import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlRootElement}

/**
  * 综采对接人员信息
  * Created by zqLuo
  */
@XmlRootElement(name = "List")
@XmlAccessorType(XmlAccessType.FIELD)
class PersonInfo {
  var Item = new util.ArrayList[PersonInfoItem]()
}

@XmlAccessorType(XmlAccessType.FIELD)
class PersonInfoItem{
  var personId:String = _
  var cardId:String = _
  var name:String = _
  var addressDetail:String = _
  var doorDetail:String = _
  var sex:String = _
  var gatherUnitName:String = _
  var gatherUserName:String = _
  var gatherDate:String = _
  var gatherReason:String = _
}
