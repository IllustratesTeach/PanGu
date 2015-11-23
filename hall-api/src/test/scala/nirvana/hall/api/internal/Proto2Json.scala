package nirvana.hall.api.internal

import java.io.{BufferedInputStream, File}

import org.apache.commons.io.{IOUtils, FileUtils}
import org.apache.tapestry5.json.JSONObject

import scala.collection.JavaConversions._

/**
 * Created by wangjue on 2015/11/17.
 */
object Proto2Json {
  def main(args:Array[String]): Unit ={
    val f = FileUtils.getFile(new File("hall-protocol/src/main/proto/sys"),"Dict.proto")
    val json=f.getAbsolutePath
      .replaceAll(".proto$",".json")
    val p = Runtime.getRuntime.exec(Array[String]("node",
        "E:\\wj-nirvana-hall\\ProtoBuf.js-3.8.2\\ProtoBuf.js-3.8.2\\bin\\proto2js",
        f.getAbsolutePath,
        "-path=hall-protocol/src/main/proto",
        "-min"))
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
