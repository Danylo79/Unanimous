package dev.dankom.unanimous.file;

import dev.dankom.file.json.JsonFile;
import dev.dankom.unanimous.profile.Profile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.UUID;

public class ProfileManager {
    private final JsonFile profiles;

    public ProfileManager(JsonFile profiles) {
        this.profiles = profiles;
    }

    public void addProfile(Profile profile) {
        if (getProfile(profile.getID()) == null) {
            profiles.addToArray("profiles", profile.toJSON());
        }
    }

    public Profile getProfile(String name) {
        for (Object o : (JSONArray) profiles.get().get("profiles")) {
            if (((JSONObject) o).get("name").equals(name)) {
                return new Profile((JSONObject) o);
            }
        }
        return null;
    }

    public Profile getProfile(UUID id) {
        for (Object o : (JSONArray) profiles.get().get("profiles")) {
            if (UUID.fromString((String) ((JSONObject) o).get("id")).equals(id)) {
                return new Profile((JSONObject) o);
            }
        }
        return null;
    }
}
