package nirvana.hall.v62.internal.filter

import com.google.protobuf.ByteString
import monad.support.services.XmlLoader
import nirvana.hall.api.services.ProtobufRequestHandler
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse, ResponseStatus}
import nirvana.hall.protocol.v62.FPTProto.{FingerFgp, TPCard}
import nirvana.hall.protocol.v62.{AddTPCardProto, FPTProto}
import nirvana.hall.v62.config.HallV62Config
import org.apache.tapestry5.ioc.{Registry, RegistryBuilder}
import org.junit.{Assert, Test}

import scala.io.Source

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-04
 */
class AddTPCardFilterTest {
  protected var registry:Registry = _
  @Test
  def test_request: Unit ={
    val modules = Seq[String](
      "nirvana.hall.api.LocalProtobufModule",
      "nirvana.hall.v62.LocalV62Module",
      "nirvana.hall.v62.internal.filter.TestModule").map(Class.forName)
    registry = RegistryBuilder.buildAndStartupRegistry(modules: _*)
    //新建用户

    val tpCard = TPCard.newBuilder()
    tpCard.setStrCardID(System.currentTimeMillis().toString)
    val blobBuilder = tpCard.addBlobBuilder()
    blobBuilder.setStMntBytes(ByteString.readFrom(getClass.getResourceAsStream("/t.mnt")))
    blobBuilder.setStImageBytes(ByteString.readFrom(getClass.getResourceAsStream("/t.cpr")))
    blobBuilder.setType(FPTProto.ImageType.IMAGETYPE_FINGER)
    blobBuilder.setFgp(FingerFgp.FINGER_R_LITTLE)

    val textBuilder = tpCard.getTextBuilder
    textBuilder.setStrName ("蔡Sir")
    textBuilder.setStrAliasName ("大刀蔡")
    textBuilder.setNSex (1)
    textBuilder.setStrBirthDate ("19800911")
    textBuilder.setStrIdentityNum ("123123")
    textBuilder.setStrBirthAddrCode ("123123")
    textBuilder.setStrBirthAddr ("中国")
    textBuilder.setStrAddrCode ("12")
    textBuilder.setStrAddr ("中国CHINA")
    textBuilder.setStrPersonType ("1")
    textBuilder.setStrCaseType1 ("1")
    textBuilder.setStrCaseType2 ("1")
    textBuilder.setStrCaseType3 ("1")
    textBuilder.setStrPrintUnitCode ("1")
    textBuilder.setStrPrintUnitName ("1")
    textBuilder.setStrPrinter ("1")
    textBuilder.setStrPrintDate ("20150121")
    textBuilder.setStrComment ("中国CHINA")
    textBuilder.setStrNation ("CHINA")
    textBuilder.setStrRace ("asdf")
    textBuilder.setStrCertifType ("1")
    textBuilder.setStrCertifID ("1")
    textBuilder.setBHasCriminalRecord (true)
    textBuilder.setStrCriminalRecordDesc ("asdf")
    textBuilder.setStrPremium ("asdf")
    textBuilder.setNXieChaFlag (1)
    textBuilder.setStrXieChaRequestUnitName ("asdfasdf")
    textBuilder.setStrXieChaRequestUnitCode ("123")
    textBuilder.setNXieChaLevel (1)
    textBuilder.setStrXieChaForWhat ("哈哈哈哈哈")
    textBuilder.setStrRelPersonNo ("12")
    textBuilder.setStrRelCaseNo ("12")
    textBuilder.setStrXieChaTimeLimit ("12")
    textBuilder.setStrXieChaDate ("")
    textBuilder.setStrXieChaRequestComment ("")
    textBuilder.setStrXieChaContacter ("")
    textBuilder.setStrXieChaTelNo ("")
    textBuilder.setStrShenPiBy ("")

    val addTpCard = AddTPCardProto.AddTPCardRequest.newBuilder()
    addTpCard.setCard(tpCard.build())

    val handler = registry.getService(classOf[ProtobufRequestHandler])
    val protobufRequest = BaseRequest.newBuilder().setToken("asdf").setVersion(102)
    protobufRequest.setExtension(AddTPCardProto.AddTPCardRequest.cmd, addTpCard.build())
    val protobufResponse = BaseResponse.newBuilder()
    protobufResponse.setStatus(ResponseStatus.OK)
    handler.handle(protobufRequest.build(), protobufResponse)

    Assert.assertTrue(protobufResponse.hasExtension(AddTPCardProto.AddTPCardResponse.cmd))
    Assert.assertEquals(ResponseStatus.OK,protobufResponse.getStatus)
  }

}

object TestModule {
  def buildHallV62Config={
    val content = Source.fromInputStream(getClass.getResourceAsStream("/test-v62.xml"),"utf8").mkString
    XmlLoader.parseXML[HallV62Config](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/v62/v62.xsd")))
  }
}
