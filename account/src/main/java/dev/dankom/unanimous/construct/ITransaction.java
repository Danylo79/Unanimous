package dev.dankom.unanimous.construct;

import java.util.UUID;

public interface ITransaction {
    UUID getID();
    UUID getSender();
    UUID getReceiver();
    Long getTime();
    Float getAmount();
    String getDescription();
    boolean isMine(UUID id);
}
