package nirvana.hall.api.internal.fpt

import com.google.protobuf.ByteString
import nirvana.hall.api.internal.JniLoaderUtil
import nirvana.hall.api.services.{CaseInfoService, LPCardService, LPPalmService, TPCardService}
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.api.services.remote.HallImageRemoteService
import nirvana.hall.c.services.gfpt5lib.{FingerprintPackage, LatentPackage}
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.api.FPTProto.ImageType

import scala.collection.JavaConversions._

/**
  * Created by songpeng on 2017/11/3.
  */
class FPT5ServiceImpl(hallImageRemoteService: HallImageRemoteService,
                      tPCardService: TPCardService,
                      caseInfoService: CaseInfoService,
                      lPCardService: LPCardService,
                      lPPalmService: LPPalmService) extends FPT5Service{
  //fpt处理需要加载jni
  JniLoaderUtil.loadExtractorJNI()
  JniLoaderUtil.loadImageJNI()
  /**
    * 获取捺印信息
    * @param cardId 捺印卡号
    * @return
    */
  override def getFingerprintPackage(cardId: String): FingerprintPackage = {
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
    FPT5Converter.convertTPCard2FingerprintPackage(tpCard.build)
  }

  /**
    * 获取现场信息
    * @param cardId 现场卡号
    * @return
    */
  override def getLatentPackage(cardId: String): LatentPackage = {
    val caseInfo = caseInfoService.getCaseInfo(cardId)
    val lpCardList = caseInfo.getStrFingerIDList.map{fingerId=>
      lPCardService.getLPCard(fingerId)
    }
    val palmList = caseInfo.getStrPalmIDList.map{palmId=>
      lPPalmService.getLPCard(palmId)
    }
    FPT5Converter.convertCaseInfoAndLPCard2LatentPackage(caseInfo, lpCardList, palmList)
  }
}
