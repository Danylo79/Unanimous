package dev.dankom.unanimous.config.wrapper;

import org.json.simple.JSONObject;

public class Role {
    private final String name;
    private final boolean isAdministrator;

    public Role(JSONObject json) {
        this.name = (String) json.get("name");
        this.isAdministrator = (boolean) json.get("isAdministrator");
    }

    public String getName() {
        return name;
    }

    public boolean isAdministrator() {
        return isAdministrator;
    }
}
