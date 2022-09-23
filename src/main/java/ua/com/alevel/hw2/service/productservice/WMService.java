package ua.com.alevel.hw2.service.productservice;

import ua.com.alevel.hw2.annotation.Autowired;
import ua.com.alevel.hw2.annotation.Singleton;
import ua.com.alevel.hw2.dao.productdao.hibernate.WMDaoJPA;
import ua.com.alevel.hw2.model.product.Manufacturer;
import ua.com.alevel.hw2.model.product.WashingMachine;

import java.util.Map;

@Singleton
public class WMService extends TechProductService<WashingMachine> {
    private static WMService instance;
    private final WMDaoJPA wmDaoJPA;

    @Autowired
    private WMService(final WMDaoJPA wmDaoJPA) {
        super(wmDaoJPA);
        this.wmDaoJPA = wmDaoJPA;
    }

    public static WMService getInstance() {
        if (instance == null) {
            instance = new WMService(WMDaoJPA.getInstance());
        }
        return instance;
    }

    @Override
    public WashingMachine createProductFromMapImpl(Map<String, Object> map) {
        return new WashingMachine(map.get("model").toString(),
                Manufacturer.valueOf(map.get("manufacturer").toString()),
                Integer.parseInt(map.get("count").toString()),
                Double.parseDouble(map.get("price").toString()),
                Integer.parseInt(map.get("turnsNumber").toString())
        );
    }
}
