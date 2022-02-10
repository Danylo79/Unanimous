package dev.dankom.unanimous.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    public static List<String> getJobsFromJson(JSONObject json) {
        List<String> out = new ArrayList<>();
        for (Object o : (JSONArray) json.get("jobs")) {
            out.add((String) o);
        }
        return out;
    }
}
