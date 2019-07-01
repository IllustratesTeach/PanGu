package nirvana.hall.webservice.internal.greathand

import java.io.ByteArrayInputStream
import java.util
import java.util.{Date, UUID}

import cherish.component.jpa.ImageData
import monad.core.services.{CronScheduleWithStartModel, StartAtDelay}
import monad.support.services.LoggerSupport
import nirvana.hall.api.services.TPCardService
import nirvana.hall.api.services.remote.HallImageRemoteService
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.protocol.api.FPTProto.ImageType
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.webservice.KryoListConvertUtils
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.internal.greathand.jpa.{PersonInfo, SyncCronLog, Tpcardimgmnt}
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor

class ConvertImgMntService(v62Facade: V62Facade
                           , hallWebserviceConfig: HallWebserviceConfig
                           , hallImageRemoteService: HallImageRemoteService
                           , extractor: FeatureExtractor
                           , tPCardService: TPCardService) extends LoggerSupport {
  //滚动右拇
  val FINGER_R_THUMB = 1
  //滚动右食
  val FINGER_R_INDEX = 2
  //滚动右中
  val FINGER_R_MIDDLE = 3
  //滚动右环
  val FINGER_R_RING = 4
  //滚动右小
  val FINGER_R_LITTLE = 5
  //滚动左姆
  val FINGER_L_THUMB = 6
  //滚动左食
  val FINGER_L_INDEX = 7
  //滚动左中
  val FINGER_L_MIDDLE = 8
  //滚动左环
  val FINGER_L_RING = 9
  //滚动左小
  val FINGER_L_LITTLE = 10
  //平面右拇
  val TPLAIN_R_THUMB = 11
  //平面右食
  val TPLAIN_R_INDEX = 12
  //平面右中
  val TPLAIN_R_MIDDLE = 13
  //平面右环
  val TPLAIN_R_RING = 14
  //平面右小
  val TPLAIN_R_LITTLE = 15
  //平面左姆
  val TPLAIN_L_THUMB = 16
  //平面左食
  val TPLAIN_L_INDEX = 17
  //平面左中
  val TPLAIN_L_MIDDLE = 18
  //平面左环
  val TPLAIN_L_RING = 19
  //平面左小
  val TPLAIN_L_LITTLE = 20
  final val SUCCESS = "1" //成功
  final val FAIL = "0" //失败
  final val ConvertType = 2

  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if (hallWebserviceConfig.penaltyTechService.cron_tp != null) {
      periodicExecutor.addJob(new CronScheduleWithStartModel(hallWebserviceConfig.penaltyTechService.cron_tp, StartAtDelay), "SyncCronTemplateFingerService", new Runnable {
        override def run(): Unit = {
          try {
            info("ConvertImgMntService-start")
            PersonInfo.where(PersonInfo.isConvert === 0).limit(50).foreach {
              person =>
                //val bytes = KryoListConvertUtils.serializationList(JavaConversions.seqAsJavaList[ImageData](buildImgData(person)))
                val bytes = KryoListConvertUtils.serializationList(buildImgData(person))
                saveKryoImageData(bytes,person.personid)
            }
          } catch {
            case ex: Exception =>
              error("ConvertImgMntService-error:{}", ex.getMessage)
          }
        }
      })
    }
  }

  private def buildImgData(person: PersonInfo): util.List[ImageData] = {
    info("buildImgData-start:personid:{}", person.personid)
    val arrayList = new util.ArrayList[ImageData]()
    //滚指
    arrayList.add(convertImg(person.rmr, FINGER_R_THUMB,person.personid))
    arrayList.add(convertImg(person.rsr, FINGER_R_INDEX,person.personid))
    arrayList.add(convertImg(person.rzr, FINGER_R_MIDDLE,person.personid))
    arrayList.add(convertImg(person.rhr, FINGER_R_RING,person.personid))
    arrayList.add(convertImg(person.rxr, FINGER_R_LITTLE,person.personid))
    arrayList.add(convertImg(person.lmr, FINGER_L_THUMB,person.personid))
    arrayList.add(convertImg(person.lsr, FINGER_L_INDEX,person.personid))
    arrayList.add(convertImg(person.lzr, FINGER_L_MIDDLE,person.personid))
    arrayList.add(convertImg(person.lhr, FINGER_L_RING,person.personid))
    arrayList.add(convertImg(person.lxr, FINGER_L_LITTLE,person.personid))
    //平指
    arrayList.add(convertImg(person.rmp, TPLAIN_R_THUMB,person.personid))
    arrayList.add(convertImg(person.rsp, TPLAIN_R_INDEX,person.personid))
    arrayList.add(convertImg(person.rzp, TPLAIN_R_MIDDLE,person.personid))
    arrayList.add(convertImg(person.rhp, TPLAIN_R_RING,person.personid))
    arrayList.add(convertImg(person.rxp, TPLAIN_R_LITTLE,person.personid))
    arrayList.add(convertImg(person.lmp, TPLAIN_L_THUMB,person.personid))
    arrayList.add(convertImg(person.lsp, TPLAIN_L_INDEX,person.personid))
    arrayList.add(convertImg(person.lzp, TPLAIN_L_MIDDLE,person.personid))
    arrayList.add(convertImg(person.lhp, TPLAIN_L_RING,person.personid))
    arrayList.add(convertImg(person.lxp, TPLAIN_L_LITTLE,person.personid))
    info("buildImgData-end:personid:{}", person.personid)
    arrayList
  }

  def convertImg(data: Array[Byte], fgp: Int, cardId:String, imageType: ImageType = ImageType.IMAGETYPE_FINGER, result: ImageData = new ImageData()): ImageData = {
    info("convertImg-start:fgp:{}", fgp)
    try {
      if (null != data) {
        var gafisImage = new GAFISIMAGESTRUCT()
        //val is = new ByteArrayInputStream(data)
        gafisImage.fromByteArray(data)
        if (gafisImage.stHead.bIsCompressed > 0) {    //判断是否压缩
          gafisImage = hallImageRemoteService.decodeGafisImage(gafisImage)
        }
        if(gafisImage.stHead.nWidth != 640 || gafisImage.stHead.nHeight != 640){
          result.imgData = gafisImage.toByteArray()
          throw new Exception("图片大小不符合640*640")
        }else {
          val mntData = extractByGAFISIMG(gafisImage, false)
          result.mntData = mntData._1.toByteArray()
          result.imgData = gafisImage.toByteArray()
        }
      }
    } catch {
      case ex: Exception => error("convertImg-error:{}", ex.getMessage)
        new SyncCronLog(UUID.randomUUID().toString.replace("-", "")
          , cardId
          , ConvertType.toString
          , new Date()
          , FAIL
          , "cardid:"+cardId+"fgp:"+fgp+":"+ex.getMessage).save()
    } finally{
      result.fgp = fgp
    }
    info("convertImg-start:fgp:{}",fgp)
    result
  }


  private def extractByGAFISIMG(originalImage: GAFISIMAGESTRUCT, isLatent: Boolean, isPalm: Boolean = false): (GAFISIMAGESTRUCT, GAFISIMAGESTRUCT) = {
    if (!isPalm) {
      if (isLatent) {
        extractor.extractByGAFISIMG(originalImage, FingerPosition.FINGER_UNDET, FeatureType.FingerLatent)
      } else {
        val fingerIndex = originalImage.stHead.nFingerIndex
        val fingerPos = if (fingerIndex > 10) {
          FingerPosition.valueOf(fingerIndex - 10)
        } else {
          FingerPosition.valueOf(fingerIndex)
        }
        extractor.extractByGAFISIMG(originalImage, fingerPos, FeatureType.FingerTemplate)
      }
    } else {
      val ext = extractor.extractByGAFISIMGBinary(new ByteArrayInputStream(originalImage.toByteArray())
        , FingerPosition.FINGER_L_THUMB
        , FeatureType.PalmTemplate)
      (new GAFISIMAGESTRUCT().fromByteArray(ext.get._1), new GAFISIMAGESTRUCT().fromByteArray(ext.get._2))
    }
  }

  def saveKryoImageData(array:Array[Byte],personid:String): Unit = {
    info("saveKryoImageData-start:personid:{}",personid)
    try{
      val headOption = Tpcardimgmnt.where(Tpcardimgmnt.personid === personid).headOption
      if(headOption.nonEmpty){
        info("updateTpcardimgmntTable-id:{}",headOption.get.id)
        Tpcardimgmnt.update.set(imgData = array,updatetime = new Date()).where(Tpcardimgmnt.personid === personid).execute
      }else{
        val tpcardimgmnt = new Tpcardimgmnt()
        tpcardimgmnt.id = UUID.randomUUID().toString.replace("-","")
        tpcardimgmnt.personid = personid
        tpcardimgmnt.imgData = array
        tpcardimgmnt.flag = 0
        tpcardimgmnt.createtime = new Date()
        tpcardimgmnt.save()
        info("createTpcardimgmntTable-id:{}",tpcardimgmnt.id)
      }
      PersonInfo.update.set(isConvert = new Integer(1)).where(PersonInfo.personid === personid).execute
    }catch {
      case ex:Exception =>
        error("saveKryoImageData-error:personid:{}",personid,ex.getMessage)
    }
  }
}
