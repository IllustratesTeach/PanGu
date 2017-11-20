package nirvana.hall.webservice.internal.survey.gz.vo

import java.util
import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlRootElement}

/**
  * Created by ssj on 2017/11/16.
  */
@XmlRootElement(name = "List")
@XmlAccessorType(XmlAccessType.FIELD)
class CaseTextList{
  var K = new util.ArrayList[ListCaseNode]()
}
@XmlAccessorType(XmlAccessType.FIELD)
class ListCaseNode{
  var CaseID :String = _
  var ClassCode1 :String = _
  var ClassCode2 :String = _
  var ClassCode3 :String = _
  var Date :String = _
  var OccurPlaceCode :String = _
  var OccurPlace :String = _
  var Comment :String = _
  var HomicideCaseFlag :String = _
  var MoneyInvolved :String = _
  var GatherUnitCode :String = _
  var GatherUnitName :String = _
  var GatherDate :String = _
  var GatherManName :String = _
  var SuspiciousPlaceCode1 :String = _
  var SuspiciousPlaceCode2 :String = _
  var SuspiciousPlaceCode3 :String = _
  var UrgeLevel :String = _
  var Bonus :String = _
  var CooperationUnitCode :String = _
  var CooperationUnitName :String = _
  var CooperationDate :String = _
  var CooperationStatus :String = _
  var WithDrawReason :String = _
  var CaseStatus :String = _
  var FingerCount :String = _
  var SendCount :String = _
}