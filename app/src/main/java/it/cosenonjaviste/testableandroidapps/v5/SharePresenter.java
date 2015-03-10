package it.cosenonjaviste.testableandroidapps.v5;

import javax.inject.Inject;

import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.ShareExecutor;

public class SharePresenter {

    private ShareActivity view;

    private ShareModel model;

    @Inject ShareExecutor shareExecutor;

    @Inject public SharePresenter() {
    }

    public void init(ShareActivity view, ShareModel model) {
        this.view = view;
        this.model = model;
        view.updateUi(model);
    }

    public ShareModel getModel() {
        return model;
    }

    public void updateModel(String title, String body) {
        model.setTitle(title);
        model.setBody(body);
    }

    public void validateFields(String title, String body) {
        updateModel(title, body);
        model.setTitleError(model.getTitle().isEmpty() ? R.string.mandatory_field : 0);
        model.setBodyError(model.getBody().isEmpty() ? R.string.mandatory_field : 0);
        view.updateUi(model);
    }

    public void share(String title, String body) {
        validateFields(title, body);
        if (model.getTitleError() == 0 && model.getBodyError() == 0) {
            shareExecutor.startSendActivity(title, body);
        }
    }
}
