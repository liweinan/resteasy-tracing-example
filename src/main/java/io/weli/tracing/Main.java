package io.weli.tracing;

import io.undertow.servlet.api.DeploymentInfo;
import org.jboss.logmanager.LogManager;
import org.jboss.resteasy.plugins.server.servlet.ResteasyContextParameters;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.resteasy.tracing.RESTEasyTracingLogger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Main {
    public static void main(String[] args) throws Exception {

        UndertowJaxrsServer server = null;
        Client client = null;

        try {
            System.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");
            LogManager.getLogManager().readConfiguration(Main.class.getClassLoader().getResourceAsStream("logging.jboss.properties"));

            server = new UndertowJaxrsServer().start();

            Thread.sleep(1000);

            ResteasyDeployment deployment = new ResteasyDeployment();

            deployment.setApplicationClass(TracingApp.class.getName());

            DeploymentInfo di = server.undertowDeployment(deployment);
            di.setClassLoader(TracingApp.class.getClassLoader());
            di.setContextPath("");
            di.setDeploymentName("Resteasy");
            di.getServlets().get("ResteasyServlet").addInitParam(ResteasyContextParameters.RESTEASY_TRACING_TYPE, ResteasyContextParameters.RESTEASY_TRACING_TYPE_ALL)
                    .addInitParam(ResteasyContextParameters.RESTEASY_TRACING_THRESHOLD, ResteasyContextParameters.RESTEASY_TRACING_LEVEL_VERBOSE);
            server.deploy(di);

            // shutdown server
            Thread.currentThread().join();

            client = ClientBuilder.newClient();
            WebTarget target = client.target("http://localhost:8081/type");
            assertEquals(ResteasyContextParameters.RESTEASY_TRACING_TYPE_ALL, target.request().get(String.class));

            target = client.target("http://localhost:8081/level");
            assertEquals(ResteasyContextParameters.RESTEASY_TRACING_LEVEL_VERBOSE, target.request().get(String.class));

            target = client.target("http://localhost:8081/logger");
            assertEquals(RESTEasyTracingLogger.class.getName(), target.request().get(String.class));

        } finally {
            if (client != null) {
                client.close();
            }
            Thread.sleep(1000);
            if (server != null) {
                server.stop();
            }
        }
    }
}
