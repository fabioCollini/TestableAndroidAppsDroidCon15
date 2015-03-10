package it.cosenonjaviste.testableandroidapps.v5;

import org.parceler.Parcel;

import java.util.List;

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

    public String getErrorText() {
        return errorText;
    }
}
