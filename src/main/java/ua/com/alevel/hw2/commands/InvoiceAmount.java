package ua.com.alevel.hw2.commands;

import ua.com.alevel.hw2.service.invoiceservice.InvoiceService;

public class InvoiceAmount implements Command {
    private static final InvoiceService INVOICE_SERVICE = InvoiceService.getInstance();

    @Override
    public void execute() {
        System.out.println("\nInvoice amount: " + INVOICE_SERVICE.getInvoiceAmount());
    }
}
