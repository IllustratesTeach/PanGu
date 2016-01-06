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
 * SysUserRole generated by hall orm 
 */
object SysUserRole extends ActiveRecordInstance[SysUserRole]

@Entity
@Table(name="SYS_USER_ROLE"
)
class SysUserRole extends ActiveRecord {


      @Id 
         @Column(name="PK_ID", unique=true, nullable=false, length=32)
     var pkId:java.lang.String =  _
          @Column(name="USER_ID", nullable=false, length=32)
     var userId:java.lang.String =  _
          @Column(name="ROLE_ID", nullable=false, length=32)
     var roleId:java.lang.String =  _
          @Column(name="REMARK", length=300)
     var remark:java.lang.String =  _
          @Column(name="CREATE_USER", nullable=false, length=60)
     var createUser:java.lang.String =  _
     @Temporal(TemporalType.TIMESTAMP)     @Column(name="CREATE_DATETIME", length=23)
     var createDatetime:java.util.Date =  _
          @Column(name="UPDATE_USER", length=60)
     var updateUser:java.lang.String =  _
     @Temporal(TemporalType.TIMESTAMP)     @Column(name="UPDATE_DATETIME", length=23)
     var updateDatetime:java.util.Date =  _
          @Column(name="CREATE_USER_ID", length=32)
     var createUserId:java.lang.String =  _
          @Column(name="UPDATE_USER_ID", length=32)
     var updateUserId:java.lang.String =  _
          @Column(name="DEPART_CODE", length=12)
     var departCode:java.lang.String =  _


	
    def this(pkId:java.lang.String, userId:java.lang.String, roleId:java.lang.String, createUser:java.lang.String) {
        this()
        this.pkId = pkId
        this.userId = userId
        this.roleId = roleId
        this.createUser = createUser
    }
    def this(pkId:java.lang.String, userId:java.lang.String, roleId:java.lang.String, remark:java.lang.String, createUser:java.lang.String, createDatetime:java.util.Date, updateUser:java.lang.String, updateDatetime:java.util.Date, createUserId:java.lang.String, updateUserId:java.lang.String, departCode:java.lang.String) {
       this()
       this.pkId = pkId
       this.userId = userId
       this.roleId = roleId
       this.remark = remark
       this.createUser = createUser
       this.createDatetime = createDatetime
       this.updateUser = updateUser
       this.updateDatetime = updateDatetime
       this.createUserId = createUserId
       this.updateUserId = updateUserId
       this.departCode = departCode
    }
   




}


