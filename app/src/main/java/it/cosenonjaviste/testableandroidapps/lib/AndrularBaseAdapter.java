package it.cosenonjaviste.testableandroidapps.lib;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AndrularBaseAdapter extends BaseAdapter {
    private Activity activity;
    private final ValueReference itemCountValueReference;
    private ValueReferenceMap itemMethodsMap;
    private final int layoutId;

    public AndrularBaseAdapter(Activity activity, ValueReference itemCountValueReference, ValueReferenceMap itemMethodsMap, int layoutId) {
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
        View itemView;
        if (convertView == null) {
            itemView = LayoutInflater.from(activity).inflate(layoutId, parent, false);
        } else {
            itemView = convertView;
        }

        itemMethodsMap.doOnEach((viewId, valueReference) -> {
            TextView view = (TextView) itemView.findViewById(viewId);
            Object value = valueReference.get(position);
            System.out.println(value + "-->" + valueReference);
            view.setText(value.toString());
        });

        return convertView;
    }
}
