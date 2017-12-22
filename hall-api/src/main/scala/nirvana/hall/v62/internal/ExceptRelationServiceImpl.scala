package nirvana.hall.v62.internal

import java.io.ByteArrayOutputStream
import javax.activation.DataHandler

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.api.services.{ExceptRelationService, GetPKIDService, LPCardService, QueryService}
import nirvana.hall.c.services.gfpt4lib.FPT4File._
import nirvana.hall.v62.config.HallV62Config
import org.apache.axiom.attachments.ByteArrayDataSource
import org.apache.commons.lang.StringUtils

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
    var tscnt = new Array[Byte](1)
    var flag:Byte= {0}
    var tflag:Byte= {0}
    val pkidlist = getPKIDService.getDataInfo(queryid,ora_sid)
    for (i <- 0 to pkidlist.size - 1)
    {
      if(null != pkidlist(i).get("candlist")){
        for (ii<- 1 to pkidlist(i).get("num").get.asInstanceOf[Int]){
          if(pkidlist(i).get("querytype").get.asInstanceOf[String].equals(MatchRelationService.querytypeTT)||pkidlist(i).get("querytype").get.asInstanceOf[String].equals(MatchRelationService.querytypeLL)){
            Array.copy(pkidlist(i).get("candlist").get.asInstanceOf[Array[Byte]],96*(ii-1)+57,tscnt,0,1)
            flag = {6}
            tflag = {4}
          }else{
            Array.copy(pkidlist(i).get("candlist").get.asInstanceOf[Array[Byte]],96*(ii-1)+57,tscnt,0,1)
            flag = {7}
            tflag = {7}
          }
          if(tscnt(0).equals(flag)||tscnt(0).equals(tflag)){
            val tcode = new Array[Byte](32)
            Array.copy(pkidlist(i).get("candlist").get.asInstanceOf[Array[Byte]],96*(ii-1)+8,tcode,0,32)
            val code = bytes2String(tcode)
            var source =pkidlist(i).get("keyid").get.asInstanceOf[String].toString
            if(MatchRelationService.querytypeLT.equals(pkidlist(i).get("querytype").get.asInstanceOf[String])
              ||MatchRelationService.querytypeLL.equals(pkidlist(i).get("querytype").get.asInstanceOf[String])){
              source = pkidlist(i).get("keyid").get.asInstanceOf[String].substring(0,pkidlist(i).get("keyid").get.asInstanceOf[String].length - 2)
            }
            exportImplMRELATION(fPT4File,source,pkidlist(i).get("querytype").get.asInstanceOf[String],
              code,pkidlist(i).get("ora_uuid").get.asInstanceOf[String],ii)
            dataHandler = new DataHandler(new ByteArrayDataSource(fPT4File.build().toByteArray()))
          }
        }
      }
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

        val logic02Rec = fptService.getLogic02Rec(code)
        val logic03Rec = fptService.getLogic03Rec(tcode.substring(0,tcode.length-2))
        val logic04Rec = fptService.getLogic04Rec(uuid,num)

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
        val logic02Rec = fptService.getLogic02Rec(tcode)
        val logic03Rec = fptService.getLogic03Rec(code)
        val logic04Rec = fptService.getLogic04Rec(uuid,num)


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
        val logic03RecSource = fptService.getLogic03Rec(code)
        val logic03RecDest = fptService.getLogic03Rec(tcode.substring(0,tcode.length-2))
        val logic06Rec = fptService.getLogic06Rec(uuid,num)


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
      val registerUser = pkidlist(0).get("checkusername").get.asInstanceOf[String]
      gafisMatchInfo.registerTime = pkidlist(0).get("checktime").get.toString
      gafisMatchInfo.querytype = pkidlist(0).get("querytype").get.asInstanceOf[String]
      val tcode = new Array[Byte](32)
      Array.copy(pkidlist(i).get("candlist").get.asInstanceOf[Array[Byte]],96*(num-1)+8,tcode,0,32)
      val tfgp = new Array[Byte](1)
      Array.copy(pkidlist(i).get("candlist").get.asInstanceOf[Array[Byte]],96*(num-1)+56,tfgp,0,1)
      val codes = bytes2String(tcode)
      val fgp = decode(bytes2HexString(tfgp))
      gafisMatchInfo.tcode = codes
      if(gafisMatchInfo.querytype.equals(MatchRelationService.querytypeTL)){
        gafisMatchInfo.code = codes
        gafisMatchInfo.tcode = pkidlist(0).get("keyid").get.asInstanceOf[String]
      }
      //过滤
      if(StringUtils.isEmpty(fgp.toString)||StringUtils.isBlank(fgp.toString)){
        gafisMatchInfo.fgp = "00"
      }
      if(StringUtils.isEmpty(registerUser)||StringUtils.isBlank(registerUser)){
        gafisMatchInfo.registerOrg = "未知"
      }
      gafisMatchInfo.matchName = "未知"
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

  def bytes2HexString(b: Array[Byte]): String = {
    var ret: String = ""
    var i: Int = 0
    while (i < b.length) {
      {
        var hex: String = Integer.toHexString(b(i) & 0xFF)
        if (hex.length == 1) {
          hex = '0' + hex
        }
        ret += hex.toUpperCase
      }
      ({
        i += 1; i - 1
      })
    }
    return ret
  }

  private val hexString: String = "0123456789ABCDEF"

  def   decode(bytes: String):String ={
    val baos: ByteArrayOutputStream = new ByteArrayOutputStream(bytes.length / 2)
    var i: Int = 0
    while (i < bytes.length) {
      baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))))
      i += 2
    }
    return new String(baos.toByteArray)
  }

}