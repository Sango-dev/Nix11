package ua.com.alevel.hw2.dao.productdao.mongo;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import ua.com.alevel.hw2.config.MongoDBConfig;
import ua.com.alevel.hw2.dao.productdao.IProductDao;
import ua.com.alevel.hw2.model.invoice.Invoice;
import ua.com.alevel.hw2.model.product.Mouse;
import ua.com.alevel.hw2.model.product.WashingMachine;

import java.util.*;

public class WMMongoDao implements IProductDao<WashingMachine> {
    private static final MongoCollection<Document> WM = MongoDBConfig.getMongoDatabase().getCollection(WashingMachine.class.getSimpleName());
    private static final MongoCollection<Document> INVOICES = MongoDBConfig.getMongoDatabase().getCollection(Invoice.class.getSimpleName());
    private static Gson gson;
    private static WMMongoDao instance;

    private WMMongoDao () {
        gson = new Gson();
    }

    public static WMMongoDao getInstance() {
        if (instance == null) {
            instance = new WMMongoDao();
        }

        return instance;
    }

    @Override
    public void save(WashingMachine product) {
        WM.insertOne(Document.parse(gson.toJson(product)));
    }

    @Override
    public void saveAll(List<WashingMachine> products) {
        List<Document> list = products.stream()
                .map(product -> Document.parse(gson.toJson(product)))
                .toList();

        WM.insertMany(list);
    }

    @Override
    public void update(WashingMachine product) {
        WM.updateOne(Filters.eq("id", product.getId()), new Document("$set", gson.toJson(product)));
    }

    @Override
    public void delete(String id) {
        WM.deleteOne(Filters.eq("id", id));
    }

    @Override
    public Optional<WashingMachine> findById(String id) {
        WashingMachine washingMachine = WM.find(Filters.eq("id", id))
                .map(element -> gson.fromJson(element.toJson(), WashingMachine.class))
                .first();

        return Optional.of(washingMachine);
    }

    @Override
    public List<WashingMachine> getAll() {
        return WM.find()
                .map(object -> gson.fromJson(object.toJson(), WashingMachine.class))
                .into(new ArrayList<WashingMachine>());
    }

    @Override
    public boolean checkNullForeignInvoiceID(String id) {
        List<WashingMachine> list = getAll();
        if (list.isEmpty()) {
            return false;
        }

        return (list.stream()
                .filter(product -> INVOICES.find(Filters.eq("productInMongo", product.getId())).first() != null)
                .toList().isEmpty()) ? true : false;
    }
}
