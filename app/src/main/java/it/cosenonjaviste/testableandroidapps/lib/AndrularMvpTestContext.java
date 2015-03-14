package it.cosenonjaviste.testableandroidapps.lib;

import android.app.Activity;

import it.cosenonjaviste.testableandroidapps.RetainedFragment;
import it.cosenonjaviste.testableandroidapps.v8.PostListComponent;
import it.cosenonjaviste.testableandroidapps.v8.PostListModel;
import it.cosenonjaviste.testableandroidapps.v8.PostListPresenter;

public class AndrularMvpTestContext extends AndrularMvpContext {
    public AndrularMvpTestContext(Activity view, PostListPresenter presenter) {
        super(view, null, RetainedFragment.create(new PostListComponent() {
            @Override public PostListPresenter createPresenter() {
                return presenter;
            }
        }));
    }

    @Override protected BaseContext createAndrularContext(Activity view, PostListModel model, PostListPresenter presenter) {
        return new AndrularTestContext(model, presenter);
    }

    public int getListSize(int list) {
        return getContext().getListSize(list);
    }

    private AndrularTestContext getContext() {
        return (AndrularTestContext) context;
    }
}
