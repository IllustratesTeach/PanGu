package nirvana.spark.services

import javax.sql.DataSource

import com.google.protobuf.ByteString
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.support.services.JdbcDatabase
import nirvana.spark.config.{DatabaseConfig, NirvanaSparkConfig}
import nirvana.spark.services.SparkFunctions.{StreamError, StreamEvent}

import scala.util.control.NonFatal

/**
 * partition records saver
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-02-09
 */
object PartitionRecordsSaver {
  var databaseConfig:Option[DatabaseConfig] = None
  lazy implicit val dataSource = buildMntDataSource()
  case class DbError(streamEvent: StreamEvent,message:String) extends StreamError(streamEvent) {
    override def getMessage: String = "S|"+message
  }
  def savePartitionRecords(parameter: NirvanaSparkConfig)(records:Iterator[(StreamEvent, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)]):Unit = {
      databaseConfig = Some(parameter.db)
      val flag= parameter.kafkaTopicName
      records.foreach { case (event, mnt ,bin) =>
        try {
          if (event.personId != null && event.personId.length > 0) { //save template
            var fagCase = 0
            /*val personIdOpt = queryPersonById(event.personId)
            if (personIdOpt.isEmpty) {
              try savePersonInfo(event.personId, event.path,flag)
              catch {
                case e: Throwable =>
                  if (e.toString.indexOf("PK_GAFIS_PERSON_PERSONID") == -1)
                    throw e
              }
            }*/

            if (mnt.stHead.bIsPlain == 1) //is plain finger
              fagCase = 1
            //save mnt
            savePersonFingerMntInfo(event.personId, fagCase, event.position.getNumber, mnt.toByteArray(), event.path, 0,flag)
            //save bin
            if (parameter.isExtractorBin)
              savePersonFingerMntInfo(event.personId, fagCase, event.position.getNumber, bin.toByteArray(), event.path ,4,flag)
          }

          if (event.caseId != null && event.caseId.length > 0) { //save latent
              val caseIdOpt = queryCaseInfoById(event.caseId)
              if(caseIdOpt.isEmpty){
                try saveCaseInfo(event.caseId)
                catch {
                  case e: Throwable =>
                    if (e.toString.indexOf("PK_PK_GAFIS_CASE") == -1)
                      throw e
                }
              }
              //save card info
              val cardIdOpt = queryCaseFingerInfoById(event.cardId)
              if(cardIdOpt.isEmpty){
                var seqNo = ""
                if (event.sendNo != null) {
                  seqNo = event.sendNo
                }
                try saveCaseFingerInfo(seqNo,event.cardId,event.caseId,event.path)
                catch {
                  case e: Throwable =>
                    if (e.toString.indexOf("PK_GAFIS_CASE_FINGER") == -1)
                      throw e
                }
              }
              //save mnt
              val hasMnt = updateCaseFingerMntInfo(event.cardId)
              if (hasMnt == 0)
                saveCaseFingerMntInfo(event.cardId,event.caseId,mnt.toByteArray())
          }
        } catch {
          case NonFatal(e) =>
            e.printStackTrace(System.err)
            SparkFunctions.reportError(parameter, DbError(event, e.toString))
        }
      }
  }

  //查询人员主表信息
  def queryPersonById(personId : String) : Option[String] = {
      JdbcDatabase.queryFirst("select personid from gafis_person where personid = ?"){ps =>
        ps.setString(1,personId)
      }{rs=>
        rs.getString("personid")
      }
  }

  //保存人员主表信息
  private def savePersonInfo(personId : String,path : String ,flag : String): Unit = {
    if ("FPT".equals(flag)) {
      val savePersonSql = "insert into gafis_person(personid,sid,seq,deletag,data_sources,fingershow_status,inputtime,gather_date,data_in,fpt_path) " +
        "values(?,gafis_person_sid_seq.nextval,gafis_person_seq.nextval,1,5,1,sysdate,sysdate,2,?)"
      JdbcDatabase.update(savePersonSql) { ps =>
        ps.setString(1, personId)
        ps.setString(2, path)
      }
    } else {
      val savePersonSql = "insert into gafis_person(personid,sid,seq,deletag,data_sources,fingershow_status,inputtime,gather_date) " +
        "values(?,gafis_person_sid_seq.nextval,gafis_person_seq.nextval,1,5,1,sysdate,sysdate)"
      JdbcDatabase.update(savePersonSql) { ps =>
        ps.setString(1, personId)
      }
    }
  }


  //保存人员指纹特征信息
  private def savePersonFingerMntInfo(personId : String,featureType : Integer,position : Integer,mnt : Array[Byte],path : String,groupId : Integer ,flag : String): Unit = {
    if ("FPT".equals(flag)) {
      val saveFingerSql: String = "insert into gafis_gather_finger(pk_id,person_id,fgp,fgp_case,group_id,lobtype,inputtime,seq,gather_data,fpt_path)" +
        "values(sys_guid(),?,?,?,?,2,sysdate,gafis_gather_finger_seq.nextval,?,?)"
      //保存指纹特征信息
      JdbcDatabase.update(saveFingerSql) { ps =>
        ps.setString(1, personId)
        ps.setInt(2, position)
        ps.setInt(3, featureType)
        ps.setInt(4, groupId)
        ps.setBytes(5, mnt)
        ps.setString(6, path)
      }
    } else {
      val saveFingerSql: String = "insert into gafis_gather_finger_temp(pk_id,person_id,fgp,fgp_case,group_id,lobtype,inputtime,seq,gather_data)" +
        "values(sys_guid(),?,?,?,?,2,sysdate,gafis_gather_finger_seq.nextval,?)"
      //保存指纹特征信息
      JdbcDatabase.update(saveFingerSql) { ps =>
        ps.setString(1, personId)
        ps.setInt(2, position)
        ps.setInt(3, featureType)
        ps.setInt(4, groupId)
        ps.setBytes(5, mnt)
      }
    }
  }

  //查询案件信息
  def queryCaseInfoById(caseId : String) : Option[String] = {
    JdbcDatabase.queryFirst("select t.case_id from GAFIS_CASE t where t.case_id = ?"){ps =>
      ps.setString(1,caseId)
    }{rs=>
      rs.getString("case_id")
    }
  }

  //保存案件信息
  def saveCaseInfo(caseId : String): Unit = {
    val saveCaseSql = "insert into GAFIS_CASE(CASE_ID,Inputtime,DELETAG,Case_Source,Is_Checked) values(?,sysdate,1,5,0)"
    JdbcDatabase.update(saveCaseSql) { ps =>
      ps.setString(1, caseId)
    }
  }

  //查询现场指纹信息
  def queryCaseFingerInfoById(fingerId : String) : Option[String] = {
    JdbcDatabase.queryFirst("select t.finger_id from GAFIS_CASE_FINGER t where t.finger_id = ?"){ps =>
      ps.setString(1,fingerId)
    }{rs=>
      rs.getString("finger_id")
    }
  }

  //保存现场指纹信息
  def saveCaseFingerInfo(seqNo : String , fingerId : String , caseId : String , fptPath : String): Unit = {
    val saveCaseFingerSql = "insert into GAFIS_CASE_FINGER(SEQ_NO,FINGER_ID,CASE_ID,Finger_Img,INPUTTIME,Deletag,Data_In,Data_Matcher,Sid,Fpt_Path)" +
      "values(?,?,?,EMPTY_BLOB(),sysdate,1,2,1,GAFIS_CASE_SID_SEQ.NEXTVAL,?)"
    JdbcDatabase.update(saveCaseFingerSql) { ps =>
      ps.setString(1, seqNo)
      ps.setString(2, fingerId)
      ps.setString(3, caseId)
      ps.setString(4, fptPath)

    }
  }

  //修改现场指纹特征信息(判断特征是否已经存在)
  def updateCaseFingerMntInfo(fingerId : String): Int = {
    val updateCaseFingerMntSql = "update GAFIS_CASE_FINGER_MNT t set t.modifiedtime = sysdate where t.finger_id = ? and t.is_main_mnt = 1"
    JdbcDatabase.update(updateCaseFingerMntSql) { ps =>
      ps.setString(1, fingerId)
    }
  }

  //保存现场指纹特征信息
  def saveCaseFingerMntInfo(fingerId : String , caseId : String,mnt : Array[Byte]): Unit = {
    val saveCaseFingerMntSql = "insert into GAFIS_CASE_FINGER_MNT(PK_ID,Finger_Id,Capture_Method,FINGER_MNT,Is_Main_Mnt,Inputtime)" +
      "values(sys_guid(),?,'U',?,1,sysdate)"
    JdbcDatabase.update(saveCaseFingerMntSql) { ps =>
      ps.setString(1, fingerId)
      ps.setBytes(2,mnt)
    }
  }

  def buildMntDataSource(): DataSource = {
    databaseConfig match{
      case Some(config) =>
        val hikariConfig = new HikariConfig()
        //针对oracle特别处理
        hikariConfig.setConnectionTestQuery("select 1 from dual")
        hikariConfig.setDriverClassName(config.driver)

        /*hikariConfig.setJdbcUrl(System.getProperty("daku.mnt.jdbc.url"))
      hikariConfig.setUsername(System.getProperty("daku.mnt.jdbc.user"))
      hikariConfig.setPassword(System.getProperty("daku.mnt.jdbc.pass"))*/

        hikariConfig.setJdbcUrl(config.url)
        hikariConfig.setUsername(config.user)
        hikariConfig.setPassword(config.password)
        //设置自动提交事务
        hikariConfig.setAutoCommit(false)

        hikariConfig.addDataSourceProperty("cachePrepStmts", "true")
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250")
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
        hikariConfig.setMaximumPoolSize(20)
        //hikariConfig.addDataSourceProperty("maximumPoolSize", "5")

        new HikariDataSource(hikariConfig)
      case None=>
      throw new IllegalStateException("database configuration not set")
    }
  }

}
