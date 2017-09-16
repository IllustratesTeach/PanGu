package nirvana.hall.api.internal.remote

import com.google.protobuf.ByteString
import monad.support.services.LoggerSupport
import nirvana.hall.api.config.HallImageRemoteConfigSupport
import nirvana.hall.api.services.remote.HallImageRemoteService
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.image.config.HallImageConfig
import nirvana.hall.image.internal.{FirmDecoderImpl, ImageEncoderImpl}
import nirvana.hall.protocol.image.FirmImageDecompressProto.{FirmImageDecompressRequest, FirmImageDecompressResponse}
import nirvana.hall.protocol.image.ImageCompressProto.ImageCompressRequest.CompressMethod
import nirvana.hall.protocol.image.ImageCompressProto.{ImageCompressRequest, ImageCompressResponse}
import nirvana.hall.support.services.RpcHttpClient

/**
  * Created by songpeng on 2017/1/23.
  * gafisImage图像处理service, 调用远程接口imagedecompressurl处理数据，如过没有配置imagedecompressurl，本地处理
  */
class HallImageRemoteServiceImpl(hallImageUrl: HallImageRemoteConfigSupport, rpcHttpClient: RpcHttpClient) extends HallImageRemoteService with LoggerSupport{
  val firmDecoder = new FirmDecoderImpl("support",new HallImageConfig)
  val imageEncoder = new ImageEncoderImpl(firmDecoder)
  /**
    * 解压图像
    * @param gafisImage
    * @return
    */
  override def decodeGafisImage(gafisImage: GAFISIMAGESTRUCT): GAFISIMAGESTRUCT = {
    info("remote decodeGafisImage compressMethod(%s)".format(gafisImage.stHead.nCompressMethod))
    if(gafisImage.stHead.bIsCompressed == 0){
      return gafisImage
    }
    val fptCompressMethod = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod)
    fptCompressMethod match {
      case fpt4code.GAIMG_CPRMETHOD_EGFS_CODE
//           |
//           fpt4code.GAIMG_CPRMETHOD_WSQ_CODE |
//           fpt4code.GAIMG_CPRMETHOD_WSQ_BY_GFS_CODE
      =>
        firmDecoder.decode(gafisImage)
      case other =>
        if(hallImageUrl.hallImageUrl == null || hallImageUrl.hallImageUrl.isEmpty){
          firmDecoder.decode(gafisImage)
        }else{
          val request = FirmImageDecompressRequest.newBuilder()
          request.setCprData(ByteString.copyFrom(gafisImage.toByteArray(AncientConstants.GBK_ENCODING)))
          val response = rpcHttpClient.call(hallImageUrl.hallImageUrl, FirmImageDecompressRequest.cmd, request.build())
          val firmImageDecompressResponse = response.getExtension(FirmImageDecompressResponse.cmd)

          new GAFISIMAGESTRUCT().fromByteArray(firmImageDecompressResponse.getOriginalData.toByteArray)
        }
    }
  }

  /**
    * 将图像数据压缩为wsq
    * @param gafisImage
    * @return
    */
override def encodeGafisImage2Wsq(gafisImage: GAFISIMAGESTRUCT): GAFISIMAGESTRUCT = {
  info("remote encodeGafisImage2Wsq compressMethod(%s)".format(gafisImage.stHead.nCompressMethod))
  if(gafisImage.stHead.nCompressMethod == glocdef.GAIMG_CPRMETHOD_WSQ){
    return gafisImage
  }
  val fptCompressMethod = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod)
  fptCompressMethod match {
    case fpt4code.GAIMG_CPRMETHOD_EGFS_CODE  =>
      imageEncoder.encodeWSQ(gafisImage)
    case other =>
      if(hallImageUrl.hallImageUrl == null || hallImageUrl.hallImageUrl.isEmpty){
        imageEncoder.encodeWSQ(gafisImage)
      }else{
        val request = ImageCompressRequest.newBuilder()
        request.setOriginalData(ByteString.copyFrom(gafisImage.toByteArray(AncientConstants.GBK_ENCODING)))
        request.setCompressMethod(CompressMethod.WSQ)
        val response = rpcHttpClient.call(hallImageUrl.hallImageUrl, ImageCompressRequest.cmd, request.build())
        val imageCompressResponse= response.getExtension(ImageCompressResponse.cmd)

        new GAFISIMAGESTRUCT().fromByteArray(imageCompressResponse.getCprData.toByteArray)
      }
  }

}

  /**
    * 将图像数据压缩为GFS压缩图
    * @param gafisImage
    * @return
    */
  override def encodeGafisImage2GFS(gafisImage: GAFISIMAGESTRUCT): GAFISIMAGESTRUCT = ???
}
