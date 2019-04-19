package com.fynn.intellij;

import java.util.*;

public class LinkedProperties extends Properties {

    private LinkedHashSet keySet = new LinkedHashSet();

    @Override
    public synchronized Object put(Object key, Object value) {
        keySet.add(key);
        return super.put(key, value);
    }

    @Override
    public Set<Object> keySet() {
        return keySet;
    }

    @Override
    public Set<String> stringPropertyNames() {
        Set<String> set = new LinkedHashSet<>();
        for (Object key : keySet) {
            set.add((String) key);
        }
        return set;
    }

    @Override
    public Enumeration<?> propertyNames() {
        return Collections.enumeration(keySet);
    }

    @Override
    public Enumeration<Object> keys() {
        return Collections.enumeration(keySet);
    }
}
