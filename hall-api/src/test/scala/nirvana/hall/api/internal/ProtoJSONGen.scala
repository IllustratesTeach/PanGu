package nirvana.hall.api.internal

import java.io.{BufferedInputStream, File}

import org.apache.commons.io.{FileUtils, IOUtils}
import org.apache.tapestry5.json.JSONObject

import scala.collection.JavaConversions._

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-22
 */
object ProtoJSONGen {
  def main(args:Array[String]): Unit ={
    val files = FileUtils.listFiles(new File("hall-protocol/src/main/proto"),Array[String]("proto"),true)
    files.foreach{f=>
      val json=f.getAbsolutePath
        .replaceAll("hall-protocol/src/main/proto","hall-protocol/src/main/resources/proto")
        .replaceAll(".proto$",".json")
      val p = Runtime.getRuntime.exec(Array[String]("/Users/jcai/HBuilderProjects/ProtoBuf.js-3.8.2/bin/proto2js",
        f.getAbsolutePath,
        "-path=hall-protocol/src/main/proto","-min"))
      if(p.waitFor() != 0){
        val es = p.getErrorStream
        println(IOUtils.readLines(es).mkString(","))
        println("error")
      }

      val in = new BufferedInputStream(p.getInputStream());
      val str = IOUtils.readLines(in).mkString("")
      val jsonObject = new JSONObject(str)
      jsonObject.remove("imports")
      jsonObject.remove("options")
      FileUtils.writeStringToFile(new File(json),jsonObject.toCompactString)
    }
  }
}
