package ua.com.alevel.hw2.config;

import org.flywaydb.core.Flyway;
import ua.com.alevel.hw2.annotationhandler.AnnotationHandler;

public final class FlywayConf {
    private static final String URL = "jdbc:postgresql://ec2-44-205-63-142.compute-1.amazonaws.com:5432/d7fakea4aa1co4";
    private static final String USER = "kwxmuuikcdvnus";
    private static final String PASSWORD = "a7291f133f4577fbbbfceebfb070de06a62db5da0664e84b92d242ebd16853ce";
    private static final String SCHEMA = "public";
    private static final String LOCATION = "db/migration";

    private FlywayConf() {
    }

    public static void init() {
        Flyway flyway = Flyway.configure()
                .dataSource(URL, USER, PASSWORD)
                .baselineOnMigrate(true)
                .schemas(SCHEMA)
                .locations(LOCATION)
                .load();
        flyway.clean();
        AnnotationHandler.createAndCasheWithAnnotation();
        flyway.migrate();
    }
}