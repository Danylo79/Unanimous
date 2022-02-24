package dev.dankom.unanimous.construct;

import java.util.List;
import java.util.UUID;

public interface IProfile {
    float getBalance();
    UUID getID();
    void setID(UUID id);
    List<String> getIdentities();
    void addIdentity(String identity);
    List<String> getRoles();
    void addRole(String role);
    List<String> getJobs();
    void addJob(String job);
    boolean shouldCheckFunds();
}
