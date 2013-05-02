package nu.wasis.jlog.resource;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import nu.wasis.jlog.model.Post;
import nu.wasis.jlog.service.PostService;
import nu.wasis.jlog.util.GPlusUtils;
import nu.wasis.jlog.util.PrivateConstants;
import nu.wasis.jlog.util.TemplateLoader;

import org.apache.log4j.Logger;

import com.sun.jersey.api.NotFoundException;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Renders the html/index.ftl file.
 */
@Path("/")
public class IndexResource {

    private static final String STATE_ATTRIBUTE_KEY = "state";

    private static final Logger LOG = Logger.getLogger(IndexResource.class);

    /*
     * private static final String USER_AGENT_HEADER = "User-Agent"; private static final String HTTP_ACCEPT = "Accept";
     */

    private static final List<String> templateDirectories = Arrays.asList("css", "html", "js", "bootstrap/css", "bootstrap/img", "bootstrap/js");

    static {
        for (final String templateDirectoryPath : templateDirectories) {
            try {
                final File templateDirectory = new File(PrivateConstants.BASE_TEMPLATE_PATH + File.separator + templateDirectoryPath);
                TemplateLoader.INSTANCE.addDirectory(templateDirectory);
            } catch (final IOException e) {
                LOG.error("Initialization of TemplateLoader failed:", e);
            }
        }
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getIndex(@Context final HttpServletRequest request, @QueryParam("compress") @DefaultValue("true") final boolean compress,
                           @QueryParam("postId") final String postId) throws IOException, TemplateException {
        final HttpSession session = request.getSession(true);
        final String state = new BigInteger(130, new SecureRandom()).toString(32);
        session.setAttribute(STATE_ATTRIBUTE_KEY, state);
        final StringWriter writer = new StringWriter();
        final String templateFilename = getTemplateFilename(request);
        TemplateLoader.INSTANCE.setStripCommentsEnabled(compress);
        final Template template = TemplateLoader.INSTANCE.getTemplate(templateFilename, buildReplacements(compress));
        final Map<String, Object> map = createTemplateMap(request, state, postId);
        template.process(map, writer);
        return writer.toString();
    }

    @GET
    @Path("feed")
    @Produces(MediaType.APPLICATION_ATOM_XML)
    public List<Post> getFeed() {
        return PostService.INSTANCE.getPosts();
    }

    /*
     * private UserAgentInfo getUserAgentInfo(final HttpServletRequest request) { final String userAgent =
     * getUserAgent(request); final String httpAccept = getHttpAccept(request); return new UserAgentInfo(userAgent,
     * httpAccept); }
     */

    private String getTemplateFilename(final HttpServletRequest request) {
        return "index.ftl";
        // maybe later:
        // final UserAgentInfo userAgentInfo = getUserAgentInfo(final HttpServletRequest request);
        // return userAgentInfo.getIsTierGenericMobile() ? "index_mobile.ftl" : "index.ftl";
    }

    /*
     * private String getUserAgent(final HttpServletRequest request) { return request.getHeader(USER_AGENT_HEADER); }
     * private String getHttpAccept(final HttpServletRequest request) { return request.getHeader(HTTP_ACCEPT); }
     */

    private Map<String, String> buildReplacements(final boolean compress) {
        final Map<String, String> replacements = new HashMap<>();
        replacements.put("$COMPRESS_BEGIN", maybeEmpty(compress, "<#compress>"));
        replacements.put("$COMPRESS_END", maybeEmpty(compress, "</#compress>"));
        replacements.put("$COMPRESS_SINGLE_LINE_BEGIN", maybeEmpty(compress, "<@compress single_line=true>"));
        replacements.put("$COMPRESS_SINGLE_LINE_END", maybeEmpty(compress, "</@compress>"));
        return replacements;
    }

    private String maybeEmpty(final boolean returnActualString, final String actualString) {
        return returnActualString ? actualString : "";
    }

    private Map<String, Object> createTemplateMap(final HttpServletRequest request, final String state, final String postId) {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("client_id", PrivateConstants.CLIENT_ID);
        map.put(STATE_ATTRIBUTE_KEY, state);
        map.put("username", GPlusUtils.getCurrentUsername(request));
        map.put("loggedin", GPlusUtils.isLoggedIn(request));
        map.put("isowner", GPlusUtils.isOwnerLoggedIn(request));
        map.put("GetPostsArrayCommand", getGetPostsArrayCommand(postId));
        map.put("baseUrl", request.getRequestURL());
        return map;
    }

    private String getGetPostsArrayCommand(final String postId) {
        if (null == postId) {
            return "Post.query()";
        }
        final Post post = PostService.INSTANCE.getPost(postId);
        if (null == post) {
            throw new NotFoundException("No post with id `" + postId + "'.");
        }
        return "[" + GPlusUtils.GSON.toJson(post) + "]";
    }

}
