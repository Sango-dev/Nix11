package ua.com.alevel.hw2.service;

import ua.com.alevel.hw2.annotation.Autowired;
import ua.com.alevel.hw2.annotation.Singleton;
import ua.com.alevel.hw2.model.Manufacturer;
import ua.com.alevel.hw2.model.WashingMachine;
import ua.com.alevel.hw2.repository.CrudRepository;
import ua.com.alevel.hw2.repository.WMRepository;

import java.util.Map;

@Singleton
public class WMService extends TechProductService<WashingMachine> {

    private static WMService instance;

    @Autowired
    private WMService(final WMRepository repository) {
        super(repository);
    }

    public static WMService getInstance() {
        if (instance == null) {
            instance = new WMService(WMRepository.getInstance());
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
