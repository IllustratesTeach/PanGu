package nirvana.hall.webservice.internal.survey.gz


import java.io.{ByteArrayInputStream, File}
import java.util.Date

import com.google.protobuf.ByteString
import monad.support.services.{LoggerSupport, XmlLoader}
import nirvana.hall.api.services.{CaseInfoService, LPCardService, LPPalmService}
import nirvana.hall.api.services.remote.HallImageRemoteService
import nirvana.hall.c.services.gfpt4lib.FPT4File.Logic03Rec
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.internal.FPTMntConverter
import nirvana.hall.image.internal.FPTImageConverter
import nirvana.hall.protocol.api.FPTProto._
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.internal.survey.gz.vo.{CaseTextList, ListCaseNode}
import nirvana.hall.webservice.services.survey.{HandprintService, SurveyRecord}
import org.apache.commons.io.{FileUtils, IOUtils}
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import stark.webservice.services.StarkWebServiceClient

import scala.collection.mutable.ArrayBuffer




/**
  * Created by ssj on 2017/11/13.
  */
class GetDateServiceCronService(hallWebserviceConfig: HallWebserviceConfig,
                                surveyRecord: SurveyRecord) extends LoggerSupport{
  val url = hallWebserviceConfig.union4pfmip.url
  val targetNamespace = hallWebserviceConfig.union4pfmip.targetNamespace
  val userId = hallWebserviceConfig.union4pfmip.user
  val password = hallWebserviceConfig.union4pfmip.password
  val client = StarkWebServiceClient.createClient(classOf[HandprintService], url, targetNamespace)

  /**
    * 定时器，调用海鑫现勘接口
    * 获取现勘记录表中的 文字信息 和 指掌纹数据 和 接警编号
    * 1 判断是否有 案件编号 使用获取案事件编号接口 getCaseNo
    * 2 如果有案事件编号则调用获取文字信息的接口 getOriginalData 传入参数 T，然后将获取的数据入库，现勘记录表
    * 根据现勘号，一个一个获取指掌纹数据。直接入库操作，更新指掌纹记录表和现勘记录表
    * 如果没有则跳出当前定时
    * 3 入库成功后，调用获取接警编号接口，存入oracle case表中并更新记录表
    *
    * @param periodicExecutor
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if(hallWebserviceConfig.union4pfmip.cron!= null){
      periodicExecutor.addJob(new CronSchedule(hallWebserviceConfig.union4pfmip.cron), "sync-cron", new Runnable {
        override def run(): Unit = {
          info("begin GetDateServiceCronService")
          try{
            val caseNolist = surveyRecord.getXkcodebyState(0)
            if(caseNolist.size > 0){
              for(i <- 0 until caseNolist.size){
                val kno = caseNolist(i)
                //拼接request msg
                val map = new scala.collection.mutable.HashMap[String,Any]
                map += ("a" -> userId)
                map += ("b" -> password)
                map += ("c" -> kno)
                //获取没有处理过的现勘编号的对应的案件编号
                val caseid = client.getCaseNo(userId, password, kno)
                val requestmsgs = surveyRecord.mapToSting("getCaseNo",map)
                surveyRecord.saveSurveyLogRecord("getCaseNo","","",requestmsgs,caseid.toString,"")
                info("hx  getCaseNo -- 案件编号：" + caseid)
                if(caseid.equals("")||caseid == null){
                  return
                }else{
                  val map1 = new scala.collection.mutable.HashMap[String,Any]
                  map1 += ("a" -> userId)
                  map1 += ("b" -> password)
                  map1 += ("c" -> kno)
                  map1 += ("d" -> "")
                  map1 += ("e" -> "T")
                  //TODO 执行获取案件信息入库操作
                  val caseinfo = client.getOriginalData(userId,password,kno,"","T")
                  val requestmsgs1 = surveyRecord.mapToSting("getOriginalData",map1)
                  surveyRecord.saveSurveyLogRecord("getOriginalData","","",requestmsgs1,caseinfo.toString,"")
                  info("hx  getOriginalDataList --" + caseinfo.toString)

                  val caseText = XmlLoader.parseXML[CaseTextList](new String(caseinfo))
                  for(i <- 0 until caseText.K.size()){
                    //案件文字信息解析入库操作(默认caseid案件编号存在或不存在)
                    surveyRecord.addCaseInfo(caseText.K.get(i),caseid)
                    //更新现勘表状态为入库状态（入库成功）
                    surveyRecord.updateXkcodeState(Constant.xkcodeknosuccess,kno)

                    val snorecordlist = surveyRecord.getSurveySnoRecord(kno)
                    if(snorecordlist.size > 0){
                      for(s <- 0 until snorecordlist.size){
                        val map2 = new scala.collection.mutable.HashMap[String,Any]
                        map2 += ("a" -> userId)
                        map2 += ("b" -> password)
                        map2 += ("c" -> kno)
                        map2 += ("d" -> snorecordlist(i).get("sno").get.asInstanceOf[String])
                        map2 += ("e" -> snorecordlist(i).get("cardtype").get.asInstanceOf[String])
                        val fpt = client.getOriginalData(userId,password,kno,snorecordlist(i).get("sno").get.asInstanceOf[String],snorecordlist(i).get("cardtype").get.asInstanceOf[String])
                        val path = hallWebserviceConfig.localTenprintPath + snorecordlist(i).get("sno").get.asInstanceOf[String] + ".FPT"
                        val requestmsgs2 = surveyRecord.mapToSting("getOriginalData",map2)
                        surveyRecord.saveSurveyLogRecord("getOriginalData","","",requestmsgs2,path,"")
                        if(hallWebserviceConfig.saveFPTFlag.equals("1")){
                          FileUtils.writeByteArrayToFile(new File(path), IOUtils.toByteArray(new ByteArrayInputStream(fpt)))
                        }
                        try{
                          snorecordlist(i).get ("cardtype").get.asInstanceOf[String] match {
                            case "F" =>
                              val fptFile = FPTFile.parseFromInputStream(getClass.getResourceAsStream(path))
                              val logic03Rec = fptFile.right.get.logic03Recs(0)
                              surveyRecord.addFingers(logic03Rec)
                              surveyRecord.updateSnoState(Constant.snosuccess,snorecordlist(i).get("sno").get.asInstanceOf[String])
                            case "P" =>
                              val fptFile = FPTFile.parseFromInputStream(getClass.getResourceAsStream(path))
                              val logic03Rec = fptFile.right.get.logic03Recs(0)
                              surveyRecord.addPalms(logic03Rec)
                              surveyRecord.updateSnoState(Constant.snosuccess,snorecordlist(i).get("sno").get.asInstanceOf[String])
                          }
                        }catch {
                          case e: Exception =>
                            error("GetDateServiceCronService-error:" + e.getMessage)
                            val map3 = new scala.collection.mutable.HashMap[String,Any]
                            map3 += ("a" -> userId)
                            map3 += ("b" -> password)
                            map3 += ("c" -> kno)
                            map3 += ("d" -> snorecordlist(i).get("sno").get.asInstanceOf[String])
                            map3 += ("e" -> "")
                            map3 += ("f" -> snorecordlist(i).get("cardtype").get.asInstanceOf[String])
                            map3 += ("g" -> "9")
                            val responses = client.FBUseCondition(userId,password,kno,snorecordlist(i).get("sno").get.asInstanceOf[String],"",snorecordlist(i).get("cardtype").get.asInstanceOf[String],"9")
                            val requestmsgs3 = surveyRecord.mapToSting("FBUseCondition",map3)
                            surveyRecord.saveSurveyLogRecord("FBUseCondition","","",requestmsgs3,responses,"")
                        }
                      }
                      val map4 = new scala.collection.mutable.HashMap[String,Any]
                      map4 += ("a" -> userId)
                      map4 += ("b" -> password)
                      map4 += ("c" -> kno)
                      val receprtionNO = client.getReceptionNo(userId,password,kno)
                      val requestmsgs4 = surveyRecord.mapToSting("getReceptionNo",map4)
                      surveyRecord.saveSurveyLogRecord("getReceptionNo","","",requestmsgs4,receprtionNO.toString,"")
                      info("hx  getReceptionNo --" + receprtionNO.toString)
                      if(receprtionNO.equals("")||receprtionNO == null){
                        surveyRecord.updateXkcodeState(Constant.xkcodeajbherror,kno)
                      }else{
                        //TODO 接警编号入库操作
                        surveyRecord.updateCasePeception(receprtionNO,kno)
                        //更新已经获取到接警编号的现勘号的状态
                        surveyRecord.updateXkcodeState(Constant.xkcodeajbhsuccess,kno)
                      }
                    }
                  }
                }
              }
            }else{
              info("没有需要获取的现勘数据...")
            }
          }catch{
            case e: Exception =>
              error("GetDateServiceCronService-error:" + e.getMessage)
              surveyRecord.saveSurveyLogRecord("","","","","",e.getMessage)
          }
          info("end GetDateServiceCronService")
        }
      })
    }
  }
 }
