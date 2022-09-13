package ua.com.alevel.hw2.commands;

import ua.com.alevel.hw2.model.product.*;
import ua.com.alevel.hw2.service.productservice.MouseService;
import ua.com.alevel.hw2.service.productservice.PhoneService;
import ua.com.alevel.hw2.service.productservice.TechProductService;
import ua.com.alevel.hw2.service.productservice.WMService;
import ua.com.alevel.hw2.util.UtilInputUser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Print implements Command{

    private static final TechProductService<Phone> PHONE_SERVICE = PhoneService.getInstance();
    private static final TechProductService<Mouse> MOUSE_SERVICE = MouseService.getInstance();
    private static final TechProductService<WashingMachine> WM_SERVICE = WMService.getInstance();

    @Override
    public void execute() {
        System.out.print("\nShow (choose type of product):");
        TechProductType[] types = TechProductType.values();
        List<String> names = Arrays.stream(types)
                .map(Enum::name)
                .collect(Collectors.toList());

        final int userInput = UtilInputUser.getUserInput(names);
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
