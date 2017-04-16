package nirvana.hall.matcher

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.matcher.internal.GafisConverter
import org.apache.commons.io.IOUtils
import org.jboss.netty.buffer.ChannelBuffers
import org.junit.{Assert, Test}

/**
  * Created by songpeng on 2017/4/16.
  */
class GafisConverterTest {

  @Test
  def test_mic: Unit ={
    val mic = IOUtils.toByteArray(getClass.getResourceAsStream("/mic.dat"))
    val mics = GafisConverter.GAFIS_MIC_GetDataFromStream(ChannelBuffers.wrappedBuffer(mic))
    Assert.assertTrue(mics.size > 0)
    mics.foreach { micStruct =>
      Assert.assertEquals(micStruct.nMntLen, micStruct.pstMnt_Data.length)
      Assert.assertEquals(micStruct.nBinLen, micStruct.pstBin_Data.length)
      if(micStruct.nBinLen > 0){
        val bin = new GAFISIMAGESTRUCT().fromByteArray(micStruct.pstBin_Data)
        Assert.assertEquals(bin.stHead.nImgSize, bin.bnData.length)
      }
    }
  }
}
