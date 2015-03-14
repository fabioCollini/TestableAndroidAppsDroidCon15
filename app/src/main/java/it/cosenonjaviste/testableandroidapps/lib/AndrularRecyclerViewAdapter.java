package it.cosenonjaviste.testableandroidapps.lib;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AndrularRecyclerViewAdapter extends RecyclerView.Adapter<AndrularViewHolder> {
    private Activity activity;
    private ValueReferenceMap itemMethodsMap;
    private final int layoutId;
    private final ValueReference itemCountValueReference;

    public AndrularRecyclerViewAdapter(Activity activity, ValueReferenceMap itemMethodsMap, ValueReference itemCountValueReference, int layoutId) {
        this.activity = activity;
        this.itemMethodsMap = itemMethodsMap;
        this.layoutId = layoutId;
        this.itemCountValueReference = itemCountValueReference;
    }

    @Override public AndrularViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(layoutId, viewGroup, false);
        return new AndrularViewHolder(view);
    }

    @Override public void onBindViewHolder(AndrularViewHolder andrularViewHolder, int i) {
        itemMethodsMap.doOnEach((viewId, valueReference) -> {
            TextView view = (TextView) andrularViewHolder.itemView.findViewById(viewId);
            Object value = valueReference.get(i);
            view.setText(value.toString());
        });
    }

    @Override public int getItemCount() {
        return (int) itemCountValueReference.get();
    }
}
