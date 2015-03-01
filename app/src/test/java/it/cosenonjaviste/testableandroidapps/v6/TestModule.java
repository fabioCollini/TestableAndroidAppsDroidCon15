package it.cosenonjaviste.testableandroidapps.v6;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.testableandroidapps.v3.WordPressService;

@Module
public class TestModule {
    @Provides @Singleton EmailSender provideEmailSender() {
        return Mockito.mock(EmailSender.class);
    }

    @Provides @Singleton WordPressService provideWordPressService() {
        return Mockito.mock(WordPressService.class);
    }
}
