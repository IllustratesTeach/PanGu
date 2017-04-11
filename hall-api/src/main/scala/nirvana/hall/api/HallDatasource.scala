package nirvana.hall.api

object HallDatasource {

  final val TABLE_V62_TPCARD = "normaltp_tpcardinfo"
  final val TABLE_V62_CASE = "normallp_case"
  final val TABLE_V62_LATFINGER= "normallp_latfinger"
  final val TABLE_V62_LATPALM = "normallp_latpalm"
  final val TABLE_V62_QUERY = "normalquery_queryque"

  final val TABLE_V70_PERSON = "hall_ds_person"
  final val TABLE_V70_PERSON_FINGER = "hall_ds_gather_finger"
  final val TABLE_V70_PERSON_PALM = "hall_ds_gather_palm"
  final val TABLE_V70_PERSON_PORTRAIT = "hall_ds_gather_portrait"
  final val TABLE_V70_CASE  = "hall_ds_case"
  final val TABLE_V70_CASE_FINGER = "hall_ds_case_finger"
  final val TABLE_V70_CASE_FINGER_MNT = "hall_ds_case_finger_mnt"
  final val TABLE_V70_CASE_PALM = "hall_ds_case_palm"
  final val TABLE_V70_CASE_PALM_MNT = "hall_ds_case_palm_mnt"
  final val TABLE_V70_NORMALQUERY_QUERYQUE = "hall_ds_normalquery_queryque"

  final val DATA_SOURCE_HALL =  1

  final val SERVICE_TYPE_SYNC = 1
  final val SERVICE_TYPE_REMOTE = 2
  final val SERVICE_TYPE_UPLOAD = 3
  final val SERVICE_TYPE_DOWNLOAD = 4

  final val OPERATION_TYPE_ADD = 1
  final val OPERATION_TYPE_MODIFY = 2
  final val OPERATION_TYPE_DEL = 3

  final val SIGN =  "1"
}

class HallDatasource (_serviceid : String, _distServiceid :String,_ipSource: String,
                      _updateServiceType: Integer, _updateOperationType: Integer,
                      _createServiceType: Integer,_createOperationType: Integer
                     ) {

  var id: java.lang.String = _
  var serviceid: java.lang.String = _serviceid
  var distServiceid: java.lang.String = _distServiceid
  var servicePkid: java.lang.String = _
  var ipSource: java.lang.String = _ipSource
  var dataSource: java.lang.Integer = _
  var createServiceType: java.lang.Integer = _createServiceType
  var createOperationType: java.lang.Integer = _createOperationType
  var updateServiceType: java.lang.Integer = _updateServiceType
  var updateOperationType: java.lang.Integer = _updateOperationType
  var status: java.lang.Integer = _
  var sign: java.lang.Integer = _
  var remark: java.lang.String = _

  def this(_serviceid : String, _distServiceid :String,_ipSource: String,
           updateServiceType: Integer, updateOperationType: Integer
          ) {
    this(serviceid, distServiceid, ipSource, updateServiceType, updateOperationType, null, null)
  }

  def this() {
    this(_,_,_,_,_,_,_)
  }

}