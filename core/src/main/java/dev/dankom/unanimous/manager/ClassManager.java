package dev.dankom.unanimous.manager;

import dev.dankom.file.type.Directory;
import dev.dankom.unanimous.config.Configuration;
import dev.dankom.unanimous.construct.IGroup;
import dev.dankom.unanimous.construct.IManager;
import dev.dankom.unanimous.file.FileManager;
import dev.dankom.unanimous.group.UGroup;
import dev.dankom.unanimous.group.profile.UIdentity;
import dev.dankom.unanimous.group.profile.UProfile;
import dev.dankom.unanimous.group.transaction.UTransaction;
import dev.dankom.unanimous.transaction.ThreadSafeTransactor;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ClassManager implements IManager {
    private final Directory root;
    private final Configuration configuration;
    private final ThreadSafeTransactor transactor;
    private final List<UGroup> groups = new ArrayList<>();

    public ClassManager(FileManager fileManager) {
        this(fileManager.root, new Configuration(fileManager));
    }

    public ClassManager(Directory root, Configuration configuration) {
        this.root = root;
        this.configuration = configuration;
        this.transactor = new ThreadSafeTransactor(this);

        if (!new File(root, "teachers.json").exists()) {
            addGroup(new UGroup("teachers", root));
            save();
        }

        load();
    }

    public boolean login(String username, String pin) {
        for (UGroup group : groups) {
            for (UProfile profile : group.getProfiles()) {
                for (UIdentity identity : group.getIdentities(profile)) {
                    if (identity.getUsername().equals(username) && identity.doesPinMatch(pin)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void transact(UProfile sender, UProfile receiver, float amount, String description) {
        try {
            transact(sender.getID(), receiver.getID(), amount, description);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void transact(UUID sender, UUID receiver, float amount, String description) throws Exception {
        transactor.queueTransaction(new UTransaction(UUID.randomUUID(), sender, receiver, new Date().getTime(), amount, description));
    }

    public UProfile addTeacher(UProfile profile, UIdentity... identities) {
        UGroup teachers = getGroup("teachers");
        teachers.addProfile(profile);

        for (UIdentity i : identities) {
            teachers.addIdentity(i);
            profile.addIdentity(i.getID().toString());
        }

        return profile;
    }

    public UProfile createStudent(String homeroom, String username, String pin) {
        return createStudent(homeroom, new UIdentity(UUID.randomUUID(), username, pin));
    }

    public UProfile createStudent(String homeroom, UIdentity... identities) {
        return addStudent(homeroom, new UProfile(getGroup(homeroom), UUID.randomUUID()), identities);
    }

    public UProfile addStudent(String homeroom, UProfile profile, UIdentity... identities) {
        UGroup clazz = getGroup(homeroom);
        clazz.addProfile(profile);

        for (UIdentity i : identities) {
            clazz.addIdentity(i);
            profile.addIdentity(i.getID().toString());
        }

        return profile;
    }

    public void addClass(String homeroom) {
        addGroup(new UGroup(homeroom, new Directory(root, "classes")));
    }

    public void addGroup(UGroup group) {
        groups.add(group);
    }

    public UGroup getGroup(String id) {
        for (UGroup group : groups) {
            if (group.getID().equals(id)) {
                return group;
            }
        }
        return null;
    }

    public UProfile getProfileInGlobal(UUID id) {
        for (UGroup group : groups) {
            for (UProfile profile : group.getProfiles()) {
                if (profile.getID().equals(id)) {
                    return profile;
                }
            }
        }
        return null;
    }

    public List<UGroup> getGroups() {
        return groups;
    }

    public void collect(Consumer<UProfile> consumer) {
        groups.forEach(g -> g.getProfiles().forEach(consumer));
    }

    public void load() {
        for (File file : root.getFiles()) {
            if (file.getName().equals("group.json")) {
                UGroup group = new UGroup(file);
                groups.add(group);
            }
        }

        groups.forEach(group -> group.load());
    }

    public void save() {
        groups.forEach(group -> group.save());
    }

    public void purge() {
        groups.clear();
    }
}
