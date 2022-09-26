package ua.com.alevel.hw2.commands;

import ua.com.alevel.hw2.service.invoiceservice.InvoiceService;

import java.util.Map;

public class GroupBySum implements Command {
    private static final InvoiceService INVOICE_SERVICE = InvoiceService.getInstance();

    @Override
    public void execute() {
        Map<Double, Long> groups = INVOICE_SERVICE.groupingBySum();
        System.out.println("SUM | Amount");
        groups.forEach((key, value) -> System.out.println(key + " : " + value));
    }
}
