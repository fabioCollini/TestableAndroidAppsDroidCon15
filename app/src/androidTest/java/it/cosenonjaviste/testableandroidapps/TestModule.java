package it.cosenonjaviste.testableandroidapps;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;
import it.cosenonjaviste.testableandroidapps.utils.EspressoExecutor;
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

    @Provides @Singleton SchedulerManager providesSchedulerManager() {
        return new SchedulerManager(Schedulers.from(EspressoExecutor.getCachedThreadPool()), AndroidSchedulers.mainThread());
    }
}
