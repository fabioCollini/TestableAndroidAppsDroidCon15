package it.cosenonjaviste.testableandroidapps.lib;

import android.app.Activity;
import android.os.Bundle;

import org.parceler.Parcels;

import it.cosenonjaviste.testableandroidapps.RetainedFragment;

public class AndrularMvpContext<M, V> {

    public static final String MODEL = "model";

    protected BaseContext context;

    private M model;

    private V view;

    private Presenter<M, V> presenter;

    public AndrularMvpContext(V view, Bundle state, RetainedFragment<MvpFactory<Presenter<M, V>>> retainedFragment) {
        this.presenter = retainedFragment.get().createPresenter();

        if (state != null) {
            model = Parcels.unwrap(state.getParcelable(MODEL));
        } else {
            model = presenter.createDefaultModel();
        }

        retainedFragment.setOnDestroy(c -> destroy());

        this.view = view;
        context = createAndrularContext(view, model, presenter);
    }

    protected BaseContext createAndrularContext(V view, M model, Presenter presenter) {
        return new AndrularContext((Activity) view, model, presenter);
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

    public M getModel() {
        return model;
    }

    public V getView() {
        return view;
    }
}
