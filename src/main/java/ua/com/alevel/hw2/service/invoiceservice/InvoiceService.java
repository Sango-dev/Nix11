package ua.com.alevel.hw2.service.invoiceservice;

import ua.com.alevel.hw2.dao.invoicedao.mongo.InvoiceMongoDao;
import ua.com.alevel.hw2.model.invoice.Invoice;
import ua.com.alevel.hw2.model.product.TechProduct;

import java.util.*;

public final class InvoiceService {
    private final InvoiceMongoDao invoiceMongoDao;
    private static InvoiceService instance;

    private InvoiceService(InvoiceMongoDao invoiceMongoDao) {
        this.invoiceMongoDao = invoiceMongoDao;
    }

    public static InvoiceService getInstance() {
        if (instance == null) {
            instance = new InvoiceService(InvoiceMongoDao.getInstance());
        }
        return instance;
    }

    public Invoice createInvoice(List<TechProduct> products) {
        double sum = products.stream().mapToDouble(product -> product.getPrice()).sum();
        return new Invoice(sum, products, new Date());
    }

    public void save(Invoice invoice) {
        if (invoice == null) {
            throw new IllegalArgumentException("Invoice must not be null!");
        }
        invoiceMongoDao.save(invoice);
    }

    public Optional<Invoice> findById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null!");
        }
        return invoiceMongoDao.findById(id);
    }

    public List<Invoice> getAll() {
        return invoiceMongoDao.getAll();
    }

    public int getInvoiceAmount() {
        return invoiceMongoDao.getInvoiceAmount();
    }

    public List<Invoice> getInvoicesWhereSumMoreThanPrice(double price) {
        return invoiceMongoDao.getInvoicesWhereSumMoreThanPrice(price);
    }

    public void updateDate(String id, Date date) {
        invoiceMongoDao.updateDate(id, date);
    }

    public Map<Double, Integer> groupingBySum() {
        return invoiceMongoDao.groupingBySum();
    }
}
