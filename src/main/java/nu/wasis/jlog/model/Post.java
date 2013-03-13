package nu.wasis.jlog.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.bson.types.ObjectId;

import com.github.jmkgreen.morphia.annotations.Entity;

@XmlRootElement
@Entity("Posts")
public class Post extends AbstractDBObject {

    private String title;
    private String body;
    private User author;
    private Date date = new Date();

    private List<Comment> comments = new LinkedList<>();

    public Post() {
    }

    public Post(final String title, final String body, final User author) {
        this.title = title;
        this.body = body;
        this.author = author;
    }

    public void addComment(final Comment comment) {
        comments.add(comment);
    }

    public void removeComment(final String commentId) {
        for (int i = 0; i < comments.size(); ++i) {
            if (comments.get(i).getId().equals(new ObjectId(commentId))) {
                comments.remove(i);
                return;
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(final User author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(final List<Comment> comments) {
        this.comments = comments;
    }

}
