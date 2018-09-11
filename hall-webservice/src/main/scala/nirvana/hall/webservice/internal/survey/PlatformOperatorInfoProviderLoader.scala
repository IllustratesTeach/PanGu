package nirvana.hall.webservice.internal.survey

/**
  * Created by yuchen on 2018/8/3.
  */
object PlatformOperatorInfoProviderLoader {

  private lazy val provider:PlatformOperatorInfoProvider = createProvider(providerClassName)
  private var providerClassName:String = _

  def createProvider(className:String):PlatformOperatorInfoProvider={
    providerClassName = className
    Thread.currentThread().getContextClassLoader
      .loadClass(providerClassName).newInstance().asInstanceOf[PlatformOperatorInfoProvider]
  }
}
