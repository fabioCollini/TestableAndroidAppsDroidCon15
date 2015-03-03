package it.cosenonjaviste.testableandroidapps.v5;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = MainModule.class)
public interface MainComponent {
    PostsBatch getBatch();
}
