package nirvana.hall.extractor.internal

import java.nio.ByteOrder

import nirvana.hall.c.services.kernel.mnt_checker_def.MNTDISPSTRUCT
import nirvana.hall.c.services.kernel.mnt_def.FINGERLATMNTSTRUCT
import nirvana.hall.extractor.jni.NativeExtractor

/**
  * convert fpt latent
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-03-17
  */
object FPTLatentConverter {
  def convert(mNTDISPSTRUCT: MNTDISPSTRUCT):FINGERLATMNTSTRUCT={
    val bytes = new FINGERLATMNTSTRUCT().toByteArray()
    //此处结构传入到JNI层需要采用低字节序
    val dispBytes = mNTDISPSTRUCT.toByteArray(ByteOrder.LITTLE_ENDIAN)
    NativeExtractor.ConvertFPTLatentMNT2Std(dispBytes,bytes)
    new FINGERLATMNTSTRUCT().fromByteArray(bytes)
  }
}
