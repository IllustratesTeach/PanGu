package nirvana.hall.matcher.internal.adapter.common.vo

/**
  * Created by songpeng on 2017/8/22.
  * 查询任务信息
  * @param keyId   卡号
  * @param oraSid  任务号
  * @param queryType 查询类型
  * @param isPalm  是否掌纹
  */
class QueryQueVo(val keyId: String, val oraSid: Int, val queryType: Int, val isPalm: Boolean) {
}
