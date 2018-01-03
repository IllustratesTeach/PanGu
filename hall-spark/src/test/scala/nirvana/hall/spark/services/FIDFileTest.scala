package nirvana.hall.spark.services
import java.io.{ByteArrayInputStream, File}

import monad.support.MonadSupportConstants
import monad.support.services.XmlLoader
import nirvana.hall.c.AncientConstants
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.internal.{AfisDatabaseProvider, GafisPartitionRecordsBjsjSave, GafisPartitionRecordsDakuSaver}
import nirvana.hall.spark.services.FptPropertiesConverter.{LatentCaseConvert, LatentFingerConvert, LatentFingerFeatureConvert}
import org.apache.commons.io.{FileUtils, IOUtils}
import org.jboss.netty.buffer.ChannelBuffers
import org.junit.{Assert, Test}

import scala.io.Source

/**
  * Created by wangjue on 2017/12/22.
  */
class FIDFileTest {

  @Test
  def test_parseFID: Unit ={
    val file = FileUtils.readFileToByteArray(new File("C:\\Users\\wangjue\\Desktop\\测试平台\\bjsj_palm\\FID\\R1100000008882014080781.fid"))
    val fid = FIDFile.parseFromInputStream(new ByteArrayInputStream(file))
    println("fingers : " +fid.fingers.length+" , palms : "+fid.palms.length + " , portraits : " + fid.portraits.length)
    Assert.assertEquals(fid.fileFlag,"FID")
  }

  @Test
  def test_inputStream: Unit ={
    val file = FileUtils.readFileToByteArray(new File("C:\\Users\\wangjue\\Desktop\\测试平台\\bjsj_palm\\FID\\R1100000008882014080781.fid"))
    val buffer = ChannelBuffers.wrappedBuffer(file)
    //2810
    //2819 palm
    //215601110224199509
    buffer.readerIndex(43)
    val byte = buffer.readBytes(16).array()
    byte.foreach(t => print(t))
    println()
    println(new String(byte,AncientConstants.GBK_ENCODING))

  }

  @Test
  def test_list_exist: Unit ={
    val content = Source.fromFile(new File("src/test/resources/test_spark_bjsj.xml"),MonadSupportConstants.UTF8_ENCODING).mkString
    val config = XmlLoader.parseXML[NirvanaSparkConfig](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/spark/nirvana-spark.xsd")))
    SysProperties.setConfig(config)
    val path = "/fid/data/A/1/R1101119200002014090064.fid"
    val fileName = path.substring(path.lastIndexOf("/")+1,path.lastIndexOf("."))
    val listPalms : List[Array[Int]] = GafisPartitionRecordsBjsjSave.queryPalmByPersonId(fileName)
    val imageIsExists = listPalms.exists(x=> x(0) == 11 && x(1) == 1)
    val mntIsExists = listPalms.exists(x=> x(0) == 12 && x(1) == 0)
    Assert.assertEquals(listPalms.size,4)
  }


  def test_export_62_latent_palm {

  }

  @Test
  def test_saveLatentCase: Unit ={
    val content = Source.fromFile(new File("src/test/resources/test_spark_bjsj.xml"),MonadSupportConstants.UTF8_ENCODING).mkString
    val config = XmlLoader.parseXML[NirvanaSparkConfig](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/spark/nirvana-spark.xsd")))
    SysProperties.setConfig(config)

    val collections = FileUtils.listFiles(new File("C:\\Users\\wangjue\\Desktop\\测试平台\\latent_palm"),Array("img","mnt"),true)
    val it = collections.iterator()
    while (it.hasNext) {
      val file = it.next()
      val fileName = file.getName
      val caseId = "A"+fileName.substring(0,22)
      val fingerId = "A"+fileName.substring(0,24)
      val seqNo = fileName.substring(22,24)
      val latentCase = new LatentCaseConvert
      latentCase.caseId = caseId

      val latentFinger = new LatentFingerConvert
      latentFinger.seqNo = seqNo
      latentFinger.fingerId = fingerId
      latentFinger.caseId = caseId
      if (fileName.endsWith("img")) latentFinger.imgData = FileUtils.readFileToByteArray(file)

      val latentFingerFeature = new LatentFingerFeatureConvert
      latentFingerFeature.fingerId = fingerId
      if (fileName.endsWith("mnt")) latentFingerFeature.fingerMnt = FileUtils.readFileToByteArray(file)

      latentFinger.LatentFingerFeatures = latentFingerFeature :: Nil
      latentCase.latentFingers =  latentFinger :: Nil

      GafisPartitionRecordsBjsjSave.saveLatent(latentCase)

    }
  }

  @Test
  def test_saveLatentPalmTo70From60: Unit = {
    val content = Source.fromFile(new File("src/test/resources/test_spark_bjsj.xml"),MonadSupportConstants.UTF8_ENCODING).mkString
    val config = XmlLoader.parseXML[NirvanaSparkConfig](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/spark/nirvana-spark.xsd")))
    SysProperties.setConfig(config)
    AfisDatabaseProvider.saveLatentPalm
  }

  @Test
  def test_queryHasPerson: Unit = {
    val content = Source.fromFile(new File("src/test/resources/test_spark_bjsj.xml"),MonadSupportConstants.UTF8_ENCODING).mkString
    val config = XmlLoader.parseXML[NirvanaSparkConfig](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/spark/nirvana-spark.xsd")))
    SysProperties.setConfig(config)
    val files = FileUtils.listFiles(new File("C:\\Users\\wangjue\\Desktop\\测试平台\\bjsj_palm\\fid_file_path\\bjsj"),Array("txta"),true)
    val it = files.iterator()
    while (it.hasNext) {
      val file = it.next()
      val lines = Source.fromFile(file).getLines()
      lines.foreach{ t =>
        val personId = t.substring(t.lastIndexOf("/")+1,t.lastIndexOf("."))
        val hasPerson = GafisPartitionRecordsBjsjSave.queryPersonById(personId)
        if (hasPerson.isEmpty) {
          println(t)
          FileUtils.writeStringToFile(new File("C:\\Users\\wangjue\\Desktop\\测试平台\\bjsj_palm\\fid_file_path\\lost_a.txt"),t+"\r\n",true)
        }
      }
    }

  }


}
