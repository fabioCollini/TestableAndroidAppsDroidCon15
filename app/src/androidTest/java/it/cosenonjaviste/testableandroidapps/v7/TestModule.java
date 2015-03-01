package it.cosenonjaviste.testableandroidapps.v7;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;

@Module
public class TestModule {
    @Provides @Singleton ShareExecutor provideShareExecutor() {
        return Mockito.mock(ShareExecutor.class);
    }

    @Provides @Singleton WordPressService providesWordPressService() {
        return Mockito.mock(WordPressService.class);
    }
}
