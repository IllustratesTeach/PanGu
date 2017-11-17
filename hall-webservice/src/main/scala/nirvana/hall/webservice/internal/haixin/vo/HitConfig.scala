package nirvana.hall.webservice.internal.haixin.vo


import java.util
import javax.xml.bind.annotation._

/**
  * Created by yuchen on 2017/8/3.
  */
@XmlRootElement(name = "Root")
@XmlAccessorType(XmlAccessType.FIELD)
class HitConfig{

  var List = new ListItem

}

@XmlAccessorType(XmlAccessType.FIELD)
class ListItem{
   var Item = new util.ArrayList[String]()

  @XmlAttribute(name = "ItemCnt")
  var itemCnt:Int = _
}

