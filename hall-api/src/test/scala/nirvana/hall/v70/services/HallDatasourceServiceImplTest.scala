package nirvana.hall.v70.services

import nirvana.hall.api.HallDatasource
import nirvana.hall.api.services.HallDatasourceService
import nirvana.hall.v70.internal.BaseV70TestCase
import org.junit.Test

class HallDatasourceServiceImplTest extends BaseV70TestCase{

  @Test
  def save: Unit = {
    val service = getService[HallDatasourceService]
    val hallDatasource = new HallDatasource
    hallDatasource.serviceid = "R3702020100002016000200"
    hallDatasource.distServiceid = "1"
    hallDatasource.createServiceType = 1
    hallDatasource.createOperationType= 1
    hallDatasource.updateServiceType= 1
    hallDatasource.updateOperationType= 2
    service.save(hallDatasource, HallDatasource.TABLE_V70_PERSON)
  }

}
