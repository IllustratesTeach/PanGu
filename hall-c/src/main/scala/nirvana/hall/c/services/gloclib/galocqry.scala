package nirvana.hall.c.services.gloclib

import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services.{SID_TYPE, AncientData}
import nirvana.hall.c.services.gbaselib.gathrdop.{GBASE_CRIT, GAFIS_EVENT_HANDLE}
import nirvana.hall.c.services.gloclib.gaqryque.{GAQUERYTOSEARCHSTRUCT, GAQUERYCANDSTRUCT}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-12
 */
object galocqry {
  // when we need a query, we search this queue
  type GAMEMQUERYSTRUCT = GAQUERYTOSEARCHSTRUCT;
  /*tagGAMEMQUERYSTRUCT
  {
    UCHAR	nState;		// state : ready(need to search), working, deleted
    UCHAR	nPriority;	// priority
    UCHAR	nQueryType;	// query type, QUERYTYPE_LT, LL, TL, TT, TEXT
    UCHAR	nFlag;		// adopted from query structure
    UCHAR	nRes[2];	// reserved
    UCHAR	nDBID[2];	// database id
    UCHAR	nTableID[2];	// file, for fifo que use only
    UCHAR	nFileID[2];		// file id
    UCHAR	nRID[4];		// record id
    UCHAR	tGetTime[4];	// when the query was got(we need to refresh this query to ready state)
    AFISDateTime	tSubmitTime;	// submit time
  } GAMEMQUERYSTRUCT;	// for parallel match use only
  */

  // to form link list(using index, not pointer)
  // we build two que, one for all queries to be searched, another for searching
  class GAMEMQUERYQUESTRUCT extends AncientData
  {
    var stCrit = new GBASE_CRIT;	// critical section.	// 96 byte long.
  var nQueryCount:Int = _;	// how many queries in this que, effective query
  var nQueryBufSize:Int = _;	// query buffer size
  var pstMemQry_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstMemQry_Data:Array[GAMEMQUERYSTRUCT] = _ // for pstMemQry pointer ,struct:GAMEMQUERYSTRUCT;
  var hQryFinEvent = new GAFIS_EVENT_HANDLE;
    var hHasQryEvent = new GAFIS_EVENT_HANDLE;
    @Length(4*3)
    var bnRes_Pt:Array[Byte] = _ ;
  } // GAMEMQUERYQUESTRUCT;	// memory query 128 bytes long.


  class CANDNODESTRUCT extends AncientData
  {
    var stCand = new GAQUERYCANDSTRUCT;
    var pstNext_Ptr:Int= _;
  } // CANDNODESTRUCT;

  class CANDLINKHEADSTRUCT extends AncientData
  {
    var nMaxCand:Int = _;
    var nCurCand:Int = _;
    var pstFirst_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstFirst_Data:Array[CANDNODESTRUCT] = _ // for pstFirst pointer ,struct:CANDNODESTRUCT;
  } // CANDLINKHEADSTRUCT;

  //// the following lines are added on Aug. 19, 2005
  // in order to notify client whether query finished, we need to build a queue to do this.
  class GAFIS_QRYSTATUSNODE extends AncientData
  {
    var hQryFin = new GAFIS_EVENT_HANDLE;	// if signaled then query fin.
  @Length(4)
  var bnRes_Pt:Array[Byte] = _ ;
    var nretval:Int = _;			// <0 error, 0 time out. 1 ok finished.
  var nDBID:Short = _;
    var nTID:Short = _;
    var nSID = new SID_TYPE;			// sid
  var bStatusSet:Byte = _ ;
    @Length(3)
    var bnRes:Array[Byte] = _ ;
    var nIndex:Int = _;
  } // GAFIS_QRYSTATUSNODE;	// 32 bytes long.

  // status list.
  class GAFIS_QRYSTATUSLIST extends AncientData
  {
    var stCrit = new GBASE_CRIT;			// 96 bytes long.
  var ppstNode_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var ppstNode_Data:Array[GAFIS_QRYSTATUSNODE] = _ // for ppstNode pointer ,struct:GAFIS_QRYSTATUSNODE;	//
  @Length(4)
  var bnRes_Pt:Array[Byte] = _ ;
    var nNodeBufCnt:Int = _;
    var nHighWaterMark:Int = _;	// high water mark
  var nUsedCnt:Int = _;
    @Length(12)
    var bnRes:Array[Byte] = _ ;
  } // GAFIS_QRYSTATUSLIST;	// 128 bytes long.

  // DSID means DTID and SID
  class GAFIS_QRYDSIDENTRY extends AncientData
  {
    var nSID = new SID_TYPE;	// query id.
  var nDBID:Short = _;
    var nTID:Short = _;
    var nIndex:Int = _;	// query index.
  } // GAFIS_QRYDSIDENTRY;	// 16 bytes long.


  class GAFIS_SUBMITTIMEENTRY extends AncientData
  {
    var nSubmitTime:Int = _;
    var nIndex:Int = _;
  } // GAFIS_SUBMITTIMEENTRY;	// 8 bytes long.

  type GAFIS_MEMQUERYENTRY=GAQUERYTOSEARCHSTRUCT	;

  // [mnt  txt   mis]
  class GAFIS_MEMQUERYQUE extends AncientData
  {
    var pstQry_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstQry_Data:Array[GAFIS_MEMQUERYENTRY] = _ // for pstQry pointer ,struct:GAFIS_MEMQUERYENTRY;	// first part is que will be moved for each query, long que is not moved.
  var pstDsid_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstDsid_Data:Array[GAFIS_QRYDSIDENTRY] = _ // for pstDsid pointer ,struct:GAFIS_QRYDSIDENTRY;
  var pstStime_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstStime_Data:Array[GAFIS_SUBMITTIMEENTRY] = _ // for pstStime pointer ,struct:GAFIS_SUBMITTIMEENTRY;	// submit time.
  @Length(4*3)
  var bnRes_Pt:Array[Byte] = _ ;
    // to here is 24 bytes long.
    var nDsidBufCnt:Int = _;
    var nQryBufCnt:Int = _;	// usually speaking nDsidBufCnt and nQryBufCnt are the same.

    var nStimeBufCnt:Int = _;

    var nQryCnt:Int = _;	// valid query count. nQryCnt = nMntQryCnt+nTxtQryCnt+nMisQryCnt.

    var nMntQryCnt:Int = _;	// actual mnt query left.
  var nTxtQryCnt:Int = _;	// actual text query left
  var nMisQryCnt:Int = _;	// actual mis query left.

    var nTxtQryStartIdx:Int = _;
    var nMisQryStartIdx:Int = _;
    var nTotalCount:Int = _;		// total count.	// nTotalCount=nMisQryStartIdx+nMisQryCnt.

    var nDelQryCnt:Int = _;		// nDelQryCnt=nTotalIdx-(nMntQryCnt+nTxtQryCnt+nMisQryCnt) used when bMarkDel is true.

    var bMarkDel:Int = _;		// when delete whether directly delete or only mark.
  @Length(8)
  var bnRes:Array[Byte] = _ ;
  } // GAFIS_MEMQUERYQUE;	// 80 bytes long.

  class GAFIS_MEMQUERYADM extends AncientData
  {
    var stCrit = new GBASE_CRIT;	// critical section.	// 96 byte long.
  var stShort = new GAFIS_MEMQUERYQUE;	// 64 bytes long.
  var stLong = new GAFIS_MEMQUERYQUE;		// 64 bytes long.
  var hQryFinEvent = new GAFIS_EVENT_HANDLE;
    var hHasQryEvent = new GAFIS_EVENT_HANDLE;
    @Length(4*2)
    var bnRes_Pt:Array[Byte] = _ ;
    @Length(16)
    var bnRes:Array[Byte] = _ ;
  } // GAFIS_MEMQUERYADM;	// memory query 128 bytes long.



  //// above lines are added on Aug. 19, 2005
  ////////////////////////////////////////////////


}
