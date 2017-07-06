package nirvana.hall.webservice.services.xingzhuan

import nirvana.hall.webservice.HallDatasource

trait HallDatasourceService {

    def save(hallDatasource:HallDatasource, table:String): Unit

}
