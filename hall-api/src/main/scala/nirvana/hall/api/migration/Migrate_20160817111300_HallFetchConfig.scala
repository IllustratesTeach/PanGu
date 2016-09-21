package nirvana.hall.api.migration

import stark.migration._

/**
 * 上报6.2相关的表
 * Created by songpeng on 15/12/4.
 */
class Migrate_20160817111300_HallFetchConfig extends Migration{
  override def up(): Unit = {
    createTable("HALL_FETCH_CONFIG",Comment("同步抓取配置表")){ t=>
      t.column("PK_ID",VarcharType,Limit(32),NotNull,Comment("主键"),PrimaryKey)
      t.column("NAME",VarcharType,Limit(100),Nullable,Comment("名称"))
      t.column("URL",VarcharType,Limit(100),Nullable,Comment("同步服务器的URL"))
      t.column("TYP",VarcharType,Limit(32),Nullable,Comment("同步类型"))
      t.column("DBID",VarcharType,Limit(32),Nullable,Comment("目标库dbid"))
      t.column("DEST_DBID",VarcharType,Limit(32),Nullable,Comment("存储本地的dbid"))
      t.column("WRITE_STRATEGY",VarcharType,Limit(1000),Nullable,Comment("写入策略"))
      t.column("CONFIG",VarcharType,Limit(1000),Nullable,Comment("写入策略"))
      t.column("SEQ",BigintType,Limit(15),NotNull,Comment("同步seq"))
      t.column("DELETAG",VarcharType,Limit(1),NotNull,Comment("删除标记，0：删除"))
    }
    createTable("HALL_READ_CONFIG",Comment("同步数据读取配置")){ t=>
      t.column("PK_ID",VarcharType,Limit(32),NotNull,Comment("主键"),PrimaryKey)
      t.column("NAME",VarcharType,Limit(100),Nullable,Comment("服务器名称"))
      t.column("IP",VarcharType,Limit(100),Nullable,Comment("服务器名称"))
      t.column("TYP",VarcharType,Limit(32),Nullable,Comment("数据类型"))
      t.column("DBID",VarcharType,Limit(32),Nullable,Comment("数据库dbid"))
      t.column("SEQ",BigintType,Limit(15),NotNull,Comment("记录请求的seq"))
      t.column("READ_STRATEGY",VarcharType,Limit(1000),Nullable,Comment("数据读取策略"))
      t.column("CONFIG",VarcharType,Limit(1000),Nullable,Comment("写入策略"))
      t.column("DELETAG",VarcharType,Limit(1),NotNull,Comment("删除标记，0：删除"))
    }
  }

  override def down(): Unit = {
    dropTable("HALL_READ_CONFIG")
    dropTable("HALL_FETCH_CONFIG")
  }
}
