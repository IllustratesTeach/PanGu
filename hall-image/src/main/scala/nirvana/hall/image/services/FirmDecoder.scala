package nirvana.hall.image.services

/**
 * firm decoder
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
trait FirmDecoder{
  def decode(code:String,cpr_data:Array[Byte],width:Int,height:Int,dpi:Int): Array[Byte];
}
