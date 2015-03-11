package it.cosenonjaviste.testableandroidapps.v6;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(PostListActivity activity);

    void inject(ShareActivity activity);

    PostListPresenter getPostListPresenter();

    SharePresenter getSharePresenter();
}
