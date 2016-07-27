package nirvana.hall.spark.services

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.c.services.kernel.mnt_def.FINGERMNTSTRUCT
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.SparkFunctions._
import nirvana.hall.spark.services.bianjian.BianjianImageProviderService.RequestRemoteFileError
import nirvana.hall.support.services.JdbcDatabase
import org.apache.commons.io.IOUtils
import org.jboss.netty.buffer.ChannelBuffers

import scala.util.control.NonFatal

/**
  * Created by wangjue on 2016/7/21.
  */
object PartitionValidData {

  lazy implicit val dataSource = SysProperties.getDataSource("gafis")
  val querySqlByPersonId = "select t.gather_data,t.fpt_path from gafis_gather_finger t where t.person_id = ? and t.group_id = 0"
  val deleteFingerByPersonId = "delete from gafis_gather_finger t where t.person_id = ?"
  def validPartitionData(parameter: NirvanaSparkConfig)(personIds:Iterator[(String)]):Unit = {
    def reportException(e: Throwable,personId:String,fgp:Integer,path:String) = {
      e.printStackTrace(System.err)
      val event = StreamEvent(path, personId,  FeatureType.FingerTemplate, FingerPosition.FINGER_UNDET,"","","")
      reportError(parameter, RequestRemoteFileError(event, e.toString))
    }
    personIds.foreach(item=> {
      val personId = item
      var fptPath = ""
      try {
        var valid = false
        JdbcDatabase.queryWithPsSetter(querySqlByPersonId){ps=>
          ps.setString(1, personId)
        }{rs=>
          try {
            fptPath = rs.getString("FPT_PATH")
            val data = rs.getBinaryStream("GATHER_DATA")
            val gafisImg = new GAFISIMAGESTRUCT
            val gafisbuffer = ChannelBuffers.wrappedBuffer(IOUtils.toByteArray(data))
            gafisImg.fromStreamReader(gafisbuffer)
            val feature = new FINGERMNTSTRUCT
            feature.fromByteArray(gafisImg.bnData)
            if (feature.nWidth != 640 || feature.nHeight != 640) {
              valid = true
            }
            IOUtils.closeQuietly(data)
          }  catch{
            case NonFatal(e)=>
              reportException(e,personId,-1,fptPath)
          }
        }
        if (valid) {
          deleteFingerByPersonId(personId)
          throw new IllegalArgumentException("the feature width or height is not 640 for "+ personId)
        }

      } catch{
        case NonFatal(e)=>
          reportException(e,personId,-1,fptPath)
      }
    })
  }

  private def deleteFingerByPersonId(personId : String): Unit = {
    JdbcDatabase.update(deleteFingerByPersonId){ps=>
      ps.setString(1,personId)
    }
  }
}
