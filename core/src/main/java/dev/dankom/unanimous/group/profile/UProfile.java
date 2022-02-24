package dev.dankom.unanimous.group.profile;

import dev.dankom.file.json.JsonObjectBuilder;
import dev.dankom.unanimous.construct.IProfile;
import dev.dankom.unanimous.group.UGroup;
import dev.dankom.unanimous.group.transaction.UTransaction;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UProfile implements IProfile {
    private UGroup parent;
    private UUID id;

    private List<String> identities;
    private List<String> roles;
    private List<String> jobs;

    public UProfile(UGroup parent, JSONObject json) {
        this.parent = parent;
        this.id = UUID.fromString((String) json.get("id"));

        this.identities = (List<String>) json.get("identities");
        this.roles = (List<String>) json.get("roles");
        this.jobs = (List<String>) json.get("jobs");
    }

    public UProfile(UGroup parent, UUID id) {
        this.parent = parent;
        this.id = id;

        this.identities = new ArrayList<>();
        this.roles = new ArrayList<>();
        this.jobs = new ArrayList<>();
    }

    public float getBalance() {
        float out = 0;
        for (UTransaction transaction : parent.getTransactions()) {
            if (transaction.getSender().equals(getID())) {
                out -= transaction.getAmount();
            } else if (transaction.getReceiver().equals(getID())) {
                out += transaction.getAmount();
            } else {
                continue;
            }
        }

        if (shouldCheckFunds() && out <= 0) {
            return -999999999.9999999999999999999999999999999999999999999999999999999999F;
        }

        return out;
    }

    public UGroup getParent() {
        return parent;
    }

    public void setParent(UGroup parent) {
        this.parent = parent;
    }

    public UUID getID() {
        return id;
    }

    public void setID(UUID id) {
        this.id = id;
    }

    public List<String> getIdentities() {
        return identities;
    }

    public void addIdentity(String identity) {
        identities.add(identity);
    }

    public List<String> getRoles() {
        return roles;
    }

    public void addRole(String role) {
        roles.add(role);
    }

    public List<String> getJobs() {
        return jobs;
    }

    public void addJob(String job) {
        jobs.add(job);
    }

    public boolean shouldCheckFunds() {
        return false;
    }

    @Override
    public String toString() {
        return "UProfile{" +
                "parent=" + parent +
                ", id=" + id +
                ", identities=" + identities +
                ", roles=" + roles +
                ", jobs=" + jobs +
                '}';
    }

    public JSONObject toJSON() {
        JsonObjectBuilder builder = new JsonObjectBuilder();
        builder.addKeyValuePair("id", id.toString());
        builder.addArray("identities", identities);
        builder.addArray("roles", roles);
        builder.addArray("jobs", jobs);
        return builder.build();
    }
}
