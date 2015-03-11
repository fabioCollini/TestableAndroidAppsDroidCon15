package it.cosenonjaviste.testableandroidapps.v7;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.testableandroidapps.ShareExecutor;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class TestModule {
    @Provides @Singleton ShareExecutor provideShareExecutor() {
        return Mockito.mock(ShareExecutor.class);
    }

    @Provides @Singleton WordPressService providesWordPressService() {
        return Mockito.mock(WordPressService.class);
    }

    @Provides PostListPresenter providesPresenter(WordPressService wordPressService) {
        return new PostListPresenter(wordPressService, Schedulers.io(), AndroidSchedulers.mainThread());
    }
}
