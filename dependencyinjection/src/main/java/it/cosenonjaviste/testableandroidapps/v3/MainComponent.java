package it.cosenonjaviste.testableandroidapps.v3;

import dagger.Component;

@Component(modules = MainModule.class)
public interface MainComponent {
    PostsBatch getBatch();
}
