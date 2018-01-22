package nirvana.hall.v70.internal.adapter.gz.jpa

import javax.persistence.{Column, Entity, Id, Table}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
  * Created by yuchen on 2017/12/27.
  */
object GafisCheckinReview extends ActiveRecordInstance[GafisCheckinReview]

@Entity
@Table(name = "gafis_checkin_review")
class GafisCheckinReview extends ActiveRecord{
  @Id
  @Column(name = "PK_ID", unique = true, nullable = false, length = 32)
  var pkId: java.lang.String = _
  @Column(name = "CHECKIN_ID",length = 32)
  var checkInId: java.lang.String = _
  @Column(name = "RESULT",length = 1)
  var result: java.lang.Short = _
  @Column(name = "REVIEW_ORG",length = 32)
  var reviewOrg: java.lang.String = _
  @Column(name = "REVIEW_USER",length = 32)
  var reviewUser: java.lang.String = _
  @Column(name = "REASON",length = 300)
  var reason: java.lang.String = _
  @Column(name = "REVIEW_TIME",length = 23)
  var reviewTime: java.util.Date = _

  def this(pkId: java.lang.String){
    this()
    this.pkId = pkId
  }

}
