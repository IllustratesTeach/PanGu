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
class ImageEncoderImpl extends ImageEncoder{
  override def encode(imageCompressRequest: ImageCompressRequest): GAFISIMAGESTRUCT = {
    val method = imageCompressRequest.getCompressMethod
    val imageData = imageCompressRequest.getOriginalData.toByteArray
    val image = new GAFISIMAGESTRUCT().fromByteArray(imageData)
    val destImg = new GAFISIMAGESTRUCT
    destImg.stHead.fromByteArray(image.stHead.toByteArray())
    destImg.stHead.bIsCompressed = 1
    destImg.stHead.nBits = 8

    method match{
      case ImageCompressRequest.CompressMethod.WSQ =>
        val cpr = NativeImageConverter.encodeByWSQ(image.bnData,
          image.stHead.nWidth,
          image.stHead.nHeight,
          image.stHead.nResolution,
          10)
        destImg.bnData = cpr
        destImg.stHead.nCompressMethod = glocdef.GAIMG_CPRMETHOD_WSQ
        destImg
      case other=>
        throw new IllegalArgumentException("only support wsq,"+other+" not supported")
    }
  }
}
