package nirvana.hall.extractor.internal

import java.awt.Image
import java.awt.color.ColorSpace
import java.awt.image.{BufferedImage, ColorConvertOp, DataBufferByte}
import java.io.{ByteArrayInputStream, InputStream}
import javax.imageio.ImageIO
import javax.imageio.spi.IIORegistry

import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.{GAFISIMAGEHEADSTRUCT, GAFISIMAGESTRUCT}
import nirvana.hall.c.services.kernel.mnt_def._
import nirvana.hall.extractor.HallExtractorConstants
import nirvana.hall.extractor.jni.NativeExtractor
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.{FingerPosition, NewFeatureTry}
import nirvana.hall.support.HallSupportConstants
import nirvana.hall.support.services.GAFISImageReaderSpi
import org.jboss.netty.buffer.ChannelBuffers

/**
 * implements FeatureExtractor
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-11
 */
class FeatureExtractorImpl extends FeatureExtractor{
  val iioRegistry = IIORegistry.getDefaultInstance
  iioRegistry.registerServiceProvider(new GAFISImageReaderSpi)

  private val COLOR_GRAY_SPACE=  ColorSpace.getInstance(ColorSpace.CS_GRAY);
  /**
   * extract feature from image data
    *
    * @param img image data
   * @param fingerPos finger position
   * @param featureType feature type
   * @return GAFISIMAGESTRUCT
   */
  override def extractByGAFISIMG(img: GAFISIMAGESTRUCT, fingerPos: FingerPosition, featureType: FeatureType,newFeatureTry: NewFeatureTry=NewFeatureTry.V1)= {
    if(img.stHead.bIsCompressed == 1)
      throw new IllegalArgumentException("compressed image unspported!")
    val imgData = img.toByteArray()
    val mntData = extractByGAFISIMGBinary(new ByteArrayInputStream(imgData),fingerPos,featureType,newFeatureTry)

    (new GAFISIMAGESTRUCT().fromByteArray(mntData.get._1),new GAFISIMAGESTRUCT().fromByteArray(mntData.get._2))
  }
  override def extractByGAFISIMGBinary(is:InputStream, fingerPos: FingerPosition, featureType: FeatureType,newFeatureTry: NewFeatureTry=NewFeatureTry.V1): Option[(Array[Byte],Array[Byte])]= {
    val image = readByteArrayAsGAFISIMAGE(is)
    val originalImgData = image.toByteArray()
    val imgHead = image.stHead

    val newFeature = newFeatureTry == NewFeatureTry.V2

    val (feature,isLatent) = featureType match {
      case FeatureType.FingerTemplate =>
        image.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte
        if(newFeature) (new FINGERMNTSTRUCT_NEWTT,0) else (new FINGERMNTSTRUCT,0)
      case FeatureType.FingerLatent =>
        image.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte
        if(newFeature) (new FINGERLATMNTSTRUCT_NEWTT,1) else (new FINGERLATMNTSTRUCT,1)
      case FeatureType.PalmTemplate =>
        image.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_PALM.toByte
        (new PALMMNTSTRUCT,0)
      case FeatureType.PalmLatent =>
        image.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_PALM.toByte
        (new PALMLATMNTSTRUCT,1)
      case other =>
        throw new IllegalArgumentException("unsupported data type %s".format(other))
    }

    val mntBuffer = ChannelBuffers.buffer(imgHead.getDataSize + feature.getDataSize)
    //add head information
    //NOTICE don't use imageHead to original data
    imgHead.bIsCompressed = 0
    imgHead.nCompressMethod = 0
    imgHead.nImgSize = feature.getDataSize
    imgHead.nFingerIndex = fingerPos.getNumber.toByte
    if(fingerPos.getNumber >0)
      imgHead.szName = HallExtractorConstants.MNT_NAMES(fingerPos.getNumber - 1)
    imgHead.writeToStreamWriter(mntBuffer)

    val mntData = mntBuffer.array()

    val sbinHead = new GAFISIMAGEHEADSTRUCT
    val binBuffer = ChannelBuffers.buffer(20480)
    sbinHead.writeToStreamWriter(binBuffer)
    val binData = binBuffer.array()

    if(newFeature)
      NativeExtractor.ExtractMNT_AllWithNewFeature(originalImgData,mntData, binData,fingerPos.getNumber.toByte, 0.toByte,isLatent.toByte)
    else
      NativeExtractor.ExtractMNT_All(originalImgData,mntData, binData,fingerPos.getNumber.toByte, 0.toByte,isLatent.toByte)
    sbinHead.fromByteArray(binData)
    val bin = ChannelBuffers.buffer(sbinHead.nImgSize+64)
    val sbinData = bin.array()
    System.arraycopy(binData,0,sbinData,0,sbinHead.nImgSize+64)
    //FileUtils.writeByteArrayToFile(new File("C:\\Users\\wangjue\\Desktop\\testImR\\mnt.mnt"),mntData)
    //FileUtils.writeByteArrayToFile(new File("C:\\Users\\wangjue\\Desktop\\testImR\\bin.bin"),sbinData)
    Some((mntData,sbinData))
  }
  private def readByteArrayAsGAFISIMAGE(imgData:InputStream): GAFISIMAGESTRUCT ={
    val img = ImageIO.read(imgData)
    val grayImg = img.getColorModel.getColorSpace match{
      case COLOR_GRAY_SPACE=>
        img
      case other=>
        val dstImage = new BufferedImage(img.getWidth, img.getHeight, img.getType);
        val colorConvertOp = new ColorConvertOp(COLOR_GRAY_SPACE, null);
        colorConvertOp.filter(img, dstImage)
        dstImage
    }

    val originalHeadObject = img.getProperty(HallSupportConstants.GAFIS_IMG_HEAD_KEY)
    val gafisImg = new GAFISIMAGESTRUCT
    val dataBuffer = img.getRaster.getDataBuffer.asInstanceOf[DataBufferByte].getData
    if(originalHeadObject == null || originalHeadObject == Image.UndefinedProperty) {
      gafisImg.stHead.nResolution = 500
      gafisImg.stHead.nWidth = grayImg.getWidth.toShort
      gafisImg.stHead.nHeight = grayImg.getHeight.toShort
      gafisImg.stHead.nBits = grayImg.getColorModel.getPixelSize.toByte
      gafisImg.stHead.nImgSize = grayImg.getWidth * grayImg.getHeight
      gafisImg.bnData = dataBuffer
    }else{
      val originalHead = originalHeadObject.asInstanceOf[GAFISIMAGEHEADSTRUCT]
      gafisImg.bnData = new Array[Byte](originalHead.nImgSize)
      gafisImg.stHead = originalHead
      System.arraycopy(dataBuffer,0,gafisImg.bnData,0,originalHead.nImgSize)
    }


    gafisImg
  }
}
