package nirvana.hall.api.internal

import javax.servlet.http.HttpServletRequest

import nirvana.hall.api.HallApiConstants

/**
 * Created by songpeng on 16/7/4.
 */
package object filter {

  /**
   * 获取DBID
   * 6.2需要toShort转换一下
   * @param httpServletRequest
   * @return
   */
  def getDBID(httpServletRequest: HttpServletRequest): Option[String] ={
    val dbId = httpServletRequest.getHeader(HallApiConstants.HTTP_HEADER_DBID)
    if(dbId != null){
      Option(dbId)
    }else{
      None
    }
  }
}
