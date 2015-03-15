package it.cosenonjaviste.testableandroidapps.mvplib;

public interface Presenter<M, V> {
    void resume(MvpContext<M, V> mvpContext);

    void pause();

    void destroy();

    M createDefaultModel();
}
