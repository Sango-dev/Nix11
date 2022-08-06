package ua.com.alevel.hw2.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public final class UtilUser {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    private UtilUser() {}

    public static int inputData(int len, List<String> names) {
        int ind = -1;
        while(ind == -1) {
            ind = inputData(names, len);
        }
        return ind;
    }

    private static int inputData(List<String> names, int len) {
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
        }
        catch (IOException | NumberFormatException e) {
            System.out.println("Input is not correct");
        }
        return -1;
    }
}
