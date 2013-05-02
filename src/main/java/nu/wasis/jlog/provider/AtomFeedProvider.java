package nu.wasis.jlog.provider;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import nu.wasis.jlog.model.Post;
import nu.wasis.jlog.util.HTMLUtils;
import nu.wasis.jlog.util.PrivateConstants;

import org.apache.log4j.Logger;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

@Provider
public class AtomFeedProvider implements MessageBodyWriter<List<Post>> {

    private static final Logger LOG = Logger.getLogger(AtomFeedProvider.class);

    private static final String FEED_TYPE_ATOM_1_0 = "atom_1.0";
    private static final String FEED_DESCRIPTION = "Blog about technical stuff.";

    @Override
    public long getSize(final List<Post> t, final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
        // don't know yet
        return -1;
    }

    @Override
    public boolean isWriteable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
        boolean isExpectedType;
        if (List.class.isAssignableFrom(type) && genericType instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType) genericType;
            final Type[] actualTypeArgs = (parameterizedType.getActualTypeArguments());
            isExpectedType = (actualTypeArgs.length == 1 && actualTypeArgs[0].equals(Post.class));
        } else {
            isExpectedType = false;
        }

        return isExpectedType && MediaType.APPLICATION_ATOM_XML_TYPE.equals(mediaType);
    }

    @Override
    public void writeTo(final List<Post> posts, final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType,
                        final MultivaluedMap<String, Object> httpHeaders, final OutputStream entityStream) throws IOException, WebApplicationException {
        final SyndFeed feed = createFeed(posts);
        final SyndFeedOutput output = new SyndFeedOutput();
        final PrintWriter outputWriter = new PrintWriter(entityStream);
        try {
            output.output(feed, outputWriter);
        } catch (final FeedException e) {
            LOG.error("Error writing feed.", e);
            outputWriter.write("Error writing feed: " + e.getMessage());
        } finally {
            outputWriter.close();
            entityStream.close();
        }
    }

    private SyndFeed createFeed(final List<Post> posts) {
        final SyndFeed feed = new SyndFeedImpl();

        feed.setFeedType(FEED_TYPE_ATOM_1_0);
        feed.setTitle("wasis.nu/mit/blog?");
        feed.setLink(PrivateConstants.BASE_URL);
        feed.setDescription(FEED_DESCRIPTION);
        feed.setCopyright("(c) " + Calendar.getInstance().get(Calendar.YEAR) + " - wasis.nu");
        feed.setEncoding("UTF-8");

        final List<SyndEntry> entries = new LinkedList<>();
        for (final Post post : posts) {
            entries.add(createEntry(post));
        }
        feed.setEntries(entries);

        return feed;
    }

    private SyndEntry createEntry(final Post post) {
        final SyndEntry entry = new SyndEntryImpl();

        entry.setTitle(HTMLUtils.stripHtmlTags(post.getTitle()));
        entry.setDescription(createDescription(post));
        entry.setAuthor(post.getAuthor().getName());
        entry.setPublishedDate(post.getDate());
        entry.setLink(PrivateConstants.BASE_URL + "?postId=" + post.getId());

        return entry;
    }

    private SyndContent createDescription(final Post post) {
        final SyndContent description = new SyndContentImpl();
        description.setType(MediaType.TEXT_HTML);
        description.setValue(post.getBody());
        return description;
    }
}
