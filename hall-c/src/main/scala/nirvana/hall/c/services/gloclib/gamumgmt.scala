package nirvana.hall.c.services.gloclib

import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.gbaselib.gathrdop.GBASE_CRIT
import nirvana.hall.c.services.gbaselib.gbasedef.GAFIS_UUIDStruct
import nirvana.hall.c.services.gbaselib.paramadm.GBASE_PARAM_MEMITEM

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-12
 */
object gamumgmt {

  // ip address management.
  // ip address. if the server is dbsvr, then client(mmu) got address
  // like 229.119.xxx.000. xxx is between 2 and 254(so one db server
  // can attach at most 254 mmu). if the server is mmu and the client
  // is gmu then got specific address, it's 229.119.xxx.3, if the server
  // is mmu and the client is smu then got address like 229.119.xxx.lmn
  // and lmn is larger then 1. and it knews that 229.119.xxx.1 is the
  // ip address for smu to get data from mmu.

  // dbsvr-mmu multicast to get data.   ip is 229.119.1.1
  // mmu-smu or mmu-gmu multicast address  : ip is 229.119.xxx.1, where xxx is in [2, 254]
  // smu-gmu multicast address  : ip is 229.119.xxx.lmn, where lmn>2.
  // role : recv ip                  /          sending ip
  // dbsvr: n/a                                 229.119.1.1
  // mmu  : 229.119.1.1                         229.119.xxx.1
  // smu  : 229.119.xxx.1                       229.119.xxx.lmn(lmn>2)
  // gmu  : 229.119.xxx.1 or 229.119.xxx.lmn    n/a
  // ip got from server.
  // mmu    : 229.119.xxx
  // smu    : 229.119.xxx.lmn ( lmn>2)
  // gmu    : 229.119.xxx.1 or 229.119.xxx.lmn
  // 229.119.xxx.2 is reserved ip.

  // the following structure will be used by dbsvr, and mu will use different
  // structure.
  class GAFIS_MATCHSVRINFO extends AncientData
  {
    var stUUID = new GAFIS_UUIDStruct;	// uuid of the match server.
  @Length(32)
  var szName:String = _ ;		// name of the match server.
  var nIP:Int = _ ;			// ip address of the server(net work byte order).
  var nMcastIPHead:Int = _ ;	// only 3 bytes used.
  var nID:Int = _ ;				// id of the mu
  var nLastAccessTime:Int = _ ;	// last access time. if access time exceed 30 seconds
  // we will view this matchserver as invalid.
  var nRegisterTime:Int = _ ;	// register time.
  @Length(60)
  var bnRes:Array[Byte] = _ ;
  } // GAFIS_MATCHSVRINFO;	// size of this structure is 128 bytes long.

  // nMcastIPHead and nMuMaxIdleTime can be configured.
  class GAFIS_MATCHSVRMGMT extends AncientData
  {
    var nMuCnt:Int = _;
    var nMuBufCnt:Int = _;
    var pstMu_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstMu_Data:Array[GAFIS_MATCHSVRINFO] = _ // for pstMu pointer ,struct:GAFIS_MATCHSVRINFO;
  @Length(4)
  var bnRes_Pt:Array[Byte] = _ ;
    // to here is 16 bytes long.
    var nNextMUID:Int = _;	// next muid.
  var nMcastIPHead:Int = _ ;	// only two bytes used nMcastIPHead[0] and nMcastIPHead[1].
  var nMuMaxIdleTime:Int = _;		// if idle time exceed this value, then we will remove it.
  // default is 600 seconds. min value is 60 max value is 3600*5
  @Length(4)
  var bnRes:Array[Byte] = _ ;
    // to here is 32 bytes long.
    var stCrit = new GBASE_CRIT;	// critical section 96 bytes long.
  @Length(2)
  var stMemItem:Array[GBASE_PARAM_MEMITEM] = _;	// two items.
  } // GAFIS_MATCHSVRMGMT;	// size 128 bytes long



}
