package ua.com.alevel.hw2.commands;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum Commands {
    CREATE("Create product", new Create()),
    UPDATE("Update product", new Update()),
    DELETE("Delete product", new Delete()),
    PRINT("Print products", new Print()),
    CREATE_INVOICE("Create invoice", new MakeInvoice()),
    INVOICE_AMOUNT("Count invoice amount", new InvoiceAmount()),
    INVOICE_SUM_MORE("Get invoices (sum > ?)", new InvoiceSumMore()),
    UPDATE_INVOICE_DATE("Update date by id", new UpdateInvoiceDate()),
    GROUP_BY_SUM("Group by sum", new GroupBySum()),
    WORK_WITH_TREE("Work with Tree", new Tree()),
    EXIT("Exit", null);

    private final String name;
    private final Command command;

    Commands(String name, Command command) {
        this.name = name;
        this.command = command;
    }

    public boolean execute() {
        if (command == null) {
            return false;
        }
        command.execute();
        return true;
    }
}
