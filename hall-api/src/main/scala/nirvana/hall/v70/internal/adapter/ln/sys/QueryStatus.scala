package nirvana.hall.v70.internal.adapter.ln.sys

/**
  * Created by mengxin on 2017/11/8.
  */
trait QueryStatus {

  /**
    * 根据任务号sid获取比对状态 SQL查询方式
    * @param oraSid
    */
  def getStatusBySidSQL(oraSid:Long): Int

  /**
    * 更新任务表中对应这条认定的候选信息的候选状态
    * @param oraSid
    * @param taskType
    * @param keyId
    * @param fgp
    * @return
    */
  def updateCandListStatus(oraSid:String,taskType:Int,keyId:String,tCode:String,fgp:Int): Long
}
