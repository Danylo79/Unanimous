package dev.dankom.unanimous.construct;

import java.util.UUID;

public interface IIdentity {
    UUID getID();
    void setId(UUID id);
    String getUsername();
    void setUsername(String username);
    String getPin();
    void setPin(String pin);
    boolean doesPinMatch(String pin);
}
