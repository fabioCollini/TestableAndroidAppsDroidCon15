package it.cosenonjaviste.testableandroidapps.v4;

import dagger.Component;

@Component(modules = MainModule.class)
public interface MainComponent {
    PostsBatch getBatch();
}
