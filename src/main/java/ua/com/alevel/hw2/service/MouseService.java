package ua.com.alevel.hw2.service;

import ua.com.alevel.hw2.model.ConnectionType;
import ua.com.alevel.hw2.model.Manufacturer;
import ua.com.alevel.hw2.model.Mouse;
import ua.com.alevel.hw2.repository.CrudRepository;
import ua.com.alevel.hw2.repository.MouseRepository;
import ua.com.alevel.hw2.repository.PhoneRepository;

import java.util.Map;

public class MouseService extends TechProductService<Mouse> {

    public MouseService(CrudRepository<Mouse> repository) {
        super(repository);
    }

    private static MouseService instance;

    public static MouseService getInstance() {
        if (instance == null) {
            instance = new MouseService(MouseRepository.getInstance());
        }
        return instance;
    }

    @Override
    public Mouse createProductFromMapImpl(Map<String, Object> map) {
        return new Mouse(map.get("model").toString(),
                Manufacturer.valueOf(map.get("manufacturer").toString()),
                Integer.parseInt(map.get("count").toString()),
                Double.parseDouble(map.get("price").toString()),
                ConnectionType.valueOf(map.get("connectionType").toString()),
                Integer.parseInt(map.get("dpiAmount").toString())
        );
    }
}
