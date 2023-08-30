package jskang.springboot.multitenant.database;

public enum DatabaseEnum {

    companyA("company-a"),
    companyB("company-b");

    private final String name;

    DatabaseEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
