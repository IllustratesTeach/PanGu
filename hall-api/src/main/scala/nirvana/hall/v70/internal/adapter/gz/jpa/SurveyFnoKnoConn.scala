package nirvana.hall.v70.internal.adapter.gz.jpa

import javax.persistence.{Column, Entity, Id, Table}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance};

object SurveyFnoKnoConn extends ActiveRecordInstance[SurveyFnoKnoConn]

@Entity
@Table(name = "SURVEY_FNO_KNO_CONN"
)
class SurveyFnoKnoConn extends ActiveRecord {

  @Id
  @Column(name = "PHYSICAL_EVIDENCE_NO", unique = true, nullable = false, length = 32)
  var physicalEvidenceNo: java.lang.String = _
  @Column(name = "SCENE_SURVEY_ID", length = 23)
  var sceneSurveyId: java.lang.String = _


  def this(physicalEvidenceNo: java.lang.String) {
    this()
    this.physicalEvidenceNo = physicalEvidenceNo
  }

  def this(physicalEvidenceNo: java.lang.String, sceneSurveyId: java.lang.String) {
    this()
    this.physicalEvidenceNo = physicalEvidenceNo
    this.sceneSurveyId = sceneSurveyId

  }


}


