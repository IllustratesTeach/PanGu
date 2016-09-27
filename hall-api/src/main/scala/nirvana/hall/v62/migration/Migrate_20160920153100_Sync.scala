package nirvana.hall.v62.migration

import stark.migration._

import scala.io.Source

/**
 * 由于6.2删除任务后任务号还会重新利用，hall同步查询任务添加seq，保证唯一
 * Created by songpeng on 15/12/9.
 */
class Migrate_20160920153100_Sync extends Migration{
  override def up(): Unit = {
    addColumn("NORMALQUERY_QUERYQUE", "SEQ", BigintType,Limit(15), Nullable, Comment("hall同步SID用来确定唯一性"))
    execute(Source.fromInputStream(getClass.getResourceAsStream("Migration_20160920153100_Sync.sql")).mkString)
  }

  override def down(): Unit = {
    removeColumn("NORMALQUERY_QUERYQUE", "SEQ")
  }
}
