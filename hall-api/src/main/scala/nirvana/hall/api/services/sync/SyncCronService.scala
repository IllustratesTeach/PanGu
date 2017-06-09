package nirvana.hall.api.services.sync

import nirvana.hall.protocol.api.FPTProto.{LPCard, TPCard}
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import org.apache.tapestry5.json.JSONObject

/**
 * Created by songpeng on 16/8/18.
 */
trait SyncCronService {

  /**
   * 定时任务调用方法
   */
  def doWork()

  /**
   * 验证捺印卡信息,是否符合策略
   * @param tpCard
   * @param writeStrategy
   */
  def validateTPCardByWriteStrategy(tpCard: TPCard, writeStrategy: String): Boolean = {
    var flag = true
    val strategy = new JSONObject(writeStrategy)
    if (strategy.has("cardid")){
      val cardId = tpCard.getStrCardID
      val cardStrategy = strategy.getString("cardid")
      if(cardStrategy.startsWith("!")){
        flag = !cardId.startsWith(cardStrategy.substring(1,cardStrategy.length))
      }else{
        flag = cardId.startsWith(cardStrategy)
      }
    }
    flag
  }

  def validateLPCardByWriteStrategy(lpCard: LPCard, writeStrategy: String): Boolean = {
    var flag = true
    val strategy = new JSONObject(writeStrategy)
    if (strategy.has("cardid")){
      val cardId = lpCard.getStrCardID
      val cardStrategy = strategy.getString("cardid")
      if(cardStrategy.startsWith("!")){
        flag = !cardId.startsWith(cardStrategy.substring(1,cardStrategy.length))
      }else{
        flag = cardId.startsWith(cardStrategy)
      }
    }
    flag
  }

  def validateMatchTaskByWriteStrategy(matchTask: MatchTask, writeStrategy: String): Boolean = true //TODO

  def validateMatchResultByWriteStrategy(matchResult: MatchResult, writeStrategy: String): Boolean = true //TODO
}
