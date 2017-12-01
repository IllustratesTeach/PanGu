package nirvana.hall.v70.gz.fpt5

import monad.support.services.XmlLoader
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.c.services.gfpt5lib.{FPT5File, FingerprintPackage}
import nirvana.hall.v70.gz.sys.BaseV70TestCase
import org.apache.commons.io.IOUtils
import org.junit.Test


/**
  * Created by Administrator on 2017/9/26.
  */
class Fpt5Test extends BaseV70TestCase{

  @Test
  def test_fpt5_tpget(): Unit = {
    val service = getService[FPT5Service]
    val fPT5File = new FPT5File
    val fingerprintPackage =  new scala.collection.mutable.ArrayBuffer[FingerprintPackage]()
    fingerprintPackage += service.getFingerprintPackage("R5224010109992013070451")

    fPT5File.fingerprintPackage = fingerprintPackage.toArray

    val a = fPT5File.build(fPT5File)
    a.packageHead.originSystem = fPT5File.JINGZONG_SYSTEM
    a.packageHead.sendUnitCode = "000000000000"
    a.packageHead.sendUnitName = "aaa"
    a.packageHead.sendPersonName = "yuchen"
    a.packageHead.sendPersonIdCard = "120101198601031538"
    a.packageHead.sendPersonTel = "3756473"
    a.packageHead.sendUnitSystemType = "5555"

    println(XmlLoader.toXml(a))

    val obj = XmlLoader.parseXML[FPT5File](XmlLoader.toXml(a),xsd = Some(getClass.getResourceAsStream("/fpt5.xsd")))
    println(obj)
  }


  @Test
  def test_fpt5_tpadd(): Unit = {
    val service = getService[FPT5Service]
    val fPT5File = new FPT5File

    val originalList = XmlLoader.parseXML[FPT5File](new String(IOUtils.toByteArray(getClass.getResourceAsStream("/R5224010107772013070452.xml"))))

    originalList.fingerprintPackage.foreach{
      fingerprintPackage => service.addFingerprintPackage(fingerprintPackage)
    }
  }

  @Test
  def test_fpt5_lp(): Unit = {
//    val service = getService[FPT5Service]
//    val fPT5File = new FPT5File
//
//    fPT5File.latentPackage.add(service.getLatentPackage("A5202000000002017050600"))
//
//    val a = fPT5File.build(fPT5File)
//    a.packageHead.originSystem = fPT5File.JINGZONG_SYSTEM
//    a.taskInfo.sendUnitCode = "000000000000"
//    a.taskInfo.sendUnitName = "aaa"
//    a.taskInfo.sendPersonName = "yuchen"
//    a.taskInfo.sendPersonIdCard = "120101198601031538"
//    a.taskInfo.sendPersonTel = "3756473"
//    a.taskInfo.sendUnitSystemType = "5555"

//    println(XmlLoader.toXml(a))
//
//    val obj = XmlLoader.parseXML[FPT5File](XmlLoader.toXml(a),xsd = Some(getClass.getResourceAsStream("/fpt5.xsd")))
//    println(obj)
  }
}
