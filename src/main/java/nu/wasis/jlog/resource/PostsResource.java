package nu.wasis.jlog.resource;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import nu.wasis.jlog.exception.IllegalDataException;
import nu.wasis.jlog.exception.NotAllowedException;
import nu.wasis.jlog.model.Comment;
import nu.wasis.jlog.model.Post;
import nu.wasis.jlog.service.PostService;
import nu.wasis.jlog.util.GPlusUtils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

@Stateless
@Resource(type = PostsResource.class, name = "PostsResource")
@Path("/posts")
public class PostsResource {

    @Inject
    private GPlusUtils gPlusUtils;

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(PostsResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Post> getPosts() {
        return PostService.INSTANCE.getPosts();
    }

    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public int getCount() {
        return PostService.INSTANCE.getPosts().size();
    }

    @GET
    @Path("{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post getPost(@PathParam("postId") final String id) {
        final Post post = PostService.INSTANCE.getPost(id);
        if (null == post) {
            throw new NotFoundException("No post with id " + id);
        }
        return post;
    }

    @GET
    @Path("byindex/{postIndex}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post getPost(@PathParam("postIndex") final int index) {
        final List<Post> allPosts = PostService.INSTANCE.getPosts();
        if (index >= allPosts.size()) {
            throw new NotFoundException("No post with index " + index);
        }
        return allPosts.get(index);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Post savePost(@Context final HttpServletRequest request, final Post post) {
        checkIsOwner(request);
        if (StringUtils.isBlank(post.getTitle()) || StringUtils.isBlank(post.getBody())) {
            throw new IllegalDataException("Title and body must not be empty.");
        }
        post.setAuthor(gPlusUtils.getCurrentUser(request));
        PostService.INSTANCE.save(post);
        return post;
    }

    @PUT
    @Path("{postId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Post updatePost(@Context final HttpServletRequest request, final Post post) {
        return savePost(request, post);
    }

    @DELETE
    @Path("{postId}")
    public void deletePost(@Context final HttpServletRequest request, @PathParam("postId") final String id) {
        checkIsOwner(request);
        PostService.INSTANCE.deletePost(new ObjectId(id));
    }

    @POST
    @Path("{postId}/comments")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Comment addComment(@Context final HttpServletRequest request, @PathParam("postId") final String postId, final Comment comment) {
        if (!gPlusUtils.isLoggedIn(request)) {
            throw new NotAllowedException("Must be logged in to do this.");
        }
        if (StringUtils.isBlank(comment.getBody())) {
            throw new IllegalDataException("Body must not be empty.");
        }
        comment.setAuthor(gPlusUtils.getCurrentUser(request));
        if (!gPlusUtils.isOwnerLoggedIn(request)) {
            comment.setBody(StringEscapeUtils.escapeHtml(comment.getBody()));
        }
        PostService.INSTANCE.addComment(postId, comment);
        return comment;
    }

    @DELETE
    @Path("{postId}/comments/{commentId}")
    public void deleteComment(@Context final HttpServletRequest request, @PathParam("postId") final String postId,
            @PathParam("commentId") final String commentId) {
        checkIsOwner(request);
        PostService.INSTANCE.deleteComment(postId, commentId);
    }

    private void checkIsOwner(final HttpServletRequest request) {
        if (!gPlusUtils.isOwnerLoggedIn(request)) {
            throw new NotAllowedException("Must be owner to do this.");
        }
    }
}
