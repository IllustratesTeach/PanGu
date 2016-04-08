package nirvana.hall.matcher.pages

import nirvana.hall.matcher.service.PutMatchResultService
import org.apache.tapestry5.ioc.annotations.Inject
import org.apache.tapestry5.services.{Request, Response}

/**
 * Created by songpeng on 16/3/20.
 */
class PutMatchResult {
  @Inject
  private var request: Request = _
  @Inject
  private var response: Response = _

  private var putMatchResultService: PutMatchResultService = _

  def onActivate = {

  }
}
