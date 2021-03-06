package nirvana.hall.webservice.internal.survey.gz

object Constant {

  final val EMPTY = ""
  final val DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss"

  final val SURVEY_CODE_INIT = 0
  final val SURVEY_CODE_KNO_SUCCESS = 1
  final val SURVEY_CODE_KNO_FAIL = -1
  final val SURVEY_CODE_CASEID_ERROR = -2
  final val SURVEY_CODE_CASEID_SUCCESS = 2
  final val SURVEY_CODE_CASEID_REPEAT = 3 //未获取到数据包标识
  final val SURVEY_CODE_FILE_ERROR = -4
  final val SURVEY_CODE_DATA_FAIL = 9 //解析数据包异常
  final val SURVEY_CODE_FAIL = -9 //获取数据包后系统异常

  final val SNO_SUCCESS = 1

  final val INIT = 0


  final val  GET_FINGER_PRINT_COUNT = "getFingerPrintCount"
  final val  GET_FINGER_PRINT_LIST = "getFingerPrintList"
  final val  GET_CASE_NO = "getCaseNo"
  final val  GET_ORIGINAL_DATA = "getFingerPrint"
  final val  FBUSECONDITION = "FBUseCondition"
  final val  GET_RECEPTION_NO = "getReceptionNo"
  final val  FBMatchCondition = "FBMatchCondition"

//  final val  GET_TEXT_INFO = "T"
//  final val  GET_FINGER_FPT = "F"
//  final val  GET_PALM_FPT = "P"

  final val  FPT_PARSE_SUCCESS = "1"
  final val  FPT_PARSE_FAIL = "9"
  final val  IMAGE_UNQUALIFIED = "8"

}
