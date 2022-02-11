package dev.dankom.unanimous.manager;

import dev.dankom.file.type.Directory;
import dev.dankom.unanimous.file.FileManager;
import dev.dankom.unanimous.group.UGroup;
import dev.dankom.unanimous.group.profile.UIdentity;
import dev.dankom.unanimous.group.profile.UProfile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassManager {
    private final Directory root;
    private final List<UGroup> groups = new ArrayList<>();

    public ClassManager(FileManager fileManager) {
        this(fileManager.root);
    }

    public ClassManager(Directory root) {
        this.root = root;

        if (!new File(root, "teachers.json").exists()) {
            addGroup(new UGroup("teachers", root));
            save();
        }
    }

    public void addTeacher(UProfile profile, UIdentity identity) {
        getGroup("teachers").addProfile(profile, identity);
    }

    public void addStudent(String homeroom, UProfile profile, UIdentity identity) {
        getGroup(homeroom).addProfile(profile, identity);
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
}
