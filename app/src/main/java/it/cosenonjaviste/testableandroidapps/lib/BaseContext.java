package it.cosenonjaviste.testableandroidapps.lib;

import android.view.View;
import android.widget.AdapterView;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.OnClickListener;

public abstract class BaseContext {
    protected ValueReferenceMap gettersMap = new ValueReferenceMap();
    protected ValueReferenceMap settersMap = new ValueReferenceMap();
    protected ValueReferenceMap itemMethodsMap = new ValueReferenceMap();
    protected Map<Integer, ValueReference> onClickMap = new HashMap<>();
    protected Map<Integer, ValueReference> onItemClickMap = new HashMap<>();

    public void init(Object... objs) {
        for (Object obj : objs) {
            ValueReferenceMap bindedFields = ReflectionUtils.getBindedFields(obj);

            gettersMap.putAll(bindedFields);
            gettersMap.putAll(ReflectionUtils.getBindedGetters(obj));

            settersMap.putAll(bindedFields);
            settersMap.putAll(ReflectionUtils.getBindedSetters(obj));

            itemMethodsMap.putAll(ReflectionUtils.getBindedItemMethods(obj));
            onClickMap.putAll(ReflectionUtils.getOnClickMethods(obj));
            onItemClickMap.putAll(ReflectionUtils.getOnItemClickMethods(obj));
        }
        for (Object obj : objs) {
            initLists(obj);
        }
        for (final Map.Entry<Integer, ValueReference> entry : onItemClickMap.entrySet()) {
            AdapterView.OnItemClickListener onClickListener = new AdapterView.OnItemClickListener() {
                @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    updateModel();
                    entry.getValue().invoke(position);
                    updateView();
                }
            };
            bindOnItemClickListener(onClickListener, entry.getKey());
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

    protected abstract void bindOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener, Integer listId);

    public void updateModel() {
        settersMap.doOnEach((viewId, valueReference) -> {
            if (valueReference.getBindField() == BindField.TEXT) {
                String text = getTextValue(viewId);
                valueReference.set(text);
            }
        });
    }

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
        gettersMap.doOnEach((viewId, valueReference) -> {
            Object value = valueReference.get();
            System.out.println(value + "-->" + valueReference);
            updateView(viewId, value, valueReference.getBindField());
        });
    }

    protected abstract void updateView(int viewId, Object value, BindField bindField);

    protected abstract String getTextValue(Integer viewId);
}
