package nirvana.hall.c.services.gftplib

import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services.gbaselib.gbasedef.GAKEYSTRUCT
import nirvana.hall.c.services.ghpcbase.ghpcdef.{AFISDate, AFISDateTime}
import nirvana.hall.c.services.gloclib.galoclp.{GLPCARDINFOSTRUCT, GCASEINFOSTRUCT}
import nirvana.hall.c.services.gloclib.galoctp.GTPCARDINFOSTRUCT
import nirvana.hall.c.services.gloclib.glocdef.GATEXTITEMSTRUCT
import nirvana.hall.c.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-15
 */
object gftptdef {


  final val TEXTITEM_MAXCOUNT = 64
  final val TEXTITEM_COMMENTLEN = 512



  class FPTLPRECORD2INFOSTRUCT extends AncientData
  {
    var nRecLen:Long = _ ;			//this record len
  var nRecType:Byte = _ ;			//record type,must is '2'
  @Length(6)
  var nRecID:String = _ ;			//record id
  var szSystemType:Int = _ ;	//system type
  @Length(23)
  var szCaseID:String = _ ;		//case id
  @Length(20)
  var szFingerID:String = _ ;		//finger id
  @Length(6)
  var szCaseType1:String = _ ;		//case type 1
  @Length(6)
  var szCaseType2:String = _ ;		//case type 2
  @Length(6)
  var szCaseType3:String = _ ;		//case type 3
  var tCaseOccurDate:Long = _ ;	//case occur date 20021119
  var nCaseLevel:Byte = _ ;			//case level 1,2,3,4,0
  @Length(70)
  var szCaseOccurPlace:String = _ ;	//case occur place
  @Length(12)
  var szCollectUnitCode:String = _ ;	//case collect unit code
  @Length(70)
  var szCollectUnitName:String = _ ;	//case collect unit name
  @Length(30)
  var szCollecter:String = _ ;		//case collect person
  @Length(6)
  var szThread1:String = _ ;			//dubious place thread 1
  @Length(6)
  var szThread2:String = _ ;			//dubious place thread 2
  @Length(6)
  var szThread3:String = _ ;			//dubious place thread 3
  @Length(10)
  var nMoney:String = _ ;				//money

    @Length(TEXTITEM_COMMENTLEN)
    var szComment:String = _ ;			//comment

    var nFingerTotalCount:Short = _ ;	//total finger count in this case
  var nFingerSendCount:Short = _ ;	//send finger count in this case
  } //FPTLPRECORD2INFOSTRUCT;

  class FPTLPFINGERMNTSTRUCT extends AncientData
  {
    @Length(30)
    var szLeavePart:String = _ ;			//leave part
  @Length(10)
  var szAnalyFingerIndex:String = _ ;		//analy finger index
  var nRidgeColor:Byte = _ ;				//ridge line corlor,white:1,blace:2,unknown:0
  var szUnionFingerStart:Short = _ ;		//union finger start no
  var szUnionFingerEnd:Short = _ ;		//union finger end no
  var nExtractMntMethod:Byte = _ ;			//extract finger mnt method, 'A','U','E','M'
  @Length(7)
  var szPatternType:String = _ ;			//finger pattern type
  @Length(5)
  var szFingerDirect:String = _ ;			//finger direction
  @Length(14)
  var szCorePoint:String = _ ;			//center point
  @Length(14)
  var szACorePoint:String = _ ;			//accessorial core point
  @Length(14)
  var szLeftDelta:String = _ ;			//left delta
  @Length(14)
  var szRightDelta:String = _ ;			//right delta
  @Length(3)
  var nMntCount:String = _ ;				//mnt count
  @Length(200 * 9)
  var szMNT:Array[Byte]= _;				//mnt point
  } //FPTLPFINGERMNTSTRUCT;

  class FTPFINGERIMGHEADSTRUCT extends AncientData
  {
    @Length(3)
    var nWidth:String = _ ;			//image width
  @Length(3)
  var nHeight:String = _ ;			//image height
  @Length(3)
  var nResolution:String = _ ;		//image resolution
  var szCompressCode:Int = _ ;	//image compress method code,0000 no compress
  @Length(6)
  var nSize:String = _ ;			//image size
  } //FTPFINGERIMGHEADSTRUCT;

  class FPTFINGERIMGSTRUCT extends AncientData
  {
    var stHead = new FTPFINGERIMGHEADSTRUCT;
    var pData_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pData_Data:Array[Byte] = _ // for pData pointer ,struct:char;
  } //FPTFINGERIMGSTRUCT,FPTLPFINGERIMGSTRUCT,FPTTPFINGERIMGSTRUCT;

  type FPTLPFINGERIMGSTRUCT = FPTFINGERIMGSTRUCT
  type FPTTPFINGERIMGSTRUCT = FPTFINGERIMGSTRUCT

  class FPTUSERDATASTRUCT extends AncientData
  {
    var nSize:Int = _ ;		//user define data len
  var pData_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pData_Data:Array[Byte] = _ // for pData pointer ,struct:char;			//data, point to GAFISUSERDEFDATSTRUCT
  } //FPTUSERDATASTRUCT;

  class FPTLPFINGERSTRUCT extends AncientData
  {
    @Length(6)
    var nFingerInforLen:String = _ ;
    var nSendID:Short = _ ;
    var nFingerSeqNo:Short = _ ;
    @Length(20)
    var szFingerID:String = _ ;
    var stMnt = new FPTLPFINGERMNTSTRUCT;
    var stUser = new FPTUSERDATASTRUCT;
    var stImg = new FPTLPFINGERIMGSTRUCT;
  } //FPTLPFINGERSTRUCT;


  class FPTLPRECORD3INFOSTRUCT extends AncientData
  {
    var nRecLen:Long = _ ;			//this record len
  var nRecType:Byte = _ ;			//record type,must is '3'
  @Length(6)
  var nRecID:String = _ ;			//record id
  var szSystemType:Int = _ ;	//system type
  @Length(23)
  var szPersonID:String = _ ;		//person id
  @Length(20)
  var szCardID:String = _ ;		//finger Card id
  @Length(30)
  var szPersonName:String = _ ;	//person name
  @Length(30)
  var szPersonAliasName:String = _ ;	//person alias name
  var nSex:Byte = _ ;					//person sex
  var szBirthDate:Long = _ ;			//person birth date
  @Length(18)
  var szShenFenID:String = _ ;		//perosn shen fen id
  @Length(6)
  var szBirthAddressCode:String = _ ;	//person birth place code
  @Length(70)
  var szBirthAddress:String = _ ;		//person birth place
  @Length(6)
  var szAddressCode:String = _ ;		//person address code
  @Length(70)
  var szAddress:String = _ ;			//person address
  @Length(20)
  var szPersonType:String = _ ;		//person type
  @Length(6)
  var szCaseType1:String = _ ;			//case type 1
  @Length(6)
  var szCaseType2:String = _ ;			//case type 2
  @Length(6)
  var szCaseType3:String = _ ;			//case type 3
  @Length(12)
  var szPrintUnitCode:String = _ ;		//print unit code
  @Length(70)
  var szPrintUnitName:String = _ ;	//print unit name
  @Length(30)
  var szPrinter:String = _ ;			//printer name
  var szPrintDate:Long = _ ;			//print date
  @Length(TEXTITEM_COMMENTLEN)
  var szComment:String = _ ;			//comment
  var nFingerSendCount:Short = _ ;	//send finger count in this case
  } //FPTLPRECORD3INFOSTRUCT;

  class FPTTPFINGERMNTSTRUCT extends AncientData
  {
    var nExtractMntMethod:Byte = _ ;			//extract finger mnt method, 'A','U','E','M'
  var nPattern1:Byte = _ ;					//finger pattern type 1
  var nPattern2:Byte = _ ;					//finger pattern type 2
  @Length(5)
  var szFingerDirect:String = _ ;			//finger direction
  @Length(14)
  var szCorePoint:String = _ ;			//center point
  @Length(14)
  var szACorePoint:String = _ ;			//accessorial core point
  @Length(14)
  var szLeftDelta:String = _ ;			//left delta
  @Length(14)
  var szRightDelta:String = _ ;			//right delta
  @Length(3)
  var nMntCount:String = _ ;				//mnt count
  @Length(200*9)
  var szMNT:Array[Byte]= _ ;				//mnt point
  } //FPTTPFINGERMNTSTRUCT;

  class FPTTPFINGERSTRUCT extends AncientData
  {
    @Length(6)
    var nFingerInforLen:String = _ ;
    var nSendID:Short = _ ;
    var nFingerIndex:Short = _ ;
    var stMnt = new FPTTPFINGERMNTSTRUCT;
    var stUser = new FPTUSERDATASTRUCT;
    var stImg = new FPTTPFINGERIMGSTRUCT;
  } //FPTTPFINGERSTRUCT;

  class FPTINFOHEADSTRUCT extends AncientData
  {
    @Length(7)
    var szVer:String = _ ;				//file ver , must is "FPT0300"、"FPT0301"、"FPT0400"等
  @Length(12)
  var nFileLen:String = _ ;			//file total len
  var nRecType:Byte = _ ;				//logic record type, must is 1;
  @Length(6)
  var nRec2Count:String = _ ;			//second record count
  @Length(6)
  var nRec3Count:String = _ ;			//third record count
  @Length(14)
  var tSendTime:String = _ ;			//send time,format is 20021119141500
  @Length(12)
  var szRecvUnitCode:String = _ ;		//receive unit code
  @Length(12)
  var szSendUnitCode:String = _ ;		//send unit code
  @Length(70)
  var szSendUnitName:String = _ ;	//send unit name
  @Length(30)
  var szSender:String = _ ;			//send person
  var szSendUnitType:Int = _ ;		//send unit type
  @Length(10)
  var szTaskControlCode:String = _ ;	//task control code
  @Length(TEXTITEM_COMMENTLEN)
  var szComment:String = _ ;		//comment
  var szFileSplitChar:Byte = _ ;		//file split char
  } //FPTINFOHEADSTRUCT;		//size is 697

  class FPTLPRECORD2STRUCT extends AncientData
  {
    var stHead = new FPTLPRECORD2INFOSTRUCT;
    var pFinger_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pFinger_Data:Array[FPTLPFINGERSTRUCT] = _ // for pFinger pointer ,struct:FPTLPFINGERSTRUCT;
  } //FPTLPRECORD2STRUCT;

  class FPTLPRECORD3STRUCT extends AncientData
  {
    var stHead = new FPTLPRECORD3INFOSTRUCT;
    var pFinger_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pFinger_Data:Array[FPTTPFINGERSTRUCT] = _ // for pFinger pointer ,struct:FPTTPFINGERSTRUCT;
  } //FPTLPRECORD3STRUCT;

  class FPTFILESTRUCT extends AncientData
  {
    var stHead = new FPTINFOHEADSTRUCT;
    var pRecord2_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pRecord2_Data:Array[FPTLPRECORD2STRUCT] = _ // for pRecord2 pointer ,struct:FPTLPRECORD2STRUCT;
  var pRecord3_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pRecord3_Data:Array[FPTLPRECORD3STRUCT] = _ // for pRecord3 pointer ,struct:FPTLPRECORD3STRUCT;
  } //FPTFILESTRUCT;		//fpt file struct


  /*-----------gafis fpt struct-------------------*/

  final val GFPT_VERSION_03 = 3
  final val GFPT_VERSION_04 = 4

  class GFPTINFOHEADSTRUCT extends AncientData
  {
    var nVerID:Int = _;		//GFPT_VERSION_XX
  var nRecCount2:Int = _ ;
    var nRecCount3:Int = _ ;
    var nRecCount4:Int = _ ;
    var nRecCount5:Int = _ ;
    var nRecCount6:Int = _ ;
    var nRecCount7:Int = _ ;
    var nRecCount8:Int = _ ;
    var nRecCount9:Int = _ ;
    var nRecCount10:Int = _ ;
    var nRecCount11:Int = _ ;
    var nRecCount12:Int = _ ;
    var nRecCount99:Int = _ ;
    var tSendTime = new AFISDateTime;
    @Length(12)
    var szRecvUnitCode:String = _ ;
    @Length(12)
    var szSendUnitCode:String = _ ;
    @Length(70)
    var szSendUnitName:String = _ ;
    @Length(30)
    var szSender:String = _ ;
    var szSendUnitType:Int = _ ;
    @Length(10)
    var szTaskControlCode:String = _ ;
    @Length(TEXTITEM_COMMENTLEN)
    var szComment:String = _ ;
  } //GFPTINFOHEADSTRUCT;

  class GFPTRECORD2STRUCT extends AncientData
  {
    var stCaseInfo = new GCASEINFOSTRUCT;
    @Length(32)
    var szDefCase:String = _ ;
    var nCardCount:Byte = _ ;
    @Length(3)
    var bnRes:Array[Byte] = _ ;
    var szSystemType:Int = _ ;
    var pCardInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pCardInfo_Data:Array[GLPCARDINFOSTRUCT] = _ // for pCardInfo pointer ,struct:GLPCARDINFOSTRUCT;
  var nIndex:Int = _;		//1,2..
  } //GFPTRECORD2STRUCT;

  class GFPTRECORD3STRUCT extends AncientData
  {
    var stCardInfo = new GTPCARDINFOSTRUCT;
    var nIndex:Int = _;
    var szSystemType:Int = _ ;
  } //GFPTRECORD3STRUCT;


  class GFPTRECORD2STRUCTEX extends AncientData
  {
    var pstRec2_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstRec2_Data:Array[GFPTRECORD2STRUCT] = _ // for pstRec2 pointer ,struct:GFPTRECORD2STRUCT;
  var FileName:Long = _ ;
  } //GFPTRECORD2STRUCTEX;

  class GFPTRECORD3STRUCTEX extends AncientData
  {
    var pstRec3_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstRec3_Data:Array[GFPTRECORD3STRUCT] = _ // for pstRec3 pointer ,struct:GFPTRECORD3STRUCT;
  var FileName:Long = _ ;
  } //GFPTRECORD3STRUCTEX;

  class GFPTFINGERMATCHINFOSTRUCT extends AncientData
  {
    @Length(16)
    var szMatchUnitCode:String = _ ;		//Match unit code
  @Length(128)
  var szMatchUnitName:String = _ ;		//Match unit name
  @Length(32)
  var szMatchPersonName:String = _ ;		//Matcher name
  var szMatchDate = new AFISDate;				//Match date	(YYYYMMDD)
  @Length(256)
  var szComment:String = _ ;				//Comment
  @Length(32)
  var szReporter:String = _ ;				//Reporter
  var szReporterDate = new AFISDate;				//Report date	(YYYYMMDD)
  @Length(32)
  var szApproveName:String = _ ;			//Approve name
  var szApproveDate = new AFISDate;				//Approve date	(YYYYMMDD)
  @Length(16)
  var szReportUnitCode:String = _ ;		//Report unit code
  @Length(128)
  var szReportUnitName:String = _ ;		//Report unit name
  @Length(32)
  var szRechecker:String = _ ;			//Rechecker
  @Length(16)
  var szRecheckUnitCode:String = _ ;		//Recheck unit code
  var szRecheckDate = new AFISDate;				//Recheck date	(YYYYMMDD)
  } //GFPTFINGERMATCHINFOSTRUCT;

  class GFPTRECORD4STRUCT extends AncientData
  {
    var nIndex:Int = _;		//1,2..	Current record seq
  @Length(32)
  var szCaseNO:String = _ ;		//case ID
  var nLatFingerSeq:Byte = _ ;		//Lat Finger Seq
  @Length(32)
  var szPersonNO:String = _ ;		//Person ID
  var nTPFingerPos:Byte = _ ;		//Tp Finger Pos
  var bCaughtDirectly:Byte = _;	//catch directly	1(True),0(False)
  var nMatchMethod:Byte = _ ;		//Match method		1(ID_FINGERMATCHMETHOD_LT),2(ID_FINGERMATCHMETHOD_TL),9(ID_FINGERMATCHMETHOD_OTHER)
  var stMatchInfo = new GFPTFINGERMATCHINFOSTRUCT;
  } //GFPTRECORD4STRUCT;			//!Finger LT,TL Match Info Struct

  class GFPTRECORD5STRUCT extends AncientData
  {
    var nIndex:Int = _;		//1,2..  Current record seq
  @Length(32)
  var szPersonNO1:String = _ ;	//First Person ID
  @Length(32)
  var szPersonNO2:String = _ ;	//Second Person ID
  var stMatchInfo = new GFPTFINGERMATCHINFOSTRUCT;
  } //GFPTRECORD5STRUCT;			//!Finger TT Match Info Struct

  class GFPTRECORD6STRUCT extends AncientData
  {
    var nIndex:Int = _;		//1,2,3..	Current record seq
  @Length(32)
  var szCaseNO1:String = _ ;		//First case ID
  var nLatFingerSeq1:Byte = _ ;		//First Lat finger seq
  @Length(32)
  var szCaseNO2:String = _ ;		//Second case ID
  var nLatFingerSeq2:Byte = _ ;		//Second Lat finger seq
  var stMatchInfo = new GFPTFINGERMATCHINFOSTRUCT;
  } //GFPTRECORD6STRUCT;			//!Finger LL Match Info Struct

  class GFPTRECORD7STRUCT extends AncientData
  {
    var nIndex:Int = _;				//1,2,3..
  @Length(32)
  var szCaseNO:String = _ ;		//Finger Query Case ID
  var nLatSeq:Byte = _ ;			//Lat Finger seq
  var nQueryType:Byte = _ ;			//Query ID	1(ID_SENDLATFINGERQUERY_LT),2(ID_SENDLATFINGERQUERY_LL),3(ID_SENDLATFINGERQUERY_QUERYSRC)
  } //GFPTRECORD7STRUCT;

  class GFPTRECORD8STRUCT extends AncientData
  {
    var nIndex:Int = _;				//1,2,3...
  @Length(32)
  var szPersonNO:String = _ ;		//Person ID
  var nQueryType:Byte = _ ;			//Query ID	1(ID_SENDTPFINGERQUERY_TL), 2(ID_SENDTPFINGERQUERY_TT)
  } //GFPTRECORD8STRUCT;

  class GFPTFINGERLTINFOSTRUCT extends AncientData
  {
    var nRank:Int = _;			//finger match result rank
  var nScore:Int = _;			//match result score
  @Length(32)
  var szPersonNO:String = _ ;	//finger person ID
  var nFingerPos:Byte = _ ;		//finger pos
  } //GFPTFINGERLTINFOSTRUCT;

  class GFPTRECORD9STRUCT extends AncientData
  {
    var nIndex:Int = _;				//1,2,3..
  @Length(32)
  var szCheckUnitCode:String = _ ;			//Submit result unit code
  var szSearchFinishTime = new AFISDate;			//Search finish time	(YYYYMMDD)
  @Length(32)
  var szCheckerName:String = _ ;				//Checker name
  @Length(32)
  var szCaseNO:String = _ ;					//Finger case ID
  var nFingerSeq:Byte = _ ;						//Finger seq
  var nFingerCount:Int = _;					//Finger count
  var pFingerLTInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pFingerLTInfo_Data:Array[GFPTFINGERLTINFOSTRUCT] = _ // for pFingerLTInfo pointer ,struct:GFPTFINGERLTINFOSTRUCT;
  } //GFPTRECORD9STRUCT;

  class GFPTFINGERTLINFOSTRUCT extends AncientData
  {
    var nRank:Int = _;				//finger match result rank
  var nScore:Int = _;				//match result score
  var nFingerPos:Byte = _ ;			//finger pos
  @Length(32)
  var szCaseNO:String = _ ;		//case ID
  var nLatSeq:Byte = _ ;			//lat seq
  } //GFPTFINGERTLINFOSTRUCT;

  class GFPTRECORD10STRUCT extends AncientData
  {
    var nIndex:Int = _;				//1,2..
  @Length(32)
  var szCheckUnitCode:String = _ ;			//Search unit code
  var szSearchFinishTime = new AFISDate;			//Search finish time	(YYYYMMDD)
  @Length(32)
  var szPersonNO:String = _ ;					//Finger person ID
  var nLatFingerCount:Int = _;				//finger count
  var pFingerTLInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pFingerTLInfo_Data:Array[GFPTFINGERTLINFOSTRUCT] = _ // for pFingerTLInfo pointer ,struct:GFPTFINGERTLINFOSTRUCT;
  } //GFPTRECORD10STRUCT;

  class GFPTPERSONTTINFOSTRUCT extends AncientData
  {
    var nRank:Int = _;				//match result rank
  var nScore:Int = _;				//match result score
  @Length(32)
  var szPersonNO:String = _ ;		//Person ID
  } //GFPTPERSONTTINFOSTRUCT;

  class GFPTRECORD11STRUCT extends AncientData
  {
    var nIndex:Int = _;				//1,2...
  @Length(32)
  var szCheckUnitCode:String = _ ;			//Search Unit Code
  var szSearchFinishTime = new AFISDate;			//Search Finish Time	(YYYYMMDD)
  @Length(32)
  var szPersonNO:String = _ ;					//Person ID
  var nPersonCount:Int = _;					//person count
  var pPersonTTInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pPersonTTInfo_Data:Array[GFPTPERSONTTINFOSTRUCT] = _ // for pPersonTTInfo pointer ,struct:GFPTPERSONTTINFOSTRUCT;
  } //GFPTRECORD11STRUCT;

  class GFPTCASELLINFOSTRUCT extends AncientData
  {
    var nRank:Int = _;				//match result rank
  var nScore:Int = _;				//match result score
  @Length(32)
  var szCaseNO:String = _ ;		//case ID
  var nFingerSeq:Byte = _ ;			//finger seq
  } //GFPTCASELLINFOSTRUCT;

  class GFPTRECORD12STRUCT extends AncientData
  {
    var nIndex:Int = _;			//1,2,3...
  @Length(32)
  var szCheckUnitCode:String = _ ;			//Search Unit Code
  var szSearchFinishTime = new AFISDate;			//Search Finish Time	(YYYYMMDD)
  @Length(32)
  var szCaseNO:String = _ ;					//Case ID
  var nFingerSeq:Byte = _ ;						//finger seq
  var nLatFingerCount:Int = _;				//finger count
  var pCaseLLInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pCaseLLInfo_Data:Array[GFPTCASELLINFOSTRUCT] = _ // for pCaseLLInfo pointer ,struct:GFPTCASELLINFOSTRUCT;
  } //GFPTRECORD12STRUCT;


  final val INFO_ITEMFLAG_LTTLHIT = 1
  final val INFO_ITEMFLAG_TTHIT = 2
  final val INFO_ITEMFLAG_LLHIT = 3
  final val INFO_ITEMFLAG_TPQUERY = 4
  final val INFO_ITEMFLAG_LATQUERY = 5
  final val INFO_ITEMFLAG_LTMATCH_TP = 6
  final val INFO_ITEMFLAG_TLMATCH_LAT = 7
  final val INFO_ITEMFLAG_TTMATCH_TP = 8
  final val INFO_ITMEFLAG_LLMATCH_LAT = 9

  class GFPTINFOSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;			// size of this structure
  var nTextItemCount:Short = _ ;	// text item count
  var nItemFlag:Byte = _ ;			// reserved, INFO_ITEMFLAG_XXXX
  @Length(32)
  var szCardID:String = _ ;			// key  of this item in database
  var bTextCanBeFreed:Byte = _ ;	// whether pstText can be freed
  var pstText_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstText_Data:Array[GATEXTITEMSTRUCT] = _ // for pstText pointer ,struct:GATEXTITEMSTRUCT;	// lp card text info
  @Length(8)
  var bnRes:Array[Byte] = _ ;
  } //GFPTINFOSTRUCT;

  final val MATCHINFO_ITEMFLAG_LT = 1
  final val MATCHINFO_ITEMFLAG_TL = 2
  final val MATCHINFO_ITEMFLAG_TT = 3
  final val MATCHINFO_ITEMFLAG_LL = 4
  class GFPTMATCHINFOSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;			// size of this structure
  var nIndex:Int = _;				//1,2...
  var nTextItemCount:Short = _ ;	// text item count
  var nCandListCount:Short = _ ;	// cand list count
  var nItemFlag:Byte = _ ;			// reserved, MATCHINFO_ITEMFLAG_XXXX
  var bTextCanBeFreed:Byte = _ ;	// whether pstText can be freed
  var bRes:Short = _ ;
    @Length(32)
    var szCardID:String = _ ;			// key  of this item in database
  var pstText_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstText_Data:Array[GATEXTITEMSTRUCT] = _ // for pstText pointer ,struct:GATEXTITEMSTRUCT;	// card text info
  var pCandInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pCandInfo_Data:Array[GFPTINFOSTRUCT] = _ // for pCandInfo pointer ,struct:GFPTINFOSTRUCT;	//fpt candlist info
  @Length(4)
  var bnRes:Array[Byte] = _ ;
  } //GFPTMATCHINFOSTRUCT;

  class GFPT4HITINFOSTRUCT extends AncientData
  {
    var nIndex:Int = _;
    var szSystemType:Int = _ ;
    var stHitInfo = new GFPTINFOSTRUCT;
  } //GFPT4HITINFOSTRUCT;

  class GFPT4LATQUERYINFOSTRUCT extends AncientData
  {
    var stRes = new GFPTRECORD7STRUCT;
    var stQueryInfo = new GFPTINFOSTRUCT;
  } //GFPT4LATQUERYINFOSTRUCT;

  class GFPT4TPQUERYINFOSTRUCT extends AncientData
  {
    var stRes = new GFPTRECORD8STRUCT;
    var stQueryInfo = new GFPTINFOSTRUCT;
  } //GFPT4TPQUERYINFOSTRUCT;

  class GFPTRECORD99STRUCT extends AncientData
  {
    var nIndex:Int = _;					//1,2,3..
  var nDataLen:Int = _;				//user defined data length
  var pData_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pData_Data:Array[Byte] = _ // for pData pointer ,struct:char;					//user defined data
  } //GFPTRECORD99STRUCT;

  class GFPTUSERDEFINFOSTRUCT extends AncientData
  {
    var pUserData_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pUserData_Data:Array[GFPTRECORD99STRUCT] = _ // for pUserData pointer ,struct:GFPTRECORD99STRUCT;
  var nDataCount:Int = _;
  } //GFPTUSERDEFINFOSTRUCT;

  class GAFISFPTSTRUCT extends AncientData
  {
    var stHead = new GFPTINFOHEADSTRUCT;
    var pRecord2_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pRecord2_Data:Array[GFPTRECORD2STRUCT] = _ // for pRecord2 pointer ,struct:GFPTRECORD2STRUCT;
  var pRecord3_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pRecord3_Data:Array[GFPTRECORD3STRUCT] = _ // for pRecord3 pointer ,struct:GFPTRECORD3STRUCT;
  var pRecord4_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pRecord4_Data:Array[GFPT4HITINFOSTRUCT] = _ // for pRecord4 pointer ,struct:GFPT4HITINFOSTRUCT;
  var pRecord5_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pRecord5_Data:Array[GFPT4HITINFOSTRUCT] = _ // for pRecord5 pointer ,struct:GFPT4HITINFOSTRUCT;
  var pRecord6_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pRecord6_Data:Array[GFPT4HITINFOSTRUCT] = _ // for pRecord6 pointer ,struct:GFPT4HITINFOSTRUCT;
  var pRecord7_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pRecord7_Data:Array[GFPT4LATQUERYINFOSTRUCT] = _ // for pRecord7 pointer ,struct:GFPT4LATQUERYINFOSTRUCT;
  var pRecord8_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pRecord8_Data:Array[GFPT4TPQUERYINFOSTRUCT] = _ // for pRecord8 pointer ,struct:GFPT4TPQUERYINFOSTRUCT;
  var pRecord9_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pRecord9_Data:Array[GFPTMATCHINFOSTRUCT] = _ // for pRecord9 pointer ,struct:GFPTMATCHINFOSTRUCT;
  var pRecord10_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pRecord10_Data:Array[GFPTMATCHINFOSTRUCT] = _ // for pRecord10 pointer ,struct:GFPTMATCHINFOSTRUCT;
  var pRecord11_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pRecord11_Data:Array[GFPTMATCHINFOSTRUCT] = _ // for pRecord11 pointer ,struct:GFPTMATCHINFOSTRUCT;
  var pRecord12_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pRecord12_Data:Array[GFPTMATCHINFOSTRUCT] = _ // for pRecord12 pointer ,struct:GFPTMATCHINFOSTRUCT;
  var pRecord99_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pRecord99_Data:Array[GFPTRECORD99STRUCT] = _ // for pRecord99 pointer ,struct:GFPTRECORD99STRUCT;
  } //GAFISFPTSTRUCT;

  //user defined data head struct
  class GAFISUSERDEFHEADSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;		//size of this struct
  var szMagic:Long = _ ;		//"$GAFIS6$"
  var nVersion:Short = _ ;	//version
  var nMntSize:Short = _ ;	//mnt size
  var nBinSize:Short = _ ;	//bin size
  var nItemSize:Short = _ ;	//for extend data
  @Length(12)
  var bnRes:Array[Byte] = _ ;		//reserved data
  } //GAFISUSERDEFHEADSTRUCT;	//size is 32

  //user defined data struct
  class GAFISUSERDEFDATSTRUCT extends AncientData
  {
    var stHead = new GAFISUSERDEFHEADSTRUCT;
    @Length(8)
    var bnData:Array[Byte] = _ ;	//mnt + bin + extend data
  } //GAFISUSERDEFDATSTRUCT;



  /**
   * 把ASCII格式的特征数据项（例如特征点的x坐标）转换成short型
   * pszValue所指的特征数据项一共nValueLength个字节，无有效值的数据用ASCII码空格填写
   */

  //  [5/8/2007]




  final val UTIL_CHECKUNITCODE_ITEMTYPE_TPRID = 1		// 捺印卡片的编号
  final val UTIL_CHECKUNITCODE_ITEMTYPE_PRINTUNIT = 2		// 捺印单位代码
  final val UTIL_CHECKUNITCODE_ITEMTYPE_LATID = 3		// 现场卡片的编号
  final val UTIL_CHECKUNITCODE_ITEMTYPE_EXTRACTUNIT = 4		// 提取单位代码




  //extern int	GAFIS_SaveBinDataToFile(char *pszPathFileName, void *pData, int nDataLen);

  //0:CaseInfo; 1:lpfingerinfo; 2:tpcardinfo

  // add by beagle Feb. 26 2008 根据单位代码来判断FPTLIB_GlobalVar中的ExpOption

  //add 2003.05.26

  // modify by xxf [7/28/2003]
  /*
  上海模式
  当前NEC现场卡号的命名规则：
  现场卡号共22位，其中1－6位为行政区划代码，7－9位序号，10－12位一般为"999"，13－18位为年月，19－22位为序号。其中除掉7－12以外其余相同的卡号属于同一个案件，上海市局希望我们提供一个程序把NEC现场卡号转换成满足公安部标准的案件编号和现场卡号。

  转换方式：
  1、	把上述22位的现场卡号变成22位的案件编号，其中7－12位固定为"000999"；
  2、	把7－9的序号中的8－9位加到上述案件编号后面作为现场卡号；
  3、	允许用户通过修改现场卡号的后两位序号来修改现场卡号（建库时）；
  */

  final val APPMODE_NORMAL = 0	//标准模式
  final val APPMODE_NOTLOGIN = 1	//简单模式，不需登录，只能浏览FPT文件
  final val APPMODE_SHANGHAI = 2	//上海模式，批量导入上海FPT文件时采用特殊处理
  final val APPMODE_RESERVEFPTINFO = 3	//保留FPT里面的信息，即保留FPT文件中捺印卡片的卡号和人员信息编号
  final val APPMODE_QINGDAO_1 = 4	//青岛模式１，人员信息编号删除第９到１２位后，作为捺印指纹条码号
  final val APPMODE_QINGDAO_2 = 5	//青岛模式２，人员信息编号删除８８８或者９９９后，作为捺印指纹条码号
  final val APPMODE_SHCOGENTLP = 100	//[4/24/2006]上海模式，案件编号采用原始案件编号
  final val APPMODE_SHENYANG = 6	//[5/8/2007]沈阳模式，案件编号由指纹编号转换而来
  //210100          07  1 0098
  //210100 000888 2007 01 0098
  final val APPMODE_QINGDAO_3 = 7	//青岛模式3，导入捺印卡片时只导入不存在的信息项

  final val APPMODE_LIAONING = 120 //辽宁模式，详情参考辽宁需求文档《公安部标准转换程序GAFIS2FPT修改需求.doc》

  /**
   * 针对广东招标测试的两种模式，
   *  1、标准，捺印卡号使用人员编号，现场卡号使用案件编号加指纹序号
   *  2、指纹编号，分别使用2、3逻辑记录中的20位指纹卡号
   *  3、EX的主要目的是检查fpt文件是否满足广东测试文档上的要求(捺印没有特征，没有用户自定义数据等)
   * Modified on Dec. 28, 2009
   * 去掉EX模式，是否检查以及检查哪些项，由FPTLIB_IMPORTEXOPTION结构中的值来判断
   */
  final val APPMODE_GDZBTEST_STANDARD = 201
  final val APPMODE_GDZBTEST_FINGERID = 202
  //#define APPMODE_GDZBTEST_STANDARDEX		203
  //#define APPMODE_GDZBTEST_FINGERIDEX		204

  //////////////////////////////////////////////////////////////////////////

  final val FPTFMT_EXP_DEFAULT = 0	//default format
  final val FPTFMT_EXP_COGENT_OLD = 1	//old cogent format, limit mnt count and no lowcore
  final val FPTFMT_EXP_COGENT_NEW = 2	//new cogent format, no lowcore
  final val FPTFMT_EXP_XC_ONLYCASEINFO = 3	//协查仅案件信息
  final val FPTFMT_EXP_XC_ONLYTPINFO = 4	//协查仅捺印人员信息
  final val FPTFMT_EXP_XC_ONLYTP = 5	//协查仅捺印人员
  final val FPTFMT_EXP_XC_ONLYCASE = 6	//协查仅案件
  final val FPTFMT_EXP_XC_TPINFOMNT = 7	//协查捺印人员信息与特征，无图像gs add at 20130829
  final val FPTFMT_EXP_XC_CASEMNT = 8	//协查案件与特征，无图像gs add at 20130829
  final val FPTFMT_EXP_INDEX_DATA = 9  //导出FPT索引，包括文字信息、特征、不带（指纹、人像）

  //新的导出数据选项，与之前的选项在底层使用上兼容，后期再有需要追加
  final val FPT_LOCAL_EXPORT_OPTION_MASK = 0xFFFFFFFF
  final val FPT_LOCAL_EXPORT_OPTION_TP_TXT_INFO = 128     //0x80导出文字信息
  final val FPT_LOCAL_EXPORT_OPTION_TP_MNT = 256     //特征点
  final val FPT_LOCAL_EXPORT_OPTION_TP_TEN_FINGERS = 512 //捺印十指
  final val FPT_LOCAL_EXPORT_OPTION_TP_PANEL_FINGERS = 1024  //平面十指
  final val FPT_LOCAL_EXPORT_OPTION_TP_PLAM = 2048    //掌纹
  final val FPT_LOCAL_EXPORT_OPTION_TP_FACE = 4096   //人像
  final val FPT_LOCAL_EXPORT_OPTION_TP_FACE_FRONT = 4097 //正面人像
  final val FPT_LOCAL_EXPORT_OPTION_TP_FACE_LEFT = 4098 //左侧人像
  final val FPT_LOCAL_EXPORT_OPTION_TP_FACE_RIGHT = 4100 //右侧人像
  //////////////////////////////////////////////////////////////////////////

  // 在FPT导出时编号10-12位的转换选项
  final val FPTOPT_EXP_DEFAULT = 0	//auto convert
  final val FPTOPT_EXP_888 = 1	//10-12 ->888
  final val FPTOPT_EXP_999 = 2	//10-12 ->999
  final val FPTOPT_EXP_NOTVARY = 3

  final val FPTOPT_EXP_STRING_DEFAULT = "自动转换"	//auto convert
  final val FPTOPT_EXP_STRING_888 = "转换成888"	//10-12 ->888
  final val FPTOPT_EXP_STRING_999 = "转换成999"	//10-12 ->999
  final val FPTOPT_EXP_STRING_NOTVARY = "不进行转换"

  // add by beagle [8/8/2007]
  class FPTLIB_ConvCaseMISPsnParam extends AncientData
  {
    @Length(32)
    var szCardID:String = _ ;		// 卡号
  @Length(32)
  var szCaseMISPsn:String = _ ;	// 人员编号或案件编号
  var bIsCaseID:Byte = _ ;			// 是否案件编号
  var bCard2CasePsn:Byte = _ ;		// 是否是从卡号得到人员(或案件)编号
    /*
      var nExpLocalTenOption:Byte = _ ;		// 本地捺印卡片的转换选项
      var nExpLocalLatOption:Byte = _ ;		// 本地现场案件和卡片的转换选项
      var nExpOutTenOption:Byte = _ ;		// 外地捺印卡片的转换选项
      var nExpOutLatOption:Byte = _ ;		// 外地现场案件和卡片的转换选项
    */

    //	UCHAR	nExpOption;			// FPTOPT_EXP_XXX
    @Length(6)
    var nReserved:String = _ ;
  } //FPTLIB_CONVCASEMISPSNPARAM;


  /**
  说明：导出FPT时编号和单位代码的转换方式 (on July.8, 2008)
	0	-- 自动转换
	1	-- 第10-12位转换成888
	2	-- 第10-12位转换成999
	3   -- 不转换
  设置信息保存到数据库的参数表中
    */

  final val FPT_EXPREPLACE_PARAMNAME = "FPTExpReplace"
  final val FPT_EXPORTOPTION_PARAMNAME = "FPT_ExportOption"
  final val FPT_XIECHA_REPORT_PARAMNAME = "FPT_XIECHA_ReportOption"

  class FPTLIB_ExpReplaceOption extends AncientData
  {
    var nExpLocalTenOption:Byte = _ ;			// 本地捺印卡片的转换选项
  var nExpLocalLatOption:Byte = _ ;			// 本地现场案件和卡片的转换选项
  var nExpOutTenOption:Byte = _ ;			// 外地捺印卡片的转换选项
  var nExpOutLatOption:Byte = _ ;			// 外地现场案件和卡片的转换选项

    var nExpLocalPrintOption:Byte = _ ;		// 本地捺印单位代码的转换选项
  var nExpLocalExtractOption:Byte = _ ;		// 本地提取单位代码的转换选项
  var nExpOutPrintOption:Byte = _ ;			// 外地捺印单位代码的转换选项
  var nExpOutExtractOption:Byte = _ ;		// 外地提取单位代码的转换选项
  } //FPTLIB_EXPREPLACEOPTION;
  type FPTLIB_EXPREPLACEOPTION = FPTLIB_ExpReplaceOption

  /**
   * 新增的导入选项，详细信息请参考gfptoption.h文件的对应说明
   * 这里的选项都是针对特定目的额外增加的设置，fpt数据无论如何首先得满足fpt标准
   */
  class FPTLIB_ImportExOption extends AncientData
  {
    /**
     * 导入的fpt数据中禁止具有的数据项，值为GFPTDATA_ITEMFLAG_xxx
     */
    var nTprDisabledDataFlag:Int = _;	//!< 十指指纹信息数据项
  var nLatDisabledDataFlag:Int = _;	//!< 现场指纹信息数据项
    @Length(48 - 4*2)
  var bnResDisable:Array[Byte]  = _ //[48 - sizeof(int) * 2];

    /**
     * 导入的fpt数据中必须具有的数据项，如果等于0则不检查
     */
    var nTprMustDataItemFlag:Int = _;
    var nLatMustDataItemFlag:Int = _;
    @Length(48 - 4*2)
    var bnResMust:Array[Byte] = _ //[48 - sizeof(int) * 2];

    var nRecordsPerFpt:Int = _;			//!< 每个fpt文件允许具有的记录数，0表示没有限制
  var bSaveUserData2File:Byte = _ ;		//!< 是否把用户自定义数据保存到文件中
    @Length(32 - 4 - 1)
  var bnResOther:Array[Byte] = _ //[32 - sizeof(int) * 1 - 1];
  } //FPTLIB_IMPORTEXOPTION;	// size is 128 bytes
  type FPTLIB_IMPORTEXOPTION = FPTLIB_ImportExOption;

  class FPTLIB_ExpImpOption extends AncientData
  {
    /**
     * Export option, total size is 128 bytes
     */
    var stReplace = new FPTLIB_EXPREPLACEOPTION;

    var nRecCountPerFPT:Int = _;	// 每个FPT文件最多放置的记录个数，如果是fpt4.0格式，则该值不能超过256，缺省值为100
  var bFPT4Version:Byte = _ ;		// 导出成fpt4.0格式
    @Length(128 - 13)
  var bnExpRes:Array[Byte] = _ //[128 - 13];

    /**
     * Import option, total size is 128 bytes
     */
    var stImpOption = new FPTLIB_IMPORTEXOPTION;
  } //FPTLIB_EXPIMPOPTION;	// size is 256 bytes
  type FPTLIB_EXPIMPOPTION = FPTLIB_ImportExOption


  class fptlib_xiecha_reportoption extends AncientData
  {
    var uReportRule:Byte = _ ;  //协查上报规则：0-不符合标准的人员（案件）编号（含字母的编号除外）修改为标准的编号规则上报。
  //1-只上报符合标准规则的数据。
  @Length(64-1)
  var bnExpRes:Array[Byte] = _ ;
  } //FPTLIB_Xiecha_ReportOption;
  type FPTLIB_Xiecha_ReportOption = fptlib_xiecha_reportoption


  class FPTLIB_GlobalVar extends AncientData
  {
    @Length(16)
    var m_szUnitCode:String = _ ;
    @Length(16)
    var m_szUserName:String = _ ;
    var m_nMntFmt:Int = _;
    var m_nExportTPCardMaxCount:Int = _;

    var m_bUseImg:Byte = _ ;
    var m_bUseMnt:Byte = _ ;
    var m_bUseTpCpr:Byte = _ ;
    var m_bUseLpCpr:Byte = _ ;
    var m_nCaseLevel:Byte = _ ;
    var m_bUseLog:Byte = _ ;				//use log file
  var m_bIsImport:Byte = _ ;
    var m_bExportCase:Byte = _ ;			//export case info

    var m_bCreateMatchFPT:Byte = _ ;		//create match fpt file
  var m_bAddtoExfQue:Byte = _ ;		//add tpcard into exf queue
  var m_bExportUserDefData:Byte = _ ;	//export user defined data
  var m_bUseTPLain:Byte = _ ;			//use tplain finger when create fpt
  var m_nExpFPTFmt:Byte = _ ;			//export fpt format, FPTFMT_EXP_XXX
  var m_bSkipTextQue:Byte = _ ;		//skip textinput queue

    /**
     * GAFIS6.2系统中的协查级别使用的是FPT标准3（Version3）标准，其代码为：0 - 未知；1 - A级；2 - B级；3 - C级；4 - B级紧急
     * 而最新协查级别为公安部FTP标准4（Version4），其代码：1 - A级；2 - B级紧急；3 - B级；4 - C级；9 - 其他；
     * 为了与最新的标准一致，在fpt导入导出时，对协查级别进行转换，转换原则为：
     *	1、导出时，协查级别转换成Version4标准，并且在fpt文件的第一类逻辑记录的备注里面增
     *		加标记"<FPX_Level Version = "4" />"，除非m_bReserveFPX_Level = 1
     * 2、导入时，如果导入的是非东方金指生成的fpt文件，则其中的协查级别自动被认为是Version4，
     *		把其转换成Version3标准，如果是东方金指生成的fpt文件，则检查第一类逻辑记录的备注中有
     *		没有FPX_Level Version标记，如果有并且其值为4，则转换成Version3标准，如果没有或者其值
     *		为3，则认为就是Version3标准，不用转换
     *	3、不管是导入还是导出，如果m_bReserveFPX_Level=1，则不对协查级别进行转换
     */
    var m_bReserveFPX_Level:Byte = _ ;		//!< 保留当前的协查级别，不要进行转换
  @Length(1)
  var bnRes:Array[Byte] = _ ;

    // modified by beagle Feb. 26 2008 根据单位代码来判断FPTLIB_GlobalVar中的m_nExpOption(FPT导出时编号10-12位的转换选项)
    // UCHAR m_nExpOption;				// batch exp fpt option, FPTOPT_EXP_xxx

    // FPTLIB_EXPREPLACEOPTION	stExpOption;
    var dwExExportOption:Int = _;  //扩展的本地客户端导出选项，由宏FPT_LOCAL_EXPORT_OPTION_xx定义，支持累加多种选项
  //通过dwExExportOption&FPT_LOCAL_EXPORT_OPTION_xx来判断是否有该项
  var stExpImpOption = new FPTLIB_EXPIMPOPTION;

    var stXiechaReportOption = new FPTLIB_Xiecha_ReportOption;

  } //FPTLIB_GlobalVar;


  /**
  说明：把转换方式保存到数据库中或从数据库中获取转换方式
    */



//create fpt file param struct
final val FPTCRTPARAM_OPT_ONLYEXPBROKEN = 0x0001	//导出案件时，只导出比中的现场指纹
final val FPTCRTPARAM_OPT_CREATEEMPTYFPT = 0x0002	//如果没有数据，则创建一个空的FPT文件（只包含第一类逻辑记录）

class FPTCreateParamStruct extends AncientData
{
  @Length(256)
  var szFileName:String = _ ;	//FPT文件名称
  @Length(8 * 2)
  var hCon:Array[Byte] = _ //new HGNETCONNECTIONOBJECT;	//网络连接句柄
  var pstLPCase_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstLPCase_Data:Array[GAKEYSTRUCT] = _ // for pstLPCase pointer ,struct:GAKEYSTRUCT;		//需要导出的案件列表
  var pstLPCard_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstLPCard_Data:Array[GAKEYSTRUCT] = _ // for pstLPCard pointer ,struct:GAKEYSTRUCT;		//需要导出的现场指纹列表
  var pstTPCard_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstTPCard_Data:Array[GAKEYSTRUCT] = _ // for pstTPCard pointer ,struct:GAKEYSTRUCT;		//需要导出的捺印指纹列表
  @Length(32)
  var szStartCaseID:String = _ ;	//起始案件编号
  var nLPCaseCount:Int = _;		//案件列表的个数
  var nLPCardCount:Int = _;		//现场指纹列表的个数
  var nTPCardCount:Int = _;		//捺印指纹列列表的个数
  var nMaxFingerPerCase:Int = _;	//每个案件中最多包含的现场指纹的个数
  var bCanceled:Int = _;			//是否可以取消当前操作
  var nOption:Int = _;			//FPTCRTPARAM_OPT_XXX
  var nTPDBID:Short = _;			//捺印库ID
  var nLPDBID:Short = _;			//现场库ID
  var nExpFPTFmt:Byte = _ ;			//FPTFMT_EXP_XXX
  @Length(3)
  var bnRes1:Array[Byte] = _ ;
  var pFingerLTTLInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pFingerLTTLInfo_Data:Array[GFPTINFOSTRUCT] = _ // for pFingerLTTLInfo pointer ,struct:GFPTINFOSTRUCT;	//指纹正查和倒查比中信息记录
  var pFingerTTInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pFingerTTInfo_Data:Array[GFPTINFOSTRUCT] = _ // for pFingerTTInfo pointer ,struct:GFPTINFOSTRUCT;		//指纹查重比中信息记录
  var pFingerLLInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pFingerLLInfo_Data:Array[GFPTINFOSTRUCT] = _ // for pFingerLLInfo pointer ,struct:GFPTINFOSTRUCT;		//指纹串查比中信息记录
  var pLatQryInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pLatQryInfo_Data:Array[GFPTINFOSTRUCT] = _ // for pLatQryInfo pointer ,struct:GFPTINFOSTRUCT;		//现场指纹查询请求信息记录
  var pTpQryInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pTpQryInfo_Data:Array[GFPTINFOSTRUCT] = _ // for pTpQryInfo pointer ,struct:GFPTINFOSTRUCT;			//十指指纹查询请求信息记录
  var pLTInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pLTInfo_Data:Array[GFPTMATCHINFOSTRUCT] = _ // for pLTInfo pointer ,struct:GFPTMATCHINFOSTRUCT;		//正查比对结果候选信息记录
  var pTLInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pTLInfo_Data:Array[GFPTMATCHINFOSTRUCT] = _ // for pTLInfo pointer ,struct:GFPTMATCHINFOSTRUCT;	//倒查比对结果候选信息记录
  var pTTInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pTTInfo_Data:Array[GFPTMATCHINFOSTRUCT] = _ // for pTTInfo pointer ,struct:GFPTMATCHINFOSTRUCT;		//查重比对结果候选信息记录
  var pLLInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pLLInfo_Data:Array[GFPTMATCHINFOSTRUCT] = _ // for pLLInfo pointer ,struct:GFPTMATCHINFOSTRUCT;		//串查比对结果候选信息记录
  var pUserDefinedInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pUserDefinedInfo_Data:Array[GFPTUSERDEFINFOSTRUCT] = _ // for pUserDefinedInfo pointer ,struct:GFPTUSERDEFINFOSTRUCT;		//用户自定义信息
  var nLTTTHitCount:Int = _;				//指纹正查和倒查比中信息个数
  var nTTHitCount:Int = _;				//指纹查重比中信息个数
  var nLLHitCount:Int = _;				//指纹串查比中信息个数
  var nLatQryCount:Int = _;				//现场指纹查询请求信息个数
  var nTpQryCount:Int = _;				//十指指纹查询请求信息个数
  var nLTMatchCount:Int = _;				//正查比对结果候选信息个数
  var nTLMatchCount:Int = _;				//倒查比对结果候选信息个数
  var nTTMatchCount:Int = _;				//查重比对结果候选信息个数
  var nLLMatchCount:Int = _;				//串查比对结果候选信息个数
  var pstTPDBID_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstTPDBID_Data:Array[Short] = _ // for pstTPDBID pointer ,struct:uint2;					//捺印指纹列表各卡对应的库ID,仅在导出TT分组查询时使用
  @Length(12)
  var szTaskID:String = _ ;				//任务控制号
  //@Length(1024-256-15*sizeof(void*)-17*sizeof(int)-32-12)
  @Length(1024-256-15* 8 -17*4-32-12)
  var bnRes:Array[Byte] = _
} //FPTCREATEPARAMSTRUCT;	//创建FPT文件的参数, used by GAFIS_FPTLIB_CreateFPTFileEx


/**
  * 类似GAFIS_FPTLIB_CreateLogic23FPT4File函数，不过使用的是内存不是文件
  * 返回值是FPT数据的长度，如果<0则表示出错
  */

//////////////////////////////////////////////////////////////////////////
final val FPT4_DATA_TENPRINT = 0x0001  //捺印
final val FPT4_DATA_CASE = 0x0002  //案件

/**
  * 首先判断fpt文件的版本号，然后根据不同的版本，分别调用相应的函数获取fpt文件中的数据
  */



//////////////////////////////////////////////////////////////////////////
//依据比中或比对的关系类型判断导入的卡片中原卡不导入
final val FPT4_HIT_TYPE_LG4 = 0x0002  //正查倒查比中
final val FPT4_HIT_TYPE_LG5 = 0x0004  //查重比中
final val FPT4_HIT_TYPE_LG6 = 0x0008 //串查比中
final val FPT4_MATCH_TYPE_LG9 = 0x0010  //正查比对
final val FPT4_MATCH_TYPE_LG10 = 0x0020 //倒查比对
final val FPT4_MATCH_TYPE_LG11 = 0x0040 //查重比对
final val FPT4_MATCH_TYPE_LG12 = 0x0080 //串查比对
final val FPT4_HIT_MATCH_TYPE_ALL_LG = FPT4_HIT_TYPE_LG4|FPT4_HIT_TYPE_LG5|FPT4_HIT_TYPE_LG6|FPT4_MATCH_TYPE_LG9|FPT4_MATCH_TYPE_LG10|FPT4_MATCH_TYPE_LG11|FPT4_MATCH_TYPE_LG12

class FPTCreateFromTPCardParam extends AncientData
{
  @Length(256)
  var szFileName:String = _ ;		//fpt file name
  @Length(2 * 8)
  var hCon:Array[Byte] = _ //new HGNETCONNECTIONOBJECT;	//connection handle
  var pstCard_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstCard_Data:Array[GTPCARDINFOSTRUCT] = _ // for pstCard pointer ,struct:GTPCARDINFOSTRUCT;	//tpcard
  var nCardCount:Int = _;				//count of tpcard
  var nOption:Int = _;			//not used
  var nExpFPTFmt:Byte = _ ;			//FPTFMT_EXP_XXX
  @Length(1024-256-2*8-4*2-1)
  var bnRes:Byte = _ //[1024-256-2*sizeof(void*)-sizeof(int)*2-1];
} //FPTCREATEFROMTPCARDPARAM;


//Check FPT4 File is standard

//////////////////////////////////////////////////////////////////////////
//校验类型，主要校验逻辑2~12，可以累加多个类型
final val FPT4_CHECK_TYPE_LG2 = 0x0002
final val FPT4_CHECK_TYPE_LG3 = 0x0004
final val FPT4_CHECK_TYPE_LG4 = 0x0008
final val FPT4_CHECK_TYPE_LG5 = 0x0010
final val FPT4_CHECK_TYPE_LG6 = 0x0020
final val FPT4_CHECK_TYPE_LG7 = 0x0040
final val FPT4_CHECK_TYPE_LG8 = 0x0080
final val FPT4_CHECK_TYPE_LG9 = 0x0100
final val FPT4_CHECK_TYPE_LG10 = 0x0200
final val FPT4_CHECK_TYPE_LG11 = 0x0400
final val FPT4_CHECK_TYPE_LG12 = 0x0800
final val FPT4_CHECK_TYPE_ALL_LG = FPT4_CHECK_TYPE_LG2 | FPT4_CHECK_TYPE_LG3 | FPT4_CHECK_TYPE_LG4 | FPT4_CHECK_TYPE_LG5 | FPT4_CHECK_TYPE_LG6 | FPT4_CHECK_TYPE_LG7 | FPT4_CHECK_TYPE_LG8 | FPT4_CHECK_TYPE_LG9 | FPT4_CHECK_TYPE_LG10 | FPT4_CHECK_TYPE_LG11 | FPT4_CHECK_TYPE_LG12

//////////////////////////////////////////////////////////////////////////


/**
  *Add by xff 090601 for fpt4.0 message info
  */
final val STR_ISDEADBODY = "IsDeadBody"
final val STR_DEADPERSONNO = "DeadPersonNo"
final val STR_XIECHAFLAG = "XieChaFlag"
final val STR_BIDUISTATE = "BiDuiState"
final val STR_XIECHAREQUESTUNITNAME = "XieChaRequestUnitName"
final val STR_XIECHAREQUESTUNITCODE = "XieChaRequestUnitCode"
final val STR_XIECHALEVEL = "XieChaLevel"
final val STR_XIECHAFORWHAT = "XieChaForWhat"
final val STR_RELPERSONNO = "RelPersonNo"
final val STR_RELCASENO = "RelCaseNo"
final val STR_XIECHATIMELIMIT = "XieChaTimeLimit"
final val STR_XIECHADATE = "XieChaDate"
final val STR_XIECHAREQUESTCOMMENT = "XieChaRequestComment"
final val STR_XIECHACONTACTER = "XieChaContacter"
final val STR_XIECHATELNO = "XieChaTelNo"
final val STR_SHENPIBY = "ShenPiBy"
final val STR_CANCELFLAG = "CancelFlag"
final val STR_PREMIUM = "Premium"
final val STR_CASENO = "CaseNo"
final val STR_LATFINGERSEQ = "LatFingerSeq"
final val STR_PERSONNO = "PersonNo"
final val STR_TPFINGERPOS = "TPFingerPos"
final val STR_CAUGHTDIRECTLY = "CaughtDirectly"
final val STR_BIDUIMETHOD = "BiDuiMethod"
final val STR_BIDUIUNITCODE = "BiDuiUnitCode"
final val STR_BIDUIUNITNAME = "BiDuiUnitName"
final val STR_BIDUIUSER = "BiDuiUser"
final val STR_BIDUIDATE = "BiDuiDate"
final val STR_COMMENT = "Comment"
final val STR_REPORTER = "Reporter"
final val STR_REPORTERDATE = "ReporterDate"
final val STR_SHENPIDATE = "ShenPiDate"
final val STR_REPORTUNITCODE = "ReportUnitCode"
final val STR_REPORTUNITNAME = "ReportUnitName"
final val STR_RECHECKER = "Rechecker"
final val STR_RECHECKUNITCODE = "RecheckUnitCode"
final val STR_RECHECKDATE = "RecheckDate"
final val STR_PERSONNO1 = "PersonNo1"
final val STR_PERSONNO2 = "PersonNo2"
final val STR_CASENO1 = "CaseNo1"
final val STR_CASENO2 = "CaseNo2"
final val STR_LATFINGERSEQ1 = "LatFingerSeq1"
final val STR_LATFINGERSEQ2 = "LatFingerSeq2"
final val STR_QUERYTYPE = "QueryType"
final val STR_CHECKERUNITCODE = "CheckerUnitCode"
final val STR_SEARCHFINISHTIME = "SearchFinishTime"
final val STR_CHECKERNAME = "CheckerName"
final val STR_RANK = "Rank"
final val STR_SCORE = "Score"
final val STR_FINGERPOS = "FingerPos"
final val STR_LATFINGERSTART = "LatStart"
final val STR_LATFINGEREND = "LatEnd"


final val GFPTLIB_SUBKEY = "\\Gfptlib"
final val GFPTLIB_IMPORTMODE = "ImportMode"

final val IDS_APPMODE_LNCASESTR = "原案件号"		//辽宁模式 备注保留原案件号和现场卡号
final val IDS_APPMODE_LNLPSTR = "原现场号"


}
