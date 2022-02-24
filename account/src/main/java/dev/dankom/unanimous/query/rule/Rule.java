package dev.dankom.unanimous.query.rule;

import dev.dankom.type.map.SimpleMap;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface Rule<T> {
    RuleType getType();
    SimpleMap<String, Object> getMatcher();

    default boolean matcher(T object) {
        try {
            if (getType().getGroup().equals("FIELD")) {
                Field field = object.getClass().getDeclaredField(getMatcher().getKey());
                field.setAccessible(true);
                if (getType() == RuleType.FIELD_INCLUSION) {
                    return field.get(object).equals(getMatcher().getValue());
                } else if (getType() == RuleType.FIELD_EXCLUSION) {
                    return !field.get(object).equals(getMatcher().getValue());
                }
            } else if (getType().getGroup().equals("METHOD")) {
                Object o = runGetter(getMatcher().getKey(), object);
                if (getType() == RuleType.METHOD_INCLUSION) {
                    return o.equals(getMatcher().getValue());
                } else if (getType() == RuleType.METHOD_EXCLUSION) {
                    return !o.equals(getMatcher().getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    default Object runGetter(String field, Object o) {
        for (Method method : o.getClass().getMethods()) {
            if ((method.getName().startsWith("get")) && (method.getName().length() == (field.length() + 3))) {
                if (method.getName().toLowerCase().endsWith(field.toLowerCase())) {
                    try {
                        return method.invoke(o);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return null;
    }

    static Rule newSimple(RuleType type, String key, String value) {
        return new Rule() {
            @Override
            public RuleType getType() {
                return type;
            }

            @Override
            public SimpleMap<String, Object> getMatcher() {
                return new SimpleMap<>(key, value);
            }
        };
    }

    static Rule newJSON(JSONObject json) {
        return new Rule() {
            @Override
            public RuleType getType() {
                return RuleType.get((String) json.get("type"));
            }

            @Override
            public SimpleMap<String, Object> getMatcher() {
                return new SimpleMap<>((String) json.get("key"), json.get("object"));
            }
        };
    }
}
