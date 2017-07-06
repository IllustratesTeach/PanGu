package nirvana.hall.webservice

/**
  * Created by songpeng on 2017/4/24.
  */
object HallWebserviceConstants {
  val EXTRA_MODULE_CLASS = "extra.module"

  //操作类型
  val LocalTenFinger = "localTenprint" // 本地十指
  val LocalLatent = "localLatent" //本地现场
  val LocalHitResult = "localhitResult" //本地比对关系
  val XCHitResult = "xchitresult" //外省查询比对关系

  //存库发查询类型定义
  val Status = 0 //发查询初始状态
  val SucStatus = 7  //发查询成功
  val ErrStatus = -2 //发查询失败

  val TaskXC = 1 //现场任务
  val TaskZT = 2 //现场任务
}
