package nu.wasis.jlog.resource.session;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nu.wasis.jlog.config.Configuration;
import nu.wasis.jlog.util.GPlusUtils;

import org.apache.log4j.Logger;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Tokeninfo;

@SessionScoped
@Resource(type = GooglePlusSessionResource.class, name = "GooglePlusSessionResource")
@Path("session/gplus")
public class GooglePlusSessionResource implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(GooglePlusSessionResource.class);

    @Inject
    private transient Configuration configuration;

    @Inject
    private transient GPlusUtils gPlusUtils;

    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Context final HttpServletRequest request, @QueryParam("state") final String state, @QueryParam("gplus_id") final String gplusId, final String body) throws IOException {
        final String tokenData = (String) request.getSession(true).getAttribute("token");
        if (tokenData != null) {
            return Response.status(400).entity("{\"message\": \"Already connected.\"}").build();
        }

        // Ensure that this is no request forgery going on, and that the user
        // sending us this connect request is the user that was supposed to.
        final String sessionState = (String) request.getSession(true).getAttribute("state");
        if (!state.equals(sessionState)) {
            LOG.debug("Session state: " + sessionState);
            LOG.debug("Request state: " + state);
            return Response.status(400).entity("Invalid state parameter.").build();
        }

        final String code = body;
        // Upgrade the authorization code into an access and refresh token.
        final GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(GPlusUtils.TRANSPORT, GPlusUtils.JSON_FACTORY,
                configuration.getGoogleApiClientId(),
                configuration.getGoogleApiClientSecret(), code,
                "postmessage").execute();
        // Create a credential representation of the token data.
        final GoogleCredential credential = new GoogleCredential.Builder().setJsonFactory(GPlusUtils.JSON_FACTORY).setTransport(GPlusUtils.TRANSPORT)
                .setClientSecrets(configuration.getGoogleApiClientId(), configuration.getGoogleApiClientSecret()).build()
                .setFromTokenResponse(tokenResponse);

        // Check that the token is valid.
        final Oauth2 oauth2 = new Oauth2.Builder(GPlusUtils.TRANSPORT, GPlusUtils.JSON_FACTORY, credential).build();
        final Tokeninfo tokenInfo = oauth2.tokeninfo().setAccessToken(credential.getAccessToken()).execute();
        // If there was an error in the token info, abort.
        if (tokenInfo.containsKey("error")) {
            return Response.status(401).entity(tokenInfo.get("error").toString()).build();
        }
        // Make sure the token we got is for the intended user.
        if (!tokenInfo.getUserId().equals(gplusId)) {
            return Response.status(401).entity("Token's user ID doesn't match given user ID.").build();
        }
        // Make sure the token we got is for our app.
        if (!tokenInfo.getIssuedTo().equals(configuration.getGoogleApiClientId())) {
            return Response.status(401).entity("Token's client ID does not match app's.").build();
        }
        // Store the token in the session for later use.
        request.getSession(true).setAttribute("token", tokenResponse.toString());
        return Response.ok()
                .entity("{\"user\":" + GPlusUtils.GSON.toJson(gPlusUtils.getCurrentUser(request)) + ", \"isOwner\":" + gPlusUtils.isOwnerLoggedIn(request) + "}").build();
    }

    @POST
    @Path("logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@Context final HttpServletRequest request) throws IOException {
        final String tokenData = (String) request.getSession(true).getAttribute("token");
        if (tokenData == null) {
            return Response.status(401).entity("Current user not connected.").build();
        }
        // Build credential from stored token data.
        final GoogleCredential credential = new GoogleCredential.Builder().setJsonFactory(GPlusUtils.JSON_FACTORY)
                .setTransport(GPlusUtils.TRANSPORT)
                .setClientSecrets(configuration.getGoogleApiClientId(), configuration.getGoogleApiClientSecret())
                .build()
                .setFromTokenResponse(GPlusUtils.JSON_FACTORY.fromString(tokenData,
                        GoogleTokenResponse.class));
        // Execute HTTP GET request to revoke current token.
        GPlusUtils.TRANSPORT.createRequestFactory()
        .buildGetRequest(new GenericUrl(String.format("https://accounts.google.com/o/oauth2/revoke?token=%s", credential.getAccessToken())))
        .execute();
        // Reset the user's session.
        request.getSession(true).removeAttribute("token");
        return Response.ok().entity("{\"message\":\"Successfully disconnected.\"}").build();
    }
}
