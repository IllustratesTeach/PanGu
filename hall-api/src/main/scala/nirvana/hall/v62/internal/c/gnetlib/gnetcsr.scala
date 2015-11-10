package nirvana.hall.v62.internal.c.gnetlib

import nirvana.hall.v62.internal.c.gloclib.gaqryque.{GAFIS_QUERYINFO, GAQUERYSTRUCT}
import nirvana.hall.v62.internal.c.gloclib.glocdef.GAFISMICSTRUCT
import nirvana.hall.v62.internal.c.gloclib.glocndef.GNETANSWERHEADOBJECT
import nirvana.hall.v62.internal.{AncientClientSupport, NoneResponse}
import nirvana.hall.v62.services.ChannelOperator

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-10
 */
trait gnetcsr {
  this: AncientClientSupport =>
  def GAFIS_NETSCR_RecvQueryInfo(channel:ChannelOperator,response:GNETANSWERHEADOBJECT,pstQry:GAQUERYSTRUCT):Unit= {
    val qry = channel.receive[GAQUERYSTRUCT]()
    // alloc memory for all data
    pstQry.nCandHeadLen = qry.nCandHeadLen
    pstQry.nCandLen = qry.nCandLen
    pstQry.nQryCondLen = qry.nQryCondLen

    pstQry.nMICCount = qry.nMICCount
    pstQry.nSvrListLen = qry.nSvrListLen
    pstQry.nMISCondLen = qry.nMISCondLen
    pstQry.nTextSqlLen = qry.nTextSqlLen
    pstQry.nCommentLen = qry.nCommentLen
    pstQry.nQryInfoLen = qry.nQryInfoLen

    val ncandhead = pstQry.nCandHeadLen
    val ncand = pstQry.nCandLen
    val nqrycond = pstQry.nQryCondLen

    val nMicCount = pstQry.nMICCount
    val nSvrListLen = pstQry.nSvrListLen
    val nMisCondLen = pstQry.nMISCondLen
    val nTxtSqlLen = pstQry.nTextSqlLen
    val nCommentLen = pstQry.nCommentLen
    val nqryinfolen = pstQry.nQryInfoLen

    if ( ncand<=0 && ncandhead<=0 && nqrycond<=0 && nMicCount<=0 && nSvrListLen<=0 &&
      nMisCondLen<=0 && nTxtSqlLen<=0 && nCommentLen<=0 && nqryinfolen<=0 ) {
      throw new IllegalArgumentException("wrong data,data is null")
    }

    if(nMicCount > 0)
    {
      pstQry.bMICCanBeFree = 1;
    }
    if(nSvrListLen > 0)
    {
      pstQry.bSvrListCanBeFree = 1;
    }

    if ( ncand >0 ) {
      pstQry.bCandCanBeFree = 1;
    }
    if ( ncandhead >0) {
      pstQry.bCandHeadCanBeFree = 1;
    }
    if ( nqrycond >0) {
      pstQry.bQryCondCanBeFree = 1;
    }
    if ( nMisCondLen>0 ) {
      pstQry.bMISCondCanBeFree = 1;
    }

    if ( nCommentLen >0) {
      pstQry.bCommentCanBeFree = 1;
    }

    if ( nTxtSqlLen >0) {
      pstQry.bTextSqlCanBeFree = 1;	// can be free.
    }

    if ( nqryinfolen >0) {
      pstQry.bQryInfoCanBeFree = 1;
    }

    response.nReturnValue = 1
    channel.writeMessage[NoneResponse](response)

    var bClearMicPt = 0
    if( nMicCount > 0)
    {
      bClearMicPt = 1;
      val mics = 0 until nMicCount map{i=>
        channel.receive[GAFISMICSTRUCT]()
      }
      pstQry.pstMIC_Data = mics.toArray
      bClearMicPt = 0
      pstQry.pstMIC_Data.foreach(GAFIS_NETSCR_RecvMICStruct(channel,_))
    }

    if( nSvrListLen > 0 )  pstQry.pstSvrList_Data = channel.receiveByteArray(nSvrListLen).array()
    if( ncandhead> 0 )  pstQry.pstSvrList_Data = channel.receiveByteArray(ncandhead).array()
    if( ncand> 0 )  pstQry.pstSvrList_Data = channel.receiveByteArray(ncand).array()

    if ( nqrycond > 0) pstQry.pstQryCond_Data = channel.receiveByteArray(nqrycond).array()
    if ( nMisCondLen >0) pstQry.pstMISCond_Data = channel.receiveByteArray(nMisCondLen).array()
    if ( nTxtSqlLen > 0) pstQry.pstTextSql_Data = channel.receiveByteArray(nTxtSqlLen).array()
    if ( nCommentLen > 0) pstQry.pszComment_Data = channel.receiveByteArray(nCommentLen).array()
    if ( nqryinfolen > 0) pstQry.pstInfo_Data = channel.receive[GAFIS_QUERYINFO]()
  }

  protected def GAFIS_NETSCR_SendQueryInfo(channel: ChannelOperator, pstQry: GAQUERYSTRUCT) {
    val ncandhead = pstQry.nCandHeadLen
    val ncand = pstQry.nCandLen
    val nqrycond = pstQry.nQryCondLen
    val nMicCount = pstQry.nMICCount;
    val nSvrListLen = pstQry.nSvrListLen;
    val nMisCondLen = pstQry.nMISCondLen;
    val nTxtSqlLen = pstQry.nTextSqlLen;
    val nCommentLen = pstQry.nCommentLen;
    // comment length.
    val nqryinfolen = pstQry.nQryInfoLen;

    if (ncand <= 0 && ncandhead <= 0 && nqrycond <= 0 && nMicCount <= 0 && nSvrListLen <= 0 &&
      nMisCondLen <= 0 && nTxtSqlLen <= 0 && nCommentLen <= 0 && nqryinfolen <= 0) {
      throw new IllegalArgumentException("data is null");
    }
    val response = channel.writeMessage[GNETANSWERHEADOBJECT](pstQry)
    validateResponse( channel,response)

    if (nMicCount > 0) {
      pstQry.pstMIC_Data.foreach(channel.writeMessage[NoneResponse](_))
      pstQry.pstMIC_Data.foreach(GAFIS_NETSCR_SendMICStruct(channel,_))
    }
    if (nSvrListLen > 0) channel.writeByteArray(pstQry.pstSvrList_Data, 0, nSvrListLen);

    if (ncandhead > 0) pstQry.pstCandHead_Data.foreach(channel.writeMessage[NoneResponse](_))
    if (ncand > 0) pstQry.pstCand_Data.foreach(channel.writeMessage[NoneResponse](_))

    if (nqrycond > 0) channel.writeByteArray[NoneResponse](pstQry.pstQryCond_Data, 0, nqrycond)
    if (nMisCondLen > 0) channel.writeByteArray[NoneResponse](pstQry.pstMISCond_Data, 0, nMisCondLen)
    if (nTxtSqlLen > 0) channel.writeByteArray[NoneResponse](pstQry.pstTextSql_Data, 0, nTxtSqlLen);
    if (nCommentLen > 0) channel.writeByteArray[NoneResponse](pstQry.pszComment_Data, 0, nCommentLen);
    if (nqryinfolen > 0) channel.writeMessage[NoneResponse](pstQry.pstInfo_Data)
  }
}
