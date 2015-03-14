package it.cosenonjaviste.testableandroidapps.v7;

import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.testableandroidapps.ApplicationComponent;

@Singleton
@Component(modules = TestModule.class)
public interface TestComponent extends ApplicationComponent {
    void inject(PostListActivityTest test);

    void inject(ShareActivityTest test);
}
