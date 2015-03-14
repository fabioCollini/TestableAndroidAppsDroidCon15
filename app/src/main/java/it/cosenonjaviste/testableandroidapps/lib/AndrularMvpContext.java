package it.cosenonjaviste.testableandroidapps.lib;

import it.cosenonjaviste.testableandroidapps.v8.PostListPresenter;

public class AndrularMvpContext<M, V> {
    private BaseContext context;

    private M model;

    private V view;

    private PostListPresenter presenter;

    public AndrularMvpContext(V view) {
        this.view = view;
    }
}
