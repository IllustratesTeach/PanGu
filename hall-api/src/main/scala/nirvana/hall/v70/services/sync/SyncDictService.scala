package nirvana.hall.v70.services.sync

import nirvana.hall.protocol.sys.DictProto.{DictData, DictType}
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 15/12/7.
 */
trait SyncDictService {
  /**
   * 同步字典
   * @param dictType 字典类型
   * @param dictDataList 要同步的字典数据
   * @return
   */
  @Transactional
  def syncDict(dictType: DictType, dictDataList: Seq[DictData])
}
