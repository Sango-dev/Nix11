package ua.com.alevel.hw2.service;

import ua.com.alevel.hw2.model.Manufacturer;
import ua.com.alevel.hw2.model.WashingMachine;
import ua.com.alevel.hw2.repository.CrudRepository;
import ua.com.alevel.hw2.repository.WMRepository;

import java.util.Map;

public class WMService extends TechProductService<WashingMachine> {

    public WMService(CrudRepository<WashingMachine> repository) {
        super(repository);
    }

    private static WMService instance;

    public static WMService getInstance() {
        if (instance == null) {
            instance = new WMService(new WMRepository());
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
