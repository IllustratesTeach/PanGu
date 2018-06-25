package nirvana.hall.matcher.service

import nirvana.protocol.SyncDataProto.{SyncDataRequest, SyncDataResponse}

/**
 * Created by songpeng on 16/3/20.
 */
trait SyncDataService {

  /**
   * 同步数据
   * @param syncDataRequest
   * @return
   */
  def syncData( syncDataRequest:SyncDataRequest):SyncDataResponse
}

protected trait Fetcher{
  def doFetch(syncDataResponse: SyncDataResponse.Builder, size: Int, from: Long):Unit
}
trait PersonFetcher extends Fetcher
trait CaseFetcher extends Fetcher
trait TemplateFingerFetcher extends Fetcher
trait TemplatePalmFetcher extends Fetcher
trait LatentFingerFetcher extends Fetcher
trait LatentPalmFetcher extends Fetcher
