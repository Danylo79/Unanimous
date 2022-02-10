package dev.dankom.unanimous.profile;

import dev.dankom.file.json.JsonFile;
import dev.dankom.file.type.Directory;
import dev.dankom.unanimous.file.FileManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ClassManager {
    private FileManager fileManager;
    private List<UClass> classes = new ArrayList<>();

    public ClassManager(FileManager fileManager) {
        this.fileManager = fileManager;
        load();
    }

    /**
     * Creates a new class if one doesn't already exist
     * @param homeroom The id of the class
     */
    public void createClass(String homeroom) {
        if (getClass(homeroom) == null) {
            classes.add(new UClass(fileManager.root, homeroom));
        } else {
            throw new Error("Class already exists " + homeroom);
        }
    }

    /**
     * Gets a class
     * @param homeroom The id of the class to get
     * @return The class or null if it doesn't exist
     */
    public UClass getClass(String homeroom) {
        for (UClass clazz : classes) {
            if (clazz.getHomeroom().equals(homeroom)) {
                return clazz;
            }
        }
        return null;
    }

    /**
     * Updates a class
     * @param clazz The class to update
     */
    public void updateClass(UClass clazz) {
        for (int i = 0; i < classes.size(); i++) {
            UClass c = classes.get(i);
            if (c.getHomeroom().equals(c.getHomeroom())) {
                classes.set(i, clazz);
            }
        }
    }

    /**
     * Modifies and updates a class
     * @param homeroom The target class
     * @param function The transformer to apply
     */
    public void modifyClass(String homeroom, Function<UClass, UClass> function) {
        classes.forEach(clazz -> {
            if (clazz.getHomeroom().equals(homeroom)) {
                updateClass(function.apply(clazz));
            }
        });
    }

    public void load() {
        for (File file : new Directory(fileManager.root, "classes").getFiles()) {
            if (file.getName().endsWith(".json")) {
                classes.add(new UClass(new JsonFile(file.getParentFile(), file.getName().replace(".json", ""))));
            }
        }
    }

    public void save() {
        for (UClass clazz : classes) {
            clazz.save();
        }
    }
}
