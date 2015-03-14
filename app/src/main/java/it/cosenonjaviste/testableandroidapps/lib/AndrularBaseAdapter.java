package it.cosenonjaviste.testableandroidapps.lib;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Map;

public class AndrularBaseAdapter extends BaseAdapter {
    private Activity activity;
    private final ValueReference itemCountValueReference;
    private Map<Integer, ValueReference> itemMethodsMap;
    private final int layoutId;

    public AndrularBaseAdapter(Activity activity, ValueReference itemCountValueReference, Map<Integer, ValueReference> itemMethodsMap, int layoutId) {
        this.activity = activity;
        this.itemCountValueReference = itemCountValueReference;
        this.itemMethodsMap = itemMethodsMap;
        this.layoutId = layoutId;
    }

    @Override public int getCount() {
        int count = itemCountValueReference.get();
        System.out.println(count + "-->" + itemCountValueReference);
        return count;
    }

    @Override public Object getItem(int position) {
        return null;
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(layoutId, parent, false);
        }

        for (Map.Entry<Integer, ValueReference> entry : itemMethodsMap.entrySet()) {
            TextView view = (TextView) convertView.findViewById(entry.getKey());
            Object value = entry.getValue().get(position);
            System.out.println(value + "-->" + entry.getValue());
            view.setText(value.toString());
        }

        return convertView;
    }
}
