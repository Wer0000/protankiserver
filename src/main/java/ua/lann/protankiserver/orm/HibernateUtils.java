package ua.lann.protankiserver.orm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lann.protankiserver.orm.entities.Player;

public class HibernateUtils {
    private static final Logger logger = LoggerFactory.getLogger(HibernateUtils.class);
    private static SessionFactory builder;

    public static void setupSessionFactory() {
        Configuration config = new Configuration();

        config.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");

        config.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/protanki");
        config.setProperty("hibernate.connection.username", "root");
        config.setProperty("hibernate.connection.password", "");
        config.setProperty("dialect", "com.mysql.cj.jdbc.Driver");

        config.setProperty("hibernate.show_sql", "false");
        config.setProperty("hibernate.format_sql", "true");
        config.setProperty("hibernate.hbm2ddl.auto", "update");

        config.addAnnotatedClass(Player.class);

        logger.info("Configured: {}@{}", config.getProperty("hibernate.connection.username"), config.getProperty("hibernate.connection.url"));

        builder = config.buildSessionFactory();
    }

    public static Session session() {
        return builder.openSession();
    }
}
