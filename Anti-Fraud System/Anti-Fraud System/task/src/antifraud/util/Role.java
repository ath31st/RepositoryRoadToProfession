package antifraud.util;

public enum Role {
    ADMINISTRATOR("ROLE_ADMINISTRATOR"),
    MERCHANT("ROLE_MERCHANT"),
    SUPPORT("ROLE_SUPPORT");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
