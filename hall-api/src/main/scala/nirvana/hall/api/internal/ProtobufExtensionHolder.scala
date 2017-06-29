package nirvana.hall.api.internal

import com.google.protobuf.GeneratedMessage

/**
 * protobuf exension registry
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-05-21
 */
object ProtobufExtensionHolder {
  var extensions: Seq[GeneratedMessage.GeneratedExtension[_, _]] = _
}
