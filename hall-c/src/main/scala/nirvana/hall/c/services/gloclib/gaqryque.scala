package nirvana.hall.c.services.gloclib

import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services.gbaselib.gbasedef.GATIMERANGE
import nirvana.hall.c.services.ghpcbase.ghpcdef.AFISDateTime
import nirvana.hall.c.services.gloclib.gadbprop.GADBIDSTRUCT
import nirvana.hall.c.services.gloclib.glocdef._
import nirvana.hall.c.services._
import nirvana.hall.c.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-06
 */
object gaqryque {


  class GA_SIMPCAND extends AncientData
  {
    var nDestDBID:Short = _ ;
    var nDestTID:Short = _ ;
    var nScore:Int = _ ;	// score of the candidate.
  // to here is 8 bytes long.
  @Length(6)
  var nSID:String = _ ;	// sid of the candidate.  6 bytes long.
  var nFg:Byte = _ ;		// finger position.
  var nSrcKeyIndex:Byte = _ ;	// when query has more than one source, using this to indicates
    // which source match the given one.
  } // GA_SIMPCAND;	// candidates, size is 16 bytes long.

  class GA_CANDTEMP {
    @Length(2)
    var data:Array[Byte] = _
    /*
    UCHAR	nRowIndex[2];
    char	nFinal;	// 需要出现的正是的候选队列中。nFinal越大越需要出现在候选当中。
    */
  } //GA_CANDTEMP;


  class GA_SIMPCANDEX extends AncientData
  {
    //GA_SIMPCAND	stCand;	// candidates. though it's simple to include a GA_SIMPCAND structure, but
    // it's difficult to use,  so we include every field in
    // GA_SIMPCAND.
    var nDestDBID:Short = _ ;
    var nDestTID:Short = _ ;
    var nScore:Int = _ ;	// score of the candidate.
  // to here is 8 bytes long.
  @Length(6)
  var nSID:String = _ ;	// sid of the candidate.  6 bytes long.
  var nFg:Byte = _ ;		// finger position.
  var nSrcKeyIndex:Byte = _ ;	// when query has more than one source, using this to indicates
  // which source match the given one.
  // to here is 16 bytes long.
  var nFilterKeyIndex:Byte = _ ;	// when using candidates key filter, using this to indicates
    // this candidates belong to which filter group. at least
    // have to filter group. searching used only.
    //	UCHAR	nFgGroup;			// when searching latent database, some finger  may belong to
    // same person's same finger, so we do not want those fingers
    // appear in candidate list same time. so we remove them like
    // remove duplicate tenprint card.
    /**
     * 把 bnRes0更改为用来表示这个候选是来自那种算法的，值为GAFISMATCH_ALGORITHM_xxx
     **/
    //UCHAR	bnRes0[1];			// reserved one bytes.
    var nMatchAlgorithm:Byte = _ ;

    var stTemp = new GA_CANDTEMP;
    @Length(12)
    var bnRes:Array[Byte] = _ ;	// reserved
  } // GA_SIMPCANDEX;	// candidates, size is 32 bytes long.

  final val GAFISMATCH_ALGORITHM_XGW = 0	//!< 由许公望比对算法得到的候选，这是缺省值
  final val GAFISMATCH_ALGORITHM_LCW = 1	//!< 由王曙光比对算法得到的候选
  final val GAFISMATCH_ALGORITHM_MIXXL = 2	//!< 由许公望和王曙光的比对算法合并得到的候选

  class GA_SIMPCANDEXOFFSET extends AncientData
  {
    var nDestDBID:Int = _;	// = 0
  var nDestTID:Int = _;	// = 2
  var nScore:Int = _;		// = 4
  var nSID:Int = _;		// = 8
  var nFg:Int = _;		// = 14
  var nSrcKeyIndex:Int = _;	// = 15
  var nFilterKeyIndex:Int = _;	// = 16
  @Length(36)
  var bnRes:Array[Byte] = _ ;
  } // GA_SIMPCANDEXOFFSET;	// 64 bytes long.


  class GA_CANDTYPE extends Enumeration
  {
    val CANDTYPE_SIMP = Value
    val CANDTYPE_SIMPEX = Value
    val CANDTYPE_QRYCAND = Value
  } //GA_CANDTYPE;

  class GA_CANDDIST extends AncientData
  {
    var nDBID:Short = _;
    var nTID:Short = _;
    var nStartIdx:Int = _;
    var nEndIdx:Int = _;
  } // GA_CANDDIST;	// candidate distribution by dtid. 12 bytes long.

  class GA_CANDPERSONID extends AncientData
  {
    @Length(6)
    var nSID:String = _ ;
    @Length(8-SID_SIZE)
    var bnRes_SID:Array[Byte] = _ ;
    @Length(32)
    var szPersonID:String = _ ;
    var nFg:Int = _;	// finger.
  var nIndex:Int = _;
    var nScore:Int = _ ;
    @Length(12)
    var bnRes2:Array[Byte] = _ ;
  } // GA_CANDPERSONID;	// 64 bytes long.

  class GA_LATPRINTID extends AncientData
  {
    @Length(6)
    var nSID:String = _ ;
    @Length(8-SID_SIZE)
    var bnRes_SID:Array[Byte] = _ ;
    @Length(32)
    var szGroupID:String = _ ;
    // to here is 40 bytes long.
    var nFgIndex:Byte = _ ;
    var nFgGroup:Byte = _ ;
    var bNotUsed:Byte = _ ;
    @Length(1)
    var bnRes:Array[Byte] = _ ;
    var nIndex:Int = _;
    // to here is 48 bytes long.
    var nScore:Int = _;	// score.
  var nFg:Int = _;
    @Length(8)
    var bnResx:Array[Byte] = _ ;
  } // GA_LATPRINTID;	// 64 bytes long.

  // for the match engine, we does not need user name and key to search next
  // step which query we need to search, we just need the following values
  // we must restrict the size of the structure
  class GAQUERYTOSEARCHSTRUCT extends AncientData
  {
    var nQueryType:Byte = _ ;			// querytype, QUERYTYPE_LT, LL, TL, TT, TEXT
  var nPriority:Byte = _ ;			// priority
  var bIsFifoQue:Byte = _ ;			// whether is fifo queue
  var nStatus:Byte = _ ;			// status(to search, searching, to check , checking, checked), GAQRY_STATUS_XXXX
  var nFlag:Byte = _ ;				// flag(current not used
  var nRmtFlag:Byte = _ ;			// GAQRY_RMTFLAG_XXX
  var nStage:Byte = _ ;				// stage( for two step method, we can expand to k step methods)
  var nFlagEx:Byte = _ ;			// Extended flag, GAQRY_FLAGEX_
  var nDBID:Short = _ ;			// database id
  @Length(SID_SIZE)
  var nQueryID:String = _ ;		// query id
  var nTableID:Short = _ ;		// table id
  var bInMemUsed:Byte = _ ;			// whether it's used in memory
  var bInMemSearched:Byte = _ ;		// it's being searched
  var tGetTime:Int = _ ;		// when the query was got(we need to refresh this query to ready state)
  var tSubmitTime = new AFISDateTime;	// submit time, 8 bytes
  var nMuID:Int = _ ;		// id of mu.
  var nRegisterTime:Int = _ ;	// time of register.
  // the following items are added on July 7, 2004
  @Length(4)
  var stDestDB:Array[GADBIDSTRUCT] = _;	// at most have 4 dest dbid.
  var nDestDBCnt:Byte = _ ;		// # of destdbid.
  var nFlag3:Byte = _ ;
    var nFlag4:Byte = _ ;
    var nMISState:Byte = _ ;
    var nInMemFlag:Byte = _ ;	// GAQRY_INMEMFLAG_XXX
  var nDynPri:Byte = _ ;	// dynamic priority.
  var nQueryTypePri:Byte = _ ;	// if nDynPri
  var nSchType:Byte = _ ;		// searching type.	GAQRY_SCHTYPE_xxxx
    //!< 增加查询的提交用户，added on June 14, 2011
    //char	szSubmitUser[16];
  } // GAQUERYTOSEARCHSTRUCT;	// size of this structure 64 + 16 bytes. Yes, it's very compact
  // the best way is to sort the query according some criteria
  // priority is first, then query type(TT is first, TL is second, LT is and LL are last)
  // the submit time

  final val GAQRY_STATUS_WAITSEARCH = READY
  final val GAQRY_STATUS_SEARCHING = WORKING
  final val GAQRY_STATUS_WAITCHECK = FINISHED
  final val GAQRY_STATUS_CHECKING = CHECKING
  final val GAQRY_STATUS_CHECKED = CHECKED
  final val GAQRY_STATUS_ERRDATA = ERRDATA
  final val GAQRY_STATUS_WAITCENSOR = WAITCENSOR
  final val GAQRY_STATUS_WAITRECHECK = WAITRECHECK
  final val GAQRY_STATUS_RECHECKING = RECHECKING
  final val GAQRY_STATUS_RECHECKED = RECHECKED
  final val GAQRY_STATUS_NULLSTATE = 55	// the current queries state is undetermined

  //GAQUERYTOSEARCHSTRUCT::nSchType
  final val GAQRY_SCHTYPE_MNTSEARCH = 0
  final val GAQRY_SCHTYPE_TXTSEARCH = 1
  final val GAQRY_SCHTYPE_MISSEARCH = 2

  //GAQUERYTOSEARCHSTRUCT::nInMemFlag
  // the following structure is used to generate a list of query
  class GAQUERYSIMPSTRUCT extends AncientData
  {
    var cbSize:Int = 192 ;			// size of this structure
  @Length(32)
  var szKeyID:String = _ ;		// original key(finger id)
  @Length(16)
  var szUserName:String = _ ;		// user name(to here is 52 bytes)
  var nQueryType:Byte = _ ;			// querytype, QUERYTYPE_LT, TT, TL, LL
  var nPriority:Byte = _ ;			// priority
  var nHitPossibility:Byte = _ ;	// hit possibility(0-100)
  var nStatus:Byte = _ ;			// status(to search, searching, to check , checking, checked, nullstate)(to here is 56 bytes)
  var nFlag:Byte = _ ;				// GAQRY_FLAG_XXX
  var nRmtFlag:Byte = _ ;			// identify type of the query GAQRY_RMTFLAG_XX
  var nStage:Byte = _ ;				// stage( for two step or multi step method, we can expand to k step methods)
  var nFlagEx:Byte = _ ;			// Extended flag
  var nQueryID_old:Int = _ ;		// query id(to here is 64 bytes)
  var stSrcDB = new GADBIDSTRUCT;	// where the data come from
  @Length(4)
  var stDestDB:Array[GADBIDSTRUCT] = _;	// at most we can search 4 database at the same time
  var nTimeUsed:Int = _ ;		// in seconds
  var nMaxCandidateNum:Int = _ ;	// maximum candidate num
  var nCurCandidateNum:Int = _ ;	// current candidate num(to here is 96 bytes)
  var tSubmitTime = new AFISDateTime;	// submit time
  var tFinishTime = new AFISDateTime;	// finish time
  var tCheckTime = new AFISDateTime;		// check time
  @Length(16)
  var szCheckUserName:String = _ ;	// this user name is in user id list
  ///////////////to here is 96+40=136 bytes
  var nVerifyResult:Byte = _ ;	// verify result, not match, match, undetermined, GAQRY_VERIFYRES_XXX
  var nRmtState:Byte = _ ;		// remote query state, GAQRY_RMTSTATE_xxxx
  var nMISState:Byte = _ ;		// mis query state, GAQRY_MISSTATE_XXX
  var nDestDBCount:Byte = _ ;
    var nGroupID:Int = _ ;	// group id
  var bIsFifoQue:Byte = _ ;
    var nVerifyPri:Byte = _ ;		// verify priority.
  var nFlag3:Byte = _ ;			// add two more flags GARAY_FLAG3_XXX
  var nFlag4:Byte = _ ;			// GAQRY_FLAG4_XXX
  var nFlag5:Byte = _ ;			// GAQRY_FLAG5_XXX
  var nFlag6:Byte = _ ;			// GAQRY_FLAG6_XXX, for 查询的多点下载
  var nFlag7:Byte = _ ;			// GAQRY_FLAG7_XXX，TT自动认定使用的标志。
  var nFlag8:Byte = _ ;			// 2008.04.12 未使用，为扩展保留；2010.05.09：增加一个标记，表明是广东LT衰减率测试
  @Length(SID_SIZE)
  var nQueryID:Array[Byte]= _ ;
    @Length(2)
    var bnRes_sid:Array[Byte] = _ ;		// reserved
  var nMinScore:Int = _ ;	// the score in candidate list at least this value.
  var nSchCandCnt:Int = _ ;	// search candidate #.
  @Length(16)
  var szReCheckUserName:String = _ ;	// rechecker user name
  var tReCheckDate = new AFISDateTime;			// re check date
  } // GAQUERYSIMPSTRUCT;	// size of this structure is 192  bytes. too big?

  // remote query state may be wait sending query, wait collecting results, wait receive cand data, finished
  final val GAQRY_RMTSTATE_WAITSENDING = 0x1
  final val GAQRY_RMTSTATE_WAITRESULT = 0x2
  final val GAQRY_RMTSTATE_WAITDATA = 0x3
  final val GAQRY_RMTSTATE_FINISHED = 0x4
  final val GAQRY_RMTSTATE_RMTGOTTEN = 0x5	// the other rmt side has gotten the query and this
  // query can be deleted
  final val GAQRY_RMTSTATE_WAITCHECK = 0x6
  final val GAQRY_RMTSTATE_CHECKING = 0x7
  final val GAQRY_RMTSTATE_CHECKED = 0x8
  final val GAQRY_RMTSTATE_WAITRECHECK = 0x9
  final val GAQRY_RMTSTATE_RECHECKING = 0xA
  final val GAQRY_RMTSTATE_RECHECKED = 0xB
  final val GAQRY_RMTSTATE_WAITFEEDBACK = 0xC
  final val GAQRY_RMTSTATE_FINISHFEEDBACK = 0xD

  final val GAQRY_RMTSTATE_ERR_SEND = 0x20	// the query has some error when sending data
  final val GAQRY_RMTSTATE_ERR_GETRESULT = 0x21	// the query has some error when get cand list
  final val GAQRY_RMTSTATE_ERR_GETDATA = 0x22	// the query has some error when get cand data
  final val GAQRY_RMTSTATE_ERR_RMTSVRNOTFOUND = 0x23	// the rmt server not found
  final val GAQRY_RMTSTATE_ERR_TOOMANYQUERY = 0x24
  final val GAQRY_RMTSTATE_ERR_OTHER = 0x25	// other error

  final val GAQRY_RMTFLAG_LOCAL = 0x0	// local query
  final val GAQRY_RMTFLAG_TOREMOTE = 0x1	// the query will not search in local
  final val GAQRY_RMTFLAG_FROMREMOTE = 0x2	// the query was got from remote, so it will be searched
  final val GAQRY_RMTFLAG_PROXYONLY = 0x3	// the query will not be searched local
  final val GAQRY_RMTFLAG_LOCALANDPROXY = 0x4	// the query will be send to local and remote
  final val GAQRY_RMTFLAG_DOWNLOADED = 0x5	// download from remote.
  final val GAQRY_RMTFLAG_CHECK = 0x6
  final val GAQRY_RMTFLAG_RECHECK = 0x7

  final val GAQRY_VERIFYRES_NOTSURE = 0x0
  final val GAQRY_VERIFYRES_NOTMATCH = 0x1
  final val GAQRY_VERIFYRES_MATCH = 0x2

  //GAQUERYSIMPSTRUCT::nFlag
  final val GAQRY_FLAG_USEFINGER = 0x1		// using finger
  final val GAQRY_FLAG_USEPALM = 0x2		// using palm
  final val GAQRY_FLAG_USETEXT = 0x4		// has text
  final val GAQRY_FLAG_USEMIS = 0x8		// is mis query
  final val GAQRY_FLAG_SEARCHINCAND = 0x10	// search in candidate list.
  final val GAQRY_FLAG_USEMINSCORE = 0x20
  final val GAQRY_FLAG_ALLCANDFINGER = 0x40	// used when searching in candidate list and searching
  // type is LT, in that case, the finger index field
  // in candidate will not be used and all fingers for
  // one fingers will be searched. for step two this flag
  // will not be set.
  final val GAQRY_FLAG_USEPLAINFINGER = 0x80	// using plain finger.

  // GAQUERYSIMPSTRUCT::nFlag3
  final val GAQRY_FLAG3_REVERSEKEYRANGE = 0x1		// normally speaking, GAQUERYSTRUCT::stKeyRange specify
  // the searching range will include keys that matching
  // the condition. but if this bit is set, all keys matching
  // this condition will not be searched.
  final val GAQRY_FLAG3_NEEDRECHECK = 0x2		// call for recheck.
  final val GAQRY_FLAG3_RECHECKED = 0x4		// has been rechecked.
  final val GAQRY_FLAG3_BROKEN = 0x8		// found matching.
  final val GAQRY_FLAG3_DOWNLOADCHECKED = 0x10	// for remote query, the remote user only download
  // those queries that has been check by the central
  // agency(assume the remote user is incapable of
  // identifying whether two finger match or not, but
  // want to know the result).
  final val GAQRY_FLAG3_URGENT = 0x20	// the query is urgent, need quick response. in fact.
  // this flag means the query need manual edit or check
  // of source fingers minutia and check of searching
  // result.
  final val GAQRY_FLAG3_SEARCHMNTFIRST = 0x40	// if has text, we would search text first by default
  // the query can be submitted from 2 systems : from AFIS or from MIS.
  // if from mis then default searching minutia and text searching will
  // be omitted(in this case we assume we have a candidate list).
  // if from AFIS then the text searching and if GAQRY_FLAG_USETEXT is specified
  // then texting searching ahs two methods : by AFIS or by MIS. default is by AFIS
  // if GAQRY_FLAG_USEMIS flag has been set then text searching will be carried out
  // by MIS.
  // if searching text in mis then nMISState
  // will be
  //  (1)WAIT TO SENDING TO MIS.
  //  (2)WAIT MIS RESULT ( we have an standalone app to connect between GAFIS server and MIS server)
  //     the app will retrieve query from GAFIS and sending it to MIS and get result from mis.
  //  (3)MIS FINISHED.

  //GAQUERYSIMPSTRUCT::nMISState, this flag is also called textstate.
  final val GAQRY_MISSTATE_NEEDSENDTOMIS = 0	// if GAQRY_FLAG_USEMIS flag set
  final val GAQRY_MISSTATE_WAITRESULT = 1	// wait mis result.
  final val GAQRY_MISSTATE_FINISHED = 2

  //GAQUERYSIMPSTRUCT::nFlag4
  final val GAQRY_FLAG4_MNTSEARCHED = 0x1	// indicate mnt has been searched.
  final val GAQRY_FLAG4_TXTSEARCHED = 0x2	// indicate txt has been searched.
  final val GAQRY_FLAG4_MERGETPBYPERSON = 0x4	// for LT. if some candidates are fingers of the
  // same person, the result will be merged and only left
  // one with the highest score.
  // for tl, all fingers of the same person are used to do tl
  final val GAQRY_FLAG4_EXCLUDENONCAPTURED = 0x8	// when doing tl, if the the lp finger has been marked
  // broken but not captured, then exclude it from candidate
  // list.
  final val GAQRY_FLAG4_EXCLUDECAPTURED = 0x10	// when doing tl, if the lp finger is broken and
  // corresponding criminal has been captured then
  // exclude lp finger from candidate list.

  final val GAQRY_FLAG4_ISFPXVERIFY = 0x20
  final val GAQRY_FLAG4_SRCMULTIKEY = 0x40	// source have multi key(belong to one group).

  // fast tt search [5/17/2007]
  final val GAQRY_FLAG5_FASTTTSEARCH = 0x01	//只比对SID小的记录
  final val GAQRY_FLAG5_TTAUTOCHECK = 0x02	//自动认定TT查询（把候选结果中比中的加入相应的标志）。
  //如果全部候选都为比中，则修改查询的状态为认定完毕。
  final val GAQRY_FLAG5_TTAUTOMERGE = 0x04	//自动合并TT候选结果，构建指纹组，覆盖TTAUTOCHECK选项，
  //自动认定，并且把多余的候选删除。只剩余不能够自动认定的候选。
  final val GAQRY_FLAG5_DELAFTERAUTOCHECK = 0x08	//认定后自动删除
  //该标志是否可以扩展到除了TT查询之外的其他查询类型？
  final val GAQRY_FLAG5_CHECKERSPECIFIED = 0x10	// 指定查询的认定者。
  final val GAQRY_FLAG5_LIVESCANQUERY = 0x20	// 此查询是活体查询，需要及时反馈结果，此查询一般是远程查询，并且此查询
  // 不受查询个数限制。2008.04.16，此标志由远程程序自动添加，用户提交查询的
  // 时候不能够设置此标志。
  final val GAQRY_FLAG5_TTHITWANTEDMAN = 0x40	//!< 表示该TT查询比中了一个追逃、辑控人员

  /* QueryType */
  final val QUERYTYPE_TT = TTMATCH
  final val QUERYTYPE_TL = TLMATCH
  final val QUERYTYPE_LL = LLMATCH
  final val QUERYTYPE_LT = LTMATCH

  final val QUERYTYPE_NOTEXIST = 100

  // nFlagEx bit or'ed
  //           +------------------------downloaded flag, totally downloaded
  //           |      +-----------------downloading flag, if set then downloading, when gfdbsvr started, will be cleared
  //           |      |     |-----------rmt check flag, if set then rmtsvr can download this query
  //   Bit3   Bit2   Bit1  Bit0                         and this query can be checked by rmt user
  final val GAQRY_FLAGEX_CANRMTCHECK = 0x1	//
  final val GAQRY_FLAGEX_DOWNLOADING = 0x2
  final val GQQRY_FLAGEX_DOWNLOADED = 0x4
  final val GAQRY_FLAGEX_FINNOTIFY = 0x8		// notify user when finished.
  final val GAQRY_FLAGEX_RESERVESCHCAND = 0x10	// reserve sch candidate, default is not
  final val GAQRY_FLAGEX_TTSWAP = 0x20	// when doing tt search, using roll finger to search plain and vice versa

  // auto delete the query if no candidate.
  final val GAQRY_FLAGEX_AUTODELIFNOCAND = 0x40
  // copy the query to backup query lib.
  final val GAQRY_FLAGEX_BACKUPWHENDEL = 0x80

  // GAQUERYSIMPSTRUCT::nFlag6
  final val GAQRY_FLAG6_PCS_DOWNLOADING = 0x01		// 派出所正在下载该查询
  final val GAQRY_FLAG6_PCS_DOWNLOADED = 0x02		// 派出所已经下载了该查询
  final val GAQRY_FLAG6_FXJ_DOWNLOADING = 0x04		// 分县局正在下载该查询
  final val GAQRY_FLAG6_FXJ_DOWNLOADED = 0x08		// 分县局已经下载了该查询
  final val GAQRY_FLAG6_DSJ_DOWNLOADING = 0x10		// 地市局正在下载该查询
  final val GAQRY_FLAG6_DSJ_DOWNLOADED = 0x20		// 地市局已经下载了该查询
  final val GAQRY_FLAG6_SHENGTING_DOWNLOADING = 0x40	// 省厅正在下载该查询
  final val GAQRY_FLAG6_SHENGTING_DOWNLOADED = 0x80	// 省厅已经下载了该查询

  // GAQUERYSIMPSTRUCT::nFlag7
  /*
    这个标志的格式如下
    +-----+-----+-----+-----+-----+-----+-----+-----+
    |Bit7 |Bit6 |Bit5 |Bit4 |Bit3 | Bit2| Bit1| Bit0|
    +-----+-----+-----+-----+-----+-----+-----+-----+
    |TT自动认定结果标志     | DB3 | DB2 | DB1 | DB0 |
    +-----------------------+-----+-----+-----+-----+
    如果源数据的条码出现在stDestDB[i]中，则DBi就被设置为1，否则设置为0，其中0<=i<=3
    具体可以参考《远程查询TT自动认定规则.DOC》文件。
   */

  // GAQUERYSIMPSTRUCT::nFlag8
  final val GAQRY_FLAG8_GDTEST = 0x1	//!< 表示是广州测试，特别列出广东模式，是因为在显示比对库时要特殊处理

  /**
   * 用来标记一些具有特殊意义的查询，例如：
   * 1、在大连，这些查询的指掌纹数据是从海鑫系统获取的
   * 2、在新疆，这些查询是通过自动翻查系统发送的查询
   */
  final val GAQRY_FLAG8_SPECIAL = 0x2

  final val GAQRY_FLAG8_SYSTEST = 0x4	//!< 类似贵州、甘肃、广东测试模式下发送的查询

  /**
   * 目前:对大连表示此查询已反馈给海鑫系统
   */
  final val GAQRY_FLAG8_AFTERSPE = 0x8 //!< 对于GAQRY_FLAG8_SPECIAL标记的后续修改标记

  class GAKEYRANGESTRUCT extends AncientData
  {
    @Length(32)
    var szStartKey:String = _ ;
    @Length(32)
    var szEndKey:String = _ ;
  } // GAKEYRANGESTRUCT;	// size of this structure is 64 bytes

  // the following structure need not be changed frequently
  class GAQUERYCANDHEADSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;	// size of this structure
  @Length(32)
  var szKey:String = _ ;	// key of the original data
  @Length(16)
  var szUserName:String = _ ;	// user name
  var nQueryType:Byte = _ ;	// query type
  var CPUCoef:Byte = _ ;	// cpu coefficient
  var nSrcDBID:Short = _ ;	// dbid
  var nTableID:Short = _ ;	// table id
  var bIsPalm:Byte = _ ;		// is palm match
  @Length(3)
  var bnRes:Array[Byte] = _ ;	// to here is 64 bytes
  @Length(SID_SIZE)
  var nQueryID:String = _ ;	// query id
  var nCandidateNum:Int = _ ;	// current candidate num
  var tSubmitTime = new AFISDateTime;	// submit time
  var tFinishTime = new AFISDateTime;	// finish time
  var nRecSearched:Long = _ ;		// record number searched, to here is 96 bytes
  @Length(32)
  var bnRes2:Array[Byte] = _ ;			// reserved
  } // GAQUERYCANDHEADSTRUCT;	// size of this structure is 128 bytes

  // candidate structure
  // normally, for 50 candidate's we need 50*96+128=4928bytes. not very large
  // but very nice to store those data.
  final val GAQRYCAND_STATUS_READY = 0x00
  final val GAQRYCAND_STATUS_FINISHED = 0x01
  final val GAQRYCAND_STATUS_ERROR = 0x02

  class GAQUERYCANDSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;		// size of this structure
  var nScore:Int = _ ;		// score
  @Length(32)
  var szKey:String = _ ;		// key
  var nServerID:Short = _ ;	// server id
  var nDBID:Short = _ ;		// dbid
  var nTableID:Short = _ ;	// table id
  var nFileID:Short = _ ;		// file id
  var nCheckState:Byte = _ ;	// check state, GAQRYCAND_CHKSTATE_XXX
  @Length(3)
  var bnRes_nRID:Array[Byte] = _ ;		// record id, to here is 52 bytes
  var nStatus:Byte = _ ;		// status,GAQRYCAND_STATUS_XXX
  var nGetCandFailCnt:Byte = _ ;	// get fail count
  var bIsDataCached:Byte = _ ;		// is data cached
  var nTxPassedCount:Byte = _ ;		// tx passed count
  var nIndex:Byte = _ ;				// 1-10, finger, 1-2 palm
  var nFlag:Byte = _ ;				// GAQRYCAND_FLAG_XXX
  var nFinalServerID:Short = _ ;	// final server id, to here is 60 bytes
  var nSrcKeyIndex:Byte = _ ;		// if src have multi key., using this flag to indicate which one match
    /**
     * 增加两个字段：
     *	nMatchAlgorithm：用来表示该候选是通过那个比对算法得到的，值为GAFISMATCH_ALGORITHM_xxx
     *	nGroupid：用来表示候选中的重卡信息，nGroupid=0表示没有重卡，nGroupid>0并且相等的候选表示属于同一个重卡组
     * 2010.05.12：又增加了一个字段nDupCardCnt，用来记录属于该groupid的重卡个数
     */
    var nMatchAlgorithm:Byte = _ ;
    var nGroupid:Byte = _ ;
    var nDupCardCnt:Byte = _ ;
    //UCHAR	bnRes2[1];			// reserved(64 bytes to here)
    var tFinishTime = new AFISDateTime;	// finished time
  var nServerDBID:Short = _ ;		// at by xiaxinfeng.
  var nServerTableID:Short = _ ;

    /**
     *	2011年8月2日：新增两个字段，替换了下面的bnRes3[4]字段，
     * 这两个字段分别用来表示该合并候选在许公望候选和王曙光候选中的排名（从1开始）
     * 这两个字段是否有意义由nMatchAlgorithm的值决定
     */
    //UCHAR	bnRes3[4];				// reserved
    var nXgwRank:Short = _ ;
    var nWsgRank:Short = _ ;
    @Length(SID_SIZE)
    var nSID:Array[Byte]= _ ;
    @Length(8-SID_SIZE)
    var bnRes_SID:Array[Byte] = _ ;
    /**
     * 增加两个字段，分别用来标记该候选在一次比对时的得分和排名（从1开始）
     * On Apr. 6, 2010
     */
    var nStepOneScore:Short = _ ;
    var nStepOneRank:Int = _ ;
    @Length(2)
    var bnRes4:Array[Byte] = _ ;		// reserved
  } // GAQUERYCANDSTRUCT;	// size of this structure is 96 bytes

  // when verifying, we set the candidate state to
  // the following one to indicate the candidate whether
  // match source.
  // GAQUERYCANDSTRUCT::nCheckState
  final val GAQRYCAND_CHKSTATE_UNCHECKED = 0x0	// not checked.
  final val GAQRYCAND_CHKSTATE_UNCERTAIN = 0x1	// checked and uncertain. need more time to check and may be match
  final val GAQRYCAND_CHKSTATE_NOTMATCH = 0x2	// the candidate does not match source.
  final val GAQRYCAND_CHKSTATE_MATCH = 0x3	// the candidate match the source.

  // GAQUERYCANDSTRUCT::nFlag
  final val GAQRYCAND_FLAG_NEEDRECHECK = 0x1	// the candidate may match with the source, call for recheck.
  final val GAQRYCAND_FLAG_RECHECKED = 0x2	// has rechecked.
  final val GAQRYCAND_FLAG_BROKEN = 0x4	// matching with source.

  class QUERYMNTSTRUCT extends AncientData
  {
    var nItemCount:Byte = _ ;	// how many fingers mnt/palm mnt in this memory block
  var bIsPalm:Byte = _ ;
    @Length(2)
    var bnRes:Array[Byte] = _ ;	// reserved
  var nEachItemLen:Int = _ ;	// each item length
  @Length(24)
  var nFingerIndex:String = _ ;	// finger index, 1-10
  @Length(8)
  var bnData:Array[Byte] = _ ;
  } // QUERYMNTSTRUCT;		// size is 32 to 2GB.

  // dec. 12. 09, 2004. the GAQUERYSTRUCT is too small, and can not hold too many info
  // so we add another sub structure to hold it.
  class GAFIS_QUERYINFO extends AncientData
  {
    var cbSize:Int = _ ;	// size now is 256 bytes long.
  @Length(4)
  var bnRes:Array[Byte] = _ ;	// reserved.
  @Length(16)
  var szUserUnitCode:String = _ ;
    @Length(16)
    var szCheckerUnitCode:String = _ ;
    @Length(16)
    var szReCheckerUnitCode:String = _ ;
    // to here is 48+8 bytes long.
    var nRecSearched:Int = _ ;	// how many records has been searched.
  @Length(4)
  var bnRes0:Array[Byte] = _ ;
    // to here is 64 bytes long.
    @Length(16)
    var bnIP:Array[Byte] = _ ;	// for ipv4, using first 4 bytes.
  @Length(32)
  var szComputerName:String = _ ;	// computer name.
  @Length(32)
  var szQryUID:String = _ ;		// query uuid.
  // to here is 128+16 bytes long.
  @Length(32)
  var szFPXForeignTaskID:String = _ ;
    @Length(16)
    var szFPXVerifyUnitCode:String = _ ;
    // to here is 128+64 bytes long.
    var nFPXStatus:Byte = _ ;
    var nFPXVerifyResult:Byte = _ ;
    var nItemFlag:Byte = _ ;	// QUERYINFO_ITEMFLAG_XXX
  @Length(5)
  var bnRes1:Array[Byte] = _ ;

    @Length(56)
    var bnRes2:Array[Byte] = _ ;	// reserved.
  } // GAFIS_QUERYINFO;	// 256 bytes long.

  // GAFIS_QUERYINFO::nItemFlag;
  final val QUERYINFO_ITEMFLAG_QRYUID = 0x1
  final val QUERYINFO_ITEMFLAG_FPXFOREIGNTASKID = 0x2
  final val QUERYINFO_ITEMFLAG_FPXVERIFYUNITCODE = 0x4
  final val QUERYINFO_ITEMFLAG_FPXSTATUS = 0x8
  final val QUERYINFO_ITEMFLAG_FPXVERIFYRESULT = 0x10
  final val QUERYINFO_ITEMFLAG_RECSEARCHED = 0x20

  // the following is used to get query condition
  class GAQUERYSTRUCT extends AncientData
  {
    var stSimpQry = new GAQUERYSIMPSTRUCT;			// 192 bytes
  var nStartLibID:Int = _ ;		// int4 start lib id
  var nEndLibID:Int = _ ;		// int4 end lib id
  var tRmtAddTime:Int = _ ;		// distinguish different queries
  var nItemFlagA:Byte = _ ;			// GQIFA_XXX
  var nItemFlagB:Byte = _ ;			// GQIFB_xxx
  var nItemFlagC:Byte = _ ;			// GQIFC_xxx
  var nItemFlagD:Byte = _ ;			// GAIFD_XXX
  var nItemFlagE:Byte = _ ;			// GAIFE_XXX
  var nItemFlagF:Byte = _ ;			// GAIFF_XXX
  var nItemFlagG:Byte = _ ;			// GAIFG_XXX
  var bQryInfoCanBeFree:Byte = _ ;
    var nCommentLen:Int = _ ;		// length of comment.
  var pszComment_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;		// some comments.
  @Length(4)
  var bnRes_CommentPt:Array[Byte] = _ ;
    @Length(2)
    var stKeyRange:Array[GAKEYRANGESTRUCT] = _;		// 128 bytes, to here is 256+64 bytes
  var pstCandHead_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstCandHead_Data:GAQUERYCANDHEADSTRUCT = _ // for pstCandHead pointer ,struct:GAQUERYCANDHEADSTRUCT;		// pointer to candidate head, no substructure
  var pstCand_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstCand_Data:Array[GAQUERYCANDSTRUCT] = _ // for pstCand pointer ,struct:GAQUERYCANDSTRUCT;			// pointer to candidate list, no substructure
  var pstMIC_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstMIC_Data:Array[GAFISMICSTRUCT] = _ // for pstMIC pointer ,struct:GAFISMICSTRUCT;			// pointer to micstructure, contain many informations, has substructure
  var pstQryCond_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstQryCond_Data:Array[Byte] = _ // for pstQryCond pointer ,struct:void;		// points to GBASE_ITEMPKG structure
  var pstMISCond_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstMISCond_Data:Array[Byte] = _ // for pstMISCond pointer ,struct:void;		// mis query condition, no substructure
  var pstSvrList_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstSvrList_Data:Array[Byte] = _ // for pstSvrList pointer ,struct:void;		// server list, no substructure
  var pstTextSql_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstTextSql_Data:Array[Byte] = _ // for pstTextSql pointer ,struct:void;		// pointer to text sql statement, no substructure
  @Length(4*7)
  var bnRes_AllPt:Array[Byte] = _ ;
    // to here is 320+56+32=408 bytes
    var nQryCondLen:Int = _ ;		// qry condition length
  var nCandHeadLen:Int = _ ;	// candidate head len
  var nCandLen:Int = _ ;		// candidate length
  var nSvrListLen:Int = _ ;
    var nMISCondLen:Int = _ ;
    var nTextSqlLen:Int = _ ;
    var nMICCount:Int = _ ;		// # of mics
  // to here is 376+28+32 = 436 bytes
  var bCandHeadCanBeFree:Byte = _ ;
    var bCandCanBeFree:Byte = _ ;
    var bMICCanBeFree:Byte = _ ;
    var bQryCondCanBeFree:Byte = _ ;
    var bMISCondCanBeFree:Byte = _ ;
    var bSvrListCanBeFree:Byte = _ ;
    var bTextSqlCanBeFree:Byte = _ ;
    var bCommentCanBeFree:Byte = _ ;
    var nQryInfoLen:Int = _ ;		// qry info length.
  var nItemFlagH:Byte = _ ;		// GAIFH_XXX
  @Length(11)
  var bnRes3:Array[Byte] = _ ;			// reserved
  var nFifoQueDBID:Short = _ ;
    var nFifoQueTID:Short = _ ;
    @Length(SID_SIZE)
    var nFifoQueSID:String = _ ;
    @Length(8-SID_SIZE)
    var bnRes_FifoQueSID:Array[Byte] = _ ;
    var pstInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstInfo_Data:GAFIS_QUERYINFO = _ // for pstInfo pointer ,struct:GAFIS_QUERYINFO;			// query info.
  @Length(4)
  var bnRes_pstInfo:Array[Byte] = _ ;		//
  @Length(32)
  var szRmtQryKey:String = _ ;	// used by remote server to add heterogeneous systems
  } // GAQUERYSTRUCT;	// size of this structure is 512 bytes

  // nItemFlagA
  final val GAIFA_KEYID = 0x1
  final val GAIFA_USERNAME = 0x2
  final val GAIFA_QUERYTYPE = 0x4
  final val GAIFA_PRIORITY = 0x8
  final val GAIFA_HITPOSS = 0x10
  final val GAIFA_STATUS = 0x20
  final val GAIFA_FLAG = 0x40
  final val GAIFA_ISRMTQRY = 0x80


  // nItemFlagB
  final val GAIFB_STAGE = 0x1
  final val GAIFB_FLAGEX = 0x2
  final val GAIFB_QUERYID = 0x4
  final val GAIFB_SRCDBID = 0x8
  final val GAIFB_DESTDBID = 0x10
  final val GAIFB_SRCTABLEID = 0x20
  final val GAIFB_DESTTABLEID = 0x40
  final val GAIFB_TIMEUSED = 0x80

  // nItemFlagC
  final val GAIFC_MAXCANDNUM = 0x1
  final val GAIFC_CURCANDNUM = 0x2
  final val GAIFC_SUBMITTIME = 0x4
  final val GAIFC_FINISHTIME = 0x8
  final val GAIFC_STARTLIBID = 0x10
  final val GAIFC_ENDLIBID = 0x20
  final val GAIFC_RMTADDTIME = 0x40
  final val GAIFC_KEYRANGE = 0x80

  // nItemFlagD
  final val GAIFD_CANDHEAD = 0x1
  final val GAIFD_CAND = 0x2
  final val GAIFD_SRCMNT = 0x4
  final val GAIFD_SRCIMG = 0x8
  final val GAIFD_QRYCOND = 0x10
  final val GAIFD_MISCOND = 0x20
  final val GAIFD_SVRLIST = 0x40
  final val GAIFD_TEXTSQL = 0x80

  // nItemFlagE
  final val GAIFE_CHECKTIME = 0x1
  final val GAIFE_CHECKUSERNAME = 0x2
  final val GAIFE_RMTFLAG = 0x4
  final val GAIFE_VERIFYRESULT = 0x8
  final val GAIFE_RMTSTATE = 0x10
  final val GAIFE_COMMENT = 0x20
  final val GAIFE_FLAG3 = 0x40
  final val GAIFE_FLAG4 = 0x80

  // nItemFlagF
  final val GAIFF_RECHECKDATE = 0x1
  final val GAIFF_RECHECKERNAME = 0x2
  final val GAIFF_GROUPID = 0x4
  final val GAIFF_MISSTATE = 0x8
  final val GAIFF_USERUNITCODE = 0x10
  final val GAIFF_CHECKERUNITCODE = 0x20
  final val GAIFF_RECHECKERUNITCODE = 0x40
  final val GAIFF_IP = 0x80

  // nItemFlagG
  final val GAIFG_COMPUTERNAME = 0x1
  final val GAIFG_ISFIFOQUE = 0x2
  final val GAIFG_FIFOQUESID = 0x4
  final val GAIFG_FIFOQUEDBID = 0x8
  final val GAIFG_FIFOQUETID = 0x10
  final val GAIFG_SRCMULTIKEY = 0x20
  final val GAIFG_FLAG5 = 0x40
  final val GAIFG_FLAG6 = 0x80


  // GAQUERYSTRUCT::nItemFlagH
  final val GAIFH_FLAG7 = 0x1
  final val GAIFH_FLAG8 = 0x2

  final val GAQUERY_RETR_INVALIDVALUE = 255

  // we submit some simple condition to retrieve
  class GQUERYRETRSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;		// size of this structure
  @Length(44)
  var szKeyWild:String = _ ;	// wild key, 44 bytes
  @Length(16)
  var szUserNameWild:String = _ ;	// user name wild, to here is 64 bytes
  var nQueryType:Byte = _ ;
    var nPriorityLow:Byte = _ ;
    var nPriorityHigh:Byte = _ ;
    var nHitPossibility:Byte = _ ;
    var nStatus1:Byte = _ ;	// 255 is invalid value
  var nStatus2:Byte = _ ;	// 255 is invalid value
  var nStatus3:Byte = _ ;	// 255 is invalid value
  var nStatus4:Byte = _ ;	// 255 is invalid value
  var stSubmitTimeRange = new GATIMERANGE;			// 16 bytes
  var stFinishTimeRange = new GATIMERANGE;			// 16 bytes
  var nMaxToGet:Int = _ ;	// at most at this values of records
  var nItemFlag:Byte = _ ;		// indicate which item to use, GQUERYRETR_ITEMFLAG_XXX
  var nHitPossLow:Byte = _ ;
    var nHitPossHigh:Byte = _ ;
    var nRmtFlag1:Byte = _ ;	// 255 is invalid value
  var nRmtFlag2:Byte = _ ;	// 255 is invalid value
  var nRmtFlag3:Byte = _ ;	// 255 is invalid value
  var nRmtFlag4:Byte = _ ;	// 255 is invalid value
  var nRmtState:Int = _ ;	// 255 is invalid value
  @Length(8)
  var bnRes:Array[Byte] = _ ;		// bytes reserved
  var nItemFlagEx:Byte = _ ;	// indicate which item to be used, GQUERYRETR_ITEMFLAGEX_XXX
  } // GQUERYRETRSTRUCT;	// size is 128 bytes

  final val GQUERYRETR_ITEMFLAG_KEYWILD = 0x1
  final val GQUERYRETR_ITEMFLAG_USERNAME = 0x2
  final val GQUERYRETR_ITEMFLAG_SUBMITTIME = 0x4
  final val GQUERYRETR_ITEMFLAG_FINISHTIME = 0x8
  final val GQUERYRETR_ITEMFLAG_MAXGET = 0x10
  final val GQUERYRETR_ITEMFLAG_QUERYTYPE = 0x20
  final val GQUERYRETR_ITEMFLAG_PRIORITY = 0x40
  final val GQUERYRETR_ITEMFLAG_HITPOSS = 0x80

  final val GQUERYRETR_ITEMFLAGEX_RMTFLAG = 0x1
  final val GQUERYRETR_ITEMFLAGEX_RMTSTATE = 0x2

  final val GQUERYRETR_ITEMFLAGEX_GLOBALNOT = 0x80	// not all condition

  class GAQUERYMATCHERABILITY extends AncientData
  {
    var nLTSpeed:Int = _ ;	// how many ten card searched / second
  var nLLSpeed:Int = _ ;	// how many latent card search/second
  var nTLSpeed:Int = _ ;	// how many tl search /second
  var nTTSpeed:Int = _ ;	// how many tt search /second
  var nRating:Byte = _ ;		// nratiing(1-100)
  @Length(15)
  var bnRes:Array[Byte] = _ ;		// 15 bytes reserved
  } // GAQUERYMATCHERABILITY;	// size of this structure is 32 bytes

  class GASUBLIBRANGE extends AncientData
  {
    @Length(SID_SIZE)
    var nStartSID:String = _ ;
    @Length(8-SID_SIZE)
    var bnRes_SID1:Array[Byte] = _ ;
    @Length(SID_SIZE)
    var nEndSID:String = _ ;	// if nEndSID is zero then it means to the end of table
  @Length(8-SID_SIZE)
  var bnRes_SID2:Array[Byte] = _ ;
  } // GASUBLIBRANGE;	// size is 8+8

  class GASUBLIBPROPSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;			// size of this structure
  var tLastUpdateTime:Int = _ ;	// last update time(server time)
  var tLoc_LastUpdateTime:Int = _ ;	// last update time(local)
  var nType:Byte = _ ;				// same value as in GADBPROPSTRUCT
  @Length(3)
  var bnRes:Array[Byte] = _ ;			// reserved
  var stRangeStart = new GASUBLIBRANGE;		// range start(sid)
  var stRangeEnd = new GASUBLIBRANGE;			// range end(sid)(to here is 32 bytes)
  var nLoc_DBID:Short = _ ;		// dbid, dbid on local(matcher)
  var nLoc_TableID:Short = _ ;	// table id on local(matcher)
  var nServerDBID:Short = _ ;		// server dbid
  var nServerTableID:Short = _ ;	// server table id
  @Length(40)
  var bnRes2:Array[Byte] = _ ;			// reserved
  @Length(16)
  var guidServerUUID:String = _ ;	// server's uuid for paramatch use only
  @Length(16)
  var guidServerDBUUID:String = _ ;	// for paramatch use only, server db's uuid
  @Length(16)
  var guidLoc_DBUUID:String = _ ;		// uuid of this database, we internally use this value to distinguish different database
  } // GASUBLIBPROPSTRUCT;	// property of parallel match lib, size is 128 bytes


  class GAQUERYGETMATCHCOND_OLD extends AncientData
  {
    var cbSize:Int = _ ;	// size of this structure
  var nDestDBID:Short = _ ;		// which database to search
  var nDestTableID:Short = _ ;	// which table to be searched
  var stRangeStart = new GASUBLIBRANGE;
    var stRangeEnd = new GASUBLIBRANGE;
    var nSegmentID:Int = _ ;		// queryid, segment id this value is used identify different query
  var nSeqNo:Int = _ ;			// sequence no, this value is used to identify different query
  var nGetTime:Int = _ ;		// get match time(server time)
  var nGetTimeLocal:Int = _ ;	// get time (local, send to server)(to here is 40 bytes)
  @Length(16)
  var guidUUID:String = _ ;		// uuid of the matcher
  var bIsFifoQue:Byte = _ ;			// whether is fifo queue
  var nOption:Byte = _ ;			// Option
  var nFileID:Short = _ ;			// file id
  @Length(4)
  var bnRes_nRID:Array[Byte] = _ ;			// nrid, record id
  var stAbility = new GAQUERYMATCHERABILITY;	// ability, 32 bytes
  var nQueryDBID:Short = _ ;				// where the query come from
  var nQueryTableID:Short = _ ;			// where is the table id
  @Length(4)
  var bnRes0:Array[Byte] = _ ;
    @Length(SID_SIZE)
    var nSID:String = _ ;
    @Length(8-SID_SIZE)
    var bnRes_SID:Array[Byte] = _ ;
    @Length(16)
    var bnRes1:Array[Byte] = _ ;			// 28 bytes reserved
  } // GAQUERYGETMATCHCOND_OLD;	// size of this structure is 128 bytes

  // because a small bug the following structure not suitable aligned.
  class GAQUERYGETMATCHCOND extends AncientData
  {
    var cbSize:Int = _ ;	// size of this structure
  var bIsFifoQue:Byte = _ ;			// whether is fifo queue
  var nDBCnt:Byte = _ ;	// # of database count.
  var nOption:Byte = _ ;			// Option
  var nResultFlag:Byte = _ ;			// result flag. GAGETMATCH_RESFLAG_XXX

    var nQueryDBID:Short = _ ;				// where the query come from
  var nQueryTableID:Short = _ ;			// where is the table id
  @Length(4)
  var bnRes1:Array[Byte] = _ ;
    @Length(SID_SIZE)
    var nSID:String = _ ;
    @Length(2)
    var bnRes2:Array[Byte] = _ ;		// to here is 24 bytes long.
  var nMuID:Int = _ ;		// id of mu.
  var nRegisterTime:Int = _ ;	// register time.
  var nServerTime:Int = _ ;		// when query was get, this field record the server time.
  var nClientTime:Int = _ ;
    @Length(12)
    var bnRes3:Array[Byte] = _ ;			// reserved.

    @Length(20)
    var stDTID:Array[GADBIDSTRUCT] = _;	// size is 20*4=80 bytes long.
  } // GAQUERYGETMATCHCOND;	// size is 128 +4 bytes long

  //GAQUERYGETMATCHCOND::nResultFlag
  final val GAGETMATCH_RESFLAG_TOOMUCH_CAND = 0x1	// candidate too much.

  // error data
  final val GAGM_OPTION_ERRDATA = 0x1

  // following are priorities of query, don't change it any more
  final val QQRS_PRIORITY_LOWEST = 0
  final val QQRS_PRIORITY_LOW = 1
  final val QQRS_PRIORITY_NORMAL = 2
  final val QQRS_PRIORITY_ABOVENORMAL = 3
  final val QQRS_PRIORITY_HIGH = 4
  final val QQRS_PRIORITY_HIGHEST = 5
  final val QQRS_PRIORITY_REALTIME = 9	// real time need instant treating.

  final val QQRS_PRIORITY_LEVELCOUNT = 6	// number of priority


  final val GAFIS_QRYADD_OPT_KEEPSUBMITTIME = 0x1
  final val GAFIS_QRYADD_OPT_CHANGEMNTONLY = 0x2	// resubmit, keep old mnt, old query status must be WAITCENSOR.
  final val GAFIS_QRYADD_OPT_LIVESCANCARD = 0x4	// 活体查询


  //////////////////////candidate key filter.
  // we restrict number or percent of candidate keys appeared in candidate list
  // according to some pattern
  class GAQRYCAND_FILTER extends AncientData
  {
    var cbSize:Int = _ ;
    @Length(4)
    var bnRes:Array[Byte] = _ ;
    @Length(32)
    var szKeyWild:String = _ ;	// if a candidate satisfy this wild match, then candidate number
  // must obey following requirement.
  var nFlag:Byte = _ ;			// GAQRYCAND_FILTER_FLAG_XXX
  @Length(3)
  var bnRes1:Array[Byte] = _ ;
    var nBound:Short = _ ;		// bound(number of candidate)
  @Length(2)
  var bnRes10:Array[Byte] = _ ;		// reserved.
  // to here is 48 bytes long.
  @Length(16)
  var bnRes2:Array[Byte] = _ ;
    @Length(64)
    var bnResx:Array[Byte] = _ ;
  } // GAQRYCAND_FILTER;	// 128 bytes long.


  final val GAQRYCAND_FILTER_FLAG_NEG = 0x1	// if a candidate does not match szKeyWild, then the restriction will apply

  // the following filter for one query type only.
  class GAQRYCAND_MULTIFILTER extends AncientData
  {
    var cbSize:Int = _ ;
    @Length(4)
    var bnRes:Array[Byte] = _ ;
    var nFilterCount:Byte = _ ;
    var nFlag:Byte = _ ;			// GAQRYCAND_MFILTER_FLAG_XXXX
  var nQueryType:Byte = _ ;		// query type.
  @Length(5)
  var bnRes1:Array[Byte] = _ ;
    @Length(48)
    var bnRes2:Array[Byte] = _ ;
    @Length(64)
    var bnResx:Array[Byte] = _ ;
    // to here is 128 bytes long.
    @Length(1)
    var stFilter:Array[GAQRYCAND_FILTER] = _;	//
  } // GAQRYCAND_MULTIFILTER;	// 128*N bytes long. N>=2, at least 256 bytes long.

  // GAQRYCAND_MULTIFILTER::nFlag
  final val GAQRYCAND_MFILTER_FLAG_ALLOWETC = 0x1	// we have a # of filter's, but some record may do not
  // fit any filter, if this flag not set, then the record
  // will drop from candidate, else it will included in candidates.
  final val GAQRYCAND_MFILTER_FLAG_ABSOLUTE = 0x2 // nBound store absolute value of candidate count. all filter must have the same
  // type of value : either absolute or percent

  //////////////////TEXT SQL EXPANSION
  class GAFIS_TABLETEXTSQL extends AncientData
  {
    var cbSize:Int = 32 ;	//
  @Length(4)
  var bnRes:Array[Byte] = _ ;
    // to here is 8 bytes long.
    var nTextLength:Short = _ ;	// szText length(not including trailing zero.
  var nTextBufLength:Short = _ ;	// total szText length.
  var nTID:Short = _ ;			// table id(apply to which table).
  @Length(2)
  var bnRes2:Array[Byte] = _ ;
    // to here is 16 bytes long
    @Length(16)
    var szText:String = _ ;
  } // GAFIS_TABLETEXTSQL;	// minimal size is 32 bytes long. may larger according text size.


  class GAFIS_TEXTSQL extends AncientData
  {
    var cbSize:Int = _ ;
    var nMajorVersion:Short = _ ;
    var nMinorVersion:Short = _ ;
    // to here is 8 bytes long.
    var nSqlCount:Byte = _ ;		// # of different sql statements(conditions).
  var nFlag:Byte = _ ;			// TEXTSQL_FLAG_XXX
  @Length(6)
  var bnRes1:Array[Byte] = _ ;
    // to here is 16 bytes long.
    var ppstSql_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var ppstSql_Data:Array[GAFIS_TABLETEXTSQL] = _ // for ppstSql pointer ,struct:GAFIS_TABLETEXTSQL;
  @Length(4)
  var bnRes_Pt:Array[Byte] = _ ;
    @Length(8)
    var bnRes2:Array[Byte] = _ ;
  } // GAFIS_TEXTSQL;	// size is 32 bytes long.

  // GAFIS_TEXTSQL::nFlag
  final val TEXTSQL_FLAG_CASEIDCANBENULL = 0x1	// if a lat finger does not associate with
  // a case, and searching case for some
  // condition, we also include this latent finger


  final val QRY_SRCMULTI_MAXKEY = 32	// can only have this # of keyes.


  // 2008.04.12 TT查询自动认定的情况下，那些不自动认定的结果说明
  final val NOTTAUTOCHECK_GETMASK = 0xF0			// MASK
  final val NOTTAUTOCHECK_SETMASK = 0x0F
  final val NOTTAUTOCHECK_UNKNOWN = 0x00			// 未知原因
  final val NOTTAUTOCHECK_SCORE_TOO_SMALL = 0x10	// 分数太低（低于g_Lv.nTTAutoMergeCandScore)
  final val NOTTAUTOCHECK_ERROR_OCCURED = 0x20	// 发生了错误。
  final val NOTTAUTOCHECK_MEM_ALLOC_FAILED = 0x30	// 分配内存发生错误。
  final val NOTTAUTOCHECK_FILTERD = 0x40	// 不满足条码过滤条件
  final val NOTTAUTOCHECK_SRC_KEY_NOT_EXIST = 0x50	// SRC KEY 不在数据库中
  final val NOTTAUTOCHECK_NOT_SET = 0x60	// 不需要自动认定或者参数设置不对。


  /////////////// function declared in glocfh.h
}
