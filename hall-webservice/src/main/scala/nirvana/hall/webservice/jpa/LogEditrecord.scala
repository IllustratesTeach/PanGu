package nirvana.hall.webservice.jpa

// Generated 2018-7-24 14:53:35 by Stark Activerecord generator 4.3.1.Final


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import stark.activerecord.services.ActiveRecord;
import stark.activerecord.services.ActiveRecordInstance;

/**
 * LogEditrecord generated by stark activerecord generator
 */
object LogEditrecord extends ActiveRecordInstance[LogEditrecord]

@Entity
@Table(name="LOG_EDITRECORD"
)
class LogEditrecord extends ActiveRecord {


      @Id 
         @Column(name="PK_ID", unique=true, nullable=false, length=32)
     var pkId:java.lang.String =  _
          @Column(name="OLD_XCZW_ASJBH", length=30)
     var oldXczwAsjbh:java.lang.String =  _
          @Column(name="OLD_XCZW_YSXT_ASJBH", length=23)
     var oldXczwYsxtAsjbh:java.lang.String =  _
          @Column(name="OLD_XCZW_XCKYBH", length=23)
     var oldXczwXckybh:java.lang.String =  _
          @Column(name="OLD_XCZW_YSXT_XCZZHWBH", length=30)
     var oldXczwYsxtXczzhwbh:java.lang.String =  _
          @Column(name="OLD_XCZW_XCWZBH", length=30)
     var oldXczwXcwzbh:java.lang.String =  _
          @Column(name="OLD_XCZW_XCZZHWKBH", length=23)
     var oldXczwXczzhwkbh:java.lang.String =  _
          @Column(name="OLD_NYZW_YSXT_ASJXGRYBH", length=23)
     var oldNyzwYsxtAsjxgrybh:java.lang.String =  _
          @Column(name="OLD_NYZW_JZRYBH", length=23)
     var oldNyzwJzrybh:java.lang.String =  _
          @Column(name="OLD_NYZW_ASJXGRYBH", length=23)
     var oldNyzwAsjxgrybh:java.lang.String =  _
          @Column(name="OLD_NYZW_ZZHWKBH", length=23)
     var oldNyzwZzhwkbh:java.lang.String =  _
          @Column(name="OLD_NYZW_ZZHWDM", length=2)
     var oldNyzwZzhwdm:java.lang.String =  _
     @Temporal(TemporalType.DATE)     @Column(name="MODIFYTIME", length=10)
     var modifytime:java.util.Date =  _
          @Column(name="NEW_XCZW_ASJBH", length=30)
     var newXczwAsjbh:java.lang.String =  _
          @Column(name="NEW_XCZW_YSXT_ASJBH", length=23)
     var newXczwYsxtAsjbh:java.lang.String =  _
          @Column(name="NEW_XCZW_XCKYBH", length=23)
     var newXczwXckybh:java.lang.String =  _
          @Column(name="NEW_XCZW_YSXT_XCZZHWBH", length=30)
     var newXczwYsxtXczzhwbh:java.lang.String =  _
          @Column(name="NEW_XCZW_XCWZBH", length=30)
     var newXczwXcwzbh:java.lang.String =  _
          @Column(name="NEW_XCZW_XCZZHWKBH", length=23)
     var newXczwXczzhwkbh:java.lang.String =  _
          @Column(name="NEW_NYZW_YSXT_ASJXGRYBH", length=23)
     var newNyzwYsxtAsjxgrybh:java.lang.String =  _
          @Column(name="NEW_NYZW_JZRYBH", length=23)
     var newNyzwJzrybh:java.lang.String =  _
          @Column(name="NEW_NYZW_ASJXGRYBH", length=23)
     var newNyzwAsjxgrybh:java.lang.String =  _
          @Column(name="NEW_NYZW_ZZHWKBH", length=23)
     var newNyzwZzhwkbh:java.lang.String =  _
          @Column(name="NEW_NYZW_ZZHWDM", length=2)
     var newNyzwZzhwdm:java.lang.String =  _


	
    def this(pkId:java.lang.String) {
        this()
        this.pkId = pkId
    }
    def this(pkId:java.lang.String, oldXczwAsjbh:java.lang.String, oldXczwYsxtAsjbh:java.lang.String, oldXczwXckybh:java.lang.String, oldXczwYsxtXczzhwbh:java.lang.String, oldXczwXcwzbh:java.lang.String, oldXczwXczzhwkbh:java.lang.String, oldNyzwYsxtAsjxgrybh:java.lang.String, oldNyzwJzrybh:java.lang.String, oldNyzwAsjxgrybh:java.lang.String, oldNyzwZzhwkbh:java.lang.String, oldNyzwZzhwdm:java.lang.String, modifytime:java.util.Date, newXczwAsjbh:java.lang.String, newXczwYsxtAsjbh:java.lang.String, newXczwXckybh:java.lang.String, newXczwYsxtXczzhwbh:java.lang.String, newXczwXcwzbh:java.lang.String, newXczwXczzhwkbh:java.lang.String, newNyzwYsxtAsjxgrybh:java.lang.String, newNyzwJzrybh:java.lang.String, newNyzwAsjxgrybh:java.lang.String, newNyzwZzhwkbh:java.lang.String, newNyzwZzhwdm:java.lang.String) {
       this()
       this.pkId = pkId
       this.oldXczwAsjbh = oldXczwAsjbh
       this.oldXczwYsxtAsjbh = oldXczwYsxtAsjbh
       this.oldXczwXckybh = oldXczwXckybh
       this.oldXczwYsxtXczzhwbh = oldXczwYsxtXczzhwbh
       this.oldXczwXcwzbh = oldXczwXcwzbh
       this.oldXczwXczzhwkbh = oldXczwXczzhwkbh
       this.oldNyzwYsxtAsjxgrybh = oldNyzwYsxtAsjxgrybh
       this.oldNyzwJzrybh = oldNyzwJzrybh
       this.oldNyzwAsjxgrybh = oldNyzwAsjxgrybh
       this.oldNyzwZzhwkbh = oldNyzwZzhwkbh
       this.oldNyzwZzhwdm = oldNyzwZzhwdm
       this.modifytime = modifytime
       this.newXczwAsjbh = newXczwAsjbh
       this.newXczwYsxtAsjbh = newXczwYsxtAsjbh
       this.newXczwXckybh = newXczwXckybh
       this.newXczwYsxtXczzhwbh = newXczwYsxtXczzhwbh
       this.newXczwXcwzbh = newXczwXcwzbh
       this.newXczwXczzhwkbh = newXczwXczzhwkbh
       this.newNyzwYsxtAsjxgrybh = newNyzwYsxtAsjxgrybh
       this.newNyzwJzrybh = newNyzwJzrybh
       this.newNyzwAsjxgrybh = newNyzwAsjxgrybh
       this.newNyzwZzhwkbh = newNyzwZzhwkbh
       this.newNyzwZzhwdm = newNyzwZzhwdm
    }
}


