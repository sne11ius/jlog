package nu.wasis.jlog;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import nu.wasis.jlog.service.PostService;
import nu.wasis.jlog.util.GPlusUtils;
import nu.wasis.jlog.util.PrivateConstants;

import org.apache.log4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Path("/")
public class IndexResource {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(IndexResource.class);

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getIndex(@Context final HttpServletRequest request) throws IOException, TemplateException {
		//InputStream resourceStream = IndexResource.class.getClassLoader().getResourceAsStream("index.html");
		//return IOUtils.toString(resourceStream);
		HttpSession session = request.getSession(true);
		final Configuration cfg = createConfig();
		final String state = new BigInteger(130, new SecureRandom()).toString(32);
		session.setAttribute("state", state);
		final StringWriter writer = new StringWriter();
		final Template template = cfg.getTemplate("index.ftl");
		final Map<String, Object> map = createTemplateMap(state, request);
		template.process(map, writer);
		return writer.toString();
	}

	private Map<String, Object> createTemplateMap(final String state, final HttpServletRequest request) {
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("posts", PostService.INSTANCE.getPosts());
		map.put("client_id", PrivateConstants.CLIENT_ID);
		map.put("state", state);
		map.put("nickname", GPlusUtils.getCurrentUsername(request));
		map.put("loggedin", GPlusUtils.isLoggedIn(request));
		map.put("isowner", GPlusUtils.isOwnerLoggedIn(request));
		return map;
	}

	private Configuration createConfig() {
		final Configuration config = new Configuration();
		config.setClassForTemplateLoading(IndexResource.class, "/templates");
		config.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
		return config;
	}

}
