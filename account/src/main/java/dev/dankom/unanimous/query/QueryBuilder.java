package dev.dankom.unanimous.query;

import dev.dankom.unanimous.query.rule.Rule;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QueryBuilder<T> {
    private List<Rule<T>> rules = new ArrayList<>();

    QueryBuilder() {}

    public QueryBuilder<T> rule(Rule<T> rule) {
        rules.add(rule);
        return this;
    }

    public Query<T> build() {
        return new Query<>(rules);
    }

    public static <T> QueryBuilder<T> newBuilder() {
        return new QueryBuilder<>();
    }

    public static <T> QueryBuilder<T> newBuilder(List<Rule<T>> rules) {
        QueryBuilder<T> builder = newBuilder();
        rules.forEach(r -> builder.rule(r));
        return builder;
    }

    public static <T> QueryBuilder<T> fromJSON(JSONObject json) {
        QueryBuilder<T> builder = newBuilder();
        if (json.containsKey("rules")) {
            for (Object o : (JSONArray) json.get("rules")) {
                builder.rule(Rule.newJSON((JSONObject) o));
            }
        }
        return builder;
    }
}
