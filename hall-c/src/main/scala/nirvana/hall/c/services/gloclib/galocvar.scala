package nirvana.hall.c.services.gloclib

import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.gbaselib.garray.GARRAY_STRUCT
import nirvana.hall.c.services.gbaselib.gathrdop.{GAFIS_EVENT_HANDLE, GAFIS_CRITSECT, GBASE_CRIT}
import nirvana.hall.c.services.gbaselib.paramadm.GBASE_PARAM_MEMITEM
import nirvana.hall.c.services.gloclib.gacodetb.GAFIS_CODETB_CACHEMGR
import nirvana.hall.c.services.gloclib.gafisusr.GAFIS_USERACCOUNTINMEM
import nirvana.hall.c.services.gloclib.galocdbp.GAFIS_DEFDBIDSTRUCT
import nirvana.hall.c.services.gloclib.galocqry.{GAFIS_QRYSTATUSLIST, GAFIS_MEMQUERYADM, GAMEMQUERYQUESTRUCT}
import nirvana.hall.c.services.gloclib.galoctp.GADB_MICSTREAMNAMESTRUCT
import nirvana.hall.c.services.gloclib.gamumgmt.GAFIS_MATCHSVRMGMT
import nirvana.hall.c.services.gloclib.glocdef.GAFIS_STANDARD

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-12
 */
object galocvar {


  class GAFIS_INSTALLUSER extends AncientData
  {
    @Length(32)
    var szUserCode:String = _ ;
    @Length(128+64)
    var szUserName:String = _ ;	// can hold 80 hanzi.
  @Length(32)
  var bnRes:Array[Byte] = _ ;
  } // GAFIS_INSTALLUSER;	// size of this structure is 256 bytes long

  class GAFIS_FINGPALMMNTSIZE extends AncientData
  {
    var nMntMaxLen:Int = _ ;
    var nBinMaxLen:Int = _ ;
    var nFeatMaxLen:Int = _ ;
    var nBinAveLen:Int = _ ;		// average length of the binary data.
  } // GAFIS_FINGPALMMNTSIZE;	// for finger or palm, 16 bytes long.

  //
  class GAFIS_QUERYPRIBOOST extends AncientData
  {
    var nQueryTypeBoostPri:Int = _;	// if query type match, then it's the smallest priority.
  var nQueryTypePri:Int = _;	// query type priority.
  var nBoostPriTime:Int = _;	// if query submit time+this time less than current time
  // then it may be boosted. unit is hour.
  var nTimeBoostPri:Int = _;	// boost to this priority.
  } // GAFIS_QUERYPRIBOOST;	// 16 bytes long.

  // we may support different minutia format.
  class GAFIS_MNTSIZEINFO extends AncientData
  {
    // tenprint
    var stTPFing = new GAFIS_FINGPALMMNTSIZE;
    var stTPPalm = new GAFIS_FINGPALMMNTSIZE;
    var stTPFace = new GAFIS_FINGPALMMNTSIZE;
    var stTPVoice = new GAFIS_FINGPALMMNTSIZE;

    // latent
    var stLPFing = new GAFIS_FINGPALMMNTSIZE;
    var stLPPalm = new GAFIS_FINGPALMMNTSIZE;
    var stLPFace = new GAFIS_FINGPALMMNTSIZE;
    var stLPVoice = new GAFIS_FINGPALMMNTSIZE;

    var stTPlain = new GAFIS_FINGPALMMNTSIZE;	// tenprint plain
  var bSupportTP:Byte = _ ;
    var bSupportLP:Byte = _ ;
    var bSupportTPPalm:Byte = _ ;
    var bSupportLPPalm:Byte = _ ;
    var bSupportTPFeat:Byte = _ ;
    var bSupportPalmFeat:Byte = _ ;
    var bSupportTPlain:Byte = _ ;
    var bTPlainHasBin:Byte = _ ;
    var bSupportFace:Byte = _ ;
    var bSupportVoice:Byte = _ ;
    @Length(38+64)
    var bnRes:Array[Byte] = _ ;
  } // GAFIS_MNTSIZEINFO;	// size is 256 bytes long.

  /*
  typedef	enum	tagGAFIS_BIOMETRICTYPE
  {
    BIOMETRICTYPE_MNT,
    BIOMETRICTYPE_IMG,
    BIOMETRICTYPE_CPR,
    BIOMETRICTYPE_BIN,
    BIOMETRICTYPE_FEAT,
    BIOMETRICTYPE_WIDTH,
    BIOMETRICTYPE_HEIGHT,
    BIOMETRICTYPE_DPI
  } GAFIS_BIOMETRICTYPE;

  typedef	enum	tagGAFIS_BIODATATYPE
  {
    BIODATATYPE_TPROLL,
    BIODATATYPE_TPPLAIN,
    BIODATATYPE_TPPALM,
    BIODATATYPE_TPFACE,
    BIODATATYPE_TPVOICE,

    BIODATATYPE_LPFINGER,
    BIODATATYPE_LPPALM,
    BIODATATYPE_LPFACE,
    BIODATATYPE_LPVOICE
  } GAFIS_BIODATATYPE;
  */

  class GAFIS_BIOITEMSIZE extends AncientData
  {
    var nMntSize:Int = _ ;
    var nImgSize:Int = _ ;
    var nFeatSize:Int = _ ;
    var nCprSize:Int = _ ;
    var nBinSize:Int = _ ;
    var nWidth:Int = _ ;
    var nHeight:Int = _ ;
    var nDpi:Int = _ ;
  } // GAFIS_BIOITEMSIZE;	// bio data item size.	// 32 bytes long.

  class GAFIS_MINMAX_BIOITEMSIZE extends AncientData
  {
    var stMin = new GAFIS_BIOITEMSIZE;
    var stMax = new GAFIS_BIOITEMSIZE;
  } // GAFIS_MINMAX_BIOITEMSIZE;	// 64 bytes long.

  class GAFIS_BIODATASIZE extends AncientData
  {
    var stTPRollFinger = new GAFIS_MINMAX_BIOITEMSIZE;
    var stTPPlainFinger = new GAFIS_MINMAX_BIOITEMSIZE;
    var stTPPalm = new GAFIS_MINMAX_BIOITEMSIZE;
    var stTPFace = new GAFIS_MINMAX_BIOITEMSIZE;
    var stTPVoice = new GAFIS_MINMAX_BIOITEMSIZE;

    var stLPFinger = new GAFIS_MINMAX_BIOITEMSIZE;
    var stLPPalm = new GAFIS_MINMAX_BIOITEMSIZE;
    var stLPFace = new GAFIS_MINMAX_BIOITEMSIZE;
    var stLPVoice = new GAFIS_MINMAX_BIOITEMSIZE;
    @Length(448)
    var bnRes:Array[Byte] = _ ;
  } // GAFIS_BIODATASIZE;	// 1KB size.

  class GAFIS_USERKEY_PAIR extends AncientData
  {
    var pszUserWild_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUserWild_Data:Array[Byte] = _ // for pszUserWild pointer ,struct:char;
  var pszKeyWild_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszKeyWild_Data:Array[Byte] = _ // for pszKeyWild pointer ,struct:char;
  } // GAFIS_USERKEY_PAIR;

  class GAFIS_USERKEY_FILTER_ITEM extends AncientData
  {
    var pstPair_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstPair_Data:Array[GAFIS_USERKEY_PAIR] = _ // for pstPair pointer ,struct:GAFIS_USERKEY_PAIR;	// pstPair[0]是需要被包含的的组合。其余的是需要排除的组合。
  var nPairCnt:Int = _;	// >=1
  @Length(4)
  var bnRes_Pt4:Array[Byte] = _ ;
  } // GAFIS_USERKEY_FILTER_ITEM;

  class GAFIS_USERKEY_FILTER_LIST extends AncientData
  {
    var pstItem_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstItem_Data:Array[GAFIS_USERKEY_FILTER_ITEM] = _ // for pstItem pointer ,struct:GAFIS_USERKEY_FILTER_ITEM;
  var nItemCnt:Int = _;
    @Length(4)
    var bnRes_Pt4:Array[Byte] = _ ;
  } // GAFIS_USERKEY_FILTER_LIST;


  /**
   * Added by beagle On Jan. 1, 2010
   * 增加特征提取和特征更新选项设置
   * 不管是许公望还是王曙光在提取特征或者把特征从Disp格式转换成相应的标准(Std)格式，都有一些可选项：
   * 首先，特征提取，分为两种模式：重新提取模式、更新模式，Disp到Std的转换也被看成是一种更新
   * 在更新模式下，不同的算法具有不同的更新选项
   * 许公望的特征更新可选项请参考afiskernel.h文件中GAFIS_ExtractMNT_All函数的相关说明
   * 王曙光的特征更新可选项，目前只有一个选项：根据现有特征自动提取Cell和数线信息
   */
  class GAFIS_EXTRACTMNTOPTION extends AncientData
  {
    /**
     * 特征提取模式，值为：
     *	EXTRACTMODE_NEW				0			//	重新提取全部特征
     *	EXTRACTMODE_UPDATE			1			//	在原有特征基础上更新特征
     */
    var nTprExtractMode:Byte = _ ;	//!< 捺印卡片特征提取模式
  var nLatExtractMode:Byte = _ ;	//!< 现场卡片特征提取模式
  @Length(6)
  var bnRes1:Array[Byte] = _ ;

    /**
     * 特征更新选项，不同的算法具有不同的选项值
     */
    var nTprUpdateOption:Int = _;
    var nLatUpdateOption:Int = _;
    @Length(16)
    var bnRes2:Array[Byte] = _ ;

  } //GAFIS_EXTRACTMNTOPTION;	// size is 32 bytes


  final val GAFIS_EXTRACTMNTMODE_NEW = 0
  final val GAFIS_EXTRACTMNTMODE_UPDATE = 1
  final val GAFIS_EXTRACTMNTMODE_CHECK = 2		//	检查原有特征的错误
  final val GAFIS_EXTRACTMNTMODE_MAXVALUE = 2

  //!< 许公望特征更新选项

  /**
   * 王曙光的特征更新选项
   *
   */
  final val UPDATEMNT_LCWOPTION_CELLLINECNT = 0x00000001	//!< 根据现有特征数据，提取数线和Cell信息
  final val UPDATEMNT_LCWOPTION_RESERVE = 0x00000002	//!< 保留现有特征不动，这种情况发生在需要更新或重提许公望的特征时


  /**
   * 针对特征提取模式和更新选项，提供两个参数，分别用来设置捺印和现场卡片
   * 各算法的特征提取模式和更新选项(把特征从Disp转换到Std也看作是特征的更新)，其格式为
   * GAFIS_TPCARD_ExtractOption=mntformat:extractmode,updateoption@mntformat:extractmode,updateoption...
   * GAFIS_LPCARD_ExtractOption=mntformat:extractmode,updateoption@mntformat:extractmode,updateoption...
   * 其中，mntformat表示了特征格式(算法)，值分别为0(许公望格式)、1(王曙光格式)；
   * extractmode表示特征提取模式，参考afiskernel.h中GAFIS_ExtractMNT_All函数的说明，
   *	值分别为0(重新提取全部特征)、1(在原有特征基础上更新特征)，缺省值为0；
   * updateoption表示特征更新选项，针对不同的特征格式具有不同的含义：
   * 在许公望特征格式下，updateoption的值与afiskernel.h中XGWMNTEXTRACTSTR结构的MntBeUpdated成员取值一致，
   *	当extractmode=0时，updateoption表示从Disp到Std转换时的特征更新选项，缺省值是0表示不进行更新处理
   *	当extractmode=1时，updateoption表示了更新特征和从Disp到Std转换时的更新选项，缺省值是1
   * 在王曙光特征格式下，updateoption的值请参考galocvar.h中GAFIS_EXTRACTMNTOPTION结构的相关说明，
   *	目前有两个选项值，其中0表示不进行更新；1表示根据现有特征数据，提取Cell和数线信息；
   *	不管是哪种特征提取模式，updateoption的缺省值都是1
   * 注：在extractmode=1时，如果updateoption=0则将被自动转换成缺省值
   */

  final val GAFIS_TPCARD_EXTRACTOPTION_PARAMNAME = "GAFIS_TPCARD_ExtractOption"
  final val GAFIS_LPCARD_EXTRACTOPTION_PARAMNAME = "GAFIS_LPCARD_ExtractOption"



  class GAFIS_STATICVARSTRUCT extends AncientData
  {
    var nRowCountPerBlk_TPCard:Int = _;
    var nRowCountPerBlk_LPFing:Int = _;
    var nRowCountPerBlk_LPPalm:Int = _;
    var nRowCountPerBlk_Log:Int = _;
    var nRowCountPerBlk_Other:Int = _;

    var nCreateTableThreadCount:Int = _;
    var nUpdateTableThreadCount:Int = _;
    var bInServerMode:Int = _;	// check user right
  var bSupportFinger:Int = _;
    var bSupportPalm:Int = _;
    var bSupportFace:Int = _;
    var bSupportVoice:Int = _;
    // add member after this
    var nQueryExpireTime:Int = _;	// seconds
  // 2002.12.06, the following four parameters will be set by license control system
  var nMaxTPFingerAllowed:Int = _;
    var nMaxTPPalmAllowed:Int = _;
    var nMaxLPFingerAllowed:Int = _;
    var nMaxLPPalmAllowed:Int = _;

    var nCurTPFingerCount:Int = _;
    var nCurTPPalmCount:Int = _;
    var nCurLPFingerCount:Int = _;
    var nCurLPPalmCount:Int = _;

    var nMaxTPFaceAllowed:Int = _;
    var nMaxTPVoiceAllowed:Int = _;
    var nMaxLPFaceAllowed:Int = _;
    var nMaxLPVoiceAllowed:Int = _;

    var nCurTPFaceCount:Int = _;
    var nCurTPVoiceCount:Int = _;
    var nCurLPFaceCount:Int = _;
    var nCurLPVoiceCount:Int = _;

    var nMaxFingerNumberAllowed:Int = _;	// how many fingers allowed
  var nMaxPalmNumberAllowed:Int = _;
    var nMaxFaceNumberAllowed:Int = _;
    var nMaxVoiceNumberAllowed:Int = _;

    var nAutoLTQueryPriority:Int = _;
    var nAutoLLQueryPriority:Int = _;
    var nAutoTTQueryPriority:Int = _;
    var nAutoTLQueryPriority:Int = _;

    var stLimitCrit:Int = _;	// control license controlled finger count
    @Length(96-4)
  var bnRes_Crit:Array[Byte] = _

    var pszTempPath_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTempPath_Data:Array[Byte] = _ // for pszTempPath pointer ,struct:char;
  var nMntFormat:Int = _;		// mnt format.GAFIS_MNTFORMAT_xxx
  //	int		bUseTTFeatFilter;	// when tt match, using filter.
  //	int		bSublibFeat;		// feature table will be created. if bUseTTFeatFilter is 1 and
  // bSublibFeat is zero. then feature will not be used too.
  var nMatchMethod:Int = _;	// match method. GAFIS_MATCHMETHOD_XX
  @Length(32)
  var stMemItem:Array[GBASE_PARAM_MEMITEM] = _;	// 32 items.
  var nMuMaxIdleTime:Int = _;	// 600 seconds.
  var nMessageLevel:Int = _;
    var nSockSendTimeOut:Int = _;	// in seconds.
  var nSockRecvTimeOut:Int = _;	// normal , in seconds.
  var nDBSkmComptDate:Int = _;	// DBSkmComptDate = 20050224, it's an integer.
  var bKeepLatestSkm:Int = _;		// whether keep latest skm.
  var bKeepOldColumn:Int = _;		// whether keep old column when upgrading to new table schema.
  var bAdvQueryTaskSort:Int = _;
    var bFaceHasCpr:Int = _;		// default is not.
  //  the following field is added to help search engine using
  // editor.
  var bEXFDoRollPlainMatch:Int = _;
    var bEXFDoRollPlainPatternReplace:Int = _;
    var nEXFRollPlainUnmatchScore:Int = _;
    var nEXFRollPlainMatchScore:Int = _;
    var bEditDoRollPlainMatch:Int = _;
    var bEditDoRollPlainPatternReplace:Int = _;
    var nEditRollPlainUnmatchScore:Int = _;
    var nEditRollPlainMatchScore:Int = _;
    var bTTSearchQueRemoveDupCand:Int = _;
    var bCheckForeignKeyIntegrity:Int = _;
    var bTTSearchQueAutoMerge:Int = _;
    var nTTAutoMergeCandScore:Int = _;
    var bTTSearchQueAutoCheckRemote:Int = _;	// auto check remote tt query.
  // add member before this
  var nVersion:Int = _;
    var nMaxTextItemLen:Int = _;	// max length of text in GATEXTITEMSTRUCT's data. default is 2mb.
  var stInstallUser = new GAFIS_INSTALLUSER;
    var eStd = new GAFIS_STANDARD;
    var nDataBaseType:Int = _;	// database type. GAFIS_DBTYPE_XXX
  var nMaxCandCount:Int = _;	// maximum candidate count. local used only.
  var bSkmChanged:Int = _;	// whether skm has been changed.

    var nLiveScanMinImageQuality:Int = _;
    var nLiveScanGoodImageQuality:Int = _;
    var bLiveScanDoCrossCheck:Int = _;
    var nLiveScanCrossCheckScore:Int = _;
    var bLiveScanDoSameFingerCheck:Int = _;
    var nLiveScanSameFingerScore:Int = _;
    var bLiveScanWillEditFinger:Int = _;

    var nLiveScanPlainFingerChoice:Int = _;	// GAFIS_LIVESCAN_XXXXX

    @Length(16)
    var szLiveScanFingerEnforced:String = _ ;	// '0000000000' ten string.

    var bNewMemQryAdm:Int = _;
    // the following variables are added on Dec. 15, 2005
    var bPolicy_NonAdmViewAllQuery:Int = _;
    var bPolicy_NonAdmViewAllLatData:Int = _;
    var bPolicy_NonAdmEditAllLatData:Int = _;
    var bPolicy_QueryNeedReCheck:Int = _;

    // the following variable is added on Feb. 13, 2006
    var bAutoMisPersonID:Int = _;

    // the following variables are added Mar. 07, 2006
    var nGfpManagerModule_QryMaxCnt:Int = _;
    var nGfpManagerModule_CardMaxCnt:Int = _;
    var nQueryModule_QryMaxCnt:Int = _;
    var nQueryModule_CardMaxCnt:Int = _;
    var bEditModule_OnlyOwnerKey:Int = _;

    var bQueTTSearchAfterEdit:Int = _;
    var bQueTLSearchAfterEdit:Int = _;
    var bQueLTSearchAfterEdit:Int = _;
    var bQueLLSearchAfterEdit:Int = _;

    var bQueSupportPalmTL:Int = _;
    var bQrySourceSupportMulti:Int = _;
    var bBioMetricCheck:Int = _;
    var bMISPersonIDIsUniq:Int = _;	// whether mis person id is uniq.
  var bMISConnectPersonIDIsUniq:Int = _;	// 2007.01.26.
  var bSupportLPExfQue:Int = _;			// 2007.03.07
  var nLockLevel:Int = _;					// 2007.06.29
  var pLockHandle_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pLockHandle_Data:Array[Byte] = _ // for pLockHandle pointer ,struct:void;				// 2007.06.29
  /*
    在文本查询的情况下，我们首先查询文本，然后再查询特征。但是有的时候文本条件的
    候选个数特别的多，例如可能有几百万（可能比真正的数据库少不了多少），占用特别
    多的内存和硬盘。因此如果候选个数超过下面的数值，我们先进行特征查询，然后才进行
    文本查询。
    如果文本查询的候选大于nTxtSearchCandLimit，则我们先查询特征。
    nTxtSearchCandLimit内部的硬限制是1000-200000。并且我们设置候选结果的个数为
    nCandCountForTextSearch
   */
  var nTxtSearchCandLimit:Int = _;	// 2008.03.04
  var nCandCountForTextSearch:Int = _;		// 2008.03.04

    /*
      // 不需要这个设置。因为在每个TPCARDINFO结构中增加一个标志来标明是否支持新的标志。
      var bTPLPUpdateSingleItem:Int = _;	// TP和LP可以根据TPCARDINFO、LPCARDINFO、CASEINFO来更新独立的列。原来是管理信息必须
                      // 同时更新（没有加入区分每个数据项的指标）。
    */

    var bQualCheckSupportQue:Int = _;
    var pszCandKeyFilterFN_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszCandKeyFilterFN_Data:Array[Byte] = _ // for pszCandKeyFilterFN pointer ,struct:char;	// file name for candidate key filter, "xxcandkeyfilter.ini"

    // the following variables are added [5/17/2007]
    var bTTSearchQryAutoMerge:Int = _;
    var bTTSearchQryAutoCheckLocal:Int = _;	// auto check local tt query.

    // 2008.04.12
    var bIsRmtTTAutoCheck:Int = _;

    /**
     * 设置GAFIS系统缺省的图像压缩方法和压缩率，处理器使用该压缩方法来进行图像压缩
     * Addeb by Beagle on Feb. 27, 2009
     */
    var nImageCprMethod:Int = _;
    var nImageCprRatio:Int = _;

    //! 是否把扫描的掌纹数据备份到硬盘上，以便掌纹特征提取算法升级时重新提取
    var bBackupPalmData2Disk:Int = _;

    //! 定义人像特征提取和比对算法
    var nFaceAlgorithm:Int = _;

    /**
     *	Add by beagle on Oct, 15, 2009
     *  增加一个参数，用来实现王曙光算法中通过现场指纹的人工标点特征信息提取
     *	相应的数线和Cell信息的功能；
     *	现场指纹的特征提取方法：值为LPEXF_METHOD_xxx
     *	在afisinit.ini文件中的参数名为GAFISLPExfMethod
     */
    //int		nLPExfMethod;

    /**
     * Add by beagle on Oct, 30, 2009
     * 增加相应的系统参数，用来实现许公望和王曙光算法的合并功能，
     * 也即nMntFormat=GAFIS_MNTFORMAT_XGWLCW、nMatchMethod=GAFIS_MATCHMETHOD_XGWLCW时：
     * 1、bMixMinutia：用来指示是否要把王曙光提取的细节特征合并到许公望的细节特征中
     * 2、nCandMergeStyle：用来指示TT、LT、TL、LL查询的候选结果的合并方式，值为GAFIS_CANDMERGESTYLE_xxx
     * 在afisinit.ini文件中的参数名为GAFIS_MixMinutia、GAFIS_CandMergeStyle、TT_WsgThreshold
     */
    var bMixMinutia:Int = _;
    var nCandMergeStyle:Int = _;

    /**
     * 许公望和王曙光TT比对，自动认为比中的阈值，许公望算法的TT阈值为nTT_XgwThreshold，缺省为60分
     * 王曙光的缺省值是60，范围是[20,10000]
     */
    var nTT_WsgThreshold:Int = _;

    /**
     * 在用处理器解压缩图像时，是否把解压缩失败的卡片记录到log文件中
     * 在广东fpt导入捺印卡片时对于解压缩失败的卡片需要记录下来，缺省为0不记录
     * 参数名称为GAFIS_EXF_LogCprFailInfo
     */
    var bExfLogCprFailInfo:Int = _;
    /**
     * 把处理器产生的错误记录写到数据库对应的表中，缺省为不记录
     */
    var bExfLogErrorInfo2DB:Int = _;
    /**
     * 在处理一个卡片时，发生了多少次错误后，才从处理队列中删除，缺省为5次，最多10次
     */
    var nExfErrorCntBeforDelFromQue:Int = _;

    /**
     * 特征提取和特征更新选项设置
     */
    var stXgwExfMntOption = new GAFIS_EXTRACTMNTOPTION;
    var stLcwExfMntOption = new GAFIS_EXTRACTMNTOPTION;

    /**
     * 设置服务器发送和接受数据超时时间，单位是秒，缺省为5秒
     */
    var nServerSendTimeOut:Int = _;
    var nServerRecvTimeOut:Int = _;

    /**
     * 广东比对测试的一些选项设置
     */
    var nGDQryTestOption:Int = _;	//!< 值为GDQRYTEST_OPTION_xxx，缺省值为0

    /**
     * On Mar. 25, 2010
     * 用两个参数来控制是否输出检索时间超过指定秒数的SQL语句，现在是硬编码在服务器中，只要检索时间超过10s，就会在
     * errlog.txt文件中输出一条相应的语句，现在更改为由系统参数控制
     */
    var bDBLONGOP_PrintInfo:Int = _;	//!< 是否打印花费了服务器大量时间的那些操作信息，缺省为0，不打印
  var nDBLONGOP_TimeOut:Int = _;		//!< 在要打印的情况下，如果操作时间超过了该值，才打印，缺省值为20s

    /**
     * On Apr. 25, 2010
     *  增加一个系统运行模式，值为GAFIS_SYSTEMRUNMODE_xxx
     *  缺省值为GAFIS_SYSTEMRUNMODE_DEFAULT(0)，也就是我们平时的运行模式
     */
    var nGAFIS_SystemRunMode:Int = _;

    /**
     * 许公望TT算法的阈值，只要由许公望TT算法比对产生的候选得分大于该值，就认为其是匹配的了
     * 该参数与nTTAutoMergeCandScore的区别是：
     *	nTTAutoMergeCandScore -- 是GAFIS系统根据实际情况确定的用来进行TT自动合并的候选应满足的最小得分，与比对算法无关
     */
    var nTT_XgwThreshold:Int = _;

    //!< GAFIS系统产品ID，参数名为DEF_PRODUCT_ID，值为GFS6_PRODUCT_xxx，见gbaselib\rclocid.h中的定义
    var nGAFIS_ProductID:Int = _;

    var bDelTTUnMatchedLocQueQry:Int = _;	//!< 删除本地走队列(search-check)的未比中TT查询，缺省为1：删除

    var bTTSearchQueAutoCheckLocal:Int = _;	// auto check local fifo tt query.

  } // GAFIS_STATICVARSTRUCT;


  final val LPEXF_METHOD_REEXTRACTALL = 0		//!< 重提现场卡片的特征，这是缺省值

  final val GDQRYTEST_OPTION_NEEDSELECTDB = 0x01		//!< 在LT比中率测试时，需要选择目标数据库
  final val GDQRYTEST_OPTION_DECREMENTWAIT = 0x02		//!< LT衰减率测试，等待前一个比对完成

  /**
   * 通过FPT发送TT、TL查询时，查询中的图像数据使用原图
   * 在缺省情况下，TT、TL查询中所带的图像数据是压缩图，不管是否设置了该值，LT查询所带的都是原图
   */
  final val GDQRYTEST_OPTION_USINGIMG = 0x04

  /**
   * 是否把广东TT、LT、TL测试的候选结果保存到文件中
   */
  final val GDQRYTEST_OPTION_SAVEQRYRESULT = 0x08

  /**
   * 在TT比中候选中是否排除掉广东测试时漏掉的卡片，缺省为排除掉
   */
  final val GDQRYTEST_OPTION_DELTTMISSHITCARD = 0x10


  final val GAFIS_SERVERSENDTIMEOUT_DEFAULT = 5
  final val GAFIS_SERVERRECVTIMEOUT_DEFAULT = 5

  final val GAFIS_SERVERSENDTIMEOUT_PARAMNAME = "GAFIS_ServerSendTimeOut"
  final val GAFIS_SERVERRECVTIMEOUT_PARAMNAME = "GAFIS_ServerRecvTimeOut"

  final val DBLONGOP_PRINTINFO_PARAMNAME = "DBLONGOP_PrintInfo"
  final val DBLONGOP_TIMEOUT_PARAMNAME = "DBLONGOP_TimeOut"

  /**
   * GAFIS系统运行模式nGAFIS_SystemRunMode
   */
  final val GAFIS_SYSTEMRUNMODE_DEFAULT = 0	//!< 缺省，平时使用的运行模式
  final val GAFIS_SYSTEMRUNMODE_GDTEST = 1	//!< 广东测试模式
  final val GAFIS_SYSTEMRUNMODE_TESTMODE = 2	//!< 测试模式

  //#define GAFIS_SYSTEMRUNMODE_GUIZHOU		2	//!< 贵州测试模式
  //#define GAFIS_SYSTEMRUNMODE_GANSU		3	//!< 甘肃测试模式
  //#define GAFIS_SYSTEMRUNMODE_GSINGFS		4	//!< 在公司数据中心的甘肃测试模式

  //!< 在GSCH_QRYLISTNODE_BuildCand构造候选时，不管GSCH_MATCH_IsMergeXgwLcwCand是否返回1，都调用GSCH_QRYLISTNODE_BuildMergedCand函数
  final val GAFIS_SYSTEMRUNMODE_MERGEXWCAND = 255


  /**
   *	On Jan. 1, 2010
   *	把原先的LPEXF_METHOD_CELLLINECNT更改为LPEXF_METHOD_AUTOUPDATE，
   *	对于王曙光算法，意义与LPEXF_METHOD_CELLLINECNT一样，根据现有特征提取数线和Cell信息
   *	对于许公望，表示afiskernel.h中XGWMNTEXTRACTSTR结构的MntBeUpdated成员取值为EXTRACTMNT_AUTO
   */
  //#define	LPEXF_METHOD_CELLLINECNT		1		//!< 提取数线和Cell信息，只对王曙光的算法有用
  //#define	LPEXF_METHOD_AUTOUPDATE			1

  final val GAFIS_CANDMERGESTYLE_ONLYXGW = 0		//!< 表示二次比对时，只取许公望的候选
  final val GAFIS_CANDMERGESTYLE_ONLYLCW = 1		//!< 表示二次比对时，只取王曙光的候选
  final val GAFIS_CANDMERGESTYLE_XGWLCW = 2		//!< 表示二次比对时，同时显示许公望和王曙光的候选
  final val GAFIS_CANDMERGESTYLE_MIXXL = 3		//!< 表示二次比对时，对许公望和王曙光的候选按照一定的算法进行合并，显示合并后的候选
  final val GAFIS_CANDMERGESTYLE_XGWSTEPONE = 4		//!< 只做许公望的一次比对，返回许一次比对的得分情况，主要目的是比较许一次和二次比对的得分差别
  final val GAFIS_CANDMERGESTYLE_LCWSTEPONE = 5		//!< 只使用王曙光的算法进行比对，因为一次比对时没有王曙光的特征数据，所以在一次比对时所有候选都返回满分
  final val GAFIS_CANDMERGESTYLE_LCWRANDOM = 6		//!< 只使用王曙光的算法进行无指位比对，实现方式与GAFIS_CANDMERGESTYLE_LCWSTEPONE类似
  final val GAFIS_CANDMERGESTYLE_MAXVALUE = 6		//!< 允许的最大值


  //#define GAFIS_CPRMETHOD_PARAMNAME	"CprMethod"
  //#define GAFIS_CPRRATIO_PARAMNAME	"CprRatio"

  final val GAFIS_FACEALGORITHM_PARAMNAME = "FaceAlgorithm"

  final val GAFIS_MATCHMETHOD_XGWNEW = 0
  final val GAFIS_MATCHMETHOD_LCW = 1
  final val GAFIS_MATCHMETHOD_XGWOLD = 2
  final val GAFIS_MATCHMETHOD_XCG = 3
  final val GAFIS_MATCHMETHOD_XGWLCW = 4
  final val GAFIS_MATCHMETHOD_ZKY = 5

  // GAFIS_STATICVARSTRUCT::nDataBaseType
  final val GAFIS_DBTYPE_NUMINA = 0
  final val GAFIS_DBTYPE_ORACLE = 1
  final val GAFIS_DBTYPE_MSSQL = 2
  final val GAFIS_DBTYPE_DB2 = 3
  final val GAFIS_DBTYPE_MYSQL = 4
  final val GAFIS_DBTYPE_SYBASE = 5

  // GAFIS_STATICVARSTRUCT::nLiveScanPlainFingerChoice
  final val GAFIS_LIVESCAN_DISABLEPALIN = 0	// can not capture plain finger.
  final val GAFIS_LIVESCAN_FORCEPLAIN = 1	// plain finger must be captured.
  final val GAFIS_LIVESCAN_FREEPLAIN = 2	// plain finger can be captured freely

  //!< GAFIS_STATICVARSTRUCT::nFaceAlgorithm
  final val GAFIS_FACEALGORITHM_EGFS = 0	//!< 使用东方金指的人像算法
  final val GAFIS_FACEALGORITHM_COGNITECH = 1	//!< 使用Cognitech公司的人像算法

  //!< GAFIS_STATICVARSTRUCT::nMaxCandCount
  final val GAFIS_MAXCANDCOUNT_LOWER = 100
  final val GAFIS_MAXCANDCOUNT_HIGHER = 100000


  class GAFIS_CACHEDBENTRY extends AncientData
  {
    var nDBID:Short = _;
    var nType:Byte = _ ;	// db type.
  var bnRes:Byte = _ ;
    var nCacheTime:Int = _;	// cached time.
  } // GAFIS_CACHEDBENTRY;	// 8 bytes long.

  class GAFIS_CACHEDBADM extends AncientData
  {
    var stCrit = new GBASE_CRIT;	// critical section. 96 bytes long.
  var pstDB_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstDB_Data:Array[GAFIS_CACHEDBENTRY] = _ // for pstDB pointer ,struct:GAFIS_CACHEDBENTRY;
  @Length(4)
  var bnRes_Pt:Array[Byte] = _ ;
    var nBufCnt:Short = _;
    var nDBCnt:Short = _;
    var tLoginTime:Int = _;	// login time.
  @Length(16)
  var szServerName:String = _ ;	// server name.
  } // GAFIS_CACHEDBADM;	// size is 128 bytes long.

  // #define	CAND_FILTER_LOCAL_MANAGE	0

  /**
   * 用来存放广东测试比对结果文件
   */
  /**
   * 查询第一名与后续非重卡的分差相关信息
   */
  class GDTEST_QRYSCOREDIFF_INFO extends AncientData
  {
    var fRatio:Double = _;		//!< 分差比，分为6段：>=1.3、>=1.25、>=1.2、>=1.15、>=1.1、>=1.5
  var nQryCount:Int = _;	//!< 属于这个分差段的查询个数
  var nDupCard:Int = _;	//!< 属于这个分差段的LT查询，与第一名是重卡的候选数（包括第一名自己)
  } //GDTEST_QRYSCOREDIFF_INFO;

  class GDTEST_QRYRESULT_SAVEDATA extends AncientData
  {
    //!< TT比中信息
    var nTTHitCount:Int = _;		//!< TT比中的查询数
  var nTTDupCardCnt:Int = _;		//!< 所有TT比中的查询一共查中了多少重卡

    /**
     * LT比中信息，分别统计第一名与后续非重卡的分差为>=1.3、>=1.25、>=1.2、>=1.15、>=1.1、>=1.5
     * 的查询的个数，已经对应的重卡数
     */
    @Length(8)
    var stLTMatchInfo:Array[GDTEST_QRYSCOREDIFF_INFO] = _;
    @Length(8)
    var stTLMatchInfo:Array[GDTEST_QRYSCOREDIFF_INFO] = _;

    var nLTMatchInfo:Int = _;
    var nTLMatchInfo:Int = _;
  } //GDTEST_QRYRESULT_SAVEDATA;

  class GDTEST_SAVEQRYRESULT_DATA extends AncientData
  {
    @Length(256)
    var szQryResultPath:String = _ ;	//!< 用来保存比对结果的目录，为"%GAFIS_HOME%\DOC\"
  var nLastSavedTime:Int = _;			//!< 最近一次保存比对结果文件的时间

    var bInited:Byte = _ ;	//!< 是否已经初始化了
  var bNeedSaved:Byte = _ ;	//!< 是否更新了数据，需要保存到文件中
  @Length(2)
  var bnRes:Array[Byte] = _ ;

    var stSaveData = new GDTEST_QRYRESULT_SAVEDATA;

  } //GDTEST_SAVEQRYRESULT_DATA;

  final val GDTEST_QRYRESULT_SAVEDATA_TEXTFILE1 = "GD_MatchResult1.txt"
  final val GDTEST_QRYRESULT_SAVEDATA_DATAFILE1 = "GD_MatchResult1.dat"
  final val GDTEST_QRYRESULT_SAVEDATA_TEXTFILE2 = "GD_MatchResult2.txt"
  final val GDTEST_QRYRESULT_SAVEDATA_DATAFILE2 = "GD_MatchResult2.dat"


  //!< 缓冲所有与缉控人员有重卡关系的捺印卡片
  class UTIL_CACHEWANTEDTPCARDLIST extends AncientData
  {
    var stCrit = new GBASE_CRIT;			// critical section. 96 bytes long.
  var stCardList = new GARRAY_STRUCT;
    var bInited:Byte = _ ;
    var bCardCached:Byte = _ ;
    @Length(6)
    var bnRes:Array[Byte] = _ ;
  } //UTIL_CACHEWANTEDTPCARDLIST;


  class GAFISLOCVARSTRUCT extends AncientData
  {
    var bWillExitAFIS:Int = _;
    var bServerCapInited:Int = _;
    var critSysSect = new GAFIS_CRITSECT;
    var critMessageCrit = new GAFIS_CRITSECT;
    var bDBInited:Int = _;
    var stReadyQry = new GAMEMQUERYQUESTRUCT;
    var stQryAdm = new GAFIS_MEMQUERYADM;	// memory query adm
  //	GAMEMQUERYQUESTRUCT	stTextQry;	// added on may. 19. 2005 for text query.
  var critMemQryQue = new GAFIS_CRITSECT;
    var critExfQue = new GAFIS_CRITSECT;
    var critTPLib = new GAFIS_CRITSECT;
    var critLPLib = new GAFIS_CRITSECT;
    var critQryLib = new GAFIS_CRITSECT;
    var critFifoQue = new GAFIS_CRITSECT;
    var pszInnerUserName_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszInnerUserName_Data:Array[Byte] = _ // for pszInnerUserName pointer ,struct:char;
  var pszInnerExfServerName_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszInnerExfServerName_Data:Array[Byte] = _ // for pszInnerExfServerName pointer ,struct:char;
  var pszInnerMatcherServerName_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszInnerMatcherServerName_Data:Array[Byte] = _ // for pszInnerMatcherServerName pointer ,struct:char;
  var pszInnerMatcherName_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszInnerMatcherName_Data:Array[Byte] = _ // for pszInnerMatcherName pointer ,struct:char;
  var pszinnerUserPass_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszinnerUserPass_Data:Array[Byte] = _ // for pszinnerUserPass pointer ,struct:char;
  var pszInnerExfServerPass_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszInnerExfServerPass_Data:Array[Byte] = _ // for pszInnerExfServerPass pointer ,struct:char;
  var pszInnerMatcherServerPass_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszInnerMatcherServerPass_Data:Array[Byte] = _ // for pszInnerMatcherServerPass pointer ,struct:char;
  var pszInnerMatcherPass_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszInnerMatcherPass_Data:Array[Byte] = _ // for pszInnerMatcherPass pointer ,struct:char;
  var stUser = new GAFIS_USERACCOUNTINMEM;
    var stMicStream = new GADB_MICSTREAMNAMESTRUCT;
    var stDefDBID = new GAFIS_DEFDBIDSTRUCT;

    @Length(96)
    var stMemItem:Array[GBASE_PARAM_MEMITEM] = _;	// 96 items.
  @Length(4)
  var stQryPriBoost:Array[GAFIS_QUERYPRIBOOST] = _;
    // we maintain muinfo in local lib to improve the performance
    // of query searching. the following structure is used by
    // db server only, so initialize in db server.
    var stMumg = new GAFIS_MATCHSVRMGMT;	// matchunit management.
  @Length(64)
  var szNewDBMainPath:String = _ ;	// used for create new db.
  var stCacheDB = new GAFIS_CACHEDBADM;
    var hNewQueryEvent = new GAFIS_EVENT_HANDLE;	// add new query
  var hFinQueryEvent = new GAFIS_EVENT_HANDLE;	// query finished event.
  var stQryStatusList = new GAFIS_QRYSTATUSLIST;	// query status list.
  var stCodeTbMgr = new GAFIS_CODETB_CACHEMGR;
    var stTPGetMisIDCrit = new GBASE_CRIT;	// generate mis person id critical section.

    // we store these parameter's in database's variable table, so need store here
    var pszCandFilter_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszCandFilter_Data:Array[Byte] = _ // for pszCandFilter pointer ,struct:char;
  var nCandFilterLen:Int = _;
    var stCandFilter = new GBASE_CRIT;	// to allow simultaneous access.

    var stBioDataSize = new GAFIS_BIODATASIZE;	// bio data size(tp lp, need to check invalid data.).

    var stRmtTTAutoCheckIf = new GAFIS_USERKEY_FILTER_LIST;
    var stRmtNoTTAutoCheckIf = new GAFIS_USERKEY_FILTER_LIST;

    /**
     * 广东测试用来存放比对结果的相关数据结构
     */
    var stGDTEST_SQRData = new GDTEST_SAVEQRYRESULT_DATA;

    //!< 缓冲所有与缉控人员有重卡关系的捺印卡片
    var stWantedCardList = new UTIL_CACHEWANTEDTPCARDLIST;

  } // GAFISLOCVARSTRUCT;


  final val SERVERTYPE_APP = 0x0
  final val SERVERTYPE_MATCHSVR = 0x1
  final val SERVERTYPE_EXFER = 0x2
  final val SERVERTYPE_MATCHER = 0x3




  final val TTN_TPCARDTEXT = 0x0
  final val TTN_PERSONTEXT = 0x1
  final val TTN_LPCASETEXT = 0x2
  final val TTN_LPFINGERTEXT = 0x3
  final val TTN_LPPALMTEXT = 0x4
  final val TTN_LPFACETEXT = 0x5
  final val TTN_LPVOICETEXT = 0x6

  // note : nTblIdx are values TTN_*

  final val GAFIS_OUTERMSG_NAME = "OuterMsg"
  final val GAFIS_INNERMSG_NAME = "InnerMsg"

}
