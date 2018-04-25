package io.weli;


import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;

public class UndertowWithLogger {
    public static void main(String[] args) throws Exception {
        System.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");
        org.jboss.logmanager.LogManager.getLogManager().readConfiguration(UndertowWithLogger.class.getClassLoader().getResourceAsStream("logging.jboss.properties"));
        UndertowJaxrsServer server = new UndertowJaxrsServer().start();
    }
}
