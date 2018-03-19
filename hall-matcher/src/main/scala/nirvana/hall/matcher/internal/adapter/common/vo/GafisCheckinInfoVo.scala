package nirvana.hall.matcher.internal.adapter.common.vo

/**
  * Created by zqLuo
  */
class GafisCheckinInfoVo{
  var code:String = _ // 编号1
  var tcode:String = _ // 编号2
  var registerUser:String = _ //查中登记用户
  var registerOrg:String = _ //查中登记单位
  var hitpossibility:Int = _ //查中概率
  var priority:Int = _ //比对优先级
  var queryUUID:String = _ //查询任务ID
  var rank:Int = _ //比中排名
  var fraction:Int = _ //比中分数
  var fgp:Int = _ //比中指位
  var cardType1:Int = _ //编号1指掌纹标识 1：指纹；2：掌纹
}
