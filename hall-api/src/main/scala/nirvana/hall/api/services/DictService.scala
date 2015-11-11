package nirvana.hall.api.services

import nirvana.hall.protocol.sys.DictProto.{DictData, DictType}
import org.springframework.transaction.annotation.Transactional
import scalikejdbc.DBSession

/**
 * 字典同步 service
 * Created by songpeng on 15/11/4.
 */
trait DictService {

  /**
   * 根据字典类型获取全部字典数据
   * @param dictType 字典类型
   * @return
   */
  def findAllDict(dictType: DictType): Seq[DictData]

  /**
   * 同步字典
   * @param dictType 字典类型
   * @param dictDataList 要同步的字典数据
   * @param session
   * @return
   */
  @Transactional
  def syncDict(dictType: DictType, dictDataList: Seq[DictData])(implicit session: DBSession = AutoSpringDataSourceSession.apply())

  /**
   * 查询字典列表
   * @param dictType 字典类型
   * @return
   */
  def findDictList(dictType: DictType, code: Option[String], name: Option[String], from:Int = 0, size:Int ) :Seq[(String, String)]
}
