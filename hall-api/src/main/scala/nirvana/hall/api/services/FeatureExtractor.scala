package nirvana.hall.api.services

import java.io.InputStream

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.{FingerPosition, NewFeatureTry}

/**
 * extract feature from image
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-11
 */
trait FeatureExtractor {
  /**
   * extract feature from image data
   * @param img image data
   * @param fingerPos finger position
   * @param featureType feature type
   * @return GAFISIMAGESTRUCT
   */
  def extractByGAFISIMG(img:GAFISIMAGESTRUCT,
                        fingerPos:FingerPosition,
                        featureType:FeatureType,
                        newFeatureTry: NewFeatureTry=NewFeatureTry.V1):(GAFISIMAGESTRUCT,GAFISIMAGESTRUCT)

  /**
   * extract feature from image data
   * @param imgData binary image data
   * @param fingerPos finger position
   * @param featureType feature type
   * @return feature data
   */
  def extractByGAFISIMGBinary(imgData:InputStream,
                        fingerPos:FingerPosition,
                        featureType:FeatureType,
                        newFeatureTry: NewFeatureTry=NewFeatureTry.V1):Option[(Array[Byte],Array[Byte])]

  /**
   * old feature converter to new feature
   * @param oldMnt
   */
  def ConvertMntOldToNew(oldMnt:InputStream) : Option[Array[Byte]]
}
