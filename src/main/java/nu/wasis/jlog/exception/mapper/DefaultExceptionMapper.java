package nu.wasis.jlog.exception.mapper;

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
            return buildResponse(404, e);
        }
        if (e instanceof NotAllowedException) {
            return buildResponse(403, e);
        }
        if (e instanceof IllegalDataException) {
            return buildResponse(400, e);
        }
        LOG.debug("Mapping unknown Exception of class: " + e.getClass().getName());
        LOG.debug(e);
        return buildResponse(400, e);
    }

    private Response buildResponse(final int status, final Throwable e) {
        return Response.status(status).entity(e.getMessage()).build();
    }

}
