package nirvana.hall.api.app;

import monad.support.services.AppBootstrap;

/**
 * nirvana hall api bootstrap
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-08
 */
public class SuowenApiBootstrap extends AppBootstrap{
    public static void main(String[] args) throws Exception {
        start("nirvana.hall.api.app.HallApiApp", args);
    }
}
