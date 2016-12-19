package nirvana.hall.v62.fingerInfoInteractive.liaoning

import nirvana.hall.v62.fingerInfoInteractive.liaoning.services.FingerInfoService

/**
  * Created by win-20161010 on 2016/12/16.
  */
class FingerInfoServiceImpl extends FingerInfoService{
  override def fingerInfoBuild(obj: List[Map[String, Object]]): Unit = ???

  def  subBytes(src:Array[Byte], begin:Int,count:Int):Array[Byte] ={
    val bs = new Array[Byte](count)
    val length = count-begin;
    for(i <- begin to length){
      bs(i-begin) = src(i)
    }
    bs
  }
}
