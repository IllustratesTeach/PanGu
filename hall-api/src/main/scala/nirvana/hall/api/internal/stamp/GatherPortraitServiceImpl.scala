package nirvana.hall.api.internal.stamp

import java.util.UUID

import nirvana.hall.api.entities.GafisGatherPortrait._
import nirvana.hall.api.entities.{GafisPerson, SysUser, GafisGatherPortrait}
import nirvana.hall.api.services.stamp.GatherPortraitService
import org.joda.time.DateTime
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
   * 人像信息修改
   * @param gafisGatherPortrait
   * @return
   */
  override def updateGatherPortrait(gafisGatherPortrait: GafisGatherPortrait) (implicit session: DBSession = GafisGatherPortrait.autoSession) : Boolean = {
    try {
      withSQL {update(GafisPerson).set(
        column.personid -> gafisGatherPortrait.personid,
        column.fgp -> gafisGatherPortrait.fgp,
        column.gatherData -> gafisGatherPortrait.gatherData,
        column.inputpsn -> gafisGatherPortrait.inputpsn,
        column.inputtime -> gafisGatherPortrait.inputtime,
        column.modifiedpsn -> gafisGatherPortrait.modifiedpsn,
        column.modifiedtime -> gafisGatherPortrait.modifiedtime,
        column.deletag -> gafisGatherPortrait.deletag,
        column.gatherdatanosqlid -> gafisGatherPortrait.gatherdatanosqlid
      ).where.eq(GafisGatherPortrait.column.personid,gafisGatherPortrait.personid).and.eq(GafisGatherPortrait.column.fgp,gafisGatherPortrait.fgp)}.
      update.apply()
      true
    } catch {
      case exception: Exception => false
    }

  }

  /**
   * 人像信息新增
   * @param gafisGatherPortrait
   * @return
   */
  override def addGatherPortrait(gafisGatherPortrait: GafisGatherPortrait, login: SysUser) (implicit session: DBSession = GafisGatherPortrait.autoSession) : Boolean = {
    try {

      GafisGatherPortrait.create(UUID.randomUUID().toString.replace("-",""),gafisGatherPortrait.personid,
          gafisGatherPortrait.fgp,gafisGatherPortrait.gatherData,Some(login.pkId),
            new DateTime(),Some(login.pkId),Some(new DateTime()),
              gafisGatherPortrait.deletag,gafisGatherPortrait.gatherdatanosqlid)
      true
    } catch {
      case exception: Exception => false
    }

  }
}
