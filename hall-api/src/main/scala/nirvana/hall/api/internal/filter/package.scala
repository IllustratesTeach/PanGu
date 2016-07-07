package nirvana.hall.api.internal

import javax.servlet.http.HttpServletRequest

import nirvana.hall.api.HallApiConstants
import nirvana.hall.api.config.DBConfig

/**
 * Created by songpeng on 16/7/4.
 */
package object filter {
  /**
   * 获取物理库或者逻辑库信息
   * @param httpServletRequest
   * @return
   */
  def getDBConfig(httpServletRequest: HttpServletRequest): DBConfig ={
    val dbId = httpServletRequest.getHeader(HallApiConstants.HALL_HTTP_HEADER_DBID)
    if(dbId != null){
      val tableId = httpServletRequest.getHeader(HallApiConstants.HALL_HTTP_HEADER_TABLEID)
      DBConfig(if(dbId.matches("\\d+")) Left(dbId.toShort) else Right(dbId), if(tableId != null ) Option(tableId.toShort) else None)
    }else{
      null
    }
  }
}
