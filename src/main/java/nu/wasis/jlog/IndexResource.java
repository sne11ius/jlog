package nu.wasis.jlog;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import nu.wasis.jlog.util.GPlusUtils;
import nu.wasis.jlog.util.PrivateConstants;
import nu.wasis.jlog.util.TemplateLoader;

import org.apache.log4j.Logger;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Path("/")
public class IndexResource {

    private static final Logger LOG = Logger.getLogger(IndexResource.class);

    private static final List<String> templateDirectories = Arrays.asList("css", "html", "js");

    static {
        final URL baseUrl = IndexResource.class.getClassLoader().getResource(".");
        // LOG.debug("baseUrl: " + baseUrl);
        for (final String templateDirectoryPath : templateDirectories) {
            try {
                final String baseDirectoryName = new File(baseUrl.toURI()).getPath();
                final File templateDirectory = new File(baseDirectoryName + File.separator + templateDirectoryPath);
                // LOG.debug("Loading directory `" + templateDirectory.getAbsolutePath() + "'");
                TemplateLoader.INSTANCE.addDirectory(templateDirectory);
            } catch (final IOException | URISyntaxException e) {
                LOG.error("Initialization of TemplateLoader failed:", e);
            }
        }
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getIndex(@Context final HttpServletRequest request, @QueryParam("compress") final boolean compress) throws IOException, TemplateException {
        final HttpSession session = request.getSession(true);
        final String state = new BigInteger(130, new SecureRandom()).toString(32);
        session.setAttribute("state", state);
        final StringWriter writer = new StringWriter();
        final Template template = TemplateLoader.INSTANCE.getTemplate("index.ftl", buildReplacements(compress));
        final Map<String, Object> map = createTemplateMap(request, state);
        template.process(map, writer);
        return writer.toString();
    }

    private Map<String, String> buildReplacements(final boolean compress) {
        final Map<String, String> replacements = new HashMap<>();
        final String compressBegin = compress ? "<@compress single_line=true>" : "";
        final String compressEnd = compress ? "</@compress>" : "";
        replacements.put("$COMPRESS_BEGIN", compressBegin);
        replacements.put("$COMPRESS_END", compressEnd);
        return replacements;
    }

    private Map<String, Object> createTemplateMap(final HttpServletRequest request, final String state) {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("client_id", PrivateConstants.CLIENT_ID);
        map.put("state", state);
        map.put("username", GPlusUtils.getCurrentUsername(request));
        map.put("loggedin", GPlusUtils.isLoggedIn(request));
        map.put("isowner", GPlusUtils.isOwnerLoggedIn(request));
        return map;
    }

}
