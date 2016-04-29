package nirvana.hall.extractor.internal

import com.google.protobuf.ByteString
import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{RpcServerMessageFilter, CommandResponse, RpcServerMessageHandler}
import nirvana.hall.c.services.gfpt4lib.FPT4File.FingerLData
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.c.services.kernel.FPTLDataToMNTDISP
import nirvana.hall.protocol.extract.LatentConverterExtractProto.{LatentConverterExtractResponse, LatentConverterExtractRequest}

/**
 * Created by wangjue on 2016/4/20.
 */
class LatentConverterExtractRequestFilter extends RpcServerMessageFilter {
  override def handle(commandRequest: BaseCommand, response: CommandResponse, handler: RpcServerMessageHandler): Boolean = {
    if(commandRequest.hasExtension(LatentConverterExtractRequest.cmd)){
      val extractRequest = commandRequest.getExtension(LatentConverterExtractRequest.cmd)
      //latent mnt converter
      val fingerLData = new FingerLData
      fingerLData.pattern = extractRequest.getFingerLData.getPattern
      fingerLData.fingerDirection = extractRequest.getFingerLData.getFingerDirection
      fingerLData.featureCount = extractRequest.getFingerLData.getFeatureCount
      fingerLData.fgp = extractRequest.getFingerLData.getFgp
      fingerLData.centerPoint = extractRequest.getFingerLData.getCenterPoint
      fingerLData.subCenterPoint = extractRequest.getFingerLData.getSubCenterPoint
      fingerLData.leftTriangle = extractRequest.getFingerLData.getLeftTriangle
      fingerLData.rightTriangle = extractRequest.getFingerLData.getRightTriangle
      fingerLData.feature = extractRequest.getFingerLData.getFeature
      fingerLData.imgHorizontalLength = extractRequest.getFingerLData.getImgHorizontalLength
      fingerLData.imgVerticalLength = extractRequest.getFingerLData.getImgVerticalLength
      fingerLData.dpi = extractRequest.getFingerLData.getDpi

      val disp = FPTLDataToMNTDISP.convertFPT03ToMNTDISP(fingerLData)
      val latentFeature = FPTLatentConverter.convert(disp)
      val mnt = new GAFISIMAGESTRUCT()
      mnt.bnData = latentFeature.toByteArray()
      mnt.stHead.nImgSize = 640
      val extractResponseBuilder = LatentConverterExtractResponse.newBuilder()
      extractResponseBuilder.setMntData(ByteString.copyFrom(mnt.toByteArray()))
      response.writeMessage(commandRequest,LatentConverterExtractResponse.cmd,extractResponseBuilder.build())

      true
    }else{
      handler.handle(commandRequest,response)
    }
  }
}
