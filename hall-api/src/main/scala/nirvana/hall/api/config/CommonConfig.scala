package nirvana.hall.api.config

/**
 *  查询db配置
 * @param dbId  查询库id
 * @param srcDB 特征来源库id
 * @param destDB 被查库id
 */
case class QueryDBConfig(dbId: Option[Short], srcDB: Option[Short], destDB: Option[Short])
