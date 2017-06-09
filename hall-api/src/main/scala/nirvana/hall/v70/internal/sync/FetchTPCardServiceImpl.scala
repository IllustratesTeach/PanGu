package nirvana.hall.v70.internal.sync

import javax.sql.DataSource

import nirvana.hall.api.services.sync.FetchTPCardService
import nirvana.hall.protocol.api.FPTProto.TPCard
import org.apache.tapestry5.json.JSONObject

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/8/18.
 */
class FetchTPCardServiceImpl(implicit dataSource: DataSource) extends SyncDataFetcher with FetchTPCardService{
  override val SYNC_SQL: String = "select t.personid as sid, t.seq from gafis_person t left join gafis_logic_db_fingerprint db on t.personid=db.fingerprint_pkid where db.logic_db_pkid =? and t.seq >=? and t.seq <=?"
  //override val SYNC_SQL: String = "select t.personid as sid, t.seq from gafis_person t left join gafis_logic_db_fingerprint db on t.personid=db.fingerprint_pkid where db.logic_db_pkid =? and t.seq >=? and t.seq <=? and t.personid not in (select h.serviceid from HALL_DS_PERSON h where h.status != '0')"


  /**
   * 获取捺印同步卡号列表
   * @param seq
   * @param size
   * @param dbId
   */
  override def fetchCardId(seq: Long, size: Int, dbId: Option[String]): Seq[(String, Long)] = {
    val cardIdList = new ArrayBuffer[(String, Long)]()
    doFetcher(cardIdList, seq, size, dbId)
    cardIdList
  }

  /**
   * 获取最大的seq值
   * @return
   */
  override def getMaxSeq(dbId: Option[String])(implicit dataSource: DataSource): Long = {
    getSeqBySql(s"select max(seq) from gafis_person t left join gafis_logic_db_fingerprint db on t.personid=db.fingerprint_pkid where db.logic_db_pkid='${dbId.get}' ")
  }

  /**
   * 获取最小的seq值, 大于from
   * @param from
   * @return
   */
  override def getMinSeq(from: Long, dbId: Option[String])(implicit dataSource: DataSource): Long = {
     getSeqBySql(s"select min(seq) from gafis_person t left join gafis_logic_db_fingerprint db on t.personid=db.fingerprint_pkid where db.logic_db_pkid='${dbId.get}' and t.seq >${from} ")
    //getSeqBySql(s"select min(t.seq) from gafis_person t left join gafis_logic_db_fingerprint db on t.personid=db.fingerprint_pkid where db.logic_db_pkid='${dbId.get}' and t.seq >${from} and t.personid not in (select t1.serviceid from hall_ds_person t1 where t1.status != '0')")
  }

  override def validateByReadStrategy(tpCard: TPCard, readStrategy: String): Boolean = {

    val strategy = new JSONObject(readStrategy)
    if(strategy.has("datasource")){
      val dataSourceStrategy = strategy.getString("datasource")
      val dataSource = tpCard.getStrDataSource
      if(dataSourceStrategy.startsWith("!")){
        if(dataSource.equals(dataSourceStrategy.substring(1,dataSourceStrategy.length))){
          return false
        }
      }else{
        if(!dataSource.equals(dataSourceStrategy)){
          return false
        }
      }
    }

    if(strategy.has("cardid")){
      val cardIdStrategy = strategy.getString("cardid")
      val cardId = tpCard.getStrCardID
      if(cardIdStrategy.startsWith("=")){
        if(!cardId.startsWith(cardIdStrategy.substring(1,cardIdStrategy.length))){
          return false
        }
      }else{
        if(!cardId.startsWith(cardIdStrategy)){
          return false
        }
      }
    }
    true
  }
}
