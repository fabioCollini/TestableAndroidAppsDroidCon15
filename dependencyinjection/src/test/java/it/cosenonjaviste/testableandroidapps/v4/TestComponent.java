package it.cosenonjaviste.testableandroidapps.v4;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = TestModule.class)
public interface TestComponent {
    void inject(PostsBatchTest test);
}
