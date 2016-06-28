package nirvana.hall.v70.migration

import stark.migration._

/**
 * 上报6.2相关的表
 * Created by songpeng on 15/12/4.
 */
class Migrate_20160618150600_SyncConfig extends Migration{
  override def up(): Unit = {
    createTable("SYNC_CONFIG",Comment("同步，记录同步的seq信息")){ t=>
      t.column("PK_ID",VarcharType,Limit(32),NotNull,Comment("主键"),PrimaryKey)
      t.column("NAME",VarcharType,Limit(100),Nullable,Comment("服务器名称"))
      t.column("URL",VarcharType,Limit(100),Nullable,Comment("同步服务器的URL"))
      t.column("CONFIG",VarcharType,Limit(100),Nullable,Comment("配置json"))
      t.column("TIMESTAMP",BigintType,Limit(15),NotNull,Comment("同步时间戳"))
      t.column("DELETAG",VarcharType,Limit(1),NotNull,Comment("删除标记，0：删除"))
    }
    createTable("REMOTE_QUERY_CONFIG",Comment("7向6发送远程查询配置")){ t=>
      t.column("PK_ID",VarcharType,Limit(32),NotNull,Comment("主键"),PrimaryKey)
      t.column("NAME",VarcharType,Limit(100),Nullable,Comment("服务器名称"))
      t.column("URL",VarcharType,Limit(100),Nullable,Comment("同步服务器的URL"))
      t.column("CONFIG",VarcharType,Limit(100),Nullable,Comment("配置json"))
      t.column("DELETAG",VarcharType,Limit(1),NotNull,Comment("删除标记，0：删除"))
    }
  }

  override def down(): Unit = {
    dropTable("SYNC_CONFIG")
    dropTable("REMOTE_QUERY_CONFIG")
  }
}
