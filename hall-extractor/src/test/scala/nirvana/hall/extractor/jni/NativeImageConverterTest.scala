package nirvana.hall.extractor.jni

import nirvana.hall.c.services.afiskernel
import nirvana.hall.c.services.gloclib.glocdef.{GAFISIMAGEHEADSTRUCT, GAFISIMAGESTRUCT}
import nirvana.hall.protocol.v62.FPTProto.FingerFgp
import org.apache.commons.io.IOUtils
import org.jboss.netty.buffer.ChannelBuffers
import org.junit.Test

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
class NativeImageConverterTest extends BaseJniTest{
  @Test
  def test_extractor: Unit ={
    val img = IOUtils.toByteArray(getClass.getResourceAsStream("/lf.img"))
    val gafisImg = new GAFISIMAGESTRUCT
    val buffer = ChannelBuffers.wrappedBuffer(img)
    gafisImg.fromStreamReader(buffer)

    val mntBuffer = ChannelBuffers.buffer(gafisImg.stHead.getDataSize + 640)
    val mntHead = gafisImg.stHead
    mntHead.nImgSize = 640
    mntHead.szName="FingerMnt"
    mntHead.writeToStreamWriter(mntBuffer)
    val mntBytes = mntBuffer.array()
    NativeExtractor.ExtractMNT_All(img,mntBytes,FingerFgp.FINGER_R_LITTLE.ordinal().toByte,afiskernel.EXTRACTMODE_NEW.toByte,0)
    val mntHead2 = new GAFISIMAGEHEADSTRUCT().fromByteArray(mntBytes)

    val expect = IOUtils.toByteArray(getClass.getResourceAsStream("/lf.mnt"))
    val expectHead = new GAFISIMAGEHEADSTRUCT().fromByteArray(expect)
    //Assert.assertArrayEquals(expect,mntBytes)
  }
}
