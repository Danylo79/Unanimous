package dev.dankom.unanimous.group;

import dev.dankom.file.json.JsonFile;
import dev.dankom.file.json.JsonObjectBuilder;
import dev.dankom.file.type.Directory;
import dev.dankom.unanimous.group.profile.UIdentity;
import dev.dankom.unanimous.group.profile.UProfile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UGroup {
    private final Directory root;

    private final JsonFile groupJson;
    private final JsonFile profilesJson;
    private final JsonFile identitiesJson;

    private final List<UProfile> profiles = new ArrayList<>();
    private final List<UIdentity> identities = new ArrayList<>();

    public UGroup(String id, Directory groupDirectory) {
        this.root = new Directory(groupDirectory, id);

        this.groupJson = new JsonFile(root, "group", new JsonObjectBuilder()
                .addKeyValuePair("id", id)
                .build());
        this.profilesJson = new JsonFile(root, "profiles", new JsonObjectBuilder()
                .addKeyValuePair("parent", id)
                .addArray("profiles", new JSONArray())
                .build());
        this.identitiesJson = new JsonFile(root, "identities", new JsonObjectBuilder()
                .addKeyValuePair("parent", id)
                .addArray("identities", new JSONArray())
                .build());
    }

    public void addProfile(UProfile profile, UIdentity identity) {
        profiles.add(profile);
        identities.add(identity);
    }

    public UIdentity getIdentity(UProfile profile) {
        return getIdentity(profile.getID());
    }

    public UIdentity getIdentity(UUID id) {
        for (UIdentity identity : identities) {
            if (identity.getParent().getID().equals(id)) {
                return identity;
            }
        }
        return null;
    }

    public UProfile getProfile(UUID id) {
        for (UProfile profile : profiles) {
            if (profile.getID().equals(id)) {
                return profile;
            }
        }
        return null;
    }

    public void load() {
        for (Object o : (JSONArray) profilesJson.get().get("profiles")) {
            profiles.add(new UProfile(this, (JSONObject) o));
        }

        for (Object o : (JSONArray) identitiesJson.get().get("identities")) {
            identities.add(new UIdentity(this, (JSONObject) o));
        }
    }

    public void save() {
        JSONArray profiles = new JSONArray();
        for (UProfile profile : this.profiles) {
            profiles.add(profile.toJSON());
        }
        profilesJson.set("profiles", profiles);

        JSONArray identities = new JSONArray();
        for (UIdentity identity : this.identities) {
            identities.add(identity.toJSON());
        }
        identitiesJson.set("identities", identities);
    }
}
