package nu.wasis.jlog;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;

import org.apache.log4j.Logger;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class JLog extends ResourceConfig {

    private static final Logger LOG = Logger.getLogger(JLog.class);

    @Inject
    public JLog(final ServiceLocator serviceLocator) {
        property("com.sun.jersey.api.json.POJOMappingFeature", true);
        packages("nu.wasis.jlog", "org.codehaus.jackson.jaxrs");
        // register(new JlogBinder());
        // final DynamicConfiguration dc =
        // Injections.getConfiguration(serviceLocator);
        // Injections.addBinding(Injections.newBinder(Configuration.class).to(Configuration.class).in(Singleton.class),
        // dc);
        // dc.commit();
    }

    /*
     * public JLog() { LOG.debug("init jlog");
     * property("com.sun.jersey.api.json.POJOMappingFeature", true);
     * packages("nu.wasis.jlog", "org.codehaus.jackson.jaxrs"); register(new
     * JlogBinder()); }
     */

    /*
     * public JLog() throws NamingException {
     * 
     * LOG.debug("init jlog");
     * property("com.sun.jersey.api.json.POJOMappingFeature", true); packages(
     * "nu.wasis.jlog", "org.codehaus.jackson.jaxrs" );
     * Injections.addBinding(Injections.newBinder(Configuration.class),
     * configuration); }
     */

    // We only want relative urls in the JAX-RS definitions, so put the absolute
    // part here.
    // private static final String URL_PREFIX = "mit/blog";

    // final Map<String, String> initParams = new HashMap<String, String>();
    // initParams.put("com.sun.jersey.config.property.packages",
    // "nu.wasis.jlog;org.codehaus.jackson.jaxrs");
    // initParams.put("com.sun.jersey.api.json.POJOMappingFeature", "true");

    // public static void main(final String[] args) throws IOException {
    // if (null == args || 1 > args.length) {
    // LOG.error("Plz point to configuration file in args[0]. I can't do shit without it.");
    // return;
    // }
    // try {
    // Configuration.load(args[0]);
    // } catch (final InvalidConfigurationException e) {
    // LOG.error(e.getMessage());
    // return;
    // }
    // LOG.info("nothing started @ localhost:4567\nHit enter to stop...");
    // System.in.read();
    // }

}
