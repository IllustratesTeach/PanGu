package nirvana.hall.image.app;

import monad.support.services.AppBootstrap;

/**
 * bootstrap hall image application
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-13
 */
public class HallImageBootstrap extends AppBootstrap {
    public static void main(String[] args) throws Exception {
        start("nirvana.hall.image.app.HallImageApp", args);
    }
}
