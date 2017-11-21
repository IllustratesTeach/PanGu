package nirvana.hall.webservice.internal.survey.gz

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
}
