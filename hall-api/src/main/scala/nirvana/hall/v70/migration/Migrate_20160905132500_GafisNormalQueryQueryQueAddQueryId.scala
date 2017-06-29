package nirvana.hall.v70.migration

import stark.migration._

/**
 * 添加发送远程比对标记字段
 * Created by songpeng on 15/12/9.
 */
class Migrate_20160905132500_GafisNormalQueryQueryQueAddQueryId extends Migration{
  override def up(): Unit = {
    addColumn("GAFIS_NORMALQUERY_QUERYQUE", "QUERYID", BigintType,Limit(15), Nullable, Comment("远程查询SID"))
  }

  override def down(): Unit = {
    removeColumn("GAFIS_NORMALQUERY_QUERYQUE", "QUERYID")
  }
}
