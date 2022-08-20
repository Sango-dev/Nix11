package ua.com.alevel.fileservice;

import ua.com.alevel.exception.IncorrectEnterString;
import ua.com.alevel.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public final class ReadCSV {

    private static final int SIZE = 7;
    private static Map<String, Integer> mapNames;

    private ReadCSV() {
    }

    public static List<Product> read() {

        String filename = "products.csv";
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filename);
        String line = null;
        List<Product> prodList = null;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            line = reader.readLine();
            boolean flag = getMapOfNames(line);
            if (!flag) {
                throw new IncorrectEnterString("Wrong headers!!!");
            }

            while ((line = reader.readLine()) != null) {
                Optional<Product> var = getProduct(line);
                if (var.isPresent()) {
                    if (prodList == null) {
                        prodList = new ArrayList<>();
                    }
                    prodList.add(var.get());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return prodList;
    }

    private static boolean getMapOfNames(String line) {
        mapNames = new HashMap<>();
        String[] lines = line.trim().split(",");
        if (lines.length == SIZE) {
            for (int i = 0; i < lines.length; i++) {
                mapNames.put(lines[i].trim(), i);
            }
            return true;
        }
        return false;
    }

    private static Optional<Product> getProduct(String line) throws IllegalArgumentException {
        String[] lines = line.trim().split(",");
        if (lines.length == SIZE) {
            Product product;
            String type = lines[mapNames.get("type")];
            if (type.equals("Telephone")) {
                String series = lines[mapNames.get("series")];
                Manufacturer model = Manufacturer.valueOf(lines[mapNames.get("model")].toUpperCase(Locale.ROOT));
                ScreenType screenType = ScreenType.valueOf(lines[mapNames.get("screen type")].toUpperCase(Locale.ROOT));
                double price;
                try {
                    price = Double.parseDouble(lines[mapNames.get("price")]);
                } catch (NumberFormatException nfe) {
                    price = 0.0;
                }
                return Optional.of(new Telephone(series, model, screenType, price));
            } else if (type.equals("Television")) {
                String series = lines[mapNames.get("series")];
                double diagonal;
                try {
                    diagonal = Double.parseDouble(lines[mapNames.get("diagonal")]);
                } catch (NumberFormatException nfe) {
                    diagonal = 0.0;
                }
                ScreenType screenType = ScreenType.valueOf(lines[mapNames.get("screen type")].toUpperCase(Locale.ROOT));
                Country country = Country.valueOf(lines[mapNames.get("country")].toUpperCase(Locale.ROOT));
                double price;
                try {
                    price = Double.parseDouble(lines[mapNames.get("price")]);
                } catch (NumberFormatException nfe) {
                    price = 0.0;
                }
                return Optional.of(new Television(series, diagonal, screenType, country, price));
            }
        }
        return Optional.empty();
    }
}
