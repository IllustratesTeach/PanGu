package nirvana.hall.api.services

import nirvana.hall.protocol.sys.SyncDictProto.DictType
import nirvana.hall.protocol.sys.SyncDictProto.SyncDictResponse.SyncData
import org.springframework.transaction.annotation.Transactional
import scalikejdbc.DBSession

/**
 * 字典同步 service
 * Created by songpeng on 15/11/4.
 */
trait SyncDictService {

  /**
   * 根据字典类型获取全部字典数据
   * @param dictType 字典类型
   * @return
   */
  def findAllDict(dictType: DictType): Seq[SyncData]

  /**
   * 同步字典
   * @param dictType 字典类型
   * @param syncDataList 要同步的字典数据
   * @param session
   * @return
   */
  @Transactional
  def syncDict(dictType: DictType, syncDataList: Seq[SyncData])(implicit session: DBSession = AutoSpringDataSourceSession.apply())

}
