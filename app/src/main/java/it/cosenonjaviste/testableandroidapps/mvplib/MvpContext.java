package it.cosenonjaviste.testableandroidapps.mvplib;

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
        } else {
            model = presenter.createDefaultModel();
        }

        retainedFragment.setOnDestroy(c -> presenter.destroy());

        this.view = view;
    }

    protected MvpContext() {
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
}