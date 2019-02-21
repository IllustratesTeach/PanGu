package nirvana.hall.webservice.internal.penaltytech.vo

import nirvana.hall.webservice.internal.penaltytech.JSONData

/**
  * @author <a href="yuchen_1997_200486@126.com">yuchen</a>.
  * @since 2018/12/8
  */
class Hit extends JSONData{
    var sourceTemplateFingerId:String = _
    var destTemplateFingerId:String = _
    var fingerPalmType:Int =_
    var hitDate:String = _
    var hitUser:String = _
    var hitUnit:String = _
    var verifyDate:String = _
    var verifyStatus:Int = _
    var inputTime:String = _
    var inputUser:String = _
    var inputUnit:String = _
    var deleteFlag:String = _
}
