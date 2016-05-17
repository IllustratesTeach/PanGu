package nirvana.hall.v62.internal.c.gnetlib

import nirvana.hall.v62.internal.AncientClientSupport

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-17
  */
trait gnetfunc {
  this:AncientClientSupport with reqansop with netpmadm =>
  /*
  //get local unit code
  def NET_GAFIS_RMTLIB_GetLocUnitCode:String= {
    char	szValue[256] = {0};
    char	*pszName;

    if( g_stRmtParamName.pszLocUnitCode )
      pszName = g_stRmtParamName.pszLocUnitCode;
    else
      pszName = g_stParamName.pszUnitCode;

    if( NET_GAFIS_PMADM_GetStr(hCon, pszName, szValue) < 0 ) ERRFAILRETURN();

    memcpy(pszUnitCode, szValue, nBufSize);
    pszUnitCode[nBufSize-1] = 0;
    GAFIS_RMTLIB_UTIL_Trim(pszUnitCode);
    return 1;
  }
  */

}
