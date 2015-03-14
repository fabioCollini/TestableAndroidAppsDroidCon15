package it.cosenonjaviste.testableandroidapps.lib;

import android.view.View;
import android.widget.AdapterView;

import java.util.HashMap;
import java.util.Map;

public class AndrularTestContext extends BaseContext {

    private Map<Integer, Integer> listSizes;
    private Map<Integer, Object> viewValues = new HashMap<>();
    private Map<Integer, ValueReference> listSizesValueReferences;
    private Map<Integer, AdapterView.OnItemClickListener> onItemClickListenerMap = new HashMap<>();

    public AndrularTestContext(Object... objs) {
        init(objs);
    }

    @Override protected void updateModel() {

    }

    @Override protected void bindOnClickListener(View.OnClickListener onClickListener, int viewId) {

    }

    @Override protected void bindOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener, Integer listId) {
        onItemClickListenerMap.put(listId, onItemClickListener);
    }

    @Override protected void bindList(int viewId, int layoutId, ValueReference itemsCountValueReference) {
        listSizesValueReferences = new HashMap<>();
        listSizesValueReferences.put(viewId, itemsCountValueReference);
    }

    @Override public void updateView() {
        super.updateView();
        if (listSizes == null) {
            listSizes = new HashMap<>();
        }
        for (Map.Entry<Integer, ValueReference> entry : listSizesValueReferences.entrySet()) {
            listSizes.put(entry.getKey(), entry.getValue().get());
        }
    }

    @Override protected void updateView(int viewId, Object value, BindField bindField) {
        viewValues.put(viewId, value);
    }

    public void writeText(int editTextId, String text) {
        settersMap.get(editTextId).set(text);
    }

    public void click(int buttonId) {
        throw new RuntimeException();
//        try {
//            onClickMap.get(buttonId).invoke();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }

    public String getText(int textId) {
        Object obj = viewValues.get(textId);
        return obj == null ? "" : obj.toString();
    }

    public int getListSize(int listId) {
        return listSizes.get(listId);
    }

    public void clickOnItem(int listId, int position) {
        onItemClickListenerMap.get(listId).onItemClick(null, null, position, 0);
    }
}
