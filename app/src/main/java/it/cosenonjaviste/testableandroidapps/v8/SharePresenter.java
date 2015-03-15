package it.cosenonjaviste.testableandroidapps.v8;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.ShareExecutor;

public class SharePresenter {

    private ShareActivity view;

    private ShareModel model;

    private ShareExecutor shareExecutor;

    @InjectView(R.id.share_title) EditText titleEditText;

    @InjectView(R.id.share_body) EditText bodyEditText;

    @Inject public SharePresenter(ShareExecutor shareExecutor) {
        this.shareExecutor = shareExecutor;
    }

    public void init(ShareActivity view, ShareModel model) {
        this.view = view;
        this.model = model;
        updateUi(model);
    }

    public ShareModel getModel() {
        return model;
    }

    public void updateModel(String title, String body) {
        model.setTitle(title);
        model.setBody(body);
    }

    @OnFocusChange({R.id.share_title, R.id.share_body})
    public void validateFields() {
        String title = titleEditText.getText().toString();
        String body = bodyEditText.getText().toString();
        updateModel(title, body);
        model.setTitleError(model.getTitle().isEmpty() ? R.string.mandatory_field : 0);
        model.setBodyError(model.getBody().isEmpty() ? R.string.mandatory_field : 0);
        updateUi(model);
    }

    @OnClick(R.id.share_button)
    public void share() {
        validateFields();
        if (model.getTitleError() == 0 && model.getBodyError() == 0) {
            shareExecutor.startSendActivity(model.getTitle(), model.getBody());
        }
    }

    public void updateUi(ShareModel model) {
        titleEditText.setText(model.getTitle());
        bodyEditText.setText(model.getBody());
        titleEditText.setError(getErrorString(model.getTitleError()));
        bodyEditText.setError(getErrorString(model.getBodyError()));
    }

    private String getErrorString(int error) {
        return error == 0 ? null : view.getString(error);
    }
}
