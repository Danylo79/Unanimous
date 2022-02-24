package dev.dankom.unanimous.query.rule;

public enum RuleType {
    FIELD_INCLUSION("FIELD"),
    FIELD_EXCLUSION("FIELD"),
    METHOD_INCLUSION("METHOD"),
    METHOD_EXCLUSION("METHOD");

    private String group;

    RuleType(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public static RuleType get(String name) {
        for (RuleType type : values()) {
            if (type.name().equalsIgnoreCase(name)) return type;
        }
        return null;
    }
}
