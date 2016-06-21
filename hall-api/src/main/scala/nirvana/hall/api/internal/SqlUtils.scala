package nirvana.hall.api.internal

/**
 * Created by songpeng on 16/6/17.
 */
object SqlUtils {
  /**
   * 包装updatetime 转为YYYYMMDDHH24MISS
   * @param func
   * @return
   */
  def wrapUpdateTimeAsLong(func:Option[String]=None): String ={
    func match{
      case Some(f)=>
        s"$f(to_number(to_char(t.updatetime,'YYYYMMDDHH24MISS')))"
      case None =>
        "(to_number(to_char(t.updatetime,'YYYYMMDDHH24MISS')))"
    }
  }
  /**
   * 包装modtime 转为YYYYMMDDHH24MISS
   * @param func
   * @return
   */
  def wrapModTimeAsLong(func:Option[String]=None): String ={
    func match{
      case Some(f)=>
        s"$f(to_number(to_char(t.modtime,'YYYYMMDDHH24MISS')))"
      case None =>
        "(to_number(to_char(t.modtime,'YYYYMMDDHH24MISS')))"
    }
  }
}
