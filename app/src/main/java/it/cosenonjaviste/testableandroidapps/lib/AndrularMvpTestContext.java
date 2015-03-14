package it.cosenonjaviste.testableandroidapps.lib;

import it.cosenonjaviste.testableandroidapps.RetainedFragment;

public class AndrularMvpTestContext<M, V> extends AndrularMvpContext<M, V> {
    public AndrularMvpTestContext(V view, Presenter<M, V> presenter) {
        super(view, null, RetainedFragment.create(new MvpFactory<Presenter<M, V>>() {
            @Override public Presenter<M, V> createPresenter() {
                return presenter;
            }
        }));
    }

    @Override protected BaseContext createAndrularContext(Object view, Object model, Presenter presenter) {
        return new AndrularTestContext(model, presenter);
    }

    public int getListSize(int list) {
        return getContext().getListSize(list);
    }

    private AndrularTestContext getContext() {
        return (AndrularTestContext) context;
    }
}
