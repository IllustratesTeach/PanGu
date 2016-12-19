package nirvana.hall.api.webservice.services.internal

import java.util.Date
import javax.activation.DataHandler

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.{CaseInfoService, LPCardService, TPCardService}
import nirvana.hall.api.webservice.services.WsFingerService
import nirvana.hall.api.webservice.util.FPTFileBuilder
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPT4File.{FPT4File, Logic02Rec}
import org.apache.axiom.attachments.ByteArrayDataSource


/**
  * 互查系统webservice实现类
  */
class WsFingerServiceImpl(tpCardService: TPCardService,lpCardService: LPCardService, caseInfoService: CaseInfoService) extends WsFingerService with LoggerSupport
{

  /**
    * 查询十指指纹文字信息供用户选择进行任务的协查
    *
    * @param userid      用户名
    * @param password    密码
    * @param ryno        人员编号
    * @param xm          姓名
    * @param xb          性别
    * @param idno        身份证号码
    * @param zjlb        证件类别
    * @param zjhm        证件号码
    * @param hjddm       户籍地代码
    * @param xzzdm       现住址代码
    * @param rylb        人员类别
    * @param ajlb        案件类别
    * @param qkbs        前科标识
    * @param xcjb        协查级别
    * @param nydwdm      捺印单位代码
    * @param startnydate 开始时间（检索捺印时间，时间格式YYYYMMDDHHMM）
    * @param endnydate   结束时间（检索捺印时间，时间格式YYYYMMDDHHMM）
    * @return 返回的FPT文件信息需用soap的附件形式存储，只返回相应文字信息，不包含图像信息（FPT文件中145项“人像图像数据长度”应为0,147项“发送指纹个数”应为0）
    *         每次返回的FPT信息数量不多于256条
    *         若没有查询出数据，则返回一个空FPT文件，即只有第一类记录
    */
  override def getTenprintRecord(userid: String, password: String, ryno: String, xm: String, xb: String, idno: String, zjlb: String, zjhm: String, hjddm: String, xzzdm: String, rylb: String, ajlb: String, qkbs: String, xcjb: String, nydwdm: String, startnydate: String, endnydate: String): DataHandler = {
    info("fun:getTenprintRecord,inputParam-userid:{};password:{};ryno:{};xm:{};xb:{};idno:{};zjlb:{};zjhm:{};hjddm:{};xzzdm:{};rylb:{};ajlb:{};qkbs:{};xcjb:{};nydwdm:{};startnydate:{};endnydate:{}",userid,password,ryno,xm,xb,idno,zjlb,zjhm,hjddm,xzzdm,rylb,ajlb,qkbs,xcjb,nydwdm,startnydate,endnydate)
    try{
      //1 根据查询条件查询捺印文字信息数据集合
      val logic02RecList :Seq[Logic02Rec] = tpCardService.getFPT4Logic02RecList(ryno, xm, xb, idno, zjlb, zjhm, hjddm, xzzdm, rylb, ajlb, qkbs, xcjb, nydwdm, startnydate, endnydate)
      if(null != logic02RecList && logic02RecList.size > 0){
        //2 将捺印文字信息数据集合 封装成FPT
        val FPT4File = FPTFileBuilder.buildTenprintRecordFpt(logic02RecList)
        new DataHandler(new ByteArrayDataSource(FPT4File.toByteArray(AncientConstants.GBK_ENCODING)))
      } else {
        FPTFileBuilder.emptyFPT
      }
    }catch{
      case e : Exception => error("fun:getTenprintFinger Exception" + ",inputParam-userid:{};password:{};ryno:{};xm:{};xb:{};idno:{};zjlb:{};zjhm:{};hjddm:{};xzzdm:{};rylb:{};ajlb:{};qkbs:{};xcjb:{};nydwdm:{};startnydate:{};endnydate:{},errormessage:{}"
        ,userid,password,ryno,xm,xb,idno,zjlb,zjhm,hjddm,xzzdm,rylb,ajlb,qkbs,xcjb,nydwdm,startnydate,endnydate,e.getMessage)
        e.printStackTrace()
        FPTFileBuilder.emptyFPT
    }
  }

  /**
    * 通过查询参数返回指定返回相应人员的全部信息（包含文字信息和图像信息）
    *
    * @param userid   用户名
    * @param password 密码
    * @param ryno     人员编号
    * @return 返回的FPT文件信息需用soap的附件形式存储
    *         若没有查询出数据，则返回一个空FPT文件，即只有第一类记录
    */
  override def getTenprintFinger(userid: String, password: String, ryno: String): DataHandler = {
    info("fun:getTenprintFinger,inputParam-userid:{};password:{};ryno:{};time:{}",userid,password,ryno,new Date)
    try{
      if(tpCardService.isExist(ryno)){
        val tpCard = tpCardService.getTPCard(ryno)
        val fptObj = FPTFileBuilder.convertProtoBuf2TPFPT4File(tpCard)
        new DataHandler(new ByteArrayDataSource(fptObj.toByteArray(AncientConstants.GBK_ENCODING)))
      }else{
        FPTFileBuilder.emptyFPT
      }

    }catch{
      case e : Exception => error("fun:getTenprintFinger Exception" +
        ",inputParam-userid:{};password:{};ryno:{},errormessage:{},outtime:{}"
        ,userid,password,ryno,e.getMessage,new Date)
        FPTFileBuilder.emptyFPT
    }
  }

  /**
    * 查询现场指纹文字信息供用户选择进行任务的协查
    *
    * @param userid      用户名
    * @param password    密码
    * @param ajno        案件编号
    * @param ajlb        案件类别
    * @param fadddm      发案地代码
    * @param mabs        命案标识
    * @param xcjb        协查级别
    * @param xcdwdm      查询单位代码
    * @param startfadate 开始时间（检索发案时间，时间格式YYYYMMDD）
    * @param endfadate   结束时间（检索发案时间，时间格式YYYYMMDD）
    * @return 返回的FPT文件信息需用soap的附件形式存储，只返回相应文字信息，不包含图像信息（FPT文件中132项“发送现场指纹个数”应为0）
    *         若没有查询出数据，则返回一个空FPT文件，即只有第一类记录
    */
  override def getLatent(userid: String, password: String, ajno: String, ajlb: String, fadddm: String, mabs: String, xcjb: String, xcdwdm: String, startfadate: String, endfadate: String): DataHandler = {
    val fpt = new FPT4File
    val logic03RecList = caseInfoService.getFPT4Logic03RecList(ajno, ajlb, fadddm, mabs, xcjb, xcdwdm, startfadate, endfadate)
    if(logic03RecList.nonEmpty){
      fpt.lpCount = logic03RecList.size.toString
      fpt.logic03Recs = logic03RecList.toArray
    }
    new DataHandler(new ByteArrayDataSource(fpt.toByteArray()))
  }


  /**
    * 通过查询参数返回指定返回相应案件的全部信息（包含文字信息和图像信息）
    *
    * @param userid   用户名
    * @param password 密码
    * @param ajno     案件编号
    * @author ssj
    * @return 返回的FPT文件信息需用soap的附件形式存储，只返回相应文字信息，不包含图像信息（FPT文件中132项“发送现场指纹个数”应为0）
    *         若没有查询出数据，则返回一个空FPT文件，即只有第一类记录
    */
  override def getLatentFinger(userid: String, password: String, ajno: String): DataHandler = {
    info("fun:getLatentFinger,inputParam-userid:{};password:{};ajno:{},time{}:",userid,password,ajno,new Date)
    //TODO 使用接口方法查询
   /* try{
      var caseid = ""
      var fingerid = ""
      if(null != ajno && ajno.contains("A") && 23 == ajno.length){
        val ss = ajno.split("A")
        caseid = ss(1)
        if(caseInfoService.isExist(caseid)){

          val sql = "select t.fingerid from NORMALLP_LATFINGER t where t.caseid =?"
          JdbcDatabase.queryFirst(sql) { ps =>
            ps.setString(1, caseid)
          } { rs =>
            fingerid = rs.getString("fingerid")
          }.get

          val lpCard = lpCardService.getLPCard(fingerid)   //NORMALLP_LATFINGER  fingerid
          val caseInfo = caseInfoService.getCaseInfo(caseid)  //NORMALLP_CASE  caseid

          val fptObj = FPTFileBuilder.convertProtoBuf2LPFPT4File(lpCard,caseInfo)
          new DataHandler(new ByteArrayDataSource(fptObj.toByteArray(AncientConstants.GBK_ENCODING)))
        }else{
          FPTFileBuilder.emptyFPT
        }
      }else{
        throw new Exception("传入的案件编号ajno:不符合要求")
      }
    }catch{
      case e : Exception => error("fun:getLatentFinger Exception" +
        ",inputParam-userid:{};password:{};ajno:{},errormessage:{},outtime:{}"
        ,userid,password,ajno,e.getMessage,new Date)
        FPTFileBuilder.emptyFPT
    }*/
    FPTFileBuilder.emptyFPT
  }
}
