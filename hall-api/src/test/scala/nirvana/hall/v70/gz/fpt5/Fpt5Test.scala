package nirvana.hall.v70.gz.fpt5

import monad.support.services.XmlLoader
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.c.services.gfpt5lib.FPT5File
import nirvana.hall.v70.gz.sys.BaseV70TestCase
import org.junit.Test


/**
  * Created by Administrator on 2017/9/26.
  */
class Fpt5Test extends BaseV70TestCase{

  @Test
  def test_fpt5_tp(): Unit = {
    val service = getService[FPT5Service]
    val fPT5File = new FPT5File

    fPT5File.fingerprintPackage.add(service.getFingerprintPackage("P5200000000002017099992"))

    val a = fPT5File.build(fPT5File)
    a.packageHead.originSystem = fPT5File.JINGZONG_SYSTEM
    a.taskInfo.sendUnitCode = "000000000000"
    a.taskInfo.sendUnitName = "aaa"
    a.taskInfo.sendPersonName = "yuchen"
    a.taskInfo.sendPersonIdCard = "120101198601031538"
    a.taskInfo.sendPersonTel = "3756473"
    a.taskInfo.sendUnitSystemType = "5555"

    println(XmlLoader.toXml(a))

    val obj = XmlLoader.parseXML[FPT5File](XmlLoader.toXml(a),xsd = Some(getClass.getResourceAsStream("/fpt5.xsd")))
    println(obj)
  }

  @Test
  def test_fpt5_lp(): Unit = {
    val service = getService[FPT5Service]
    val fPT5File = new FPT5File

    fPT5File.latentPackage.add(service.getLatentPackage("A5202000000002017050600"))

    val a = fPT5File.build(fPT5File)
    a.packageHead.originSystem = fPT5File.JINGZONG_SYSTEM
    a.taskInfo.sendUnitCode = "000000000000"
    a.taskInfo.sendUnitName = "aaa"
    a.taskInfo.sendPersonName = "yuchen"
    a.taskInfo.sendPersonIdCard = "120101198601031538"
    a.taskInfo.sendPersonTel = "3756473"
    a.taskInfo.sendUnitSystemType = "5555"

    println(XmlLoader.toXml(a))

    val obj = XmlLoader.parseXML[FPT5File](XmlLoader.toXml(a),xsd = Some(getClass.getResourceAsStream("/fpt5.xsd")))
    println(obj)
  }
}
