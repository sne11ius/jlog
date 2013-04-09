package nu.wasis.jlog.model;

import java.util.Date;

import com.github.jmkgreen.morphia.annotations.Embedded;

@Embedded
public class Comment extends AbstractDBObject {

    private User author;
    private String body;
    private Date date = new Date();

    public Comment() {
        // empty d'tor is empty - for bean stuff
    }

    public Comment(final User user, final String body) {
        super();
        this.author = user;
        this.body = body;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(final User user) {
        this.author = user;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

}
