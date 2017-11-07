package nirvana.hall.api.internal

import stark.activerecord.generator.ActiverecordGenerator


/**
  * Created by songpeng on 2017/4/19.
  */
object PojoCreatorApp {

  def main(args: Array[String]): Unit = {
    /**
      * url 数据库连接
      * user 用户名
      * pwd 密码
      * pkg 生成的包
      * 程序会在该工程target/dest/ 目录下生成对应的包路径
      * 建议使用h2数据库生成pojo,由于在连接Oracle数据库会查询所有用户的表信息,而且不同用户存在相同的表名，会有冲突
      */
    val url = "jdbc:h2:file:~/h2_db/gafis-gz/gafis"
    val user = "sa"
    val pwd = ""
    val pkg = "nirvana.hall.v70.gz.jpa"
    ActiverecordGenerator.main(Array(url,user,pwd,pkg))
  }
}
