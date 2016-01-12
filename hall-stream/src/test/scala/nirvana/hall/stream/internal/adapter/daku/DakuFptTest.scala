package nirvana.hall.stream.internal.adapter.daku

import java.io.File
import java.util

import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.stream.internal.adapter.bianjian.BianjianTestSymobls
import nirvana.hall.stream.internal.adapter.daku.util.FPTObject
import nirvana.hall.stream.services.StreamService
import org.apache.commons.io.IOUtils
import org.apache.tapestry5.ioc.services.RegistryShutdownHub
import org.junit.{Assert, Test}
import org.mockito.Mockito
import org.slf4j.LoggerFactory

/**
 * Created by wangjue on 2015/12/29.
 */
class DakuFptTest {
  private val logger = LoggerFactory getLogger getClass
  @Test
  def parseFpt : Unit = {
    //val fpt = FPTObject.parseOfFile(new File("D:\\fpt\\P5200000000002015129985.fpt"))
    val fpt = FPTObject.parseOfFile(new File("D:\\fpt\\R0000123324111626522353.fpt"))//R0000123324111626522353.fpt
    val list = fpt.getTpDataList
    Assert.assertTrue(list.size()>0)
  }

  @Test
  def decodeFptData : Unit = {
    val streamService = Mockito.mock(classOf[StreamService])
    /*val registry = Mockito.mock(classOf[RegistryShutdownHub])
    System.setProperty(DakuSymobls.MNT_JDBC_URL, "jdbc:oracle:thin:@127.0.0.1:1521:xe")
    System.setProperty(DakuSymobls.MNT_JDBC_USER, "pcsys_cs")
    System.setProperty(DakuSymobls.MNT_JDBC_PASS, "11")

    val datasource = DakuModule.buildMntDataSource(registry,logger)*/
    val dakuStream = new DakuStream(null,streamService)
    dakuStream.startStream()

  }

}
