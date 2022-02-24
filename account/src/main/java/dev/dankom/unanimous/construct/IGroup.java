package dev.dankom.unanimous.construct;

import dev.dankom.unanimous.query.Query;
import dev.dankom.unanimous.type.Savable;

import java.util.ArrayList;
import java.util.List;

public interface IGroup extends Savable {
    List<? extends IProfile> getProfiles();
    List<? extends IIdentity> getIdentities();
    List<? extends ITransaction> getTransactions();

    default List<IProfile> queryProfiles(Query query) {
        return query(getProfiles(), query);
    }

    default List<IIdentity> queryIdentities(Query query) {
        return query(getIdentities(), query);
    }

    default List<ITransaction> queryTransactions(Query query) {
        return query(getTransactions(), query);
    }

    default <T> List<T> query(List<T> list, Query<T> query) {
        List<T> out = new ArrayList<>();
        for (T o : list) {
            if (query.matcher(o)) {
                out.add(o);
            }
        }
        return out;
    }
}
