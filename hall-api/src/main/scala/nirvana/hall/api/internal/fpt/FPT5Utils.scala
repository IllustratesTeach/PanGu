package nirvana.hall.api.internal.fpt

import java.io.{File, FileInputStream, FileOutputStream, InputStream}

import org.apache.tools.zip.{ZipEntry, ZipFile, ZipOutputStream}


/**
  * Created by ssj on 2017/11/2.
  */
object FPT5Utils {

  /**
    *  压缩文件
    *
    * @param srcFile 待压缩的文件
    * @param basedir 压缩成zip存入的地址
    */
  def zipFile(srcFile :File , basedir :String): Unit ={
    var input:FileInputStream = null
    var zipOut:ZipOutputStream = null
    try{
      input = new FileInputStream(srcFile)
      zipOut = new ZipOutputStream(new FileOutputStream(
        basedir))
      zipOut.putNextEntry(new ZipEntry(srcFile.getName))
      var temp = 0
      while (temp != -1) {
        if(temp != 0){
          zipOut.write(temp)
        }
        temp = input.read
      }
    }finally {
      if(null != input) input.close
      if(null != zipOut) zipOut.close
    }
  }

  /**
    * 解压缩zip文件到指定路径下
    * @param zipFile 源zip压缩文件
    * @param descDir 指定路径
    */
  def unzipFile( zipFile:ZipFile,  descDir:String): Unit={
    var fos:FileOutputStream = null
    var input:InputStream = null
    try{
      val e = zipFile.getEntries
      val zipEntry = e.nextElement.asInstanceOf[ZipEntry]
      input = zipFile.getInputStream(zipEntry)
      val f = new File(descDir)
      f.getParentFile.mkdir
      f.createNewFile
      fos = new FileOutputStream(f)
      var temp = 0
      while (temp != -1) {
        if(temp != 0){
          fos.write(temp)
        }
        temp = input.read
      }
    }finally {
      if(null != input) input.close
      if(null != fos) fos.close
    }
  }
}
