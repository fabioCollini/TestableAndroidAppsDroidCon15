package it.cosenonjaviste.testableandroidapps.lib;

public interface Presenter<M, V> {
    void resume(AndrularMvpContext<M, V> andrularMvpContext);

    void pause();

    void destroy();

    M createDefaultModel();
}
