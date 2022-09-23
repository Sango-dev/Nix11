package ua.com.alevel.hw2.service.invoiceservice;

import ua.com.alevel.hw2.dao.invoicedao.hibernate.InvoiceDaoJPA;
import ua.com.alevel.hw2.model.invoice.Invoice;
import ua.com.alevel.hw2.model.product.TechProduct;

import java.util.*;

public final class InvoiceService {
    private final InvoiceDaoJPA invoiceDaoJPA;
    private static InvoiceService instance;

    private InvoiceService(InvoiceDaoJPA invoiceDaoJPA) {
        this.invoiceDaoJPA = invoiceDaoJPA;
    }

    public static InvoiceService getInstance() {
        if (instance == null) {
            instance = new InvoiceService(InvoiceDaoJPA.getInstance());
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
        invoiceDaoJPA.save(invoice);
    }

    public Optional<Invoice> findById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null!");
        }
        return invoiceDaoJPA.findById(id);
    }

    public List<Invoice> getAll() {
        return invoiceDaoJPA.getAll();
    }

    public int getInvoiceAmount() {
        return invoiceDaoJPA.getInvoiceAmount();
    }

    public List<Invoice> getInvoicesWhereSumMoreThanPrice(double price) {
        return invoiceDaoJPA.getInvoicesWhereSumMoreThanPrice(price);
    }

    public void updateDate(String id, Date date) {
        invoiceDaoJPA.updateDate(id, date);
    }

    public Map<Double, Integer> groupingBySum() {
        return invoiceDaoJPA.groupingBySum();
    }
}
