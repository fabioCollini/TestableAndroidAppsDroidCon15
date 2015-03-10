package it.cosenonjaviste.testableandroidapps.v5;

import org.parceler.Parcel;

@Parcel
public class ShareModel {
    String title;

    String body;
    private int titleError;
    private int bodyError;

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
