package it.cosenonjaviste.testableandroidapps.v3;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = MainModule.class)
public interface MainComponent {
    PostBatch getBatch();
}
