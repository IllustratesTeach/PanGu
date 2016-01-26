package nirvana.hall.v70.migration

import monad.migration.Migration

import scala.io.Source

/**
 * Created by songpeng on 15/10/26.
 * 初始化字典数据，行政区划
 */
class Migrate_20151026190000_InitCode extends Migration{
  override def up(): Unit = {
    execute(Source.fromInputStream(getClass.getResourceAsStream("Migration_20151026190000_Code.sql"),"UTF-8").mkString)
    execute(Source.fromInputStream(getClass.getResourceAsStream("Migration_20151026190000_Area.sql"),"UTF-8").mkString)
    execute(Source.fromInputStream(getClass.getResourceAsStream("Migration_20151120150000_Area2.sql"),"UTF-8").mkString)
  }

  override def down(): Unit = {

  }
}
