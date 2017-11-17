package nirvana.hall.webservice




import java.text.SimpleDateFormat
import java.util.Date
import javax.activation.DataHandler

import monad.support.services.XmlLoader
import nirvana.hall.webservice.internal.haixin.vo.{HitConfig, ListItem}
import org.apache.axiom.attachments.ByteArrayDataSource
import org.apache.commons.io.IOUtils
import org.apache.tapestry5.json.JSONObject
import org.junit.Test

/**
  * Created by yuchen on 2017/8/3.
  */
class XStreamTest {


  /*<?xml version="1.0" encoding="UTF-8"?>
      <Root>
        <List ItemCnt="5">
          <Item>R2100002017032999990001</Item>
          <Item>R2100002017032999990002</Item>
          <Item>R2100002017032999990003</Item>
          <Item>R2100002017032999990004</Item>
          <Item>R2100002017032999990005</Item>
        </List>
      </Root>
    */
  @Test
  def test_stream(): Unit = {

    val arrayListItem = new java.util.ArrayList[String]

    for(i <- 1 until 10){
      arrayListItem.add("R01" + i)
    }

    val listItem = new ListItem
    listItem.Item = arrayListItem
    listItem.itemCnt = 5

    val hitConfig = new HitConfig
    hitConfig.List = listItem

    println(XmlLoader.toXml(hitConfig))
    val dataHandle = new DataHandler(new ByteArrayDataSource(XmlLoader.toXml(hitConfig).getBytes))
    println("转换后:" + dataHandle)
    println("恢复后:" + new String(IOUtils.toByteArray(dataHandle.getInputStream)))



  }


  @Test
  def test_JSON(): Unit ={
    val jsonObj= new JSONObject("{\"TPDB\":[1,11]}")
    var str = ""
    if(jsonObj.has("TPDB")){
      val iter = jsonObj.getJSONArray("TPDB").iterator()
      while (iter.hasNext){
        val db = iter.next()
        str += db.toString
      }
    }
    println(str)


  }

  @Test
  def test_map(): Unit ={
    //if(logic02Rec.personId.toUpperCase.startsWith("R")) logic02Rec.personId.take(1) else logic02Rec.personId
    val str = "r1000000000111111111123"
    if(str.toUpperCase.startsWith("R"))
      println(str.drop(1))
  }

  @Test
  def test_date(): Unit ={
    val date = new SimpleDateFormat("YYYYMMddHHmmss").format(new Date)
    println(date)
  }

  @Test
  def test_option(): Unit ={

    /*val listString:Option[String] = None
    listString match{
      case Some(t) => println("****")
      case _ => println("input")
    }*/

    None.foreach(t => println(t))

  }
}
