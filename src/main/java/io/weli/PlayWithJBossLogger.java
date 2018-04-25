package io.weli;


import org.jboss.logmanager.LogManager;


public class PlayWithJBossLogger {
    public static void main(String[] args) throws Exception {
        System.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");
//        System.setProperty("java.util.logging.config.file", PlayWithJBossLogger.class.getResource("/").getPath() + "logging.jboss.properties");
        LogManager.getLogManager().readConfiguration(UndertowWithLogger.class.getClassLoader().getResourceAsStream("logging.jboss.properties"));
        org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger(PlayWithJBossLogger.class.toString());


        System.out.println(logger);
        logger.warn("前方高能！");
        logger.info("普通消息");
        logger.debug("调试消息");
        logger.trace("跟踪信息");
    }
}
