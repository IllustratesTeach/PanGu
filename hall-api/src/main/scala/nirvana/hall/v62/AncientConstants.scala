package nirvana.hall.v62

import java.nio.charset.Charset

/**
 * constants for ancient system
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-30
 */
object AncientConstants {
  final val UTF8_ENCODING=Charset.forName("GBK")
  final val GBK_ENCODING=Charset.forName("GBK")

  /* all operation are divided into several groups */
  val OP_CLASS_TPLIB   = 101
  val OP_CLASS_LPLIB   = 102
  val OP_CLASS_PARAMATCH = 103 /* parallel match */
  val OP_CLASS_USER  = 104
  val OP_CLASS_QUERY   = 105
  val OP_CLASS_LOG   = 106
  val OP_CLASS_ADM   = 107
  val OP_CLASS_BACKUP  = 108
  val OP_CLASS_STATISTICS= 109
  val OP_CLASS_MISC  = 110
  val OP_CLASS_RMTINPUTCENSOR  = 111
  val OP_CLASS_RMTCLIENT = 112
  val OP_CLASS_FIFOQUE = 113
  val OP_CLASS_CASE  = 114
  val OP_CLASS_PERSON  = 115
  val OP_CLASS_SYS   = 116
  val OP_CLASS_BLOB  = 117
  val OP_CLASS_EXF   = 118
  val OP_CLASS_SEARCH  = 119
  val OP_CLASS_CODETABLE = 121
  val OP_CLASS_MSG   = 122
  val OP_CLASS_MOBICASE= 123
  val OP_CLASS_PARAMETER = 124
  val OP_CLASS_VERIFYLOG = 125
  val OP_CLASS_DBLOG   = 126
  val OP_CLASS_CURSOR  = 127
  val OP_CLASS_MNTSVR  = 128
  val OP_CLASS_TABLE   = 130

  // define opclass for search server
  val OP_CLASS_TASK  = 150
  val OP_CLASS_MU    = 151

  val OP_CLASS_GMC   = 152
  val OP_CLASS_MCOP  = 153
  val OP_CLASS_FILE  = 131
  val OP_CLASS_HOTUPDATE = 132
  val OP_CLASS_NMDB  = 135
  val OP_CLASS_SVRMISC = 136 // server misc
  val OP_CLASS_PARAMADM= 137
  val OP_CLASS_LSN   = 138
  val OP_CLASS_QRYASSIGN = 140
  val OP_CLASS_FACEQUERY = 161
  val OP_CLASS_TEST  = 254
  val OP_CLASS_EXIT  = 255


  /* OP_CLASS_TPLIB opcode */
  val OP_TPLIB_ADD   = 50
  val OP_TPLIB_UPDATE  = 51
  val OP_TPLIB_DEL   = 52
  val OP_TPLIB_RETR  = 53
  val OP_TPLIB_EXIST   = 54
  val OP_TPLIB_GET   = 55
  val OP_TPLIB_REBUILDFEAT = 56
  val OP_TPLIB_DELIMGIFCPREXIST= 57  // delete img if cpr exists
  val OP_TPLIB_DELPALM = 58
  val OP_TPLIB_GETMICBSIG= 59
  val OP_TPLIB_MICBSIGMATCH= 60
  val OP_TPLIB_GETAUTOMISPERSONID= 61
  val OP_TPLIB_UNMATCH_ADD = 62
  val OP_TPLIB_UNMATCH_DEL = 63
  val OP_TPLIB_UNMATCH_GET = 64
  val OP_TPLIB_GENAUTOMISPID = 65

  /* OP_CLASS_LPLIB */
  val OP_LPLIB_ADD   = 100
  val OP_LPLIB_UPDATE  = 101
  val OP_LPLIB_DEL   = 102
  val OP_LPLIB_RETR  = 103
  val OP_LPLIB_EXIST   = 104
  val OP_LPLIB_GET   = 105
  val OP_LPLIB_GETMICBSIG  = 106
  val OP_LPLIB_MICBSIGMATCH= 107


  val OP_LPLIB_UNMATCH_ADD = 108
  val OP_LPLIB_UNMATCH_DEL = 109
  val OP_LPLIB_UNMATCH_GET = 110

  val OP_LPGROUP_ADD     = 115
  val OP_LPGROUP_DEL     = 116
  val OP_LPGROUP_GET     = 117
  val OP_LPGROUP_UPDATE  = 118


  /* OP_CLASS_CASE */
  val OP_CASE_ADD      = 250
  val OP_CASE_UPDATE     = 251
  val OP_CASE_DEL      = 252
  val OP_CASE_RETR     = 253
  val OP_CASE_EXIST    = 254
  val OP_CASE_GET      = 255

  val OP_CASEGROUP_ADD   = 260
  val OP_CASEGROUP_DEL   = 261
  val OP_CASEGROUP_UPDATE  = 262
  val OP_CASEGROUP_GET   = 263

  val OP_CASE_CLEARQRYASSIGN = 265
  val OP_CASE_GETQRYASSIGN = 266

  /* OP_CLASS_PERSON */
  val OP_PERSON_ADD    = 300
  val OP_PERSON_UPDATE   = 301
  val OP_PERSON_DEL    = 302
  val OP_PERSON_RETR     = 303
  val OP_PERSON_EXIST    = 304
  val OP_PERSON_GET    = 305



  /* OP_CLASS_QUERY opcode */
  val OP_QUERY_ADD     = 450
  val OP_QUERY_UPDATE    = 451
  val OP_QUERY_DEL     = 452
  val OP_QUERY_RETR    = 453
  val OP_QUERY_GET     = 455
  val OP_QUERY_GETTOSEARCH = 460
  val OP_QUERY_FINISHSEARCH= 461
  val OP_QUERY_ABORTSEARCH = 462
  val OP_QUERY_GETTOCHECK  = 470
  val OP_QUERY_FINISHCHECK = 471
  val OP_QUERY_ABORTCHECK  = 472
  val OP_QUERY_RESETINMEM  = 473
  val OP_QUERY_GETESTIMATEFINTIME= 474
  val OP_QUERY_CHANGEPRI     = 475
  val OP_QUERY_SUBMIT      = 476
  val OP_QUERY_MISGETSEARCH  = 477
  val OP_QUERY_MISFINSEARCH  = 478
  val OP_QUERY_MISABORTSEARCH  = 479
  val OP_QUERY_WAITFINISH    = 480 // wait query to finish
  val OP_QUERY_GETCURSTATUS  = 481




  // can be or'ed
  final val GAMIC_ITEMFLAG_MNT      =0x1
  final val GAMIC_ITEMFLAG_IMG      =0x2
  final val GAMIC_ITEMFLAG_CPR      =0x4
  final val GAMIC_ITEMFLAG_BIN      =0x8

  // cann't be or'ed
  final val GAMIC_ITEMTYPE_FINGER   =0x1 // rolled finger.
  final val GAMIC_ITEMTYPE_PALM     =0x2 // palm part.
  final val GAMIC_ITEMTYPE_PLAINFINGER  =0x3 // plain finger using for information store.
  final val GAMIC_ITEMTYPE_FACE     =0x4 // ITEMDATA:1-FRONT, 2 noseleftSIDE, 3 noserightSIDE, GTPIO_ITEMINDEX_XXX
  final val GAMIC_ITEMTYPE_DATA     =0x5 // card bin data.
  // =0x6 is not used, it's reserved for text, refer to GTPIO_ITEMTYPE
  final val GAMIC_ITEMTYPE_SIGNATURE  =0x7
  final val GAMIC_ITEMTYPE_TPLAIN   =0x8 // plain finger, PLAINFINGER is used to store data.
  // and TPLAIN is used to do search
  final val GAMIC_ITEMTYPE_EXTRAFINGER  =0x9 // six finger
  final val GAMIC_ITEMTYPE_HANDPART   =0xa // hand part except palm
  final val GAMIC_ITEMTYPE_VOICE    =0xb
}
