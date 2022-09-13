package ua.com.alevel.hw2.service.productservice;

import ua.com.alevel.hw2.annotation.Autowired;
import ua.com.alevel.hw2.annotation.Singleton;
import ua.com.alevel.hw2.dao.productdao.WMDao;
import ua.com.alevel.hw2.model.product.Manufacturer;
import ua.com.alevel.hw2.model.product.WashingMachine;

import java.util.Map;

@Singleton
public class WMService extends TechProductService<WashingMachine> {
    private static WMService instance;
    private final WMDao wmDao;

    @Autowired
    private WMService(final WMDao wmDao) {
        super(wmDao);
        this.wmDao = wmDao;
    }

    public static WMService getInstance() {
        if (instance == null) {
            instance = new WMService(WMDao.getInstance());
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
