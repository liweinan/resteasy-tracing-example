package io.weli;

import org.jboss.resteasy.spi.ResteasyConfiguration;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@Path("/hello")
public class HelloWorldResource {
    @GET
    public String sayHello() {
        return "Hello, world!";
    }

    @GET
    @Path("/uri")
    public String uri(@Context UriInfo uriInfo) {
        return uriInfo.getPath().toString();

    }

    @GET
    @Path("/config")
    public String config(@Context ResteasyConfiguration config) {
        return config.getInitParameter("foo");
    }

    @GET
    @Path("/factory")
    public String config(@Context ResteasyDeployment deployment) {
        return deployment.getProviderFactory().toString();
    }

}
