package nirvana.hall.v70.migration

import stark.migration._

/**
 * 上报6.2相关的表
 * Created by songpeng on 15/12/4.
 */
class Migrate_20160618150600_SyncGafis6Config extends Migration{
  override def up(): Unit = {
    createTable("SYNC_GAFIS6_CONFIG",Comment("同步6.2信息记录表，记录同步的seq信息")){ t=>
      t.column("PK_ID",VarcharType,Limit(1),NotNull,Comment("主键=1"),PrimaryKey)
      t.column("IP",VarcharType,Limit(20),Nullable,Comment("gafis6 IP"))
      t.column("PORT",VarcharType,Limit(10),Nullable,Comment("gafis6 port"))
      t.column("TPCARD_TIMESTAMP",BigintType,Limit(15),NotNull,Comment("同步捺印的时间戳"))
      t.column("LPCARD_TIMESTAMP",BigintType,Limit(15),NotNull,Comment("同步现场的时间戳"))
      t.column("CASE_TIMESTAMP",BigintType,Limit(15),NotNull,Comment("同步案件的时间戳"))
    }
    //初始化一条数据
    execute("insert into SYNC_GAFIS6_CONFIG (PK_ID, IP, PORT, TPCARD_TIMESTAMP, LPCARD_TIMESTAMP, CASE_TIMESTAMP) values ('1','127.0.0.1','8080',0,0,0)")
  }

  override def down(): Unit = {
    dropTable("SYNC_GAFIS6_CONFIG")
  }
}
