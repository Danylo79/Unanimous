package dev.dankom.unanimous.group.profile;

import dev.dankom.file.json.JsonObjectBuilder;
import dev.dankom.unanimous.group.UGroup;
import dev.dankom.unanimous.group.transaction.UTransaction;
import org.json.simple.JSONObject;

import java.util.*;

public class UProfile {
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
        Map<UUID, UTransaction> calculated = new HashMap<>();
        for (UTransaction transaction : parent.getTransactions()) {
            if (!calculated.containsKey(transaction.getID())) {
                if (transaction.getSender() == getID()) {
                    calculated.put(transaction.getID(), transaction);
                    out -= transaction.getAmount();
                } else if (transaction.getReceiver() == getID()) {
                    calculated.put(transaction.getID(), transaction);
                    out += transaction.getAmount();
                } else {
                    continue;
                }
            }
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
        return true;
    }

    public JSONObject toJSON() {
        JsonObjectBuilder builder = new JsonObjectBuilder();
        builder.addKeyValuePair("id", id.toString());
        builder.addArray("roles", roles);
        builder.addArray("jobs", jobs);
        return builder.build();
    }
}
