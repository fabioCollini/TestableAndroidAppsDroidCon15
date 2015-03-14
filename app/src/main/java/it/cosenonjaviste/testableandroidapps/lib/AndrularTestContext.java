package it.cosenonjaviste.testableandroidapps.lib;

import android.view.View;
import android.widget.AdapterView;

import java.util.HashMap;
import java.util.Map;

public class AndrularTestContext extends BaseContext {

    private Map<Integer, Integer> listSizes;
    private Map<Integer, Object> viewValues = new HashMap<>();
    private Map<Integer, ValueReference> listSizesValueReferences = new HashMap<>();
    private Map<Integer, AdapterView.OnItemClickListener> onItemClickListenerMap = new HashMap<>();
    private Map<Integer, View.OnClickListener> onClickListenerMap = new HashMap<>();

    public AndrularTestContext(Object... objs) {
        init(objs);
    }

    @Override protected String getTextValue(Integer viewId) {
        Object obj = viewValues.get(viewId);
        return obj == null ? "" : obj.toString();
    }

    @Override protected void bindOnClickListener(View.OnClickListener onClickListener, int viewId) {
        onClickListenerMap.put(viewId, onClickListener);
    }

    @Override protected void bindOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener, Integer listId) {
        onItemClickListenerMap.put(listId, onItemClickListener);
    }

    @Override protected void bindList(int viewId, int layoutId, ValueReference itemsCountValueReference) {
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
        if (bindField == BindField.TEXT) {
            viewValues.put(viewId, value);
        }
    }

    public void writeText(int viewId, String text) {
        viewValues.put(viewId, text);
    }

    public void click(int buttonId) {
        onClickListenerMap.get(buttonId).onClick(null);
    }

    public int getListSize(int listId) {
        return listSizes.get(listId);
    }

    public void clickOnItem(int listId, int position) {
        onItemClickListenerMap.get(listId).onItemClick(null, null, position, 0);
    }
}
