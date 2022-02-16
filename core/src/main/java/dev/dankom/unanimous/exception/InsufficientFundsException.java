package dev.dankom.unanimous.exception;

import dev.dankom.unanimous.group.profile.UProfile;

public class InsufficientFundsException extends Exception {
    private final UProfile profile;

    public InsufficientFundsException(UProfile profile) {
        super("Insufficient Funds");
        this.profile = profile;
    }

    public UProfile getProfile() {
        return profile;
    }
}
