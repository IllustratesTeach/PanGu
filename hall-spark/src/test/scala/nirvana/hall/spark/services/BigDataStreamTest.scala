package nirvana.hall.spark.services

import java.io.File

import monad.support.MonadSupportConstants
import monad.support.services.XmlLoader
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.internal.{AfisDatabaseProvider, GafisDatabaseMigrationProvider, GafisPartitionRecordsMigrationSaver}
import org.junit.{Assert, Test}

import scala.io.Source

/**
 * Created by wangjue on 2016/2/16.
 */
class BigDataStreamTest {


  @Test
  def streamTestConverter (){
    val array = Array("hall-spark/src/test/resources/test_spark_convert.xml")
    BigDataStream.main(array)
    //SaveErrorStream.main(array)
  }
  @Test
  def streamTest (){
    val array = Array("src/test/resources/test_spark.xml")
    BigDataStream.main(array)
    //SaveErrorStream.main(array)
  }
  @Test
  def streamTestFTP (){
    val array = Array("src/test/resources/test_spark_file.xml")
    BigDataStream.main(array)
  }

  @Test
  def streamTestFULL (){
    val array = Array("src/test/resources/test_spark_full.xml")
    BigDataStream.main(array)
  }

  @Test
  def streamTestReExtract (){
    val array = Array("src/test/resources/test_spark_reExtract.xml")
    BigDataStream.main(array)
  }

  @Test
  def streamTestIdCard (){
    val array = Array("src/test/resources/test_spark_idcard.xml")
    BigDataForBMPStream.main(array)
  }

  @Test
  def streamTestWJW (){
    val array = Array("src/test/resources/test_spark_wjw.xml")
    BigDataForBMPStream.main(array)
  }

  @Test
  def streamTestTXT (){
    val array = Array("src/test/resources/test_spark_file.xml","C:\\Users\\wangjue\\Desktop\\测试平台\\spark\\latent_fpt_path.txt")
    BigDataForFileStream.main(array)
  }


  @Test
  def streamTestBJSJ (){
    val array = Array("src/test/resources/test_spark_bjsj.xml")
    BigDataForBjsjStream.main(array)
  }

  @Test
  def streamTestMigration (){
    val array = Array("src/test/resources/test_spark_migration.xml")
    BigDataForMigrationStream.main(array)
  }


  @Test
  def test_findSuitablePersonId: Unit = {
    val content = Source.fromFile(new File("src/test/resources/test_spark.xml"),MonadSupportConstants.UTF8_ENCODING).mkString
    val config = XmlLoader.parseXML[NirvanaSparkConfig](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/spark/nirvana-spark.xsd")))
    SysProperties.setConfig(config)
    GafisDatabaseMigrationProvider.findSuitablePersonId
  }

  @Test
  def test_importTemplate: Unit = {
    val content = Source.fromFile(new File("src/test/resources/test_spark.xml"),MonadSupportConstants.UTF8_ENCODING).mkString
    val config = XmlLoader.parseXML[NirvanaSparkConfig](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/spark/nirvana-spark.xsd")))
    SysProperties.setConfig(config)
    GafisDatabaseMigrationProvider.importTemplateFinger("P1201606231466652741170")
  }

  @Test
  def test_importLatent: Unit = {
    val content = Source.fromFile(new File("src/test/resources/test_spark_migration.xml"),MonadSupportConstants.UTF8_ENCODING).mkString
    val config = XmlLoader.parseXML[NirvanaSparkConfig](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/spark/nirvana-spark.xsd")))
    SysProperties.setConfig(config)
    var i= 1
    Source.fromFile(new File("C:\\Users\\wangjue\\Desktop\\测试平台\\小库\\LT_LATENT.log")).getLines().foreach{ t =>
      val records = GafisDatabaseMigrationProvider.importProvider(t).toIterator
      GafisPartitionRecordsMigrationSaver.savePartitionRecords(records)
      println("index : " + i + " , " + t + " process done !")
      i = i + 1
    }

  }
}
