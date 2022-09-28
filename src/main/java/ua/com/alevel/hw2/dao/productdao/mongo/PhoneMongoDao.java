package ua.com.alevel.hw2.dao.productdao.mongo;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import ua.com.alevel.hw2.config.MongoDBConfig;
import ua.com.alevel.hw2.dao.productdao.IProductDao;
import ua.com.alevel.hw2.model.invoice.Invoice;
import ua.com.alevel.hw2.model.product.Phone;

import java.util.*;

public class PhoneMongoDao implements IProductDao<Phone> {
    private static final MongoCollection<Document> PHONES = MongoDBConfig.getMongoDatabase().getCollection(Phone.class.getSimpleName());
    private static final MongoCollection<Document> INVOICES = MongoDBConfig.getMongoDatabase().getCollection(Invoice.class.getSimpleName());
    private static Gson gson;
    private static PhoneMongoDao instance;

    private PhoneMongoDao() {
        gson = new Gson();
    }

    public static PhoneMongoDao getInstance() {
        if (instance == null) {
            instance = new PhoneMongoDao();
        }

        return instance;
    }

    @Override
    public void save(Phone product) {
        PHONES.insertOne(Document.parse(gson.toJson(product)));
    }

    @Override
    public void saveAll(List<Phone> products) {
        List<Document> list = products.stream()
                .map(product -> Document.parse(gson.toJson(product)))
                .toList();

        PHONES.insertMany(list);
    }

    @Override
    public void update(Phone product) {
        PHONES.updateOne(Filters.eq("id", product.getId()), new Document("$set", gson.toJson(product)));
    }

    @Override
    public void delete(String id) {
        PHONES.deleteOne(Filters.eq("id", id));
    }

    @Override
    public Optional<Phone> findById(String id) {
        Phone phone = PHONES.find(Filters.eq("id", id))
                .map(element -> gson.fromJson(element.toJson(), Phone.class))
                .first();

        return Optional.of(phone);
    }

    @Override
    public List<Phone> getAll() {
        return PHONES.find()
                .map(object -> gson.fromJson(object.toJson(), Phone.class))
                .into(new ArrayList<Phone>());
    }

    @Override
    public boolean checkNullForeignInvoiceID(String id) {
        List<Phone> list = getAll();
        if (list.isEmpty()) {
            return false;
        }

        return (list.stream()
                .filter(product -> INVOICES.find(Filters.eq("productInMongo", product.getId())).first() != null)
                .toList().isEmpty()) ? true : false;
    }
}
