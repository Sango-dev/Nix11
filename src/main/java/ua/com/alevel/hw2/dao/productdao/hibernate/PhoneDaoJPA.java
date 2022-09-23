package ua.com.alevel.hw2.dao.productdao.hibernate;

import ua.com.alevel.hw2.config.EntityManagerConfig;
import ua.com.alevel.hw2.dao.productdao.IProductDao;
import ua.com.alevel.hw2.model.product.Phone;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class PhoneDaoJPA implements IProductDao<Phone> {
    private static final EntityManager MANAGER = EntityManagerConfig.getEntityManager();
    private static PhoneDaoJPA instance;

    private PhoneDaoJPA() {}

    public static PhoneDaoJPA getInstance() {
        if (instance == null) {
            instance = new PhoneDaoJPA();
        }

        return instance;
    }

    @Override
    public void save(Phone product) {
        MANAGER.getTransaction().begin();
        MANAGER.persist(product);
        MANAGER.flush();
        MANAGER.getTransaction().commit();
    }

    @Override
    public void saveAll(List<Phone> products) {
        MANAGER.getTransaction().begin();
        products.stream()
                .forEach(product -> MANAGER.persist(product));
        MANAGER.flush();
        MANAGER.getTransaction().commit();
    }

    @Override
    public void update(Phone product) {
        MANAGER.getTransaction().begin();
        MANAGER.merge(product);
        MANAGER.getTransaction().commit();
    }

    @Override
    public void delete(String id) {
        MANAGER.getTransaction().begin();
        MANAGER.remove(MANAGER.find(Phone.class, id));
        MANAGER.getTransaction().commit();
    }

    @Override
    public Optional<Phone> findById(String id) {
        Optional<Phone> phone = (Optional<Phone>) MANAGER.createQuery("from Phone as p where p.id = :id")
                .setParameter("id", id)
                .getSingleResult();
        return (phone.isPresent()) ? phone : Optional.empty();
    }

    @Override
    public List<Phone> getAll() {
        List<Phone> list = MANAGER.createQuery("from Phone")
                .getResultList();

        return (list.isEmpty()) ? Collections.emptyList() : list;
    }

    @Override
    public boolean checkNullForeignInvoiceID(String id) {
        Optional<Phone> phone = (Optional<Phone>) MANAGER.createQuery("from Phone as p where p.id = :id and p.invoice_id = null")
                .setParameter("id", id)
                .getSingleResult();
        return (phone.isPresent()) ? true : false;
    }
}
