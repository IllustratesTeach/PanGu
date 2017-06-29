package nirvana.hall.c.services.gbaselib

import nirvana.hall.c.annotations.Length
import nirvana.hall.c.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-07
 */
object gafiserr {

  final val ERROR_OCCURED_AT_CLIENT = -7932


  //*--------------下面是原来的代码----------------------------
  final val GAFISERR_NOERROR = 0
  final val GAFISERR_FILE_OPEN = 1
  final val GAFISERR_FILE_LOCK = 2
  final val GAFISERR_FILE_READ = 3
  final val GAFISERR_FILE_WRITE = 4
  final val GAFISERR_FILE_SEEK = 5
  final val GAFISERR_FILE_CLOSE = 6
  final val GAFISERR_FILE_CREATE = 7
  final val GAFISERR_FILE_UNLOCK = 8
  final val GAFISERR_FILE_MAP = 9
  final val GAFISERR_FILE_STAT = 10
  final val GAFISERR_FILE_OTHEROP = 11
  final val GAFISERR_FILE_COPY = 12
  final val GAFISERR_FILE_CHMOD = 13
  final val GAFISERR_FILE_RENAME = 14
  final val GAFISERR_FILE_CHSIZE = 15
  final val GAFISERR_FILE_NOSPACE = 16
  final val GAFISERR_FILE_DEL = 17
  final val GAFISERR_FILE_SIZETOOSHORT = 18
  final val GAFISERR_FILE_SIZETOOLONG = 19

  final val GAFISERR_MEM_ALLOC = 20
  final val GAFISERR_MEM_FREE = 21
  final val GAFISERR_MEM_OUTOF = 22

  final val GAFISERR_FILE_NOTEXIST = 23
  final val GAFISERR_FILE_EXIST = 24
  final val GAFISERR_FILE_BADPOS = 25
  final val GAFISERR_FILE_SYNC = 26
  final val GAFISERR_FILE_FINDEMPTYPATH = 27
  final val GAFISERR_FILE_FINDCOPYNAME = 28
  final val GAFISERR_FILE_CHDIR = 29

  final val GAFISERR_BAR_INVALID = 30
  final val GAFISERR_BAR_TOOLONG = 31
  final val GAFISERR_BAR_TOOSHORT = 32
  final val GAFISERR_BAR_EXIST = 33
  final val GAFISERR_BAR_LENNOTMATCH = 34
  final val GAFISERR_BAR_NOTEXIST = 35
  final val GAFISERR_BAR_NEEDMOREBUF = 36

  final val GAFISERR_TENSTR_INVALID = 40
  final val GAFISERR_TENSTR_TOOLONG = 41
  final val GAFISERR_TENSTR_TOOSHORT = 42

  final val GAFISERR_PARAMETER_INVALID = 50
  final val GAFISERR_PARAMETER_ISNULL = 51
  final val GAFISERR_PARAMETER_NOENTRY = 52

  final val GAFISERR_MISC_NOEFFECT = 60

  final val GAFISERR_SOCK_OPEN = 71
  final val GAFISERR_SOCK_CLOSE = 72
  final val GAFISERR_SOCK_SEND = 73
  final val GAFISERR_SOCK_RECV = 74
  final val GAFISERR_SOCK_SELECT = 75
  final val GAFISERR_SOCK_BIND = 76
  final val GAFISERR_SOCK_LISTEN = 77
  final val GAFISERR_SOCK_ACCEPT = 78
  final val GAFISERR_SOCK_INIT = 79
  final val GAFISERR_SOCK_SOCKET = 80
  final val GAFISERR_SOCK_GETHOSTBYNAME = 81
  final val GAFISERR_SOCK_CONNECT = 82
  final val GAFISERR_SOCK_UNKNOWN = 83
  final val GAFISERR_SOCK_NAME2IP = 84
  final val GAFISERR_SOCK_SENDPARAM = 85
  final val GAFISERR_SOCK_RECVPARAM = 86
  final val GAFISERR_SOCK_WAITPARAM = 87

  final val GAFISERR_LIB_INVALIDMNT = 89

  final val GAFISERR_SYS_INVALIDFORMAT = 100
  final val GAFISERR_SYS_FATAL = 101
  final val GAFISERR_SYS_BUSY = 102
  final val GAFISERR_SYS_UNKNOWN = 103
  final val GAFISERR_SYS_OPCODE = 104
  final val GAFISERR_SYS_NOUSER = 105
  final val GAFISERR_SYS_MUCHANGED = 106		/* matchunit changed */
  final val GAFISERR_SYS_NOTSUPPPARAMATCH = 107		/* paramatch not support */
  final val GAFISERR_SYS_ABORTBYCLIENT = 108
  final val GAFISERR_SYS_REACHLIMIT = 109
  final val GAFISERR_SYS_LOWERSOURCE = 110
  final val GAFISERR_SYS_NOLICENSE = 111
  final val GAFISERR_SYS_SVRWILLSTOP = 112
  final val GAFISERR_SYS_SIZE = 113
  final val GAFISERR_SYS_NOTSUPPORT = 114
  final val GAFISERR_SYS_ALREADYRUN = 115
  final val GAFISERR_SYS_NOCALLBACK = 116
  final val GAFISERR_SYS_NOENTRY = 117	// no entry
  final val GAFISERR_SYS_TERMBYCLIENT = 118
  final val GAFISERR_SYS_CLIENTERR = 119
  final val GAFISERR_SYS_NOTEXIST = 120
  final val GAFISERR_SYS_UNFINISHED = 121
  final val GAFISERR_SYS_BADQUEUE = 122
  final val GAFISERR_SYS_NOTAPPLICABLE = 123
  final val GAFISERR_SYS_LENGTH = 124
  final val GAFISERR_SYS_IMGSIZE = 125
  final val GAFISERR_SYS_BADLICENSE = 126
  final val GAFISERR_SYS_GETCOMPUTERNAME = 127
  final val GAFISERR_SYS_BADFORMAT = 128
  final val GAFISERR_SYS_PINYINNOTEXIST = 129
  final val GAFISERR_SYS_SVRNOTRUN = 130
  final val GAFISERR_SYS_BADSTATE = 131
  final val GAFISERR_SYS_EXIST = 132
  final val GAFISERR_SYS_UNREACHCODE = 133
  final val GAFISERR_SYS_SETLOCALE = 134
  final val GAFISERR_SYS_INVALIDDESIGN = 135
  final val GAFISERR_SYS_FILEVERNOTMATCH = 136
  final val GAFISERR_SYS_NOTMULTIBLOCKSIZE = 137
  final val GAFISERR_SYS_NOADMINLIB = 138
  final val GAFISERR_SYS_DUPADMINLIB = 139
  final val GAFISERR_SYS_BADYMD = 140	// bad year, or month or day.
  final val GAFISERR_SYS_BADTIMEVALUE = 141
  final val GAFISERR_SYS_GETCWD = 142
  final val GAFISERR_SYS_LOADDLLERROR = 143	// load dynamic link library error
  final val GAFISERR_SYS_SEQNOINITED = 144
  final val GAFISERR_SYS_NOTIMPLEMENTED = 145	// the function has not been implemented
  final val GAFISERR_SYS_NOENTRYINDLL = 146	// no given entry in DLL(no given export function).
  final val GAFISERR_SYS_CLOSEDLLERROR = 147	// close dll error.

  final val GAFISERR_DISK_GETINFO = 201
  final val GAFISERR_DISK_NOSPACE = 202

  final val GAFISERR_BASE_NOVALIDCOLLID = 250	// no valid collate id
  final val GAFISERR_BASE_BADCOLLSTRING = 251	// bad collate type string
  final val GAFISERR_BASE_NOACTIVELICENSE = 252	// no active license
  final val GAFISERR_BASE_OPENREGKEYERR = 253	// open registry key
  final val GAFISERR_BASE_QUERYREGVALERR = 254	// query registry value
  final val GAFISERR_BASE_CRTREGKEYERR = 255	// create registry key.
  final val GAFISERR_BASE_SETREGVALERR = 256	// set registry value
  final val GAFISERR_BASE_PARAMGETINTVALUE = 257	// parameter get int value
  final val GAFISERR_BASE_PARAMGETBOOLVALUE = 258	// parameter get bool value
  final val GAFISERR_BASE_CRTFILEMAPPING = 259	// create file mapping
  final val GAFISERR_BASE_MAPVIEWOFFILE = 260	// map view of file
  final val GAFISERR_BASE_OPENFILEMAPPING = 261	// open file mapping
  final val GAFISERR_BASE_SOCKNTOA = 262	// sock ntoa
  final val GAFISERR_BASE_CREATEMUTEX = 263	// create mutex
  final val GAFISERR_BASE_GETPASS = 264	// get pass
  final val GAFISERR_BASE_GETLOGONUSERNAME = 265	// get logon user name
  final val GAFISERR_BASE_GETEFFECTIVEUNAME = 266	// get effective user name
  final val GAFISERR_BASE_GETPWNAME = 267	// get password name
  final val GAFISERR_BASE_SETUID = 268	// set uid
  final val GAFISERR_BASE_PLATFORMERROR = 269	// platform does not suitable for database run
  final val GAFISERR_BASE_GETKERNINFO = 270	// ibm get kern info
  final val GAFISERR_BASE_STACKGETDEEPTOP = 271	// stack array get deep top : bad index
  final val GAFISERR_BASE_MEMSTREAMREACHEND = 272	// memory stream reach end
  final val GAFISERR_BASE_GETFILEVERSIZE = 273	// get file version info size
  final val GAFISERR_BASE_GETFILEVERINFO = 274	// get file version info
  final val GAFISERR_BASE_FILEVERQUERYVALUE = 275	// file version query value
  final val GAFISERR_BASE_OPENPROCESS = 276	// open process
  final val GAFISERR_BASE_CREATETHREAD = 277	// create thread
  final val GAFISERR_BASE_PTHREADATTRINIT = 278	// pthread attr init
  final val GAFISERR_BASE_PTHREADSETSTACK = 279	// pthread set stack size
  final val GAFISERR_BASE_PTHREADCREATE = 280	// pthread create
  final val GAFISERR_BASE_PTHREADMUTEXINIT = 281	// pthread mutex init
  final val GAFISERR_BASE_PTHREADMUTEXLOCK = 282	// pthread mutex lock
  final val GAFISERR_BASE_PTHREADMUTEXTRYLOCK = 283	// pthread mutex try lock
  final val GAFISERR_BASE_PTHREADMUTEXUNLOCK = 284	// pthread mutex unlock
  final val GAFISERR_BASE_PTHREADMUTEXDESTROY = 285	// pthread mutex destroy
  final val GAFISERR_BASE_EVENTCREATE = 286	// create event
  final val GAFISERR_BASE_EVENTOPEN = 287	// open event
  final val GAFISERR_BASE_EVENTSET = 288	// set event
  final val GAFISERR_BASE_EVENTWAIT = 289	// wait event
  final val GAFISERR_BASE_PTHREADCONDWAIT = 290	// pthread cond wait
  final val GAFISERR_BASE_SETENDOFFILE = 291	// set end of file
  final val GAFISERR_BASE_FILEPKGITEMTOOLARGE = 292	// file pkg item too large
  final val GAFISERR_BASE_FILEPKGALIGNERROR = 293	// file pkg align
  final val GAFISERR_BASE_FAILTOCRTTEMPFILE = 294	// failed to create temp file
  final val GAFISERR_BASE_NOBFBADFIRSTBKID = 295	// bad first bkid in nobf
  final val GAFISERR_BASE_TEMPNAME = 296	// temp file name
  final val GAFISERR_BASE_FILEBADOFFSET = 297	// offset invalid
  final val GAFISERR_BASE_BADBERRLOGHEAD = 298	// bad berrorlog head
  final val GAFISERR_BASE_BADBMPSIGNATURE = 299	// bad bmp file signature
  final val GAFISERR_BASE_BADUDPPACKET = 300	// bad udp packet
  final val GAFISERR_BASE_BADBARRULESIG = 301	// bad key conversion rule file signature
  final val GAFISERR_BASE_BADNOBFILEHEAD = 302	// bad NOBF file head
  final val GAFISERR_BASE_FILETOOLONG = 303	// file size too long
  final val GAFISERR_BASE_OFFSETEXCEEDFILEEND = 304	// offset exceed file end
  final val GAFISERR_BASE_TXTHOOKNOTEXIST = 305	// txt hook does not exist with given hook id
  final val GAFISERR_BASE_INITPARAMERROR = 306	// parameter in afisinit.ini has an invalid value.


  // parameter invalid.
  final val GAFISERR_PARAM_FILELISTINDIR = 401
  final val GAFISERR_PARAM_NOTPATH = 402
  final val GAFISERR_PARAM_COPYUPDATE = 403
  final val GAFISERR_PARAM_COPYREAD = 404
  final val GAFISERR_PARAM_WRITEBIN = 405
  final val GAFISERR_PARAM_READBIN = 406
  final val GAFISERR_PARAM_SAVEDATASBMP = 407
  final val GAFISERR_PARAM_LOADBMP = 408
  final val GAFISERR_PARAM_TIMEWAIT = 409
  final val GAFISERR_PARAM_SEMCREATE = 410
  final val GAFISERR_PARAM_BADLSNFILEHEAD = 411
  final val GAFISERR_PARAM_CREATEMAINPATH = 412
  final val GAFISERR_PARAM_VALUENOTBOOL = 413	// value must be 0 or 1.
  final val GAFISERR_PARAM_PAINSERT = 414
  final val GAFISERR_PARAM_PADEL = 415
  final val GAFISERR_PARAM_PACOPY = 416
  final val GAFISERR_PARAM_PAGET = 417
  final val GAFISERR_PARAM_BADTIMECYCLETYPE = 418
  final val GAFISERR_PARAM_TIMEWAITHANDLE = 419
  final val GAFISERR_PARAM_EXPECTMORECHAR = 420
  final val GAFISERR_PARAM_BADHZ = 421
  final val GAFISERR_PARAM_HALFHZ = 422
  final val GAFISERR_PARAM_PKGEXIMBADOFFSET = 423
  final val GAFISERR_PARAM_PKGEXITEMBADOFFSET = 424
  final val GAFISERR_PARAM_PKGBADHEAD = 425
  final val GAFISERR_PARAM_PKGBADITEM = 426
  final val GAFISERR_PARAM_PKGNEEDDATA = 427
  final val GAFISERR_PARAM_PKGNEEDDATA2 = 428
  final val GAFISERR_PARAM_PKGNEEDDATA3 = 429
  final val GAFISERR_PARAM_BADBLOCKSIZE = 430
  final val GAFISERR_PARAM_FILESIZENOTMATCH = 431
  final val GAFISERR_PARAM_TXTHOOKBADMASK = 432
  final val GAFISERR_PARAM_TXTHOOKBADFN = 433
  final val GAFISERR_PARAM_MEMDELANDMOVE = 434
  final val GAFISERR_PARAM_MEMINSERT = 435

  final val GAFISERR_STR_NEEDMOREBUF = 501

  final val GAFISERR_DIR_NOTEMPTY = 900

  final val GAFISERR_PINYIN_NOTEXIST = 930
  final val GAFISERR_PINYIN_BADGLOBPT = 931
  final val GAFISERR_PINYIN_BADHZCODE = 932
  final val GAFISERR_PINYIN_NOTINITED = 933
  final val GAFISERR_PINYIN_NOTHZ = 934
  final val GAFISERR_PINYIN_STR2PYPARAM = 935
  final val GAFISERR_PINYIN_STR2PYTOOLONG = 936

  final val GAFISERR_FILENAME_MAINPATH = 1000
  final val GAFISERR_FILENAME_TOOLONG = 1001

  final val GAFISERR_USER_NOACCOUNT = 1100
  final val GAFISERR_USER_NORIGHT = 1101
  final val GAFISERR_USER_PASSERR = 1102
  final val GAFISERR_USER_NOLOGIN = 1103
  final val GAFISERR_USER_EXIST = 1104
  final val GAFISERR_USER_PASSTOLONG = 1105
  final val GAFISERR_USER_NAMETOLONG = 1106
  final val GAFISERR_USER_DISABLED = 1107
  final val GAFISERR_USER_IPNOTMATCH = 1108
  final val GAFISERR_USER_LISTEMPTY = 1109
  final val GAFISERR_USER_ISGROUP = 1110
  final val GAFISERR_USER_DISABLELOCLOGIN = 1111	// 禁止不通过通讯服务器登录
  final val GAFISERR_USER_DISABLETXLOGIN = 1112	// 禁止通过通讯服务器登录

  // ipc error coce.
  final val GAFISERR_IPC_WAITFAILED = 1200	// wait failed.
  final val GAFISERR_IPC_CREATEMUTEX = 1201	// create mutex failed.
  final val GAFISERR_IPC_OPENMUTEX = 1202	// open mutex failed.
  final val GAFISERR_IPC_RELEASEMUTEX = 1203	// release mutex failed.
  final val GAFISERR_IPC_CREATESEMPHORE = 1204	// create semaphore failed.
  final val GAFISERR_IPC_OPENSEMPHORE = 1205	// open semaphore failed.
  final val GAFISERR_IPC_OPENSEMPPARAM = 1206	// open semaphore parameter error.
  final val GAFISERR_IPC_RELEASESEMPHORE = 1207	// release semaphore error.
  final val GAFISERR_IPC_SEMRELEASEPARAM = 1208	// release semaphore parameter error.

  final val GAFISERR_QUERY_QUERYIDTOLARGE = 2001
  final val GAFISERR_QUERY_NOTMATCH = 2002
  final val GAFISERR_QUERY_NOTFINISHED = 2003
  final val GAFISERR_QUERY_NOTEXIST = 2004
  final val GAFISERR_QUERY_WORKING = 2005
  final val GAFISERR_QUERY_EXIST = 2006
  final val GAFISERR_QUERY_NOTSAME = 2007
  final val GAFISERR_QUERY_TOOMANY = 2008
  final val GAFISERR_QUERY_REJECTED = 2009
  final val GAFISERR_QUERY_INIT = 2010
  final val GAFISERR_QUERY_TOOMUCHCOMMENT = 2011	// too much comment and can not add any more
  final val GAFISERR_QUERY_CONDNOGIVENITEM = 2012	// no item with given name in query condition

  final val GAFISERR_LOG_LOGTYPE = 2500

  final val GAFISERR_DB_NOENTRY = 2600
  final val GAFISERR_DB_REACHLIBLIMIT = 2601
  final val GAFISERR_DB_NOMNT = 2602
  final val GAFISERR_DB_EXIST = 2603
  final val GAFISERR_DB_BADPROP = 2604
  final val GAFISERR_DB_BADQUEUE = 2605
  final val GAFISERR_DB_RECEXIST = 2606
  final val GAFISERR_DB_NOTEXIST = 2607
  final val GAFISERR_DB_QUECORRUPT = 2608
  final val GAFISERR_DB_NOKEYCOL = 2609
  final val GAFISERR_DB_NOCTWITHGIVENCTID = 2610
  final val GAFISERR_DB_NOTABLEWITHGIVENTID = 2611
  final val GAFISERR_DB_INDEXNOTSUPPORT = 2612
  final val GAFISERR_DB_TABLEIDCANNOTBEZERO = 2613
  final val GAFISERR_DB_NOTRECORDFORGIVENSID = 2614
  final val GAFISERR_DB_RECORDINRESERVESTATE = 2615
  final val GAFISERR_DB_INVALIDBKID = 2616
  final val GAFISERR_DB_INVALIDBMID = 2617
  // following error code added on feb. 25, 2005
  final val GAFISERR_DB_INVALIDFILEHEAD = 2618
  final val GAFISERR_DB_INVALIDSRID = 2619
  final val GAFISERR_DB_ROWSIZENOTMATCH = 2620
  final val GAFISERR_DB_INVALIDFILEID = 2621
  final val GAFISERR_DB_FILECOUNTNOTMATCH = 2622
  final val GAFISERR_DB_TOKENTOOLONG = 2623
  final val GAFISERR_DB_DESTBUFTOOSHORT = 2624
  final val GAFISERR_DB_TAGNOTFOUND = 2625
  final val GAFISERR_DB_TAGTYPENOTMATCH = 2626
  final val GAFISERR_DB_FQROWHASBEENDEL = 2627
  final val GAFISERR_DB_FQROWPREVNOTFIRST = 2628
  final val GAFISERR_DB_FQROWNEXTNOTLAST = 2629
  final val GAFISERR_DB_IDBTLOSTAFTERADD = 2630
  final val GAFISERR_DB_CACHEDATAPTERROR = 2631
  final val GAFISERR_DB_ADDTOCACHEERROR = 2632
  final val GAFISERR_DB_ITEMSIZENOTMATCH = 2633
  final val GAFISERR_DB_ROLENOTSET = 2634
  final val GAFISERR_DB_STOPRUN = 2635
  final val GAFISERR_DB_MAINPATHTOOLONG = 2636
  final val GAFISERR_DB_TOOMUCHBKPNAME = 2637
  final val GAFISERR_DB_TOOMUCHDBID = 2638
  final val GAFISERR_DB_COLNOTEXIST = 2639
  final val GAFISERR_DB_COLEXIST = 2640
  final val GAFISERR_DB_DUPCOLUUID = 2641
  final val GAFISERR_DB_DUPCOLNAME = 2642
  final val GAFISERR_DB_COLLENISZERO = 2643
  final val GAFISERR_DB_PATHHEADNOTMATCH = 2644
  final val GAFISERR_DB_PATHTAILNOTMATCH = 2645
  final val GAFISERR_DB_BADSHOBJSTATUS = 2646
  final val GAFISERR_DB_INVALIDBKPT = 2647
  final val GAFISERR_DB_COLNOTSET = 2648
  final val GAFISERR_DB_MAPPERBUFNOTENOUGH = 2649
  final val GAFISERR_DB_BADPTTYPE = 2650
  final val GAFISERR_DB_ROWISNOTUSED = 2651
  final val GAFISERR_DB_KEYTOOLONG = 2652
  final val GAFISERR_DB_SERIALROWBADTYPE = 2653
  final val GAFISERR_DB_COLNOTMATCH = 2654
  final val GAFISERR_DB_NEEDLENFIELD = 2655
  final val GAFISERR_DB_SAVESEQBUTHASKEY = 2656
  final val GAFISERR_DB_KEYLENNOTMATCH = 2657
  final val GAFISERR_DB_KEYNOTMATCH = 2658
  final val GAFISERR_DB_SIDNOTMATCH = 2659
  final val GAFISERR_DB_NEEDKEY = 2660
  final val GAFISERR_DB_NOGROUPCOL = 2661
  final val GAFISERR_DB_VALUELENISZERO = 2662
  final val GAFISERR_DB_BUFNOTINITED = 2663
  final val GAFISERR_DB_NOENOUGHSRCDATA = 2664
  final val GAFISERR_DB_NEEDLOB = 2665
  final val GAFISERR_DB_NEEDMORELEAFENT = 2666
  final val GAFISERR_DB_GETAUTOKEYERROR = 2667
  final val GAFISERR_DB_TOOMUCHENT = 2668
  final val GAFISERR_DB_CTCOUNTNOTMATCH = 2669
  final val GAFISERR_DB_CTNOTSAME = 2670
  final val GAFISERR_DB_FILECNTNOTMATCH = 2671
  final val GAFISERR_DB_BADFILEID = 2672
  final val GAFISERR_DB_NOXACT = 2673
  final val GAFISERR_DB_BADXLOGFILEHEAD = 2674
  final val GAFISERR_DB_DUPCHKPTFLAG = 2675
  final val GAFISERR_DB_METSTARTCKPTTWICE = 2676
  final val GAFISERR_DB_HASACTIVEXACT = 2677
  final val GAFISERR_DB_BADBKSIZE = 2678
  final val GAFISERR_DB_ROWSIZEISZERO = 2679
  final val GAFISERR_DB_TABLENOKEY = 2680
  final val GAFISERR_DB_NOTAPPLICABLE = 2681
  final val GAFISERR_DB_ISALOBPTNOTUSED = 2682	// GADB_ISAROWSCHEMA's stLobPt is not used.
  final val GAFISERR_DB_BADDBTYPE = 2683
  final val GAFISERR_DB_COLISNULL = 2684
  final val GAFISERR_DB_NOCOLFORUUID = 2685
  final val GAFISERR_DB_SIDTOOLARGE = 2686
  final val GAFISERR_DB_XACTNOSPACE = 2687
  final val GAFISERR_DB_BADTABLETYPE = 2688

  final val GAFISERR_CHECK_LENNOTEQ = 3000
  final val GAFISERR_CHECK_BADFINGERNUM = 3001

  // error for numina.
  final val GAFISERR_NM_ALLOCBLOCKFATAL = 3100	// bmf alloc block fatal error
  final val GAFISERR_NM_INSUFFICIENTCOLLATE = 3101	// collate need more parameters
  final val GAFISERR_NM_INVALIDCOLLATESTR = 3102	// invalid collate parameters
  final val GAFISERR_NM_NONEXISTBKSID = 3103	// sta invalid bksid
  final val GAFISERR_NM_TRCIDXFILETOOLARGE = 3104	// trace index file too large
  final val GAFISERR_NM_TRCMODLOGFILETOOLARGE = 3105	// trace modlog file too large
  final val GAFISERR_NM_CRTBMFINVALIDPARAM = 3106	// bmf create file : invalid parameter
  final val GAFISERR_NM_CRTBMFINVALIDBKSIZE = 3107	// bmf create file : invalid bksize or head size
  final val GAFISERR_NM_CTINITOBJINVALIDPARAM = 3108	// child table initobject invalid param
  final val GAFISERR_NM_CTCRTFILEINVALIDPARAM = 3109	// child table create file invalid param
  final val GAFISERR_NM_BADDTCLASSTYPE = 3110	// bad data type class
  final val GAFISERR_NM_NONLOBCANNOTHOLDLOB = 3111	// non-lob column can not hold lob column
  final val GAFISERR_NM_NOTENOUGHTARGETBUF = 3112	// not enough target buffer
  final val GAFISERR_NM_FILEHEADMAGICERROR = 3113	// file head magic signature
  final val GAFISERR_NM_FILEHEADVERSION = 3114	// file head version not match
  final val GAFISERR_NM_DBWITHSAMEUUID = 3115	// two databases with same uuid
  final val GAFISERR_NM_INVALIDROLE = 3116	// invalid role
  final val GAFISERR_NM_NOINDEXWITHGIVENNAME = 3117	// no index with given name
  final val GAFISERR_NM_NOCOLUMNINSELECT = 3118	// no column specified in select statement
  final val GAFISERR_NM_DUPBULKREADOPTERR = 3119	// dup bulk read option error
  final val GAFISERR_NM_DUPBULKSAVEOPTERR = 3120	// dup bulk save option error
  final val GAFISERR_NM_GETALLBUTVALNOTZERO = 3121	// get all column but some parameter not zero
  final val GAFISERR_NM_NAMEGIVENINVALIDPARAM = 3122	// get given column but some parameter invalid
  final val GAFISERR_NM_PREPAREDINVALIDPARAM = 3123	// get prepared column but some parameter invalid
  final val GAFISERR_NM_INVALIDCOLFMT = 3124	// get column invalid column format
  final val GAFISERR_NM_GETRESPARAM = 3125	// get res invalid parameter
  final val GAFISERR_NM_SIDSTARTTOOLARGE = 3126	// sid range's start sid larger then endsid
  final val GAFISERR_NM_SAVERESDATANULL = 3127	// save selres but data buffer is null
  final val GAFISERR_NM_SAVERESPOSERR = 3128	// save selres but pos parameter invalid
  final val GAFISERR_NM_SAVERESSAVEMODEERR = 3129	// save selres but save mode invalid
  final val GAFISERR_NM_TABLENOKEY = 3130	// table does not have key column
  final val GAFISERR_NM_CHKEYTARGETEXIST = 3131	// change key but new key already exists
  final val GAFISERR_NM_CHKEYOLDNOTEXIST = 3132	// change key but old key not exists
  final val GAFISERR_NM_NMUSERFILETOOSHORT = 3133	// nm user file too short
  final val GAFISERR_NM_NMUSERFILESIZENOTMATCH = 3134	// nm user file size not match
  final val GAFISERR_NM_VMEXPTYPENOTMATCH = 3140	// vm expression type not match
  final val GAFISERR_NM_VMBETWEENSTATEMENT = 3141	// vm between statement error
  final val GAFISERR_NM_NEEDTABLEPARAM = 3142	// need load lob but table parameter not given
  final val GAFISERR_NM_RESCOLCANNOTBESET = 3143	// reserved column can not be set
  final val GAFISERR_NM_NULLCOLNOTPERMIT = 3144	// null column not permitted
  final val GAFISERR_NM_DATAEXCEEDCOLWIDTH = 3145	// data exceed column width
  final val GAFISERR_NM_NOKEYDEL = 3146	// del by key but table no key
  final val GAFISERR_NM_NOCTINROW = 3147	// no given ctid in row
  final val GAFISERR_NM_NEEDOLDROW = 3148	// update but old row is empty
  final val GAFISERR_NM_SELRESITEMBADFFLAG = 3149	// selres bad item form flag
  final val GAFISERR_NM_SELRESNOTINFLATFORM = 3150	// selres not in flat form
  final val GAFISERR_NM_NEEDRESITEM = 3151	// selres column is null
  final val GAFISERR_NM_SELRESNOTKEYCOL = 3152	// selres does not contain key column
  final val GAFISERR_NM_SELRESROWHEADNOSID = 3153	// selres row head does not contain sid
  final val GAFISERR_NM_SELRESNOLENFIELD = 3154	// selres general blob but no length field
  final val GAFISERR_NM_SELRESSRCTOOLONG = 3155	// selres source data too long
  final val GAFISERR_NM_SELRESHASINVALIDCOL = 3156	// selres has invalid column
  final val GAFISERR_NM_SELRESNOROWHEAD = 3157	// selres no row head
  final val GAFISERR_NM_SELRESNULLFLAGFORFREE = 3158	// selres only free format has null flag
  final val GAFISERR_NM_SELRESREADFLAGERR = 3159	// selres read flag is not null
  final val GAFISERR_NM_SELRESHASDUPCOL = 3160	// selres has duplicate column
  final val GAFISERR_NM_TABLESTANOTMATCH = 3165	// table sta not match
  final val GAFISERR_NM_NOGIVENDEVICE = 3167	// no device with given device id
  final val GAFISERR_NM_FILETYPEERR = 3168	// file type error
  final val GAFISERR_NM_IDXNOTSIZETOOLARGE = 3170	// index node size too large
  final val GAFISERR_NM_IDXNAMETOOLONG = 3171	// index name too long
  final val GAFISERR_NM_IDXINVALIDFILEHEAD = 3172	// index invalid file head
  final val GAFISERR_NM_SIDINVALIDFILEHEAD = 3175	// sid invalid file head
  final val GAFISERR_NM_SIDNOTUSED = 3176	// sid not used
  final val GAFISERR_NM_SIDCHENTRYINVALID = 3177	// change sid entry state : invalid parameter
  final val GAFISERR_NM_NOTABLEWITHUUID = 3178	// no table with given uuid
  final val GAFISERR_NM_BADBMFILEHEAD = 3179	// bad bm file head
  final val GAFISERR_NM_SELRESNETHEADERROR = 3180	// bad selres net head format
  final val GAFISERR_NM_BADCOLSTAFILE = 3181	// bad colsta file
  final val GAFISERR_NM_COLSTABADROWCNT = 3182	// colsta bad row count
  final val GAFISERR_NM_BADDEVICEFILE = 3183	// bad device management file
  final val GAFISERR_NM_INVALIDDEVINFO = 3184	// invalid device info
  final val GAFISERR_NM_INVALIDDFID = 3185	// invalid dfid file
  final val GAFISERR_NM_INVALIDWORKITEM = 3190	// xact invalid work item
  final val GAFISERR_NM_SHORTSPACEWORKITEM = 3191	// not enough space in work item head
  final val GAFISERR_NM_SHORTWORKITEMBUF = 3192	// not enough buffer in work item
  final val GAFISERR_NM_TOOMUCHDATAFILE = 3193	// too much data file
  final val GAFISERR_NM_REACHIDBTPAGELIMIT = 3194	// reach idbt tree page limit
  final val GAFISERR_NM_REACHMODLOGLIMIT = 3195	// too much modlog
  final val GAFISERR_NM_EXCEEDBLOCKROWLIMIT = 3196	// reach block row limit(32765)
  final val GAFISERR_NM_BMIDTOOBIG = 3197	// bmid too big and exceed actual size
  final val GAFISERR_NM_DEVSHORTSPACE = 3198	// not enough space on all devices
  final val GAFISERR_NM_NOTABLEWITHNAME = 3199	// no table with given name
  final val GAFISERR_NM_NOCTWITHGIVENCOL = 3200	// no child table with given column
  final val GAFISERR_NM_ROWANDBLOCKSIZENOTMATCH = 3201	// error result bad upgrade progress.
  final val GAFISERR_NM_NODATAFILEWITHGIVENID = 3202	// no data file with given file id.
  final val GAFISERR_NM_SELECTREACHMEMLIMIT = 3203	// select using too much memory.
  final val GAFISERR_NM_SIDHASBEENUSED = 3204	// sid has been used
  final val GAFISERR_NM_SIDMUSTBELONGTOSAMEBLOCK = 3205	// sid must belong to same block.
  final val GAFISERR_NM_INVALIDOID = 3206		// invalid oid
  final val GAFISERR_NM_UNIQUECOLEXIST = 3207	// row exists with value marked unique
  final val GAFISERR_NM_DFILENOTEXISTFORFID = 3208	// data file does not exist for given file id.

  final val GAFISERR_LOCK_UNINIT = 3501
  final val GAFISERR_LOCK_RESOURCE = 3502
  final val GAFISERR_LOCK_PARAMITEMERR = 3503
  final val GAFISERR_LOCK_UNLOCKITEM = 3504

  final val GAFISERR_TABLE_NOTREADY = 3550	// table is not ready.
  final val GAFISERR_TABLE_DISABLEWRITE = 3551
  final val GAFISERR_TABLE_DISABLEREAD = 3552


  final val GAFISERR_KEY_EXIST = 4001
  final val GAFISERR_KEY_NOTEXIST = 4002
  final val GAFISERR_KEY_NOTINMAPPER = 4003
  final val GAFISERR_KEY_NOTMATCHMAPPER = 4004
  final val GAFISERR_KEY_HASINVALIDCHAR = 4005
  final val GAFISERR_KEY_TOOLONG = 4006
  final val GAFISERR_KEY_TOOSHORT = 4007

  final val GAFISERR_EXF_COMPRESSEXCEPT = 4050	// compress image exception
  final val GAFISERR_EXF_COMPRESSERROR = 4051	// compress error.
  final val GAFISERR_EXF_EXTRACTMNTEXCEPT = 4052	// extract mnt exception
  final val GAFISERR_EXF_EXTRACTMNTERROR = 4053	// extract mnt error.
  final val GAFISERR_EXF_UNCOMPRESSEXCEPT = 4054	// uncompress exception
  final val GAFISERR_EXF_UNCOMPRESSERROR = 4055	// uncompress error.
  final val GAFISERR_EXF_MATCHEXCEPT = 4056	// match except
  final val GAFISERR_EXF_MATCHERROR = 4057	// match error.
  final val GAFISERR_EXF_PATREPEXCEPT = 4058	// pattern replace exception
  final val GAFISERR_EXF_PATRETERROR = 4059	// pattern replace error.
  final val GAFISERR_EXF_UNKNOWN = 4060	// unknown
  final val GAFISERR_EXF_DLLNOTEXIST = 4061	// dll not exist.
  final val GAFISERR_EXF_DLLLOADFAILED = 4062	// dll load failed.
  final val GAFISERR_EXF_DLLBADVERSION = 4063	// dll bad version(function may not exist)
  final val GAFISERR_EXF_ROLLPLAINNOTMATCH = 4064	// rolled finger and plain finger not match.
  final val GAFISERR_EXF_DUPLICATEROLL = 4065	// rolled finger may be duplicated.
  final val GAFISERR_EXF_INCONSISTENTMNT = 4066	// minutia has some inconsistent error.

  // hot-update error code.
  final val GAFISERR_HU_BADPATCHIDSTR = 4200	// bad patch id string
  final val GAFISERR_HU_BADPATCHFILEHEAD = 4201	// bad patch file head.
  final val GAFISERR_HU_BADINSTALLFILEHEAD = 4202	// bad installed history file head
  final val GAFISERR_HU_PATCHFILETOOSHORT = 4203	// patch history file too short.
  final val GAFISERR_HU_DUPPATCHID = 4204	// duplicate patch id
  final val GAFISERR_HU_PATCHIDSEQERR = 4205	// patch id sequence error.
  final val GAFISERR_HU_INSTALLFILETOOSHORT = 4206	// install history file too short

  final val GAFISERR_VM_NEEDDOLLOR = 5001	// an internal name is like $$ID
  final val GAFISERR_VM_NEEDOPERAND = 5002	// binary operator need two operands
  final val GAFISERR_VM_NEEDRBRACKET = 5003	// right ] is omitted
  final val GAFISERR_VM_SLASHBADPARAM = 5004	// back slash need parameter or parameter is invalid
  final val GAFISERR_VM_BADCHAR = 5005	// an invalid char met
  final val GAFISERR_VM_COLNOTEXIST = 5006	// variable as column does not exist in table
  final val GAFISERR_VM_FUNCTNOTEXIST = 5007	// function does not exist
  final val GAFISERR_VM_NEEDTOKEN = 5008	// some operator need more token
  final val GAFISERR_VM_NOTEXPECTEDTOKEN = 5009	// not expected token
  final val GAFISERR_VM_TOOMANYPARAMETERS = 5010	// too many parameters in function call
  final val GAFISERR_VM_BADEXPKIND = 5011	// the given expression kind does not seem valid
  final val GAFISERR_VM_BADEXPTYPE = 5012	// the variable type does not match
  final val GAFISERR_VM_BADOPERATORTYPE = 5013	// bad operator type
  final val GAFISERR_VM_NSGIVENCOLDATATYPE = 5014	// NS : not support the given col datatype
  final val GAFISERR_VM_DIVBYZERO = 5015	// divided by zero, overflow
  final val GAFISERR_VM_BADDATETIMEFORMAT = 5016	// bad date time format
  final val GAFISERR_VM_BADTOKENTYPE = 5017
  final val GAFISERR_VM_BADOPERAND = 5018

  final val GAFISERR_FLOB_COLDIRTYPE_NOTMATCH = 5500
  final val GAFISERR_FLOB_BADSIZETYPE = 5501
  final val GAFISERR_FLOB_BADBKSIZE = 5502
  // new add dec. 4, 2004
  final val GAFISERR_FLOB_DATALOST = 5503
  final val GAFISERR_FLOB_UNRECOVERABLE = 5504
  final val GAFISERR_FLOB_MANYDATALOST = 5505
  final val GAFISERR_FLOB_COLDATALOST = 5506
  final val GAFISERR_FLOB_LINKCYCLE = 5507
  final val GAFISERR_FLOB_KEYTOOLONG = 5508
  final val GAFISERR_FLOB_NOSLOT = 5509
  final val GAFISERR_FLOB_NEEDCHECKCOUNT = 5510
  final val GAFISERR_FLOB_BKHEADNOTVALID = 5511
  final val GAFISERR_FLOB_PTDIRHEADNOTVALID = 5512
  final val GAFISERR_FLOB_CNTTYPENOTMATCH = 5513
  final val GAFISERR_FLOB_TOOMUCHPOINTER = 5514
  final val GAFISERR_FLOB_BKSIZENOTMATCH = 5515
  final val GAFISERR_FLOB_COLDIRHEADINVALID = 5516
  final val GAFISERR_FLOB_COLDIRHEADLENERROR = 5517
  final val GAFISERR_FLOB_BADSUBBKID = 5518
  final val GAFISERR_FLOB_REFREEBKPT = 5519
  final val GAFISERR_FLOB_NEEDSETBKPTINISA = 5520
  final val GAFISERR_FLOB_NOTLINKHEAD = 5521
  final val GAFISERR_FLOB_NOTLINKTAIL = 5522
  final val GAFISERR_FLOB_CANNOTHOLDGIVENPT = 5523
  final val GAFISERR_FLOB_BLOCKCHECKED = 5524
  final val GAFISERR_FLOB_ISABKPTNOTMATCH = 5525
  final val GAFISERR_FLOB_SIDINLOBNOTMATCHISA = 5526
  final val GAFISERR_FLOB_BADFILEPARAM = 5527
  final val GAFISERR_FLOB_NOTENOUGHPRELOADPT = 5528	// not enough preloaded pt
  final val GAFISERR_FLOB_NOTMATCHPRELOADPT = 5529	// not match preloaded pt
  final val GAFISERR_FLOB_BKIDXEXCEEDLIMIT = 5530	// block index exceed limit
  final val GAFISERR_FLOB_NOTMATCHNEXTTYPE = 5531	// not match next size type or cnt type
  final val GAFISERR_FLOB_ISLINKHEAD = 5532	// can not be link head
  final val GAFISERR_FLOB_DIRSPACENOTMATCH = 5533	// flob col dir space calculated not match actual
  final val GAFISERR_FLOB_DIFFPTENTRYCNT = 5534	// flob pt count in entry differ actual
  final val GAFISERR_FLOB_NOTENOUGHPT = 5535	// space can not hold given number pt
  final val GAFISERR_FLOB_BADNEXTBKINCHAIN = 5536	// next bk info in chain is invalid.
  final val GAFISERR_FLOB_BADPTENTRY = 5537	// flob bad pointer entry
  final val GAFISERR_FLOB_ORPHANBLOCK = 5538	// flob orphan block
  final val GAFISERR_FLOB_INVALIDBKSIZETYPE = 5539	// invalid block size type.

  // used by remote system[5/27/2005]
  final val GAFISERR_RMT_ULDATATOOMANY = 6000	//upload too many data
  final val GAFISERR_RMT_DLDATATOOMANY = 6001	//download too many data
  final val GAFISERR_RMT_RMTCFG_NOTENTRY = 6002	//no correspond rmt config entry
  final val GAFISERR_RMT_PROXYDATATOOMANY = 6003	//proxy too many data
  final val GAFISERR_RMT_KEYHEADNOTMATCH = 6004	//key head and unitcode not match
  final val GAFISERR_RMT_UNQUALIFIEDFINGER = 6005	//unqualified finger or palm
  final val GAFISERR_RMT_FORBIDIP = 6006	//forbid ip address
  final val GAFISERR_RMT_PROXYQRYTOOMANY = 6007	//proxy too many query
  final val GAFISERR_RMT_BUSYLINE = 6008	//busy line
  final val GAFISERR_RMT_DIALFAILED = 6009	//dial failed
  final val GAFISERR_RMT_NOTSUPPORTWORK = 6010	//not be supported operation
  final val GAFISERR_RMT_INVALIDTRANSQUE = 6011	//invalid transmit queue record
  final val GAFISERR_RMT_INVALIDASYNCMD = 6012	//invalid aysn command format
  final val GAFISERR_RMT_MAXTTL = 6013	//exceed max ttl
  final val GAFISERR_RMT_LOOPLINE = 6014	//form a loop when routine
  final val GAFISERR_RMT_FORBIDKEY = 6015	//
  final val GAFISERR_RMT_FORBIDADDNEW = 6016	//forbid add new record
  final val GAFISERR_RMT_FORBIDUPDATE = 6017	//forbid update record
  final val GAFISERR_RMT_FORBIDDOWNLOAD = 6018	//forbid download record
  final val GAFISERR_RMT_NEEDRESCAN = 6019	//need rescan record
  final val GAFISERR_RMT_MISSIMAGE = 6020	//miss finger or palm image
  final val GAFISERR_RMT_MISSMINUTIA = 6021	//miss finger or palm minutia
  final val GAFISERR_RMT_MISSTEXT = 6022	//miss text info
  final val GAFISERR_RMT_RMTKEYEXIST = 6023	//remote key has exist
  final val GAFISERR_RMT_FORBIDULUQREC = 6024	//forbid upload unqualitied record

  final val GAFISERR_RMT_PARAM_NOTENTRY = 6050	//no entry in txserver.ini
  final val GAFISERR_RMT_PARAM_NOMAINTXSVR = 6051	//no MainTxServerName in txserver.ini
  final val GAFISERR_RMT_PARAM_MAINTXSVRISSELF = 6052	//MainTxSeverName can't itself

  final val GAFISERR_RMT_AUTOQRY_NOTEXIST = 6060	//auto query config not exist
  final val GAFISERR_RMT_AUTOQRY_NOTENTRY = 6061	//no correspond auto query entry

  final val GAFISERR_RMT_DATCTRL_NOTEXIST = 6070	//data control not exist
  final val GAFISERR_RMT_DATCTRL_NOTENTRY = 6071	//no correspond data control entry

  final val GAFISERR_RMT_QRYCTRL_NOTEXIST = 6080	//query or data control not exist
  final val GAFISERR_RMT_QRYCTRL_NOTENTRY = 6081	//no correspond query or data control entry

  final val GAFISERR_RMT_QRYECHO_NOTEXIST = 6090	//query echo config not exist
  final val GAFISERR_RMT_QRYECHO_NOTENTRY = 6091	//no correspond query echo config entry

  final val GAFISERR_RMT_USER_REACHLIMIT = 6100	//too many user logined
  final val GAFISERR_RMT_USER_NOTEXIST = 6101	//user not exist

  final val GAFISERR_RMT_UNITCODE_ISNULL = 6110	//unitcode is null
  final val GAFISERR_RMT_UNITCODE_INVALID = 6111	//invalid unitcode
  final val GAFISERR_RMT_UNITCODE_NOTEXIST = 6112	//unitcode not exist

  final val GAFISERR_RMT_SERVER_NOTEXIST = 6120	//server not exist
  final val GAFISERR_RMT_SERVER_NODIRECTUPPER = 6121	//no direct upper server
  final val GAFISERR_RMT_SERVER_NOPROXYSVR = 6122	//not find proxy server
  final val GAFISERR_RMT_SERVER_NOTDIRECT = 6123	//not direct server
  final val GAFISERR_RMT_SERVER_NOLIVESVR = 6124	//live update server not exist

  final val GAFISERR_RMT_DBMAP_NOTEXIST = 6130	//database not found in database map

  final val GAFISERR_RMT_CONVERT_NOTEXIST = 6140	//item not exist when data convert
  final val GAFISERR_RMT_PKGITEM_NOTEXIST = 6141	//item not exist in rmt pkg
  final val GAFISERR_RMT_TXTITEM_NOTEXIST = 6142	//item not exist in text info
  final val GAFISERR_RMT_PKGITEM_INVALID = 6143	//item is invalid in rmt pkg

  final val GAFISERR_RMT_ASYNCMD_INVALID = 6150	//invalid asyn command

  final val GAFISERR_RMT_QUERY_INVALIDTYPE = 6160	//INVALID QUERY TYPE
  final val GAFISERR_RMT_QUERY_INVALIDSTATUS = 6161	//INVALID QUERY STATUS
  final val GAFISERR_RMT_QUERY_INVALIDRMTSTATUS = 6162	//INVALID QUERY REMOTE STATUS

  final val GAFISERR_RMT_TPCARDTEXT_MISSNAME = 6200	//miss name
  final val GAFISERR_RMT_TPCARDTEXT_MISSBIRTHDAY = 6201	//miss birthday
  final val GAFISERR_RMT_TPCARDTEXT_MISSSEX = 6202	//miss sex
  final val GAFISERR_RMT_TPCARDTEXT_MISSHUKOUCODE = 6203	//miss hukou unitcode
  final val GAFISERR_RMT_TPCARDTEXT_MISSADDRESSCODE = 6204	//miss address unitcode
  final val GAFISERR_RMT_TPCARDTEXT_MISSPRINTUNITCODE = 6205	//miss print unitcode
  final val GAFISERR_RMT_TPCARDTEXT_MISSPRINTUSER = 6206	//miss print user
  final val GAFISERR_RMT_TPCARDTEXT_MISSPRINTDATE = 6207	//miss print date
  final val GAFISERR_RMT_TPCARDTEXT_MISSMISPERSONID = 6208	//miss mispersonid
  final val GAFISERR_RMT_TPCARDTEXT_MISSHUKOUUNITNAME = 6209	//miss hukou unitname
  final val GAFISERR_RMT_TPCARDTEXT_MISSADDRESSUNITNAME = 6210	//miss address unitname
  final val GAFISERR_RMT_TPCARDTEXT_MISSPRINTUNITNAME = 6211	//miss print unitname
  final val GAFISERR_RMT_TPCARDTEXT_MISSCASECLASS = 6212	//miss case class
  final val GAFISERR_RMT_TPCARDTEXT_MISSCOMMENTS = 6213	//miss comments

  final val GAFISERR_RMT_LPCARDTEXT_MISSSEQNO = 6220	//miss seq no
  final val GAFISERR_RMT_LPCARDTEXT_MISSREMAINPLACE = 6221	//miss remain place
  final val GAFISERR_RMT_LPCARDTEXT_MISSRIDGECOLOR = 6222	//miss ridge color
  final val GAFISERR_RMT_LPCARDTEXT_MISSCAPTUREDMETHOD = 6223	//miss captured method
  final val GAFISERR_RMT_LPCARDTEXT_MISSCOMMENT = 6224	//miss comment

  final val GAFISERR_RMT_LPCASETEXT_MISSCASECLASS = 6230	//miss case type
  final val GAFISERR_RMT_LPCASETEXT_MISSCASEOCCURDATE = 6231	//miss case occur date
  final val GAFISERR_RMT_LPCASETEXT_MISSCASEOCCURADDRESS = 6232	//miss case occur address
  final val GAFISERR_RMT_LPCASETEXT_MISSCASEEXTRACTUNIT = 6233	//miss case extract unit code
  final val GAFISERR_RMT_LPCASETEXT_MISSCASEEXTRACTUSER = 6234	//miss case extract user
  final val GAFISERR_RMT_LPCASETEXT_MISSCASECOMMENTS = 6235	//miss case comments
  final val GAFISERR_RMT_LPCASETEXT_MISSCASELEVEL = 6236	//miss case level

  // used by oracle api [7/13/2005]
  final val GAFISERR_ORADB_EXCEPTION = 6500	//exception occur
  final val GAFISERR_ORADB_INVALIDROWID = 6501	//invalid row id
  final val GAFISERR_ORADB_INVALIDROWPOS = 6502	//invalid row positon
  final val GAFISERR_ORADB_INVALIDDATATYPE = 6503	//invalid data type

  final val GAFISERR_ORADB_CREATEENV = 6520	//create environment failed
  final val GAFISERR_ORADB_CREATEPOOL = 6521	//create connection pool failed
  final val GAFISERR_ORADB_CREATECONNECTION = 6522	//create connection failed
  final val GAFISERR_ORADB_CREATETABLE = 6523	//create table failed
  final val GAFISERR_ORADB_CREATESEQUENCE = 6524	//create sequence failed

  final val GAFISERR_ORADB_INSERT = 6540	//insert record failed
  final val GAFISERR_ORADB_UPDATE = 6541	//update record failed
  final val GAFISERR_ORADB_SELECT = 6542	//select record failed
  final val GAFISERR_ORADB_DELETE = 6543	//delete record failed
  final val GAFISERR_ORADB_TRUNCATE = 6544	//truncate record failed
  final val GAFISERR_ORADB_DROPTAB = 6545	//drop table failed


  /// local lib error.
  final val GAFISERR_LOC_BADCODEFILE = 8000	// bad code table file line.
  final val GAFISERR_LOC_NOTHREADUSERINFO = 8001	// no thread user info
  final val GAFISERR_LOC_TABLEPROPISNULL = 8002	// table user defined data is null
  final val GAFISERR_LOC_BADLOCKFILEID = 8003	// bad lock file id
  final val GAFISERR_LOC_STREAMNAMENOTMATCH = 8004	// mic stream name not match
  final val GAFISERR_LOC_STREAMTOOSHORT = 8005	// stream does not contain enough data
  final val GAFISERR_LOC_STREAMLENNOTMATCH = 8006	// stream length does not match
  final val GAFISERR_LOC_NOTENOUGHMCASEIP = 8010	// not enough mcast ip address
  final val GAFISERR_LOC_CREATETEMPFILEFAILED = 8011	// create temp file failed
  final val GAFISERR_LOC_BADPALMINDEX = 8012	// bad palm index
  final val GAFISERR_LOC_BADFINGERINDEX = 8013	// bad finger index
  final val GAFISERR_LOC_BADPLAINFINGERINDEX = 8014	// bad plain finger index
  final val GAFISERR_LOC_BADTPLAINFINGERINDEX = 8015	// bad tplain finger index
  final val GAFISERR_LOC_BADFACEINDEX = 8016	// bad face index.
  final val GAFISERR_LOC_BADSIGNATUREINDEX = 8017	// bad signature index
  final val GAFISERR_LOC_BADVOICEINDEX = 8018	// bad voice index
  final val GAFISERR_LOC_BADITEMTYPE = 8019	// bad item type
  final val GAFISERR_LOC_BADCARDDATAINDEX = 8020	// bad card data index
  final val GAFISERR_LOC_COLNOTINIT = 8021	// col name not initialized
  final val GAFISERR_CODETABLE_BADTYPE = 8030	// code multisearch : bad item name
  final val GAFISERR_CODETABLE_CODETOOLONG = 8031	// code table : code too long
  final val GAFISERR_CODETABLE_INCODETOOLONG = 8032	// code table : input code too long
  final val GAFISERR_CODETABLE_NAMETOOLONG = 8033	// code table : name too long
  final val GAFISERR_LOC_INSVRTYPEERROR = 8040	// inner user name : server type error
  final val GAFISERR_LOC_USEREXIST = 8041	// user with given name exists
  final val GAFISERR_LOC_USERNOTEXIST = 8042	// no user with given name
  final val GAFISERR_LOC_CASEOPTIDERROR = 8050	// case op table id error
  final val GAFISERR_LOC_BADLOGTYPE = 8052	// bad log type
  final val GAFISERR_LOC_LPIMGSIZETOOSMALL = 8060	// lp card image size too small
  final val GAFISERR_LOC_LPIMGNOTMEETCHINASTD = 8061	// lp card image does not conform to china national standard
  final val GAFISERR_LOC_LPMNTSIZEERROR = 8062	// lp card mnt error
  final val GAFISERR_LOC_LPOPTIDERROR = 8063	// lp card op : table id error
  final val GAFISERR_LOC_LPNEEDSIGBUF = 8064	// lp get sig : need buf
  final val GAFISERR_LOC_TABLENOTQUETYPE = 8070	// table type not que type
  final val GAFISERR_LOC_CRTDBNEEDNAME = 8071	// create db : database name is empty
  final val GAFISERR_LOC_TPIMGSIZETOOSMALL = 8080	// tp card image size too small
  final val GAFISERR_LOC_TPIMGNOTMEETCHINASTD = 8081	// tp card image does not conform to china national standard
  final val GAFISERR_LOC_TPGETSIGPARAMERROR = 8082	// tp card get signature parameter error
  final val GAFISERR_LOC_TPMNTSIZEERROR = 8083	// tp card mnt size error
  final val GAFISERR_LOC_DBFIFOQUENAMENOTSET = 8090	// fifo que name not set
  final val GAFISERR_LOC_INVALIDDESIGN = 8091	// design error
  final val GAFISERR_LOC_BADINDEX = 8092	// bad index
  final val GAFISERR_LOC_INVALIDIMGIDX = 8093	// invalid image index
  final val GAFISERR_LOC_TEMPPATHISNULL = 8094	// temp path can not be null
  final val GAFISERR_LOC_UNITCODEISNULL = 8095	// unit code can not be null
  final val GAFISERR_LOC_UNITNAMEISNULL = 8096	// unit name can not be null
  final val GAFISERR_LOC_PERSONOPTIDERROR = 8100	// person op tid error

  final val GAFISERR_QUERY_OPTIDERROR = 8110	// query op tid error
  final val GAFISERR_QUERY_NODESTDB = 8111	// query : dest db count is zero
  final val GAFISERR_QUERY_INVALIDQUERYTYPE = 8112	// query : invalid query type
  final val GAFISERR_QUERY_NOSRCMNT = 8113	// query : no source mnt
  final val GAFISERR_QUERY_TOOMUCHMIC = 8114	// query : too much mic
  final val GAFISERR_QUERY_DUPFINGER = 8115	// query : source data has duplicate finger
  final val GAFISERR_QUERY_TLSOURCEINVALID = 8116	// query : tl query source data has palm and finger, only one allowed
  final val GAFISERR_QUERY_LTITEMTYPE = 8117	// query : lt source data item type invalid
  final val GAFISERR_QUERY_LLHASPALMANDFINGER = 8118	// query : ll source data both have palm and finger
  final val GAFISERR_QUERY_LTTTDESTTABLETYPE = 8119	// query : lt or tt. but dest table type not tenprint
  final val GAFISERR_QUERY_TLLLDESTTABLETYPE = 8120	// query : tl or ll. but dest table type not latent
  final val GAFISERR_QUERY_DESTDBNOTQUERYTYPE = 8121	// query : database not query type
  final val GAFISERR_QUERY_BADQUERYTYPE = 8122	// query : bad query type
  final val GAFISERR_QUERY_DESTDBNOTTPORLP = 8123	// query : dest db not tp or lp type
  final val GAFISERR_QUERY_DBTYPEQRYTYPENOTMATCH = 8124	// query : database type not match query type
  final val GAFISERR_QUERY_TARGETDBCNTERROR = 8125	// query : target db count error
  final val GAFISERR_QUERY_TARGETDBNOTSAMETYPE = 8126	// query : has at least 2 target db but type not same
  final val GAFISERR_QUERY_TARGETDBNOTSEARCHED = 8127	// query : target db is not configured as parallel searched
  final val GAFISERR_QUERY_TARGETTABLENOTEXIST = 8128	// query : target table does not exist
  final val GAFISERR_QUERY_COMMENTREACHLIMIT = 8129	// query : query comment reach limit
  final val GAFISERR_QUERY_NOTPLAINTOSEARCH = 8130	// no tplain finger to search.
  final val GAFISERR_QUERY_TXTSCHTOOMANYCAND = 8131	// 进行文本信息查询，有太多的候选了。
  final val GAFISERR_QUERY_BADMNTDATA = 8132	// 查询中的特征数据错误
  final val GAFISERR_QUERY_BADBINDATA = 8133	// 查询中的纹线数据错误

  final val GAFISERR_LOC_NEWDBMAINPATHEMPTY = 8150	// create new db but main path not set
  final val GAFISERR_LOC_NEWDBREFDBTYPEERR = 8151	// create new db from exist one but type not match
  final val GAFISERR_LOC_NEWDBTYPEINVALID = 8152	// create new db but db type invalid
  final val GAFISERR_LOC_TEXTITEMNAMEERROR = 8160	// text item name error
  final val GAFISERR_LOC_TEXTITEMINVALID = 8161	// text item invalid
  final val GAFISERR_LOC_BADIMGSIGNATURE = 8162	// bad image signature
  final val GAFISERR_LOC_INVALIDXGWQRYCOND = 8163	// invalid xgw query condition
  final val GAFISERR_LOC_INVALIDLCWQRYCOND = 8164	// invalid lcw query condition
  final val GAFISERR_LOC_QUERYTYPEERROR = 8165	// bad query type
  final val GAFISERR_LOC_FINGERPALMTYPEERROR = 8166	// bad finger type or palm type
  final val GAFISERR_LOC_SYSPATHTYPEERROR = 8171	// bad syspath type
  final val GAFISERR_LOC_MOBCASETIDERROR = 8180	// mobile case op table id error
  final val GAFISERR_LOC_DBTYPENOTMATCH = 8181	// db type not match
  final val GAFISERR_LOC_TABLETYPENOTMATCH = 8182	// table type not match
  final val GAFISERR_LOC_KEYLISTLENERROR = 8190	// key list length error
  final val GAFISERR_LOC_KEYNOTINKEYLIST = 8191	// key not in key list
  final val GAFISERR_LOC_KEYEXISTINKEYLIST = 8192	// key already in key list
  final val GAFISERR_LOC_CMPQRY_GETTIMENOTMATCH = 8193	// finish qry : get time not match.
  final val GAFISERR_LOC_CMPQRY_MUIDNOTMATCH = 8194	// finish qry : muid not match.
  final val GAFISERR_LOC_CMPQRY_SUBMITTIMENOTMATCH = 8195	// finish qry : submit time not match.
  final val GAFISERR_LOC_CMPQRY_QIDNOTMATCH = 8196		// finish qry : query id not match.

  final val GAFISERR_QUERY_MICITEMINDEXLARGE = 8197		// mic's itemindex too large.
  final val GAFISERR_QUERY_MICINTEINDEXDUP = 8198		// duplicate mic's itemindex.
  final val GAFISERR_QUERY_MICINDEXMISSING = 8199		// mic's index is not consecutive.

  final val GAFISERR_LOC_BUILDINDBNAMEIDNOTMATCH = 8201	// build in db name and dbid unmatch.
  final val GAFISERR_LOC_DBPROPCANNOTBENULL = 8202	// db property can not be null.
  final val GAFISERR_LOC_DBTYPENOTMATCHBUILDIN = 8203	// given database type not match build in.

  // net lib [8300-8499]
  final val GAFISERR_NET_DBTYPENOTTPLP = 8300	// only tp or lp support parallel searching
  final val GAFISERR_NET_NOUSERDATA = 8301	// no user data
  final val GAFISERR_NET_DBTYPEERROR = 8302	// db type not tp or lp
  final val GAFISERR_NET_RECVCOLPARAMERR = 8303	// receive col parameter error
  final val GAFISERR_NET_MNT2FEATPROCNOTSET = 8304	// mnt2feat proc not set
  final val GAFISERR_NET_MACHINEUUIDNOTMATCH = 8305	// machine uuid not match
  final val GAFISERR_NET_MACHINEUUIDSAME = 8306	// same uuid for different host
  final val GAFISERR_NET_INVALIDCONNECTION = 8307	// invalid connection
  final val GAFISERR_NET_INVALIDLOGINFO = 8308	// invalid authentication info
  final val GAFISERR_NET_TOOMUCHRIGHTTOQUERY = 8309	// too much right to query
  final val GAFISERR_NET_TEXTTOOLONG = 8310	// text too long
  final val GAFISERR_NET_ONLYFORTP = 8311	// given operation only for tplib
  final val GAFISERR_NET_MCASTSVROPCLASSERR = 8320	// mcast svr invalid op class
  final val GAFISERR_NET_PARAMNOTINTTYPE = 8321	// parameter not integer type
  final val GAFISERR_NET_PARAMNOTLONGTYPE = 8322	// parameter not long type
  final val GAFISERR_NET_PARAMNOTSTRING = 8323	// parameter not string
  final val GAFISERR_NET_CHGKEYTOOLONG = 8324	// change key : key too long
  final val GAFISERR_NET_INVALIDNETREQ = 8325	// invalid net request

  // bkp lib[8500, 8599]
  final val GAFISERR_BKP_TABLENOTEXIST = 8500	// table not exist
  final val GAFISERR_BKP_INVALIDBKPTIMELOG = 8501	// invalid bkp time log
  final val GAFISERR_BKP_LOADBKPTIMELOGFAIL = 8502	// load bkp time log file fail
  final val GAFISERR_BKP_BKPSKMNOTEXIST = 8503	// backup skm log not exist
  final val GAFISERR_BKP_RECLDBNOTEXIST = 8504	// recycle db not exist
  final val GAFISERR_BKP_BKPTASKNOTWHOLEDB = 8505	// backup task not whole db
  final val GAFISERR_BKP_BKPTASKNOTMATCH = 8506	// not given backup task
  final val GAFISERR_BKP_BKPTASKWHOLEDB = 8507	// backup task whole db
  final val GAFISERR_BKP_SYNCDBMAINPATHNULL = 8510	// sync db main path is null
  final val GAFISERR_BKP_ASYNCDBMAINPATHNULL = 8511	// async db main path is null
  final val GAFISERR_BKP_ONCEDBMAINPATHNULL = 8512	// once db main path is null
  final val GAFISERR_BKP_SEGLOGFILEHEADINVALID = 8513	// seg log file head invalid
  final val GAFISERR_BKP_SEGLOGFMTINVALID = 8514	// seg log seg format invalid
  final val GAFISERR_BKP_TASKINFOSIZEINVALID = 8515	// task info size error
  final val GAFISERR_BKP_TASKINFOVERINVALID = 8516	// task info version invalid
  final val GAFISERR_BKP_TASKINFOBKPTYPEINVALID = 8517	// task info bkp type invalid
  final val GAFISERR_BKP_TASKINFODBUUIDINVALID = 8518	// task info dbuuid invalid
  final val GAFISERR_BKP_TASKINFOTABLEUUID = 8519	// task info table uuid invalid
  final val GAFISERR_BKP_RECLDBEMPTY = 8520	// task info recycle db is empty
  final val GAFISERR_BKP_RECLTBLEMPTY = 8521	// task info recycle table is empty
  final val GAFISERR_BKP_TASKINFOFILESIZEERR = 8522	// task info file size not match
  final val GAFISERR_BKP_UPDATETASKPARAM = 8523	// update task param error
  final val GAFISERR_BKP_SVRCFGFILEHEAD = 8524	// svr cfg file head invalid
  final val GAFISERR_BKP_SVRHASTASK = 8525	// remove all task before remove server

  // searching server error.Searching Engine.
  final val GAFISERR_SE_INVALIDTIDINTASK = 9000	// invalid tid in task
  final val GAFISERR_SE_BADSVRTID = 9001	// bad server tid
  final val GAFISERR_SE_BADLOCTID = 9002	// bad local tid
  final val GAFISERR_SE_SVRNOTSUPPPALMORFING = 9003	// server neither support palm nor finger
  final val GAFISERR_SE_LOCANDSVRLSNNOTMATCH = 9004	// license on server and local not match
  final val GAFISERR_SE_LCWONLYSUPPTT = 9005	// match method lcw only support tt
  final val GAFISERR_SE_UNKNOWNTASKTYPE = 9006	// unknown task type(may be design error)
  final val GAFISERR_SE_LOCTID2SVRERROR = 9007	// convert loc tid to server tid error
  final val GAFISERR_SE_QRYINITFAILED = 9008	// query initialization failed
  final val GAFISERR_SE_GETHANDLEERROR = 9009	// get handle(may be design error)
  final val GAFISERR_SE_LOADSIDTIMENOTMATCH = 9010	// load sid but time not match
  final val GAFISERR_SE_SIDFILESIZEERROR = 9011	// sid file size not match head
  final val GAFISERR_SE_PMTABLESTATUSERROR = 9012	// pm table status error(may be design error)
  final val GAFISERR_SE_BADMATCHMETHOD = 9013	// match method invalid
  final val GAFISERR_SE_CANDINVALID = 9014	// parameter in candidate invalid
  final val GAFISERR_SE_BADTASKTYPE = 9015	// bad task type
  final val GAFISERR_SE_CANDDISTERROR = 9016	// candidate has invalid tid
  final val GAFISERR_SE_INITCANDMGMTPARAMERR = 9017	// initialize cand management parameter error
  final val GAFISERR_SE_INVALIDCAND = 9018	// invalid candidate
  final val GAFISERR_SE_ADDTABLETYPENOTMATCH = 9019	// add table but table type not match
  final val GAFISERR_SE_CANNOTRMACTIVEMU = 9020	// can not remove active mu
  final val GAFISERR_SE_ESTCOEFQRYTYPE = 9021	// estimate query coefficient query type error
  final val GAFISERR_SE_TARGETDBNOTEXIST = 9022	// target db does not exist on mu
  final val GAFISERR_SE_NODBTOBESEARCHED = 9023	// no db to be searched
  final val GAFISERR_SE_MUTYPENOTSET = 9024	// matcher type not set.
  final val GAFISERR_SE_SUBLIBPATHNOTSET = 9025	// sublib main path not set
  final val GAFISERR_SE_SEGLOGFILEBAD = 9026	// seg lob file head invalid
  final val GAFISERR_SE_MEMREACHLIMIT = 9027	// reach memusage limit
  final val GAFISERR_SE_SVRDTID2LOC = 9028	// convert server dbit to local dtid error.
  final val GAFISERR_SE_MATCHERNOTSUPPTASK = 9029	// matcher does not support task service.
  final val GAFISERR_SE_MATCHTYPENOTMATCH = 9030	// local matcher type and server matcher type not match.
  final val GAFISERR_SE_EXCEEDPMTABLELIMIT = 9031	// 超过并行比对允许的可以支持的并行比对表的数量。


  // svr param
  final val GAFISERR_DBSVR_TOOMUCHKEY = 9500	// key count error
  final val GAFISERR_DBSVR_BKPSERVICENOTSTART = 9501	// backup service not started
  //----------------------------------------------------------------------------------------------------------*/
  final val MAX_ERRSTR_LEN = 256
  final val GBASE_ERRTYPE_OSERROR = 0x1
  final val GBASE_ERRTYPE_WIN32ERROR = 0x2

  // nOption is GBASE_ERRTYPE_XXX
  final val GBASE_ERRTYPE_SOCKERROR = 0x4
    final val ERRPRINTOPT_CHINESE = 0x1	// Chinese language
    final val ERRPRINTOPT_FILETRACK = 0x2

  ///////////////////////////////////////
  class FILENLSTRUCT extends AncientData
  {
  var nLineNum:Short = _ ;	// can not exceed 65536 lines
  @Length(18)
  var sFileName:String = _ ;	// maximum 18 characters
  } // FILENLSTRUCT;	// size is 20 bytes

  class GAFISERRDATSTRUCT extends AncientData
  {
  var cbSize:Int = 640 ;			// size of this structure
  var nAFISErrno:Int = _ ;		// AFIS internal errno
  var nSYSErrno:Int = _ ;		// operation system errno
  var nLineNum:Short = _ ;		// file line no, 2 bytes int
  var nFileCount:Byte = _ ;			// how many files followed
  var nErrDataLen:Byte = _ ;		// err data len
  @Length(32)
  var szFileName:String = _ ;		// file name when this error was generated, to here is 48 bytes
  var bIsOSError:Byte = _ ;			// whether is os error or not
  @Length(1)
  var bnRes:Array[Byte] = _ ;			// reserved,
  var nSubErrno:Short = _ ;		// sub error code.
  var nWinErr:Int = _ ;			// to here is 56 bytes
  @Length(96)
  var bnAFISErrData:Array[Byte] = _ ;	// length is 96 bytes, to here 152 bytes
  @Length(60)
  var szSysErrStr:String = _ ;	// 60 bytes error string
  @Length(14)
  var stFileList:Array[FILENLSTRUCT] = _;	// total size is 280 bytes
  @Length(8)
  var bnErrOccureTime:Array[Byte] = _ ;	// error occur date time. format is AFISDateTime.
  @Length(12)
  var bnRes2:Array[Byte] = _ ;			// reserved.
  @Length(16)
  var szHostName:String = _ ;		// computer name.
  @Length(64)
  var szEnvInfo:String = _ ;
  @Length(48)
  var bnAFISErrData2:Array[Byte] = _ ;	// 2006.01.06 we found 96 bytes to store error specific data is
  // not enough, so we expand error data size.
  } // GAFISERRDATSTRUCT;	// total size of this struct is 512+128 = 640 bytes

}
