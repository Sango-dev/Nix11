package ua.com.alevel.hw2.commands;

import ua.com.alevel.hw2.model.invoice.Invoice;
import ua.com.alevel.hw2.service.invoiceservice.InvoiceService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class InvoiceSumMore implements Command {
    private static final InvoiceService INVOICE_SERVICE = InvoiceService.getInstance();
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void execute() {
        try {
            System.out.print("\nEnter price -> ");
            double sum = Double.parseDouble(READER.readLine());

            List<Invoice> invoices = INVOICE_SERVICE.getInvoicesWhereSumMoreThanPrice(sum);
            if (invoices.isEmpty()) {
                System.out.println("Invoices not found!");
            } else {
                invoices.forEach(invoice -> {
                    System.out.println("> id: " + invoice.getId() + ", sum: " + invoice.getSum() + ", date: " + invoice.getDate());
                    invoice.getProducts().forEach(product -> System.out.println(">>> " + product));
                });
            }

        } catch (Exception e) {
            System.out.println("Incorrect input!");
        }
    }
}
