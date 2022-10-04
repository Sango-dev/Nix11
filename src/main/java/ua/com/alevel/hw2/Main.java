package ua.com.alevel.hw2;
import org.flywaydb.core.Flyway;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ua.com.alevel.hw2.config.FlywayConf;
import ua.com.alevel.hw2.controller.Controller;


public class Main {
    static Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    static {
        root.setLevel(Level.INFO);
    }

    public static void main(String[] args) {
        FlywayConf.init();
        Controller.run();
    }
}
