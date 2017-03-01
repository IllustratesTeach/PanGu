package nirvana.hall.v70.migration

import stark.migration._

/**
 * 标志位相关表
 * Created by zhoaoyang on 17/02/08.
 */
class Migrate_20170208101700_RecordAfis extends Migration{

  override def up(): Unit = {
    createTable("RECORD_AFIS",Comment("标志位表")){ t=>
      t.column("uuid",VarcharType,Limit(36),NotNull,Comment("主键，唯一标志键"))
      t.column("card_id",VarcharType,Limit(34),Comment("卡号"))
      t.column("type",VarcharType,Limit(20),Comment("类型"))
      t.column("ip_source",VarcharType,Limit(15),Comment("源IP"))
      t.column("insert_date",TimestampType,Comment("插入时间"))
      t.column("status",VarcharType,Limit(1),Comment("结果状态  0-失败 1-成功"))
      t.column("is_send",VarcharType,Limit(1),Comment("是否是发送端  0-发送端 1-接收端"))
    }

    createTable("HALL_SYS_LOG",Comment("hall系统日志表")){ t=>
      t.column("uuid",VarcharType,Limit(36),NotNull,Comment("主键，唯一标志键"))
      t.column("card_id",VarcharType,Limit(34),Comment("卡号"))
      t.column("insert_date",TimestampType,Nullable,Comment("插入时间"))
      t.column("content",ClobType,Comment("日志内容"))
      t.column("log_type",IntegerType,Comment("日志类型 1-消息 2-错误"))
      t.column("msg",VarcharType,Limit(1000),Comment("日志信息"))
      t.column("seq",VarcharType,Limit(40),Comment("序列"))
    }
  }

  override def down(): Unit = {
    dropTable("RECORD_AFIS")
    dropTable("HALL_SYS_LOG")
  }
}
