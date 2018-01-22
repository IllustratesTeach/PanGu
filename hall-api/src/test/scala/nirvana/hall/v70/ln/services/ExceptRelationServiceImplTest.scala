package nirvana.hall.v70.ln.services

import java.io.FileOutputStream

import nirvana.hall.api.services.ExportRelationService
import nirvana.hall.v70.internal.BaseV70TestCase
import org.junit.Test

/**
  * Created by ssj on 2017/11/7.
  */
class ExceptRelationServiceImplTest extends BaseV70TestCase{

  @Test
  def test_get: Unit ={

    val service = getService[ExportRelationService]
    val relation = service.exportMatchRelation("0","12210")  //TT  10110  //LL  10513  //tl  8408  //lt  12210
    if(relation!= null){
      val fileOutPutStream = new FileOutputStream("D://123.fpt")
      relation.writeTo(fileOutPutStream)
      fileOutPutStream.flush()
      fileOutPutStream.close()
    }
    println("asa")

  }
}
