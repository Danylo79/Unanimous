package dev.dankom.unanimous.group.profile;

import dev.dankom.file.json.JsonObjectBuilder;
import dev.dankom.security.hash.hashers.Sha256;
import dev.dankom.unanimous.group.UGroup;
import org.json.simple.JSONObject;

import java.util.UUID;

public class UIdentity {
    private final UGroup parent;
    private UProfile profile;
    private String username;
    private String pin;

    public UIdentity(UGroup parent, JSONObject json) {
        this(parent, parent.getProfile(UUID.fromString((String) json.get("parent"))), (String) json.get("username"), (String) json.get("pin"));
    }

    public UIdentity(UGroup parent, UProfile profile, String username, String pin) {
        this.parent = parent;
        this.profile = profile;
        this.username = username;
        this.pin = new Sha256().hash(pin);
    }

    public UProfile getParent() {
        return profile;
    }

    public void setParent(UProfile profile) {
        this.profile = profile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public UProfile getProfile() {
        return parent.getProfile(profile.getID());
    }

    public JSONObject toJSON() {
        return new JsonObjectBuilder()
                .addKeyValuePair("parent", profile.getID().toString())
                .addKeyValuePair("username", username)
                .addKeyValuePair("pin", pin)
                .build();
    }
}
