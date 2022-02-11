package dev.dankom.unanimous;

import dev.dankom.unanimous.file.FileManager;
import dev.dankom.unanimous.group.UGroup;

public class Main {
    public static void main(String[] args) {
        FileManager fileManager = new FileManager();
        UGroup group = new UGroup("710", fileManager.root);
    }
}
