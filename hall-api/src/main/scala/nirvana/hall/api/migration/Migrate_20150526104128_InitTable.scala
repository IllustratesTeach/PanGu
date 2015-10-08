package nirvana.hall.api.migration

import monad.migration._

/**
 * migration
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-05-26
 */
class Migrate_20150526104128_InitTable extends Migration {
  override def up(): Unit = {
    createTable("user",Comment("用户")) { t =>
      t.integer("id", PrimaryKey, AutoIncrement,Comment("主键"))
      t.varchar("login", Limit(50), NotNull,Comment("登录用户名"))
      t.varchar("password", Limit(50), NotNull,Comment("密码"))
      t.varchar("corporate_name", Limit(100), NotNull,Comment("公司名称"))
      t.varchar("boss", Limit(50), NotNull,Comment("老板"))
      t.varchar("link_man", Limit(50), NotNull,Comment("联系人"))
      t.integer("industry_id",Comment("所属行业"))
      t.integer("created_time",Comment("创建时间"))
      t.integer("updated_time",Comment("更新时间"))
    }

    createTable("industry",Comment("行业信息")) { t =>
      t.integer("id", PrimaryKey, AutoIncrement,Comment("主键"))
      t.varchar("name", Limit(50), NotNull,Comment("行业名称"))
    }
    createTable("online_user",Comment("在线用户")) { t =>
      t.varchar("login", Unique, NotNull, Limit(50),Comment("登录名称"))
      t.integer("login_time", NotNull,Comment("登录时间"))
      t.integer("latest_time", NotNull,Comment("最新活动时间"))
      t.varchar("token", NotNull, Unique, Limit(50),Comment("校验使用的token，这个值是变化的"))
    }
    createTable("goods",Comment("商品表")) { t =>
      initUserTable(t)
      t.varchar("name", Limit(50), NotNull,Comment("商品名称"))
      t.varchar("code", Limit(50),Comment("商品代码"))
      t.varchar("pinyin", Limit(50),Comment("商品名称对应的拼音"))
      t.integer("last_price",Comment("最新价格"))
      t.integer("quantity", NotNull, Default("0"),Comment("商品数量，考虑放入到字表"))
    }

    createTable("transfer_method",Comment("转账方法")) { t =>
      initUserTable(t)
      t.varchar("name", Limit(20),Comment("名称"))
    }

    //partner
    createTable("partner",Comment("合作伙伴")) { t =>
      initUserTable(t)
      t.integer("relation_id",Comment("关联到该合作伙伴对应用户的ID"))
      t.varchar("corporate_name", Limit(100), NotNull,Comment("公司名称"))
      t.varchar("boss", Limit(10),Comment("老板"))
      t.varchar("address", Limit(255),Comment("地址"))
      t.varchar("mobile", Limit(15),NotNull,Comment("手机号"))
      t.varchar("link_man", Limit(20),NotNull,Comment("联系人"))
    }

    //stock 部分
    createTable("delivery_note",Comment("发货单")) { t =>
      initPartnerTable(t)
      t.integer("note_type", NotNull, Default("1"),Comment("发货单类型：1 正常发货\n2 退货发货\n3 内部调拨"))
      t.integer("amount",Comment("发货金额"))
      t.integer("time",Comment("发货时间"))
    }
    createTable("delivery_note_detail",Comment("发货单明细")) {
      t: TableDefinition =>
        t.integer("id", PrimaryKey, AutoIncrement,Comment("主键"))
        t.integer("delivery_note_id", NotNull,Comment("关联的发货单"))
        t.integer("goods_id", NotNull,Comment("关联的货物ID"))
        t.integer("quantity", NotNull,Comment("发货数量"))
        t.integer("unit_price",Comment("单价"))
        t.integer("amount", NotNull,Comment("金额"))
    }
    createTable("delivery_note_detail_history",Comment("发货单明细历史表，主要记录明细修改记录")) {
      t =>
        t.integer("id", PrimaryKey, AutoIncrement,Comment("主键"))
        t.integer("delivery_note_detail_id", NotNull,Comment("发货单明细ID"))
        t.integer("quantity_before", NotNull,Comment("调整之前的数量"))
        t.integer("quantity_after", NotNull,Comment("调整之后的数量"))
        t.integer("amount_before", NotNull,Comment("调整之前的金额"))
        t.integer("amount_after", NotNull,Comment("调整之后的金额"))
        t.integer("time",Comment("变动时间"))
    }
    createTable("statement_delivery",Comment("对账单和发货明细关联表")) { t =>
      t.integer("delivery_note_detail_id", NotNull,Comment("关联的发货明细"))
      t.integer("statement_id", NotNull,Comment("关联的对账单"))
    }

    //收货部分表格
    createTable("receiving_note",Comment("收货单")) { t =>
      initPartnerTable(t)
      t.integer("note_type", NotNull, Default("1"),Comment("收货单类型：1 正常收货\n2 退货收货\n3 内部调拨"))
      t.integer("amount",Comment("收货金额"))
      t.integer("status", Default("1"),Comment("订单状态，1: 收货 2: 明细已经对账完毕"))
      t.integer("time",NotNull,Comment("收货时间"))
      t.varchar("voucher_url",Limit(100),Comment("凭证的url"))
    }
    createTable("receiving_note_detail",Comment("收货单明细")) { t =>
      t.integer("id", PrimaryKey, AutoIncrement,Comment("主键"))
      t.integer("receiving_note_id", NotNull,Comment("关联的收货单"))
      t.integer("goods_id", NotNull,Comment("关联的商品"))
      t.integer("quantity", NotNull,Comment("数量"))
      t.integer("unit_price",Comment("单价"))
      t.integer("amount", NotNull,Comment("金额"))
    }
    createTable("receiving_note_detail_history",Comment("收货单明细历史表,主要用来调整数量和金额")) { t =>
      t.integer("id", PrimaryKey, AutoIncrement,Comment("主键"))
      t.integer("receiving_note_detail_id", NotNull,Comment("收货单明细主键"))
      t.integer("quantity_before", NotNull,Comment("调整前数量"))
      t.integer("quantity_after", NotNull,Comment("调整后数量"))
      t.integer("amount_before", NotNull,Comment("调整前金额"))
      t.integer("amount_after", NotNull,Comment("调整后金额"))
      t.integer("time",Comment("变动时间"))
    }
    createTable("statement_receiving",Comment("对账单和收货明细关联表")) { t =>
      t.integer("receiving_note_detail_id", NotNull,Comment("关联收货明细"))
      t.integer("statement_id", NotNull,Comment("关联对账单"))
    }

    createTable("take_stock",Comment("仓库盘点")) { t =>
      t.integer("id", PrimaryKey, AutoIncrement,Comment("主键"))
      t.integer("goods_id", NotNull,Comment("商品ID"))
      t.integer("quantity_before", NotNull,Comment("盘点前金额"))
      t.integer("quantity_after", NotNull,Comment("盘点后金额"))
      t.integer("time",Comment("盘点时间"))
    }

    //finance

    createTable("statement",Comment("对账单")) { t =>
        initPartnerTable(t)
        t.integer("statement_summary_id",Comment("关联所属对账单汇总"))
        t.varchar("name", Limit(100),Comment("对账单名称"))
        t.integer("my_amount", NotNull,Comment("我方金额"))
        t.integer("his_amount", NotNull,Comment("对方金额"))
        t.integer("amount_fixed", NotNull,Comment("修正金额"))
    }
    createTable("statement_summary",Comment("对账汇总表")) { t =>
        initPartnerTable(t)
        t.integer("amount_caled",Comment("计算金额"))
        t.integer("amount_fixed",Comment("修正金额"))
    }
    createTable("transfer_plan",Comment("汇款计划(打款计划)")) { t =>
        initPartnerTable(t)
        t.integer("statement_summary_id", NotNull,Comment("对账汇总金额"))
        t.integer("transfer_method_id", NotNull,Comment("转账方法"))
        t.integer("amount", NotNull,Comment("金额"))
        t.integer("plan_time",Comment("计划打款时间"))
        t.integer("actual_time",Comment("实际打款时间"))
        t.integer("transfer_type",Comment("打款类型\n1:  正常打货款\n2 :  预付款"),Default("1"))
    }

    createTable("receiving_money",Comment("收款单")) { t =>
        initPartnerTable(t)
        t.smallint("receiving_type",Comment("1 正常收货款\n2 收预付款"),Default("1"))
        t.integer("amount",Comment("收款金额"))
    }

    createTable("receiving_money_statement",Comment("收款和对账单关联表")) { t =>
        t.integer("statement_id", NotNull,Comment("关联的对账单"))
        t.integer("receiving_money_id", NotNull,Comment("关联收款单"))
        t.integer("amount", NotNull,Comment("金额"))
    }

    execute("insert into industry(name) values('玻璃管')")
  }

  //和用户关联的表
  private def initUserTable(t: TableDefinition): Unit = {
    t.integer("id", PrimaryKey, AutoIncrement,Comment("主键"))
    t.integer("user_id", NotNull,Comment("关联用户主键"))
    t.integer("created_time",Comment("创建时间"))
    t.integer("updated_time",Comment("更新时间"))
  }

  //和用户和合作伙伴关联的表
  private def initPartnerTable(t: TableDefinition): Unit = {
    initUserTable(t)
    t.integer("partner_id", NotNull,Comment("关联的合作伙伴"))
  }

  override def down(): Unit = {
    dropTable("receiving_money_statement")
    dropTable("receiving_money")
    dropTable("transfer_plan")
    dropTable("statement_summary")
    dropTable("statement")
    dropTable("take_stock")
    dropTable("statement_receiving")
    dropTable("receiving_note_detail_history")
    dropTable("receiving_note_detail")
    dropTable("receiving_note")
    dropTable("statement_delivery")
    dropTable("delivery_note_detail_history")
    dropTable("delivery_note_detail")
    dropTable("delivery_note")
    dropTable("partner")
    dropTable("transfer_method")
    dropTable("goods")
    dropTable("online_user")
    dropTable("industry")
    dropTable("user")
  }
}
