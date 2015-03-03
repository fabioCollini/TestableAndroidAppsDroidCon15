package it.cosenonjaviste.testableandroidapps.v5;

import org.mockito.Mockito;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;

@Module
public class TestModule {
    @Provides @Singleton EmailSender provideEmailSender() {
        return Mockito.mock(EmailSender.class);
    }

    @Provides @Singleton WordPressService provideWordPressService() {
        return Mockito.mock(WordPressService.class);
    }

    @Provides @Singleton Executor provideExecutor() {
        return new Executor() {
            @Override public void execute(Runnable command) {
                command.run();
            }
        };
    }
}
