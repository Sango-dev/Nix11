package ua.com.alevel.hw2.dao.productdao.mongo;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import ua.com.alevel.hw2.config.MongoDBConfig;
import ua.com.alevel.hw2.dao.productdao.IProductDao;
import ua.com.alevel.hw2.model.invoice.Invoice;
import ua.com.alevel.hw2.model.product.Mouse;

import java.util.*;

public class MouseMongoDao implements IProductDao<Mouse> {
    private static final MongoCollection<Document> MICE = MongoDBConfig.getMongoDatabase().getCollection(Mouse.class.getSimpleName());
    private static final MongoCollection<Document> INVOICES = MongoDBConfig.getMongoDatabase().getCollection(Invoice.class.getSimpleName());
    private static Gson gson;
    private static MouseMongoDao instance;

    private MouseMongoDao() {
        gson = new Gson();
    }

    public static MouseMongoDao getInstance() {
        if (instance == null) {
            instance = new MouseMongoDao();
        }

        return instance;
    }

    @Override
    public void save(Mouse product) {
        MICE.insertOne(Document.parse(gson.toJson(product)));
    }

    @Override
    public void saveAll(List<Mouse> products) {
        List<Document> list = products.stream()
                .map(product -> Document.parse(gson.toJson(product)))
                .toList();

        MICE.insertMany(list);
    }

    @Override
    public void update(Mouse product) {
        Bson filter = Filters.eq("id", product.getId());
        Bson updates = Updates.combine(
                Updates.set("model", product.getModel()),
                Updates.set("manufacturer", product.getManufacturer().name()),
                Updates.set("count", product.getCount()),
                Updates.set("connectionType", product.getConnectionType().name()),
                Updates.set("price", product.getPrice()),
                Updates.set("dpiAmount", product.getDpiAmount()));

        MICE.updateOne(filter, updates);
    }

    @Override
    public void delete(String id) {
        MICE.deleteOne(Filters.eq("id", id));
    }

    @Override
    public Optional<Mouse> findById(String id) {
        Mouse mouse = MICE.find(Filters.eq("id", id))
                .map(element -> gson.fromJson(element.toJson(), Mouse.class))
                .first();

        return Optional.ofNullable(mouse);
    }

    @Override
    public List<Mouse> getAll() {
        return MICE.find()
                .map(object -> gson.fromJson(object.toJson(), Mouse.class))
                .into(new ArrayList<Mouse>());
    }

    @Override
    public boolean checkNullForeignInvoiceID(String id) {
        List<Mouse> list = getAll();
        if (list.isEmpty()) {
            return false;
        }

        for (Mouse mouse : list) {
            if (mouse.getId().equals(id)) {
                return (list.stream()
                        .filter(product -> INVOICES.find(Filters.eq("productInMongo", product.getId())).first() != null)
                        .toList().isEmpty()) ? true : false;
            }
        }

        return false;
    }
}
