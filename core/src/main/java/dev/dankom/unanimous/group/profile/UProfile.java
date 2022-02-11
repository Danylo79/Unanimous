package dev.dankom.unanimous.group.profile;

import dev.dankom.file.json.JsonObjectBuilder;
import dev.dankom.unanimous.group.UGroup;
import dev.dankom.unanimous.group.type.Role;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UProfile {
    private UGroup parent;
    private UUID id;
    private List<Role> roles;
    private List<String> jobs;

    public UProfile(UGroup parent, JSONObject json) {
        this.parent = parent;
        this.id = UUID.fromString((String) json.get("id"));

        this.roles = new ArrayList<>();
        for (String role : (List<String>) json.get("roles")) {
            roles.add(Role.get(role));
        }

        this.jobs = (List<String>) json.get("jobs");
    }

    public UProfile(UGroup parent, UUID id, List<Role> roles, List<String> jobs) {
        this.parent = parent;
        this.id = id;
        this.roles = roles;
        this.jobs = jobs;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<String> getJobs() {
        return jobs;
    }

    public void setJobs(List<String> jobs) {
        this.jobs = jobs;
    }

    public UIdentity getIdentity() {
        return parent.getIdentity(this);
    }

    public JSONObject toJSON() {
        JsonObjectBuilder builder = new JsonObjectBuilder();
        builder.addKeyValuePair("id", id.toString());

        List<String> roles = new ArrayList<>();
        for (Role role : this.roles) {
            roles.add(role.name());
        }
        builder.addArray("roles", roles);

        builder.addArray("jobs", jobs);
        return builder.build();
    }
}
