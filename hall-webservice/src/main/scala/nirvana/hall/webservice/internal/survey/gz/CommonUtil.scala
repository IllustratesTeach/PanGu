package nirvana.hall.webservice.internal.survey.gz

import org.apache.commons.lang.StringUtils

/**
  * Created by yuchen on 2017/11/21.
  */
object CommonUtil {

  def appendParam(param:String*): String ={
    var paramStr = Constant.EMPTY
    for(i<-param) {
      paramStr += i.concat("|")
    }
    paramStr.substring(0,paramStr.length-1)
  }


  def isNullOrEmpty(str:String):Boolean = {
    var bStr = false
    if(StringUtils.isEmpty(str) || StringUtils.isBlank(str)){
      bStr = true
    }
    bStr
  }
}
