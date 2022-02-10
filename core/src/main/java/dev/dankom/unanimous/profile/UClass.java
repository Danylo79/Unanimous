package dev.dankom.unanimous.profile;

import dev.dankom.file.json.JsonFile;
import dev.dankom.file.json.JsonObjectBuilder;
import dev.dankom.file.type.Directory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UClass {
    private JsonFile json;
    private List<UStudent> students = new ArrayList<>();

    /**
     * Create a new class
     */
    public UClass(Directory root, String homeroom) {
        this.json = new JsonFile(new Directory(root, "classes/" + homeroom), homeroom, new JsonObjectBuilder()
                .addArray("students", new JSONArray())
                .build());
    }

    /**
     * Loads a class
     */
    public UClass(JsonFile json) {
        this.json = json;
        load();
    }

    public void addStudent(UStudent student) {
        students.add(student);
    }

    public UStudent getStudent(UUID id) {
        for (UStudent student : students) {
            if (student.getID().equals(id)) {
                return student;
            }
        }
        return null;
    }

    public UStudent getStudent(String username) {
        for (UStudent student : students) {
            if (student.getUsername().equals(username)) {
                return student;
            }
        }
        return null;
    }

    public void load() {
        for (Object o : (JSONArray) json.get().get("students")) {
            students.add(new UStudent((JSONObject) o));
        }
    }

    public void save() {
        JSONArray out = new JSONArray();
        for (UStudent student : students) {
            out.add(student.toJSON());
        }
        json.set("students", out);
    }

    public String getHomeroom() {
        return json.getName().replace(".json", "");
    }
}
