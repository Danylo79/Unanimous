package dev.dankom.unanimous.config;

import dev.dankom.file.json.JsonFile;
import dev.dankom.file.json.JsonObjectBuilder;
import dev.dankom.file.type.Directory;
import dev.dankom.type.returner.Returner;
import dev.dankom.unanimous.config.wrapper.Job;
import dev.dankom.unanimous.config.wrapper.Role;
import dev.dankom.unanimous.file.FileManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Configuration {
    private final Directory root;
    private final JsonFile config;

    private final List<Job> jobs = new ArrayList<>();
    private final List<Role> roles = new ArrayList<>();

    public Configuration(FileManager fileManager) {
        this(fileManager.root);
    }

    public Configuration(Directory root) {
        this.root = root;
        this.config = new JsonFile(root, "config", ((Returner<JSONObject>) () -> {
            JsonObjectBuilder builder = new JsonObjectBuilder();

            JSONArray jobs = new JSONArray();
            jobs.add(new JsonObjectBuilder()
                    .addKeyValuePair("name", "Fine Officer")
                    .addKeyValuePair("salary", 800)
                    .addKeyValuePair("isTeacherReferenceRequired", true)
                    .build());
            builder.addArray("jobs", jobs);

            JSONArray roles = new JSONArray();
            roles.add(new JsonObjectBuilder()
                    .addKeyValuePair("name", "Teacher")
                    .addKeyValuePair("isAdministrator", true)
                    .build());
            builder.addArray("roles", roles);

            return builder.build();
        }).returned());

        load();
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void load() {
        for (JSONObject jo : (List<JSONObject>) config.get().get("jobs")) {
            jobs.add(new Job(jo));
        }

        for (JSONObject jo : (List<JSONObject>) config.get().get("roles")) {
            roles.add(new Role(jo));
        }
    }
}
