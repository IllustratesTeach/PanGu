package nirvana.hall.v70.gz.gzcapture

import java.util.{Date, UUID}
import javax.persistence.EntityManager

import com.google.protobuf.ByteString
import nirvana.hall.api.config.QueryDBConfig
import nirvana.hall.api.internal.DateConverter
import nirvana.hall.api.services.QueryService
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYSTRUCT
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchStatus
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask.LatentMatchData
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.v70.gz.jpa._
import nirvana.hall.v70.gz.sync.ProtobufConverter
import nirvana.hall.v70.gz.{Constant, sys}
import nirvana.hall.v70.internal.Gafis70Constants
import nirvana.hall.v70.internal.query.QueryConstants
import org.springframework.transaction.annotation.Transactional

import scala.collection.mutable

/**
 * Created by songpeng on 16/1/26.
 */
class QueryServiceImpl(entityManager: EntityManager, userService:sys.UserService) extends QueryService{
  /**
   * 发送查询任务
    *
    * @param matchTask
   * @return
   */
  @Transactional
  override def addMatchTask(matchTask: MatchTask, queryDBConfig: QueryDBConfig): Long = {
    val gafisQuery = ProtobufConverter.convertMatchTask2GafisNormalqueryQueryque(matchTask)
    val query = entityManager.createNativeQuery("select SEQ_ORASID.nextval from dual")
    gafisQuery.oraSid = query.getResultList.get(0).toString.toLong
    gafisQuery.pkId = UUID.randomUUID().toString.replace("-", Constant.EMPTY)
    gafisQuery.submittsystem = QueryConstants.SUBMIT_SYS_GATHER   //标采提交系统
    //用户名获取用户ID
    var sysUser = userService.findSysUserByLoginName(gafisQuery.username)
    if (sysUser.isEmpty) {
      sysUser = Option(SysUser.find(Gafis70Constants.INPUTPSN))
    }
    gafisQuery.userid = sysUser.get.pkId
    gafisQuery.username = sysUser.get.trueName
    gafisQuery.userunitcode = sysUser.get.departCode
    if (matchTask.getOraCreatetime.nonEmpty) {
      gafisQuery.createtime = DateConverter.convertString2Date(matchTask.getOraCreatetime, "yyyy-MM-dd HH:mm:ss")
    } else {
      gafisQuery.createtime = new Date()
    }
    gafisQuery.deletag = Gafis70Constants.DELETAG_USE
    gafisQuery.save()

    gafisQuery.oraSid
  }

  /**
   * 获取查询信息
    *
    * @param oraSid
   * @return
   */
  override def getMatchResult(oraSid: Long, dbId: Option[String]): Option[MatchResult]= ???

  /**
    * 通过卡号查找第一个的比中结果
    *
    * @param cardId 卡号
    * @return 比对结果
    */
  override def findFirstQueryResultByCardId(cardId: String, dbId: Option[String]): Option[MatchResult] = {
    throw new UnsupportedOperationException
  }

  /**
   * 根据卡号查找第一个比对任务的状态, 如果没有获取到返回UN_KNOWN
    *
    * @param cardId
   * @return
   */
  override def findFirstQueryStatusByCardIdAndMatchType(cardId: String, matchType: MatchType, dbId: Option[String]): MatchStatus = {
    throw new UnsupportedOperationException
  }

  /**
    * 根据卡号信息发送查询, 不需要特征信息
    * 根据卡号和查询类型从数据库查询特征数据，并放入matchTask，然后调用addMatchTask
    *
    * @param matchTask 只有查询信息不需要特征信息
    * @param queryDBConfig
    * @return 任务号
    */
  override def sendQuery(matchTask: MatchTask, queryDBConfig: QueryDBConfig): Long = {
    val matchTaskBuilder = matchTask.toBuilder
    val cardId = matchTask.getMatchId
    var sid: java.lang.Long = 1.toLong
    val hallTaskRecode = new HallTaskRecord()
    hallTaskRecode.uuid = UUID.randomUUID().toString.replace("-",Constant.EMPTY)
    hallTaskRecode.personid = cardId
    hallTaskRecode.puuid =  HallCaptureRecord.find_by_personid_and_issendquery(cardId,0.toString).headOption.get.uuid
    hallTaskRecode.querytype = matchTask.getMatchType.ordinal().toShort.toString
    try{
      matchTask.getMatchType match {
        case MatchType.FINGER_TT | MatchType.FINGER_TL =>
          val fingerList = GafisGatherFinger.find_by_personId_and_lobtype(cardId, Gafis70Constants.LOBTYPE_MNT)
          val mntMap = mutable.Map[String, GafisGatherFinger]()
          val binMap = mutable.Map[String, GafisGatherFinger]()
          fingerList.filter(_.groupId == Gafis70Constants.GROUP_ID_MNT).map{finger=>
            mntMap += (finger.fgpCase +"-"+finger.fgp -> finger)
          }
          fingerList.filter(_.groupId == Gafis70Constants.GROUP_ID_BIN).foreach{finger=>
            binMap += (finger.fgpCase +"-"+finger.fgp -> finger)
          }
          mntMap.foreach{fingerMnt=>
            val tdata = matchTaskBuilder.getTDataBuilder.addMinutiaDataBuilder()
            var pos:Int = fingerMnt._2.fgp
            //平面指位+10
            if(fingerMnt._2.fgpCase == Gafis70Constants.FGP_CASE_PLAIN){
              pos = fingerMnt._2.fgp + 10
            }
            tdata.setMinutia(ByteString.copyFrom(fingerMnt._2.gatherData)).setPos(pos)
            //纹线数据
            val fingerBin = binMap.get(fingerMnt._1)
            if(fingerBin.nonEmpty){
              tdata.setRidge(ByteString.copyFrom(fingerBin.get.gatherData)).setPos(pos)
            }
          }
          if(fingerList.isEmpty){
            throw new IllegalArgumentException("特征数据不存在cardId:"+ cardId)
          }
        case MatchType.FINGER_LT | MatchType.FINGER_LL =>
          val fingerList = GafisCaseFingerMnt.find_by_fingerId(cardId)
          fingerList.foreach{finger=>
            val ldata = LatentMatchData.newBuilder()
            ldata.setMinutia(ByteString.copyFrom(finger.fingerMnt))
            if(finger.fingerRidge != null){
              ldata.setRidge(ByteString.copyFrom(finger.fingerRidge))
            }
            matchTaskBuilder.setLData(ldata.build())
          }
          if(fingerList.isEmpty){
            throw new IllegalArgumentException("特征数据不存在cardId:"+ cardId)
          }
      }
      sid = addMatchTask(matchTaskBuilder.build())

      HallCaptureRecord.update.set(issendquery = Constant.ISSEND).where(HallCaptureRecord.uuid === hallTaskRecode.puuid).execute

      hallTaskRecode.orasid = sid.toString
      hallTaskRecode.status = Constant.SUCCESS
      hallTaskRecode.tasksenddate = new Date()
      hallTaskRecode.save()
    }catch {
      case ex:Exception =>
        hallTaskRecode.status = Constant.FAIL
        hallTaskRecode.tasksenddate = new Date()
        hallTaskRecode.save()
        val hallCaptureException = new HallCaptureException()
        hallCaptureException.uuid = UUID.randomUUID().toString.replace("-",Constant.EMPTY)
        hallCaptureException.puuid = hallTaskRecode.uuid
        hallCaptureException.msg = ex.getMessage
        hallCaptureException.errtype = Constant.TaskQueryFilter
        hallCaptureException.save()
    }
    sid
  }

  /**
    * 根据编号和查询类型发送查询
    * 最大候选50，优先级2，最小分数60
    *
    * @param cardId
    * @param matchType
    * @param queryDBConfig
    * @return
    */
  override def sendQueryByCardIdAndMatchType(cardId: String, matchType: MatchType, queryDBConfig: QueryDBConfig = new QueryDBConfig(None, None, None)): Long = ???

  /**
    * 根据任务号sid获取比对状态
    *
    * @param oraSid
    * @param dbId
    * @return
    */
  override def getStatusBySid(oraSid: Long, dbId: Option[String]): Int = ???

  /**
    * 获取查询信息GAQUERYSTRUCT
    *
    * @param oraSid
    * @param dbId
    * @return
    */
  override def getGAQUERYSTRUCT(oraSid: Long, dbId: Option[String]): GAQUERYSTRUCT = ???

  def convertGafisNormalqueryQueryque2GAQUERYSTRUCT(gafisNormalqueryQueryque: GafisNormalqueryQueryque): GAQUERYSTRUCT = ???

  override def updateCandListFromQueryQue(gaQuery: GAQUERYSTRUCT, dbId: Option[String]): Unit = ???

  override def getGAQUERYSTRUCTListByKeyId(keyId: String, dbId: Option[String]): List[GAQUERYSTRUCT] = ???
}
