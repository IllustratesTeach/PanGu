package nirvana.hall.v70.migration

import stark.migration.{Nullable, TimestampType, _}

/**
  * Created by yuchen on 2017/8/10.
  */
class Migrate_20170810101420_HallReadRecord extends Migration{
  override def up(): Unit = {
    createTable("HALL_READ_RECORD",Comment("该表用来记录hall7.0已经同步给6.2的任务，保证下一次不会将相同的任务再次同步给6.2")){ t=>
      t.column("UUID",VarcharType,Limit(32),NotNull,Comment("该表主键"),PrimaryKey)
      t.column("ISSYNCCANDLIST",VarcharType,Limit(1),Nullable,Comment("是否同步过候选"))
      t.column("CREATETIME",TimestampType,Nullable,Comment("当前时间"))
      t.column("ORASID",VarcharType,Limit(32),Nullable,Comment("任务号"))
    }
  }

  override def down(): Unit = {

  }
}
