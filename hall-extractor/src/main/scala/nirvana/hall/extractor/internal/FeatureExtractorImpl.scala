package nirvana.hall.extractor.internal

import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.{GAFISIMAGEHEADSTRUCT, GAFISIMAGESTRUCT}
import nirvana.hall.c.services.kernel.mnt_def.{PALMLATMNTSTRUCT, PALMMNTSTRUCT, FINGERLATMNTSTRUCT, FINGERMNTSTRUCT}
import nirvana.hall.extractor.jni.NativeExtractor
import nirvana.hall.extractor.services.ExtractorModel.{ExtractMode, FeatureType, FingerPosition}
import nirvana.hall.extractor.services.FeatureExtractor
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
   * @param extractMode extract mode
   * @return GAFISIMAGESTRUCT
   */
  override def extractByGAFISIMG(img: GAFISIMAGESTRUCT, fingerPos: FingerPosition, featureType: FeatureType, extractMode: ExtractMode=ExtractMode.NEW): GAFISIMAGESTRUCT = {
    val imgData = img.toByteArray
    val mntData = extractByGAFISIMGBinary(imgData,fingerPos,featureType,extractMode)

    new GAFISIMAGESTRUCT().fromByteArray(mntData)
  }
  override def extractByGAFISIMGBinary(imgData: Array[Byte], fingerPos: FingerPosition, featureType: FeatureType, extractMode: ExtractMode=ExtractMode.NEW): Array[Byte]= {
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
      fingerPos.getValue.toByte,
      extractMode.ordinal().toByte,
      featureType.ordinal().toByte)

    mntData
  }
}
