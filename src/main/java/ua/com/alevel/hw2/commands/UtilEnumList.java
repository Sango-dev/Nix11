package ua.com.alevel.hw2.commands;

import ua.com.alevel.hw2.model.TechProductType;

import java.util.ArrayList;
import java.util.List;

public final class UtilEnumList {

    private UtilEnumList(){}

    public static List<String> getTypesName(TechProductType[] types) {
        List<String> names= new ArrayList<>(types.length);
        for (TechProductType type : types) {
            names.add(type.name());
        }
        return names;
    }

    public static List<String> getTypesName(Commands[] commands) {
        List<String> names = new ArrayList<>(commands.length);
        for (Commands type : commands) {
            names.add(type.name());
        }
        return names;
    }

}
