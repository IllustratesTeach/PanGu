package nirvana.hall.webservice.util

import java.text.SimpleDateFormat
import java.util.Date

/**
  * Created by yuchen on 2017/4/21.
  */
object AFISConstant{

  /**
    * AFIS 分类
    */
   val XINGZHUAN = "0"
   val HAIXIN = "1"

  /**
    * 上报 状态
    */
  val NO_REPORTED = "0"
  val REPORTED = "1"


  val EMPTY = ""

  val SERVER_TIME = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date())


}
