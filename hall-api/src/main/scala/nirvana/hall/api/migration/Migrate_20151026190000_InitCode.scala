package nirvana.hall.api.migration

import monad.migration.Migration

import scala.io.Source

/**
 * Created by songpeng on 15/10/26.
 * 初始化字典数据，行政区划
 */
class Migrate_20151026190000_InitCode extends Migration{
  override def up(): Unit = {
    //execute(Source.fromInputStream(getClass.getResourceAsStream("Migration_20151026190000_Code.sql")).mkString)
    //execute(Source.fromInputStream(getClass.getResourceAsStream("Migration_20151026190000_Area.sql")).mkString)
  }

  override def down(): Unit = {

  }
}
