package nirvana.hall.support.services

import java.awt.image._
import java.awt.{Point, Rectangle}
import java.io.IOException
import java.util
import java.util.Locale
import javax.imageio.metadata.IIOMetadata
import javax.imageio.spi.{ImageReaderSpi, ServiceRegistry}
import javax.imageio.stream.ImageInputStream
import javax.imageio.{IIOException, ImageReadParam, ImageReader, ImageTypeSpecifier}

import com.sun.imageio.plugins.bmp.BMPMetadata
import com.sun.imageio.plugins.common.{I18N, ImageUtil}
import nirvana.hall.c.services.AncientData._
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGEHEADSTRUCT
import nirvana.hall.support.HallSupportConstants

/**
 * gafis image reader spi
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-14
 */
object GAFISImageReaderSpi{
  val writerSpiNames: Array[String] = Array("nirvana.hall.extractor.internal.GAFISImageWriterSpi")
  val formatNames: Array[String] = Array("gfs", "GFS")
  val entensions: Array[String] = Array("gfs")
  val mimeType: Array[String] = Array("image/gfs")
}
class GAFISImageReaderSpi extends ImageReaderSpi(
  "Oracle Corporation",
    "1.0",
    GAFISImageReaderSpi.formatNames,
    GAFISImageReaderSpi.entensions,
    GAFISImageReaderSpi.mimeType,
    "nirvana.hall.support.services.GAFISImageReader",
  Array[Class[_]](classOf[ImageInputStream]), GAFISImageReaderSpi.writerSpiNames, false, null, null, null, null, true, BMPMetadata.nativeMetadataFormatName, "com.sun.imageio.plugins.bmp.BMPMetadataFormat", null, null)
{
  private var registered: Boolean = false

  override def onRegistration(registry: ServiceRegistry, category: Class[_]) {
    if (registered) {
      return
    }
    registered = true
  }

  def getDescription(locale: Locale): String = {
    "Standard GAFIS Image Reader"
  }

  @throws(classOf[IOException])
  def canDecodeInput(source: AnyRef): Boolean = {
    if (!source.isInstanceOf[ImageInputStream]) {
      return false
    }
    val stream: ImageInputStream = source.asInstanceOf[ImageInputStream]
    stream.mark
    val b = stream.readInt()
    stream.reset

    b == 64
  }

  @throws(classOf[IIOException])
  def createReaderInstance(extension: AnyRef): ImageReader = {
    new GAFISImageReader(this)
  }
}
class GAFISImageReader(originator:ImageReaderSpi) extends ImageReader(originator){
  private var iis:ImageInputStream= _
  private val head = new GAFISIMAGEHEADSTRUCT
  private var width:Int = _
  private var height:Int = _
  private var sampleModel:SampleModel = _
  private var colorModel:ColorModel = _

  private var bnData:Array[Byte] = _
  private var bi:BufferedImage = _
  private var gotHeader = false
  /** Indicates whether subsampled, subregion is required, and offset is
    * defined
    */
  private var noTransform: Boolean = true
  /** Indicates whether subband is selected. */
  private var seleBand: Boolean = false
  /** The scaling factors. */
  private var scaleX: Int = 0
  private var scaleY: Int = 0
  /** source and destination bands. */
  private var sourceBands: Array[Int] = null
  private var destBands: Array[Int] = null
  /** The destination region. */
  private var destinationRegion: Rectangle = null
  /** The source region. */
  private var sourceRegion: Rectangle = null

  override def getImageMetadata(imageIndex: Int): IIOMetadata = null

  override def getStreamMetadata: IIOMetadata = null

  override def read(imageIndex: Int, param1: ImageReadParam): BufferedImage = {
    checkIndex(imageIndex)
    readHeader

    //校验数据长度
    val bits= if(head.nBits < 1) 8 else head.nBits
    val imgSize = (head.nWidth * bits +7)/8 * head.nHeight
    if(imgSize != head.nImgSize){
      throw new IOException("image size (%s) != head.nImgSize(%s)".format(imgSize,head.nImgSize))
    }


    var param = getDefaultReadParam
    if (param1 != null) param = param1

    //read header
    readHeader

    sourceRegion = new Rectangle(0, 0, 0, 0)
    destinationRegion = new Rectangle(0, 0, 0, 0)

    ImageReader.computeRegions(param, head.nWidth, head.nHeight, param.getDestination, sourceRegion, destinationRegion)

    scaleX = param.getSourceXSubsampling
    scaleY = param.getSourceYSubsampling

    // If the destination band is set used it
    sourceBands = param.getSourceBands
    destBands = param.getDestinationBands

    seleBand = (sourceBands != null) && (destBands != null)
    noTransform = (destinationRegion == new Rectangle(0, 0, head.nWidth, head.nHeight)) || seleBand

    if (!seleBand) {
      val numBands = 1
      sourceBands = new Array[Int](numBands)
      destBands = new Array[Int](numBands)

      0 until numBands foreach{i=>
        sourceBands(i) = i
        destBands(i) = i
      }
    }

    // If the destination is provided, then use it.  Otherwise, create new one
    bi = param.getDestination

    // Get the image data.
    var raster: WritableRaster = null

    val properties = new util.Hashtable[String,Any]()
    if (bi == null) {
      if (sampleModel != null && colorModel != null) {
        sampleModel = sampleModel.createCompatibleSampleModel(destinationRegion.x + destinationRegion.width, destinationRegion.y + destinationRegion.height)
        if (seleBand) sampleModel = sampleModel.createSubsetSampleModel(sourceBands)
        raster = Raster.createWritableRaster(sampleModel, new Point)
        properties.put(HallSupportConstants.GAFIS_IMG_HEAD_KEY,head)
        bi = new BufferedImage(colorModel, raster, false,properties)
      }
    }
    else {
      raster = bi.getWritableTile(0, 0)
      sampleModel = bi.getSampleModel
      colorModel = bi.getColorModel
      noTransform &= (destinationRegion == raster.getBounds)
    }

    val bdata = raster.getDataBuffer.asInstanceOf[DataBufferByte].getData
    if(head.bIsCompressed == 1)
      iis.read(bdata,0,head.nImgSize)
    else
      read8Bit(bdata)

    bi
  }


  // Method to read 8 bit BMP image data
  private def read8Bit(bdata:Array[Byte]){

    // Padding bytes at the end of each scanline
    /*
    var padding = width % 4;
    if (padding != 0) {
      padding = 4 - padding;
    }
    */

    //val lineLength = width + padding;
    val lineLength = width // + padding;

    if (noTransform) {
      var j = 0

      0 until height foreach{i=>
        iis.readFully(bdata, j, width)
        j += width
        processImageUpdate(bi, 0, i,
          destinationRegion.width, 1, 1, 1,
        Array(0))
        processImageProgress(100.0F * i/destinationRegion.height);
      }
    } else {
      val buf = new Array[Byte](lineLength)
      val lineStride = sampleModel.asInstanceOf[ComponentSampleModel].getScanlineStride

      iis.skipBytes(lineLength * sourceRegion.y)

      val skipLength = lineLength * (scaleY - 1);

      var k = destinationRegion.y * lineStride;

      k += destinationRegion.x;

      var j =0
      var y = sourceRegion.y

      while(j < destinationRegion.height){

        j+=1
        y+=scaleY
        iis.read(buf, 0, lineLength)
        var m = sourceRegion.x
        0 until destinationRegion.width foreach{i=>
          bdata(k+i) = buf(m)
          m += scaleX
        }

        k +=  lineStride

        iis.skipBytes(skipLength)
        processImageUpdate(bi, 0, j,
          destinationRegion.width, 1, 1, 1,
        Array(0));
        processImageProgress(100.0F*j/destinationRegion.height)
      }
    }
  }


  /** Overrides the method defined in the superclass. */
  override def setInput(input: AnyRef, seekForwardOnly: Boolean, ignoreMetadata: Boolean) {
    super.setInput(input, seekForwardOnly, ignoreMetadata)
    iis = input.asInstanceOf[ImageInputStream]
    //if (iis != null) iis.setByteOrder(ByteOrder.LITTLE_ENDIAN)
    gotHeader = false
    readHeader
  }


  /** Overrides the method defined in the superclass. */
  @throws(classOf[IOException])
  def getNumImages(allowSearch: Boolean): Int = {
    if (iis == null) {
      throw new IllegalStateException(I18N.getString("GetNumImages0"))
    }
    if (seekForwardOnly && allowSearch) {
      throw new IllegalStateException(I18N.getString("GetNumImages1"))
    }
    return 1
  }


  private def readHeader: Unit ={
    if(!gotHeader) {
      head.fromStreamReader(iis)

      width = head.nWidth
      height = head.nHeight
      if(iis.length()>0){
        val remainingLength = iis.length() - iis.getStreamPosition
        if(height * width > (iis.length() - iis.getStreamPosition)){
          throw new IllegalStateException("height %s * width %s > bdata %s".format(height,width,remainingLength))
        }
      }

      val numBands = 1
      val bandOffsets = Array(0)
      sampleModel = new PixelInterleavedSampleModel(DataBuffer.TYPE_BYTE, width, height, numBands, numBands * width, bandOffsets)
      colorModel = ImageUtil.createColorModel(null,sampleModel)
      gotHeader = true
    }
  }

  @throws(classOf[IOException])
  def getWidth(imageIndex: Int): Int = {
    checkIndex(imageIndex)
    readHeader
    return head.nWidth
  }

  @throws(classOf[IOException])
  def getHeight(imageIndex: Int): Int = {
    checkIndex(imageIndex)
    readHeader
    return head.nHeight
  }

  private def checkIndex(imageIndex: Int) {
    if (imageIndex != 0) {
      throw new IndexOutOfBoundsException(I18N.getString("BMPImageReader0"))
    }
  }

  override def getImageTypes(imageIndex: Int): util.Iterator[ImageTypeSpecifier] = {
    checkIndex(imageIndex)
    readHeader
    val list = new util.ArrayList[ImageTypeSpecifier](1)

    list.add(new ImageTypeSpecifier(colorModel, sampleModel))
    list.iterator
  }
}