package nirvana.hall.v70.internal

import nirvana.hall.api.HallApiConstants
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.v62.internal.V62Facade
import org.apache.tapestry5.json.JSONObject

/**
 * http请求头信息工具类
 * 用于同步GafisSyncConfig.config解析
 * 用于发远程查询GafisSyncConfig.config解析
 */
object HttpHeaderUtils {
  final val DB_KEY_TPLIB = "TPLibDB"
  final val DB_KEY_LPLIB = "LPLibDB"
  final val DB_KEY_QUERYLIB = "QueryLibDB"

  /**
   * 获取v62的请求头部信息
   * @return
   */
  def getV62HeaderMap(config: String): Map[String, String] ={
    val map = scala.collection.mutable.Map[String,String]()
    val json  = new JSONObject(config)
    if(json.has("host")){
      val value = json.getString("host")
      map.+=(V62Facade.X_V62_HOST_HEAD -> value)
    }
    if(json.has("port")){
      val value = json.getString("port")
      map.+=(V62Facade.X_V62_PORT_HEAD -> value)
    }
    if(json.has("user")){
      val value = json.getString("user")
      map.+=(V62Facade.X_V62_USER_HEAD -> value)
    }
    if(json.has("password")){
      val value = json.getString("password")
      map.+=(V62Facade.X_V62_PASSWORD_HEAD -> value)
    }

    map.toMap
  }

  def getHeaderMapOfDBID(config: String, key: String): Map[String, String]={
    val json = new JSONObject(config)
    if(json.has(key)){
      val dbId = json.getString(key)
      Map(HallApiConstants.HTTP_HEADER_DBID -> dbId)
    }else{
      Map()
    }
  }

  /**
   * 获取请求头信息，包含查询请求的数据库信息
   * @param config
   * @param matchType
   * @return
   */
  def getHeaderMapOfQueryConfig(config: String, matchType: MatchType): Map[String, String] ={
    val map = scala.collection.mutable.Map[String,String]()
    val json = new JSONObject(config)
    //解析数据头信息
    map.++=(HttpHeaderUtils.getV62HeaderMap(config))
    if(json.has("QueryLibDB")){
      val dbId = json.getString("QueryLibDB")
      map.+=(HallApiConstants.HTTP_HEADER_DBID-> dbId)
    }
    matchType match {
      case MatchType.FINGER_TT | MatchType.FINGER_LT=>
        if(json.has("TPLibDB")){
          val dbId = json.getString("TPLibDB")
          map.+=(HallApiConstants.HTTP_HEADER_QUERY_DEST_DBID -> dbId)
        }
      case MatchType.FINGER_TL | MatchType.FINGER_LL =>
        if(json.has("LPLibDB")){
          val dbId = json.getString("LPLibDB")
          map.+=(HallApiConstants.HTTP_HEADER_QUERY_DEST_DBID -> dbId)
        }
      case other =>
    }

    map.toMap
  }
  def getHeaderMapOfTPLib(config: String): Map[String, String] ={
    val map = scala.collection.mutable.Map[String,String]()
    val json = new JSONObject(config)
    //解析数据头信息
    map.++=(HttpHeaderUtils.getV62HeaderMap(config))
    if(json.has("QueryLibDB")){
      val dbId = json.getString("QueryLibDB")
      map.+=(HallApiConstants.HTTP_HEADER_DBID-> dbId)
    }

    map.toMap
  }

}
