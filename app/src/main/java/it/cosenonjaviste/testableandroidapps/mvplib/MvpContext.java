package it.cosenonjaviste.testableandroidapps.mvplib;

import android.content.Intent;
import android.os.Bundle;

import org.parceler.Parcels;

import it.cosenonjaviste.testableandroidapps.RetainedFragment;

public class MvpContext<M, V> {

    public static final String MODEL = "model";

    protected M model;

    protected V view;

    protected Presenter<M, V> presenter;

    public MvpContext(V view, Bundle state, RetainedFragment<Presenter<M, V>> retainedFragment) {
        this.presenter = retainedFragment.get();

        if (state != null) {
            model = Parcels.unwrap(state.getParcelable(MODEL));
        }
        if (model == null) {
            model = presenter.createDefaultModel();
        }

        retainedFragment.setOnDestroy(c -> presenter.destroy());

        this.view = view;
    }

    protected MvpContext() {
    }

    public static void populateIntent(Intent intent, Object model) {
        intent.putExtra(MODEL, Parcels.wrap(model));
    }

    public M getModel() {
        return model;
    }

    public V getView() {
        return view;
    }

    public void saveState(Bundle outState) {
        outState.putParcelable(MODEL, Parcels.wrap(model));
    }

    public void resume() {
        presenter.resume(this);
    }

    public void pause() {
        presenter.pause();
    }

    public Presenter<M, V> getPresenter() {
        return presenter;
    }
}
