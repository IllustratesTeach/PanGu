package nirvana.hall.extractor.app;

import monad.support.services.AppBootstrap;

/**
 * bootstrap hall extractor application
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-13
 */
public class HallExtractorBootstrap extends AppBootstrap {
    public static void main(String[] args) throws Exception {
        start("nirvana.hall.extractor.app.HallExtractorApp", args);
    }
}
