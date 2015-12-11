package nirvana.hall.extractor.services

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.services.ExtractorModel.{ExtractMode, FeatureType, FingerPosition}

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
   * @param extractMode extract mode
   * @return GAFISIMAGESTRUCT
   */
  def extractByGAFISIMG(img:GAFISIMAGESTRUCT,
                        fingerPos:FingerPosition,
                        featureType:FeatureType,
                        extractMode:ExtractMode=ExtractMode.NEW):GAFISIMAGESTRUCT

  /**
   * extract feature from image data
   * @param imgData binary image data
   * @param fingerPos finger position
   * @param featureType feature type
   * @param extractMode extract mode
   * @return feature data
   */
  def extractByGAFISIMGBinary(imgData:Array[Byte],
                        fingerPos:FingerPosition,
                        featureType:FeatureType,
                        extractMode:ExtractMode=ExtractMode.NEW):Array[Byte]
}
