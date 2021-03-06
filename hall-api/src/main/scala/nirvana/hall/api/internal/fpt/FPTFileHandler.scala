package nirvana.hall.api.internal.fpt

import java.io.FileInputStream

import com.google.protobuf.{ByteString, ExtensionRegistry}
import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPT3File.FPT3File
import nirvana.hall.c.services.gfpt4lib.FPT4File.FPT4File
import nirvana.hall.c.services.gfpt4lib.{FPTFile, fpt4code}
import nirvana.hall.c.services.gfpt4lib.fpt4code.FPTFingerData
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.c.services.kernel.mnt_checker_def.MNTDISPSTRUCT
import nirvana.hall.extractor.internal.{FPTLatentConverter, FeatureExtractorImpl}
import nirvana.hall.protocol.extract.ExtractProto
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.protocol.image.FirmImageDecompressProto
import nirvana.hall.protocol.image.FirmImageDecompressProto.{FirmImageDecompressRequest, FirmImageDecompressResponse}
import nirvana.hall.support.internal.RpcHttpClientImpl

/**
  * Created by yuchen on 2016/12/7.
  * FPT文件处理类，其中包括：FPT文件解析、获取图像、解压图像获得原图、提特征
  */
object FPTFileHandler {

  private lazy val extractor = new FeatureExtractorImpl
  /**
    * FPT文件解析
    *
    * @param fileInputStream
    * @return
    */
  def FPTFileParse(fileInputStream: FileInputStream): Either[FPT3File, FPT4File] = {
    FPTFile.parseFromInputStream(fileInputStream, AncientConstants.GBK_ENCODING)
  }


  /**
    * 调用hall-image服务_解压图像
    */
  def callHallImageDecompressionImage(imageDecompressUrl:String,compressedImg: GAFISIMAGESTRUCT): GAFISIMAGESTRUCT = {

    val registry = ExtensionRegistry.newInstance()
    FirmImageDecompressProto.registerAllExtensions(registry)
    ExtractProto.registerAllExtensions(registry)

    val httpClient = new RpcHttpClientImpl(registry)

    val request = FirmImageDecompressRequest.newBuilder()
    request.setCprData(ByteString.copyFrom(compressedImg.toByteArray()))


    val baseResponse = httpClient.call(imageDecompressUrl, FirmImageDecompressRequest.cmd, request.build())
    baseResponse.getStatus match {
      case CommandStatus.OK =>
        if (baseResponse.hasExtension(FirmImageDecompressResponse.cmd)) {
          val response = baseResponse.getExtension(FirmImageDecompressResponse.cmd)
          val imgData = response.getOriginalData
          val gafisImg = new GAFISIMAGESTRUCT
          val is = imgData.newInput()
          gafisImg.fromStreamReader(is)
        } else {
          throw new IllegalAccessException("response haven't FirmImageDecompressResponse")
        }
      case CommandStatus.FAIL =>
        throw new IllegalAccessException("fail to decompress,server message:%s".format(baseResponse.getMsg))
    }
  }

  /**
    * 指纹数据转换为Gafis结构
    *
    * @param tData 指纹数据
    * @return
    */
  def fingerDataToGafisImage(tData: FPTFingerData): GAFISIMAGESTRUCT = {
    fpt4code.FPTFingerDataToGafisImage(tData)
  }

  /**
    * 提特征
    * @param img 图像结构
    * @param fingerPos 指位
    * @param featureType 特征类型
    * @return
    */
  def extractorFeature(img: GAFISIMAGESTRUCT,fingerPos: FingerPosition, featureType: FeatureType): Option[(GAFISIMAGESTRUCT,GAFISIMAGESTRUCT)] = {
    val (mnt, bin) = extractor.extractByGAFISIMG(img, fingerPos, featureType)
    Some(mnt,bin)
  }

  def createImageLatentEvent(caseId: String, sendNo: String, cardId: String, disp: MNTDISPSTRUCT): GAFISIMAGESTRUCT = {
    val latentFeature = FPTLatentConverter.convert(disp)
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.nImgSize = latentFeature.toByteArray().length
    gafisImg.bnData = latentFeature.toByteArray()
    gafisImg
  }

  def createImageLatentEvent(disp: MNTDISPSTRUCT): Array[Byte] = {
    val latentFeature = FPTLatentConverter.convert(disp)
    latentFeature.toByteArray()
  }
}
