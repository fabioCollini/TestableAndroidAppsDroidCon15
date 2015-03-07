package it.cosenonjaviste.testableandroidapps.v4;

import org.parceler.Parcel;

@Parcel
public class ShareModel {
    String title;

    String body;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
