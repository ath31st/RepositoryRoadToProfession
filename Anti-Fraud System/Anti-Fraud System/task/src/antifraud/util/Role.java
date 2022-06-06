package antifraud.util;

public enum Role {
    ADMIN("ROLE_ADMINISTRATOR"),
    USER("ROLE_USER");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
