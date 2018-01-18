package nirvana.hall.webservice.services.survey

import nirvana.hall.c.services.gloclib.survey.SURVEYHITRESULTRECORD

/**
  * Created by songpeng on 2018/1/16.
  */
trait SurveyHitResultRecordService {

  def addSurveyHitResultRecord(hitResult: SURVEYHITRESULTRECORD)

  def updateSurveyHitResultRecord(hitResult: SURVEYHITRESULTRECORD)

  def getSurveyHitResultRecordList(state: Short): Seq[SURVEYHITRESULTRECORD]
}
