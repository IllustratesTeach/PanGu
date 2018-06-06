package nirvana.hall.database.config

import monad.core.config.DatabaseConfig
import stark.activerecord.config.ActiveRecordConfigSupport

class HallDatabaseConfig extends ActiveRecordConfigSupport{
  var db: DatabaseConfig = new DatabaseConfig()
}
