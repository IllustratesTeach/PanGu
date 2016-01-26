package nirvana.hall.api.internal.query

import java.util.Date
import javax.persistence.EntityManagerFactory

import com.google.protobuf.ByteString
import nirvana.hall.api.jpa.{GafisNormalqueryQueryque, GafisQuery7to6}
import nirvana.hall.api.services.query.Query7to6Service
import nirvana.hall.c.services.ganumia.gadbdef.GADB_KEYARRAY
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask.LatentMatchData
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.gloclib.{galoctp, gaqryqueConverter}
import nirvana.hall.v70.config.HallV70Config
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.jboss.netty.buffer.ChannelBuffers
import org.springframework.orm.jpa.{EntityManagerFactoryUtils, EntityManagerHolder}
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

/**
 * Created by songpeng on 15/12/9.
 */
class Query7to6ServiceImpl(facade:V62Facade, v62Config:HallV62Config, v70Config: HallV70Config)
  extends Query7to6Service{

  private val STATUS_MATCHING =1.toShort//任务状态，正在比对

  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, entityManagerFactory: EntityManagerFactory): Unit = {
    periodicExecutor.addJob(new CronSchedule(v70Config.sync62Cron), "query-70to62", new Runnable {
      override def run(): Unit = {
        try {
          val emHolder = new EntityManagerHolder(entityManagerFactory.createEntityManager())
          TransactionSynchronizationManager.bindResource(entityManagerFactory, emHolder)
          doWork
          TransactionSynchronizationManager.unbindResource(entityManagerFactory)
          EntityManagerFactoryUtils.closeEntityManager(emHolder.getEntityManager)
        }
        catch {
          case e: Throwable =>
            e.printStackTrace()
        }
      }
    })
  }

  private def doWork: Unit ={
    getMatchTask.foreach{ matchTask =>
      sendQuery(matchTask)
      doWork
    }

  }

  /**
   * 获取一条查询任务
   * @return
   */
  @Transactional
  override def getMatchTask: Option[MatchTask] ={
    val query = GafisNormalqueryQueryque.where("status=0 and syncTargetSid is not null").desc("priority").asc("oraSid").takeOption

    query.foreach(updateGafisNormalqueryQueryqueStatusMatching)

    query.map(convertGafisNormalqueryQueryque2MatchTask)
  }

  /**
   * 更新状态为正在比对
   * @param queryque
   * @return
   */
  private def updateGafisNormalqueryQueryqueStatusMatching(queryque: GafisNormalqueryQueryque): Unit ={
    GafisNormalqueryQueryque.where("pkId=?1", queryque.pkId).update(status=STATUS_MATCHING, begintime=new Date())
  }

  private def convertGafisNormalqueryQueryque2MatchTask(query: GafisNormalqueryQueryque): MatchTask = {
    val matchTask = MatchTask.newBuilder()
    matchTask.setMatchId(query.keyid)
    matchTask.setMatchType(MatchType.valueOf(query.querytype+1))
    matchTask.setObjectId(query.oraSid)//必填项，现在用于存放oraSid
    matchTask.setPriority(query.priority.toInt)
    matchTask.setScoreThreshold(query.minscore)

    val mics = new galoctp{}.GAFIS_MIC_GetDataFromStream(ChannelBuffers.wrappedBuffer(query.mic))
    mics.foreach{mic =>
      if(mic.bIsLatent == 1){
        val ldata = LatentMatchData.newBuilder()
        ldata.setMinutia(ByteString.copyFrom(mic.pstMnt_Data))
        matchTask.setLData(ldata)
      }else{
        //TODO pos 6to8 ??? 平面？？？
        if(mic.nItemData <= 10)
          matchTask.getTDataBuilder.addMinutiaDataBuilder().setMinutia(ByteString.copyFrom(mic.pstMnt_Data)).setPos(mic.nItemData)
      }
    }
    matchTask.build()
  }

  /**
   * 发送比对任务
   * @param matchTask
   * @return
   */
  override def sendQuery(matchTask: MatchTask): Unit = {
    val key = matchTask.getMatchId.getBytes()
    val pstKey = new GADB_KEYARRAY
    pstKey.nKeyCount = 1
    pstKey.nKeySize = key.size.asInstanceOf[Short]
    pstKey.pKey_Data = key

    val queryStruct = gaqryqueConverter.convertProtoBuf2GAQUERYSTRUCT(matchTask)(v62Config)
//    val idx= 1 to 10 map(x=>x.asInstanceOf[Byte]) toArray
//    val retval = facade.NET_GAFIS_QUERY_Submit(v62Config.queryTable.dbId.toShort, v62Config.queryTable.tableId.toShort, pstKey, queryStruct, idx)
//    //记录6.2的查询SID
//    retval.foreach{ ret =>
//      new GafisQuery7to6(matchTask.getObjectId,gaqryqueConverter.convertSixByteArrayToLong(ret.nSID)).save()
//    }
    val oraSid = facade.NET_GAFIS_QUERY_Add(v62Config.queryTable.dbId.toShort, v62Config.queryTable.tableId.toShort, queryStruct)
    new GafisQuery7to6(matchTask.getObjectId, oraSid).save()
  }



}
