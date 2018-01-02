package nirvana.hall.v62.internal.c.gnetlib

import nirvana.hall.c.services.ghpcbase.gnopcode
import nirvana.hall.c.services.gloclib.gafisusr.GAFIS_USERSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.GNETANSWERHEADOBJECT
import nirvana.hall.v62.internal.AncientClientSupport

/**
  * Created by yuchen on 2017/12/30.
  */
trait ganetuser {
  this:AncientClientSupport with gnetcsr with reqansop=>
    def NET_GAFIS_USER_UTIL_Op(nDBID:Short,
                               nTID:Short,
                               pstUser:GAFIS_USERSTRUCT,
                               nOp:Int):Unit=executeInChannel{
      pstCon =>
         val pReq = createRequestHeader
         val pAns = new GNETANSWERHEADOBJECT
         var retval:Int = -1
        if(nOp == gnopcode.OP_USER_DEL || nOp == gnopcode.OP_USER_GETUSERINFO){
          pReq.bnData = pstUser.stInfo.szName.getBytes
        }
         NETREQ_SetOpClass(pReq, gnopcode.OP_CLASS_USER);
         NETREQ_SetOpCode(pReq, nOp);
         NETOP_SENDREQ(pstCon, pReq);
        if(nOp == gnopcode.OP_USER_ADD || nOp == gnopcode.OP_USER_UPDATE){
          NETOP_SENDDATA(pstCon,pstUser)
        }
        NETOP_RECVANS(pstCon,pAns)
        retval = NETANS_GetRetVal(pAns)
        if(retval <= 0){
          return
        }
        nOp match{
          case gnopcode.OP_USER_GETUSERINFO =>
            GAFIS_NETSCR_RecvUserInfo(pstCon,pAns,pstUser)
            if(pAns.nReturnValue < 0){
              return
            }
          case gnopcode.OP_USER_DEL
                 | gnopcode.OP_USER_UPDATE
                 | gnopcode.OP_USER_ADD =>
          case _ =>

        }
    }


  def NET_GAFIS_USER_GetUserInfo(nDBID:Short,
                           nTableID:Short,
                           pstUser:GAFIS_USERSTRUCT,
                           nOption:Int=0
                          ):Unit=
    NET_GAFIS_USER_UTIL_Op(nDBID, nTableID, pstUser, gnopcode.OP_USER_GETUSERINFO);

}
