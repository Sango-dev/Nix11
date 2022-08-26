package ua.com.alevel.hw2.commands;

import ua.com.alevel.hw2.factory.ProductFactory;
import ua.com.alevel.hw2.model.Mouse;
import ua.com.alevel.hw2.model.Phone;
import ua.com.alevel.hw2.model.TechProductType;
import ua.com.alevel.hw2.model.WashingMachine;
import ua.com.alevel.hw2.service.MouseService;
import ua.com.alevel.hw2.service.PhoneService;
import ua.com.alevel.hw2.service.TechProductService;
import ua.com.alevel.hw2.service.WMService;
import ua.com.alevel.hw2.util.UtilInputUser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Create implements Command {
    private static final TechProductService<Phone> PHONE_SERVICE = PhoneService.getInstance();
    private static final TechProductService<Mouse> MOUSE_SERVICE = MouseService.getInstance();
    private static final TechProductService<WashingMachine> WM_SERVICE = WMService.getInstance();

    @Override
    public void execute() {
        System.out.print("\nCreate and store (choose type of product):");
        final TechProductType[] types = TechProductType.values();
        List<String> names = Arrays.stream(types)
                .map(Enum::name)
                .collect(Collectors.toList());

        final int userInput = UtilInputUser.getUserInput(names);
        switch (types[userInput]) {
            case PHONE -> PHONE_SERVICE.save((Phone) ProductFactory.createProduct(TechProductType.PHONE));
            case MOUSE -> MOUSE_SERVICE.save((Mouse) ProductFactory.createProduct(TechProductType.MOUSE));
            case WASHING_MACHINE -> WM_SERVICE.save((WashingMachine) ProductFactory.createProduct(TechProductType.WASHING_MACHINE));
        }
    }
}
