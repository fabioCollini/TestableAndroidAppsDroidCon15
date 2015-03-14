package it.cosenonjaviste.testableandroidapps.lib;

public interface MvpFactory<P extends Presenter> {
    P createPresenter();
}
