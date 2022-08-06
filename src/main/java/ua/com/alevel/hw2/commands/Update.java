package ua.com.alevel.hw2.commands;

import ua.com.alevel.hw2.model.*;
import ua.com.alevel.hw2.repository.MouseRepository;
import ua.com.alevel.hw2.repository.PhoneRepository;
import ua.com.alevel.hw2.repository.WMRepository;
import ua.com.alevel.hw2.service.MouseService;
import ua.com.alevel.hw2.service.PhoneService;
import ua.com.alevel.hw2.service.TechProductService;
import ua.com.alevel.hw2.service.WMService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

public class Update implements Command{

    private static final TechProductService<Phone> PHONE_SERVICE = PhoneService.getInstance();
    private static final TechProductService<Mouse> MOUSE_SERVICE = MouseService.getInstance();
    private static final TechProductService<WashingMachine> WM_SERVICE = WMService.getInstance();
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void execute() {
        System.out.print("\nUpdate (choose type of product):");
        TechProductType[] types = TechProductType.values();
        List<String> names = UtilEnumList.getTypesName(types);
        int userInput = UtilUser.inputData(types.length, names);
        switch (types[userInput]) {
            case PHONE -> update(PHONE_SERVICE);
            case MOUSE -> update(MOUSE_SERVICE);
            case WASHING_MACHINE -> update(WM_SERVICE);
        }
    }

    private void update(TechProductService<? extends TechProduct> productService) {
        if (productService.getAll().isEmpty()) {
            System.out.println("\nStore is empty");
        }
        else {
            boolean flag = false;
            while (!flag) {
                System.out.print("\nEnter id (to update product by id) -> ");
                try {
                    String id = READER.readLine();
                    Optional<? extends TechProduct> product = productService.findById(id);
                    if (product.isPresent()) {
                        productService.update(updateCountPrice(product.get()));
                        flag = true;
                    }
                }
                catch (Exception e) {
                    System.out.println("Input is incorrect!");
                }
            }
        }
    }

    private TechProduct updateCountPrice(TechProduct product) {
        boolean flag = false;
        while (!flag) {
            try {

                System.out.print("Enter price -> ");
                double price = Double.parseDouble(READER.readLine());
                System.out.println();
                if (price <= 0.0) {
                    throw new RuntimeException();
                }

                System.out.print("Enter count -> ");
                int count = Integer.parseInt(READER.readLine());
                System.out.println();
                if (count <= 0) {
                    throw new RuntimeException();
                }

                product.setPrice(price);
                product.setCount(count);
                flag = true;
            }
            catch (Exception e) {
                System.out.println("Input is incorrect!");
            }
        }
        return product;
    }

}
