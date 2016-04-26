package nirvana.hall.c.services.grmtlib

import nirvana.hall.c.annotations.Length
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.SID_SIZE
import nirvana.hall.c.services.gloclib.gaqryque.GAKEYRANGESTRUCT

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-04-26
  */
class gautoqry {
  //used to RMTCLIENTAUTOQRYCONDSTRUCT::nFlag
  final val RMTAUTOQRYCOND_FLAG_KEYRANGE = 0x01
  final val RMTAUTOQRYCOND_FLAG_MAXCANDNUM = 0x02
  final val RMTAUTOQRYCOND_FLAG_PRIORIRY = 0x04

  //condtion of auto send query when report data
  class RMTCLIENTAUTOQRYCONDSTRUCT extends AncientData
  {
    var nFlag:Byte = _ ;		//RMTAUTOQRYCOND_FLAG_XXX, use which parameter
  var nFlagEx:Byte = _ ;	//not used
  var nPriority:Byte = _ ;	//query priority
  var bCanRmtCheck:Byte = _ ;	// if set this flag, this query can be remote download check [5/22/2007]
  var nMaxCandidateNum:Int = _ ;	//max candidate number
  @Length(2)
  var stKeyRange:Array[GAKEYRANGESTRUCT] = _;		// 128 bytes, to here is 128 + 8 bytes
  @Length(128-8)
  var bnRes:Array[Byte] = _ ;	//reserved, not used
  } //RMTCLIENTAUTOQRYCONDSTRUCT;	//size is 256

  //send query flag
  final val RMTAUTOQRY_SENDFLAG_NOTSEND = 0	//NOT SEND
  final val RMTAUTOQRY_SENDFLAG_SEND = 1	//SEND QUERY
  final val RMTAUTOQRY_SENDFLAG_CLIENT = 2	//decide by client
  final val RMTAUTOQRY_SENDFLAG_DIRSVR = 3	// 向直接上级服务器发送查询

  // 上报到服务器后向服务器的直接上级发送查询参数
  class RmtSvrAutoQry_SendUpperSvrParam extends AncientData
  {
    var bSendTT:Byte = _ ;
    var bSendTL:Byte = _ ;
    var bSendLT:Byte = _ ;
    var bSendLL:Byte = _ ;
    @Length(28)
    var nReserved:String = _ ;
  } //RMTSVRAUTOQRY_SENDUPPERSVRPARAM;


  //check query flag
  final val RMTAUTOQRY_CHECKFLAG_NOTCHECK = 0	//NOT CHECK
  final val RMTAUTOQRY_CHECKFLAG_CHECK = 1	//CHECK QUERY
  final val RMTAUTOQRY_CHECKFLAG_CLIENT = 2	//decided by client
  final val RMTAUTOQRY_CHECKFLAG_FORBIDCLIENT = 3	//forbid client check

  //options of client auto send query
  class RMTCLIENTAUTOQRYOPTSTRUCT extends AncientData
  {
    @Length(8-SID_SIZE)
    var bnSID:Array[Byte] = _ ;
    @Length(SID_SIZE)
    var nQueryID:String = _ ;	//query id
  var nQueryType:Byte = _ ;			//query type
  var nSendFlag:Byte = _ ;		//RMTAUTOQRY_SENDFLAG_XXX
  var nCheckFlag:Byte = _ ;		//RMTAUTOQRY_CHECKFLAG_XXX
  var nQueryStatus:Byte = _ ;	//query status
  @Length(52)
  var bnRes0:Array[Byte] = _ ;		//reserved, not used
  } //RMTCLIENTAUTOQRYOPTSTRUCT;	//size is 64

  //client auto send query struct
  class RMTCLIENTAUTOQRYSTRUCT extends AncientData
  {
    var stCond = new RMTCLIENTAUTOQRYCONDSTRUCT;	//size is 256, auto send query condition
  var stOpt = new RMTCLIENTAUTOQRYOPTSTRUCT;	//size is 64, auto send query options
  @Length(256-64)
  var bnRes:Array[Byte] = _ ;	//reserved, not used
  } //RMTCLIENTAUTOQRYSTRUCT;	//size is 512

  //server auto send query struct

  // 使用RMTSVRAUTOQRYSTRUCT结构中的bnResData0成员，增加查询的优先级设置
  class RMTSVRAUTOQRY_ResData0 extends AncientData
  {
    var nTTPriority:Byte = _ ;
    var nTLPriority:Byte = _ ;
    var nLTPriority:Byte = _ ;
    var nLLPriority:Byte = _ ;
    @Length(64 - 4)
    var nResData:String = _ ;
  } //RMTSVRAUTOQRY_RESDATA0;	// size of is 64 bytes

  final val RMTSVRAUTOQRY_FLAG_USERDATA0 = 0x1		// bnResData0保存了RMTSVRAUTOQRY_RESDATA0结构的内容

  class RMTSVRAUTOQRYSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;			//size of this struct

    @Length(8-SID_SIZE)
    var bnSID:Array[Byte] = _ ;
    @Length(SID_SIZE)
    var nID:String = _ ;		//sid

    @Length(16)
    var szUserName:String = _ ;		//user name
  @Length(16)
  var szUnitCode:String = _ ;		//unit code

    var nSendTT:Byte = _ ;	//RMTAUTOQRY_SENDFLAG_XXX
  var nSendTL:Byte = _ ;
    var nSendLT:Byte = _ ;
    var nSendLL:Byte = _ ;

    var nCheckTT:Byte = _ ;	//RMTAUTOQRY_CHECKFLAG_XXX
  var nCheckTL:Byte = _ ;
    var nCheckLT:Byte = _ ;
    var nCheckLL:Byte = _ ;

    var nFlag:Byte = _ ;		//not used
  var nFlagEx:Byte = _ ;	//not used
  // is group or belong to which group [1/15/2007]
  var bIsGroup:Byte = _ ;		//is group ?
  var nGroupID:Int = _ ;	//group id
  @Length(5)
  var bnRes0:Array[Byte] = _ ;		//to here is 64

    @Length(64)
    var bnResData0:Array[Byte] = _ ;		//for extend
  @Length(128)
  var bnResData1:Array[Byte] = _ ;	//for extend
  } //RMTSVRAUTOQRYSTRUCT;	//size is 256




}
