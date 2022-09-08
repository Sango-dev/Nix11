package ua.com.alevel.hw2.parsers;

import ua.com.alevel.hw2.model.product.Manufacturer;
import ua.com.alevel.hw2.model.product.OperatingSystem;
import ua.com.alevel.hw2.model.phonebox.PhoneBox;
import ua.com.alevel.hw2.readfromfile.ReadFile;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class XMLParser {

    private XMLParser() {
    }

    public static Optional<PhoneBox> createPhoneBoxFromXmlFile() {
        String fileName = "phoneXML.xml";
        List<String> linesFromXmlFile = ReadFile.readFromFile(fileName);
        if (linesFromXmlFile.isEmpty()) {
            System.out.println("Object is not created!!!");
        }

        Class phoneBoxClass = null;
        Class operatingSystemClass = null;
        try {
            phoneBoxClass = Class.forName(PhoneBox.class.getName());
            operatingSystemClass = Class.forName(OperatingSystem.class.getName());
        } catch (ClassNotFoundException e) {
            return Optional.empty();
        }

        PhoneBox phoneBox = null;
        OperatingSystem operatingSystem;
        try {
            phoneBox = (PhoneBox) phoneBoxClass.newInstance();
            operatingSystem = (OperatingSystem) operatingSystemClass.newInstance();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String reggexField = "<(.+)?>.+<.+>";
        String reggexValue = "<.+>(.+)?<.+>";
        String reggexCurrency = ".+=\"(.+)?\"";
        Pattern compileFieldPattern = Pattern.compile(reggexField);
        Pattern compileValuePattern = Pattern.compile(reggexValue);
        Pattern compileCurrencyPattern = Pattern.compile(reggexCurrency);

        for (String line : linesFromXmlFile) {
            Matcher matcherFieldName = compileFieldPattern.matcher(line);
            if (matcherFieldName.find()) {
                String fieldName = matcherFieldName.group(1);
                Field fieldPhoneBox = null;
                Field fieldOperatingSystem = null;
                Matcher matcherValue = compileValuePattern.matcher(line);
                try {
                    fieldPhoneBox = PhoneBox.class.getDeclaredField(fieldName);
                } catch (NoSuchFieldException e) {
                    if (fieldName.contains("price")) {
                        String currency = "";
                        Matcher matcherCurrency = compileCurrencyPattern.matcher(fieldName);
                        if (matcherCurrency.find()) {
                            currency = matcherCurrency.group(1);
                        }
                        double price = 0.0;
                        if (matcherValue.find()) {
                            price = Double.parseDouble(matcherValue.group(1));
                        }

                        try {
                            fieldPhoneBox = PhoneBox.class.getDeclaredField("price");
                            fieldPhoneBox.setAccessible(true);
                            fieldPhoneBox.set(phoneBox, price);
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            return Optional.empty();
                        }

                        try {
                            fieldPhoneBox = PhoneBox.class.getDeclaredField("currency");
                            fieldPhoneBox.setAccessible(true);
                            fieldPhoneBox.set(phoneBox, currency);
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            return Optional.empty();
                        }

                    } else {
                        String rez = "";
                        if (matcherValue.find()) {
                            rez = matcherValue.group(1);
                        }

                        try {
                            fieldOperatingSystem = OperatingSystem.class.getDeclaredField(fieldName);
                            fieldOperatingSystem.setAccessible(true);
                            fieldOperatingSystem.set(operatingSystem, switch (fieldName) {
                                case "version" -> Integer.parseInt(rez);
                                default -> rez;
                            });
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            return Optional.empty();
                        }
                    }
                    continue;
                }
                if (matcherValue.find()) {
                    String value = matcherValue.group(1);
                    fieldPhoneBox.setAccessible(true);
                    try {
                        fieldPhoneBox.set(phoneBox, switch (fieldName) {
                            case "count" -> Integer.parseInt(value);
                            case "manufacturer" -> Manufacturer.valueOf(value);
                            case "created" -> formatter.parse(value);
                            default -> value;
                        });
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        Optional.empty();
                    }
                }
            }
        }

        phoneBox.setOperatingSystem(operatingSystem);
        return Optional.of(phoneBox);
    }
}
