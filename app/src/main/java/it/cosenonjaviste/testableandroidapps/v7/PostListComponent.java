package it.cosenonjaviste.testableandroidapps.v7;

import dagger.Component;
import it.cosenonjaviste.testableandroidapps.MvpScope;

@MvpScope
@Component(dependencies = ApplicationComponent.class, modules = PostListModule.class)
public interface PostListComponent {
    void inject(PostListActivity activity);
}
