package nirvana.hall.v70.ln.services

import java.text.SimpleDateFormat
import javax.activation.DataHandler

import nirvana.hall.api.services.{ExceptRelationService, GetPKIDService}
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.c.services.gfpt4lib.FPT4File._
import nirvana.hall.v70.ln.jpa._
import org.apache.axiom.attachments.ByteArrayDataSource

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/9/21.
 */
class ExceptRelationServiceImpl(fptService: FPTService, getPKIDService: GetPKIDService) extends ExceptRelationService{
  /**
    * 导出比对关系
    *
    * @param queryid
    * @param ora_sid
    * @return
    */
  override def exportMatchRelation(queryid:String,ora_sid:String): DataHandler = {
    val fPT4File = new FPT4File
    var dataHandler:DataHandler = null
    val num = 0
    val pkidlist = getPKIDService.getDataInfo(queryid,ora_sid)
    for (i <- 0 until pkidlist.size)
    {
      val gafisCheckinInfo = GafisCheckinInfo.find_by_pkId(pkidlist(i).get("pk_id").get.asInstanceOf[String])
      if(gafisCheckinInfo.nonEmpty){
        val gafisCaseFingerSource = GafisCaseFinger.find_by_fingerId(gafisCheckinInfo.headOption.get.code)
        val gafisCaseFingerDest = GafisCaseFinger.find_by_fingerId(gafisCheckinInfo.headOption.get.tcode)
        val gafisGatherFingerSource = GafisGatherFinger.find_by_personId(gafisCheckinInfo.headOption.get.code)
        val gafisGatherFingerDest = GafisGatherFinger.find_by_personId(gafisCheckinInfo.headOption.get.tcode)
        if(gafisCaseFingerSource.nonEmpty || gafisCaseFingerDest.nonEmpty || gafisGatherFingerSource.nonEmpty || gafisGatherFingerDest.nonEmpty){
          gafisCheckinInfo.headOption.get.querytype.toString match {
            case MatchRelationService.querytypeTT =>
              val logic02RecSource = fptService.getLogic02Rec(gafisGatherFingerSource.head.personId)
              val logic02RecDest = fptService.getLogic02Rec(gafisGatherFingerDest.head.personId)
              val logic05Rec = fptService.getLogic05Rec(pkidlist(i).get("pk_id").get.asInstanceOf[String],num)
              val logic02List = new ArrayBuffer[Logic02Rec]
              if(null != fPT4File.logic02Recs){
                for (i <- 0 until fPT4File.logic02Recs.length){
                  logic02List += fPT4File.logic02Recs(i)
                }
                logic02List += logic02RecDest
              }else{
                logic02List += logic02RecSource
                logic02List += logic02RecDest
              }
              fPT4File.logic02Recs = logic02List.toArray
              val logic05List = new ArrayBuffer[Logic05Rec]
              if(null != fPT4File.logic05Recs){
                for(i <- 0 until fPT4File.logic05Recs.length){
                  logic05List += fPT4File.logic05Recs(i)
                }
                logic05List += logic05Rec
              }else{
                logic05List += logic05Rec
              }
              fPT4File.logic05Recs = logic05List.toArray
            case MatchRelationService.querytypeTL=>
              val logic03Rec = fptService.getLogic03Rec(gafisCaseFingerSource.head.caseId)
              val logic02Rec = fptService.getLogic02Rec(gafisGatherFingerDest.head.personId)
              val logic04Rec = fptService.getLogic04Rec(pkidlist(i).get("pk_id").get.asInstanceOf[String],num)
              fPT4File.logic02Recs = Array(logic02Rec)
              if(null != fPT4File.logic03Recs){
                val logic03List = new ArrayBuffer[Logic03Rec]
                for(i <- 0 until fPT4File.logic03Recs.length){
                  logic03List += fPT4File.logic03Recs(i)
                }
                logic03List += logic03Rec
                fPT4File.logic03Recs = logic03List.toArray
              }else{
                fPT4File.logic03Recs = Array(logic03Rec)
              }
              val logic04List = new ArrayBuffer[Logic04Rec]
              if(null != fPT4File.logic04Recs){
                for(i <- 0 until fPT4File.logic04Recs.length){
                  logic04List += fPT4File.logic04Recs(i)
                }
                logic04List += logic04Rec
              }else{
                logic04List += logic04Rec
              }
              fPT4File.logic04Recs = logic04List.toArray
            case MatchRelationService.querytypeLT=>
              val logic02Rec = fptService.getLogic02Rec(gafisGatherFingerDest.head.personId)
              val logic03Rec = fptService.getLogic03Rec(gafisCaseFingerSource.head.caseId)
              val logic04Rec = fptService.getLogic04Rec(pkidlist(i).get("pk_id").get.asInstanceOf[String],num)
              if(null != fPT4File.logic02Recs){
                val logic02List = new ArrayBuffer[Logic02Rec]
                for(i <- 0 until fPT4File.logic02Recs.length){
                  logic02List += fPT4File.logic02Recs(i)
                }
                logic02List += logic02Rec
                fPT4File.logic02Recs = logic02List.toArray
              }else{
                fPT4File.logic02Recs = Array(logic02Rec)
              }
              fPT4File.logic03Recs = Array(logic03Rec)
              val logic04List = new ArrayBuffer[Logic04Rec]
              if(null != fPT4File.logic04Recs){
                for(i <- 0 until fPT4File.logic04Recs.length){
                  logic04List += fPT4File.logic04Recs(i)
                }
                logic04List += logic04Rec
              }else{
                logic04List += logic04Rec
              }
              fPT4File.logic04Recs = logic04List.toArray
            case MatchRelationService.querytypeLL =>
              val logic03RecSource = fptService.getLogic03Rec(gafisCaseFingerSource.head.caseId)
              val logic03RecDest = fptService.getLogic03Rec(gafisCaseFingerDest.head.caseId)
              val logic06Rec = fptService.getLogic06Rec(pkidlist(i).get("pk_id").get.asInstanceOf[String],num)
              val logic03List = new ArrayBuffer[Logic03Rec]
              if(null != fPT4File.logic03Recs){
                for(i <- 0 until fPT4File.logic03Recs.length){
                  logic03List += fPT4File.logic03Recs(i)
                }
                logic03List += logic03RecDest
                fPT4File.logic03Recs = logic03List.toArray
              }else{
                logic03List += logic03RecSource
                logic03List += logic03RecDest
                fPT4File.logic03Recs = logic03List.toArray
              }
              val logic06List = new ArrayBuffer[Logic06Rec]
              if(null != fPT4File.logic06Recs){
                for(i <- 0 until fPT4File.logic06Recs.length){
                  logic06List += fPT4File.logic06Recs(i)
                }
                logic06List += logic06Rec
              }else{
                logic06List += logic06Rec
              }
              fPT4File.logic06Recs = logic06List.toArray
            case _ =>
              throw new Exception("queryType error:" + gafisCheckinInfo.headOption.get.querytype)
          }
        }
      }
      dataHandler = new DataHandler(new ByteArrayDataSource(fPT4File.build().toByteArray()))
    }
    dataHandler
  }

  /**
    * 获取查询的比对关系
    *
    * @param pkid
    * @return
    */
  override def getSearchMatchRelation(pkid: String,num:Int): GafisMatchInfo = {
    val gafisCheckinInfo = GafisCheckinInfo.find_by_pkId(pkid)
    val gafisMatchInfo = new GafisMatchInfo
    val df = new SimpleDateFormat("yyyyMMdd")
    if(gafisCheckinInfo.size>0){
      gafisMatchInfo.code = gafisCheckinInfo.headOption.get.code
      gafisMatchInfo.tcode = gafisCheckinInfo.headOption.get.tcode
      gafisMatchInfo.registerOrg = gafisCheckinInfo.headOption.get.registerOrg
      gafisMatchInfo.registerUser = SysUser.find_by_pkId(gafisCheckinInfo.headOption.get.registerUser).headOption.get.trueName
      gafisMatchInfo.registerTime = df.format(gafisCheckinInfo.headOption.get.registerTime)
      gafisMatchInfo.querytype = gafisCheckinInfo.headOption.get.querytype
      gafisMatchInfo.fgp = gafisCheckinInfo.headOption.get.fgp
      gafisMatchInfo.matchName = SysDepart.find_by_code(gafisMatchInfo.registerOrg).headOption.get.name
    }
    gafisMatchInfo
  }
}
