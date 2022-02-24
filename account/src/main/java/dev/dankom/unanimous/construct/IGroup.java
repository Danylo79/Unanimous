package dev.dankom.unanimous.construct;

import dev.dankom.unanimous.type.Savable;

import java.util.List;

public interface IGroup extends Savable {
    List<? extends IProfile> getProfiles();
    List<? extends IIdentity> getIdentities();
    List<? extends ITransaction> getTransactions();
}
