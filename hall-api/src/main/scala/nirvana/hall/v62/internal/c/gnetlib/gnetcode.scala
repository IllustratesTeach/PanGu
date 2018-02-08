package nirvana.hall.v62.internal.c.gnetlib

import nirvana.hall.c.services.ghpcbase.gnopcode
import nirvana.hall.c.services.gloclib.gacodetb.GAFIS_CODE_ENTRYSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.GNETANSWERHEADOBJECT
import nirvana.hall.v62.internal.AncientClientSupport

/**
  * Created by yuchen on 2018/1/26.
  */
trait gnetcode {
  this:AncientClientSupport with gnetcsr with reqansop=>
  def NET_GAFIS_CODETB_ENTRY_Op(nDBID:Short,
                             nTID:Short,
                             pstCodeTableName:Array[Byte],
                             pstCode:GAFIS_CODE_ENTRYSTRUCT,
                             nOp:Int):Unit=executeInChannel{
    pstCon =>
      val pReq = createRequestHeader
      val pAns = new GNETANSWERHEADOBJECT
      NETREQ_SetOpClass(pReq, gnopcode.OP_CLASS_CODETABLE)
      NETREQ_SetOpCode(pReq, nOp)
      pReq.bnData = pstCodeTableName
      nOp match {
        case gnopcode.OP_CODETABLE_GETALL =>
          NETOP_SENDREQ(pstCon,pReq)
          NETOP_RECVANS(pstCon,pAns)
          if(NETANS_GetRetVal(pAns) <= 0){
            return
          }
          NETANS_SetRetVal(pAns,1)
          NETOP_SENDANS(pstCon,pAns)
          NETOP_RECVDATA(pstCon,pstCode)
        case gnopcode.OP_CODETABLE_ADD | gnopcode.OP_CODETABLE_DEL
          | gnopcode.OP_CODETABLE_EXIST | gnopcode.OP_CODETABLE_UPDATE =>
          NETOP_SENDREQ(pstCon,pReq)
          NETOP_SENDDATA(pstCon,pstCode)
          NETOP_RECVANS(pstCon,pAns)
          if(NETANS_GetRetVal(pAns) <= 0){
            return
          }
        case gnopcode.OP_CODETABLE_GET =>
          NETOP_SENDREQ(pstCon,pReq)
          NETOP_SENDDATA(pstCon,pstCode)
          NETOP_RECVANS(pstCon,pAns)
          if(NETANS_GetRetVal(pAns) <= 0){
            return
          }
          NETOP_RECVDATA(pstCon,pstCode)
        case gnopcode.OP_CODETABLE_DELALL =>
          NETOP_SENDREQ(pstCon,pReq)
          NETOP_RECVANS(pstCon,pAns)
          if(NETANS_GetRetVal(pAns) <= 0){
            return
          }
      }
  }

  def NET_GAFIS_CODETB_INFO(nDBID:Short,
                                nTID:Short,
                                pstCodeTableName:Array[Byte],
                                pstCode:GAFIS_CODE_ENTRYSTRUCT,
                                nOp:Int): Unit ={
    NET_GAFIS_CODETB_ENTRY_Op(nDBID,nTID,pstCodeTableName,pstCode,nOp)
  }
}
