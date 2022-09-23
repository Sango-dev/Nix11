package ua.com.alevel.hw2.dao.productdao.hibernate;

import ua.com.alevel.hw2.config.EntityManagerConfig;
import ua.com.alevel.hw2.dao.productdao.IProductDao;
import ua.com.alevel.hw2.model.product.Mouse;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class MouseDaoJPA implements IProductDao<Mouse> {
    private static final EntityManager MANAGER = EntityManagerConfig.getEntityManager();
    private static MouseDaoJPA instance;

    private MouseDaoJPA() {}

    public static MouseDaoJPA getInstance() {
        if (instance == null) {
            instance = new MouseDaoJPA();
        }

        return instance;
    }

    @Override
    public void save(Mouse product) {
        MANAGER.getTransaction().begin();
        MANAGER.persist(product);
        MANAGER.flush();
        MANAGER.getTransaction().commit();
    }

    @Override
    public void saveAll(List<Mouse> products) {
        MANAGER.getTransaction().begin();
        products.stream()
                .forEach(product -> MANAGER.persist(product));
        MANAGER.flush();
        MANAGER.getTransaction().commit();
    }

    @Override
    public void update(Mouse product) {
        MANAGER.getTransaction().begin();
        MANAGER.merge(product);
        MANAGER.getTransaction().commit();
    }

    @Override
    public void delete(String id) {
        MANAGER.getTransaction().begin();
        MANAGER.remove(MANAGER.find(Mouse.class, id));
        MANAGER.getTransaction().commit();
    }

    @Override
    public Optional<Mouse> findById(String id) {
        Optional<Mouse> mouse = (Optional<Mouse>) MANAGER.createQuery("from Mouse as m where m.id = :id")
                .setParameter("id", id)
                .getSingleResult();
        return (mouse.isPresent()) ? mouse : Optional.empty();
    }

    @Override
    public List<Mouse> getAll() {
        List<Mouse> list = MANAGER.createQuery("from Mouse")
                .getResultList();

        return (list.isEmpty()) ? Collections.emptyList() : list;
    }

    @Override
    public boolean checkNullForeignInvoiceID(String id) {
        Optional<Mouse> mouse = (Optional<Mouse>) MANAGER.createQuery("from Mouse as m where m.id = :id and m.invoice_id = null")
                .setParameter("id", id)
                .getSingleResult();
        return (mouse.isPresent()) ? true : false;
    }
}