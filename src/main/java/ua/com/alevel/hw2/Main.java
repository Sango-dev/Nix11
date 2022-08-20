package ua.com.alevel.hw2;

import lombok.SneakyThrows;
import ua.com.alevel.hw2.model.PhoneBox;
import ua.com.alevel.hw2.parsers.JSONParser;
import ua.com.alevel.hw2.parsers.XMLParser;

import java.io.InputStream;
import java.text.ParseException;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        Optional<PhoneBox> optionalFromXml = XMLParser.createPhoneBoxFromXmlFile();
        Optional<PhoneBox> optionalFromJson = JSONParser.createPhoneBoxFromJsonFile();

        if (optionalFromXml.isPresent()) {
            System.out.println("From xml file: " + optionalFromXml.get());
        }

        if (optionalFromJson.isPresent()) {
            System.out.println("From json file: " + optionalFromJson.get());
        }

    }
}
