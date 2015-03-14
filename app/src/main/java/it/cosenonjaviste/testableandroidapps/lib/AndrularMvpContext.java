package it.cosenonjaviste.testableandroidapps.lib;

import android.app.Activity;
import android.os.Bundle;

import org.parceler.Parcels;

import it.cosenonjaviste.testableandroidapps.RetainedFragment;
import it.cosenonjaviste.testableandroidapps.v8.PostListActivity;
import it.cosenonjaviste.testableandroidapps.v8.PostListComponent;
import it.cosenonjaviste.testableandroidapps.v8.PostListModel;
import it.cosenonjaviste.testableandroidapps.v8.PostListPresenter;

public class AndrularMvpContext<M, V> {

    public static final String MODEL = "model";

    protected BaseContext context;

    private PostListModel model;

    private V view;

    private PostListPresenter presenter;

    public AndrularMvpContext(Activity view, Bundle state, RetainedFragment<PostListComponent> retainedFragment) {
        if (state != null) {
            model = Parcels.unwrap(state.getParcelable(MODEL));
        } else {
            model = new PostListModel();
        }

        this.presenter = retainedFragment.get().createPresenter();

        retainedFragment.setOnDestroy(c -> destroy());

        this.view = (V) view;
        context = createAndrularContext(view, model, presenter);
    }

    protected BaseContext createAndrularContext(Activity view, PostListModel model, PostListPresenter presenter) {
        return new AndrularContext(view, model, presenter);
    }

    public void resume() {
        presenter.resume(this);
    }

    public void pause() {
        presenter.pause();
    }

    public void saveState(Bundle outState) {
        outState.putParcelable(MODEL, Parcels.wrap(model));
    }

    public void destroy() {
        presenter.destroy();
    }

    public void updateView() {
        context.updateView();
    }

    public PostListModel getModel() {
        return model;
    }

    public PostListActivity getView() {
        return (PostListActivity) view;
    }
}
