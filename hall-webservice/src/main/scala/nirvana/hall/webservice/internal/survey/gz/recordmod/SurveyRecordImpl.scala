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
    * @param kno
    * @param sno
    * @param cardtype
    * @param casename
    */

  def saveSurveySnoRecord(kno:String,sno:String,cardtype:String,casename:String): Unit={
    val sql = s"insert into SURVEY_SNO_RECORD(UUID,KNO,SNO,CARDTYPE,CASENAME,BACKSTATE,STATE,INSERTTIME,UPDATETIME) " +
      s"values (?,?,?,?,?,0,0,sysdate,null)"
    JdbcDatabase.update(sql){ps=>
      ps.setString(1,UUID.randomUUID().toString.replace("-",""))
      ps.setString(2,kno)
      ps.setString(3,sno)
      ps.setString(4,cardtype)
      ps.setString(5,casename)
    }
  }

  /**
    * 判断现勘号再现勘记录表中是否存在
    * @param kno
    * @return
    */
  def isKno(kno:String): Boolean={
    var bStr = false
    val sql = s"select count(1) num from SURVEY_XKCODE_RECORD where kno = ?"
    var num = 0
    JdbcDatabase.queryWithPsSetter2(sql){ps=>
      ps.setString(1,kno)
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

  /**
    * 存储现勘号记录到现勘记录表中
    * @param kno
    */
  def saveSurveyKnoRecord(kno:String): Unit={
    val sql = s"insert into SURVEY_XKCODE_RECORD(UUID,KNO,STATE,INSERTTIME,UPDATETIME) " +
      s"values (?,?,0,sysdate,null)"
    JdbcDatabase.update(sql){ps=>
      ps.setString(1,UUID.randomUUID().toString.replace("-",""))
      ps.setString(2,kno)
    }
  }

  /**
    * 存储调用接口后的请求和返回入库操作
    * @param interfacetype
    * @param kno
    * @param Sno
    * @param requestmsg
    * @param responsemsg
    * @param error
    */
  override def saveSurveyLogRecord(interfacetype: String, kno: String, Sno: String, requestmsg: String, responsemsg: String, error: String): Unit = {
    val sql = s"INSERT INTO SURVEY_LOGGER_RECORD(UUID,INTERFACETYPE,KNO,SNO,REQUESTMSG,RESPONSEMSG,ERRORMSG,CREATETIME) " +
              s"VALUES (?,?,?,?,?,?,?,sysdate)"
    JdbcDatabase.update(sql){ps=>
      ps.setString(1,UUID.randomUUID().toString.replace("-",""))
      ps.setString(2,interfacetype)
      ps.setString(3,kno)
      ps.setString(4,Sno)
      ps.setString(5,requestmsg)
      ps.setString(6,responsemsg)
      ps.setString(7,error)
    }
  }

  /**
    * 根据状态，获取不同的现勘号业务
    * @param state
    * @return
    */
  def getXkcodebyState(state: Int,batchSize:Int) : ListBuffer[String] ={
    val sql = "SELECT t.kno FROM survey_xkcode_record t WHERE state=? AND ROWNUM <=?"
    val resultList = new mutable.ListBuffer[String]
    JdbcDatabase.queryWithPsSetter(sql){ ps =>
      ps.setInt(1,state)
      ps.setInt(2,batchSize)
    } {rs=>
      resultList.append(rs.getString("kno"))
    }
    resultList
  }

  /**
    * 更新现勘表状态根据kno
    * @param state
    * @param kno
    */
  def updateXkcodeState(state: Int,kno : String): Unit =  {
    val sql = s"update survey_xkcode_record " +
      s"set state = ?,updatetime = sysdate where kno = ?"
    JdbcDatabase.update(sql){ps=>
      ps.setInt(1,state)
      ps.setString(2,kno)
    }
  }

  /**
    * 根据现勘号查询对应的现勘现场数据
    * @param kno
    */
  def getSurveySnoRecord(kno: String): ListBuffer[mutable.HashMap[String,Any]] = {
    val sql = s"SELECT * FROM SURVEY_SNO_RECORD t where t.kno = ? and state = 0 "
    val resultList = new mutable.ListBuffer[mutable.HashMap[String,Any]]

    JdbcDatabase.queryWithPsSetter2(sql){ps=>
      ps.setString(1,kno)
    }{rs=>
      var map = new scala.collection.mutable.HashMap[String,Any]
      map += ("sno" -> rs.getString("SNO"))
      map += ("cardtype" -> rs.getString("CARDTYPE"))
      map += ("casename" -> rs.getString("CASENAME"))
      resultList.append(map)
    }
    resultList
  }

  /**
    * 更新现勘现场数据状态，根据sno
    * @param state
    * @param sno
    */
  def updateSnoState(state: Int,sno : String): Unit = {
    val sql = s"UPDATE survey_sno_record SET state = ?,updatetime = sysdate WHERE sno = ?"
    JdbcDatabase.update(sql){ps=>
      ps.setInt(1,state)
      ps.setString(2,sno)
    }
  }

  /**
    * 增加接警编号到case表 中
    */
  def updateCasePeception(peception: String,kno: String): Unit = {
    val sql = s"update gafis_case " +
      s"set peception_no = ? where SCENE_SURVEY_ID = ?"
    JdbcDatabase.update(sql){ps=>
      ps.setString(1,peception)
      ps.setString(2,kno)
    }
  }
}
