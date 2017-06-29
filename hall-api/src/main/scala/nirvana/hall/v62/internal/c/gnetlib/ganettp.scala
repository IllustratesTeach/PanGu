package nirvana.hall.v62.internal.c.gnetlib

import nirvana.hall.c.services.gbaselib.gbasedef.GAKEYSTRUCT
import nirvana.hall.c.services.ghpcbase.gnopcode._
import nirvana.hall.c.services.gloclib.galoctp.{GPERSONINFOSTRUCT, GPERSONRETRSTRUCT}
import nirvana.hall.c.services.gloclib.glocndef.GNETANSWERHEADOBJECT
import nirvana.hall.v62.internal.AncientClientSupport

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-07-04
  */
trait ganettp {
  this:AncientClientSupport with gnetcsr with reqansop=>
  def NET_GAFIS_PERSON_Op(nDBID:Short,
    nTID:Short,pstPer:GPERSONINFOSTRUCT ,
    pstRetrCond:GPERSONRETRSTRUCT,
    pstKey:Array[GAKEYSTRUCT],
    nOption:Int,
    nOp:Int):Unit=executeInChannel {pstCon=>

    val pReq = createRequestHeader
    val pAns = new GNETANSWERHEADOBJECT


    NETREQ_SetOption(pReq, nOption);
    NETREQ_SetDBID(pReq, nDBID);
    NETREQ_SetTableID(pReq, nTID);
    NETREQ_SetOpClass(pReq, OP_CLASS_PERSON);
    NETREQ_SetOpCode(pReq, nOp);
    if ( pstPer != null ) {
      pReq.bnData = new Array[Byte](64)
      val pidData= pstPer.szPersonID.getBytes
      System.arraycopy(pidData,0,pReq.bnData,0,pidData.length)
      pReq.bnData(32) = pstPer.nItemFlag
      pReq.bnData(33) = pstPer.nItemFlag2
    }
    NETOP_SENDREQ(pstCon, pReq);
    nOp match {
      case	OP_PERSON_ADD | OP_PERSON_UPDATE=>
        /*
      if ( GAFIS_NETSCR_SendPersonInfo(pstCon, pReq, pAns, pstPer, &stErrIndicator)<0 ) ERRFAILFINISHEXIT();
      NETOP_RECVANS(pstCon, pAns);
      retval = NETANS_GetRetVal(pAns);
      if ( retval<0 ) stErrIndicator.bErrorAtOtherSide = 1;
      break;
      */
      case	OP_PERSON_DEL | OP_PERSON_EXIST =>
        /*
        NETOP_RECVANS(pstCon, pAns);
      retval = NETANS_GetRetVal(pAns);
      if ( retval<0 ) stErrIndicator.bErrorAtOtherSide = 1;
      break;
      */
      case	OP_PERSON_GET =>
        NETOP_RECVANS(pstCon, pAns);
        validateResponse(pstCon,pAns)
        if ( pAns.nReturnValue > 0 )
          GAFIS_NETSCR_RecvPersonInfo(pstCon, pReq, pAns, pstPer)
      case	OP_PERSON_RETR=>
        /*
        NETOP_SENDDATA(pstCon, pstRetrCond, sizeof(*pstRetrCond));
      NETOP_RECVANS(pstCon, pAns);
      n = NETANS_GetRetVal(pAns);
      if ( n<0 ) {
        stErrIndicator.bErrorAtOtherSide = 1;
        goto	Finish_Exit;
      }
      if ( n>0 ) {
        ZMALLOC_GOTOFIN(*pstKey, GAKEYSTRUCT *, sizeof(GAKEYSTRUCT)*n);
        NETOP_RECVDATA(pstCon, *pstKey, sizeof(GAKEYSTRUCT)*n);
      }
      retval = n;
      break;
      */
    }

  }
  def NET_GAFIS_PERSON_Get(nDBID:Short,
    nTableID:Short,
    pstPersonData:GPERSONINFOSTRUCT,
    nOption:Int=0
    ):Unit=
    NET_GAFIS_PERSON_Op(nDBID, nTableID, pstPersonData, null, null, nOption, OP_PERSON_GET);
}
