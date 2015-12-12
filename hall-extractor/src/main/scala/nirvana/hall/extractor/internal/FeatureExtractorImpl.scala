package nirvana.hall.extractor.internal

import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.{GAFISIMAGEHEADSTRUCT, GAFISIMAGESTRUCT}
import nirvana.hall.c.services.kernel.mnt_def.{FINGERLATMNTSTRUCT, FINGERMNTSTRUCT, PALMLATMNTSTRUCT, PALMMNTSTRUCT}
import nirvana.hall.extractor.jni.NativeExtractor
import nirvana.hall.extractor.services.ExtractorModel.{FeatureType, FingerPosition}
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.protocol.image.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.image.ExtractProto.FingerPosition
import org.jboss.netty.buffer.ChannelBuffers

/**
 * implements FeatureExtractor
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-11
 */
class FeatureExtractorImpl extends FeatureExtractor{
  /**
   * extract feature from image data
   * @param img image data
   * @param fingerPos finger position
   * @param featureType feature type
   * @return GAFISIMAGESTRUCT
   */
  override def extractByGAFISIMG(img: GAFISIMAGESTRUCT, fingerPos: FingerPosition, featureType: FeatureType): GAFISIMAGESTRUCT = {
    val imgData = img.toByteArray
    val mntData = extractByGAFISIMGBinary(imgData,fingerPos,featureType)

    new GAFISIMAGESTRUCT().fromByteArray(mntData)
  }
  override def extractByGAFISIMGBinary(imgData: Array[Byte], fingerPos: FingerPosition, featureType: FeatureType): Array[Byte]= {
    val imgHead = new GAFISIMAGEHEADSTRUCT
    imgHead.fromByteArray(imgData)
    if(imgHead.nImgSize + imgHead.getDataSize != imgData.length){
      throw new IllegalArgumentException("wrong image data")
    }

    val feature:AncientData = imgHead.nImageType match {
      case glocdef.GAIMG_IMAGETYPE_FINGER =>
        if(featureType == FeatureType.Template) new FINGERMNTSTRUCT else new FINGERLATMNTSTRUCT
      case glocdef.GAIMG_IMAGETYPE_PALM =>
        if(featureType == FeatureType.Template) new PALMMNTSTRUCT else new PALMLATMNTSTRUCT
      case other=>
        throw new IllegalArgumentException("unsupported data type %s".format(other))
    }

    val mntBuffer = ChannelBuffers.buffer(imgHead.getDataSize + feature.getDataSize)
    //add head information
    imgHead.nImgSize = feature.getDataSize
    imgHead.szName = "FingerMnt"
    imgHead.writeToStreamWriter(mntBuffer)

    val mntData = mntBuffer.array()

    NativeExtractor.ExtractMNT_All(imgData,mntData,
      fingerPos.getNumber.toByte,
      0.toByte,
      featureType.ordinal().toByte)

    mntData
  }
}
