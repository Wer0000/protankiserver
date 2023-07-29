package ua.lann.protankiserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lann.protankiserver.orm.HibernateUtils;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Hello, baka!");

        HibernateUtils.setupSessionFactory();

        Server server = new Server();
        server.run();

        logger.info("Shutting down (end of main)");
    }
}