package it.cosenonjaviste.testableandroidapps.mvplib;

import android.widget.TextView;

public class MvpTestContext<M, V> extends MvpContext<M, V> {

    private TestInjector injector = new TestInjector();

    public MvpTestContext(V view, Presenter<M, V> presenter) {
        this.model = presenter.createDefaultModel();
        this.view = view;
        this.presenter = presenter;

        injector.inject(presenter);
    }

    public <T> T getView(int viewId) {
        return injector.getView(viewId);
    }

    public TextView getTextView(int viewId) {
        return injector.getTextView(viewId);
    }

    public void clickOnView(int viewId) {
        injector.clickOnView(viewId);
    }

    public void clickOnItem(int viewId, int position) {
        injector.clickOnItem(viewId, position);
    }

    public void setModel(M model) {
        this.model = model;
    }
}
