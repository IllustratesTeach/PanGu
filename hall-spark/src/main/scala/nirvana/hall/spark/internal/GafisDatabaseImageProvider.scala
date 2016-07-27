package nirvana.hall.spark.internal

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.SparkFunctions._
import nirvana.hall.spark.services.{SysProperties, ImageProvider}
import nirvana.hall.support.services.JdbcDatabase
import org.apache.commons.io.IOUtils
import org.jboss.netty.buffer.ChannelBuffers

import scala.collection.mutable.ArrayBuffer
import scala.util.control.NonFatal

/**
  * 基于数据库的图像提供
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-30
  */
class GafisDatabaseImageProvider extends ImageProvider{
  private lazy val converterExtract = SysProperties.getBoolean("extractor.converter",defaultValue = false)
  private lazy implicit val dataSource = GafisPartitionRecordsSaver.dataSource
  val querySqlByPersonId = "select t.gather_data,t.fgp,t.fgp_case from gafis_gather_finger t where t.person_id = ? and t.group_id = 1"
  val querySql = "select t.gather_data,t.fgp,t.fgp_case,t.person_id from gafis_gather_finger t where t.pk_id = ?"

  def requestImage(parameter:NirvanaSparkConfig,pkId:String): Seq[(StreamEvent,GAFISIMAGESTRUCT)] = {
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
  private def createDataImageEvent(pkId : String ,personId: String, fgp : Integer ,gafisImg: GAFISIMAGESTRUCT): (StreamEvent,GAFISIMAGESTRUCT) = {
    val event = new StreamEvent(pkId,personId, FeatureType.FingerTemplate, getFingerPosition(fgp.toInt),"","","")
    if (converterExtract) //converter new feature
      (event,gafisImg)
    else {
      if (gafisImg.stHead.nCompressMethod.toInt >= 10)
        (event, gafisImg)
      else {
        gafisImg.transformForFPT()
        val gafisImg1 = new GAFISIMAGESTRUCT
        gafisImg1.bnData = gafisImg.toByteArray()
        gafisImg1.stHead = gafisImg.stHead
        gafisImg1.stHead.nImgSize = gafisImg1.bnData.length
        (event, gafisImg1)
      }
    }
  }

}
