package nirvana.hall.v70.gz.jpa

import javax.persistence.{Column, Entity, Id, Table}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
  * 查中登记
  */
object GafisCheckinInfo extends ActiveRecordInstance[GafisCheckinInfo]

@Entity
@Table(name = "GAFIS_CHECKIN_INFO")
class GafisCheckinInfo extends ActiveRecord {

  @Id
  @Column(name = "PK_ID", unique = true, nullable = false, length = 32)
  var pkId: java.lang.String = _
  @Column(name = "CODE", length = 25)
  var code: java.lang.String = _
  @Column(name = "TCODE", length = 25)
  var tcode: java.lang.String = _
  @Column(name = "QUERYTYPE", length = 1)
  var querytype: java.lang.Short = _
  @Column(name = "REGISTER_TIME", length = 23)
  var registerTime: java.util.Date = _
  @Column(name = "REGISTER_USER", length = 32)
  var registerUser: java.lang.String = _
  @Column(name = "REGISTER_ORG", length = 32)
  var registerOrg: java.lang.String = _
  @Column(name = "HITPOSSIBILITY", length = 3)
  var hitpossibility: java.lang.Short = _
  @Column(name = "PRIORITY", length = 3)
  var priority: java.lang.Short = _
  @Column(name = "REVIEW_STATUS", length = 2)
  var reviewStatus: java.lang.Short = _
  @Column(name = "REVIEW_BOUT", length = 1)
  var reviewBout: java.lang.Short = _
  @Column(name = "APPEAL_STATUS", length = 1)
  var appealStatus: java.lang.Short = _
  @Column(name = "CONFIRM_STATUS", length = 3)
  var confirmStatus: java.lang.Short = _
  @Column(name = "CONFIRM_USER", length = 32)
  var confirmUser: java.lang.String = _
  @Column(name = "CONFIRM_TIME", length = 23)
  var confirmTime: java.util.Date = _
  @Column(name = "QUERY_UUID", length = 32)
  var queryUUID: java.lang.String = _
  @Column(name = "REVIEW_ORG", length = 32)
  var reviewOrg: java.lang.String = _
  @Column(name = "RANK", length = 6)
  var rank: java.lang.Integer = _
  @Column(name = "FRACTION", length = 6)
  var fraction: java.lang.Integer = _
  @Column(name = "FGP", length = 20)
  var fgp: java.lang.String = _
  @Column(name = "CONFIRM_OPINION", length = 200)
  var confirmOption: java.lang.String = _
  @Column(name = "CARD_TYPE1")
  var cardType1: java.lang.Long= _
  @Column(name = "CARD_TYPE2")
  var cardType2: java.lang.Long= _
  @Column(name = "LAST_HANDLE_DATE", length = 23)
  var lastHandleDate: java.util.Date = _
  @Column(name = "OPERATETYPE")
  var operatetype: java.lang.Long= _
  @Column(name = "CK_SOURCE", length = 1)
  var ckSource: java.lang.Short = _
  @Column(name = "PASS_STATUS", length = 1)
  var passStatus: java.lang.Short = _
  @Column(name = "PERSON_CONTR_DELTAG", length = 1)
  var personContrDeltag: java.lang.String = _
  @Column(name = "REMOVEDELTAG", length = 1)
  var removedeltag: java.lang.String = _

  def this(pkId: java.lang.String){
    this()
    this.pkId = pkId
  }

}

