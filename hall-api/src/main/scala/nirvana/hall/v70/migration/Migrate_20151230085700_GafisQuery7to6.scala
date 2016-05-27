package nirvana.hall.v70.migration

import stark.migration._

/**
 * 上报6.2相关的表
 * Created by songpeng on 15/12/4.
 */
class Migrate_20151230085700_GafisQuery7to6 extends Migration{
  override def up(): Unit = {
    createTable("GAFIS_QUERY_7TO6",Comment("查询7to6关联表")){ t=>
      t.column("ORA_SID",BigintType,Limit(15),NotNull,Comment("7.0的任务号，对应GAFIS_NORMALQUERY_QUERYQUE的ora_sid"),PrimaryKey)
      t.column("QUERY_ID",BigintType,Limit(15),NotNull,Comment("6.2的任务号ora_sid,用来获取6.2查询结果"))
    }

  }

  override def down(): Unit = {
    dropTable("GAFIS_QUERY_7TO6")
  }
}
