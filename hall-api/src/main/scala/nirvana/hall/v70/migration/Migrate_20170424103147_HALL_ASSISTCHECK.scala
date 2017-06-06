package nirvana.hall.v70.migration

import stark.migration._

/**
  * Created by yuchen on 2017/4/21.
  */
class Migrate_20170424103147_HALL_ASSISTCHECK extends Migration{
  def up(): Unit = {
    createTable("HALL_ASSISTCHECK",Comment("针对协查创建的记录表,无论和哪个厂商互联,全部记录到这个表中,在此表中区分不同厂商过来的任务")){ t=>
      t.column("ID",VarcharType,Limit(32),NotNull,Comment("该表主键"),PrimaryKey)
      t.column("QUERYID",VarcharType,Limit(32),Nullable,Comment("远程厂商任务号"))
      t.column("ORASID",VarcharType,Limit(32),Nullable,Comment("本地生成的任务号"))
      t.column("CASEID",VarcharType,Limit(32),Nullable,Comment("案件编号"))
      t.column("PERSONID",VarcharType,Limit(32),Nullable,Comment("人员编号"))
      t.column("CREATETIME",TimestampType,Nullable,Comment("当前时间"))
      t.column("SOURCE",VarcharType,Limit(4),Nullable,Comment("来源:区分不同的厂商"))
      t.column("STATUS",VarcharType,Limit(2),Nullable,Comment("是否已经上报"))
    }
  }

  override def down(): Unit = ???
}
