package nirvana.hall.webservice.internal.survey.gafis62

import nirvana.hall.v62.config.HallV62Config
import org.apache.tapestry5.json.JSONObject

/**
  * Created by T430 on 2/1/2018.
  */
object FPT50HandprintUtils {

  /**
    * 根据配置信息修改当前62配置信息
    *
    * @param config
    * @param hallV62Config
    */
   def modifyHallV62ConfigAppSvr(config: String, hallV62Config: HallV62Config): Unit = {
    val configJson = new JSONObject(config)
    if (configJson.has("host") && configJson.has("port") && configJson.has("user") && configJson.has("password")) {
      hallV62Config.appServer.host = configJson.getString("host")
      hallV62Config.appServer.port = configJson.getInt("port")
      hallV62Config.appServer.user = configJson.getString("user")
      hallV62Config.appServer.password = configJson.getString("password")
    }
  }

}
