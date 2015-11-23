package nirvana.hall.v62.internal.c.gbaselib

import nirvana.hall.v62.annotations.{IgnoreTransfer, Length}
import nirvana.hall.v62.internal.c.gbaselib.gbasedef.GBASE_CALLTRACEHEAD
import nirvana.hall.v62.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-23
 */
object gathrdop {

  class DWORD extends AncientData{
    @Length(8)
    var bnRes:Array[Byte] = _
  }
    type HANDLE=DWORD

    type GAFIS_THREADPROC_RETVALTYPE = DWORD
    type GAFIS_THREAD_HANDLE = HANDLE
    type GAFIS_THREAD_ID = DWORD

    type GAFIS_EVENT_HANDLE = HANDLE

  type GAFIS_CRITSECT = DWORD
    class GBASE_CRIT extends AncientData
    {
    var stCrit = new GAFIS_CRITSECT;	// critical section structure.
    var bCritInited:Byte = _ ;	// whether initialized.
      @Length(96 - 8 - 1)
    var bnRes_Crit:Array[Byte] = _ //[96-sizeof(GAFIS_CRITSECT)-1];
  } // GBASE_CRIT;	// critical section, 96 bytes long.

  /************************************************************************************
  * if you want to use internal deadlock checking mechanism, please define an macro
  * #define	DEFINE_GAFIS_CHECK_DEADLOCK
  * if not defined , we do not to do deadlock checking
  *
  ************************************************************************************/
  //#define	DEFINE_GAFIS_CHECK_DEADLOCK

  // for each enter critical section, we log it's file name and line #
  // when checking deadlock we check through each critical section
  class CRIT_ENTERNODE extends AncientData
  {
  var pCrit_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pCrit_Data:Array[Byte] = _ // for pCrit pointer ,struct:void;	// critical section pointer
  @Length(4)
  var bnRes_Pointer:Array[Byte] = _ ;	//
  var nThreadID:Int = _;	// thread id
  var nLineNo:Short = _;	// maximum line # if 65535
  @Length(2)
  var bnRes:Array[Byte] = _ ;	// reserved
  @Length(16)
  var szFileName:String = _ ;
  } // CRIT_ENTERNODE;	// critical section enter node	// size is 32 bytes

  class CRIT_ENTERARRAY extends AncientData
  {
  var nNodeCount:Int = _;
  var nNodeBufCount:Int = _;
  var pCrit_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pCrit_Data:Array[Byte] = _ // for pCrit pointer ,struct:void;	// critical section
  var pNode_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pNode_Data:Array[CRIT_ENTERNODE] = _ // for pNode pointer ,struct:CRIT_ENTERNODE;
  @Length(4*2)
  var bnRes_Pointer:Array[Byte] = _ ;
  @Length(8)
  var bnRes:Array[Byte] = _ ;
  } // CRIT_ENTERARRAY;	// size is 32 bytes, one array for each critical section

  class CRIT_ENTERCHECKER extends AncientData
  {
  var nArrayCount:Int = _;
  var nArrayBufCount:Int = _;
  var pstArray_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstArray_Data:Array[CRIT_ENTERARRAY] = _ // for pstArray pointer ,struct:CRIT_ENTERARRAY;
  @Length(4)
  var bnRes_Pointer:Array[Byte] = _ ;
  @Length(16)
  var bnRes:Array[Byte] = _ ;
  var stCrit = new GAFIS_CRITSECT;
    @Length(96 - 8)
  var bnRes_Crit:Array[Byte] = _
  } // CRIT_ENTERCHECKER;	// size is 128 bytes, only for whole system

  class GBASE_THREADLISTNODE extends AncientData
  {

    var  pstPrev_Ptr:Int = _
  var pstPrev_Data :Array[GBASE_THREADLISTNODE] = _
  var  pstNext_Ptr:Int = _
  var  pstNext_Data:Array[GBASE_THREADLISTNODE] = _
  var nTID = new GAFIS_THREAD_ID;	// thread id.
  var stStackHead = new GBASE_CALLTRACEHEAD;	// stack trace.
  } // GBASE_THREADLISTNODE;

  // for debug used only.
  class GBASE_THREADLIST extends AncientData
  {
  var pstFirst_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstFirst_Data:Array[GBASE_THREADLISTNODE] = _ // for pstFirst pointer ,struct:GBASE_THREADLISTNODE;
  var pstLast_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstLast_Data:Array[GBASE_THREADLISTNODE] = _ // for pstLast pointer ,struct:GBASE_THREADLISTNODE;	// last node.
  @Length(4*2)
  var bnRes_Pt:Array[Byte] = _ ;
  var stCrit = new GBASE_CRIT;
  var nThreadCnt:Int = _;
  @Length(12)
  var bnRes:Array[Byte] = _ ;
  } // GBASE_THREADLIST;	// thread list head 128 bytes long.

  /// return value for GAFIS_EVENT_TimeWait
  final val GAEVENT_TIMEOUT = 0
  final val GAEVENT_HASEVENT = 1

  class GBASE_DATAQUE extends AncientData
  {
  var stCrit = new GBASE_CRIT;	// 96 bytes long.

  var nTotalItemCnt:Int = _;	// how many pstParam in que.
  var nFirst:Int = _;
  var nLast:Int = _;
  var nCurItemCnt:Int = _;
  var nItemSize:Int = _;
  var bNoWait:Byte = _ ;
  @Length(3)
  var nRes:String = _ ;
  // to here 120 bytes long.

  var hAddDataEvent = new GAFIS_EVENT_HANDLE;	// que event.
  var hGetDataEvent = new GAFIS_EVENT_HANDLE;
  var pData_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pData_Data:Array[Byte] = _ // for pData pointer ,struct:UCHAR;
  @Length(4*3)
  var bnRes_Pt:Array[Byte] = _ ;
  // to here is 120+24=144 bytes long.
  } // GBASE_DATAQUE;	// 144 bytes long.


  class GBASE_INTRESOURCE extends AncientData
  {
  var nMax:Int = _;	// [0, nMax-1]. 最大资源的数量
  var nCur:Int = _;	// 剩余的资源的数量
  @Length(16)
  var bnRes:Array[Byte] = _ ;
  var hFreeEvent = new GAFIS_EVENT_HANDLE;
  @Length(4)
  var bnRes_Pt:Array[Byte] = _ ;
  // to here is 32 bytes long.
  var stCrit = new GBASE_CRIT;
  } // GBASE_INTRESOURCE;

  //////////////////////////////
}
