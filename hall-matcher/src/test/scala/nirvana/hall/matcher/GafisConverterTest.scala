package nirvana.hall.matcher

import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.c.services.gloclib.gqrycond.GAFIS_KEYLISTSTRUCT
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

  /**
    * 组查询mic，包含多个捺印卡指纹信息
    */
  @Test
  def test_mic2: Unit ={
    val mic = IOUtils.toByteArray(getClass.getResourceAsStream("/mic2.dat"))
    val mics = GafisConverter.GAFIS_MIC_GetDataFromStream(ChannelBuffers.wrappedBuffer(mic))
    var nIndex = 0
    mics.foreach { micStruct =>
      nIndex = micStruct.nIndex
    }
    Assert.assertEquals(nIndex, 2)
  }

  @Test
  def test_qrycondition = {
    val qrycondition = IOUtils.toByteArray(getClass.getResourceAsStream("/qrycondition.dat"))
    val item = new GBASE_ITEMPKG_OPSTRUCT
    item.fromByteArray(qrycondition)
    val keylist = new GAFIS_KEYLISTSTRUCT().fromByteArray(item.items(0).bnRes)
    Assert.assertEquals(keylist.stKey.length, 3)
    keylist.stKey.foreach{key =>
      println(key.szKey)
    }
  }
}
