package ua.com.alevel.hw2.service;

import ua.com.alevel.hw2.annotation.Autowired;
import ua.com.alevel.hw2.annotation.Singleton;
import ua.com.alevel.hw2.model.ConnectionType;
import ua.com.alevel.hw2.model.Manufacturer;
import ua.com.alevel.hw2.model.Mouse;
import ua.com.alevel.hw2.repository.CrudRepository;
import ua.com.alevel.hw2.repository.MouseRepository;

import java.util.Map;

@Singleton
public class MouseService extends TechProductService<Mouse> {
    private static MouseService instance;

    @Autowired
    private MouseService(final MouseRepository repository) {
        super(repository);
    }

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
