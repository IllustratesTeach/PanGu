package nirvana.hall.c.services.gloclib

import java.net.Socket

import nirvana.hall.c.annotations.Length
import nirvana.hall.c.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-06
 */
object glocndef {

  final val GNRHO_MAGICSTR	= "G@xucg$"

  class GNETREQUESTHEADOBJECT extends AncientData
  {
    var cbSize:Int = 192 ;			// size of this structure
  var nMajorVer:Short = 6 ;		// major version, must be 6
  var nMinorVer:Short = 1 ;		// minor version, must be 1
    @Length(8)
    var szMagicStr:String = GNRHO_MAGICSTR ;		// must be G@xucg$
  @Length(16)
  var szUserName:String = _ ;		// user name
  @Length(16)
  var szUserPass:String = _ ;		// user password
  var tLoginTime:Int = _ ;		// login time, server time
  var nOpClass:Short = _ ;		// operation class, 2 bytes int
  var nOpCode:Short = _ ;			// operation code
  var nLoginID:Int = _ ;		// login id
  var nSeqNo:Int = _ ;			// sequence number
  @Length(16)
  var nIP:String = _ ;			// ip address
  var bIsRmtUser:Byte = _ ;			// is remote user
  var bIsIPV6:Byte = _ ;			// is ip v6
  @Length(2)
  var bnRes:Array[Byte] = _ ;			// 4 bytes reserved
  var nDataLen:Int = _ ;		// data length followed
  var nOption:Int = _ ;			// noption
  var nDBID:Short = _ ;			// dbid
  var nTableID:Short = _ ;		// tableid
  var nRetVal:Long = _ ;			// client return value
  @Length(24)
  var bnRes2:Array[Byte] = _ ;			// 28 bytes reserved
  @Length(64)
  var bnData:Array[Byte] = _ ;			// send 64 bytes data as ancillary data
  } // GNETREQUESTHEADOBJECT;	// size of this structure is 192 bytes

  class GNETANSWERHEADOBJECT extends AncientData
  {
    var cbSize:Int = 96 ;			// size of this structure
  var nMajorVer:Short = 6 ;		// major version, must be 6
  var nMinorVer:Short = 0 ;		// minor version, must be 0
    @Length(8)
    var szMagicStr:String = GNRHO_MAGICSTR ;		// must be G@xucg$
  var nDataLen:Int = _ ;		// data length followed
  var nReturnValue:Int= _ ;	// return values
    @Length(4)
    var nReturnValueRes:Array[Byte]= _ ;	// return values
  @Length(4)
  var bnRes:Array[Byte] = _ ;			// 8 bytes reserved
  @Length(64)
  var bnData:Array[Byte] = _ ;			// send 64 bytes data
  } // GNETANSWERHEADOBJECT;	// size of this structure is 96 bytes

  class GAPPCONNECTIONSTRUCT extends AncientData
  {
    var nSockfd:Socket = _;
    //SOCKET nSockfd;  // SOCKET handle
    //#ifndef WIN64
    @Length(4)
    var bnRes:Array[Byte] = _ ;
    //#endif
    // speed data are used only sending data
    var nSendPercent:Int = _; //// how many percent has been sent[0, 100]
    var nTimeUsed:Int = _ ; // time used, in CLOCKS_PER_SECOND
  } //GAPPCONNECTIONSTRUCT;


}
