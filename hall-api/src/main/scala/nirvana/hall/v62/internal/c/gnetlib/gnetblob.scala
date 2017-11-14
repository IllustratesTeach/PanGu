package nirvana.hall.v62.internal.c.gnetlib

import java.nio.ByteBuffer

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

  private val GAFIS_MULTICOLDATASTRUCT_cbSize = new GAFIS_MULTICOLDATASTRUCT().toByteArray().length
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

  def NET_GAFIS_COL_UpdateBySID(nDBID: Short, nTID: Short, sid: Long, pszColName: String, pD: Array[Byte], option: Int = 0): Unit ={
    val stData = GAFIS_COL_UTIL_BuildDataForUpdate(pszColName, pD)
    NET_GAFIS_COL_MultiUpdateBySid(nDBID, nTID, sid, stData, option)
  }
  def NET_GAFIS_COL_UpdateByKey(nDBID: Short, nTID: Short, pszKey: String, pszColName: String, pD: Array[Byte], option: Int = 0): Unit ={
    val stData = GAFIS_COL_UTIL_BuildDataForUpdate(pszColName, pD)
    val stKey = new GAKEYSTRUCT
    stKey.szKey = pszKey

    NET_GAFIS_COL_MultiUpdateByKey(nDBID, nTID, stKey, stData, option)
  }

  def NET_GAFIS_COL_MultiGetBySid(nDBID: Short, nTID: Short, sid: Long, pData: GAFIS_MULTICOLDATASTRUCT, nOp: Int): Unit ={
    NET_GAFIS_COL_MultiGet(nDBID, nTID, null, sid, pData, nOp)
  }

  def NET_GAFIS_COL_MultiGetByKey(nDBID: Short, nTID: Short, key: GAKEYSTRUCT, pData: GAFIS_MULTICOLDATASTRUCT, nOp: Int): Unit ={
    NET_GAFIS_COL_MultiGet(nDBID, nTID, key, 0, pData, nOp)
  }
  def NET_GAFIS_COL_MultiUpdateBySid(nDBID: Short, nTID: Short, sid: Long, pData: GAFIS_MULTICOLDATASTRUCT, nOp: Int): Unit ={
    NET_GAFIS_COL_MultiUpdate(nDBID, nTID, null, sid, pData, nOp)
  }

  def NET_GAFIS_COL_MultiUpdateByKey(nDBID: Short, nTID: Short, key: GAKEYSTRUCT, pData: GAFIS_MULTICOLDATASTRUCT, nOp: Int): Unit ={
    NET_GAFIS_COL_MultiUpdate(nDBID, nTID, key, 0, pData, nOp)
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
    pData.cbSize = GAFIS_MULTICOLDATASTRUCT_cbSize
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
      pData.bRetValCanBeFree = 1
      pData.pData_Data = NETOP_RECVDATA(pstCon, pData.nDataLen).array()
    }

    pData.pnItemLen = NETOP_RECVDATA(pstCon, 4).readInt()
    pData.pnOffset = NETOP_RECVDATA(pstCon, 4).readInt()
    pData.pnRetVal = NETOP_RECVDATA(pstCon, 4).readInt()
  }

  /**
    * 目前只实现了单字段更新
    * @param nDBID
    * @param nTID
    * @param key
    * @param sid
    * @param pData
    * @param nOp
    */
  private def NET_GAFIS_COL_MultiUpdate(nDBID: Short, nTID: Short, key: GAKEYSTRUCT, sid: Long, pData: GAFIS_MULTICOLDATASTRUCT, nOp: Int): Unit = executeInChannel{pstCon=>
    //TODO 支持多字段更新
    var nOpCode = gnopcode.OP_COL_MULTIUPDATEBYSID
    if(sid <= 0){
      nOpCode = gnopcode.OP_COL_MULTIUPDATEBYKEY
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
    NETOP_SENDDATA(pstCon, pData.pData_Data)

    NETOP_SENDDATA(pstCon, ByteBuffer.allocate(4).putInt(pData.pnItemLen).array())
    NETOP_SENDDATA(pstCon, ByteBuffer.allocate(4).putInt(pData.pnOffset).array())
    NETOP_RECVANS(pstCon, pAns)
    validateResponse(pstCon, pAns)
    pData.bRetValCanBeFree = 1
    NETOP_SENDDATA(pstCon, ByteBuffer.allocate(4).putInt(pData.pnRetVal).array())
  }

  private def GAFIS_COL_UTIL_BuildDataForUpdate(pszColName: String, pD: Array[Byte]): GAFIS_MULTICOLDATASTRUCT={
    val pData = new GAFIS_MULTICOLDATASTRUCT
    pData.cbSize = GAFIS_MULTICOLDATASTRUCT_cbSize
    pData.szColName = pszColName
    pData.bItemLenCanBeFree = 1
    pData.bOffsetCanBeFree = 1
    pData.bDataCanBeFree = 1
    pData.bRetValCanBeFree = 1
    pData.nItemCount = 1
    pData.pData_Data = pD
    pData.nDataLen = pD.length
    pData.pnItemLen = pD.length
    pData.pnRetVal = pD.length

    pData
  }
}
