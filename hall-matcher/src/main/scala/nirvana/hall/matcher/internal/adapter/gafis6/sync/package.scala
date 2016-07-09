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
        s"$f(to_number(to_char(t.updatetime,'YYYYMMDDHH24MISS')))"
      case None =>
        "(to_number(to_char(t.updatetime,'YYYYMMDDHH24MISS')))"
    }
  }
  def wrapModTimeAsLong(func:Option[String]=None): String ={
    func match{
      case Some(f)=>
        s"$f(to_number(to_char(t.modtime,'YYYYMMDDHH24MISS')))"
//        s"$f(t.modtime)"
      case None =>
        "(to_number(to_char(t.modtime,'YYYYMMDDHH24MISS')))"
//        "(t.modtime)"
    }
  }
}
