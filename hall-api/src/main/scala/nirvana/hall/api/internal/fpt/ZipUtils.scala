package nirvana.hall.api.internal.fpt

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, IOException}
import java.util.zip.{GZIPInputStream, GZIPOutputStream}

import sun.misc.{BASE64Decoder, BASE64Encoder}

/**
  * 压缩工具类
  */
object ZipUtils {

  /**
    * 字符串压缩
    * @param str 待压缩的字符串
    * return 压缩后的字符串
    */
  @throws(classOf[IOException])
  def compress(str: String): String = {
    val input = new ByteArrayInputStream(str.getBytes("UTF-8"))
    val output = new ByteArrayOutputStream()
    val gout = new GZIPOutputStream(output)
    val buf = new Array[Byte](1024)
    var num = 0
    while ({num = input.read(buf); num} > 0){
      gout.write(buf, 0, num)
    }
    gout.close()
    input.close()
    val result = new BASE64Encoder().encode(output.toByteArray)
    output.close()

    result
  }

  @throws(classOf[IOException])
  def unCompress(str: String): String = {
    val output = new ByteArrayOutputStream(1024)
    val input = new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(str))
    val gzinp = new GZIPInputStream(input)

    val buf = new Array[Byte](1024)
    var num = 0
    while ({num = gzinp.read(buf); num} > 0){
      output.write(buf, 0, num)
    }
    gzinp.close()
    input.close()
    val result = new String(output.toString("UTF-8"))
    output.close()

    result
  }
}
