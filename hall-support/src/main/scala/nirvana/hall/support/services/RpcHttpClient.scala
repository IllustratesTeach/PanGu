package nirvana.hall.support.services

import com.google.protobuf.GeneratedMessage.GeneratedExtension
import monad.rpc.protocol.CommandProto.BaseCommand

/**
 * rpc http client
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-15
 */
trait RpcHttpClient {
  /**
   * rpc http client
   * @param url url
   * @return response
   */
  def call[T](url: String,extension: GeneratedExtension[BaseCommand, T], value: T):BaseCommand
}
