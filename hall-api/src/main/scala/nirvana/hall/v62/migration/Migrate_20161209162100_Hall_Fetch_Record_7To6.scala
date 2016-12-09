package nirvana.hall.v62.migration

import stark.migration._

/**
 * 上报6.2相关的表
 * Created by songpeng on 15/12/4.
 */
class Migrate_20161209162100_Hall_Fetch_Record_7To6 extends Migration{
  override def up(): Unit = {
    createTable("HALL_FETCH_RECORD_7TO6",Comment("同步抓取任务记录表")){ t=>
      t.column("ORA_UUID",VarcharType,Limit(32),NotNull,Comment("主键"),PrimaryKey)
    }
  }

  override def down(): Unit = {
    /*dropTable("HALL_READ_CONFIG")
    dropTable("HALL_FETCH_CONFIG")*/
  }
}
