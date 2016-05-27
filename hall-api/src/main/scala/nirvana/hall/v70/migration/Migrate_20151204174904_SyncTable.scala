package nirvana.hall.v70.migration

import stark.migration._

/**
 * 上报6.2相关的表
 * Created by songpeng on 15/12/4.
 */
class Migrate_20151204174904_SyncTable extends Migration{
  override def up(): Unit = {
    createTable("SYNC_QUEUE",Comment("数据同步队列表")){ t=>
      t.column("PK_ID",VarcharType,Limit(32),NotNull,PrimaryKey)
      t.column("UPLOAD_KEYID",VarcharType,Limit(32),Nullable,Comment("上报编号"))
      t.column("UPLOAD_FLAG",VarcharType,Limit(2),Nullable,Comment("上报标记(1:捺印;2:案件;3:现场)"))
      t.column("UPLOAD_TYPE",VarcharType,Limit(2),Nullable,Comment("上报方式(1:自动;2:手工)"))
      t.column("UPLOAD_STATUS",VarcharType,Limit(10),Nullable,Comment("上报成功标记"))
      t.column("UPLAOD_TIMES",SmallintType,Limit(1),Nullable,Comment("上报次数"))
      t.column("HAS_PALM",VarcharType,Limit(2),Nullable,Comment("是否上报掌纹"))
      t.column("TARGET_IP",VarcharType,Limit(20),Nullable,Comment("上报目标库IP"))
      t.column("TARGET_PORT",VarcharType,Limit(10),Nullable,Comment("上报目标库端口"))
      t.column("TARGET_USERNAME",VarcharType,Limit(20),Nullable,Comment("上报目标库用户名"))
      t.column("TARGET_SID",VarcharType,Limit(10),Nullable,Comment("上报目标库标识"))
      t.column("CREATEDATE",TimestampType,Limit(7),Nullable,Comment("上报创建时间"))
      t.column("FINISHDATE",TimestampType,Limit(7),Nullable,Comment("上报完成时间"))
      t.column("OPRATION",VarcharType,Limit(10),Nullable,Comment("操作(insert,update,delete)"))
      t.column("REMARK",VarcharType,Limit(500),Nullable,Comment("备注"))
    }
    createTable("SYNC_STRATEGY",Comment("策略表")){ t=>
      t.column("PK_ID",VarcharType,Limit(32),NotNull,PrimaryKey)
      t.column("STRATEGY_NAME",VarcharType,Limit(20),Nullable,Comment("策略名称"))
      t.column("STRATEGY_QUERY",VarcharType,Limit(500),Nullable,Comment("策略查询条件"))
      t.column("TYPE",VarcharType,Limit(2),Nullable,Comment("上报方式(1:自动;2手工)"))
      t.column("TARGETS",VarcharType,Limit(500),Nullable,Comment("上报目标集合"))
      t.column("DELTAG",VarcharType,Limit(2),Nullable,Comment("删除标记"))
      t.column("FLAG",VarcharType,Limit(2),Nullable,Comment("数据标记(1:捺印;2:案件;3:现场)"))
      t.column("HAS_PALM",VarcharType,Limit(2),Nullable,Comment("是否包含掌纹"))
    }
    createTable("SYNC_TARGET",Comment("上报目标库管理表")){ t=>
      t.column("PK_ID",VarcharType,Limit(32),NotNull,PrimaryKey)
      t.column("TARGET_SID",VarcharType,Limit(10),Nullable,Comment("上报目标库标识"))
      t.column("TARGET_NAME",VarcharType,Limit(20),Nullable,Comment("上报目标库名称"))
      t.column("TARGET_IP",VarcharType,Limit(20),Nullable,Comment("上报目标库IP"))
      t.column("TARGET_PORT",VarcharType,Limit(10),Nullable,Comment("上报目标库端口"))
      t.column("TARGET_USERNAME",VarcharType,Limit(20),Nullable,Comment("上报目标库用户名"))
      t.column("DELTAG",VarcharType,Limit(2),Nullable,Comment("删除标记(0,删除)"))
    }

  }

  override def down(): Unit = {
    dropTable("SYNC_TARGET")
    dropTable("SYNC_STRATEGY")
    dropTable("SYNC_QUEUE")

  }
}
