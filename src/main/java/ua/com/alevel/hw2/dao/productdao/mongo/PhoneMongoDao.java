package ua.com.alevel.hw2.dao.productdao.mongo;

import com.google.gson.Gson;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import lombok.SneakyThrows;
import org.bson.Document;
import org.bson.conversions.Bson;
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

    @SneakyThrows
    @Override
    public void update(Phone product) {
        Bson filter = Filters.eq("id", product.getId());
        Bson updates = Updates.combine(
                Updates.set("model", product.getModel()),
                Updates.set("manufacturer", product.getManufacturer().name()),
                Updates.set("count", product.getCount()),
                Updates.set("coreNumbers", product.getCoreNumbers()),
                Updates.set("price", product.getPrice()),
                Updates.set("batteryPower", product.getBatteryPower()));

        PHONES.updateOne(filter, updates);
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

        return Optional.ofNullable(phone);
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

        for (Phone phone : list) {
            if (phone.getId().equals(id)) {
                return (list.stream()
                        .filter(product -> INVOICES.find(Filters.eq("productInMongo", product.getId())).first() != null)
                        .toList().isEmpty()) ? true : false;
            }
        }

        return false;
    }
}
