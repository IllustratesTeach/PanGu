package nirvana.hall.v62.internal.c.grmtlib

import nirvana.hall.v62.annotations.Length
import nirvana.hall.v62.services.AncientData
import nirvana.hall.v62.internal.c._

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-24
 */
object gserver {

  //dial server struct
  class RMTDIALSVRSTRUCT extends AncientData
  {
    @Length(32)
    var szTelephoneNum:String = _ ;		//telephone number
  @Length(16)
  var szEnOSUserName:String = _ ;		//dial server user name
  @Length(16)
  var szEnOSPassword:String = _ ;		//dial server password,XOR
  @Length(32)
  var szDomainName:String = _ ;		//dial server domain name
  @Length(32)
  var bnRes:Array[Byte] = _ ;
  } //RMTDIALSVRSTRUCT;	//size is 128, dial server struct

  class RMTSVRADVANCEDOPTSTRUCT extends AncientData
  {
    var nLanMaxFailureCnt:Byte = _ ;				//try max times if login server failed
  var nDialMaxFailureCnt:Byte = _ ;				//try max times if dial server failed
  var nLanWaitTimeIfConnectFail:Short = _ ;	//wait time if connect server failed
  var nDialWaitTimeIfConnectFail:Short = _ ;	//wait time if dial server failed
  var nLanWaitTimeForUnfinished:Short = _ ;	//wait time for next check if query not matched
  var nDialWaitTimeForUnfinished:Short = _ ;	//wait time for next check if query not matched
  var nLanWaitTimeIfSendQryFail:Short = _ ;	//wait time for next send qry if send query failed
  var nDialWaitTimeIfSendQryFail:Short = _ ;	//wait time for next send qry if send query failed
  var nDialDurationTime:Short = _ ;			//dial duration time in two dials
  var nDialInterval:Int = _ ;				//dial standing on time
  var nLastDialTime:Int = _ ;				//last dial time
  var nLastOpTime:Int = _ ;					//last op time, login server, 28
    @Length(36 + 64)
  var bnRes:Array[Byte] = _
  } //RMTSVRADVANCEDOPTSTRUCT;	//size is 128

  //RMTSERVERSTRUCT::nFlag's value
  final val RMTSVR_FLAG_DIRECTSVR = 0x01		//direct server, PROXY server
  final val RMTSVR_FLAG_NEEDDIAL = 0x02		//need dial server
  final val RMTSVR_FLAG_ISIPV6 = 0x04		//ip address is v6
  final val RMTSVR_FLAG_ISNETIP = 0x08		//include 4 ip(int)

  //RMTSERVERSTRUCT::nLevel's value
  final val RMTSVR_LEVEL_DEFAULT = 0x00
  final val RMTSVR_LEVEL_DIRECTUPPER = 0x01
  final val RMTSVR_LEVEL_NORMALUPPER = 0x02
  final val RMTSVR_LEVEL_LOWER = 0x03
  final val RMTSVR_LEVEL_SAMELEVEL = 0x04


  //remote server struct
  class RMTSERVERSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;				// size of this structure
  @Length(16)
  var szUnitCode:String = _ ;			// key, index, special the only server
  @Length(64)
  var szServerName:String = _ ;		// server name
  @Length(16)
  var szServerIP:String = _ ;			// server IP,"192.168.0.20"
  @Length(16)
  var szEnUserName:String = _ ;		// afis user id, XOR
  @Length(16)
  var szEnUserPass:String = _ ;		// afis user password,XOR
  @Length(16)
  var szServerUUID:String = _ ;		// server uuid

    var nServerPort:Int = _ ;			// server port,default is TXPORTDEF_SERVER
  var nServerID:Short = _ ;			// server id
  var nGroupID:Short = _ ;			// group id
  var tCreateTime:Int = _ ;			// create time,get by gafis_time
  var tLastOpTime:Int = _ ;			// last op time, get by gafis_time

    var nFlag:Byte = _ ;					// RMTSVR_FLAG_XXX, Flags at this server
  var nFlagEx:Byte = _ ;				// RMTSVR_FLAGEX_XXX, not used,to this is 128
  var nLevel:Byte = _ ;					// RMTSVR_LEVEL_XXX, server level
  @Length(25)
  var bnRes0:Array[Byte] = _ ;
    @Length(32)
    var szComment:String = _ ;			// server comment
  @Length(32)
  var bnResData:Array[Byte] = _ ;			// for extend, to here is 256

    var stAdvancedOpt = new RMTSVRADVANCEDOPTSTRUCT;	//size is 128
  var stDialSvr = new RMTDIALSVRSTRUCT;		//size is 128
  } //RMTSERVERSTRUCT;	//size is 512


  //RMTQRYSVRSTRUCT::nState's Value
  final val RMTQRYSVR_STATE_WAITSENDQRY = 0	// wait for send query
  final val RMTQRYSVR_STATE_WAITCANDLIST = 1	// wait for cand list
  final val RMTQRYSVR_STATE_WAITCANDDATA = 2	// wait for cand data
  final val RMTQRYSVR_STATE_FINISHED = 3	// finished
  final val RMTQRYSVR_STATE_ERROR = 4	// error
  final val RMTQRYSVR_STATE_WAITCHECK = 5	// wait check
  final val RMTQRYSVR_STATE_WAITCHECKRESULT = 6	// wait check result
  final val RMTQRYSVR_STATE_WAITVERIFY = 7	// wait verify
  final val RMTQRYSVR_STATE_WAITVERIFYRESULT = 8	// wait verify result
  final val RMTQRYSVR_STATE_WAITFEEDBACK = 9	// wait feedback
  final val RMTQRYSVR_STATE_FINISHFEEDBACK = 10	// finished feedback

  //  [8/17/2006]
  final val RMTQRYSVR_FLAG_USECHECKUNITCODE = 0x01	//check unitcode is valid
  final val RMTQRYSVR_FLAG_USEVERIFYUNITCODE = 0x02	//verify unitcode is valid
  final val RMTQRYSVR_FLAG_USECHECKSID = 0x04	//check sid is  valid
  final val RMTQRYSVR_FLAG_USEVERIFYSID = 0x08	//verify sid is valid

  //this struct used to GAQRYSTRUCT, pstSvrList
  class RMTQRYSVRSTRUCT extends AncientData
  {
    @Length(8)
    var szMagic:String = "$$RMTQRY" ;				//must is "$$RMTQRY"
    @Length(8 - SID_SIZE)
  var bnSID:Array[Byte] = _
    @Length(SID_SIZE)
    var nRmtQueryID:String = _ ;	//query id on upper server
  @Length(32)
  var szRmtQueryKey:String = _ ;		//query key, used for heterogeneity database query

    @Length(16)
    var szLocUnitCode:String = _ ;		//local server unit code
  @Length(16)
  var szProxyUnitCode:String = _ ;	//proxy server, may is rmtunitcode
  @Length(16)
  var szRmtUnitCode:String = _ ;		//remote server unit code
  //to here is 96
  var nAfisErrNo:Int = _ ;			//afis error number
  var nTTL:Byte = _ ;					//ttl
  var nState:Byte = _ ;					//RMTQRYSVR_STATE_XXX
  var nErrTimes:Byte = _ ;				//error occur times
  var nHitPossibility:Byte = _ ;		//query hit pos at proxy server

    // added by xxf for query may be verified by other unitcode [6/17/2005]
    @Length(8-SID_SIZE)
    var bnCheckSID:Array[Byte] = _ ;
    @Length(SID_SIZE)
    var nCheckSID:String = _ ;		//query id on check server
  @Length(8-SID_SIZE)
  var bnVerifySID:Array[Byte] = _ ;
    @Length(SID_SIZE)
    var nVerifySID:String = _ ;	//query id on recheck server
  @Length(16)
  var szCheckUnitCode:String = _ ;	//check unitcode
  @Length(16)
  var szVerifyUnitCode:String = _ ;	//verify unitcode
  //  [8/17/2006]
  var nFlag:Byte = _ ;				//RMTQRYSVR_FLAG_XXX
  //  [11/8/2006]
  var nFeedbackMode:Byte = _ ;		//feedback check or verify result to szRmtUnitCode
    //RMTQRYCTRL_FEEDBACKMODE_XXX

    @Length(102)
    var bnRes1:Array[Byte] = _ ;		//reserved
  final val szMATCHUNITCODE = szRmtUnitCode
  } //RMTQRYSVRSTRUCT;	//size is 256, used for GAQRYSTRUCT


  final val RMTSVRGETOPT_USEDEFIP = 0x01	//use default ip





}
