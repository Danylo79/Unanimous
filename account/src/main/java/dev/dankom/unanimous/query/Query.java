package dev.dankom.unanimous.query;

import dev.dankom.unanimous.query.rule.Rule;

import java.util.List;

public class Query<T> {
    private final List<Rule<T>> rules;

    Query(List<Rule<T>> rules) {
        this.rules = rules;
    }

    public boolean matcher(T object) {
        for (Rule<T> rule : rules) {
            if (!rule.matcher(object)) return false;
        }
        return true;
    }
}
