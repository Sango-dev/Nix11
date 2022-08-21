package ua.com.alevel.hw2.readfromfile;

import lombok.SneakyThrows;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public final class ReadFile {

    private ReadFile() {
    }

    @SneakyThrows
    public static List<String> readFromFile(String filename) {
        List<String> lines = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filename);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.trim());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
        return lines;
    }
}