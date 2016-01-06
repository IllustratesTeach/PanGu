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
 * GafisGatherFlow generated by hall orm 
 */
object GafisGatherFlow extends ActiveRecordInstance[GafisGatherFlow]

@Entity
@Table(name="GAFIS_GATHER_FLOW"
)
class GafisGatherFlow extends ActiveRecord {


      @Id 
         @Column(name="PK_ID", unique=true, nullable=false, length=32)
     var pkId:java.lang.String =  _
          @Column(name="PERSON_ID", length=32)
     var personId:java.lang.String =  _
          @Column(name="GATHER_STATUS")
     var gatherStatus:java.lang.Long =  _
     @Temporal(TemporalType.TIMESTAMP)     @Column(name="GATHER_DATE", length=23)
     var gatherDate:java.util.Date =  _
          @Column(name="GATHERER_ID", length=32)
     var gathererId:java.lang.String =  _
          @Column(name="ORD")
     var ord:java.lang.Long =  _
          @Column(name="GATHER_NODE_ID", length=32)
     var gatherNodeId:java.lang.String =  _
          @Column(name="IS_SKIP")
     var isSkip:java.lang.Long =  _
          @Column(name="SKIP_REASON", length=1000)
     var skipReason:java.lang.String =  _
          @Column(name="SKIP_OPERATOR", length=32)
     var skipOperator:java.lang.String =  _
     @Temporal(TemporalType.TIMESTAMP)     @Column(name="SKIP_TIME", length=23)
     var skipTime:java.util.Date =  _


	
    def this(pkId:java.lang.String) {
        this()
        this.pkId = pkId
    }
    def this(pkId:java.lang.String, personId:java.lang.String, gatherStatus:java.lang.Long, gatherDate:java.util.Date, gathererId:java.lang.String, ord:java.lang.Long, gatherNodeId:java.lang.String, isSkip:java.lang.Long, skipReason:java.lang.String, skipOperator:java.lang.String, skipTime:java.util.Date) {
       this()
       this.pkId = pkId
       this.personId = personId
       this.gatherStatus = gatherStatus
       this.gatherDate = gatherDate
       this.gathererId = gathererId
       this.ord = ord
       this.gatherNodeId = gatherNodeId
       this.isSkip = isSkip
       this.skipReason = skipReason
       this.skipOperator = skipOperator
       this.skipTime = skipTime
    }
   




}


