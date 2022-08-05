package ua.com.alevel.hw2.commands;

import lombok.Getter;
import lombok.Setter;

public enum Commands {
    CREATE("Create", new Create()),
    UPDATE("Update", new Update()),
    DELETE("Delete", new Delete()),
    PRINT("Print", new Print()),
    WORK_WITH_TREE("Work with Tree", new Tree()),
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
