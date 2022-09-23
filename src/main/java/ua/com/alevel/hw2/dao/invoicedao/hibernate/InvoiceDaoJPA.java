package ua.com.alevel.hw2.dao.invoicedao.hibernate;

import org.hibernate.Session;
import org.hibernate.type.IntegerType;
import ua.com.alevel.hw2.config.EntityManagerConfig;
import ua.com.alevel.hw2.dao.invoicedao.IInvoiceDao;
import ua.com.alevel.hw2.model.invoice.Invoice;
import ua.com.alevel.hw2.model.product.Mouse;
import ua.com.alevel.hw2.model.product.Phone;
import ua.com.alevel.hw2.model.product.WashingMachine;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class InvoiceDaoJPA implements IInvoiceDao {
    private static final EntityManager MANAGER = EntityManagerConfig.getEntityManager();
    private static InvoiceDaoJPA instance;

    private InvoiceDaoJPA() {}

    public static InvoiceDaoJPA getInstance() {
        if (instance == null) {
            instance = new InvoiceDaoJPA();
        }

        return instance;
    }

    @Override
    public void save(Invoice invoice) {
        invoice.getProducts().forEach(product -> product.setInvoice(invoice));
        MANAGER.getTransaction().begin();
        MANAGER.persist(invoice);
        MANAGER.flush();
        MANAGER.getTransaction().commit();
    }

    @Override
    public Optional<Invoice> findById(String id) {
        Optional<Invoice> invoice = (Optional<Invoice>) MANAGER.createQuery("from Invoice as i where i.id = :id")
                .setParameter("id", id)
                .getSingleResult();

        return (invoice.isPresent()) ? invoice : Optional.empty();
    }

    @Override
    public List<Invoice> getAll() {
        List<Invoice> list = MANAGER.createQuery("from Invoice").getResultList();

        return (list.isEmpty()) ? Collections.emptyList() : list;
    }

    public void updateDate(String id, Date date) {
        Invoice invoice = findById(id).get();
        invoice.setDate(date);
        MANAGER.getTransaction().begin();
        MANAGER.merge(invoice);
        MANAGER.getTransaction().commit();
    }

    public int getInvoiceAmount() {
        return (int) MANAGER.unwrap(Session.class)
                .createSQLQuery("select count(*) as amount from Invoice")
                .addScalar("amount", IntegerType.INSTANCE)
                .getSingleResult();
    }

    public List<Invoice> getInvoicesWhereSumMoreThanPrice(double price) {
        List<Invoice> list = MANAGER.createQuery("from Invoice as i where i.sum > :price")
                .setParameter("price", price)
                .getResultList();

        return (list.isEmpty()) ? Collections.emptyList() : list;
    }

    public Map<Double, Integer> groupingBySum() {
        CriteriaBuilder cb = MANAGER.getCriteriaBuilder();
        CriteriaQuery<Object[]> cr = cb.createQuery(Object[].class);
        Root<Invoice> root = cr.from(Invoice.class);
        cr.multiselect(cb.count(root.get("id")), root.get("sum"));
        cr.groupBy(root.get("sum"));

        List<Object[]> result = MANAGER.createQuery(cr).getResultList();
        Map<Double, Integer> rez = new HashMap<>();
        result.forEach(el -> rez.put((Double) el[1], (Integer) el[0]));

        return (rez.isEmpty()) ? Collections.emptyMap() : rez;
    }




}
