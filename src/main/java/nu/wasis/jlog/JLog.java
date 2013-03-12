package nu.wasis.jlog;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.api.container.grizzly2.GrizzlyWebContainerFactory;

public class JLog {

    private static final String URL_PREFIX = "mit/blog";

    private static int getPort(final int defaultPort) {
        final String httpPort = System.getProperty("jersey.test.port");
        if (null != httpPort) {
            try {
                return Integer.parseInt(httpPort);
            } catch (final NumberFormatException e) {
            }
        }
        return defaultPort;
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/" + URL_PREFIX).port(getPort(4567)).build();
    }

    public static final URI BASE_URI = getBaseURI();

    protected static HttpServer startServer() throws IOException {
        final Map<String, String> initParams = new HashMap<String, String>();
        initParams.put("com.sun.jersey.config.property.packages", "nu.wasis.jlog;org.codehaus.jackson.jaxrs");
        initParams.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
        System.out.println("Starting grizzly2...");
        return GrizzlyWebContainerFactory.create(BASE_URI, initParams);
    }

    public static void main(final String[] args) throws IOException {
        // PostService.INSTANCE.deleteAll();
        final HttpServer httpServer = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                                         + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        httpServer.stop();
    }
}
