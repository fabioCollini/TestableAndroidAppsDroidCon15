package it.cosenonjaviste.testableandroidapps;

import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
        }
        Post post = getItem(position);

        CharSequence dateFormatted = DateUtils.getRelativeTimeSpanString(convertView.getContext(), post.getDate().getTime());

        TextView text1 = (TextView) convertView.findViewById(android.R.id.text1);
        TextView text2 = (TextView) convertView.findViewById(android.R.id.text2);

        text1.setText(Html.fromHtml(post.getTitle()));
        text2.setText(dateFormatted + ", " + post.getAuthor().getFirstName() + " " + post.getAuthor().getLastName());

        return convertView;
    }

    public List<Post> getItems() {
        return items;
    }
}
