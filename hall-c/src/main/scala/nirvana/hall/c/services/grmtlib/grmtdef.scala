package nirvana.hall.c.services.grmtlib

import nirvana.hall.c.annotations.Length
import nirvana.hall.c.services.AncientData

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-04-26
  */
object grmtdef {


  final val SIZE_1MB = 1048576	//1 MB

  //#define UNREFERENCEPARAMETER(a)	(a)
//  final val UNREFERENCEPARAMETER(a) = UNREFPARAMETER(a)

  final val RMTUNITCODELEN = 16
  final val RMTAUTOREPORTUSER = "AutoReport"
  final val RMTAUTODOWNLOADUSER = "AutoDownload"
  final val RMTSVRITEM_PREFETCHCOUNT = 5000
  final val RMTSVRITEM_REFRESHTIMES = 900		//15 minutes
  final val RMTSVRITEM_MAXERRTIMES = 5
  final val _MAX_DESTSVR_COUNT_ = 64	//max dest remote server count
//  final val RMTUSER_UNITCODE(p) = (p).szPhone[2]

  //remote operate type
  final val RMTOPT_OP_DEFAULT = 0	//default operate
  final val RMTOPT_OP_QUEUEUPLOAD = 1	//upload by queue
  final val RMTOPT_OP_QUEUEDOWNLOAD = 2	//download by queue
  final val RMTOPT_OP_TXTQRYDOWNLOAD = 3	//download by rmttxtquery
  final val RMTOPT_OP_QRYCANDDOWNLOAD = 4	//download by query cand
  final val RMTOPT_OP_QRYUPLOAD = 5	//upload when send query
  final val RMTOPT_OP_QRYDOWNLOAD = 6	//DOWNLOAD WHEN DOWNLOAD QUERY
  final val RMTOPT_OP_SENDQRYWHENUPLOAD = 7	//auto send query when upload finger

  //remote destination
  final val RMTOPT_DEST_DEFAULT = 0
  final val RMTOPT_DEST_ENDPOINT = 1	//end point
  final val RMTOPT_DEST_PROXY = 2	//proxy point
  final val RMTOPT_DEST_MIXQUERY = 3	//transmit query and remote query



  //remote server udp command, processed by udp server
  class RMTSVRUDPCMDSTRUCT extends AncientData
  {
    var szMagic:Long = _ ;		//"$RMTCMD$"
  var nCommand:Int = _ ;	//RMTLIB_CMD_xxx, grmtcode.h
  @Length(16)
  var bnData:Array[Byte] = _ ;
    @Length(4)
    var bnRes:Array[Byte] = _ ;
  } //RMTSVRUDPCMDSTRUCT; //size is 32

  final val RMTFUNC_GETOPT_DEFAULT = 0
  final val RMTFUNC_GETOPT_BYSID = 1
  final val RMTFUNC_GETOPT_BYUSERNAMEANDUNITCODE = 2




}
