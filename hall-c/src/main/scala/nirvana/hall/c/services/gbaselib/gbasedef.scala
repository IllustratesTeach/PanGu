package nirvana.hall.c.services.gbaselib

import java.nio.ByteBuffer
import java.util.Calendar

import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-06
 */
object gbasedef {

  final val GA_TRUE = 1
  final val GA_FALSE = 0


  final val WCHARDIR = '/'
  /* it's a char */
  final val WSTRDIR = "/"

  /* it's a string */

  class GAFIS_UUIDStruct extends AncientData {
    var Data1: Int = _;
    // generation time
    var Data2: Short = _;
    // a random number
    var Data3: Short = _;
    // value returned by clock()
    var Data4: Short = _;
    // second random num, srand by value returned by clock
    @Length(6)
    var Data5: String = _; // using malloc memory to get another random char
  }

  // GAFIS_UUIDStruct;	// size is 16 bytes

  /* these name will appear in file AFISsrc */
  final val NAME_SERVERNAME = "DBServerName"
  final val NAME_SERVERPORT = "DBServerPort"
  final val NAME_MATCHSERVERPORT = "MatchServerPort"
  final val NAME_EXFSERVERPORT = "ExfServerPort"
  final val NAME_WATCHMATCHPORT = "WatchMatchPort"
  final val NAME_WATCHEXFPORT = "WatchExfPort"
  final val NAME_TXSERVERNAME = "TxServerName"
  final val NAME_TXSERVERPORT = "TxServerPort"
  final val NAME_RCSERVERNAME = "RcServerName"
  final val NAME_RCSERVERPORT = "RcServerPort"
  final val NAME_DECPRSVRNAME = "DecompressSvrName"
  final val NAME_DECPRSVRPORT = "DecompressSvrPort"
  final val NAME_TASKSERVERPORT = "TaskServerPort"

  //
  final val NAME_DBTOEXF = "DBToEXF"
  final val NAME_DBTOMATCH = "DBToMatch"

  /* default port number */
  final val PORTDEF_SERVER = 6798
  final val PORTDEF_RMTSERVER = 6811
  final val TXPORTDEF_SERVER = PORTDEF_RMTSERVER
  final val PORTDEF_MATCHER = 7012
  final val PORTDEF_MCASTSERVER = 7357
  final val PORTDEF_FRONTSERVER = 6715
  final val PORTDEF_EXFSERVER = 5769
  final val PORTDEF_TASKSERVER = 5076
  // task server port on windows os
  final val PORTDEF_TASKSERVER_LINUX = 5077
  //task server port on linux os
  final val PORTDEF_PXYSERVER = 6898
  // database proxy server, modification
  // some times we need run numina server and proxy server
  // same time and on same machine, so we change proxy server
  // port.
  final val PORTDEF_SVCMASTER = 6910 // service master.

  final val PORTDEF_FACEMATCHER = 6833

  final val PORTDEF_GFWEBSVR = 8383
  //GfWebSvr服务的端口号
  final val PORTDEF_DECPRSVR = 8585 //解压缩服务器的端口号



  /*
        typedef	struct
        {
        void	*pdata;	// points to  CALLBACKDATASTRUCT
        int		(*pfnCALLBACK)(int, char *, void *pdata);	/* used to set progress info */
        // always aligned at 8 bytes boundary
        char	sbuf[120];	// pdata=sbuf always
  } PROGRESSINFO_CALLBACK, *PPROGRESSINFO_CALLBACK;

  typedef	int	(*GAFIS_SHOWMESSAGEFUNCTION)(char *fmt, ...);
  typedef	int	(*GAFIS_INNERSHOWMSGFUNCTION)(int nlevel, char *fmt, va_list argptr);

  typedef	int	(*GAFIS_HOOKMSGSHOW)(uint4 nMask, char *fmt, ...);

  final val BARCODELEN = 32

  final val UNREFPARAMETER(a) = ((a) = (a))
  */

  // date time format, t[7:6]-year, t[5]-mon, t[4]-day, t[3]-hour, t[2]-minute, t[1:0]-millisec
  class GAFIS_TIME extends AncientData {
    private val calendar = Calendar.getInstance()
    setJavaSecs(calendar.get(Calendar.SECOND))
    var tMilliSec: Short = _
    def setJavaSecs(sec:Int): Unit ={
      val n = sec * 1000
      tMilliSec = switchShortEndian(n.toShort)
    }
    def convertAsJavaSecs(): Int ={
      //高地位转换之后，如果高位是1，toShort方法会转为负数，函数运行会失真。这里特意转为Int之后再做除以1000的运算
      ByteBuffer.allocate(4).putShort(2, switchShortEndian(tMilliSec)).getInt() / 1000
    }
    // millisecond.	[0, 999]
    var tMin: Byte = calendar.get(Calendar.MINUTE).toByte;
    // minute. [0, 59]
    var tHour: Byte = calendar.get(Calendar.HOUR_OF_DAY).toByte; // hour,   [0, 23]
  }

  /**
    * short高地位转换
    * @param short
    * @return
    */
  private def switchShortEndian(short: Short): Short = {
    (((short >>> 8) & 0xff) | (short << 8)).toShort
  }

  // GAFIS_TIME;	// size is 4 bytes long
  class GAFIS_DATE extends AncientData {
    private val calendar = Calendar.getInstance()
    setJavaYear(calendar.get(Calendar.YEAR))
    var tDay: Byte = calendar.get(Calendar.DAY_OF_MONTH).toByte;
    // day, [1, 31]
    var tMonth: Byte = calendar.get(Calendar.MONTH).toByte;
    // month, [0, 11]
    var tYear: Short = _
    def setJavaYear(year:Int): Unit ={
      tYear = switchShortEndian(year.toShort)
    }
    def convertAsJavaYear():Int ={
      switchShortEndian(tYear)
    }
  }

  // GAFIS_DATE;	// size is 4 bytes long.

  class GAFIS_DATETIME extends AncientData {
    var tTime = new GAFIS_TIME;
    var tDate = new GAFIS_DATE; // date
  }

  // GAFIS_DATETIME;	// gafis data time. size is 8 bytes long

  type AFISDateTime = GAFIS_DATETIME
  // can map to GAFIS_DATETIME structure
  type AFISDate = GAFIS_DATE
  // can map to GAFIS_DATE structure
  type AFISTime = GAFIS_TIME // can map to GAFIS_TIME structure

  /*
  typedef UCHAR	AFISDateTime[8];	// can map to GAFIS_DATETIME structure
  typedef	UCHAR	AFISDate[4];		// can map to GAFIS_DATE structure
  typedef	UCHAR	AFISTime[4];		// can map to GAFIS_TIME structure
  */

  final val LOGINFROM_LOCAL = 0
  final val LOGINFROM_REMOTE = 1

  /*
  final val GAFIS_CLEAR_STRUCT(cs) = memset(&cs, 0, sizeof(cs))
  final val CSTRUCT_CLEAR(cs) = memset(&(cs), 0, sizeof(cs))
  final val CSTRUCT_COPY(cdest, = csrc)	memcpy(&(cdest), &(csrc), sizeof(cdest))
  final val CARRAY_CLEAR(cs) = memset(cs, 0, sizeof(cs))
  final val CARRAY_COPY(cdest, = csrc)	memcpy(cdest, csrc, sizeof(cdest))

  /* set values */
  final val CHAR1COPY(dest, = src)	((dest) = (src))
  final val CHAR2COPY(dest, = src)	( *((int2 *)(dest)) = *((int2 *)(src)) )
  //#define	CHAR4COPY(dest, src)	( *((int4 *)(dest)) = *((int4 *)(src)) )
  final val CHAR4COPY(dest, = src)	{ \
  ((char *)(dest))[0] = ((char *)(src))[0]; \
  ((char *)(dest))[1] = ((char *)(src))[1]; \
  ((char *)(dest))[2] = ((char *)(src))[2]; \
  ((char *)(dest))[3] = ((char *)(src))[3]; \
  }

  final val CHAR6COPY(dest, = src)	memcpy(dest, src, 6)

  final val CHAR8COPY(dest, = src)	( *((ga_int8 *)(dest)) = *((ga_int8 *)(src)) )
  final val CHAR12COPY(dest, = src)	( CHAR8COPY(dest, src)); CHAR4COPY(dest+8, src+8)

  /* compare , c must be ==, != */
  final val CHAR1COMP(a, = c, b)	( (a) c (b) )
  final val CHAR2COMP(a, = c, b)	( *((int2 *)(a)) c *((int2 *)(b)) )
  final val CHAR4COMP(a, = c, b)	( *((int4 *)(a)) c *((int4 *)(b)) )
  final val CHAR8COMP(a, = c, b)	( *((ga_int8 *)(a)) c *((ga_int8 *)(b)) )

  // given a c structure return it items size and offset
  final val CSTRUCT_OFFSET(stname, = itemname) ((size_t)&(((stname *)0)->itemname))
  final val CSTRUCT_SIZE(stname, = itemname)	 (sizeof((stname *)0)->itemname)

  //#define	LOGTYPE_USERLOG			1	/* login and logout userlog.log */
  //#define	LOGTYPE_SYSCHANGE		2	/* not used syschang.log */
  //#define	LOGTYPE_USERMOD			3	/* modify parameter and user usermod.log */
  //#define	LOGTYPE_QUERYLOG		4	/* record searched for query querylog.log*/
  //#define	LOGTYPE_FINGERREP		5	/* which barcode's finger has been replaced fingerep.log*/
  //#define	LOGTYPE_SYSLOG			6	/* disk and db change syslog.log */
  //#define	LOGTYPE_ERRORLOG		7	/* error log errorlog.log */
  //#define	LOGTYPE_DBLOG			8	/* db modify dbchange.log */
  //#define	LOGTYPE_WORKINPUT		11	/* work input wkinput.log */
  //#define	LOGTYPE_WORKEDIT		12	/* work edit wkedit.log */
  //#define	LOGTYPE_WORKCHECK		13	/* work check wkcheck.log */
  //#define	LOGTYPE_QUALITYCHECK	14	/* quality check, may not be used qualichk.log */
  //#define	LOGTYPE_REINPUT			15	/* error barcode */
  //#define	LOGTYPE_RMTINPUT		16	/* record remote user input barcode */
  final val LOGTYPE_SERVERRUN = 1	/* record the starting process of server */
  //#define	LOGTYPE_TXSERVERRUN		21	/* tx server */
  //#define	LOGTYPE_RCSERVERRUN		22	/* data transmission server */
  //#define	LOGTYPE_RMTBARADD		23	/* remote barcode add */
  //#define	LOGTYPE_RMTBARDOWN		24	/* bar code down load */
  //
  */
  /* used to construct lock file */
  final val LOCKFILEID_SERVER = 1
  final val LOCKFILEID_MATCHER = 2
  final val LOCKFILEID_EXF = 3
  final val LOCKFILEID_TXSERVER = 4
  final val LOCKFILEID_RCSERVER = 5
  final val LOCKFILEID_PXYSERVER = 6
  // proxy server
  final val LOCKFILEID_SVCMASTER = 7
  // service master.
  final val LOCKFILEID_DECPRSVR = 8
  // decompress image server
  final val LOCKFILEID_PROCESSOR = 9
  // processor
  final val LOCKFILEID_TASKSERVER = 10

  // task server

  /*
  final val STRUCT_CLEAR(cs) = memset(cs, 0, sizeof(cs))
  */


  def GBASE_UTIL_ALIGN(a:Int, at:Int)= ((a + at - 1) / at) * at

  class GAKEYSTRUCT extends AncientData {
    @Length(32)
    var szKey: String = _;
  }

  // GAKEYSTRUCT;	// size is 32 bytes;

  final val TIMECYCLE_TYPE_UNKNOWN = 0
  final val TIMECYCLE_TYPE_BYHOUR = 1
  final val TIMECYCLE_TYPE_BYDAY = 2
  final val TIMECYCLE_TYPE_BYWEEK = 3
  final val TIMECYCLE_TYPE_BYMONTH = 4
  final val TIMECYCLE_TYPE_BYYEAR = 5
  final val TIMECYCLE_TYPE_BYCYCLE = 6

  // algorithm for judge whether reached.
  // if by cycle then check time passed since last action
  // else get current year, month, day, weekday, hour, minute
  // check for each type.
  class GBASE_TIMECYCLE extends AncientData {
    var cbSize: Int = _;
    var nCycleType: Byte = _;
    // TIMECYCLE_TYPE_XX
    var nWeekDay: Byte = _;
    // used when nCycleType==TIMECYCLE_TYPE_BYWEEK, [0-6]
    var nMonthDay: Byte = _;
    // used when nCycleType==TIMECYCLE_TYPE_BYMONTH, [0-30]
    var nYearMonth: Byte = _;
    // used when nCycleType==TIMECYCLE_TYPE_BYYEAR	[0-11];
    var nYearDay: Byte = _;
    // used when nCycleType==TIMECYCLE_TYPE_BYYEAR [0, 30];
    @Length(3)
    var bnRes: Array[Byte] = _;
    var nHour: Byte = _;
    // [0, 23]
    var nMinute: Byte = _;
    // [0, 59]
    var nCycleTime: Short = _; // in minutes, used when nCycleType==TIMECYCLE_TYPE_BYCYCLE
  }

  // GBASE_TIMECYCLE;	// time cycle structure size 16, used by update and backup

  class GATIMERANGE extends AncientData {
    var tStartTime = new AFISDateTime;
    var tEndTime = new AFISDateTime;
  }

  // GATIMERANGE;

  class GBASE_INTTIMERANGE extends AncientData {
    var tStartTime: Int = _;
    var tEndTime: Int = _;
  }

  // GBASE_INTTIMERANGE;	// size is 8.

  // pointer
  class GBASE_PT extends AncientData {
    var pData: Byte = _;
    // pointer
    @Length(4)
    var bnRes_Pointer: Array[Byte] = _;
  }

  // GBASE_PT;	// size of this structure is 8

  class GBASE_APPVERSION extends AncientData {
    var nMajor: Short = _;
    var nMinor: Short = _;
    var nSubMinor: Short = _;
    var nBuild: Short = _;
  }

  // GBASE_APPVERSION;	// version.


  class GBASE_CALLTRACEENT extends AncientData {
    @Length(12)
    var szFN: String = _;
    // file name. only a small part.(12 chars).
    var nLineNo: Int = _; // line number.
  }

  // GBASE_CALLTRACEENT;	// call trace entry, 16 bytes long.

  class GBASE_CALLTRACEHEAD extends AncientData {
    var pstStack_Ptr: Int = _
    //using 4 byte as pointer
    @IgnoreTransfer
    var pstStack_Data: Array[GBASE_CALLTRACEENT] = _;
    // stack trace. 8*16=128 bytes long.
    @Length(4)
    var bnRes_Pt: Array[Byte] = _;
    var nTraceCnt: Int = _;
    // how many entry has been used.
    var nTraceBufCnt: Int = _;
    var nFailCnt: Int = _;
    // if we push stack failed, then pop stack will minus this value.
    @Length(4)
    var bnRes: Array[Byte] = _;
  }

  // GBASE_CALLTRACEHEAD;	// 24 bytes long.

}
