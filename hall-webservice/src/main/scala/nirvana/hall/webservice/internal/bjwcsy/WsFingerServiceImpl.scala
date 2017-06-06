package nirvana.hall.webservice.internal.bjwcsy

import java.util.Date
import javax.activation.DataHandler

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.api.services.{CaseInfoService, LPCardService, TPCardService}
import nirvana.hall.c.services.gfpt4lib.FPT4File.{FPT4File, Logic02Rec, Logic03Rec}
import nirvana.hall.v70.jpa.{GafisCaseFinger, GafisCheckinInfo, GafisGatherFinger}
import nirvana.hall.webservice.services.bjwcsy.WsFingerService
import org.apache.axiom.attachments.ByteArrayDataSource

import scala.collection.mutable.ArrayBuffer


/**
  * 互查系统webservice实现类
  */
class WsFingerServiceImpl(tpCardService: TPCardService, lpCardService: LPCardService, caseInfoService: CaseInfoService, fptService: FPTService) extends WsFingerService with LoggerSupport
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
    val fPT4File = new FPT4File
    try{
      //1 根据查询条件查询捺印文字信息数据集合
      val logic02RecList :Seq[Logic02Rec] = tpCardService.getFPT4Logic02RecList(ryno, xm, xb, idno, zjlb, zjhm, hjddm, xzzdm, rylb, ajlb, qkbs, xcjb, nydwdm, startnydate, endnydate)
      var dataHandler:DataHandler = null
      if(null != logic02RecList && logic02RecList.size > 0){

        fPT4File.logic02Recs = logic02RecList.toArray
        dataHandler = new DataHandler(new ByteArrayDataSource(fPT4File.build().toByteArray()))
      } else {
        dataHandler = new DataHandler(new ByteArrayDataSource(fPT4File.build().toByteArray()))
      }
      dataHandler
    }catch{
      case e : Exception => error("fun:getTenprintFinger Exception" + ",inputParam-userid:{};password:{};ryno:{};xm:{};xb:{};idno:{};zjlb:{};zjhm:{};hjddm:{};xzzdm:{};rylb:{};ajlb:{};qkbs:{};xcjb:{};nydwdm:{};startnydate:{};endnydate:{},errormessage:{}"
        ,userid,password,ryno,xm,xb,idno,zjlb,zjhm,hjddm,xzzdm,rylb,ajlb,qkbs,xcjb,nydwdm,startnydate,endnydate,e.getMessage)
        e.printStackTrace()
        new DataHandler(new ByteArrayDataSource(fPT4File.build().toByteArray()))
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
    val fPT4File = new FPT4File
    try{
      var dataHandler:DataHandler = null
      val logic02Rec = fptService.getLogic02Rec(ryno)
      if(logic02Rec != null){
        fPT4File.logic02Recs = Array(logic02Rec)
        dataHandler = new DataHandler(new ByteArrayDataSource(fPT4File.build().toByteArray()))
      }else{
        dataHandler = new DataHandler(new ByteArrayDataSource(fPT4File.build().toByteArray()))
      }
      dataHandler
    }catch{
      case e : Exception => error("fun:getTenprintFinger Exception" +
        ",inputParam-userid:{};password:{};ryno:{},errormessage:{},outtime:{}"
        ,userid,password,ryno,e.getMessage,new Date)
        new DataHandler(new ByteArrayDataSource(fPT4File.build().toByteArray()))
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
    info("fun:getLatent,inputParam-userid:{};password:{};ajno:{};ajlb:{};fadddm:{};mabs:{};xcjb:{};xcdwdm:{};startfadate:{};endfadate:{}",userid,password, ajno, ajlb, fadddm, mabs, xcjb, xcdwdm, startfadate, endfadate)
    val fPT4File = new FPT4File
    try{
      var dataHandler:DataHandler = null
      val logic03RecList :Seq[Logic03Rec] = caseInfoService.getFPT4Logic03RecList(ajno, ajlb, fadddm, mabs, xcjb, xcdwdm, startfadate, endfadate)
      if(null != logic03RecList && logic03RecList.size > 0){
        val fpt4File = new FPT4File
        fpt4File.logic03Recs = logic03RecList.toArray
        dataHandler = new DataHandler(new ByteArrayDataSource(fpt4File.build().toByteArray()))
      } else {
        dataHandler = new DataHandler(new ByteArrayDataSource(fPT4File.build().toByteArray()))
      }
      dataHandler
    }catch{
      case e : Exception => error("fun:getTenprintFinger Exception" + ",inputParam-userid:{};password:{};ajno:{};ajlb:{};fadddm:{};mabs:{};xcjb:{};xcdwdm:{};startfadate:{};endfadate:{},errormessage:{}"
        ,userid,password,userid,password, ajno, ajlb, fadddm, mabs, xcjb, xcdwdm, startfadate, endfadate, e.getMessage)
        e.printStackTrace()
        new DataHandler(new ByteArrayDataSource(fPT4File.build().toByteArray()))
    }
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
    val fPT4File = new FPT4File
    try{
      var dataHandler:DataHandler = null
      val logic03Rec = fptService.getLogic03Rec(ajno)
      if(logic03Rec != null){
        fPT4File.logic03Recs = Array(logic03Rec)
        dataHandler = new DataHandler(new ByteArrayDataSource(fPT4File.toByteArray()))
      }else{
        dataHandler = new DataHandler(new ByteArrayDataSource(fPT4File.build().toByteArray()))
      }
      dataHandler
    }catch{
      case e : Exception => error("fun:getLatentFinger Exception" +
        ",inputParam-userid:{};password:{};ajno:{},errormessage:{},outtime:{}"
        ,userid,password,ajno,e.getMessage,new Date)
        new DataHandler(new ByteArrayDataSource(fPT4File.build().toByteArray()))
    }
  }
}
