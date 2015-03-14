package it.cosenonjaviste.testableandroidapps.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Action2;

public class ValueReferenceMap {
    protected Map<Integer, List<ValueReference>> map = new HashMap<>();

    public void put(int value, ValueReference valueReference) {
        List<ValueReference> valueReferences = map.get(value);
        if (valueReferences == null) {
            valueReferences = new ArrayList<>();
            map.put(value, valueReferences);
        }
        valueReferences.add(valueReference);
    }

    public void putAll(ValueReferenceMap m) {
        for (Map.Entry<Integer, List<ValueReference>> entry : m.map.entrySet()) {
            for (ValueReference valueReference : entry.getValue()) {
                put(entry.getKey(), valueReference);
            }
        }
    }

    public void doOnEach(Action2<Integer, ValueReference> action) {
        for (Map.Entry<Integer, List<ValueReference>> entry : map.entrySet()) {
            for (ValueReference valueReference : entry.getValue()) {
                action.call(entry.getKey(), valueReference);
            }
        }
    }
}
