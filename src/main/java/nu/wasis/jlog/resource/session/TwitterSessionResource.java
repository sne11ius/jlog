//package nu.wasis.jlog.resource.session;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.Context;
//
//import nu.wasis.jlog.config.Configuration;
//
//import org.apache.log4j.Logger;
//
//import twitter4j.Twitter;
//import twitter4j.TwitterException;
//import twitter4j.TwitterFactory;
//import twitter4j.auth.RequestToken;
//
//@Path("session/twitter")
//public class TwitterSessionResource {
//
//    private static final Logger LOG = Logger.getLogger(TwitterSessionResource.class);
//
//    @GET
//    @Path("login")
//    public String login(@Context final HttpServletRequest request) throws TwitterException {
//        final Twitter twitter = new TwitterFactory().getInstance();
//        /*
//         * final StringBuffer callbackURL = request.getRequestURL(); final int index = callbackURL.lastIndexOf("/");
//         * callbackURL.replace(index, callbackURL.length(), "").append("/callback");
//         */
//        final String callbackURL = Configuration.getInstance().getBaseUrl() + "/twitterSession/callback";
//        final RequestToken requestToken = twitter.getOAuthRequestToken(callbackURL.toString());
//        request.getSession().setAttribute("requestToken", requestToken);
//        final String authenticationURL = requestToken.getAuthenticationURL();
//        LOG.debug("callbackURL: " + callbackURL);
//        LOG.debug("authenticationURL: " + authenticationURL);
//        return authenticationURL;
//    }
//
//    @GET
//    @Path("callback")
//    public void callback(@Context final HttpServletRequest request, @QueryParam("oauth_token") final String oAuthToken,
//            @QueryParam("oauth_verifier") final String oAuthVerifier) throws TwitterException {
//        LOG.debug("oAuthToken:");
//        LOG.debug(oAuthToken);
//        LOG.debug("oAuthVerifier:");
//        LOG.debug(oAuthVerifier);
//        final Twitter twitter = new TwitterFactory().getInstance();
//        final RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
//        twitter.getOAuthAccessToken(requestToken, oAuthVerifier);
//        request.getSession().removeAttribute("requestToken");
//        // response.sendRedirect(request.getContextPath() + "/");
//    }
//
// }
