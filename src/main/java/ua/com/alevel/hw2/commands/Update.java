package ua.com.alevel.hw2.commands;

import ua.com.alevel.hw2.model.product.*;
import ua.com.alevel.hw2.service.productservice.MouseService;
import ua.com.alevel.hw2.service.productservice.PhoneService;
import ua.com.alevel.hw2.service.productservice.TechProductService;
import ua.com.alevel.hw2.service.productservice.WMService;
import ua.com.alevel.hw2.util.UtilInputUser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Update implements Command {

    private static final TechProductService<Phone> PHONE_SERVICE = PhoneService.getInstance();
    private static final TechProductService<Mouse> MOUSE_SERVICE = MouseService.getInstance();
    private static final TechProductService<WashingMachine> WM_SERVICE = WMService.getInstance();
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void execute() {
        System.out.print("\nUpdate (choose type of product):");
        TechProductType[] types = TechProductType.values();
        List<String> names = Arrays.stream(types)
                .map(Enum::name)
                .collect(Collectors.toList());

        final int userInput = UtilInputUser.getUserInput(names);
        switch (types[userInput]) {
            case PHONE -> update(PHONE_SERVICE);
            case MOUSE -> update(MOUSE_SERVICE);
            case WASHING_MACHINE -> update(WM_SERVICE);
        }
    }

    private void update(TechProductService<? extends TechProduct> productService) {
        if (productService.getAll().isEmpty()) {
            System.out.println("\nStore is empty");
        } else {
            boolean flag = false;
            while (!flag) {
                System.out.print("\nEnter id (to update product by id) -> ");
                try {
                    String id = READER.readLine();
                    Optional<? extends TechProduct> product = productService.findById(id);
                    if (product.isPresent()) {
                        productService.update(updateProduct(product.get()));
                        flag = true;
                    }
                } catch (Exception e) {
                    System.out.println("Input is incorrect!");
                }
            }
        }
    }

    private TechProduct updateProduct(TechProduct product) {
        while (true) {
            try {

                System.out.print("Enter model -> ");
                String model = READER.readLine();
                System.out.println();

                System.out.print("Enter manufacturer -> ");
                Manufacturer manufacturer = Manufacturer.valueOf(READER.readLine().toUpperCase());
                System.out.println();

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

                if (product.getClass().equals(Phone.class)) {
                    System.out.print("Enter core numbers -> ");
                    int coreNumbers = Integer.parseInt(READER.readLine());
                    System.out.println();
                    if (coreNumbers <= 0) {
                        throw new RuntimeException();
                    }

                    System.out.print("Enter battery power -> ");
                    int batteryPower = Integer.parseInt(READER.readLine());
                    System.out.println();
                    if (batteryPower <= 0) {
                        throw new RuntimeException();
                    }

                    return new Phone(
                            product.getId(),
                            model,
                            manufacturer,
                            count,
                            price,
                            coreNumbers,
                            batteryPower
                    );

                } else if (product.getClass().equals(Mouse.class)) {
                    System.out.print("Enter connection type -> ");
                    ConnectionType connectionType = ConnectionType.valueOf(READER.readLine().toUpperCase());
                    System.out.println();

                    System.out.print("Enter dpi amount -> ");
                    int dpiAmount = Integer.parseInt(READER.readLine());
                    System.out.println();
                    if (dpiAmount <= 0) {
                        throw new RuntimeException();
                    }

                    return new Mouse(
                            product.getId(),
                            model,
                            manufacturer,
                            count,
                            price,
                            connectionType,
                            dpiAmount);

                } else if (product.getClass().equals(WashingMachine.class)) {
                    System.out.print("Enter turns number -> ");
                    int turnsNumber = Integer.parseInt(READER.readLine());
                    System.out.println();
                    if (turnsNumber <= 0) {
                        throw new RuntimeException();
                    }
                    return new WashingMachine(
                            product.getId(),
                            model,
                            manufacturer,
                            count,
                            price,
                            turnsNumber);
                }
            } catch (Exception e) {
                System.out.println("Input is incorrect!");
            }
        }
    }

}
