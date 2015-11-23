package nirvana.hall.api.internal.stamp

import java.util
import java.util.UUID
import javax.sql.rowset.serial.SerialBlob
import nirvana.hall.api.entities.{GafisGatherPortrait}
import nirvana.hall.api.internal.{AnalysisData}
import nirvana.hall.api.services.stamp.GatherPortraitService
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import scalikejdbc._


/**
 * Created by wangjue on 2015/10/27.
 */
class GatherPortraitServiceImpl extends GatherPortraitService{
  val gpt = GafisGatherPortrait.syntax("gpt")
  /**
   * 人像信息查询
   * @param person
   * @return
   */
  override def queryGatherPortrait(person: String) (implicit session: DBSession = GafisGatherPortrait.autoSession) : List[GafisGatherPortrait] = {
    withSQL {
      select.from(GafisGatherPortrait as gpt).where.eq(GafisGatherPortrait.column.personid,person)
    }.map(GafisGatherPortrait(gpt.resultName)).list.apply()
  }



  /**
   * 人像信息新增
   * @param gafisGatherPortrait
   * @return
   */
  override def addGatherPortrait(gafisGatherPortrait: GafisGatherPortrait) (implicit session: DBSession = GafisGatherPortrait.autoSession) : Boolean = {
    try {

      GafisGatherPortrait.create(UUID.randomUUID().toString.replace("-",""),gafisGatherPortrait.personid,
          gafisGatherPortrait.fgp,gafisGatherPortrait.gatherData,gafisGatherPortrait.inputpsn,
            new DateTime(),gafisGatherPortrait.modifiedpsn,Some(new DateTime()),
              gafisGatherPortrait.deletag,gafisGatherPortrait.gatherdatanosqlid)
      true
    } catch {
      case exception: Exception => false
    }

  }

  /**
   * 覆盖删除人像
   * @param personid
   * @param session
   * @return
   */
  override def deleteGatherPortrait(personid: String)(implicit session: DBSession): Boolean = {
    try {
      withSQL {
        delete.from(GafisGatherPortrait).where.eq(GafisGatherPortrait.column.personid,personid)
      }
      true
    } catch {
      case exception: Exception => false
    }
  }

  /**
   * 解析保存人像
   * @param personId
   * @param faceData
   * @param session
   * @return
   */
  override def analysisGatherPortrait(personId: String, faceData: String)(implicit session: DBSession): String = {
    val listData : util.List[util.HashMap[_, _]] = AnalysisData.analysisPortrait(faceData)
    deleteGatherPortrait(personId);//保存前删除人像
    for (v <- 0 to listData.size()-1) {
      val map : util.HashMap[_, _] = listData.get(v)
      val pkId = map.get("pkid").toString
      val fgp  = map.get("fgp").toString
      val gatherData : SerialBlob = map.get("gatherData").asInstanceOf[SerialBlob]
      val inputTime = DateTime.now()
      GafisGatherPortrait.create(pkId,Some(personId),fgp,gatherData,None,inputTime,None,None,Some("1"),None)
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
  def parseDateTime(time : String) : DateTime = {
    if (time == None || time.equals("") || time.isEmpty)
      null
    else
      DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(time)
  }

  def parseDateTimeToString(time : DateTime) : String = {
    if (time == null)
      ""
    else
      time.toString("yyyy-MM-dd HH:mm:ss")
  }


}


