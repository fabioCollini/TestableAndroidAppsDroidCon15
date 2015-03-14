package it.cosenonjaviste.testableandroidapps.lib;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ValueReference {

    private Field field;

    private Method method;

    private Object dest;

    private BindField bindField;

    public ValueReference(Field field, Object dest, BindField bindField) {
        this.field = field;
        this.dest = dest;
        this.bindField = bindField;
        field.setAccessible(true);
    }

    public ValueReference(Method method, Object dest, BindField bindField) {
        this.method = method;
        this.dest = dest;
        this.bindField = bindField;
    }

    public <T> T get() {
        if (field != null) {
            try {
                return (T) field.get(dest);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                return (T) method.invoke(dest);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public <T> T get(int i) {
        try {
            return (T) method.invoke(dest, i);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void set(String s) {
        if (field != null) {
            try {
                field.set(dest, s);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                method.invoke(dest, s);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void invoke() {
        try {
            method.invoke(dest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void invoke(int position) {
        try {
            method.invoke(dest, position);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BindField getBindField() {
        return bindField;
    }

    @Override public String toString() {
        return "ValueReference{" +
                (field != null ? "field=" + field.getName() : "") +
                (method != null ? "method=" + method.getName() : "") +
                ", bindField=" + bindField +
                '}';
    }
}
