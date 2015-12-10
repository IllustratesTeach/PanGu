package nirvana.hall.api.migration

import monad.migration._

/**
 * 添加发送远程比对标记字段
 * Created by songpeng on 15/12/9.
 */
class Migrate_20151209173038_AlertGafisNormalQueryQueryQue extends Migration{
  override def up(): Unit = {
    addColumn("GAFIS_NORMALQUERY_QUERYQUE", "SYNC_TARGET_SID", VarcharType, Limit(20), Nullable, Comment("远程比对库(null表示不是远程;非null:表示对应远程比对库SID)"))
  }

  override def down(): Unit = {
    removeColumn("GAFIS_NORMALQUERY_QUERYQUE", "SYNC_TARGET_SID")
  }
}
