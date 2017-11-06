package nirvana.hall.api.internal.fpt

/**
  * Created by Administrator on 2017/11/2.
  */
object FPT5Utils {
  /**
    * 判断字符串是否为空Null
    * @param sourse  源字符串
    * @return
    */
  def isNull(sourse: String):  Boolean = {
    var is = false
    if(sourse == null){
      is = true
    }
    is
  }

  /**
    * xsd校验根据length 校验。
    * 去空格后判断长度
    * @param sourse  源字符串
    * @param in  定义长度
    * @return
    */
  def replaceLength(sourse: String,in: Int):  Boolean = {
    var is = false
    if(sourse.replace(" ", "").length != in){
      is = true
    }
    is
  }
}
