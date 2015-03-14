package it.cosenonjaviste.testableandroidapps.v8;

import javax.inject.Inject;

import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.ShareExecutor;
import it.cosenonjaviste.testableandroidapps.lib.AndrularMvpContext;
import it.cosenonjaviste.testableandroidapps.lib.OnClick;
import it.cosenonjaviste.testableandroidapps.lib.Presenter;

public class SharePresenter implements Presenter<ShareModel, ShareActivity> {

    private ShareExecutor shareExecutor;

    private AndrularMvpContext<ShareModel, ShareActivity> andrularMvpContext;

    @Inject public SharePresenter(ShareExecutor shareExecutor) {
        this.shareExecutor = shareExecutor;
    }

    public void validateFields() {
        ShareModel model = andrularMvpContext.getModel();
        model.setTitleError(model.getTitle().isEmpty() ? R.string.mandatory_field : 0);
        model.setBodyError(model.getBody().isEmpty() ? R.string.mandatory_field : 0);
//        view.updateUi(model);
    }

    @OnClick(R.id.share_button)
    public void share() {
        validateFields();
        ShareModel model = andrularMvpContext.getModel();
        if (model.getTitleError() == 0 && model.getBodyError() == 0) {
            shareExecutor.startSendActivity(model.getTitle(), model.getBody());
        }
    }

    @Override public void resume(AndrularMvpContext<ShareModel, ShareActivity> andrularMvpContext) {
        this.andrularMvpContext = andrularMvpContext;
    }

    @Override public void pause() {

    }

    @Override public void destroy() {

    }

    @Override public ShareModel createDefaultModel() {
        return new ShareModel();
    }
}
