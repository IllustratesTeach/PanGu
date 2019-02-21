package nirvana.hall.webservice.internal.penaltytech.sync

import java.util.{Date, UUID}

import com.alibaba.fastjson.{JSON, JSONArray, JSONObject}
import monad.core.services.{CronScheduleWithStartModel, StartAtDelay}
import monad.support.services.LoggerSupport
import nirvana.hall.api.HallApiConstants
import nirvana.hall.api.internal.{DateConverter, ExceptionUtil}
import nirvana.hall.api.services.QueryService
import nirvana.hall.c.services.gloclib.gaqryque
import nirvana.hall.v62.internal.c.CodeHelper
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.internal.penaltytech.{PenaltyTechConstant, V62ServiceSupport, WebHttpClientUtils}
import nirvana.hall.webservice.internal.penaltytech.jpa.{GafisCronConfig, LogRecord}
import nirvana.hall.webservice.internal.penaltytech.vo.Hit
import nirvana.hall.webservice.penaltytech.GafisEncryptUtil
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor

/**
  * @author <a href="yuchen_1997_200486@126.com">yuchen</a>.
  * @since 2018/12/7
  */
class SyncCronLTHitService(hallWebserviceConfig: HallWebserviceConfig
                           , v62ServiceSupport: V62ServiceSupport
                           , queryService: QueryService) extends LoggerSupport {
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {

    if (hallWebserviceConfig.penaltyTechService.cron_lt != null) {

      periodicExecutor.addJob(new CronScheduleWithStartModel(hallWebserviceConfig.penaltyTechService.cron_lt, StartAtDelay), "SyncCronLTHitService", new Runnable {
        override def run(): Unit = {
          try {
            doWork
          } catch {
            case ex: Exception =>
              error("SyncCronLTHitService:" + ExceptionUtil.getStackTraceInfo(ex))
          }
        }
      })
    }
  }

  private def doWork(): Unit = {
    try {
      info("SyncCronLTHitService-start")
      val startTime = GafisCronConfig.where(GafisCronConfig.typ === PenaltyTechConstant.LT_HIT)
        .and(GafisCronConfig.deleteFlag === PenaltyTechConstant.NOT_DELETE).head.startTime
      val startTimeStr = DateConverter.convertDate2String(startTime, "yyyy-MM-dd HH:mm:ss")
      val currentTime = new Date()
      val currentTimeStr = DateConverter.convertDate2String(currentTime, "yyyy-MM-dd HH:mm:ss")
      info("SyncCronLTHitService-startTime{},currentTime{}", startTimeStr, currentTime)
      val statement = Option("((CheckTime>=to_date('%s','yyyy-MM-dd hh24:mi:ss')) AND (CheckTime<to_date('%s','yyyy-MM-dd hh24:mi:ss')) AND (QUERYTYPE ='%s') AND (VERIFYRESULT ='%s'))"
        .format(startTimeStr, currentTimeStr, gaqryque.QUERYTYPE_LT, PenaltyTechConstant.VERIFYRESULT_HITED))
      info("SyncCronLTHitService-TaskCount{}", v62ServiceSupport.getTask(statement, gaqryque.GAQRY_STATUS_RECHECKED).size)
      val groupNumMap = v62ServiceSupport.getTask(statement, gaqryque.GAQRY_STATUS_RECHECKED).groupBy[Int] { querySimpleStruts =>
        val code = CodeHelper.convertAsInt(querySimpleStruts.nQueryID, 2) % 5
        code match {
          case 0 => 1
          case 1 => 2
          case 2 => 3
          case 3 => 4
          case 4 => 5
          case _ => 6
        }
      }
      info("SyncCronLTHitService-groupNumMap{}", groupNumMap.size)
      val keyit = groupNumMap.keysIterator
      while (keyit.hasNext) {
        val key = keyit.next
        var jsonArray = new JSONArray()
        val list = groupNumMap.get(key).get
        if (list.nonEmpty) {
          list.foreach {
            querySimpleStruts =>
              val taskNo = CodeHelper.convertAsInt(querySimpleStruts.nQueryID, 2)
              info("SyncCronLTHitService-taskNo{}", taskNo)
              val candData = queryService.getGAQUERYSTRUCT(taskNo).pstCand_Data
              info("SyncLLHitService-candData{}", candData.length)
              candData
                .filter(_.nFlag >= 4)
                .foreach {
                  t =>
                    info("创建正查比中关系对象-任务号{}", taskNo)
                    val hit = new Hit
                    hit.sourceTemplateFingerId = querySimpleStruts.szKeyID
                    hit.destTemplateFingerId = t.szKey
                    hit.fingerPalmType = 1 //querySimpleStruts.nFlag.toInt //按布标进行转换
                    hit.hitDate = DateConverter.convertAFISDateTime2String(querySimpleStruts.tCheckTime)
                    hit.hitUser = querySimpleStruts.szReCheckUserName
                    hit.hitUnit = queryService.getGAQUERYSTRUCT(taskNo).pstInfo_Data.szReCheckerUnitCode
                    hit.verifyDate = hit.hitDate
                    hit.verifyStatus = querySimpleStruts.nVerifyResult
                    hit.inputTime = DateConverter.convertAFISDateTime2String(querySimpleStruts.tSubmitTime)
                    hit.inputUser = querySimpleStruts.szUserName
                    hit.inputUnit = queryService.getGAQUERYSTRUCT(taskNo).pstInfo_Data.szUserUnitCode
                    hit.deleteFlag = "1"
                    jsonArray.add(hit.getJSONObject)
                }
          }
        }
        val resultJsonArray = WebHttpClientUtils.call(hallWebserviceConfig.penaltyTechService.data_transport_url + "addlthit", GafisEncryptUtil.encrypt(jsonArray.toJSONString.replace("\\n", "")))
        val results = JSON.parseArray(resultJsonArray).iterator()
        while (results.hasNext) {
          val result = results.next().asInstanceOf[JSONObject]
          var flag = PenaltyTechConstant.SUCCESS
          var errorMsg = ""
          result.getString("flag") match {
            case "0" => flag = PenaltyTechConstant.FAIL; errorMsg = result.getString("errormsg")
            case "1" =>
          }
          new LogRecord(UUID.randomUUID().toString.replace("-", "")
            , result.getString("cardid")
            , PenaltyTechConstant.LT_HIT.toString
            , new Date()
            , flag
            , errorMsg).save()
        }
      }
      GafisCronConfig.update.set(startTime = currentTime, inputTime = new Date()).where(GafisCronConfig.typ === PenaltyTechConstant.LT_HIT).execute
    } catch {
      case ex: Exception =>
        error("SyncCronLTHitService:error:{}", ExceptionUtil.getStackTraceInfo(ex))
    }
  }
}
