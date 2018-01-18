package nirvana.hall.v62.internal.c.gnetlib

import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.ghpcbase.gnopcode._
import nirvana.hall.c.services.gloclib.glocndef.GNETANSWERHEADOBJECT
import nirvana.hall.c.services.gloclib.survey
import nirvana.hall.c.services.gloclib.survey._
import nirvana.hall.v62.internal.AncientClientSupport

/**
  * Created by songpeng on 2018/1/16.
  */
trait gnetsurvey {
  this:AncientClientSupport with gnetcsr with reqansop=>

  def NET_GAFIS_SURVEYRECORD_ADD(nDBID:Short, nTableID:Short, surveyRecord: SURVEYRECORD, nOption: Int = 0):Unit=executeInChannel { channel =>
    NET_GAFIS_AddOrUpdate(nDBID, nTableID, OP_ADM_SURVEY_RECORD_ADD.toShort, surveyRecord, nOption)
  }
  def NET_GAFIS_SURVEYRECORD_UPDATE(nDBID:Short, nTableID:Short, surveyRecord: SURVEYRECORD, nOption: Int = 0):Unit=executeInChannel { channel =>
    NET_GAFIS_AddOrUpdate(nDBID, nTableID, OP_ADM_SURVEY_RECORD_UPDATE.toShort, surveyRecord, nOption)
  }

  private def NET_GAFIS_AddOrUpdate[R <: AncientData](nDBID:Short, nTableID:Short, nOpCode: Short, data: R, nOption : Int = 0):Unit=executeInChannel { channel =>
    val pReq = createRequestHeader
    val stAns = new GNETANSWERHEADOBJECT

    pReq.nOption = nOption
    pReq.nDBID = nDBID
    pReq.nTableID = nTableID
    pReq.nOpClass = OP_CLASS_ADM.asInstanceOf[Short]
    pReq.nOpCode = nOpCode
    NETOP_SENDREQ(channel, pReq)
    NETOP_SENDDATA(channel, data)

    NETOP_RECVANS(channel, stAns)
    validateResponse(channel,stAns)
  }

  def NET_GAFIS_SURVEYRECORD_LIST_GET_BY_STATE(nDBID:Short, nTableID:Short, state: Byte, limit: Int = 10, nOption: Int = 0):Seq[SURVEYRECORD]=executeInChannel { channel =>
    val condition = new SURVEYRECORDLISTCONDITION
    condition.nItemFlag = survey.SURVEY_RECORD_CONDITION_STATE
    condition.szKeyWild = state.toString
    condition.nMaxToGet = limit
    NET_GAFIS_SURVEYRECORD_LIST(nDBID, nTableID, condition, nOption)
  }

  def NET_GAFIS_SURVEYRECORD_LIST_GET_BY_JIEJINGSTATE(nDBID:Short, nTableID:Short, jieJingState: Byte, limit: Int = 10, nOption: Int = 0):Seq[SURVEYRECORD]=executeInChannel { channel =>
    val condition = new SURVEYRECORDLISTCONDITION
    condition.nItemFlag = survey.SURVEY_RECORD_CONDITION_JIEJINGSTATE
    condition.szKeyWild = jieJingState.toString
    condition.nMaxToGet = limit
    NET_GAFIS_SURVEYRECORD_LIST(nDBID, nTableID, condition, nOption)
  }

  private def NET_GAFIS_SURVEYRECORD_LIST(nDBID:Short, nTableID:Short, condition: SURVEYRECORDLISTCONDITION, nOption: Int = 0):Seq[SURVEYRECORD]=executeInChannel { channel =>
    val pReq = createRequestHeader
    val stAns = new GNETANSWERHEADOBJECT

    pReq.nOption = nOption
    pReq.nDBID = nDBID
    pReq.nTableID = nTableID
    pReq.nOpClass = OP_CLASS_ADM.asInstanceOf[Short]
    pReq.nOpCode = OP_ADM_SURVEY_RECORD_LIST_GET.asInstanceOf[Short]
    NETOP_SENDREQ(channel, pReq)

    NETOP_SENDDATA(channel, condition)
    NETOP_RECVANS(channel, stAns)
    validateResponse(channel,stAns)

    val n = NETANS_GetLongRetVal(stAns)
    if(n > 0){
      val recordList = Range(0, n).map(x=> new SURVEYRECORD).toArray
      NETOP_RECVDATA(channel, recordList)

      recordList
    }else{
      Seq()
    }
  }

  def NET_GAFIS_SURVEYCONFIG_ADD(nDBID:Short, nTableID:Short, surveyConfig: SURVEYCONFIG, nOption: Int = 0):Unit = executeInChannel { channel =>
    NET_GAFIS_AddOrUpdate(nDBID, nTableID, OP_ADM_SURVEY_CONFIG_ADD.toShort, surveyConfig, nOption)
  }

  def NET_GAFIS_SURVEYCONFIG_UPDATE(nDBID:Short, nTableID:Short, surveyConfig: SURVEYCONFIG, nOption: Int = 0):Unit = executeInChannel { channel =>
    NET_GAFIS_AddOrUpdate(nDBID, nTableID, OP_ADM_SURVEY_CONFIG_UPDATE.toShort, surveyConfig, nOption)
  }

  def NET_GAFIS_SURVEYCONFIG_GET(nDBID:Short, nTableID:Short, nOption: Int = 0):Seq[SURVEYCONFIG] = executeInChannel { channel =>
    val pReq = createRequestHeader
    val stAns = new GNETANSWERHEADOBJECT

    pReq.nOption = nOption
    pReq.nDBID = nDBID
    pReq.nTableID = nTableID
    pReq.nOpClass = OP_CLASS_ADM.asInstanceOf[Short]
    pReq.nOpCode = OP_ADM_SURVEY_CONFIG_GET.asInstanceOf[Short]
    NETOP_SENDREQ(channel, pReq)

    NETOP_RECVANS(channel, stAns)
    val n = NETANS_GetLongRetVal(stAns)
    if(n > 0){
      val configList = Range(0, n).map(x=> new SURVEYCONFIG).toArray
      NETOP_RECVDATA(channel, configList)

      configList
    }else{
      Seq()
    }
  }

  def NET_GAFIS_SURVEYHITRESULTRECORD_ADD(nDBID:Short, nTableID:Short, surveyHitresultRecord: SURVEYHITRESULTRECORD, nOption: Int = 0):Unit = executeInChannel { channel =>
    NET_GAFIS_AddOrUpdate(nDBID, nTableID, OP_ADM_SURVEY_HITRESULT_RECORD_ADD.toShort, surveyHitresultRecord, nOption)
  }

  def NET_GAFIS_SURVEYHITRESULTRECORD_UPDATE(nDBID:Short, nTableID:Short, surveyHitresultRecord: SURVEYHITRESULTRECORD, nOption: Int = 0):Unit = executeInChannel { channel =>
    NET_GAFIS_AddOrUpdate(nDBID, nTableID, OP_ADM_SURVEY_HITRESULT_RECORD_UPDATE.toShort, surveyHitresultRecord, nOption)
  }

  def NET_GAFIS_SURVEYHITRESULTRECORD_GET_BY_STATE(nDBID:Short, nTableID:Short,state: Byte, limit: Int = 10, nOption: Int = 0):Seq[SURVEYHITRESULTRECORD] = executeInChannel { channel =>
    val pReq = createRequestHeader
    val stAns = new GNETANSWERHEADOBJECT

    pReq.nOption = nOption
    pReq.nDBID = nDBID
    pReq.nTableID = nTableID
    pReq.nOpClass = OP_CLASS_ADM.asInstanceOf[Short]
    pReq.nOpCode = OP_ADM_SURVEY_HITRESULT_RECORD_GET.asInstanceOf[Short]
    NETOP_SENDREQ(channel, pReq)

    val condition = new SURVEYHITRESULTRECORDLISTCONDITION
    condition.nItemFlag = SURVEY_HITRESULT_RECORD_CONDITION_STATE
    condition.szKeyWild = state.toString
    condition.nMaxToGet = limit
    NETOP_SENDDATA(channel, condition)

    NETOP_RECVANS(channel, stAns)
    val n = NETANS_GetLongRetVal(stAns)
    if(n > 0){
      val hitResultList = Range(0, n).map(x=> new SURVEYHITRESULTRECORD).toArray
      NETOP_RECVDATA(channel, hitResultList)

      hitResultList
    }else{
      Seq()
    }
  }

}
