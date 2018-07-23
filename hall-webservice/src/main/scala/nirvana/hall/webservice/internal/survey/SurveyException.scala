package nirvana.hall.webservice.internal.survey

/**
  * Created by yuchen on 2018/7/3.
  */
object SurveyException {
  class DataPackageNotAvailableException(exceptionInfo:String) extends Exception(exceptionInfo)
  class ImageException(exceptionInfo:String) extends Exception(exceptionInfo)
}
