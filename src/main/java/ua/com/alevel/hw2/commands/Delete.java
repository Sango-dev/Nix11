package ua.com.alevel.hw2.commands;

import ua.com.alevel.hw2.model.*;
import ua.com.alevel.hw2.service.MouseService;
import ua.com.alevel.hw2.service.PhoneService;
import ua.com.alevel.hw2.service.TechProductService;
import ua.com.alevel.hw2.service.WMService;
import ua.com.alevel.hw2.util.UtilInputUser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Delete implements Command{

    private static final TechProductService<Phone> PHONE_SERVICE = PhoneService.getInstance();
    private static final TechProductService<Mouse> MOUSE_SERVICE = MouseService.getInstance();
    private static final TechProductService<WashingMachine> WM_SERVICE = WMService.getInstance();
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void execute() {
        System.out.print("\nRemove (choose type of product:");
        TechProductType[] types = TechProductType.values();
        List<String> names = Arrays.stream(types)
                .map(Enum::name)
                .collect(Collectors.toList());

        final int userInput = UtilInputUser.getUserInput(names);
        switch (types[userInput]) {
            case PHONE -> remove(PHONE_SERVICE);
            case MOUSE -> remove(MOUSE_SERVICE);
            case WASHING_MACHINE -> remove(WM_SERVICE);
        }
    }

    private void remove(TechProductService<? extends TechProduct> productService) {
        if (productService.getAll().isEmpty()) {
            System.out.println("\nStore is empty");
        }
        else {
            boolean flag = false;
            while (!flag) {
                System.out.print("\nEnter id (to remove product by id) -> ");
                try {
                    String id = READER.readLine();
                    if (productService.findById(id).isPresent()) {
                        productService.delete(id);
                        flag = true;
                    }
                }
                catch (Exception e) {
                    System.out.println("Input is incorrect!");
                }
            }
        }
    }
}

