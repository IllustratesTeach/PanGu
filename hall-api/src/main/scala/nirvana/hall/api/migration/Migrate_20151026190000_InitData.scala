package nirvana.hall.api.migration

import monad.migration.Migration

import scala.io.Source

/**
 * Created by songpeng on 15/10/26.
 */
class Migrate_20151026190000_InitData extends Migration{
  override def up(): Unit = {
    //读取SQL文件
    execute(Source.fromInputStream(getClass.getResourceAsStream("Migration_20151026190000_Code.sql")).mkString)
    execute(Source.fromInputStream(getClass.getResourceAsStream("Migration_20151026190000_Sys.sql")).mkString)
//    execute(Source.fromInputStream(getClass.getResourceAsStream("Migration_20151026190000_SysArea.sql")).mkString)
    execute(Source.fromInputStream(getClass.getResourceAsStream("Migration_20151026190000_Gather.sql")).mkString)

  }

  override def down(): Unit = {

  }
}
