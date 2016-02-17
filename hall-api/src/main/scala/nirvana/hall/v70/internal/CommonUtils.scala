package nirvana.hall.v70.internal

import java.util.UUID

/**
 * Created by songpeng on 16/2/17.
 */
object CommonUtils {


  def getUUID(): String ={
    UUID.randomUUID().toString.replace("-","")
  }
}
