package it.cosenonjaviste.testableandroidapps.injector;

import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;

public class TestInjector {

    private Map<Integer, View> views = new HashMap<>();

    private Map<Integer, InvocableMethod> onClickMethods = new HashMap<>();

    private Map<Integer, InvocableMethod> onItemClickMethods = new HashMap<>();

    public void inject(Object object) {
        try {
            CtClass objectClass = ClassPool.getDefault().get(object.getClass().getName());
            initFields(object, objectClass);
            initMethods(object, objectClass);
        } catch (Exception e) {
            throw new RuntimeException("Error injecting object " + object, e);
        }
    }

    private void initFields(Object object, CtClass objectClass) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        CtField[] declaredFields = objectClass.getDeclaredFields();
        for (CtField field : declaredFields) {
            InjectView annotation = (InjectView) field.getAnnotation(InjectView.class);
            if (annotation != null) {
                int viewId = annotation.value();
                Field javaField = object.getClass().getDeclaredField(field.getName());
                View v = getOrCreateView(javaField.getType(), viewId);
                javaField.setAccessible(true);
                javaField.set(object, v);
            }
        }
    }

    private void initMethods(Object object, CtClass objectClass) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        CtMethod[] declaredMethods = objectClass.getDeclaredMethods();
        for (CtMethod method : declaredMethods) {
            OnClick onClickAnnotation = (OnClick) method.getAnnotation(OnClick.class);
            if (onClickAnnotation != null) {
                int[] viewIds = onClickAnnotation.value();
                Method javaMethod = object.getClass().getDeclaredMethod(method.getName());
                InvocableMethod invocableMethod = new InvocableMethod(javaMethod, object);
                for (int viewId : viewIds) {
                    onClickMethods.put(viewId, invocableMethod);
                }
            }
            OnItemClick onItemClickAnnotation = (OnItemClick) method.getAnnotation(OnItemClick.class);
            if (onItemClickAnnotation != null) {
                int[] viewIds = onItemClickAnnotation.value();
                Method javaMethod = object.getClass().getDeclaredMethod(method.getName(), Integer.TYPE);
                InvocableMethod invocableMethod = new InvocableMethod(javaMethod, object);
                for (int viewId : viewIds) {
                    onItemClickMethods.put(viewId, invocableMethod);
                }
            }
        }
    }

    private View getOrCreateView(Class<?> type, int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = createFake((Class<? extends View>) type);
            views.put(viewId, view);
        }
        return view;
    }

    public <T> T getView(int viewId) {
        return (T) views.get(viewId);
    }

    public TextView getTextView(int viewId) {
        return getView(viewId);
    }

    public static <T> T createFake(Class<T> fakeClass) {
        try {
            ProxyFactory factory = new ProxyFactory();
            factory.setSuperclass(fakeClass);
            Class<T> aClass = factory.createClass();
            Constructor<T> constructor = aClass.getConstructor(Context.class);
            final T newInstance = constructor.newInstance(new Object[]{null});
            MethodHandler methodHandler = new MethodHandler() {

                private Map<String, Object> values = new HashMap<>();

                @Override
                public Object invoke(Object self, Method overridden, Method proceed, Object[] args) throws Throwable {
                    String fieldName = getFieldName(overridden.getName());
                    if (args.length == 1) {
                        values.put(fieldName, args[0]);
                        return null;
                    } else {
                        return convertReturnValue(values.get(fieldName), overridden.getReturnType());
                    }
                }
            };
            ((Proxy) newInstance).setHandler(methodHandler);
            return newInstance;
        } catch (Exception e) {
            throw new RuntimeException("Error creating fake object for class " + fakeClass.getName(), e);
        }
    }

    private static Object convertReturnValue(Object obj, Class<?> returnType) {
        if (obj != null) {
            if (returnType.equals(Editable.class)) {
                return new FakeEditable(obj.toString());
            }
            return obj;
        }
        if (returnType.equals(Boolean.TYPE)) {
            return false;
        }
//        if (returnType.equals(String.class) || returnType.equals(CharSequence.class)) {
//            return "";
//        }
        if (returnType.equals(Editable.class)) {
            return new FakeEditable("");
        }
        return null;
    }

    private static String getFieldName(String name) {
        if (name.startsWith("get") || name.startsWith("set")) {
            return name.substring(3);
        } else if (name.startsWith("is")) {
            return name.substring(2);
        } else {
            throw new RuntimeException("Error invoking method " + name);
        }
    }

    public void clickOnView(int viewId) {
        onClickMethods.get(viewId).invoke();
    }

    public void clickOnItem(int viewId, int position) {
        onItemClickMethods.get(viewId).invoke(position);
    }

    private static class InvocableMethod {
        private Method method;

        private Object receiver;

        public InvocableMethod(Method method, Object receiver) {
            this.method = method;
            this.receiver = receiver;
        }

        public void invoke() {
            try {
                method.invoke(receiver);
            } catch (Exception e) {
                throw new RuntimeException("Error invoking method " + method.getName() + " on object " + receiver, e);
            }
        }

        public void invoke(int position) {
            try {
                method.invoke(receiver, position);
            } catch (Exception e) {
                throw new RuntimeException("Error invoking method " + method.getName() + " on object " + receiver, e);
            }
        }
    }
}
