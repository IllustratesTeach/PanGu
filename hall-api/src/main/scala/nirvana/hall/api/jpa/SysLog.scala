package nirvana.hall.api.jpa;
// Generated Jan 6, 2016 6:08:10 PM by hall orm generator 4.3.1.Final


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import nirvana.hall.orm.services.ActiveRecord;
import nirvana.hall.orm.services.ActiveRecordInstance;

/**
 * SysLog generated by hall orm 
 */
object SysLog extends ActiveRecordInstance[SysLog]

@Entity
@Table(name="SYS_LOG"
)
class SysLog extends ActiveRecord {


      @Id 
         @Column(name="PK_ID", unique=true, nullable=false, length=32)
     var pkId:java.lang.String =  _
          @Column(name="ADDRE_IP", length=50)
     var addreIp:java.lang.String =  _
          @Column(name="USER_NAME", length=50)
     var userName:java.lang.String =  _
          @Column(name="USER_ID", length=50)
     var userId:java.lang.String =  _
     @Temporal(TemporalType.TIMESTAMP)     @Column(name="CREATE_DATETIME", nullable=false, length=23)
     var createDatetime:java.util.Date =  _
          @Column(name="DELETE_FLAG", nullable=false)
     var deleteFlag:long =  _
          @Column(name="REMARK", length=200)
     var remark:java.lang.String =  _
          @Column(name="LOG_TYPE", length=50)
     var logType:java.lang.String =  _


	
    def this(pkId:java.lang.String, createDatetime:java.util.Date, deleteFlag:long) {
        this()
        this.pkId = pkId
        this.createDatetime = createDatetime
        this.deleteFlag = deleteFlag
    }
    def this(pkId:java.lang.String, addreIp:java.lang.String, userName:java.lang.String, userId:java.lang.String, createDatetime:java.util.Date, deleteFlag:long, remark:java.lang.String, logType:java.lang.String) {
       this()
       this.pkId = pkId
       this.addreIp = addreIp
       this.userName = userName
       this.userId = userId
       this.createDatetime = createDatetime
       this.deleteFlag = deleteFlag
       this.remark = remark
       this.logType = logType
    }
   




}


