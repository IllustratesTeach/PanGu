package nirvana.hall.stream.internal.adapter.daku

import java.io.File
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
import org.apache.commons.io.FileUtils
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
    val files  = FileUtils.listFiles(new File(fpt_dir),Array[String]("fpt"),true)
    val it = files.iterator()
    while (it.hasNext) {
      val fptFile = it.next()
      val fpt = FPTObject.parseOfFile(fptFile)
      val tpData = fpt.getTpDataList.get(0)
      val id = queryPersonIfById(tpData)
      if (id == null || "".equals(id)) {//不存在
        savePersonInfo(tpData)//保存人员信息
        //解压提取特征
        val fingerDataList = fpt.getTpDataList.get(0).getFingerDataList
        for (i <- 0 to fingerDataList.size()-1) {
          val finger = fingerDataList.get(i)
          val fgp = finger.getFgp.toInt
          var fgpp = finger.getFgp.toInt//parse FingerPosition
          if (fgpp > 10) fgpp -= 10
          val gafisImg = new GAFISIMAGESTRUCT
          var imgCompressMethod = finger.getImgCompressMethod
          if (finger.getImgCompressMethod.startsWith("14") || finger.getImgCompressMethod.endsWith("31"))
            imgCompressMethod = fpt4code.GAIMG_CPRMETHOD_WSQ_CODE
          gafisImg.stHead.bIsCompressed = 1
          gafisImg.stHead.nCompressMethod = getCodeFromGAFISImage(imgCompressMethod).toByte
          gafisImg.bnData = finger.getImgData
          gafisImg.stHead.nImgSize = gafisImg.bnData.length
          streamService.pushEvent(tpData.getPersonId+"_"+fgp,gafisImg,getFingerPosition(fgpp), FeatureType.FingerTemplate)
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
    println(id)
    id
  }

  //保存人员信息
  private def savePersonInfo(tpData : FPTObject.FPTtpData) : Unit = {
    val savePersonSql = "insert into gafis_person(personid,sid,seq,deletag) values(?,gafis_person_sid_seq.nextval,gafis_person_seq.nextval,1)"
    JdbcDatabase.update(savePersonSql){ps =>
      ps.setString(1,tpData.getPersonId)
    }


  }

  //fpt4code to glocdef
  private def getCodeFromGAFISImage(codeFromFpt: String): String ={
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
      case fpt4code.GAIMG_CPRMETHOD_NOCPR_CODE => //不压缩(glocdef对应代码不明确)
        glocdef.GAIMG_CPRMETHOD_JPG.toString
      case other=>
        throw new UnsupportedOperationException("%s compress not supported".format(codeFromFpt))
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
