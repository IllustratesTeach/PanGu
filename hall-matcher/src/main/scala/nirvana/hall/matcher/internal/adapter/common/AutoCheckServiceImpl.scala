package nirvana.hall.matcher.internal.adapter.common

import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.adapter.common.vo.QueryQueVo
import nirvana.hall.matcher.service.AutoCheckService
import nirvana.hall.support.services.JdbcDatabase
import nirvana.protocol.MatchResult.MatchResultRequest

/**
  * Created by songpeng on 2017/8/10.
  */
class AutoCheckServiceImpl(hallMatcherConfig: HallMatcherConfig, implicit val dataSource: DataSource) extends AutoCheckService with LoggerSupport{
  /**
    * TT自动认定,在比对返回候选的时候自动认定
    * @param matchResultRequest
    */
  override def ttAutoCheck(matchResultRequest: MatchResultRequest, queryQue: QueryQueVo): Unit = {
    if(hallMatcherConfig.autoCheck != null){
      //获取查询卡的重卡组号
      var repeatNoOpt = getFingerRepeatNoByPersonid(queryQue.keyId)
      val candIter = matchResultRequest.getCandidateResultList.iterator()
      while(candIter.hasNext) {
        val cand = candIter.next()
        //大于自动认定分数，自动认定
        if(cand.getScore >= hallMatcherConfig.autoCheck.confirmScore){
          val personidRepeatNoOpt = getPersonidAndFingerRepeatNoBySid(cand.getObjectId)
          if(personidRepeatNoOpt.nonEmpty){
            val personidRepeatNo = personidRepeatNoOpt.get
            repeatNoOpt match {
              case Some(repeatNo)=>
                if(personidRepeatNo._2 == null){
                  //更新候选卡重卡组号
                  updateFingerRepeatNoByPersonid(personidRepeatNo._1, repeatNo)
                } else if(personidRepeatNo._2.equals(repeatNo)){
                  //已经存在相同的重卡组号
                  info("已经存在重卡组号:{}", repeatNo)
                }else{
                  error("重卡归并任务号：{}，人员编号：{}，{} 存在不相同的重卡组号", queryQue.oraSid, queryQue.keyId, personidRepeatNo._1)
                }
              case None =>
                if(personidRepeatNo._2 != null){
                  updateFingerRepeatNoByPersonid(queryQue.keyId,personidRepeatNo._2)
                }else{
                  //双方都不存在重卡组号，使用查询卡号作为新的重卡组号
                  repeatNoOpt = Some(queryQue.keyId)
                  updateFingerRepeatNoByPersonid(queryQue.keyId, queryQue.keyId)
                  updateFingerRepeatNoByPersonid(personidRepeatNo._1, queryQue.keyId)
                }
            }
          }else{
            error("查询{},候选卡信息不存在sid:{}", queryQue.oraSid, cand.getObjectId)
          }

        }else if(cand.getScore < hallMatcherConfig.autoCheck.denyScore){
          //小于自动否定分数
        }
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
    * 通过gafis_person表中的sid查询对应该条人员记录的人员编号和重卡组号。(参数sid来自候选)
    * @param sid 序号
    * @return
    */
  private def getPersonidAndFingerRepeatNoBySid(sid: Long): Option[(String,String)] ={
    var repeatNo:Option[(String,String)] = None
    val sql = "SELECT PERSONID, FINGERREPEATNO  from GAFIS_PERSON t where t.SID=?"
    JdbcDatabase.queryWithPsSetter(sql){
      ps=> ps.setLong(1, sid)
    }{
      rs=> repeatNo = Some(rs.getString("PERSONID"), rs.getString("FINGERREPEATNO"))
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
}
