package nirvana.hall.c.services.gfpt5lib

import java.text.SimpleDateFormat
import java.util
import java.util.Date
import javax.xml.bind.annotation.{XmlElement, _}

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
    @XmlElement(name = "fingerprintPackage")
    var fingerprintPackage: Array[FingerprintPackage] = _
    @XmlElement(name = "latentPackage")
    var latentPackage: Array[LatentPackage] = _
    @XmlElement(name = "LatentTaskPackage")
    var latentTaskPackage: Array[LatenttaskPackage] = _
    @XmlElement(name = "PrintTaskPackage")
    var printTaskPackage: Array[PrinttaskPackage] = _
    @XmlElement(name = "LTResultPackage")
    var ltResultPackage: Array[LtResultPackage] = _
    @XmlElement(name = "TLResultPackage")
    var tlResultPackage: Array[TlResultPackage] = _
    @XmlElement(name = "TTResultPackage")
    var ttResultPackage: Array[TtResultPackage] = _
    @XmlElement(name = "LLResultPackage")
    var llResultPackage: Array[LlResultPackage] = _
    @XmlElement(name = "LTHitResultPackage")
    var ltHitResultPackage: Array[LtHitResultPackage] = _
    @XmlElement(name = "TTHitResultPackage")
    var ttHitResultPackage: Array[TtHitResultPackage] = _
    @XmlElement(name = "LLHitResultPackage")
    var llHitResultPackage: Array[LlHitResultPackage] = _
    @XmlElement(name = "cancellatentPackage")
    var cancelLatentPackage: Array[cancelLatentPackage] = _

    /**
      * 构建fpt对象，校验数据，设置head信息，计算逻辑记录长度，fileLength
      *
      * @return
      */
    def build(fpt5File: FPT5File): FPT5File = {
        fpt5File
    }
}


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "packageHead")
    class PackageHead{
        @XmlElement(name = "version")
        var version: String = "FPT0500"
        @XmlElement(name = "createTime") //日期时间型,格式是”YYYYMMDDHH24MISS”
        var createTime: String = new SimpleDateFormat("YYYYMMddHHmmss").format(new Date)
        @XmlElement(name = "originSystem")
        var originSystem:String = _
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
    @XmlType(name = "fingerprintPackage")
    class FingerprintPackage{
        @XmlElement(name = "descriptiveMsg")
        var descriptiveMsg: DescriptMsg = _
        @XmlElement(name = "collectInfoMsg")
        var collectInfoMsg:CollectInfoMsg = _
        @XmlElement(name = "fingers")
        var fingers:Fingers = _
        @XmlElement(name = "palms")
        var palms:Palms = _
        @XmlElement(name = "fourprints")
        var fourprints:Fourprints = _
        @XmlElement(name = "knuckleprints")
        var knuckleprints:Knuckleprints = _
        @XmlElement(name = "fullpalms")
        var fullpalms:Fullpalms = _
        @XmlElement(name = "faceImages")
        var faceImages:FaceImages = _
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "CollectInfoMsg")
    class CollectInfoMsg{
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
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "descriptMsg")
    class DescriptMsg{
        @XmlElement(name = "ysxt_asjxgrybh")
        var originalSystemCasePersonId:String = _ //原始系统案事件相关人员编号
        @XmlElement(name = "jzrybh")
        var jingZongPersonId:String = _ //警综人员编号
        @XmlElement(name = "asjxgrybh")
        var casePersonid:String = _ //案事件人员编号
        @XmlElement(name = "zzhwkbh")
        var fingerPalmCardId:String = _ //指掌纹卡编号
        @XmlElement(name = "collectingReasonSet")
        var collectingReasonSet = new CollectingReasonSet
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
        @XmlElement(name = "bz")
        var memo:String = _
    }


    //--------------------指纹相关begin-------------------------//
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "CollectingReasonSet")
    class CollectingReasonSet{
        @XmlElement(name = "cjxxyydm")
        var captureInfoReasonCode:Array[String] = _ //采集信息原因代码
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "FingerMinutiaSet")
    class FingerMinutiaSet{
        @XmlElement(name = "minutia")
        var minutia:Array[Minutia] = _
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Minutia")
    class Minutia{
        @XmlElement(name = "zwtzd_tzxzb")
        var fingerFeaturePointXCoordinate:Int = _
        @XmlElement(name = "zwtzd_tzyzb")
        var fingerFeaturePointYCoordinate:Int = _
        @XmlElement(name = "zwtzd_tzfx")
        var fingerFeaturePointDirection:Int = _
        @XmlElement(name = "zwtzd_tzzl")
        var fingerFeaturePointQuality:Int = _
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Fingers")
    class Fingers{
        @XmlElement(name = "fingerMsg")
        var fingerMsg:Array[FingerMsg] = _
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "FingerMsg")
    class FingerMsg{
        @XmlElement(name = "zwzwdm")
        var fingerPositionCode:String = _
        @XmlElement(name = "zzhwtztqfsdm")
        var fingerFeatureExtractionMethodCode:String  = _
        @XmlElement(name = "zzhwqsqkdm")
        var adactylismCauseCode:String = _ //指掌纹缺失情况代码
        @XmlElement(name = "zwwxzfl_zwwxdm")
        var fingerPatternMasterCode:String = _ //指纹纹型主分类代码
        @XmlElement(name = "zwwxffl_zwwxdm")
        var fingerPatternSlaveCode:String = _  //指纹纹型副分类代码
        @XmlElement(name = "zwfx_tzfx")
        var fingerFeatureDirection:Int = _ //指纹方向-特征方向
        @XmlElement(name = "zwfx_tzfxfw")
        var fingerFeatureDirectionRange:Int = _ //指纹方向-特征方向-范围
        @XmlElement(name = "zwzxd_tzxzb")
        var fingerCenterPointFeatureXCoordinate:Int = _ //指纹中心点特征X坐标
        @XmlElement(name = "zwzxd_tzyzb")
        var fingerCenterPointFeatureYCoordinate:Int = _ //指纹中心点特征Y坐标
        @XmlElement(name = "zwzxd_tzzbfw")
        var fingerCenterPointFeatureCoordinateRange:Int = _ //指纹中心点特征坐标范围
        @XmlElement(name = "zwzxd_tzfx")
        var fingerCenterPointFeatureDirection:Int = _ //指纹中心点特征特征方向
        @XmlElement(name = "zwzxd_tzfxfw")
        var fingerCenterPointFeatureDirectionRange:Int = _ //指纹中心点特征特征方向范围
        @XmlElement(name = "zwzxd_tzkkd")
        var fingerCenterPointFeatureReliabilityLevel:Int = _ //指纹中心点可靠度
        @XmlElement(name = "zwfzx_tzxzb")
        var fingerSlaveCenterFeatureXCoordinate:Int = _ //指纹副中心特征X坐标
        @XmlElement(name = "zwfzx_tzyzb")
        var fingerSlaveCenterFeatureYCoordinate:Int = _ //指纹副中心特征Y坐标
        @XmlElement(name = "zwfzx_tzzbfw")
        var fingerSlaveCenterFeatureCoordinateRange:Int = _ //指纹副中心特征坐标范围
        @XmlElement(name = "zwfzx_tzfx")
        var fingerSlaveCenterFeatureDirection:Int = _ //指纹副中心特征方向
        @XmlElement(name = "zwfzx_tzfxfw")
        var fingerSlaveCenterFeatureDirectionRange:Int = _ //指纹副中心特征方向范围
        @XmlElement(name = "zwfzx_tzkkd")
        var fingerSlaveCenterFeatureReliabilityLevel:Int = _ //指纹副中心特征可靠度
        @XmlElement(name = "zwzsj_tzxzb")
        var fingerLeftTriangleFeatureXCoordinate:Int = _ //指纹左三角特征X坐标
        @XmlElement(name = "zwzsj_tzyzb")
        var fingerLeftTriangleFeatureYCoordinate:Int = _ //指纹左三角特征Y坐标
        @XmlElement(name = "zwzsj_tzzbfw")
        var fingerLeftTriangleFeatureCoordinateRange:Int = _ //指纹左三角特征坐标范围
        @XmlElement(name = "zwzsj_tzfx")
        var fingerLeftTriangleFeatureDirection:Int = _ //指纹左三角特征方向
        @XmlElement(name = "zwzsj_tzfxfw")
        var fingerLeftTriangleFeatureDirectionRange:Int = _ //指纹左三角特征方向范围
        @XmlElement(name = "zwzsj_tzkkd")
        var fingerLeftTriangleFeatureReliabilityLevel:Int = _ //指纹左三角特征方向范围
        @XmlElement(name = "zwysj_tzxzb")
        var fingerRightTriangleFeatureXCoordinate:Int = _ //指纹右三角特征X坐标
        @XmlElement(name = "zwysj_tzyzb")
        var fingerRightTriangleFeatureYCoordinate:Int = _ //指纹右三角特征Y坐标
        @XmlElement(name = "zwysj_tzzbfw")
        var fingerRightTriangleFeatureCoordinateRange:Int = _ //指纹右三角特征坐标范围
        @XmlElement(name = "zwysj_tzfx")
        var fingerRightTriangleFeatureDirection:Int = _ //指纹右三角特征方向
        @XmlElement(name = "zwysj_tzfxfw")
        var fingerRightTriangleFeatureDirectionRange:Int = _ //指纹右三角特征方向范围
        @XmlElement(name = "zwysj_tzkkd")
        var fingerRightTriangleFeatureReliabilityLevel:Int = _ //指纹右三角特征方向范围
        @XmlElement(name = "minutiaSet")
        var fingerMinutiaSet = new FingerMinutiaSet
        @XmlElement(name = "zw_zdyxx")
        var fingerCustomInfo:Array[Byte] = _
        @XmlElement(name = "zw_txspfxcd")
        var fingerImageHorizontalDirectionLength:Int = _
        @XmlElement(name = "zw_txczfxcd")
        var fingerImageVerticalDirectionLength:Int = _
        @XmlElement(name = "zw_txfbl")
        var fingerImageRatio:Int = _
        /**
          * 0000表示无压缩，其他值前2个字节数字代表系统产品生产的单位代码，
          * 后2个字节数字代表系统产品的版本，从00开始递增，对于WSQ压缩方法，
          * 前2个数字固定为14，后两个数字代表系统产品生产的单位代码
          */
        @XmlElement(name = "zw_txysffms")
        var fingerImageCompressMethodDescript:String = _
        @XmlElement(name = "zw_txzl")
        var fingerImageQuality:Int = _
        @XmlElement(name = "zw_txsj")
        var fingerImageData:Array[Byte] = _
    }
    //--------------------指纹相关end-------------------------//

    //--------------------掌纹相关begin-----------------------//
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "CoreLikePatternSet")
    class CoreLikePatternSet{
        @XmlElement(name = "coreLikePatternSet")
        var coreLikePattern:Array[CoreLikePattern] =_
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "CoreLikePattern")
    class CoreLikePattern{

        @XmlElement(name = "zhwzfd_tzxzb")
        var palmRetracingPointFeatureXCoordinate:Int = _ //掌纹折返点_特征X坐标
        @XmlElement(name = "zhwzfd_tzyzb")
        var palmRetracingPointFeatureYCoordinate:Int = _ //掌纹折返点_特征Y坐标
        @XmlElement(name = "zhwzfd_tzzbfw")
        var palmRetracingPointFeatureCoordinateRange:Int = _ //掌纹折返点_特征坐标范围
        @XmlElement(name = "zhwzfd_tzfx")
        var palmRetracingPointFeatureDirection:Int = _ //掌纹折返点_特征方向
        @XmlElement(name = "zhwzfd_tzfxfw")
        var palmRetracingPointFeatureDirectionRange:Int = _ //掌纹折返点_特征方向范围
        @XmlElement(name = "zhwzfd_tzzl")
        var palmRetracingPointFeatureQuality:Int = _ //掌纹折返点_特征方向范围
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "DeltaSet")
    class DeltaSet{
        @XmlElement(name = "delta")
        var delta:Array[Delta] = _
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Delta")
    class Delta{
        @XmlElement(name = "zhwsjd_tzxzb")
        var palmTrianglePointFeatureXCoordinate:Int = _
        @XmlElement(name = "zhwsjd_tzyzb")
        var palmTrianglePointFeatureYCoordinate:Int = _
        @XmlElement(name = "zhwsjd_tzzbfw")
        var palmTrianglePointFeatureCoodinateRange:Int = _
        @XmlElement(name = "deltaDirection")
        var deltaDirection:Array[DeltaDirection] =_
        @XmlElement(name = "zhwsjd_zhwsjwzlxdm")
        var deltaPostionClassCode:Int = _
        @XmlElement(name = "zhwsjd_tzzl")
        var deltaFeatureQuality:Int = _
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "DeltaDirection")
    class DeltaDirection{
        @XmlElement(name = "zhwsjd_tzfx")
        var palmTrianglePointFeatureDirection:Int = _ //掌纹三角点_特征方向
        @XmlElement(name = "zhwsjd_tzfxfw")
        var palmTrianglePointFeatureDirectionRange:Int = _ //掌纹三角点_特征方向范围
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "PalmMinutia")
    class PalmMinutia{
        @XmlElement(name = "zhwtzd_tzxzb")
        var fingerFeaturePointXCoordinate:Int = _
        @XmlElement(name = "zhwtzd_tzyzb")
        var fingerFeaturePointYCoordinate:Int = _
        @XmlElement(name = "zhwtzd_tzfx")
        var fingerFeaturePointDirection:Int = _
        @XmlElement(name = "zhwtzd_tzzl")
        var fingerFeaturePointQuality:Int = _
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "PalmMinutiaSet")
    class PalmMinutiaSet{
        @XmlElement(name = "minutia")
        var palmMinutia:Array[PalmMinutia] = _
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Palms")
    class Palms{
        @XmlElement(name = "palmMsg")
        var palmMsg:Array[PalmMsg] = _
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "PalmMsg")
    class PalmMsg{
        @XmlElement(name = "zhwzhwdm")
        var palmPostionCode:String = _
        @XmlElement(name = "zhw_zzhwqsqkdm")
        var lackPalmCauseCode:String = _ //缺掌原因代码
        @XmlElement(name = "zhw_zzhwtztqfsdm")
        var palmFeatureExtractionMethodCode:String = _
        @XmlElement(name = "coreLikePatternSet")
        var coreLikePatternSet:CoreLikePatternSet = _
        @XmlElement(name = "deltaSet")
        var deltaSet:DeltaSet = _
        @XmlElement(name = "deltaDirection")
        var deltaDirection:DeltaDirection = _
        @XmlElement(name = "minutiaSet")
        var palmMinutiaSet:PalmMinutiaSet = _
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
        @XmlElement(name = "zhw_txzl")
        var palmImageQuality:Int = _
        @XmlElement(name = "zhw_txsj")
        var palmImageData:Array[Byte] = _

    }
    //--------------------掌纹相关end-----------------------//

    //--------------------四联指相关begin-----------------------//
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Fourprints")
    class Fourprints{
        @XmlElement(name = "fourprintMsg")
        var fourprintMsg:Array[FourprintMsg] = _
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "FourprintMsg")
    class FourprintMsg{
        @XmlElement(name = "slz_zwzwdm")
        var fourPrintPostionCode:String = _
        @XmlElement(name = "slz_zzhwqsqkdm")
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
        @XmlElement(name = "slz_txzl")
        var fourPrintImageQuality:Int = _
        @XmlElement(name = "slz_txsj")
        var fourPrintImageData:Array[Byte] = _
    }
    //--------------------四联指相关end-----------------------//

    //--------------------指节纹相关begin-----------------------//
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Knuckleprints")
    class Knuckleprints{
        @XmlElement(name = "knuckleprintMsg")
        var knuckleprintMsg:Array[KnuckleprintMsg] = _
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "KnuckleprintMsg")
    class KnuckleprintMsg{
        @XmlElement(name = "zjw_zwzwdm")
        var knucklePrintPostionCode:String = _
        @XmlElement(name = "zjw_zzhwqsqkdm")
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
        @XmlElement(name = "zjw_txzl")
        var knucklePrintImageQuality:Int = _
        @XmlElement(name = "zjw_txsj")
        var knucklePrintImageData:Array[Byte] = _
    }
    //--------------------指节纹相关end-----------------------//

    //--------------------全掌相关begin-----------------------//
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Fullpalms")
    class Fullpalms{
        @XmlElement(name = "fullpalmMsg")
        var fullpalmMsg:Array[FullpalmMsg] =_
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "FullpalmMsg")
    class FullpalmMsg{
        @XmlElement(name = "qz_zhwzhwdm")
        var fullPalmPostionCode:String = _
        @XmlElement(name = "qz_zzhwqsqkdm")
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
        @XmlElement(name = "qz_txzl")
        var fullPalmImageQuality:Int = _
        @XmlElement(name = "qz_txsj")
        var fullPalmImageData:Array[Byte] = _
    }
    //--------------------全掌相关end-----------------------//

    //--------------------人像相关end-----------------------//
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "FaceImages")
    class FaceImages{
        @XmlElement(name = "faceImage")
        var faceImage:Array[FaceImage] = _
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "FaceImage")
    class FaceImage{
        @XmlElement(name = "rxzplxdm")
        var personPictureTypeCode:String = _ //人像照片类型代码
        @XmlElement(name = "rx_dzwjgs")
        var personPictureFileLayout:String = _ //人像_电子文件格式
        @XmlElement(name = "rx_txsj")
        var personPictureImageData:Array[Byte] = _ //人像_图像数据
    }
    //--------------------人像相关end-----------------------//
    //-------------------------捺印部分--END------------------------------------//



    //-------------------------现场部分--BEGIN------------------------------------//
    /**
      * 现场
      */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LatentPackage")
    class LatentPackage{
        @XmlElement(name = "caseMsg")
        var caseMsg:CaseMsg = _
        @XmlElement(name = "collectInfoMsg")
        var latentCollectInfoMsg:LatentCollectInfoMsg = _
        @XmlElement(name = "fingers")
        var latentFingers:Array[LatentFingers] = _
        @XmlElement(name = "palms")
        var latentPalms:Array[LatentPalms] = _
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "CaseClassSet")
    class CaseClassSet{
        @XmlElement(name = "ajlbdm")
        var caseTypeCode:Array[String] = _ //案件类别代码
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "collectInfoMsg")
    class LatentCollectInfoMsg{
        @XmlElement(name = "zwbdxtlxms")
        var fingerprintComparisonSysTypeDescript:String = _ //指纹比对系统类型描述
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
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "CaseMsg")
    class CaseMsg{
        @XmlElement(name = "ysxt_asjbh")
        var originalSystemCaseId:String = _
        @XmlElement(name = "asjbh")
        var caseId:String = _
        @XmlElement(name = "xckybh")
        var latentSurveyId:String = _
        @XmlElement(name = "xczzhwkbh")
        var latentCardId:String = _
        @XmlElement(name = "caseClassSet")
        var caseClassSet:CaseClassSet = _
        @XmlElement(name = "ssjzrmby")
        var money:String = _ //损失价值人民币
        @XmlElement(name = "asjfsdd_xzqhdm")
        var caseOccurAdministrativeDivisionCode:String = _
        @XmlElement(name = "asjfsdd_dzmc")
        var caseOccurAddress:String = _
        @XmlElement(name = "jyaq")
        var briefCase:String = _
        @XmlElement(name = "sfma_pdbz")
        var whetherKill:String = _
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LatentFingers")
    class LatentFingers{
        @XmlElement(name = "fingerImageMsg")
        var latentFingerImageMsg:LatentFingerImageMsg = _
        @XmlElement(name = "fingerFeatureMsg")
        var latentFingerFeatureMsg:Array[LatentFingerFeatureMsg] = _
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LatentFingerMsg")
    class LatentFingerImageMsg{
      /**
        * 新数据必须提供，规则是‘F+现勘号后22位+4位物证分类代码+3位顺序号'
        * ,历史数据如无现勘编号，前23位用'F0000000000000000000000'占位
        */
        @XmlElement(name = "ysxt_xczzhwbh")
        var originalSystemLatentFingerPalmId:String = _
        @XmlElement(name = "xcwzbh")
        var latentPhysicalId:String = _
        @XmlElement(name = "xczw_xczzhwylbw")
        var latentFingerLeftPosition:String = _ //现场指纹遗留部位
        @XmlElement(name = "xczw_stzzhw_pdbz")
        var latentFingerCorpseJudgeIdentify:String = _
        @XmlElement(name = "xczw_rtxysdm")
        var latentFingerMastoidProcessLineColorCode:String = _ //乳突线颜色代码
        @XmlElement(name = "xczw_lzks_xcwzbh")
        var latentFingerConnectFingerBeginPhysicalId:String = _
        @XmlElement(name = "xczw_lzjs_xcwzbh")
        var latentFingerConnectFingerEndPhysicalId:String = _
        @XmlElement(name = "xczw_zzhwbdztdm")
        var latentFingerComparisonStatusCode:String = _
        @XmlElement(name = "xczw_zhiwfx_jyqk")
        var latentFingerAnalysisPostionBrief:String = _
        @XmlElement(name = "xczw_wxfx_jyqk")
        var latentFingerPatternAnalysisBrief:String = _
        @XmlElement(name = "xczw_zwfx_tzfx")
        var latentFingerFeatureDirection:Int = _
        @XmlElement(name = "xczw_zwfx_tzfxfw")
        var latentFingerFeatureDirectionRange:Int = _
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
        @XmlElement(name = "xczw_zdyxx")
        var latentFingerCustomInfo:Array[Byte] = _
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "MinutiaSet")
    class LatentMinutiaSet{
        @XmlElement(name = "minutia")
        var latentMinutia:Array[LatentMinutia] = _
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LatentMinutia")
    class LatentMinutia{
        @XmlElement(name = "xczw_zwtzd_tzxzb")
        var fingerFeaturePointXCoordinate:Int = _
        @XmlElement(name = "xczw_zwtzd_tzyzb")
        var fingerFeaturePointYCoordinate:Int = _
        @XmlElement(name = "xczw_zwtzd_tzfx")
        var fingerFeaturePointDirection:Int = _
        @XmlElement(name = "xczw_zwtzd_tzzl")
        var fingerFeaturePointQuality:Int = _
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "FingerFeatureMsg")
    class LatentFingerFeatureMsg{
        /**
          * 新数据必须提供，规则是‘F+现勘号后22位+4位物证分类代码+3位顺序号'
          * ,历史数据如无现勘编号，前23位用'F0000000000000000000000'占位
          */
        @XmlElement(name = "ysxt_xczzhwbh")
        var originalSystemLatentFingerPalmId:String = _
        @XmlElement(name = "xcwzbh")
        var latentPhysicalId:String = _
        /**
          * 刑侦部门机构代码（12位）+3位流水号
          */
        @XmlElement(name = "xczw_tzzhbsf")
        var latentFeatureGroupIdentifier:String = _
        @XmlElement(name = "xczw_tzzhmsxx")
        var latentFeatureGroupDscriptInfo:String = _
        @XmlElement(name = "xczw_zzhwtztqfsdm")
        var latentFeatureExtractMethodCode:String = _
        @XmlElement(name = "xczw_zhiwfx_jyqk")
        var fingerAnalysisPostionBrief:String = _
        @XmlElement(name = "xczw_wxfx_jyqk")
        var fingerPatternAnalysisBrief:String = _
        @XmlElement(name = "xczw_zwfx_tzfx")
        var fingerFeatureDirection:Int = _ //指纹方向-特征方向
        @XmlElement(name = "xczw_zwfx_tzfxfw")
        var fingerFeatureDirectionRange:Int = _ //指纹方向-特征方向-范围
        @XmlElement(name = "xczw_zwzxd_tzxzb")
        var fingerCenterPointFeatureXCoordinate:Int = _ //指纹中心点特征X坐标
        @XmlElement(name = "xczw_zwzxd_tzyzb")
        var fingerCenterPointFeatureYCoordinate:Int = _ //指纹中心点特征Y坐标
        @XmlElement(name = "xczw_zwzxd_tzzbfw")
        var fingerCenterPointFeatureCoordinateRange:Int = _ //指纹中心点特征坐标范围
        @XmlElement(name = "xczw_zwzxd_tzfx")
        var fingerCenterPointFeatureDirection:Int = _ //指纹中心点特征特征方向
        @XmlElement(name = "xczw_zwzxd_tzfxfw")
        var fingerCenterPointFeatureDirectionRange:Int = _ //指纹中心点特征特征方向范围
        @XmlElement(name = "xczw_zwzxd_tzkkd")
        var fingerCenterPointFeatureReliabilityLevel:Int = _ //指纹中心点可靠度
        @XmlElement(name = "xczw_zwfzx_tzxzb")
        var fingerSlaveCenterFeatureXCoordinate:Int = _ //指纹副中心特征X坐标
        @XmlElement(name = "xczw_zwfzx_tzyzb")
        var fingerSlaveCenterFeatureYCoordinate:Int = _ //指纹副中心特征Y坐标
        @XmlElement(name = "xczw_zwfzx_tzzbfw")
        var fingerSlaveCenterFeatureCoordinateRange:Int = _ //指纹副中心特征坐标范围
        @XmlElement(name = "xczw_zwfzx_tzfx")
        var fingerSlaveCenterFeatureDirection:Int = _ //指纹副中心特征方向
        @XmlElement(name = "xczw_zwfzx_tzfxfw")
        var fingerSlaveCenterFeatureDirectionRange:Int = _ //指纹副中心特征方向范围
        @XmlElement(name = "xczw_zwfzx_tzkkd")
        var fingerSlaveCenterFeatureReliabilityLevel:Int = _ //指纹副中心特征可靠度
        @XmlElement(name = "xczw_zwzsj_tzxzb")
        var fingerLeftTriangleFeatureXCoordinate:Int = _ //指纹左三角特征X坐标
        @XmlElement(name = "xczw_zwzsj_tzyzb")
        var fingerLeftTriangleFeatureYCoordinate:Int = _ //指纹左三角特征Y坐标
        @XmlElement(name = "xczw_zwzsj_tzzbfw")
        var fingerLeftTriangleFeatureCoordinateRange:Int = _ //指纹左三角特征坐标范围
        @XmlElement(name = "xczw_zwzsj_tzfx")
        var fingerLeftTriangleFeatureDirection:Int = _ //指纹左三角特征方向
        @XmlElement(name = "xczw_zwzsj_tzfxfw")
        var fingerLeftTriangleFeatureDirectionRange:Int = _ //指纹左三角特征方向范围
        @XmlElement(name = "xczw_zwzsj_tzkkd")
        var fingerLeftTriangleFeatureReliabilityLevel:Int = _ //指纹左三角特征方向范围
        @XmlElement(name = "xczw_zwysj_tzxzb")
        var fingerRightTriangleFeatureXCoordinate:Int = _ //指纹右三角特征X坐标
        @XmlElement(name = "xczw_zwysj_tzyzb")
        var fingerRightTriangleFeatureYCoordinate:Int = _ //指纹右三角特征Y坐标
        @XmlElement(name = "xczw_zwysj_tzzbfw")
        var fingerRightTriangleFeatureCoordinateRange:Int = _ //指纹右三角特征坐标范围
        @XmlElement(name = "xczw_zwysj_tzfx")
        var fingerRightTriangleFeatureDirection:Int = _ //指纹右三角特征方向
        @XmlElement(name = "xczw_zwysj_tzfxfw")
        var fingerRightTriangleFeatureDirectionRange:Int = _ //指纹右三角特征方向范围
        @XmlElement(name = "xczw_zwysj_tzkkd")
        var fingerRightTriangleFeatureReliabilityLevel:Int = _ //指纹右三角特征方向范围
        @XmlElement(name = "minutiaSet")
        var LatentMinutiaSet = new LatentMinutiaSet
        @XmlElement(name = "xczw_zdyxx")
        var latentFingerCustomInfo:Array[Byte] = _
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LatentPalms")
    class LatentPalms{
        @XmlElement(name = "palmImageMsg")
        var latentPalmImageMsg:LatentPalmImageMsg = _
        @XmlElement(name = "palmFeatureMsg")
        var latentPalmFeatureMsg:Array[LatentPalmFeatureMsg] = _
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LatentPalmCoreLikePatternSet")
    class LatentPalmCoreLikePatternSet{
        @XmlElement(name = "coreLikePattern")
        var latentPalmCoreLikePattern:Array[LatentPalmCoreLikePattern] =_
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LatentPalmCoreLikePattern")
    class LatentPalmCoreLikePattern{
        @XmlElement(name = "xczhw_zhwzfd_tzxzb")
        var latentPalmRetracingPointFeatureXCoordinate:Int = _
        @XmlElement(name = "xczhw_zhwzfd_tzyzb")
        var latentPalmRetracingPointFeatureYCoordinate:Int = _
        @XmlElement(name = "xczhw_zhwzfd_tzzbfw")
        var latentPalmRetracingPointFeatureCoordinateRange:Int = _
        @XmlElement(name = "xczhw_zhwzfd_tzfx")
        var latentPalmRetracingPointFeatureDirection:Int = _
        @XmlElement(name = "xczhw_zhwzfd_tzfxfw")
        var latentPalmRetracingPointFeatureDirectionRange:Int = _
        @XmlElement(name = "xczhw_zhwzfd_tzzl")
        var latentPalmRetracingPointFeatureQuality:Int = _
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LatentPalmDeltaSet")
    class LatentPalmDeltaSet{
        @XmlElement(name  = "delta")
        var  latentPalmDelta:Array[LatentPalmDelta] = _
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LatentPalmDelta")
    class LatentPalmDelta{

        @XmlElement(name = "xczhw_zhwsjd_tzxzb")
        var latentPalmTrianglePointFeatureXCoordinate:Int = _
        @XmlElement(name = "xczhw_zhwsjd_tzyzb")
        var latentPalmTrianglePointFeatureYCoordinate:Int = _
        @XmlElement(name = "xczhw_zhwsjd_tzzbfw")
        var latentPalmTrianglePointFeatureRange:Int = _
        @XmlElement(name = "deltaDirection")
        var latentPalmDeltaDirection:Array[LatentPalmDeltaDirection] = _
        @XmlElement(name = "xczhw_zhwsjd_zhwsjwzlxdm")
        var palmTrianglePostionTypeCode:Int = _
        @XmlElement(name = "xczhw_zhwsjd_tzzl")
        var latentPalmTrianglePointFeatureQuality:Int = _
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LatentPalmDeltaDirection")
    class LatentPalmDeltaDirection{
         @XmlElement(name  = "xczhw_zhwsjd_tzfx")
         var latentPalmTrianglePointFeatureDirection:Int = _
         @XmlElement(name = "xczhw_zhwsjd_tzfxfw")
         var latentPalmTrianglePointFeatureDirectionRange:Int = _
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LatentPalmMinutiaSet")
    class LatentPalmMinutiaSet{
        @XmlElement(name = "minutia")
        var latentPalmMinutia:Array[LatentPalmMinutia] = _
    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LatentPalmMinutia")
    class LatentPalmMinutia{
        @XmlElement(name = "xczhw_zhwtzd_tzxzb")
        var fingerFeaturePointXCoordinate:Int = _
        @XmlElement(name = "xczhw_zhwtzd_tzyzb")
        var fingerFeaturePointYCoordinate:Int = _
        @XmlElement(name = "xczhw_zhwtzd_tzfx")
        var fingerFeaturePointDirection:Int = _
        @XmlElement(name = "xczhw_zhwtzd_tzzl")
        var fingerFeaturePointQuality:Int = _
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LatentPalmImageMsg")
    class LatentPalmImageMsg {

        @XmlElement(name = "xczhw_xczzhwbh")
        var latentPalmId: String = _
        @XmlElement(name = "xczhw_xcwzbh")
        var latentPalmPhysicalId: String = _
        @XmlElement(name = "xczhw_xczzhwylbw")
        var latentPalmLeftPostion: String = _
        @XmlElement(name = "xczhw_stzzhw_pdbz")
        var latentPalmCorpseJudgeIdentify: String = _
        @XmlElement(name = "xczhw_rtxysdm")
        var latentPalmMastoidProcessLineColorCode: String = _
        @XmlElement(name = "xczhw_zzhwbdztdm")
        var latentPalmComparisonStatusCode: String = _
        @XmlElement(name = "xczhw_zhwfx_jyqk")
        var latentPalmPostionAnalysisBriefly:String = _
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
        @XmlElement(name = "xczhw_zdyxx")
        var latentPalmCustomInfo:Array[Byte] = _

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LatentPalmFeatureMsg")
    class LatentPalmFeatureMsg{
        @XmlElement(name = "xczhw_xczzhwbh")
        var latentPalmNo:String = _
        @XmlElement(name = "xczhw_xcwzbh")
        var latentPalmPhysicalId:String = _
        @XmlElement(name = "xczhw_tzzhbsf")
        var latentPalmFeatureGroupIdentifier:String = _
        @XmlElement(name = "xczhw_tzzhmsxx")
        var latentPalmFeatureDscriptInfo:String = _
        @XmlElement(name = "xczhw_zzhwtztqfsdm")
        var latentPalmFeatureExtractMethodCode:String = _
        @XmlElement(name = "xczhw_zzhwbdztdm")
        var latentPalmComparisonStatusCode:String = _
        @XmlElement(name = "xczhw_zhwfx_jyqk")
        var latentPalmAnalysisBrief:String = _
        @XmlElement(name = "coreLikePatternSet")
        var latentPalmCoreLikePatternSet:LatentPalmCoreLikePatternSet = _
        @XmlElement(name = "deltaSet")
        var latentPalmDeltaSet:LatentPalmDeltaSet = _
        @XmlElement(name = "minutiaSet")
        var latentPalmMinutiaSet:LatentPalmMinutiaSet = _
        @XmlElement(name = "xczhw_zdyxx")
        var latentPalmCustomInfo:Array[Byte] = _
    }
    //-------------------------现场部分--END------------------------------------//




    /**
      * 现场指掌纹查询比对请求信息
      */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LatenttaskPackage")
    class LatenttaskPackage{
        @XmlElement(name = "rwbh")
        var taskId:String = _
        @XmlElement(name = "zzhwbdrwlxdm")
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
        @XmlElement(name = "xczzhwkbh")
        var latentCardId:String = _
        @XmlElement(name = "tjsj")
        var submitDateTime:String = _
        @XmlElement(name = "latentPackage")
        var latentPackage:Array[LatentPackage] = _
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
        var fingerprintPackage:Array[FingerprintPackage] = _
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
        var latentPackage:Array[LatentPackage]= _
        @XmlElement(name = "resultMsg")
        var resultMsg:Array[LTResultMsg] = _
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
        var fingerprintPackage:Array[FingerprintPackage] = _
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
        var fingerprintPackage:Array[FingerprintPackage] = _
        @XmlElement(name = "resultMsg")
        var resultMsg:Array[TLResultMsg] = _
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
        var latentPackage:Array[LatentPackage] = _
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
        var fingerprintPackage:Array[FingerprintPackage] = _
        @XmlElement(name = "resultMsg")
        var resultMsg:Array[TTResultMsg] = _
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
        var fingerprintPackage:Array[FingerprintPackage] = _
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
        var latentPackage:Array[LatentPackage] = _
        @XmlElement(name = "resultMsg")
        var resultMsg:Array[LLResultMsg] = _
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
        var latentPackage:Array[LatentPackage] = _
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
        var latentPackage:Array[LatentPackage] = _
        @XmlElement(name = "fingerprintPackage")
        var fingerprintPackage:Array[FingerprintPackage] = _
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
        var fingerprintPackageSource:Array[FingerprintPackage] = _
        @XmlElement(name = "fingerprintPackage")
        var fingerprintPackageDesc:Array[FingerprintPackage] = _
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
        var latentPackageSource:Array[LatentPackage] = _
        @XmlElement(name = "latentPackage")
        var latentPackageDesc:Array[LatentPackage] = _
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







