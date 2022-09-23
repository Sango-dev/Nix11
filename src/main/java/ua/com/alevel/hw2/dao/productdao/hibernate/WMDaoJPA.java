package ua.com.alevel.hw2.dao.productdao.hibernate;

import ua.com.alevel.hw2.config.EntityManagerConfig;
import ua.com.alevel.hw2.dao.productdao.IProductDao;
import ua.com.alevel.hw2.model.product.WashingMachine;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class WMDaoJPA implements IProductDao<WashingMachine> {
    private static final EntityManager MANAGER = EntityManagerConfig.getEntityManager();
    private static WMDaoJPA instance;

    private WMDaoJPA() {}

    public static WMDaoJPA getInstance() {
        if (instance == null) {
            instance = new WMDaoJPA();
        }

        return instance;
    }

    @Override
    public void save(WashingMachine product) {
        MANAGER.getTransaction().begin();
        MANAGER.persist(product);
        MANAGER.flush();
        MANAGER.getTransaction().commit();
    }

    @Override
    public void saveAll(List<WashingMachine> products) {
        MANAGER.getTransaction().begin();
        products.stream()
                .forEach(product -> MANAGER.persist(product));
        MANAGER.flush();
        MANAGER.getTransaction().commit();
    }

    @Override
    public void update(WashingMachine product) {
        MANAGER.getTransaction().begin();
        MANAGER.merge(product);
        MANAGER.getTransaction().commit();
    }

    @Override
    public void delete(String id) {
        MANAGER.getTransaction().begin();
        MANAGER.remove(MANAGER.find(WashingMachine.class, id));
        MANAGER.getTransaction().commit();
    }

    @Override
    public Optional<WashingMachine> findById(String id) {
        Optional<WashingMachine> machine = (Optional<WashingMachine>) MANAGER.createQuery("from Washing_Machine as wm where wm.id = :id")
                .setParameter("id", id)
                .getSingleResult();
        return (machine.isPresent()) ? machine : Optional.empty();
    }

    @Override
    public List<WashingMachine> getAll() {
        List<WashingMachine> list = MANAGER.createQuery("from Washing_Machine")
                .getResultList();

        return (list.isEmpty()) ? Collections.emptyList() : list;
    }

    @Override
    public boolean checkNullForeignInvoiceID(String id) {
        Optional<WashingMachine> machine = (Optional<WashingMachine>) MANAGER.createQuery("from Washing_Machine as wm where wm.id = :id and wm.invoice_id = null")
                .setParameter("id", id)
                .getSingleResult();
        return (machine.isPresent()) ? true : false;
    }
}