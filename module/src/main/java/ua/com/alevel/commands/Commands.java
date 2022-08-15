package ua.com.alevel.commands;

public enum Commands {
    CREATE_ORDERS("CreateOrders orders (N = 15)", new CreateOrders()),
    ANALYZE("Do analytical actions", new Analyze()),
    EXIT("Exit", null);

    private final String name;
    private final Command command;

    Commands(String name, Command command) {
        this.name = name;
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public Command getCommand() {
        return command;
    }
}
