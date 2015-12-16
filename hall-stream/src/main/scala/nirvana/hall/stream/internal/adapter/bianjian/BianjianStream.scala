package nirvana.hall.stream.internal.adapter.bianjian

import javax.annotation.PostConstruct
import javax.sql.DataSource

import nirvana.hall.stream.services.StreamService
import org.apache.tapestry5.ioc.annotations.EagerLoad

/**
 * bianjian stream
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-16
 */
@EagerLoad
class BianjianStream {

  @PostConstruct
  def startStream(dataSource:DataSource,streamService: StreamService): Unit ={
    //read data from databa source
    //.........

    //push data
    //streamService.pushEvent()
  }
}
