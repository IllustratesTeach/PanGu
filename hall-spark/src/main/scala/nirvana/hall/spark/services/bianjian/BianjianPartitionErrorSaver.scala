package nirvana.hall.spark.services.bianjian

import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.SysProperties
import nirvana.hall.support.services.JdbcDatabase

/**
 * Created by wangjue on 2016/2/22.
 */
object BianjianPartitionErrorSaver {

  private lazy implicit val dataSource = SysProperties.getDataSource("bianjian")

  def savePartitionErrors(parameter: NirvanaSparkConfig)(errors:Iterator[(String)]):Unit = {
    errors.foreach(item=> {
      val error = item
      println("-----------------------------------"+error)
      saveErrorInfo(error)
    }
    )

  }

  //save error info to database
  private def saveErrorInfo(error:String): Unit = {
    val arr = error.split('|')
    val fpt_path = arr(0)//error file
    val keyId = arr(1)//key no
    val group = arr(2).split('_')
    val errorType = arr(3)
    var featureType = ""
    var direction = ""
    var position = ""
    if (!errorType.equals("R")) {
      featureType = group(0) //template or case
      direction = group(1) //left or right hand
      position = group(2) //position
    }

    val errorDetail = arr(4)//error details


    val saveErrorSql = "INSERT INTO GAFIS_ERROR(csid,ERROR_TYPE,ERROR_DETAIL) VALUES(?,?,?)"
    JdbcDatabase.update(saveErrorSql) { ps =>
      ps.setString(1,keyId)
      ps.setString(2,errorType)
      ps.setString(3,errorDetail)
    }
  }

}



