package nu.wasis.jlog.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import nu.wasis.jlog.exception.IllegalDataException;
import nu.wasis.jlog.exception.NotAllowedException;

import org.apache.log4j.Logger;

import com.sun.jersey.api.NotFoundException;

@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Throwable> {

    public static final Logger LOG = Logger.getLogger(DefaultExceptionMapper.class);

    @Override
    public Response toResponse(final Throwable e) {
        if (e instanceof NotFoundException) {
            return Response.status(404).entity("Unknown post index.").build();
        }
        if (e instanceof NotAllowedException) {
            return Response.status(403).entity(e.getMessage()).build();
        }
        if (e instanceof IllegalDataException) {
            return Response.status(400).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
        }
        LOG.debug("Mapping unknown Exception of class: " + e.getClass().getName());
        LOG.debug(e);
        return Response.status(400).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
    }

}
