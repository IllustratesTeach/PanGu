package nirvana.hall.api.internal

import nirvana.hall.extractor.jni.JniLoader

/**
  * Created by songpeng on 2017/4/28.
  * 这里重新定义FeatureExtractorImpl，由于提取特征动态库加载依赖授权，对于不需要提取特征功能的服务，就不必启动时就加载jni（在HallApiApp里)
  */
class FeatureExtractorImpl extends nirvana.hall.extractor.internal.FeatureExtractorImpl{
  JniLoader.loadJniLibrary(".", null)
}
