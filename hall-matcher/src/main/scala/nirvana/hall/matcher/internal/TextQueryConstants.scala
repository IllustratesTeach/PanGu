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
  val PERSONID = "personId"
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
  val INPUTTIME_BEG = "inputtimeST"
  val INPUTTIME_END = "inputtimeED"
  val MODIFIEDTIME_BEG = "modifiedtimeST"
  val MODIFIEDTIME_END = "modifiedtimeED"
  val IMPKEYS = "impKeys"

  val CASEID = "caseId"
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
  val EXTRACTDATE_BEG = "extractDateBeg"
  val EXTRACTDATE_END = "extractDateEnd"

  //案件
  //贵州
  val COL_NAME_CASEID = CASEID
  val COL_NAME_CID_PRE = "cId_pre"
  val COL_NAME_CID_DEPT = "cId_deptCode"
  val COL_NAME_CID_DATE = "cId_date"
  val COL_NAME_CARDID = "cardId"
  val COL_NAME_CASECLASSCODE = "caseClassCode"
  val COL_NAME_CASECLASSCODE2 = "caseClassCode2"
  val COL_NAME_CASECLASSCODE3 = "caseClassCode3"
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
  val COL_NAME_PERSONID = PERSONID
  val COL_NAME_PID_PRE = "pId_pre"
  val COL_NAME_PID_DEPT = "pId_deptCode"
  val COL_NAME_PID_DATE = "pId_date"
  val COL_NAME_GATHERCATEGORY = "gatherCategory"
  val COL_NAME_GATHERTYPE = "gatherType"
  val COL_NAME_DOOR = "door"
  val COL_NAME_ADDRESS = "address"
  val COL_NAME_SEXCODE = "sexCode"
  val COL_NAME_NAME = PERSON_NAME
  val COL_NAME_IDCARDNO = IDCARDNO
  val COL_NAME_DATASOURCES = "dataSources"
  val COL_NAME_CASECLASS = "caseClass"
  val COL_NAME_CASECLASS2 = "caseClass2"
  val COL_NAME_CASECLASS3 = "caseClass3"
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
  val COL_NAME_SPELLNAME = "spellname" //姓名拼音
  val COL_NAME_PERSONTYPE = "personType"
  val COL_NAME_NATIONCODE = "nationCode"
  val COL_NAME_RECORDMARK = "recordmark"
  val COL_NAME_GATHERORGCODE = "gatherOrgCode"
  val COL_NAME_NATIVEPLACECODE = "nativeplaceCode"
  val COL_NAME_FOREIGNNAME = "foreignName"
  val COL_NAME_ASSISTREFPERSON = "assistRefPerson"
  val COL_NAME_ASSISTREFCASE = "assistRefCase"
  val COL_NAME_LOGICDB = "logicDB"
  val COL_NAME_GATHERDEPARTNAME = "gatherdepartname"
  val COL_NAME_GATHERUSERNAME = "gatherusername"
  val COL_NAME_CONTRCAPTURECODE = "contrcaptureCode"
  val COL_NAME_CERTIFICATETYPE = "certificatetype"
  val COL_NAME_CERTIFICATEID = "certificateid"
  val COL_NAME_PROCESSNO = "processNo"
  val COL_NAME_PSISNO = "psisNo"
  val COL_NAME_USEDNAME = "usedname"
  val COL_NAME_USEDSPELL = "usedspell"
  val COL_NAME_ALIASNAME = "aliasname"
  val COL_NAME_ALIASSPELL = "aliasspell"
  val COL_NAME_BIRTHCODE = "birthCode"
  val COL_NAME_BIRTHSTREET = "birthStreet"
  val COL_NAME_BIRTHDETAIL = "birthdetail"
  val COL_NAME_DOORSTREET = "doorStreet"
  val COL_NAME_DOORDETAIL = "doordetail"
  val COL_NAME_ADDRESSSTREET = "addressStreet"
  val COL_NAME_ADDRESSDETAIL = "addressdetail"
  val COL_NAME_CULTURECODE = "cultureCode"
  val COL_NAME_FAITHCODE = "faithCode"
  val COL_NAME_HAVEEMPLOYMENT = "haveemployment"
  val COL_NAME_JOBCODE = "jobCode"
  val COL_NAME_OTHERSPECIALTY = "otherspecialty"
  val COL_NAME_SPECIALIDENTITYCODE = "specialidentityCode"
  val COL_NAME_SPECIALGROUPCODE = "specialgroupCode"
  val COL_NAME_GATHERERID = "gathererId"
  val COL_NAME_FINGERREPEATNO = "fingerrepeatno"
  val COL_NAME_PERSONCATEGORY = "personCategory"
  val COL_NAME_GATHERFINGERMODE = "gatherFingerMode"
  val COL_NAME_CASENAME = "caseName"
  val COL_NAME_REASON = "reason"
  val COL_NAME_GATHERDEPARTCODE = "gatherdepartcode"
  val COL_NAME_GATHERUSERID = "gatheruserid"
  val COL_NAME_ISXJSSMZ = "isXjssmz"
  val COL_NAME_INPUTTIME = "inputtime"
  val COL_NAME_MODIFIEDTIME = "modifiedtime"
  val COL_NAME_GATHERFINGERTIME = "gatherFingerTime"
  val COL_NAME_EXTRACTDATE = "extractDate"

  //gafis6.2字段
  val GAFIS_KEYLIST_GetName = "KeyList"
  val GAFIS_QRYPARAM_GetName = "QryParam"
  val GAFIS_QRYFILTER_GetName = "QryFilter"
  val GAFIS_CANDKEYFILTER_GetName = "CandKeyFilter"
  val GAFIS_TEXTSQL_GetName = "TextSql"

  val COL_NAME6_CARDID = "CardID"
  val COL_NAME6_NAME = "Name"
  val COL_NAME6_CREATEUSERNAME = "CreateUserName"
  val COL_NAME6_BIRTHDATE = "BirthDate"
  val COL_NAME6_ADDRESSTAIL = "AddressTail"
  val COL_NAME6_CASECLASS1CODE = "CaseClass1Code"
  val COL_NAME6_PRINTERUNITNAMETAIL = "PrinterUnitNameTail"
  val COL_NAME6_SEXCODE = "SexCode"
  val COL_NAME6_RACECODE = "RaceCode"
  val COL_NAME6_FINGERID = "FingerID"
  val COL_NAME6_CASEID = "CaseID"
}
