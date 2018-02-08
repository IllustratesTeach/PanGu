package nirvana.hall.webservice.services.survey

import java.io.File
import javax.activation.{DataHandler, FileDataSource}

import nirvana.hall.api.internal.fpt.FPT5Utils
import nirvana.hall.c.services.gloclib.survey.SURVEYHITRESULTRECORD
import org.apache.commons.io.FileUtils

/**
  * Created by songpeng on 2018/1/16.
  */
trait SurveyHitResultRecordService {

  /**
    * 添加现勘比中信息
    * @param hitResult
    */
  def addSurveyHitResultRecord(hitResult: SURVEYHITRESULTRECORD)

  /**
    * 获取现堪比中信息
    * @param hitResult
    */
  def updateSurveyHitResultRecord(hitResult: SURVEYHITRESULTRECORD)

  /**
    * 获取现勘比中信息
    * @param state 状态survey.SURVEY_STATE_xx
    * @param limit 数量
    * @return
    */
  def getSurveyHitResultRecordList(state: Byte, limit: Int): Seq[SURVEYHITRESULTRECORD]

  /**
    * 根据hitResult获取FPT5比对关系
    * @param hitResult SURVEYHITRESULTRECORD
    * @return
    */
  def getDataHandlerOfLtOrLlHitResultPackage(hitResult: SURVEYHITRESULTRECORD): Option[DataHandler]

  protected def getZipDataHandlerOfString(xml: String, fileName: String, path: String): DataHandler = {
    val xmlFile = new File(path + File.separator + fileName + ".xml")
    val zipFilePath = path + File.separator + fileName + ".zip"
    FileUtils.writeByteArrayToFile(xmlFile, xml.getBytes())
    FPT5Utils.zipFile(xmlFile, zipFilePath)

    new DataHandler(new FileDataSource(zipFilePath))
  }
}
