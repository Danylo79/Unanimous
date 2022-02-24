package dev.dankom.unanimous.group.profile;

import dev.dankom.file.json.JsonObjectBuilder;
import dev.dankom.security.hash.hashers.Sha256;
import dev.dankom.unanimous.construct.IIdentity;
import org.json.simple.JSONObject;

import java.util.UUID;

public class UIdentity implements IIdentity {
    private UUID id;
    private String username;
    private String pin;

    public UIdentity(JSONObject json) {
        this.id = UUID.fromString((String) json.get("id"));
        this.username = (String) json.get("username");
        this.pin = (String) json.get("pin");
    }

    public UIdentity(UUID id, String username, String pin) {
        this.id = id;
        this.username = username;
        this.pin = new Sha256().hash(pin);
    }

    public UUID getID() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public boolean doesPinMatch(String pin) {
        String source = this.pin;
        String target = new Sha256().hash(pin);
        return source.equals(target);
    }

    @Override
    public String toString() {
        return "UIdentity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", pin='" + pin + '\'' +
                '}';
    }

    public JSONObject toJSON() {
        return new JsonObjectBuilder()
                .addKeyValuePair("id", id.toString())
                .addKeyValuePair("username", username)
                .addKeyValuePair("pin", pin)
                .build();
    }
}
