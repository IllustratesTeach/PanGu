package nirvana.hall.v70.migration

import stark.migration.{Nullable, _}

/**
  * Created by win-20161010 on 2017/6/27.
  */
class Migrate_201706271452_HALL_PKID_BREAKID extends Migration{
  override def up(): Unit = {
    createTable("HALL_PKID_BREAKID",Comment("该表用来记录7.0比中关系表主键pk_id与为6.2生成的breakId的对应关系")){ t=>
      t.column("PKID",VarcharType,Limit(32),NotNull,Comment("gafis_checkin_info中的pk_id"))
      t.column("BREAKID",VarcharType,Limit(24),Nullable,Comment("6.2系统破案表admin_breakcase表中存储的主键breakid，该值由hall70生成"))
    }
  }

  override def down(): Unit = {

  }
}
