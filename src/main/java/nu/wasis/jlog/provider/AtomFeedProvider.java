//package nu.wasis.jlog.provider;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.io.Writer;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.util.Calendar;
//import java.util.LinkedList;
//import java.util.List;
//
//import javax.annotation.Resource;
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//import javax.ws.rs.WebApplicationException;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.MultivaluedMap;
//import javax.ws.rs.ext.MessageBodyWriter;
//import javax.ws.rs.ext.Provider;
//
//import nu.wasis.jlog.config.Configuration;
//import nu.wasis.jlog.model.Post;
//import nu.wasis.jlog.util.HTMLUtils;
//
//import org.apache.log4j.Logger;
//import org.displaytag.util.HtmlTagUtil;
//
//import com.sun.syndication.feed.synd.SyndContent;
//import com.sun.syndication.feed.synd.SyndContentImpl;
//import com.sun.syndication.feed.synd.SyndEntry;
//import com.sun.syndication.feed.synd.SyndEntryImpl;
//import com.sun.syndication.feed.synd.SyndFeed;
//import com.sun.syndication.feed.synd.SyndFeedImpl;
//import com.sun.syndication.io.FeedException;
//import com.sun.syndication.io.SyndFeedOutput;
//
//@Stateless
//@Resource(type = AtomFeedProvider.class, name = "AtomFeedProvider")
//@Provider
//public class AtomFeedProvider implements MessageBodyWriter<List<Post>> {
//
//    private static final Logger LOG = Logger.getLogger(AtomFeedProvider.class);
//
//    @Inject
//    private Configuration configuration;
//
//    private static final String FEED_TYPE_ATOM_1_0 = "atom_1.0";
//    private static final String FEED_DESCRIPTION = "Blog about technical stuff.";
//
//    private static final int ABBREVIATED_CHAR_COUNT = 1_000;
//    private static final String ABBREVIATION_HINT = " [This post abbrev. due to popular demand (tm). See original for full version.]";
//
//    @Override
//    public long getSize(final List<Post> t, final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
//        // don't know yet
//        return -1;
//    }
//
//    @Override
//    public boolean isWriteable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
//        boolean isExpectedType;
//        if (List.class.isAssignableFrom(type) && genericType instanceof ParameterizedType) {
//            final ParameterizedType parameterizedType = (ParameterizedType) genericType;
//            final Type[] actualTypeArgs = (parameterizedType.getActualTypeArguments());
//            isExpectedType = (actualTypeArgs.length == 1 && actualTypeArgs[0].equals(Post.class));
//        } else {
//            isExpectedType = false;
//        }
//
//        return isExpectedType && MediaType.APPLICATION_ATOM_XML_TYPE.equals(mediaType);
//    }
//
//    @Override
//    public void writeTo(final List<Post> posts, final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType,
//            final MultivaluedMap<String, Object> httpHeaders, final OutputStream entityStream) throws IOException, WebApplicationException {
//        final SyndFeed feed = createFeed(posts);
//        final SyndFeedOutput output = new SyndFeedOutput();
//        final Writer writer = new OutputStreamWriter(entityStream, "UTF-8");
//        try {
//            output.output(feed, writer);
//        } catch (final FeedException e) {
//            LOG.error("Error writing feed.", e);
//            writer.write("Error writing feed: " + e.getMessage());
//        } finally {
//            writer.close();
//            entityStream.close();
//        }
//    }
//
//    private SyndFeed createFeed(final List<Post> posts) {
//        final SyndFeed feed = new SyndFeedImpl();
//
//        feed.setFeedType(FEED_TYPE_ATOM_1_0);
//        feed.setTitle("wasis.nu/mit/blog?");
//        feed.setLink(configuration.getBaseUrl());
//        feed.setDescription(FEED_DESCRIPTION);
//        feed.setCopyright("(c) " + Calendar.getInstance().get(Calendar.YEAR) + " - wasis.nu");
//        feed.setEncoding("UTF-8");
//
//        final List<SyndEntry> entries = new LinkedList<>();
//        for (final Post post : posts) {
//            entries.add(createEntry(post));
//        }
//        feed.setEntries(entries);
//
//        return feed;
//    }
//
//    private SyndEntry createEntry(final Post post) {
//        final SyndEntry entry = new SyndEntryImpl();
//
//        entry.setTitle(HTMLUtils.stripHtmlTags(post.getTitle()));
//        entry.setDescription(createDescription(post));
//        entry.setAuthor(post.getAuthor().getName());
//        entry.setPublishedDate(post.getDateCreated());
//        entry.setUpdatedDate(post.getDateUpdated());
//        entry.setLink(configuration.getBaseUrl() + "?postId=" + post.getId());
//
//        return entry;
//    }
//
//    private SyndContent createDescription(final Post post) {
//        final SyndContent description = new SyndContentImpl();
//        description.setType(MediaType.TEXT_HTML);
//        final StringBuffer descriptionBuffer = new StringBuffer(ABBREVIATED_CHAR_COUNT);
//        descriptionBuffer.append(HtmlTagUtil.abbreviateHtmlString(post.getBody(), ABBREVIATED_CHAR_COUNT, false));
//        if (descriptionBuffer.length() < post.getBody().length()) {
//            descriptionBuffer.append(ABBREVIATION_HINT);
//        }
//        description.setValue(descriptionBuffer.toString());
//        return description;
//    }
// }
