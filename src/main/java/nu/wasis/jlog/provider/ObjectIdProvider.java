package nu.wasis.jlog.provider;

import static org.codehaus.jackson.annotate.JsonAutoDetect.Visibility.ANY;
import static org.codehaus.jackson.map.DeserializationConfig.Feature.AUTO_DETECT_SETTERS;
import static org.codehaus.jackson.map.SerializationConfig.Feature.AUTO_DETECT_GETTERS;

import java.io.IOException;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.introspect.VisibilityChecker.Std;
import org.codehaus.jackson.map.module.SimpleModule;

// copypasta from https://gist.github.com/yamsellem/3155831
@Provider
public class ObjectIdProvider implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper;

    public ObjectIdProvider() {
        mapper = createMapper();
    }

    @Override
    public ObjectMapper getContext(final Class<?> type) {
        return mapper;
    }

    private static ObjectMapper createMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(AUTO_DETECT_GETTERS, false);
        mapper.configure(AUTO_DETECT_SETTERS, false);
        mapper.setVisibilityChecker(Std.defaultInstance().withFieldVisibility(ANY));

        mapper.registerModule(new SimpleModule("jersey", new Version(1, 0, 0, null)).addSerializer(_id, _idSerializer())
                                                                                    .addDeserializer(_id, _idDeserializer()));
        return mapper;
    }

    private static Class<ObjectId> _id = ObjectId.class;

    private static JsonDeserializer<ObjectId> _idDeserializer() {
        return new JsonDeserializer<ObjectId>() {
            @Override
            public ObjectId deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
                return new ObjectId(jp.readValueAs(String.class));
            }
        };
    }

    private static JsonSerializer<Object> _idSerializer() {
        return new JsonSerializer<Object>() {
            @Override
            public void serialize(final Object obj, final JsonGenerator jsonGenerator, final SerializerProvider provider) throws IOException,
                                                                                                                         JsonProcessingException {
                jsonGenerator.writeString(obj == null ? null : obj.toString());
            }
        };
    }
}
