package it.cosenonjaviste.testableandroidapps.v4;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(PostListActivity activity);

    void inject(ShareActivity activity);
}
