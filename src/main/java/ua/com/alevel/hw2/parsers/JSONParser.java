package ua.com.alevel.hw2.parsers;

import ua.com.alevel.hw2.model.product.Manufacturer;
import ua.com.alevel.hw2.model.product.OperatingSystem;
import ua.com.alevel.hw2.model.phonebox.PhoneBox;
import ua.com.alevel.hw2.readfromfile.ReadFile;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JSONParser {

    private JSONParser() {

    }

    public static Optional<PhoneBox> createPhoneBoxFromJsonFile() {
        String fileName = "phoneJSON.json";
        String regex = "\"(.+)?\":.\"(.+)?\",*";
        Pattern compileRegex = Pattern.compile(regex);
        Map<String, String> mapOfPhoneBoxFields = new HashMap<>();
        Map<String, String> mapOfOperatingSystemFields = new HashMap<>();
        List<String> linesFromJsonFile = ReadFile.readFromFile(fileName);
        if (linesFromJsonFile.isEmpty()) {
            System.out.println("Object is not created!!!");
        }

        Arrays.stream(PhoneBox.class
                        .getDeclaredFields())
                .forEach(field -> mapOfPhoneBoxFields.put(field.getName(), "true"));

        Arrays.stream(OperatingSystem.class
                        .getDeclaredFields())
                .forEach(field -> mapOfOperatingSystemFields.put(field.getName(), "true"));

        for (String line : linesFromJsonFile) {
            Matcher matcher = compileRegex.matcher(line);
            if (matcher.find()) {
                String fieldName = matcher.group(1);
                if (mapOfPhoneBoxFields.containsKey(fieldName)) {
                    String value = matcher.group(2);
                    mapOfPhoneBoxFields.put(fieldName, value);
                } else if (mapOfOperatingSystemFields.containsKey(fieldName)) {
                    String value = matcher.group(2);
                    mapOfOperatingSystemFields.put(fieldName, value);
                } else {
                    throw new IllegalArgumentException("This fieldName \"" + fieldName + "\" is not validate!!!");
                }
            }
        }

        try {
            return Optional.of(new PhoneBox(
                    mapOfPhoneBoxFields.get("title"),
                    mapOfPhoneBoxFields.get("model"),
                    Double.parseDouble(mapOfPhoneBoxFields.get("price")),
                    mapOfPhoneBoxFields.get("currency"),
                    Manufacturer.valueOf(mapOfPhoneBoxFields.get("manufacturer")),
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(mapOfPhoneBoxFields.get("created")),
                    Integer.parseInt(mapOfPhoneBoxFields.get("count")),
                    new OperatingSystem(
                            mapOfOperatingSystemFields.get("designation"),
                            Integer.parseInt(mapOfOperatingSystemFields.get("version"))
                    )
            ));
        } catch (Exception e) {
            System.out.println("Here something went wrong: " + e.getMessage());
            return Optional.empty();
        }

    }

}
