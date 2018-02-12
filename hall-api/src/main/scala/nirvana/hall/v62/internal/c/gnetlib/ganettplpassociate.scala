package nirvana.hall.v62.internal.c.gnetlib

import nirvana.hall.c.services.ghpcbase.gnopcode
import nirvana.hall.c.services.gloclib.gatplpassociate.GAFIS_TPLP_ASSOCIATE
import nirvana.hall.c.services.gloclib.glocndef.GNETANSWERHEADOBJECT
import nirvana.hall.v62.internal.AncientClientSupport

/**
  * Created by yuchen on 2018/2/5.
  */
trait ganettplpassociate {

  this:AncientClientSupport with reqansop=>
  def NET_GAFIS_TPLP_ASSOCIATE_Op(pstRec:GAFIS_TPLP_ASSOCIATE,
                                  nOption:Int,
                                  nOp:Int):Unit=executeInChannel{

    pstCon =>
      val pReq = createRequestHeader
      val pAns = new GNETANSWERHEADOBJECT
      NETREQ_SetOpClass(pReq, gnopcode.OP_CLASS_ADM)
      NETREQ_SetOpCode(pReq, nOp)
      NETREQ_SetOption(pReq,nOption)

      nOp match{
        case gnopcode.OP_ADM_TPLP_ASSOCIATE_ADD
             | gnopcode.OP_ADM_TPLP_ASSOCIATE_DEL
          =>
          NETOP_SENDREQ(pstCon,pReq)
          NETOP_SENDDATA(pstCon,pstRec)
          NETOP_RECVANS(pstCon,pAns)
          if(NETANS_GetRetVal(pAns) < 0){
            return
          }
        case gnopcode.OP_ADM_TPLP_ASSOCIATE_GET =>
          NETOP_SENDREQ(pstCon,pReq)
          NETOP_SENDDATA(pstCon,pstRec)
          NETOP_RECVANS(pstCon,pAns)
          if(NETANS_GetRetVal(pAns) < 0){
            return
          }
          NETOP_RECVDATA(pstCon,pstRec)
      }
  }

  def NET_GAFIS_TPLP_ASSOCIATE_ADD(pstRec:GAFIS_TPLP_ASSOCIATE,
                                   nOption:Int): Unit =
    NET_GAFIS_TPLP_ASSOCIATE_Op(pstRec,nOption,gnopcode.OP_ADM_TPLP_ASSOCIATE_ADD)

  def NET_GAFIS_TPLP_ASSOCIATE_GET(pstRec:GAFIS_TPLP_ASSOCIATE,
                                   nOption:Int): Unit =
    NET_GAFIS_TPLP_ASSOCIATE_Op(pstRec,nOption,gnopcode.OP_ADM_TPLP_ASSOCIATE_GET)

  def NET_GAFIS_TPLP_ASSOCIATE_DELETE(pstRec:GAFIS_TPLP_ASSOCIATE,
                                    nOption:Int): Unit =
    NET_GAFIS_TPLP_ASSOCIATE_Op(pstRec,nOption,gnopcode.OP_ADM_TPLP_ASSOCIATE_DEL)
}
