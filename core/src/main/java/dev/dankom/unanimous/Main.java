package dev.dankom.unanimous;

import dev.dankom.unanimous.file.FileManager;
import dev.dankom.unanimous.group.UGroup;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        FileManager fileManager = new FileManager();
        UGroup group = new UGroup("710", fileManager.root);

        group.load();
        System.out.println(group.getIdentity(UUID.fromString("ac88c4ff-c058-4280-9a80-8fe66d6ec530")).doesPinMatch("1234"));
    }
}
