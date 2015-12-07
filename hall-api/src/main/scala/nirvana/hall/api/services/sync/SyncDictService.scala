package nirvana.hall.api.services.sync

import nirvana.hall.api.services.AutoSpringDataSourceSession
import nirvana.hall.protocol.sys.DictProto.{DictType, DictData}
import org.springframework.transaction.annotation.Transactional
import scalikejdbc.DBSession

/**
 * Created by songpeng on 15/12/7.
 */
trait SyncDictService {
  /**
   * 同步字典
   * @param dictType 字典类型
   * @param dictDataList 要同步的字典数据
   * @param session
   * @return
   */
  @Transactional
  def syncDict(dictType: DictType, dictDataList: Seq[DictData])(implicit session: DBSession = AutoSpringDataSourceSession.apply())
}
