package nirvana.hall.matcher.service

trait AutoExtractFeatureService {

  /**
    * 重新提取特征
    * @param personid 人员编号
    */
  def reExtractFeature(personid: String)
}
