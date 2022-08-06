package ua.com.alevel.hw2.controller;
import ua.com.alevel.hw2.commands.Command;
import ua.com.alevel.hw2.commands.Commands;
import ua.com.alevel.hw2.commands.UtilEnumList;
import ua.com.alevel.hw2.commands.UtilUser;

import java.util.List;

public final class Controller {
    private Controller() {}

    public static void run() {
        Commands[] commands = Commands.values();
        boolean exit;

        do {
            exit = chooseAction(commands);
        } while (!exit);
    }

    private static boolean chooseAction(Commands[] commands) {
        System.out.print("\nChoose action:");
        List<String> commandsList = UtilEnumList.getTypesName(commands);
        int choice = UtilUser.inputData(commands.length, commandsList);
        Command command = commands[choice].getCommand();
        if (command == null) {
            return true;
        }
        else {
            command.execute();
            return false;
        }
    }
}
