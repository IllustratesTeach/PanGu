package nirvana.hall.v62.internal.c.gnetlib

import nirvana.hall.c.services.gbaselib.gbasedef.GAKEYSTRUCT
import nirvana.hall.c.services.ghpcbase.gnopcode
import nirvana.hall.c.services.gloclib.glocndef.GNETANSWERHEADOBJECT
import nirvana.hall.v62.internal.AncientClientSupport
import nirvana.hall.v62.internal.c.gloclib.gaqryqueConverter
import nirvana.hall.v62.internal.c.gloclib.glocblob.GAFIS_MULTICOLDATASTRUCT

/**
  * 通用查询字段接口
  * Created by songpeng on 2017/11/13.
  */
trait gnetblob {
  this:AncientClientSupport with gnetcsr with reqansop=>

  /**
    * 根据sid读取字段pszColName值
    * @param nDBID
    * @param nTID
    * @param sid
    * @param pszColName
    * @param option
    * @return
    */
  def NET_GAFIS_COL_GetBySID(nDBID: Short, nTID: Short, sid: Long, pszColName: String, option: Int = 0): Array[Byte]={
    val stData = new GAFIS_MULTICOLDATASTRUCT
    stData.nItemCount = 1
    stData.szColName = pszColName
    NET_GAFIS_COL_MultiGetBySid(nDBID, nTID, sid, stData, option)

    stData.pData_Data
  }

  /**
    * 根据keyid查询pszColName字段值
    * @param nDBID
    * @param nTID
    * @param pszKey
    * @param pszColName
    * @param option
    * @return
    */
  def NET_GAFIS_COL_GetByKey(nDBID: Short, nTID: Short, pszKey: String, pszColName: String, option: Int = 0): Array[Byte]={
    val stData = new GAFIS_MULTICOLDATASTRUCT
    stData.nItemCount = 1
    stData.szColName = pszColName
    val stKey = new GAKEYSTRUCT
    stKey.szKey = pszKey
    NET_GAFIS_COL_MultiGetByKey(nDBID, nTID, stKey, stData, option)

    stData.pData_Data
  }
  def NET_GAFIS_COL_MultiGetBySid(nDBID: Short, nTID: Short, sid: Long, pData: GAFIS_MULTICOLDATASTRUCT, nOp: Int): Unit ={
    NET_GAFIS_COL_MultiGet(nDBID, nTID, null, sid, pData, nOp)
  }

  def NET_GAFIS_COL_MultiGetByKey(nDBID: Short, nTID: Short, key: GAKEYSTRUCT, pData: GAFIS_MULTICOLDATASTRUCT, nOp: Int): Unit ={
    NET_GAFIS_COL_MultiGet(nDBID, nTID, key, 0, pData, nOp)
  }

  /**
    * 目前只实现了单字段查询
    * @param nDBID
    * @param nTID
    * @param key
    * @param sid
    * @param pData
    * @param nOp
    */
  //uint2 nDBID, uint2 nTID, GAKEYSTRUCT *pstKey, SID_TYPE *pnSID,
  private def NET_GAFIS_COL_MultiGet(nDBID: Short, nTID: Short, key: GAKEYSTRUCT, sid: Long, pData: GAFIS_MULTICOLDATASTRUCT, nOp: Int): Unit = executeInChannel{pstCon=>
    //TODO 支持多字段查询
    pData.cbSize = pData.toByteArray().length
    var nOpCode = gnopcode.OP_COL_MULTIGETBYSID
    if(sid <= 0){
      nOpCode = gnopcode.OP_COL_MULTIGETBYKEY
    }
    val pReq = createRequestHeader
    val pAns = new GNETANSWERHEADOBJECT


    NETREQ_SetDBID(pReq, nDBID)
    NETREQ_SetTableID(pReq, nTID)
    NETREQ_SetOpClass(pReq, gnopcode.OP_CLASS_BLOB)
    NETREQ_SetOpCode(pReq, nOpCode)
    NETOP_SENDREQ(pstCon, pReq)
    NETOP_SENDDATA(pstCon, pData)
    NETOP_RECVANS(pstCon, pAns)
    validateResponse(pstCon, pAns)
    if(key != null){
      NETOP_SENDDATA(pstCon, key)
    }
    if(sid > 0){
      NETOP_SENDDATA(pstCon, gaqryqueConverter.convertLongAsSixByteArray(sid))
    }
    NETOP_RECVANS(pstCon, pAns)
    validateResponse(pstCon, pAns)
    NETOP_RECVDATA(pstCon, pData)
    //根据nDataLen读取数据，并赋值pData_Data
    if(pData.nDataLen > 0){
      pData.pData_Data = NETOP_RECVDATA(pstCon, pData.nDataLen).array()
    }

    pData.pnItemLen = NETOP_RECVDATA(pstCon, 4).readInt()
    pData.pnOffset = NETOP_RECVDATA(pstCon, 4).readInt()
    pData.pnRetVal = NETOP_RECVDATA(pstCon, 4).readInt()
  }

}
