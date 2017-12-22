package nirvana.hall.spark.services

import java.io.InputStream
import java.nio.charset.Charset

import nirvana.hall.c.AncientConstants
import nirvana.hall.c.annotations.{IgnoreTransfer, Length, LengthRef}
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.AncientData.{AncientDataException, StreamReader}

import scala.util.control.NonFatal

/**
  * Created by wangjue on 2017/12/22.
  */
object FIDFile {
  val FS:Byte= 28
  val GS:Byte= 29

  trait DynamicFingerData extends AncientData {
    @IgnoreTransfer
    private var logicEnd: Byte = FS

    // GS
    protected def getFingerDataCountString: String

    protected def getFingerDataCount: Int = {
      val sendFingerCount = getFingerDataCountString
      if (sendFingerCount != null && sendFingerCount.nonEmpty)
        sendFingerCount.toInt
      else
        0 //如果数据为空的时候,则返回0
      //throw new AncientDataException("sendFingerCount field is "+sendFingerCount)
    }

    override def getDataSize: Int = {
      var count = super.getDataSize
      if (getFingerDataCount == 0)
        count += 1
      count
    }

    override def fromStreamReader(dataSource: StreamReader, encoding: Charset = AncientConstants.UTF8_ENCODING): this.type = {
      super.fromStreamReader(dataSource, encoding)
      if (getFingerDataCount == 0) {
        logicEnd = dataSource.readByte()
      }
      this
    }
  }

  def parseFromInputStream(stream:InputStream, encoding: Charset = AncientConstants.GBK_ENCODING) : FIDFile = {
    val streamReader:StreamReader = stream
    try {
      new FIDFile().fromStreamReader(streamReader,encoding)
    }catch{
      case e:AncientDataException=>
        throw e
      case NonFatal(e)=>
        throw new FIDParseException(e.toString,e)
    }
  }

  class FIDParseException(message:String,cause:Throwable) extends RuntimeException(message,cause){
    def this(message:String){
      this(message,null)
    }
  }

  class FIDFile extends DynamicFingerData {
    @Length(3)
    var fileFlag : String = _               //文件标识符
    @Length(4)
    var fileVersion : String = _            //版本编号
    @Length(12)
    var fileLength : String = _             //文件长度
    @Length(4)
    var systemType : String = _             //系统类型
    @Length(8)
    var gaSerial : String = _               //采集仪GA认证标志序列号
    @Length(12)
    var captureCode : String = _            //采集点编号
    @Length(14)
    var captureDate : String = _            //采集时间
    @Length(23)
    var personNo : String = _               //人员编号
    @Length(23)
    var cardNo : String = _                 //卡号
    @Length(30)
    var name : String = _                   //姓名
    @Length(30)
    var alias : String = _                  //别名
    @Length(1)
    var gender : String = _                 //性别
    @Length(8)
    var birthday : String = _               //生日
    @Length(3)
    var nation : String = _                 //国籍
    @Length(2)
    var race : String = _                   //民族
    @Length(18)
    var idCard : String = _                 //身份证号码
    @Length(3)
    var certificateType : String = _        //证件类型
    @Length(20)
    var certificateNo : String = _          //证件号码
    @Length(6)
    var doorCode : String = _               //户籍地代码
    @Length(70)
    var door : String = _                   //户籍地祥址
    @Length(6)
    var addressCode : String = _            //现住址代码
    @Length(70)
    var address : String = _                //现住址祥址
    @Length(2)
    var personType : String = _             //人员类别
    @Length(6)
    var caseClass1 : String = _             //案件类别1
    @Length(6)
    var caseClass2 : String = _             //案件类别2
    @Length(6)
    var caseClass3 : String = _             //案件类别3
    @Length(1)
    var recordMark : String = _             //前科标识
    @Length(1024)
    var record : String = _                 //前科情况
    @Length(12)
    var printUnitCode : String = _          //捺印单位代码
    @Length(70)
    var printUnitName : String = _          //捺印单位名称
    @Length(30)
    var printName : String = _              //捺印人姓名
    @Length(8)
    var printDate : String = _              //捺印日期
    @Length(1)
    var assistLevel : String = _            //协查级别
    @Length(6)
    var assistBonus : String = _            //协查奖金
    @Length(5)
    var assistPurpose : String = _          //协查目的
    @Length(23)
    var relPersonNo : String = _            //相关人员编号
    @Length(23)
    var relCaseNo : String = _              //相关案件编号
    @Length(1)
    var assistValidDate : String = _        //协查有效时限
    @Length(512)
    var assistExplain : String = _          //协查请求说明
    @Length(12)
    var assistUnitCode : String = _         //协查单位代码
    @Length(70)
    var assistUnitName : String = _         //协查单位名称
    @Length(8)
    var assistDate : String = _             //协查日期
    @Length(30)
    var assistContacts : String = _         //联系人
    @Length(40)
    var assistNumber : String = _           //联系方式
    @Length(30)
    var assistApproval : String = _         //审批人
    @Length(512)
    var remark : String = _                 //备注
    @Length(1)
    var assistSign : String = _             //协查标记
    @Length(2)
    var fingerCount : String = _            //发送指纹个数
    @Length(2)
    var palmCount : String = _             //发送掌纹个数
    @Length(2)
    var portraitCount : String = _          //发送人像个数

    @LengthRef("fingerCount")
    var fingers : Array[FingerData] = _

    @LengthRef("palmCount")
    var palms : Array[PalmData] = _

    override protected def getFingerDataCountString: String = palmCount
    /*

    private def tryReadLogic(dataSource:StreamReader,encoding:Charset): Unit ={
      if (palms == null) {
        palms = Array[PalmData](new PalmData)
      } else {
        val tmp = palms
        palms = new Array[PalmData](palms.length+1)
        System.arraycopy(tmp,0,palms,0,tmp.length)
        palms(tmp.length) = new PalmData
      }
      palmCount = palms.length.toString
      palms.last.fromStreamReader(dataSource)
    }

    override def fromStreamReader(dataSource: StreamReader, encoding: Charset): FIDFile.this.type = {
      super.fromStreamReader(dataSource, encoding)
      if(palmCount == null || palmCount.isEmpty){
        try {
          0 until 100 foreach{i=>
            tryReadLogic(dataSource,encoding)
          }
        }catch{
          case NonFatal(e)=>
          //do nothing
        }
      }
      this
    }*/

    @LengthRef("portraitCount")
    var portraits : Array[PortraitData] = _
  }

  class FingerData extends AncientData{
    @Length(7)
    var dataLength : String = _             //指纹信息长度
    @Length(2)
    var sendNo : String = _                 //发送序号
    @Length(1)
    var fingerType : String = _             //指纹类别
    @Length(2)
    var fgp : String = _                    //指位
    @Length(1)
    var extractMethod : String = _          //特征提取方式
    @Length(1)
    var pattern1 : String = _               //主纹型分类
    @Length(1)
    var pattern2 : String = _               //副纹型分类
    @Length(5)
    var fingerDirection: String = _         //指纹方向
    @Length(14)
    var centerPoint: String = _             //中心点
    @Length(14)
    var subCenterPoint: String = _          //副中心
    @Length(14)
    var leftTriangle: String = _            //左三角
    @Length(14)
    var rightTriangle: String = _           //右三角
    @Length(3)
    var featureCount: String = _            //特征点个数
    @Length(1800)
    var feature: String = _                 //特征点
    @Length(6)
    var customInfoLength: String = _        //自定义信息长度
    @LengthRef("customInfoLength")
    var customInfo: Array[Byte] = _         //自定义信息
    @Length(3)
    var imgHorizontalLength: String = _     //图像水平方向长度
    @Length(3)
    var imgVerticalLength: String = _       //图像垂直方向长度
    @Length(3)
    var dpi: String = _                     //图像分辨率
    @Length(4)
    var imgCompressMethod: String = _       //图像压缩方法代码
    @Length(6)
    var imgDataLength: String = _           //图像长度
    @LengthRef("imgDataLength")
    var imgData: Array[Byte]= _             //图像数据
    var end: Byte = GS
  }

  class PalmData extends AncientData{
    @Length(10)
    var dataLength : String = _             //掌纹信息长度
    @Length(2)
    var sendNo : String = _                 //发送序号
    @Length(2)
    var position : String = _               //位置编号
    @Length(8)
    var customInfoLength: String = _        //自定义信息长度
    @LengthRef("customInfoLength")
    var customInfo: Array[Byte] = _         //自定义信息
    @Length(4)
    var imgHorizontalLength: String = _     //图像水平方向长度
    @Length(4)
    var imgVerticalLength: String = _       //图像垂直方向长度
    @Length(3)
    var dpi: String = _                     //图像分辨率
    @Length(4)
    var imgCompressMethod: String = _       //图像压缩方法代码
    @Length(8)
    var imgDataLength: String = _           //图像长度
    @LengthRef("imgDataLength")
    var imgData: Array[Byte]= _             //图像数据
    var end: Byte = GS
  }

  class PortraitData extends AncientData{
    @Length(7)
    var dataLength : String = _             //人像数据长度
    @Length(2)
    var senNo : String = _                  //发送序号
    @Length(1)
    var position : String = _               //人像位置标志
    @Length(6)
    var imgDataLength: String = _           //图像长度
    @LengthRef("imgDataLength")
    var imgData: Array[Byte]= _             //图像数据
    @Length(8)
    var customInfoLength: String = _        //自定义信息长度
    @LengthRef("customInfoLength")
    var customInfo: Array[Byte] = _         //自定义信息
    var end: Byte = GS
  }

}
