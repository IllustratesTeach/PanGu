package nirvana.hall.api.services

import nirvana.hall.api.HallDatasource

trait HallDatasourceService {

    def save(hallDatasource:HallDatasource, table:String): Unit

}
