package ua.com.alevel.hw2.model;

public class OperatingSystem {

    private final String designation;
    private int version;

    public OperatingSystem() {
        designation = "";
        version = 0;
    }

    public OperatingSystem(String designation, int version) {
        this.designation = designation;
        this.version = version;
    }

    @Override
    public String toString() {
        return "OperatingSystem{" +
                "designation='" + designation + '\'' +
                ", version=" + version +
                '}';
    }
}
