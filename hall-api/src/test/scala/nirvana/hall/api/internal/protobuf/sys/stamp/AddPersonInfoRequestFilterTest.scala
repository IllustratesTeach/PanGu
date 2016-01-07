package nirvana.hall.api.internal.protobuf.sys.stamp

import nirvana.hall.api.internal.BaseServiceTestSupport
import nirvana.hall.api.jpa.GafisPerson
import nirvana.hall.api.services.ProtobufRequestHandler
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse, ResponseStatus}
import nirvana.hall.protocol.sys.stamp.SavePersonProto.{SavePersonRequest, SavePersonResponse}
import nirvana.hall.protocol.sys.stamp.UpdatePersonProto.UpdatePersonRequest
import org.junit.{Assert, Test}

/**
 * Created by wangjue on 2015/11/4.
 */
class AddPersonInfoRequestFilterTest extends BaseServiceTestSupport {

  @Test
  def test_addPersonInfo: Unit ={
    val addRequest = SavePersonRequest.newBuilder()
    addRequest.setPersonInfo("personid=CS520201511050001&yy=test&name=&idcardno=&gatherDate=2015-11-9 15:53:30&dataSources=1&gatherFingerNum=10")

    val handler = registry.getService(classOf[ProtobufRequestHandler])

    //add
    val protobufRequest = BaseRequest.newBuilder().setToken("asdf").setVersion(102)
    protobufRequest.setExtension(SavePersonRequest.cmd, addRequest.build())
    val protobufResponse = BaseResponse.newBuilder()
    protobufResponse.setStatus(ResponseStatus.OK)
    handler.handle(protobufRequest.build(), protobufResponse)
    val personid = protobufResponse.getExtension(SavePersonResponse.cmd).getPersonInfo(0).getPersonid
    Assert.assertEquals("CS520201511050001",personid)

    //query
    /*val queryRequest = QueryBasePersonRequest.newBuilder()
    queryRequest.setPersonid("CS520201511050001")
    val protobufRequest1 = BaseRequest.newBuilder().setToken("asdf").setVersion(103)
    protobufRequest1.setExtension(QueryBasePersonRequest.cmd, queryRequest.build())
    val protobufResponse1 = BaseResponse.newBuilder()
    protobufResponse1.setStatus(ResponseStatus.OK)
    handler.handle(protobufRequest1.build(), protobufResponse1)

    val personInfo = protobufResponse.getExtension(QueryBasePersonResponse.cmd)
    Assert.assertTrue(personInfo != null)*/
    val person = GafisPerson.find("CS520201511050001")
    println(person.name)
    println(person.spellname)
    Assert.assertEquals("",person.name)


    //update

    /*val person1 = GafisPerson.save(new GafisPerson("CS520201511050001",Some("123"),Some("anmiyy")))
    Assert.assertEquals("anmiyy",person1.name.get)*/

    /*val gatherPersonService = new GatherPersonServiceImpl()
    val person1 = gatherPersonService.updateGatherPerson("personid=CS520201511050001&name=anmiyy&idcardno=123&gatherDate=2015-11-9 15:53:30&dataSources=1&gatherFingerNum=10")
    Assert.assertEquals("anmicc",person1.name.get)*/

    val updateRequest = UpdatePersonRequest.newBuilder()
    updateRequest.setPersonInfo("personid=CS520201511050001&name=anmiyy&idcardno=123&gatherDate=2015-11-9 15:53:30&dataSources=1&gatherFingerNum=10")
    val protobufRequest2 = BaseRequest.newBuilder().setToken("asdf").setVersion(104)
    protobufRequest2.setExtension(UpdatePersonRequest.cmd ,updateRequest.build())
    val protobufResponse2 = BaseResponse.newBuilder()
    protobufResponse2.setStatus(ResponseStatus.OK)
    handler.handle(protobufRequest2.build(), protobufResponse2)

    //val personInfo2 = protobufResponse.getExtension(QueryBasePersonResponse.cmd)
    //Assert.assertTrue(personInfo2 != null)


    val person2 = GafisPerson.find("CS520201511050001")
    Assert.assertEquals("anmicc",person2.name)


    val person3 = GafisPerson.all
    Assert.assertTrue(person3.size >=1)


    /*val person : GafisPerson = GafisPerson.find("CS520201511050001").get
    Assert.assertEquals("CS520201511050001",person.personid)
    Assert.assertEquals("anmiyy",person.name)
    val b = PersonInfo.newBuilder()
    ScalaUtils.convertScalaToProtobuf(person,b)
    Assert.assertEquals("CS520201511050001",b.getPersonid)*/

  }

}
