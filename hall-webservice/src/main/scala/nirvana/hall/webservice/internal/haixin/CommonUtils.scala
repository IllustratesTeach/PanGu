package nirvana.hall.webservice.internal.haixin

import org.apache.commons.lang.StringUtils

/**
  * Created by yuchen on 2017/7/26.
  */
object CommonUtils {

  def isNullOrEmpty(str:String):Boolean = {
      var bStr = false
      if(StringUtils.isEmpty(str) || StringUtils.isBlank(str)){
        bStr = true
      }
    bStr
  }

}
