package it.cosenonjaviste.testableandroidapps.v8;

import android.text.Html;
import android.text.Spanned;

import org.parceler.Parcel;

import java.util.List;

import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.lib.Bind;
import it.cosenonjaviste.testableandroidapps.lib.BindField;
import it.cosenonjaviste.testableandroidapps.lib.BindItem;
import it.cosenonjaviste.testableandroidapps.lib.BindList;
import it.cosenonjaviste.testableandroidapps.model.Post;

@Parcel
public class PostListModel {
    List<Post> items;

    String errorText;

    public List<Post> getItems() {
        return items;
    }

    public void setItems(List<Post> items) {
        this.items = items;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    @Bind(R.id.error_text)
    public String getErrorText() {
        return errorText;
    }

    @Bind(value = R.id.error_layout, field = BindField.VISIBILITY)
    public boolean isErrorVisible() {
        return errorText != null;
    }

    @Bind(value = R.id.list, field = BindField.VISIBILITY)
    public boolean isDataAvailable() {
        return items != null;
    }

    @BindList(value = R.id.list, layoutId = android.R.layout.simple_list_item_2)
    public int getItemsSize() {
        return items == null ? 0 : items.size();
    }

    @BindItem(android.R.id.text1)
    public Spanned getTitle(int pos) {
        Post post = items.get(pos);
        return Html.fromHtml(post.getTitle());
    }

    @BindItem(android.R.id.text2)
    public String getSubTitle(int pos) {
        Post post = items.get(pos);
//        CharSequence dateFormatted = DateUtils.getRelativeTimeSpanString(convertView.getContext(), post.getDate().getTime());
        return //dateFormatted + ", " +
                post.getAuthor().getFirstName() + " " + post.getAuthor().getLastName();
    }
}
