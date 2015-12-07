package nirvana.hall.api.migration

import monad.migration._

/**
 * make login name field as unique
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-02
 */
class Migrate_20151202105912_AlterLoginNameUnique  extends Migration{
  override def up(): Unit = {
    alterColumn("SYS_USER","LOGIN_NAME",VarcharType,Limit(60),NotNull,Unique)
  }

  override def down(): Unit = {
    alterColumn("SYS_USER","LOGIN_NAME",VarcharType,Limit(60),NotNull)
  }
}
