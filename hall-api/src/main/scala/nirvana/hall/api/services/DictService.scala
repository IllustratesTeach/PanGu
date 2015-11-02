package nirvana.hall.api.services

import nirvana.hall.protocol.sys.DictListProto.DictListRequest.DictType

/**
 * Created by songpeng on 15/10/28.
 */
trait DictService {

  def findDictList(dictType: DictType) :Seq[(String, String)]
}
