package nirvana.hall.matcher.internal.adapter.gafis6

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-06-01
  */
package object sync {

  def wrapUpdateTimeAsLong(func:Option[String]=None): String ={
    func match{
      case Some(f)=>
        s"($f(t.updatetime) - to_date('01-JAN-1970','DD-MON-YYYY')) * 86400"
      case None =>
        "(t.updatetime - to_date('01-JAN-1970','DD-MON-YYYY')) * 86400"
    }
  }

}
