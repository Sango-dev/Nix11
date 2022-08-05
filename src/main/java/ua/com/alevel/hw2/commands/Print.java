package ua.com.alevel.hw2.commands;

import ua.com.alevel.hw2.model.*;
import ua.com.alevel.hw2.repository.MouseRepository;
import ua.com.alevel.hw2.repository.PhoneRepository;
import ua.com.alevel.hw2.repository.WMRepository;
import ua.com.alevel.hw2.service.MouseService;
import ua.com.alevel.hw2.service.PhoneService;
import ua.com.alevel.hw2.service.TechProductService;
import ua.com.alevel.hw2.service.WMService;

import java.util.List;

public class Print implements Command{

    private static final TechProductService<Phone> PHONE_SERVICE = PhoneService.getInstance();
    private static final TechProductService<Mouse> MOUSE_SERVICE = MouseService.getInstance();
    private static final TechProductService<WashingMachine> WM_SERVICE = WMService.getInstance();

    @Override
    public void execute() {
        System.out.print("\nShow (choose type of product):");
        TechProductType[] types = TechProductType.values();
        List<String> names = UtilEnumList.getTypesName(types);
        int userInput = UtilUser.inputData(types.length, names);
        switch (types[userInput]) {
            case PHONE -> show(PHONE_SERVICE);
            case MOUSE -> show(MOUSE_SERVICE);
            case WASHING_MACHINE -> show(WM_SERVICE);
        }
    }

    private void show(TechProductService<? extends TechProduct> productService) {
        if (productService.getAll().isEmpty()) {
            System.out.println("\nStore is empty");
        }
        else {
            System.out.println("\nStore: ");
            productService.getAll().forEach(System.out::println);
        }
    }
}
