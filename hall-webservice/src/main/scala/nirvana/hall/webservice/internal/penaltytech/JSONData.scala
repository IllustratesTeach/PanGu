package nirvana.hall.webservice.internal.penaltytech

import java.util.concurrent.ConcurrentHashMap

import com.alibaba.fastjson.JSONObject

import scala.collection.mutable
import scala.language.experimental.macros
import scala.language.reflectiveCalls
import scala.reflect.runtime.universe
/**
  * @author <a href="yuchen_1997_200486@126.com">yuchen</a>.
  * @since 2018/12/9
  */
object JSONData {
  private lazy val  mirror = universe.runtimeMirror(Thread.currentThread().getContextClassLoader)
}

trait JSONData{
  private lazy val instanceMirror = JSONData.mirror.reflect(this)
  private lazy val clazzSymbol = instanceMirror.symbol
  private lazy val clazzType = clazzSymbol.asType.toType

  private val concurrentHashMap = new ConcurrentHashMap[String,Any]

  def getJSONObject: JSONObject ={
    var members = concurrentHashMap.get(getClass)
    val jsonObject = new JSONObject()
    members = clazzType.members.filter(_.isTerm)
      .filter(_.asTerm.isVar)
      .toSeq.reverse.foreach {
      m =>
        val term = instanceMirror.reflectField(m.asTerm)
        jsonObject.put(term.symbol.name.toString.trim,term.get)
    }
    jsonObject
  }

  def getJSONString: String ={
    var members = concurrentHashMap.get(getClass)
    val jsonObject = new JSONObject()
    members = clazzType.members.filter(_.isTerm)
      .filter(_.asTerm.isVar)
      .toSeq.reverse.foreach {
      m =>
        val term = instanceMirror.reflectField(m.asTerm)
        jsonObject.put(term.symbol.name.toString.trim,term.get)
    }
    jsonObject.toString()
  }

  def getJSONMap:mutable.HashMap[String,Any] = {
    var members = concurrentHashMap.get(getClass)
    var map = mutable.HashMap[String,Any]()
    members = clazzType.members.filter(_.isTerm)
      .filter(_.asTerm.isVar)
      .toSeq.reverse.foreach {
      m =>
        val term = instanceMirror.reflectField(m.asTerm)
        map += (term.symbol.name.toString.trim -> term.get)
    }
    map
  }
}
