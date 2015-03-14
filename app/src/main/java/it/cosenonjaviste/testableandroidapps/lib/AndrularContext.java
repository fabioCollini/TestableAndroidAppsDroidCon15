package it.cosenonjaviste.testableandroidapps.lib;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AndrularContext extends BaseContext {
    private final Activity activity;

    private RecyclerView.Adapter<AndrularViewHolder> adapter;

    private BaseAdapter listAdapter;

    public AndrularContext(Activity activity, final Object... objs) {
        this.activity = activity;
        init(objs);
    }

    protected void bindList(int viewId, int layoutId, ValueReference itemsCountValueReference) {
        View view = activity.findViewById(viewId);
        if (view instanceof RecyclerView) {
            bindRecyclerView((RecyclerView) view, itemsCountValueReference, layoutId);
        } else if (view instanceof ListView) {
            bindListView((ListView) view, itemsCountValueReference, layoutId);
        }
    }

    protected void bindOnClickListener(View.OnClickListener onClickListener, int viewId) {
        activity.findViewById(viewId).setOnClickListener(onClickListener);
    }

    @Override protected void bindOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener, Integer viewId) {
        ListView list = (ListView) activity.findViewById(viewId);
        list.setOnItemClickListener(onItemClickListener);
    }

    private void bindListView(ListView listView, ValueReference itemCountValueReference, final int layoutId) {
        listAdapter = new AndrularBaseAdapter(activity, itemCountValueReference, itemMethodsMap, layoutId);
        listView.setAdapter(listAdapter);
    }

    private void bindRecyclerView(RecyclerView recyclerView, ValueReference itemCountValueReference, final int layoutId) {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new AndrularRecyclerViewAdapter(activity, itemMethodsMap, itemCountValueReference, layoutId);
        recyclerView.setAdapter(adapter);
    }

    public void updateView() {
        super.updateView();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        if (listAdapter != null) {
            listAdapter.notifyDataSetChanged();
        }
    }

    @Override protected void updateView(int viewId, Object value, BindField bindField) {
        if (bindField == BindField.TEXT) {
            String valueString = value == null ? "" : value.toString();
            View view = activity.findViewById(viewId);
            TextView textView = (TextView) view;
            if (!textView.getText().toString().equals(valueString)) {
                textView.setText(valueString);
            }
        } else if (bindField == BindField.VISIBILITY) {
            View view = activity.findViewById(viewId);
            view.setVisibility(Boolean.TRUE.equals(value) ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override protected String getTextValue(Integer viewId) {
        return ((TextView) activity.findViewById(viewId)).getText().toString();
    }
}
