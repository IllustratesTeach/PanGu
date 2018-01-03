package nirvana.hall.webservice.internal.survey.gz.recordmod

import java.sql.Timestamp
import java.util.{Date, UUID}
import javax.sql.DataSource
import nirvana.hall.api.internal.{DateConverter, JniLoaderUtil}
import nirvana.hall.api.services.remote.HallImageRemoteService
import nirvana.hall.api.services.{CaseInfoService, LPCardService, LPPalmService}
import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.webservice.internal.survey.gz.Constant
import nirvana.hall.webservice.internal.survey.gz.vo.{TimeConfig}
import nirvana.hall.webservice.services.survey.gz.SurveyRecordService

import scala.collection.mutable
import scala.collection.mutable.{ListBuffer}

/**
  * Created by ssj on 2017/11/16.
  */
class SurveyRecordImpl(hallImageRemoteService: HallImageRemoteService,
                       caseInfoService: CaseInfoService,
                       lPCardService: LPCardService,
                       lPPalmService: LPPalmService,
                       implicit val dataSource: DataSource) extends SurveyRecordService{

  JniLoaderUtil.loadExtractorJNI
  JniLoaderUtil.loadImageJNI


  override def isSleep(haiXinServerTime:Long):(Boolean,TimeConfig) = {
    var bStr = false
    val timeConfig = new TimeConfig
    getSurveyConfig match {
      case Some(t) =>
        val endTime = t.get("starttime").get.asInstanceOf[Date].getTime + t.get("increments").get.asInstanceOf[String].toLong*60*1000
        if(haiXinServerTime <= endTime){
          bStr = true
        }else{
          timeConfig.startTime = DateConverter.convertDate2String(new Date(t.get("starttime").get.asInstanceOf[Date].getTime),Constant.DATETIME_FORMAT)
          timeConfig.endTime = DateConverter.convertDate2String(new Date(endTime),Constant.DATETIME_FORMAT)
        }
      case _ => throw new Exception("time config empty or error")
    }
    (bStr,timeConfig)
  }

  /**
    * 获取现勘时间配置信息
    *
    * @return
    */
  private def getSurveyConfig(): Option[mutable.HashMap[String,Any]] =  {
    val sql = "SELECT * FROM SURVEY_CONFIG t WHERE t.flags = '1'"
    var map = new scala.collection.mutable.HashMap[String,Any]
    JdbcDatabase.queryWithPsSetter2(sql){ps=>
    }{rs=>
      if(rs.next()){
        map += ("starttime" -> rs.getTimestamp("START_TIME"))
        map += ("increments" -> rs.getString("INCREMENTS"))
      }
    }
    Some(map)
  }

  /**
    * 更新现勘时间配置表 开始时间字段
    *
    * @param endTime
    * @return
    */
  def updateSurveyConfig(endTime : Timestamp): Unit =  {
    val sql = s"UPDATE SURVEY_CONFIG SET START_TIME = ? ,UPDATE_TIME=sysdate"
    JdbcDatabase.update(sql){ps=>
      ps.setTimestamp(1,endTime)
    }
  }

  /**
    * 存入现勘现场数据入记录表操作
    * @param xcwzbh
    */
  def isSurvey(xcwzbh:String): Boolean={
    var bStr = false
    val sql = s"select count(1) num from SURVEY_RECORD where PHYSICAL_EVIDENCE_NO = ?"
    var num = 0
    JdbcDatabase.queryWithPsSetter2(sql){ps=>
      ps.setString(1,xcwzbh)
    }{rs=>
      if(rs.next()){
        num = rs.getInt("num")
      }
    }
    if(num>0){
      bStr = true
    }
    bStr
  }

  def saveSurveyRecord(casename:String,kno:String,xcwzbh:String): Unit={
    val sql = s"insert into SURVEY_RECORD(UUID,KNO,PHYSICAL_EVIDENCE_NO,CASENAME,STATE,INSERTTIME,UPDATETIME,RECEPTIONNO_STATE) " +
      s"values (?,?,?,?,0,sysdate,null,0)"
    JdbcDatabase.update(sql){ps=>
      ps.setString(1,UUID.randomUUID().toString.replace("-",""))
      ps.setString(2,kno)
      ps.setString(3,xcwzbh)
      ps.setString(4,casename)
    }
  }

  /**
    * 存储调用接口后的请求和返回入库操作
    * @param interfacetype
    * @param kno
    * @param xcwzbh
    * @param requestmsg
    * @param responsemsg
    * @param error
    */
  override def saveSurveyLogRecord(interfacetype: String, kno: String, xcwzbh: String, requestmsg: String, responsemsg: String, error: String): Unit = {
    val sql = s"INSERT INTO SURVEY_LOGGER_RECORD(UUID,INTERFACETYPE,KNO,PHYSICAL_EVIDENCE_NO,REQUESTMSG,RESPONSEMSG,ERRORMSG,CREATETIME) " +
      s"VALUES (?,?,?,?,?,?,?,sysdate)"
    JdbcDatabase.update(sql){ps=>
      ps.setString(1,UUID.randomUUID().toString.replace("-",""))
      ps.setString(2,interfacetype)
      ps.setString(3,kno)
      ps.setString(4,xcwzbh)
      ps.setString(5,requestmsg)
      ps.setString(6,responsemsg)
      ps.setString(7,error)
    }
  }

  /**
    * 获取survey_record现勘号
    * @param state
    * @return
    */
  def getSurveyRecordbyState(state: Int,batchSize:Int) : ListBuffer[String] ={
    val sql = "SELECT t.kno FROM survey_record t WHERE state=? AND ROWNUM <=? GROUP BY t.kno"
    val resultList = new mutable.ListBuffer[String]
    JdbcDatabase.queryWithPsSetter(sql){ ps =>
      ps.setInt(1,state)
      ps.setInt(2,batchSize)
    } {rs=>
      while (rs.next()) {
        resultList.append(rs.getString("kno"))
      }
    }
    resultList
  }

  /**
    * 根据现勘号查询对应的现勘现场数据
    * @param kno
    */
  def getWZBHbyKno(kno: String): ListBuffer[mutable.HashMap[String,Any]] = {
    val sql = s"SELECT * FROM SURVEY_RECORD t where t.kno = ? and state = 0 "
    val resultList = new mutable.ListBuffer[mutable.HashMap[String,Any]]
    JdbcDatabase.queryWithPsSetter2(sql){ps=>
      ps.setString(1,kno)
    }{rs=>
      while (rs.next()){
        val map = new scala.collection.mutable.HashMap[String,Any]
        map += ("xcwzbh" -> rs.getString("PHYSICAL_EVIDENCE_NO"))
        resultList.append(map)
      }

    }
    resultList
  }

  /**
    * 更新现勘现场数据状态，根据xcwzbh
    * @param state
    * @param xcwzbh
    */
  def updateRecordStateByXCWZBH(state: Int,xcwzbh : String): Unit = {
    val sql = s"UPDATE survey_record SET state = ?,updatetime = sysdate WHERE PHYSICAL_EVIDENCE_NO = ?"
    JdbcDatabase.update(sql){ps=>
      ps.setInt(1,state)
      ps.setString(2,xcwzbh)
    }
  }

  /**
    * 更新现勘现场数据状态，根据kno
    * @param reception_state
    * @param kno
    */
  def updateRecordStateByKno(reception_state: Int,kno : String): Unit = {
    val sql = s"UPDATE survey_record SET reception_state = ?,updatetime = sysdate WHERE kno = ?"
    JdbcDatabase.update(sql){ps=>
      ps.setInt(1,reception_state)
      ps.setString(2,kno)
    }
  }

  /**
    * 增加palm路径到记录表中
    * @param kno
    * @param sno
    * @param path
    */
  def savePalmpath(kno: String,sno: String,path: String): Unit = {
    val sql = s"INSERT INTO SURVEY_SAVE_PALM(UUID,KNO,SNO,PALMPATH,INSERTTIME) " +
      s"VALUES (?,?,?,?,sysdate)"
    JdbcDatabase.update(sql){ps=>
      ps.setString(1,UUID.randomUUID().toString.replace("-",""))
      ps.setString(2,kno)
      ps.setString(3,sno)
      ps.setString(4,path)
    }
  }

  /**
    * 增加接警编号到case表 中
    */
  def updateCasePeception(receptionid: String,kno: String): Unit = {
    val sql = s"update gafis_case " +
      s"set reception_no = ? where SCENE_SURVEY_ID = ?"
    JdbcDatabase.update(sql){ps=>
      ps.setString(1,receptionid)
      ps.setString(2,kno)
    }
  }

  /**
    * 获取现勘比中数据列表
    *
    * @return
    */
  def getSurveyHit(batchSize: Int): ListBuffer[mutable.HashMap[String,Any]] = {
    val sql = s"SELECT * " +
      s"FROM SURVEY_HITRESULT_RECORD t where state = 0 and rownum <= ?"
    val resultList = new mutable.ListBuffer[mutable.HashMap[String,Any]]

    JdbcDatabase.queryWithPsSetter2(sql){ps=>
      ps.setInt(1,batchSize)
    }{rs=>
      while (rs.next()){
        val map = new scala.collection.mutable.HashMap[String, Any]
        map += ("uuid" -> rs.getString("UUID"))
        map += ("kno" -> rs.getString("KNO"))
        map += ("xcwzbh" -> rs.getString("PHYSICAL_EVIDENCE_NO"))
        map += ("orasid" -> rs.getString("ORA_SID"))
        map += ("queryType" -> rs.getString("QUERY_TYPE"))
        resultList.append(map)
      }
    }
    resultList
  }

  /**
    * 更新比中关系列表状态，根据orasid
    */
  def updateSurveyHitState(state: String, uuid : String): Unit = {
    val sql = s"update survey_hitresult_record " +
      s"set state = ? where uuid = ?"
    JdbcDatabase.update(sql){ps=>
      ps.setInt(1,Integer.parseInt(state))
      ps.setString(2,uuid)
    }
  }
}
