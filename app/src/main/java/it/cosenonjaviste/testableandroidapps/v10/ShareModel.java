package it.cosenonjaviste.testableandroidapps.v10;

import org.parceler.Parcel;

@Parcel
public class ShareModel {
    String title;

    String body;

    int titleError;

    int bodyError;

    public ShareModel() {
    }

    public ShareModel(String title, String body) {
        this.title = title;
        this.body = body;
    }

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

    public void setTitleError(int titleError) {
        this.titleError = titleError;
    }

    public int getTitleError() {
        return titleError;
    }

    public void setBodyError(int bodyError) {
        this.bodyError = bodyError;
    }

    public int getBodyError() {
        return bodyError;
    }
}
