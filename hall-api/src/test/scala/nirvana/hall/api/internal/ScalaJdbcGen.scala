package nirvana.hall.api.internal

import java.io.File
import java.util.Properties

import nirvana.hall.api.config.HallApiConfig
import org.apache.tapestry5.ioc.RegistryBuilder
import scalikejdbc.mapper._

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-01
 */
object ScalaJdbcGen {
  private[this] final val GENERATOR = "generator."
  private[this] final val PACKAGE_NAME = GENERATOR + "packageName"
  private[this] final val TEMPLATE = GENERATOR + "template"
  private[this] final val TEST_TEMPLATE = GENERATOR + "testTemplate"
  private[this] final val LINE_BREAK = GENERATOR + "lineBreak"
  private[this] final val CASE_CLASS_ONLY = GENERATOR + "caseClassOnly"
  private[this] final val ENCODING = GENERATOR + "encoding"
  private[this] final val AUTO_CONSTRUCT = GENERATOR + "autoConstruct"
  private[this] final val DEFAULT_AUTO_SESSION = GENERATOR + "defaultAutoSession"
  private[this] final val DATETIME_CLASS = GENERATOR + "dateTimeClass"
  private[this] final val RETURN_COLLECTION_TYPE = GENERATOR + "returnCollectionType"
  private[this] val generatorKeys = Set(
    PACKAGE_NAME, TEMPLATE, TEST_TEMPLATE, LINE_BREAK, CASE_CLASS_ONLY,
    ENCODING, AUTO_CONSTRUCT, DEFAULT_AUTO_SESSION, DATETIME_CLASS, RETURN_COLLECTION_TYPE)
  private[this] val allKeys = generatorKeys

  def main(args: Array[String]): Unit = {

    val modules = Seq[String](
      "nirvana.hall.api.LocalDataSourceModule",
      "nirvana.hall.api.internal.TestModule").map(Class.forName)
    val registry = RegistryBuilder.buildAndStartupRegistry(modules: _*)

    val hallApiConfig = registry.getService(classOf[HallApiConfig])

    val srcDir = new File("hall-api/src/main/scala")
    val testDir = new File("hall-api/src/test/scala")
    val properties = new Properties()
    properties.load(getClass.getResourceAsStream("/scalikejdbc-generator.properties"))
    val generatorSettings = loadGeneratorSettings(properties)
    val config = GeneratorConfig(
      srcDir = srcDir.getAbsolutePath,
      testDir = testDir.getAbsolutePath,
      packageName = generatorSettings.packageName,
      template = GeneratorTemplate(generatorSettings.template),
      testTemplate = GeneratorTestTemplate(generatorSettings.testTemplate),
      lineBreak = LineBreak(generatorSettings.lineBreak),
      caseClassOnly = generatorSettings.caseClassOnly,
      encoding = generatorSettings.encoding,
      autoConstruct = generatorSettings.autoConstruct,
      defaultAutoSession = generatorSettings.defaultAutoSession,
      dateTimeClass = generatorSettings.dateTimeClass,
      tableNameToClassName = generatorSettings.tableNameToClassName,
      columnNameToFieldName = generatorSettings.columnNameToFieldName,
      returnCollectionType = generatorSettings.returnCollectionType)

    val jdbc = JDBCSettings(
      driver = hallApiConfig.api.db.driver,
      url = hallApiConfig.api.db.url,
      username = hallApiConfig.api.db.user,
      password = hallApiConfig.api.db.password,
      schema = null //hallApiConfig.api.db.user
      )
    val className = None
    Class.forName(jdbc.driver) // load specified jdbc driver
    val model = Model(jdbc.url, jdbc.username, jdbc.password)
    model.allTables(jdbc.schema).filterNot(_.name == "SCHEMA_MIGRATIONS").map { table =>
      println(table.name)
      new CodeGenerator(table, className)(config) {
        override def modelAll(): String = {
          val all = super.modelAll()
          all.replaceAll(" override val autoSession = AutoSession", "override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()")
        }
      }
    }.foreach { g =>
      g.writeModel()
    }
  }

  private[this] def loadGeneratorSettings(props: Properties): GeneratorSettings = {
    val defaultConfig = GeneratorConfig()
    GeneratorSettings(
      packageName = getString(props, PACKAGE_NAME).getOrElse(defaultConfig.packageName),
      template = getString(props, TEMPLATE).getOrElse(defaultConfig.template.name),
      testTemplate = getString(props, TEST_TEMPLATE).getOrElse(GeneratorTestTemplate.specs2unit.name),
      lineBreak = getString(props, LINE_BREAK).getOrElse(defaultConfig.lineBreak.name),
      caseClassOnly = getString(props, CASE_CLASS_ONLY).map(_.toBoolean).getOrElse(defaultConfig.caseClassOnly),
      encoding = getString(props, ENCODING).getOrElse(defaultConfig.encoding),
      autoConstruct = getString(props, AUTO_CONSTRUCT).map(_.toBoolean).getOrElse(defaultConfig.autoConstruct),
      defaultAutoSession = getString(props, DEFAULT_AUTO_SESSION).map(_.toBoolean).getOrElse(defaultConfig.defaultAutoSession),
      dateTimeClass = getString(props, DATETIME_CLASS).map {
        name => DateTimeClass.JodaDateTime
      }.getOrElse(defaultConfig.dateTimeClass),

      defaultConfig.tableNameToClassName,
      defaultConfig.columnNameToFieldName,
      returnCollectionType = getString(props, RETURN_COLLECTION_TYPE).map { name =>
        ReturnCollectionType.List
      }.getOrElse(defaultConfig.returnCollectionType))
  }

  private[this] def getString(props: Properties, key: String): Option[String] =
    Option(props.get(key)).map { value =>
      val str = value.toString
      if (str.startsWith("\"") && str.endsWith("\"") && str.length >= 2) {
        str.substring(1, str.length - 1)
      }
      else str
    }

  case class JDBCSettings(driver: String, url: String, username: String, password: String, schema: String)

  case class GeneratorSettings(
    packageName: String,
    template: String,
    testTemplate: String,
    lineBreak: String,
    caseClassOnly: Boolean,
    encoding: String,
    autoConstruct: Boolean,
    defaultAutoSession: Boolean,
    dateTimeClass: DateTimeClass,
    tableNameToClassName: String => String,
    columnNameToFieldName: String => String,
    returnCollectionType: ReturnCollectionType)
}
