package dev.dankom.unanimous.profile;

import dev.dankom.file.json.JsonObjectBuilder;
import dev.dankom.unanimous.util.JsonUtil;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.UUID;

public class UStudent {
    private UUID id;
    private String username;
    private String pin;
    private int homeroom;
    private int studentId;
    private int coins;
    private boolean isCommittee;
    private List<String> jobs;

    public UStudent(JSONObject json) {
        this(
                UUID.fromString((String) json.get("id")),
                (String) json.get("username"),
                (String) json.get("pin"),
                ((Long) json.get("homeroom")).intValue(),
                ((Long) json.get("studentId")).intValue(),
                ((Long) json.get("coins")).intValue(),
                (Boolean) json.get("isCommittee"),
                JsonUtil.getJobsFromJson(json)
        );
    }

    public UStudent(UUID id, String username, String pin, int homeroom, int studentId, int coins, boolean isCommittee, List<String> jobs) {
        this.id = id;
        this.username = username;
        this.pin = pin;
        this.homeroom = homeroom;
        this.studentId = studentId;
        this.coins = coins;
        this.isCommittee = isCommittee;
        this.jobs = jobs;
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

    public int getHomeroom() {
        return homeroom;
    }

    public void setHomeroom(int homeroom) {
        this.homeroom = homeroom;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public boolean isCommittee() {
        return isCommittee;
    }

    public void setCommittee(boolean isCommittee) {
        this.isCommittee = isCommittee;
    }

    public List<String> getJobs() {
        return jobs;
    }

    public void setJobs(List<String> jobs) {
        this.jobs = jobs;
    }

    public JSONObject toJSON() {
        return new JsonObjectBuilder()
                .addKeyValuePair("id", id.toString())
                .addKeyValuePair("username", username)
                .addKeyValuePair("pin", pin)
                .addKeyValuePair("homeroom", homeroom)
                .addKeyValuePair("studentId", studentId)
                .addKeyValuePair("coins", coins)
                .addKeyValuePair("isCommittee", isCommittee)
                .addKeyValuePair("jobs", jobs)
                .build();
    }
}
