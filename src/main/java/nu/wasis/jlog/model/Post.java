package nu.wasis.jlog.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.bson.types.ObjectId;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.sun.jersey.api.NotFoundException;

@XmlRootElement
@Entity("Posts")
public class Post extends AbstractDBObject {

    private String title;
    private String body;
    private User author;
    private Date date = new Date();
    private Date dateCreated = null;
    private Date dateUpdated = null;

    private List<Comment> comments = new LinkedList<>();

    public Post() {
        dateCreated = date;
        dateUpdated = date;
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
        throw new NotFoundException("No comment with that id in this post ;)");
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(final Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(final Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

}
