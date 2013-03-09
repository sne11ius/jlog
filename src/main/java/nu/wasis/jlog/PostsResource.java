package nu.wasis.jlog;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nu.wasis.jlog.model.Comment;
import nu.wasis.jlog.model.Post;
import nu.wasis.jlog.service.PostService;

import org.apache.log4j.Logger;

import com.sun.jersey.api.NotFoundException;

@Path("/posts")
public class PostsResource {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(PostsResource.class);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Post> getPosts() {
		final List<Post> posts = PostService.INSTANCE.getPosts();
		return posts;
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
	public void addPost(final Post post) {
		PostService.INSTANCE.save(post);
	}
	
	@POST
	@Path("{postId}/comments")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addComment(@PathParam("postId") final String postId, final Comment comment) {
		LOG.debug(comment);
		PostService.INSTANCE.addComment(postId, comment);
	}
}
