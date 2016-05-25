package nirvana.hall.stream.services

import java.io.InputStream

import com.google.protobuf.ByteString

/**
 * Created by wangjue on 2016/5/24.
 */
trait FeatureConverterService {


  def converter(oldMnt : InputStream): Option[ByteString]
}
