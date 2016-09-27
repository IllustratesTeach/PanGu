package nirvana.hall.v70.migration

import stark.migration._

/**
 * 添加发送远程比对标记字段
 * Created by songpeng on 15/12/9.
 */
class Migrate_20160921150800_Sync extends Migration{
  override def up(): Unit = {
    addColumn("GAFIS_CASE", "SEQ", BigintType,Limit(15), Nullable, Comment("hall同步seq"))
//    execute(Source.fromFile("").mkString)
  }

  override def down(): Unit = {
    removeColumn("GAFIS_CASE", "SEQ")
  }
}
