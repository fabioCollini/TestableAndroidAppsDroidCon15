package it.cosenonjaviste.testableandroidapps.lib;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectionUtils {
    public static Map<Integer, ValueReference> getOnClickMethods(Object obj) {
        Map<Integer, ValueReference> onClickMap = new HashMap<>();
        Method[] methods = obj.getClass().getMethods();
        for (Method method : methods) {
            OnClick annotation = method.getAnnotation(OnClick.class);
            if (annotation != null) {
                onClickMap.put(annotation.value(), new ValueReference(method, obj, null));
            }
        }
        return onClickMap;
    }

    public static Map<Integer, ValueReference> getOnItemClickMethods(Object obj) {
        Map<Integer, ValueReference> onClickMap = new HashMap<>();
        Method[] methods = obj.getClass().getMethods();
        for (Method method : methods) {
            OnItemClick annotation = method.getAnnotation(OnItemClick.class);
            if (annotation != null) {
                onClickMap.put(annotation.value(), new ValueReference(method, obj, null));
            }
        }
        return onClickMap;
    }

    public static HashMap<Integer, ValueReference> getBindedFields(Object obj) {
        List<Field> annotatedFields = getAnnotatedFields(obj.getClass(), Bind.class);
        HashMap<Integer, ValueReference> fieldsMap = new HashMap<>();
        for (Field field : annotatedFields) {
            Bind annotation = field.getAnnotation(Bind.class);
            fieldsMap.put(annotation.value(), new ValueReference(field, obj, annotation.field()));
        }
        return fieldsMap;
    }

    public static <T extends Annotation> List<Field> getAnnotatedFields(Class<?> aClass, Class<T> annotationType) {
        List<Field> fieldsList = new ArrayList<>();
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            T annotation = field.getAnnotation(annotationType);
            if (annotation != null) {
                field.setAccessible(true);
                fieldsList.add(field);
            }
        }
        return fieldsList;
    }

    public static <T extends Annotation> List<Method> getAnnotatedMethods(Class<?> aClass, Class<T> annotationType) {
        List<Method> fieldsList = new ArrayList<>();
        Method[] fields = aClass.getDeclaredMethods();
        for (Method field : fields) {
            T annotation = field.getAnnotation(annotationType);
            if (annotation != null) {
                field.setAccessible(true);
                fieldsList.add(field);
            }
        }
        return fieldsList;
    }

    public static Map<Integer, ValueReference> getBindedItemMethods(Object obj) {
        Map<Integer, ValueReference> map = new HashMap<>();
        List<Method> annotatedMethods = getAnnotatedMethods(obj.getClass(), BindItem.class);
        for (Method annotatedMethod : annotatedMethods) {
            BindItem annotation = annotatedMethod.getAnnotation(BindItem.class);
            map.put(annotation.value(), new ValueReference(annotatedMethod, obj, null));
        }
        return map;
    }

    public static Map<Integer, ValueReference> getBindedGetters(Object obj) {
        Map<Integer, ValueReference> map = new HashMap<>();
        List<Method> annotatedMethods = getAnnotatedMethods(obj.getClass(), Bind.class);
        for (Method annotatedMethod : annotatedMethods) {
            if (!annotatedMethod.getReturnType().equals(Void.class)) {
                Bind annotation = annotatedMethod.getAnnotation(Bind.class);
                map.put(annotation.value(), new ValueReference(annotatedMethod, obj, annotation.field()));
            }
        }
        return map;
    }

    public static Map<Integer, ValueReference> getBindedSetters(Object obj) {
        Map<Integer, ValueReference> map = new HashMap<>();
        List<Method> annotatedMethods = getAnnotatedMethods(obj.getClass(), Bind.class);
        for (Method annotatedMethod : annotatedMethods) {
            if (annotatedMethod.getReturnType().equals(Void.class)) {
                Bind annotation = annotatedMethod.getAnnotation(Bind.class);
                map.put(annotation.value(), new ValueReference(annotatedMethod, obj, annotation.field()));
            }
        }
        return map;
    }
}
