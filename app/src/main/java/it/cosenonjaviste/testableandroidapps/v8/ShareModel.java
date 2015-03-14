package it.cosenonjaviste.testableandroidapps.v8;

import org.parceler.Parcel;

import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.lib.Bind;
import it.cosenonjaviste.testableandroidapps.lib.BindField;

@Parcel
public class ShareModel {
    @Bind(R.id.share_title)
    String title;

    @Bind(R.id.share_body)
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

    @Bind(value = R.id.share_title, field = BindField.ERROR)
    public int getTitleError() {
        return titleError;
    }

    public void setBodyError(int bodyError) {
        this.bodyError = bodyError;
    }

    @Bind(value = R.id.share_body, field = BindField.ERROR)
    public int getBodyError() {
        return bodyError;
    }
}
