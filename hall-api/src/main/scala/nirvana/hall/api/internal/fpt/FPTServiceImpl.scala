package nirvana.hall.api.internal.fpt

import com.google.protobuf.ByteString
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.api.services.remote.HallImageRemoteService
import nirvana.hall.api.services.{CaseInfoService, LPCardService, TPCardService}
import nirvana.hall.c.services.gfpt4lib.FPT4File.{Logic02Rec, Logic03Rec}
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.api.FPTProto.{ImageType, LPCard}

/**
  * Created by songpeng on 2017/1/23.
  */
class FPTServiceImpl(hallImageRemoteService: HallImageRemoteService,
                     tPCardService: TPCardService,
                     caseInfoService: CaseInfoService,
                     lPCardService: LPCardService) extends FPTService{

  override def getLogic02Rec(cardId: String, dbId: Option[String]): Logic02Rec = {
    if(tPCardService.isExist(cardId)){
      val tpCard = tPCardService.getTPCard(cardId).toBuilder
      //转换数据格式wsq
      val iter = tpCard.getBlobBuilderList.iterator()
      while (iter.hasNext){
        val blob = iter.next()
        blob.getType match {
          case ImageType.IMAGETYPE_FINGER =>
            val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
            if(gafisImage.stHead.nCompressMethod != glocdef.GAIMG_CPRMETHOD_WSQ){
              val wsqImg = hallImageRemoteService.encodeGafisImage2Wsq(gafisImage)
              blob.setStImageBytes(ByteString.copyFrom(wsqImg.toByteArray()))
            }
          case other =>
        }
      }

      FPTFileBuilder.convertTPCard2Logic02Res(tpCard.build())
    }else{
      null
    }

  }

  override def getLogic03Rec(caseId: String, dbId: Option[String]): Logic03Rec = {
    val lpCardList = new scala.collection.mutable.ListBuffer[LPCard]
    if(caseId != null && caseInfoService.isExist(caseId)) {
      val caseInfo = caseInfoService.getCaseInfo(caseId)
      val fingerIdCount = caseInfo.getStrFingerIDList.size
      for (i <- 0 to fingerIdCount - 1) {
        val lPCard = lPCardService.getLPCard(caseInfo.getStrFingerID(i)).toBuilder
        lPCard.getBlob.getType match {
          case ImageType.IMAGETYPE_FINGER =>
            val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(lPCard.getBlob.getStImageBytes.toByteArray)
            if(gafisImage.stHead.nCompressMethod != glocdef.GAIMG_CPRMETHOD_WSQ){
              val wsqImg = hallImageRemoteService.encodeGafisImage2Wsq(gafisImage)
              lPCard.getBlobBuilder.setStImageBytes(ByteString.copyFrom(wsqImg.toByteArray()))
            }
          case other =>
        }
        lpCardList.append(lPCard.build())
      }

      FPTFileBuilder.convertCaseAndLPCard2Logic03Rec(caseInfo, lpCardList)
    }else{
      null
    }
  }

  override def addLogic02Res(logic02Rec: Logic02Rec): Unit = {

  }

  override def addLogic03Res(logic03Rec: Logic03Rec): Unit = {

  }
}
