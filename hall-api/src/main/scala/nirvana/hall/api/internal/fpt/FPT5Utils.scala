package nirvana.hall.api.internal.fpt

import java.io.{File, FileInputStream, FileOutputStream}

import org.apache.tools.zip.{ZipEntry, ZipFile, ZipOutputStream}


/**
  * Created by ssj on 2017/11/2.
  */
object FPT5Utils {
  /**
    * 判断字符串是否为空Null
    *
    * @param sourse  源字符串
    * @return
    */
  def isNull(sourse: String):  Boolean = {
    var is = false
    if(sourse == null){
      is = true
    }
    is
  }

  /**
    * xsd校验根据length 校验。
    * 去空格后判断长度
    *
    * @param sourse  源字符串
    * @param in  定义长度
    * @return
    */
  def replaceLength(sourse: String,in: Int):  Boolean = {
    var is = false
    if(sourse.replace(" ", "").length != in){
      is = true
    }
    is
  }

  /**
    *  压缩文件
    *
    * @param srcfile 待压缩的文件
    * @param basedir 压缩成zip存入的地址
    */
  def zipFile(srcfile :File , basedir :String): Unit ={
    val input = new FileInputStream(srcfile)
    val zipOut = new ZipOutputStream(new FileOutputStream(
      basedir))
    zipOut.putNextEntry(new ZipEntry(srcfile.getName()))
    var temp = 0
    while (temp != -1) {
      if(temp != 0){
        zipOut.write(temp)
      }
      temp = input.read()
    }
    input.close()
    zipOut.close()
  }

  /**
    * 解压缩zip文件到指定路径下
    * @param zipFile 源zip压缩文件
    * @param descDir 指定路径
    */
  def upzipFile( zipFile:ZipFile,  descDir:String): Unit={
    val e = zipFile.getEntries()
    val zipEntry = e.nextElement().asInstanceOf[ZipEntry]
    val input = zipFile.getInputStream(zipEntry)
    val f = new File(descDir)
    f.getParentFile.mkdir()
    f.createNewFile()
    val fos = new FileOutputStream(f)
    var temp = 0
    while (temp != -1) {
      if(temp != 0){
        fos.write(temp)
      }
      temp = input.read()
    }
    input.close()
    fos.close()
  }
}
