package nirvana.hall.c.services.ghpcbase

import nirvana.hall.c.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-07
 */
object gnopcode {


  class NOUSESTRUCT extends AncientData
  {
    var no:Int = _;
  } // NOUSESTRUCT;

  /* all operation are divided into several groups */
  final val OP_CLASS_TPLIB = 101
  final val OP_CLASS_LPLIB = 102
  final val OP_CLASS_PARAMATCH = 103	/* parallel match */
  final val OP_CLASS_USER = 104
  final val OP_CLASS_QUERY = 105
  final val OP_CLASS_LOG = 106
  final val OP_CLASS_ADM = 107
  final val OP_CLASS_BACKUP = 108
  final val OP_CLASS_STATISTICS = 109
  final val OP_CLASS_MISC = 110
  final val OP_CLASS_RMTINPUTCENSOR = 111
  final val OP_CLASS_RMTCLIENT = 112
  final val OP_CLASS_FIFOQUE = 113
  final val OP_CLASS_CASE = 114
  final val OP_CLASS_PERSON = 115
  final val OP_CLASS_SYS = 116
  final val OP_CLASS_BLOB = 117
  final val OP_CLASS_EXF = 118
  final val OP_CLASS_SEARCH = 119
  final val OP_CLASS_CODETABLE = 121
  final val OP_CLASS_MSG = 122
  final val OP_CLASS_MOBICASE = 123
  final val OP_CLASS_PARAMETER = 124
  final val OP_CLASS_VERIFYLOG = 125
  final val OP_CLASS_DBLOG = 126
  final val OP_CLASS_CURSOR = 127
  final val OP_CLASS_MNTSVR = 128
  final val OP_CLASS_TABLE = 130

  /// define opclass for search server
  final val OP_CLASS_TASK = 150
  final val OP_CLASS_MU = 151

  final val OP_CLASS_GMC = 152
  final val OP_CLASS_MCOP = 153

  final val OP_CLASS_FILE = 131
  final val OP_CLASS_HOTUPDATE = 132
  final val OP_CLASS_NMDB = 135
  final val OP_CLASS_SVRMISC = 136	// server misc
  final val OP_CLASS_PARAMADM = 137
  final val OP_CLASS_LSN = 138
  final val OP_CLASS_QRYASSIGN = 140

  /**
   * 人像查询
   */
  final val OP_CLASS_FACEQUERY = 161


  final val OP_CLASS_TEST = 254

  final val OP_CLASS_EXIT = 255


  /* OP_CLASS_TPLIB opcode */
  final val OP_TPLIB_ADD = 50
  final val OP_TPLIB_UPDATE = 51
  final val OP_TPLIB_DEL = 52
  final val OP_TPLIB_RETR = 53
  final val OP_TPLIB_EXIST = 54
  final val OP_TPLIB_GET = 55
  final val OP_TPLIB_REBUILDFEAT = 56
  final val OP_TPLIB_DELIMGIFCPREXIST = 57	// delete img if cpr exists
  final val OP_TPLIB_DELPALM = 58
  final val OP_TPLIB_GETMICBSIG = 59
  final val OP_TPLIB_MICBSIGMATCH = 60
  final val OP_TPLIB_GETAUTOMISPERSONID = 61

  final val OP_TPLIB_UNMATCH_ADD = 62
  final val OP_TPLIB_UNMATCH_DEL = 63
  final val OP_TPLIB_UNMATCH_GET = 64

  final val OP_TPLIB_GENAUTOMISPID = 65


  /* OP_CLASS_LPLIB */
  final val OP_LPLIB_ADD = 100
  final val OP_LPLIB_UPDATE = 101
  final val OP_LPLIB_DEL = 102
  final val OP_LPLIB_RETR = 103
  final val OP_LPLIB_EXIST = 104
  final val OP_LPLIB_GET = 105
  final val OP_LPLIB_GETMICBSIG = 106
  final val OP_LPLIB_MICBSIGMATCH = 107


  final val OP_LPLIB_UNMATCH_ADD = 108
  final val OP_LPLIB_UNMATCH_DEL = 109
  final val OP_LPLIB_UNMATCH_GET = 110

  final val OP_LPGROUP_ADD = 115
  final val OP_LPGROUP_DEL = 116
  final val OP_LPGROUP_GET = 117
  final val OP_LPGROUP_UPDATE = 118

  /* OP_CLASS_FIFOQUE */
  final val OP_FIFOQUE_ADD = 150
  final val OP_FIFOQUE_DEL = 151
  final val OP_FIFOQUE_RETR = 152
  final val OP_FIFOQUE_GETTOWORK = 153
  final val OP_FIFOQUE_FINISHWORK = 154
  final val OP_FIFOQUE_ABORTWORK = 155
  final val OP_FIFOQUE_GET = 156
  final val OP_FIFOQUE_MULTIADD = 157
  final val OP_FIFOQUE_MULTIDEL = 158


  /* OP_CLASS_USER */
  final val OP_USER_LOGIN = 200
  final val OP_USER_LOGOUT = 201
  final val OP_USER_ADD = 202
  final val OP_USER_DEL = 203
  final val OP_USER_GETALLUSERINFO = 204
  final val OP_USER_GETACCOUNTNUM = 205
  final val OP_USER_RIGHTQUERY = 206
  final val OP_USER_CHANGEPASSWORD = 207
  final val OP_USER_GETUSERINFO = 208
  final val OP_USER_GETTRUSTTYPE = 209
  final val OP_USER_INWORKING = 210
  final val OP_USER_KILL = 211
  final val OP_USER_GETALLWORKID = 212
  final val OP_USER_CANLOGIN = 213
  final val OP_USER_RELOGIN = 214
  final val OP_USER_GETMAXPRIORITY = 215
  final val OP_USER_GETQUERYCNT = 216
  final val OP_USER_ISLOGINED = 217
  final val OP_USER_SETQRYCNT = 218
  final val OP_USER_GETLOGININFO = 219
  final val OP_USER_GETALLACCOUNT = 220
  final val OP_USER_UPDATE = 221

  /* OP_CLASS_CASE */
  final val OP_CASE_ADD = 250
  final val OP_CASE_UPDATE = 251
  final val OP_CASE_DEL = 252
  final val OP_CASE_RETR = 253
  final val OP_CASE_EXIST = 254
  final val OP_CASE_GET = 255

  final val OP_CASEGROUP_ADD = 260
  final val OP_CASEGROUP_DEL = 261
  final val OP_CASEGROUP_UPDATE = 262
  final val OP_CASEGROUP_GET = 263

  final val OP_CASE_CLEARQRYASSIGN = 265
  final val OP_CASE_GETQRYASSIGN = 266

  /* OP_CLASS_PERSON */
  final val OP_PERSON_ADD = 300
  final val OP_PERSON_UPDATE = 301
  final val OP_PERSON_DEL = 302
  final val OP_PERSON_RETR = 303
  final val OP_PERSON_EXIST = 304
  final val OP_PERSON_GET = 305


  /* OP_CLASS_PARAMATCH opcode */
  final val OP_PARAMATCH_SUBLIB = 350
  final val OP_PARAMATCH_UPDATE = 361

  /* OP_CLASS_USER opcode */

  /* OP_CLASS_QUERY opcode */
  final val OP_QUERY_ADD = 450
  final val OP_QUERY_UPDATE = 451
  final val OP_QUERY_DEL = 452
  final val OP_QUERY_RETR = 453
  final val OP_QUERY_GET = 455
  final val OP_QUERY_GETTOSEARCH = 460
  final val OP_QUERY_FINISHSEARCH = 461
  final val OP_QUERY_ABORTSEARCH = 462
  final val OP_QUERY_GETTOCHECK = 470
  final val OP_QUERY_FINISHCHECK = 471
  final val OP_QUERY_ABORTCHECK = 472
  final val OP_QUERY_RESETINMEM = 473
  final val OP_QUERY_GETESTIMATEFINTIME = 474
  final val OP_QUERY_CHANGEPRI = 475
  final val OP_QUERY_SUBMIT = 476
  final val OP_QUERY_MISGETSEARCH = 477
  final val OP_QUERY_MISFINSEARCH = 478
  final val OP_QUERY_MISABORTSEARCH = 479
  final val OP_QUERY_WAITFINISH = 480	// wait query to finish
  final val OP_QUERY_GETCURSTATUS = 481


  /*OP_CLASS_SYS */
  final val OP_SYS_GETALLDB = 500
  final val OP_SYS_GETDBBYID = 501
  final val OP_SYS_ALTERDB = 502
  final val OP_SYS_GETTABLEBYDBID = 503
  final val OP_SYS_GETTABLESCHEMA = 504
  final val OP_SYS_NEWDB = 505
  final val OP_SYS_DROPDB = 506
  final val OP_SYS_NEWTABLE = 507
  final val OP_SYS_DROPTABLE = 508
  final val OP_SYS_ALTERTABLE = 509
  final val OP_SYS_GETINFO = 510
  final val OP_SYS_GETDBBYUUID = 511
  final val OP_SYS_GETPMDB = 512
  final val OP_SYS_GETPMTABLE = 513
  final val OP_SYS_NEWFIFOQUE = 514
  final val OP_SYS_EMPTYDB = 515
  final val OP_SYS_GETDBIDBYNAME = 516
  final val OP_SYS_GETTIDBYNAME = 517
  final val OP_SYS_GETNETTABLE = 518
  final val OP_SYS_GETNETDB = 519
  final val OP_SYS_CHANGECOLDEF = 520
  final val OP_SYS_GETALLNETPMTABLE = 521
  final val OP_SYS_GETTABLEPROPBYDTID = 522
  final val OP_SYS_GETALLCODETABLE = 523


  /* OP_CLASS_LOG opcode */
  final val OP_LOG_ADD = 300
  final val OP_LOG_ADDQUALITYCHECK = 301
  final val OP_LOG_ERRLOG_DEL = 303
  final val OP_LOG_ERRLOG_GETALL = 304
  final val OP_LOG_ERRLOG_SETMODE = 305

  /* OP_CLASS_ADM opcode */
  final val OP_ADM_ADDPARAMETER = 350
  final val OP_ADM_ADDDB = 351
  final val OP_ADM_MODIFYDB = 352
  final val OP_ADM_GETDB = 353
  final val OP_ADM_GETDBBYID = 354
  final val OP_ADM_ADDMATCHSVRCTRL = 355
  final val OP_ADM_GETMATCHSVRCTRL = 356
  final val OP_ADM_LIBCLEAR = 360
  final val OP_ADM_CREATEDB = 361
  final val OP_ADM_GETEXFMATCHDB = 362
  final val OP_ADM_DELLOG = 363
  final val OP_ADM_GETPARAMETER = 364
  final val OP_ADM_GETCFGEXF = 365
  final val OP_ADM_SETCFGEXF = 366
  final val OP_ADM_GETCFGMATCH = 367
  final val OP_ADM_CHECKDB = 369
  final val OP_ADM_GETSUBSERVER = 380
  final val OP_ADM_GETSUBSVRCFG = 381
  final val OP_ADM_DBLOG_GET = 400
  final val OP_ADM_DBLOG_DEL = 401
  final val OP_ADM_DBLOG_RETR = 402
  final val OP_ADM_VERIFYLOG_ADD = 405
  final val OP_ADM_VERIFYLOG_DEL = 406
  final val OP_ADM_VERIFYLOG_GET = 407
  final val OP_ADM_VERIFYLOG_RETR = 408
  final val OP_ADM_VERIFYLOG_UPDATE = 409
  // range[420, 429] reserved for user auth log
  final val OP_ADM_USERAUTHLOG_GET = 420
  // range[430, 439] reserved for dbrun log.
  final val OP_ADM_DBRUNLOG_GET = 430
  // range[440, 449] reserved for qual check log
  final val OP_ADM_QUALCHECKLOG_ADD = 440
  final val OP_ADM_QUALCHECKLOG_DEL = 441
  final val OP_ADM_QUALCHECKLOG_UPDATE = 442
  final val OP_ADM_QUALCHECKLOG_GET = 444
  final val OP_ADM_QUALCHECKLOG_FINCHK = 445
  final val OP_ADM_QUALCHECKLOG_GETWORK = 446	// get to work
  final val OP_ADM_QUALCHECKLOG_ABTWORK = 447	// abort work

  // range[450, 459] reserved for work log
  final val OP_ADM_WORKLOG_ADD = 450
  final val OP_ADM_WORKLOG_DEL = 451
  final val OP_ADM_WORKLOG_UPDATE = 452
  final val OP_ADM_WORKLOG_RETR = 453
  final val OP_ADM_WORKLOG_GET = 454
  // range[460, 469] reserved for mnt edit info log.
  final val OP_ADM_MNTEDITLOG_ADD = 460
  final val OP_ADM_MNTEDITLOG_DEL = 461
  final val OP_ADM_MNTEDITLOG_UPDATE = 462
  final val OP_ADM_MNTEDITLOG_RETR = 463
  final val OP_ADM_MNTEDITLOG_GET = 464
  // range [470, 479] reserved for exf error log
  final val OP_ADM_EXFERRLOG_ADD = 470
  final val OP_ADM_EXFERRLOG_DEL = 471
  final val OP_ADM_EXFERRLOG_UPDATE = 472
  final val OP_ADM_EXFERRLOG_RETR = 473
  final val OP_ADM_EXFERRLOG_GET = 474

  // range [480, 489] reserved for quality check log
  final val OP_ADM_QUALCHKWORKLOG_ADD = 480
  final val OP_ADM_QUALCHKWORKLOG_DEL = 481
  final val OP_ADM_QUALCHKWORKLOG_GET = 482

  // range [490, 499] reserved for check work log(check and recheck).
  final val OP_ADM_QRYCHECKLOG_ADD = 490
  final val OP_ADM_QRYCHECKLOG_DEL = 491
  final val OP_ADM_QRYCHECKLOG_GET = 492

  // range [500, 509] reserved for submit log(query)
  final val OP_ADM_QRYSUBMITLOG_ADD = 500
  final val OP_ADM_QRYSUBMITLOG_DEL = 501
  final val OP_ADM_QRYSUBMITLOG_GET = 502

  // range [510, 519] reserved for recheck log(query)
  final val OP_ADM_QRYRECHECKLOG_ADD = 510
  final val OP_ADM_QRYRECHECKLOG_DEL = 511
  final val OP_ADM_QRYRECHECKLOG_GET = 512


  // range [520, 529] reserved for search log(query)
  final val OP_ADM_QRYSEARCHLOG_ADD = 520
  final val OP_ADM_QRYSEARCHLOG_DEL = 521
  final val OP_ADM_QRYSEARCHLOG_GET = 522

  // range [530, 539] reserved for tplp associate
  final val OP_ADM_TPLP_ASSOCIATE_ADD = 530
  final val OP_ADM_TPLP_ASSOCIATE_DEL = 531
  final val OP_ADM_TPLP_ASSOCIATE_GET = 532
  final val OP_ADM_TPLP_ASSOCIATE_UPDATE = 533

  // range [540, 549] reserved for tplp unmatch.
  final val OP_ADM_TPLP_UNMATCH_ADD = 540
  final val OP_ADM_TPLP_UNMATCH_DEL = 541
  final val OP_ADM_TPLP_UNMATCH_GET = 542

  // range [550, 559] reserved for tplp fpx que
  final val OP_ADM_TPLP_FPXQUE_ADD = 550
  final val OP_ADM_TPLP_FPXQUE_DEL = 551
  final val OP_ADM_TPLP_FPXQUE_GET = 552
  final val OP_ADM_TPLP_FPXQUE_UPDATE = 553

  // range [560, 569] reserved for tplp fpx log
  final val OP_ADM_TPLP_FPXLOG_ADD = 560
  final val OP_ADM_TPLP_FPXLOG_DEL = 561
  final val OP_ADM_TPLP_FPXLOG_GET = 562

  // range [570, 579] reserved for query fpx que
  final val OP_ADM_QUERY_FPXQUE_ADD = 570
  final val OP_ADM_QUERY_FPXQUE_DEL = 571
  final val OP_ADM_QUERY_FPXQUE_GET = 572
  final val OP_ADM_QUERY_FPXQUE_UPDATE = 573

  // range [580, 589] reserved for query fpx log
  final val OP_ADM_QUERY_FPXLOG_ADD = 580
  final val OP_ADM_QUERY_FPXLOG_DEL = 581
  final val OP_ADM_QUERY_FPXLOG_GET = 582

  // rang [590, 609] reserved for 指纹信息采集系统的数据交换状态表、信息反馈表
  final val OP_ADM_LFIC_DTSTATUS_ADD = 590
  final val OP_ADM_LFIC_DTSTATUS_GET = 591
  final val OP_ADM_LFIC_DTSTATUS_UPDATE = 592
  final val OP_ADM_LFIC_DTSTATUS_DEL = 593

  final val OP_ADM_LFIC_FBINFO_ADD = 600
  final val OP_ADM_LFIC_FBINFO_GET = 601
  final val OP_ADM_LFIC_FBINFO_UPDATE = 602
  final val OP_ADM_LFIC_FBINFO_DEL = 603



  /* OP_CLASS_BLOB */
  final val OP_BLOB_GETPROP = 2000
  final val OP_BLOB_GET = 2001
  final val OP_BLOB_ADD = 2002
  final val OP_BLOB_UPDATE = 2003
  final val OP_COL_MULTIGETBYKEY = 2004
  final val OP_COL_MULTIGETBYSID = 2005
  final val OP_COL_MULTIUPDATEBYKEY = 2006
  final val OP_COL_MULTIUPDATEBYSID = 2007
  final val OP_COL_GETBYKEY = 2008
  final val OP_COL_GETBYSID = 2009
  final val OP_COL_UPDATEBYKEY = 2010
  final val OP_COL_UPDATEBYSID = 2011

  /* OP_CLASS_BACKUP opcode */
  final val OP_BACKUP_GETSKMLOG = 400
  final val OP_BACKUP_GETAUDITLOG = 401
  final val OP_BACKUP_GETBKPLOG = 402
  final val OP_BACKUP_GETDBSKMLOG = 403
  final val OP_BACKUP_SVRCFGADD = 404
  final val OP_BACKUP_SVRCFGMOD = 405
  final val OP_BACKUP_SVRCFGDEL = 406
  final val OP_BACKUP_SVRCFGGETALL = 407

  final val OP_BACKUP_TASKADD = 410
  final val OP_BACKUP_TASKMOD = 411
  final val OP_BACKUP_TASKDEL = 412
  final val OP_BACKUP_TASKGETALL = 413
  final val OP_BACKUP_SETUPDATEFLAG = 414

  /* OP_CLASS_STATISTICS opcode */
  final val OP_STATISTICS_ONTPLIB = 450
  final val OP_STATISTICS_ONLPLIB = 451
  final val OP_STATISTICS_ONUSERWORK = 452
  final val OP_STATISTICS_ONQUERY = 453

  /* OP_CLASS_MISC opcode */
  final val OP_MISC_GETBARHEAD = 500
  final val OP_MISC_RETRONUSERWORK = 501
  final val OP_MISC_RETRONLIB = 502
  final val OP_MISC_RETRONLOG = 503
  final val OP_MISC_SERVERINFO = 504
  final val OP_MISC_GETCOMPRESSMETHOD = 505
  final val OP_MISC_GETSIMPLEINFO = 506
  final val OP_MISC_SCHFILE = 507
  final val OP_MISC_GETBARLEN = 508
  final val OP_MISC_GETALLDBITEM = 509
  final val OP_MISC_DELFILEBYFN = 511
  final val OP_MISC_GETSEARCHRESULT = 515
  final val OP_MISC_GETDYNMATCHOPT = 516
  final val OP_MISC_GETSERVERID = 517
  final val OP_MISC_GETCONVBARRULE = 519
  final val OP_MISC_BUILDCORRECTBAR = 520	// give a bar to construct a new barcode
  final val OP_MISC_TIMETTOSTRING = 521
  final val OP_MISC_GETSERVERCAP = 522
  final val OP_MISC_MNTFEATTABSIZE = 523
  final val OP_MISC_GETSERVERTIME = 524
  final val OP_MISC_GETDEFDBID = 525
  final val OP_MISC_KEY2SID = 526
  final val OP_MISC_SID2KEY = 527
  final val OP_MISC_DELBYSID = 528	// delete a record by sid
  final val OP_MISC_DELBYKEY = 529	// del by key
  final val OP_MISC_RETRKEY = 530
  final val OP_MISC_GETMAXSID = 531
  final val OP_MISC_KEYARRAY2SID = 532
  final val OP_MISC_SIDARRAY2KEY = 533
  final val OP_MISC_GETROWCOUNT = 534
  final val OP_MISC_GETMAXSIDEX = 535
  final val OP_MISC_CHECKBLOB = 544
  final val OP_MISC_NETSPEEDTEST = 545

  final val OP_MISC_GETPARAM = 546
  final val OP_MISC_SETPARAM = 547
  final val OP_MISC_GETINSTUSER = 548
  final val OP_MISC_ISHOSTALIVE = 549	// whether host is running
  final val OP_MISC_ISAFISALIVE = 550	// whether there is afis system on given host and given port
  final val OP_MISC_INTDISPTIMECONV = 551	// conversion between UTC time and locale time.
  final val OP_MISC_HASLICENSE = 552
  final val OP_MISC_GETSVRFNBYID = 553	// get server file name by file id.
  final val OP_MISC_GETMEMSTATUS = 554
  final val OP_MISC_GETPROCESSINFO = 555
  final val OP_MISC_GETMAINPATH = 556	// get gafis main path.
  final val OP_MISC_GETDISKUSAGE = 557	// given path, calculate disk usage.
  final val OP_MISC_GETSIDCOUNT = 558
  final val OP_MISC_GETSYSFILENAME = 559
  final val OP_MISC_GETPATHDELIMIT = 560


  /* OP_CLASS_RMTINPUTCENSOR */
  final val OP_RMTINPUTCENSOR_GETSTATISTICS = 600
  final val OP_RMTINPUTCENSOR_FINISHCENSOR = 601
  final val OP_RMTINPUTCENSOR_FINISHCENSORBYC = 602
  final val OP_RMTINPUTCENSOR_RETRIEVE = 603

  /* OP_CLASS_RMTCLIENT */
  final val OP_RMTCLI_SVRCFG_ADD = 650
  final val OP_RMTCLI_SVRCFG_DEL = 651
  final val OP_RMTCLI_SVRCFG_GETALL = 652
  final val OP_RMTCLI_GRPCFG_ADD = 660
  final val OP_RMTCLI_GRPCFG_DEL = 661
  final val OP_RMTCLI_GRPCFG_GETALL = 662
  final val OP_RMTCLI_BARQUE_ADD = 670
  final val OP_RMTCLI_BARQUE_DEL = 671
  final val OP_RMTCLI_BARQUE_GETALL = 672
  final val OP_RMTCLI_CSSVRCFG_GET = 673
  final val OP_RMTCLI_GETEXISTBAR = 674
  final val OP_RMTCLI_BARQUE_GETINCBAR = 675
  final val OP_RMTCLI_BARQUE_GETHIST = 676

  final val OP_RMTCLI_CPQ_ADD = 680
  final val OP_RMTCLI_CPQ_DEL = 681
  final val OP_RMTCLI_CPQ_GETALL = 682
  final val OP_RMTCLI_CPQ_GETCPQITEMS = 683
  final val OP_RMTCLI_CPQ_CLEARCENSORFLAG = 684

  final val OP_RMTCLI_ISSERVEROK = 690
  final val OP_RMTCLI_GETDB = 691
  final val OP_RMTCLI_GETDBBYID = 692
  final val OP_RMTCLI_GETSERVERID = 693
  final val OP_RMTCLI_STOPSLEEP = 694
  final val OP_RMTCLI_SETCPQOPT = 695
  final val OP_RMTCLI_GETAUADDCFG = 696
  final val OP_RMTCLI_WRITEAUADDCFG = 697
  final val OP_RMTCLI_GETGLBVAR = 698
  final val OP_RMTCLI_SETGLBVAR = 699
  final val OP_RMTCLI_CLEARERROR = 700

  final val OP_RMTCLI_GETQRYCTRL = 701
  final val OP_RMTCLI_MODQRYCTRL = 702
  final val OP_RMTCLI_REFRESHQRYCTRL = 703	// according the change of user and rmt server to modify

  /* OP_CLASS_EXF */
  final val OP_EXF_GETTOWORK = 4500
  final val OP_EXF_FINISHWORK = 4501
  final val OP_EXF_ABORTWORK = 4502
  final val OP_LPEXF_GETTOWORK = 4505
  final val OP_LPEXF_FINISHWORK = 4506
  final val OP_LPEXF_ABORTWORK = 4507

  /* OP_CLASS_TEST */
  final val OP_TEST_CALLBACK = 950

  /* OP_CLASS_CURSOR */
  final val OP_CURSOR_OPEN = 2501
  final val OP_CURSOR_DESTROY = 2502
  final val OP_CURSOR_GOTO = 2503
  final val OP_CURSOR_NEXT = 2504
  final val OP_CURSOR_PREV = 2505
  final val OP_CURSOR_FIRST = 2506
  final val OP_CURSOR_LAST = 2507
  final val OP_CURSOR_SETDIR = 2508
  final val OP_CURSOR_GETSIDARRAY = 2509

  /* OP_CLASS_TABLE */
  final val OP_TABLE_GETROWDATA = 3001
  final val OP_TABLE_GETROWDATABYSIDARRAY = 3002
  final val OP_TABLE_GETMODSIDARRAY = 3003
  final val OP_TABLE_MIRRORSCHMA = 3004
  final val OP_TABLE_CALCOPYGETDATA = 3005
  final val OP_TABLE_CALCOPYGETDATABYSID = 3006
  final val OP_TABLE_ROWEXISTWITHKEY = 3007
  final val OP_TABLE_ROWEXISTWITHSID = 3008
  final val OP_TABLE_SELECT = 3009
  final val OP_TABLE_LOADUSERDEFDATA = 3010
  final val OP_TABLE_SAVEUSERDEFDATA = 3011
  final val OP_TABLE_DATASIZEBYSIDARRAY = 3012
  final val OP_TABLE_GETSELRESBYSID = 3013
  final val OP_TABLE_SAVESELRES = 3014
  final val OP_TABLE_SAVEROWDATABYSKM = 3015
  final val OP_TABLE_GETMODSIDBYNAME = 3016
  final val OP_TABLE_COPY = 3017
  final val OP_TABLE_DELROW = 3018
  final val OP_TABLE_SELECTSID = 3019
  final val OP_TABLE_MODCOLPROP = 3020
  final val OP_TABLE_GETDUPCOLSCHEMA = 3021
  final val OP_TABLE_CHANGEKEY = 3022
  final val OP_TABLE_MULTICHANGEKEY = 3023
  final val OP_TABLE_MULTIDELROWBYKEY = 3024
  final val OP_TABLE_MULTIDELROWBYSID = 3025
  final val OP_TABLE_MODLOG_ADD = 3026
  final val OP_TABLE_MODLOG_DEL = 3027
  final val OP_TABLE_MODLOG_GETALL = 3028
  final val OP_TABLE_MODLOG_MOD = 3029
  final val OP_TABLE_GETOPTIMESTA = 3030	// get operations statistics.
  final val OP_TABLE_HASKEYUNIQCOL = 3031
  final val OP_TABLE_GETALLCOL = 3032
  final val OP_TABLE_TESTGETCOLSPEED = 3033
  final val OP_TABLE_GETNULLFLAG = 3034
  final val OP_TABLE_GETNETROWCOUNT = 3035
  final val OP_TABLE_GETPALMROWCNT = 3036
  final val OP_TABLE_GETSTATISTICS = 3037
  final val OP_TABLE_MULTIKEY2SID = 3038
  final val OP_TABLE_SIDRETR = 3039
  final val OP_TABLE_SIMPISARETR = 3040
  final val OP_TABLE_GETDETAILSTA = 3041
  final val OP_TABLE_ADDBYSELRES = 3042
  final val OP_TABLE_UPDATEBYSELRES = 3043
  final val OP_TABLE_ATTACHCHILDKEY = 3044
  final val OP_TABLE_DETACHCHILDKEY = 3045
  final val OP_TABLE_GETCOLDATA = 3046
  final val OP_TABLE_EXPANDTOSID = 3047
  final val OP_TABLE_RETRBYINDEX = 3048	// retrieve by index.
  final val OP_TABLE_UNIQUEVALEXIST = 3049	// check whether unique value has been used.


  /* OP_CLASS_CODETABLE */
  final val OP_CODETABLE_GETALL = 3050
  final val OP_CODETABLE_ADD = 3051
  final val OP_CODETABLE_GET = 3052
  final val OP_CODETABLE_DEL = 3053
  final val OP_CODETABLE_UPDATE = 3054
  final val OP_CODETABLE_EXIST = 3055
  final val OP_CODETABLE_MULTIADD = 3066
  final val OP_CODETABLE_DELALL = 3067

  /* OP_CLASS_MSG */
  final val OP_MSG_RETR = 3100
  final val OP_MSG_ADD = 3101
  final val OP_MSG_DEL = 3102
  final val OP_MSG_SETSTATE = 3103
  final val OP_MSG_GET = 3104

  /* OP_CLASS_MOBICASE */
  final val OP_MOBICASE_ADD = 3150
  final val OP_MOBICASE_DEL = 3151
  final val OP_MOBICASE_GET = 3152
  final val OP_MOBICASE_RETR = 3153
  final val OP_MOBICASE_UPDATE = 3154

  /* OP_CLASS_PARAMETER */
  final val OP_PARAMETER_ADD = 3200
  final val OP_PARAMETER_DEL = 3201
  final val OP_PARAMETER_GET = 3202
  final val OP_PARAMETER_GETALL = 3203
  final val OP_PARAMETER_UPDATE = 3204
  final val OP_PARAMETER_EXIST = 3205

  /* OP_CLASS_MNTSVR*/
  final val OP_MNTSVR_EXTRACT = 9001
  final val OP_MNTSVR_MATCHONE2ONE = 9002

  /* OP_CLASS_FILE */
  final val OP_FILE_GETFILELISTINDIR = 9050
  final val OP_FILE_GETFILEINFO = 9051
  final val OP_FILE_GETFILE = 9052
  final val OP_FILE_GETFILEINPART = 9053
  final val OP_FILE_ISFILEEXIST = 9054
  final val OP_FILE_GETWHOLEDIR = 9055
  final val OP_FILE_MAKEDIR = 9056
  final val OP_FILE_PUTFILE = 9060
  final val OP_FILE_PUTFILEINPART = 9061
  final val OP_FILE_PUTWHOLEDIR = 9062
  final val OP_FILE_DELFILE = 9063
  final val OP_FILE_DELDIR = 9064
  final val OP_FILE_CHANGEDIR = 9066
  final val OP_FILE_GETVERSION = 9067
  final val OP_FILE_RECURFILELIST = 9068

  final val OP_FILE_RUNFILE = 9070

  /* OP_CLASS_HOTUPDATE */
  final val OP_HOTU_GETNEXTPATCHFILE = 10000
  final val OP_HOTU_ADDNEXTPATCHFILE = 10001
  final val OP_HOTU_GETPATCHINFO = 10002
  final val OP_HOTU_GETTEMPPATCHFILE = 10003
  final val OP_HOTU_GETALLPATCHITEM = 10004
  final val OP_HOTU_SETPATCHINFO = 10005
  final val OP_HOTU_PATCHDEL = 10006
  final val OP_HOTU_NEWPATCHADDED = 10007
  final val OP_HOTU_NEWHISTFILE = 10008
  final val OP_HOTU_GETPATCHPATH = 10009
  final val OP_HOTU_APPENDPATCHITEM = 10010	// append patch item
  final val OP_HOTU_REMOVEPATCH = 10011	// remove patch
  final val OP_HOTU_PLATFORMPATCHPATH = 10012	// get platform related patch path.
  final val OP_HOTU_PLATFORMPATCHEXIST = 10013	// check whether platform patch exist

  /* OP_CLASS_NMDB */
  final val OP_NMDB_LOADUSERDEFDATA = 10050
  final val OP_NMDB_SAVEUSERDEFDATA = 10051
  final val OP_NMDB_EXISTBYUUID = 10052
  final val OP_NMDB_GENBKPDBNAME = 10053	// generate backup database name
  final val OP_NMDB_PATHEXIST = 10054	// judge whether path exists
  final val OP_NMDB_CREATEBYNETDB = 10055
  final val OP_NMDB_CREATETABLEBYNET = 10056
  final val OP_NMDB_GETTABLEPROPBYUUID = 10057

  /*  OP_CLASS_SVRMISC	136	// server misc */
  final val OP_SVRMISC_SHOWINFO = 10100
  final val OP_SVRMISC_TESTCONSPEED = 10101
  final val OP_SVRMISC_GETTXTINFO = 10102

  /* OP_CLASS_TASK   150 */
  final val OP_TASK_GET = 10150
  final val OP_TASK_FINISH = 10151
  final val OP_TASK_ABORT = 10152

  /* OP_CLASS_MU    151 */
  final val OP_MU_REGISTER = 10250
  final val OP_MU_UNREGISTER = 10251
  final val OP_MU_GETALLMU = 10252
  final val OP_MU_REMOVE = 10253
  final val OP_MU_UPDATE = 10254
  final val OP_MU_GETHOSTINFO = 10255
  final val OP_MU_GETALLNETPMTABLE = 10256
  final val OP_MU_RESUBLIB = 10257
  final val OP_MU_GETPARAMFRMSVR = 10258
  final val OP_MU_GETQRYLIST = 10259
  final val OP_MU_GETTASKLIST = 10260
  final val OP_MU_GETQRYDETAIL = 10261
  final val OP_MU_GETTASKDETAIL = 10262
  final val OP_MU_PRINTQRYLIST = 10263
  final val OP_MU_PRINTTASKLIST = 10264
  final val OP_MU_PRINTQRYDETAIL = 10265
  final val OP_MU_PRINTTASKDETAIL = 10266
  final val OP_MU_GETTASKSEG = 10267
  final val OP_MU_PRINTSUBLIBTABLE = 10268
  final val OP_MU_PRINTINFO = 10269

  /* OP_CLASS_GMC 152 */
  final val OP_GMC_OPENCONN = 10200
  final val OP_GMC_REGISTERTABLE = 10201
  final val OP_GMC_REGISTERMU = 10202
  final val OP_GMC_UNREGISTERMU = 10203
  final val OP_GMC_UNREGISTERTABLE = 10204
  final val OP_GMC_UPDATEMU = 10205
  final val OP_GMC_UPDATETABLE = 10206
  final val OP_GMC_PRINTINFO = 10207

  /* OP_CLASS_MCOP */
  final val OP_MCOP_DATARECVED = 10300
  final val OP_MCOP_ISCONNOK = 10301

  /* OP_CLASS_PARAMADM */
  final val OP_PARAMADM_GET = 10350
  final val OP_PARAMADM_SET = 10351
  final val OP_PARAMADM_GETALL = 10352

  /* OP_CLASS_LSN */
  final val OP_LSN_ADD = 10400
  final val OP_LSN_DEL = 10401
  final val OP_LSN_UPDATE = 10402
  final val OP_LSN_RPTGET = 10403	// report our signature and get renewed serial no.
  // used for both remote and local license server.
  final val OP_LSN_GET = 10405

  /* OP_CLASS_QRYASSIGN */
  final val OP_QRYASSIGN_ADD = 10450
  final val OP_QRYASSIGN_DEL = 10451
  final val OP_QRYASSIGN_START = 10452
  final val OP_QRYASSIGN_STOP = 10453
  final val OP_QRYASSIGN_GET = 10454
  final val OP_QRYASSIGN_GETALL = 10455
  final val OP_QRYASSIGN_IS_SUPPORT_ASSIGN = 10456
  final val OP_QRYASSIGN_IS_USER_ACTIVE = 10457

  /**
   * 人像查询
   */
  /* OP_CLASS_FACEQUERY opcode */
  final val OP_FACEQUERY_ADD = 800
  final val OP_FACEQUERY_UPDATE = 801
  final val OP_FACEQUERY_DEL = 802
  final val OP_FACEQUERY_RETR = 803
  final val OP_FACEQUERY_GET = 804
  final val OP_FACEQUERY_SUBMIT = 805
  final val OP_FACEQUERY_GETTOSEARCH = 806
  final val OP_FACEQUERY_FINISHSEARCH = 807
  final val OP_FACEQUERY_ABORTSEARCH = 808
  final val OP_FACEQUERY_GETTOCHECK = 809
  final val OP_FACEQUERY_FINISHCHECK = 810
  final val OP_FACEQUERY_ABORTCHECK = 811
  final val OP_FACEQUERY_DELBYSID = 812
  final val OP_FACEQUERY_CHANGESTATUS = 813
  final val OP_FACEQUERY_GETCURSTATUS = 814

}
