package ua.com.alevel.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class UtilUser {

    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static double inputLimit() {
        boolean flag = false;
        double limit = 0.0;
        while (!flag) {
            try {
                System.out.print("\n Please type a limit: ");
                limit = Double.parseDouble(READER.readLine());
            } catch (Exception e) {
                continue;
            }
            flag = true;
        }
        return limit;
    }

    public static int inputData(List<String> names, int len) {
        try {
            System.out.println("\nEnter some number (0->" + (len - 1) + ")");
            for (int i = 0; i < len; i++) {
                System.out.printf("%d) %s%n", i, names.get(i));
            }

            System.out.println();
            System.out.print("-> ");
            int input = Integer.parseInt(READER.readLine());

            if (input >= 0 && input < len) {
                return input;
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Input is not correct");
        }
        return -1;
    }
}
