package nirvana.hall.stream.app;

import monad.support.services.AppBootstrap;

/**
 * bootstrap hall image application
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-13
 */
public class HallStreamBootstrap extends AppBootstrap {
    public static void main(String[] args) throws Exception {
        start("nirvana.hall.stream.app.HallStreamApp", args);
    }
}
