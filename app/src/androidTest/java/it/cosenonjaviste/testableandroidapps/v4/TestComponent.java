package it.cosenonjaviste.testableandroidapps.v4;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = TestModule.class)
public interface TestComponent extends ApplicationComponent {
    void inject(PostListActivityTest test);

    void inject(ShareActivityTest test);
}
