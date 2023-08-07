package ua.lann.protankiserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lann.protankiserver.game.battles.map.MapManager;
import ua.lann.protankiserver.game.garage.GarageManager;
import ua.lann.protankiserver.game.garage.ItemsDataManager;
import ua.lann.protankiserver.game.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.game.protocol.packets.PacketHandlersRegistry;
import ua.lann.protankiserver.orm.HibernateUtils;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Hello, baka!");

        logger.info("Initializing Hibernate ORM...");
        HibernateUtils.setupSessionFactory();

        logger.info("Loading garage items...");
        ItemsDataManager.load();
        GarageManager.loadItems();

        logger.info("Loading codecs & packet handlers...");
        CodecRegistry.load();
        PacketHandlersRegistry.load();

        logger.info("Loading maps...");
        MapManager.loadMaps();

        Server server = new Server();
        server.run();

        logger.info("Shutting down (end of main)");
    }
}