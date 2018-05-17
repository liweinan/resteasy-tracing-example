package io.weli.tracing;

import org.jboss.resteasy.spi.HttpRequest;
<<<<<<< HEAD
import org.jboss.resteasy.spi.ResteasyDeployment;
=======
>>>>>>> 9066e742e3af385eb12fe88b7321038d4dbbad45
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.resteasy.tracing.RESTEasyTracingLogger;
import org.jboss.resteasy.tracing.RESTEasyTracingUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

@Path("/")
public class TracingConfigResource extends Application {

    @GET
    @Path("/type")
    public String type(@Context ResteasyDeployment deployment) {
        return RESTEasyTracingUtils.getTracingConfig(deployment.getProviderFactory()).toString();
    }

    @GET
    @Path("/level")
    public String level(@Context ResteasyDeployment deployment) {
        return RESTEasyTracingUtils.getTracingThreshold(deployment.getProviderFactory()).toString();
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
