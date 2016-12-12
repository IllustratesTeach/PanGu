package nirvana.hall.v62.migration

import stark.migration._

/**
 * 上报6.2相关的表
 * Created by zhoaoyang on 16/12/09.
 */
class Migrate_20161209151500_AdminAfisinit extends Migration{

  override def up(): Unit = {
    createTable("ADMIN_AFISINIT",Comment("adminAfisinit.ini配置表")){ t=>
      t.column("KEY",VarcharType,Limit(200),NotNull,Comment("键"),PrimaryKey)
      t.column("VALUE",VarcharType,Limit(200),NotNull,Comment("值"))
    }
  }

  override def down(): Unit = {
    dropTable("ADMIN_AFISINIT")
  }
}
