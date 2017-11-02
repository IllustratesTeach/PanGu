package nirvana.hall.c.services.gfpt5lib

import java.text.SimpleDateFormat
import java.util
import java.util.Date
import javax.xml.bind.annotation._

/**
  * Created by yuchen on 2017/10/17.
  */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "package")
class FPT5File {


    /**
      * 刑专系统：来源系统为固定内容“XZ”；
      * 警综平台：来源系统为固定内容“JZ”；
      * 指掌纹自动识别系统：来源系统为固定内容“AFIS”；
      * 采集系统：来源系统为固定内容“CJ”；
      * 现勘系统：来源系统为固定内容“XK”；
      * 其他系统：来源系统内容为”QT”。
      */
    final val XINGZHUAN_SYSTEM = "XZ"
    final val JINGZONG_SYSTEM = "JZ"
    final val AFIS_SYSTEM = "AFIS"
    final val CAPTURE_SYSTEM = "CJ"
    final val SURVEY_SYSTEM = "XK"
    final val OTHER_SYSTEM = "QT"

    final val EMPTY_STRING = ""

    @XmlElement(name = "packageHead")
    var packageHead: PackageHead = new PackageHead
    @XmlElement(name = "taskInfo")
    var taskInfo: TaskInfo = new TaskInfo
    @XmlElement(name = "fingerprintPackage")
    var fingerprintPackage: util.ArrayList[FingerprintPackage] = new util.ArrayList[FingerprintPackage]
    @XmlElement(name = "latentPackage")
    var latentPackage: util.ArrayList[LatentPackage] = new util.ArrayList[LatentPackage]
    @XmlElement(name = "LatentTaskPackage")
    var latentTaskPackage: util.ArrayList[LatenttaskPackage] = new util.ArrayList[LatenttaskPackage]
    @XmlElement(name = "PrintTaskPackage")
    var printTaskPackage: util.ArrayList[PrinttaskPackage] = new util.ArrayList[PrinttaskPackage]
    @XmlElement(name = "LTResultPackage")
    var ltResultPackage: util.ArrayList[LtResultPackage] = new util.ArrayList[LtResultPackage]
    @XmlElement(name = "TLResultPackage")
    var tlResultPackage: util.ArrayList[TlResultPackage] = new util.ArrayList[TlResultPackage]
    @XmlElement(name = "TTResultPackage")
    var ttResultPackage: util.ArrayList[TtResultPackage] = new util.ArrayList[TtResultPackage]
    @XmlElement(name = "LLResultPackage")
    var llResultPackage: util.ArrayList[LlResultPackage] = new util.ArrayList[LlResultPackage]
    @XmlElement(name = "LTHitResultPackage")
    var ltHitResultPackage: util.ArrayList[LtHitResultPackage] = new util.ArrayList[LtHitResultPackage]
    @XmlElement(name = "TTHitResultPackage")
    var ttHitResultPackage: util.ArrayList[TtHitResultPackage] = new util.ArrayList[TtHitResultPackage]
    @XmlElement(name = "LLHitResultPackage")
    var llHitResultPackage: util.ArrayList[LlHitResultPackage] = new util.ArrayList[LlHitResultPackage]
    @XmlElement(name = "cancellatentPackage")
    var cancelLatentPackage: util.ArrayList[cancelLatentPackage] = new util.ArrayList[cancelLatentPackage]

    /**
      * 构建fpt对象，校验数据，设置head信息，计算逻辑记录长度，fileLength
      *
      * @return
      */
    def build(fpt5File: FPT5File): FPT5File = {
        val taskInfo = new TaskInfo
        taskInfo.fingerprintNum = fpt5File.fingerprintPackage.size
        taskInfo.latentNum = fpt5File.latentPackage.size
        taskInfo.LTHitResultNum = fpt5File.ltHitResultPackage.size
        taskInfo.TTHitResultNum = fpt5File.ttHitResultPackage.size
        taskInfo.LLHitResultNum = fpt5File.llHitResultPackage.size
        taskInfo.latentTaskNum = fpt5File.latentTaskPackage.size
        taskInfo.printTaskNum = fpt5File.printTaskPackage.size
        taskInfo.LTResultNum = fpt5File.ltResultPackage.size
        taskInfo.TLResultNum = fpt5File.tlResultPackage.size
        taskInfo.TTResultNum = fpt5File.ttResultPackage.size
        taskInfo.LLResultNum = fpt5File.llResultPackage.size
        taskInfo.CancelLatentNum = fpt5File.cancelLatentPackage.size
        taskInfo.sendUnitCode = EMPTY_STRING //发送单位代码
        taskInfo.sendUnitName = EMPTY_STRING //发送单位名称
        taskInfo.sendUnitSystemType = EMPTY_STRING //发送单位系统
        taskInfo.sendPersonName = EMPTY_STRING //发送人姓名
        taskInfo.sendPersonIdCard = EMPTY_STRING //发送人身份证号码
        taskInfo.sendPersonTel = EMPTY_STRING //发送人联系电话
        fpt5File.taskInfo = taskInfo
        fpt5File
    }
}




    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "PackageHead")
    class PackageHead{
        @XmlElement(name = "version")
        var version: String = "FPT0500"
        @XmlElement(name = "createTime") //日期时间型,格式是”YYYYMMDDHH24MISS”
        var createTime: String = new SimpleDateFormat("YYYYMMddHHmmss").format(new Date)
        @XmlElement(name = "originSystem")
        var originSystem:String = _
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "TaskInfo")
    class TaskInfo{
        @XmlElement(name = "fingerprintNum")
        var fingerprintNum:Int = _
        @XmlElement(name = "latentNum")
        var latentNum:Int = _
        @XmlElement(name = "LTHitResultNum")
        var LTHitResultNum:Int = _
        @XmlElement(name = "TTHitResultNum")
        var TTHitResultNum:Int = _
        @XmlElement(name = "LLHitResultNum")
        var LLHitResultNum:Int = _
        @XmlElement(name = "latentTaskNum")
        var latentTaskNum:Int = _
        @XmlElement(name = "printTaskNum")
        var printTaskNum:Int = _
        @XmlElement(name = "LTResultNum")
        var LTResultNum:Int = _
        @XmlElement(name = "TLResultNum")
        var TLResultNum:Int = _
        @XmlElement(name = "TTResultNum")
        var TTResultNum:Int = _
        @XmlElement(name = "LLResultNum")
        var LLResultNum:Int = _
        @XmlElement(name = "CancelLatentNum")
        var CancelLatentNum:Int = _
        @XmlElement(name = "fsdw_gajgjgdm")
        var sendUnitCode:String = _ //发送单位代码
        @XmlElement(name = "fsdw_gajgmc")
        var sendUnitName:String = _ //发送单位名称
        @XmlElement(name = "fsdw_xtlx")
        var sendUnitSystemType:String = _ //发送单位系统类型
        @XmlElement(name = "fsr_xm")
        var sendPersonName:String = _ //发送人姓名
        @XmlElement(name = "fsr_gmsfhm")
        var sendPersonIdCard:String = _ //发送人身份证号码
        @XmlElement(name = "fsr_lxdm")
        var sendPersonTel:String = _ //发送人联系电话
    }

    /**
      * 捺印
      */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "FingerprintPackage")
    class FingerprintPackage{
        @XmlElement(name = "descriptMsg")
        var descriptMsg: DescriptMsg = new DescriptMsg
        @XmlElement(name = "fingerprintMsg")
        var fingerprintMsg:FingerprintMsg = new FingerprintMsg
        @XmlElement(name = "fingers")
        var fingers:Fingers = new Fingers
        @XmlElement(name = "palms")
        var palms:Palms = new Palms
        @XmlElement(name = "fourprints")
        var fourprints:Fourprints = new Fourprints
        @XmlElement(name = "knuckleprints")
        var knuckleprints:Knuckleprints = new Knuckleprints
        @XmlElement(name = "fullpalms")
        var fullpalms:Fullpalms = new Fullpalms
        @XmlElement(name = "portraits")
        var portraits:Portraits = new Portraits
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "FingerprintMsg")
    class FingerprintMsg{
        @XmlElement(name = "zwbdxtlxms")
        var fingerprintComparisonSysTypeDescript:String = _ //指纹比对系统类型描述
        @XmlElement(name = "nydw_gajgjgdm")
        var chopUnitCode:String = _ //捺印单位公安机关机构代码
        @XmlElement(name = "nydw_gajgmc")
        var chopUnitName:String = _ //捺印单位公安机关机构名称
        @XmlElement(name = "nyry_xm")
        var chopPersonName:String = _ //捺印人姓名
        @XmlElement(name = "nyry_gmsfhm")
        var chopPersonIdCard:String = _ //捺印人身份证号码
        @XmlElement(name = "nyry_lxdh")
        var chopPersonTel:String =_ //捺印人联系电话
        @XmlElement(name = "nysj")
        var chopDateTime:String = _ //捺印时间
        @XmlElement(name = "bz")
        var memo:String = _ //备注
        @XmlElement(name = "zw_sl")
        var fingerNum:Int = _ //指纹数量
        @XmlElement(name = "zhw_sl")
        var palmNum:Int = _ //掌纹数量
        @XmlElement(name = "slz_sl")
        var fourFingerNum:Int = _ //四联指数量
        @XmlElement(name = "zjw_sl")
        var knuckleFingerNum:Int = _ //指节纹数量
        @XmlElement(name = "qz_sl")
        var fullPalmNum:Int = _ // 全掌数量
        @XmlElement(name = "rx_sl")
        var personPictureNum:Int = _ //人像数量
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "DescriptMsg")
    class DescriptMsg{
        @XmlElement(name = "ysxt_asjxgrybh")
        var originalSystemCasePersonId:String = _ //原始系统案事件相关人员编号
        @XmlElement(name = "jzrybh")
        var jingZongPersonId:String = _ //警综人员编号
        @XmlElement(name = "asjxgrybh")
        var casePersonid:String = _ //案事件人员编号
        @XmlElement(name = "zzwkbh")
        var fingerPalmCardId:String = _ //指掌纹卡编号
        @XmlElement(name = "bnyzwyydm")
        var chopedCauseCode:String = _ //被捺印指纹原因代码
        @XmlElement(name = "xm")
        var name:String = _
        @XmlElement(name = "bmch")
        var alias:String = _
        @XmlElement(name = "xbdm")
        var sex:String = _
        @XmlElement(name = "csrq")
        var birthday:String = _
        @XmlElement(name = "gjdm")
        var nationality:String = _
        @XmlElement(name = "mzdm")
        var nation:String = _
        @XmlElement(name = "cyzjdm")
        var credentialsCode:String = _ //常用证件代码
        @XmlElement(name = "zjhm")
        var credentialsNo:String = _ //证件号码
        @XmlElement(name = "hjdz_xzqhdm")
        var houkouAdministrativeDivisionCode:String = _ //户籍地行政区划代码
        @XmlElement(name = "hjdz_dzmc")
        var houkouAddress:String = _ //户籍地地址名称
        @XmlElement(name = "xzz_xzqhdm")
        var houseAdministrativeDivisionCode:String = _ //现住址行政区划代码
        @XmlElement(name = "xzz_dzmc")
        var houseAddress:String = _ //现住址地址
    }


    //--------------------指纹相关begin-------------------------//
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Fingers")
    class Fingers{
        @XmlElement(name = "rolling")
        var rolling:Rolling = new Rolling
        @XmlElement(name = "planar")
        var planar:Planar = new Planar
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Rolling")
    class Rolling{
        @XmlElement(name = "fingerMsg")
        var fingerMsg = new util.ArrayList[FingerMsg]()
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Planar")
    class Planar{
        @XmlElement(name = "fingerMsg")
        var fingerMsg = new util.ArrayList[FingerMsg]()
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "FingerMsg")
    class FingerMsg{
        @XmlElement(name = "zwzwdm")
        var fingerPositionCode:String = _
        @XmlElement(name = "zzwtztqfsdm")
        var fingerFeatureExtractionMethodCode:String  = _
        @XmlElement(name = "qzyydm")
        var adactylismCauseCode:String = _ //缺指原因代码
        @XmlElement(name = "zwwxzfl_zwwxdm")
        var fingerPatternMasterCode:String = _ //指纹纹型主分类代码
        @XmlElement(name = "zwwxffl_zwwxdm")
        var fingerPatternSlaveCode:String = _  //指纹纹型副分类代码
        @XmlElement(name = "zwfxms")
        var fingerDirectionDescript:String = _ //指纹方向描述
        @XmlElement(name = "zwzxd")
        var fingerCenterPoint:String = _ //指纹中心点
        @XmlElement(name = "zwfzx")
        var fingerSlaveCenter:String = _ //指纹副中心
        @XmlElement(name = "zwzsj")
        var fingerLeftTriangle:String = _ //指纹左三角
        @XmlElement(name = "zwysj")
        var fingerRightTriangle:String = _
        @XmlElement(name = "zwtzd_sl")
        var fingerExtractionNum:Int = _
        @XmlElement(name = "zwtzdxx")
        var fingerExtractionInfo:String = _
        @XmlElement(name = "zw_zdyxx")
        var fingerCustomInfo:Array[Byte] = _
        @XmlElement(name = "zw_txspfxcd")
        var fingerImageHorizontalDirectionLength:Int = _
        @XmlElement(name = "zw_txczfxcd")
        var fingerImageVerticalDirectionLength:Int = _
        @XmlElement(name = "zw_txfbl")
        var fingerImageRatio:Int = _

      /**
        * 0000表示无压缩，其他值前2个字节数字代表系统产品生产的单位代码
        * ，后2个字节数字代表系统产品的版本，从00开始递增，对于WSQ压缩方法
        * ，前2个数字固定为14，后两个数字代表系统产品生产的单位代码
        */
        @XmlElement(name = "zw_txysffms")
        var fingerImageCompressMethodDescript:String = _
        @XmlElement(name = "zw_txsj")
        var fingerImageData:Array[Byte] = _
    }
    //--------------------指纹相关end-------------------------//



    //--------------------掌纹相关begin-----------------------//
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Palms")
    class Palms{
        @XmlElement(name = "palmMsg")
        var palmMsg = new util.ArrayList[PalmMsg]()
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "PalmMsg")
    class PalmMsg{
        @XmlElement(name = "zhwzwdm")
        var palmPostionCode:String = _
        @XmlElement(name = "qszwyydm")
        var lackPalmCauseCode:String = _ //缺掌原因代码
        @XmlElement(name = "zzwtztqfsdm")
        var palmFeatureExtractionMethodCode:String = _
        @XmlElement(name = "zhwzfd_sl")
        var palmRetracingPoint:Int = _ //掌纹折返点数量
        @XmlElement(name = "zhwzfdxx")
        var palmRetracingPointInfo:String = _ //掌纹折返点信息
        @XmlElement(name = "zhwsjd_sl")
        var palmTrianglePointNum:Int = _ //掌纹三角点数量
        @XmlElement(name = "zhwsjdxx")
        var palmTrianglePointInfo:String = _ //掌纹三角点信息
        @XmlElement(name = "zhwtzd_sl")
        var palmFeaturePointNum:Int = _ //掌纹特征点数量
        @XmlElement(name = "zhwtzdxx")
        var palmFeaturePointInfo:String = _ //掌纹特征点信息
        @XmlElement(name = "zhw_zdyxx")
        var palmCustomInfo:Array[Byte] = _
        @XmlElement(name = "zhw_txspfxcd")
        var palmImageHorizontalDirectionLength:Int = _
        @XmlElement(name = "zhw_txczfxcd")
        var palmImageVerticalDirectionLength:Int = _
        @XmlElement(name = "zhw_txfbl")
        var palmImageRatio:Int = _

      /**
        * 0000表示无压缩，其他值前2个字节数字代表系统产品生产的单位代码，
        * 后2个字节数字代表系统产品的版本，从00开始递增，对于WSQ压缩方法，
        * 前2个数字固定为14，后两个数字代表系统产品生产的单位代码
        */
      @XmlElement(name = "zhw_txysffms")
        var palmImageCompressMethodDescript:String = _
        @XmlElement(name = "zhw_txsj")
        var palmImageData:Array[Byte] = _

    }
    //--------------------掌纹相关end-----------------------//



    //--------------------四联指相关begin-----------------------//
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Fourprints")
    class Fourprints{
        @XmlElement(name = "fourprintMsg")
        var fourprintMsg = new util.ArrayList[FourprintMsg]()
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "FourprintMsg")
    class FourprintMsg{
        @XmlElement(name = "slz_zwzwdm")
        var fourPrintPostionCode:String = _
        @XmlElement(name = "slz_qzyydm")
        var fourPrintLackFingerCauseCode:String = _
        @XmlElement(name = "slz_zdyxx")
        var fourPrintCustomInfo:Array[Byte] = _
        @XmlElement(name = "slz_txspfxcd")
        var fourPrintImageHorizontalDirectionLength:Int = _
        @XmlElement(name = "slz_txczfxcd")
        var fourPrintImageVerticalDirectionLength:Int = _
        @XmlElement(name = "slz_txfbl")
        var fourPrintImageRatio:Int = _
        @XmlElement(name = "slz_txysffms")
        var fourPrintImageCompressMethodDescript:String = _
        @XmlElement(name = "slz_txsj")
        var fourPrintImageData:Array[Byte] = _
    }
    //--------------------四联指相关end-----------------------//



    //--------------------指节纹相关begin-----------------------//
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Knuckleprints")
    class Knuckleprints{
        @XmlElement(name = "knuckleprintMsg")
        var knuckleprintMsg = new util.ArrayList[KnuckleprintMsg]()
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "KnuckleprintMsg")
    class KnuckleprintMsg{
        @XmlElement(name = "zjw_zwzwdm")
        var knucklePrintPostionCode:String = _
        @XmlElement(name = "zjw_qzyydm")
        var knucklePrintLackFingerCauseCode:String = _
        @XmlElement(name = "zjw_zdyxx")
        var knucklePrintCustomInfo:Array[Byte] = _
        @XmlElement(name = "zjw_txspfxcd")
        var knucklePrintImageHorizontalDirectionLength:Int = _
        @XmlElement(name = "zjw_txczfxcd")
        var knucklePrintImageVerticalDirectionLength:Int = _
        @XmlElement(name = "zjw_txfbl")
        var knucklePrintImageRatio:Int = _
        @XmlElement(name = "zjw_txysffms")
        var knucklePrintImageCompressMethodDescript:String = _
        @XmlElement(name = "zjw_txsj")
        var knucklePrintImageData:Array[Byte] = _
    }
    //--------------------指节纹相关end-----------------------//




    //--------------------全掌相关begin-----------------------//
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Fullpalms")
    class Fullpalms{
        @XmlElement(name = "fullpalmMsg")
        var fullpalmMsg = new util.ArrayList[FullpalmMsg]()
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "FullpalmMsg")
    class FullpalmMsg{
        @XmlElement(name = "qz_zwzwdm")
        var fullPalmPostionCode:String = _
        @XmlElement(name = "qz_qzyydm")
        var fullPalmLackPalmCauseCode:String = _
        @XmlElement(name = "qz_zdyxx")
        var fullPalmCustomInfo:Array[Byte] = _
        @XmlElement(name = "qz_txspfxcd")
        var fullPalmImageHorizontalDirectionLength:Int = _
        @XmlElement(name = "qz_txczfxcd")
        var fullPalmImageVerticalDirectionLength:Int = _
        @XmlElement(name = "qz_txfbl")
        var fullPalmImageRatio:Int = _
        @XmlElement(name = "qz_txysffms")
        var fullPalmImageCompressMethodDescript:String = _
        @XmlElement(name = "qz_txsj")
        var fullPalmImageData:Array[Byte] = _
    }
    //--------------------全掌相关end-----------------------//

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Portraits")
class Portraits{
    @XmlElement(name = "rxzplxdm")
    var personPictureTypeCode:String = _
    @XmlElement(name = "rx_txsj")
    var personPictureImageData:Array[Byte] = _
}


    /**
      * 现场
      */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LatentPackage")
    class LatentPackage{
        @XmlElement(name = "caseMsg")
        var caseMsg:CaseMsg = new CaseMsg
        @XmlElement(name = "fingers")
        var latentFingers:LatentFingers = new LatentFingers
        @XmlElement(name = "palms")
        var latentPalms:LatentPalms = new LatentPalms
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "CaseMsg")
    class CaseMsg{
        @XmlElement(name = "zwbdxtlxms")
        var latentFingerprintComparisonSysTypeDescript:String = _
        @XmlElement(name = "ysxt_asjbh")
        var originalSystemCaseId:String = _
        @XmlElement(name = "asjbh")
        var caseId:String = _
        @XmlElement(name = "xckybh")
        var latentSurveyId:String = _
        @XmlElement(name = "xczzwkbh")
        var latentCardId:String = _
        @XmlElement(name = "ajlbdm")
        var caseTypeCode:String = _ //案件类别代码 逗号分隔，最多10个
        @XmlElement(name = "asjsscw_jermby")
        var money:Long = _
        @XmlElement(name = "asjfsdd_xzqhdm")
        var caseOccurAdministrativeDivisionCode:String = _
        @XmlElement(name = "asjfsdd_dzmc")
        var caseOccurAddress:String = _
        @XmlElement(name = "jyaq")
        var briefCase:String = _
        @XmlElement(name = "sfma_pdbz")
        var whetherKill:String = _
        @XmlElement(name = "tqsj_gajgjgdm")
        var extractUnitCode:String = _
        @XmlElement(name = "tqsj_gajgmc")
        var extractUnitName:String = _
        @XmlElement(name = "tqry_xm")
        var extractPerson:String = _
        @XmlElement(name = "tqry_gmsfhm")
        var extractPersonIdCard:String = _
        @XmlElement(name = "tqry_lxdh")
        var extractPersonTel:String = _
        @XmlElement(name = "tqsj")
        var extractDateTime:String = _
        @XmlElement(name = "xczw_sl")
        var latentFingerNum:Int = _
        @XmlElement(name = "xczhw_sl")
        var latentPalmNum:Int = _

    }

    //--------------------现场指纹相关begin-----------------------//
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LatentFingers")
    class LatentFingers{
        @XmlElement(name = "fingerMsg")
        var latentfingerMsg = new util.ArrayList[LatentFingerMsg]()
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LatentFingerMsg")
    class LatentFingerMsg{
      /**
        * 新数据必须提供，规则是‘F+现勘号后22位+4位物证分类代码+3位顺序号'
        * ,历史数据如无现勘编号，前23位用'F0000000000000000000000'占位
        */
        @XmlElement(name = "ysxt_xczzwbh")
        var originalSystemLatentFingerPalmId:String = _
        @XmlElement(name = "xcwzbh")
        var latentPhysicalId:String = _
        @XmlElement(name = "xczw_xczzwylbw")
        var latentFingerLeftPosition:String = _ //现场指纹遗留部位
        @XmlElement(name = "xczw_zzwtztqfsdm")
        var latentFingerFeatureExtractMethodCode:String = _
        @XmlElement(name = "xczw_stzzw_pdbz")
        var latentFingerCorpseJudgeIdentify:String = _
        @XmlElement(name = "xczw_rtxysdm")
        var latentFingerMastoidProcessLineColorCode:String = _ //乳突线颜色代码
        @XmlElement(name = "xczw_lzks_xcwzbh")
        var latentFingerConnectFingerBeginPhysicalId:String = _
        @XmlElement(name = "xczw_lzjs_xcwzbh")
        var latentFingerConnectFingerEndPhysicalId:String = _
        @XmlElement(name = "xczw_zwbdztdm")
        var latentFingerComparisonStatusCode:String = _
        @XmlElement(name = "xczw_tzzhsl")
        var latentFingerFeatureGroupNum:Int = _
        @XmlElement(name = "xczwtzz")
        var latentFingerFeatureGroup: util.ArrayList[Xczwtzz] = new util.ArrayList[Xczwtzz]
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Xczwtzz")
    class Xczwtzz{
        @XmlElement(name = "xczw_tzzhbsf")
        var latentFingerFeatureGroupIdentify:String = _
        @XmlElement(name = "xczw_tzzhms")
        var latentFingerFeatureGroupDescript:String = _
        @XmlElement(name = "xczw_fxzw_jyqk")
        var latentFingerAnalysisPostionBrief:String = _
        @XmlElement(name = "xczw_zwwxdm")
        var latentFingerPatternCode:String = _
        @XmlElement(name = "xczw_zwfxms")
        var latentFingerDirectionDescript:String = _
        @XmlElement(name = "xczw_zwzxd")
        var latentFingerCenterPoint:String = _
        @XmlElement(name = "xczw_zwfzx")
        var latentFingerSlaveCenter:String = _
        @XmlElement(name = "xczw_zwzsj")
        var latentFingerLeftTriangle:String = _
        @XmlElement(name = "xczw_zwysj")
        var latentFingerRightTriangle:String = _
        @XmlElement(name = "xczw_zwtzd_sl")
        var latentFingerFeatureNum:Int = _
        @XmlElement(name = "xczw_zwtzdxx")
        var latentFingerFeatureInfo:String = _
        @XmlElement(name = "xczw_zdyxx")
        var latentFingerCustomInfo:Array[Byte] = _
        @XmlElement(name = "xczw_txspfxcd")
        var latentFingerImageHorizontalDirectionLength:Int = _
        @XmlElement(name = "xczw_txczfxcd")
        var latentFingerImageVerticalDirectionLength:Int = _
        @XmlElement(name = "xczw_txfbl")
        var latentFingerImageRatio:Int = _
        @XmlElement(name = "xczw_txysffms")
        var latentFingerImageCompressMethodDescript:String = _
        @XmlElement(name = "xczw_txsj")
        var latentFingerImageData:Array[Byte] = _
    }
    //--------------------现场指纹相关end-----------------------//


    //--------------------现场掌纹相关begin-----------------------//
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LatentPalms")
    class LatentPalms{
        @XmlElement(name = "plamMsg")
        var latentplamMsg = new util.ArrayList[LatentplamMsg]()
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LatentplamMsg")
    class LatentplamMsg {

        @XmlElement(name = "xczhw_xczzwbh")
        var latentPalmId: String = _
        @XmlElement(name = "xczhw_xcwzbh")
        var latentPalmPhysicalId: String = _
        @XmlElement(name = "xczhw_xczzwylbw")
        var latentPalmLeftPostion: String = _
        @XmlElement(name = "xczhw_zzwtztqfsdm")
        var latentPalmFeatureExtractMethodCode: String = _
        @XmlElement(name = "xczhw_stzzw_pdbz")
        var latentPalmCorpseJudgeIdentify: String = _
        @XmlElement(name = "xczhw_rtxysdm")
        var latentPalmMastoidProcessLineColorCode: String = _
        @XmlElement(name = "xczhw_zhwbdztdm")
        var latentPalmComparisonStatusCode: String = _
        @XmlElement(name = "xczhw_tzzhsl")
        var latentPalmFeatureGroupNum:Int = _
        @XmlElement(name = "xczhwtzz")
        var latentPalmFeatureGroup: util.ArrayList[Xczhwtzz] = new util.ArrayList[Xczhwtzz]
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Xczhwtzz")
    class Xczhwtzz{

        @XmlElement(name = "xczhw_tzzhbsf")
        var latentPalmFeatureGroupIdentify:String = _
        @XmlElement(name = "xczhw_tzzhms")
        var latentPalmFeatureGroupDescript:String = _
        @XmlElement(name = "xczhw_fxzhw_jyqk")
        var latentPalmAnalysisPostionBrief:String = _
        @XmlElement(name = "xczhw_zwzfd_sl")
        var latentPalmRetracingPointNum:Int = _
        @XmlElement(name = "xczhw_zwzfdxx")
        var latentPalmRetracingPointInfo:String = _
        @XmlElement(name = "xczhw_zwsjd_sl")
        var latentPalmTrianglePointNum:Int = _
        @XmlElement(name = "xczhw_zwsjdxx")
        var latentPalmTrianglePointInfo:String = _
        @XmlElement(name = "xczhw_zhwtzd_sl")
        var latentPalmFeaturePointNum:Int = _
        @XmlElement(name = "xczhw_zhwtzdxx")
        var latentPalmFeaturePointInfo:String = _
        @XmlElement(name = "xczhw_zdyxx")
        var latentPalmCustomInfo:Array[Byte] = _
        @XmlElement(name = "xczhw_txspfxcd")
        var latentPalmImageHorizontalDirectionLength:Int = _
        @XmlElement(name = "xczhw_txczfxcd")
        var latentPalmImageVerticalDirectionLength:Int = _
        @XmlElement(name = "xczhw_txfbl")
        var latentPalmImageRatio:Int = _
        @XmlElement(name = "xczhw_txysffms")
        var latentPalmImageCompressMethodDescript:String = _
        @XmlElement(name = "xczhw_txsj")
        var latentPalmImageData:Array[Byte] = _
    }
    //--------------------现场掌纹相关end-----------------------//


    /**
      * 现场指掌纹查询比对请求信息
      */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LatenttaskPackage")
    class LatenttaskPackage{
        @XmlElement(name = "rwbh")
        var taskId:String = _
        @XmlElement(name = "zzwbdrwlxdm")
        var taskTypeCode:String = _
        @XmlElement(name = "zwbdxtlxms")
        var comparisonSystemDescript:String = _
        @XmlElement(name = "ysxt_asjbh")
        var originalSystemCaseId:String = _
        @XmlElement(name = "asjbh")
        var caseId:String = _
        @XmlElement(name = "xckybh")
        var latentSurveyId:String = _
        @XmlElement(name = "ysxt_xczzwbh")
        var originalSystemLatentFingerId:String = _
        @XmlElement(name = "xcwzbh")
        var latentPhysicalId:String = _
        @XmlElement(name = "xczzwkbh")
        var latentCardId:String = _
        @XmlElement(name = "tjsj")
        var submitDateTime:String = _
        @XmlElement(name = "latentPackage")
        var latentPackage:util.ArrayList[LatentPackage] = new util.ArrayList[LatentPackage]
    }


    /**
      * 捺印指掌纹查询比对请求信息
      */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "PrinttaskPackage")
    class PrinttaskPackage{
        @XmlElement(name = "rwbh")
        var taskId:String = _
        @XmlElement(name = "zzwbdrwlxdm")
        var taskTypeCode:String = _
        @XmlElement(name = "zwbdxtlxms")
        var comparisonSystemDescript:String = _
        @XmlElement(name = "ysxt_asjxgrybh")
        var originalSystemPersonId:String = _
        @XmlElement(name = "jzrybh")
        var jingZongPersonId:String = _
        @XmlElement(name = "asjxgrybh")
        var personId:String = _
        @XmlElement(name = "zzwkbh")
        var cardId:String = _
        @XmlElement(name = "tjsj")
        var submitDateTime:String = _
        @XmlElement(name = "fingerprintPackage")
        var fingerprintPackage:FingerprintPackage = new FingerprintPackage
    }


    /**
      * 指掌纹正查比对结果信息
      */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LtResultPackage")
    class LtResultPackage{
        @XmlElement(name = "rwbh")
        var taskId:String = _
        @XmlElement(name = "zwbdxtlxms")
        var comparisonSystemTypeDescript:String = _
        @XmlElement(name = "bddw_gajgjgdm")
        var comparisonUnitCode:String = _
        @XmlElement(name = "bddw_gajgmc")
        var comparisonUnitName:String = _
        @XmlElement(name = "bdwcsj")
        var comparisonCompleteDateTime:String = _
        @XmlElement(name = "ysxt_asjbh")
        var originalSystemCaseId:String = _
        @XmlElement(name = "asjbh")
        var caseId:String = _
        @XmlElement(name = "xckybh")
        var latentSurveyId:String = _
        @XmlElement(name = "ysxt_xczzwbh")
        var originalSystemLatentFingerId:String = _
        @XmlElement(name = "xcwzbh")
        var latentPhysicalId:String = _
        @XmlElement(name = "xczzwkbh")
        var latentCardId:String = _
        @XmlElement(name = "latentPackage")
        var latentPackage:util.ArrayList[LatentPackage] = new util.ArrayList[LatentPackage]
        @XmlElement(name = "resultMsg")
        var resultMsg:util.ArrayList[LTResultMsg] = new util.ArrayList[LTResultMsg]
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LTResultMsg")
    class LTResultMsg{
        @XmlElement(name = "bdjg_sl")
        var resultNum:Int = _
        @XmlElement(name = "bdjg_mc")
        var resultRanking:Int = _
        @XmlElement(name = "bdjg_df")
        var resultScore:Int = _
        @XmlElement(name = "bdjg_ysxt_asjxgrybh")
        var resultOriginalSystemPersonId:String = _
        @XmlElement(name = "bdjg_jzrybh")
        var resultJingZongPersonId:String = _
        @XmlElement(name = "bdjg_asjxgrybh")
        var resultPersonId:String = _
        @XmlElement(name = "bdjg_zzwkbh")
        var resultCardId:String = _
        @XmlElement(name = "bdjg_zzwdm")
        var resultFingerPalmPostionCode:String = _ //比对结果指掌位代码
        @XmlElement(name = "fingerprintPackage")
        var fingerprintPackage:util.ArrayList[FingerprintPackage] = new util.ArrayList[FingerprintPackage]
    }


    /**
      * 指掌纹倒查比对结果信息
      */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "TlResultPackage")
    class TlResultPackage{
        @XmlElement(name = "rwbh")
        var taskId:String = _
        @XmlElement(name = "zwbdxtlxms")
        var comparisonSystemTypeDescript:String = _
        @XmlElement(name = "bddw_gajgjgdm")
        var comparisonUnitCode:String = _
        @XmlElement(name = "bddw_gajgmc")
        var comparisonUnitName:String = _
        @XmlElement(name = "bdwcsj")
        var comparisonCompleteDateTime:String = _
        @XmlElement(name = "ysxt_asjxgrybh")
        var originalSystemPersonId:String = _
        @XmlElement(name = "jzrybh")
        var jingZongPersonId:String = _
        @XmlElement(name = "asjxgrybh")
        var personId:String = _
        @XmlElement(name = "zzwkbh")
        var cardId:String = _
        @XmlElement(name = "fingerprintPackage")
        var fingerprintPackage:util.ArrayList[FingerprintPackage] = new util.ArrayList[FingerprintPackage]
        @XmlElement(name = "resultMsg")
        var resultMsg:util.ArrayList[TLResultMsg] = new util.ArrayList[TLResultMsg]
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "TLResultMsg")
    class TLResultMsg{
        @XmlElement(name = "bdjg_sl")
        var resultNum:Int = _
        @XmlElement(name = "bdjg_mc")
        var resultRanking:Int = _
        @XmlElement(name = "bdjg_df")
        var resultScore:Int = _
        @XmlElement(name = "bdjg_ysxt_asjbh")
        var resultOriginalSystemCaseId:String = _
        @XmlElement(name = "bdjg_asjbh")
        var resultCaseId:String = _
        @XmlElement(name = "bdjg_xckybh")
        var resultLatentSurveyId:String = _
        @XmlElement(name = "bdjg_ysxt_xczzwbh")
        var resultOriginalSystemLatentFingerPalmId:String = _
        @XmlElement(name = "bdjg_xcwzbh")
        var resultLatentPhysicalId:String = _
        @XmlElement(name = "bdjg_xczzwkbh")
        var resultCardId:String = _
        @XmlElement(name = "bdjg_zzwdm")
        var resultFingerPalmPostionCode:String = _
        @XmlElement(name = "latentPackage")
        var latentPackage:util.ArrayList[LatentPackage] = new util.ArrayList[LatentPackage]
    }


    /**
      * 指掌纹查重比对结果信息
      */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "TtResultPackage")
    class TtResultPackage{
        @XmlElement(name = "xxzjbh")
        var xxzjbh:String = _
        @XmlElement(name = "rwbh")
        var taskId:String = _
        @XmlElement(name = "zwbdxtlxms")
        var comparisonSystemTypeDescript:String = _
        @XmlElement(name = "bddw_gajgjgdm")
        var comparisonUnitCode:String = _
        @XmlElement(name = "bddw_gajgmc")
        var comparisonUnitName:String = _
        @XmlElement(name = "bdwcsj")
        var comparisonCompleteDateTime:String = _
        @XmlElement(name = "ysxt_asjxgrybh")
        var originalSystemPersonId:String = _
        @XmlElement(name = "jzrybh")
        var jingZongPersonId:String = _
        @XmlElement(name = "asjxgrybh")
        var personId:String = _
        @XmlElement(name = "zzwkbh")
        var cardId:String = _
        @XmlElement(name = "sfzw_pdbz")
        var whetherFingerJudgmentMark:String = _
        @XmlElement(name = "fingerprintPackage")
        var fingerprintPackage:util.ArrayList[FingerprintPackage] = new util.ArrayList[FingerprintPackage]
        @XmlElement(name = "resultMsg")
        var resultMsg:util.ArrayList[TTResultMsg] = new util.ArrayList[TTResultMsg]
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "TTResultMsg")
    class TTResultMsg{
        @XmlElement(name = "bdjg_sl")
        var resultNum:Int = _
        @XmlElement(name = "bdjg_mc")
        var resultRanking:Int = _
        @XmlElement(name = "bdjg_df")
        var resultScore:Int = _
        @XmlElement(name = "bdjg_ysxt_asjxgrybh")
        var resultOriginalSystemPersonId:String = _
        @XmlElement(name = "bdjg_jzrybh")
        var resultJingZongPersonId:String = _
        @XmlElement(name = "bdjg_asjxgrybh")
        var resultPersonId:String = _
        @XmlElement(name = "bdjg_zzwkbh")
        var resultCardId:String = _
        @XmlElement(name = "zzwccbzlxdm")
        var ttHitTypeCode:String = _
        @XmlElement(name = "fingerprintPackage")
        var fingerprintPackage:util.ArrayList[FingerprintPackage] = new util.ArrayList[FingerprintPackage]
    }


    /**
      * 指掌纹串查比对结果信息
      */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LlResultPackage")
    class LlResultPackage{
        @XmlElement(name = "xxzjbh")
        var xxzjbh:String = _
        @XmlElement(name = "rwbh")
        var taskId:String = _
        @XmlElement(name = "zwbdxtlxms")
        var comparisonSystemTypeDescript:String = _
        @XmlElement(name = "bddw_gajgjgdm")
        var comparisonUnitCode:String = _
        @XmlElement(name = "bddw_gajgmc")
        var comparisonUnitName:String = _
        @XmlElement(name = "bdwcsj")
        var comparisonCompleteDateTime:String = _
        @XmlElement(name = "ysxt_asjbh")
        var originalSystemCaseId:String = _
        @XmlElement(name = "asjbh")
        var caseId:String = _
        @XmlElement(name = "xckybh")
        var latentSurveyId:String = _
        @XmlElement(name = "ysxt_xczzwbh")
        var originalSystemLatentFingerId:String = _
        @XmlElement(name = "xcwzbh")
        var latentPhysicalId:String = _
        @XmlElement(name = "xczzwkbh")
        var latentCardId:String = _
        @XmlElement(name = "latentPackage")
        var latentPackage:util.ArrayList[LatentPackage] = new util.ArrayList[LatentPackage]
        @XmlElement(name = "resultMsg")
        var resultMsg:util.ArrayList[LLResultMsg] = new util.ArrayList[LLResultMsg]
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LLResultMsg")
    class LLResultMsg{
        @XmlElement(name = "bdjg_sl")
        var resultNum:Int = _
        @XmlElement(name = "bdjg_mc")
        var resultRanking:Int = _
        @XmlElement(name = "bdjg_df")
        var resultScore:Int = _
        @XmlElement(name = "bdjg_ysxt_asjbh")
        var resultOriginalSystemCaseId:String = _
        @XmlElement(name = "bdjg_asjbh")
        var resultCaseId:String = _
        @XmlElement(name = "bdjg_xckybh")
        var resultLatentSurveyId:String = _
        @XmlElement(name = "bdjg_xcwzbh")
        var resultLatentPhysicalId:String = _
        @XmlElement(name = "bdjg_ysxt_xczzwbh")
        var resultOriginalSystemLatentFingerId:String = _
        @XmlElement(name = "bdjg_xczzwkbh")
        var resultLatentCardId:String = _
        @XmlElement(name = "latentPackage")
        var latentPackage:util.ArrayList[LatentPackage] = new util.ArrayList[LatentPackage]
    }


    /**
      * 指掌纹正查和倒查比中信息
      */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LtHitResultPackage")
    class LtHitResultPackage{
        @XmlElement(name = "zwbdxtlxms")
        var comparisonSystemTypeDescript:String = _
        @XmlElement(name = "xczw_asjbh")
        var latentFingerCaseId:String = _
        @XmlElement(name = "xczw_ysxt_asjbh")
        var latentFingerOriginalSystemCaseId:String = _
        @XmlElement(name = "xczw_xckybh")
        var latentFingerLatentSurveyId:String = _
        @XmlElement(name = "xczw_ysxt_xczzwbh")
        var latentFingerOriginalSystemFingerId:String = _
        @XmlElement(name = "xczw_xcwzbh")
        var latentFingerLatentPhysicalId:String = _
        @XmlElement(name = "xczw_xczzwkbh")
        var latentFingerCardId:String = _
        @XmlElement(name = "nyzw_ysxt_asjxgrybh")
        var fingerPrintOriginalSystemPersonId:String = _
        @XmlElement(name = "nyzw_jzrybh")
        var fingerPrintJingZongPersonId:String = _
        @XmlElement(name = "nyzw_asjxgrybh")
        var fingerPrintPersonId:String = _
        @XmlElement(name = "nyzw_zzwkbh")
        var fingerPrintCardId:String = _
        @XmlElement(name = "nyzw_zzwdm")
        var fingerPrintPostionCode:String = _
        @XmlElement(name = "nyzw_zzwbdffdm")
        var fingerPrintComparisonMethodCode:String = _
        @XmlElement(name = "bzdw_gajgjgdm")
        var hitUnitCode:String = _
        @XmlElement(name = "bzdw_gajgmc")
        var hitUnitName:String = _
        @XmlElement(name = "bzr_xm")
        var hitPersonName:String = _
        @XmlElement(name = "bzr_gmsfhm")
        var hitPersonIdCard:String = _
        @XmlElement(name = "bzr_lxdh")
        var hitPersonTel:String = _
        @XmlElement(name = "bzsj")
        var hitDateTime:String = _
        @XmlElement(name = "fhdw_gajgjgdm")
        var checkUnitCode:String = _
        @XmlElement(name = "fhdw_gajgmc")
        var checkUnitName:String = _
        @XmlElement(name = "fhr_xm")
        var checkPersonName:String = _
        @XmlElement(name = "fhr_gmsfhm")
        var checkPersonIdCard:String = _
        @XmlElement(name = "fhr_lxdh")
        var checkPersonTel:String = _
        @XmlElement(name = "fhsj")
        var checkDateTime:String = _
        @XmlElement(name = "bz")
        var memo:String = _
        @XmlElement(name = "latentPackage")
        var latentPackage:util.ArrayList[LatentPackage] = new util.ArrayList[LatentPackage]
        @XmlElement(name = "fingerprintPackage")
        var fingerprintPackage:util.ArrayList[FingerprintPackage] = new util.ArrayList[FingerprintPackage]
    }


    /**
      * 指掌纹查重比中信息
      */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "TtHitResultPackage")
    class TtHitResultPackage{
        @XmlElement(name = "zwbdxtlxms")
        var comparisonSystemTypeDescript:String = _
        @XmlElement(name = "zzwccbzlxdm")
        var ttHitTypeCode:String = _
        @XmlElement(name = "ysxt_asjxgrybh")
        var originalPersonId:String = _
        @XmlElement(name = "jzrybh")
        var jingZongPersonId:String = _
        @XmlElement(name = "asjxgrybh")
        var personId:String = _
        @XmlElement(name = "zzwkbh")
        var cardId:String = _
        @XmlElement(name = "sfzw_pdbz")
        var whetherFingerJudgmentMark:String = _
        @XmlElement(name = "bzjg_ysxt_asjxgrybh")
        var resultOriginalSystemPersonId:String = _
        @XmlElement(name = "bzjg_jzrybh")
        var resultjingZongPersonId:String = _
        @XmlElement(name = "bzjg_asjxgrybh")
        var resultPersonId:String = _
        @XmlElement(name = "bzjg_zzwkbh")
        var resultCardId:String = _
        @XmlElement(name = "bzdw_gajgjgdm")
        var hitUnitCode:String = _
        @XmlElement(name = "bzdw_gajgmc")
        var hitUnitName:String = _
        @XmlElement(name = "bzr_xm")
        var hitPersonName:String = _
        @XmlElement(name = "bzr_gmsfhm")
        var hitPersonIdCard:String = _
        @XmlElement(name = "bzr_lxdh")
        var hitPersonTel:String = _
        @XmlElement(name = "bzsj")
        var hitDateTime:String = _
        @XmlElement(name = "fhdw_gajgjgdm")
        var checkUnitCode:String = _
        @XmlElement(name = "fhdw_gajgmc")
        var checkUnitName:String = _
        @XmlElement(name = "fhr_xm")
        var checkPersonName:String = _
        @XmlElement(name = "fhr_gmsfhm")
        var checkPersonIdCard:String = _
        @XmlElement(name = "fhr_lxdh")
        var checkPersonTel:String = _
        @XmlElement(name = "fhsj")
        var checkDateTime:String = _
        @XmlElement(name = "bz")
        var memo:String = _
        @XmlElement(name = "fingerprintPackage")
        var fingerprintPackageSource:util.ArrayList[FingerprintPackage] = new util.ArrayList[FingerprintPackage]
        @XmlElement(name = "fingerprintPackage")
        var fingerprintPackageDesc:util.ArrayList[FingerprintPackage] = new util.ArrayList[FingerprintPackage]
    }


    /**
      * 指掌纹串查比中信息
      */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LlHitResultPackage")
    class LlHitResultPackage{
        @XmlElement(name = "xxzjbh")
        var xxzjbh:String = _
        @XmlElement(name = "zwbdxtlxms")
        var comparisonSystemTypeDescript:String = _
        @XmlElement(name = "ysxt_asjbh")
        var originalSystemCaseId:String = _
        @XmlElement(name = "asjbh")
        var caseId:String = _
        @XmlElement(name = "xckybh")
        var latentSurveyId:String = _
        @XmlElement(name = "ysxt_xczzwbh")
        var originalSystemLatentFingerId:String = _
        @XmlElement(name = "xcwzbh")
        var latentPhysicalId:String = _
        @XmlElement(name = "xczzwkbh")
        var cardId:String = _
        @XmlElement(name = "bzjg_ysxt_asjbh")
        var resultOriginalSystemCaseId:String = _
        @XmlElement(name = "bzjg_asjbh")
        var resultCaseId:String = _
        @XmlElement(name = "bzjg_xckybh")
        var resultLatentSurveyId:String = _
        @XmlElement(name = "bzjg_ysxt_xczzwbh")
        var resultOriginalSystemLatentPersonId:String = _
        @XmlElement(name = "bzjg_xcwzbh")
        var resultLatentPhysicalId:String = _
        @XmlElement(name = "bzjg_xczzwkbh")
        var resultCardId:String = _
        @XmlElement(name = "bzdw_gajgjgdm")
        var hitUnitCode:String = _
        @XmlElement(name = "bzdw_gajgmc")
        var hitUnitName:String = _
        @XmlElement(name = "bzr_xm")
        var hitPersonName:String = _
        @XmlElement(name = "bzr_gmsfhm")
        var hitPersonIdCard:String = _
        @XmlElement(name = "bzr_lxdh")
        var hitPersonTel:String = _
        @XmlElement(name = "bzsj")
        var hitDateTime:String = _
        @XmlElement(name = "fhdw_gajgjgdm")
        var checkUnitCode:String = _
        @XmlElement(name = "fhdw_gajgmc")
        var checkUnitName:String = _
        @XmlElement(name = "fhr_xm")
        var checkPersonName:String = _
        @XmlElement(name = "fhr_gmsfhm")
        var checkPersonIdCard:String = _
        @XmlElement(name = "fhr_lxdh")
        var checkPersonTel:String = _
        @XmlElement(name = "fhsj")
        var checkDateTime:String = _
        @XmlElement(name = "bz")
        var memo:String = _
        @XmlElement(name = "latentPackage")
        var latentPackageSource:util.ArrayList[LatentPackage] = new util.ArrayList[LatentPackage]
        @XmlElement(name = "latentPackage")
        var latentPackageDesc:util.ArrayList[LatentPackage] = new util.ArrayList[LatentPackage]
    }


    /**
      * 撤销现场指纹信息
      */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "cancelLatentPackage")
    class cancelLatentPackage{
        @XmlElement(name = "ysxt_asjbh")
        var originalSystemCaseId:String = _
        @XmlElement(name = "asjbh")
        var caseId:String = _
        @XmlElement(name = "xckybh")
        var latentSurveyId:String = _
        @XmlElement(name = "ysxt_xczzwbh")
        var originalSystemLatentFingerId:String = _
        @XmlElement(name = "xcwzbh")
        var latentPhysicalId:String = _
        @XmlElement(name = "xczzwkbh")
        var latentCardId:String = _
        @XmlElement(name = "bldw_gajgjgdm")
        var handleUnitCode:String = _
        @XmlElement(name = "bldw_gajgmc")
        var handleUnitName:String = _
        @XmlElement(name = "blr_xm")
        var handlePersonName:String = _
        @XmlElement(name = "blr_gmsfhm")
        var handlePersonIdCard:String = _
        @XmlElement(name = "blr_lxdh")
        var handlePersonTel:String = _
        @XmlElement(name = "bzsj")
        var hitDateTime:String = _
    }







