package nirvana.hall.v70.migration

import stark.migration._

/**
  * Created by yuchen on 2017/2/13.
  */
class Migrate_20170213162859_AddDelFlagForGAFISCASEPALMMNT extends Migration{
  override def up(): Unit = {
    addColumn("GAFIS_CASE_PALM_MNT", "DELETAG", VarcharType,Limit(1),  Comment("删除标志"))
  }

  override def down(): Unit = {
    removeColumn("GAFIS_CASE_PALM_MNT", "DELETAG")
  }

}
