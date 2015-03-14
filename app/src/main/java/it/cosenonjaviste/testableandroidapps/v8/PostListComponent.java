package it.cosenonjaviste.testableandroidapps.v8;

import dagger.Component;
import it.cosenonjaviste.testableandroidapps.MvpScope;
import it.cosenonjaviste.testableandroidapps.lib.MvpFactory;
import it.cosenonjaviste.testableandroidapps.lib.Presenter;

@MvpScope
@Component(dependencies = ApplicationComponent.class, modules = PostListModule.class)
public interface PostListComponent extends MvpFactory<Presenter<PostListModel, PostListActivity>> {
    PostListPresenter createPresenter();
}
