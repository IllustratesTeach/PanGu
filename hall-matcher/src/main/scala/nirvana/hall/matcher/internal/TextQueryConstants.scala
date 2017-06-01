package nirvana.hall.matcher.internal

/**
  * Created by songpeng on 2017/3/6.
  * 文本查询常量类
  *
  * 说明：
  * COL_NAME_*
  *   该类属性代表Lucene索引字段名，
  *   为了方便数据库读取数据统一规范, 跟数据库sql字段一一对应，如果与数据库字段名称不一致，则使用sql别名。也尽量跟查询条件KEY值保持一致
  *   捺印跟现场不能存在相同的COL_NAME
  *
  */
object TextQueryConstants {
  val OCCUR_SHOULD = "yes"
  val OCCUR_MUST_NOT = "no"

  //文本查询json KEY
  val PERSONID_BEG1 = "personIdST1"
  val PERSONID_END1 = "personIdED1"
  val PERSONID_BEG2 = "personIdST2"
  val PERSONID_END2 = "personIdED2"
  val PERSONID_OCCUR1 = "sendKey1Str"
  val PERSONID_OCCUR2 = "sendKey2Str"
  val BIRTHDAY_BEG = "birthdayST"
  val BIRTHDAY_END = "birthdayED"
  val GATHERDATE_BEG = "gatherDateST"
  val GATHERDATE_END = "gatherDateED"
  val IMPKEYS = "impKeys"

  val CASEID_BEG1 = "caseIdST1"
  val CASEID_END1 = "caseIdED1"
  val CASEID_BEG2 = "caseIdST2"
  val CASEID_END2 = "caseIdED2"
  val CASEID_OCCUR1 = "sendKey1Str"
  val CASEID_OCCUR2 = "sendKey2Str"
  val PERSON_NAME = "name"
  val CASEOCCURDATE_BEG = "caseOccurDateBeg"
  val CASEOCCURDATE_END = "caseOccurDateEnd"
  val IDCARDNO = "idcardno"  //身份证
  val IS_HOMONYM = "isHomonym" //姓名是否同音

  //案件
  //贵州
  val COL_NAME_CASEID = "caseId"
  val COL_NAME_CID_PRE = "cId_pre"
  val COL_NAME_CID_DEPT = "cId_deptCode"
  val COL_NAME_CID_DATE = "cId_date"
  val COL_NAME_CARDID = "cardId"
  val COL_NAME_CASECLASSCODE = "caseClassCode"
  val COL_NAME_CASENATURE = "caseNature"
  val COL_NAME_CASEOCCURPLACECODE = "caseOccurPlaceCode"
  val COL_NAME_SUSPICIOUSAREACODE = "suspiciousAreaCode"
  val COL_NAME_ISMURDER = "isMurder"
  val COL_NAME_ISASSIST = "isAssist"
  val COL_NAME_ASSISTLEVEL = "assistLevel"
  val COL_NAME_CASESTATE = "caseState"
  val COL_NAME_DELETAG = "deletag"
  val COL_NAME_ISPALM = "isPalm"
  val COL_NAME_CASEOCCURDATE = "caseOccurDate"

  //捺印
  //贵州
  val COL_NAME_PERSONID = "personId"
  val COL_NAME_PID_PRE = "pId_pre"
  val COL_NAME_PID_DEPT = "pId_deptCode"
  val COL_NAME_PID_DATE = "pId_date"
  val COL_NAME_GATHERCATEGORY = "gatherCategory"
  val COL_NAME_GATHERTYPE = "gatherType"
  val COL_NAME_DOOR = "door"
  val COL_NAME_ADDRESS = "address"
  val COL_NAME_SEXCODE = "sexCode"
  val COL_NAME_NAME = "name"
  val COL_NAME_DATASOURCES = "dataSources"
  val COL_NAME_CASECLASS = "caseClass"
  val COL_NAME_BIRTHDAY = "birthday"
  val COL_NAME_GATHERDATE = "gatherDate"

  //青岛追加
  val COL_NAME_ISCHECKED = "isChecked"
  val COL_NAME_LTSTATUS = "ltStatus"
  val COL_NAME_CASESOURCE = "caseSource"
  val COL_NAME_CASEOCCURPLACEDETAIL = "caseOccurPlaceDetail"
  val COL_NAME_EXTRACTOR = "extractor"
  val COL_NAME_EXTRACTUNITCODE = "extractUnitCode"
  val COL_NAME_EXTRACTUNITNAME = "extractUnitName"
  val COL_NAME_BROKENSTATUS = "brokenStatus"
  val COL_NAME_CREATORUNITCODE = "creatorUnitCode"
  val COL_NAME_UPDATORUNITCODE = "updatorUnitCode"
  val COL_NAME_INPUTPSN = "inputpsn"
  val COL_NAME_MODIFIEDPSN = "modifiedpsn"
  val COL_NAME_SPELLNAME = "spellName" //姓名拼音
}
