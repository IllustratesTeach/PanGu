package nirvana.hall.extractor.internal

import java.awt.color.ColorSpace
import java.awt.image.{BufferedImage, ColorConvertOp, DataBufferByte}
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO

import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.c.services.kernel.mnt_def.{FINGERLATMNTSTRUCT, FINGERMNTSTRUCT, PALMLATMNTSTRUCT, PALMMNTSTRUCT}
import nirvana.hall.extractor.jni.NativeExtractor
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import org.jboss.netty.buffer.ChannelBuffers

/**
 * implements FeatureExtractor
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-11
 */
class FeatureExtractorImpl extends FeatureExtractor{
  private val COLOR_GRAY_SPACE=  ColorSpace.getInstance(ColorSpace.CS_GRAY);
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
    val image = readByteArrayAsGAFISIMAGE(imgData)
    val originalImgData = image.toByteArray
    val imgHead = image.stHead

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

    NativeExtractor.ExtractMNT_All(originalImgData,mntData,
      fingerPos.getNumber.toByte,
      0.toByte,
      featureType.ordinal().toByte)

    mntData
  }
  private def readByteArrayAsGAFISIMAGE(imgData:Array[Byte]): GAFISIMAGESTRUCT ={
    if(imgData(0) == 0x00 && imgData(1) == 0x40){
      new GAFISIMAGESTRUCT().fromByteArray(imgData)
    }else {
      val img = ImageIO.read(new ByteArrayInputStream(imgData))
      val grayImg = img.getColorModel.getColorSpace match{
        case COLOR_GRAY_SPACE=>
          img
        case other=>
          val dstImage = new BufferedImage(img.getWidth, img.getHeight, img.getType);
          val colorConvertOp = new ColorConvertOp(COLOR_GRAY_SPACE, null);
          colorConvertOp.filter(img, dstImage)
          dstImage
      }

      val gafisImg = new GAFISIMAGESTRUCT
      gafisImg.stHead.nResolution = 500
      //TODO determined by feature type
      gafisImg.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte
      gafisImg.stHead.nWidth = grayImg.getWidth.toShort
      gafisImg.stHead.nHeight = grayImg.getHeight.toShort
      gafisImg.stHead.nBits = grayImg.getColorModel.getPixelSize.toByte
      gafisImg.stHead.nImgSize = grayImg.getWidth * grayImg.getHeight

      //get gray image data
      val dataBuffer = img.getRaster.getDataBuffer.asInstanceOf[DataBufferByte]
      gafisImg.bnData = dataBuffer.getData

      gafisImg
    }
  }
}
