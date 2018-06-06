package nirvana.hall.database

import org.junit.Test
import stark.migration.SchemaDumper

class HallSchemaDumperTest {

  @Test
  def test_SchemaDumper: Unit ={
    System.setProperty("dump.jdbc.url","jdbc:oracle:thin:@192.168.1.213:1521:oragafis")
    System.setProperty("dump.jdbc.user","gafis_nj")
    System.setProperty("dump.jdbc.pass","gafis")
    System.setProperty("dump.jdbc.schema","gafis_nj")
    SchemaDumper.main(null)
  }
}
