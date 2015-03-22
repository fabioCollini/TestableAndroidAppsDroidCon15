package it.cosenonjaviste.testableandroidapps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import it.cosenonjaviste.testableandroidapps.model.Post;

public class PostAdapter extends BaseAdapter {

    private List<Post> items = new ArrayList<>();

    private LayoutInflater inflater;

    public PostAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void addItems(List<Post> posts) {
        items.addAll(posts);
        notifyDataSetChanged();
    }

    public void setItems(List<Post> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override public int getCount() {
        return items.size();
    }

    @Override public Post getItem(int position) {
        return items.get(position);
    }

    @Override public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        PostViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
            viewHolder = new PostViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PostViewHolder) convertView.getTag();
        }
        Post post = getItem(position);

        viewHolder.populate(post);

        return convertView;
    }

    public List<Post> getItems() {
        return items;
    }
}
