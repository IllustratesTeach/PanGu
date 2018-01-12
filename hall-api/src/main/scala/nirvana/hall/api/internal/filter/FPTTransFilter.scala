package nirvana.hall.api.internal.filter


import javax.servlet.http.HttpServletRequest

import com.google.protobuf.ProtocolStringList
import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{CommandResponse, RpcServerMessageFilter, RpcServerMessageHandler}
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.c.services.gfpt5lib.{FPT5File, FingerprintPackage, LatentPackage}
import nirvana.hall.protocol.api.FPTTrans
import nirvana.hall.protocol.api.FPTTrans.{ExportType, _}
import nirvana.hall.support.services.XmlLoader
import nirvana.hall.v70.gz.sys.UserServiceImpl

import scala.collection.mutable.ArrayBuffer
/**
  * Created by yuchen on 2017/11/9.
  * 用于指纹系统7.0导出FPT
  */
class FPTTransFilter (httpServletRequest: HttpServletRequest, fPT5Service: FPT5Service,userService: UserServiceImpl) extends RpcServerMessageFilter {
  override def handle(commandRequest: BaseCommand, commandResponse: CommandResponse, handler: RpcServerMessageHandler): Boolean = {
    if (commandRequest.hasExtension(FPTImportRequest.cmd)) {
      val request = commandRequest.getExtension(FPTImportRequest.cmd)
      val response = FPTImportResponse.newBuilder
      response.setMsg(importfpt(new String(request.getData.toByteArray)))
      response.setStatus(ResponseStatus.OK)
      commandResponse.writeMessage(commandRequest, FPTImportResponse.cmd, response.build)
      true
    }
    else if (commandRequest.hasExtension(FPTExportRequest.cmd)) {
      val request = commandRequest.getExtension(FPTExportRequest.cmd)
      val response = FPTTrans.FPTExportResponse.newBuilder
      val cardIdList = request.getCardidList
      try {
        val xmlList = export(cardIdList, request.getType, request.getOriginSystem, request.getUserId,request.getIfVerification)
        xmlList.foreach(response.addData(_))
        response.setStatus(ResponseStatus.OK)
      } catch {
        case ex: Exception =>
          response.addMsg(ExceptionUtil.getStackTraceInfo(ex))
          response.setStatus(ResponseStatus.FAIL)
      }
      commandResponse.writeMessage(commandRequest, FPTExportResponse.cmd, response.build())
      true
    } else {
      handler.handle(commandRequest, commandResponse)
    }
  }

  private def importfpt(xmlData: String): String = {
    var resultMsg = ""

    val originalList = XmlLoader.parseXML[FPT5File](xmlData)

    originalList.fingerprintPackage match {
      case null =>
      case any => val originalFingerList = XmlLoader.parseXML[FPT5File](xmlData, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/fpt5/fingerprint.xsd")), basePath = "/nirvana/hall/fpt5/")
        originalFingerList.fingerprintPackage.foreach {
          fingerprintPackage =>
            fPT5Service.addFingerprintPackage(fingerprintPackage)
            resultMsg += "\"successpersonid\":\"" + fingerprintPackage.descriptiveMsg.originalSystemCasePersonId + "\","
        }
    }
    originalList.latentPackage match {
      case null =>
      case any => val originalLatentList = XmlLoader.parseXML[FPT5File](xmlData, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/fpt5/latent.xsd")), basePath = "/nirvana/hall/fpt5/")
        originalLatentList.latentPackage.foreach {
          latentPackage =>
            fPT5Service.addLatentPackage(latentPackage)
            resultMsg += "\"successcaseid\":\"" + latentPackage.caseMsg.originalSystemCaseId + "\","
            latentPackage.latentFingers.foreach {
              latentFinger =>
                latentFinger.latentFingerFeatureMsg.foreach {
                  latentFingerFeatureMsg =>
                    resultMsg += "\"successcasefingerid\":\"" + latentFingerFeatureMsg.originalSystemLatentFingerPalmId + "\","
                }
            }
            if (null!=latentPackage.latentPalms) {
              latentPackage.latentPalms.foreach {
                latentPalm =>
                  latentPalm.latentPalmFeatureMsg.foreach {
                    latentPalmFeatureMsg =>
                      resultMsg += "\"successcasepalmid\":\"" + latentPalmFeatureMsg.latentPalmNo + "\","
                  }
              }
            }

        }
    }
    println(resultMsg)
    resultMsg
  }

  /**
    * export FPT
    *
    * @param cardIdList
    * @param exportType
    * @return
    */
  private def export(cardIdList: ProtocolStringList, exportType: ExportType, originSystem: String, userId: String, ifVerification: String): Seq[String] = {
    val cardIdListIterator = cardIdList.iterator
    var fPT5File: FPT5File = null
    val xmlList = new ArrayBuffer[String]
    val userMessage = userService.getFPT5CurrentUserMessage(userId)
    fPT5File = new FPT5File
    fPT5File.packageHead.originSystem = originSystem
    fPT5File.packageHead.sendUnitCode = userMessage.sendUnitCode
    fPT5File.packageHead.sendUnitName = userMessage.sendUnitName
    fPT5File.packageHead.sendUnitSystemType = "1900"
    fPT5File.packageHead.sendPersonName = userMessage.sendPersonName
    fPT5File.packageHead.sendPersonTel = userMessage.sendPersonTel
    fPT5File.packageHead.sendPersonIdCard = userMessage.sendPersonIdCard

    exportType match {

      case ExportType.TP =>
        while (cardIdListIterator.hasNext) {
          val cardId = cardIdListIterator.next
          val fingerprintPackage = fPT5Service.getFingerprintPackage(cardId)
          val fingerprintPackageArray = new ArrayBuffer[FingerprintPackage]
          fingerprintPackageArray += fingerprintPackage
          fPT5File.fingerprintPackage = fingerprintPackageArray.toArray
          val xmlData = XmlLoader.toXml(fPT5File)
          ifVerification match {
            case "0" =>
              val originalFingerList = XmlLoader.parseXML[FPT5File](xmlData)
            case "1" =>
              val originalFingerList = XmlLoader.parseXML[FPT5File](xmlData, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/fpt5/fingerprint.xsd")), basePath = "/nirvana/hall/fpt5/")
          }
          val originalFingerList = XmlLoader.parseXML[FPT5File](xmlData, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/fpt5/fingerprint.xsd")), basePath = "/nirvana/hall/fpt5/")
          xmlList += xmlData
        }

      case ExportType.LP =>

        while (cardIdListIterator.hasNext) {
          val cardId = cardIdListIterator.next
          val latentPackage = fPT5Service.getLatentPackage(cardId)
          val latentPackageArray = new ArrayBuffer[LatentPackage]
          latentPackageArray += latentPackage
          fPT5File.latentPackage = latentPackageArray.toArray
          val xmlData = XmlLoader.toXml(fPT5File)
          ifVerification match {

            case "0" =>
              val originalFingerList = XmlLoader.parseXML[FPT5File](xmlData)
            case "1" =>
              val originalFingerList = XmlLoader.parseXML[FPT5File](xmlData, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/fpt5/latent.xsd")), basePath = "/nirvana/hall/fpt5/")

          }
          xmlList += xmlData
        }

      case ExportType.QUERY_TT =>
      case ExportType.QUERY_TL =>
      case ExportType.QUERY_LT =>
      case ExportType.QUERY_LL =>
      case ExportType.LP_FINGER =>
    }

    xmlList.toSeq
  }
}

