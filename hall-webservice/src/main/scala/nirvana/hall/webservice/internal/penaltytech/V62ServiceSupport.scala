package nirvana.hall.webservice.internal.penaltytech

import nirvana.hall.api.services.QueryService
import nirvana.hall.c.services.gloclib.galoclp.{ GLPCARDINFOSTRUCT}
import nirvana.hall.c.services.gloclib.galoctp.GTPCARDINFOSTRUCT
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYSIMPSTRUCT
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.gloclib.gcolnames
import nirvana.hall.v62.internal.c.gloclib.gcolnames.g_stCN

/**
  * @author <a href="yuchen_1997_200486@126.com">yuchen</a>.
  * @since 2018/12/8
  */
class V62ServiceSupport(v62Facade: V62Facade,v62Config: HallV62Config,queryService:QueryService) {


  def getTemplateCardIdList(startTime:String,endTime:String): Seq[String] ={
    val statement = Option("((CreateTime>=to_date('%s','yyyy-MM-dd hh24:mi:ss')) AND (CreateTime<to_date('%s','yyyy-MM-dd hh24:mi:ss')))"
      .format(startTime.trim,endTime.trim))
    val mapper = Map("cardid"->"szCardID")

    val result = v62Facade.queryV62Table[GTPCARDINFOSTRUCT](
      V62Facade.DBID_TP_DEFAULT,
      V62Facade.TID_TPCARDINFO,
      mapper,
      statement,
      1000000)
    result.map(t => t.szCardID)
  }


  def getLatentCardList(startTime:String,endTime:String): Seq[String] ={
    val statement = Option("((CreateTime>=to_date('%s','yyyy-MM-dd hh24:mi:ss')) AND (CreateTime<to_date('%s','yyyy-MM-dd hh24:mi:ss')))"
      .format(startTime.trim,endTime.trim))
    val mapper = Map("fingerid"->"szCardID")

    val result = v62Facade.queryV62Table[GLPCARDINFOSTRUCT](
      V62Facade.DBID_LP_DEFAULT,
      V62Facade.TID_LATFINGER,
      mapper,
      statement,
      1000000)
    result.map(t => t.szCardID)
  }


  def getCaseIdByFingerId(fingerId:String): String ={
    val queryVal = g_stCN.stLCsID.pszName
    val data = v62Facade.NET_GAFIS_COL_GetByKey(V62Facade.DBID_LP_DEFAULT, V62Facade.TID_LATFINGER, fingerId, queryVal)
    new String(data).trim
  }

  /**
    * 通过认定时间来查询,获得已完成认定的任务的任务号
    * @return
    */
  def getTask(statement:Option[String],queryStatus:Int): List[GAQUERYSIMPSTRUCT]= {

    val mapper = Map(
      gcolnames.g_stCN.stNuminaCol.pszSID -> "nQueryID",
      "KEYID" -> "szKeyID",
      "FLAG" -> "nFlag",
      "VERIFYRESULT" -> "nVerifyResult",
      "SUBMITTIME" -> "tSubmitTime",
      "USERNAME" -> "szUserName",
      "STATUS" -> "nStatus",
      "CheckUserName" -> "szCheckUserName",
      "CheckTime" -> "tCheckTime",
      "ReCheckUserName" -> "szReCheckUserName"
    )
    val result = v62Facade.queryV62Table[GAQUERYSIMPSTRUCT](V62Facade.DBID_QRY_DEFAULT, V62Facade.TID_QUERYQUE, mapper, statement, 100000)

    result.filter(_.nStatus == queryStatus)
  }
}
