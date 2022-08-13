package ua.com.alevel.commands;

import java.util.ArrayList;
import java.util.List;

public final class UtilEnumList {

    private UtilEnumList(){}

    public static List<String> getTypesName(Commands[] commands) {
        List<String> names = new ArrayList<>(commands.length);
        for (Commands type : commands) {
            names.add(type.name());
        }
        return names;
    }

}