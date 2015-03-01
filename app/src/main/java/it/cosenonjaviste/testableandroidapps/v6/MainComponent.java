package it.cosenonjaviste.testableandroidapps.v6;

import dagger.Component;

@Component(modules = MainModule.class)
public interface MainComponent {
    PostsBatch getBatch();
}
