package nirvana.hall.api.config

/**
 *  查询db配置
 * @param dbId  查询库id
 * @param srcDB 特征来源库id
 * @param destDB 被查库id
 */
case class QueryDBConfig(dbId: Option[Short], srcDB: Option[Short], destDB: Option[Short])

/**
 * 查询任务信息
 * @param keyId   卡号
 * @param oraSid  任务号
 * @param queryType 查询类型
 * @param isPalm  是否掌纹
 */
class QueryQue(val keyId: String, val oraSid: Int, val queryType: Int, val isPalm: Boolean)
