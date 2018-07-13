package nirvana.hall.webservice.internal.survey

import monad.support.services.MonadException
import nirvana.hall.webservice.internal.survey.SurveyException.{DataPackageNotAvailableException, ImageException}

/**
  * Created by yuchen on 2018/7/3.
  */
object SurveyFatal {

  def unapply(t: Throwable): Boolean = t match {
    case _: MonadException | _: DataPackageNotAvailableException | _:ImageException => true
    case _ => false
  }

}
