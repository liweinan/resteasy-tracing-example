package io.weli;

import io.undertow.servlet.api.DeploymentInfo;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class PlayWithContextInjection {
    public static void main(String[] args) throws Exception {
        UndertowJaxrsServer server = new UndertowJaxrsServer().start();
//        server.deploy(SampleApplication.class);
        ResteasyDeployment deployment = new ResteasyDeployment();

        deployment.setApplicationClass(SampleApplication.class.getName());

        DeploymentInfo di = server.undertowDeployment(deployment);
        di.setClassLoader(SampleApplication.class.getClassLoader());
        di.setContextPath("");
        di.setDeploymentName("Resteasy");
        di.getServlets().get("ResteasyServlet").addInitParam("foo", "bar");
        server.deploy(di);

        Thread.sleep(1000);

        Client client = ClientBuilder.newClient();

        // Set breakpoint to org.jboss.resteasy.core.ContextParameterInjector
        WebTarget target = client.target("http://localhost:8081/hello/uri");
        System.out.println(target.request().get(String.class));

        System.out.println("");
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        System.out.println("");

        System.out.println(client.target("http://localhost:8081/hello/config").request().get(String.class));

        System.out.println("");
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        System.out.println("");

        System.out.println(client.target("http://localhost:8081/hello/factory").request().get(String.class));

        System.out.println("");
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        System.out.println("");

        client.close();
        server.stop();

    }
}
