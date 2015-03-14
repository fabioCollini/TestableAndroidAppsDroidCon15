package it.cosenonjaviste.testableandroidapps.lib;

import android.view.View;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.OnClickListener;

public abstract class BaseContext {
    protected Map<Integer, ValueReference> gettersMap = new HashMap<>();
    protected Map<Integer, ValueReference> settersMap = new HashMap<>();
    protected Map<Integer, ValueReference> itemMethodsMap = new HashMap<>();
    protected Map<Integer, ValueReference> onClickMap = new HashMap<>();

    public void init(Object... objs) {
        for (Object obj : objs) {
            HashMap<Integer, ValueReference> bindedFields = ReflectionUtils.getBindedFields(obj);

            gettersMap.putAll(bindedFields);
            gettersMap.putAll(ReflectionUtils.getBindedGetters(obj));

            settersMap.putAll(bindedFields);
            settersMap.putAll(ReflectionUtils.getBindedSetters(obj));

            itemMethodsMap.putAll(ReflectionUtils.getBindedItemMethods(obj));
            onClickMap.putAll(ReflectionUtils.getOnClickMethods(obj));
        }
        for (Object obj : objs) {
            initLists(obj);
        }
        for (final Map.Entry<Integer, ValueReference> entry : onClickMap.entrySet()) {
            OnClickListener onClickListener = new OnClickListener() {
                @Override public void onClick(View v) {
                    updateModel();
                    entry.getValue().invoke();
                    updateView();
                }
            };
            bindOnClickListener(onClickListener, entry.getKey());
        }

        updateView();
    }

    protected abstract void updateModel();

    protected abstract void bindOnClickListener(OnClickListener onClickListener, int viewId);

    private void initLists(final Object obj) {
        List<Method> methods = ReflectionUtils.getAnnotatedMethods(obj.getClass(), BindList.class);
        for (final Method method : methods) {
            final BindList annotation = method.getAnnotation(BindList.class);
            int viewId = annotation.value();
            int layoutId = annotation.layoutId();
            ValueReference valueReference = new ValueReference(method, obj, null);
            bindList(viewId, layoutId, valueReference);
        }
    }

    protected abstract void bindList(int viewId, int layoutId, ValueReference itemsCountValueReference);

    public void updateView() {
        for (Map.Entry<Integer, ValueReference> entry : gettersMap.entrySet()) {
            ValueReference valueReference = entry.getValue();
            Object value = valueReference.get();
            System.out.println(value + "-->" + valueReference);
            updateView(entry.getKey(), value, valueReference.getBindField());
        }
    }

    protected abstract void updateView(int viewId, Object value, BindField bindField);
}
