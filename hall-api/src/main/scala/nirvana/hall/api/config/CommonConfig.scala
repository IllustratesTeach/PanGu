package nirvana.hall.api.config

/**
 * Created by songpeng on 16/6/28.
 */
case class DBConfig(dbId: Either[Short, String], tableId: Option[Short])
