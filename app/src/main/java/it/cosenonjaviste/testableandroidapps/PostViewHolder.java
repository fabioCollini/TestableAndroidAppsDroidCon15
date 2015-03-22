package it.cosenonjaviste.testableandroidapps;

import android.text.Html;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import it.cosenonjaviste.testableandroidapps.model.Post;

public class PostViewHolder {

    private final TextView text1;
    private final TextView text2;

    public PostViewHolder(View convertView) {
        text1 = (TextView) convertView.findViewById(android.R.id.text1);
        text2 = (TextView) convertView.findViewById(android.R.id.text2);
    }

    public void populate(Post post) {
        CharSequence dateFormatted = DateUtils.getRelativeTimeSpanString(text1.getContext(), post.getDate().getTime());

        text1.setText(Html.fromHtml(post.getTitle()));
        text2.setText(dateFormatted + ", " + post.getAuthor().getFirstName() + " " + post.getAuthor().getLastName());
    }
}
