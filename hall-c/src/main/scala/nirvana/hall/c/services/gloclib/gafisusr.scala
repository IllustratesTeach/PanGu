package nirvana.hall.c.services.gloclib

import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.gbaselib.gathrdop.GAFIS_CRITSECT
import nirvana.hall.c.services.gbaselib.gbasedef.GAFIS_UUIDStruct
import nirvana.hall.c.services.ghpcbase.ghpcdef.AFISDateTime
import nirvana.hall.c.services.gloclib.glocdef.GAFISMICSTRUCT

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-12
 */
object gafisusr {

  final val USERWORKIDLEN = 16
  final val ENCODEDPASSLEN = 16
  final val USERNAMELEN = 48
  final val USERCOMMENTLEN = 128

  class GAFIS_USERDBRIGHTSTRUCT extends AncientData
  {
    var stUUID = new GAFIS_UUIDStruct;	// uuid of the database

    var bDR_Add:Byte = _ ;
    var bDR_Del:Byte = _ ;
    var bDR_Get:Byte = _ ;
    var bDR_Select:Byte = _ ;
    var bDR_Verify:Byte = _ ;
    var bDR_Update:Byte = _ ;
    @Length(2)
    var bnRes_DR:Array[Byte] = _ ;

    var bDBA_NewTable:Byte = _ ;
    var bDBA_UpdateTable:Byte = _ ;
    var bDBA_DropTable:Byte = _ ;
    @Length(5)
    var bDBA_Res:String = _ ;
  } // GAFIS_USERDBRIGHTSTRUCT;	// size is 32 bytes

  class GAFIS_USERDATASTRUCT extends AncientData
  {
    @Length(16)
    var EncPassWord:String = _ ;
    @Length(8)
    var bnRes_0:Array[Byte] = _ ;
    var tDisabledTime = new AFISDateTime;	//!< 账号停用时的时间
  var bUserIsDisabled:Byte = _ ;
    var bSR_ADM_Administrator:Byte = _ ;		// USERRQ_ADM_xxx
  var bSR_ADM_GeneralAdmin:Byte = _ ;		//
  var bSR_ADM_ShutDownServer:Byte = _ ;
    var bSR_ADM_ShutDownMatcher:Byte = _ ;
    var bSR_ADM_ShutDownExfer:Byte = _ ;
    var bSR_ADM_ShutDownRmtSvr:Byte = _ ;
    var bSR_ADM_StartServer:Byte = _ ;
    var bSR_ADM_StartMatcher:Byte = _ ;
    var bSR_ADM_StartExfer:Byte = _ ;
    var bSR_ADM_StartRmtSvr:Byte = _ ;
    var bSR_ADM_ModifyLicense:Byte = _ ;
    var bSR_ADM_ModifyUser:Byte = _ ;
    var bSR_ADM_ModifyParameter:Byte = _ ;
    var bSR_ADM_QualityCheck:Byte = _ ;
    var bSR_ADM_Other:Byte = _ ;
    var bSR_ADM_ShutDownFrontServer:Byte = _ ;
    var bSR_ADM_ShutDownProxyServer:Byte = _ ;
    var bSR_ADM_ShutDownSvcMaster:Byte = _ ;
    var bSR_ADM_DoStatistic:Byte = _ ;	// 是否可以进行系统的统计
  @Length(12)
  var bnRes_SR:Array[Byte] = _ ;	// SR right has 32 items

    var bDR_TP_Add:Byte = _ ;
    var bDR_TP_Update:Byte = _ ;
    var bDR_TP_Del:Byte = _ ;
    var bDR_TP_Select:Byte = _ ;
    var bDR_TP_Get:Byte = _ ;

    var bDR_LP_Add:Byte = _ ;
    var bDR_LP_Update:Byte = _ ;
    var bDR_LP_Del:Byte = _ ;
    var bDR_LP_Select:Byte = _ ;
    var bDR_LP_Get:Byte = _ ;

    var bDR_QRY_Add:Byte = _ ;
    var bDR_QRY_Update:Byte = _ ;
    var bDR_QRY_Del:Byte = _ ;
    var bDR_QRY_Select:Byte = _ ;
    var bDR_QRY_Get:Byte = _ ;
    var bDR_QRY_Verify:Byte = _ ;

    var bDR_QUE_Edit:Byte = _ ;
    var bDR_QUE_Check:Byte = _ ;
    var bDR_QUE_Del:Byte = _ ;
    var bDR_QUE_Other:Byte = _ ;
    var bDR_QUE_Add:Byte = _ ;
    var bDR_QUE_TextInput:Byte = _ ;

    var bDR_DUPCARD_Add:Byte = _ ;
    var bDR_DUPCARD_Del:Byte = _ ;
    var bDR_DUPCARD_Update:Byte = _ ;

    var bDR_BREAKCASE_Add:Byte = _ ;
    var bDR_BREAKCASE_Del:Byte = _ ;
    var bDR_BREAKCASE_Update:Byte = _ ;
    var bDR_BREAKCASE_Get:Byte = _ ;
    var bDR_BREAKCASE_Retr:Byte = _ ;

    var bDR_QUE_QualCheck:Byte = _ ;

    var bDR_DUPCARD_Get:Byte = _ ;
    var bDR_DUPCARD_Select:Byte = _ ;
    @Length(15)
    var bnRes_DR:Array[Byte] = _ ;	// DR right has 48 items

    var bRR_RMT_QryToRmt:Byte = _ ;	// 可以发生远程查询（可以发生查询到远程）
  var bRR_RMT_TPToRmt:Byte = _ ;	// 远程用户可以下载捺印数据
  var bRR_RMT_LPToRmt:Byte = _ ;	// 远程用户可以下载现场数据
  var bRR_RMT_QryToLoc:Byte = _ ;	// 远程用户可以提交查询到本地
  var bRR_RMT_TPToLoc:Byte = _ ;	// 远程用户可以上报捺印指纹到本地
  var bRR_RMT_LPToLoc:Byte = _ ;	// 远程用户可以上报现场指纹到本地
  var bRR_RMT_OtherToRmt:Byte = _ ;	// 其他通向远程的操作
  var bRR_RMT_OtherToLoc:Byte = _ ;	// 其他指向本地的操作
  var bRR_RMT_TPUpdateLoc:Byte = _ ;	// 远程用户可以更新本地捺印数据
  var bRR_RMT_LPUpdateLoc:Byte = _ ;	// 远程用户可以更新本地现场数据
  var bRR_RMT_CaseToRmt:Byte = _ ;		// 远程用户可以下载案件数据
  var bRR_RMT_CaseToLoc:Byte = _ ;		// 远程用户可以增加案件
  var bRR_RMT_CaseUpdateLoc:Byte = _ ;	// 远程用户可以更新本地案件
  var bRR_RMT_PersonToRmt:Byte = _ ;	// 远程用户可以下载（获取）重卡信息
  var bRR_RMT_PersonToLoc:Byte = _ ;	// 远程用户可以增加重卡信息
  var bRR_RMT_PersonUpdateLoc:Byte = _ ;	// 远程用户可以更新本地重卡信息
    //UCHAR	bnRes_RR[6];	// RR right has 16 items

    var bDBA_NewDB:Byte = _ ;
    var bDBA_UpdateDB:Byte = _ ;
    var bDBA_DropDB:Byte = _ ;
    var bDBA_NewTable:Byte = _ ;
    var bDBA_UpdateTable:Byte = _ ;
    var bDBA_DropTable:Byte = _ ;
    @Length(10)
    var bnRes_DBA:Array[Byte] = _ ;	// DBA rights 16 items

    var bOTHER_Add:Byte = _ ;
    var bOTHER_Del:Byte = _ ;
    var bOTHER_Update:Byte = _ ;
    var bLSN_Add:Byte = _ ;
    var bLSN_Del:Byte = _ ;
    var bLSN_Update:Byte = _ ;
    var bLSN_Get:Byte = _ ;
    @Length(9)
    var bnRes_OTHER:Array[Byte] = _ ;	// 16 items

    // 远程的第二部分
    var bRR_RMT_BreakCaseAdd:Byte = _ ;	// 远程用户增加破案记录
  var bRR_RMT_BreakCaseGet:Byte = _ ;	// 远程用户取得破案记录
  var bRR_RMT_BreakCaseUpdate:Byte = _ ;	// 远程用户更新破案记录
  var bRR_RMT_TPSelect:Byte = _ ;			// 检索捺印库
  var bRR_RMT_LPSelect:Byte = _ ;			// 检索现场库
  var bRR_RMT_CaseSelect:Byte = _ ;			// 检索现场库
  var bRR_RMT_PersonSelect:Byte = _ ;		// 检索重卡库
  var bRR_RMT_BreakCaseSelect:Byte = _ ;	// 检索破案库
  var bRR_RMT_QuerySelect:Byte = _ ;		// 检索查询库
  var bRR_RMT_QueryUpdateToLoc:Byte = _ ;	// 允许远程用户更新查询库
  @Length(6)
  var bnRes_RR2:Array[Byte] = _ ;		// 一共16个权限


    @Length(14)
    var bnRes:Array[Byte] = _ ;
    var nDBRightCount:Byte = _ ;	// db right count
  var nGroupCount:Byte = _ ;	// # of groups belong to
  @Length(10)
  var stDBRight:Array[GAFIS_USERDBRIGHTSTRUCT] = _;	// specific to each db, 32 bytes. at most 10 dbs

    @Length(8 * 16)
  var szGroupBelongTo:Array[Byte]= _ ;			// at most 8 groups
  } // GAFIS_USERDATASTRUCT;	// size is 640 bytes

  // the following structure represents a user's biometric data
  // and associate parameters
  class GAFIS_USERBIOVERIFYSTRUCT extends AncientData
  {
    var nMicCount:Byte = _ ;			// we may use finger or palm to verify a user
  @Length(3)
  var bnRes:Array[Byte] = _ ;			// 7 bytes reserved
  var nDataLen:Int = _ ;		// used for inner
  var pstMic_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstMic_Data:Array[GAFISMICSTRUCT] = _ // for pstMic pointer ,struct:GAFISMICSTRUCT;	// # of mics
  @Length(4)
  var bnRes_Pointer:Array[Byte] = _ ;
  } // GAFIS_USERBIOVERIFYSTRUCT;	// size of this structure is 16 bytes

  class GAFIS_USERINFOSTRUCT extends AncientData
  {
    @Length(16)
    var szName:String = _ ;		// user name or ID
  @Length(48)
  var szFullName:String = _ ;	// full user name
  @Length(128)
  var szComment:String = _ ;	// to here is 128+64 bytes
  @Length(3 * 24)
  var szPhone:String = _ ;	// telephone number, max three items, 72 bytes
  @Length(48)
  var szMail:String = _ ;		// mail address, 48 bytes
  @Length(96)
  var szAddress:String = _ ;	// 48 hanzi
  @Length(16)
  var szPostCode:String = _ ;	// post code
  var bIsGroup:Byte = _ ;
    var nLoginWeekMask:Byte = _ ;		// using seven bit if that bit set then does not allow login
  var nFlag:Byte = _ ;				// USERINFO_FLAG_XXX
  @Length(1)
  var bnRes0:Array[Byte] = _ ;
    var nLoginHourMask:Int = _ ;	// just using 3 bytes. if that bit set then does not allow login.
  //UCHAR	bnRes1[8];
  var tCreateTime = new AFISDateTime;
    // in normal wild template, dot(.) means any char, but in ip address dot
    // will usually appear, so we use $ to represent any char.
    @Length(16)
    var szLoginIPMask:String = _ ;	// login ip address must match this such 220.165.*
  //UCHAR	bnRes[56];		// reserved
  @Length(48)
  var szOrganization:String = _ ;	//!< 集团ID
  var tUpdateTime = new AFISDateTime;
  } // GAFIS_USERINFOSTRUCT;	// size is 512 bytes

  // GAFIS_USERINFOSTRUCT::nFlag
  final val USERINFO_FLAG_DISABLELOCALIP = 0x1	// disable 127.0.0.1 login.

  class GAFIS_USERMISCSTRUCT extends AncientData
  {
    var bVerifyMode:Byte = _ ;		// user verify mode:, USERVERIFYMODE_XX
  var nMaxQueryPriority:Byte = _ ;
    var nQueryTrustType:Byte = _ ;	// control the query is directly into queryque or wait censor
  var nDataTrustType:Byte = _ ;		// control the query is directly into database or wait check
  var nRmtFlag:Byte = _ ;			// USERMIC_RMTFLAG_XX
  @Length(11+16)
  var bnRes_0:Array[Byte] = _ ;	// reserved,
  } // GAFIS_USERMISCSTRUCT;	// size is 32 bytes

  // GAFIS_USERMISCSTRUCT
  // 下面这个被注释掉的标志与0X2和0X4冲突。所以暂时不使用。
  //#define USERMIC_RMTFLAG_ISRMTUSER			0x1	// 该用户是一个远程用户（就是)，把用户分为两类（远程和本地）。
  final val USERMIC_RMTFLAG_DISABLELOGBINBYLOC = 0x2 // 禁止不通过通讯服务器登录。（必须通过通讯服务器登录）
  final val USERMIC_RMTFLAG_DISABLELOGINBYTX = 0x4	// 禁止通过通讯服务器登录。（必须不通过通讯服务器登录）

  // user verify mode:
  // user has three identities : password, username, finger
  // we can use u+f, u+p, u+p+f, f only
  class GAFIS_USERSTRUCT extends AncientData
  {
    var stInfo = new GAFIS_USERINFOSTRUCT;	// 512 bytes
  /* align at 8 bytes boundary */
  var stMiscData = new GAFIS_USERMISCSTRUCT;	// misc data, 32 bytes
  var stRightData = new GAFIS_USERDATASTRUCT;	// 640 bytes .user right data, the data will be invisible to user
  var stBioData = new GAFIS_USERBIOVERIFYSTRUCT;		// 16 bytes
  var nItemFlag:Byte = _ ;	// USER_ITEMFLAG_XXX, used on updating data
  @Length(79)
  var bnRes:Array[Byte] = _ ;				// reserved
  } // GAFIS_USERSTRUCT;	/* size of this structure is 1024 +256 byte */

  final val USER_ITEMFLAG_INFO = 0x1
  final val USER_ITEMFLAG_MISC = 0x2
  final val USER_ITEMFLAG_RIGHT = 0x4
  final val USER_ITEMFLAG_BIODATA = 0x8

  // in the following u -- represents user, p -- password, f -- finger
  final val USERVERIFYMODE_UP = 0x0
  final val USERVERIFYMODE_UPF = 0x1
  final val USERVERIFYMODE_UF = 0x2
  final val USERVERIFYMODE_UPORF = 0x3
  final val USERVERIFYMODE_FONLY = 0x4	// only finger


  // 1 to 32
  final val USERRQ_ADM_BASE = 0
  final val USERRQ_ADM_ALL = (USERRQ_ADM_BASE+1)
  final val USERRQ_ADM_GENALL = (USERRQ_ADM_BASE+2)
  final val USERRQ_ADM_SHUTDOWNSERVER = (USERRQ_ADM_BASE+3)
  final val USERRQ_ADM_SHUTDOWNMATCHER = (USERRQ_ADM_BASE+4)
  final val USERRQ_ADM_SHUTDOWNEXFER = (USERRQ_ADM_BASE+5)
  final val USERRQ_ADM_SHUTDOWNRMTSERVER = (USERRQ_ADM_BASE+6)
  final val USERRQ_ADM_STARTSERVER = (USERRQ_ADM_BASE+7)
  final val USERRQ_ADM_STARTMATCHER = (USERRQ_ADM_BASE+8)
  final val USERRQ_ADM_STARTEXFER = (USERRQ_ADM_BASE+9)
  final val USERRQ_ADM_STARTRMTSERVER = (USERRQ_ADM_BASE+10)
  final val USERRQ_ADM_MODIFYLICENSE = (USERRQ_ADM_BASE+11)
  final val USERRQ_ADM_MODIFYUSER = (USERRQ_ADM_BASE+12)
  final val USERRQ_ADM_MODIFYPARAMETER = (USERRQ_ADM_BASE+13)
  final val USERRQ_ADM_QUALITYCHECK = (USERRQ_ADM_BASE+14)
  final val USERRQ_ADM_OTHER = (USERRQ_ADM_BASE+15)
  final val USERRQ_ADM_SHUTDOWNFRONTSVR = (USERRQ_ADM_BASE+16)
  final val USERRQ_ADM_SHUTDOWNPXYSERVER = (USERRQ_ADM_BASE+17)
  final val USERRQ_ADM_SHUTDOWNSVCMASTER = (USERRQ_ADM_BASE+18)
  final val USERRQ_ADM_DOSTATISTICS = (USERRQ_ADM_BASE+19)

  // 33-80
  final val USERRQ_TP_BASE = 32
  final val USERRQ_TP_ADD = (USERRQ_TP_BASE+1)
  final val USERRQ_TP_UPDATE = (USERRQ_TP_BASE+2)
  final val USERRQ_TP_DEL = (USERRQ_TP_BASE+3)
  final val USERRQ_TP_SELECT = (USERRQ_TP_BASE+4)
  final val USERRQ_TP_GET = (USERRQ_TP_BASE+5)

  final val USERRQ_LP_BASE = 42
  final val USERRQ_LP_ADD = (USERRQ_LP_BASE+1)
  final val USERRQ_LP_UPDATE = (USERRQ_LP_BASE+2)
  final val USERRQ_LP_DEL = (USERRQ_LP_BASE+3)
  final val USERRQ_LP_SELECT = (USERRQ_LP_BASE+4)
  final val USERRQ_LP_GET = (USERRQ_LP_BASE+5)

  final val USERRQ_QRY_BASE = 52
  final val USERRQ_QRY_ADD = (USERRQ_QRY_BASE+1)
  final val USERRQ_QRY_UPDATE = (USERRQ_QRY_BASE+2)
  final val USERRQ_QRY_DEL = (USERRQ_QRY_BASE+3)
  final val USERRQ_QRY_SELECT = (USERRQ_QRY_BASE+4)
  final val USERRQ_QRY_VERIFY = (USERRQ_QRY_BASE+5)
  final val USERRQ_QRY_GET = (USERRQ_QRY_BASE+6)
  final val USERRQ_QRY_RECHECK = (USERRQ_QRY_BASE+7)

  final val USERRQ_QUE_BASE = 62
  final val USERRQ_QUE_EDIT = (USERRQ_QUE_BASE+1)
  final val USERRQ_QUE_CHECK = (USERRQ_QUE_BASE+2)
  final val USERRQ_QUE_DEL = (USERRQ_QUE_BASE+3)
  final val USERRQ_QUE_OTHER = (USERRQ_QUE_BASE+4)
  final val USERRQ_QUE_ADD = (USERRQ_QUE_BASE+5)
  final val USERRQ_QUE_TEXTINPUT = (USERRQ_QUE_BASE+6)
  final val USERRQ_QUE_QUALCHECK = (USERRQ_QUE_BASE+7)

  final val USERRQ_DUPCARD_BASE = 72
  final val USERRQ_DUPCARD_ADD = (USERRQ_DUPCARD_BASE+1)
  final val USERRQ_DUPCARD_DEL = (USERRQ_DUPCARD_BASE+2)
  final val USERRQ_DUPCARD_UPDATE = (USERRQ_DUPCARD_BASE+3)
  final val USERRQ_DUPCARD_GET = (USERRQ_DUPCARD_BASE+4)
  final val USERRQ_DUPCARD_SELECT = (USERRQ_DUPCARD_BASE+5)

  final val USERRQ_BREAKCASE_BASE = 82
  final val USERRQ_BREAKCASE_ADD = (USERRQ_BREAKCASE_BASE+1)
  final val USERRQ_BREAKCASE_DEL = (USERRQ_BREAKCASE_BASE+2)
  final val USERRQ_BREAKCASE_UPDATE = (USERRQ_BREAKCASE_BASE+3)
  final val USERRQ_BREAKCASE_GET = (USERRQ_BREAKCASE_BASE+4)
  final val USERRQ_BREAKCASE_RETR = (USERRQ_BREAKCASE_BASE+5)

  // 92-102
  final val USERRQ_RMT_BASE = 92
  final val USERRQ_RMT_QRYTORMT = (USERRQ_RMT_BASE+1)
  final val USERRQ_RMT_TPTORMT = (USERRQ_RMT_BASE+2)
  final val USERRQ_RMT_LPTORMT = (USERRQ_RMT_BASE+3)
  final val USERRQ_RMT_OTHERTORMT = (USERRQ_RMT_BASE+4)
  final val USERRQ_RMT_QRYTOLOC = (USERRQ_RMT_BASE+5)
  final val USERRQ_RMT_TPTOLOC = (USERRQ_RMT_BASE+6)
  final val USERRQ_RMT_LPTOLOC = (USERRQ_RMT_BASE+7)
  final val USERRQ_RMT_OTHERTOLOC = (USERRQ_RMT_BASE+8)
  final val USERRQ_RMT_TPUPDATELOC = (USERRQ_RMT_BASE+9)
  final val USERRQ_RMT_LPUPDATELOC = (USERRQ_RMT_BASE+10)

  final val USERRQ_RMT_CASETORMT = (USERRQ_RMT_BASE+11)
  final val USERRQ_RMT_CASETOLOC = (USERRQ_RMT_BASE+12)
  final val USERRQ_RMT_CASEUPDATELOC = (USERRQ_RMT_BASE+13)
  final val USERRQ_RMT_PERSONTORMT = (USERRQ_RMT_BASE+14)
  final val USERRQ_RMT_PERSONTOLOC = (USERRQ_RMT_BASE+15)
  final val USERRQ_RMT_PERSONUPDATELOC = (USERRQ_RMT_BASE+16)


  final val USERRQ_DBA_BASE = 110
  final val USERRQ_DBA_NEWDB = (USERRQ_DBA_BASE+1)
  final val USERRQ_DBA_UPDATEDB = (USERRQ_DBA_BASE+2)
  final val USERRQ_DBA_DROPDB = (USERRQ_DBA_BASE+3)
  final val USERRQ_DBA_NEWTABLE = (USERRQ_DBA_BASE+4)
  final val USERRQ_DBA_UPDATETABLE = (USERRQ_DBA_BASE+5)
  final val USERRQ_DBA_DROPTABLE = (USERRQ_DBA_BASE+6)

  // 120-140
  final val USERRQ_OTHER_BASE = 120
  final val USERRQ_OTHER_ADD = (USERRQ_OTHER_BASE+1)
  final val USERRQ_OTHER_DEL = (USERRQ_OTHER_BASE+2)
  final val USERRQ_OTHER_UPDATE = (USERRQ_OTHER_BASE+3)
  final val USERRQ_OTHER_LSN_ADD = (USERRQ_OTHER_BASE+4)
  final val USERRQ_OTHER_LSN_DEL = (USERRQ_OTHER_BASE+5)
  final val USERRQ_OTHER_LSN_GET = (USERRQ_OTHER_BASE+6)
  final val USERRQ_OTHER_LSN_UPDATE = (USERRQ_OTHER_BASE+7)

  // 用户权限的第二部分
  final val USERRQ_RMT_BASE2 = 141
  final val USERRQ_RMT_BREAKCASEADD = (USERRQ_RMT_BASE2+0)
  final val USERRQ_RMT_BREAKCASEGET = (USERRQ_RMT_BASE2+1)
  final val USERRQ_RMT_BREAKCASEUPDATE = (USERRQ_RMT_BASE2+2)
  final val USERRQ_RMT_TPSELECT = (USERRQ_RMT_BASE2+4)
  final val USERRQ_RMT_LPSELECT = (USERRQ_RMT_BASE2+5)
  final val USERRQ_RMT_CASESELECT = (USERRQ_RMT_BASE2+6)
  final val USERRQ_RMT_PERSONSELECT = (USERRQ_RMT_BASE2+7)
  final val USERRQ_RMT_BREAKCASESELECT = (USERRQ_RMT_BASE2+8)
  final val USERRQ_RMT_QUERYSELECT = (USERRQ_RMT_BASE2+9)
  final val USERRQ_RMT_QRYUPDATETOLOC = (USERRQ_RMT_BASE2+10)

  //
  class GAFIS_USERACCOUNTINMEM extends AncientData
  {
    var bInited:Int = _;
    var bCritInited:Int = _;
    var nUserCount:Int = _;	// # of user accounts including groups
  var nUserBufCount:Int = _;	// # of user buffer's
  var mCrit = new GAFIS_CRITSECT;	// critical section
  var pUser_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pUser_Data:Array[GAFIS_USERSTRUCT] = _ // for pUser pointer ,struct:GAFIS_USERSTRUCT;
  @Length(4)
  var bnRes_Pointer:Array[Byte] = _ ;
  } // GAFIS_USERACCOUNTINMEM;

  // because numina in it self has no access control, so all control
  // is set on AFIS level. We even implement server mode license check
  // and pc mode license check on AFIS level. the SERVER and CLIENT
  // level are only stubs. because many functions may need user license
  // but it's tedious to place every routines first parameter to be
  // pointer to the following structure, so we place the user data to each
  // thread data area.
  class GAFIS_USEROBJECT extends AncientData
  {
    @Length(USERWORKIDLEN)
    var UserID:String = _ ;
  } // GAFIS_USEROBJECT;	// size is 16 bytes
  /* function protypes */

  final val USER_ITEMINDEX_INFO = 0x1
  final val USER_ITEMINDEX_RIGHT = 0x2
  final val USER_ITEMINDEX_BIODATA = 0x3







}
