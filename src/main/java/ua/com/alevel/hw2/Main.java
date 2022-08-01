package ua.com.alevel.hw2;

import ua.com.alevel.hw2.container.ProductContainer;
import ua.com.alevel.hw2.factory.ProductFactory;
import ua.com.alevel.hw2.model.*;
import ua.com.alevel.hw2.repository.MouseRepository;
import ua.com.alevel.hw2.repository.PhoneRepository;
import ua.com.alevel.hw2.repository.WMRepository;
import ua.com.alevel.hw2.service.MouseService;
import ua.com.alevel.hw2.service.PhoneService;
import ua.com.alevel.hw2.service.TechProductService;
import ua.com.alevel.hw2.service.WMService;

import java.lang.reflect.Field;
import java.util.Optional;

public class Main {

    private static final TechProductService<Phone> PHONE_SERVICE = new PhoneService(new PhoneRepository());
    private static final TechProductService<Mouse> MOUSE_SERVICE = new MouseService(new MouseRepository());
    private static final TechProductService<WashingMachine> WM_SERVICE = new WMService(new WMRepository());
    private static final int NUMBER_ZERO = 0;
    private static final int COUNT = 5;
    private static final int PHONE_COUNT = 99;
    private static final double PHONE_PRICE = 99.9;
    private static final int CNT_REPEAT = 10;

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        //SAVE OBJECTS
        System.out.println("Save objects");
        PHONE_SERVICE.createAndSave(COUNT, TechProductType.PHONE);
        MOUSE_SERVICE.createAndSave(COUNT, TechProductType.MOUSE);
        WM_SERVICE.createAndSave(COUNT, TechProductType.WASHING_MACHINE);

        //PRINTALL
        System.out.println("\n\nPrint All Objects");
        PHONE_SERVICE.printAll();
        System.out.println("~".repeat(CNT_REPEAT));
        MOUSE_SERVICE.printAll();
        System.out.println("~".repeat(CNT_REPEAT));
        WM_SERVICE.printAll();

        //FINDBYID
        System.out.print("\n\nFind phone with id: ");
        String id = PHONE_SERVICE.getAll().get(NUMBER_ZERO).getId();
        System.out.println('"' + id + '"');
        Optional<Phone> phoneOptional = PHONE_SERVICE.findById(id);
        if (phoneOptional.isPresent()) {
            System.out.println(phoneOptional.get());
        }

        //UPDATE
        System.out.println("\n\nUpdate phone with id: " + '"' + id + '"');
        Phone phone = new Phone(
                phoneOptional.get().getModel(),
                phoneOptional.get().getManufacturer(),
                PHONE_COUNT,
                PHONE_PRICE,
                phoneOptional.get().getCoreNumbers(),
                phoneOptional.get().getBatteryPower()
        );

        Field field = phone.getClass().getSuperclass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(phone, id);
        PHONE_SERVICE.update(phone);
        System.out.println(PHONE_SERVICE.findById(id).get());

        //DELETE
        System.out.println("\n\nDelete phone with id: " + '"' + id + '"');
        PHONE_SERVICE.delete(id);
        PHONE_SERVICE.printAll();

        //CONTAINER
        System.out.println("\n\nPhone in Container:");
        ProductContainer<Phone> container = new ProductContainer(ProductFactory.creatProduct(TechProductType.PHONE));
        System.out.println(container.getProduct());
        System.out.println("Add " + PHONE_COUNT + " to count:");
        container.addCount(PHONE_COUNT);
        System.out.println(container.getProduct());
        System.out.println("Do discount: " + container.changePriceWithDiscount() + '%');
        System.out.println(container.getProduct());
    }
}
