package nirvana.hall.webservice.internal.penaltytech

/**
  * @author <a href="yuchen_1997_200486@126.com">yuchen</a>.
  * @since 2018/12/8
  */
object PenaltyTechConstant {

  final val  TEMPLATE_FINGER = 1
  final val  LATENT_FINGER = 2
  final val  TT_HIT = 3
  final val  LL_HIT = 4
  final val  LT_HIT = 5
  final val  TL_HIT = 6

  final val DELETED = "0" //已删除
  final val NOT_DELETE = "1" //未删除

  final val SUCCESS = "1"
  final val FAIL = "0"


  final val VERIFYRESULT_HITED = "2" //表示比中
  final val VERIFYRESULT_NOTHIT = "1"  //表示未比中
  final val VERIFYRESULT_NOTRESULT = "0" //表示没有结果

}
