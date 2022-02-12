package dev.dankom.unanimous.config.wrapper;

import org.json.simple.JSONObject;

public class Job {
    private final String name;
    private final long salary;
    private final boolean isTeacherReferenceRequired;

    public Job(JSONObject json) {
        name = (String) json.get("name");
        salary = (Long) json.get("salary");
        isTeacherReferenceRequired = (Boolean) json.get("isTeacherReferenceRequired");
    }

    public String getName() {
        return name;
    }

    public long getSalary() {
        return salary;
    }

    public boolean isTeacherReferenceRequired() {
        return isTeacherReferenceRequired;
    }
}
