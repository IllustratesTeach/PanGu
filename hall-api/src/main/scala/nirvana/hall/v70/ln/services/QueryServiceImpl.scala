package nirvana.hall.v70.ln.services

import java.util.Date
import javax.persistence.EntityManager
import javax.sql.DataSource

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
import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.v70.internal.query.QueryConstants
import nirvana.hall.v70.internal.{CommonUtils, Gafis70Constants}
import nirvana.hall.v70.ln.jpa.{GafisCaseFingerMnt, GafisGatherFinger, SysUser}
import nirvana.hall.v70.ln.sync.ProtobufConverter
import nirvana.hall.v70.ln.sys
import nirvana.hall.v70.ln.sys.UserService

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
  * Created by songpeng on 16/1/26.
  */
class QueryServiceImpl(entityManager: EntityManager, userService:UserService,implicit val dataSource: DataSource) extends QueryService{
  /**
    * 发送查询任务
    *
    * @param matchTask
    * @return 任务号
    */
  override def addMatchTask(matchTask: MatchTask, queryDBConfig: QueryDBConfig): Long = {
    val gafisQuery = ProtobufConverter.convertMatchTask2GafisNormalqueryQueryque(matchTask)
    val query = entityManager.createNativeQuery("select SEQ_ORASID.nextval from dual")
    gafisQuery.oraSid = query.getResultList.get(0).toString.toLong
    gafisQuery.pkId = CommonUtils.getUUID()
    gafisQuery.submittsystem = QueryConstants.SUBMIT_SYS_AFIS

    //用户名获取用户ID
    var sysUser = userService.findSysUserByLoginName(gafisQuery.username)
    sysUser match{
      case Some(t) =>

      case _ =>
        sysUser = Option(SysUser.find(Gafis70Constants.INPUTPSN))
    }

    gafisQuery.userid = sysUser.head.pkId
    gafisQuery.username = sysUser.head.trueName
    gafisQuery.userunitcode =sysUser.head.departCode

    if(matchTask.getOraCreatetime.nonEmpty){
      gafisQuery.createtime = DateConverter.convertString2Date(matchTask.getOraCreatetime, "yyyy-MM-dd HH:mm:ss")
    }else{
      gafisQuery.createtime = new Date()
    }

    gafisQuery.deletag = Gafis70Constants.DELETAG_USE
    gafisQuery.save()

    gafisQuery.oraSid
  }

  /**
    * 根据编号和查询类型发送查询
    * 最大候选50，优先级2，最小分数60
    *
    * @param cardId
    * @param matchType
    * @return
    */
  override def sendQueryByCardIdAndMatchType(cardId: String, matchType: MatchType, queryDBConfig: QueryDBConfig = new QueryDBConfig(None, None, None)): Long = {
    val matchTask = MatchTask.newBuilder
    matchType match {
      case MatchType.FINGER_TT |
           MatchType.FINGER_TL  =>
        matchTask.setMatchType(matchType)
      case other =>
        throw new IllegalArgumentException("unsupport MatchType:" + matchType)
    }
    matchTask.setObjectId(0)//这里设置为0也不会比中自己
    matchTask.setMatchId(cardId)
    matchTask.setTopN(50)
    matchTask.setObjectId(0)
    matchTask.setPriority(2)
    matchTask.setScoreThreshold(60)
    matchTask.setCommitUser("lnzc")

    sendQuery(matchTask.build(), queryDBConfig)
  }


  /**
    * 根据任务号sid获取比对状态
    *
    * @param oraSid
    * @param dbId
    * @return
    */
  override def getStatusBySid(oraSid: Long, dbId: Option[String]): Int = ???

  /**
    * 根据卡号信息发送查询, 不需要特征信息
    *
    * @param matchTask 只有查询信息不需要特征信息
    * @param queryDBConfig
    * @return 任务号
    */
  override def sendQuery(matchTask: MatchTask, queryDBConfig: QueryDBConfig): Long = {
    val matchTaskBuilder = matchTask.toBuilder
    val cardId = matchTask.getMatchId
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
    addMatchTask(matchTaskBuilder.build())
  }

  /**
    * 根据卡号查找第一个比对任务的状态, 如果没有获取到返回UN_KNOWN
    *
    * @param cardId
    * @return
    */
  override def findFirstQueryStatusByCardIdAndMatchType(cardId: String, matchType: MatchType, dbId: Option[String]): MatchStatus = ???

  /**
    * 获取查询信息GAQUERYSTRUCT
    *
    * @param oraSid
    * @param dbId
    * @return
    */
  override def getGAQUERYSTRUCT(oraSid: Long, dbId: Option[String]): GAQUERYSTRUCT = ???

  /**
    * 通过卡号查找第一个的比中结果
    *
    * @param cardId 卡号
    * @return 比对结果
    */
  override def findFirstQueryResultByCardId(cardId: String, dbId: Option[String]): Option[MatchResult] = ???

  /**
    * 获取查询结果信息
    *
    * @param oraSid
    * @return
    */
  override def getMatchResult(oraSid: Long, dbId: Option[String]): Option[MatchResult] = ???
}
