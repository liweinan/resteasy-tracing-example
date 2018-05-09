package io.weli.tracing;

import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.resteasy.tracing.RESTEasyTracingLogger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

@Path("/")
public class TracingConfigResource extends Application {

    @GET
    @Path("/type")
    public String type() {
        return ResteasyProviderFactory.getTracingType().toString();
    }

    @GET
    @Path("/level")
    public String level() {
        return ResteasyProviderFactory.getTracingThreshold().toString();
    }

    @GET
    @Path("/logger")
    public String logger(@Context HttpRequest request) throws NoSuchMethodException {
        RESTEasyTracingLogger logger = (RESTEasyTracingLogger) request.getAttribute(RESTEasyTracingLogger.PROPERTY_NAME);
        if (logger == null) {
            return "";
        } else {
            return RESTEasyTracingLogger.class.getName();
        }
    }

}
