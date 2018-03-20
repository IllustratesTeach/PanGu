package nirvana.hall.matcher.internal.adapter.common

import java.io.ByteArrayOutputStream
import java.util.UUID
import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYCANDSTRUCT
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.adapter.common.vo.{GafisCheckinInfoVo, QueryQueVo}
import nirvana.hall.matcher.service.AutoCheckService
import nirvana.hall.support.services.JdbcDatabase
import nirvana.protocol.MatchResult.MatchResultRequest
import org.jboss.netty.buffer.ChannelBuffers

import scala.collection.mutable

/**
  * Created by songpeng on 2017/8/10.
  */
class AutoCheckServiceImpl(hallMatcherConfig: HallMatcherConfig, implicit val dataSource: DataSource) extends AutoCheckService with LoggerSupport{
  /**
    * TT自动认定,在比对返回候选的时候自动认定
    * @param matchResultRequest
    * @param queryQue
    */
  override def ttAutoCheck(matchResultRequest: MatchResultRequest, queryQue: QueryQueVo): Unit = {
    if(hallMatcherConfig.autoCheck != null){
      //获取查询卡的重卡组号
      var repeatNoOpt = getFingerRepeatNoByPersonid(queryQue.keyId)
      //获取候选队列
      val candList = getCandListArrayByOraSid(queryQue.oraSid)
      //获取当前任务相关信息
      val queryDetailInfo = getQueryDetailInfo(queryQue.oraSid)
      //获取查询卡的文字信息
      val personInfo = getPerosnInfoByPersonId(queryQue.keyId)
      if(candList.size > 0 && personInfo.nonEmpty && queryDetailInfo.nonEmpty){
        //当前比对任务是否认定成功
        var verifyResult = 99
        //重卡比中个数
        var checkInNum = 0
        //比对任务提交系统 1：指纹系统，2：标采系统
        val submittSystem = queryDetailInfo.get._2
        val candByteArray = new ByteArrayOutputStream()
        candList.foreach{cand =>
          var newFlag = 7 //新的候选比中状态，默认7：已否定
          //大于自动认定分数，自动认定
          if(cand.nScore >= hallMatcherConfig.autoCheck.confirmScore){
            //判断是否存在查重比中关系，若不存在自动认定
            if(!hasMatchRelation(queryQue.keyId,cand.szKey)){
              //获取候选卡重卡组号
              val personidRepeatNo = getFingerRepeatNoByPersonid(cand.szKey).getOrElse(null)
              verifyResult = 98
              checkInNum += 1
              newFlag = 6
              //生成重卡组
              repeatNoOpt match {
                case Some(repeatNo)=>
                  if(personidRepeatNo == null){
                    //更新候选卡重卡组号
                    updateFingerRepeatNoByPersonid(cand.szKey, repeatNo)
                  } else if(personidRepeatNo.equals(repeatNo)){
                    //已经存在相同的重卡组号
                    info("已经存在重卡组号:{}", repeatNo)
                  }else{
                    error("重卡归并任务号：{}，人员编号：{}，{} 存在不相同的重卡组号", queryQue.oraSid, queryQue.keyId, personidRepeatNo)
                  }
                case None =>
                  if(personidRepeatNo != null){
                    updateFingerRepeatNoByPersonid(queryQue.keyId,personidRepeatNo)
                  }else{
                    //双方都不存在重卡组号，使用查询卡号作为新的重卡组号
                    repeatNoOpt = Some(queryQue.keyId)
                    updateFingerRepeatNoByPersonid(queryQue.keyId, queryQue.keyId)
                    updateFingerRepeatNoByPersonid(cand.szKey, queryQue.keyId)
                  }
              }

              //新增查中登记记录
              val checkinInfoVo = new GafisCheckinInfoVo
              checkinInfoVo.code = queryQue.keyId
              checkinInfoVo.tcode = cand.szKey
              checkinInfoVo.rank = cand.nStepOneRank //排名
              checkinInfoVo.fraction = cand.nScore //得分
              checkinInfoVo.fgp = cand.nIndex //比中指位
              checkinInfoVo.cardType1 = if(queryQue.isPalm) 2 else 1
              checkinInfoVo.queryUUID = queryDetailInfo.get._1
              checkinInfoVo.priority = queryDetailInfo.get._3
              checkinInfoVo.hitpossibility = queryDetailInfo.get._4
              checkinInfoVo.registerUser = personInfo.get._1 //查中登记用户
              checkinInfoVo.registerOrg = personInfo.get._2 //查中登记单位
              createMatchRelation(checkinInfoVo,submittSystem)
            }else{
              //已存在比中关系，当前候选不认定
              newFlag = 0
            }
          }else if(cand.nScore < hallMatcherConfig.autoCheck.denyScore){
            //小于自动否定分数
          }
          //更新候选比中状态
          cand.nFlag = newFlag.toByte
          candByteArray.write(cand.toByteArray(AncientConstants.GBK_ENCODING))
        }
        //更新任务表信息
        updateQueryInfo(verifyResult,candByteArray.toByteArray,queryQue.oraSid,checkInNum,submittSystem,queryQue.keyId)
      }
    }
  }

  /**
    * 通过查询中的卡号查询对应该记录的重卡组号(卡号即personid;重卡组号对应gafis_person中的fingerrepeatno字段)
    * @param personid 人员编号 即发送查询的卡号keyid
    * @return
    */
  private def getFingerRepeatNoByPersonid(personid: String): Option[String] ={
    var repeatNo:Option[String] = None
    val sql = "SELECT FINGERREPEATNO FROM GAFIS_PERSON t where t.PERSONID=?"
    JdbcDatabase.queryWithPsSetter(sql){
      ps=> ps.setString(1, personid)
    }{
      rs=>
        val fingerRepeatNo = rs.getString("FINGERREPEATNO")
        if(fingerRepeatNo != null){
          repeatNo = Some(fingerRepeatNo)
        }
     }
    repeatNo
  }

  /**
    * 按照人员编号更新gafis_person的重卡组号字段,从而生成重卡关联关系
    * @param personid 人员编号
    * @param fingerRepeatNo 重卡组号
    */
  private def updateFingerRepeatNoByPersonid(personid: String, fingerRepeatNo: String): Unit ={
    val sql = "UPDATE GAFIS_PERSON t SET t.FINGERREPEATNO=? WHERE t.PERSONID=?"
    JdbcDatabase.update(sql){ps=>
      ps.setString(1, fingerRepeatNo)
      ps.setString(2, personid)
    }
  }

  /**
    * 判断两卡号是否存在已认定的比中关系
    * @param code
    * @param tcode
    * @return
    */
  private def hasMatchRelation(code: String ,tcode: String): Boolean ={
    var hasConfirmRelation = false
    val sql = "select t.pk_id from gafis_checkin_info t where ((t.code = ? and t.tcode = ?) or (t.code = ? and t.tcode = ?)) and t.confirm_status = 98 and t.querytype = 0"
    JdbcDatabase.queryWithPsSetter(sql){
      ps =>
        ps.setString(1,code)
        ps.setString(2,tcode)
        ps.setString(3,tcode)
        ps.setString(4,code)
    }{
      rs =>
        val checkId = rs.getString("pk_id")
        if(checkId != null){
          hasConfirmRelation = true
        }
    }
    hasConfirmRelation
  }

  /**
    * 根据任务编号获取候选数组
    * @param oraSid
    * @return
    */
  private def getCandListArrayByOraSid(oraSid : Int) : List[GAQUERYCANDSTRUCT] = {
    val candList = mutable.ListBuffer[GAQUERYCANDSTRUCT]()
    var candArray:Option[Array[Byte]] = None
    val sql = "select t.candlist from gafis_normalquery_queryque t where t.ora_sid = ?"
    JdbcDatabase.queryWithPsSetter(sql){
      ps =>
        ps.setInt(1,oraSid)
    }{
      rs => candArray = Some(rs.getBytes("candlist"))
    }
    if(candArray.nonEmpty && candArray.get != null){
      val buffer = ChannelBuffers.wrappedBuffer(candArray.get)
      while(buffer.readableBytes() >= 96) {
        val gaCand = new GAQUERYCANDSTRUCT
        gaCand.fromStreamReader(buffer)
        candList += gaCand
      }
    }
    candList.toList
  }

  /**
    * 获取查询任务详细信息
    * @param oraSid
    * @return
    */
  private def getQueryDetailInfo(oraSid: Int) : Option[(String,String,Int,Int)] = {
    var queryInfo:Option[(String,String,Int,Int)] = None
    val sql = "select t.PK_ID,t.SUBMITTSYSTEM,t.PRIORITYNEW,t.HITPOSSIBILITY from gafis_normalquery_queryque t where t.ora_sid = ?"
    JdbcDatabase.queryWithPsSetter(sql){
      ps =>
        ps.setInt(1,oraSid)
    }{
      rs => queryInfo = Some(rs.getString("PK_ID"),rs.getString("SUBMITTSYSTEM"),rs.getInt("PRIORITYNEW"),rs.getInt("HITPOSSIBILITY"))
    }
    queryInfo
  }

  /**
    * 新增比中关系
    * @param info
    * @param submittSystem
    */
  private def createMatchRelation(info: GafisCheckinInfoVo, submittSystem:String) : Unit = {
    //插入比中关系
    val checkInSql = "insert into gafis_checkin_info t (t.pk_id,t.code,t.tcode,t.querytype,t.register_time,t.register_user, t.register_org,t.hitpossibility," +
      "t.priority,t.confirm_status,t.confirm_time, t.query_uuid,t.rank,t.fraction,t.fgp,t.card_type1,t.last_handle_date,t.operatetype,t.ck_source )" +
      " values(?,?,?,0,sysdate,?,?,?,?,98,sysdate,?,?,?,?,?,sysdate,0,'1')"
    JdbcDatabase.update(checkInSql){
      ps =>
        ps.setString(1,generatorPkId())
        ps.setString(2,info.code)
        ps.setString(3,info.tcode)
        ps.setString(4,info.registerUser)
        ps.setString(5,info.registerOrg)
        ps.setInt(6,info.hitpossibility)
        ps.setInt(7,info.priority)
        ps.setString(8,info.queryUUID)
        ps.setInt(9,info.rank)
        ps.setInt(10,info.fraction)
        ps.setInt(11,info.fgp)
        ps.setInt(12,info.cardType1)
    }
    //如果比对任务来自标采系统，比中信息存入指纹比对结果表
    if("2".equals(submittSystem)){
      val fingerMatchSql = "insert into GAFIS_FINGER_MATCH m (m.pk_id,m.person_id,m.match_type,m.match_card,m.match_date,m.match_score) " +
          " values (?,?,'TT',?,sysdate,?)"
      JdbcDatabase.update(fingerMatchSql){
        ps =>
          ps.setString(1,generatorPkId())
          ps.setString(2,info.code)
          ps.setString(3,info.tcode)
          ps.setInt(4,info.fraction)
      }
    }
  }

  /**
    * 更新查询任务信息
    * @param verifyResult
    * @param candList
    * @param oraSid
    * @param checkInNum
    * @param submittSystem
    * @param personId
    */
  private def updateQueryInfo(verifyResult: Int, candList: Array[Byte], oraSid: Int, checkInNum: Int, submittSystem: String, personId:String) : Unit = {
    val updateQueryQueSql = "update gafis_normalquery_queryque t set t.verifyresult=?,t.candlist=?,t.handleresult=1 where ora_sid = ? "
    JdbcDatabase.update(updateQueryQueSql){
      ps =>
        ps.setInt(1,verifyResult)
        ps.setBytes(2,candList)
        ps.setInt(3,oraSid)
    }
    //如果比对任务来自标采系统，修改指纹上报入库查询监控状态
    if("2".equals(submittSystem)){
      val getFingerMonitorSql = "select t.PK_ID,p.NAME,p.GATHER_ORG_CODE,p.GATHERUSERID from GAFIS_FINGER_MONITOR t,gafis_person p " +
        "where t.person_id = p.personid and p.personid = ? and rownum = 1"
      val personInfo = mutable.Map[String, String]()
      JdbcDatabase.queryWithPsSetter(getFingerMonitorSql){
        ps =>
          ps.setString(1,personId)
      }{
        rs =>
          personInfo.put("pkId",rs.getString("PK_ID"))
          personInfo.put("name", rs.getString("NAME"))
          personInfo.put("gatherOrgCode", rs.getString("GATHER_ORG_CODE"))
          personInfo.put("gatherUserid", rs.getString("GATHERUSERID"))
      }
      if(personInfo.get("pkId").nonEmpty){
        val updateMonitorSql = "update GAFIS_FINGER_MONITOR t set t.tt_sta=?,t.than_num=?,t.modifiedtime=sysdate,t.tt_handle=1 where t.pk_id = ?"
          JdbcDatabase.update(updateMonitorSql){
          ps =>
            ps.setInt(1,verifyResult)
            ps.setInt(2,checkInNum)
            ps.setString(3,personInfo.get("pkId").get)
        }
        if(verifyResult == 98){
          val insertSysMegSql = "insert into SYS_MEG t (t.pk_id,t.person_id,t.person_name,t.gather_user_id,t.meg_type,t.message,t.look_status," +
            "t.inputtime,t.gather_depart_code) values (?,?,?,?,2,'TT自动认定',0,sysdate,?)"
          JdbcDatabase.update(insertSysMegSql){
            ps =>
              ps.setString(1,generatorPkId())
              ps.setString(2,personId)
              ps.setString(3,personInfo.get("name").getOrElse(""))
              ps.setString(4,personInfo.get("gatherUserid").getOrElse(""))
              ps.setString(5,personInfo.get("gatherOrgCode").getOrElse(""))
          }
        }
      }
    }
  }

  /**
    * 根据捺印卡号获取捺印信息
    * @param personId
    * @return
    */
  private def getPerosnInfoByPersonId(personId:String) : Option[(String,String)] = {
    var personInfo:Option[(String,String)] = None
    val sql = "select p.INPUTPSN,p.GATHER_ORG_CODE from gafis_person p where p.personid = ?"
    JdbcDatabase.queryWithPsSetter(sql){
      ps =>
        ps.setString(1,personId)
    }{
      rs => personInfo = Some(rs.getString("INPUTPSN"),rs.getString("GATHER_ORG_CODE"))
    }
    personInfo
  }
  /**
    * 生成主键
    * @return
    */
  private def generatorPkId() : String = {
    UUID.randomUUID().toString.replaceAll("-","")
  }

}
