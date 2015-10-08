package nirvana.hall.api.migration

import monad.migration._

/**
 * migration
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-05-26
 */
class Migrate_20151008104128_CreateOnlineTable extends Migration {
  override def up(): Unit = {

    createTable("online_user",Comment("在线用户")) { t =>
      t.varchar("login", Unique, NotNull, Limit(50),Comment("登录名称"))
      t.integer("login_time", NotNull,Comment("登录时间"))
      t.integer("latest_time", NotNull,Comment("最新活动时间"))
      t.varchar("token", NotNull, Unique, Limit(50),Comment("校验使用的token，这个值是变化的"))
    }
  }

  override def down(): Unit = {
    dropTable("online_user")
  }
}
