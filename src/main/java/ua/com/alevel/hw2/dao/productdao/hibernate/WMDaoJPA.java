package ua.com.alevel.hw2.dao.productdao.hibernate;

import ua.com.alevel.hw2.annotation.Autowired;
import ua.com.alevel.hw2.annotation.Singleton;
import ua.com.alevel.hw2.config.EntityManagerConfig;
import ua.com.alevel.hw2.dao.productdao.IProductDao;
import ua.com.alevel.hw2.model.product.Mouse;
import ua.com.alevel.hw2.model.product.WashingMachine;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Singleton
public final class WMDaoJPA implements IProductDao<WashingMachine> {
    private static final EntityManager MANAGER = EntityManagerConfig.getEntityManager();
    private static WMDaoJPA instance;

    @Autowired
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
        List<WashingMachine> list = MANAGER.createQuery("from WashingMachine as wm where wm.id = :id")
                .setParameter("id", id)
                .getResultList();

        return (list.isEmpty()) ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public List<WashingMachine> getAll() {
        List<WashingMachine> list = MANAGER.createQuery("from WashingMachine")
                .getResultList();

        return (list.isEmpty()) ? Collections.emptyList() : list;
    }

    @Override
    public boolean checkNullForeignInvoiceID(String id) {
        List<WashingMachine> list = MANAGER.createQuery("from WashingMachine as wm where wm.id = :id and wm.invoice = null")
                .setParameter("id", id)
                .getResultList();

        return (list.isEmpty()) ? false : true;
    }
}