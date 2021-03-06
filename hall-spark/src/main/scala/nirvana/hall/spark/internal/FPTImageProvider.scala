package nirvana.hall.spark.internal

import java.io.ByteArrayInputStream

import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.fpt4code._
import nirvana.hall.c.services.gfpt4lib.{FPTFile, fpt4code}
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.c.services.kernel.FPTLDataToMNTDISP
import nirvana.hall.c.services.kernel.mnt_checker_def.MNTDISPSTRUCT
import nirvana.hall.extractor.internal.FPTLatentConverter
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.FptPropertiesConverter.TemplateFingerConvert
import nirvana.hall.spark.services.SparkFunctions.{StreamEvent, _}
import nirvana.hall.spark.services._

import scala.collection.mutable.ArrayBuffer
import scala.util.control.NonFatal

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-30
  */
class FPTImageProvider extends ImageProvider{
  private lazy val imageFileServer = SysProperties.getPropertyOption("fpt.file.server")

  override def requestImageByBMP(parameter:NirvanaSparkConfig,pkId:String): Option[(StreamEvent,TemplateFingerConvert,GAFISIMAGESTRUCT,GAFISIMAGESTRUCT)] = {null}
  override def requestData(parameter: NirvanaSparkConfig, message: String): Seq[(StreamEvent, TemplateFingerConvert, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)] = {ArrayBuffer[(StreamEvent, TemplateFingerConvert, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)]()}

  override def requestImage(parameter:NirvanaSparkConfig,filePath:String): Seq[(StreamEvent,GAFISIMAGESTRUCT)] ={
    //PartitionRecordsSaver.databaseConfig=Some(parameter.db)
    //    @tailrec
    def fetchFPT(seq:Int): Seq[(StreamEvent, GAFISIMAGESTRUCT)] = {
      try {
        val data = SparkFunctions.httpClient.download(imageFileServer.get + filePath)
        val fpt = FPTFile.parseFromInputStream(new ByteArrayInputStream(data), AncientConstants.GBK_ENCODING)

        //FileUtils.writeByteArrayToFile(new File("/tmp/"+filePath.substring(filePath.lastIndexOf("/"))),data)
        /*val is = getClass.getResourceAsStream("/A2342124324222297342355.FPT")
        val fpt= FPTFile.parseFromInputStream(is)
        IOUtils.closeQuietly(is)*/

        val buffer = ArrayBuffer[(StreamEvent, GAFISIMAGESTRUCT)]()
        if (filePath.isEmpty) return buffer.toSeq
        var personId: String = null
        fpt match {
          case Left(fpt3) =>
            //assert(fpt3.fileLength.toInt == fpt3.getDataSize,"fpt3 fileLength != dataSize")
            val tpCounts = fpt3.tpCount
            var tpCount = 0
            if (tpCounts!=null && !"".equals(tpCounts))
              tpCount = tpCounts.toInt
            val lpCounts = fpt3.lpCount
            var lpCount = 0
            if (lpCounts!=null && !"".equals(lpCounts))
              lpCount = lpCounts.toInt
            if (tpCount > 0) {
              assert(tpCount == fpt3.logic3Recs.length)
              fpt3.logic3Recs.foreach { tp =>
                val fingerCount = tp.sendFingerCount.toInt
                assert(fingerCount == tp.fingers.length)
                personId = tp.personId
                assert(personId != null, "person id is null")
                val list = GafisPartitionRecordsSaver.queryFingerFgpAndFgpCaseByPersonId(personId)
                tp.fingers.foreach { tData =>
                  if (tData.imgData != null && tData.imgData.length > 0) {
                    val tBuffer = createImageEvent(filePath, personId, tData, list)
                    if (tBuffer != null)
                      buffer += tBuffer
                  }
                }
              }
            }
            if (lpCount > 0) { //process latent FPT
              fpt3.logic2Recs.foreach { lp =>
                val caseId = lp.caseId
                assert(caseId != null && !"".equals(caseId), "case id is null")
                val headL = lp.fingers.head
                var seqNo = headL.sendNo
                var cardId = lp.cardId.trim
                if (cardId == null || "".equals(cardId)) {
                  if (seqNo != null && !"".equals(seqNo)) {
                    if (seqNo.toInt < 10)
                      seqNo = "0" + seqNo
                    cardId = caseId + seqNo
                  } else
                    cardId = null
                }
                assert(cardId != null && !"".equals(cardId), "card id is null")
                if (headL != null && headL.featureCount.toInt > 0) {
                  val disp = FPTLDataToMNTDISP.convertFPT03ToMNTDISP(headL)
                  buffer += createImageLatentEvent(filePath, caseId, seqNo, cardId, disp)
                }
              }
            }

          case Right(fpt4) =>
            /*if (fpt4.fileLength.toInt != fpt4.getDataSize) {
              println("fileLength="+fpt4.fileLength.toInt+"|dataSize="+fpt4.getDataSize+"|filePath="+filePath)
            }
            assert(fpt4.fileLength.toInt == fpt4.getDataSize,"fpt4 fileLength != dataSize")*/
            val tpCounts = fpt4.tpCount
            var tpCount = 0
            if (tpCounts!=null && !"".equals(tpCounts))
              tpCount = tpCounts.toInt
            val lpCounts = fpt4.lpCount
            var lpCount = 0
            if (lpCounts!=null && !"".equals(lpCounts))
              lpCount = lpCounts.toInt
            if (tpCount > 0) {
              assert(tpCount == fpt4.logic02Recs.length)
              fpt4.logic02Recs.foreach { tp =>
                val fingerCount = tp.sendFingerCount.toInt
                assert(fingerCount == tp.fingers.length)
                personId = tp.personId
                assert(personId != null, "person id is null")
                val list = GafisPartitionRecordsSaver.queryFingerFgpAndFgpCaseByPersonId(personId)
                tp.fingers.foreach { tData =>
                  if (tData.imgData != null && tData.imgData.length > 0)
                    if (tData.imgData != null && tData.imgData.length > 0) {
                      val tBuffer = createImageEvent(filePath, personId, tData, list)
                      if (tBuffer != null)
                        buffer += tBuffer
                    }
                }
              }
            }
            if (lpCount > 0) { //process latent FPT
              fpt4.logic03Recs.foreach { lp =>
                val caseId = lp.caseId
                assert(caseId != null && !"".equals(caseId), "case id is null")
                val headL = lp.fingers.head
                var seqNo = headL.sendNo
                var cardId = lp.cardId.trim
                if (cardId == null || "".equals(cardId)) {
                  if (seqNo != null && !"".equals(seqNo)) {
                    if (seqNo.toInt < 10)
                      seqNo = "0" + seqNo
                    cardId = caseId + seqNo
                  } else
                    cardId = null
                }
                assert(cardId != null && !"".equals(cardId), "card id is null")
                if (headL != null && headL.featureCount.toInt > 0) {
                  val disp = FPTLDataToMNTDISP.convertFPT03ToMNTDISP(headL)
                  buffer += createImageLatentEvent(filePath, caseId, seqNo, cardId, disp)
                }
              }
            }
        }


        buffer.toSeq
      }catch{
        case e:CallRpcException=>
          //try 4 times to fetch fpt file
          if(seq == 4)  reportException(e)  else fetchFPT(seq+1)
        case NonFatal(e)=>
          reportException(e)
      }
    }
    def reportException(e: Throwable) = {
      e.printStackTrace(System.err)
      val event = StreamEvent(filePath, "",  FeatureType.FingerTemplate, FingerPosition.FINGER_UNDET,"","","")
      reportError(parameter, RequestRemoteFileError(event, e.toString))
      Nil
    }

    fetchFPT(1)
  }

  def createImageEvent(filePath: String, personId: String, tData: FPTFingerData, list : List[Array[Int]]): (StreamEvent,GAFISIMAGESTRUCT) = {
    val gafisImg = fpt4code.FPTFingerDataToGafisImage(tData)
    val fgp = getFingerPosition(tData.fgp.toInt)
    val fgpCase = gafisImg.stHead.bIsPlain
    val isExists = list.exists(x=> x(0) == fgp.getNumber && x(1) == fgpCase)

    if (!isExists) {
      val event = new StreamEvent(filePath, personId, FeatureType.FingerTemplate, fgp, "", "", "")
      (event, gafisImg)
    } else null
  }

  //Latent
  def createImageLatentEvent(filePath: String, caseId: String,sendNo : String, cardId : String, disp: MNTDISPSTRUCT): (StreamEvent,GAFISIMAGESTRUCT) = {
    SparkFunctions.loadExtractorJNI()
    val latentFeature = FPTLatentConverter.convert(disp)
    //println(latentFeature.nWidth)
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.nImgSize = latentFeature.toByteArray().length
    gafisImg.bnData = latentFeature.toByteArray()
    val event = new StreamEvent(filePath,"",null,null,caseId, sendNo, cardId)
    (event,gafisImg)

  }

}
