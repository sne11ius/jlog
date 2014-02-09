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

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import nu.wasis.jlog.config.Configuration;
import nu.wasis.jlog.model.Post;
import nu.wasis.jlog.service.PostService;
import nu.wasis.jlog.util.GPlusUtils;
import nu.wasis.jlog.util.TemplateLoader;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Renders the html/index.ftl file.
 */
@SessionScoped
@Resource(type = IndexResource.class, name = "IndexResource")
@Path("/")
public class IndexResource {

    @Inject
    private Configuration configuration;

    @Inject
    private GPlusUtils gPlusUtils;

    private static final String STATE_ATTRIBUTE_KEY = "state";

    private static final Logger LOG = Logger.getLogger(IndexResource.class);

    private static final List<String> templateDirectories = Arrays.asList("css", "html", "js/app", "js/libs", "bootstrap/css", "bootstrap/img", "bootstrap/js");

    @PostConstruct
    public void init() {
        LOG.info("initializing...");
        for (final String templateDirectoryPath : templateDirectories) {
            try {
                final File templateDirectory = new File(configuration.getBaseTemplatePath() + File.separator + templateDirectoryPath);
                TemplateLoader.INSTANCE.addDirectory(templateDirectory);
            } catch (final IOException e) {
                LOG.error("Initialization of TemplateLoader failed:", e);
            }
        }
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getIndex(@Context final HttpServletRequest request, @QueryParam("compress") @DefaultValue("false") final boolean compress,
            @QueryParam("postId") final String postId, @QueryParam("inTitle") final String titleSubstring) throws IOException, TemplateException {
        final HttpSession session = request.getSession(true);
        final String state = new BigInteger(130, new SecureRandom()).toString(32);
        session.setAttribute(STATE_ATTRIBUTE_KEY, state);
        LOG.debug("state: " + state);
        final StringWriter writer = new StringWriter();
        final String templateFilename = getTemplateFilename(request);
        TemplateLoader.INSTANCE.setStripCommentsEnabled(compress);
        final Template template = TemplateLoader.INSTANCE.getTemplate(templateFilename, buildReplacements(compress));
        final Map<String, Object> map = createTemplateMap(request, state, postId, titleSubstring);
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

    private Map<String, Object> createTemplateMap(final HttpServletRequest request, final String state, final String postId, final String titleSubstring) {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("client_id", configuration.getGoogleApiClientId());
        map.put(STATE_ATTRIBUTE_KEY, state);
        map.put("username", gPlusUtils.getCurrentUsername(request));
        map.put("loggedin", gPlusUtils.isLoggedIn(request));
        map.put("isowner", gPlusUtils.isOwnerLoggedIn(request));
        map.put("GetPostsArrayCommand", getGetPostsArrayCommand(postId, titleSubstring));
        map.put("baseUrl", request.getRequestURL());
        return map;
    }

    private String getGetPostsArrayCommand(final String postId, final String titleSubstring) {
        if (!StringUtils.isEmpty(postId)) {
            final Post post = PostService.INSTANCE.getPost(postId);
            if (null == post) {
                throw new NotFoundException("No post with id `" + postId + "'.");
            }
            return "[" + GPlusUtils.GSON.toJson(post) + "]";
        }
        if (!StringUtils.isEmpty(titleSubstring)) {
            final List<Post> posts = PostService.INSTANCE.getPosts();
            // FIXME: Get this done in morphia D:
            CollectionUtils.filter(posts, new PostTitleMatchPredicate(titleSubstring.replace("_", " ")));
            String postsString = "[";
            for (int i = 0; i < posts.size() - 1; ++i) {
                postsString += GPlusUtils.GSON.toJson(posts.get(i)) + ", ";
            }
            postsString += GPlusUtils.GSON.toJson(posts.get(posts.size() - 1)) + "]";
            LOG.debug(postsString);
            return postsString;
        }
        return "Post.query()";
    }

    private static final class PostTitleMatchPredicate implements Predicate {

        private final String titleSubstring;

        public PostTitleMatchPredicate(final String titleSubstring) {
            this.titleSubstring = titleSubstring.toLowerCase();
        }

        @Override
        public boolean evaluate(final Object object) {
            return ((Post) object).getTitle().toLowerCase().contains(titleSubstring);
        }
    }
}
