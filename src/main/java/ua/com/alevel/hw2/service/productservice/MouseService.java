package ua.com.alevel.hw2.service.productservice;

import ua.com.alevel.hw2.annotation.Autowired;
import ua.com.alevel.hw2.annotation.Singleton;
import ua.com.alevel.hw2.dao.productdao.IProductDao;
import ua.com.alevel.hw2.dao.productdao.hibernate.MouseDaoJPA;
import ua.com.alevel.hw2.model.product.ConnectionType;
import ua.com.alevel.hw2.model.product.Manufacturer;
import ua.com.alevel.hw2.model.product.Mouse;

import java.util.Map;

@Singleton
public class MouseService extends TechProductService<Mouse> {
    private static MouseService instance;

    @Autowired
    private MouseService(final IProductDao<Mouse> dao) {
        super(dao);
    }

    public static MouseService getInstance() {
        if (instance == null) {
            instance = new MouseService(MouseDaoJPA.getInstance());
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


