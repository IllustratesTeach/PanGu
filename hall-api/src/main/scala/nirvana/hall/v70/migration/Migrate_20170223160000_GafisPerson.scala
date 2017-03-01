package nirvana.hall.v70.migration

import stark.migration._

/**
  * 修改gafis_case表remark字段大小
  * Created by zhoaoyang on 17/02/23
  */
class Migrate_20170223160000_GafisPerson extends Migration {

  override def up(): Unit = {
    addColumn("gafis_person", "remark_temp", ClobType, Comment("备注或抓获情况"))
    execute("update gafis_person t set remark_temp = t.remark")
    removeColumn("gafis_person", "remark")
    execute("alter table gafis_person rename column remark_temp to remark")
  }

  override def down(): Unit = {
    addColumn("gafis_person", "remark_temp", VarcharType, Limit(2800), Comment("备注或抓获情况"))
    execute("update gafis_person t set remark_temp = t.remark")
    removeColumn("gafis_person", "remark")
    execute("alter table gafis_person rename column remark_temp to remark")
  }

}
