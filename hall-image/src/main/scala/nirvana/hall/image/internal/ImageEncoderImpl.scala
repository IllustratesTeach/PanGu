package nirvana.hall.image.internal

import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.image.jni.NativeImageConverter
import nirvana.hall.image.services.ImageEncoder
import nirvana.hall.protocol.image.ImageCompressProto.ImageCompressRequest

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-04-15
  */
class ImageEncoderImpl(firmDecoderImpl: FirmDecoderImpl) extends ImageEncoder{
  override def encode(imageCompressRequest: ImageCompressRequest): GAFISIMAGESTRUCT = {
    val method = imageCompressRequest.getCompressMethod
    val imageData = imageCompressRequest.getOriginalData.toByteArray
    val image = new GAFISIMAGESTRUCT().fromByteArray(imageData)

    method match{
      case ImageCompressRequest.CompressMethod.WSQ =>
        encodeWSQ(image)
      case other=>
        throw new IllegalArgumentException("only support wsq,"+other+" not supported")
    }
  }

  override def encodeWSQ(gafisImage : GAFISIMAGESTRUCT) : GAFISIMAGESTRUCT = {
    var image = gafisImage
    //如果数据是压缩图，先解压图像
    if(image.stHead.bIsCompressed > 0){
      image = firmDecoderImpl.decodeByGFS(image)
    }
    val destImg = new GAFISIMAGESTRUCT
    destImg.stHead.fromByteArray(image.stHead.toByteArray())
    destImg.stHead.bIsCompressed = 1
    destImg.stHead.nBits = 8
    val cpr = NativeImageConverter.encodeByWSQ(image.bnData,
          image.stHead.nWidth,
          image.stHead.nHeight,
          image.stHead.nResolution,
          10)
    destImg.bnData = cpr
    destImg.stHead.nImgSize = destImg.bnData.length
    destImg.stHead.nCompressMethod = glocdef.GAIMG_CPRMETHOD_WSQ
    destImg
  }
}
