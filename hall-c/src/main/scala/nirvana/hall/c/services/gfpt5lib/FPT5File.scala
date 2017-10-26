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
  @XmlElement(name = "packageHead")
  var packageHead: PackageHead = new PackageHead
  @XmlElement(name = "taskInfo")
  var taskInfo: TaskInfo = new TaskInfo
  @XmlElement(name = "fingerprintPackage")
  var fingerprintPackage:FingerprintPackage = new FingerprintPackage
  @XmlElement(name = "latentPackage")
  var latentPackage:LatentPackage = new LatentPackage
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
    var fsdw_gajgjgdm:String = _
    @XmlElement(name = "fsdw_gajgmc")
    var fsdw_gajgmc:String = _
    @XmlElement(name = "fsdw_xtlx")
    var fsdw_xtlx:String = _
    @XmlElement(name = "fsr_xm")
    var fsr_xm:String = _
    @XmlElement(name = "fsr_gmsfhm")
    var fsr_gmsfhm:String = _
    @XmlElement(name = "fsr_lxdm")
    var fsr_lxdm:String = _
}

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
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FingerprintMsg")
class FingerprintMsg{
    @XmlElement(name = "zwbdxtlxms")
    var zwbdxtlxms:String = _
    @XmlElement(name = "nydw_gajgjgdm")
    var nydw_gajgjgdm:String = _
    @XmlElement(name = "nydw_gajgmc")
    var nydw_gajgmc:String = _
    @XmlElement(name = "nyry_xm")
    var nyry_xm:String = _
    @XmlElement(name = "nyry_gmsfhm")
    var nyry_gmsfhm:String = _
    @XmlElement(name = "nyry_lxdh")
    var nyry_lxdh:String =_
    @XmlElement(name = "nysj")
    var nysj:String = _
    @XmlElement(name = "bz")
    var bz:String = _
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DescriptMsg")
class DescriptMsg{
    @XmlElement(name = "zwbdxtlxms")
    var zwbdxtlxms:String = _
    @XmlElement(name = "ysxt_asjxgrybh")
    var ysxt_asjxgrybh:String = _
    @XmlElement(name = "jzrybh")
    var jzrybh:String = _
    @XmlElement(name = "asjxgrybh")
    var asjxgrybh:String = _
    @XmlElement(name = "zzwkbh")
    var zzwkbh:String = _
    @XmlElement(name = "bnyzwyydm")
    var bnyzwyydm:String = _
    @XmlElement(name = "xm")
    var xm:String = _
    @XmlElement(name = "bmch")
    var bmch:String = _
    @XmlElement(name = "xbdm")
    var xbdm:String = _
    @XmlElement(name = "csrq")
    var csrq:String = _
    @XmlElement(name = "gjdm")
    var gjdm:String = _
    @XmlElement(name = "mzdm")
    var mzdm:String = _
    @XmlElement(name = "cyzjdm")
    var cyzjdm:String = _
    @XmlElement(name = "zjhm")
    var zjhm:String = _
    @XmlElement(name = "hjdz_xzqhdm")
    var hjdz_xzqhdm:String = _
    @XmlElement(name = "hjdz_dzmc")
    var hjdz_dzmc:String = _
    @XmlElement(name = "xzz_xzqhdm")
    var xzz_xzqhdm:String = _
    @XmlElement(name = "xzz_dzmc")
    var xzz_dzmc:String = _
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
    var zwzwdm:String = _
    @XmlElement(name = "zzwtztqfsdm")
    var zzwtztqfsdm:String  = _
    @XmlElement(name = "qzyydm")
    var qzyydm:String = _
    @XmlElement(name = "zwwxzfl_zwwxdm")
    var zwwxzfl_zwwxdm:String = _
    @XmlElement(name = "zwwxzfl_zwwxdm")
    var zwwxffl_zwwxdm:String = _
    @XmlElement(name = "zwfxms")
    var zwfxms:String = _
    @XmlElement(name = "zwzxd")
    var zwzxd:String = _
    @XmlElement(name = "zwfzx")
    var zwfzx:String = _
    @XmlElement(name = "zwzsj")
    var zwzsj:String = _
    @XmlElement(name = "zwysj")
    var zwysj:String = _
    @XmlElement(name = "zwtzd_sl")
    var zwtzd_sl:String = _
    @XmlElement(name = "zwtzdxx")
    var zwtzdxx:String = _
    @XmlElement(name = "zw_zdyxx")
    var zw_zdyxx:String = _
    @XmlElement(name = "zw_txspfxcd")
    var zw_txspfxcd:String = _
    @XmlElement(name = "zw_txczfxcd")
    var zw_txczfxcd:String = _
    @XmlElement(name = "zw_txfbl")
    var zw_txfbl:String = _
    @XmlElement(name = "zw_txysffms")
    var zw_txysffms:String = _
    @XmlElement(name = "zw_txsj")
    var zw_txsj:String = _
}
//--------------------指纹相关end-------------------------//



//--------------------掌纹相关begin-----------------------//
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Palms")
class Palms{
    @XmlElement(name = "palmMsg")
    var palmMsg:PalmMsg = new PalmMsg
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PalmMsg")
class PalmMsg{
    @XmlElement(name = "zhwzwdm")
    var zhwzwdm:String = _
    @XmlElement(name = "qszwyydm")
    var qszwyydm:String = _
    @XmlElement(name = "zzwtztqfsdm")
    var zzwtztqfsdm:String = _
    @XmlElement(name = "zhwzfd_sl")
    var zhwzfd_sl:Int = _
    @XmlElement(name = "zhwzfdxx")
    var zhwzfdxx:String = _
    @XmlElement(name = "zhwsjd_sl")
    var zhwsjd_sl:Int = _
    @XmlElement(name = "zhwsjdxx")
    var zhwsjdxx:String = _
    @XmlElement(name = "zhwtzd_sl")
    var zhwtzd_sl:Int = _
    @XmlElement(name = "zhwtzdxx")
    var zhwtzdxx:String = _
    @XmlElement(name = "zhw_zdyxx")
    var zhw_zdyxx:String = _
    @XmlElement(name = "zhw_txspfxcd")
    var zhw_txspfxcd:Int = _
    @XmlElement(name = "zhw_txczfxcd")
    var zhw_txczfxcd:Int = _
    @XmlElement(name = "zhw_txfbl")
    var zhw_txfbl:Int = _
    @XmlElement(name = "zhw_txysffms")
    var zhw_txysffms:String = _
    @XmlElement(name = "zhw_txsj")
    var zhw_txsj:String = _

}
//--------------------掌纹相关end-----------------------//



//--------------------四联指相关begin-----------------------//
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Fourprints")
class Fourprints{
    @XmlElement(name = "fourprintMsg")
    var fourprintMsg:FourprintMsg = new FourprintMsg
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FourprintMsg")
class FourprintMsg{
    @XmlElement(name = "slz_zwzwdm")
    var slz_zwzwdm:String = _
    @XmlElement(name = "slz_qzyydm")
    var slz_qzyydm:String = _
    @XmlElement(name = "slz_zdyxx")
    var slz_zdyxx:String = _
    @XmlElement(name = "slz_txspfxcd")
    var slz_txspfxcd:String = _
    @XmlElement(name = "slz_txczfxcd")
    var slz_txczfxcd:String = _
    @XmlElement(name = "slz_txfbl")
    var slz_txfbl:String = _
    @XmlElement(name = "slz_txysffms")
    var slz_txysffms:String = _
    @XmlElement(name = "slz_txsj")
    var slz_txsj:String = _
}
//--------------------四联指相关end-----------------------//



//--------------------指节纹相关begin-----------------------//
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Knuckleprints")
class Knuckleprints{
    @XmlElement(name = "knuckleprintMsg")
    var knuckleprintMsg:KnuckleprintMsg = new KnuckleprintMsg
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KnuckleprintMsg")
class KnuckleprintMsg{
    @XmlElement(name = "zjw_zwzwdm")
    var zjw_zwzwdm:String = _
    @XmlElement(name = "zjw_qzyydm")
    var zjw_qzyydm:String = _
    @XmlElement(name = "zjw_zdyxx")
    var zjw_zdyxx:String = _
    @XmlElement(name = "zjw_txspfxcd")
    var zjw_txspfxcd:String = _
    @XmlElement(name = "zjw_txczfxcd")
    var zjw_txczfxcd:String = _
    @XmlElement(name = "zjw_txfbl")
    var zjw_txfbl:String = _
    @XmlElement(name = "zjw_txysffms")
    var zjw_txysffms:String = _
    @XmlElement(name = "zjw_txsj")
    var zjw_txsj:String = _
}
//--------------------指节纹相关end-----------------------//




//--------------------全掌相关begin-----------------------//
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Fullpalms")
class Fullpalms{
    @XmlElement(name = "fullpalmMsg")
    var fullpalmMsg:FullpalmMsg = new FullpalmMsg
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FullpalmMsg")
class FullpalmMsg{
    @XmlElement(name = "qz_zwzwdm")
    var qz_zwzwdm:String = _
    @XmlElement(name = "qz_qzyydm")
    var qz_qzyydm:String = _
    @XmlElement(name = "qz_zdyxx")
    var qz_zdyxx:String = _
    @XmlElement(name = "qz_txspfxcd")
    var qz_txspfxcd:Int = _
    @XmlElement(name = "qz_txczfxcd")
    var qz_txczfxcd:Int = _
    @XmlElement(name = "qz_txfbl")
    var qz_txfbl:Int = _
    @XmlElement(name = "qz_txysffms")
    var qz_txysffms:String = _
    @XmlElement(name = "qz_txsj")
    var qz_txsj:String = _
}
//--------------------全掌相关end-----------------------//


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
    var zwbdxtlxms:String = _
    @XmlElement(name = "ysxt_asjbh")
    var ysxt_asjbh:String = _
    @XmlElement(name = "asjbh")
    var asjbh:String = _
    @XmlElement(name = "xckybh")
    var xckybh:String = _
    @XmlElement(name = "xczzwkbh")
    var xczzwkbh:String = _
    @XmlElement(name = "ajlbdm")
    var ajlbdm:String = _
    @XmlElement(name = "asjsscw_jermby")
    var asjsscw_jermby:BigDecimal = _
    @XmlElement(name = "asjfsdd_xzqhdm")
    var asjfsdd_xzqhdm:String = _
    @XmlElement(name = "asjfsdd_dzmc")
    var asjfsdd_dzmc:String = _
    @XmlElement(name = "jyaq")
    var jyaq:String = _
    @XmlElement(name = "sfma_pdbz")
    var sfma_pdbz:String = _
    @XmlElement(name = "tqsj_gajgjgdm")
    var tqsj_gajgjgdm:String = _
    @XmlElement(name = "tqsj_gajgmc")
    var tqsj_gajgmc:String = _
    @XmlElement(name = "tqry_xm")
    var tqry_xm:String = _
    @XmlElement(name = "tqry_gmsfhm")
    var tqry_gmsfhm:String = _
    @XmlElement(name = "tqry_lxdh")
    var tqry_lxdh:String = _
    @XmlElement(name = "tqsj")
    var tqsj:String = _
    @XmlElement(name = "xczw_sl")
    var xczw_sl:Int = _
    @XmlElement(name = "xczhw_sl")
    var xczhw_sl:Int = _
    @XmlElement(name = "ysxt_xczzwbh")
    var ysxt_xczzwbh:String = _

}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LatentFingers")
class LatentFingers{
    /*现场物证编号	xcwzbh	字符型
    现场指纹_现场指掌纹遗留部位	xczw_xczzwylbw	字符型
    现场指纹_指掌纹特征提取方式代码	xczw_zzwtztqfsdm	字符型
    现场指纹_尸体指掌纹_判断标识	xczw_stzzw_pdbz	字符型
    现场指纹_乳突线颜色代码	xczw_rtxysdm	字符型
    现场指纹_连指开始_现场物证编号	xczw_lzks_xcwzbh	字符型
    现场指纹_连指结束_现场物证编号	xczw_lzjs_xcwzbh	字符型
    现场指纹_指纹比对状态代码	xczw_zwbdztdm	字符型
    现场指纹_特征组合数量	xczw_tzzhsl	数值型
    现场指纹_特征组合标识符	xczw_tzzhbsf	字符型
    现场指纹_特征组合描述	xczw_tzzhms	字符型
    现场指纹_分析指位_简要情况	xczw_fxzw_jyqk	字符型
    现场指纹_指纹纹型代码	xczw_zwwxdm	字符型
    现场指纹_指纹方向描述	xczw_zwfxms	字符型
    现场指纹_指纹中心点	xczw_zwzxd	字符型
    现场指纹_指纹副中心	xczw_zwfzx	字符型
    现场指纹_指纹左三角	xczw_zwzsj	字符型
    现场指纹_指纹右三角	xczw_zwysj	字符型
    现场指纹_指纹特征点_数量	xczw_zwtzd_sl	数值型
    现场指纹_指纹特征点信息	xczw_zwtzdxx	字符型
    现场指纹_自定义信息	xczw_zdyxx	二进制
    现场指纹_图像水平方向长度	xczw_txspfxcd	数值型
    现场指纹_图像垂直方向长度	xczw_txczfxcd	数值型
    现场指纹_图像分辨率	xczw_txfbl	数值型
    现场指纹_图像压缩方法描述	xczw_txysffms	字符型
    现场指纹_图像数据	xczw_txsj	二进制*/

}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LatentPalms")
class LatentPalms{

}

object  FPT5File{
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
}

