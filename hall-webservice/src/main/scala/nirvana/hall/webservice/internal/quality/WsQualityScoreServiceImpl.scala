package nirvana.hall.webservice.internal.quality

import java.io.File
import java.util.Date
import javax.activation.DataHandler

import monad.support.services.MonadException
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.c.services.gfpt5lib.FPT5File
import nirvana.hall.support.services.XmlLoader
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.quality.{StrategyService, WsQualityScoreService}
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.{FileUtils, IOUtils}
import stark.utils.services.LoggerSupport

import scala.io.Source


/**
  * Created by yuchen on 2017/7/24.
  */
class WsQualityScoreServiceImpl(hallWebserviceConfig: HallWebserviceConfig,
                                fpt5Service: FPT5Service,
                                strategyService : StrategyService) extends WsQualityScoreService with LoggerSupport{
  /**
    * 接口01:捺印指纹信息录入
    *
    * @param userID    请求方id
    * @param password  请求方密码
    * @param 	NYZZWdh 捺印指掌纹信息交换文件
    * @return xml结果文件
    */
  override def sendFingerPrint(userID: String, password: String, NYZZWdh: DataHandler): Array[Byte] = {
    val responseInfo = new ResponseInfo
    responseInfo.STATUS ="SUCCESS"

    val paramMap = new scala.collection.mutable.HashMap[String,Any]
    paramMap.put("userID",userID)
    paramMap.put("password",password)
    paramMap.put("NYZZWdh",NYZZWdh)
    //验证传入参数是否为空
    strategyService.inputParamIsNullOrEmpty(paramMap)

    try{
      val path = "/fingerPrintFPT" + "/" + new Date().getTime.toString
      FileUtils.writeByteArrayToFile(new File(path +".fptx"), IOUtils.toByteArray(NYZZWdh.getInputStream))
      info("请求信息数据 ：userId:"+userID + " password:"+password + " fingerprintPackage:"+ path)
      if(!userID.equals("gafis") || !password.equalsIgnoreCase(DigestUtils.md5Hex("gafis"))){
        throw new Exception("userID或password 错误！")
      }
      val content = Source.fromInputStream(NYZZWdh.getInputStream).mkString
      val fpt5File = XmlLoader.parseXML[FPT5File](content,xsd = Some(getClass.getResourceAsStream("/nirvana/hall/fpt5/fingerprint.xsd")), basePath= "/nirvana/hall/fpt5/")
      for(i <- 0 until fpt5File.fingerprintPackage.size) {
        info("开始保存捺印信息")
        fpt5Service.addQualityFingerprintPackage(fpt5File.fingerprintPackage(i))
        info("捺印信息保存完成")
      }
    }
    catch {
      case e:Exception =>
        responseInfo.STATUS = "FAIL"
        responseInfo.MSG = e.getMessage
        //记录错误日志
        error(e.getMessage)
      case ex:MonadException =>
        responseInfo.STATUS = "FAIL"
        responseInfo.MSG = "xsd校验未通过"+ ex.getMessage
      //记录错误日志
        error(ex.getMessage)
    }
    responseInfo.RETURNCODE = ""
    val resultXml = XmlLoader.toXml(responseInfo)
    resultXml.getBytes("UTF-8")
  }

}
