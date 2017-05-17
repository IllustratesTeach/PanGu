package nirvana.hall.v62.internal

import java.io.ByteArrayOutputStream
import javax.activation.DataHandler

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.api.services.{ExceptRelationService, LPCardService, QueryService}
import nirvana.hall.c.services.gfpt4lib.FPT4File.{FPT4File, Logic02Rec, Logic03Rec}
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.services.service.GetPKIDService
import org.apache.axiom.attachments.ByteArrayDataSource

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/6/21.
 */
class ExceptRelationServiceImpl(v62Config: HallV62Config, facade: V62Facade, lPCardService: LPCardService, queryService: QueryService, getPKIDService: GetPKIDService, fptService: FPTService) extends ExceptRelationService with LoggerSupport{

  /**
    * 导出比对关系
    *
    * @param queryid
    * @param  ora_sid
    * @return
    */
  override def exportMatchRelation(queryid:String,ora_sid:String): DataHandler = {
    val fPT4File = new FPT4File
    var dataHandler:DataHandler = null
    val pkidlist = getPKIDService.getDataInfo(queryid,ora_sid)
    for (i <- 0 to pkidlist.size - 1)
    {
      if(pkidlist(i).get("candlist").size>0){
        for (ii<- 1 to pkidlist(i).get("num").get.asInstanceOf[Int]){
          val tscnt = new Array[Byte](1)
          Array.copy(pkidlist(i).get("candlist").get.asInstanceOf[Array[Byte]],96*(ii-1)+56,tscnt,0,1)
          val flag:Byte = {7}
          if(tscnt(0).equals(flag)){
            val tcode = new Array[Byte](32)
            Array.copy(pkidlist(i).get("candlist").get.asInstanceOf[Array[Byte]],96*(ii-1)+8,tcode,0,32)
            val code = bytes2String(tcode)
            val source = pkidlist(i).get("keyid").get.asInstanceOf[String].substring(0,pkidlist(i).get("keyid").get.asInstanceOf[String].length - 2)
            exportImplMRELATION(fPT4File,source,pkidlist(i).get("querytype").get.asInstanceOf[String],
              code,pkidlist(i).get("ora_uuid").get.asInstanceOf[String],ii)
          }
          }
      }
    dataHandler = new DataHandler(new ByteArrayDataSource(fPT4File.build().toByteArray()))
    }
    dataHandler
  }

  /**
    * 实现导出比对关系
    * @param fPT4File
    * @param code
    * @param querytype
    * @param tcode
    * @param uuid
    */
  def exportImplMRELATION(fPT4File:FPT4File,code:String,querytype:String,tcode:String,uuid:String,num:Int):Unit={

    querytype match{
      case MatchRelationService.querytypeTT =>
        val logic02RecSource = fptService.getLogic02Rec(code)
        val logic02RecDest = fptService.getLogic02Rec(tcode)
        val logic05Rec = fptService.getLogic05Rec(uuid,num)
        val logic02List = new ArrayBuffer[Logic02Rec]()
        logic02List += logic02RecSource
        logic02List += logic02RecDest
        fPT4File.logic02Recs = logic02List.toArray
        fPT4File.logic05Recs = Array(logic05Rec)
      case MatchRelationService.querytypeTL=>
        val logic03Rec = fptService.getLogic03Rec(code)
        val logic02Rec = fptService.getLogic02Rec(tcode)
        val logic04Rec = fptService.getLogic04Rec(uuid,num)
        fPT4File.logic03Recs = Array(logic03Rec)
        fPT4File.logic02Recs = Array(logic02Rec)
        fPT4File.logic04Recs = Array(logic04Rec)
      case MatchRelationService.querytypeLT=>
        val logic02Rec = fptService.getLogic02Rec(code)
        val logic03Rec = fptService.getLogic03Rec(tcode)
        val logic04Rec = fptService.getLogic04Rec(uuid,num)
        fPT4File.logic02Recs = Array(logic02Rec)
        fPT4File.logic03Recs = Array(logic03Rec)
        fPT4File.logic04Recs = Array(logic04Rec)
      case MatchRelationService.querytypeLL =>
        val logic03RecSource = fptService.getLogic03Rec(code)
        val logic03RecDest = fptService.getLogic03Rec(tcode)
        val logic06Rec = fptService.getLogic06Rec(uuid,num)
        val logic03List = new ArrayBuffer[Logic03Rec]()
        logic03List += logic03RecSource
        logic03List += logic03RecDest
        fPT4File.logic03Recs = logic03List.toArray
        fPT4File.logic06Recs = Array(logic06Rec)
      case _ =>
        throw new Exception("queryType error:" + querytype)
    }
  }

  /**
    * 获取查询的比对关系
    *
    * @param pkid
    * @return
    */
  override def getSearchMatchRelation(pkid: String,num: Int): GafisMatchInfo = {
    val gafisMatchInfo = new GafisMatchInfo
    val pkidlist = getPKIDService.getDatabyPKIDInfo(pkid)
    for (i <- 0 to pkidlist.size - 1)
    {
      gafisMatchInfo.code = pkidlist(0).get("keyid").get.asInstanceOf[String]
      gafisMatchInfo.registerOrg = pkidlist(0).get("checkerunitcode").get.asInstanceOf[String]
      gafisMatchInfo.registerUser = pkidlist(0).get("checkusername").get.asInstanceOf[String]
      gafisMatchInfo.registerTime = pkidlist(0).get("checktime").get.toString
      gafisMatchInfo.querytype = pkidlist(0).get("querytype").get.asInstanceOf[String]
      val tcode = new Array[Byte](32)
      Array.copy(pkidlist(i).get("candlist").get.asInstanceOf[Array[Byte]],96*(num-1)+8,tcode,0,32)
      val tfgp = new Array[Byte](1)
      Array.copy(pkidlist(i).get("candlist").get.asInstanceOf[Array[Byte]],96*(num-1)+65,tfgp,0,1)
      val codes = bytes2String(tcode)
      val fgp = bytes2String(tfgp)
      gafisMatchInfo.tcode = codes
      gafisMatchInfo.fgp = fgp
    }
    gafisMatchInfo
  }
  /**
    * 将byte数组转换为String(GB2312格式编码),去除byte数组空字节
    */
  def bytes2String(bs:Array[Byte]): String = {
    val out = new ByteArrayOutputStream();
    for (i <- 0 to bs.size - 1) {
      if(bs(i) != 0){
        out.write(bs(i));
      }
    }
    try {
      out.toString("GB2312");
    }
    out.toString();
  }

}
