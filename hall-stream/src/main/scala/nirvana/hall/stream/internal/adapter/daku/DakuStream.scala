package nirvana.hall.stream.internal.adapter.daku

import scala.util.control.Breaks._
import java.io.{InputStreamReader, BufferedReader, File}
import java.net.{URLConnection, URL}
import java.util.Date
import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.stream.internal.adapter.daku.util.FPTObject
import nirvana.hall.stream.services.StreamService
import nirvana.hall.support.services.JdbcDatabase
import org.apache.commons.io.{FileUtils}
import org.apache.tapestry5.ioc.annotations.{EagerLoad, InjectService}

/**
 * Created by wangjue on 2015/12/29.
 */
@EagerLoad
class DakuStream (@InjectService("MntDataSource") dataSource:DataSource,streamService:StreamService)
  extends LoggerSupport{

  private implicit val ds = dataSource

  def startStream(): Unit ={
    val fpt_dir = System.getProperty(DakuSymobls.FPT_DIR)
    val fpt_file_server = System.getProperty(DakuSymobls.FPT_FILE_SERVER)//nginx 文件服务器地址
    val error_fpt_dir = System.getProperty(DakuSymobls.ERROR_FPT_DIR)
    /*val files  = FileUtils.listFiles(new File(fpt_dir),Array[String]("fpt"),true)
    val total = files.size()
    info("start stream,total files:{}",total)*/

    val files  = FileUtils.listFiles(new File(fpt_dir),Array[String]("txt"),true)
    val mask = (1 << 12) -1
    val it = files.iterator()
    var recordProcessLineFile : String = ""
    if (new File("recordProcessLine.txt").exists())
      recordProcessLineFile = FileUtils.readFileToString(new File("recordProcessLine.txt"))
    while (it.hasNext) {
      val fptFile = it.next()
      var recordLine = 0
      breakable {
        if (!recordProcessLineFile.isEmpty) {
          val fileAndLine = recordProcessLineFile.substring(recordProcessLineFile.indexOf("[") + 1, recordProcessLineFile.lastIndexOf("]"))
          val recordFile = fileAndLine.substring(0, fileAndLine.indexOf("."))
          recordLine = fileAndLine.substring(fileAndLine.lastIndexOf(":") + 1).toInt
            if (fptFile.getName.substring(0, fptFile.getName.indexOf(".")).toLong < recordFile.toLong)
              break()
        }

        val lines = FileUtils.readLines(fptFile, "UTF-8")

        info("lines total {}", lines.size())

        for (lno <- recordLine to lines.size() - 1) {
          val line = lines.get(lno)
          val path = line.mkString
          breakable {
            if (path.isEmpty)
              break
            if (path.startsWith("over") || lno == lines.size() - 1) {
              info("file {} process success!", fptFile.getName)
              //FileUtils.moveFile(fptFile, new File(fptFile.toString + "_s"))
            }
            else {
              var tempFile: File = null
              val remoteFptPath = path.substring(path.indexOf("backup") + 7)
              println(remoteFptPath)
              val remoteFpt = fpt_file_server + remoteFptPath
              var connection: URLConnection = null
              try {
                val url = new URL(remoteFpt)
                connection = url.openConnection()
                connection.setRequestProperty("accept", "0/0")
                connection.setRequestProperty("connection", "Keep-Alive")
                connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)")
                connection.setRequestProperty("Content-type", "text/html")
                connection.setRequestProperty("Accept-Charset", "GB2312")
                connection.setRequestProperty("contentType", "GB2312")
                connection.connect();
                //val in = new BufferedReader(new InputStreamReader(connection.getInputStream()))
                tempFile = new File("temp.fpt")
                FileUtils.copyInputStreamToFile(connection.getInputStream(), tempFile)
              }
              catch {
                case e: Throwable =>
                  error(e.toString, e)
                  info("FPT下载失败{}", remoteFptPath + ",error:" + e.toString)
              }

              var fpt: FPTObject = null
              try {
                fpt = FPTObject.parseOfFile(tempFile)
                tempFile.delete()


              }
              catch {
                case e: Throwable =>
                  error(e.toString, e)
                  info("FPT解析失败！ {}", remoteFptPath + ",error:" + e.toString)
                //FileUtils.copyFile(fptFile,new File(error_fpt_dir,fptFile.getName))
                /*try {


              val destFile = new File(fptFile.getAbsolutePath.replace(fpt_dir, error_fpt_dir))
              FileUtils.forceMkdir(destFile.getParentFile)
              //FileUtils.moveFile(fptFile, destFile)

              FileUtils.copyFile(fptFile, destFile)

              //val bool= FileUtils.deleteQuietly(fptFile)
              val boool = fptFile.delete()
              info("delete file 【" + fptFile.getName + "】" + boool)

            } catch {
              case e: Throwable =>
                error(e.toString, e)
                info("FPT移动失败！ {}", fptFile.getName + ",error:" + e.toString)
            }*/
              }
              try {
                if ((fpt.getTpDataList != null && fpt.getTpDataList.size() > 0) && fpt != null) {
                  val tpData = fpt.getTpDataList.get(0)
                  val fingerDataList = tpData.getFingerDataList
                  for (i <- 0 to fingerDataList.size() - 1) {
                    val finger = fingerDataList.get(i)
                    if (finger.getDataLength == 0) {
                      finger.getCenterPoint
                    }
                    val fgp = finger.getFgp.toInt
                    var fgpp = finger.getFgp.toInt //parse FingerPosition
                    if (fgpp > 10) fgpp -= 10
                    val gafisImg = new GAFISIMAGESTRUCT
                    var imgCompressMethod = finger.getImgCompressMethod
                    if (finger.getImgCompressMethod.startsWith("14") || finger.getImgCompressMethod.endsWith("31"))
                      imgCompressMethod = fpt4code.GAIMG_CPRMETHOD_WSQ_CODE
                    imgCompressMethod match {
                      case fpt4code.GAIMG_CPRMETHOD_NOCPR_CODE => //不压缩(glocdef对应代码不明确)
                        gafisImg.stHead.bIsCompressed = 0
                        gafisImg.stHead.nCompressMethod = 0.toByte
                      case other =>
                        gafisImg.stHead.bIsCompressed = 1
                        gafisImg.stHead.nCompressMethod = getCodeFromGAFISImage(imgCompressMethod, fptFile.getName).toByte
                      //gafisImg.stHead.nCompressMethod = imgCompressMethod.toByte
                    }
                    gafisImg.bnData = finger.getImgData
                    gafisImg.stHead.nWidth = finger.getImgHorizontalLength.toShort
                    gafisImg.stHead.nHeight = finger.getImgVerticalLength.toShort
                    gafisImg.stHead.nResolution = finger.getDpi.toShort
                    gafisImg.stHead.nImgSize = gafisImg.bnData.length
                    //debug("导出图像数据 {}",tpData.getPersonId+"_"+fgp)
                    //FileUtils.writeByteArrayToFile(new File("cpr\\"+tpData.getPersonId+"_"+fgp+".cpr"),gafisImg.toByteArray)

                    if (gafisImg.stHead.nImgSize == 0)
                      info("图像长度为0! {}", tpData.getPersonId + "_" + fgp)
                    else
                      streamService.pushEvent(tpData.getPersonId + "_" + fgp + "&" + fptFile.getName + ",line:" + lno, gafisImg, getFingerPosition(fgpp), FeatureType.FingerTemplate)

                  }

                }
              }
              catch {
                case e: Throwable =>
                  warn(e.toString, e)
              }

            }
          }

        }
      }
    }
  }

  private def queryPersonIfById(tpData : FPTObject.FPTtpData) : String = {
    val countSql = "select personid from gafis_person where personid = ?"
    var id = ""
    JdbcDatabase.queryWithPsSetter(countSql){ps =>
      ps.setString(1,tpData.getPersonId)
    }{rs=>
      id = rs.getString("personid")
    }
    id
  }

  //保存人员信息
  private def savePersonInfo(tpData : FPTObject.FPTtpData) : Unit = {
    val savePersonSql = "insert into gafis_person(personid,sid,seq,deletag,data_sources,fingershow_status,inputtime,gather_date,data_in) values(?,gafis_person_sid_seq.nextval,gafis_person_seq.nextval,1,5,1,sysdate,sysdate,2)"
    JdbcDatabase.update(savePersonSql){ps =>
      ps.setString(1,tpData.getPersonId)
    }


  }

  //fpt4code to glocdef
  private def getCodeFromGAFISImage(codeFromFpt: String,fptName : String): String ={
    codeFromFpt match{
      case fpt4code.GAIMG_CPRMETHOD_GA10_CODE => 	// 公安部10倍压缩方法
        glocdef.GAIMG_CPRMETHOD_GA10.toString
      case fpt4code.GAIMG_CPRMETHOD_EGFS_CODE => 	// golden
        glocdef.GAIMG_CPRMETHOD_GFS.toString
      case fpt4code.GAIMG_CPRMETHOD_PKU_CODE => 	// call pku's compress method
        glocdef.GAIMG_CPRMETHOD_PKU.toString
      case fpt4code.GAIMG_CPRMETHOD_COGENT_CODE => 	// cogent compress method
        glocdef.GAIMG_CPRMETHOD_COGENT.toString
      case fpt4code.GAIMG_CPRMETHOD_WSQ_CODE => 	// was method
        glocdef.GAIMG_CPRMETHOD_WSQ.toString
      case fpt4code.GAIMG_CPRMETHOD_NEC_CODE => 	// nec compress method
        glocdef.GAIMG_CPRMETHOD_NEC.toString
      case fpt4code.GAIMG_CPRMETHOD_TSINGHUA_CODE => 	// tsing hua
        glocdef.GAIMG_CPRMETHOD_TSINGHUA.toString
      case fpt4code.GAIMG_CPRMETHOD_BUPT_CODE => 	// beijing university of posts and telecommunications
        glocdef.GAIMG_CPRMETHOD_BUPT.toString
      case other=>
        throw new UnsupportedOperationException("%s compress not supported".format(codeFromFpt+"--fpt--"+fptName))
    }
  }

  //fpg to FingerPosition
  private def getFingerPosition(fgp : Integer) : FingerPosition = {
      fgp.intValue() match {
        case 1 => FingerPosition.FINGER_R_THUMB
        case 2 => FingerPosition.FINGER_R_INDEX
        case 3 => FingerPosition.FINGER_R_MIDDLE
        case 4 => FingerPosition.FINGER_R_RING
        case 5 => FingerPosition.FINGER_R_LITTLE
        case 6 => FingerPosition.FINGER_L_THUMB
        case 7 => FingerPosition.FINGER_L_INDEX
        case 8 => FingerPosition.FINGER_L_MIDDLE
        case 9 => FingerPosition.FINGER_L_RING
        case 10 => FingerPosition.FINGER_L_LITTLE
      }
  }

}
