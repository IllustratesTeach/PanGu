package nirvana.hall.webservice.internal.survey.gz

/**
  * 标采与指纹系统对接变量设置
  * Created by ssj on 2017/11/17.
  */
object Constant {

  final val EMPTY = ""
  final val DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss"

  final val SURVEY_CODE_INIT = 0
  final val SURVEY_CODE_KNO_SUCCESS = 1
  final val SURVEY_CODE_KNO_FAIL = -1
  final val SURVEY_CODE_CASEID_ERROR = -2
  final val SURVEY_CODE_CASEID_SUCCESS = 2

  final val SNO_SUCCESS = 1

  final val INIT = 0


  final val  GET_ORIGINAL_DATA_COUNT = "getOriginalDataCount"
  final val  GET_ORIGINAL_DATA_LIST = "getOriginalDataList"
  final val  GET_CASE_NO = "getCaseNo"
  final val  GET_ORIGINAL_DATA = "getOriginalData"
  final val  FBUSECONDITION = "FBUseCondition"
  final val  GET_RECEPTION_NO = "getReceptionNo"
  final val  FBMatchCondition = "FBMatchCondition"

  final val  GET_TEXT_INFO = "T"
  final val  GET_FINGER_FPT = "F"
  final val  GET_PALM_FPT = "P"

  final val  FPT_PARSE_FAIL = "9"
  final val  IMAGE_UNQUALIFIED = "8"

}
