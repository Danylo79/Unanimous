package dev.dankom.unanimous.group;

import dev.dankom.file.json.JsonFile;
import dev.dankom.file.json.JsonObjectBuilder;
import dev.dankom.file.type.Directory;
import dev.dankom.unanimous.group.profile.UIdentity;
import dev.dankom.unanimous.group.profile.UProfile;
import dev.dankom.unanimous.group.transaction.UTransaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UGroup {
    private final Directory root;

    private final JsonFile groupJson;
    private final JsonFile profilesJson;
    private final JsonFile identitiesJson;
    private final JsonFile ledgerJson;

    private final List<UProfile> profiles = new ArrayList<>();
    private final List<UIdentity> identities = new ArrayList<>();
    private final List<UTransaction> transactions = new ArrayList<>();

    public UGroup(File groupJson) {
        this(groupJson.getParentFile().getName(), new Directory(groupJson.getParentFile().getParentFile().getAbsolutePath()));
    }

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
        this.ledgerJson = new JsonFile(root, "ledger", new JsonObjectBuilder()
                .addKeyValuePair("parent", id)
                .addArray("transactions", new JSONArray())
                .build());
    }

    public void addProfile(UProfile profile) {
        profiles.add(profile);
    }

    public void addIdentity(UIdentity identity) {
        identities.add(identity);
    }

    public void addTransaction(UTransaction transaction) {
        transactions.add(transaction);
    }

    public UIdentity getIdentity(UProfile profile) {
        return getIdentity(profile.getID());
    }

    public UIdentity getIdentity(UUID id) {
        for (UIdentity identity : identities) {
            if (identity.getID().equals(id)) {
                return identity;
            }
        }
        return null;
    }

    public List<UIdentity> getIdentities(UProfile profile) {
        List<UIdentity> out = new ArrayList<>();
        for (String identity : profile.getIdentities()) {
            out.add(getIdentity(UUID.fromString(identity)));
        }
        return out;
    }

    public UProfile getProfile(UUID id) {
        for (UProfile profile : profiles) {
            if (profile.getID().equals(id)) {
                return profile;
            }
        }
        return null;
    }

    public List<UProfile> getProfiles() {
        return profiles;
    }

    public List<UIdentity> getIdentities() {
        return identities;
    }

    public List<UTransaction> getTransactions() {
        return transactions;
    }

    public String getID() {
        return (String) groupJson.get().get("id");
    }

    public void load() {
        for (Object o : (JSONArray) profilesJson.get().get("profiles")) {
            profiles.add(new UProfile(this, (JSONObject) o));
        }

        for (Object o : (JSONArray) identitiesJson.get().get("identities")) {
            identities.add(new UIdentity((JSONObject) o));
        }

        for (Object o : (JSONArray) ledgerJson.get().get("transactions")) {
            transactions.add(new UTransaction((String) o));
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

        JSONArray transactions = new JSONArray();
        for (UTransaction identity : this.transactions) {
            transactions.add(identity.toString());
        }
        ledgerJson.set("transactions", transactions);
    }
}
