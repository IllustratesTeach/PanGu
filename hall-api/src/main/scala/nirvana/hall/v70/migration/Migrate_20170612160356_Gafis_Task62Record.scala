package nirvana.hall.v70.migration

import stark.migration.{Nullable, PrimaryKey, TimestampType, _}

/**
  * Created by win-20161010 on 2017/6/12.
  */
class Migrate_20170612160356_Gafis_Task62Record extends Migration{

  override def up(): Unit = {
    createTable("Gafis_Task62Record",Comment("该表用来记录hall6.2同步过来的任务，为了在7.0数据库中区分是6的任务，还是7自身的任务")){ t=>
      t.column("UUID",VarcharType,Limit(32),NotNull,Comment("该表主键"),PrimaryKey)
      t.column("QUERYID",VarcharType,Limit(32),Nullable,Comment("6.2系统的任务号"))
      t.column("ORASID",VarcharType,Limit(32),Nullable,Comment("本地生成的任务号"))
      t.column("ISSYNCCANDLIST",VarbinaryType,Limit(1),Nullable,Comment("是否已经同步了6.2系统生成的候选"))
      t.column("CREATETIME",TimestampType,Nullable,Comment("当前时间"))
    }
  }

  override def down(): Unit = {
    dropTable("Gafis_Task62Record")
  }
}
