package nirvana.hall.v62.internal

import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.gloclib.glocndef.GNETREQUESTHEADOBJECT
import nirvana.hall.v62.services.{ChannelOperator, V62ServerAddress}

/**
  * provide AncientClient instance
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-02
 */
trait AncientClientSupport {
  /**
   * execute in channel
   */
  def executeInChannel[T](channelOperator: ChannelOperator=>T):T={
    new XSocketAncientClient(serverAddress.host,serverAddress.port,serverAddress.connectionTimeoutSecs,serverAddress.readTimeoutSecs).executeInChannel(channelOperator)
  }
  def serverAddress:V62ServerAddress
  def createRequestHeader:GNETREQUESTHEADOBJECT={
    val header = new GNETREQUESTHEADOBJECT
    header.szUserName=serverAddress.user
    serverAddress.password.foreach(header.szUserPass = _)
    header.cbSize = 192
    header.nMajorVer = 6
    header.nMinorVer = 1

    header
  }




}
class NoneResponse extends AncientData{
}
