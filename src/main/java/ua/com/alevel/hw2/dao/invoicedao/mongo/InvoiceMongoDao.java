package ua.com.alevel.hw2.dao.invoicedao.mongo;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import ua.com.alevel.hw2.config.MongoDBConfig;
import ua.com.alevel.hw2.dao.invoicedao.IInvoiceDao;
import ua.com.alevel.hw2.model.invoice.Invoice;
import ua.com.alevel.hw2.model.product.Mouse;
import ua.com.alevel.hw2.model.product.Phone;
import ua.com.alevel.hw2.model.product.WashingMachine;

import java.util.*;

public class InvoiceMongoDao implements IInvoiceDao {
    private static final MongoCollection<Document> INVOICES = MongoDBConfig.getMongoDatabase().getCollection(Invoice.class.getSimpleName());
    private static final MongoCollection<Document> PHONES = MongoDBConfig.getMongoDatabase().getCollection(Phone.class.getSimpleName());
    private static final MongoCollection<Document> MICE = MongoDBConfig.getMongoDatabase().getCollection(Mouse.class.getSimpleName());
    private static final MongoCollection<Document> WM = MongoDBConfig.getMongoDatabase().getCollection(WashingMachine.class.getSimpleName());
    private static InvoiceMongoDao instance;
    private static Gson gson;

    private InvoiceMongoDao() {
        gson = new Gson();
    }

    public static InvoiceMongoDao getInstance() {
        if (instance == null) {
            instance = new InvoiceMongoDao();
        }

        return instance;
    }

    @Override
    public void save(Invoice invoice) {
        INVOICES.insertOne(Document.parse(gson.toJson(invoice)));
    }

    @Override
    public Optional<Invoice> findById(String id) {
        Invoice invoice = INVOICES.find(Filters.eq("id", id))
                .map(el -> gson.fromJson(el.toJson(), Invoice.class))
                .first();

        return (invoice == null) ? Optional.empty() : Optional.of(revealProducts(invoice));
    }

    private Invoice revealProducts(Invoice invoice) {
        invoice.setProducts(new ArrayList<>());
        for (String productId : invoice.getProductInMongo()) {
            Phone phone = PHONES.find(Filters.eq("id", productId))
                    .map(object -> gson.fromJson(object.toJson(), Phone.class))
                    .first();

            if (phone != null) {
                invoice.getProducts().add(phone);
                continue;
            }

            Mouse mouse = MICE.find(Filters.eq("id", productId))
                    .map(object -> gson.fromJson(object.toJson(), Mouse.class))
                    .first();

            if (mouse != null) {
                invoice.getProducts().add(mouse);
                continue;
            }

            WashingMachine washingMachine = WM.find(Filters.eq("id", productId))
                    .map(object -> gson.fromJson(object.toJson(), WashingMachine.class))
                    .first();

            if (washingMachine != null) {
                invoice.getProducts().add(washingMachine);
            }
        }

        return invoice;
    }

    @Override
    public List<Invoice> getAll() {
        List<Invoice> invoices = new ArrayList<>();
        INVOICES.find()
                .map(object -> gson.fromJson(object.toJson(), Invoice.class))
                .into(invoices);

        invoices.stream().forEach(invoice -> revealProducts(invoice));
        return invoices;
    }

    public List<Invoice> getInvoicesWhereSumMoreThanPrice(double price) {
        List<Invoice> invoices = new ArrayList<>();
        INVOICES.find(Filters.gt("sum", price))
                .map(object -> gson.fromJson(object.toJson(), Invoice.class))
                .into(invoices);

        invoices.stream().forEach(invoice -> revealProducts(invoice));
        return invoices;
    }

    public int getInvoiceAmount() {
        return (int) INVOICES.countDocuments();
    }

    public void updateDate(String id, Date date) {
        Invoice invoice = findById(id).get();
        invoice.setDate(date);
        INVOICES.updateOne(Filters.eq("id", id), new Document("$set", Document.parse(gson.toJson(invoice))));
    }

    public Map<Double, Integer> groupingBySum() {
        Map<Double, Integer> map = new HashMap<>();

        INVOICES.aggregate(List.of(Aggregates.group("$sum", Accumulators.sum("count", 1))))
                .map(element -> gson.fromJson(element.toJson(), JsonObject.class))
                .into(new ArrayList<>())
                .forEach(element -> map.put(element.get("_id").getAsDouble(), element.get("count").getAsInt()));

        return map;
    }
}
