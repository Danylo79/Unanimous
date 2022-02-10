package dev.dankom.unanimous.file;

import dev.dankom.file.json.JsonFile;
import dev.dankom.file.json.JsonObjectBuilder;
import dev.dankom.file.type.Directory;
import org.json.simple.JSONArray;

public class FileManager {
    public final Directory root = new Directory("./.unanimous");
}
