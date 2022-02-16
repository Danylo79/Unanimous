package dev.dankom.unanimous.group.transaction;

import java.util.UUID;

public class UTransaction {
    private final UUID id;
    private final UUID sender;
    private final UUID receiver;
    private final Long time;
    private final Float amount;
    private final String description;

    public UTransaction(String in) {
        String[] split = in.split(":");

        this.id = UUID.fromString(split[0]);
        this.sender = UUID.fromString(split[1]);
        this.receiver = UUID.fromString(split[2]);
        this.time = Long.parseLong(split[3]);
        this.amount = Float.parseFloat(split[4]);
        this.description = split[5];
    }

    public UTransaction(UUID id, UUID sender, UUID receiver, Long time, Float amount, String description) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
        this.amount = amount;
        this.description = description;
    }

    public UUID getID() {
        return id;
    }

    public UUID getSender() {
        return sender;
    }

    public UUID getReceiver() {
        return receiver;
    }

    public Long getTime() {
        return time;
    }

    public Float getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public boolean isMine(UUID id) {
        return getSender().equals(id) || getReceiver().equals(id);
    }

    @Override
    public String toString() {
        return id.toString() + ":" + sender.toString() + ":" + receiver.toString() + ":" + time + ":" + amount + ":" + description;
    }
}
