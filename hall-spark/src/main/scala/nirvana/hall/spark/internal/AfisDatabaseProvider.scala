package nirvana.hall.spark.internal

import nirvana.hall.spark.services.FptPropertiesConverter.{LatentCaseConvert, LatentFingerConvert, LatentFingerFeatureConvert}
import nirvana.hall.spark.services.SysProperties
import nirvana.hall.support.services.JdbcDatabase
import org.slf4j.LoggerFactory

/**
  * Created by wangjue on 2017/12/28.
  */
object AfisDatabaseProvider {
  private val logger = LoggerFactory getLogger getClass
  lazy implicit val dataSource = SysProperties.getDataSource("afis")

  def saveLatentPalm: Unit ={
    JdbcDatabase.queryWithPsSetter("select t.palmid,t.palmimg,t.palmmnt from NORMALLP_LATPALM t"){ps =>}{rs =>
      val palmId = rs.getString("palmid")
      val caseId = palmId.substring(0,22)
      val seqNo = palmId.substring(22)
      val palmImg = rs.getBytes("palmimg")
      val palmMnt = rs.getBytes("palmmnt")

      val latentCase = new LatentCaseConvert
      latentCase.caseId = caseId

      val latentFinger = new LatentFingerConvert
      latentFinger.seqNo = seqNo
      latentFinger.fingerId = palmId
      latentFinger.caseId = caseId
      latentFinger.imgData = palmImg

      val latentFingerFeature = new LatentFingerFeatureConvert
      latentFingerFeature.fingerId = palmId
      latentFingerFeature.fingerMnt = palmMnt

      latentFinger.LatentFingerFeatures = latentFingerFeature :: Nil
      latentCase.latentFingers =  latentFinger :: Nil

      GafisPartitionRecordsBjsjSave.saveLatent(latentCase)
    }
  }
}

