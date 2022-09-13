package ua.com.alevel.hw2.service.invoiceservice;

import ua.com.alevel.hw2.dao.invoicedao.InvoiceDao;
import ua.com.alevel.hw2.model.invoice.Invoice;
import ua.com.alevel.hw2.model.product.TechProduct;

import java.util.*;

public final class InvoiceService {
    private final InvoiceDao invoiceDao;
    private static InvoiceService instance;

    private InvoiceService(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    public static InvoiceService getInstance() {
        if (instance == null) {
            instance = new InvoiceService(InvoiceDao.getInstance());
        }
        return instance;
    }

    public Invoice createInvoice(List<TechProduct> products) {
        double sum = products.stream().mapToDouble(product -> product.getPrice()).sum();
        return new Invoice(UUID.randomUUID().toString(), sum, products, new Date());
    }

    public void save(Invoice invoice) {
        if (invoice == null) {
            throw new IllegalArgumentException("Invoice must not be null!");
        }
        invoiceDao.save(invoice);
    }

    public Optional<Invoice> findById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null!");
        }
        return invoiceDao.findById(id);
    }

    public List<Invoice> getAll() {
        return invoiceDao.getAll();
    }

    public int getInvoiceAmount() {
        return invoiceDao.getInvoiceAmount();
    }

    public List<Invoice> getInvoicesWhereSumMoreThanPrice(double price) {
        return invoiceDao.getInvoicesWhereSumMoreThanPrice(price);
    }

    public void updateDate(String id, Date date) {
        invoiceDao.updateDate(id, date);
    }

    public Map<Double, Integer> groupingBySum() {
        return invoiceDao.groupingBySum();
    }
}
