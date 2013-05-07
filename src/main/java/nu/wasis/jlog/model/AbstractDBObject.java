package nu.wasis.jlog.model;

import org.bson.types.ObjectId;

import com.github.jmkgreen.morphia.annotations.Id;
import com.google.gson.Gson;

/**
 * Abstract base class for objects that will be stored in the db.
 */
public abstract class AbstractDBObject {

    public AbstractDBObject() {
        this.id = new ObjectId();
    }

    @Id
    private ObjectId id;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(final ObjectId id) {
        this.id = id;
    }

}
