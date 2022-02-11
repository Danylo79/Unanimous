package dev.dankom.unanimous.group.type;

public enum Role {
    TEACHER,
    COMMITTEE,
    STUDENT;

    public static Role get(String role) {
        for (Role r : values()) {
            if (r.name().equals(role)) {
                return r;
            }
        }
        return null;
    }
}
