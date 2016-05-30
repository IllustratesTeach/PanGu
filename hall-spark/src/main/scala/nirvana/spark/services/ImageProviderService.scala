package nirvana.spark.services

import java.io.{File, ByteArrayInputStream}
import java.util.concurrent.atomic.AtomicBoolean

import com.google.protobuf.ByteString
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.fpt4code.FPTFingerData
import nirvana.hall.c.services.gfpt4lib.{FPTFile, fpt4code}
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.c.services.kernel.FPTLDataToMNTDISP
import nirvana.hall.c.services.kernel.mnt_checker_def.MNTDISPSTRUCT
import nirvana.hall.extractor.internal.FPTLatentConverter
import nirvana.hall.extractor.jni.JniLoader
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.support.services.JdbcDatabase
import nirvana.spark.config.NirvanaSparkConfig
import nirvana.spark.services.SparkFunctions._
import org.apache.commons.io.{FileUtils, IOUtils}
import org.jboss.netty.buffer.ChannelBuffers

import scala.collection.mutable.ArrayBuffer
import scala.util.control.NonFatal

/**
 * request remote fpt file
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-02-09
 */
object ImageProviderService {
  private lazy implicit val dataSource = PartitionRecordsSaver.dataSource
  val querySqlByPersonId = "select t.gather_data,t.fgp,t.fgp_case from gafis_gather_finger t where t.person_id = ?"
  val querySql = "select t.gather_data,t.fgp,t.fgp_case,t.person_id from gafis_gather_finger t where t.pk_id = ?"
  private lazy val init = new AtomicBoolean(false)
  def loadJNI() ={
    if(init.compareAndSet(false,true)) {
      JniLoader.loadJniLibrary(".",null)
    }
    init
  }
  case class RequestRemoteFileError(streamEvent: StreamEvent,message:String) extends StreamError(streamEvent) {
    override def getMessage: String = "R|"+message
  }
  def requestRemoteFile(parameter:NirvanaSparkConfig,filePath:String): Seq[(StreamEvent,GAFISIMAGESTRUCT)] ={
    //PartitionRecordsSaver.databaseConfig=Some(parameter.db)
//    @tailrec
    def fetchFPT(seq:Int): Seq[(StreamEvent, GAFISIMAGESTRUCT)] = {
      try {
        val data = SparkFunctions.httpClient.download(parameter.imageFileServer + filePath)
        val fpt = FPTFile.parseFromInputStream(new ByteArrayInputStream(data), AncientConstants.GBK_ENCODING)

        //FileUtils.writeByteArrayToFile(new File("/tmp/"+filePath.substring(filePath.lastIndexOf("/"))),data)
        /*val is = getClass.getResourceAsStream("/A2342124324222297342355.FPT")
        val fpt= FPTFile.parseFromInputStream(is)
        IOUtils.closeQuietly(is)*/

        val buffer = ArrayBuffer[(StreamEvent, GAFISIMAGESTRUCT)]()
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
                val person = PartitionRecordsSaver.queryPersonById(personId)
                if(person.isEmpty) {
                  tp.fingers.foreach { tData =>
                    if (tData.imgData != null && tData.imgData.length > 0)
                      buffer += createImageEvent(filePath, personId, tData)
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

                val person = PartitionRecordsSaver.queryPersonById(personId)
                if(person.isEmpty) {
                  tp.fingers.foreach { tData =>
                    if (tData.imgData != null && tData.imgData.length > 0)
                      buffer += createImageEvent(filePath, personId, tData)
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

  def createImageEvent(filePath: String, personId: String, tData: FPTFingerData): (StreamEvent,GAFISIMAGESTRUCT) = {
    //pt4code.FPTFingerDataToGafisImage(tData)
    val gafisImg = fpt4code.FPTFingerDataToGafisImage(tData)
    val event = new StreamEvent(filePath,personId, FeatureType.FingerTemplate, getFingerPosition(tData.fgp.toInt),"","","")
    (event,gafisImg)
  }

  def createDataImageEvent(pkId : String ,personId: String, fgp : Integer ,gafisImg: GAFISIMAGESTRUCT): (StreamEvent,GAFISIMAGESTRUCT) = {
    val event = new StreamEvent(pkId,personId, FeatureType.FingerTemplate, getFingerPosition(fgp.toInt),"","","")
    if (gafisImg.stHead.nCompressMethod.toInt>=10)
      (event,gafisImg)
    else {
      gafisImg.transformForFPT()
      val gafisImg1 = new GAFISIMAGESTRUCT
      gafisImg1.bnData = gafisImg.toByteArray()
      gafisImg1.stHead = gafisImg.stHead
      gafisImg1.stHead.nImgSize = gafisImg1.bnData.size
      (event, gafisImg1)
    }
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

  //fpg to FingerPosition
  private def getFingerPosition(fgp : Int) : FingerPosition = {
    val normalPosition = (fgp-1) % 10
    normalPosition match {
      case 0 => FingerPosition.FINGER_R_THUMB
      case 1 => FingerPosition.FINGER_R_INDEX
      case 2 => FingerPosition.FINGER_R_MIDDLE
      case 3 => FingerPosition.FINGER_R_RING
      case 4 => FingerPosition.FINGER_R_LITTLE
      case 5 => FingerPosition.FINGER_L_THUMB
      case 6 => FingerPosition.FINGER_L_INDEX
      case 7 => FingerPosition.FINGER_L_MIDDLE
      case 8 => FingerPosition.FINGER_L_RING
      case 9 => FingerPosition.FINGER_L_LITTLE
      case _ => FingerPosition.FINGER_UNDET
    }
  }

  def requestDataBaseData(parameter:NirvanaSparkConfig,pkId:String): Seq[(StreamEvent,GAFISIMAGESTRUCT)] = {
    def reportException(e: Throwable,personId:String,fgp:Integer,pkId:String) = {
      e.printStackTrace(System.err)
      val event = StreamEvent(pkId, personId,  FeatureType.FingerTemplate, getFingerPosition(fgp),"","","")
      reportError(parameter, RequestRemoteFileError(event, e.toString))
      Nil
    }
    try {
      val buffer = ArrayBuffer[(StreamEvent, GAFISIMAGESTRUCT)]()
      JdbcDatabase.queryFirst(querySql){ps=>
        ps.setString(1, pkId)
      }{rs=>
        var fgp = 0
        var personId = ""
        try {
          val data = rs.getBinaryStream("GATHER_DATA")
          val tmpFgp = rs.getInt("FGP")
          val fgpCase = rs.getString("FGP_CASE")
          personId = rs.getString("PERSON_ID")

          val gafisImg = new GAFISIMAGESTRUCT
          gafisImg.stHead.bIsPlain = fgpCase.toByte
          val gafisbuffer = ChannelBuffers.wrappedBuffer(IOUtils.toByteArray(data))
          gafisImg.fromStreamReader(gafisbuffer)

          /*gafisImg.stHead.bIsCompressed = 1
          gafisImg.stHead.bIsPlain = fgpCase.toByte
          gafisImg.stHead.nCompressMethod = glocdef.GAIMG_CPRMETHOD_WSQ.toByte
          gafisImg.bnData = IOUtils.toByteArray(data)
          gafisImg.stHead.nImgSize = gafisImg.bnData.length*/

          IOUtils.closeQuietly(data)
          if (gafisImg.stHead.nImgSize <= 0)
            throw new IllegalArgumentException("nImage is "+ gafisImg.stHead.nImgSize)
          if(tmpFgp < 1 || tmpFgp >10 )
            throw new IllegalArgumentException("invalid finger position "+ tmpFgp)

          fgp = tmpFgp

          buffer += createDataImageEvent(pkId,personId,fgp, gafisImg)
        }  catch{
          case NonFatal(e)=>
            reportException(e,personId,fgp,pkId)
        }

      }
      buffer.toSeq


    } catch{
      case NonFatal(e)=>
        reportException(e,"",-1,pkId)
    }

  }

}
