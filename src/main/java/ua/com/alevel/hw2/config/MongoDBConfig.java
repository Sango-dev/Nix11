package ua.com.alevel.hw2.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public final class MongoDBConfig {
    private static final String URL = "localhost";
    private static final int PORT = 27017;
    private static final String DATABASE = "ProductShop";
    private static MongoClient mongoClient;
    private static MongoDatabase db;

    private MongoDBConfig() {
    }

    public static MongoDatabase getMongoDatabase() {
        if (mongoClient == null) {
            mongoClient = new MongoClient(URL, PORT);
            db = mongoClient.getDatabase(DATABASE);
            db.drop();
        }

        return db;
    }
}
