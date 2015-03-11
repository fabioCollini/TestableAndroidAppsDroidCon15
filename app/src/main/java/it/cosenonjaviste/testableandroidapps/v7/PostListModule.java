package it.cosenonjaviste.testableandroidapps.v7;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.testableandroidapps.MvpScope;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class PostListModule {

    @Provides @MvpScope PostListPresenter providesPresenter(WordPressService wordPressService) {
        return new PostListPresenter(wordPressService, Schedulers.io(), AndroidSchedulers.mainThread());
    }
}
