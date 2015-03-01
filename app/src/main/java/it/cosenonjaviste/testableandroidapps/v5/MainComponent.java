package it.cosenonjaviste.testableandroidapps.v5;

import dagger.Component;

@Component(modules = MainModule.class)
public interface MainComponent {
    PostsBatch getBatch();
}
