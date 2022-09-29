package ua.com.alevel.hw2.commands;

import lombok.SneakyThrows;
import ua.com.alevel.hw2.model.product.TechProduct;
import ua.com.alevel.hw2.service.invoiceservice.InvoiceService;
import ua.com.alevel.hw2.service.productservice.MouseService;
import ua.com.alevel.hw2.service.productservice.PhoneService;
import ua.com.alevel.hw2.service.productservice.WMService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

public class MakeInvoice implements Command {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final PhoneService PHONE_SERVICE = PhoneService.getInstance();
    private static final MouseService MOUSE_SERVICE = MouseService.getInstance();
    private static final WMService WASHING_MACHINE_SERVICE = WMService.getInstance();
    private static final InvoiceService INVOICE_SERVICE = InvoiceService.getInstance();

    @Override
    public void execute() {
        System.out.println("Creating invoice (enter id to add this product to invoice):");
        Map<String, Boolean> added = new HashMap<>();
        List<TechProduct> productList = new ArrayList<>();
        boolean flag = false;
        while (!flag) {
            try {
                System.out.print("\nEnter id -> ");
                String id = READER.readLine();
                if (added.containsKey(id)) {
                    System.out.println("This product is already added!");
                    flag = doExit(productList);
                    continue;
                }

                boolean isNull = PHONE_SERVICE.checkNullForeignInvoiceID(id) || MOUSE_SERVICE.checkNullForeignInvoiceID(id) || WASHING_MACHINE_SERVICE.checkNullForeignInvoiceID(id);
                if (isNull == true) {
                    PHONE_SERVICE.findById(id).ifPresent(product -> productList.add(product));
                    MOUSE_SERVICE.findById(id).ifPresent(product -> productList.add(product));
                    WASHING_MACHINE_SERVICE.findById(id).ifPresent(product -> productList.add(product));
                    added.put(id, true);
                } else {
                    System.out.println("This id does not exist or foreign key invoice_id is not null!");
                    flag = doExit(productList);
                    continue;
                }

                flag = doExit(productList);
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        }
    }

    @SneakyThrows
    private boolean doExit(List<TechProduct> productList) {
        System.out.print("Do u want to leave ? y/ or type another string: ");
        String eliminate = READER.readLine();
        if (eliminate.equals("y")) {
            if (!productList.isEmpty()) {
                INVOICE_SERVICE.save(INVOICE_SERVICE.createInvoice(productList));
            }
            return true;
        }

        return false;
    }
}
