// Copyright 2015 Jun Tsai. All rights reserved.
// site: http://www.ganshane.com
package nirvana.hall.api

import nirvana.hall.api.internal.ProtobufServletFilter
import org.apache.tapestry5.ioc.annotations.Contribute
import org.apache.tapestry5.ioc.{Configuration, MappedConfiguration, OrderedConfiguration}
import org.apache.tapestry5.services.{ClasspathAssetAliasManager, HttpServletRequestFilter, HttpServletRequestHandler, LibraryMapping}

/**
 * define some web module
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-01-20
 */
object LocalApiWebModule {

  @Contribute(classOf[HttpServletRequestHandler])
  def provideProtobufFilter(configuration: OrderedConfiguration[HttpServletRequestFilter]): Unit = {
    configuration.addInstance("protobuf", classOf[ProtobufServletFilter], "after:GZIP")
  }

  def contributeComponentClassResolver(configuration: Configuration[LibraryMapping]) {
    configuration.add(new LibraryMapping("api", "nirvana.hall.api"))
  }

  @Contribute(classOf[ClasspathAssetAliasManager])
  def addApplicationAndTapestryMappings(configuration: MappedConfiguration[String, String]){
    configuration.add("proto", "proto")
  }
}
