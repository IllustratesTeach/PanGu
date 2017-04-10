package nirvana.hall.v70.migration

import stark.migration._

import scala.io.Source

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-08
 */
class Migrate_20170406142915_GAFIS_LOGIC_DB_RULE
  extends Migration {

  def up(): Unit = {
    createTable("GAFIS_LOGIC_DB_RULE",Comment("逻辑库判断规则")){ t=>
      t.column("KEYID",VarcharType,Limit(32),NotNull,Comment("该表主键"),PrimaryKey)
      t.column("PK_ID",VarcharType,Limit(32),Nullable,Comment("主键ID，用于区分逻辑库"))
      t.column("LOGIC_CODE",VarcharType,Limit(32),Nullable,Comment("编号或代码号"))
      t.column("LOGIC_NAME",VarcharType,Limit(32),Nullable,Comment("名称"))
      t.column("LOGIC_CATEGORY",VarcharType,Limit(1),Nullable,Comment("分库类型，0:捺印分库,  1:案件分库"))
     // t.column("LOGIC_DELTAG",VarcharType,Limit(1),Nullable,Comment("禁启用标识，0: 禁用，1:启用"))
      t.column("LOGIC_REMARK",VarcharType,Limit(300),Nullable,Comment("备注"))
      t.column("LOGIC_RULE",VarcharType,Limit(300),Nullable,Comment("逻辑库匹配规则"))
    }
    execute("alter table record_afis modify(type varchar2(50))")
//    execute(Source.fromInputStream(getClass.getResourceAsStream("Migration_20170406150000_LogicDBRule.sql"),"UTF-8").mkString)
  }

  override def down(): Unit = ???
}

