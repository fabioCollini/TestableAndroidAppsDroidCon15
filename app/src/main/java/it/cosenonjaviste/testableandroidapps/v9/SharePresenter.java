package it.cosenonjaviste.testableandroidapps.v9;

import android.widget.EditText;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.ShareExecutor;
import it.cosenonjaviste.testableandroidapps.mvplib.MvpContext;
import it.cosenonjaviste.testableandroidapps.mvplib.Presenter;

public class SharePresenter implements Presenter<ShareModel, ShareActivity> {

    private ShareExecutor shareExecutor;

    private MvpContext<ShareModel, ShareActivity> mvpContext;

    @InjectView(R.id.share_title) EditText titleEditText;

    @InjectView(R.id.share_body) EditText bodyEditText;

    @Inject public SharePresenter(ShareExecutor shareExecutor) {
        this.shareExecutor = shareExecutor;
    }

    @Override public void resume(MvpContext<ShareModel, ShareActivity> mvpContext) {
        this.mvpContext = mvpContext;
        updateUi(mvpContext.getModel());
    }

    public ShareModel getModel() {
        return mvpContext.getModel();
    }

    public void updateModel(String title, String body) {
        getModel().setTitle(title);
        getModel().setBody(body);
    }

    @OnFocusChange({R.id.share_title, R.id.share_body})
    public void validateFields() {
        String title = titleEditText.getText().toString();
        String body = bodyEditText.getText().toString();
        updateModel(title, body);
        ShareModel model = getModel();
        model.setTitleError(model.getTitle().isEmpty() ? R.string.mandatory_field : 0);
        model.setBodyError(model.getBody().isEmpty() ? R.string.mandatory_field : 0);
        updateUi(model);
    }

    @OnClick(R.id.share_button)
    public void share() {
        validateFields();
        ShareModel model = getModel();
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
        return error == 0 ? null : mvpContext.getView().getString(error);
    }

    @Override public void pause() {

    }

    @Override public void destroy() {

    }

    @Override public ShareModel createDefaultModel() {
        return new ShareModel();
    }
}
