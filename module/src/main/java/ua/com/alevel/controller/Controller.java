package ua.com.alevel.controller;

import ua.com.alevel.commands.Command;
import ua.com.alevel.commands.Commands;
import ua.com.alevel.commands.UtilEnumList;
import ua.com.alevel.commands.UtilUser;

import java.util.List;

public final class Controller {
    private Controller() {
    }

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
        int choice = UtilUser.inputData(commandsList, commands.length);
        Command command = commands[choice].getCommand();
        if (command == null) {
            return true;
        } else {
            try {
                command.execute();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return false;
        }
    }
}
