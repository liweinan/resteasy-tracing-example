package io.weli;

import io.undertow.servlet.api.DeploymentInfo;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyConfiguration;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

public class GetConfigurationFromUndertow {
    public static void main(String[] args) throws Exception {


        UndertowJaxrsServer server = new UndertowJaxrsServer().start();
//        server.stop();

        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setApplicationClass(SampleApplication.class.getName());
        DeploymentInfo di = server.undertowDeployment(deployment);
        di.setClassLoader(SampleApplication.class.getClassLoader());
        di.setContextPath("");
        di.setDeploymentName("Resteasy");
        server.deploy(di);

        System.out.println("");
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        System.out.println("");

        di.getServletContextAttributes().forEach((k, v) -> {
            System.out.println("k -> " + k + ", v -> " + v);
        });

        System.out.println("");
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        System.out.println("");

        ResteasyDeployment _deployment = (ResteasyDeployment) di.getServletContextAttributes().get(ResteasyDeployment.class.getName());
        System.out.println(_deployment);

        /*
         * org.jboss.resteasy.spi.ResteasyDeployment@5419f379
         * k -> interface org.jboss.resteasy.spi.ResteasyConfiguration, v -> org.jboss.resteasy.plugins.server.servlet.ServletBootstrap@6156496
         * k -> interface javax.servlet.ServletConfig, v -> io.undertow.servlet.spec.ServletConfigImpl@3c153a1
         * k -> interface javax.servlet.ServletContext, v -> io.undertow.servlet.spec.ServletContextImpl@b62fe6d
         * k -> class org.jboss.resteasy.spi.ResteasyDeployment, v -> org.jboss.resteasy.spi.ResteasyDeployment@5419f379
         */
        _deployment.getDefaultContextObjects().forEach((k, v) -> {
            System.out.println("k -> " + k + ", v -> " + v);
            if (v instanceof ResteasyConfiguration) {
                System.out.println(((ResteasyConfiguration) v).getParameterNames());
                System.out.println(((ResteasyConfiguration) v).getInitParameterNames());
            }
        });

        System.out.println("");
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        System.out.println("");

        System.out.println(ResteasyProviderFactory.getContextDataMap());

        server.stop();
    }
}
