package nirvana.hall.spark.services

import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.support.services.JdbcDatabase

/**
 * Created by wangjue on 2016/2/22.
 */
object PartitionErrorSaver {

  private lazy implicit val dataSource = SysProperties.getDataSource("gafis")

  def savePartitionErrors(parameter: NirvanaSparkConfig)(errors:Iterator[(String)]):Unit = {
    errors.foreach(item=> {
      val error = item
      //println("-----------------------------------"+error)
      saveErrorInfo(error)
    }
    )

  }

  //save error info to database
  private def saveErrorInfo(error:String): Unit = {
    val arr = error.split('|')
    val fpt_path = arr(0)//error file
    val keyId = arr(1)//key no
    var featureType = arr(5)// template or latent
    var direction = ""
    var position = ""
    var errorType = ""
    var cardId = ""
    if ("template".equals(featureType)) {
      val group = arr(2).split('_')
      errorType = arr(3)

      if (group!=null) {
        if (group.length == 3) {
          featureType = group(0)
          direction = group(1) //left or right hand
          position = group(2) //position
        } else if (group.length == 2) {//valid position
          featureType = group(0)
          position = group(1)
        }
      }
    } else
        cardId = arr(2)


    val errorDetail = arr(4)//error details


    val saveErrorSql = "INSERT INTO GAFIS_DAKU_ERROR(KEY_ID,FPT_PATH,FEATURE_TYPE,DIRECTION,POSITION,ERROR_TYPE,ERROR_DETAIL,CARDID,CREATE_TIME) VALUES(?,?,?,?,?,?,?,?,sysdate)"
    JdbcDatabase.update(saveErrorSql) { ps =>
      ps.setString(1,keyId)
      ps.setString(2,fpt_path)
      ps.setString(3,featureType)
      ps.setString(4,direction)
      ps.setString(5,position)
      ps.setString(6,errorType)
      ps.setString(7,errorDetail)
      ps.setString(8,cardId)
    }
  }
}



