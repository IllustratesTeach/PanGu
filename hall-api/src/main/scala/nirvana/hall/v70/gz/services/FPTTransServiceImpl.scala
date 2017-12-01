package nirvana.hall.v70.gz.services


import monad.support.services.XmlLoader
import nirvana.hall.api.services.FPTTransService
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.c.services.gfpt5lib.FPT5File
import nirvana.hall.protocol.api.FPTTrans.ExportType

/**
  * Created by yuchen on 2017/11/9.
  */
class FPTTransServiceImpl(fPT5Service: FPT5Service) extends FPTTransService{
  override def fPTImport(data: Array[Byte]): Unit = ???

  override def fPTExport(cardId: String, exportType: ExportType): String = {
    val fPT5File = new FPT5File

    exportType.getNumber match{
      case ExportType.TP_VALUE =>
        fPT5File.fingerprintPackage :+ (fPT5Service.getFingerprintPackage(cardId))
      case ExportType.LP_VALUE =>
        fPT5File.latentPackage :+ (fPT5Service.getLatentPackage(cardId))
      case ExportType.QUERY_VALUE =>
      case ExportType.CANDLIST_VALUE =>
      case ExportType.HIT_VALUE =>
      case ExportType.CANCEL_VALUE =>
      case _ =>
    }
    val fptObject = fPT5File.build(fPT5File)
    fptObject.packageHead.originSystem = fPT5File.AFIS_SYSTEM
    XmlLoader.toXml(fptObject)
  }
}
