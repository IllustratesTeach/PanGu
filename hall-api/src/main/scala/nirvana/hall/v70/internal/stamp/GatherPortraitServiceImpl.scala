package nirvana.hall.v70.internal.stamp

import java.text.SimpleDateFormat
import java.util
import java.util.{Date, UUID}
import javax.sql.rowset.serial.SerialBlob

import nirvana.hall.api.internal.AnalysisData
import nirvana.hall.v70.services.stamp.GatherPortraitService
import nirvana.hall.orm.services.Relation
import nirvana.hall.v70.jpa.GafisGatherPortrait


/**
 * Created by wangjue on 2015/10/27.
 */
class GatherPortraitServiceImpl extends GatherPortraitService{
  /**
   * 人像信息查询
   * @param person
   * @return
   */
  override def queryGatherPortrait(person: String)  : Relation[GafisGatherPortrait] = {
    GafisGatherPortrait.find_by_personid(person)
  }



  /**
   * 人像信息新增
   * @param gafisGatherPortrait
   * @return
   */
  override def addGatherPortrait(gafisGatherPortrait: GafisGatherPortrait)  : Boolean = {
    gafisGatherPortrait.pkId = UUID.randomUUID().toString.replace("-","")
    gafisGatherPortrait.save()
    true
    /*
    try {

      new GafisGatherPortrait(UUID.randomUUID().toString.replace("-",""),gafisGatherPortrait.personid,
          gafisGatherPortrait.fgp,gafisGatherPortrait.gatherData,gafisGatherPortrait.inputpsn,
            new DateTime(),gafisGatherPortrait.modifiedpsn,new DateTime()),
              gafisGatherPortrait.deletag,gafisGatherPortrait.gatherdatanosqlid)
      true
    } catch {
      case exception: Exception => false
    }
    */

  }

  /**
   * 覆盖删除人像
   * @param personid
   * @return
   */
  override def deleteGatherPortrait(personid: String): Boolean = {
    GafisGatherPortrait.find_by_personid(personid).delete
    true
    /*
    try {
      withSQL {
        delete.from(GafisGatherPortrait).where.eq(GafisGatherPortrait.column.personid,personid)
      }
      true
    } catch {
      case exception: Exception => false
    }
    */
  }

  /**
   * 解析保存人像
   * @param personId
   * @param faceData
   * @return
   */
  override def analysisGatherPortrait(personId: String, faceData: String): String = {
    val listData : util.List[util.HashMap[_, _]] = AnalysisData.analysisPortrait(faceData)
    deleteGatherPortrait(personId);//保存前删除人像
    for (v <- 0 to listData.size()-1) {
      val map : util.HashMap[_, _] = listData.get(v)
      val pkId = map.get("pkid").toString
      val fgp  = map.get("fgp").toString
      val gatherData : SerialBlob = map.get("gatherData").asInstanceOf[SerialBlob]
      val inputTime = new Date()
      val portrait = new GafisGatherPortrait()
      portrait.pkId = pkId
      portrait.personid = personId
      portrait.fgp = fgp
      portrait.gatherData = gatherData
      portrait.inputtime = new Date()
      portrait.deletag = "1"
      portrait.save()
      //GafisGatherPortrait.create(pkId,Some(personId),fgp,gatherData,None,inputTime,None,None,Some("1"),None)
    }

    /*val portraits : List[GafisGatherPortrait] = GafisGatherPortrait.findAllBy(sqls.eq(GafisGatherPortrait.column.personid,personId))
    for (portrait <- portraits) {
      val portrait = portraits(0)
      val pkid = portrait.pkId
      val fgp  = portrait.fgp
      val gatherData  = portrait.gatherData
      val inputTime = parseDateTimeToString(portrait.inputtime)
      val by : Array[Byte] = gatherData.getBytes(0,gatherData.length().toInt)
      val by1 : Array[Byte] = gatherData.getBytes(129,gatherData.length().toInt)
      /*val out = new PrintWriter("C:\\image.jpg")
      for (b <- by1) {
        out.write(b)
      }*/
      println("gatherData---"+MsgBase64.toBase64(by1))
      println("--inputTime--"+inputTime)
    }*/

    "true"
  }

  //格式化时间
  def parseDateTime(time : String) : Date = {
    if (time == None || time.equals("") || time.isEmpty)
      null
    else
      new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time)
  }

  def parseDateTimeToString(time : Date) : String = {
    if (time == null)
      ""
    else
      new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time)
  }


}


