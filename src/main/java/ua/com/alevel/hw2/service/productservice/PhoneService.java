package ua.com.alevel.hw2.service.productservice;

import ua.com.alevel.hw2.annotation.Autowired;
import ua.com.alevel.hw2.annotation.Singleton;
import ua.com.alevel.hw2.dao.productdao.hibernate.PhoneDaoJPA;
import ua.com.alevel.hw2.dao.productdao.jdbc.PhoneDao;
import ua.com.alevel.hw2.model.product.Manufacturer;
import ua.com.alevel.hw2.model.product.Phone;

import java.util.Map;

@Singleton
public class PhoneService extends TechProductService<Phone> {

    private static PhoneService instance;

    private final PhoneDaoJPA phoneDaoJPA;

    @Autowired
    private PhoneService(final PhoneDaoJPA phoneDaoJPA) {
        super(phoneDaoJPA);
        this.phoneDaoJPA = phoneDaoJPA;
    }

    public static PhoneService getInstance() {
        if (instance == null) {
            instance = new PhoneService(PhoneDaoJPA.getInstance());
        }
        return instance;
    }

    public static PhoneService getInstance(final PhoneDaoJPA phoneDaoJPA) {
        if (instance == null) {
            instance = new PhoneService(phoneDaoJPA);
        }
        return instance;
    }

    public boolean isConcreteDetailExist(String needsDetail) {
        return getAll()
                .stream()
                .flatMap(phone -> phone.getDetails().stream())
                .anyMatch(detail -> detail.equals(needsDetail));
    }

    @Override
    public Phone createProductFromMapImpl(Map<String, Object> map) {
        return new Phone(map.get("model").toString(),
                Manufacturer.valueOf(map.get("manufacturer").toString()),
                Integer.parseInt(map.get("count").toString()),
                Double.parseDouble(map.get("price").toString()),
                Integer.parseInt(map.get("coreNumbers").toString()),
                Integer.parseInt(map.get("batteryPower").toString())
        );
    }
}
