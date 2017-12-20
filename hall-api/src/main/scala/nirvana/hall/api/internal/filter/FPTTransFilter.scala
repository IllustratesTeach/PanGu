package nirvana.hall.api.internal.filter


import javax.servlet.http.HttpServletRequest

import com.google.protobuf.{ProtocolStringList}
import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{CommandResponse, RpcServerMessageFilter, RpcServerMessageHandler}
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.c.services.gfpt5lib.{FPT5File, FingerprintPackage, LatentPackage}
import nirvana.hall.protocol.api.FPTTrans
import nirvana.hall.protocol.api.FPTTrans.{ExportType, _}
import nirvana.hall.support.services.XmlLoader

import scala.collection.mutable.ArrayBuffer
/**
  * Created by yuchen on 2017/11/9.
  * 用于指纹系统7.0导出FPT
  */
class FPTTransFilter (httpServletRequest: HttpServletRequest, fPT5Service: FPT5Service) extends RpcServerMessageFilter{
  override def handle(commandRequest: BaseCommand, commandResponse: CommandResponse, handler: RpcServerMessageHandler): Boolean = {
    if(commandRequest.hasExtension(FPTImportRequest.cmd)){
      val request = commandRequest.getExtension(FPTImportRequest.cmd)
      val response = FPTImportResponse.newBuilder
      commandResponse.writeMessage(commandRequest, FPTImportResponse.cmd, response.build)
      true
    }
    else if(commandRequest.hasExtension(FPTExportRequest.cmd)){
      val request = commandRequest.getExtension(FPTExportRequest.cmd)
      val response = FPTTrans.FPTExportResponse.newBuilder
      val cardIdList = request.getCardidList
      try{
        val xmlList = export(cardIdList,request.getType)
        xmlList.foreach(response.addData(_))
        response.setStatus(ResponseStatus.OK)
      }catch {
        case ex:Exception =>
          response.setMsg(ExceptionUtil.getStackTraceInfo(ex))
          response.setStatus(ResponseStatus.FAIL)
      }
      commandResponse.writeMessage(commandRequest, FPTExportResponse.cmd, response.build())
      true
    }else{
      handler.handle(commandRequest, commandResponse)
    }
  }

  /**
    * export FPT
    * @param cardIdList
    * @param exportType
    * @return
    */
  private def export(cardIdList:ProtocolStringList,exportType: ExportType): Seq[String] ={
    val cardIdListIterator = cardIdList.iterator
    var fPT5File:FPT5File = null
    val xmlList = new ArrayBuffer[String]

    exportType match {
      case ExportType.TP =>

        while(cardIdListIterator.hasNext){
          val cardId = cardIdListIterator.next
          val fingerprintPackage = fPT5Service.getFingerprintPackage(cardId)
          fPT5File = new FPT5File
          val fingerprintPackageArray = new ArrayBuffer[FingerprintPackage]
          fingerprintPackageArray += fingerprintPackage
          fPT5File.fingerprintPackage = fingerprintPackageArray.toArray
          xmlList += XmlLoader.toXml(fPT5File)
        }

      case ExportType.LP =>

        while(cardIdListIterator.hasNext){
          val cardId = cardIdListIterator.next
          val latentPackage = fPT5Service.getLatentPackage(cardId)
          fPT5File = new FPT5File
          val latentPackageArray = new ArrayBuffer[LatentPackage]
          latentPackageArray += latentPackage
          fPT5File.latentPackage = latentPackageArray.toArray
          xmlList += XmlLoader.toXml(fPT5File)
        }

      case ExportType.CASEFINGER =>
      case ExportType.CASEPALM =>
      case ExportType.QUERY =>
      case ExportType.CANDLIST =>
      case ExportType.CANCEL =>
      case ExportType.HIT =>
    }

    xmlList.toSeq
  }
}
