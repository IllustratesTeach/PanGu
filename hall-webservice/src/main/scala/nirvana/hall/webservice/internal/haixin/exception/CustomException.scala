package nirvana.hall.webservice.internal.haixin.exception

/**
  * Created by yuchen on 2017/7/25.
  */
object CustomException {
  class InputParamIsNullOrEmptyException(exceptionInfo:String) extends Exception(exceptionInfo)
  class CollectSrcIsNotVaildException(exceptionInfo:String) extends Exception(exceptionInfo)
  class UserIsNotVaildException(exceptionInfo:String) extends Exception(exceptionInfo)
  class FingerExistException(exceptionInfo:String) extends Exception(exceptionInfo)
  class FingerNotExistException(exceptionInfo:String) extends Exception(exceptionInfo)
  class FPTNot4Exception(exceptionInfo: String) extends Exception(exceptionInfo)
  class IndoorPersonIdNotEqFPTPersonIdException(exceptionInfo: String) extends Exception(exceptionInfo)
}
