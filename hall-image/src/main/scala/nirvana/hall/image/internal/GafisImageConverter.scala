package nirvana.hall.image.internal

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.nio.ByteOrder
import java.util.concurrent.atomic.AtomicBoolean
import javax.imageio.ImageIO

import nirvana.hall.c.annotations.Length
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.image.config.HallImageConfig
import nirvana.hall.image.jni.JniLoader

/**
  * Created by songpeng on 2017/1/10.
  * 处理GAFISIMAGESTRUCT，可将转换为BMP,JPG等图片格式
  * 推荐使用GAFISImageReaderSpi
  */
@deprecated
object GafisImageConverter{

  private lazy val jniInit = new AtomicBoolean(false)
  @volatile
  private var jniLoaded = false
  private def loadJNI() {
    if(jniInit.compareAndSet(false,true)) {
      JniLoader.loadJniLibrary(".",null)
      jniLoaded= true
    }
    if(!jniLoaded)
      loadJNI()
  }
  /**
    * GAFISIMAGESTRUCT转换为BMP图像数据
    * @param gafisImage
    * @return
    */
  def convertGafisImage2BMP(gafisImage: GAFISIMAGESTRUCT): Array[Byte]={
    val width = gafisImage.stHead.nWidth
    val height = gafisImage.stHead.nHeight
    val bmpHeader = initBmpHeader(width, height)
    val headerData = bmpHeader.toByteArray(byteOrder = ByteOrder.LITTLE_ENDIAN)

    val out = new ByteArrayOutputStream()
    out.write(headerData)

    if(gafisImage.stHead.bIsCompressed > 0){
      loadJNI()
      val decoder = new FirmDecoderImpl("support",new HallImageConfig)
      val decoderedImg = decoder.decode(gafisImage)
      out.write(decoderedImg.bnData)
    }else{//原图
      out.write(gafisImage.bnData)
    }

    out.toByteArray
  }

  /**
    * GAFISIMAGESTRUCT转换为jpg格式
    * @param gafisImage
    * @return
    */
  def convertGafisImage2jgp(gafisImage: GAFISIMAGESTRUCT): Array[Byte]={
    val out = new ByteArrayOutputStream()
    val bmp = convertGafisImage2BMP(gafisImage)
    val bufferedImage = ImageIO.read(new ByteArrayInputStream(bmp))
    ImageIO.write(bufferedImage, "jpg", out)

    out.toByteArray
  }

  /**
    * 构造bmp图像头信息
    * @param width
    * @param height
    * @return
    */
  def initBmpHeader(width: Int, height: Int): BMPHeader={
    val bmpHeader = new BMPHeader
    val fileHeader = bmpHeader.fileHeader
    val infoHeader = bmpHeader.infoHeader
    val bfOffBits = 1078 //bmp头信息长度
    fileHeader.bfType = "BM"
    fileHeader.bfOffBits = bfOffBits
    fileHeader.bfSize = bfOffBits + width * height
    infoHeader.biSize = 40 //infoHeader信息长度
    infoHeader.biWidth = width
    infoHeader.biHeight = height
    infoHeader.biPlanes = 1
    infoHeader.biBitCount = 8
    infoHeader.biCompression = 0
    infoHeader.biSizeImage = width * height
    infoHeader.biXPelsPerMeter = 19700 //500dpi
    infoHeader.biYPelsPerMeter = 19700

    (0 to 255).foreach{i =>
      val rgbQuad = new RGBQUAD
      rgbQuad.rgbRed = i.toByte
      rgbQuad.rgbGreen = i.toByte
      rgbQuad.rgbBlue = i.toByte
      bmpHeader.rgbQuad(i) = rgbQuad
    }

    bmpHeader
  }
}

class BMPHeader extends AncientData{
  var fileHeader: BITMAPFILEHEADER = new BITMAPFILEHEADER
  var infoHeader: BITMAPINFOHEADER = new BITMAPINFOHEADER
  @Length(256)
  var rgbQuad: Array[RGBQUAD] = new Array[RGBQUAD](256)
}

/**
  * BMP文件头
  */
class BITMAPFILEHEADER extends AncientData{
  @Length(2)
  var bfType: String = _ //位图文件的类型，必须为BM(1-2字节）
  var bfSize: Int = _ //位图文件的大小，以字节为单位（3-6字节，低位在前）
  var bfReserved1: Short= _ //位图文件保留字，必须为0(7-8字节）
  var bfReserved2: Short= _ //位图文件保留字，必须为0(9-10字节）
  var bfOffBits: Int = _ //位图数据的起始位置，以相对于位图（11-14字节，低位在前）
  //文件头的偏移量表示，以字节为单位
}

/**
  * 位图信息头
  */
class BITMAPINFOHEADER extends AncientData{
  var biSize: Int = _ //本结构所占用字节数（15-18字节）
  var biWidth: Int = _ //位图的宽度，以像素为单位（19-22字节）
  var biHeight: Int = _ //位图的高度，以像素为单位（23-26字节）
  var biPlanes: Short = _ //目标设备的级别，必须为1(27-28字节）
  var biBitCount: Short = _ //每个像素所需的位数，必须是1（双色），（29-30字节）
  //4(16色），8(256色）16(高彩色)或24（真彩色）之一
  var biCompression: Int = _ //位图压缩类型，必须是0（不压缩），（31-34字节）
  //1(BI_RLE8压缩类型）或2(BI_RLE4压缩类型）之一
  var biSizeImage: Int = _ //位图的大小(其中包含了为了补齐行数是4的倍数而添加的空字节)，以字节为单位（35-38字节）
  var biXPelsPerMeter: Int = _ //位图水平分辨率，每米像素数（39-42字节）
  var biYPelsPerMeter: Int = _ //位图垂直分辨率，每米像素数（43-46字节)
  var biClrUsed: Int = _ //位图实际使用的颜色表中的颜色数（47-50字节）
  var biClrImportant: Int = _ //位图显示过程中重要的颜色数（51-54字节）
}

/**
  * 颜色表
  */
class RGBQUAD extends AncientData{
  var rgbBlue: Byte = _ //蓝色的亮度（值范围为0-255)
  var rgbGreen: Byte = _ //绿色的亮度（值范围为0-255)
  var rgbRed: Byte = _ //红色的亮度（值范围为0-255)
  var rgbReserved: Byte = _ //保留，必须为0
}
