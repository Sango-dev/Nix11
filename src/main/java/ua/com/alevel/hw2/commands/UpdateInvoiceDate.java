package ua.com.alevel.hw2.commands;

import ua.com.alevel.hw2.service.invoiceservice.InvoiceService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateInvoiceDate implements Command {
    private static final InvoiceService INVOICE_SERVICE = InvoiceService.getInstance();
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void execute() {
        try {
            System.out.print("\nEnter id -> ");
            String id = READER.readLine();

            System.out.print("yyyy-MM-dd -> ");
            String date = READER.readLine();
            System.out.println();
            System.out.print("HH:mm:ss -> ");
            String time = READER.readLine();
            System.out.println();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date updateDate = formatter.parse(date + "T" + time + "Z");

            if (INVOICE_SERVICE.findById(id).isPresent()) {
                INVOICE_SERVICE.updateDate(id, updateDate);
            } else {
                System.out.println("Id is not exist!!!");
            }
        } catch (Exception e) {
            System.out.println("Incorrect input!");
        }
    }
}
