package nirvana.hall.extractor.jni

import java.io.File

import nirvana.hall.c.services.afiskernel
import nirvana.hall.c.services.gloclib.glocdef.{GAFISIMAGEHEADSTRUCT, GAFISIMAGESTRUCT}
import nirvana.hall.protocol.api.FPTProto.FingerFgp
import org.apache.commons.io.{FileUtils, IOUtils}
import org.jboss.netty.buffer.ChannelBuffers
import org.junit.Test

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
class NativeExtractorTest extends BaseJniTest{
  @Test
  def test_extractor_palm: Unit ={
    val img = IOUtils.toByteArray(getClass.getResourceAsStream("/tp_1.img"))
    val gafisImg = new GAFISIMAGESTRUCT
    val buffer = ChannelBuffers.wrappedBuffer(img)
    gafisImg.fromStreamReader(buffer)

    val mntBuffer = ChannelBuffers.buffer(gafisImg.stHead.getDataSize + 640)
    val mntHead = gafisImg.stHead
    mntHead.bIsCompressed = 0
    mntHead.nImgSize = 640
    mntHead.szName="FingerRHMMnt"
    mntHead.writeToStreamWriter(mntBuffer)
    val mntBytes = mntBuffer.array()
    NativeExtractor.ExtractMNT_All(img,mntBytes,null,FingerFgp.FINGER_R_THUMB.ordinal().toByte,afiskernel.EXTRACTMODE_NEW.toByte,0)
    FileUtils.writeByteArrayToFile(new File("tp_1.mnt"),mntBytes)
    val mntHead2 = new GAFISIMAGEHEADSTRUCT().fromByteArray(mntBytes)


    val expect = IOUtils.toByteArray(getClass.getResourceAsStream("/tp_1.mnt"))
    val expectHead = new GAFISIMAGEHEADSTRUCT().fromByteArray(expect)
  }
  @Test
  def test_extractor: Unit ={
    val img = IOUtils.toByteArray(getClass.getResourceAsStream("/tp_1.img"))
    val gafisImg = new GAFISIMAGESTRUCT
    val buffer = ChannelBuffers.wrappedBuffer(img)
    gafisImg.fromStreamReader(buffer)

    val mntBuffer = ChannelBuffers.buffer(gafisImg.stHead.getDataSize + 640)
    val mntHead = gafisImg.stHead
    mntHead.bIsCompressed = 0
    mntHead.nImgSize = 640
    mntHead.szName="FingerRHMMnt"
    mntHead.writeToStreamWriter(mntBuffer)
    val mntBytes = mntBuffer.array()
    NativeExtractor.ExtractMNT_All(img,mntBytes,null,FingerFgp.FINGER_R_THUMB.ordinal().toByte,afiskernel.EXTRACTMODE_NEW.toByte,0)
    FileUtils.writeByteArrayToFile(new File("tp_1.mnt"),mntBytes)
    val mntHead2 = new GAFISIMAGEHEADSTRUCT().fromByteArray(mntBytes)


    val expect = IOUtils.toByteArray(getClass.getResourceAsStream("/tp_1.mnt"))
    val expectHead = new GAFISIMAGEHEADSTRUCT().fromByteArray(expect)
  }
}
